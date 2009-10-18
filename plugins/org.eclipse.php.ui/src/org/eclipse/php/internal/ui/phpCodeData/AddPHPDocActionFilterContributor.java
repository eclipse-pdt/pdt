/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.phpCodeData;

import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class AddPHPDocActionFilterContributor implements
		IActionFilterContributor {

	public boolean testAttribute(Object target, String name, String value) {
		return true;
		// if (!(target instanceof ISourceModule) && !(target instanceof IType)
		// && !(target instanceof IMethod) && !(target instanceof IField) &&
		// !(target instanceof Member) ) {
		// return false;
		// }
		// return true;
		// IModelElement modelElement = (IModelElement)target;
		// return (IMember)modelElement.get != null;
	}
}
