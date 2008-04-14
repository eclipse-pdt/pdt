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
package org.eclipse.php.internal.ui.projectOutline;

import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;

class CollapseAllAction extends Action {

	private ProjectOutlinePart fOutlinePart;

	CollapseAllAction(ProjectOutlinePart part) {
		super(PHPUIMessages.getString("CollapseAllAction_label"));
		setDescription(PHPUIMessages.getString("CollapseAllAction_description"));
		setToolTipText(PHPUIMessages.getString("CollapseAllAction_tooltip"));
		PHPPluginImages.setLocalImageDescriptors(this, "collapseall.gif"); //$NON-NLS-1$

		fOutlinePart = part;
		// HELP - PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.COLLAPSE_ALL_ACTION);
	}

	public void run() {
		fOutlinePart.collapseAll();
	}
}
