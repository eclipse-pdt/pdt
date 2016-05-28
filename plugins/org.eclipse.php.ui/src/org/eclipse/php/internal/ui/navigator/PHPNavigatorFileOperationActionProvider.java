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
import org.eclipse.php.internal.ui.actions.PHPFileOperationActionGroup;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class PHPNavigatorFileOperationActionProvider extends CommonActionProvider {
	private PHPFileOperationActionGroup fFileOperationGroup;

	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (fFileOperationGroup != null) {
			fFileOperationGroup.fillActionBars(actionBars);
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (fFileOperationGroup != null) {
			fFileOperationGroup.fillContextMenu(menu);
		}
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite)
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();

		// we only initialize the group when in a view part
		// (required for the constructor)
		if (workbenchSite != null) {
			IWorkbenchPart viewPart = workbenchSite.getPart();
			if (viewPart instanceof IViewPart) {
				fFileOperationGroup = new PHPFileOperationActionGroup((IViewPart) viewPart);
			}
		}
	}

	@Override
	public void setContext(ActionContext context) {
		if (fFileOperationGroup != null) {
			fFileOperationGroup.setContext(context);
		}
	}

	@Override
	public void dispose() {
		if (fFileOperationGroup != null)
			fFileOperationGroup.dispose();
		super.dispose();
	}
}
