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
package org.eclipse.php.core.documentModel;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;

public interface IWorkspaceModelListener {
	
	/**
	 * Event been fired when {@link PHPProjectModel} is been created and added to the {@link PHPWorkspaceModelManager} 
	 */
	public void projectModelAdded(IProject project);

	/**
	 * Event been fired when {@link PHPProjectModel} is been removed from the {@link PHPWorkspaceModelManager} 
	 */

	public void projectModelRemoved(IProject project);
	
	/**
	 * Event been fired when {@link PHPProjectModel} is built (fully not for every file changed) 
	 */
	
	public void projectModelChanged(IProject project);
}