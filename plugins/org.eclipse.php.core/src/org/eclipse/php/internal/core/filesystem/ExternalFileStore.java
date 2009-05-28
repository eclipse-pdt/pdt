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
package org.eclipse.php.internal.core.filesystem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.core.CoreMessages;

/**
 * An IFileStore that handles non-existing files (externals).
 * 
 * @author Shalom Gibly
 * @see ExternalFileWrapper
 */
public class ExternalFileStore extends FileStore {

	/*
	 * The java.io.File that this store represents.
	 */
	private File file;

	/*
	 * The file path of the file represented by this store.
	 * This path is important for the URI calculation. The path differs 
	 * from the path calculated by the LocalFile class in a way that it 
	 * does not take the absolute file path (not adding a default device 
	 * name when it's missing).
	 */
	private String filePath;

	/**
	 * Constructs a new ExternalFileStore with a given java.io.File.
	 * The given file should not exist. In case it exists, a {@link LocalFile} should be used.
	 * 
	 * @param file
	 * @throws IllegalArgumentException if the given file exists.
	 * @see FileStoreFactory
	 */
	protected ExternalFileStore(File file) throws IllegalArgumentException {
		if (file.exists()) {
			throw new IllegalArgumentException(CoreMessages.getString("ExternalFileStore_0"));
		}
		this.file = file;
		this.filePath = file.getPath();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#childNames(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public String[] childNames(int options, IProgressMonitor monitor) throws CoreException {
		return EMPTY_STRING_ARRAY;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#fetchInfo(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IFileInfo fetchInfo(int options, IProgressMonitor monitor) throws CoreException {
		//in-lined non-native implementation
		FileInfo info = new FileInfo(file.getName());
		info.setExists(false);
		return info;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#getChild(java.lang.String)
	 */
	public IFileStore getChild(String name) {
		return new ExternalFileStore(new File(file, name));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#getName()
	 */
	public String getName() {
		return file.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#getParent()
	 */
	public IFileStore getParent() {
		File parent = file.getParentFile();
		return parent == null ? null : new ExternalFileStore(parent);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#hashCode()
	 */
	public int hashCode() {
		return file.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#openInputStream(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InputStream openInputStream(int options, IProgressMonitor monitor) throws CoreException {
		return new ByteArrayInputStream(new byte[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#toURI()
	 */
	public URI toURI() {
		return URIUtil.toURI(filePath);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.filesystem.provider.FileStore#toString()
	 */
	public String toString() {
		return filePath;
	}
}
