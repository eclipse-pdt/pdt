/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		// Core tests
		org.eclipse.php.core.tests.AllTests.class,
		// Formatter tests
		org.eclipse.php.formatter.core.tests.AllTests.class,
		// Refactoring tests
		org.eclipse.php.refactoring.core.test.AllTests.class,
		// UI tests
		org.eclipse.php.ui.tests.AllTests.class })

public final class AllTests {
}
