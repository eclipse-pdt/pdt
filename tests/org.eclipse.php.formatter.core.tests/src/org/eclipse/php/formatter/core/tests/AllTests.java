/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.formatter.core.tests");

		// $JUnit-BEGIN$

		suite.addTest(FormatterTests.suite());
		suite.addTest(FormatterAutoEditTests.suite());
		suite.addTest(FormatterLinuxAutoEditTests.suite());

		// $JUnit-END$
		return suite;
	}
}
