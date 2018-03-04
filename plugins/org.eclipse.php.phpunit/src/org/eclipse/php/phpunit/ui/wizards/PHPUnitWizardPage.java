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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.PHPUnitSearchEngine;
import org.eclipse.php.phpunit.model.PHPUnitValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;

public abstract class PHPUnitWizardPage extends WizardPage {

	class ClassNameFieldAdapter implements IDialogFieldListener {

		@Override
		public void dialogFieldChanged(final DialogField field) {
			classNameFieldChanged(field);
		}
	}

	class ContainerFieldAdapter implements IStringButtonAdapter, IDialogFieldListener {
		@Override
		public void changeControlPressed(final DialogField field) {
			containerChangeControlPressed(field);
		}

		@Override
		public void dialogFieldChanged(final DialogField field) {
			containerDialogFieldChanged(field);
		}
	}

	class FileNameFieldAdapter implements IDialogFieldListener {
		@Override
		public void dialogFieldChanged(final DialogField field) {
			fileNameFieldChanged(field);
		}
	}

	class SuperClassFieldAdapter implements IStringButtonAdapter, IDialogFieldListener {
		@Override
		public void changeControlPressed(final DialogField field) {
			superClassButtonPressed(field);
		}

		@Override
		public void dialogFieldChanged(final DialogField field) {
			superClassFieldChanged(field);
		}
	}

	private final static String DEFAULT_EXTENSION = ".php"; //$NON-NLS-1$

	protected boolean fClassNameManual;
	protected String fClassNameProposal;
	protected IStatus fElementToTestStatus; // status
	protected IStatus fClassNameStatus;
	protected IStatus fContainerStatus;
	protected IStatus fFileNameStatus;
	protected StringButtonDialogField fSuperClassDialogField;
	protected IStatus fSuperClassStatus;
	protected StringDialogField fClassNameDialogField;

	private IContainer fContainer;
	private IScriptProject fScriptProject;
	private PHPUnitSearchEngine fSearchEngine;
	private StringButtonDialogField fContainerDialogField;
	private StringDialogField fFileNameDialogField;
	private boolean fFileNameManual;
	private String fFileNameProposal;
	private boolean fPageVisible;
	private IType fSuperClass;
	private Label fSuperClassFileNameLabel;

	public PHPUnitWizardPage(final String pageName) {
		super(pageName);

		fPageVisible = false;

		final ContainerFieldAdapter containerAdapter = new ContainerFieldAdapter();

		fContainerDialogField = new StringButtonDialogField(containerAdapter);
		fContainerDialogField.setDialogFieldListener(containerAdapter);
		fContainerDialogField.setLabelText(getContainerLabel());
		fContainerDialogField.setButtonLabel(PHPUnitMessages.PHPUnitWizardPage_0);

		fContainerStatus = new StatusInfo();

		final SuperClassFieldAdapter superClassAdapter = new SuperClassFieldAdapter();

		fSuperClassDialogField = new StringButtonDialogField(superClassAdapter);
		fSuperClassDialogField.setDialogFieldListener(superClassAdapter);
		fSuperClassDialogField.setLabelText(PHPUnitMessages.PHPUnitWizardPage_1);
		fSuperClassDialogField.setButtonLabel(PHPUnitMessages.PHPUnitWizardPage_2);

		fSuperClassStatus = new StatusInfo();

		fClassNameDialogField = new StringDialogField();
		fClassNameDialogField.setDialogFieldListener(new ClassNameFieldAdapter());
		fClassNameStatus = new StatusInfo();
		fClassNameDialogField.setLabelText(PHPUnitMessages.PHPUnitWizardPage_3);

		fFileNameDialogField = new StringDialogField();
		fFileNameDialogField.setDialogFieldListener(new FileNameFieldAdapter());
		fFileNameStatus = new StatusInfo();
		fFileNameDialogField.setLabelText(PHPUnitMessages.PHPUnitWizardPage_4);

		fElementToTestStatus = new StatusInfo();
	}

