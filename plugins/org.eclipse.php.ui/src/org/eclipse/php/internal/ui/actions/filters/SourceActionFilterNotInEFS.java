/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions.filters;

import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class SourceActionFilterNotInEFS implements IActionFilterContributor {

	@Override
	public boolean testAttribute(Object target, String name, String value) {
		/*
		 * if (target instanceof IModelElement) { IModelElement modelElem =
		 * (IModelElement) target; final IResource resource = modelElem.getResource();
		 * 
		 * return resource != null && resource.exists(); }
		 */
		// file is not in EFS, e.g include path
		// TODO what about external files? should be fixed according to the DLTK
		// rules
		return true;
	}

}
