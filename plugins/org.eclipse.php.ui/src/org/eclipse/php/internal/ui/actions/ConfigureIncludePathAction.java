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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.treecontent.PHPTreeNode;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ConfigureIncludePathAction extends SelectionDispatchAction {

	public static final String ID_INCLUDES_NODE = "org.eclipse.php.ui.treecontent.IncludesNode";									   
	private static final String INCLUDEPATH_PREFERENCES_PAGE_ID = "org.eclipse.php.ui.propertyPages.IncludePathPropertyPage";
	
	private IProject project;

	/**
	 * Creates a new <code>OpenAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public ConfigureIncludePathAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.ConfigureIncludePathAction_label);
		setToolTipText(PHPUIMessages.ConfigureIncludePathAction_tooltip);
		setDescription(PHPUIMessages.ConfigureIncludePathAction_description);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.OPEN_ACTION);
	}


	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(ITextSelection selection) {
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(checkEnabled(selection));
	}

	private boolean checkEnabled(IStructuredSelection selection) {
		if (selection.isEmpty())
			return false;

		Object firstElement= selection.getFirstElement();
		project= getProjectFromSelectedElement(firstElement);
		
		return project != null;
		
	}

	private IProject getProjectFromSelectedElement(Object element) {
		
		// project
		if (element instanceof IProject){			
			return (IProject) element;
		}
		// file
		if (element instanceof PHPCodeData){
			IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();			
			String fileName = ((PHPCodeData) element).getUserData().getFileName();
			IFile file = wsRoot.getFile(new Path(fileName));
			return file.getProject();			
		}
		
		// library node
		if ((element instanceof PHPTreeNode) && ID_INCLUDES_NODE.equals(((PHPTreeNode) element).getId())) {
			PHPTreeNode treeNode = (PHPTreeNode) element;
			return (IProject) treeNode.getData();
		} 
		return null;
	}


	

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void run(IStructuredSelection selection) {
		if (project != null) {
			PreferencesUtil.createPropertyDialogOn(getShell(), project, INCLUDEPATH_PREFERENCES_PAGE_ID,null, null).open();
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

	
}
