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
package org.eclipse.php.debug.core.sourcelookup;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * A PHP source search engine supplies a 'good guess' for resources that the source lookup
 * should find when a debugger was paused or produced an error to the console as a resource link.
 * 
 * @author shalom
 */
public class PHPSourceSearchEngine {

	private static final char SEPARATOR = '/';

	/**
	 * Search and return the IFile resource that might be the match for the given resource name.
	 * The search trims the resourceName from left to right (using the / path separator) until a match
	 * is found under the given project. 
	 * In case there was no exact match, a non existing IFile is returned (a result of project.getFile(resourceName)).
	 * 
	 * @param resourceName
	 * @param project
	 * @return
	 */
	public static IFile getResource(String resourceName, IProject project) {
		IFile file = project.getFile(resourceName);
		if (file.exists()) {
			return file;
		}
		// Search for a possible match.
		// Stop on the first match.
		int separatorIndex = resourceName.indexOf(SEPARATOR);
		while (separatorIndex > -1) {
			resourceName = resourceName.substring(separatorIndex + 1);
			IFile f = project.getFile(resourceName);
			if (f.exists()) {
				return f;
			}
			separatorIndex = resourceName.indexOf(SEPARATOR);
		}

		// If we could not find any matching resource, return the non-existing IFile resource
		// the the project.getFile(resourceName) returned.
		return file;
	}
}
