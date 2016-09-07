/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.project;

import java.util.Observable;

import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.php.composer.ui.editor.composer.LicenseContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.php.composer.api.ComposerConstants;

@SuppressWarnings("restriction")
public class BasicSettingsGroup extends Observable implements IDialogFieldListener {

	protected StringDialogField vendorField;
	protected StringDialogField typeField;
	protected StringDialogField descriptionField;
	protected StringDialogField keywordField;
	protected StringDialogField licenseField;

	protected Shell shell;
	protected Composite nameComposite;

	public BasicSettingsGroup(Composite composite, Shell shell) {
		createControl(composite, shell);
	}

	public void createControl(Composite composite, Shell shell) {
		this.shell = shell;

		nameComposite = new Composite(composite, SWT.NONE);
		nameComposite.setFont(composite.getFont());
		nameComposite.setLayout(new GridLayout(2, false));
		nameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// text field for project vendor name
		vendorField = new StringDialogField();
		vendorField.setLabelText("Vendor name");
		vendorField.setDialogFieldListener(this);
		vendorField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(vendorField.getTextControl(null));

		// text field for project type
		typeField = new StringDialogField();
		typeField.setLabelText("Type");
		typeField.setDialogFieldListener(this);
		typeField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(typeField.getTextControl(null));

		ControlDecoration decoration = new ControlDecoration(typeField.getTextControl(), SWT.TOP | SWT.LEFT);

		FieldDecoration indicator = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);

		decoration.setImage(indicator.getImage());
		decoration.setDescriptionText(indicator.getDescription() + "(Ctrl+Space)");
		decoration.setShowOnlyOnFocus(true);

		new AutoCompleteField(typeField.getTextControl(), new TextContentAdapter(), ComposerConstants.TYPES);

		// text field for project description
		descriptionField = new StringDialogField();
		descriptionField.setLabelText("Description");
		descriptionField.setDialogFieldListener(this);
		descriptionField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(descriptionField.getTextControl(null));

		// text field for project description
		keywordField = new StringDialogField();
		keywordField.setLabelText("Keywords");
		keywordField.setDialogFieldListener(this);
		keywordField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(keywordField.getTextControl(null));

		// text field for project description
		licenseField = new StringDialogField();
		licenseField.setLabelText("License");
		licenseField.setDialogFieldListener(this);
		licenseField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(licenseField.getTextControl(null));

		ControlDecoration licenseDecoration = new ControlDecoration(licenseField.getTextControl(), SWT.TOP | SWT.LEFT);

		licenseDecoration.setImage(indicator.getImage());
		licenseDecoration.setDescriptionText(indicator.getDescription() + "(Ctrl+Space)");
		licenseDecoration.setShowOnlyOnFocus(true);

		new AutoCompleteField(licenseField.getTextControl(), new LicenseContentAdapter(), ComposerConstants.LICENSES);
	}

	protected void fireEvent() {
		setChanged();
		notifyObservers();
	}

	public String getVendor() {
		return vendorField.getText().trim();
	}

	public String getDescription() {
		return descriptionField.getText().trim();
	}

	public String getLicense() {
		return licenseField.getText().trim();
	}

	public String getType() {
		return typeField.getText().trim();
	}

	public String getKeywords() {
		return keywordField.getText().trim();
	}

	public void postSetFocus() {
		vendorField.postSetFocusOnDialogField(shell.getDisplay());
	}

	public void setVendor(String name) {
		vendorField.setText(name);
	}

	@Override
	public void dialogFieldChanged(DialogField field) {
		fireEvent();
	}

	public void hide() {
		nameComposite.setVisible(false);
	}

	public void show() {
		nameComposite.setVisible(true);
	}

	public void setEnabled(boolean enabled) {

		vendorField.setEnabled(enabled);
		nameComposite.setEnabled(enabled);
		typeField.setEnabled(enabled);
		descriptionField.setEnabled(enabled);
		keywordField.setEnabled(enabled);
		licenseField.setEnabled(enabled);
	}
}
