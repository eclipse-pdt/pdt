/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Bartlomiej Laczkowski - [513093]  Lot of DLTK model exceptions...
 *******************************************************************************/
package org.eclipse.php.composer.ui.utils;

import java.text.MessageFormat;

import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.ui.internal.ide.misc.FileInfoAttributesMatcher;

/**
 * Utility class for creating basic resource filters.
 */
public class ResourceFilterUtil {

	private static final String EXCLUDE_PATTERN = "1.0-projectRelativePath-matches-false-false-{0}";

	private ResourceFilterUtil() {
		// No instance, utility class.
	};

	/**
	 * Creates and returns filter for excluding given resource and all of its
	 * child elements from local refresh process.
	 * 
	 * @param project
	 * @param resourceRelativePath
	 * @return filter for excluding given resource and all of its child elements
	 *         from local refresh process
	 */
	public static IResourceFilterDescription createExcludeFilter(IProject project, String resourceRelativePath) {
		try {
			return project.createFilter(
					IResourceFilterDescription.EXCLUDE_ALL | IResourceFilterDescription.FILES
							| IResourceFilterDescription.FOLDERS | IResourceFilterDescription.INHERITABLE,
					new FileInfoMatcherDescription(FileInfoAttributesMatcher.ID,
							MessageFormat.format(EXCLUDE_PATTERN, resourceRelativePath)),
					IResource.BACKGROUND_REFRESH, new NullProgressMonitor());
		} catch (CoreException e) {
			Logger.logException(e);
			return null;
		}
	}

}
