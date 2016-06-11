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
package org.eclipse.php.core.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.php.core.tests.performance.zf.ZFPerformanceTests;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Core Performance Tests");

		// $JUnit-BEGIN$
		suite.addTest(ZFPerformanceTests.suite());
		// $JUnit-END$

		return suite;
	}
}
