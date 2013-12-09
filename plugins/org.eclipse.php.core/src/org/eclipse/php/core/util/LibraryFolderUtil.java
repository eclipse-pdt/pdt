/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.util;

import static org.eclipse.php.internal.core.util.LibraryFolderHelper.isTopmostLibraryFolder;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.wst.validation.ValidationFramework;

/**
 * Utilities for working with library folders.
 * 
 * <p>
 * Library folders are source folders containing code, which is not
 * application-specific. This code is not validated, but is still accessible for
 * content assist.
 * </p>
 * 
 * @author Kaloyan Raev
 * @since 3.3
 */
public class LibraryFolderUtil {

	/**
	 * Returns whether the given resource is inside a library folder.
	 * 
	 * <p>
	 * A library folder is a source folder that is disabled for validation in
	 * the WTP Validation Framework. So, this method checks if a parent folder
	 * of the given model element is disabled for validation.
	 * </p>
	 * 
	 * @param resource
	 *            a resource to check if inside a library folder
	 * 
	 * @return <code>true</code> if the given model element is inside a library
	 *         folder, and <code>false</code> otherwise
	 * 
	 * @see ValidationFramework#disableValidation(IResource)
	 */
	public static boolean isInLibraryFolder(IResource resource) {
		if (resource == null)
			// the model element has no corresponding resource, so it is not
			// part of any source file and hence cannot be in a library folder
			return false;

		if (resource.getType() == IResource.FILE) {
			// the model element is a source file, so take its parent folder
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			// check if the folder is disabled in the WTP Validation Framework
			if (isTopmostLibraryFolder(resource)) {
				return true;
			}
			// the folder is not disabled, so check its parent folder
			resource = resource.getParent();
		}

		// none of the element's parent folders is disabled in the WTP
		// Validation Framework, so the element is not inside a library folder
		return false;
	}

	/**
	 * Returns whether the given model element is inside a library folder.
	 * 
	 * <p>
	 * A library folder is a source folder that is disabled for validation in
	 * the WTP Validation Framework. So, this method checks if a parent folder
	 * of the given model element is disabled for validation.
	 * </p>
	 * 
	 * @param element
	 *            a model element to check if inside a library folder
	 * 
	 * @return <code>true</code> if the given model element is inside a library
	 *         folder, and <code>false</code> otherwise
	 * 
	 * @see ValidationFramework#disableValidation(IResource)
	 */
	public static boolean isInLibraryFolder(IModelElement element) {
		IResource resource = element.getResource();
		return isInLibraryFolder(resource);
	}

}
