/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.actions.IRenamePHPElementActionFactory;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

public class RenameAction extends SelectionDispatchAction {

	private static final String RENAME_ELEMENT_ACTION_ID = "org.eclipse.php.ui.actions.RenameElement"; //$NON-NLS-1$

	private RenamePHPElementAction fRenamePHPElement;
	private RenameResourceAction fRenameResource;

	private PHPStructuredEditor fEditor;

	/**
	 * Creates a new <code>RenameAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public RenameAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.getString("RenameAction_text"));

		// gets the right factory to the element rename refactoring
		final IRenamePHPElementActionFactory actionDelegatorFactory = PHPActionDelegatorRegistry.getActionDelegatorFactory(RENAME_ELEMENT_ACTION_ID);
		if (actionDelegatorFactory == null) {
			// default rename action
			fRenamePHPElement = new RenamePHPElementAction(site);
			fRenamePHPElement.setText(getText());
		} else {
			fRenamePHPElement = actionDelegatorFactory.createRenameAction(site);
			fRenamePHPElement.setText(getText());
		}

		// rename resource
		fRenameResource = new RenameResourceAction(site);
		fRenameResource.setText(getText());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IIDEHelpContextIds.RENAME_RESOURCE_ACTION);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call this constructor.
	 * @param editor the compilation unit editor
	 */
	public RenameAction(PHPStructuredEditor editor) {
		this(editor.getEditorSite());
		fEditor = editor;
		// gets the right factory to the element rename refactoring
		final IRenamePHPElementActionFactory actionDelegatorFactory = PHPActionDelegatorRegistry.getActionDelegatorFactory(RENAME_ELEMENT_ACTION_ID);
		if (actionDelegatorFactory == null) {
			// default rename action
			fRenamePHPElement = new RenamePHPElementAction(editor);
			fRenamePHPElement.setText(getText());
		} else {
			fRenamePHPElement = actionDelegatorFactory.createRenameAction(editor);
			fRenamePHPElement.setText(getText());
		}
		// rename resource
		fRenameResource = new RenameResourceAction(editor.getSite());
		fRenameResource.setText(getText());
	}

	/*
	 * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		fRenamePHPElement.selectionChanged(event);
		if (fRenameResource != null)
			fRenameResource.selectionChanged(event);
		setEnabled(computeEnabledState());
	}

	/*
	 * @see SelectionDispatchAction#update(ISelection)
	 */
	public void update(ISelection selection) {
		fRenamePHPElement.update(selection);

		if (fRenameResource != null)
			fRenameResource.update(selection);

		setEnabled(computeEnabledState());
	}

	private boolean computeEnabledState() {
		if (fRenameResource != null) {
			return fRenamePHPElement.isEnabled() || fRenameResource.isEnabled();
		} else {
			return fRenamePHPElement.isEnabled();
		}
	}

	public void run(IStructuredSelection selection) {
		boolean isFile = ActionUtils.containsOnly(selection.toList(), PHPFileData.class);

		if (!isFile && fRenamePHPElement.isEnabled()) {
			fRenamePHPElement.run(selection);
			return;
		}
		if (fRenameResource != null && fRenameResource.isEnabled()) {
			if (isFile) // convert selection to be resource
			{
				List arrayList = new ArrayList();
				for (Iterator iter = selection.iterator(); iter.hasNext();) {
					arrayList.add(PHPModelUtil.getResource(iter.next()));
				}
				selection = new StructuredSelection(arrayList);
			}
			fRenameResource.run(selection);
		}
	}

	public void run(ITextSelection selection) {
		if (!ActionUtils.isProcessable(getShell(), fEditor))
			return;
		try {
			if (fRenamePHPElement.canRun())
				fRenamePHPElement.run(selection);
			else
				MessageDialog.openInformation(getShell(), PHPUIMessages.getString("RenameAction_rename"), PHPUIMessages.getString("RenameAction_unavailable"));
		} catch (ModelException e) {
			Logger.logException(e);
		}
	}
}
