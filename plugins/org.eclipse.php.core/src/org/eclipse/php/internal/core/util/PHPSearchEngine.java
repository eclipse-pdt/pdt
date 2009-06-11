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
package org.eclipse.php.internal.core.util;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;

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
	
	private static Pattern RELATIVE_PATH_PATTERN = Pattern.compile("\\.\\.?[/\\\\].*");

	/**
	 * Searches for the given path using internal PHP mechanism
	 *
	 * @param path File path to resolve
	 * @param currentWorkingDir Current working directory (usually: CWD of PHP process), absolute (workspace of file system)
	 * @param currentScriptDir Absolute (workspace of file system) directory of current script (which is interpreted by the PHP at this time)
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
		if (RELATIVE_PATH_PATTERN.matcher(path).matches()) { // check whether the path starts with ./ or ../
			return searchExternalOrWorkspaceFile(currentWorkingDir, path);
		}

		// look into include path:
		IncludePath[] includePaths = buildIncludePath(currentProject);
		for (IncludePath includePath : includePaths) {
			if (includePath.isBuildpath()) {
				Result<?, ?> searchInBuildpathEntry = searchInBuildpathEntry(path, (IBuildpathEntry) includePath.getEntry(), currentProject);
				if (searchInBuildpathEntry != null) {
					return searchInBuildpathEntry;	
				}
			} else {
				IContainer container = (IContainer) includePath.getEntry();
				IResource resource = container.findMember(path);
				if (resource instanceof IFile) {
					return new ResourceResult((IFile) resource);
				}
			}
		}

		// look at current script directory:
		return searchExternalOrWorkspaceFile(currentScriptDir, path);
	}

	private static Result<?, ?> searchInBuildpathEntry(String path, IBuildpathEntry entry, IProject currentProject) {

		IPath entryPath = EnvironmentPathUtils.getLocalPath(entry.getPath());
		
		if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
			File entryDir = entryPath.toFile();
			File file = new File(entryDir, path);
			if (file.exists()) {
				return new IncludedFileResult(entry, file);
			}	
        } else if (entry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
			entryPath = DLTKCore.getResolvedVariablePath(entryPath);
			File entryDir = entryPath.toFile();
			File file = new File(entryDir, path);
			if (file.exists()) {
				return new IncludedFileResult(entry, file);
			}
		} else if (entry.getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = workspaceRoot.getProject(entryPath.segment(0));
			if (project.isAccessible()) {
				IResource resource = project.findMember(path);
				if (resource instanceof IFile) {
					return new ResourceResult((IFile) resource);
				}
			}
		} else if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = workspaceRoot.findMember(entryPath);
			if (resource instanceof IContainer) {
				resource = ((IContainer)resource).findMember(path);
				if (resource instanceof IFile) {
					return new ResourceResult((IFile) resource);
				}
			}
		} else if (entry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
			try {
				IScriptProject scriptProject = DLTKCore.create(currentProject);
				IBuildpathContainer container = DLTKCore.getBuildpathContainer(entry.getPath(), scriptProject);
				if (container != null) {
					IBuildpathEntry[] buildpathEntries = container.getBuildpathEntries(scriptProject);
					if (buildpathEntries != null) {
						for (IBuildpathEntry buildpathEntry : buildpathEntries) {
							Result<?, ?> result = searchInBuildpathEntry(path, buildpathEntry, currentProject);
							if (result != null) {
								IProjectFragment[] projectFragments = scriptProject.findProjectFragments(entry);
								((IncludedFileResult)result).setProjectFragments(projectFragments);
								return result;
							}
						}
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		
		return null;
	}

	private static Result<?, ?> searchExternalOrWorkspaceFile(String directory, String relativeFile) {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(directory);
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			IResource file = container.findMember(relativeFile);

			if (file instanceof IFile) {
				return new ResourceResult((IFile) file);
			}
		}
		File dir = new File(directory);
		if (dir.isDirectory()) {
			return searchExternalOrWorkspaceFile(new File(dir, relativeFile));
		}
		return null;
	}

	private static Result<?, ?> searchExternalOrWorkspaceFile(File file) {
		if (file.exists()) {
			IFile res = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromOSString(file.getAbsolutePath()));
			if (res != null) {
				return new ResourceResult(res);
			}
			if (file.exists()) {
				return new ExternalFileResult(file);
			}
		}
		return null;
	}

	/**
	 * Builds include path for searching by the given project.
	 * Result contains include path of the given project, referenced projects and their include paths.
	 * @param project Current project
	 * @return array of include path objects (it can be one of: IContainer, IncludePathEntry)
	 */
	public static IncludePath[] buildIncludePath(IProject project) {
		Set<IncludePath> results = new LinkedHashSet<IncludePath>();
		buildIncludePath(project, results);
		return results.toArray(new IncludePath[results.size()]);
	}

	/**
	 * Builds include path for searching by the given project.
	 * Result contains include path of the given project, referenced projects and their include paths.
	 * @param project Current project
	 * @param results Array of include path objects (it can be one of: IContainer, IncludePathEntry)
	 */
	public static void buildIncludePath(IProject project, Set<IncludePath> results) {
		if (results.contains(project)) {
			return;
		}
		if (!project.isAccessible() || !project.isOpen()) {
			return;
		}
		// Collect include paths:
		results.addAll(Arrays.asList(IncludePathManager.getInstance().getIncludePaths(project)));
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
		
		public void setContainer(T container) {
			this.container = container;
		}
		
		public void setFile(S file) {
			this.file = file;
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
	public static class IncludedFileResult extends Result<IBuildpathEntry, File> {
		private IProjectFragment[] projectFragments;
		
		public IncludedFileResult(IBuildpathEntry container, File file) {
			super(container, file);
		}
		
		public void setProjectFragments(IProjectFragment[] projectFragments) {
			this.projectFragments = projectFragments;
		}
		
		public IProjectFragment[] getProjectFragments() {
			return projectFragments;
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
