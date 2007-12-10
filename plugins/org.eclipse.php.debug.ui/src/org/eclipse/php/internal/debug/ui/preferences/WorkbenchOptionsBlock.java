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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A PHP debug preferences page Workspace UI addon.
 * This addon adds 3 check boxes for the debug perspective, debug info and debug views.
 *
 * @author shalom
 */
public class WorkbenchOptionsBlock extends AbstractPHPPreferencePageBlock {

	private Button fOpenInBrowser;
	private Button fOpenDebugViews;
	private PreferencePage propertyPage;
	private RadioGroupFieldEditor fSwitchPerspField;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addWorkspacePreferenceSubsection(composite);
	}

	public void initializeValues(PreferencePage propertyPage) {
		this.propertyPage = propertyPage;

		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fOpenDebugViews.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));
		fOpenInBrowser.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER));

		fSwitchPerspField.setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
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
		fOpenInBrowser.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER));
		fOpenDebugViews.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));

		fSwitchPerspField.setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		fSwitchPerspField.load();
	}

	private void addWorkspacePreferenceSubsection(Composite composite) {
		// Switch back to PHP prespective when the debug is terminated
		fSwitchPerspField = new RadioGroupFieldEditor(PreferenceConstants.SWITCH_BACK_TO_PHP_PERSPECTIVE, PHPDebugUIMessages.PHPLaunchingPreferencePage_switchToPHPMessage, 3, new String[][] { { PHPDebugUIMessages.PHPLaunchingPreferencePage_Always, MessageDialogWithToggle.ALWAYS }, { PHPDebugUIMessages.PHPLaunchingPreferencePage_Never, MessageDialogWithToggle.NEVER }, { PHPDebugUIMessages.PHPLaunchingPreferencePage_Prompt, MessageDialogWithToggle.PROMPT } }, composite, true);

		fOpenInBrowser = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_11, PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, 0);
		fOpenDebugViews = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_7, PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, 0);
	}

	private void savePreferences() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, fOpenInBrowser.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, fOpenDebugViews.getSelection());
		PHPDebugPlugin.getDefault().savePluginPreferences();

		fSwitchPerspField.store();
	}
}
