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
package org.eclipse.php.debug.ui.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IExpressionManager;
import org.eclipse.debug.core.model.IWatchExpression;
import org.eclipse.debug.internal.ui.actions.expressions.WatchExpressionAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMText;

/**
 * An action for adding a new PHP watch expressions.
 * 
 * @author shalom
 */
public class PHPWatchAction extends WatchExpressionAction implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {

	public void init(IWorkbenchWindow window) {
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			action.setEnabled(textSelection.getLength() != 0);
		}
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

	public void run(IAction action) {
		IStructuredSelection selection = getCurrentSelection();
		if (selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			IExpressionManager expressionManager = DebugPlugin.getDefault().getExpressionManager();
			IDOMText domText = (IDOMText) selection.getFirstElement();
			String expression;
			try {
				expression = domText.getFirstStructuredDocumentRegion().getParentDocument().get(textSelection.getOffset(), textSelection.getLength());
				//	create the new watch expression
				IWatchExpression watchExpression = expressionManager.newWatchExpression(expression.trim());
				expressionManager.addExpression(watchExpression);
				// refresh and re-evaluate
				watchExpression.setExpressionContext(getContext());
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

}
