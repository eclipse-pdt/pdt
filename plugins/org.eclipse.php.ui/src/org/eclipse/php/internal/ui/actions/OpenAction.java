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

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.explorer.PHPTreeViewer;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IEditorStatusLine;

public class OpenAction extends SelectionDispatchAction {

	private PHPStructuredEditor fEditor;

	/**
	 * Creates a new <code>OpenAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public OpenAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.OpenAction_label);
		setToolTipText(PHPUIMessages.OpenAction_tooltip);
		setDescription(PHPUIMessages.OpenAction_description);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.OPEN_ACTION);
	}

	/* 
	 * We override this function since we've changed isEnabled() to check its status according to the selection
	 */
	public void selectionChanged(ITextSelection selection) {
	}

	/* 
	 * We override this function since we've changed isEnabled() to check its status according to the selection
	 */
	public void selectionChanged(IStructuredSelection selection) {
	}

	
	public boolean isEnabled() {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			return checkEnabled(structuredSelection);
		}
		return true;
	}

	private boolean checkEnabled(IStructuredSelection selection) {
		if (selection.isEmpty())
			return false;
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof PHPCodeData)
				continue;
			if (element instanceof IFile)
				continue;
			if (element instanceof IStorage)
				continue;
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void run(ITextSelection selection) {
		if (!ActionUtils.isProcessable(getShell(), fEditor))
			return;
		PHPCodeData element = SelectionConverter.codeResolve(fEditor, getShell(), getDialogTitle(), PHPUIMessages.OpenAction_select_element);
		if (element == null) {
			IEditorStatusLine statusLine = (IEditorStatusLine) fEditor.getAdapter(IEditorStatusLine.class);
			if (statusLine != null)
				statusLine.setMessage(true, PHPUIMessages.OpenAction_error_messageBadSelection, null);
			getShell().getDisplay().beep();
			return;
		}

		run(new Object[] { element }, null);
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void run(IStructuredSelection selection) {
		if (!checkEnabled(selection))
			return;
		run(selection.toArray(), ((PHPTreeViewer) getSelectionProvider()).getTree().getSelection());
	}

	/**
	 * Note: this method is for internal use only. Clients should not call this method.
	 * 
	 * @param elements the elements to process
	 */
	public void run(Object[] elements, Object[] treeNodes) {
		if (elements == null)
			return;
		for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			try {
				element = getElementToOpen(element);
				boolean activateOnOpen = fEditor != null ? true : OpenStrategy.activateOnOpen();
				if (element instanceof PHPCodeData && treeNodes != null) {
					OpenActionUtil.open(treeNodes[i], activateOnOpen);
				} else {
					OpenActionUtil.open(element, activateOnOpen);
				}
			} catch (PartInitException x) {
				MessageDialog.openError(getShell(), PHPUIMessages.OpenAction_error_messageProblems, "");

			}
		}
	}

	/**
	 * Note: this method is for internal use only. Clients should not call this method.
	 * 
	 * @param object the element to open
	 * @return the real element to open
	 */
	public Object getElementToOpen(Object object) {
		return object;
	}

	private String getDialogTitle() {
		return PHPUIMessages.OpenAction_error_title;
	}
	
}
