package org.eclipse.php.core.tests.performance;

import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.osgi.framework.Bundle;

import junit.framework.TestCase;

/**
 * This is an abstract test for .pdtt tests
 * 
 * @author michael
 */
@Deprecated
abstract public class AbstractPDTTTest extends TestCase {

	public AbstractPDTTTest() {
		super();
	}

	public AbstractPDTTTest(String name) {
		super(name);
	}

	protected static String[] getPDTTFiles(String testsDirectory) {
		return PDTTUtils.getPDTTFiles(testsDirectory, PHPCoreTests.getDefault().getBundle());
	}

	protected static String[] getPDTTFiles(String testsDirectory, Bundle bundle) {
		return PDTTUtils.getFiles(testsDirectory, bundle, ".pdtt");
	}

	protected static String[] getFiles(String testsDirectory, String ext) {
		return PDTTUtils.getFiles(testsDirectory, PHPCoreTests.getDefault().getBundle(), ext);
	}

	protected static String[] getFiles(String testsDirectory, Bundle bundle, String ext) {
		return PDTTUtils.getFiles(testsDirectory, bundle, ext);
	}

	protected void assertContents(String expected, String actual) {
		String diff = PHPCoreTests.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}
}
