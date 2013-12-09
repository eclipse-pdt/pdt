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
package org.eclipse.php.internal.core.util;

import static org.eclipse.php.core.util.LibraryFolderUtil.isInLibraryFolder;

import java.util.*;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.wst.validation.ValidationFramework;
import org.eclipse.wst.validation.internal.DisabledResourceManager;

/**
 * Helper methods for working with library folders.
 * 
 * @author Kaloyan Raev
 */
public class LibraryFolderHelper {

	/**
	 * Enables validation for the given model element in the WTP Validation
	 * Framework.
	 * 
	 * @param element
	 *            a model elements to enable validation for
	 */
	public static void enableValidation(IModelElement element) {
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
	public static void disableValidation(IModelElement element)
			throws ModelException {
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
	public static void enableValidation(IModelElement[] elements) {
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
	public static void disableValidation(IModelElement[] elements)
			throws ModelException {
		for (IModelElement element : elements) {
			disableValidation(element);
		}
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
	public static boolean isTopmostLibraryFolder(IResource resource) {
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
	public static boolean isTopmostLibraryFolder(IModelElement element) {
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
	public static IModelElement getTopmostLibraryFolder(IModelElement element) {
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
	public static IModelElement[] getAllSubfolders(IModelElement[] elements)
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
	public static IModelElement[] getAllSubfolders(IModelElement element)
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
	private static boolean pathContainsModelElement(IPath path,
			IModelElement element) {
		// get the first segments from the elements path
		IPath elementPathPrefix = element.getPath().uptoSegment(
				path.segmentCount());

		// compare the prefix with the given path
		return path.equals(elementPathPrefix);
	}

}