	protected IContainer chooseContainer() {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), false, "Select New Source Folder"); //$NON-NLS-1$
		dialog.setHelpAvailable(false);
		dialog.setTitle(PHPUnitMessages.PHPUnitWizardPage_10);
		dialog.setMessage(PHPUnitMessages.PHPUnitWizardPage_11);

		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1) {
				IPath path = (IPath) result[0];
				IResource foundMemder = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
				if (foundMemder instanceof IContainer) {
					IContainer container = (IContainer) foundMemder;
					fContainerDialogField.setText(container.getFullPath().toOSString());

					if (container.getType() == IContainer.FOLDER) {
						return PHPUnitValidator.validateContainer(container);
					}
					if (container.getType() == IContainer.PROJECT) {
						return PHPUnitValidator.validateProject(container);
					}
				}
			}
		}
		return null;
	}

	abstract protected IType chooseSuperClass();

	protected void classNameChanged() {
		final StatusInfo status = new StatusInfo();
		final String updatedClassName = PHPUnitValidator.validateClassName(getClassName(),
				fContainer != null ? fContainer.getProject() : null, status);
		if ("".equals(updatedClassName) || updatedClassName.equalsIgnoreCase(fClassNameProposal)) { //$NON-NLS-1$
			fClassNameManual = false;
		} else {
			fClassNameManual = true;
		}
		fClassNameStatus = status;
		fFileNameProposal = getClassName() != null ? getClassName() + DEFAULT_EXTENSION : ""; //$NON-NLS-1$
		if (!fFileNameManual && fFileNameDialogField != null) {
			fFileNameDialogField.setText(fFileNameProposal);
		}
		updateStatus(getStatusList());

	}

	private void classNameFieldChanged(final DialogField field) {
		if (field == fClassNameDialogField) {
			classNameChanged();
		}
		updateStatus(getStatusList());
	}

	private void containerChangeControlPressed(final DialogField field) {
		// take the current jproject as init element of the dialog
		final IContainer root = chooseContainer();
		if (root != null) {
			setContainer(root);
		}
	}

	protected void containerChanged() {
		final StatusInfo status = new StatusInfo();
		IContainer newContainer = PHPUnitValidator.validateContainer(fContainerDialogField.getText(), true, status);

		// when we changed the project in the field, we invalidate all cached
		// elements.
		if (fContainer != newContainer) {
			invalidatCachedElements();
		}

		fContainer = newContainer;
		fContainerStatus = status;
		if (fContainer != null) {
			IProject project = fContainer.getProject();
			if (project != null) {
				IScriptProject scriptProject = DLTKCore.create(project);
				if (fScriptProject != scriptProject) {
					fScriptProject = scriptProject;
					fSearchEngine = new PHPUnitSearchEngine(fScriptProject);
				}
			}
		}
		updateStatus(getStatusList());
	}

	protected abstract void invalidatCachedElements();

	private void containerDialogFieldChanged(final DialogField field) {
		if (field == fContainerDialogField) {
			containerChanged();
		}
		updateStatus(getStatusList());
	}

	protected void createClassNameControls(final Composite composite, final int nColumns) {
		fClassNameDialogField.doFillIntoGrid(composite, nColumns);
		// DialogField.createEmptySpace(composite);

		final Text text = fClassNameDialogField.getTextControl(null);
		LayoutUtil.setWidthHint(text, getMaxFieldWidth());
	}

	protected void createContainerControls(final Composite parent, final int nColumns) {
		fContainerDialogField.doFillIntoGrid(parent, nColumns);
		LayoutUtil.setWidthHint(fContainerDialogField.getTextControl(null), getMaxFieldWidth());
	}

	@Override
	public void createControl(final Composite parent) {

		initializeDialogUnits(parent);

		final Composite composite = new Composite(parent, SWT.NONE);
		final int nColumns = 3;

		final GridLayout layout = new GridLayout(3, false);
		composite.setLayout(layout);

		createContainerControls(composite, nColumns);
		createSuperClassControls(composite, nColumns);
		createElementToTestControls(composite, nColumns);
		createSeparator(composite, nColumns);
		createClassNameControls(composite, nColumns);
		createFileNameControls(composite, nColumns);

		setControl(composite);

		Dialog.applyDialogFont(composite);

		setFocus();
		updateStatus(getStatusList());
	}

	abstract protected void createElementToTestControls(Composite composite, int columns);

	protected void createFileNameControls(final Composite composite, final int nColumns) {
		fFileNameDialogField.doFillIntoGrid(composite, nColumns);
	}

	protected void createSeparator(final Composite composite, final int nColumns) {
		new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, nColumns, 1));
	}

	protected void createSuperClassControls(final Composite composite, final int nColumns) {
		fSuperClassDialogField.doFillIntoGrid(composite, nColumns);
		final Text text = fSuperClassDialogField.getTextControl(null);
		// text.setEditable(false);
		LayoutUtil.setWidthHint(text, getMaxFieldWidth());

		new Label(composite, SWT.NONE);
		fSuperClassFileNameLabel = new Label(composite, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		fSuperClassFileNameLabel.setLayoutData(gridData);
		LayoutUtil.setWidthHint(fSuperClassFileNameLabel, getMaxFieldWidth());
		new Label(composite, SWT.NONE);

		superClassChanged();

	}

	abstract protected String defaultSuperClass();

	@Override
	public void dispose() {
		fContainerDialogField.setDialogFieldListener(null);
		fSuperClassDialogField.setDialogFieldListener(null);
		fClassNameDialogField.setDialogFieldListener(null);
		fFileNameDialogField.setDialogFieldListener(null);
		super.dispose();
	}

	protected void fileNameChanged() {
		final StatusInfo status = new StatusInfo();
		final String updatedFileName = PHPUnitValidator.validateFileName(getFileName(),
				fContainer != null ? fContainer : null, status);
		if (updatedFileName.equals("") || updatedFileName.equalsIgnoreCase(fFileNameProposal)) {//$NON-NLS-1$
			fFileNameManual = false;
		} else {
			fFileNameManual = true;
		}
		fFileNameStatus = status;
		updateStatus(getStatusList());
	}

	private void fileNameFieldChanged(final DialogField field) {
		if (field == fFileNameDialogField) {
			fileNameChanged();
		}
		updateStatus(getStatusList());
	}

	public String getClassName() {
		return fClassNameDialogField.getText().trim();
	}

	protected String getContainerLabel() {
		return PHPUnitMessages.PHPUnitWizardPage_15;
	}

	public String getFileName() {
		return fFileNameDialogField.getText().trim();
	}

	protected IContainer getInitialContainer(final IStructuredSelection selection) {

		if (selection != null && !selection.isEmpty()) {
			final Object obj = selection.getFirstElement();
			if (obj instanceof IModelElement) {
				IResource resource = ((IModelElement) obj).getResource();
				if (resource != null && !(resource instanceof IContainer)) {
					resource = resource.getParent();
				}
				return (IContainer) resource;
			} else if (obj instanceof IResource) {
				IResource resource = (IResource) obj;
				if (resource != null && !(resource instanceof IContainer)) {
					resource = resource.getParent();
				}
				return (IContainer) resource;
			}
		}
		return null;
	}

	protected IModelElement getInitialPHPElement(final IStructuredSelection selection) {
		IModelElement modelElement = null;
		int offset = -1;
		if (selection != null && !selection.isEmpty()) {

			Object obj = selection.getFirstElement();
			if (obj instanceof NodeImpl) {
				if (obj instanceof IAdaptable) {
					obj = ((IAdaptable) obj).getAdapter(IModelElement.class);
				}

			} else if (obj != null && obj instanceof ISourceModule) {

				// get current document offset
				if (selection instanceof ITextSelection) {
					ITextSelection textSelection = (ITextSelection) selection;
					offset = textSelection.getOffset();

				}

				// find the IType at current offset
				IType selectedType = null;
				ISourceModule sourceModule = (ISourceModule) obj;
				if (offset > -1) {
					selectedType = PHPModelUtils.getCurrentType(sourceModule, offset);
				} else {
					IType[] types = null;
					try {
						types = sourceModule.getTypes();
					} catch (ModelException e) {
						PHPUnitPlugin.log(e);
					}
					if (types != null && types.length >= 1) {
						selectedType = types[0];
					}
				}

				// found currently selected IType
				if (selectedType != null) {
					int flags;
					try {
						flags = selectedType.getFlags();
						if (!PHPFlags.isInterface(flags) && !PHPFlags.isAbstract(flags) && fSearchEngine != null
								&& !fSearchEngine.isCase(selectedType)) {
							modelElement = selectedType;
						}
					} catch (ModelException e) {
						PHPUnitPlugin.log(e);
					}
				}

				// no luck ti find currently selected IType
				if (modelElement == null) {
					modelElement = sourceModule;
				}
			} else if (obj instanceof IType) {
				modelElement = (IType) obj;
			} else if (obj instanceof IFile) {
				IFile file = (IFile) obj;
				boolean isPhpFile = PHPToolkitUtil.isPHPFile(file);
				if (isPhpFile) {
					modelElement = DLTKCore.create((IFile) obj);
				} else {
					// TODO message not php file
				}
			}
		}
		return modelElement;
	}

	/**
	 * Initialized the page with the current selection
	 * 
	 * @param selection
	 *            The selection
	 */
	public Object init(final IStructuredSelection selection) {
		final IModelElement element;
		final IContainer container;
		final Object result;
		if ((element = getInitialPHPElement(selection)) != null) {
			initContainerPage(element);
			if (element instanceof IType) {
				setClassName(((IType) element).getElementName());
			}
			result = element;
		} else if ((container = getInitialContainer(selection)) != null) {
			setContainer(container);
			setSuperClass(null);
			result = container;
		} else {
			result = null;
			// we set empty string just to invoke validation
			fContainerDialogField.setText(""); //$NON-NLS-1$
		}
		updateStatus(getStatusList());
		return result;
	}

	/**
	 * Initializes the source folder field with a valid root. The root is computed
	 * from the given PHPlement.
	 * 
	 * @param elem
	 *            the PHP Element used to compute the initial root used as the
	 *            source folder
	 */
	protected void initContainerPage(final IModelElement modelElement) {
		IModelElement tempElement = modelElement;
		if (modelElement != null) {
			while (!((tempElement instanceof IScriptFolder) || (tempElement instanceof IScriptProject))) {
				tempElement = tempElement.getParent();
			}
		}

		IContainer container = null;
		if (tempElement instanceof IScriptFolder) {
			IResource resource = tempElement.getResource();
			if (resource instanceof IFolder) {
				container = (IFolder) resource;
			}
			if (resource instanceof IProject) {
				container = (IProject) resource;
			}
		}

		if (tempElement instanceof IScriptProject) {
			container = ((IScriptProject) tempElement).getProject();
		}
		setContainer(container);
	}

	public void setClassName(final String name) {
		fClassNameDialogField.setText(name);
	}

	public void setContainer(final IContainer root) {
		fContainer = root;
		if (fContainer != null) {
			IProject project = fContainer.getProject();
			if (project != null) {
				IScriptProject scriptProject = DLTKCore.create(project);
				if (fScriptProject != scriptProject) {
					fScriptProject = scriptProject;
					fSearchEngine = new PHPUnitSearchEngine(fScriptProject);
				}
			}
		}
		final String str = root == null ? "" : root.getFullPath().toOSString(); //$NON-NLS-1$
		fContainerDialogField.setText(str);
	}

	protected void setFocus() {
		fClassNameDialogField.setFocus();
	}

	public void setSuperClass(final IType classData) {
		fSuperClassDialogField.setText(classData != null ? classData.getElementName() : defaultSuperClass());
	}

	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);
		fPageVisible = visible;
		updateStatus(getStatusList());
	}

	private void superClassButtonPressed(final DialogField field) {
		final IType classData = chooseSuperClass();
		if (classData != null) {
			setSuperClass(classData);
		}
	}

	protected void superClassChanged() {
		final StatusInfo status = new StatusInfo();
		Object element = fSuperClass;
		if (fSuperClass == null || !fSuperClass.getElementName().equals(getSuperClassName())) {
			fSuperClass = null;
			element = getSuperClassName();
		}
		fSuperClass = (IType) PHPUnitValidator.validateElement(element,
				fContainer != null ? fContainer.getProject() : null, false, IModelElement.TYPE, status);
		final String superClassName = fSuperClass != null ? fSuperClass.getElementName() : ""; //$NON-NLS-1$
		if (fSuperClassFileNameLabel != null && !fSuperClassFileNameLabel.isDisposed()) {
			fSuperClassFileNameLabel.setText(superClassName);
		}
		fSuperClassStatus = status;
		if ("".equals(getSuperClassName())) { //$NON-NLS-1$
			status.setError(PHPUnitMessages.PHPUnitWizardPage_20); // higher
		}
		// severity
		updateStatus(getStatusList());
	}

	private void superClassFieldChanged(final DialogField field) {
		superClassChanged();
	}

	protected abstract String testSuffix();

	protected void updateStatus(final IStatus status) {
		setPageComplete(!status.matches(IStatus.ERROR));
		if (fPageVisible) {
			StatusUtil.applyToStatusLine(this, status);
		}
	}

	protected void updateStatus(final IStatus[] statuses) {
		updateStatus(StatusUtil.getMostSevere(statuses));
	}

	protected int getMaxFieldWidth() {
		return convertWidthInCharsToPixels(40);
	}

	protected IStatus[] getStatusList() {
		return new IStatus[] { fContainerStatus, fClassNameStatus, fFileNameStatus, fElementToTestStatus,
				fSuperClassStatus };
	}

	public IType getSuperClass() {
		return fSuperClass;
	}

	public String getSuperClassName() {
		return fSuperClassDialogField.getText().trim();
	}

	public IContainer getTestContainer() {
		return fContainer;
	}

}
