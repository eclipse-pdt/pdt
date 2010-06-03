/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.ui.actions.SelectionDispatchAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IWorkbenchSite;

public class SelectionDispatchActionDelegate extends SelectionDispatchAction {

	private IPHPActionDelegator action;

	protected SelectionDispatchActionDelegate(IWorkbenchSite site,
			IPHPActionDelegator action) {
		super(site);
		this.action = action;
	}

	/*
	 * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		action.selectionChanged(null, event.getSelection());
	}

	/*
	 * @see SelectionDispatchAction#update(ISelection)
	 */
	public void update(ISelection selection) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.ui.actions.SelectionDispatchAction#run(org.eclipse.jface
	 * .viewers.IStructuredSelection)
	 */
	public void run(IStructuredSelection selection) {
		action.selectionChanged(null, selection);
		action.run(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.ui.actions.SelectionDispatchAction#run(org.eclipse.jface
	 * .text.ITextSelection)
	 */
	public void run(ITextSelection selection) {
		action.selectionChanged(null, selection);
		action.run(null);
	}

}
