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
 * Test watcher to print out currently running test group suite name.
 * 
 * @author Bartlomiej Laczkowski
 */
public class TestAllSuiteWatcher extends TestWatcher {

	private static final String SUITE_STARTED = "[TESTS] -> {0}";

	@Override
	protected void starting(Description description) {
		System.out.println(MessageFormat.format(SUITE_STARTED, description.getDisplayName()));
	}

	@Override
	protected void finished(Description description) {
		System.out.println();
	}

}
