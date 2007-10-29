/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;

/**
 * This utility implements internal PHP mechanism for searching included files.
 * The algorithm is the following:<br/><br/>
 *
 *  Files for including are first looked for in each <b>include_path</b> entry
 *  relative to the current working directory, and then in the directory of current script.
 *  E.g. if your <b>include_path</b> is libraries, current working directory is /www/,
 *  you included include/a.php and there is include "b.php"  in that file,
 *  b.php is first looked in /www/libraries/  and then in /www/include/.
 *  If filename begins with ./ or ../, it is looked only in the current working directory.
 *
 * @author michael
 */
public class PHPSearchEngine {

	/**
	 * Searches for the given path using internal PHP mechanism
	 *
	 * @param path File path to resolve
	 * @param currentWorkingDir local Current working directory (usually: CWD of PHP process)
	 * @param currentScriptDir Directory of current script (which is interpreted by the PHP at this time)
	 * @param currentProject Current project to which current script belongs
	 * @return resolved path, or <code>null</code> in case of failure
	 */
	public static Result<?, ?> find(String path, String currentWorkingDir, String currentScriptDir, IProject currentProject) {
		if (path == null || currentWorkingDir == null || currentScriptDir == null || currentProject == null) {
			throw new NullPointerException("Parameters can't be null");
		}

		// check whether the path is absolute
		File file = new File(path);
		if (file.isAbsolute()) {
			return searchExternalOrWorkspaceFile(file);
		}
		if (path.matches("\\.\\.?[/\\\\].*")) { // check whether the path starts with ./ or ../
			file = new File(currentWorkingDir, path);
			return searchExternalOrWorkspaceFile(file);
		}

		// look into include path:
		Object[] includePaths = buildIncludePath(currentProject);
		for (Object includePath : includePaths) {
			if (includePath instanceof IContainer) {
				IContainer container = (IContainer) includePath;
				IResource resource = container.findMember(path);
				if (resource instanceof IFile) {
					return new ResourceResult((IFile) resource);
				}
			} else if (includePath instanceof IIncludePathEntry) {
				IIncludePathEntry entry = (IIncludePathEntry) includePath;
				IPath entryPath = entry.getPath();
				if (entry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
					if (entry.getContentKind() != IIncludePathEntry.K_BINARY) { // We don't support lookup in archive
						File entryDir = entryPath.toFile();
						file = new File(entryDir, path);
						if (file.exists()) {
							return new IncludedFileResult(entry, file);
						}
					}
				} else if (entry.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
					IProject project = (IProject) entry.getResource();
					if (project.isAccessible()) {
						IResource resource = project.findMember(path);
						if (resource != null) {
							return new IncludedFileResult(entry, file);
						}
					}
				} else if (entry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
					entryPath = IncludePathVariableManager.instance().resolveVariablePath(entryPath.toString());
					File entryDir = entryPath.toFile();
					file = new File(entryDir, path);
					if (file.exists()) {
						return new IncludedFileResult(entry, file);
					}
				}
			}
		}

		// look at current script directory:
		file = new File(currentScriptDir, path);
		return searchExternalOrWorkspaceFile(file);
	}

	private static Result<?, ?> searchExternalOrWorkspaceFile(File file) {
		IFile res = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromOSString(file.getAbsolutePath()));
		if (res != null) {
			return new ResourceResult(res);
		}
		if (file.exists()) {
			return new ExternalFileResult(file);
		}
		return null;
	}

	/**
	 * Builds include path for searching by the given project.
	 * Result contains include path of the given project, referenced projects and their include paths.
	 * @param project Current project
	 * @return array of include path objects (it can be one of: IContainer, IncludePathEntry)
	 */
	public static Object[] buildIncludePath(IProject project) {
		Set<Object> results = new HashSet<Object>();
		buildIncludePath(project, results);
		return results.toArray();
	}

	/**
	 * Builds include path for searching by the given project.
	 * Result contains include path of the given project, referenced projects and their include paths.
	 * @param project Current project
	 * @param results Array of include path objects (it can be one of: IContainer, IncludePathEntry)
	 */
	public static void buildIncludePath(IProject project, Set<Object> results) {
		if (results.contains(project)) {
			return;
		}
		if (!project.isAccessible()) {
			return;
		}
		// Collect include paths:
		PHPProjectOptions projectOptions = PHPProjectOptions.forProject(project);
		if (projectOptions != null) {
			IIncludePathEntry[] includePath = projectOptions.readRawIncludePath();
			for (IIncludePathEntry entry : includePath) {
				results.add(entry);
			}
		}
		// Collect referenced projects and their include paths:
		try {
			IProject[] referencedProjects = project.getReferencedProjects();
			for (IProject referencedProject : referencedProjects) {
				buildIncludePath(referencedProject, results);
			}
		} catch (CoreException e) {
		}
		// Add current project:
		results.add(project);
	}

	/**
	 * Result returned by PHP search engine
	 */
	abstract public static class Result<T, S> {
		private T container;
		private S file;

		public Result(T container, S file) {
			this.container = container;
			this.file = file;
		}

		public T getContainer() {
			return container;
		}

		public S getFile() {
			return file;
		}
	}

	/**
	 * Result for Workspace file
	 */
	public static class ResourceResult extends Result<Object, IFile> {
		public ResourceResult(IFile file) {
			super(file.getParent(), file);
		}
	}

	/**
	 * Result for included file (from Include Path)
	 */
	public static class IncludedFileResult extends Result<IIncludePathEntry, File> {
		public IncludedFileResult(IIncludePathEntry container, File file) {
			super(container, file);
		}
	}

	/**
	 * Result for external file (on file system)
	 */
	public static class ExternalFileResult extends Result<Object, File> {
		public ExternalFileResult(File file) {
			super(file.getParentFile(), file);
		}
	}
}
