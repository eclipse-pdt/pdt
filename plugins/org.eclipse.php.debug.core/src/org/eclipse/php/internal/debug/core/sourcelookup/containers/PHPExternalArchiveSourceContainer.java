/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ExternalArchiveSourceContainer;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;

public class PHPExternalArchiveSourceContainer extends AbstractSourceContainer {

    ExternalArchiveSourceContainer fContainer;
    IProject project;

    public PHPExternalArchiveSourceContainer(String archivePath, boolean detectRootPaths, IProject project) {

        fContainer = new ExternalArchiveSourceContainer(archivePath, detectRootPaths);
        this.project = project;
    }

    public String getName() {
        return fContainer.getName();
    }

    public Object[] findSourceElements(String name) throws CoreException {
        Object[] zipStorage = fContainer.findSourceElements(name);
        ZipEntryStorage[] PHPZipEntryStorage = new ZipEntryStorage[zipStorage.length];
        org.eclipse.debug.core.sourcelookup.containers.ZipEntryStorage entry;
        for (int i = 0; i < zipStorage.length; i++) {
            entry = (org.eclipse.debug.core.sourcelookup.containers.ZipEntryStorage) zipStorage[i];
            ZipEntryStorage zStorage = new ZipEntryStorage(entry.getArchive(), entry.getZipEntry());
            zStorage.setProject(project);
            PHPZipEntryStorage[i] = zStorage;
        }
        return PHPZipEntryStorage;
    }

    public ISourceContainerType getType() {
        // TODO Auto-generated method stub
        return null;
    }

}
