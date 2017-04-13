/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

public class ShowPreviousFailureAction extends Action {

	private PHPUnitView fView;

	public ShowPreviousFailureAction(final PHPUnitView view) {
		super(PHPUnitMessages.ShowPreviousFailureAction_Name);
		setDisabledImageDescriptor(PHPUnitPlugin.getImageDescriptor("dlcl16/select_prev.png")); //$NON-NLS-1$
		setHoverImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/select_prev.png")); //$NON-NLS-1$
		setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/select_prev.png")); //$NON-NLS-1$
		setToolTipText(PHPUnitMessages.ShowPreviousFailureAction_ToolTip);
		fView = view;
	}

	@Override
	public void run() {
		fView.selectPreviousFailure();
	}

}
