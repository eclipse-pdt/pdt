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
package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.AbstractPHPPreferencePageBlock;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * A PHP debug preferences page Workspace UI addon. This addon adds 3 check
 * boxes for the debug perspective, debug info and debug views.
 * 
 * @author shalom
 */
public class WorkbenchOptionsBlock extends AbstractPHPPreferencePageBlock {

	private Button fOpenInBrowser;
	private Button fOpenDebugViews;
	private PreferencePage propertyPage;
	private RadioGroupFieldEditor fSwitchPerspField;
	private RadioGroupFieldEditor fAllowMultipleLnchField;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addWorkspacePreferenceSubsection(composite);
	}

	public void initializeValues(PreferencePage propertyPage) {
		this.propertyPage = propertyPage;

		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fOpenDebugViews.setSelection(prefs
				.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));
		fOpenInBrowser.setSelection(prefs
				.getBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER));

		fAllowMultipleLnchField.setPreferenceStore(PHPUiPlugin.getDefault()
				.getPreferenceStore());
		fAllowMultipleLnchField.load();

		fSwitchPerspField.setPreferenceStore(PHPUiPlugin.getDefault()
				.getPreferenceStore());
		fSwitchPerspField.load();
	}

	public boolean performOK(boolean isProjectSpecific) {
		savePreferences();
		return true;
	}

	public void performApply(boolean isProjectSpecific) {
		performOK(isProjectSpecific);
	}

	public boolean performCancel() {
		return true;
	}

	public void performDefaults() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fOpenInBrowser
				.setSelection(prefs
						.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER));
		fOpenDebugViews
				.setSelection(prefs
						.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));

		fAllowMultipleLnchField.setPreferenceStore(PHPUiPlugin.getDefault()
				.getPreferenceStore());
		fAllowMultipleLnchField.loadDefault();

		fSwitchPerspField.setPreferenceStore(PHPUiPlugin.getDefault()
				.getPreferenceStore());
		fSwitchPerspField.loadDefault();
	}

	private void addWorkspacePreferenceSubsection(Composite composite) {
		fAllowMultipleLnchField = new RadioGroupFieldEditor(
				PreferenceConstants.ALLOW_MULTIPLE_LAUNCHES,
				PHPDebugUIMessages.PHPLaunchingPreferencePage_multipleMessage,
				3, new String[][] {
						{ PHPDebugUIMessages.PHPLaunchingPreferencePage_Always,
								MessageDialogWithToggle.ALWAYS },
						{ PHPDebugUIMessages.PHPLaunchingPreferencePage_Never,
								MessageDialogWithToggle.NEVER },
						{ PHPDebugUIMessages.PHPLaunchingPreferencePage_Prompt,
								MessageDialogWithToggle.PROMPT } }, composite,
				true);

		fSwitchPerspField = new RadioGroupFieldEditor(
				PreferenceConstants.SWITCH_BACK_TO_PHP_PERSPECTIVE,
				PHPDebugUIMessages.PHPLaunchingPreferencePage_switchToPHPMessage,
				3, new String[][] {
						{ PHPDebugUIMessages.PHPLaunchingPreferencePage_Always,
								MessageDialogWithToggle.ALWAYS },
						{ PHPDebugUIMessages.PHPLaunchingPreferencePage_Never,
								MessageDialogWithToggle.NEVER },
						{ PHPDebugUIMessages.PHPLaunchingPreferencePage_Prompt,
								MessageDialogWithToggle.PROMPT } }, composite,
				true);

		Group group = new Group(composite, SWT.NONE);
		group
				.setText(PHPDebugUIMessages.WorkbenchOptionsBlock_workbench_options);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		fOpenInBrowser = addCheckBox(group,
				PHPDebugUIMessages.PhpDebugPreferencePage_11,
				PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, 0);
		fOpenDebugViews = addCheckBox(group,
				PHPDebugUIMessages.PhpDebugPreferencePage_7,
				PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, 0);
	}

	private void savePreferences() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER,
				fOpenInBrowser.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS,
				fOpenDebugViews.getSelection());
		PHPDebugPlugin.getDefault().savePluginPreferences();

		fAllowMultipleLnchField.store();
		fSwitchPerspField.store();
	}
}
