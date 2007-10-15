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
package org.eclipse.php.internal.ui.workingset;

import org.eclipse.jface.action.Action;
import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.PlatformUI;

/**
 * Clears the selected working set in the action group's view.
 */
public class ClearWorkingSetAction extends Action {

	private WorkingSetFilterActionGroup fActionGroup;

	public ClearWorkingSetAction(WorkingSetFilterActionGroup actionGroup) {
		super(PHPUIMessages.getString("ClearWorkingSetAction_text"));
		Assert.isNotNull(actionGroup);
		setToolTipText(PHPUIMessages.getString("ClearWorkingSetAction_toolTip"));
		setEnabled(actionGroup.getWorkingSet() != null);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.CLEAR_WORKING_SET_ACTION);
		fActionGroup = actionGroup;
	}

	/*
	 * Overrides method from Action
	 */
	public void run() {
		fActionGroup.setWorkingSet(null, true);
	}
}
