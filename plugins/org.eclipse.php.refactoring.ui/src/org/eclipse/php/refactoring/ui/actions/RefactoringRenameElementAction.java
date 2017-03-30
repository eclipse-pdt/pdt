/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import java.io.StringReader;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.ast.locator.Locator;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.actions.ActionUtils;
import org.eclipse.php.internal.ui.actions.RenamePHPElementAction;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveFilesHandler;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveFilesHandler.SaveFilesResult;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.php.refactoring.ui.rename.RefactoringExecutionStarter;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.RenameResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

/**
 * Action called when the user selects Refactoring->Rename the action triggers
 * the refactoring process.
 * 
 * @author Roy G., 2007
 * 
 */
public class RefactoringRenameElementAction extends RenamePHPElementAction {

	/**
	 * if the selection is should not be handled by
	 * <code>RefactoringRenameElementAction</code> we delegate it to the
	 * workbench action
	 * 
	 * @see selectionChanged
	 */
	private SelectionListenerAction action = null;

	public RefactoringRenameElementAction(IWorkbenchSite site) {
		super(site);
	}

	public RefactoringRenameElementAction(PHPStructuredEditor editor) {
		super(editor);
	}

	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(true);

		if (selection instanceof ITextSelection) {
			setEnabled(true);
			return;
		}
		if (selection == null || selection.size() != 1) {
			setEnabled(false);
			return;
		}
		if (!selection.isEmpty()) {
			if (ActionUtils.containsOnlyProjects(selection.toList())) {
				setEnabled(createWorkbenchAction(selection).isEnabled());
				return;
			}
			// List elements = selection.toList();
			// IResource[] resources =
			// ActionUtils.getPHPResources(elements.toArray());
			// Collection<CodeData> refactorablePHPElements = new LinkedList();
			// Object[] phpElements = ActionUtils.getPHPElements(elements);
			// for (Object phpElement : phpElements) {
			// if (!(phpElement instanceof CodeData)) // XXX this is unclear!
			// continue;
			// CodeData codeData = (CodeData) phpElement;
			// if (codeData.isUserCode()) {
			// IResource res = PHPModelUtil.getResource(codeData);
			// if (res != null && res.exists())
			// refactorablePHPElements.add(codeData);
			// }
			// }

			// if (elements.size() != resources.length +
			// refactorablePHPElements.size())
			// setEnabled(false);
			// else
			setEnabled(true);
		}
	}

	public void selectionChanged(ITextSelection selection) {
		setEnabled(true);
	}

	private SelectionListenerAction createWorkbenchAction(IStructuredSelection selection) {
		List<?> list = selection.toList();
		if (list.size() == 0 || list.get(0) instanceof IProject) {
			action = new RenameResourceAction(getSite());
			action.selectionChanged(selection);
		} else {
			action = new MoveResourceAction(getSite());
			action.selectionChanged(selection);

		}
		return action;
	}

	public void run(IStructuredSelection selection) {

		if (!isEnabled()) {
			return;
		}

		try {
			IResource resource;
			IFile file = null;
			ASTNode locateNode = null;
			IProject project;

			// we are not operating with an editor, the selection most likely
			// are from the explorer
			if (fEditor == null) {
				// rename resource (file, folder, project)
				if (selection.getFirstElement() instanceof IResource) {
					resource = (IResource) selection.getFirstElement();
					// if it's a file we can extract the document
					// Seva: XXX why won't we process projects with PHP?
					/*
					 * if (resource instanceof IProject && action != null) {
					 * resource.refreshLocal(IResource.DEPTH_INFINITE, null);
					 * action.run(); return; }
					 */
					if (resource instanceof IFile) {
						file = (IFile) resource;
					}

					project = resource.getProject();

					// rename php element from the project expolrer
				}
				// else if (selection.getFirstElement() instanceof CodeData) {
				// //extract the file, document and offset from the code data
				// CodeData data = (CodeData) selection.getFirstElement();
				// if (data.getUserData() == null)
				// return;
				// String fileName = data.getUserData().getFileName();
				// Path path = new Path(fileName);
				// resource =
				// ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				// file = (IFile) resource;
				// project = resource.getProject();
				//
				// final int offset = data.getUserData().getStopPosition();
				//
				// // locate the php element to refactor
				// InputStreamReader inputStreamReader = new
				// InputStreamReader(file.getContents(), file.getCharset());
				// final Program program = ASTParser.parse(inputStreamReader,
				// UseAspTagsHandler.useAspTagsAsPhp(project),
				// PhpVersionProjectPropertyHandler.getVersion(project));
				// locateNode = Locator.locateNode(program, offset);
				// inputStreamReader.close();
				//
				// // else not supported
				// }
				else {
					throw new UnsupportedOperationException(
							PHPRefactoringUIMessages.getString("RefactoringRenameElementAction.0") //$NON-NLS-1$
									+ selection.getFirstElement().getClass().getName());
				}

				// rename an element from the editor
			} else {

				resource = file = ((IFileEditorInput) fEditor.getEditorInput()).getFile();
				if (file == null) {
					throw new UnsupportedOperationException(
							PHPRefactoringUIMessages.getString("RefactoringRenameElementAction.0") //$NON-NLS-1$
									+ selection.getFirstElement().getClass().getName());
				}
				final IDocument doc = fEditor.getDocumentProvider().getDocument(fEditor.getEditorInput());
				final ITextSelection sel = (ITextSelection) fEditor.getSelectionProvider().getSelection();
				final int offset = sel.getOffset() + sel.getLength();

				project = resource.getProject();

				// locate the php element to refactor
				ASTParser parser = ASTParser.newParser(new StringReader(doc.get()),
						ProjectOptions.getPHPVersion(project), ProjectOptions.useShortTags(project));
				final Program program = parser.createAST(new NullProgressMonitor());
				locateNode = Locator.locateNode(program, offset);
			}

			// starts the rename refactoring operation
			if (checkProjectSaved(project)) {
				RefactoringExecutionStarter.startRenameRefactoring(resource, locateNode, getShell());
			}

		} catch (Exception e) {
			MessageDialog.openInformation(getShell(), PHPUIMessages.RenamePHPElementAction_name,
					PHPUIMessages.RenamePHPElementAction_not_available);
		}
	}

	private boolean checkProjectSaved(IProject project) {
		// save project files
		final SaveFilesResult result = SaveFilesHandler.handle(project, false, false, new NullProgressMonitor());
		if (!result.isAccepted()) {
			return false;
		}

		// check if all saved
		final List<IEditorPart> dirtyEditors = SaveFilesHandler.getDirtyEditors(project);
		return dirtyEditors.size() == 0;
	}

	public void run(ITextSelection selection) {
		// Do nothing super.run(selection)
	}
}
