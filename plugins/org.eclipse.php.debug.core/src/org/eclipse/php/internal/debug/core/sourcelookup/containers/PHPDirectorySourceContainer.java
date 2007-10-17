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
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.debug.core.Logger;

public class PHPDirectorySourceContainer extends DirectorySourceContainer {

	private IProject project;

	public PHPDirectorySourceContainer(File dir, boolean subfolders, IProject project) {
		super(dir, subfolders);
		this.project = project;
	}

	public PHPDirectorySourceContainer(IPath dirPath, boolean subfolders) {
		super(dirPath.toFile(), subfolders);
	}

	public Object[] findSourceElements(String name) throws CoreException {
		File file = new File(name);
		if (file.exists() && !file.isDirectory()) {
			return processObjects(new Object[] { new LocalFileStorage(file) });
		}
		Object[] lFileStorage = super.findSourceElements(name);
		return processObjects(lFileStorage);
	}

	protected Object[] findSourceElements(String name, ISourceContainer[] containers) throws CoreException {
		Object[] lFileStorage = super.findSourceElements(name, containers);
		return processObjects(lFileStorage);
	}

	public String getName() {
		return super.getName();
	}

	public ISourceContainerType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object[] processObjects(Object[] lFileStorage) {
		Object[] storage = new Object[lFileStorage.length];
		org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage lsEntry;
		org.eclipse.debug.core.sourcelookup.containers.ZipEntryStorage zipEntry;
		for (int i = 0; i < lFileStorage.length; i++) {
			if (lFileStorage[i] instanceof org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage) {
				lsEntry = (org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage) lFileStorage[i];
				LocalFileStorage lStorage = new LocalFileStorage(lsEntry.getFile());
				lStorage.setProject(project);
				try {
					lStorage.setIncBaseDirName(getDirectory().getCanonicalPath());
				} catch (IOException e) {
					Logger.logException("Unexpected error in PHPDirectorySourceContainer", e);
				}
				storage[i] = lStorage;
			} else if (lFileStorage[i] instanceof org.eclipse.debug.core.sourcelookup.containers.ZipEntryStorage) {
				zipEntry = (org.eclipse.debug.core.sourcelookup.containers.ZipEntryStorage) lFileStorage[i];
				ZipEntryStorage zStorage = new ZipEntryStorage(zipEntry.getArchive(), zipEntry.getZipEntry());
				zStorage.setProject(project);
				storage[i] = zStorage;
			} else {
				storage[i] = lFileStorage[i];
			}
		}
		return storage;
	}

}
