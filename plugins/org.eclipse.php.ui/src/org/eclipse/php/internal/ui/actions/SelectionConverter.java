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
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

public class SelectionConverter {

	private static final PHPCodeData[] EMPTY_RESULT = new PHPCodeData[0];

	private SelectionConverter() {
		// no instance
	}

	/**
	 * Converts the selection provided by the given part into a structured selection.
	 * The following conversion rules are used:
	 * <ul>
	 *	<li><code>part instanceof PHPStructuredEditor</code>: returns a structured selection
	 * 	using code resolve to convert the editor's text selection.</li>
	 * <li><code>part instanceof IWorkbenchPart</code>: returns the part's selection
	 * 	if it is a structured selection.</li>
	 * <li><code>default</code>: returns an empty structured selection.</li>
	 * </ul>
	 */
	public static IStructuredSelection getStructuredSelection(IWorkbenchPart part) {
		final PHPStructuredEditor structuredEditor = EditorUtility.getPHPStructuredEditor(part);
		if (structuredEditor != null)
			return new StructuredSelection(codeResolve(structuredEditor));
		ISelectionProvider provider = part.getSite().getSelectionProvider();
		if (provider != null) {
			ISelection selection = provider.getSelection();
			if (selection instanceof IStructuredSelection)
				return (IStructuredSelection) selection;
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * Converts the given structured selection into an array of PHP elements.
	 * An empty array is returned if one of the elements stored in the structured
	 * selection is not of tupe <code>PHPCodeData</code>
	 */
	public static PHPCodeData[] getElements(IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			PHPCodeData[] result = new PHPCodeData[selection.size()];
			int i = 0;
			for (Iterator iter = selection.iterator(); iter.hasNext(); i++) {
				Object element = iter.next();
				if (!(element instanceof PHPCodeData))
					return EMPTY_RESULT;
				result[i] = (PHPCodeData) element;
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

	/**
	 * Converts the text selection provided by the given editor into an array of
	 * PHP elements. If the selection doesn't cover a PHP element and the selection's
	 * length is greater than 0 the methods returns the editor's input element.
	 */
	public static PHPCodeData[] codeResolveOrInput(PHPStructuredEditor editor) {
		PHPCodeData input = getInput(editor);
		ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
		PHPCodeData[] result = codeResolve(input, selection);
		if (result.length == 0) {
			result = new PHPCodeData[] { input };
		}
		return result;
	}

	public static PHPCodeData[] codeResolveOrInputHandled(PHPStructuredEditor editor, Shell shell, String title) {
		return codeResolveOrInput(editor);
	}

	/**
	 * Converts the text selection provided by the given editor a PHP element by
	 * asking the user if code reolve returned more than one result. If the selection 
	 * doesn't cover a PHP element and the selection's length is greater than 0 the 
	 * methods returns the editor's input element.
	 */
	public static PHPCodeData codeResolveOrInput(PHPStructuredEditor editor, Shell shell, String title, String message) {
		PHPCodeData[] elements = codeResolveOrInput(editor);
		if (elements == null || elements.length == 0)
			return null;
		PHPCodeData candidate = elements[0];
		if (elements.length > 1) {
			candidate = OpenActionUtil.selectPHPElement(elements, shell, title, message);
		}
		return candidate;
	}

	public static PHPCodeData codeResolveOrInputHandled(PHPStructuredEditor editor, Shell shell, String title, String message) {
		return codeResolveOrInput(editor, shell, title, message);
	}

	public static PHPCodeData[] codeResolve(PHPStructuredEditor editor) {
		return codeResolve(getInput(editor), (ITextSelection) editor.getSelectionProvider().getSelection());
	}

	/**
	 * Converts the text selection provided by the given editor a PHP element by
	 * asking the user if code reolve returned more than one result. If the selection 
	 * doesn't cover a PHP element <code>null</code> is returned.
	 */
	public static PHPCodeData codeResolve(PHPStructuredEditor editor, Shell shell, String title, String message) {
		PHPCodeData[] elements = codeResolve(editor);
		if (elements == null || elements.length == 0)
			return null;
		PHPCodeData candidate = elements[0];
		if (elements.length > 1) {
			candidate = OpenActionUtil.selectPHPElement(elements, shell, title, message);
		}
		return candidate;
	}

	public static PHPCodeData[] codeResolveHandled(PHPStructuredEditor editor, Shell shell, String title) {
		return codeResolve(editor);
	}

	public static PHPCodeData getElementAtOffset(PHPStructuredEditor editor) {
		return getElementAtOffset(getInput(editor), (ITextSelection) editor.getSelectionProvider().getSelection());
	}

	public static PHPFileData getInput(PHPStructuredEditor editor) {
		if (editor == null)
			return null;
		return editor.getPHPFileData();
	}

	public static PHPFileData getInputAsCompilationUnit(PHPStructuredEditor editor) {
		Object editorInput = SelectionConverter.getInput(editor);
		if (editorInput instanceof PHPFileData)
			return (PHPFileData) editorInput;
		else
			return null;
	}

	public static PHPCodeData[] codeResolve(PHPCodeData input, ITextSelection selection) {
		// a workaround:
		String text = selection.getText();
		if (text == null) {
			return EMPTY_RESULT;
		}
		text = text.trim();
		if ("".equals(text)) { //$NON-NLS-1$
			return EMPTY_RESULT;
		}

		ArrayList codeDatas = new ArrayList();

		if (input instanceof PHPFileData && text.matches("^[\\w]+$")) { //$NON-NLS-1$
			PHPWorkspaceModelManager modelManager = PHPWorkspaceModelManager.getInstance();
			IProject project = modelManager.getProjectForFileData((PHPFileData) input, null);
			PHPProjectModel model = modelManager.getModelForProject(project);
			PHPClassData classData = model.getClass(input.getName(), text);
			if (classData != null) {
				codeDatas.add(classData);
			}
			PHPFunctionData functionData = model.getFunction(input.getName(), text);
			if (functionData != null) {
				codeDatas.add(functionData);
			}
			PHPConstantData constantData = model.getConstant(input.getName(), text);
			if (constantData != null) {
				codeDatas.add(constantData);
			}
			return (PHPCodeData[]) codeDatas.toArray(new PHPCodeData[codeDatas.size()]);

		}
		return EMPTY_RESULT;
	}

	public static PHPCodeData getElementAtOffset(PHPCodeData input, ITextSelection selection) {
		if (input instanceof PHPFileData) {
			PHPFileData cunit = (PHPFileData) input;
			PHPCodeData ref = PHPModelUtil.getElementAt(cunit, selection.getOffset());
			if (ref == null)
				return input;
			else
				return ref;
		}
		return null;
	}

	public static PHPCodeData resolveEnclosingElement(PHPStructuredEditor editor, ITextSelection selection) {
		return resolveEnclosingElement(getInput(editor), selection);
	}

	public static PHPCodeData resolveEnclosingElement(PHPCodeData input, ITextSelection selection) {
		PHPCodeData atOffset = null;
		if (input instanceof PHPFileData) {
			PHPFileData cunit = (PHPFileData) input;
			atOffset = PHPModelUtil.getElementAt(cunit, selection.getOffset());
		} else {
			return null;
		}
		if (atOffset == null) {
			return input;
		}
		return atOffset;

	}

}
