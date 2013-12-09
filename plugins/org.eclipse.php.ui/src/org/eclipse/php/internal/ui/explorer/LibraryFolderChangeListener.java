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

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
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
public class LibraryFolderChangeListener implements
		ILibraryFolderChangeListener {

	public void folderChanged(IModelElement[] elements) {
		try {
			updatePhpExplorer(elements);
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
		}
	}

	/**
	 * Updates the visual state of the given model elements and all their
	 * children in the PHP Explorer view.
	 * 
	 * @param elements
	 *            an array of model elements to update
	 * 
	 * @throws ModelException
	 *             if any of the given element does not exist or if an exception
	 *             occurs while accessing its corresponding resource
	 */
	private void updatePhpExplorer(IModelElement[] elements)
			throws ModelException {
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();
		final IModelElement[] subfolders = lfm.getAllSubfolders(elements);

		// make sure the actual update in the PHP Explorer is executed in the UI
		// thread
		Display.getDefault().asyncExec(new Runnable() {
			@SuppressWarnings("restriction")
			public void run() {
				final PHPExplorerPart phpExplorer = getPhpExplorer();
				if (phpExplorer != null) {
					phpExplorer.getTreeViewer().update(subfolders, null);
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
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
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
