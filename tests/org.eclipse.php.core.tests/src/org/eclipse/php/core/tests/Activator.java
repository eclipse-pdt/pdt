package org.eclipse.php.core.tests;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Plugin;
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
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.core.tests";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static InputStream openResource(String path) throws IOException {
		URL url = getDefault().getBundle().getEntry(path);
		return new BufferedInputStream(url.openStream());		
	}
	
	/**
	 * Compares expected result with the actual.
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is equal to the actual.
	 */
	public static String compareContents(String expected, String actual) {
		actual = actual.replaceAll("[\r\n]+", "\n").trim();
		expected = expected.replaceAll("[\r\n]+", "\n").trim();
		
		int expectedDifference = StringUtils.indexOfDifference(actual, expected);
		if (expectedDifference >= 0) {
			int actualDifference = StringUtils.indexOfDifference(expected, actual);

			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED:\n--------------\n");
			errorBuf.append(expected.substring(0, expectedDifference)).append("*****").append(expected.substring(expectedDifference));
			errorBuf.append("\n\nACTUAL:\n--------------\n");
			errorBuf.append(actual.substring(0, actualDifference)).append("*****").append(actual.substring(actualDifference));
			return errorBuf.toString();
		}
		return null;
	}
	
	public static void waitForIndexer(IProject project) throws CoreException {
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
	public static void waitForAutoBuild() {
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
