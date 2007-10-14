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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/*
 * The page for setting general php plugin preferences.
 * See PreferenceConstants to access or change these values through public API.
 */
public class PHPBasePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String DOUBLE_CLICK = PreferenceConstants.DOUBLE_CLICK;
	private static final String DOUBLE_CLICK_GOES_INTO = PreferenceConstants.DOUBLE_CLICK_GOES_INTO;
	private static final String DOUBLE_CLICK_EXPANDS = PreferenceConstants.DOUBLE_CLICK_EXPANDS;

	private ArrayList fCheckBoxes;
	private ArrayList fRadioButtons;
	private ArrayList fTextControls;

	public PHPBasePreferencePage() {
		super();
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		setDescription(PHPUIMessages.PHPBasePreferencePage_description);

		fRadioButtons = new ArrayList();
		fCheckBoxes = new ArrayList();
		fTextControls = new ArrayList();
	}

	/*
	 * @see IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	private Button addRadioButton(Composite parent, String label, String key, String value) {
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		Button button = new Button(parent, SWT.RADIO);
		button.setText(label);
		button.setData(new String[] { key, value });
		button.setLayoutData(gd);

		button.setSelection(value.equals(getPreferenceStore().getString(key)));

		fRadioButtons.add(button);
		return button;
	}

	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		Composite result = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = 0;
		layout.verticalSpacing = convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		result.setLayout(layout);

		Group doubleClickGroup = new Group(result, SWT.NONE);
		doubleClickGroup.setLayout(new GridLayout());
		doubleClickGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		doubleClickGroup.setText(PHPUIMessages.PHPBasePreferencePage_doubleclick_action);
		addRadioButton(doubleClickGroup, PHPUIMessages.PHPBasePreferencePage_doubleclick_gointo, DOUBLE_CLICK, DOUBLE_CLICK_GOES_INTO);
		addRadioButton(doubleClickGroup, PHPUIMessages.PHPBasePreferencePage_doubleclick_expand, DOUBLE_CLICK, DOUBLE_CLICK_EXPANDS);
		Dialog.applyDialogFont(result);
		return result;
	}

	/*
	 * @see PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		IPreferenceStore store = getPreferenceStore();
		for (int i = 0; i < fCheckBoxes.size(); i++) {
			Button button = (Button) fCheckBoxes.get(i);
			String key = (String) button.getData();
			button.setSelection(store.getDefaultBoolean(key));
		}
		for (int i = 0; i < fRadioButtons.size(); i++) {
			Button button = (Button) fRadioButtons.get(i);
			String[] info = (String[]) button.getData();
			button.setSelection(info[1].equals(store.getDefaultString(info[0])));
		}
		for (int i = 0; i < fTextControls.size(); i++) {
			Text text = (Text) fTextControls.get(i);
			String key = (String) text.getData();
			text.setText(store.getDefaultString(key));
		}
		super.performDefaults();
	}

	/*
	 * @see IPreferencePage#performOk()
	 */
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		for (int i = 0; i < fCheckBoxes.size(); i++) {
			Button button = (Button) fCheckBoxes.get(i);
			String key = (String) button.getData();
			store.setValue(key, button.getSelection());
		}
		for (int i = 0; i < fRadioButtons.size(); i++) {
			Button button = (Button) fRadioButtons.get(i);
			if (button.getSelection()) {
				String[] info = (String[]) button.getData();
				store.setValue(info[0], info[1]);
			}
		}
		for (int i = 0; i < fTextControls.size(); i++) {
			Text text = (Text) fTextControls.get(i);
			String key = (String) text.getData();
			store.setValue(key, text.getText());
		}

		PHPUiPlugin.getDefault().savePluginPreferences();
		return super.performOk();
	}

}
