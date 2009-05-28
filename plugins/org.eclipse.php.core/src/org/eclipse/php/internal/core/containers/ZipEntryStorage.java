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
package org.eclipse.php.internal.core.containers;


import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * Storage implementation for zip entries.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * @see IStorage
 * @since 3.0
 */
public class ZipEntryStorage extends PlatformObject implements IStorage {

	/**
	 * Zip file associated with zip entry
	 */
	private ZipFile fArchive;

	/**
	 * Zip entry
	 */
	private ZipEntry fZipEntry;

    /**
     * project that contains the include Path
     */
    private IProject fProject;

	/**
	 * Constructs a new storage implementation for the
	 * given zip entry in the specified zip file
	 *
	 * @param archive zip file
	 * @param entry zip entry
	 */
	public ZipEntryStorage(ZipFile archive, ZipEntry entry) {
		setArchive(archive);
		setZipEntry(entry);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IStorage#getContents()
	 */
	public InputStream getContents() throws CoreException {
		try {
			return getArchive().getInputStream(getZipEntry());
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, PHPCorePlugin.getPluginId(), PHPCorePlugin.INTERNAL_ERROR, CoreMessages.getString("zipEntryStorage_error"), e)); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IStorage#getFullPath()
	 */
	public IPath getFullPath() {
		return new Path(getArchive().getName()).append(getZipEntry().getName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IStorage#getName()
	 */
	public String getName() {
		String zipEntryName = getZipEntry().getName();
		int index = Math.max(zipEntryName.lastIndexOf('\\'), zipEntryName.indexOf('/'));
		if (index == -1) {
			return zipEntryName;
		}
		return zipEntryName.substring(index + 1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IStorage#isReadOnly()
	 */
	public boolean isReadOnly() {
		return true;
	}

	/**
	 * Sets the archive containing the zip entry.
	 *
	 * @param archive a zip file
	 */
	private void setArchive(ZipFile archive) {
		fArchive = archive;
	}

	/**
	 * Returns the archive containing the zip entry.
	 *
	 * @return zip file
	 */
	public ZipFile getArchive() {
		return fArchive;
	}

	/**
	 * Sets the entry that contains the source.
	 *
	 * @param entry the entry that contains the source
	 */
	private void setZipEntry(ZipEntry entry) {
		fZipEntry = entry;
	}

	/**
	 * Returns the entry that contains the source
	 *
	 * @return zip entry
	 */
	public ZipEntry getZipEntry() {
		return fZipEntry;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object) {
		return object instanceof ZipEntryStorage &&
			 getArchive().getName().equals(((ZipEntryStorage)object).getArchive().getName()) &&
			 getZipEntry().getName().equals(((ZipEntryStorage)object).getZipEntry().getName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getZipEntry().getName().hashCode();
	}

    /**
     * Sets the project that contains the Include Path.
     *
     * @param project the IProject that contains the include Path
     */
    public void setProject(IProject project) {
        fProject = project;
    }

    /**
     * Returns the project that contains the Include Path
     *
     * @return IProject that contains the Include Path
     */
    public IProject getProject() {
        return fProject;
    }
}
