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
package org.eclipse.php.debug.core.sourcelookup.containers;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainer;
import org.eclipse.php.core.containers.LocalFileStorage;

public class PHPFileSourceContainer extends AbstractSourceContainer {

    private File fFile;
    private LocalFileStorage fStorage;
    private IProject project;       

    public PHPFileSourceContainer(File file, IProject project) {
        fFile = file;
        this.project = project;
        fStorage = new LocalFileStorage(fFile);
        fStorage.setProject(project);
    }

    public Object[] findSourceElements(String name) throws CoreException {
        return new Object[] { fStorage };
    }

    public String getName() {
        return fFile.getName();
    }

    public ISourceContainerType getType() {
        // TODO Auto-generated method stub
        return null;
    }

}
