/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.refactoring.core.test");
		//$JUnit-BEGIN$
		suite.addTest(org.eclipse.php.refactoring.core.rename.AllTests.suite());
		suite.addTest(org.eclipse.php.refactoring.core.move.AllTests.suite());
		suite.addTest(org.eclipse.php.refactoring.core.extract.function.AllTests.suite());
		suite.addTest(org.eclipse.php.refactoring.core.extract.variable.AllTests.suite());
		suite.addTest(org.eclipse.php.refactoring.ui.actions.AllTests.suite());
		suite.addTest(org.eclipse.php.refactoring.core.changes.AllTests.suite());
		//$JUnit-END$
		return suite;
	}

}
