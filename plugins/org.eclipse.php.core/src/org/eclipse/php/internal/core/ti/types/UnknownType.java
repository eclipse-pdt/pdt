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
 * Class that represents unknown type of PHP element (for example,
 * we couldn't determine what is the return type of foo(), so in this case:
 * $a = foo(); variable type of $a will be unknown. 
 */
public class UnknownType implements IEvaluatedType {

	public static final IEvaluatedType INSTANCE = new UnknownType();

	/**
	 * The constructor is private so that we can rely on comparing with
	 * <code>INSTANCE</code>.
	 */
	private UnknownType() {
	}

	public String toString() {
		return "Unknown"; //$NON-NLS-1$
	}
}
