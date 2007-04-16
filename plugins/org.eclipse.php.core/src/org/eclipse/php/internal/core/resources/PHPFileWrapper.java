/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.core.resources;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

/**
 * A PHPFile is a File wrapper that allows the setting of a device name.
 * This {@link PHPFileWrapper} is useful when dealing with non-workspace files (externals).
 * 
 * @author shalom
 */
public class PHPFileWrapper extends File {

	private String device;

	public PHPFileWrapper(IFile file, String device) {
		super(file.getFullPath(), (Workspace) file.getWorkspace());
		this.device = device;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IFile#getFullPath()
	 */
	public IPath getFullPath() {
		IPath path = super.getFullPath();
		return path.setDevice(device);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResource#getLocation()
	 */
	public IPath getLocation() {
		IPath location = super.getLocation();
		if (location == null) {
			location = getFullPath();
		}
		return location;
	}

}
