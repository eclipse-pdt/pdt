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
package org.eclipse.php.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.core.project.IIncludePathContainer;
public abstract class IncludePathContainerInitializer {
	
   /**
     * Creates a new includePath container initializer.
     */
    public IncludePathContainerInitializer() {
    	// a includePath container initializer must have a public 0-argument constructor
    }

 
    public abstract void initialize(IPath containerPath, IProject project) throws CoreException;
    
    /**
     * Returns <code>true</code> if this container initializer can be requested to perform updates 
     * on its own container values. If so, then an update request will be performed using
     * <code>IncludePathContainerInitializer#requestIncludePathContainerUpdate</code>/
     * <p>
     * @param containerPath the path of the container which requires to be updated
     * @param project the project for which the container is to be updated
     * @return returns <code>true</code> if the container can be updated
     * @since 2.1
     */
    public boolean canUpdateIncludePathContainer(IPath containerPath, IProject project) {
    	
		// By default, includePath container initializers do not accept updating containers
    	return false; 
    }
 
    public void requestIncludePathContainerUpdate(IPath containerPath, IProject project, IIncludePathContainer containerSuggestion) throws CoreException {

		// By default, includePath container initializers do not accept updating containers
    }

 
    public String getDescription(IPath containerPath, IProject project) {
    	
    	// By default, a container path is the only available description
    	return containerPath.makeRelative().toString();
    }
 
	public Object getComparisonID(IPath containerPath, IProject project) {

		// By default, containers are identical if they have the same containerPath first segment,
		// but this may be refined by other container initializer implementations.
		if (containerPath == null) {
			return null;
		} else {
			return containerPath.segment(0);
		}
	}
}

