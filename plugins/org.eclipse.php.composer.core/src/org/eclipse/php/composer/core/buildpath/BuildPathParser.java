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
	public static class BuildPathInfo implements Comparable<BuildPathInfo> {
		public static final int SOURCE = 2;

		public static final int VENDOR = 4;

		public final String path;

		public final int type;

		public BuildPathInfo(String path, int type) {
			this.path = path;
			this.type = type;
		}

		@Override
		public int compareTo(BuildPathInfo o) {
			return path.compareTo(o.path);
		}
	}

	private static String EMPTY = ""; //$NON-NLS-1$

	private IComposerProject project;

	public BuildPathParser(IComposerProject project) {
		this.project = project;
	}

	@Deprecated
	public List<String> getPaths() {
		List<BuildPathInfo> pathsInfo = getPathsInfo();
		List<String> pathes = new ArrayList<String>(pathsInfo.size());
		for (BuildPathInfo info : pathsInfo) {
			pathes.add(info.path);
		}

		return pathes;
	}

	public List<BuildPathInfo> getPathsInfo() {
		ComposerPackage composer = project.getComposerPackage();
		String vendor = project.getVendorDir();

		// empty list for found package paths
		List<BuildPathInfo> paths = new ArrayList<BuildPathInfo>();

		// add source paths from this package
		parsePackage(composer, paths, EMPTY, BuildPathInfo.SOURCE);

		// add composer vendor dir
		paths.add(new BuildPathInfo(vendor + "/composer", BuildPathInfo.VENDOR)); //$NON-NLS-1$

		// all installed packages
		ComposerPackages packages = project.getInstalledPackages();
		if (packages != null) {
			for (ComposerPackage p : packages) {
				parsePackage(p, paths, vendor + "/" + p.getName(), BuildPathInfo.VENDOR); //$NON-NLS-1$
			}
		}

		return paths;
	}

	private void parsePackage(ComposerPackage pkg, List<BuildPathInfo> paths, String prefix, int type) {
		if (prefix != null && !prefix.equals("") && !prefix.endsWith("/")) { //$NON-NLS-1$ //$NON-NLS-2$
			prefix += "/"; //$NON-NLS-1$
		}

		Autoload a = pkg.getAutoload();

		// psr-0
		for (Namespace namespace : a.getPsr0()) {
			for (Object path : namespace.getPaths()) {
				addPath(prefix + path, paths, type);
			}
		}

		// psr-4
		for (Namespace namespace : a.getPsr4()) {
			for (Object path : namespace.getPaths()) {
				addPath(prefix + path, paths, type);
			}
		}

		// classmap
		for (Object path : a.getClassMap()) {
			String cleanedPath = getDirectory(prefix + (String) path);
			addPath(cleanedPath, paths, type);
		}

		// files
		for (Object path : a.getFiles()) {
			String cleanedPath = getDirectory(prefix + (String) path);
			addPath(cleanedPath, paths, type);
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

	private void addPath(String path, List<BuildPathInfo> paths, int type) {
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
				paths.add(new BuildPathInfo(path, type));
			}
		}
	}
}
