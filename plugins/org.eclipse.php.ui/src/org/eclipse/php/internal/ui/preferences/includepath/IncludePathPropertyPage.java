/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Property page for configuring the include path
 */
public class IncludePathPropertyPage extends PropertyPage implements IStatusChangeListener {

	public static final String PROP_ID = "org.eclipse.php.ui.propertyPages.IncludePathPropertyPage"; //$NON-NLS-1$

	private static final String PAGE_SETTINGS = "IncludePathPropertyPage"; //$NON-NLS-1$
	private static final String INDEX = "pageIndex"; //$NON-NLS-1$

	public static final Object DATA_REVEAL_ENTRY = "select_includepath_entry"; //$NON-NLS-1$
	public static final Object DATA_REVEAL_ATTRIBUTE_KEY = "select_includepath_attribute_key"; //$NON-NLS-1$

	private IncludePathBlock fIncludePathsBlock;

	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	protected Control createContents(Composite parent) {
		// ensure the page has no special buttons
		noDefaultAndApplyButton();

		IProject project = getProject();
		Control result;
		if (project == null || !isPHPProject(project)) {
			result = createWithoutPHP(parent);
		} else if (!project.isOpen()) {
			result = createForClosedProject(parent);
		} else {
			result = createWithPHP(parent, project);
		}
		Dialog.applyDialogFont(result);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.ADDING_ELEMENTS_TO_A_PROJECT_S_INCLUDE_PATH);
		return result;
	}

	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		//HELP
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.ADDING_ELEMENTS_TO_A_PROJECT_S_INCLUDE_PATH);
	}

	private IDialogSettings getSettings() {
		IDialogSettings phpSettings = PHPUiPlugin.getDefault().getDialogSettings();
		IDialogSettings pageSettings = phpSettings.getSection(PAGE_SETTINGS);
		if (pageSettings == null) {
			pageSettings = phpSettings.addNewSection(PAGE_SETTINGS);
			pageSettings.put(INDEX, 0);
		}
		return pageSettings;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		if (fIncludePathsBlock != null) {
			if (!visible) {
				if (fIncludePathsBlock.hasChangesInDialog()) {
					String title = PHPUIMessages.getString("IncludePathsPropertyPage_unsavedchanges_title");
					String message = PHPUIMessages.getString("IncludePathsPropertyPage_unsavedchanges_message");
					String[] buttonLabels = new String[] { PHPUIMessages.getString("IncludePathsPropertyPage_unsavedchanges_button_save"), PHPUIMessages.getString("IncludePathsPropertyPage_unsavedchanges_button_discard"), PHPUIMessages.getString("IncludePathsPropertyPage_unsavedchanges_button_ignore") };
					MessageDialog dialog = new MessageDialog(getShell(), title, null, message, MessageDialog.QUESTION, buttonLabels, 0);
					int res = dialog.open();
					if (res == 0) {
						performOk();
					} else if (res == 1) {
						fIncludePathsBlock.init(getProject(), null);
					} else {
						// keep unsaved
					}
				}
			}
		}
		super.setVisible(visible);
	}

	/*
	 * Content for valid projects.
	 */
	private Control createWithPHP(Composite parent, IProject project) {
		IWorkbenchPreferenceContainer pageContainer = null;
		IPreferencePageContainer container = getContainer();
		if (container instanceof IWorkbenchPreferenceContainer) {
			pageContainer = (IWorkbenchPreferenceContainer) container;
		}

		fIncludePathsBlock = new IncludePathBlock(new BusyIndicatorRunnableContext(), this, getSettings().getInt(INDEX), false, pageContainer);
		fIncludePathsBlock.init(project, null);
		return fIncludePathsBlock.createControl(parent);
	}

	/*
	 * Content for non-PHP projects.
	 */
	private Control createWithoutPHP(Composite parent) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(PHPUIMessages.getString("IncludePathsPropertyPage_no_php_project_message"));

		fIncludePathsBlock = null;
		setValid(true);
		return label;
	}

	/*
	 * Content for closed projects.
	 */
	private Control createForClosedProject(Composite parent) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(PHPUIMessages.getString("IncludePathsPropertyPage_closed_project_message"));

		fIncludePathsBlock = null;
		setValid(true);
		return label;
	}

	private IProject getProject() {
		IAdaptable adaptable = getElement();
		if (adaptable != null) {
			if (adaptable instanceof IProject)
				return (IProject) adaptable;

		}
		return null;
	}

	private boolean isPHPProject(IProject proj) {
		try {
			return proj.hasNature(PHPNature.ID);
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}
		return false;
	}

	/*
	 * @see IPreferencePage#performOk
	 */
	public boolean performOk() {
		if (fIncludePathsBlock != null) {
			getSettings().put(INDEX, fIncludePathsBlock.getPageIndex());
			if (fIncludePathsBlock.hasChangesInDialog()) {
				IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
						fIncludePathsBlock.configurePHPProject(monitor);
					}
				};
				WorkbenchRunnableAdapter op = new WorkbenchRunnableAdapter(runnable);
				op.runAsUserJob(PHPUIMessages.getString("IncludePathsPropertyPage_job_title"), null);
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see IStatusChangeListener#statusChanged
	 */
	public void statusChanged(IStatus status) {
		setValid(!status.matches(IStatus.ERROR));
		applyToStatusLine(this, status);
	}

	public static void applyToStatusLine(DialogPage page, IStatus status) {
		String message = status.getMessage();
		switch (status.getSeverity()) {
			case IStatus.OK:
				page.setMessage(message, IMessageProvider.NONE);
				page.setErrorMessage(null);
				break;
			case IStatus.WARNING:
				page.setMessage(message, IMessageProvider.WARNING);
				page.setErrorMessage(null);
				break;
			case IStatus.INFO:
				page.setMessage(message, IMessageProvider.INFORMATION);
				page.setErrorMessage(null);
				break;
			default:
				if (message.length() == 0) {
					message = null;
				}
				page.setMessage(null);
				page.setErrorMessage(message);
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#applyData(java.lang.Object)
	 */
	public void applyData(Object data) {
		if (data instanceof Map) {
			Map map = (Map) data;
			Object selectedLibrary = map.get(DATA_REVEAL_ENTRY);
			if (selectedLibrary instanceof IIncludePathEntry) {
				IIncludePathEntry entry = (IIncludePathEntry) selectedLibrary;
				Object attr = map.get(DATA_REVEAL_ATTRIBUTE_KEY);
				String attributeKey = attr instanceof String ? (String) attr : null;
				if (fIncludePathsBlock != null) {
					fIncludePathsBlock.setElementToReveal(entry, attributeKey);
				}
			}
		}
	}

	public void dispose() {
		fIncludePathsBlock.dispose();
	}

}
