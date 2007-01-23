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
package org.eclipse.php.internal.ui.search;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class OpenPHPSearchPageAction implements IWorkbenchWindowActionDelegate {

	private static final String PHP_SEARCH_PAGE_ID = "org.eclipse.php.ui.search.PHPSearchPage"; //$NON-NLS-1$
	private IWorkbenchWindow fWindow;

	public OpenPHPSearchPageAction() {
	}

	public void init(IWorkbenchWindow window) {
		fWindow = window;
	}

	public void run(IAction action) {
		if (fWindow == null || fWindow.getActivePage() == null) {
			beep();
			PHPUiPlugin.logErrorMessage("Could not open the search dialog - for some reason the window handle was null"); //$NON-NLS-1$
			return;
		}
		NewSearchUI.openSearchDialog(fWindow, PHP_SEARCH_PAGE_ID);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing since the action isn't selection dependent.
	}

	public void dispose() {
		fWindow = null;
	}

	protected void beep() {
		Shell shell = PHPUiPlugin.getActiveWorkbenchShell();
		if (shell != null && shell.getDisplay() != null) {
			shell.getDisplay().beep();
		}
	}

}
