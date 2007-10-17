package org.eclipse.php.internal.debug.core.pathmapper;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager.ContentTypeChangeEvent;
import org.eclipse.core.runtime.content.IContentTypeManager.IContentTypeChangeListener;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

public class DebugSearchEngine {
	
	private static PHPFilenameFilter PHP_FILTER = new PHPFilenameFilter();
	private static IPathEntryFilter[] filters;
	
	/**
	 * Searches for all local resources that match provided remote file, and returns it in best match order.
	 * @param remoteFile Path of the file on server
	 * @return path entry or <code>null</code> in case it could not be found
	 * @throws InterruptedException 
	 * @throws CoreException 
	 */
	public static PathEntry find(String remoteFile, IProject currentProject) throws InterruptedException, CoreException {
		
		PathMapper pathMapper = PathMapper.getInstance();
		PathEntry localFile = pathMapper.getLocalFile(remoteFile);
		if (localFile != null) {
			return localFile;
		}
		
		AbstractPath abstractPath = new AbstractPath(remoteFile);
		LinkedList<PathEntry> results = new LinkedList<PathEntry>();
		BestMatchPathComparator bmComparator = new BestMatchPathComparator(abstractPath);
		
		if (currentProject != null) {
			// Search in current project:
			find(currentProject, abstractPath, results);

			// Search in include path:
			PHPProjectOptions projectOptions = PHPProjectOptions.forProject(currentProject);
			if (projectOptions != null) {
				IIncludePathEntry[] includePath = projectOptions.readRawIncludePath();
				for (int i = 0; i < includePath.length; ++i) {
					IPath path = includePath[i].getPath();
					if (includePath[i].getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
						File file = path.toFile();
						if (includePath[i].getContentKind() != IIncludePathEntry.K_BINARY) { // We don't support lookup in archive
							find(file, abstractPath, includePath[i], results);
						}
					} else if (includePath[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
						find(includePath[i].getResource(), abstractPath, results);
					} else if (includePath[i].getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
						path = IncludePathVariableManager.instance().resolveVariablePath(path.toString());
						File file = path.toFile();
						find(file, abstractPath, includePath[i], results);
					}
				}
			}

			// Search in referenced projects:
			IProject[] projects = currentProject.getReferencedProjects();
			for (int i = 0; i < projects.length; ++i) {
				find(projects[i], abstractPath, results);
			}
		} else {
			find(ResourcesPlugin.getWorkspace().getRoot(), abstractPath, results);
		}
		
		if (results.size() > 0) {
			Collections.sort(results, bmComparator);
			localFile = filterItems (abstractPath, results.toArray(new PathEntry[results.size()]));
			if (localFile != null) {
				PathMapper.getInstance().addEntry(remoteFile, localFile);
			}
		}
		return localFile;
	}
	
	private static PathEntry filterItems (AbstractPath remotePath, PathEntry[] entries) {
		IPathEntryFilter[] filters = initializePathEntryFilters();
		for (int i = 0; i < filters.length; ++i) {
			entries = filters[i].filter(entries, remotePath);
		}
		return entries.length > 0 ?  entries[0] : null;
	}
	
	private static synchronized IPathEntryFilter[] initializePathEntryFilters() {
		if (filters == null) {
			Map<String, IPathEntryFilter> filtersMap = new HashMap<String, IPathEntryFilter>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(), "pathEntryFilters"); //$NON-NLS-1$
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement element = elements[i];
				if ("filter".equals(element.getName())) { //$NON-NLS-1$
					String id = element.getAttribute("id"); //$NON-NLS-1$
					if (!filtersMap.containsKey(id)) {
						String overridesIds = element.getAttribute("overridesId");
						if (overridesIds != null) {
							StringTokenizer st = new StringTokenizer(overridesIds, ", "); //$NON-NLS-1$
							while (st.hasMoreTokens()) {
								filtersMap.put(st.nextToken(), null);
							}
						}
						try {
							filtersMap.put(id, (IPathEntryFilter)element.createExecutableExtension("class")); //$NON-NLS-1$
						} catch (CoreException e) {
							PHPDebugPlugin.log(e);
						}
					}
				}
			}
			filters = filtersMap.values().toArray(new IPathEntryFilter[filtersMap.size()]);
		}
		return filters;
	}

	/**
	 * Searches for the path in the given IO file
	 * 
	 * @param file File to start search from
	 * @param path Abstract path of the remote file
	 * @param container Include path entry container
	 * @param results List of results to return
	 * @throws InterruptedException
	 */
	private static void find(final File file, final AbstractPath path, final IIncludePathEntry container, final List<PathEntry> results) {
		if (!file.isDirectory() && file.getName().equals(path.getLastSegment())) {
			PathEntry.Type type = (container.getEntryKind() == IncludePathEntry.IPE_VARIABLE) ? PathEntry.Type.INCLUDE_VAR : PathEntry.Type.INCLUDE_FOLDER;   
			PathEntry pathEntry = new PathEntry(file.getAbsolutePath(), type, container);
			results.add(pathEntry);
			return;
		} else {
			File[] files = file.listFiles(PHP_FILTER);
			if (files != null) {
				for (int i = 0; i < files.length; ++i) {
					find(files[i], path, container, results);
				}
			}
		}
	}

	/**
	 * Searches for the path in the given resource
	 * @param resource Resource to start the search from
	 * @param path Abstract path of the remote file
	 * @param results List of results to return
	 * @throws InterruptedException 
	 */
	private static void find(final IResource resource, final AbstractPath path, final List<PathEntry> results) throws InterruptedException {
		if (resource == null || !resource.exists() || !resource.isAccessible()) {
			return;
		}
		WorkspaceJob findJob = new WorkspaceJob("") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				resource.accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (resource.getName().equals(path.getLastSegment())) {
							PathEntry pathEntry = new PathEntry(resource.getFullPath().toOSString(), PathEntry.Type.WORKSPACE, resource.getParent());
							results.add(pathEntry);
						}
						return true;
					}
				});
				return Status.OK_STATUS;
			}
		};
		findJob.schedule();
		findJob.join();
	}
	
	private static class PHPFilenameFilter implements FileFilter, IContentTypeChangeListener {
		private Pattern phpFilePattern;

		public PHPFilenameFilter() {
			buildPHPFilePattern();
			Platform.getContentTypeManager().addContentTypeChangeListener(this);
		}
		
		private void buildPHPFilePattern() {
			IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
			String[] phpExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			StringBuilder buf = new StringBuilder();
			buf.append(".*\\.(");
			for (int i = 0; i < phpExtensions.length; ++i) {
				if (i > 0) {
					buf.append("|");
				}
				buf.append(phpExtensions[i]);
			}
			buf.append(')');
			phpFilePattern = Pattern.compile(buf.toString(), Pattern.CASE_INSENSITIVE);
		}

		public void contentTypeChanged(ContentTypeChangeEvent event) {
			buildPHPFilePattern();
		}
		
		public boolean accept(File pathname) {
			if (pathname.isDirectory() || phpFilePattern.matcher(pathname.getName()).matches()) {
				return true;
			}
			return false;
		}
	}

}
