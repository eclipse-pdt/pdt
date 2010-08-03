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

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.texteditor.IEditorStatusLine;

public class OpenAction extends SelectionDispatchAction {

	private PHPStructuredEditor fEditor;

	/**
	 * Creates a new <code>OpenAction</code>. The action requires that the
	 * selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param site
	 *            the site providing context information for this action
	 */
	public OpenAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.OpenAction_label);
		setToolTipText(PHPUIMessages.OpenAction_tooltip);
		setDescription(PHPUIMessages.OpenAction_description);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IIDEHelpContextIds.OPEN_RESOURCE_ACTION);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the Script editor
	 */
	public OpenAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		setEnabled(EditorUtility.getEditorInputModelElement(fEditor, false) != null);
	}

	/*
	 * We override this function since we've changed isEnabled() to check its
	 * status according to the selection
	 */
	public void selectionChanged(ITextSelection selection) {
	}

	/*
	 * We override this function since we've changed isEnabled() to check its
	 * status according to the selection
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(checkEnabled(selection));
	}

	private boolean checkEnabled(IStructuredSelection selection) {
		if (selection.isEmpty())
			return false;
		for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (!checkElement(element)) {
				return false;
			}
		}
		return true;
	}

	protected boolean checkElement(Object element) {
		if ((element instanceof ISourceReference)
				|| ((element instanceof IAdaptable) && (((IAdaptable) element)
						.getAdapter(ISourceReference.class) != null)))
			return true;
		if ((element instanceof IFile)
				|| ((element instanceof IAdaptable) && (((IAdaptable) element)
						.getAdapter(IFile.class) != null)))
			return true;
		if ((element instanceof IStorage)
				|| ((element instanceof IAdaptable) && (((IAdaptable) element)
						.getAdapter(IStorage.class) != null)))
			return true;
		return false;
	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void run(ITextSelection selection) {
		if (!ActionUtils.isProcessable(getShell(), fEditor))
			return;
		// TODO open in a dialog ? with these parameters: getShell(),
		// getDialogTitle(),
		// PHPUIMessages.getString("OpenAction_select_element")
		IModelElement[] element = null;
		try {
			element = SelectionConverter.codeResolve(fEditor);
		} catch (ModelException e) {
			Logger.logException(e);
		}
		if (element == null) {
			IEditorStatusLine statusLine = (IEditorStatusLine) fEditor
					.getAdapter(IEditorStatusLine.class);
			if (statusLine != null)
				statusLine.setMessage(true,
						PHPUIMessages.OpenAction_error_messageBadSelection,
						null);
			getShell().getDisplay().beep();
			return;
		}

		run(new Object[] { element }, null);
	}

	/*
	 * (non-Javadoc) Method declared on SelectionDispatchAction.
	 */
	public void run(IStructuredSelection selection) {
		if (!checkEnabled(selection))
			return;
		run(selection.toArray(), ((TreeViewer) getSelectionProvider())
				.getTree().getSelection());
	}

	/**
	 * Note: this method is for internal use only. Clients should not call this
	 * method.
	 * 
	 * @param elements
	 *            the elements to process
	 */
	public void run(Object[] elements, Object[] treeNodes) {
		if (elements == null)
			return;
		for (int i = 0; i < elements.length; i++) {
			Object element = elements[i];
			try {
				element = getElementToOpen(element);
				boolean activateOnOpen = fEditor != null ? true : OpenStrategy
						.activateOnOpen();
				if (element instanceof ISourceModule && treeNodes != null) {
					Object o = treeNodes[i];
					if (o instanceof TreeItem) {
						TreeItem item = (TreeItem) o;
						o = item.getData();
					}
					OpenActionUtil.open(o, activateOnOpen);
				} else {
					OpenActionUtil.open(element, activateOnOpen);
				}
			} catch (PartInitException x) {
				MessageDialog.openError(getShell(),
						PHPUIMessages.OpenAction_error_messageProblems, ""); //$NON-NLS-1$
			} catch (ModelException e) {
				MessageDialog.openError(getShell(),
						PHPUIMessages.OpenAction_error_messageProblems, ""); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Note: this method is for internal use only. Clients should not call this
	 * method.
	 * 
	 * @param object
	 *            the element to open
	 * @return the real element to open
	 */
	public Object getElementToOpen(Object object) {
		return object;
	}

	private String getDialogTitle() {
		return PHPUIMessages.OpenAction_error_title;
	}

}
