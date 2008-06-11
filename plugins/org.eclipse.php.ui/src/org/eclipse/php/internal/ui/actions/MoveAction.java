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
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

public class MoveAction extends SelectionDispatchAction {

	private static final String MOVE_ACTION_ID = "org.eclipse.php.ui.actions.Move"; //$NON-NLS-1$

	private PHPStructuredEditor fEditor;
	private IPHPActionDelegator fReorgMoveAction;
	private IStructuredSelection selectedResources;

	/**
	 * Creates a new <code>MoveAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public MoveAction(IWorkbenchSite site) {
		super(site);
		initMoveAction();
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call this constructor.
	 * @param editor the compilation unit editor
	 */
	public MoveAction(PHPStructuredEditor editor) {
		super(editor.getEditorSite());
		fEditor = editor;
		initMoveAction();
	}

	/**
	 * Initialize the action
	 *
	 */
	private void initMoveAction() {
		fReorgMoveAction = PHPActionDelegatorRegistry.getActionDelegator(MOVE_ACTION_ID);
		setText(PHPUIMessages.getString("MoveAction_text"));
		update(getSelection());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IIDEHelpContextIds.MOVE_RESOURCE_ACTION);
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {

		selectedResources = null;

		// disable moving the file through right click on the editor - confusing.
		if (selection != null && selection instanceof ITextSelection) {
			setEnabled(false);
			return;
		}

		if (!selection.isEmpty()) {
			if (ActionUtils.containsOnlyProjects(selection.toList())) {
				setEnabled(createWorkbenchAction(selection).isEnabled());
				return;
			}
			List elements = selection.toList();
			IResource[] resources = ActionUtils.getResources(elements);

			// true - exclude php file data as it is resource and not element in this case 
			Object[] phpElements = ActionUtils.getPHPElements(elements, true);

			if (elements.size() != resources.length + phpElements.length)
				setEnabled(false);
			else {
				boolean enabled = true;
				if (PHPUiConstants.DISABLE_ELEMENT_REFACTORING) {
					for (int i = 0; i < phpElements.length; i++) {
						if (!(phpElements[i] instanceof PHPFileData))
							enabled = false;
					}
				}
				if (enabled) {
					List<IResource> list = new ArrayList<IResource>(Arrays.asList(resources));
					for (int i = 0; i < phpElements.length; i++) {
						if (phpElements[i] instanceof PHPFileData) {
							IResource res = PHPModelUtil.getResource(phpElements[i]);
							if (res != null && res.exists()) {
								list.add(PHPModelUtil.getResource(phpElements[i]));
							}
						}

					}
					if (list.size() == elements.size()) // only files selected
					{
						selectedResources = new StructuredSelection(list);
						enabled = createWorkbenchAction(selectedResources).isEnabled();
					}
				}
				setEnabled(enabled);
			}
		} else {
			selectedResources = StructuredSelection.EMPTY;
			setEnabled(false);
		}

		fReorgMoveAction.setSelection(selectedResources);
	}

	private SelectionListenerAction createWorkbenchAction(IStructuredSelection selection) {

		List list = selection.toList();
		SelectionListenerAction action = null;
		if (list.size() == 0 || list.get(0) instanceof IProject) {
			action = new PHPMoveProjectAction(getShell());
			action.selectionChanged(selection);
		} else if (selectedResources != null) {
			action = new MoveResourceAction(getShell());
			action.selectionChanged(selection);

		}
		return action;
	}

	@Override
	public void run(IStructuredSelection selection) {
		if (isEnabled())
			fReorgMoveAction.run(selection);

	}

	@Override
	public void run(ITextSelection selection) {
		if (!ActionUtils.isProcessable(getShell(), fEditor))
			return;

		if (tryReorgMove(selection))
			return;

		MessageDialog.openInformation(getShell(), PHPUIMessages.getString("MoveAction_Move"), PHPUIMessages.getString("MoveAction_select"));
	}

	private boolean tryReorgMove(ITextSelection selection) {
		IModelElement element;
		try {
			element = SelectionConverter.getElementAtOffset(fEditor);
		} catch (ModelException e) {
			return false;
		}
		if (element == null)
			return false;
		IStructuredSelection mockStructuredSelection = new StructuredSelection(element);
		selectionChanged(mockStructuredSelection);
		if (!isEnabled())
			return false;

		fReorgMoveAction.run(mockStructuredSelection);
		return true;
	}

	/*
	 * @see SelectionDispatchAction#update(ISelection)
	 */
	@Override
	public void update(ISelection selection) {
		super.update(selection);
	}
}
