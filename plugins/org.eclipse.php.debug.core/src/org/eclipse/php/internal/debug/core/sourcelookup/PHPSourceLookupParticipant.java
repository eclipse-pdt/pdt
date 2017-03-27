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
package org.eclipse.php.internal.debug.core.sourcelookup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.internal.core.sourcelookup.SourceLookupMessages;
import org.eclipse.dltk.core.IArchiveEntry;
import org.eclipse.dltk.core.IModelStatusConstants;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.Openable;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.PHPSymbolicLinksCache;
import org.eclipse.php.internal.core.phar.PharArchiveFile;
import org.eclipse.php.internal.core.phar.PharPath;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.core.util.SyncObject;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.zend.communication.IRemoteFileContentRequestor;
import org.eclipse.php.internal.debug.core.zend.communication.RemoteFileStorage;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;

/**
 * The PHP source lookup participant knows how to translate a PHP stack frame
 * into a source file name
 */
public class PHPSourceLookupParticipant extends AbstractSourceLookupParticipant {

	private LinkSubjectFileFinder linkSubjectFileFinder = new LinkSubjectFileFinder();
	private Map<String, RemoteFileStorage> remoteStorageCache;

	public PHPSourceLookupParticipant() {
		remoteStorageCache = new HashMap<String, RemoteFileStorage>();
	}

	/**
	 * Helper class for finding workspace files that might point to provided
	 * source location by means of being a linked/symbolic link files or be a
	 * part of the chained structure that might consist of linked/symbolic link
	 * directories/files.
	 */
	private final class LinkSubjectFileFinder {

		public Object find(final String sourceLocation, Object element) {
			final LinkedList<IResource> matches = new LinkedList<IResource>();
			final String sourceFileName = (new Path(sourceLocation)).lastSegment();
			IProject project = null;
			if (element instanceof IStackFrame) {
				IDebugTarget target = ((IStackFrame) element).getDebugTarget();
				project = PHPLaunchUtilities.getProject(target);
			}
			final IResource scope = project != null ? project : ResourcesPlugin.getWorkspace().getRoot();
			try {
				scope.accept(new IResourceVisitor() {
					@Override
					public boolean visit(IResource resource) throws CoreException {
						try {
							// Retreat if we already have a match
							if (!matches.isEmpty()) {
								return false;
							}
							// We are looking for files only
							if (resource.getType() != IResource.FILE) {
								return true;
							}
							/*
							 * The goal of this pre-check condition is to reduce
							 * the amount of files to be checked by NIO
							 * (comparing with NIO can be time consuming ).
							 */
							if (resource.getName().equals(sourceFileName) || resource.isLinked()
									|| PHPSymbolicLinksCache.INSTANCE.isSymbolicLink(resource)) {
								if (resource.getLocation() != null
										&& FileUtils.isSameFile(sourceLocation, resource.getLocation().toOSString())) {
									matches.add(resource);
								}
							}
						} catch (IOException e) {
							PHPDebugPlugin.log(e);
						}
						return true;
					}
				});
			} catch (CoreException e) {
				PHPDebugPlugin.log(e);
			}
			return !matches.isEmpty() ? matches.getFirst() : null;
		}

	}

	private static final class ExternalEntryFile extends PlatformObject implements IStorage {

		private String fileName;
		private IArchiveEntry entry;
		private PharArchiveFile archiveFile;

		public ExternalEntryFile(String fileName, PharArchiveFile archiveFile, IArchiveEntry entry) {
			this.fileName = fileName;
			this.entry = entry;
			this.archiveFile = archiveFile;
		}

		public InputStream getContents() throws CoreException {
			try {
				return this.archiveFile.getInputStream(entry);
			} catch (IOException e) {
				throw new ModelException(e, IModelStatusConstants.IO_EXCEPTION);
			}
		}

		/**
		 * @see IStorage#getFullPath
		 */
		public IPath getFullPath() {
			return new Path(this.fileName);
		}

		/**
		 * @see IStorage#getName
		 */
		public String getName() {
			return this.entry.getName();
		}

