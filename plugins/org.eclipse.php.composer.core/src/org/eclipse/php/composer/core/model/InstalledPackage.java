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
package org.eclipse.php.composer.core.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IAccessRule;
import org.eclipse.dltk.core.IBuildpathAttribute;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.log.Logger;

/**
 * Represents a package inside installed(_dev).json. This class is used to
 * handle the BuildpathContainerEntry of the experimental feature which copies
 * installed packages to a temporary to increase indexing performance by sharing
 * the index of the same package/version combination over several projects.
 * 
 * This is deprecated and will be refactored to extend the Java-Bindings API
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
@Deprecated
public class InstalledPackage extends ComposerPackage {
	private IPath path;
	private File localFile;

	public String name;
	public String version;
	public String version_normalized;
	public String project;
	public boolean isDev;
	public Map<String, String> require;
	public Map<String, String> requireDev;
	public Map<String, String> suggest;
	public String targetDir;

	public IPath getPath() {
		if (path == null) {
			path = new Path(name);
		}
		return path;
	}

	public File getLocalFile() {
		if (localFile == null) {
			IPath location = ComposerPlugin.getDefault().getStateLocation();
			IPath localPath = location.append("packages").append(getPath()).append(version); //$NON-NLS-1$
			localFile = localPath.toFile();

			Logger.debug("Retrieving local filepath for " + name + ":"); //$NON-NLS-1$ //$NON-NLS-2$
			Logger.debug(localFile.getAbsolutePath());
		}

		return localFile;

	}

	public boolean isLocalVersionAvailable() {

		if (getLocalFile() == null || !getLocalFile().exists()) {
			return false;
		}

		return getLocalFile().list() != null && getLocalFile().list().length > 0;
	}

	public static List<InstalledPackage> deserialize(InputStream input) throws IOException {
		List<InstalledPackage> pkgs = new ArrayList<InstalledPackage>();
		return pkgs;
	}

	public static List<InstalledPackage> deserialize(String propertyValue) throws IOException {
		return deserialize(new ByteArrayInputStream(propertyValue.getBytes()));
	}

	public IBuildpathEntry getBuildpathEntry() {
		IPath libPath = Path.fromOSString(getLocalFile().getAbsolutePath()).makeAbsolute();

		IPath fullPath = EnvironmentPathUtils.getFullPath(EnvironmentManager.getLocalEnvironment(), libPath);

		IPath[] excludes = new IPath[] { new Path(".git/") }; //$NON-NLS-1$
		return DLTKCore.newLibraryEntry(fullPath, new IAccessRule[0], new IBuildpathAttribute[0], new IPath[0],
				excludes, false, true);
	}

	public boolean isRequiredBy(InstalledPackage dependency) {
		return dependency.requires(this);

	}

	public boolean requires(InstalledPackage dependency) {
		return require != null && require.containsKey(dependency.name);
	}

	public String getFullName() {
		return String.format("%s (%s)", name, version); //$NON-NLS-1$
	}
}