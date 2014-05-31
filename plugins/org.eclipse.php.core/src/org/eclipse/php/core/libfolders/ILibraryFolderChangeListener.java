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
package org.eclipse.php.core.libfolders;

import org.eclipse.core.resources.IFolder;

/**
 * A library folder change listeners whenever a source folder is turned to
 * library folder and vice versa.
 * 
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Kaloyan Raev
 * @since 3.3
 */
public interface ILibraryFolderChangeListener {

	/**
	 * Notifies this listener that some folders have changed from source folders
	 * to library folders or vice versa.
	 * 
	 * <p>
	 * This method is called by the {@link LibraryFolderManager}. It is not
	 * intended to be called directly by clients.
	 * </p>
	 * 
	 * <p>
	 * This method is called after the WTP Validation Framework is updated with
	 * the changed folders, but before the re-validation has been started.
	 * </p>
	 * 
	 * <p>
	 * Clients must provide a fast and non-blocking implementation of this
	 * method. If a long-running operation is required, it must be executed in a
	 * job.
	 * </p>
	 * 
	 * @param folders
	 *            the changed folders
	 */
	public void foldersChanged(IFolder[] folders);

}
