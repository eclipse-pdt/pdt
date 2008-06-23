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

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;

public class PHPVariableSourceContainer extends CompositeSourceContainer {

    private IPath fPath;
    private String[] validExtensions;
    private IProject project;       

    public PHPVariableSourceContainer(IPath path, IProject project) {
        fPath = path;
        this.project = project;
        updateExtentionList();
    }

    protected ISourceContainer[] createSourceContainers() throws CoreException {
        String variableName = fPath.toOSString();
        File file = getVariableFile(variableName);
        ISourceContainer[] container = new ISourceContainer[1];
        if (file.isDirectory()) {
            container[0] = new PHPDirectorySourceContainer(file, false, project);
        } else {
            String fileName = file.getName();
            if (fileName.toLowerCase().endsWith(".zip")) { //$NON-NLS-1$
                container[0] = new PHPExternalArchiveSourceContainer(file.getPath(), false, project);
            } else if (isPhpFile(fileName)) {
                container[0] = new PHPFileSourceContainer(file, project);
            }
        }
        return container;
    }

    public String getName() {
        return fPath.toString();
    }

    public ISourceContainerType getType() {
        // TODO Auto-generated method stub
        return null;
    }

    private File getVariableFile(String variableName) {
        int index = variableName.indexOf('/');
        String extention = ""; //$NON-NLS-1$
        if (index != -1) {
            if (index + 1 < variableName.length()) {
                extention = variableName.substring(index + 1);
            }
            variableName = variableName.substring(0, index);
        }
        IPath path = PHPProjectOptions.getIncludePathVariable(variableName);
        path = path.append(extention);
        return path.toFile();
    }

    private boolean isPhpFile(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return false;
        }
        String ext = fileName.substring(index + 1);
        for (int i = 0; i < validExtensions.length; i++) {
            if (ext.equals(validExtensions[i])) {
                return true;
            }
        }
        return false;
    }

    private void updateExtentionList() {
        IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
        validExtensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
    }
}
