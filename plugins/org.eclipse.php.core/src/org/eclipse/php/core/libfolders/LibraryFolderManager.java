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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
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
		listeners = Collections
				.synchronizedSet(new HashSet<ILibraryFolderChangeListener>());

		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				new AutoDetectLibraryFolderListener(),
				IResourceChangeEvent.POST_CHANGE);
	}

	/**
	 * Returns the instance of the singleton class.
	 * 
	 * @return an instance of <code>LibraryFolderManager</code>.
	 */
	public static LibraryFolderManager getInstance() {
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
	 * The given array of model elements should contain {@link IScriptFolder} or
	 * {@link IProjectFragment} objects.
	 * </p>
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
	public void useAsLibraryFolder(IModelElement[] elements,
			IProgressMonitor monitor) throws OperationCanceledException,
			InterruptedException, CoreException {
		disableValidation(elements);

		elements = removeNonExisting(elements);
		if (elements.length > 0) {
			notifyListeners(elements);
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
	public void useAsSourceFolder(IModelElement[] elements,
			IProgressMonitor monitor) throws OperationCanceledException,
			InterruptedException, CoreException {
		enableValidation(elements);

		elements = removeNonExisting(elements);
		if (elements.length > 0) {
			notifyListeners(elements);
			waitValidationJobs(monitor);
			revalidate(elements, monitor);
		}
	}

	/**
	 * Enables validation for the given model element in the WTP Validation
	 * Framework.
	 * 
	 * @param element
	 *            a model elements to enable validation for
	 */
	public void enableValidation(IModelElement element) {
		ValidationFramework vf = ValidationFramework.getDefault();
		vf.enableValidation(element.getResource());
	}

	/**
	 * Disables validation for the given model element in the WTP Validation
	 * Framework.
	 * 
	 * <p>
	 * This method cleans up the disabled state for all subfolders of the given
	 * model element (i.e. source folder). This is necessary to avoid nested
	 * declarations of library folders. Otherwise, if the user marks a library
	 * folder as a source folder and there are nested library folders then there
	 * will be still subfolders which remain library folders.
	 * </p>
	 * 
	 * @param elements
	 *            a model element to disable validation for
	 * 
	 * @throws ModelException
	 *             if the given element does not exist or if an exception occurs
	 *             while accessing its corresponding resource
	 */
	public void disableValidation(IModelElement element) throws ModelException {
		ValidationFramework vf = ValidationFramework.getDefault();

		// clean up the state of all subfolders
		for (IModelElement subfolder : getAllSubfolders(element)) {
			vf.enableValidation(subfolder.getResource());
		}

		if (!isInLibraryFolder(element)) {
			// disable the given folder only if no parent folder is a library
			// folder yet
			vf.disableValidation(element.getResource());
		}
	}

	/**
	 * Enables validation for the given model elements in the WTP Validation
	 * Framework.
	 * 
	 * <p>
	 * This method invokes {@link #enableValidation(IModelElement)} for each of
	 * the model elements in the given array.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements to enable validation for
	 */
	public void enableValidation(IModelElement[] elements) {
		for (IModelElement element : elements) {
			enableValidation(element);
		}
	}

	/**
	 * Disables validation for the given model elements in the WTP Validation
	 * Framework.
	 * 
	 * <p>
	 * This method invokes {@link #disableValidation(IModelElement)} for each of
	 * the model elements in the given array.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements to disable validation for
	 * 
	 * @throws ModelException
	 *             if any of the given element does not exist or if an exception
	 *             occurs while accessing its corresponding resource
	 */
	public void disableValidation(IModelElement[] elements)
			throws ModelException {
		for (IModelElement element : elements) {
			disableValidation(element);
		}
	}

	/**
	 * Returns whether the given resource is inside a library folder.
	 * 
	 * <p>
	 * A library folder is a source folder that is disabled for validation in
	 * the WTP Validation Framework. So, this method checks if a parent folder
	 * of the given model element is disabled for validation.
	 * </p>
	 * 
	 * @param resource
	 *            a resource to check if inside a library folder
	 * 
	 * @return <code>true</code> if the given model element is inside a library
	 *         folder, and <code>false</code> otherwise
	 * 
	 * @see ValidationFramework#disableValidation(IResource)
	 */
	public boolean isInLibraryFolder(IResource resource) {
		if (resource == null)
			// the model element has no corresponding resource, so it is not
			// part of any source file and hence cannot be in a library folder
			return false;

		if (resource.getType() == IResource.FILE) {
			// the model element is a source file, so take its parent folder
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			// check if the folder is disabled in the WTP Validation Framework
			if (isTopmostLibraryFolder(resource)) {
				return true;
			}
			// the folder is not disabled, so check its parent folder
			resource = resource.getParent();
		}

		// none of the element's parent folders is disabled in the WTP
		// Validation Framework, so the element is not inside a library folder
		return false;
	}

	/**
	 * Returns whether the given model element is inside a library folder.
	 * 
	 * <p>
	 * A library folder is a source folder that is disabled for validation in
	 * the WTP Validation Framework. So, this method checks if a parent folder
	 * of the given model element is disabled for validation.
	 * </p>
	 * 
	 * @param element
	 *            a model element to check if inside a library folder
	 * 
	 * @return <code>true</code> if the given model element is inside a library
	 *         folder, and <code>false</code> otherwise
	 * 
	 * @see ValidationFramework#disableValidation(IResource)
	 */
	public boolean isInLibraryFolder(IModelElement element) {
		if (element == null)
			return false;

		IResource resource = element.getResource();

		return isInLibraryFolder(resource);
	}

	/**
	 * Returns whether the the given resource is a topmost library folder.
	 * 
	 * <p>
	 * A topmost library folder is such folder that is explicitly disable in the
	 * WTP Validation Framework. The subfolders of a topmost library folder are
	 * library folders, but the parent of the topmost library folder is not a
	 * library folder.
	 * </p>
	 * 
	 * @param resource
	 *            a resource
	 * 
	 * @return <code>true</code> if the resource is a topmost library folder,
	 *         and <code>false</code> otherwise
	 */
	@SuppressWarnings("restriction")
	public boolean isTopmostLibraryFolder(IResource resource) {
		return DisabledResourceManager.getDefault().isDisabled(resource);
	}

	/**
	 * Returns whether the the given model element is a topmost library folder.
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
	 * @return <code>true</code> if the model element is a topmost library
	 *         folder, and <code>false</code> otherwise
	 */
	public boolean isTopmostLibraryFolder(IModelElement element) {
		return isTopmostLibraryFolder(element.getResource());
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
			if (isTopmostLibraryFolder(resource)) {
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

	/**
	 * Returns all subfolders of the given model elements.
	 * 
	 * <p>
	 * This method invokes {@link #getAllSubfolders(IModelElement)} for each of
	 * the model elements in the given array and merges the result.
	 * </p>
	 * 
	 * @param elements
	 *            an array of model elements
	 * 
	 * @return an array of model elements containing the elements of the given
	 *         array and all their children
	 * 
	 * @throws ModelException
	 *             if any of the given element does not exist or if an exception
	 *             occurs while accessing its corresponding resource
	 */
	public IModelElement[] getAllSubfolders(IModelElement[] elements)
			throws ModelException {
		Collection<IModelElement> allSubfolders = new HashSet<IModelElement>();

		for (IModelElement element : elements) {
			allSubfolders.addAll(Arrays.asList(getAllSubfolders(element)));
		}

		return allSubfolders.toArray(new IModelElement[allSubfolders.size()]);
	}

	/**
	 * Returns all subfolders of the given model element.
	 * 
	 * <p>
	 * This method traverses the complete element's subtree to find the
	 * subfolders on all levels.
	 * </p>
	 * 
	 * @param element
	 *            a model element
	 * 
	 * @return an array of model elements containing the given element and all
	 *         its children
	 * 
	 * @throws ModelException
	 *             if any of the given element does not exist or if an exception
	 *             occurs while accessing its corresponding resource
	 */
	public IModelElement[] getAllSubfolders(IModelElement element)
			throws ModelException {
		List<IModelElement> children = new ArrayList<IModelElement>();

		IPath path = element.getPath();

		// check all project fragments in the project
		IProjectFragment[] fragments = element.getScriptProject()
				.getProjectFragments();

		for (IProjectFragment fragment : fragments) {
			if (pathContainsModelElement(path, fragment)) {
				// the project fragment is in the path
				children.add(fragment);
			}

			// check all script folders in the project fragment
			for (IModelElement child : fragment.getChildren()) {
				if (child.getElementType() == IModelElement.SCRIPT_FOLDER
						&& pathContainsModelElement(path, child)) {
					// the script folder is in the path
					children.add(child);
				}
			}
		}

		return children.toArray(new IModelElement[children.size()]);
	}

	/**
	 * Notifies the registered library folder change listeners that the given
	 * folders have changed from source folders to library folders or vice
	 * versa.
	 * 
	 * @param elements
	 *            the model elements representing the changed folders
	 */
	private void notifyListeners(IModelElement[] elements) {
		synchronized (listeners) {
			for (ILibraryFolderChangeListener listener : listeners) {
				try {
					listener.folderChanged(elements);
				} catch (Exception e) {
					PHPCorePlugin.log(e);
				}
			}
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
	private IModelElement[] removeNonExisting(IModelElement[] elements) {
		Collection<IModelElement> existing = new HashSet<IModelElement>();

		for (IModelElement element : elements) {
			if (element.exists()) {
				existing.add(element);
			}
		}

		return existing.toArray(new IModelElement[existing.size()]);
	}

	/**
	 * Returns whether the given path contains the given model element.
	 * 
	 * <p>
	 * This method compares if the path of the given model element is a subpath
	 * of the given path.
	 * </p>
	 * 
	 * @param path
	 *            a path
	 * @param element
	 *            a model element
	 * 
	 * @return <code>true</code> if the given model element is in the given
	 *         path, and <code>false</code> otherwise
	 */
	private boolean pathContainsModelElement(IPath path, IModelElement element) {
		// get the first segments from the elements path
		IPath elementPathPrefix = element.getPath().uptoSegment(
				path.segmentCount());

		// compare the prefix with the given path
		return path.equals(elementPathPrefix);
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
	private void deleteMarkers(IModelElement[] elements) throws CoreException {

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
	private void revalidate(IModelElement[] elements, IProgressMonitor monitor)
			throws CoreException {
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
	private void deepTouch(IResource resource, IProgressMonitor monitor)
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
	private void waitValidationJobs(IProgressMonitor monitor)
			throws OperationCanceledException, InterruptedException {
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, monitor);
	}

}
