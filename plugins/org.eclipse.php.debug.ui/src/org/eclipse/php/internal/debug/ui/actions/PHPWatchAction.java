/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IExpressionManager;
import org.eclipse.debug.core.model.IWatchExpression;
import org.eclipse.debug.internal.ui.actions.expressions.WatchExpressionAction;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.ui.*;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * An action for adding a new PHP watch expressions.
 * 
 * @author shalom
 */
public class PHPWatchAction extends WatchExpressionAction
		implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {

	@Override
	public void init(IWorkbenchWindow window) {
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			action.setEnabled(textSelection.getLength() != 0);
		}
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

	@Override
	public void run(IAction action) {
		IStructuredSelection selection = getCurrentSelection();
		if (selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			IExpressionManager expressionManager = DebugPlugin.getDefault().getExpressionManager();
			IDOMNode domNode = (IDOMNode) selection.getFirstElement();
			String expression;
			try {
				expression = domNode.getFirstStructuredDocumentRegion().getParentDocument()
						.get(textSelection.getOffset(), textSelection.getLength());
				// create the new watch expression
				IWatchExpression watchExpression = expressionManager.newWatchExpression(expression.trim());
				expressionManager.addExpression(watchExpression);
				// show expression view
				showExpressionsView();
				// refresh and re-evaluate
				watchExpression.setExpressionContext(getContext());
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	/**
	 * @since 3.4
	 */
	@Override
	public void dispose() {
	}

	/**
	 * Make the expression view visible or open one if required.
	 */
	protected void showExpressionsView() {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (activePage == null
				|| activePage.getActivePart().getSite().getId().equals(IDebugUIConstants.ID_EXPRESSION_VIEW)) {
			return;
		}
		IViewPart part = activePage.findView(IDebugUIConstants.ID_EXPRESSION_VIEW);
		if (part == null) {
			try {
				activePage.showView(IDebugUIConstants.ID_EXPRESSION_VIEW);
			} catch (PartInitException e) {
				Logger.logException(e);
			}
		} else {
			activePage.bringToTop(part);
		}
	}

}
