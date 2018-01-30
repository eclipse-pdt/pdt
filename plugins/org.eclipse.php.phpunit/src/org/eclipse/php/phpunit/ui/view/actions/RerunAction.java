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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.action.Action;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;

/**
 * Requests to rerun a test.
 */
public class RerunAction extends Action {
	private List<String> fFilters;
	private List<String> fSuites;
	private String fLaunchMode;
	private int fTestId;
	private PHPUnitView fTestRunner;

	/**
	 * Constructor for RerunAction.
	 */
	public RerunAction(final PHPUnitView runner, final int testId, final String testSuite, final String filter,
			final String launchMode) {
		this(runner, testId, testSuite == null ? Collections.emptyList() : Arrays.asList(new String[] { testSuite }),
				filter == null ? Collections.emptyList() : Arrays.asList(new String[] { filter }), launchMode);
	}

	/**
	 * Constructor for RerunAction.
	 */
	public RerunAction(final PHPUnitView runner, final int testId, final List<String> suites,
			final List<String> filters, final String launchMode) {
		super();
		if (launchMode.equals(ILaunchManager.RUN_MODE))
			setText(PHPUnitMessages.RerunAction_Run);
		else if (launchMode.equals(ILaunchManager.DEBUG_MODE))
			setText(PHPUnitMessages.RerunAction_Debug);
		fTestRunner = runner;
		fTestId = testId;
		fLaunchMode = launchMode;
		fFilters = filters;
		fSuites = suites;
	}

	@Override
	public void run() {
		fTestRunner.rerunTest(fTestId, fSuites, fFilters, null, fLaunchMode);
	}
}
