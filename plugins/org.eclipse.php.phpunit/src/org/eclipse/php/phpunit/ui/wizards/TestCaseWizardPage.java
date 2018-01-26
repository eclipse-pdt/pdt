/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.ui.dialogs.ITypeInfoFilterExtension;
import org.eclipse.dltk.ui.dialogs.ITypeInfoRequestor;
import org.eclipse.dltk.ui.dialogs.TypeSelectionExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.PHPUnitSearchEngine;
import org.eclipse.php.phpunit.model.PHPUnitValidator;
import org.eclipse.php.phpunit.ui.ElementSelectionDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

public class TestCaseWizardPage extends PHPUnitWizardPage {

	private boolean isClassOrMethodElementToTest = true;

	class ElementToTestFieldAdapter implements IStringButtonAdapter, IDialogFieldListener {

		@Override
		public void changeControlPressed(final DialogField field) {
			elementToTestButtonPressed(field);
		}

		@Override
		public void dialogFieldChanged(final DialogField field) {
			elementToTestFieldChanged(field);
		}
	}

	private final static String PAGE_NAME = "TestCaseWizardPage"; //$NON-NLS-1$

	private final static String TEST_SUFFIX = "Test"; //$NON-NLS-1$

	private IModelElement fElementToTest; // resolved model, can be null
	private StringButtonDialogField fElementToTestField; // control
	private Label fElementToTestFileNameLabel;
	private IType[] PHP_UNIT_CASE_BASE_CLASS_CACHE;

	public TestCaseWizardPage() {
		super(PAGE_NAME);
		setTitle(PHPUnitMessages.PHPUnitWizardPage_5);
		setDescription(PHPUnitMessages.PHPUnitWizardPage_6);

		fSuperClassDialogField.setText(PHPUnitSearchEngine.CLASS_CASE);
		final ElementToTestFieldAdapter elementAdapter = new ElementToTestFieldAdapter();
		fElementToTestField = new StringButtonDialogField(elementAdapter);
		fElementToTestField.setDialogFieldListener(elementAdapter);
		fElementToTestField.setLabelText(PHPUnitMessages.TestCaseWizardPage_0);
		fElementToTestField.setButtonLabel(PHPUnitMessages.TestCaseWizardPage_1);
		fElementToTestStatus = new StatusInfo();
	}

	private IModelElement chooseElementToTest() {
		final IContainer root = getTestContainer();

		if (root == null)
			return null;

		IProject project = getTestContainer().getProject();

		SelectionStatusDialog dialog = null;
		final IDLTKSearchScope searchScope = SearchEngine.createSearchScope(DLTKCore.create(project),
				IDLTKSearchScope.SOURCES);
		if (isClassOrMethodElementToTest) {
			dialog = new PHPUnitFilteredTypesSelectionDialog(getShell(), false,
					PlatformUI.getWorkbench().getProgressService(), searchScope, IDLTKSearchConstants.TYPE,
					new TypeSelectionExtension() {
						@Override
						public ITypeInfoFilterExtension getFilterExtension() {
							return new ITypeInfoFilterExtension() {
								@Override
								public boolean select(ITypeInfoRequestor typeInfoRequestor) {
									int modifiers = typeInfoRequestor.getModifiers();
									if (!PHPFlags.isInterface(modifiers) && !PHPFlags.isNamespace(modifiers)
											&& !PHPFlags.isAbstract(modifiers)) {
										return true;
									}
									return false;
								}
							};
						}
					}, PHPLanguageToolkit.getDefault());
			((PHPUnitFilteredTypesSelectionDialog) dialog).setInitialPattern("."); //$NON-NLS-1$
		} else {
			final List<IMethod> staticOrGlobalMethods = new LinkedList<>();

			IMethod[] globalFunctions = PHPModelAccess.getDefault().findMethods(null, MatchRule.PREFIX,
					Modifiers.AccGlobal, 0, searchScope, new NullProgressMonitor());
			if (globalFunctions != null) {
				staticOrGlobalMethods.addAll(Arrays.asList(globalFunctions));
			}
			IMethod[] staticMethods = PHPModelAccess.getDefault().findMethods(null, MatchRule.PREFIX,
					Modifiers.AccStatic, Modifiers.AccGlobal | Modifiers.AccPrivate, searchScope,
					new NullProgressMonitor());
			if (staticMethods != null) {
				staticOrGlobalMethods.addAll(Arrays.asList(staticMethods));
			}

			IMethod[] methodScope = staticOrGlobalMethods.toArray(new IMethod[staticOrGlobalMethods.size()]);

			dialog = new PHPUnitMethodSelectionDialog(getShell(), methodScope);
		}

		dialog.setTitle(PHPUnitMessages.TestCaseWizardPage_2);
		dialog.setMessage(PHPUnitMessages.TestCaseWizardPage_3);

		if (dialog.open() == Window.OK) {
			Object[] resultArray = dialog.getResult();
			if ((resultArray != null) && (resultArray.length > 0)) {
				return (IModelElement) resultArray[0];
			}
		}
		return null;
	}

