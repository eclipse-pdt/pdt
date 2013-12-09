/*******************************************************************************
 * Copyright (c) 2013 Zend Technologies and others.
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

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.LibraryFolderHelper;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.wst.validation.internal.DisabledResourceManager;

/**
 * Represents the "Use As Source Folder" in the PHP Explorer's context menu.
 * 
 * <p>
 * This action executes the
 * {@link LibraryFolderHelper#useAsSourceFolder(IModelElement[], IProgressMonitor)}
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
	 * The array of model elements (i.e. library folders) to mark as source
	 * folders.
	 */
	private IModelElement[] fElements;

	/**
	 * Constructor a new "Use As Source Folder" action to mark the given library
	 * folders as source folders.
	 * 
	 * @param site
	 *            a reference to the workbench site
	 * @param elements
	 *            an array of model elements to mark as source folders
	 */
	public UseAsSourceFolderAction(IWorkbenchSite site, IModelElement[] elements) {
		if (elements.length == 0)
			throw new IllegalArgumentException("empty elements array");

		fSite = site;
		fElements = elements;

		setText(Messages.LibraryFolderAction_UseAsSourceFolder_label);
		setImageDescriptor(PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT);
	}

	@Override
	public void run() {
		boolean askForConfirmation = false;
		Collection<IModelElement> topmostElements = new HashSet<IModelElement>();

		// check if any of the selected library folders is not a topmost library
		// folder
		for (IModelElement element : fElements) {
			IModelElement topmostLibraryFolder = getTopmostLibraryFolder(element);
			topmostElements.add(topmostLibraryFolder);
			if (!element.equals(topmostLibraryFolder)) {
				// there is a selected folder which is not a topmost library
				// folder, so ask the user for confirmation to mark the topmost
				// library folder as source folder
				askForConfirmation = true;
			}
		}

		final IModelElement[] elements = topmostElements
				.toArray(new IModelElement[topmostElements.size()]);

		if (askForConfirmation) {
			// show the confirmation dialog
			String title = Messages.LibraryFolderAction_Dialog_title;
			String message = NLS.bind(
					Messages.LibraryFolderAction_Dialog_description,
					StringUtils.join(getSortedElementNames(elements), ",\n\t"));

			if (!MessageDialog.openConfirm(fSite.getShell(), title, message))
				// the user clicked the Cancel button - abort the action
				return;
		}

		// execute the action in a WorkspaceModifyOperation to batch the
		// resource change events and avoid unnecessary triggering build jobs
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				LibraryFolderHelper.useAsSourceFolder(elements, monitor);
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(op);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}

	}

	/**
	 * Returns the names of the given model elements in sorted order.
	 * 
	 * @param elements
	 *            an array of model elements
	 * 
	 * @return a sorted array of strings with the names of the given model
	 *         elements
	 */
	private String[] getSortedElementNames(IModelElement[] elements) {
		String[] names = new String[elements.length];

		for (int i = 0; i < elements.length; i++) {
			names[i] = elements[i].getElementName();
		}

		Arrays.sort(names);

		return names;
	}

	/**
	 * Returns the topmost library folder of the given model element.
	 * 
	 * <p>
	 * A topmost library folder is such folder that is explicitly disable in the
	 * WTP Validation Framework. The subfolders of a topmost library folder are
	 * library folders, but the parent of the topmost library folder is not a
	 * library folder.
	 * </p>
	 * 
	 * @param element
	 *            a model element
	 * 
	 * @return the topmost library folder of the given model element
	 */
	public IModelElement getTopmostLibraryFolder(IModelElement element) {
		IResource resource = element.getResource();
		if (resource == null)
			// the model element has no corresponding resource, so it is not
			// part of any source file and hence cannot be in a library folder
			return null;

		if (resource.getType() == IResource.FILE) {
			// the model element is a source file, so take its parent folder
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			// check if the folder is disabled in the WTP Validation Framework
			if (DisabledResourceManager.getDefault().isDisabled(resource)) {
				// the folder is disabled, so it is the topmost library folder
				return DLTKCore.create(resource);
			}
			// the folder is not disabled, so check its parent folder
			resource = resource.getParent();
		}

		// none of the element's parent folders is disabled in the WTP
		// Validation Framework, so the element is not inside a library folder
		// and therefore has no topmost library folder
		return null;
	}

}
