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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.action.Action;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * Represents the "Use As Library Folder" in the PHP Explorer's context menu.
 * 
 * <p>
 * This action executes the
 * {@link LibraryFolderManager#useAsLibraryFolder(IModelElement[], IProgressMonitor)}
 * method in a {@link WorkspaceModifyOperation}.
 * </p>
 * 
 * @author Kaloyan Raev
 */
public class UseAsLibraryFolderAction extends Action {

	/**
	 * The array of model elements (i.e. source folders) to mark as library
	 * folders.
	 */
	private IModelElement[] fElements;

	/**
	 * Constructor a new "Use As Library Folder" action to mark the given source
	 * folders as library folders.
	 * 
	 * @param elements
	 *            an array of model elements to mark as library folders
	 */
	public UseAsLibraryFolderAction(IModelElement[] elements) {
		if (elements.length == 0)
			throw new IllegalArgumentException("empty elements array");

		fElements = elements;

		setText(Messages.LibraryFolderAction_UseAsLibraryFolder_label);
		setImageDescriptor(PHPPluginImages.DESC_OBJS_PHP_LIBFOLDER);
	}

	@Override
	public void run() {
		// execute the action in a WorkspaceModifyOperation to batch the
		// resource change events and avoid unnecessary triggering build jobs
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				LibraryFolderManager lfm = LibraryFolderManager.getInstance();
				lfm.useAsLibraryFolder(fElements, monitor);
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(op);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
	}

}