	@Override
	protected IType chooseSuperClass() {
		final IContainer root = getTestContainer();

		if (root == null) {
			return null;
		}

		Shell shell = getShell();
		IProject project = root.getProject();
		final IScriptProject scriptProject = DLTKCore.create(project);
		findTestCases(scriptProject);

		if (PHP_UNIT_CASE_BASE_CLASS_CACHE != null && PHP_UNIT_CASE_BASE_CLASS_CACHE.length > 0) {
			SelectionDialog dialog = new ElementSelectionDialog(shell, PHP_UNIT_CASE_BASE_CLASS_CACHE);
			dialog.setTitle(PHPUnitMessages.PHPUnitWizardPage_10);
			dialog.setMessage(PHPUnitMessages.PHPUnitWizardPage_11);
			if (dialog.open() == Window.OK) {
				final Object[] resultArray = dialog.getResult();
				if (resultArray != null && resultArray.length > 0) {
					return (IType) resultArray[0];
				}
			}
		}
		return null;
	}

	/**
	 * @param scriptProject
	 */
	private void findTestCases(final IScriptProject scriptProject) {
		if (PHP_UNIT_CASE_BASE_CLASS_CACHE == null) {
			final PHPUnitSearchEngine searchEngine = new PHPUnitSearchEngine(scriptProject);

			final List<IType> elementsList = new ArrayList<>();
			try {
				IWizardContainer container = getContainer();
				if (container != null && getControl() != null) {
					getWizard().getContainer().run(true, true, pm -> {
						pm.beginTask(PHPUnitMessages.PHPUnitSearchEngine_Searching, IProgressMonitor.UNKNOWN);
						List<IType> elements = searchEngine.findPHPUnitClassesByTestCase(scriptProject, true, false,
								SubMonitor.convert(pm, IProgressMonitor.UNKNOWN));
						elementsList.addAll(elements);
						pm.done();
					});
					if (!elementsList.isEmpty()) {
						PHP_UNIT_CASE_BASE_CLASS_CACHE = elementsList.toArray(new IType[elementsList.size()]);
					}
				}
			} catch (InvocationTargetException | InterruptedException e) {
				PHPUnitPlugin.log(e);
			}
		}

		final List<IType> nonAbstractElementsList = new ArrayList<>();
		try {
			if (PHP_UNIT_CASE_BASE_CLASS_CACHE != null) {
				for (IType type : PHP_UNIT_CASE_BASE_CLASS_CACHE) {
					if (!PHPFlags.isAbstract(type.getFlags())) {
						nonAbstractElementsList.add(type);
					}
				}
			}
		} catch (ModelException e) {
			PHPUnitPlugin.log(e);
		}
	}

	@Override
	protected void superClassChanged() {
		super.superClassChanged();
		if (!fSuperClassStatus.isOK()) {
			return;
		}

		final StatusInfo status = new StatusInfo();
		String elementName = getSuperClassName();

		final IContainer root = getTestContainer();
		if (PHP_UNIT_CASE_BASE_CLASS_CACHE == null && root != null) {
			findTestCases(DLTKCore.create(root.getProject()));
		}
		boolean result = false;
		if (PHP_UNIT_CASE_BASE_CLASS_CACHE != null) {
			for (IType type : PHP_UNIT_CASE_BASE_CLASS_CACHE) {
				if (type.getElementName().equals(elementName)) {
					result = true;
				}
			}
			if (!result) {
				status.setError(PHPUnitMessages.PHPUnitWizardPage_21);
			}
		}
		fSuperClassStatus = status;
		updateStatus(getStatusList());

	}

	@Override
	protected void containerChanged() {
		super.containerChanged();
		if (getTestContainer() != null) {
			elementToTestChanged();
		}
	}

