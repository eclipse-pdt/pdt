/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.internal.ui.scriptview.ScriptExplorerPart;
import org.eclipse.dltk.internal.ui.scriptview.ScriptMessages;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Collapse all nodes.
 */
public class CollapseAllAction extends Action {

	private ScriptExplorerPart fPackageExplorer;

	CollapseAllAction(ScriptExplorerPart part) {
		super(ScriptMessages.CollapseAllAction_label);
		setDescription(ScriptMessages.CollapseAllAction_description);
		setToolTipText(ScriptMessages.CollapseAllAction_tooltip);
		setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));

		fPackageExplorer = part;
		if (DLTKCore.DEBUG) {
			System.err.println("Add help support here..."); //$NON-NLS-1$
		}
	}

	@Override
	public void run() {
		fPackageExplorer.collapseAll();
	}
}