		/**
		 * @see IStorage#isReadOnly()
		 */
		public boolean isReadOnly() {
			return true;
		}

		/**
		 * @see IStorage#isReadOnly()
		 */
		public String toString() {
			return "ExternalEntryFile[" + this.fileName + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ExternalEntryFile))
				return false;
			ExternalEntryFile other = (ExternalEntryFile) obj;
			if (!fileName.toLowerCase().equals(other.fileName.toLowerCase()))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			return fileName.toLowerCase().hashCode();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.internal.core.sourcelookup.ISourceLookupParticipant
	 * #getSourceName(java.lang.Object)
	 */
	public String getSourceName(Object object) throws CoreException {
		if (object instanceof PHPStackFrame) {
			return ((PHPStackFrame) object).getSourceName();
		}
		if (object instanceof DBGpStackFrame) {
			String sourceName = ((DBGpStackFrame) object).getSourceName();
			if (sourceName == null) {
				sourceName = ((DBGpStackFrame) object).getQualifiedFile();
				IPath path = new Path(sourceName);
				sourceName = path.lastSegment();
			}
			return sourceName;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant#
	 * findSourceElements(java.lang.Object)
	 */
	public Object[] findSourceElements(Object object) throws CoreException {
		try {
			// Check if the source should be retrieved from the server.
			// If so, get it.
			Object[] sourceElements = getRemoteSourceElements(object, false);
			if (sourceElements != EMPTY) {
				return sourceElements;
			}
		} catch (CoreException ce) {
		}
		// In case that the source should be obtained from the local client, or,
		// in case that the source was
		// not found on the client, perform the code below.
		try {
			// Try to get it locally.
			Object[] result = getLocalSourceElements(object);
			if (result != EMPTY) {
				return result;
			}
		} catch (CoreException ce) {
		}

		List<Object> results = new ArrayList<Object>();
		CoreException single = null;
		MultiStatus multiStatus = null;
		String name = getSourceName(object);
		if (name != null) {
			ISourceContainer[] containers = getSourceContainers();
			for (int i = 0; i < containers.length; i++) {
				try {
					ISourceContainer container = getDelegateContainer(containers[i]);
					if (container != null) {
						Object[] objects = container.findSourceElements(name);
						if (objects.length > 0) {
							if (isFindDuplicates()) {
								for (int j = 0; j < objects.length; j++) {
									results.add(objects[j]);
								}
							} else {
								if (objects.length == 1) {
									return objects;
								}
								return new Object[] { objects[0] };
							}
						} else {
							// Try to get the file remotely from the server
							return getRemoteSourceElements(object, true);
						}
					}
				} catch (CoreException e) {
					if (single == null) {
						single = e;
					} else if (multiStatus == null) {
						multiStatus = new MultiStatus(PHPDebugPlugin.ID, PHPDebugPlugin.INTERNAL_ERROR,
								new IStatus[] { single.getStatus() }, SourceLookupMessages.Source_Lookup_Error, null);
						multiStatus.add(e.getStatus());
					} else {
						multiStatus.add(e.getStatus());
					}
				}
			}
		}
		if (results.isEmpty()) {
			if (multiStatus != null) {
				throw new CoreException(multiStatus);
			} else if (single != null) {
				throw single;
			}
			return EMPTY;
		}
		return results.toArray();
	}

	@Override
	public void dispose() {
		super.dispose();
		remoteStorageCache.clear();
		remoteStorageCache = null;
	}

	private Object[] getLocalSourceElements(Object object) throws CoreException {
		Object[] sourceElements = EMPTY;
		try {
			sourceElements = super.findSourceElements(object);
		} catch (CoreException e) {
			// Check if the lookup failed because the source is outside the
			// workspace.
		}
		if (sourceElements == EMPTY) {
			// If the lookup returned an empty elements array, check if the
			// source is outside the workspace.
			String sourceFilePath = null;
			if (object instanceof PHPStackFrame) {
				sourceFilePath = ((PHPStackFrame) object).getSourceName();
			} else if (object instanceof DBGpStackFrame) {
				sourceFilePath = ((DBGpStackFrame) object).getQualifiedFile();
			}
			if (sourceFilePath != null) {
				// Check if we have it in DLTK model
				HandleFactory handleFactory = new HandleFactory();
				IDLTKSearchScope scope = SearchEngine.createWorkspaceScope(PHPLanguageToolkit.getDefault());
				IPath localPath = EnvironmentPathUtils.getFile(LocalEnvironment.getInstance(), new Path(sourceFilePath))
						.getFullPath();
				Openable openable = handleFactory.createOpenable(localPath.toString(), scope);
				if (openable instanceof IStorage) {
					return new Object[] { openable };
				}
				// Check if it is not a file from PHAR
				final PharPath pharPath = PharPath.getPharPath(new Path(sourceFilePath));
				if (pharPath != null && !pharPath.getFile().isEmpty()) {
					try {
						final PharArchiveFile archiveFile = new PharArchiveFile(pharPath.getPharName());
						final IArchiveEntry entry = archiveFile.getArchiveEntry((pharPath.getFolder().length() == 0 ? "" //$NON-NLS-1$
								: pharPath.getFolder() + "/") //$NON-NLS-1$
								+ pharPath.getFile());
						return new Object[] { new ExternalEntryFile(sourceFilePath, archiveFile, entry) };
					} catch (Exception e) {
						PHPDebugPlugin.log(e);
					}
				}
				// Check if file exists before moving on to the next checks
				File file = new File(sourceFilePath);
				if (!file.exists()) {
					return EMPTY;
				}
				// Check if we have corresponding "linked chain subject" file
				Object linkedFile = linkSubjectFileFinder.find(sourceFilePath, object);
				if (linkedFile != null) {
					return new Object[] { linkedFile };
				}
				// None from above succeeded - just open external file
				return new Object[] { new LocalFile(file) };
			}
		}
		return sourceElements;
	}

	private Object[] getRemoteSourceElements(Object stackFrameObj, boolean forceRetrieval) throws CoreException {

		if (stackFrameObj instanceof PHPStackFrame) {

			PHPStackFrame stackFrame = (PHPStackFrame) stackFrameObj;
			ILaunchConfiguration launchConfiguration = stackFrame.getLaunch().getLaunchConfiguration();

			if (launchConfiguration != null) {
				if (forceRetrieval
						|| launchConfiguration.getAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, false)) {
					// Return a Remote
					PHPDebugTarget debugTarget = (PHPDebugTarget) stackFrame.getDebugTarget();

					String fileName = stackFrame.getAbsoluteFileName();
					RemoteFileStorage fileStorage = (RemoteFileStorage) remoteStorageCache.get(fileName);

					if (fileStorage == null) {

						RemoteDebugger remoteDebugger = (RemoteDebugger) debugTarget.getRemoteDebugger();
						String decodedFileName;
						try {
							decodedFileName = URLDecoder.decode(fileName, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							decodedFileName = fileName;
						}

						String originalURL = debugTarget.getLaunch().getAttribute(IDebugParametersKeys.ORIGINAL_URL);

						fileStorage = getRemoteFileStorage(remoteDebugger, decodedFileName, originalURL);

						remoteStorageCache.put(fileName, fileStorage);
					}
					return new Object[] { fileStorage };
				}
			}
		}
		return EMPTY;
	}

	private RemoteFileStorage getRemoteFileStorage(RemoteDebugger remoteDebugger, final String fileName,
			final String originalURL) {
		final SyncObject<RemoteFileStorage> syncObject = new SyncObject<>();
		final CountDownLatch waitForContentLatch = new CountDownLatch(1);
		if (remoteDebugger == null || !remoteDebugger.isActive()) {
			return null;
		}
		RemoteDebugger.requestRemoteFile(new IRemoteFileContentRequestor() {
			public void fileContentReceived(byte[] content, String serverAddress, String originalURL, String fileName,
					int lineNumber) {
				syncObject.set(new RemoteFileStorage(content, fileName, originalURL));
			}

			public void requestCompleted(Exception e) {
				waitForContentLatch.countDown();
			}
		}, fileName, 0, originalURL);
		try {
			waitForContentLatch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		return syncObject.get();
	}

}
