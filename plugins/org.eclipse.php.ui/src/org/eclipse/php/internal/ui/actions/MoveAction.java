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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.MoveProjectAction;
import org.eclipse.ui.actions.MoveResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

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
		setText(PHPUIMessages.MoveAction_text);
		update(getSelection());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.MOVE_ACTION);
	}

	/*
	 * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
	}

	public void selectionChanged(IStructuredSelection selection) {

		selectedResources = null;

		if (selection != null && selection instanceof ITextSelection) {
			selectionChanged((ITextSelection) selection);
			return;
		}

		if (!selection.isEmpty()) {
			if (ActionUtils.containsOnlyProjects(selection.toList())) {
				setEnabled(createWorkbenchAction(selection).isEnabled());
				return;
			}
			List elements = selection.toList();
			IResource[] resources = ActionUtils.getResources(elements);
			Object[] phpElements = ActionUtils.getPHPElements(elements);

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
					List list = new ArrayList(Arrays.asList(resources));
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

	// we will get to this method only in case this is an editor selection
	public void selectionChanged(ITextSelection selection) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (activePage == null)
			return;
		IWorkbenchPart activePart = activePage.getActivePart();
		if (activePage == null)
			return;
		// get the current file 
		PHPStructuredEditor editor = EditorUtility.getPHPStructuredEditor(activePart);
		if (editor == null)
			return;
		IResource[] resources = { editor.getFile() };
		selectedResources = new StructuredSelection(resources);
		setEnabled(true);
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

	public void run(IStructuredSelection selection) {
		if (isEnabled())
			fReorgMoveAction.run(selection);

	}

	public void run(ITextSelection selection) {
		if (!ActionUtils.isProcessable(getShell(), fEditor))
			return;

		if (tryReorgMove(selection))
			return;

		MessageDialog.openInformation(getShell(), PHPUIMessages.MoveAction_Move, PHPUIMessages.MoveAction_select);
	}

	private boolean tryReorgMove(ITextSelection selection) {
		PHPCodeData element = SelectionConverter.getElementAtOffset(fEditor);
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
	public void update(ISelection selection) {
		super.update(selection);
	}
	
}
