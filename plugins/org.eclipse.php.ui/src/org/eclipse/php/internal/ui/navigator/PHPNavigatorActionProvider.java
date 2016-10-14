/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
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
import org.eclipse.php.internal.ui.actions.OpenViewActionGroup;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class PHPNavigatorActionProvider extends CommonActionProvider {

	private OpenViewActionGroup fOpenViewGroup;

	private boolean fInViewPart = false;

	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (fInViewPart) {
			fOpenViewGroup.fillActionBars(actionBars);
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (fInViewPart) {
			fOpenViewGroup.fillContextMenu(menu);
		}
	}

	@Override
	public void init(ICommonActionExtensionSite site) {

		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite)
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();

		if (workbenchSite != null) {
			if (workbenchSite.getPart() != null && workbenchSite.getPart() instanceof IViewPart) {
				IViewPart viewPart = (IViewPart) workbenchSite.getPart();

				fOpenViewGroup = new OpenViewActionGroup(viewPart);
				fOpenViewGroup.containsOpenPropertiesAction(false);

				fInViewPart = true;
			}
		}
	}

	@Override
	public void setContext(ActionContext context) {
		super.setContext(context);
		if (fInViewPart) {
			fOpenViewGroup.setContext(context);
		}
	}

	@Override
	public void dispose() {
		if (fInViewPart) {
			fOpenViewGroup.dispose();
		}
		super.dispose();
	}

}