	@Override
	protected void createElementToTestControls(final Composite composite, final int nColumns) {
		createSeparator(composite, nColumns);

		new Label(composite, SWT.NONE);

		Composite selectionContainer = new Composite(composite, SWT.NULL);
		RowLayout rowLayout = new RowLayout();
		selectionContainer.setLayout(rowLayout);
		rowLayout.spacing = 10;
		Button clasOrFunctionElementToTestButton = new Button(selectionContainer, SWT.RADIO);
		clasOrFunctionElementToTestButton.setText(PHPUnitMessages.TestCaseWizardPage_5);
		clasOrFunctionElementToTestButton.setSelection(isClassOrMethodElementToTest);
		clasOrFunctionElementToTestButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				isClassOrMethodElementToTest = true;
				fElementToTest = null;
				elementToTestChanged();
			}

		});
		Button newPHPBlockBtn = new Button(selectionContainer, SWT.RADIO);
		newPHPBlockBtn.setText(PHPUnitMessages.TestCaseWizardPage_4);
		newPHPBlockBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				isClassOrMethodElementToTest = false;
				fElementToTest = null;
				elementToTestChanged();
			}

		});
		new Label(composite, SWT.NONE);

		fElementToTestField.doFillIntoGrid(composite, nColumns);
		final Text text = fElementToTestField.getTextControl(null);
		LayoutUtil.setWidthHint(text, getMaxFieldWidth());

		new Label(composite, SWT.NONE);
		fElementToTestFileNameLabel = new Label(composite, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		fElementToTestFileNameLabel.setLayoutData(gridData);
		LayoutUtil.setWidthHint(fElementToTestFileNameLabel, getMaxFieldWidth());

		// set default and focus
		final IModelElement elementToTest = getElementToTest();
		if (elementToTest != null)
			setClassName(elementToTest.getElementName() + testSuffix());
	}

	@Override
	protected void setFocus() {
		fElementToTestField.setFocus();
	}

	@Override
	protected String defaultSuperClass() {
		return PHPUnitSearchEngine.CLASS_CASE;
	}

	private void elementToTestButtonPressed(final DialogField field) {
		final IModelElement codeData = chooseElementToTest();
		if (codeData != null) {
			setElementToTest(codeData);
		}
	}

	protected void elementToTestChanged() {
		final StatusInfo status = new StatusInfo();
		Object element = fElementToTest;
		if (fElementToTest == null || !fElementToTest.getElementName().equals(fElementToTestField.getText())) {
			fElementToTest = null;
			element = fElementToTestField.getText();
		}

		final Object finalElementToTest = element;
		try {
			if (getContainer() != null && getContainer().getCurrentPage() != null
					&& getContainer().getCurrentPage().isPageComplete()) {
				getContainer().run(true, true, pm -> {
					pm.beginTask(PHPUnitMessages.PHPUnitSearchEngine_Searching, IProgressMonitor.UNKNOWN);
					int elementTypeToValidate;
					if (isClassOrMethodElementToTest) {
						elementTypeToValidate = IModelElement.TYPE;
					} else {
						elementTypeToValidate = IModelElement.METHOD;
					}
					fElementToTest = PHPUnitValidator.validateElement(finalElementToTest,
							getTestContainer() != null ? getTestContainer().getProject() : null, false,
							elementTypeToValidate, status);
					pm.done();
				});
			}
		} catch (InvocationTargetException | InterruptedException e) {
			PHPUnitPlugin.log(e);
		}

		final String elementToTestName = fElementToTest != null ? fElementToTest.getPath().lastSegment() : ""; //$NON-NLS-1$
		if (fElementToTestFileNameLabel != null && !fElementToTestFileNameLabel.isDisposed())
			fElementToTestFileNameLabel.setText(elementToTestName);
		fClassNameProposal = (fElementToTest != null ? fElementToTest.getElementName() : fElementToTestField.getText())
				+ testSuffix();
		if (!fClassNameManual && fClassNameDialogField != null) {
			fClassNameDialogField.setText(fClassNameProposal);
		}
		fElementToTestStatus = status;

		updateStatus(getStatusList());
	}

	private void elementToTestFieldChanged(final DialogField field) {
		elementToTestChanged();
	}

	public IModelElement getElementToTest() {
		return fElementToTest;
	}

	public String getElementToTestName() {
		return fElementToTestField.getText().trim();
	}

	@Override
	protected IStatus[] getStatusList() {
		return new IStatus[] { fContainerStatus, fClassNameStatus, fFileNameStatus, fElementToTestStatus,
				fSuperClassStatus };
	}

	@Override
	public Object init(final IStructuredSelection selection) {
		final Object result = super.init(selection);
		if (result instanceof IType && !(result instanceof ISourceModule)) {
			setElementToTest((IType) result);
		}
		return result;
	}

	public void setElementToTest(final IModelElement codeData) {
		fElementToTest = codeData;
		String elementName = ""; //$NON-NLS-1$
		if (codeData != null) {
			elementName = codeData.getElementName();
		}
		fElementToTestField.setText(elementName);
	}

	@Override
	protected String testSuffix() {
		return TEST_SUFFIX;
	}

	@Override
	protected void invalidatCachedElements() {
		PHP_UNIT_CASE_BASE_CLASS_CACHE = null;
	}
}