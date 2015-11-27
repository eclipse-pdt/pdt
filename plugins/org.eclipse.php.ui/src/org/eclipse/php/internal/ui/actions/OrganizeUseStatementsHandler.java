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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.corext.util.DocumentUtils;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.*;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 *
 * @see IWorkbenchWindowActionDelegate
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class OrganizeUseStatementsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow == null) {
			return null;
		}
		IWorkbenchPage page = workbenchWindow.getActivePage();
		if (page == null) {
			return null;
		}

		IEditorPart input = page.getActiveEditor();
		if (input instanceof PHPStructuredEditor) {
			PHPStructuredEditor ite = (PHPStructuredEditor) input;
			IDocument doc = ite.getDocumentProvider().getDocument(ite.getEditorInput());

			ModuleDeclaration moduleDeclaration = SourceParserUtil
					.getModuleDeclaration((ISourceModule) ite.getModelElement());

			DocumentUtils.sortUseStatements(moduleDeclaration, doc);
		}
		return null;
	}
}