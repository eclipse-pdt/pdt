/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.model.ComposerRoot;
import org.eclipse.php.composer.core.utils.ComposerProperties;
import org.eclipse.php.core.libfolders.ILibraryFolderNameProvider;

/**
 * An extension to the
 * <code>org.eclipse.php.core.libraryFolderNameProviders</code> extension point.
 * 
 * <p>
 * Returns the configured vendor directory for the given composer-enabled
 * project.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public class ComposerLibraryFolderNameProvider implements
		ILibraryFolderNameProvider {

	/**
	 * Returns the configured vendor directory for the given Composer-enabled
	 * project.
	 * 
	 * @return a singleton array containing the configured vendor directory, or
	 *         <code>null</code> if the given project is not using Composer.
	 */
	@Override
	public String[] getLibraryFolderNames(IProject project) {
		try {
			ComposerRoot root = ComposerRoot.parse(project);
			if (root != null) {
				ComposerProperties properties = new ComposerProperties(root);
				return new String[] { properties.getVendorDir() };
			}
		} catch (Exception e) {
			ComposerCorePlugin.logError(e);
		}

		return null;
	}

}
