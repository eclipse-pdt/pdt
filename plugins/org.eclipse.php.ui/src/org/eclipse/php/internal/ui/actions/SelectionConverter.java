/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class SelectionConverter {
	private static final IModelElement[] EMPTY_RESULT = new IModelElement[0];

	private SelectionConverter() {
		// no instance
	}

	/**
	 * Converts the selection provided by the given part into a structured
	 * selection. The following conversion rules are used:
	 * <ul>
	 * <li><code>part instanceof PHPStructuredEditor</code>: returns a
	 * structured selection using code resolve to convert the editor's text
	 * selection.</li>
	 * <li><code>part instanceof IWorkbenchPart</code>: returns the part's
	 * selection if it is a structured selection.</li>
	 * <li><code>default</code>: returns an empty structured selection.</li>
	 * </ul>
	 * 
	 * @param part
	 *            the part
	 * @return the selection
	 * @throws ModelException
	 */
	public static IStructuredSelection getStructuredSelection(
			IWorkbenchPart part) throws ModelException {
		if (part instanceof PHPStructuredEditor)
			return new StructuredSelection(
					codeResolve((PHPStructuredEditor) part));
		ISelectionProvider provider = part.getSite().getSelectionProvider();
		if (provider != null) {
			ISelection selection = provider.getSelection();
			if (selection instanceof IStructuredSelection)
				return (IStructuredSelection) selection;
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * Converts the given structured selection into an array of Java elements.
	 * An empty array is returned if one of the elements stored in the
	 * structured selection is not of type <code>IModelElement</code>
	 * 
	 * @param selection
	 *            the selection
	 * @return the Java element contained in the selection
	 */
	public static IModelElement[] getElements(IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			IModelElement[] result = new IModelElement[selection.size()];
			int i = 0;
			for (Iterator iter = selection.iterator(); iter.hasNext(); i++) {
				Object element = iter.next();
				if (!(element instanceof IModelElement))
					return EMPTY_RESULT;
				result[i] = (IModelElement) element;
			}
			return result;
		}
		return EMPTY_RESULT;
	}

	public static boolean canOperateOn(PHPStructuredEditor editor) {
		if (editor == null)
			return false;
		return getInput(editor) != null;

	}

	public static IModelElement[] codeResolveOrInputForked(
			PHPStructuredEditor editor) throws InvocationTargetException,
			InterruptedException {
		ISourceModule input = getInput(editor);
		if (input == null)
			return EMPTY_RESULT;

		ITextSelection selection = (ITextSelection) editor
				.getSelectionProvider().getSelection();
		IModelElement[] result = performForkedCodeResolve(input, selection);
		if (result.length == 0) {
			result = new IModelElement[] { input };
		}
		return result;
	}

	public static IModelElement[] codeResolve(PHPStructuredEditor editor)
			throws ModelException {
		return codeResolve(editor, true);
	}

	/**
	 * Perform a code resolve at the current selection of an editor
	 * 
	 * @param editor
	 *            the editor
	 * @param primaryOnly
	 *            if <code>true</code> only primary working copies will be
	 *            returned
	 * @return the resolved elements
	 * @throws ModelException
	 * @since 3.2
	 */
	public static IModelElement[] codeResolve(PHPStructuredEditor editor,
			boolean primaryOnly) throws ModelException {
		ISourceModule input = getInput(editor, primaryOnly);
		if (input != null)
			return codeResolve(input, (ITextSelection) editor
					.getSelectionProvider().getSelection());
		return EMPTY_RESULT;
	}

	/**
	 * Perform a code resolve in a separate thread.
	 * 
	 * @param editor
	 *            the editor
	 * @param primaryOnly
	 *            if <code>true</code> only primary working copies will be
	 *            returned
	 * @return the resolved elements
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 * @since 3.2
	 */
	public static IModelElement[] codeResolveForked(PHPStructuredEditor editor,
			boolean primaryOnly) throws InvocationTargetException,
			InterruptedException {
		ISourceModule input = getInput(editor, primaryOnly);
		if (input != null)
			return performForkedCodeResolve(input, (ITextSelection) editor
					.getSelectionProvider().getSelection());
		return EMPTY_RESULT;
	}

	public static IModelElement getElementAtOffset(PHPStructuredEditor editor)
			throws ModelException {
		return getElementAtOffset(editor, true);
	}

	/**
	 * Returns the element surrounding the selection of the given editor.
	 * 
	 * @param editor
	 *            the editor
	 * @param primaryOnly
	 *            if <code>true</code> only primary working copies will be
	 *            returned
	 * @return the element surrounding the current selection
	 * @throws ModelException
	 * @since 3.2
	 */
	private static IModelElement getElementAtOffset(PHPStructuredEditor editor,
			boolean primaryOnly) throws ModelException {
		ISourceModule input = getInput(editor, primaryOnly);
		if (input != null)
			return getElementAtOffset(input, (ITextSelection) editor
					.getSelectionProvider().getSelection());
		return null;
	}

	public static IType getTypeAtOffset(PHPStructuredEditor editor)
			throws ModelException {
		IModelElement element = SelectionConverter.getElementAtOffset(editor);
		IType type = (IType) element.getAncestor(IModelElement.TYPE);
		if (type == null) {
			ISourceModule unit = SelectionConverter
					.getInputAsCompilationUnit(editor);
			if (unit != null) {
				final IType[] allTypes = unit.getAllTypes();
				if (allTypes != null && allTypes.length > 0) {
					type = allTypes[0];
				}
			}

		}
		return type;
	}

	public static ISourceModule getInput(PHPStructuredEditor editor) {
		return getInput(editor, true);
	}

	/**
	 * Returns the input element of the given editor
	 * 
	 * @param editor
	 *            the Java editor
	 * @param primaryOnly
	 *            if <code>true</code> only primary working copies will be
	 *            returned
	 * @return the type root which is the editor input
	 * @since 3.2
	 */
	private static ISourceModule getInput(PHPStructuredEditor editor,
			boolean primaryOnly) {
		if (editor == null)
			return null;
		return (ISourceModule) EditorUtility.getEditorInputModelElement(editor,
				primaryOnly);
	}

	public static ISourceModule getInputAsTypeRoot(PHPStructuredEditor editor) {
		return SelectionConverter.getInput(editor);
	}

	public static ISourceModule getInputAsCompilationUnit(
			PHPStructuredEditor editor) {
		Object editorInput = SelectionConverter.getInput(editor);
		if (editorInput instanceof ISourceModule)
			return (ISourceModule) editorInput;
		return null;
	}

	private static IModelElement[] performForkedCodeResolve(
			final ISourceModule input, final ITextSelection selection)
			throws InvocationTargetException, InterruptedException {
		final class CodeResolveRunnable implements IRunnableWithProgress {
			IModelElement[] result;

			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					result = codeResolve(input, selection);
				} catch (ModelException e) {
					throw new InvocationTargetException(e);
				}
			}
		}
		CodeResolveRunnable runnable = new CodeResolveRunnable();
		PlatformUI.getWorkbench().getProgressService()
				.busyCursorWhile(runnable);
		return runnable.result;
	}

	public static IModelElement[] codeResolve(IModelElement input,
			ITextSelection selection) throws ModelException {
		if (input instanceof ICodeAssist) {
			if (input instanceof ISourceModule) {
				ScriptModelUtil.reconcile((ISourceModule) input);
			}
			IModelElement[] elements = ((ICodeAssist) input).codeSelect(
					selection.getOffset() + selection.getLength(), 0);
			if (elements.length > 0) {
				return elements;
			}
		}
		return EMPTY_RESULT;
	}

	public static IModelElement getElementAtOffset(ISourceModule input,
			ITextSelection selection) throws ModelException {
		if (input instanceof ISourceModule) {
			ScriptModelUtil.reconcile((ISourceModule) input);
		}
		IModelElement ref = input.getElementAt(selection.getOffset());
		if (ref == null)
			return input;
		return ref;
	}

	// public static IModelElement[] resolveSelectedElements(IModelElement
	// input, ITextSelection selection) throws ModelException {
	// IModelElement enclosing= resolveEnclosingElement(input, selection);
	// if (enclosing == null)
	// return EMPTY_RESULT;
	// if (!(enclosing instanceof ISourceReference))
	// return EMPTY_RESULT;
	// ISourceRange sr= ((ISourceReference)enclosing).getSourceRange();
	// if (selection.getOffset() == sr.getOffset() && selection.getLength() ==
	// sr.getLength())
	// return new IModelElement[] {enclosing};
	// }

	public static IModelElement resolveEnclosingElement(
			PHPStructuredEditor editor, ITextSelection selection)
			throws ModelException {
		ISourceModule input = getInput(editor);
		if (input != null)
			return resolveEnclosingElement(input, selection);
		return null;
	}

	public static IModelElement resolveEnclosingElement(IModelElement input,
			ITextSelection selection) throws ModelException {
		IModelElement atOffset = null;
		if (input instanceof ISourceModule) {
			ISourceModule cunit = (ISourceModule) input;
			ScriptModelUtil.reconcile(cunit);
			atOffset = cunit.getElementAt(selection.getOffset());
		} else {
			return null;
		}
		if (atOffset == null) {
			return input;
		} else {
			int selectionEnd = selection.getOffset() + selection.getLength();
			IModelElement result = atOffset;
			if (atOffset instanceof ISourceReference) {
				ISourceRange range = ((ISourceReference) atOffset)
						.getSourceRange();
				while (range.getOffset() + range.getLength() < selectionEnd) {
					result = result.getParent();
					if (!(result instanceof ISourceReference)) {
						result = input;
						break;
					}
					range = ((ISourceReference) result).getSourceRange();
				}
			}
			return result;
		}
	}

	/**
	 * Shows a dialog for resolving an ambiguous Java element. Utility method
	 * that can be called by subclasses.
	 * 
	 * @param elements
	 *            the elements to select from
	 * @param shell
	 *            the parent shell
	 * @param title
	 *            the title of the selection dialog
	 * @param message
	 *            the message of the selection dialog
	 * @return returns the selected element or <code>null</code> if the dialog
	 *         has been cancelled
	 */
	public static IModelElement selectJavaElement(IModelElement[] elements,
			Shell shell, String title, String message) {
		int nResults = elements.length;
		if (nResults == 0)
			return null;
		if (nResults == 1)
			return elements[0];

		int flags = ModelElementLabelProvider.SHOW_DEFAULT
				| ModelElementLabelProvider.SHOW_QUALIFIED
				| ModelElementLabelProvider.SHOW_ROOT;

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				shell, new ModelElementLabelProvider(flags));
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setElements(elements);

		if (dialog.open() == Window.OK) {
			return (IModelElement) dialog.getFirstResult();
		}
		return null;
	}
}
