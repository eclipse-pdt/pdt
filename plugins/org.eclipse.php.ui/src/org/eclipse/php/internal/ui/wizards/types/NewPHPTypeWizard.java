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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathUtils;
import org.eclipse.php.internal.ui.util.CodeInjector;
import org.eclipse.php.internal.ui.wizards.PHPFileCreationWizard.FileCreator;
import org.eclipse.swt.SWT;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.undo.CreateFolderOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.model.ModelManagerImpl;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.text.XMLStructuredDocumentRegion;

/**
 * This is the base abstract class for new PHP types Wizards
 * 
 */
public abstract class NewPHPTypeWizard extends Wizard implements INewWizard {

	private IStructuredSelection fSelection;
	protected ArrayList<String> requiredNamesExcludeList = new ArrayList<>();
	protected ArrayList<String> requiredToAdd = new ArrayList<>();
	protected NewPHPTypePage page;
	protected ISourceModule existingPHPFile = null;
	protected String compilationResult;
	protected IScriptProject currentProject;

	public NewPHPTypeWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		fSelection = selection;
	}

	protected boolean createNewFile(final String containerName, final String fileName, final String contents) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (resource == null || !resource.exists() || !(resource instanceof IContainer)) {
			createNewFolder(new Path(containerName));
		}

		final IFile file = root.getFile(new Path(containerName).append(fileName));
		IRunnableWithProgress op = monitor -> {
			try {
				new FileCreator() {
					@Override
					protected void normalizeFile(IFile file) {
						super.normalizeFile(file);
						IContentFormatter formatter = PHPUiPlugin.getDefault().getActiveFormatter();
						try {
							IStructuredModel structuredModel = null;

							structuredModel = StructuredModelManager.getModelManager().getModelForEdit(file);
							if (structuredModel == null) {
								return;
							}
							try {
								// setup structuredModel
								// Note: We are getting model for edit. Will
								// save model if model
								// changed.
								IStructuredDocument structuredDocument = structuredModel.getStructuredDocument();

								IRegion region = new Region(0, structuredDocument.getLength());
								formatter.format(structuredDocument, region);
								structuredModel.save();
							} finally {
								// release from model manager
								if (structuredModel != null) {
									structuredModel.releaseFromEdit();
								}
							}
							currentProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
						} catch (IOException | CoreException e) {
							Logger.logException(e);
						}
					}
				}.createFile(NewPHPTypeWizard.this, file, monitor, contents);
			} catch (CoreException e) {
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}
		};
		try {
			getContainer().run(true, false, op);
			return true;
		} catch (InterruptedException e) {
			Logger.logException(e);
			return false;
		} catch (InvocationTargetException e) {
			Logger.logException(e);
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", //$NON-NLS-1$
					realException.getMessage());
			return false;
		}
	}

	/**
	 * Processes this wizard's data after 'Finish' is clicked. This validator
	 * retrieves required information for generating code and validates it.
	 * 
	 * @author yaronm
	 */
	class PostFinishValidator {
		private ArrayList<String> warnings = new ArrayList<String>();

		public void addWarning(String warning) {
			warnings.add(warning);
		}

		public boolean hasWarnings() {
			return warnings.size() > 0;
		}

		public String[] getWarnings() {
			String[] result = new String[warnings.size()];
			warnings.toArray(result);
			return result;
		}

		public void packAndValidate() {
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			currentProject = DLTKCore.create(workspaceRoot.getProject(getProjectName(page.getSourceText())));
		}
	}

	/**
	 * @return the project name the first section of the path
	 */
	protected String getProjectName(String sourcePath) {
		int idx;
		while ((idx = Math.max(sourcePath.lastIndexOf('\\'), sourcePath.lastIndexOf('/'))) > 1) {
			sourcePath = sourcePath.substring(0, idx);
		}

		return sourcePath;
	}

	public IStructuredSelection getSelection() {
		return fSelection;
	}

	public void showWarningsDialog(String[] warnings) {
		StringBuilder buffer = new StringBuilder();
		String elementType = ""; //$NON-NLS-1$
		switch (page.fTypeKind) {
		case NewPHPTypePage.CLASS_TYPE:
			elementType = Messages.NewPHPTypeWizard_class;
			break;
		case NewPHPTypePage.INTERFACE_TYPE:
			elementType = Messages.NewPHPTypeWizard_interface;
			break;
		case NewPHPTypePage.TRAIT_TYPE:
			elementType = Messages.NewPHPTypeWizard_trait;
			break;
		}
		buffer.append(elementType + Messages.NewPHPTypeWizard_creationWasSuccessful);
		for (String element : warnings) {
			buffer.append(element + "\n"); //$NON-NLS-1$
		}
		MessageDialog dialog = new MessageDialog(getShell(), "PHP Code Generator Warnings", null, buffer.toString(), //$NON-NLS-1$
				MessageDialog.INFORMATION, new String[] { "OK" }, 0); //$NON-NLS-1$
		dialog.open();
	}

	/**
	 * This function returns the start of first php block, if found, else -1
	 * returned
	 * 
	 * @return injectOffset
	 */
	protected int findPhpBlockOffset() {
		int injectOffset = -1;
		IPhpScriptRegion scriptRegion = null;
		ITextRegion[] subRegions = getStructuredDocumentsRegionis();
		for (ITextRegion currentRegion : subRegions) {
			if (currentRegion != null && currentRegion instanceof IPhpScriptRegion) {
				scriptRegion = (IPhpScriptRegion) currentRegion;
				if (scriptRegion.getType().equals(PHPRegionTypes.PHP_CONTENT)) {
					injectOffset = scriptRegion.getEnd();
					return injectOffset;
				}
			}
		}
		return injectOffset;
	}

	protected ITextRegion[] getStructuredDocumentsRegionis() {
		IResource resource = existingPHPFile.getResource();
		IStructuredModel existingModelForRead = null;
		try {
			IFile file = (IFile) resource;
			existingModelForRead = ModelManagerImpl.getInstance().getExistingModelForRead(file);
			if (existingModelForRead == null) {
				existingModelForRead = ModelManagerImpl.getInstance().createUnManagedStructuredModelFor(file);
			}

		} catch (IOException | CoreException e) {
			PHPUiPlugin.log(e);
			return new ITextRegion[0];
		}
		IStructuredDocument structuredDocument = existingModelForRead.getStructuredDocument();
		IStructuredDocumentRegion[] subRegions = structuredDocument.getStructuredDocumentRegions();

		for (IStructuredDocumentRegion currentRegion : subRegions) {
			if (currentRegion instanceof XMLStructuredDocumentRegion) {
				return currentRegion.getRegions().toArray();
			}
		}

		return new ITextRegion[0];
	}

	/**
	 * @param containerName
	 * @param fileName
	 * @param contents
	 */
	protected boolean createNewPhpFile(final String containerName, final String fileName, final String contents) {
		if (!createNewFile(containerName, fileName, contents)) {
			return false;
		}
		Path p = new Path(containerName + File.separatorChar + fileName);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(p);

		// open file in IDE
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IDE.openEditor(page, file, true);
		} catch (Exception e) {
			Logger.logException(e);
		}
		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager().getModelForEdit(file);
		} catch (Exception e) {
			Logger.logException(e);
			return false;
		}
		IStructuredDocument document = model.getStructuredDocument();
		model.releaseFromEdit();

		// TODO - Vadim fix the formatting
		// format the code
		CodeInjector injector = new CodeInjector();
		injector.formatDocument(document, 0, document.getLength());

		// save file
		try {
			model.save();
		} catch (Exception e) {
			Logger.logException(e);
		}
		return true;
	}

	/**
	 * @param templateEngine
	 */
	protected void injectCodeIntoExistingFile() {
		// locate location for injection of :
		// --------------------------------
		// 1. Require list only when not in new block
		if (page.isInFirstPHPBlock()) {

			int injectOffset = findPhpBlockOffset();

			int phpBlockEndOffset = -1;
			if (injectOffset == -1) {
				injectOffset = 0;

				// create injector at offset
				CodeInjector phpBlockInjector = new CodeInjector(existingPHPFile, injectOffset);
				phpBlockInjector.inject("<?php", false, true);//$NON-NLS-1$

				// find created php block end
				injectOffset = findPhpBlockOffset();
				if (injectOffset != -1) {
					phpBlockEndOffset = injectOffset;
				}
			} else {
				phpBlockEndOffset = injectOffset;
			}
			// if no PHP block and no include/require found, set offset at the
			// start of the file

			CodeInjector generatedCodeInjector = new CodeInjector(existingPHPFile, phpBlockEndOffset);
			generatedCodeInjector.setOffset(phpBlockEndOffset);
			generatedCodeInjector.inject("\n" + compilationResult, true, true); //$NON-NLS-1$

		} else {
			// isInFirstPHPBlock = false
			CodeInjector injector = new CodeInjector(existingPHPFile);
			injector.inject("\n" + compilationResult, true, true); //$NON-NLS-1$
		}
	}

	protected void extractReqruiresInclude(IType superClassData) {
		if (LanguageModelInitializer.isLanguageModelElement(superClassData)) {
			return;
		}
		if (page.isInExistingPHPFile() && existingPHPFile.equals(superClassData.getSourceModule())) {
			return;
		}
		// dependent source file in a current project
		IPath superClassRelativeFilePath = IncludePathUtils.getRelativeLocationFromIncludePath(currentProject,
				superClassData);
		if (!superClassRelativeFilePath.isEmpty()) {
			final String superclassFile = superClassRelativeFilePath.toString();
			if (!requiredNamesExcludeList.contains(superclassFile)) {
				requiredNamesExcludeList.add(superclassFile);
				requiredToAdd.add(superclassFile);
			}
		}
	}

	protected void addImport(List<String> imports, IType type, List<String> existingImports) {
		IModelElement parent = type.getParent();
		if (parent instanceof IType) {
			IType parentType = (IType) parent;
			try {
				if (PHPFlags.isNamespace(parentType.getFlags())) {
					if (parent.getElementName().equals(page.getRealNamespace())) {
						return;
					}
					String use = parent.getElementName() + '\\' + type.getElementName();
					if (!existingImports.contains(use)) {
						imports.add(use);
					}
				}
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
	}

	protected List<String> getExistingImports() {
		List<String> result = new ArrayList<>();
		if (existingPHPFile != null && existingPHPFile.exists()) {
			try {
				IModelElement[] rootElements = existingPHPFile.getChildren();
				for (IModelElement element : rootElements) {
					if (!addImport(result, element) && element instanceof IType) {
						IModelElement[] elementChildren = ((IType) element).getChildren();
						for (IModelElement child : elementChildren) {
							addImport(result, child);
						}
					}
				}
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
		return result;
	}

	protected String[] getRequires() {
		if (page.isCheckboxCreationChecked(NewPHPTypePage.REQUIRE_ONCE)) {
			String[] requires = new String[requiredToAdd.size()];
			requiredToAdd.toArray(requires);
			return requires;
		} else {
			return new String[0];
		}
	}

	private boolean addImport(List<String> result, IModelElement element) throws ModelException {
		if (element instanceof IImportContainer) {
			IImportContainer importContainer = (IImportContainer) element;
			IImportDeclaration[] imports = importContainer.getImports();
			for (IImportDeclaration iImportDeclaration : imports) {
				result.add(iImportDeclaration.getElementName());
			}
			return true;
		}
		return false;
	}

	public IFolder createNewFolder(IPath newFolderPath) {
		IFolder newFolder = null;
		final IFolder newFolderHandle = createFolderHandle(newFolderPath);

		final boolean createVirtualFolder = false;
		IRunnableWithProgress op = monitor -> {
			AbstractOperation op1;
			op1 = new CreateFolderOperation(newFolderHandle, null, createVirtualFolder, null,
					IDEWorkbenchMessages.WizardNewFolderCreationPage_title);
			try {
				// see bug
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
				// directly execute the operation so that the undo state is
				// not preserved. Making this undoable can result in
				// accidental
				// folder (and file) deletions.
				op1.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
			} catch (final ExecutionException e) {
				getContainer().getShell().getDisplay().syncExec(() -> {
					if (e.getCause() instanceof CoreException) {
						ErrorDialog.openError(getContainer().getShell(), // Was
																			// Utilities.getFocusShell()
								IDEWorkbenchMessages.WizardNewFolderCreationPage_errorTitle, null, // no
																									// special
																									// message
								((CoreException) e.getCause()).getStatus());
					} else {
						IDEWorkbenchPlugin.log(getClass(), "createNewFolder()", e.getCause()); //$NON-NLS-1$
						MessageDialog.openError(getContainer().getShell(),
								IDEWorkbenchMessages.WizardNewFolderCreationPage_internalErrorTitle, NLS.bind(
										IDEWorkbenchMessages.WizardNewFolder_internalError, e.getCause().getMessage()));
					}
				});
			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			// ExecutionExceptions are handled above, but unexpected runtime
			// exceptions and errors may still occur.
			IDEWorkbenchPlugin.log(getClass(), "createNewFolder()", e.getTargetException()); //$NON-NLS-1$
			MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
					IDEWorkbenchMessages.WizardNewFolderCreationPage_internalErrorTitle,
					NLS.bind(IDEWorkbenchMessages.WizardNewFolder_internalError, e.getTargetException().getMessage()),
					SWT.SHEET);
			return null;
		}

		return newFolder;
	}

	protected IFolder createFolderHandle(IPath folderPath) {
		return IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFolder(folderPath);
	}

}
