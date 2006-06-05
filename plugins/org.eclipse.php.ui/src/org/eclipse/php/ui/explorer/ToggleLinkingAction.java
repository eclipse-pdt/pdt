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
import org.eclipse.php.internal.ui.actions.ActionMessages;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.ui.PlatformUI;

/**
 * This action toggles whether this php explorer links its selection to the active
 * editor.
 * 
 * @since 2.1
 */
public class ToggleLinkingAction extends Action {

	ExplorerPart fExplorerPart;

	/**
	 * Constructs a new action.
	 */
	public ToggleLinkingAction(ExplorerPart explorer) {
		super(ActionMessages.ToggleLinkingAction_label);
		setDescription(ActionMessages.ToggleLinkingAction_description);
		setToolTipText(ActionMessages.ToggleLinkingAction_tooltip);
		PHPPluginImages.setLocalImageDescriptors(this, "synced.gif"); //$NON-NLS-1$		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.LINK_EDITOR_ACTION);

		setChecked(explorer.isLinkingEnabled());
		fExplorerPart = explorer;
	}

	/**
	 * Runs the action.
	 */
	public void run() {
		fExplorerPart.setLinkingEnabled(isChecked());
	}

}
