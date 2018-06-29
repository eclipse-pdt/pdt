/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view.actions;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.action.Action;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

/**
 * Requests to rerun a test.
 */
public class RerunAction extends Action {
	private String fLaunchMode;
	private int fTestId;
	private PHPUnitView fTestRunner;

	/**
	 * Constructor for RerunAction.
	 */
	public RerunAction(final PHPUnitView runner, final int testId, final String launchMode) {
		super();
		if (launchMode.equals(ILaunchManager.RUN_MODE)) {
			setText(PHPUnitMessages.RerunAction_Run);
		} else if (launchMode.equals(ILaunchManager.DEBUG_MODE)) {
			setText(PHPUnitMessages.RerunAction_Debug);
		}
		fTestRunner = runner;
		fTestId = testId;
		fLaunchMode = launchMode;
	}

	@Override
	public void run() {
		fTestRunner.rerunTest(fTestId, fLaunchMode);
	}
}
