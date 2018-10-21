/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.internal.core.resources;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.api.ComposerConstants;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.ComposerPackages;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;

public class ComposerProject implements IComposerProject {

	private IProject project;
	private IScriptProject scriptProject;
	private ComposerPackage composer = null;
	private IFile json = null;
	private String vendorDir = null;
	private IPath vendorPath = null;

	public ComposerProject(IProject project) {
		this.project = project;
		IFile file = project.getFile(ComposerConstants.COMPOSER_JSON);

		if (file != null && file.exists()) {
			composer = new ComposerPackage();
			try {
				composer.fromJson(file.getLocation().toFile());
			} catch (Exception e) {
			}
		}
	}

	public ComposerProject(IScriptProject project) {
		this(project.getProject());
		scriptProject = project;
	}

	@Override
	public IPath getFullPath() {
		return project.getFullPath();
	}

	@Override
	public String getVendorDir() {
		if (vendorDir == null) {
			if (composer != null && composer.getConfig() != null) {
				vendorDir = composer.getConfig().getVendorDir();
			}

			if (vendorDir == null || vendorDir.trim().isEmpty()) {
				vendorDir = ComposerConstants.VENDOR_DIR_DEFAULT; // default
			}
		}

		return vendorDir;
	}

	@Override
	public IPath getVendorPath() {
		if (vendorPath == null) {
			IPath root = project.getLocation();
			String vendor = getVendorDir();

			if (root == null || root.segmentCount() <= 1) {
				throw new RuntimeException("Error getting composer vendor path"); //$NON-NLS-1$
			}

			vendorPath = root.removeLastSegments(1).addTrailingSeparator().append(vendor);
		}
		return vendorPath;
	}

	@Override
	public IFile getComposerJson() {
		if (json == null) {
			json = project.getFile(ComposerConstants.COMPOSER_JSON);
		}
		return json;
	}

	@Override
	public ComposerPackage getComposerPackage() {
		if (composer == null) {
			try {
				IFile json = getComposerJson();
				if (json == null) {
					return null;
				}
				composer = new ComposerPackage(json.getLocation().toFile());
			} catch (Exception e) {
			}
		}

		return composer;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public IScriptProject getScriptProject() {
		if (scriptProject == null) {
			scriptProject = DLTKCore.create(project);
		}
		return scriptProject;
	}

	@Override
	public ComposerPackages getInstalledPackages() {
		String vendor = getVendorDir();
		ComposerPackages packages = new ComposerPackages();

		IFile installed = project.getFile(vendor + "/composer/installed.json"); //$NON-NLS-1$
		if (installed != null && installed.exists()) {
			packages.addAll(loadInstalled(installed));
		}

		return packages;
	}

	@Override
	public boolean isValidComposerJson() {
		IFile json = getComposerJson();
		if (json != null && json.exists()) {
			try {
				new ComposerPackage(json.getLocation().toFile());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	// @Override
	// public ComposerPackages getInstalledDevPackages() {
	// String vendor = getVendorDir();
	// ComposerPackages packages = new ComposerPackages();
	//
	// IFile installedDev = project.getFile(vendor +
	// "/composer/installed_dev.json");
	// if (installedDev != null && installedDev.exists()) {
	// packages.addAll(loadInstalled(installedDev));
	// }
	//
	// return packages;
	// }
	//
	// @Override
	// public ComposerPackages getAllInstalledPackages() {
	// ComposerPackages packages = getInstalledPackages();
	// packages.addAll(getInstalledDevPackages());
	// return packages;
	// }

	protected ComposerPackages loadInstalled(IFile installed) {
		try {
			if (installed.getLocation() != null) {
				return new ComposerPackages(installed.getLocation().toFile());
			}
		} catch (Exception e) {
			Logger.logException(e);
		}

		return new ComposerPackages();
	}

	public String getNamespace(IPath path) {
		path = path.removeFirstSegments(1).makeRelative();
		Autoload autoload = getComposerPackage().getAutoload();
		// look for psr4 first
		String namespace = getPsrNamespace(path, autoload.getPsr4());

		if (namespace == null) {
			namespace = getPsrNamespace(path, autoload.getPsr0());
		}
		if (namespace == null) {
			autoload = getComposerPackage().getAutoloadDev();

			// look for psr4 first
			namespace = getPsrNamespace(path, autoload.getPsr4());

			if (namespace == null) {
				namespace = getPsrNamespace(path, autoload.getPsr0());
			}
		}

		return namespace;
	}

	private String getPsrNamespace(IPath path, Psr psr) {
		IPath appendix = new Path(""); //$NON-NLS-1$
		while (!path.isEmpty() && !path.isRoot()) {
			Namespace namespace = psr.getNamespaceForPath(path.addTrailingSeparator().toString());
			if (namespace == null) {
				namespace = psr.getNamespaceForPath(path.removeTrailingSeparator().toString());
			}

			if (namespace != null) {
				String nmspc = namespace.getNamespace();
				IPath nmspcPath = new Path(nmspc.replace("\\", "/")); //$NON-NLS-1$ //$NON-NLS-2$

				int match = nmspcPath.matchingFirstSegments(appendix);
				appendix = appendix.removeFirstSegments(match);

				if (appendix.segmentCount() > 0) {
					nmspc += (!nmspc.isEmpty() ? "\\" : "") //$NON-NLS-1$ //$NON-NLS-2$
							+ appendix.removeTrailingSeparator().toString().replace("/", "\\"); //$NON-NLS-1$ //$NON-NLS-2$
				}

				nmspc = nmspc.replace("\\\\", "\\"); //$NON-NLS-1$ //$NON-NLS-2$
				if (nmspc.endsWith("\\")) { //$NON-NLS-1$
					nmspc = nmspc.substring(0, nmspc.length() - 1);
				}

				return nmspc;
			}

			appendix = new Path(path.lastSegment() + "/" + appendix.toString()); //$NON-NLS-1$
			path = path.removeLastSegments(1);
		}

		return null;
	}

	@Override
	public IPath getNamespaceDir(IPath source, String namespace) {
		Autoload[] autoloads = new Autoload[] { getComposerPackage().getAutoload(),
				getComposerPackage().getAutoloadDev() };
		Namespace matched = null;
		for (Autoload autoload : autoloads) {
			for (Namespace name : autoload.getPsr4().getNamespaces()) {
				matched = match(namespace, name, matched);
			}
			for (Namespace name : autoload.getPsr0().getNamespaces()) {
				matched = match(namespace, name, matched);
			}
		}
		if (matched != null) {
			String path = matched.getPaths().get(0).toString();
			IPath result = new Path(source.segment(0)).makeAbsolute();

			int move = matched.getNamespace().length();
			if (matched.getNamespace().endsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				move--;
			}
			String addon = namespace.substring(move);
			if (addon.endsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				addon = addon.substring(0, addon.length() - 1);
			}
			result = result.append(new Path(path));
			result = result.append(new Path(addon.replace(NamespaceReference.NAMESPACE_SEPARATOR, IPath.SEPARATOR)));
			return result;
		}

		return null;
	}

	private Namespace match(String namespace, Namespace current, Namespace previous) {
		namespace = namespace + NamespaceReference.NAMESPACE_DELIMITER;
		if (!namespace.startsWith(current.getNamespace())) {
			return previous;
		}
		if (previous == null) {
			return current;
		}
		if (previous.getNamespace().length() > current.getNamespace().length()) {
			return previous;
		} else {
			return current;
		}
	}

}
