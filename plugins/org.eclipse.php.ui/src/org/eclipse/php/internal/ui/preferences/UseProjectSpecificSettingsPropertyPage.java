/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceSorter;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.osgi.service.prefs.BackingStoreException;

public abstract class UseProjectSpecificSettingsPropertyPage extends
		PropertyPage implements IWorkbenchPreferencePage {
	/*
	 * Disable link data, prevents the display of a "workspace" or "project"
	 * settings link to prevent recursive dialog launching
	 */
	private static final String DISABLE_LINK = "DISABLE_LINK"; //$NON-NLS-1$

	private Map fData = null;

	private Button fEnableProjectSettings;

	private Link fProjectSettingsLink;

	public UseProjectSpecificSettingsPropertyPage() {
		super();
	}

	public final void applyData(Object data) {
		super.applyData(data);
		if (data instanceof Map) {
			fData = (Map) data;
			updateLinkEnablement();
		}
	}

	protected abstract Control createCommonContents(Composite composite);

	public final Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(data);

		Composite checkLinkComposite = new Composite(composite, SWT.NONE);
		checkLinkComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));
		checkLinkComposite.setLayout(new GridLayout(2, false));

		if (getProject() != null) {
			fEnableProjectSettings = new Button(checkLinkComposite, SWT.CHECK);
			fEnableProjectSettings.setText(SSEUIMessages.EnableProjectSettings);
			fEnableProjectSettings.setLayoutData(new GridData(SWT.BEGINNING,
					SWT.CENTER, false, false));
			boolean enabledForProject = new ProjectScope(getProject()).getNode(
					getPreferenceNodeQualifier()).getBoolean(
					getProjectSettingsKey(), false);
			fEnableProjectSettings.setSelection(enabledForProject);
		} else {
			Label spacer = new Label(checkLinkComposite, SWT.CHECK);
			spacer.setLayoutData(new GridData());
		}

		fProjectSettingsLink = new Link(checkLinkComposite, SWT.NONE);
		fProjectSettingsLink.setFont(composite.getFont());
		fProjectSettingsLink.setLayoutData(new GridData(SWT.END, SWT.BEGINNING,
				true, false));

		/*
		 * "element" should be a project, if null, link to per-project
		 * properties
		 */
		if (getProject() != null) {
			fProjectSettingsLink
					.setText("<a>" + SSEUIMessages.ConfigureWorkspaceSettings + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$ 
		} else {
			fProjectSettingsLink
					.setText("<a>" + SSEUIMessages.ConfigureProjectSettings + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		updateLinkEnablement();

		fProjectSettingsLink.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				if (getProject() == null) {
					openProjectSettings();
				} else {
					openWorkspaceSettings();
				}
			}

		});

		if (getProject() != null) {
			Label line = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
			line.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		}

		final Control common = createCommonContents(composite);
		common.setLayoutData(new GridData(GridData.FILL_BOTH));

		if (fEnableProjectSettings != null) {
			SelectionAdapter selectionAdapter = new SelectionAdapter() {
				ControlEnableState enablements = null;

				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					if (fEnableProjectSettings.getSelection()) {
						if (enablements != null) {
							enablements.restore();
							enablements = null;
						}
					} else {
						enablements = ControlEnableState.disable(common);
					}
				}
			};
			selectionAdapter.widgetSelected(null);
			fEnableProjectSettings.addSelectionListener(selectionAdapter);
		}
		// HELP - Waiting for Keren
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
		// IPHPHelpContextIds.???);
		return composite;
	}

	protected abstract String getPreferenceNodeQualifier();

	protected abstract String getPreferencePageID();

	protected IProject getProject() {
		if (getElement() != null && getElement() instanceof IProject) {
			return (IProject) getElement();
		}
		return null;
	}

	protected abstract String getProjectSettingsKey();

	protected abstract String getPropertyPageID();

	protected boolean isElementSettingsEnabled() {
		return fEnableProjectSettings != null
				&& fEnableProjectSettings.getSelection();
	}

	void openProjectSettings() {
		ListDialog dialog = new ListDialog(getShell()) {

			protected Control createDialogArea(Composite container) {
				Control area = super.createDialogArea(container);
				getTableViewer().setSorter(
						new ResourceSorter(ResourceSorter.NAME));
				return area;
			}
		};
		dialog.setMessage(SSEUIMessages.PropertyPreferencePage_02);
		dialog.setContentProvider(new IStructuredContentProvider() {
			public void dispose() {
			}

			public Object[] getElements(Object inputElement) {
				return ((IWorkspace) inputElement).getRoot().getProjects();
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
		dialog.setLabelProvider(new DecoratingLabelProvider(
				new WorkbenchLabelProvider(), SSEUIPlugin.getDefault()
						.getWorkbench().getDecoratorManager()
						.getLabelDecorator()));
		dialog.setInput(ResourcesPlugin.getWorkspace());
		dialog.setTitle(SSEUIMessages.PropertyPreferencePage_01);
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length > 0) {
				IProject project = (IProject) dialog.getResult()[0];
				Map data = new HashMap();
				data.put(DISABLE_LINK, Boolean.TRUE);
				PreferencesUtil.createPropertyDialogOn(getShell(), project,
						getPropertyPageID(),
						new String[] { getPropertyPageID() }, data).open();
			}
		}
	}

	void openWorkspaceSettings() {
		Map data = new HashMap();
		data.put(DISABLE_LINK, Boolean.TRUE);
		PreferencesUtil.createPreferenceDialogOn(getShell(),
				getPreferencePageID(), new String[] { getPreferencePageID() },
				data).open();
	}

	public boolean performOk() {
		boolean ok = super.performOk();
		if (getProject() != null) {
			if (isElementSettingsEnabled()) {
				new ProjectScope(getProject()).getNode(
						getPreferenceNodeQualifier()).putBoolean(
						getProjectSettingsKey(),
						fEnableProjectSettings.getSelection());
			} else {
				new ProjectScope(getProject()).getNode(
						getPreferenceNodeQualifier()).remove(
						getProjectSettingsKey());
			}
			try {
				new ProjectScope(getProject()).getNode(
						getPreferenceNodeQualifier()).flush();
			} catch (BackingStoreException e) {
				Logger.logException(
						"problem saving preference settings to scope " //$NON-NLS-1$
								+ new ProjectScope(getProject()).getName(), e);
				ok = false;
			}
		}
		return ok;
	}

	private void updateLinkEnablement() {
		if (fData != null && fProjectSettingsLink != null) {
			fProjectSettingsLink.setEnabled(!Boolean.TRUE.equals(fData
					.get(DISABLE_LINK)));
		}
	}
}
