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

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.internal.core.phpModel.IPHPLanguageModel;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
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
import org.eclipse.ui.PlatformUI;


public class OpenPhpElementDialog extends Dialog {

	private PHPCodeData selectedElement = null;

	private BasicSelector basicSelector;

	private CodeData[] initialElements;

	private String initFilterText;

	private final PhpTypeFilter phpTypeFilter = new PhpTypeFilter();

	private String title;

	public OpenPhpElementDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	public OpenPhpElementDialog(Shell parentShell, String title) {
		this(parentShell);
		this.title = title;
	}

	@Override
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

	@Override
	protected Control createDialogArea(Composite parent) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.OPENING_PHP_ELEMENTS);
		Composite composite = (Composite) super.createDialogArea(parent);
		CompositeFactory phpTypeFilterCompositeFactory = new CompositeFactory() {

			public Composite createComposite(Composite parent) {
				return new PhpTypeFilterComposite(parent, phpTypeFilter, phpTypeFilter);
			}

		};
		basicSelector = new BasicSelector(composite, phpTypeFilterCompositeFactory);
		if (initFilterText != null)
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
					OpenPhpElementDialog.this.getButton(IDialogConstants.OK_ID).setEnabled(false);
					return;
				}
				OpenPhpElementDialog.this.getButton(IDialogConstants.OK_ID).setEnabled(true);
			}
		});
		return composite;
	}

	private Object[] getElements() {

		CodeData[] elements = initialElements;

		if (elements == null) {
			LinkedHashSet<CodeData> arrayList = new LinkedHashSet<CodeData>();
			// traverse over all the php projects and get the model for each
			// one.
			IProject[] projects = PHPUiPlugin.getWorkspace().getRoot().getProjects();
			for (IProject project : projects) {
				try {
					if (!project.exists() || !project.isOpen() || !project.hasNature(PHPNature.ID)) {
						continue;
					}
				} catch (CoreException ce) {
					ce.printStackTrace();
					continue;
				}
				PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
				if (projectModel == null) {
					continue;
				}
				IPhpModel[] models = projectModel.getModels();
				IPHPLanguageModel languageModel = projectModel.getPHPLanguageModel();
				for (int j = 0; j < models.length; ++j) {
					IPhpModel model = models[j];
					if (model != languageModel) {
						addData(model.getClasses(), arrayList);
						addData(model.getFunctions(), arrayList);
						addData(model.getConstants(), arrayList);
					}
				}
			}
			elements = arrayList.toArray(new CodeData[arrayList.size()]);
		}
		return sortAndremoveDuplicates(elements);
	}

	/**
	 * Sorts and removes duplicate elements
	 * @param elements
	 */
	private Object[] sortAndremoveDuplicates(CodeData[] elements) {
		// sort
		if (elements.length == 0) {
			return new Object[0];
		}
		Comparator<CodeData> comparator = new Comparator<CodeData>() {
			public int compare(CodeData arg0, CodeData arg1) {
				return arg0.toString().compareToIgnoreCase(arg1.toString());
			}
		};
		Arrays.sort(elements, comparator);

		// remove redundant elements
		CodeData last = elements[0];
		final List<Object> result = new ArrayList<Object>();
		result.add(last);
		for (CodeData codeData : elements) {
			if (!equals(last, codeData)) {
				result.add(codeData);
			}
			last = codeData;
		}
		return result.toArray();
	}

	private boolean equals(CodeData o1, CodeData o2) {

		if (o1.getClass() != o2.getClass()) {
			return false;
		}

		if (!o1.getName().equalsIgnoreCase(o1.getName())) {
			return false;
		}
		//since we know the input can be either class, function or constant - the casting here is safe.
		String fileName1 = PHPModelUtil.getPHPFileContainer((PHPCodeData) o1).getName();
		String fileName2 = PHPModelUtil.getPHPFileContainer((PHPCodeData) o2).getName();

		return fileName1.equals(fileName2);
	}

	private void addData(CodeData[] classes, Collection arrayList) {
		for (CodeData codeData : classes) {
			arrayList.add(codeData);
		}
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title != null ? title : PHPUIMessages.getString("OpenType_DialogTitle"));
	}

	public PHPCodeData getSelectedElement() {
		return selectedElement;
	}

	@Override
	protected void okPressed() {
		selectedElement = (PHPCodeData) basicSelector.getSelectedElement();
		super.okPressed();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	@Override
	public boolean close() {
		basicSelector.close();

		return super.close();
	}

}
