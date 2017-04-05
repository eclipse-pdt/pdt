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
package org.eclipse.php.internal.ui.wizards.types;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.dltk.ui.dialogs.ITypeInfoFilterExtension;
import org.eclipse.dltk.ui.dialogs.TypeSelectionExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathUtils;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

public class NewPHPClassPage extends NewPHPTypePage {

	protected Text superClassName;
	private Label superPath;
	private IType superClassData;
	private Button browseSuperBtn;
	private static final String[] CLASS_CHECKBOXES_PHP5 = new String[] { REQUIRE_ONCE, CONSTRUCTOR, PHP_DOC_BLOCKS,
			null, DESTRUCTOR, TODOS, null, INHERITED_ABSTRACT_METHODS };
	public static final String[] CLASS_MODIFIERS = new String[] { "none", //$NON-NLS-1$
			"final", "abstract" }; //$NON-NLS-1$ //$NON-NLS-2$

	public static final int VALIDATE_SUPER_CLASS = 5;
	protected StatusInfo superClassStatus;

	public NewPHPClassPage() {
		super(Messages.NewPHPClassPage_3);
		fTypeKind = CLASS_TYPE;
		setMessage(Messages.NewPHPClassPage_4);
		setDescription(Messages.NewPHPClassPage_4);
		setTitle(Messages.NewPHPClassPage_6);
		superClassStatus = new StatusInfo();
		interfacesStatus = new StatusInfo();
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		super.createControl(parent);
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);

		// the location section will be generic all new elements types (classes,
		// interfaces etc.)
		// that's why it is in PHPTypePage
		createLocationSection(composite);

		// create this element's section
		createElementSection(composite);

