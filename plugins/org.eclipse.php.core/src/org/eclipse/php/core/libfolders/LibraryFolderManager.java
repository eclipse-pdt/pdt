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

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.libfolders.AutoDetectLibraryFolderListener;
import org.eclipse.wst.validation.ValidationFramework;
import org.eclipse.wst.validation.internal.DisabledResourceManager;

/**
 * Provides the necessary API for working with library folders.
 * 
 * <p>
 * Library folders are source folders containing code, which is not
 * application-specific. This code is not validated, but is still accessible for
 * content assist.
 * </p>
 * 
 * <p>
 * This class is a singleton. Use the {@link #getInstance()} to obtain an
 * instance of the class.
 * </p>
 * 
 * @author Kaloyan Raev
 * @since 3.3
 */
public class LibraryFolderManager {

	/**
	 * The singleton's instance
	 */
	private static LibraryFolderManager instance;

	/**
	 * The collection of registered library folder change listeners.
	 */
	private Collection<ILibraryFolderChangeListener> listeners;

	private AutoDetectLibraryFolderListener listener;

	/**
	 * Private constructor to initialize the library folder manager.
	 * 
	 * <p>
	 * This constructor should not be called by clients. Use the
	 * {@link #getInstance()} to obtain an instance of the class.
	 * </p>
	 * 
	 * <p>
	 * This constructor registers the {@link AutoDetectLibraryFolderListener} as
	 * a resource change listener.
	 * </p>
	 */
	private LibraryFolderManager() {
		listener = new AutoDetectLibraryFolderListener();
		listeners = Collections.synchronizedSet(new HashSet<ILibraryFolderChangeListener>());
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
	}

	/**
	 * Returns the instance of the singleton class.
	 * 
	 * @return an instance of <code>LibraryFolderManager</code>.
	 */
	public static synchronized LibraryFolderManager getInstance() {
		if (instance == null) {
			instance = new LibraryFolderManager();
		}
		return instance;
	}

	/**
	 * Adds the given listener for library folder change events. Has no effect
	 * if an identical listener is already registered.
	 * 
	 * <p>
	 * The listener will be notified whenever a source folder is turned to
	 * library folder and vice versa.
	 * </p>
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(ILibraryFolderChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the given library folder change listener from this workspace. Has
	 * no effect if an identical listener is not registered.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(ILibraryFolderChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Marks the given source folders as library folders.
	 * 
	 * <p>
	 * This method executes the following steps:
	 * <ol>
	 * <li>Disables the given folders in the WTP Validation Framework.</li>
	 * <li>Notifies the registered library folder change listeners, e.g. for
	 * updating the images of the given folders and all their subfolders in the
	 * PHP Explorer.</li>
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
	 * @param folders
	 *            an array of {@link IFolder} objects that represents the source
	 *            folders to be marked as library folders
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
	 * @see {@link #useAsSourceFolder(IFolder[], IProgressMonitor)}
	 */
	public void useAsLibraryFolder(IFolder[] folders, IProgressMonitor monitor)
			throws OperationCanceledException, InterruptedException, CoreException {
		disableValidation(folders);

		folders = removeNonExisting(folders);
		if (folders.length > 0) {
			notifyListeners(folders);
			waitValidationJobs(monitor);
			deleteMarkers(folders);
		}
	}

	/**
	 * Marks the given library folders as source folders.
	 * 
	 * <p>
	 * This method executes the following steps:
	 * <ol>
	 * <li>Enables the given folders in the WTP Validation Framework.</li>
	 * <li>Notifies the registered library folder change listeners, e.g. for
	 * updating the images of the given folders and all their subfolders in the
	 * PHP Explorer.</li>
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
	 * @param folders
	 *            an array of {@link IFolder} objects that represents the
	 *            library folders to be marked as source folders
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
	 * @see {@link #useAsLibraryFolder(IFolder[], IProgressMonitor)}
	 */
	public void useAsSourceFolder(IFolder[] folders, IProgressMonitor monitor)
			throws OperationCanceledException, InterruptedException, CoreException {
		enableValidation(folders);

		folders = removeNonExisting(folders);
		if (folders.length > 0) {
			notifyListeners(folders);
			waitValidationJobs(monitor);
			revalidate(folders, monitor);
		}
	}

	/**
	 * Enables validation for the given folder in the WTP Validation Framework.
	 * 
	 * @param folder
	 *            a folder to enable validation for
	 */
	public void enableValidation(IFolder folder) {
		ValidationFramework vf = ValidationFramework.getDefault();
		vf.enableValidation(folder);
	}

