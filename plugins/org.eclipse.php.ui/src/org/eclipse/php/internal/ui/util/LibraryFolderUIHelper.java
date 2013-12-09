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
package org.eclipse.php.internal.ui.util;

import static org.eclipse.php.internal.core.util.LibraryFolderHelper.disableValidation;
import static org.eclipse.php.internal.core.util.LibraryFolderHelper.enableValidation;
import static org.eclipse.php.internal.core.util.LibraryFolderHelper.getAllSubfolders;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.ui.explorer.PHPExplorerPart;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * Helper methods for working with library folders.
 * 
 * @author Kaloyan Raev
 */
public class LibraryFolderUIHelper {

	/**
	 * Marks the given source folders as library folders.
	 * 
	 * <p>
	 * The given array of model elements should contain {@link IScriptFolder} or
	 * {@link IProjectFragment} objects.
	 * </p>
	 * 
	 * <p>
	 * This method executes the following steps:
	 * <ol>
	 * <li>Disables the given folders in the WTP Validation Framework.</li>
	 * <li>Updates the images of the given folders and all their subfolders in
	 * the PHP Explorer.</li>
	 * <li>Waits for any running validation job to finish.</li>
	 * <li>Deletes all problem and task markers on all the children resources of
	 * the given folders.</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * This method usually executes a significant number of resource
	 * modifications. It is highly recommended that this method is called either
	 * in a {@link WorkspaceModifyOperation} or in a {@link WorkspaceJob} to
	 * batch the resource change notifications and avoid running unnecessary
	 * build jobs.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements that represents the source folders
	 *            to be marked as library folders
	 * @param monitor
	 *            a progress monitor, or null if progress reporting is not
	 *            desired
	 * 
	 * @throws OperationCanceledException
	 *             if the progress monitor is canceled while waiting
	 * @throws InterruptedException
	 *             if this thread is interrupted while waiting
	 * @throws CoreException
	 *             if this method fails for some other reason
	 * 
	 * @see {@link #useAsSourceFolder(IModelElement[], IProgressMonitor)}
	 */
	public static void useAsLibraryFolder(IModelElement[] elements,
			IProgressMonitor monitor) throws OperationCanceledException,
			InterruptedException, CoreException {
		disableValidation(elements);

		elements = removeNonExisting(elements);
		if (elements.length > 0) {
			updatePhpExplorer(elements);
			waitValidationJobs(monitor);
			deleteMarkers(elements);
		}
	}

	/**
	 * Marks the given library folders as source folders.
	 * 
	 * <p>
	 * The given array of model elements should contain {@link IScriptFolder} or
	 * {@link IProjectFragment} objects.
	 * </p>
	 * 
	 * <p>
	 * This method executes the following steps:
	 * <ol>
	 * <li>Enables the given folders in the WTP Validation Framework.</li>
	 * <li>Updates the images of the given folders and all their subfolders in
	 * the PHP Explorer.</li>
	 * <li>Waits for any running validation job to finish.</li>
	 * <li>Invokes {@link IResource#touch(IProgressMonitor)} on all the children
	 * of the selected folders. This triggers the necessary builders to
	 * re-validate these resources.</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * This method usually executes a significant number of resource
	 * modifications. It is highly recommended that this method is called either
	 * in a {@link WorkspaceModifyOperation} or in a {@link WorkspaceJob} to
	 * batch the resource change notifications and avoid running unnecessary
	 * build jobs.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements that represents the library folders
	 *            to be marked as source folders
	 * @param monitor
	 *            a progress monitor, or null if progress reporting is not
	 *            desired
	 * 
	 * @throws OperationCanceledException
	 *             if the progress monitor is canceled while waiting
	 * @throws InterruptedException
	 *             if this thread is interrupted while waiting
	 * @throws CoreException
	 *             if this method fails for some other reason
	 * 
	 * @see {@link #useAsLibraryFolder(IModelElement[], IProgressMonitor)}
	 */
	public static void useAsSourceFolder(IModelElement[] elements,
			IProgressMonitor monitor) throws OperationCanceledException,
			InterruptedException, CoreException {
		enableValidation(elements);

		elements = removeNonExisting(elements);
		if (elements.length > 0) {
			updatePhpExplorer(elements);
			waitValidationJobs(monitor);
			revalidate(elements, monitor);
		}
	}

	/**
	 * Removes the non-existing model elements from the given array.
	 * 
	 * <p>
	 * The result is returned as new array. The given array remains untouched.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements
	 * 
	 * @return a new array that contains only the existing elements of the given
	 *         array
	 */
	private static IModelElement[] removeNonExisting(IModelElement[] elements) {
		Collection<IModelElement> existing = new HashSet<IModelElement>();

		for (IModelElement element : elements) {
			if (element.exists()) {
				existing.add(element);
			}
		}

		return existing.toArray(new IModelElement[existing.size()]);
	}

	/**
	 * Delete all problem and tasks markers on all the given model elements and
	 * all their children.
	 * 
	 * <p>
	 * Only markers of types {@link IMarker#PROBLEM} and {@link IMarker#TASK}
	 * and their subtypes are deleted. Markers of other types (bookmarks,
	 * breakpoints, etc.) are not affected.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements
	 * 
	 * @throws CoreException
	 *             if deleting the markers on any resource fails
	 */
	private static void deleteMarkers(IModelElement[] elements)
			throws CoreException {

		for (IModelElement element : elements) {
			element.getResource().deleteMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
			element.getResource().deleteMarkers(IMarker.TASK, true,
					IResource.DEPTH_INFINITE);
		}
	}

	/**
	 * Triggers validation jobs for the given model elements and all their
	 * children.
	 * 
	 * @param elements
	 *            an array of model elements
	 * @param monitor
	 *            a progress monitor, or null if progress reporting is not
	 *            desired
	 * 
	 * @throws CoreException
	 *             if touching of any of the resources fails
	 */
	private static void revalidate(IModelElement[] elements,
			IProgressMonitor monitor) throws CoreException {
		for (IModelElement element : elements) {
			deepTouch(element.getResource(), monitor);
		}
	}

	/**
	 * Invokes {@link IResource#touch(IProgressMonitor)} on the given resource
	 * and all its children.
	 * 
	 * @param resource
	 *            a resource to touch
	 * @param monitor
	 *            a progress monitor, or null if progress reporting is not
	 *            desired
	 * 
	 * @throws CoreException
	 *             if touching of any of the resources fails
	 */
	private static void deepTouch(IResource resource, IProgressMonitor monitor)
			throws CoreException {
		resource.touch(monitor);

		// touch recursively resources inside folders and projects
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			for (IResource member : container.members()) {
				deepTouch(member, monitor);
			}
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
	private static void updatePhpExplorer(IModelElement[] elements)
			throws ModelException {
		final IModelElement[] subfolders = getAllSubfolders(elements);

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
	private static PHPExplorerPart getPhpExplorer() {
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

	/**
	 * Waits for any running jobs of the WTP Validation Framework to finish.
	 * 
	 * @param monitor
	 *            a progress monitor, or null if progress reporting is not
	 *            desired
	 * 
	 * @throws OperationCanceledException
	 *             if the progress monitor is canceled while waiting
	 * @throws InterruptedException
	 *             if this thread is interrupted while waiting
	 */
	private static void waitValidationJobs(IProgressMonitor monitor)
			throws OperationCanceledException, InterruptedException {
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, monitor);
	}

}
