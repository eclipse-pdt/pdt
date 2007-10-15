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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractToggleLinkingAction extends Action {

	/**
	 * Constructs a new action.
	 */
	public AbstractToggleLinkingAction() {
		super(PHPUIMessages.getString("ToggleLinkingAction_label"));
		setDescription(PHPUIMessages.getString("ToggleLinkingAction_description"));
		setToolTipText(PHPUIMessages.getString("ToggleLinkingAction_tooltip"));
		PHPPluginImages.setLocalImageDescriptors(this, "synced.gif"); //$NON-NLS-1$		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.LINK_EDITOR_ACTION);
	}

	/**
	 * Runs the action.
	 */
	public abstract void run();
}
