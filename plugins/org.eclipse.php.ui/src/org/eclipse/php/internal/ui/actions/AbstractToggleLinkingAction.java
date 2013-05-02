/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;

public abstract class AbstractToggleLinkingAction extends Action {

	/**
	 * Constructs a new action.
	 */
	public AbstractToggleLinkingAction() {
		super(PHPUIMessages.ToggleLinkingAction_label);
		setDescription(PHPUIMessages.ToggleLinkingAction_description);
		setToolTipText(PHPUIMessages.ToggleLinkingAction_tooltip);
		PHPPluginImages.setLocalImageDescriptors(this, "synced.gif"); 		 //$NON-NLS-1$
		// HELP - PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IPHPHelpContextIds.LINK_EDITOR_ACTION);
	}

	/**
	 * Runs the action.
	 */
	public abstract void run();
}
