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
package org.eclipse.php.internal.ui.dialogs.openType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.internal.core.phpModel.IPHPLanguageModel;
import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.dialogs.openType.generic.BasicSelector;
import org.eclipse.php.internal.ui.dialogs.openType.generic.CompositeFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class OpenPhpTypeDialog extends Dialog {

	private PHPCodeData selectedElement = null;
	private BasicSelector basicSelector;
	private CodeData[] initialElements;
	private String initFilterText;
	private PhpTypeFilter phpTypeFilter = new PhpTypeFilter();

	public OpenPhpTypeDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	protected Point getInitialSize() {
		return getShell().computeSize(500, 400, true);
	}

	public void setInitialElements(CodeData[] initialElements) {
		this.initialElements = initialElements;
	}

	public void setInitFilterText(String initFilterText) {
		this.initFilterText = initFilterText;
	}
	
	public PhpTypeFilter getFilter() {
		return phpTypeFilter;
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		CompositeFactory phpTypeFilterCompositeFactory = new CompositeFactory() {

			public Composite createComposite(Composite parent) {
				return new PhpTypeFilterComposite(parent, phpTypeFilter, phpTypeFilter);
			}

		};
		basicSelector = new BasicSelector(composite, phpTypeFilterCompositeFactory);
		basicSelector.setInitFilterText(initFilterText);
		basicSelector.setLayoutData(new GridData(GridData.FILL_BOTH));
		basicSelector.addFilter(phpTypeFilter);
		basicSelector.setLabelProvider(new PhpTypeTableLabelProvider());
		basicSelector.setElements(getElements());
		basicSelector.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				okPressed();
			}
		});
		basicSelector.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					OpenPhpTypeDialog.this.getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				}
				OpenPhpTypeDialog.this.getButton(IDialogConstants.OK_ID).setEnabled(true);
			}
		});
		return composite;
	}

	private Object[] getElements() {

		Object[] elements = initialElements;

		if (elements == null) {
			ArrayList arrayList = new ArrayList();
			//traverse over all the php projects and get the model for each one.
			IProject[] projects = PHPUiPlugin.getWorkspace().getRoot().getProjects();
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				try {
					if (!project.exists() || !project.isOpen() || !project.hasNature(PHPNature.ID)) {
						continue;
					}
				} catch (CoreException ce) {
					ce.printStackTrace();
					continue;
				}
				PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
				IPhpModel[] models = projectModel.getModels();
				IPHPLanguageModel languageModel = projectModel.getPHPLanguageModel();
				for (int j = 0; j < models.length; ++j) {
					IPhpModel model = models[j];
					if (model != languageModel) {
						addClassesData(model.getClasses(), arrayList);
						addData(model.getFunctions(), arrayList);
						addData(model.getConstants(), arrayList);
					}
				}
			}
			elements = arrayList.toArray();
		}

		Arrays.sort(elements, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareToIgnoreCase(arg1.toString());
			}
		});

		return elements;
	}

	private void addClassesData(CodeData[] classes, ArrayList arrayList) {
		addData(classes, arrayList);
		for (int i = 0; i < classes.length; i++) {
			PHPClassData classData = (PHPClassData) classes[i];
			addData(classData.getConsts(), arrayList);
			addData(classData.getFunctions(), arrayList);
		}
	}

	private void addData(CodeData[] classes, ArrayList arrayList) {
		for (int i = 0; i < classes.length; i++) {
			CodeData codeData = classes[i];
			arrayList.add(codeData);
		}
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(PHPUIMessages.OpenType_DialogTitle);
	}

	public PHPCodeData getSelectedElement() {
		return selectedElement;
	}

	protected void okPressed() {
		selectedElement = (PHPCodeData) basicSelector.getSelectedElement();
		super.okPressed();
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	public boolean close() {
		basicSelector.close();

		return super.close();
	}

}
