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
package org.eclipse.php.debug.ui.preferences;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * A PHP debug preferences page Workspace UI addon.
 * This addon adds 3 check boxes for the debug perspective, debug info and debug views.
 * 
 * @author shalom
 */
public class PHPDebugPreferencesWorkspaceAddon extends AbstractDebugPreferencesPageAddon {

	private Button fUsePHPDebugPerspective;
	private Button fRunWithDebugInfo;
	private Button fOpenDebugViews;
	private Button fAutoSaveDirty;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addWorkspacePreferenceSubsection(createSubsection(composite, PHPDebugUIMessages.PhpDebugPreferencePage_0));
	}

	public void initializeValues(PropertyPage propertyPages) {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fUsePHPDebugPerspective.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE));
		fOpenDebugViews.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));
		fRunWithDebugInfo.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		fAutoSaveDirty.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY));
	}

	public boolean performOK(boolean isProjectSpecific) {
		savePreferences();
		PHPDebugPlugin.getDefault().setLaunchPerspective();
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
		fUsePHPDebugPerspective.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE));
		fRunWithDebugInfo.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		fOpenDebugViews.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));
		fAutoSaveDirty.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY));
	}

	private void addWorkspacePreferenceSubsection(Composite composite) {
		fUsePHPDebugPerspective = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_2, PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE, 0);
		fRunWithDebugInfo = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_5, PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, 0);
		fOpenDebugViews = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_7, PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, 0);
		fAutoSaveDirty = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_10, PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, 0);
	}

	private void savePreferences() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE, fUsePHPDebugPerspective.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, fRunWithDebugInfo.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, fOpenDebugViews.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY, fAutoSaveDirty.getSelection());
		PHPDebugPlugin.getDefault().savePluginPreferences();
	}
}
