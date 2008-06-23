/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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

/**
 * This action toggles whether this php explorer links its selection to the active
 * editor.
 * 
 * @since 2.1
 */
public class ToggleLinkingAction extends Action {

	ProjectOutlinePart fPart;

	/**
	 * Constructs a new action.
	 */
	public ToggleLinkingAction(ProjectOutlinePart part) {
		super(PHPUIMessages.getString("ToggleLinkingAction_label"));
		setDescription(PHPUIMessages.getString("ToggleLinkingAction_description"));
		setToolTipText(PHPUIMessages.getString("ToggleLinkingAction_tooltip"));
		PHPPluginImages.setLocalImageDescriptors(this, "synced.gif"); //$NON-NLS-1$		
		// HELP - PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.LINK_EDITOR_ACTION);

		setChecked(part.isLinkingEnabled());
		fPart = part;
	}

	/**
	 * Runs the action.
	 */
	public void run() {
		fPart.setLinkingEnabled(isChecked());
	}

}
