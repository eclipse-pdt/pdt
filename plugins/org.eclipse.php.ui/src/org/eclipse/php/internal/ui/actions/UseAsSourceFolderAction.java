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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * Represents the "Use As Source Folder" in the PHP Explorer's context menu.
 * 
 * <p>
 * This action executes the
 * {@link LibraryFolderManager#useAsSourceFolder(IFolder[], IProgressMonitor)}
 * method in a {@link WorkspaceModifyOperation}.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public class UseAsSourceFolderAction extends Action {

	/**
	 * Reference to the workbench site.
	 */
	private IWorkbenchSite fSite;

	/**
	 * The array of library folders to mark as source folders.
	 */
	private IFolder[] fFolders;

	/**
	 * Constructor a new "Use As Source Folder" action to mark the given library
	 * folders as source folders.
	 * 
	 * @param site
	 *            a reference to the workbench site
	 * @param folders
	 *            an array of library folders to mark as source folders
	 */
	public UseAsSourceFolderAction(IWorkbenchSite site, IFolder[] folders) {
		if (folders.length == 0)
			throw new IllegalArgumentException("empty folders array"); //$NON-NLS-1$

		fSite = site;
		fFolders = folders;

		setText(Messages.LibraryFolderAction_UseAsSourceFolder_label);
		setImageDescriptor(PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT);
	}

	@Override
	public void run() {
		boolean askForConfirmation = false;
		Collection<IResource> explicitlyDisabledFolders = new HashSet<IResource>();
		LibraryFolderManager lfm = LibraryFolderManager.getInstance();

		// check if any of the selected library folders is an explicitly
		// disabled library folder
		for (IFolder folder : fFolders) {
			IFolder explicitlyDisabledParent = lfm.getExplicitlyDisabledParent(folder);
			explicitlyDisabledFolders.add(explicitlyDisabledParent);

			if (!folder.equals(explicitlyDisabledParent)) {
				// there is a selected folder which is not an explicitly
				// disabled library folder, so ask the user for confirmation to
				// mark the explicitly disabled parent library folder as source
				// folder
				askForConfirmation = true;
			}
		}

		final IFolder[] folders = explicitlyDisabledFolders.toArray(new IFolder[explicitlyDisabledFolders.size()]);

		if (askForConfirmation) {
			// show the confirmation dialog
			String title = Messages.LibraryFolderAction_Dialog_title;
			String message = NLS.bind(Messages.LibraryFolderAction_Dialog_description,
					StringUtils.join(getSortedPaths(folders), ",\n\t"));

			if (!MessageDialog.openConfirm(fSite.getShell(), title, message))
				// the user clicked the Cancel button - abort the action
				return;
		}

		// execute the action in a WorkspaceModifyOperation to batch the
		// resource change events and avoid unnecessary triggering build jobs
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				LibraryFolderManager.getInstance().useAsSourceFolder(folders, monitor);
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(op);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}

	}

	/**
	 * Returns the paths of the given resources in sorted order.
	 * 
	 * @param resources
	 *            an array of resources
	 * 
	 * @return a sorted array of strings with the paths of the given resources
	 */
	private String[] getSortedPaths(IResource[] resources) {
		String[] paths = new String[resources.length];

		for (int i = 0; i < resources.length; i++) {
			paths[i] = resources[i].getFullPath().toString();
		}

		Arrays.sort(paths);

		return paths;
	}

}
