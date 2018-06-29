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
package org.eclipse.php.internal.ui.phpCodeData;

import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class AddPHPDocActionFilterContributor implements IActionFilterContributor {

	@Override
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
