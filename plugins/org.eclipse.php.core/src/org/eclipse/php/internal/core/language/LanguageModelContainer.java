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
package org.eclipse.php.internal.core.language;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathContainer;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.php.core.language.ILanguageModelProvider;
import org.eclipse.php.internal.core.Logger;

public class LanguageModelContainer implements IBuildpathContainer {

	private IPath containerPath;
	private IBuildpathEntry[] buildPathEntries;
	private IScriptProject fProject;

	public LanguageModelContainer(IPath containerPath, IScriptProject project) {
		this.containerPath = containerPath;
		this.fProject = project;
	}

	public IBuildpathEntry[] getBuildpathEntries(IScriptProject project) {
		if (buildPathEntries == null) {
			try {
				List<IBuildpathEntry> entries = new LinkedList<IBuildpathEntry>();

				for (ILanguageModelProvider provider : LanguageModelInitializer
						.getContributedProviders()) {

					// Get the location where language model files reside
					// in provider's plug-in:
					IPath path = provider.getPath(project);
					if (path != null) {

						// Copy files (if target directory is older) to the
						// plug-in state
						// location:
						path = copyToInstanceLocation(provider, path, project);
						if (path != null) {

							LanguageModelInitializer.addPathName(path, provider
									.getName());

							IEnvironment environment = EnvironmentManager
									.getEnvironment(project);
							if (environment != null) {
								path = EnvironmentPathUtils.getFullPath(
										environment, path);
							}
							entries.add(DLTKCore.newLibraryEntry(path,
									BuildpathEntry.NO_ACCESS_RULES,
									BuildpathEntry.NO_EXTRA_ATTRIBUTES,
									BuildpathEntry.INCLUDE_ALL,
									BuildpathEntry.EXCLUDE_NONE, false, true));
						}
					}
				}
				buildPathEntries = (IBuildpathEntry[]) entries
						.toArray(new IBuildpathEntry[entries.size()]);
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		return buildPathEntries;
	}

	protected IPath copyToInstanceLocation(ILanguageModelProvider provider,
			IPath path, IScriptProject project) {

		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("$nl$", Platform.getNL()); //$NON-NLS-1$
			URL url = FileLocator.find(provider.getPlugin().getBundle(),
					provider.getPath(project), map);
			File sourceFile = new File(FileLocator.toFileURL(url).getPath());
			LocalFile sourceDir = new LocalFile(sourceFile);

			IPath targetPath = LanguageModelInitializer.getTargetLocation(
					provider, Path.fromOSString(sourceFile.getAbsolutePath()),
					project);
			LocalFile targetDir = new LocalFile(targetPath.toFile());

			IFileInfo targetInfo = targetDir.fetchInfo();
			boolean update = !targetInfo.exists();
			if (!update) {
				IFileInfo sourceInfo = sourceDir.fetchInfo();
				update = targetInfo.getLastModified() < sourceInfo
						.getLastModified();
			}

			if (update) {
				targetDir.delete(EFS.NONE, new NullProgressMonitor());
				sourceDir.copy(targetDir, EFS.NONE, new NullProgressMonitor());
			}

			return targetPath;

		} catch (Exception e) {
			Logger.logException(e);
		}

		return null;
	}

	public String getDescription() {
		return LanguageModelInitializer.PHP_LANGUAGE_LIBRARY;
	}

	public int getKind() {
		return K_SYSTEM;
	}

	public IPath getPath() {
		return containerPath;
	}

	public IBuildpathEntry[] getBuildpathEntries() {
		return getBuildpathEntries(fProject);
	}
}