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

import java.util.*;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPluginConstants;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.wst.validation.ValidationFramework;

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
		List<BuildPathParser.BuildPathInfo> paths = parser.getPathsInfo();

		// project prefs
		IEclipsePreferences prefs = ComposerPlugin.getDefault().getProjectPreferences(project);
		IPath[] inclusions;
		List<IPath> exs = new ArrayList<IPath>();

		try {
			String encoded = prefs.get(ComposerPreferenceConstants.BUILDPATH_INCLUDES_EXCLUDES, ""); //$NON-NLS-1$
			inclusions = scriptProject.decodeBuildpathEntry(encoded).getInclusionPatterns();
			exs.addAll(Arrays.asList(scriptProject.decodeBuildpathEntry(encoded).getExclusionPatterns()));
		} catch (Exception e) {
			inclusions = new IPath[] {};
		}

		// add includes
		for (IPath inclusion : inclusions) {
			paths.add(new BuildPathParser.BuildPathInfo(inclusion.toString(), BuildPathParser.BuildPathInfo.SOURCE));
		}

		// clean up exclusion patterns: remove exact matches

		for (Iterator<IPath> eit = exs.iterator(); eit.hasNext();) {

			String exc = eit.next().removeTrailingSeparator().toString();
			for (Iterator<BuildPathParser.BuildPathInfo> it = paths.iterator(); it.hasNext();) {
				BuildPathParser.BuildPathInfo info = it.next();
				if (info.path.equals(exc)) {
					it.remove();
				}
			}
		}
		exclusions = exs.toArray(new IPath[] {});

		// clean build path
		List<IBuildpathEntry> buildPath = new ArrayList<IBuildpathEntry>(
				Arrays.asList(scriptProject.getRawBuildpath()));

		for (Iterator<IBuildpathEntry> it = buildPath.iterator(); it.hasNext();) {
			IBuildpathEntry entry = it.next();
			if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE
					&& entry.getExtraAttribute(ComposerPluginConstants.BPE_ATTR_NAME) != null) {
				it.remove();
			}

		}
		Collections.sort(paths);
		// sort paths for nesting detection
		Collections.sort(buildPath, new Comparator<IBuildpathEntry>() {

			@Override
			public int compare(IBuildpathEntry o1, IBuildpathEntry o2) {
				return o1.getPath().toString().compareTo(o2.getPath().toString());
			}
		});

		// add new entries to buildpath
		for (BuildPathParser.BuildPathInfo path : paths) {
			IPath entry = new Path(path.path);
			IFolder folder = project.getFolder(entry);
			if (folder != null && folder.exists()) {
				addPath(folder.getFullPath(), buildPath, path.type);
			}
		}

		DLTKCore.create(project).setRawBuildpath(buildPath.toArray(new IBuildpathEntry[0]), monitor);

		IFolder folder = project.getFolder(new Path(composerProject.getVendorDir()));

		if (folder != null && folder.exists()) {
			if (!folder.isDerived()) {
				folder.setDerived(true, monitor);
			}

			// disable validation in the vendor folder
			ValidationFramework.getDefault().disableValidation(folder);
		}
	}

	private void addPath(IPath path, List<IBuildpathEntry> entries, int type) {
		// find parent
		IBuildpathEntry parent = null;
		int parentLength = 0;
		IPath entryPath;
		for (Iterator<IBuildpathEntry> it = entries.iterator(); it.hasNext();) {
			IBuildpathEntry entry = it.next();
			if (entry.getEntryKind() != IBuildpathEntry.BPE_SOURCE) {
				continue;
			}
			if (entry.getPath().equals(path)) {
				it.remove();
				continue;
			}

			entryPath = entry.getPath();
			// user defined build path conflicted with composer entry
			if (entry.getExtraAttribute(ComposerPluginConstants.BPE_ATTR_NAME) == null) {
				if (path.isPrefixOf(entryPath) || entryPath.isPrefixOf(path)) {
					it.remove();
				}
				continue;
			}
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
			if (parent.getExtraAttribute(ComposerPluginConstants.BPE_ATTR_NAME) != null) {
				parent = DLTKCore.newSourceEntry(parent.getPath(), BuildpathEntry.INCLUDE_ALL,
						exclusions.toArray(new IPath[] {}),
						new IBuildpathAttribute[] {
								DLTKCore.newBuildpathAttribute(ComposerPluginConstants.BPE_ATTR_NAME,
										parent.getExtraAttribute(ComposerPluginConstants.BPE_ATTR_NAME)) });
				entries.add(parent);
			}

		}

		// add own entry
		// leave vendor/composer untouched with exclusions
		if (vendorPath.equals(path) || composerPath.equals(path)) {
			entries.add(DLTKCore.newSourceEntry(path, BuildpathEntry.INCLUDE_ALL, BuildpathEntry.EXCLUDE_NONE,
					new IBuildpathAttribute[] { DLTKCore.newBuildpathAttribute(ComposerPluginConstants.BPE_ATTR_NAME,
							ComposerPluginConstants.BPE_ATTR_VENDOR) }));

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

			entries.add(DLTKCore.newSourceEntry(path, BuildpathEntry.INCLUDE_ALL, ex.toArray(new IPath[] {}),
					new IBuildpathAttribute[] { DLTKCore.newBuildpathAttribute(ComposerPluginConstants.BPE_ATTR_NAME,
							getTypeName(type)) }));
		}
	}

	protected String getTypeName(int type) {
		switch (type) {
		case BuildPathParser.BuildPathInfo.SOURCE:
			return ComposerPluginConstants.BPE_ATTR_SOURCE;
		case BuildPathParser.BuildPathInfo.VENDOR:
			return ComposerPluginConstants.BPE_ATTR_VENDOR;
		}

		return null;
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
