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
package org.eclipse.php.ui.search;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

/**
 * 
 * 
 * @author shalom
 */
public class PHPWorspaceScope extends PHPSearchScope {

	public PHPWorspaceScope(int searchFor) {
		super(searchFor);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.search.PHPSearchScope#add(org.eclipse.core.resources.IResource)
	 */
	public void add(IResource resource) {
		IProject project = resource.getProject();
		add(project);
	}
	
}
