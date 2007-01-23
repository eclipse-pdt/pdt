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
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;

public class PHPCompositeSourceContainer extends CompositeSourceContainer {

    private IProject project;
    private ILaunchConfiguration configuration;

    public PHPCompositeSourceContainer(IProject project, ILaunchConfiguration configuration) {
        this.project = project;
        this.configuration = configuration;
    }

    protected ISourceContainer[] createSourceContainers() throws CoreException {

        ArrayList containers = new ArrayList();
        ISourceContainer projectContainer = new ProjectSourceContainer(project, false);
        containers.add(projectContainer);
        PHPProjectOptions options = PHPProjectOptions.forProject(project);
        if (options != null) {
            IIncludePathEntry[] entries = options.readRawIncludePath();

            if (entries != null) {
                for (int i = 0; i < entries.length; i++) {
                    if (entries[i].getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
                        IPath path = entries[i].getPath();
                        File file = new File(path.toString());
                        if (entries[i].getContentKind() == IIncludePathEntry.K_BINARY) {
                            containers.add(new PHPExternalArchiveSourceContainer(file.getAbsolutePath(), false, project));
                        } else {
                            containers.add(new PHPDirectorySourceContainer(file, false, project));
                        }
                    } else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
                        IResource includeResource = entries[i].getResource();
                        if (includeResource instanceof IProject) {
                            IProject includeProject = (IProject) entries[i].getResource();
                            containers.add(new ProjectSourceContainer(includeProject, false));
                        }
                    } else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
                        IPath path = entries[i].getPath();
                        containers.add(new PHPVariableSourceContainer(path, project));
                    }
                }
            }
        }
        ISourceContainer[] scontainers = new ISourceContainer[containers.size()];
        containers.toArray(scontainers);
        return scontainers;
    }

    public Object[] findSourceElements(String name) throws CoreException {
        Object [] objs = super.findSourceElements(name);
        return objs;
    }

    public String getName() {
        return "PHPComposite";
    }

    public ISourceContainerType getType() {
        //        return getSourceContainerType("xxxx");
        return null;
    }
    

}
