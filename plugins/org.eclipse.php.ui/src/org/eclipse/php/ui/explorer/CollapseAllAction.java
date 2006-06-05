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
package org.eclipse.php.ui.explorer;

import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.ui.PlatformUI;

class CollapseAllAction extends Action {

	private ExplorerPart fPHPExplorer;

	CollapseAllAction(ExplorerPart part) {
		super(ExplorerMessages.CollapseAllAction_label);
		setDescription(ExplorerMessages.CollapseAllAction_description);
		setToolTipText(ExplorerMessages.CollapseAllAction_tooltip);
		PHPPluginImages.setLocalImageDescriptors(this, "collapseall.gif"); //$NON-NLS-1$

		fPHPExplorer = part;
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.COLLAPSE_ALL_ACTION);
	}

	public void run() {
		fPHPExplorer.collapseAll();
	}
}
