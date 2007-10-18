package org.eclipse.php.internal.core.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
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
	 * @param currentWorkingDir Current working directory (usually: CWD of PHP process)
	 * @param currentScriptDir Directory of current script (which is interpreted by the PHP at this time)
	 * @param currentProject Current project to which current script belongs
	 * @return resolved path, or <code>null</code> in case of failure
	 */
	public static Result find (String path, String currentWorkingDir, String currentScriptDir, IProject currentProject) {
		if (path == null || currentWorkingDir == null || currentScriptDir == null || currentProject == null) {
			throw new NullPointerException();
		}
		
		// check whether the path is absolute (not depending on current OS)
		if (path.startsWith("/") || path.startsWith("\\") || path.matches("[A-Za-z]:[/\\\\].*")) {
			File file = new File(path);
			if (file.exists()) {
				return new Result(file.getParentFile(), file);
			}
		}
		else if (path.matches("\\.\\.?[/\\].*")) { // check whether the path starts with ./ or ../
			File file = new File(currentWorkingDir, path);
			if (file.exists()) {
				return new Result(file.getParentFile(), file);
			}
		} else {
			Object[] includePath = buildIncludePath(currentProject);
			for (int i = 0; i < includePath.length; ++i) {
				if (includePath[i] instanceof IContainer) {
					IContainer container = (IContainer) includePath[i];
					IResource resource = container.findMember(path);
					if (resource != null) {
						return new Result(container, resource);
					}
				}
				else if (includePath[i] instanceof IIncludePathEntry) {
					IIncludePathEntry entry = (IIncludePathEntry) includePath[i];
					IPath entryPath = entry.getPath();
					if (entry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
						if (entry.getContentKind() != IIncludePathEntry.K_BINARY) { // We don't support lookup in archive
							File entryDir = entryPath.toFile();
							File file = new File(entryDir, path);
							if (file.exists()) {
								return new Result(includePath[i], file);
							}
						}
					} else if (entry.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
						IProject project = (IProject) entry.getResource();
						IResource resource = project.findMember(path);
						if (resource != null) {
							return new Result(includePath[i], resource);
						}
					} else if (entry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
						entryPath = IncludePathVariableManager.instance().resolveVariablePath(entryPath.toString());
						File entryDir = entryPath.toFile();
						File file = new File(entryDir, path);
						if (file.exists()) {
							return new Result(includePath[i], file);
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Builds include path for searching by the given project.
	 * Result contains include path of the given project, referenced projects and their include paths.
	 * @return array of include path objects (it can be one of: IContainer, IncludePathEntry)
	 */
	public static Object[] buildIncludePath (IProject project) {
		Set<? extends Object> results = new HashSet<Object>();
		
		internalBuildIncludePath (project, results);
		
		return results.toArray();
	}
	
	private static void internalBuildIncludePath (IProject project, Set<? extends Object> results) {
	}
	
	/**
	 * Result returned by PHP search engine
	 */
	public static class Result {
		private Object container;
		private Object fileObject;
		
		public Result(Object container, Object fileObject) {
			this.container = container;
			this.fileObject = fileObject;
		}
		
		public Object getContainer() {
			return container;
		}
		
		public Object getFileObject() {
			return fileObject;
		}
	}
}
