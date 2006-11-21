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

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.documentModel.parser.structregions.PHPStructuredDocumentRegion;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.wst.xml.core.internal.document.TextImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * This Action delegate is used by the Menu's action GotoMatchingBracketAction
 * @author yaronm
 *
 */
public class GotoMatchingBracketActionDelegate implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow fWorkbench;

	public void dispose() {
		fWorkbench = null;
	}

	public void init(IWorkbenchWindow window) {
		fWorkbench = window;
	}

	public void run(IAction action) {
		PHPStructuredEditor phpEditor = getActiveEditor();
		if (phpEditor != null) {
			phpEditor.getAction(GotoMatchingBracketAction.GOTO_MATCHING_BRACKET).run();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		//enable the action only if it is a PHP code
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			Object element = sSelection.getFirstElement();
			if (element instanceof PHPElementImpl || (element instanceof TextImpl && ((IDOMNode) element).getFirstStructuredDocumentRegion() instanceof PHPStructuredDocumentRegion)) {
				action.setEnabled(true);
			} else {
				action.setEnabled(false);
			}

		} else {
			action.setEnabled(false);
		}
	}

	private PHPStructuredEditor getActiveEditor() {
		IEditorPart editor = fWorkbench.getActivePage().getActiveEditor();
		if (editor instanceof PHPStructuredEditor) {
			return (PHPStructuredEditor) editor;
		}
		return null;
	}
}
