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
package org.eclipse.php.internal.ui.workingset;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetFilter;

/**
 * Overrides the DLTK working set filter to filter model elements only based on
 * their resource path enclosure.
 * 
 * @author Kaloyan Raev
 */
public class PHPWorkingSetFilter extends WorkingSetFilter {

	@Override
	public boolean isEnclosing(IModelElement element) {
		Assert.isNotNull(element);

		if (element instanceof IMember) {
			ISourceModule ancestor = element.getAncestor(ISourceModule.class);
			return ancestor == null ? false : isEnclosing(ancestor);
		}

		IAdaptable[] cachedWorkingSet = getWorkingSet().getElements();

		IResource resource = (IResource) element.getAdapter(IResource.class);
		if (resource == null) {
			IScriptProject ancestor = element.getAncestor(IScriptProject.class);
			if (ancestor != null) {
				return isEnclosing(ancestor);
			}
			return false;
		}

		IPath path = resource.getFullPath();
		for (int i = 0; i < cachedWorkingSet.length; i++) {
			// compare resource paths
			if (isEnclosing(cachedWorkingSet[i], path))
				return true;
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
			IModelElement scriptElement = (IModelElement) element.getAdapter(IModelElement.class);
			if (scriptElement != null)
				elementPath = scriptElement.getPath();
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
