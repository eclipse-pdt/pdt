/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.buildpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.wst.validation.ValidationFramework;

@SuppressWarnings("restriction")
public class BuildPathManager {

	private IComposerProject composerProject;
	private IPath[] exclusions; // gnoar, shouldn't be a property, what did I
								// thought? DI 'n stuff...
	private IPath vendorPath;
	private IPath composerPath;

	public BuildPathManager(IComposerProject composerProject) {
		this.composerProject = composerProject;
	}

	public void update() throws CoreException {
		update(new NullProgressMonitor());
	}

	public void update(IProgressMonitor monitor) throws CoreException {
		// check for valid composer json, stop processing when invalid
		if (!composerProject.isValidComposerJson()) {
			Logger.log(Logger.INFO, "Stop BuildPathManager, composer.json invalid"); //$NON-NLS-1$
			return;
		}

		vendorPath = composerProject.getProject().getFullPath().append(composerProject.getVendorDir());
		composerPath = vendorPath.append("composer"); //$NON-NLS-1$

		IProject project = composerProject.getProject();
		IScriptProject scriptProject = composerProject.getScriptProject();
		BuildPathParser parser = new BuildPathParser(composerProject);
		List<String> paths = parser.getPaths();

		// project prefs
		IEclipsePreferences prefs = ComposerPlugin.getDefault().getProjectPreferences(project);
		IPath[] inclusions;

		try {
			String encoded = prefs.get(ComposerPreferenceConstants.BUILDPATH_INCLUDES_EXCLUDES, ""); //$NON-NLS-1$
			exclusions = scriptProject.decodeBuildpathEntry(encoded).getExclusionPatterns();
			inclusions = scriptProject.decodeBuildpathEntry(encoded).getInclusionPatterns();
		} catch (Exception e) {
			exclusions = new IPath[] {};
			inclusions = new IPath[] {};
		}

		// add includes
		for (IPath inclusion : inclusions) {
			paths.add(inclusion.toString());
		}

		// clean up exclusion patterns: remove exact matches
		List<IPath> exs = new ArrayList<IPath>();
		for (IPath exclusion : exclusions) {
			String exc = exclusion.removeTrailingSeparator().toString();

			if (paths.contains(exc)) {
				paths.remove(exc);
			} else {
				exs.add(exclusion);
			}
		}
		exclusions = exs.toArray(new IPath[] {});

		// clean build path
		IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();
		for (IBuildpathEntry entry : rawBuildpath) {
			if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
				BuildPathUtils.removeEntryFromBuildPath(scriptProject, entry);
			}
		}

		// sort paths for nesting detection
		Collections.sort(paths);

		// add new entries to buildpath
		List<IBuildpathEntry> newEntries = new ArrayList<IBuildpathEntry>();
		for (String path : paths) {
			IPath entry = new Path(path);
			IFolder folder = project.getFolder(entry);
			if (folder != null && folder.exists()) {
				addPath(folder.getFullPath(), newEntries);
			}
		}

		if (newEntries.size() > 0) {
			BuildPathUtils.addNonDupEntriesToBuildPath(scriptProject, newEntries);
		}

		IFolder folder = project.getFolder(new Path(composerProject.getVendorDir()));

		if (folder != null && folder.exists()) {
			if (!folder.isDerived()) {
				folder.setDerived(true, monitor);
			}

			// disable validation in the vendor folder
			ValidationFramework.getDefault().disableValidation(folder);
		}
	}

	private void addPath(IPath path, List<IBuildpathEntry> entries) {
		// find parent
		IBuildpathEntry parent = null;
		int parentLength = 0;
		IPath entryPath;
		for (IBuildpathEntry entry : entries) {
			entryPath = entry.getPath();
			if (entryPath.isPrefixOf(path) && (parent == null || (entryPath.toString().length() > parentLength))) {
				parent = entry;
				parentLength = parent.getPath().toString().length();
			}
		}

		// add path as exclusion to found parent
		if (parent != null) {
			List<IPath> exclusions = new ArrayList<IPath>();
			exclusions.addAll(Arrays.asList(parent.getExclusionPatterns()));

			IPath diff = path.removeFirstSegments(path.matchingFirstSegments(parent.getPath()));
			if (parent.getPath().equals(composerPath)) {
				diff = diff.uptoSegment(1);
			}
			diff = diff.removeTrailingSeparator().addTrailingSeparator();
			if (!exclusions.contains(diff)) {
				exclusions.add(diff);
			}

			entries.remove(parent);

			parent = DLTKCore.newSourceEntry(parent.getPath(), exclusions.toArray(new IPath[] {}));
			entries.add(parent);
		}

		// add own entry
		// leave vendor/composer untouched with exclusions
		if (vendorPath.equals(path) || composerPath.equals(path)) {
			entries.add(DLTKCore.newSourceEntry(path));

			// add exclusions
		} else {
			List<IPath> ex = new ArrayList<IPath>();

			// find the applying exclusion patterns for the new entry
			for (IPath exclusion : exclusions) {

				if (!exclusion.toString().startsWith("*")) { //$NON-NLS-1$
					exclusion = composerProject.getProject().getFullPath().append(exclusion);
				}

				// remove buildpath entries with exact exclusion matches
				if (path.equals(exclusion)) {
					return;
				}

				// if exclusion matches path, add the trailing path segments as
				// exclusion
				if (path.removeTrailingSeparator().isPrefixOf(exclusion)) {
					ex.add(exclusion.removeFirstSegments(path.matchingFirstSegments(exclusion)));
				}

				// if exclusion starts with wildcard, add also
				else if (exclusion.toString().startsWith("*")) { //$NON-NLS-1$
					ex.add(exclusion);
				}
			}

			entries.add(DLTKCore.newSourceEntry(path, ex.toArray(new IPath[] {})));
		}
	}

	// is this method necessary at all?
	public static void setExclusionPattern(IScriptProject project, IBuildpathEntry entry) {

		try {
			String encoded = project.encodeBuildpathEntry(entry);
			IEclipsePreferences prefs = ComposerPlugin.getDefault().getProjectPreferences(project.getProject());
			prefs.put(ComposerPreferenceConstants.BUILDPATH_INCLUDES_EXCLUDES, encoded);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
