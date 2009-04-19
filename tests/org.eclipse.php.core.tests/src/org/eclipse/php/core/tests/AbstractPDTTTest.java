package org.eclipse.php.core.tests;

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

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
		actual = actual.trim();
		expected = expected.trim();
		
		int expectedDifference = StringUtils.indexOfDifference(actual, expected);
		if (expectedDifference >= 0) {
			int actualDifference = StringUtils.indexOfDifference(expected, actual);

			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED:\n--------------\n");
			errorBuf.append(expected.substring(0, expectedDifference)).append("*****").append(expected.substring(expectedDifference));
			errorBuf.append("\n\nACTUAL:\n--------------\n");
			errorBuf.append(actual.substring(0, actualDifference)).append("*****").append(actual.substring(actualDifference));
			fail(errorBuf.toString());
		}
	}

	protected static void waitForIndexer(IProject project) throws CoreException {
		SearchEngine searchEngine = new SearchEngine();
		IDLTKSearchScope scope = PHPModelUtils.createProjectSearchScope(DLTKCore.create(project));
		SearchPattern pattern = SearchPattern.createPattern("*", IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
		searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
			}
		}, null);
	}

	/**
	 * Wait for autobuild notification to occur, that is for the autbuild to
	 * finish.
	 */
	protected static void waitForAutoBuild() {
		boolean wasInterrupted = false;
		do {
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				wasInterrupted = false;
			} catch (OperationCanceledException e) {
				throw (e);
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted);
	}
}
