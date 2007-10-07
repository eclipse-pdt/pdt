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

package org.eclipse.php.internal.core.ti.types;

/**
 * Common interface for PHP element class types
 */
public interface IClassType extends IEvaluatedType {

	/**
	 * Checks whether the given type is a super-type for this type 
	 * @param type
	 * @return <code>true</code> if given type is a super-type for this type
	 */
	boolean isSubtypeOf(IClassType classType);
}
