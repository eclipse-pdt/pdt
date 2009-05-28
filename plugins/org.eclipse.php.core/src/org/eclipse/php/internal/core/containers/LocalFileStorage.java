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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
 * Implementation of storage for a local file (<code>java.io.File</code>).
 * We use this class for included files, that's why {@link #isReadOnly()} returns
 * always <code>true</code>
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * @see IStorage
 * @since 3.0
 */
public class LocalFileStorage extends PlatformObject implements IStorage {

    /**
     * The file this storage refers to.
     */
    private File fFile;

    /**
     * project that contains the include Path
     */
    private IProject fProject;

    /**
     * The base directory specified when creating the include entry
     */
    private String fIncBaseDirName;

    /**
     * Constructs and returns storage for the given file.
     * 
     * @param file a local file
     */
    public LocalFileStorage(File file) {
        setFile(file);
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IStorage#getContents()
     */
    public InputStream getContents() throws CoreException {
        try {
            return new FileInputStream(getFile());
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR, PHPCorePlugin.getPluginId(), PHPCorePlugin.INTERNAL_ERROR, CoreMessages.getString("zipEntryStorage_error"), e)); //$NON-NLS-1$
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IStorage#getFullPath()
     */
    public IPath getFullPath() {
        try {
            return new Path(getFile().getCanonicalPath());
        } catch (IOException e) {
            PHPCorePlugin.log(e);
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IStorage#getName()
     */
    public String getName() {
        return getFile().getName();
    }

    /**
     * Returns always <code>true</code>, since this class is used for
     * storing included files.
     */
    public boolean isReadOnly() {
        return true;
    }

    /**
     * Sets the file associated with this storage
     * 
     * @param file a local file
     */
    private void setFile(File file) {
        fFile = file;
    }

    /**
     * Returns the file asscoiated with this storage
     * 
     * @return file
     */
    public File getFile() {
        return fFile;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        return object instanceof LocalFileStorage && getFile().equals(((LocalFileStorage) object).getFile());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getFile().hashCode();
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

    /**
     * Sets the base directory specified when creating the include entry.
     * 
     * @param incDir the base directory specified when creating the include entry
     */
    public void setIncBaseDirName(String incDirName) {
        if (incDirName == null) return;
        try {
            fIncBaseDirName = new File(incDirName).getCanonicalPath();
        } catch (IOException e) {
            PHPCorePlugin.log(e);
        }
    }

    /**
     * Returns the base directory specified when creating the include entry
     * 
     * @return Srring that contains the base directory specified when creating the include entry
     */
    public String getIncBaseDirName() {
        return fIncBaseDirName;
    }
}
