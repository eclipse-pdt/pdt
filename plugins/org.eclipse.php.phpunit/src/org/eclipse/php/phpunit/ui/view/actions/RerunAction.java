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

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

/**
 * Requests to rerun a test.
 */
public class RerunAction extends Action {
	private String fClass;
	private String fLaunchMode;
	private int fTestId;
	private PHPUnitView fTestRunner;
	private String fTestName;

	/**
	 * Constructor for RerunAction.
	 */
	@Deprecated
	public RerunAction(final PHPUnitView runner, final int testId, final IType phpClass, final String testName,
			final String launchMode) {
		this(runner, testId, PHPModelUtils.getFullName(phpClass), testName, launchMode);
	}

	/**
	 * Constructor for RerunAction.
	 */
	public RerunAction(final PHPUnitView runner, final int testId, final String phpClass, final String testName,
			final String launchMode) {
		super();
		if (launchMode.equals(ILaunchManager.RUN_MODE))
			setText(PHPUnitMessages.RerunAction_Run);
		else if (launchMode.equals(ILaunchManager.DEBUG_MODE))
			setText(PHPUnitMessages.RerunAction_Debug);
		fTestRunner = runner;
		fTestId = testId;
		fClass = phpClass;
		fLaunchMode = launchMode;
		fTestName = testName;
	}

	@Override
	public void run() {
		fTestRunner.rerunTest(fTestId, fClass, fTestName, fLaunchMode);
	}
}
