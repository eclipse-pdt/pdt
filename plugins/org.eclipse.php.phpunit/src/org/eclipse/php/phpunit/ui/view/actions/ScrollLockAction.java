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

public class ScrollLockAction extends Action {
	private PHPUnitView fRunnerView;

	public ScrollLockAction(final PHPUnitView viewer) {
		super(PHPUnitMessages.ScrollLockAction_Name);
		fRunnerView = viewer;
		setToolTipText(PHPUnitMessages.ScrollLockAction_ToolTip);
		setDisabledImageDescriptor(PHPUnitPlugin.getImageDescriptor("dlcl16/lock.png")); //$NON-NLS-1$
		setHoverImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/lock.png")); //$NON-NLS-1$
		setImageDescriptor(PHPUnitPlugin.getImageDescriptor("elcl16/lock.png")); //$NON-NLS-1$
		setChecked(false);
	}

	@Override
	public void run() {
		fRunnerView.setAutoScroll(!isChecked());
	}

}
