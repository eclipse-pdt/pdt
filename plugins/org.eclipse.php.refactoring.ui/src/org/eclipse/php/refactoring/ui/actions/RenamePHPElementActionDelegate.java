/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ProjectFragment;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.actions.IPHPActionDelegator;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.php.refactoring.ui.prefereces.PreferenceConstants;
import org.eclipse.php.refactoring.ui.rename.RefactoringExecutionStarter;
import org.eclipse.php.refactoring.ui.rename.RenameLinkedMode;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.RenameResourceAction;

public class RenamePHPElementActionDelegate implements IPHPActionDelegator {
	private static final String DIALOG_TITLE = Messages.RenamePHPElementActionDelegate_0;
	private IWorkbenchWindow fWindow;
	private ISelection selection;

	public void run(IAction action) {
		// The action is run from a editor.
		if (selection instanceof ITextSelection) {
			execute((ITextSelection) selection);
		}
		// The action is run from a view.
		else if (selection instanceof IStructuredSelection) {
			execute(((IStructuredSelection) selection));
		}
	}

	private void execute(ITextSelection selection) {
		setWindowIfEmpty();
		IEditorPart fEditor = fWindow.getActivePage().getActiveEditor();

		if (fEditor != null && fEditor instanceof PHPStructuredEditor) {
			IModelElement source = ((PHPStructuredEditor) fEditor).getModelElement();

			if (!(source instanceof ISourceModule)) {
				MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE, Messages.RenamePHPElementActionDelegate_1);
				return;
			}
			Program program = null;

			try {
				program = ASTUtils.createProgramFromSource((ISourceModule) source);
			} catch (Exception e) {
				MessageDialog.openError(fWindow.getShell(), DIALOG_TITLE,
						"Unexpected error happenned:" + e.getMessage()); //$NON-NLS-1$
			}

			if (program == null) {
				MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE,
						Messages.RenamePHPElementActionDelegate_2);
				return;
			}

			IPreferenceStore store = RefactoringUIPlugin.getDefault().getPreferenceStore();
			boolean isInline = store.getBoolean(PreferenceConstants.REFACTOR_LIGHTWEIGHT);

			ISourceViewer viewer = ((PHPStructuredEditor) fEditor).getTextViewer();

			// this is work around for the case
			// that the offset from editor is not accurate
			// use the offset from source viewer instead.
			Point originalSelection = viewer.getSelectedRange();
			if (originalSelection != null) {
				run(source.getResource(), program, originalSelection.x, originalSelection.y, fEditor, isInline);
			} else {
				run(source.getResource(), program, selection.getOffset(), selection.getLength(), fEditor, isInline);
			}
		}
	}

	private void setWindowIfEmpty() {
		if (fWindow == null) {
			fWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}
	}

	private void run(IResource resource, Program program, int offset, int length, IEditorPart fEditor,
			boolean isInline) {
		ASTNode selectedNode = getSelectedNode(program, offset, length);

		Shell activeShell = null;
		if (fWindow != null) {
			activeShell = fWindow.getShell();
		} else {
			activeShell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		}
		if (selectedNode != null) {
			if (isInline) {
				new RenameLinkedMode(null, (PHPStructuredEditor) fEditor).start();
			} else
				try {
					RefactoringExecutionStarter.startRenameRefactoring(resource, selectedNode, activeShell);
				} catch (CoreException e) {
					MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE,
							Messages.RenamePHPElementActionDelegate_2);
				}
		} else {
			MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE, Messages.RenamePHPElementActionDelegate_2);
		}
	}

	protected ASTNode getSelectedNode(Program program, int offset, int length) {
		ASTNode selectedNode = NodeFinder.perform(program, offset, length);

		if (selectedNode != null && selectedNode.getType() == ASTNode.IN_LINE_HTML) {
			selectedNode = selectedNode.getProgramRoot();
		}
		return selectedNode;
	}

	private void execute(IStructuredSelection selection) {
		Object object = selection.getFirstElement();
		setWindowIfEmpty();

		if (object instanceof IResource) {
			object = DLTKCore.create((IResource) object);
		}

		if (isScriptContainer(object)) {
			IModelElement element = (IModelElement) object;
			try {
				RefactoringExecutionStarter.startRenameRefactoring(element.getResource(), null, fWindow.getShell());
			} catch (CoreException e) {
				MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE,
						Messages.RenamePHPElementActionDelegate_2);
			}
			return;
		} else if (isSourceReference(object) && isModelElement(object)
				&& PHPToolkitUtil.isFromPhpProject((IModelElement) object)) {
			IModelElement element = (IModelElement) object;

			IModelElement type = element.getPrimaryElement();
			ISourceModule source = RefactoringUtility.getSourceModule(type);

			if (source == null) {
				MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE,
						Messages.RenamePHPElementActionDelegate_2);
				return;
			}

			Program program = null;
			try {
				program = ASTUtils.createProgramFromSource(source);

			} catch (ModelException e) {
			} catch (IOException e) {
			} catch (Exception e) {
			}

			if (program == null) {
				MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE,
						Messages.RenamePHPElementActionDelegate_2);
				return;
			}

			try {
				int offset = getSourceOffset(element);
				run(element.getResource(), program, offset, 0, fWindow.getActivePage().getActiveEditor(), false);

			} catch (ModelException e) {
				MessageDialog.openInformation(fWindow.getShell(), DIALOG_TITLE,
						Messages.RenamePHPElementActionDelegate_2);
				return;
			}

		} else {
			RenameResourceAction renameAction = new RenameResourceAction(new IShellProvider() {
				public Shell getShell() {
					return fWindow.getShell();
				}
			});

			renameAction.selectionChanged(selection);
			renameAction.run();
		}

	}

	protected boolean isModelElement(Object object) {
		return object instanceof IModelElement;
	}

	protected int getSourceOffset(IModelElement element) throws ModelException {
		// This is the work around for the case of multiple
		// variable/constant declaration in one line.
		if (element instanceof SourceField) {
			return ((SourceField) element).getNameRange().getOffset();
		} else {
			return ((ISourceReference) element).getSourceRange().getOffset();
		}

	}

	protected boolean isSourceReference(Object object) {
		return object instanceof ISourceReference;
	}

	protected boolean isScriptContainer(Object object) {
		return object instanceof IScriptFolder || object instanceof IScriptProject || object instanceof ProjectFragment;
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.fWindow = window;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

}
