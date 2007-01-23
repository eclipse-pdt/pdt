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
package org.eclipse.php.internal.debug.core.sourcelookup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.WorkspaceSourceContainer;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.sourcelookup.containers.PHPCompositeSourceContainer;

/**
 * Computes the default source lookup path for a PHP launch configuration. For
 * now just use a project name PHP
 */
public class PHPSourcePathComputerDelegate implements ISourcePathComputerDelegate {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.internal.core.sourcelookup.ISourcePathComputerDelegate#computeSourceContainers(org.eclipse.debug.core.ILaunchConfiguration,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
        ISourceContainer sourceContainer = null;
        String project = configuration.getAttribute(IPHPConstants.PHP_Project, (String) null);
        IProject resource = ResourcesPlugin.getWorkspace().getRoot().getProject(project);
        if (resource != null) {
            sourceContainer = new PHPCompositeSourceContainer(resource, configuration);
        }
        if (sourceContainer == null) {
            sourceContainer = new WorkspaceSourceContainer();
        }
        return new ISourceContainer[] { sourceContainer };
    }
}
