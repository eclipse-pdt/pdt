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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.LinkedList;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.dltk.core.IArchiveEntry;
import org.eclipse.dltk.core.IModelStatusConstants;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.Openable;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.phar.PharArchiveFile;
import org.eclipse.php.internal.core.phar.PharPath;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;

/**
 * The PHP source lookup participant knows how to translate a PHP stack frame
 * into a source file name
 */
public class PHPSourceLookupParticipant extends AbstractSourceLookupParticipant {

	private static final class LinkedFilesFinder {

		public static Object find(final String sourceLocation) {
			final LinkedList<IResource> matches = new LinkedList<IResource>();
			final java.nio.file.Path sourceLocationPath = FileSystems.getDefault().getPath(sourceLocation);
			final String sourceFileName = (new Path(sourceLocation)).lastSegment();
			try {
				long tb = System.currentTimeMillis();
				ResourcesPlugin.getWorkspace().getRoot().accept(new IResourceVisitor() {
					@Override
					public boolean visit(IResource resource) throws CoreException {
						try {
							if (resource.getType() != IResource.FILE) {
								return true;
							}
							if (resource.getName().equals(sourceFileName) || resource.isLinked()) {
								/*
								 * Use NIO libraries to handle comparison of
								 * files that might contains symbolic links in
								 * their paths.
								 */
								String fileLocation = resource.getLocation().toOSString();
								java.nio.file.Path currentFilePath = FileSystems.getDefault().getPath(fileLocation); // $NON-NLS-1$
								if (Files.isSameFile(sourceLocationPath, currentFilePath)) {
									matches.add(resource);
								}
							}
						} catch (IOException e) {
							PHPDebugPlugin.log(e);
						}
						return true;
					}
				});
				long ta = System.currentTimeMillis();
				System.err.println(ta - tb);
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
				// Check if it is linked file (or file from linked directory)
				Object linkedFile = LinkedFilesFinder.find(sourceFilePath);
				if (linkedFile != null) {
					return new Object[] { linkedFile };
				}
				// Check if it is local non-workspace file
				File file = new File(sourceFilePath);
				if (file.exists()) {
					return new Object[] { new LocalFile(file) };
				}
				// Check if it is not a file from PHAR
				final PharPath pharPath = PharPath.getPharPath(new Path(sourceFilePath));
				if (pharPath != null) {
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
				// Nothing from above
				return EMPTY;
			}
		}
		return sourceElements;
	}

}
