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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.ComposerPackages;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.core.resources.IComposerProject;

public class BuildPathParser {

	private IComposerProject project;

	public BuildPathParser(IComposerProject project) {
		this.project = project;
	}

	public List<String> getPaths() {
		ComposerPackages packages = project.getInstalledPackages();
		if (packages == null) {
			return null;
		}

		ComposerPackage composer = project.getComposerPackage();
		String vendor = project.getVendorDir();

		// empty list for found package paths
		List<String> paths = new ArrayList<String>();

		// add source paths from this package
		parsePackage(composer, paths);

		// add composer vendor dir
		paths.add(vendor + "/composer"); //$NON-NLS-1$

		// all installed packages
		for (ComposerPackage p : packages) {
			parsePackage(p, paths, vendor + "/" + p.getName()); //$NON-NLS-1$
		}

		return paths;
	}

	private void parsePackage(ComposerPackage pkg, List<String> paths) {
		parsePackage(pkg, paths, ""); //$NON-NLS-1$
	}

	private void parsePackage(ComposerPackage pkg, List<String> paths, String prefix) {
		if (prefix != null && !prefix.equals("") && !prefix.endsWith("/")) { //$NON-NLS-1$ //$NON-NLS-2$
			prefix += "/"; //$NON-NLS-1$
		}

		Autoload a = pkg.getAutoload();

		// psr-0
		for (Namespace namespace : a.getPsr0()) {
			for (Object path : namespace.getPaths()) {
				addPath(prefix + path, paths);
			}
		}

		// psr-4
		for (Namespace namespace : a.getPsr4()) {
			for (Object path : namespace.getPaths()) {
				addPath(prefix + path, paths);
			}
		}

		// classmap
		for (Object path : a.getClassMap()) {
			String cleanedPath = getDirectory(prefix + (String) path);
			addPath(cleanedPath, paths);
		}

		// files
		for (Object path : a.getFiles()) {
			String cleanedPath = getDirectory(prefix + (String) path);
			addPath(cleanedPath, paths);
		}
	}

	private String getDirectory(String path) {
		String cleanedPath = null;
		IPath root = project.getProject().getLocation();
		File f = new File(root.toFile(), path);
		if (f.exists()) {
			if (f.isDirectory()) {
				cleanedPath = f.getPath().replace(root.toOSString(), ""); //$NON-NLS-1$
			} else {
				cleanedPath = f.getParentFile().getPath().replace(root.toOSString(), ""); //$NON-NLS-1$
			}
		}
		return cleanedPath;
	}

	private void addPath(String path, List<String> paths) {
		if (path != null && !path.trim().isEmpty()) {
			// switch from win to unix
			path = path.replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$

			// path cleanup
			if (path.startsWith("/")) { //$NON-NLS-1$
				path = path.substring(1);
			}

			if (path.endsWith("/.")) { //$NON-NLS-1$
				path = path.substring(0, path.length() - 2);
			}

			if (path.endsWith("/")) { //$NON-NLS-1$
				path = path.substring(0, path.length() - 1);
			}

			if (path.equals(".")) { //$NON-NLS-1$
				path = ""; //$NON-NLS-1$
			}

			// if (!path.isEmpty()) {
			// path = project.getProject().getFullPath().toString() + "/" +
			// path;
			// } else {
			// path = project.getProject().getFullPath().toString();
			// }

			if (path.startsWith("/")) { //$NON-NLS-1$
				path = path.substring(1);
			}

			if (!paths.contains(path)) {
				paths.add(path);
			}
		}
	}
}
