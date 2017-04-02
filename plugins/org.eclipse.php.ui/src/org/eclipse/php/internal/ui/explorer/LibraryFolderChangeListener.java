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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.core.libfolders.ILibraryFolderChangeListener;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;

/**
 * A library folder change listener that updates the images of the given folders
 * and all their subfolders in the PHP Explorer whenever a source folder is
 * turned to library folder and vice versa.
 * 
 * @author Kaloyan Raev
 */
public class LibraryFolderChangeListener implements ILibraryFolderChangeListener {

	@Override
	public void foldersChanged(IFolder[] folders) {
		try {
			updatePhpExplorer(folders);
		} catch (CoreException e) {
			PHPUiPlugin.log(e);
		}
	}

	/**
	 * Updates the visual state of the given folders and all their subfolders
	 * (recursively) in the PHP Explorer view.
	 * 
	 * @param folders
	 *            an array of folders to update
	 * 
	 * @throws CoreException
	 *             if any of the folders does not exist or is in a closed
	 *             project
	 */
	private void updatePhpExplorer(IFolder[] folders) throws CoreException {
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();
		final IFolder[] subfolders = lfm.getAllSubfolders(folders);

		// make sure the actual update in the PHP Explorer is executed in the UI
		// thread
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				PHPExplorerPart phpExplorer = getPhpExplorer();
				if (phpExplorer != null) {
					TreeViewer tree = phpExplorer.getTreeViewer();
					for (IFolder subfolder : subfolders) {
						IModelElement element = DLTKCore.create(subfolder);
						if (element != null) {
							tree.update(element, null);
						}
					}
				}
			}
		});
	}

	/**
	 * Returns a reference to the PHP Explorer view part.
	 * 
	 * @return a reference to {@link PHPExplorerPart}, or <code>null</code> if
	 *         none is available in the active workbench page
	 */
	private PHPExplorerPart getPhpExplorer() {
		// find the active workbench window
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null)
			return null;

		// find the active workbench page
		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			return null;

		// find the PHP Explorer in all available view references
		for (IWorkbenchPartReference ref : page.getViewReferences()) {
			IWorkbenchPart part = ref.getPart(false);
			if (part != null && part instanceof PHPExplorerPart) {
				return (PHPExplorerPart) part;
			}
		}

		// the PHP Explorer view is not found
		return null;
	}

}
