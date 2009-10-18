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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.wst.jsdt.ui.actions.ConvertingSelectionProvider;

public abstract class SelectionDispatchAction extends Action implements
		ISelectionChangedListener {

	private IWorkbenchSite fSite;
	private ISelectionProvider fSpecialSelectionProvider;

	/**
	 * Creates a new action with no text and no image.
	 * <p>
	 * Configure the action later using the set methods.
	 * </p>
	 * 
	 * @param site
	 *            the site this action is working on
	 */
	protected SelectionDispatchAction(IWorkbenchSite site) {
		Assert.isNotNull(site);
		fSite = site;
	}

	/**
	 * Returns the site owning this action.
	 * 
	 * @return the site owning this action
	 */
	public IWorkbenchSite getSite() {
		return fSite;
	}

	/**
	 * Returns the selection provided by the site owning this action.
	 * 
	 * @return the site's selection
	 */
	public ISelection getSelection() {
		if (getSelectionProvider() != null)
			return getSelectionProvider().getSelection();
		else
			return null;
	}

	/**
	 * Returns the shell provided by the site owning this action.
	 * 
	 * @return the site's shell
	 */
	public Shell getShell() {
		return fSite.getShell();
	}

	/**
	 * Returns the selection provider managed by the site owning this action.
	 * 
	 * @return the site's selection provider
	 */
	public ISelectionProvider getSelectionProvider() {
		if (fSpecialSelectionProvider != null) {
			return fSpecialSelectionProvider;
		}
		return fSite.getSelectionProvider();
	}

	/**
	 * Updates the action's enablement state according to the given selection.
	 * This default implementation calls one of the
	 * <code>selectionChanged</code> methods depending on the type of the passed
	 * selection.
	 * 
	 * @param selection
	 *            the selection this action is working on
	 */
	public void update(ISelection selection) {
		dispatchSelectionChanged(selection);
	}

	/**
	 * Notifies this action that the given structured selection has changed.
	 * This default implementation calls
	 * <code>selectionChanged(ISelection selection)</code>.
	 * 
	 * @param selection
	 *            the new selection
	 */
	public void selectionChanged(IStructuredSelection selection) {
		selectionChanged((ISelection) selection);
	}

	/**
	 * Executes this actions with the given structured selection. This default
	 * implementation calls <code>run(ISelection selection)</code>.
	 * 
	 * @param selection
	 *            the selection
	 */
	public void run(IStructuredSelection selection) {
		run((ISelection) selection);
	}

	/**
	 * Notifies this action that the given text selection has changed. This
	 * default implementation calls
	 * <code>selectionChanged(ISelection selection)</code>.
	 * 
	 * @param selection
	 *            the new selection
	 */
	public void selectionChanged(ITextSelection selection) {
		selectionChanged((ISelection) selection);
	}

	/**
	 * Executes this actions with the given text selection. This default
	 * implementation calls <code>run(ISelection selection)</code>.
	 * 
	 * @param selection
	 *            the selection
	 */
	public void run(ITextSelection selection) {
		run((ISelection) selection);
	}

	/**
	 * Notifies this action that the given selection has changed. This default
	 * implementation sets the action's enablement state to <code>false</code>.
	 * 
	 * @param selection
	 *            the new selection
	 */
	public void selectionChanged(ISelection selection) {
		setEnabled(false);
	}

	/**
	 * Executes this actions with the given selection. This default
	 * implementation does nothing.
	 * 
	 * @param selection
	 *            the selection
	 */
	public void run(ISelection selection) {
	}

	/*
	 * (non-Javadoc) Method declared on IAction.
	 */
	public void run() {
		dispatchRun(getSelection());
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionChangedListener.
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		dispatchSelectionChanged(event.getSelection());
	}

	private void dispatchSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			selectionChanged((IStructuredSelection) selection);
		} else if (selection instanceof ITextSelection) {
			selectionChanged((ITextSelection) selection);
		} else {
			selectionChanged(selection);
		}
	}

	private void dispatchRun(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			run((IStructuredSelection) selection);
		} else if (selection instanceof ITextSelection) {
			run((ITextSelection) selection);
		} else {
			run(selection);
		}
	}

	/**
	 * Sets a special selection provider which will be used instead of the
	 * site's selection provider. This method should be used directly after
	 * constructing the action and before the action is registered as a
	 * selection listener. The invocation will not a perform a selection change
	 * notification.
	 * 
	 * @param provider
	 *            a special selection provider which is used instead of the
	 *            site's selection provider or <code>null</code> to use the
	 *            site's selection provider. Clients can for example use a
	 *            {@link ConvertingSelectionProvider} to first convert a
	 *            selection before passing it to the action.
	 * 
	 * @since 3.2
	 */
	public void setSpecialSelectionProvider(ISelectionProvider provider) {
		fSpecialSelectionProvider = provider;
	}

}