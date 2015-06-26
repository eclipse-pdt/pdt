/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Yannick de Lange <yannick.l.88@gmail.com>
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.corext.util.DocumentUtils;
import org.eclipse.php.internal.core.organize.UseStatementBlock;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 *
 * @see IWorkbenchWindowActionDelegate
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class OrganizeUseStatementsAction
		implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public OrganizeUseStatementsAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 *
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		IEditorPart input = window.getActivePage().getActiveEditor();

		if (input instanceof ITextEditor) {
			ITextEditor ite = (ITextEditor) input;
			IDocument doc = ite.getDocumentProvider()
					.getDocument(ite.getEditorInput());

			try {
				sortUseStatements(doc);
			} catch (BadLocationException e) {
			}
		}
	}

	private void sortUseStatements(IDocument doc) throws BadLocationException {
		UseStatementBlock block = DocumentUtils.findUseStatmentsBlock(doc);

		if (block.end == -1) {
			return;
		}
		String total = doc.get().substring(block.end);

		List<UseStatement> statements = DocumentUtils
				.filterAndSort(block.getStatements(), total);
		doc.replace(block.start, block.length,
				DocumentUtils.createStringFromUseStatement(statements));
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 *
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 *
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 *
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}