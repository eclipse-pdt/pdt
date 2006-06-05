/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.workingset;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.ui.IWorkingSet;


/**
 * Working set filter for php viewers.
 */
public class WorkingSetFilter extends ViewerFilter {
	private IWorkingSet fWorkingSet = null;
	private IAdaptable[] fCachedWorkingSet = null;

	/**
	 * Returns the working set which is used by this filter.
	 * 
	 * @return the working set
	 */
	public IWorkingSet getWorkingSet() {
		return fWorkingSet;
	}

	/**
	 * Sets this filter's working set.
	 * 
	 * @param workingSet the working set
	 */
	public void setWorkingSet(IWorkingSet workingSet) {
		fWorkingSet = workingSet;
	}

	/*
	 * Overrides method from ViewerFilter.
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (fWorkingSet == null)
			return true;

		if (element instanceof PHPCodeData)
			return isEnclosing((PHPCodeData) element);

		if (element instanceof IResource)
			return isEnclosing(((IResource) element).getFullPath());

		if (element instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) element;
			PHPCodeData je = (PHPCodeData) adaptable.getAdapter(PHPCodeData.class);
			if (je != null)
				return isEnclosing(je);

			IResource resource = (IResource) adaptable.getAdapter(IResource.class);
			if (resource != null)
				return isEnclosing(resource.getFullPath());
		}

		return true;
	}

	/*
	 * Overrides method from ViewerFilter
	 */
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		Object[] result = null;
		if (fWorkingSet != null)
			fCachedWorkingSet = fWorkingSet.getElements();
		try {
			result = super.filter(viewer, parent, elements);
		} finally {
			fCachedWorkingSet = null;
		}
		return result;
	}

	private boolean isEnclosing(IPath elementPath) {
		if (elementPath == null)
			return false;

		IAdaptable[] cachedWorkingSet = fCachedWorkingSet;
		if (cachedWorkingSet == null)
			cachedWorkingSet = fWorkingSet.getElements();

		int length = cachedWorkingSet.length;
		for (int i = 0; i < length; i++) {
			if (isEnclosing(cachedWorkingSet[i], elementPath))
				return true;
		}
		return false;
	}

	public boolean isEnclosing(PHPCodeData element) {
		IAdaptable[] cachedWorkingSet = fCachedWorkingSet;
		if (cachedWorkingSet == null)
			cachedWorkingSet = fWorkingSet.getElements();

		boolean isElementPathComputed = false;
		IPath elementPath = null; // will be lazy computed if needed

		int length = cachedWorkingSet.length;
		for (int i = 0; i < length; i++) {
			PHPCodeData scopeElement = (PHPCodeData) cachedWorkingSet[i].getAdapter(PHPCodeData.class);
			if (scopeElement != null) {
				// compare php elements
				PHPCodeData searchedElement = element;
				while (scopeElement != null && searchedElement != null) {
					if (searchedElement.equals(scopeElement))
						return true;
					else {

						searchedElement = searchedElement.getContainer();
					}
				}
				while (scopeElement != null && element != null) {
					if (element.equals(scopeElement))
						return true;
					else
						scopeElement = scopeElement.getContainer();
				}
			} else {
				// compare resource paths
				if (!isElementPathComputed) {
					//					IResource elementResource= (IResource)element.getAdapter(IResource.class);
					//					if (elementResource != null)
					//						elementPath= elementResource.getFullPath();
				}
				if (isEnclosing(cachedWorkingSet[i], elementPath))
					return true;
			}
		}
		return false;
	}

	private boolean isEnclosing(IAdaptable element, IPath path) {
		if (path == null)
			return false;

		IPath elementPath = null;

		IResource elementResource = (IResource) element.getAdapter(IResource.class);
		if (elementResource != null)
			elementPath = elementResource.getFullPath();

		if (elementPath == null) {
			PHPCodeData phpElement = (PHPCodeData) element.getAdapter(PHPCodeData.class);
			if (phpElement != null)
				elementPath = PHPModelUtil.getResource(phpElement).getLocation();
		}

		if (elementPath == null && element instanceof IStorage)
			elementPath = ((IStorage) element).getFullPath();

		if (elementPath == null)
			return false;

		if (elementPath.isPrefixOf(path))
			return true;

		if (path.isPrefixOf(elementPath))
			return true;

		return false;
	}

}
