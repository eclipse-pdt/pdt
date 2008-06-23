/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs.openType;

import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.dialogs.openType.filter.IPhpTypeFilterReadModel;
import org.eclipse.php.internal.ui.dialogs.openType.filter.IPhpTypeFilterWriteModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class PhpTypeFilterComposite extends Composite {

	private Button classesButton;
	private Button functionsButton;
	private Button constantsButton;
	private IPhpTypeFilterWriteModel phpTypeFilterWriteModel;
	private IPhpTypeFilterReadModel phpTypeFilterReadModel;

	private PhpTypeFilterComposite(final Composite parent, final int style, final IPhpTypeFilterWriteModel typeFilterWriteModel, final IPhpTypeFilterReadModel phpTypeFilterReadModel) {
		super(parent, style);
		setWriteModel(typeFilterWriteModel);
		setReadModel(phpTypeFilterReadModel);
		
		initialize();
		
//		classesButton.setSelection(model.g);
	}

	public PhpTypeFilterComposite(Composite parent, IPhpTypeFilterWriteModel phpTypeFilterWriteModel, final IPhpTypeFilterReadModel phpTypeFilterReadModel) {
		this(parent, SWT.NONE, phpTypeFilterWriteModel, phpTypeFilterReadModel);
		
	}

	private void initialize() {
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		this.setLayout(fillLayout);

		initControls();
		
		initActions();
	}

	private void initActions() {
		classesButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				phpTypeFilterWriteModel.setSelectClasses(classesButton.getSelection());
			}
		});

		functionsButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				phpTypeFilterWriteModel.setSelectFunctions(functionsButton.getSelection());
			}
		});

		constantsButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				phpTypeFilterWriteModel.setSelectConstants(constantsButton.getSelection());
			}
		});

	}

	private void initControls() {
		//		Label label = new Label(this, SWT.NONE);
		//		label.setText("Filter by type:");

		Group group = new Group(this, SWT.NONE);
		group.setText(PHPUIMessages.getString("OpenType_GroupFilterTitle"));
		//		Composite group = new Composite(this, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.marginBottom = 0;
		rowLayout.marginHeight = 3;
		rowLayout.marginLeft = 5;
		rowLayout.marginRight = 0;
		rowLayout.marginTop = 0;
		rowLayout.marginWidth = 0;
		group.setLayout(rowLayout);
		rowLayout.wrap = false;

		createClassCheckBox(group);
		createFunctionsCheckBox(group);
		createConstantsCheckBox(group);

	}

	private void createConstantsCheckBox(Composite composite) {
		composite = createbuttonComposite(composite);
		constantsButton = new Button(composite, SWT.CHECK);
		constantsButton.setText(PHPUIMessages.getString("OpenType_ConstantsFilterCheckboxName"));
		constantsButton.setSelection(this.phpTypeFilterReadModel.getSelectConstants());
	}

	private void createFunctionsCheckBox(Composite composite) {
		composite = createbuttonComposite(composite);
		functionsButton = new Button(composite, SWT.CHECK);
		functionsButton.setText(PHPUIMessages.getString("OpenType_FunctionsFilterCheckboxName"));
		functionsButton.setSelection(this.phpTypeFilterReadModel.getSelectFunctions());
	}

	private void createClassCheckBox(Composite composite) {
		composite = createbuttonComposite(composite);
		classesButton = new Button(composite, SWT.CHECK);
		classesButton.setText(PHPUIMessages.getString("OpenType_ClassesFilterCheckboxName"));
		classesButton.setSelection(this.phpTypeFilterReadModel.getSelectClasss());
	}

	private Composite createbuttonComposite(Composite composite) {
		composite = new Composite(composite, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		composite.setLayout(rowLayout);
		rowLayout.marginRight = 5;
		rowLayout.marginBottom = 0;
		rowLayout.marginHeight = 8;
		rowLayout.marginLeft = 5;
		rowLayout.marginTop = 0;
		rowLayout.marginWidth = 0;

		return composite;
	}
	
	public void setWriteModel(final IPhpTypeFilterWriteModel phpTypeFilterWriteModel) {
		//assert phpTypeFilterWriteModel != null;
		this.phpTypeFilterWriteModel = phpTypeFilterWriteModel;
	}

	private void setReadModel(final IPhpTypeFilterReadModel phpTypeFilterReadModel) {
		//assert phpTypeFilterReadModel != null;
		this.phpTypeFilterReadModel = phpTypeFilterReadModel;
	}
}
