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
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.core.resources.IFolder;
import org.eclipse.php.core.libfolders.ILibraryFolderChangeListener;

/**
 * A library folder change listener that updates the images of the given folders
 * and all their subfolders in the PHP Explorer whenever a source folder is
 * turned to library folder and vice versa.
 * 
 * @author Kaloyan Raev
 */
@Deprecated
public class LibraryFolderChangeListener implements ILibraryFolderChangeListener {

	public void foldersChanged(IFolder[] folders) {
	}

}
