/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.communication;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;

/**
 * A remote file storage for use in the remote-debugging file content retrieval.
 */
public class RemoteFileStorage extends PlatformObject implements IEncodedStorage {

	private byte[] content;
	private String fileName;
	private String originalURL;

	/**
	 * Constructs a new RemoteFileStorage basing on already received file content
	 * 
	 * @param content
	 *            File content
	 * @param fileName
	 *            File name
	 */
	public RemoteFileStorage(byte[] content, String fileName, String originalURL) {
		this.content = content;
		this.fileName = fileName;
		this.originalURL = originalURL;
	}

	public String getFileName() {
		return fileName;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IStorage#getContents()
	 */
	@Override
	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IStorage#getFullPath()
	 */
	@Override
	public IPath getFullPath() {
		return Path.fromPortableString(fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IStorage#getName()
	 */
	@Override
	public String getName() {
		if (VirtualPath.isAbsolute(fileName)) {
			return new VirtualPath(fileName).getLastSegment();
		}
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IStorage#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IEncodedStorage#getCharset()
	 */
	@Override
	public String getCharset() throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.fileName.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RemoteFileStorage) {
			return ((RemoteFileStorage) obj).fileName.equals(this.fileName);
		}
		return super.equals(obj);
	}
}
