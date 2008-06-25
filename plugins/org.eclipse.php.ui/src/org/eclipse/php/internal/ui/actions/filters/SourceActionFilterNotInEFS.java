/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions.filters;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class SourceActionFilterNotInEFS implements IActionFilterContributor {

	public boolean testAttribute(Object target, String name, String value) {
		/*if (target instanceof IModelElement) {
			IModelElement modelElem = (IModelElement) target;
			final IResource resource = modelElem.getResource();
			
			return resource != null && resource.exists();
		}*/
		// file is not in EFS, e.g include path
		// TODO what about external files? should be fixed according to the DLTK rules
		return true;
	}

}
