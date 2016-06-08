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
package org.eclipse.php.core.tests;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.search.indexing.AbstractJob;
import org.eclipse.dltk.core.search.indexing.IndexManager;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.search.ProjectIndexerManager;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class PHPCoreTests extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.core.tests";

	// The shared instance
	private static PHPCoreTests plugin;

	private static final class NoWaitSignalThread extends Thread {

		public NoWaitSignalThread() {
			super("No-Wait-Signal-Thread");
		}

		@Override
		public void run() {
			ModelManager.getModelManager().getIndexManager().waitUntilReady();
		}

	}

	private static final class NoDelayRequest extends AbstractJob {

		private final Thread noWaitSignalThread;
		private final Semaphore waitForIndexerSemaphore;
		private final IndexManager indexManager;

		private NoDelayRequest(Thread noWaitSignalThread, Semaphore waitForIndexerSemaphore) {
			this.waitForIndexerSemaphore = waitForIndexerSemaphore;
			this.noWaitSignalThread = noWaitSignalThread;
			this.indexManager = ModelManager.getModelManager().getIndexManager();
		}

		@Override
		protected void run() throws CoreException, IOException {
			/*
			 * Check if there were some new index requests added to the queue in
			 * the meantime, if so go back to the end of the queue.
			 */
			if (indexManager.awaitingJobsCount() > 1) {
				noWaitSignalThread.interrupt();
				NoWaitSignalThread noWaitSignalThread = new NoWaitSignalThread();
				// Go back to the end of the queue
				indexManager.request(new NoDelayRequest(noWaitSignalThread, waitForIndexerSemaphore));
				noWaitSignalThread.start();
				return;
			}
			// Interrupt "wait for indexer" thread (no sleeping dude...).
			noWaitSignalThread.interrupt();
			/*
			 * Requests queue is empty, we can assume that indexer has finished
			 * so release semaphore to move on with processing.
			 */
			waitForIndexerSemaphore.release();
		}

		@Override
		protected String getName() {
			return "WAIT-UNTIL-READY-NO-DELAY-JOB";
		}
	}

	/**
	 * The constructor
	 */
	public PHPCoreTests() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		PHPCorePlugin.toolkitInitialized = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
	public static PHPCoreTests getDefault() {
		return plugin;
	}

	private static String getDiffError(String expected, String actual, int expectedDiff, int actualDiff) {
		StringBuilder errorBuf = new StringBuilder();
		errorBuf.append("\nEXPECTED:\n--------------\n");
		errorBuf.append(expected.substring(0, expectedDiff)).append("*****").append(expected.substring(expectedDiff));
		errorBuf.append("\n\nACTUAL:\n--------------\n");
		errorBuf.append(actual.substring(0, actualDiff)).append("*****").append(actual.substring(actualDiff));
		return errorBuf.toString();
	}

	/**
	 * Compares expected result with the actual.
	 * 
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is
	 *         equal to the actual.
	 */
	public static String compareContents(String expected, String actual) {
		actual = actual.replaceAll("[\r\n]+", "\n").trim();
		expected = expected.replaceAll("[\r\n]+", "\n").trim();

		int expectedDiff = StringUtils.indexOfDifference(actual, expected);
		if (expectedDiff >= 0) {
			int actualDiff = StringUtils.indexOfDifference(expected, actual);
			return getDiffError(expected, actual, expectedDiff, actualDiff);
		}
		return null;
	}

	/**
	 * Compares expected result with the actual ingoring whitespace characters
	 * 
	 * @param expected
	 * @param actual
	 * @return difference string or <code>null</code> in case expected result is
	 *         equal to the actual.
	 */
	public static String compareContentsIgnoreWhitespace(String expected, String actual) {
		String tmpExpected = expected;
		String tmpActual = actual;
		String diff = StringUtils.difference(tmpExpected, tmpActual);
		while (diff.length() > 0) {
			String diff2 = StringUtils.difference(tmpActual, tmpExpected);

			if (!Character.isWhitespace(diff.charAt(0)) && !Character.isWhitespace(diff2.charAt(0))) {
				int expectedDiff = StringUtils.indexOfDifference(tmpActual, tmpExpected)
						+ (expected.length() - tmpExpected.length());
				int actualDiff = StringUtils.indexOfDifference(tmpExpected, tmpActual)
						+ (actual.length() - tmpActual.length());
				return getDiffError(expected, actual, expectedDiff, actualDiff);
			}

			tmpActual = diff.trim();
			tmpExpected = diff2.trim();

			diff = StringUtils.difference(tmpExpected, tmpActual);
		}
		return null;
	}

	public static synchronized void waitForIndexer() {
		final IndexManager indexManager = ModelManager.getModelManager().getIndexManager();
		final Semaphore waitForIndexerSemaphore = new Semaphore(0);
		final Thread noWaitSignalThread = new NoWaitSignalThread();
		indexManager.request(new NoDelayRequest(noWaitSignalThread, waitForIndexerSemaphore));
		noWaitSignalThread.start();
		// Wait for indexer requests to be finished
		waitForIndexerSemaphore.acquireUninterruptibly();
	}

	/**
	 * Wait for auto-build notification to occur, that is for the auto-build to
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

	/**
	 * Set project PHP version
	 * 
	 * @param project
	 * @param phpVersion
	 * @throws CoreException
	 */
	public static void setProjectPhpVersion(IProject project, PHPVersion phpVersion) throws CoreException {
		if (phpVersion != ProjectOptions.getPhpVersion(project)) {
			ProjectOptions.setPhpVersion(phpVersion, project);
			waitForIndexer();
		}
	}

	/**
	 * Force index
	 * 
	 * @param resource
	 */
	public static void index(IResource resource) {
		if (resource.getType() == IResource.PROJECT) {
			ProjectIndexerManager.indexProject((IProject) resource);
		} else if (resource.getType() == IResource.FILE) {
			IModelElement module = DLTKCore.create((IFile) resource);
			if (module instanceof ISourceModule) {
				ProjectIndexerManager.indexSourceModule((ISourceModule) module, PHPLanguageToolkit.getDefault());
			}
		}
	}

	/**
	 * Force removal from index IProject or IResource
	 * 
	 * @param resource
	 */
	public static void removeIndex(IResource resource) {
		if (resource.getType() == IResource.PROJECT) {
			ProjectIndexerManager.removeProject(resource.getFullPath());
		} else if (resource.getType() == IResource.FILE) {
			ProjectIndexerManager.removeSourceModule(DLTKCore.create(resource.getProject()),
					resource.getProjectRelativePath().toString());
		}
	}
}
