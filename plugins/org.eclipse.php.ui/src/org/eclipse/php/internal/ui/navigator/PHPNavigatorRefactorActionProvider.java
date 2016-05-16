/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies -  initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.php.internal.ui.actions.PHPRefactorActionGroup;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class PHPNavigatorRefactorActionProvider extends CommonActionProvider {

	private PHPRefactorActionGroup fRefactorGroup;

	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (fRefactorGroup != null) {
			fRefactorGroup.fillActionBars(actionBars);
			fRefactorGroup.retargetFileMenuActions(actionBars);
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (fRefactorGroup != null) {
			fRefactorGroup.fillContextMenu(menu);
		}
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite)
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();

		// we only initialize the refactor group when in a view part
		// (required for the constructor)
		if (workbenchSite != null) {
			if (workbenchSite.getPart() != null && workbenchSite.getPart() instanceof IViewPart) {
				IViewPart viewPart = (IViewPart) workbenchSite.getPart();

				fRefactorGroup = new PHPRefactorActionGroup(viewPart);
			}
		}
	}

	@Override
	public void setContext(ActionContext context) {
		if (fRefactorGroup != null) {
			fRefactorGroup.setContext(context);
		}
	}

	/*
	 * @see org.eclipse.ui.actions.ActionGroup#dispose()
	 * 
	 * @since 3.5
	 */
	@Override
	public void dispose() {
		if (fRefactorGroup != null)
			fRefactorGroup.dispose();
		super.dispose();
	}
}
