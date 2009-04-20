package org.eclipse.php.core.tests;

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

/**
 * This is an abstract test for .pdtt tests
 * @author michael
 */
public class AbstractPDTTTest extends TestCase {
	
	public AbstractPDTTTest() {
		super();
	}

	public AbstractPDTTTest(String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	protected static String[] getPDTTFiles(String testsDirectory) {
		List<String> files = new LinkedList<String>();
		Enumeration<String> entryPaths = Activator.getDefault().getBundle().getEntryPaths(testsDirectory);
		if (entryPaths != null) {
			while (entryPaths.hasMoreElements()) {
				final String path = (String) entryPaths.nextElement();
				URL entry = Activator.getDefault().getBundle().getEntry(path);
				// check whether the file is readable:
				try {
					entry.openStream().close();
				} catch (Exception e) {
					continue;
				}
				int pos = path.lastIndexOf('/');
				final String name = (pos >= 0 ? path.substring(pos + 1) : path);
				if (!name.endsWith(".pdtt")) { // check fhe file extention
					continue;
				}
				files.add(path);
			}
		}
		return (String[]) files.toArray(new String[files.size()]);
	}

	protected void assertContents(String expected, String actual) {
		String diff = Activator.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}
}
