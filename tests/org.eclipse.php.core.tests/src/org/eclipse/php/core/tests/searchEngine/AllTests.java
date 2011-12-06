package org.eclipse.php.core.tests.searchEngine;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(PHP5LanguageModelTest.class);
		suite.addTestSuite(PHP53LanguageModelTest.class);
		suite.addTest(SearchFieldTests.suite());
		// $JUnit-END$
		return suite;
	}

}
