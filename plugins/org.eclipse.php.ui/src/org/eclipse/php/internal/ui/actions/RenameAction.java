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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.refactoring.actions.RenameResourceAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.documentModel.dom.ElementImplForPHP;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.*;

public class RenameAction implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {

	private IPHPActionDelegator fRenamePHPElement;
	private RenameResourceAction resourceAction;
	private ISelection selection;
	private static final String RENAME_ELEMENT_ACTION_ID = "org.eclipse.php.ui.actions.RenameElement"; //$NON-NLS-1$

	@Override
	public void dispose() {

	}

	@Override
	public void init(IWorkbenchWindow window) {
		if (window != null) {

			init();
			if (fRenamePHPElement == null) {
				IWorkbenchPage page = window.getActivePage();
				if (page != null) {
					if (page.getActivePart() != null) {
						resourceAction = new RenameResourceAction(page.getActivePart().getSite());
					}
				}
			} else {
				fRenamePHPElement.init(window);
			}
		}

	}

	private void init() {
		fRenamePHPElement = PHPActionDelegatorRegistry.getActionDelegator(RENAME_ELEMENT_ACTION_ID);
	}

	@Override
	public void run(IAction action) {
		if (resourceAction != null) {
			if (!selection.isEmpty()) {
				Object object = ((IStructuredSelection) selection).getFirstElement();
				IResource resource = null;
				if (object instanceof IModelElement) {
					resource = ((IModelElement) object).getResource();
				}
				if (object instanceof IResource) {
					resource = (IResource) object;
				}
				if (object instanceof ElementImplForPHP) {
					resource = ((ElementImplForPHP) object).getModelElement().getResource();
				}
				if (resource != null) {
					IStructuredSelection resourceSel = new StructuredSelection(resource);
					resourceAction.run(resourceSel);
				} else {
					MessageDialog.openInformation(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
							PHPUIMessages.RenamePHPElementAction_name,
							PHPUIMessages.RenamePHPElementAction_not_available);
				}
			}
		} else {
			fRenamePHPElement.run(action);
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;

		if (fRenamePHPElement != null) {
			fRenamePHPElement.selectionChanged(action, selection);
		}

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (fRenamePHPElement == null) {
			init();
		}
		if (targetEditor != null && resourceAction == null && fRenamePHPElement == null) {
			resourceAction = new RenameResourceAction(targetEditor.getSite());
		}

		if (fRenamePHPElement instanceof IEditorActionDelegate) {
			((IEditorActionDelegate) fRenamePHPElement).setActiveEditor(action, targetEditor);
		}
	}

}