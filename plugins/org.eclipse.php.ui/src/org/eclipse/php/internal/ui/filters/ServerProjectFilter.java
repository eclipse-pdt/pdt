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
package org.eclipse.php.internal.ui.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;


/**
 * Filters closed projects
 */
public class ServerProjectFilter extends ViewerFilter {

	public static final String SERVER_PROJECT_NAME="Servers";
	public static final String RUNTIME_NAME=".settings/org.eclipse.wst.server.core.prefs";
	/*
	 * @see ViewerFilter
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		IProject project=null;
		if (element instanceof PHPCodeData || element instanceof PHPProjectModel) {
			IResource resource = PHPModelUtil.getResource(element);
			if (resource!=null)
				project = resource.getProject();
		}
		if (element instanceof IResource)
			project =  ((IResource) element).getProject();
		if (project!=null)
		{
			if (project.getName().startsWith(SERVER_PROJECT_NAME))
			{
				return !project.getFile(RUNTIME_NAME).exists();
			}
		}
		return true;
	}
}