		setControl(composite);
		initValues();
	}

	@Override
	protected void initValues() {
		super.initValues();
		if (superClassData != null) {
			superClassName.setText(superClassData.getElementName());
		}
	}

	@Override
	protected void validatePageValues(int validationCode) {
		super.validatePageValues(validationCode);
		IProject currentProject = getCurrentProject();
		if (currentProject != null) {
			IScriptProject model = DLTKCore.create(currentProject);

			if (superClassName != null && (superClassName.getText().length()) > 0 && (model != null)) {
				validateSuperClass(getSuperclassName());
			}
		}
	}

	private void createElementSection(final Composite composite) {
		// the element section is specific to an interface OR a class
		final Composite elementSection = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		elementSection.setLayout(layout);

		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		elementSection.setLayoutData(gd);

		addElementNameText(elementSection, Messages.NewPHPClassPage_7);
		if (phpVersion != PHPVersion.PHP5) {
			addNamespaceText(elementSection);
		}
		addElementModifiers(elementSection, CLASS_MODIFIERS);
		addElementSuperClass(elementSection);

		createSuperInterfacesControls(elementSection);
		if (phpVersion != null && phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			createTraitsControls(elementSection);
		}

		addCheckboxesCreation(elementSection, CLASS_CHECKBOXES_PHP5);
	}

	// add the UI section that handles the Superclass selection
	private void addElementSuperClass(Composite elementSection) {
		GridData gd = new GridData();
		Label superclassLabel = new Label(elementSection, SWT.NULL);
		superclassLabel.setText(Messages.NewPHPClassPage_8);
		superclassLabel.setLayoutData(gd);
		superClassName = new Text(elementSection, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		superClassName.setLayoutData(gd);
		superClassName.addModifyListener(e -> {
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			String projectName = getProjectName(getSourceText());
			if (projectName != null && projectName.length() > 0) {
				IProject currentProject = workspaceRoot.getProject(projectName);
				IScriptProject model = DLTKCore.create(currentProject);
				String superClassName = ((Text) e.getSource()).getText().trim();
				if (model != null) {
					validateSuperClass(superClassName);
				}
			}
		});

		browseSuperBtn = new Button(elementSection, SWT.PUSH);
		browseSuperBtn.setText(Messages.NewPHPClassPage_9);
		gd = new GridData();
		gd.widthHint = SWTUtil.getButtonWidthHint(browseSuperBtn);
		browseSuperBtn.setLayoutData(gd);
		browseSuperBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IType result = chooseSuperClass();
				superClassData = result;
				if (result != null) {
					superClassName.setText(result.getElementName());
				}
				changeButtonEnableStatus();
			}
		});

		// // use this dummy to take care of layout
		new Label(elementSection, SWT.NULL);

		superPath = new Label(elementSection, SWT.NULL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		superPath.setLayoutData(gd);
		new Label(elementSection, SWT.NULL);
	}

	/**
	 * @see NewPHPTypePage.java
	 */
	@Override
	protected void sourceFolderChanged() {
		super.sourceFolderChanged();
		if (browseSuperBtn == null) {
			return;
		}
		if (sourceFolderStatus.getCode() != IStatus.OK) {
			browseSuperBtn.setEnabled(false);
		} else {
			browseSuperBtn.setEnabled(true);
		}
		String sourcePath = getSourceText();
		Path projPath = new Path(sourcePath);
		if (projPath.segmentCount() != 1) {
			return;
		}
		String projectPath = projPath.segment(1);
		if (projectPath == null || projectPath.length() == 0) {
			return;
		}
		IScriptProject model = getProject();
		// check that superclass exists in model
		String superclassName = getSuperclassName();
		if (superclassName.length() > 0) {
			validateSuperClass(superclassName);
		}

		// check that interfaces exist in model
		if (getInterfaces().size() > 0) {
			validateInterfaces(model);
		}
		if (getTraits().size() > 0) {
			validateTraits(model);
		}
	}

	private void validateSuperClass(final String superclassName) {
		superPath.setText(""); //$NON-NLS-1$
		superClassStatus = new StatusInfo();
		if (superclassName.length() == 0) {
			superClassData = null;
			changeButtonEnableStatus();
			updateStatus(findMostSevereStatus());
			return;
		}

		superClassData = null;
		IDLTKSearchScope scope = SearchEngine.createSearchScope(getProject());
		IType[] types = PhpModelAccess.getDefault().findTypes(superclassName, MatchRule.EXACT, 0, 0, scope,
				new NullProgressMonitor());

		String error = null;
		for (IType type : types) {
			int modifiers = 0;
			try {
				modifiers = type.getFlags();
			} catch (ModelException e) {
			}
			if (PHPFlags.isInterface(modifiers)) {
				error = NLS.bind("Not a class: ''{0}''", superclassName); //$NON-NLS-1$
			} else if (PHPFlags.isFinal(modifiers)) {
				error = NLS.bind("Class ''{0}'' is final", superclassName); //$NON-NLS-1$
			} else {
				superClassData = type;
				IPath relativeLocation = IncludePathUtils.getRelativeLocationFromIncludePath(type.getScriptProject(),
						type);
				superPath.setText(relativeLocation.toString());
			}
		}
		if (superClassData == null) {
			superPath.setText(""); //$NON-NLS-1$
			if (error != null) {
				superClassStatus.setError(error);
			} else {
				superClassStatus.setError(NLS.bind("Cannot find: ''{0}''", //$NON-NLS-1$
						superclassName));
			}
		}
		updateStatus(findMostSevereStatus());
		changeButtonEnableStatus();
	}

	/**
	 * Returns the Name object of the superclass for the PHP element
	 * 
	 * @return
	 */
	public String getSuperclassName() {
		return superClassName.getText().trim();
	}

	/**
	 * Creates a UI component withing this Wizard page for selecting the
	 * Superclass
	 * 
	 * @return the selected super class
	 */
	protected IType chooseSuperClass() {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = workspaceRoot.getProject(getProjectName(sourceText.getText()));

		FilteredTypesSelectionDialog dialog = new FilteredTypesSelectionDialog(getShell(), false,
				PlatformUI.getWorkbench().getProgressService(),
				SearchEngine.createSearchScope(DLTKCore.create(project)), IDLTKSearchConstants.TYPE,
				new TypeSelectionExtension() {
					@Override
					public ITypeInfoFilterExtension getFilterExtension() {
						return typeInfoRequestor -> {
							int modifiers = typeInfoRequestor.getModifiers();
							if (!PHPFlags.isFinal(modifiers) && !PHPFlags.isInterface(modifiers)
									&& !PHPFlags.isNamespace(modifiers) && !PHPFlags.isTrait(modifiers)) {
								return true;
							}
							return false;
						};
					}
				}, PHPLanguageToolkit.getDefault());

		dialog.setListLabelProvider(new PHPFullPathLabelProvider());
		dialog.setListSelectionLabelDecorator(new PHPFullPathLabelProvider());
		dialog.setDetailsLabelProvider(new StatusLineLabelProvider());

		dialog.setTitle(Messages.NewPHPClassPage_0);
		dialog.setMessage(Messages.NewPHPClassPage_15);
		dialog.setInitialPattern("", //$NON-NLS-1$
				FilteredItemsSelectionDialog.FULL_SELECTION);

		if (dialog.open() == Window.OK) {
			Object[] resultArray = dialog.getResult();
			if ((resultArray != null) && (resultArray.length > 0)) {
				return (IType) resultArray[0];
			}
		}
		return null;
	}

	/**
	 * Returns true/false - whether the requested modifer's check box is
	 * selected
	 * 
	 * @param checkBoxIndex
	 *            - the index of the checkbox
	 * @return
	 */
	public boolean isCreateModifierChecked(int checkBoxIndex) {
		boolean result = false;
		if (modifiers != null) {
			Control[] btns = modifiers.getChildren();
			if (btns.length > checkBoxIndex) {
				Button btn = (Button) btns[checkBoxIndex];
				result = btn.getSelection();
			}
		}
		return result;
	}

	public IType getSuperClassData() {
		return superClassData;
	}

	/**
	 * Finds the most severe error (if there is one)
	 */
	@Override
	protected IStatus findMostSevereStatus() {
		return StatusUtil.getMostSevere(new IStatus[] { elementNameStatus, sourceFolderStatus, newFileStatus,
				existingFileStatus, superClassStatus, interfacesStatus, namespaceStatus });
	}

	@Override
	protected IModelElement getInitialPHPElement(IStructuredSelection selection) {
		IModelElement codeData = super.getInitialPHPElement(selection);

		// fixed bug 14462 - in case of class insert the class as super class
		if (codeData != null && PHPToolkitUtil.isPHPElement(codeData)) {
			int type = codeData.getElementType();
			if (type == IModelElement.TYPE && !PHPFlags.isInterface(type) && !PHPFlags.isNamespace(type)) {
				superClassData = (IType) codeData;
			}

		}
		return codeData;
	}

	@Override
	protected void changeButtonEnableStatus() {
		super.changeButtonEnableStatus();
		Button button = getButton(NewPHPClassPage.INHERITED_ABSTRACT_METHODS);
		if (button != null) {
			button.setEnabled(!fSuperInterfacesDialogField.getElements().isEmpty() || superClassData != null);
		}
	}

	@Override
	protected boolean requireOnceShouldEnabled() {
		return super.requireOnceShouldEnabled() || superClassData != null;
	}
}