	/**
	 * Disables validation for the given folder in the WTP Validation Framework.
	 * 
	 * <p>
	 * This method cleans up the disabled state for all subfolders of the given
	 * folder. This is necessary to avoid nested declarations of library
	 * folders. Otherwise, if the user marks a library folder as a source folder
	 * and there are nested library folders then there will be still subfolders
	 * which remain library folders.
	 * </p>
	 * 
	 * @param folder
	 *            a folder to disable validation for
	 * 
	 * @throws CoreException
	 *             if any of the folders does not exist or is in a closed
	 *             project
	 */
	public void disableValidation(IFolder folder) throws CoreException {
		ValidationFramework vf = ValidationFramework.getDefault();

		// clean up the state of all subfolders
		for (IFolder subfolder : getAllSubfolders(folder)) {
			vf.enableValidation(subfolder);
		}

		if (!isInLibraryFolder(folder)) {
			// disable the given folder only if no parent folder is a library
			// folder yet
			vf.disableValidation(folder);
		}
	}

	/**
	 * Enables validation for the given folders in the WTP Validation Framework.
	 * 
	 * <p>
	 * This method invokes {@link #enableValidation(IFolder)} for each of the
	 * folders in the given array.
	 * </p>
	 * 
	 * @param folders
	 *            an array of folders to enable validation for
	 */
	public void enableValidation(IFolder[] folders) {
		for (IFolder folder : folders) {
			enableValidation(folder);
		}
	}

	/**
	 * Disables validation for the given folders in the WTP Validation
	 * Framework.
	 * 
	 * <p>
	 * This method invokes {@link #disableValidation(IFolder)} for each of the
	 * folders in the given array.
	 * </p>
	 * 
	 * @param folders
	 *            an array of folders to disable validation for
	 * 
	 * @throws CoreException
	 *             if any of the folders does not exist or is in a closed
	 *             project
	 */
	public void disableValidation(IFolder[] folders) throws CoreException {
		for (IFolder folder : folders) {
			disableValidation(folder);
		}
	}

	/**
	 * Returns whether the given resource is inside a library folder.
	 * 
	 * <p>
	 * A library folder is a source folder that is disabled for validation in
	 * the WTP Validation Framework. So, this method checks if a parent folder
	 * of the given resource is disabled for validation.
	 * </p>
	 * 
	 * @param resource
	 *            a resource to check if inside a library folder
	 * 
	 * @return <code>true</code> if the given resource is inside a library
	 *         folder, and <code>false</code> otherwise
	 * 
	 * @see ValidationFramework#disableValidation(IResource)
	 */
	public boolean isInLibraryFolder(IResource resource) {
		if (resource == null)
			return false;

		if (resource.getType() == IResource.FILE) {
			// the resource is a source file, so take its parent folder
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			// check if the folder is disabled in the WTP Validation Framework
			if (isExplicitlyDisabled((IFolder) resource)) {
				return true;
			}
			// the folder is not disabled, so check its parent folder
			resource = resource.getParent();
		}

		// none of the resource's parent folders is disabled in the WTP
		// Validation Framework, so the resource is not inside a library folder
		return false;
	}

	/**
	 * Returns whether the the given folder is a library folder that is
	 * explicitly disabled in the WTP Validation Framework.
	 * 
	 * <p>
	 * This is a folder that was passed as parameter to the
	 * {@link #disableValidation(IFolder)} method.
	 * </p>
	 * 
	 * @param folder
	 *            a folder to check
	 * 
	 * @return <code>true</code> if the folder is a explicitly disabled, and
	 *         <code>false</code> otherwise
	 */
	public boolean isExplicitlyDisabled(IFolder folder) {
		if (folder == null)
			return false;

		return DisabledResourceManager.getDefault().isDisabled(folder);
	}

	/**
	 * Returns the explicitly disabled parent library folder of the given
	 * resource.
	 * 
	 * <p>
	 * This is a parent folder that was passed as parameter to the
	 * {@link #disableValidation(IFolder)} method.
	 * </p>
	 * 
	 * @param resource
	 *            a resource
	 * 
	 * @return the explicitly disabled parent library folder of the given
	 *         resource
	 */
	public IFolder getExplicitlyDisabledParent(IResource resource) {
		if (resource == null)
			return null;

		if (resource.getType() == IResource.FILE) {
			// the resource is a source file, so take its parent folder
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			IFolder folder = (IFolder) resource;
			// check if the folder is disabled in the WTP Validation Framework
			if (isExplicitlyDisabled(folder)) {
				return folder;
			}
			// the folder is not disabled, so check its parent folder
			resource = resource.getParent();
		}

		// none of the resource's parent folders is disabled in the WTP
		// Validation Framework, so the resource is not inside a library folder
		// and therefore has no explicitly disabled parent library folder
		return null;
	}

	/**
	 * Returns all subfolders (recursively) of the given folders.
	 * 
	 * <p>
	 * This method invokes {@link #getAllSubfolders(IFolder)} for each of the
	 * folders in the given array and merges the result.
	 * </p>
	 * 
	 * @param folders
	 *            an array of folders
	 * 
	 * @return an array of folders containing the folders of the given array and
	 *         all their subfolders
	 * 
	 * @throws CoreException
	 *             if any of the folders does not exist or is in a closed
	 *             project
	 */
	public IFolder[] getAllSubfolders(IFolder[] folders) throws CoreException {
		Collection<IFolder> allSubfolders = new HashSet<IFolder>();

		for (IFolder folder : folders) {
			allSubfolders.addAll(Arrays.asList(getAllSubfolders(folder)));
		}

		return allSubfolders.toArray(new IFolder[allSubfolders.size()]);
	}

