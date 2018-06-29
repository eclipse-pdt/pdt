/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests;

import java.text.MessageFormat;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Test watcher to print out currently running test suite name.
 * 
 * @author Bartlomiej Laczkowski
 */
public class TestSuiteWatcher extends TestWatcher {

	private static final String SUITE_RUNNING = "[TEST] -> {0}";

	private boolean fReported = false;

	@Override
	protected void starting(Description description) {
		if (!fReported) {
			System.out.println(MessageFormat.format(SUITE_RUNNING, description.getDisplayName()));
			fReported = true;
		}
	}

}
