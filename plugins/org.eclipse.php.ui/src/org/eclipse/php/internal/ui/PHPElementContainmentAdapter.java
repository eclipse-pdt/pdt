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
package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.ui.IContainmentAdapter;


public class PHPElementContainmentAdapter implements IContainmentAdapter {

	private PHPWorkspaceModelManager fModel = PHPWorkspaceModelManager.getInstance();

	public boolean contains(Object workingSetElement, Object element, int flags) {
		if (!(workingSetElement instanceof PHPCodeData || workingSetElement instanceof PHPWorkspaceModelManager || workingSetElement instanceof PHPProjectModel) || element == null)
			return false;

		IResource resource = null;
		Object jElement = null;
		if (workingSetElement instanceof PHPCodeData || workingSetElement instanceof PHPWorkspaceModelManager || workingSetElement instanceof PHPProjectModel) {
			jElement = element;
			resource = PHPModelUtil.getResource(element);
		} else {
			if (element instanceof IAdaptable) {
				resource = (IResource) ((IAdaptable) element).getAdapter(IResource.class);

			}
		}

		if (jElement != null) {
			if (containsElement(workingSetElement, jElement, flags))
				return true;
		} else if (resource != null) {
			return containsResource(workingSetElement, resource, flags);
		}
		return false;
	}

	private boolean containsElement(Object workingSetElement, Object element, int flags) {
		if (checkContext(flags) && workingSetElement.equals(element)) {
			return true;
		}
		if (checkIfChild(flags) && workingSetElement.equals(PHPModelUtil.getParent(element))) {
			return true;
		}
		if (checkIfDescendant(flags) && check(workingSetElement, element)) {
			return true;
		}
		if (checkIfAncestor(flags) && check(element, workingSetElement)) {
			return true;
		}
		return false;
	}

	private boolean check(Object ancestor, Object descendent) {
		descendent = PHPModelUtil.getParent(descendent);
		while (descendent != null) {
			if (ancestor.equals(descendent))
				return true;
			descendent = PHPModelUtil.getParent(descendent);
		}
		return false;
	}

	private boolean isChild(Object workingSetElement, IResource element) {
		IResource resource = PHPModelUtil.getResource(workingSetElement);
		if (resource == null)
			return false;
		return check(element, resource);
	}

	private boolean containsResource(Object workingSetElement, IResource element, int flags) {
		IResource workingSetResource = PHPModelUtil.getResource(workingSetElement);
		if (workingSetResource == null)
			return false;
		if (checkContext(flags) && workingSetResource.equals(element)) {
			return true;
		}
		if (checkIfChild(flags) && workingSetResource.equals(element.getParent())) {
			return true;
		}
		if (checkIfDescendant(flags) && check(workingSetResource, element)) {
			return true;
		}
		if (checkIfAncestor(flags) && check(element, workingSetResource)) {
			return true;
		}
		return false;
	}

	private boolean check(IResource ancestor, IResource descendent) {
		descendent = descendent.getParent();
		while (descendent != null) {
			if (ancestor.equals(descendent))
				return true;
			descendent = descendent.getParent();
		}
		return false;
	}

	private boolean checkIfDescendant(int flags) {
		return (flags & CHECK_IF_DESCENDANT) != 0;
	}

	private boolean checkIfAncestor(int flags) {
		return (flags & CHECK_IF_ANCESTOR) != 0;
	}

	private boolean checkIfChild(int flags) {
		return (flags & CHECK_IF_CHILD) != 0;
	}

	private boolean checkContext(int flags) {
		return (flags & CHECK_CONTEXT) != 0;
	}
}