	/**
	 * Returns all subfolders (recursively) of the given folder.
	 * 
	 * <p>
	 * This method traverses the complete folder's subtree to find the
	 * subfolders on all levels.
	 * </p>
	 * 
	 * @param folder
	 *            a folder
	 * 
	 * @return an array of folders containing the given folder and all its
	 *         subfolders
	 * 
	 * @throws CoreException
	 *             if the folder does not exist or is in a closed project
	 */
	public IFolder[] getAllSubfolders(IFolder folder) throws CoreException {
		List<IFolder> result = new ArrayList<IFolder>();

		collectAllSubfolders((IFolder) folder, result);

		return result.toArray(new IFolder[result.size()]);
	}

	/**
	 * WARNING: This method should not be used by the clients.
	 * 
	 * Enables/disables detection for given project.
	 * 
	 * @param project
	 * @param suspend
	 */
	public void suspendDetection(IProject project, boolean suspend) {
		listener.suspendDetection(project, suspend);
	}

	/**
	 * WARNING: This method should not be used by the clients.
	 * 
	 * Enables/disables whole detection.
	 * 
	 * @param suspend
	 */
	public void suspendAllDetection(boolean suspend) {
		listener.suspendAllDetection(suspend);
	}

	/**
	 * Add the given folder and all its subfolders (recursively) to the given
	 * result collection.
	 * 
	 * <p>
	 * This method traverses the complete folder's subtree to find the
	 * subfolders on all levels.
	 * </p>
	 * 
	 * @param folder
	 *            a folder
	 * @param result
	 *            a collection of folder to hold the result
	 * 
	 * @throws CoreException
	 *             if the folder does not exist or is in a closed project
	 */
	private void collectAllSubfolders(IFolder folder, Collection<IFolder> result) throws CoreException {
		result.add(folder);

		for (IResource child : folder.members()) {
			if (child.getType() == IResource.FOLDER) {
				collectAllSubfolders((IFolder) child, result);
			}
		}
	}

	/**
	 * Notifies the registered library folder change listeners that the given
	 * folders have changed from source folders to library folders or vice
	 * versa.
	 * 
	 * @param folders
	 *            the changed folders
	 */
	private void notifyListeners(IFolder[] folders) {
		synchronized (listeners) {
			for (ILibraryFolderChangeListener listener : listeners) {
				try {
					listener.foldersChanged(folders);
				} catch (Exception e) {
					PHPCorePlugin.log(e);
				}
			}
		}
	}

	/**
	 * Removes the non-existing folders from the given array.
	 * 
	 * <p>
	 * The result is returned as new array. The given array remains untouched.
	 * </p>
	 * 
	 * @param folders
	 *            an array of folders
	 * 
	 * @return a new array that contains only the existing folders of the given
	 *         array
	 */
	private IFolder[] removeNonExisting(IFolder[] folders) {
		Collection<IFolder> existing = new HashSet<IFolder>();

		for (IFolder folder : folders) {
			if (folder.exists()) {
				existing.add(folder);
			}
		}

		return existing.toArray(new IFolder[existing.size()]);
	}

	/**
	 * Delete all problem and tasks markers on all the given resources and all
	 * their children.
	 * 
	 * <p>
	 * Only markers of types {@link IMarker#PROBLEM} and {@link IMarker#TASK}
	 * and their subtypes are deleted. Markers of other types (bookmarks,
	 * breakpoints, etc.) are not affected.
	 * </p>
	 * 
	 * @param resources
	 *            an array of resources
	 * 
	 * @throws CoreException
	 *             if deleting the markers on any resource fails
	 */
	private void deleteMarkers(IResource[] resources) throws CoreException {
		for (IResource resource : resources) {
			resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			resource.deleteMarkers(IMarker.TASK, true, IResource.DEPTH_INFINITE);
		}
	}

	/**
	 * Triggers validation jobs for the given resources and all their children.
	 * 
	 * @param resources
	 *            an array of resources
	 * @param monitor
	 *            a progress monitor, or null if progress reporting is not
	 *            desired
	 * 
	 * @throws CoreException
	 *             if touching of any of the resources fails
	 */
	private void revalidate(IResource[] resources, IProgressMonitor monitor) throws CoreException {
		for (IResource resource : resources) {
			deepTouch(resource, monitor);
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
	private void deepTouch(IResource resource, IProgressMonitor monitor) throws CoreException {
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
	private void waitValidationJobs(IProgressMonitor monitor) throws OperationCanceledException, InterruptedException {
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, monitor);
	}

}
