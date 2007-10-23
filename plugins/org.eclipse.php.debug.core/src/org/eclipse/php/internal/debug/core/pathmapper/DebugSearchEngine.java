package org.eclipse.php.internal.debug.core.pathmapper;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager.ContentTypeChangeEvent;
import org.eclipse.core.runtime.content.IContentTypeManager.IContentTypeChangeListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.core.util.PHPSearchEngine.ExternalFileResult;
import org.eclipse.php.internal.core.util.PHPSearchEngine.IncludedFileResult;
import org.eclipse.php.internal.core.util.PHPSearchEngine.ResourceResult;
import org.eclipse.php.internal.core.util.PHPSearchEngine.Result;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class DebugSearchEngine {

	private static PHPFilenameFilter PHP_FILTER = new PHPFilenameFilter();
	private static IPathEntryFilter[] filters;

	/**
	 * Searches for all local resources that match provided remote file, and returns it in best match order.
	 * @param remoteFile Path of the file on server. This argument must not be <code>null</code>.
	 * @param launchConfiguration Launch configuration for the debug session
	 * @return path entry or <code>null</code> in case it could not be found
	 * @throws InterruptedException
	 * @throws CoreException
	 */
	public static PathEntry find(String remoteFile, ILaunchConfiguration launchConfiguration) throws InterruptedException, CoreException {
		PathEntry pathEntry = null;
		String projectName = launchConfiguration.getAttribute(IPHPConstants.PHP_Project, (String) null);
		if (projectName != null) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			PathMapper pathMapper = PathMapperRegistry.getByLaunchConfiguration(launchConfiguration);
			pathEntry = find(pathMapper, remoteFile, project, null, null);
		}
		return pathEntry;
	}

	/**
	 * Searches for all local resources that match provided remote file, and returns it in best match order.
	 * @param pathMapper Path mapper to look at
	 * @param remoteFile Path of the file on server. This argument must not be <code>null</code>.
	 * @param currentWorkingDir Current working directory of PHP process
	 * @param currentScriptDir Directory of current PHP file
	 * @return path entry or <code>null</code> in case it could not be found
	 * @throws InterruptedException
	 * @throws CoreException
	 */
	private static PathEntry find(PathMapper pathMapper, String remoteFile, IProject currentProject, String currentWorkingDir, String currentScriptDir) throws InterruptedException, CoreException {
		if (remoteFile == null) {
			throw new NullPointerException();
		}

		// Look into the path mapper:
		PathEntry localFile = pathMapper.getLocalFile(remoteFile);
		if (localFile != null) {
			return localFile;
		}

		AbstractPath abstractPath;
		try {
			abstractPath = new AbstractPath(remoteFile);
		} catch (IllegalArgumentException e) {
			if (currentProject != null && currentWorkingDir != null && currentScriptDir != null) {
				// This is not a full path, search using PHP Search Engine:
				Result<?, ?> result = PHPSearchEngine.find(remoteFile, currentWorkingDir, currentScriptDir, currentProject);
				if (result instanceof ExternalFileResult) {
					ExternalFileResult extFileResult = (ExternalFileResult) result;
					return new PathEntry(extFileResult.getFile().getAbsolutePath(), Type.EXTERNAL, extFileResult.getContainer());
				}
				if (result instanceof IncludedFileResult) {
					IncludedFileResult incFileResult = (IncludedFileResult) result;
					IIncludePathEntry container = incFileResult.getContainer();
					Type type = (container.getEntryKind() == IncludePathEntry.IPE_VARIABLE) ? Type.INCLUDE_VAR : Type.INCLUDE_FOLDER;
					return new PathEntry(incFileResult.getFile().getAbsolutePath(), type, container);
				}
				// workspace file
				ResourceResult resResult = (ResourceResult) result;
				IResource resource = resResult.getFile();
				return new PathEntry(resource.getFullPath().toOSString(), Type.WORKSPACE, resource.getParent());
			}
			return null;
		}

		LinkedList<PathEntry> results = new LinkedList<PathEntry>();
		BestMatchPathComparator bmComparator = new BestMatchPathComparator(abstractPath);

		Object[] includePaths;
		if (currentProject != null) {
			includePaths = PHPSearchEngine.buildIncludePath(currentProject);
		} else {
			// Search in the whole workspace:
			Set<Object> s = new HashSet<Object>();
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject project : projects) {
				PHPSearchEngine.buildIncludePath(project, s);
			}
			includePaths = s.toArray();
		}

		// Iterate over all include path, and search for a requested file
		for (Object includePath : includePaths) {
			if (includePath instanceof IContainer) {
				find((IContainer) includePath, abstractPath, results);
			} else if (includePath instanceof IIncludePathEntry) {
				IIncludePathEntry entry = (IIncludePathEntry) includePath;
				IPath entryPath = entry.getPath();
				if (entry.getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
					if (entry.getContentKind() != IIncludePathEntry.K_BINARY) { // We don't support lookup in archive
						File entryDir = entryPath.toFile();
						find(entryDir, abstractPath, entry, results);
					}
				} else if (entry.getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
					IProject project = (IProject) entry.getResource();
					if (project.isAccessible()) {
						find(project, abstractPath, results);
					}
				} else if (entry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
					entryPath = IncludePathVariableManager.instance().resolveVariablePath(entryPath.toString());
					File entryDir = entryPath.toFile();
					find(entryDir, abstractPath, entry, results);
				}
			}
		}

		//search in opened editors
		searchOpenedEditors(results, remoteFile);

		if (results.size() > 0) {
			Collections.sort(results, bmComparator);
			localFile = filterItems(abstractPath, results.toArray(new PathEntry[results.size()]));
			if (localFile != null) {
				pathMapper.addEntry(remoteFile, localFile);
			}
		}
		return localFile;
	}

	private static void searchOpenedEditors(final LinkedList<PathEntry> results, final String remoteFile) {
		PHPUiPlugin.getDefault().getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				IEditorReference[] editors = PHPUiPlugin.getActivePage().getEditorReferences();
				for (IEditorReference editor : editors) {
					String fileName = "";
					IEditorInput editorInput = null;
					try {
						editorInput = editor.getEditorInput();
					} catch (PartInitException e) {
						continue;
					}
					if (editorInput instanceof FileEditorInput) {
						FileEditorInput input = (FileEditorInput) editorInput;
						IPath location = input.getFile().getLocation();
						if (location == null) {
							fileName = input.getFile().getLocationURI().toString();
						} else {
							fileName = location.toString();
						}
					} else if (editorInput instanceof FileStoreEditorInput) {
						FileStoreEditorInput input = (FileStoreEditorInput) editorInput;
						fileName = URIUtil.toPath(input.getURI()).toString();
					}
					if (new Path(remoteFile).lastSegment().equalsIgnoreCase(new Path(fileName).lastSegment())) {
						results.add(new PathEntry(fileName, PathEntry.Type.EXTERNAL, null));
					}
				}
			}
		});
	}

	private static PathEntry filterItems(AbstractPath remotePath, PathEntry[] entries) {
		IPathEntryFilter[] filters = initializePathEntryFilters();
		for (int i = 0; i < filters.length; ++i) {
			entries = filters[i].filter(entries, remotePath);
		}
		return entries.length > 0 ? entries[0] : null;
	}

	private static synchronized IPathEntryFilter[] initializePathEntryFilters() {
		if (filters == null) {
			Map<String, IPathEntryFilter> filtersMap = new HashMap<String, IPathEntryFilter>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(), "pathEntryFilters"); //$NON-NLS-1$
			for (IConfigurationElement element : elements) {
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
							filtersMap.put(id, (IPathEntryFilter) element.createExecutableExtension("class")); //$NON-NLS-1$
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
			Type type = (container.getEntryKind() == IncludePathEntry.IPE_VARIABLE) ? Type.INCLUDE_VAR : Type.INCLUDE_FOLDER;
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
							PathEntry pathEntry = new PathEntry(resource.getFullPath().toOSString(), Type.WORKSPACE, resource.getParent());
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
