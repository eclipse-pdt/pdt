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
