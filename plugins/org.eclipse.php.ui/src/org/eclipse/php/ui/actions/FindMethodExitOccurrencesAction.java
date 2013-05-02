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
package org.eclipse.php.ui.actions;

import java.io.IOException;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.actions.ActionUtil;
import org.eclipse.dltk.internal.ui.editor.ModelTextSelection;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.search.MethodExitsFinder;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.ASTProvider;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.search.FindOccurrencesEngine;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.texteditor.IEditorStatusLine;

/**
 * Action to find all method exits for a given method.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 3.4
 */
public class FindMethodExitOccurrencesAction extends
		org.eclipse.dltk.ui.actions.SelectionDispatchAction {

	private PHPStructuredEditor fEditor;

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the Java editor
	 */
	public FindMethodExitOccurrencesAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(getEditorInput(editor) != null);
	}

	/**
	 * Creates a new {@link FindMethodExitOccurrencesAction}. The action
	 * requires that the selection provided by the site's selection provider is
	 * of type <code>IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the site providing context information for this action
	 */
	public FindMethodExitOccurrencesAction(IWorkbenchSite site) {
		super(site);
		setText("ActionMessages.FindMethodExitOccurrencesAction_label"); //$NON-NLS-1$
		setToolTipText("ActionMessages.FindMethodExitOccurrencesAction_tooltip"); //$NON-NLS-1$
		// TODO: attach find method occurrences context
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IJavaHelpContextIds.FIND_METHOD_EXIT_OCCURRENCES);
	}

	// ---- Text Selection
	// ----------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(ITextSelection selection) {
		setEnabled(true);
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(ModelTextSelection selection) {
		try {
			IModelElement resolveEnclosingElement;
			resolveEnclosingElement = selection.resolveEnclosingElement();
			Program astRoot = ASTProvider.getASTProvider().getAST(
					(ISourceModule) resolveEnclosingElement,
					SharedASTProvider.WAIT_YES, null);
			setEnabled(astRoot != null
					&& new MethodExitsFinder().initialize(astRoot, selection
							.getOffset(), selection.getLength()) == null);
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(false);
	}

	/*
	 * (non-JavaDoc) Method declared in SelectionDispatchAction.
	 */
	public final void run(ITextSelection ts) {
		ISourceModule input = getEditorInput(fEditor);
		if (!ActionUtil.isProcessable(getShell(), input))
			return;
		FindOccurrencesEngine engine = FindOccurrencesEngine
				.create(new MethodExitsFinder());
		try {
			String result = engine.run(input, ts.getOffset(), ts.getLength());
			if (result != null)
				showMessage(getShell(), fEditor, result);
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
		} catch (IOException e) {
			PHPUiPlugin.log(e);
		}
	}

	private static ISourceModule getEditorInput(PHPStructuredEditor editor) {
		return PHPUiPlugin.getEditorInputTypeRoot(editor.getEditorInput());
	}

	private static void showMessage(Shell shell, PHPStructuredEditor editor,
			String msg) {
		IEditorStatusLine statusLine = (IEditorStatusLine) editor
				.getAdapter(IEditorStatusLine.class);
		if (statusLine != null)
			statusLine.setMessage(true, msg, null);
		shell.getDisplay().beep();
	}
}
