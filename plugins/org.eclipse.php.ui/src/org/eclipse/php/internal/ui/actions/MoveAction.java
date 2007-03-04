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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;


public class MoveAction extends SelectionDispatchAction implements IPHPActionImplementor {

	private static final String EXTENSION_POINT = "org.eclipse.php.ui.phpActionImplementor"; //$NON-NLS-1$
	private static final String ACTION_ID_ATTRIBUTE = "actionId"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PRIORITY_ATTRIBUTE = "priority"; //$NON-NLS-1$
	
	
	private static final String MOVE_ACTION_ID = "org.eclipse.php.ui.actions.Move"; //$NON-NLS-1$
	
	private PHPStructuredEditor fEditor;
	private SelectionDispatchAction fReorgMoveAction;
	

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
		setText(PHPUIMessages.MoveAction_text);		
		instantiateActionFromExtentionPoint();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.MOVE_ACTION);
	}


	/*
	 * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		fReorgMoveAction.selectionChanged(event);
		setEnabled(computeEnableState());
	}

	public void run(IStructuredSelection selection) {
		if (fReorgMoveAction.isEnabled())
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
		StructuredSelection mockStructuredSelection = new StructuredSelection(element);
		fReorgMoveAction.selectionChanged(mockStructuredSelection);
		if (!fReorgMoveAction.isEnabled())
			return false;

		fReorgMoveAction.run(mockStructuredSelection);
		return true;
	}

	/*
	 * @see SelectionDispatchAction#update(ISelection)
	 */
	public void update(ISelection selection) {
		if(fReorgMoveAction != null){
			fReorgMoveAction.update(selection);		
			setEnabled(computeEnableState());
		}
	}

	private boolean computeEnableState() {
		return fReorgMoveAction.isEnabled();
	}
	
	/**
	 * Gets the relevant reorg move actions from the extention point
	 * The final action will be the one with the highest priority
	 */
	public void instantiateActionFromExtentionPoint(){		
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT); //$NON-NLS-1$
		int topPriority = 0;
		
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (MOVE_ACTION_ID.equals(element.getAttribute(ACTION_ID_ATTRIBUTE))) {
				int currentPriority = Integer.valueOf(element.getAttribute(PRIORITY_ATTRIBUTE)).intValue();
				// the final action should be the one with the highest priority 
				if(currentPriority > topPriority){
					try {
						fReorgMoveAction = (SelectionDispatchAction) element.createExecutableExtension(CLASS_ATTRIBUTE);
						topPriority = currentPriority;
					} catch (CoreException e) {
						Logger.logException("Failed instantiating Move action class " + element.getAttribute(ACTION_ID_ATTRIBUTE), e);
					}
				}
			}
		}
	}
}
