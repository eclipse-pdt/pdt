/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.indexing.IProjectIndexer;
import org.eclipse.dltk.core.search.indexing.IndexManager;
import org.eclipse.dltk.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.search.ProjectIndexerManager;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.validation.ValidationFramework;

/**
 * Utility & support class for all PHP tests.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("all")
public final class TestUtils {
	private static IProjectIndexer indexer;

	public static enum ColliderType {

		AUTO_BUILD, WTP_VALIDATION, LIBRARY_AUTO_DETECTION, ALL;

	}

	public static void waitForIndexer() {
		final IndexManager indexManager = ModelManager.getModelManager().getIndexManager();
		indexManager.waitUntilReady();
	}

	/**
	 * Wait for auto-build notification to occur, that is for the auto-build to
	 * finish.
	 */
	public synchronized static void waitForAutoBuild() {
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
	public static void setProjectPHPVersion(IProject project, PHPVersion phpVersion) throws CoreException {
		if (phpVersion != ProjectOptions.getPHPVersion(project)) {
			ProjectOptions.setPHPVersion(phpVersion, project);
		}
	}

	/**
	 * Set project PHP version
	 * 
	 * @param project
	 * @param phpVersion
	 * @param useASPTags
	 * @param useShortTags
	 * @throws CoreException
	 */
	public static void setProjectPHPVersion(IProject project, PHPVersion phpVersion, boolean useASPTags,
			boolean useShortTags) throws CoreException {
		if (phpVersion != ProjectOptions.getPHPVersion(project)
				|| useASPTags != ProjectOptions.isSupportingASPTags(project)
				|| useShortTags != ProjectOptions.useShortTags(project)) {
			ProjectOptions.setPHPVersion(phpVersion, project);
			ProjectOptions.setSupportingASPTags(useASPTags, project);
			ProjectOptions.setUseShortTags(useShortTags, project);
		}
	}

	/**
	 * Creates new PHP project.
	 * 
	 * @param projectName
	 * @return new PHP project
	 */
	public static IProject createProject(String projectName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			project.create(null);
			project.open(null);
			// configure nature
			IProjectDescription desc = project.getDescription();
			desc.setNatureIds(new String[] { PHPNature.ID });
			project.setDescription(desc, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return project;
	}

	/**
	 * Creates new folder under given project.
	 * 
	 * @param project
	 * @param folderName
	 * @return created folder
	 */
	public static IFolder createFolder(IProject project, String folderName) {
		IFolder folder = project.getFolder(folderName);
		try {
			folder.create(true, true, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return folder;
	}

	/**
	 * Creates new file in related project.
	 * 
	 * @param project
	 * @param fileName
	 * @param fileContent
	 * @return new file
	 */
	public static IFile createFile(IProject project, String fileName, String fileContent) {
		IFile file = project.getFile(fileName);
		try {
			file.create(new ByteArrayInputStream(fileContent.getBytes()), true, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return file;
	}

	/**
	 * Creates new file in related folder.
	 * 
	 * @param folder
	 * @param fileName
	 * @param fileContent
	 * @return new file
	 */
	public static IFile createFile(IFolder folder, String fileName, String fileContent) {
		IFile file = folder.getFile(fileName);
		try {
			file.create(new ByteArrayInputStream(fileContent.getBytes()), true, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return file;
	}

	/**
	 * Deletes project.
	 * 
	 * @param project
	 */
	public static void deleteProject(IProject project) {
		try {
			project.close(null);
			project.delete(true, true, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/**
	 * Deletes file.
	 * 
	 * @param file
	 */
	public static void deleteFile(IFile file) {
		try {
			file.delete(true, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/**
	 * Enables chosen features that might interfere performing tests.
	 * 
	 * @param collider
	 */
	public static void enableColliders(ColliderType collider) {
		switch (collider) {
		case AUTO_BUILD: {
			enableAutoBuild();
			break;
		}
		case WTP_VALIDATION: {
			enableValidation();
			break;
		}
		case LIBRARY_AUTO_DETECTION: {
			enableLibraryDetection();
			break;
		}
		case ALL: {
			enableAutoBuild();
			enableValidation();
			enableLibraryDetection();
			break;
		}
		default:
			break;
		}
	}

	/**
	 * Disables chosen features that might interfere performing tests.
	 * 
	 * @param collider
	 */
	public static void disableColliders(ColliderType collider) {
		switch (collider) {
		case AUTO_BUILD: {
			disableAutoBuild();
			break;
		}
		case WTP_VALIDATION: {
			disableValidation();
			break;
		}
		case LIBRARY_AUTO_DETECTION: {
			disableLibraryDetection();
			break;
		}
		case ALL: {
			disableAutoBuild();
			disableValidation();
			disableLibraryDetection();
			break;
		}
		default:
			break;
		}
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

	private static String getDiffError(String expected, String actual, int expectedDiff, int actualDiff) {
		StringBuilder errorBuf = new StringBuilder();
		errorBuf.append("\nEXPECTED:\n--------------\n");
		errorBuf.append(expected.substring(0, expectedDiff)).append("*****").append(expected.substring(expectedDiff));
		errorBuf.append("\n\nACTUAL:\n--------------\n");
		errorBuf.append(actual.substring(0, actualDiff)).append("*****").append(actual.substring(actualDiff));
		return errorBuf.toString();
	}

	private static void enableAutoBuild() {
		if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
			IWorkspaceDescription workspaceDescription = ResourcesPlugin.getWorkspace().getDescription();
			workspaceDescription.setAutoBuilding(true);
			try {
				ResourcesPlugin.getWorkspace().setDescription(workspaceDescription);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	private static void enableValidation() {
		ValidationFramework.getDefault().suspendAllValidation(false);
	}

	private static void enableLibraryDetection() {
		LibraryFolderManager.getInstance().suspendAllDetection(false);
	}

	private static void disableAutoBuild() {
		if (ResourcesPlugin.getWorkspace().isAutoBuilding()) {
			IWorkspaceDescription workspaceDescription = ResourcesPlugin.getWorkspace().getDescription();
			workspaceDescription.setAutoBuilding(false);
			try {
				ResourcesPlugin.getWorkspace().setDescription(workspaceDescription);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	private static void disableValidation() {
		ValidationFramework.getDefault().suspendAllValidation(true);
	}

	private static void disableLibraryDetection() {
		LibraryFolderManager.getInstance().suspendAllDetection(true);
	}

	public static void indexFile(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		/*
		 * try { sourceModule.reconcile(true, DefaultWorkingCopyOwner.PRIMARY,
		 * null); } catch (ModelException e) { }
		 */
		ProjectIndexerManager.indexSourceModule(sourceModule, PHPLanguageToolkit.getDefault());
	}

	public static void reconcileFile(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		try {
			sourceModule.reconcile(true, DefaultWorkingCopyOwner.PRIMARY, null);
		} catch (ModelException e) {
		}
		ProjectIndexerManager.reconciled(sourceModule);
	}

}
