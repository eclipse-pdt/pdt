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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;

public class FileUtils {
	/**
	 * Fetch the entire contents of a text file, and return it in a String. This
	 * style of implementation does not throw Exceptions to the caller.
	 * 
	 * @param file
	 *            is a file which already exists and can be read.
	 */
	static public String getContents(IFile file) throws IOException {
		StringBuilder contents = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		BufferedReader input = null;
		try {
			// FileReader always assumes default encoding is OK!
			input = new BufferedReader(new InputStreamReader(file.getContents(true)));
			String line = null;

			while ((line = input.readLine()) != null) {
				contents.append(line);
				contents.append(newLine);
			}

		} catch (CoreException e) {

		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {

			}
		}
		if (contents.length() > newLine.length()) {
			// remove the last line separator.
			return contents.substring(0, contents.length() - newLine.length());
		}

		return contents.toString();

	}

	// static public IProject createProject(String name) {
	// IProject project =
	// ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	// try {
	// if (!project.exists()) {
	// project.create(null);
	// project.open(null);
	// IProjectDescription desc = project.getDescription();
	// desc.setNatureIds(new String[] { PHPNature.ID });
	// project.setDescription(desc, null);
	// }
	// } catch (CoreException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// TestUtils.waitForIndexer();
	// return project;
	// }
	//
	// static public IProject createProject(String name, PHPVersion version) {
	// IProject project =
	// ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	// try {
	// if (!project.exists()) {
	// project.create(null);
	// project.open(null);
	// IProjectDescription desc = project.getDescription();
	// desc.setNatureIds(new String[] { PHPNature.ID });
	// project.setDescription(desc, null);
	// ProjectOptions.setPhpVersion(version, project);
	// }
	// } catch (CoreException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// TestUtils.waitForIndexer();
	// return project;
	// }

	public static boolean isInBuildpath(IPath resourcePath, IScriptProject project, int entryKind) {
		boolean result = false;
		if (resourcePath == null) {
			return false;
		}

		IBuildpathEntry[] buildpath = null;
		try {
			buildpath = project.getRawBuildpath();
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			return false;
		}

		// go over the build path entries and for each one of the "sources"
		// check if they are the same as the given include path entry
		for (IBuildpathEntry buildpathEntry : buildpath) {
			if (buildpathEntry.getEntryKind() == entryKind) {
				IPath buildPathEntryPath = buildpathEntry.getPath();
				if (resourcePath.toString().equals(buildPathEntryPath.toString())) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * Removes the given entry from the build path (according to the path)
	 * 
	 * @param entries
	 * @param buildpathEntry
	 * @return
	 * @throws ModelException
	 */
	public static IBuildpathEntry[] removeEntryFromBuildPath(IBuildpathEntry[] entries, IPath buidlEntryPath)
			throws ModelException {

		// get the current buildpath entries, in order to remove the given
		// entries
		List<IBuildpathEntry> newRawBuildpath = new ArrayList<>();

		for (IBuildpathEntry entry : entries) {
			if (!(entry.getPath().equals(buidlEntryPath))) {
				newRawBuildpath.add(entry);
			}

		}

		// set the new updated buildpath for the project
		return newRawBuildpath.toArray(new IBuildpathEntry[newRawBuildpath.size()]);

	}
}
