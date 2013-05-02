/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.internal.ui.scriptview.ScriptMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;

/**
 * Collapse all nodes.
 */
public class CollapseAllAction extends Action {

	private ScriptExplorerPart fPackageExplorer;

	CollapseAllAction(ScriptExplorerPart part) {
		super(ScriptMessages.CollapseAllAction_label);
		setDescription(ScriptMessages.CollapseAllAction_description);
		setToolTipText(ScriptMessages.CollapseAllAction_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(this, "collapseall.gif"); //$NON-NLS-1$ 

		fPackageExplorer = part;
		if (DLTKCore.DEBUG) {
			System.err.println("Add help support here..."); //$NON-NLS-1$ 
		}

		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IScriptHelpContextIds.COLLAPSE_ALL_ACTION);
	}

	public void run() {
		fPackageExplorer.collapseAll();
	}
}
