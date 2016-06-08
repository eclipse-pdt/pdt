/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	@Override
	protected void starting(Description description) {
		System.out.println(MessageFormat.format(SUITE_RUNNING, description.getDisplayName()));
	}

}
