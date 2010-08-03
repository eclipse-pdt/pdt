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
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class IncludePathVarsPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	public static final String ID = "org.eclipse.php.ui.preferences.IncludePathVariables"; //$NON-NLS-1$

	private VariableBlock fVariableBlock;
	private String fStoredSettings;

	/**
	 * Constructor for ClasspathVariablesPreferencePage
	 */
	public IncludePathVarsPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		fVariableBlock = new VariableBlock(true, null);
		fStoredSettings = null;

		// title only used when page is shown programatically
		setTitle(PHPUIMessages.IncludePathVariablesPreferencePage_title);
		setDescription(PHPUIMessages.IncludePathVariablesPreferencePage_description);
		noDefaultAndApplyButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.PATH_VARIABLES_PREFERENCES);
	}

	/*
	 * @see PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Control result = fVariableBlock.createContents(parent);
		Dialog.applyDialogFont(result);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.PATH_VARIABLES_PREFERENCES);
		return result;
	}

	/*
	 * @see IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * @see PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		fVariableBlock.performDefaults();
		super.performDefaults();
	}

	/*
	 * @see PreferencePage#performOk()
	 */
	public boolean performOk() {
		PHPUiPlugin.getDefault().savePluginPreferences();
		return fVariableBlock.performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		// check if the stored settings have changed
		if (visible) {
			if (fStoredSettings != null
					&& !fStoredSettings.equals(getCurrentSettings())) {
				fVariableBlock.refresh(null);
			}
		} else {
			if (fVariableBlock.hasChanges()) {
				String title = PHPUIMessages.IncludePathVariablesPreferencePage_savechanges_title;
				String message = PHPUIMessages.IncludePathVariablesPreferencePage_savechanges_message;
				if (MessageDialog.openQuestion(getShell(), title, message)) {
					performOk();
				}
				fVariableBlock.setChanges(false); // forget
			}
			fStoredSettings = getCurrentSettings();
		}
		super.setVisible(visible);
	}

	/**
	 * TODO should adapt this mechaism into DLTK
	 * 
	 * @return
	 */
	private String getCurrentSettings() {
		StringBuffer buf = new StringBuffer();
		String[] names = {};
		for (int i = 0; i < names.length; i++) {
			String curr = names[i];
			buf.append(curr).append('\0');
			IPath val = null;
			if (val != null) {
				buf.append(val.toString());
			}
			buf.append('\0');
		}
		return buf.toString();
	}

}
