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
package org.eclipse.php.internal.ui.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.IContextMenuConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

/**
 * An action group that contributes the "Use As Library Folder" and "Use As
 * Source Folder" actions in the PHP Explorer's context menu.
 * 
 * @author Kaloyan Raev
 */
public class LibraryFolderActionGroup extends CommonActionProvider {

	private IWorkbenchSite fSite;

	/*
	 * Empty constructor for extension point
	 */
	public LibraryFolderActionGroup() {
	}

	public LibraryFolderActionGroup(IViewPart part) {
		fSite = part.getSite();
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		ICommonViewerWorkbenchSite workbenchSite = null;
		if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			workbenchSite = (ICommonViewerWorkbenchSite) site.getViewSite();
		}

		if (workbenchSite != null) {
			if (workbenchSite.getSite() != null) {
				fSite = workbenchSite.getSite();
			}
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);

		IAction action = getActionFromSelection();
		if (action != null) {
			menu.appendToGroup(IContextMenuConstants.GROUP_REORGANIZE, action);
		}
	}

	/**
	 * Returns the action to add to context menu based on the current selection.
	 * 
	 * <ul>
	 * <li>If the current selection consists of only source folders then the
	 * "Use As Library Folders" action will be returned</li>
	 * <li>If the current selection consists of only library folders then the
	 * "Use As Source Folders" action will be returned</li>
	 * <li>In all other cases, this method returns <code>null</code></li>
	 * </ul>
	 * 
	 * @return the action to add to the context menu, or <code>null</code> if no
	 *         appropriate action can be determined
	 */
	private IAction getActionFromSelection() {
		ISelection sel = fSite.getSelectionProvider().getSelection();
		if (!(sel instanceof IStructuredSelection))
			return null;

		IStructuredSelection selection = (IStructuredSelection) sel;
		Iterator<?> iterator = selection.iterator();

		Collection<IFolder> selected = new HashSet<IFolder>();

		// collect all folders from the current selection
		while (iterator.hasNext()) {
			Object obj = iterator.next();

			if (obj instanceof IFolder) {
				obj = DLTKCore.create((IFolder) obj);
			}

			if (!(obj instanceof IModelElement))
				// the selection contains an object that is not a model element
				return null;

			IModelElement element = (IModelElement) obj;
			IResource resource = element.getResource();

			if (resource == null)
				// the selection contains a model element without a
				// corresponding resource on the file system
				return null;

			if (resource.getType() != IResource.FOLDER)
				// the selection contains a model element that is not a folder
				return null;

			selected.add((IFolder) resource);
		}

		IFolder[] folders = selected.toArray(new IFolder[selected.size()]);

		if (folders.length == 0)
			// no folders in the selection
			return null;

		if (!allOfSameKind(folders))
			// a mixture of source folders and library folders in the selection
			return null;

		if (LibraryFolderManager.getInstance().isInLibraryFolder(folders[0])) {
			// the selection contains only library folders
			return new UseAsSourceFolderAction(fSite, folders);
		} else {
			// the selection contains only source folders
			return new UseAsLibraryFolderAction(folders);
		}
	}

	/**
	 * Returns whether the given folders are either all library folders or all
	 * source folders.
	 * 
	 * @param folders
	 *            an array of folders
	 * 
	 * @return <code>true</code> if the given folders are either all library
	 *         folders or source folder, and <code>false</code> otherwise
	 */
	private boolean allOfSameKind(IFolder[] folders) {
		int libraryFolderCount = 0;

		// count the library folders in the array
		for (IResource folder : folders) {
			if (LibraryFolderManager.getInstance().isInLibraryFolder(folder)) {
				libraryFolderCount++;
			}
		}

		// If the number of library folders is 0, then all folders are source
		// folders. If the number of library folders equals the number of given
		// folders, then all folders are library folders. In any other case,
		// there is a mixture of library folders and source folders.
		return libraryFolderCount == 0 || libraryFolderCount == folders.length;
	}
}
