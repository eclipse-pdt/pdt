/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.pathmapper;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager.ContentTypeChangeEvent;
import org.eclipse.core.runtime.content.IContentTypeManager.IContentTypeChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.core.util.SyncObject;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping.MappingSource;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * This search engine can be used to find local equivalents of a remote file
 * with the use of path mapping facility. If there is more than one possible
 * match (no mapping exists yet) then provided search result filter is used to
 * fetch the best match.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class LocalFileSearchEngine {

	private class PHPFilenameFilter implements FileFilter, IContentTypeChangeListener {
		private Pattern phpFilePattern;

		public PHPFilenameFilter() {
			buildPHPFilePattern();
			Platform.getContentTypeManager().addContentTypeChangeListener(this);
		}

		private void buildPHPFilePattern() {
			IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
			String[] phpExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			StringBuilder buf = new StringBuilder();
			buf.append(".*\\.("); //$NON-NLS-1$
			for (int i = 0; i < phpExtensions.length; ++i) {
				if (i > 0) {
					buf.append("|"); //$NON-NLS-1$
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

	public static final String DEFAULT_FILE_SEARCH_FILTER = "org.eclipse.php.debug.openLocalFileSearchFilter"; //$NON-NLS-1$

	private LocalFileSearchEngine.PHPFilenameFilter PHP_FILTER = new PHPFilenameFilter();

	private final ILocalFileSearchFilter searchResultsFilter;

	public LocalFileSearchEngine() {
		this.searchResultsFilter = LocalFileSearchFilterRegistry.getFilter(DEFAULT_FILE_SEARCH_FILTER);
	}

	public LocalFileSearchEngine(ILocalFileSearchFilter searchResultsFilter) {
		this.searchResultsFilter = searchResultsFilter;
	}

	/**
	 * Searches for the local file equivalent of the remote file in the given
	 * workspace resource.
	 * 
	 * @param container
	 *            Resource to start the search from
	 * @param remoteFilePath
	 *            Abstract path of the remote file
	 * @param serverUniqueId
	 *            Server unique ID (is used to get mappings for related server)
	 * @return
	 * @throws InterruptedException
	 */
	public LocalFileSearchResult find(final IResource container, final String remoteFilePath,
			final String serverUniqueId) throws InterruptedException {
		if (container == null || !container.exists() || !container.isAccessible()) {
			return null;
		}
		final PathMapper pathMapper = serverUniqueId != null
				? PathMapperRegistry.getByServer(ServersManager.findServer(serverUniqueId)) : new PathMapper();
		final VirtualPath abstractPath = new VirtualPath(remoteFilePath);
		final SyncObject<LocalFileSearchResult> searchResult = new SyncObject<LocalFileSearchResult>();
		Job findJob = new Job(Messages.LocalFileSearchEngine_Searching_for_local_file) {
			protected IStatus run(IProgressMonitor monitor) {
				// First, look into the path mapper:
				LinkedList<PathEntry> results = new LinkedList<PathEntry>();
				IncludePath[] includePaths;
				IBuildpathEntry[] buildPaths = null;
				// Search in the whole workspace:
				Set<IncludePath> s = new LinkedHashSet<IncludePath>();
				Set<IBuildpathEntry> b = new LinkedHashSet<IBuildpathEntry>();
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
				for (IProject project : projects) {
					if (project.isOpen() && project.isAccessible()) {
						// get include paths of all projects
						PHPSearchEngine.buildIncludePath(project, s);
						// get build paths of all projects
						IScriptProject scriptProject = DLTKCore.create(project);
						if (scriptProject != null && scriptProject.isOpen()) {
							try {
								IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();
								for (IBuildpathEntry pathEntry : rawBuildpath) {
									b.add(pathEntry);
								}
							} catch (ModelException e) {
								PHPDebugPlugin.log(e);
							}

						}
					}
				}
				includePaths = s.toArray(new IncludePath[s.size()]);
				buildPaths = b.toArray(new IBuildpathEntry[b.size()]);
				// Try to find this file in the Workspace:
				try {
					IPath path = Path.fromOSString(remoteFilePath);
					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);
					if (file != null && file.exists()) {
						for (IncludePath includePath : includePaths) {
							if (includePath.getEntry() instanceof IContainer) {
								IContainer container = (IContainer) includePath.getEntry();
								if (container.getFullPath().isPrefixOf(file.getFullPath())) {
									PathEntry localFile = new PathEntry(file.getFullPath().toString(), Type.WORKSPACE,
											file.getParent());
									pathMapper.addEntry(remoteFilePath, localFile, MappingSource.ENVIRONMENT);
									PathMapperRegistry.storeToPreferences();
									searchResult.set(new LocalFileSearchResult(localFile));
									return Status.OK_STATUS;
								}
							}
						}
					}
				} catch (Exception e) {
					// no need to catch
				}
				// Try to find in build paths
				if (buildPaths != null) {
					for (IBuildpathEntry entry : buildPaths) {
						IPath entryPath = EnvironmentPathUtils.getLocalPath(entry.getPath());
						if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
							// We don't support lookup in archive
							File entryDir = entryPath.toFile();
							find(entryDir, abstractPath, entry, results);
						} else if (entry.getEntryKind() == IBuildpathEntry.BPE_PROJECT
								|| entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
							IResource res = ResourcesPlugin.getWorkspace().getRoot()
									.findMember(entry.getPath().lastSegment());
							if (res instanceof IProject) {
								IProject project = (IProject) res;
								if (project.isOpen() && project.isAccessible()) {
									try {
										find(project, abstractPath, results);
									} catch (InterruptedException e) {
										PHPDebugPlugin.log(e);
									}
								}
							}
						} else if (entry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
							entryPath = DLTKCore.getResolvedVariablePath(entryPath);
							if (entryPath != null) {
								File entryDir = entryPath.toFile();
								find(entryDir, abstractPath, entry, results);
							}
						} else if (entry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
							try {
								if (projects.length == 0) {
									continue;
								}
								final IProject currentProject = projects[0];
								final IScriptProject scriptProject = DLTKCore.create(currentProject);
								IBuildpathContainer container = DLTKCore.getBuildpathContainer(entry.getPath(),
										scriptProject);
								if (container != null) {
									IBuildpathEntry[] buildpathEntries = container.getBuildpathEntries();
									entryPath = EnvironmentPathUtils.getLocalPath(buildpathEntries[0].getPath());
									if (entryPath != null) {
										find(entryPath.toFile(), abstractPath, entry, results);
									}
								}
							} catch (ModelException e) {
								PHPCorePlugin.log(e);
							}
						}
					}
				}
				// Iterate all include path, and search for a requested file
				for (IncludePath includePath : includePaths) {
					if (includePath.getEntry() instanceof IContainer) {
						try {
							find((IContainer) includePath.getEntry(), abstractPath, results);
						} catch (InterruptedException e) {
							PHPDebugPlugin.log(e);
						}
					}
				}
				boolean foundInWorkspace = results.size() > 0;
				if (!foundInWorkspace && results.size() == 1
						&& abstractPath.equals(results.getFirst().getAbstractPath())) {
					searchResult.set(new LocalFileSearchResult(results.getFirst()));
				} else if (results.size() > 0) {
					Collections.sort(results, new BestMatchPathComparator(abstractPath));
					LocalFileSearchResult filteredResult = filter(results.toArray(new PathEntry[results.size()]),
							abstractPath, serverUniqueId);
					if (filteredResult.getPathEntry() != null && filteredResult.getStatus().isOK()) {
						pathMapper.addEntry(remoteFilePath, filteredResult.getPathEntry(), MappingSource.USER);
						PathMapperRegistry.storeToPreferences();
					}
					searchResult.set(filteredResult);
				}
				return Status.OK_STATUS;
			}
		};
		// Join the job...
		findJob.schedule();
		try {
			findJob.join();
		} catch (InterruptedException e) {
		}
		return searchResult.get();
	}

	private LocalFileSearchResult filter(final PathEntry[] entries, final VirtualPath remotePath,
			final String serverUniqueId) {
		return searchResultsFilter.filter(entries, remotePath, serverUniqueId);
	}

	/**
	 * Searches for the path in the given resource
	 * 
	 * @param resource
	 *            Resource to start the search from
	 * @param path
	 *            Abstract path of the remote file
	 * @param results
	 *            List of results to return
	 * @throws InterruptedException
	 */
	private void find(final IResource resource, final VirtualPath path, final List<PathEntry> results)
			throws InterruptedException {
		if (resource == null || !resource.exists() || !resource.isAccessible()) {
			return;
		}
		WorkspaceJob findJob = new WorkspaceJob("") { //$NON-NLS-1$
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				resource.accept(new IResourceVisitor() {
					public boolean visit(IResource resource) throws CoreException {
						if (!resource.isAccessible()) {
							return false;
						}
						if (resource instanceof IFile && resource.getName().equals(path.getLastSegment())) {
							PathEntry pathEntry = new PathEntry(resource.getFullPath().toString(), Type.WORKSPACE,
									resource.getParent());
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

	/**
	 * Searches for the path in the given IO file
	 * 
	 * @param file
	 *            File to start search from
	 * @param path
	 *            Abstract path of the remote file
	 * @param container
	 *            Include path entry container
	 * @param results
	 *            List of results to return
	 * @throws InterruptedException
	 */
	private void find(final File file, final VirtualPath path, final IBuildpathEntry container,
			final List<PathEntry> results) {
		if (!file.isDirectory() && file.getName().equals(path.getLastSegment())) {
			Type type = (container.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) ? Type.INCLUDE_VAR
					: Type.INCLUDE_FOLDER;
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

}