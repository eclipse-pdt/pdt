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
package org.eclipse.php.internal.core.buildpath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;

public class BuildPathUtils {

	/**
	 * Adds the given entries to the Build Path 
	 * @param scriptProject
	 * @param entries
	 * @throws ModelException
	 */
	public static void addEntriesToBuildPath(IScriptProject scriptProject, List<IBuildpathEntry> entries) throws ModelException {
		IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();

		// get the current buildpath entries, in order to add/remove entries
		Set<IBuildpathEntry> newRawBuildpath = new HashSet<IBuildpathEntry>();

		// get all of the source folders and the language library from the existing build path 
		for (IBuildpathEntry buildpathEntry : rawBuildpath) {
			newRawBuildpath.add(buildpathEntry);
		}
		// add all of the entries added in this dialog
		for (IBuildpathEntry buildpathEntry : entries) {
			newRawBuildpath.add(buildpathEntry);
		}	
		
		// set the new updated buildpath for the project		
		scriptProject.setRawBuildpath(newRawBuildpath.toArray(new IBuildpathEntry[newRawBuildpath.size()]), null);
		
	}
	
	/**
	 * Removes the given entry from the build path (according to the path)
	 * @param scriptProject
	 * @param buildpathEntry
	 * @throws ModelException 
	 */
	public static void removeEntryFromBuildPath(IScriptProject scriptProject, IBuildpathEntry buildpathEntry) throws ModelException {
		IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();

		// get the current buildpath entries, in order to remove the given entries
		List<IBuildpathEntry> newRawBuildpath = new ArrayList<IBuildpathEntry>();

		for (IBuildpathEntry entry : rawBuildpath) {
			if (!(entry.getPath().equals(buildpathEntry.getPath()))) {
				newRawBuildpath.add(entry);
			}

		}
	
		// set the new updated buildpath for the project		
		scriptProject.setRawBuildpath(newRawBuildpath.toArray(new IBuildpathEntry[newRawBuildpath.size()]), null);

	}
	
	/**
	 * Returns whether the given path is "under" the buildpath definitions
	 * Meaning if one of the entries in the build path has the same path or contains this resource
	 * @param project
	 * @param resourcePath
	 * @return
	 */
	public static boolean isContainedInBuildpath(IPath resourcePath, IScriptProject project) {
		
		boolean result = false;
		
		if(resourcePath == null){
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
		// check if they are the same as the given include path entry or if they contain it 
		for (IBuildpathEntry buildpathEntry : buildpath) {
			if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_SOURCE){
				IPath buildPathEntryPath = buildpathEntry.getPath();
				if (buildPathEntryPath.isPrefixOf(resourcePath) || resourcePath.toString().equals(buildPathEntryPath.toString())){
					result = true;
				}
			}
		}
		
		return result;
	}
	
}
