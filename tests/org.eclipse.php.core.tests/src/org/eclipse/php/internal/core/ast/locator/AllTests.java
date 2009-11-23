package org.eclipse.php.internal.core.ast.locator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.eclipse.php.internal.core.ast.locator");
		//$JUnit-BEGIN$
		suite.addTestSuite(PhpElementConciliatorTest.class);
		//$JUnit-END$
		return suite;
	}

}
