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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class SaveActionsPreferencePage extends PropertyAndPreferencePage {
	SaveActionsConfigurationBlock fConfigurationBlock;
	public static final String PREF_ID = "org.eclipse.php.ui.preferences.PHPEditorSaveActionsPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.php.ui.propertyPages.PHPEditorSaveActionsPreferencePage"; //$NON-NLS-1$

	public SaveActionsPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(PHPUIMessages.PHPSaveActionsPreferencePage_title);
	}

	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = new SaveActionsConfigurationBlock(getNewStatusChangedListener(), getProject(), container);

		super.createControl(parent);

		// TODO - Set the Help context ID
		if (isProjectPreferencePage()) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.SAVE_ACTIONS_PROPERTIES);
		} else {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
					IPHPHelpContextIds.SAVE_ACTIONS_PREFERENCES);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * createPreferenceContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createPreferenceContent(Composite composite) {
		return fConfigurationBlock.createContents(composite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
	 */
	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return fConfigurationBlock.hasProjectSpecificOptions(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPreferencePageID()
	 */
	@Override
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPropertyPageID()
	 */
	@Override
	protected String getPropertyPageID() {
		return PROP_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * enableProjectSpecificSettings(boolean)
	 */
	@Override
	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
		}
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		super.performDefaults();
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performDefaults();
		}
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performApply()
	 */
	@Override
	public void performApply() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performApply();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	@Override
	public void dispose() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.dispose();
		}
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * setElement (org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}
}
