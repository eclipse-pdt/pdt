/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.errors;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ErrorReportingTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(ErrorReportingTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTest(PHP5ErrorReportingTests.suite());
		suite.addTest(PHP53ErrorReportingTests.suite());
		suite.addTest(PHP54ErrorReportingTests.suite());
		// $JUnit-END$
		return suite;
	}

}
