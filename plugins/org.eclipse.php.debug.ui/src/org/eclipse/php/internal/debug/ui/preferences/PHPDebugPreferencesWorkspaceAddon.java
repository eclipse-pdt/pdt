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
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.preferences.AbstractPHPPreferencePageBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * A PHP debug preferences page Workspace UI addon.
 * This addon adds 3 check boxes for the debug perspective, debug info and debug views.
 * 
 * @author shalom
 */
public class PHPDebugPreferencesWorkspaceAddon extends AbstractPHPPreferencePageBlock {

	private Text fDebugTextBox;
	private Button fUsePHPDebugPerspective;
	private Button fRunWithDebugInfo;
	private Button fOpenInBrowser;
	private Button fOpenDebugViews;
	private Button fAutoSaveDirty;
	private PreferencePage propertyPage;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addWorkspacePreferenceSubsection(createSubsection(composite, PHPDebugUIMessages.PhpDebugPreferencePage_0));
	}

	public void initializeValues(PreferencePage propertyPage) {
		this.propertyPage = propertyPage;
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		fUsePHPDebugPerspective.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE));
		fOpenDebugViews.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));
		fRunWithDebugInfo.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO));
		fOpenInBrowser.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER));
		fAutoSaveDirty.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY));
		fDebugTextBox.setText(Integer.toString(prefs.getInt(PHPDebugCorePreferenceNames.DEBUG_PORT)));
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
		fOpenInBrowser.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER));
		fOpenDebugViews.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS));
		fAutoSaveDirty.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY));
		fDebugTextBox.setText(Integer.toString(prefs.getDefaultInt(PHPDebugCorePreferenceNames.DEBUG_PORT)));
	}

	private void addWorkspacePreferenceSubsection(Composite composite) {
		addLabelControl(composite, PHPDebugUIMessages.PhpDebugPreferencePage_3, PHPDebugCorePreferenceNames.DEBUG_PORT);
		fDebugTextBox = addDebugPortTextField(composite, PHPDebugCorePreferenceNames.DEBUG_PORT, 6, 2);
		fUsePHPDebugPerspective = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_2, PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE, 0);
		fRunWithDebugInfo = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_5, PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, 0);
		fOpenInBrowser = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_11, PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, 0);
		fOpenDebugViews = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_7, PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, 0);
		fAutoSaveDirty = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_10, PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY, 0);
	}

	private void savePreferences() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		prefs.setValue(PHPDebugCorePreferenceNames.USE_PHP_DEBUG_PERSPECTIVE, fUsePHPDebugPerspective.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.RUN_WITH_DEBUG_INFO, fRunWithDebugInfo.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_IN_BROWSER, fOpenInBrowser.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS, fOpenDebugViews.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.AUTO_SAVE_DIRTY, fAutoSaveDirty.getSelection());
		prefs.setValue(PHPDebugCorePreferenceNames.DEBUG_PORT, fDebugTextBox.getText());
		PHPDebugPlugin.getDefault().savePluginPreferences();
	}

	private Text addDebugPortTextField(Composite parent, String key, int textlimit, int horizontalIndent) {
		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);
		GridData data = new GridData();
		if (textlimit != 0) {
			textBox.setTextLimit(textlimit);
		}
		data.horizontalIndent = horizontalIndent;
		data.horizontalSpan = 2;
		textBox.setLayoutData(data);
		textBox.addModifyListener(new DebugPortValidateListener());
		return textBox;
	}

	class DebugPortValidateListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			String errorMessage = null;
			boolean valid = true;
			String value = ((Text) e.widget).getText();
			try {
				Integer iValue = new Integer(value);
				int i = iValue.intValue();
				if (i < 0 || i > 65535) {
					valid = false;
					errorMessage = PHPDebugUIMessages.PhpDebugPreferencePage_4;
				}
			} catch (NumberFormatException e1) {
				valid = false;
				errorMessage = PHPDebugUIMessages.PhpDebugPreferencePage_4;
			} catch (Exception e2) {
				valid = false;
				errorMessage = PHPDebugUIMessages.PhpDebugPreferencePage_4;
			}

			setErrorMessage(errorMessage);
			setValid(valid);
		}

		private void setValid(boolean valid) {
			if (propertyPage != null) {
				propertyPage.setValid(valid);
			}
		}

		private void setErrorMessage(String errorMessage) {
			if (propertyPage != null) {
				propertyPage.setErrorMessage(errorMessage);
			}
		}
	}
}
