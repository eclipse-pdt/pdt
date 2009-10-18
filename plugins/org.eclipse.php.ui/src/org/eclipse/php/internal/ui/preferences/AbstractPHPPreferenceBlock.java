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

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.IStringValidator;
import org.eclipse.php.internal.ui.util.ValidationStatus;
import org.eclipse.php.internal.ui.util.ValuedCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;

/**
 * 
 * @author guy.g
 * 
 */
public abstract class AbstractPHPPreferenceBlock extends
		AbstractPHPPreferencePageBlock {

	protected PreferencePage preferencePage;

	protected ArrayList buttons = new ArrayList();
	protected ArrayList textBoxes = new ArrayList();
	protected ArrayList labels = new ArrayList();
	protected ArrayList combos = new ArrayList();

	/**
	 * Add this control to the list of controls
	 */
	protected void add(Button button) {
		buttons.add(button);
	}

	protected void add(Text text) {
		textBoxes.add(text);
	}

	protected void add(Label label) {
		labels.add(label);
	}

	protected void add(ValuedCombo valuedCombo) {
		combos.add(valuedCombo);
	}

	protected void restoreDefaultValues() {
		restoreDefaultButtonValues();
		restoreDefaultTextValues();
		restoreDefaultComboTextValues();
	}

	protected void restoreDefaultComboTextValues() {
		for (Iterator comboIterator = this.combos.iterator(); comboIterator
				.hasNext();) {
			ValuedCombo valuedCombo = (ValuedCombo) comboIterator.next();
			Object data = valuedCombo.getData();
			if (data != null) {
				valuedCombo.selectValue(getPreferenceStore().getDefaultString(
						(String) data));
			}
		}
	}

	protected void restoreDefaultTextValues() {
		Object[] controlsArray = textBoxes.toArray();
		for (int i = 0; i < controlsArray.length; i++) {
			Text text = (Text) controlsArray[i];
			Object data = text.getData();
			if (data != null) {
				text.setText(getPreferenceStore().getDefaultString(
						(String) data));
			}
		}

	}

	protected void restoreDefaultButtonValues() {
		Object[] controlsArray = buttons.toArray();
		for (int i = 0; i < controlsArray.length; i++) {
			Button button = (Button) controlsArray[i];
			Object data = button.getData();
			if (data != null) {
				button.setSelection(getPreferenceStore().getDefaultBoolean(
						(String) data));
			}
		}
	}

	/**
	 * Initialize values with values from preference store
	 */
	protected void initializeValues() {
		initializeButtonsValues();
		initializeTextValues();
		initializeComboValues();
	}

	protected void initializeComboValues() {
		for (Iterator comboIterator = this.combos.iterator(); comboIterator
				.hasNext();) {
			ValuedCombo valuedCombo = (ValuedCombo) comboIterator.next();
			Object data = valuedCombo.getData();
			if (data != null) {
				valuedCombo.selectValue(getPreferenceStore().getString(
						(String) data));
			}
		}
	}

	protected void initializeTextValues() {
		Object[] controlsArray = textBoxes.toArray();
		for (int i = 0; i < controlsArray.length; i++) {
			Text text = (Text) controlsArray[i];
			Object data = text.getData();
			if (data != null) {
				text.setText(getPreferenceStore().getString((String) data));
			}
		}
	}

	protected void initializeButtonsValues() {
		Object[] controlsArray = buttons.toArray();
		for (int i = 0; i < controlsArray.length; i++) {
			Button button = (Button) controlsArray[i];
			Object data = button.getData();
			if (data != null) {
				button.setSelection(getPreferenceStore().getBoolean(
						(String) data));
			}
		}
	}

	/**
	 * Store field values back to the preference store
	 */
	protected void storeValues() {
		storeButtonsValues();
		storeTextValues();
		storeCombosValues();
	}

	protected void storeCombosValues() {
		for (Iterator comboIterator = this.combos.iterator(); comboIterator
				.hasNext();) {
			ValuedCombo valuedCombo = (ValuedCombo) comboIterator.next();
			Object data = valuedCombo.getData();
			if (data != null) {
				getPreferenceStore().setValue((String) data,
						valuedCombo.getSelectionValue());
			}
		}
	}

	protected void storeTextValues() {
		Object[] controlsArray = textBoxes.toArray();
		for (int i = 0; i < controlsArray.length; i++) {
			Text text = (Text) controlsArray[i];
			Object data = text.getData();
			if (data != null) {
				getPreferenceStore().setValue((String) data, text.getText());
			}
		}
	}

	protected void storeButtonsValues() {
		Object[] controlsArray = buttons.toArray();
		for (int i = 0; i < controlsArray.length; i++) {
			Button button = (Button) controlsArray[i];
			Object data = button.getData();
			if (data != null) {
				getPreferenceStore().setValue((String) data,
						button.getSelection());
			}
		}

	}

	/**
	 * Sets controls with specified key data to be active/not active
	 */
	protected void setControlsEnabled(String key, boolean enabled) {
		setControlsEnabled(buttons, key, enabled);
		setControlsEnabled(textBoxes, key, enabled);
		setControlsEnabled(labels, key, enabled);
		setControlsEnabled(combos, key, enabled);
	}

	private void setControlsEnabled(ArrayList controls, String key,
			boolean enabled) {
		for (Iterator controlIterator = controls.iterator(); controlIterator
				.hasNext();) {
			Control control = (Control) controlIterator.next();
			Object data = control.getData();
			if (data != null && ((String) data).equals(key)) {
				control.setEnabled(enabled);
			}
		}

	}

	/**
	 * Creates font metrics
	 */
	protected FontMetrics getFontMetrics(Control control) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
		return fontMetrics;
	}

	/**
	 * Creates sub-section group with title
	 */
	protected Composite createSubsection(Composite parent, String label) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		return group;
	}

	/**
	 * Create new checkbox and associate a preference key with it
	 */
	protected Button addCheckBox(Composite parent, String label,
			String prefKey, int horizontalIndent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(label);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalIndent = horizontalIndent;
		gd.horizontalSpan = 3;

		checkBox.setLayoutData(gd);
		checkBox.setData(prefKey);

		add(checkBox);
		return checkBox;
	}

	class TextFieldValidateListener implements ModifyListener {
		private IStringValidator stringValidator;

		public TextFieldValidateListener(IStringValidator stringValidator) {
			this.stringValidator = stringValidator;
		}

		public void modifyText(ModifyEvent e) {
			ValidationStatus status = stringValidator
					.validate(((Text) e.widget).getText());
			if (!status.isOK()) {
				getPreferencePage().setErrorMessage(status.getError());
				getPreferencePage().setValid(false);
			} else {
				getPreferencePage().setErrorMessage(null);
				getPreferencePage().setValid(true);
			}
		}
	}

	/**
	 * Add text box with label with input validator
	 */
	protected Text addLabelledTextField(Composite parent, String label,
			String key, int textlimit, int horizontalIndent,
			IStringValidator stringValidator) {
		Label labelControl = new Label(parent, SWT.WRAP);
		labelControl.setText(label);
		labelControl.setData(key);
		GridData data = new GridData();
		data.horizontalIndent = horizontalIndent;
		labelControl.setLayoutData(data);
		add(labelControl);

		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);
		data = new GridData();
		if (textlimit != 0) {
			textBox.setTextLimit(textlimit);
			data.widthHint = Dialog.convertWidthInCharsToPixels(
					getFontMetrics(parent), textlimit + 1);
		}
		data.horizontalSpan = 2;
		textBox.setLayoutData(data);

		if (stringValidator != null) {
			textBox.addModifyListener(new TextFieldValidateListener(
					stringValidator));
		}
		add(textBox);
		return textBox;
	}

	/**
	 * Add text box with label
	 */
	protected Text addLabelledTextField(Composite parent, String label,
			String key, int textlimit, int horizontalIndent) {
		return addLabelledTextField(parent, label, key, textlimit,
				horizontalIndent, null);
	}

	protected Control createContents(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}

	public void init(IWorkbench workbench) {
	}

	protected IPreferenceStore doGetPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	public void performDefaults() {
		restoreDefaultValues();
	}

	public boolean performOK(boolean isProjectSpecific) {
		storeValues();
		PHPUiPlugin.getDefault().savePluginPreferences();
		return true;
	}

	public void performApply(boolean isProjectSpecific) {
		storeValues();
		PHPUiPlugin.getDefault().savePluginPreferences();
	}

	protected abstract IPreferenceStore getPreferenceStore();

	protected abstract PreferencePage getPreferencePage();
}
