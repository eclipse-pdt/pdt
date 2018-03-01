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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.search.DLTKSearchTypeNameMatch;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPElementImageDescriptor;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.IListAdapter;
import org.eclipse.php.internal.ui.wizards.fields.ListDialogField;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.PHPUnitSearchEngine;
import org.eclipse.php.phpunit.ui.ElementSelectionDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TestSuiteWizardPage extends PHPUnitWizardPage {

	protected static class PHPTypeListLabelProvider extends LabelProvider implements ILabelDecorator {
		private Image fClassImage;
		private Image fAbsatractClassImage;

		public PHPTypeListLabelProvider() {
			fClassImage = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
			fAbsatractClassImage = PHPUiPlugin.getImageDescriptorRegistry()
					.get(new PHPElementImageDescriptor(DLTKPluginImages.DESC_OBJS_CLASS,
							PHPElementImageDescriptor.ABSTRACT, PHPElementImageDescriptor.SMALL_SIZE));
		}

		@Override
		public String getText(Object element) {
			if (element != null) {
				String elementName = ""; //$NON-NLS-1$
				String fileName = ""; //$NON-NLS-1$
				if (element instanceof DLTKSearchTypeNameMatch) {
					DLTKSearchTypeNameMatch typeMatch = (DLTKSearchTypeNameMatch) element;
					IType type = typeMatch.getType();
					elementName = type.getElementName();
					fileName = type.getSourceModule().getElementName();
				} else if (element instanceof IType) {
					IType sourceElement = (IType) element;
					elementName = sourceElement.getElementName();
					fileName = sourceElement.getSourceModule().getElementName();
				}

				StringBuilder result = new StringBuilder(elementName);
				result.append(" - "); //$NON-NLS-1$
				result.append(fileName);
				return result.toString();
			}
			return ""; //$NON-NLS-1$
		}

		@Override
		public Image getImage(Object element) {
			Image result = null;
			if (element != null) {
				IType type = null;
				if (element instanceof DLTKSearchTypeNameMatch) {
					DLTKSearchTypeNameMatch typeMatch = (DLTKSearchTypeNameMatch) element;
					type = typeMatch.getType();
				}
				if (element instanceof IType) {
					type = ((IType) element);
				}
				if (type == null) {
					return null;
				}
				try {

					int flags = type.getFlags();
					if (PHPFlags.isClass(flags)) {
						if (PHPFlags.isAbstract(flags)) {
							result = fAbsatractClassImage;
						} else {
							result = fClassImage;
						}
					}
				} catch (ModelException e) {
					PHPUnitPlugin.log(e);
				}
			}
			return result;
		}

		@Override
		public Image decorateImage(Image image, Object element) {
			return image;
		}

		@Override
		public String decorateText(String text, Object element) {
			return text;
		}
	}

	private final static String PAGE_NAME = "TestCaseWizardPage"; //$NON-NLS-1$
	private final static String TEST_SUFFIX = "Suite"; //$NON-NLS-1$

	private ListDialogField<IType> fElementsToTestList;
	private Button addInterfacesBtn;
	private IType[] PHP_UNIT_SUITE_BASE_CLASS_CACHE;
	private IType[] PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE;

	public TestSuiteWizardPage() {
		super(PAGE_NAME);

		setTitle(PHPUnitMessages.TestSuiteWizardPage_5);
		setDescription(PHPUnitMessages.TestSuiteWizardPage_4);

		fSuperClassDialogField.setText(PHPUnitSearchEngine.CLASS_SUITE);
		superClassChanged();
	}

	public TestSuiteWizardPage(final String pageName) {
		super(pageName);
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

		findSuiteBaseClasses(scriptProject);

		if (PHP_UNIT_SUITE_BASE_CLASS_CACHE != null && PHP_UNIT_SUITE_BASE_CLASS_CACHE.length > 0) {
			ElementSelectionDialog dialog = new ElementSelectionDialog(shell, PHP_UNIT_SUITE_BASE_CLASS_CACHE);
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
	 * This method finds all types that extend basic PHPUnit suite class, including
	 * abstract classes.
	 * 
	 * @param scriptProject
	 */
	private void findSuiteBaseClasses(final IScriptProject scriptProject) {
		if (PHP_UNIT_SUITE_BASE_CLASS_CACHE != null) {
			return;
		}
		final PHPUnitSearchEngine searchEngine = new PHPUnitSearchEngine(scriptProject);
		final List<IType> elementsList = new ArrayList<>();
		try {
			IWizardContainer container = getContainer();
			if (getControl() != null) {
				container.run(true, true, pm -> {
					pm.beginTask(PHPUnitMessages.PHPUnitSearchEngine_Searching, IProgressMonitor.UNKNOWN);
					List<IType> elements = searchEngine.findPHPUnitClassesByTestSuite(scriptProject, true, false,
							SubMonitor.convert(pm, IProgressMonitor.UNKNOWN));
					if (pm.isCanceled()) {
						return;
					}
					elementsList.addAll(elements);
					pm.done();
				});
				if (!elementsList.isEmpty()) {
					PHP_UNIT_SUITE_BASE_CLASS_CACHE = elementsList.toArray(new IType[elementsList.size()]);
				}
			}
		} catch (InvocationTargetException | InterruptedException e) {
			PHPUnitPlugin.log(e);
		}
	}

	/**
	 * This method returns all non types that extend from PHPUnit test case or test
	 * suite, that may be used to build another test suite
	 * 
	 * @param scriptProject
	 */
	private void findAllNonAbstractSuitesAndCases(final IScriptProject scriptProject) {
		if (PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE != null) {
			return;
		}
		final PHPUnitSearchEngine searchEngine = new PHPUnitSearchEngine(scriptProject);
		final List<IType> elementsList = new ArrayList<>();
		try {
			if (getContainer() != null && getContainer().getCurrentPage() != null) {
				getContainer().run(true, true, pm -> {
					pm.beginTask(PHPUnitMessages.PHPUnitSearchEngine_Searching, IProgressMonitor.UNKNOWN);
					List<IType> elements = searchEngine.findAllTestCasesAndSuites(scriptProject, false,
							SubMonitor.convert(pm));
					if (pm.isCanceled()) {
						return;
					}
					elementsList.addAll(elements);
					pm.done();
				});
				if (!elementsList.isEmpty()) {
					PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE = elementsList
							.toArray(new IType[elementsList.size()]);
				}
			}
		} catch (InvocationTargetException | InterruptedException e) {
			PHPUnitPlugin.log(e);
		}
	}

	@Override
	protected void containerChanged() {
		super.containerChanged();
		if (getTestContainer() != null) {
			fClassNameProposal = getTestContainer() != null ? getTestContainer().getName() + testSuffix() : ""; //$NON-NLS-1$
			if (!fClassNameManual && fClassNameDialogField != null) {
				fClassNameDialogField.setText(fClassNameProposal);
			}
			classNameChanged();
			elementsToTestChanged();
		}
	}

	@Override
	protected void createContainerControls(final Composite parent, final int nColumns) {
		super.createContainerControls(parent, nColumns);

		// set default and focus
		final IContainer container = getTestContainer();
		if (container != null) {
			setClassName(container.getName() + testSuffix());
		}
	}

	@Override
	protected void createElementToTestControls(final Composite composite, final int columns) {
		String[] addButtons = new String[] { "Add", null, "Remove" }; //$NON-NLS-1$ //$NON-NLS-2$
		IListAdapter<IType> listAdapter = new IListAdapter<IType>() {
			@Override
			public void customButtonPressed(ListDialogField<IType> field, int index) {
			}

			@Override
			public void doubleClicked(ListDialogField<IType> field) {
			}

			@Override
			public void selectionChanged(ListDialogField<IType> field) {
			}
		};

		fElementsToTestList = new ListDialogField<IType>(listAdapter, addButtons, new PHPTypeListLabelProvider()) {
			// override these methods to validate interfaces
			@Override
			public void removeElement(IType element) throws IllegalArgumentException {
				super.removeElement(element);
				elementsToTestChanged();
			}

			@Override
			public void removeElements(List<IType> elements) {
				super.removeElements(elements);
				elementsToTestChanged();
			}

			@Override
			public void removeAllElements() {
				super.removeAllElements();
				elementsToTestChanged();
			}
		};
		fElementsToTestList.setLabelText(PHPUnitMessages.TestSuiteWizardPage_2);
		fElementsToTestList.setRemoveButtonIndex(2);
		fElementsToTestList.removeAllElements();

		final String INTERFACE = "interface"; //$NON-NLS-1$
		Control[] controls = fElementsToTestList.doFillIntoGrid(composite, 3);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		controls[1].setLayoutData(gd);
		final TableViewer tableViewer = fElementsToTestList.getTableViewer();
		tableViewer.setColumnProperties(new String[] { INTERFACE });
		gd = (GridData) fElementsToTestList.getListControl(null).getLayoutData();
		gd.grabExcessVerticalSpace = true;
		gd.widthHint = getMaxFieldWidth();
		addInterfacesBtn = (Button) fElementsToTestList.getButtonBox(composite).getChildren()[0];
		addInterfacesBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IType[] result = choosePHPUnitElementsToTest();
				if (result != null && result.length > 0) {
					fElementsToTestList.addElements(Arrays.asList(result));
					fElementsToTestList.refresh();
					elementsToTestChanged();
				}
			}
		});
	}

	// Interfaces Selection Control
	protected IType[] choosePHPUnitElementsToTest() {
		IProject project = getTestContainer().getProject();
		IScriptProject scriptProject = DLTKCore.create(project);

		findAllNonAbstractSuitesAndCases(scriptProject);

		if (PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE != null
				&& PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE.length > 0) {
			ElementSelectionDialog dialog = new ElementSelectionDialog(getShell(),
					PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE);
			dialog.setTitle(PHPUnitMessages.PHPUnitWizardPage_10);
			dialog.setMessage(PHPUnitMessages.PHPUnitWizardPage_11);
			dialog.setMultipleSelection(true);
			if (dialog.open() == Window.OK) {
				final Object[] resultArray = dialog.getResult();
				if (resultArray != null && resultArray.length > 0) {
					IType[] typeResult = new IType[resultArray.length];
					for (int i = 0; i < resultArray.length; i++) {
						IType type = (IType) resultArray[i];
						typeResult[i] = type;
					}
					return typeResult;
				}
			}
		}
		return null;
	}

	@Override
	protected String defaultSuperClass() {
		return PHPUnitSearchEngine.CLASS_SUITE;
	}

	protected void elementsToTestChanged() {
		final StatusInfo status = new StatusInfo();
		if (fElementsToTestList != null) {
			List<IType> addedElementsToTest = fElementsToTestList.getElements();
			final IType[] addedElementsToTestArray = addedElementsToTest.toArray(new IType[addedElementsToTest.size()]);

			if (PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE == null) {

				if (getTestContainer() != null && getContainer().getCurrentPage() != null) {
					IProject project = getTestContainer().getProject();
					if (project != null) {
						final IScriptProject scriptProject = DLTKCore.create(project);
						findAllNonAbstractSuitesAndCases(scriptProject);
					}
				}
			}

			if (PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE != null) {
				List<IType> allTests = Arrays.asList(PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE);
				for (IType test : addedElementsToTestArray) {
					if (!allTests.contains(test)) {
						status.setError(
								MessageFormat.format(PHPUnitMessages.PHPUnitValidator_Not_Test, test.getElementName()));
					}
				}
				fElementToTestStatus = status;
				updateStatus(getStatusList());
			}
		}
	}

	public IType[] getElementsToTest() {
		List<IType> elements = fElementsToTestList.getElements();
		return elements.toArray(new IType[elements.size()]);
	}

	@SuppressWarnings("null")
	@Override
	public Object init(final IStructuredSelection selection) {
		super.init(selection);
		IModelElement element;
		final IType superClass = null;
		final List<IContainer> containers = new ArrayList<>(1);
		IContainer container = null;
		Object next;
		for (final Iterator<?> i = selection.iterator(); i.hasNext();) {
			next = i.next();
			if ((container = getInitialContainer(new StructuredSelection(next))) != null) {
				containers.add(container);
			} else if (superClass == null && (element = getInitialPHPElement(new StructuredSelection(next))) != null) {
				final IResource res = element.getResource();
				if (res != null) {
					setContainer(res.getParent());
				}
			}
		}
		if (containers.size() > 1) {
			for (final Iterator<IContainer> i = containers.iterator(); i.hasNext();) {
				final IContainer iContainer = i.next();
				// if a project, just select it:
				if (iContainer.getType() == IResource.PROJECT) {
					container = iContainer;
					break;
				}
				// if a folder, first find the most rooted folder
				if (container == null
						|| iContainer.getFullPath().segmentCount() < container.getFullPath().segmentCount()) {
					container = iContainer;
				}
			}
			// now find the common root:
			for (final Iterator<IContainer> i = containers.iterator(); i.hasNext();) {
				// the most rooted folder has highest priority:
				IContainer iContainer = i.next();
				if (iContainer.getProject() != container.getProject()) {
					continue;
				}
				// get higher to the level of the most rooted folder:
				for (int j = 0; j < iContainer.getFullPath().segmentCount()
						- container.getFullPath().segmentCount(); ++j) {
					iContainer = iContainer.getParent();
				}
				// if still not equals - get 1 level higher:
				if (iContainer != container) {
					container = container.getParent();
				}
			}
			setContainer(container);
		} else if (!containers.isEmpty()) {
			setContainer(containers.get(0));
		}

		return null;
	}

	@Override
	protected String testSuffix() {
		return TEST_SUFFIX;
	}

	@Override
	protected void invalidatCachedElements() {
		PHP_UNIT_SUITE_BASE_CLASS_CACHE = null;
		PHP_UNIT_CASE_AND_SUITE_NON_ABSTRAXT_CLASS_CACHE = null;
	}
}