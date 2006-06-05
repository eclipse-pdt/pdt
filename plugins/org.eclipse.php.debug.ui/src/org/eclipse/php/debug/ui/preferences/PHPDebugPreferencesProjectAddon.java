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

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.wst.sse.ui.internal.Logger;
import org.osgi.service.prefs.BackingStoreException;

public class PHPDebugPreferencesProjectAddon extends AbstractDebugPreferencesPageAddon {

	private Button fStopAtFirstLine;
	private Text fDebugTextBox;
	private Text fDefaultURLTextBox;
	private PropertyPage propertyPage;

	public void setCompositeAddon(Composite parent) {
		Composite composite = addPageContents(parent);
		addProjectPreferenceSubsection(createSubsection(composite, PHPDebugUIMessages.PhpDebugPreferencePage_6));
	}

	public void initializeValues(PropertyPage propertyPage) {
		this.propertyPage = propertyPage;
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		IScopeContext[] preferenceScopes = createPreferenceScopes(propertyPage);
		fStopAtFirstLine.setSelection(prefs.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE));
		fDebugTextBox.setText(Integer.toString(prefs.getInt(PHPDebugCorePreferenceNames.DEBUG_PORT)));
		fDefaultURLTextBox.setText(prefs.getString(PHPDebugCorePreferenceNames.DEDAULT_URL));

		if (preferenceScopes[0] instanceof ProjectScope) {
			IEclipsePreferences node = preferenceScopes[0].getNode(getPreferenceNodeQualifier());
			if (node != null && getProject(propertyPage) != null) {
				Integer port = new Integer(node.getInt(PHPDebugCorePreferenceNames.DEBUG_PORT, 0));
				boolean stop = node.getBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, false);
				String defaultURL = node.get(PHPDebugCorePreferenceNames.DEDAULT_URL, "http://localhost");
				if (port.intValue() != 0) {
					fDebugTextBox.setText(port.toString());
					fStopAtFirstLine.setSelection(stop);
					fDefaultURLTextBox.setText(defaultURL);
				}
			}
		}
	}

	public boolean performOK(boolean isProjectSpecific) {
		savePreferences(isProjectSpecific);
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
		fStopAtFirstLine.setSelection(prefs.getDefaultBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE));
		fDebugTextBox.setText(Integer.toString(prefs.getDefaultInt(PHPDebugCorePreferenceNames.DEBUG_PORT)));
		fDefaultURLTextBox.setText(prefs.getDefaultString(PHPDebugCorePreferenceNames.DEDAULT_URL));
	}

	protected String getPreferenceNodeQualifier() {
		return PHPProjectPreferences.getPreferenceNodeQualifier();
	}

	private void addProjectPreferenceSubsection(Composite composite) {
		fStopAtFirstLine = addCheckBox(composite, PHPDebugUIMessages.PhpDebugPreferencePage_1, PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, 0);
		addLabelControl(composite, PHPDebugUIMessages.PhpDebugPreferencePage_3, PHPDebugCorePreferenceNames.DEBUG_PORT);
		fDebugTextBox = addDebugPortTextField(composite, PHPDebugCorePreferenceNames.DEBUG_PORT, 6, 2);
		addLabelControl(composite, PHPDebugUIMessages.PhpDebugPreferencePage_9, PHPDebugCorePreferenceNames.DEDAULT_URL);
		fDefaultURLTextBox = addDefaultURLTextField(composite, PHPDebugCorePreferenceNames.DEDAULT_URL, 0, 2);
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

	private Text addDefaultURLTextField(Composite parent, String key, int textlimit, int horizontalIndent) {
		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);
		GridData data = new GridData();
		if (textlimit != 0) {
			textBox.setTextLimit(textlimit);
		}
		data.horizontalIndent = horizontalIndent;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 2;
		data.minimumWidth = 300;
		textBox.setLayoutData(data);
		// TODO add validation
		// textBox.addModifyListener(new DebugPortValidateListener());
		return textBox;
	}

	private void savePreferences(boolean isProjectSpecific) {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		IScopeContext[] preferenceScopes = createPreferenceScopes(propertyPage);
		IEclipsePreferences node = preferenceScopes[0].getNode(getPreferenceNodeQualifier());
		if (isProjectSpecific && node != null && preferenceScopes[0] instanceof ProjectScope && getProject(propertyPage) != null) {
			node.putBoolean(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, fStopAtFirstLine.getSelection());
			node.putInt(PHPDebugCorePreferenceNames.DEBUG_PORT, Integer.parseInt(fDebugTextBox.getText()));
			node.put(PHPDebugCorePreferenceNames.DEDAULT_URL, fDefaultURLTextBox.getText());
		} else {
			if (getProject(propertyPage) == null) {
				prefs.setValue(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE, fStopAtFirstLine.getSelection());
				prefs.setValue(PHPDebugCorePreferenceNames.DEBUG_PORT, fDebugTextBox.getText());
				prefs.setValue(PHPDebugCorePreferenceNames.DEDAULT_URL, fDefaultURLTextBox.getText());
			} else {
				if (node != null) {
					node.remove(PHPDebugCorePreferenceNames.STOP_AT_FIRST_LINE);
					node.remove(PHPDebugCorePreferenceNames.DEBUG_PORT);
					node.remove(PHPDebugCorePreferenceNames.DEDAULT_URL);
				}
			}
		}
		try {
			node.flush();
			PHPDebugPlugin.getDefault().savePluginPreferences();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	class DebugPortValidateListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			String errorMessage = null;
			boolean valid = true;
			String value = ((Text) e.widget).getText();
			try {
				Integer iValue = new Integer(value);
				int i = iValue.intValue();
				if (i < 0) {
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
