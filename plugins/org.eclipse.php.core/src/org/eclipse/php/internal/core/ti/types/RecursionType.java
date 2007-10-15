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

public class RecursionType implements IEvaluatedType {
	
	public static final IEvaluatedType INSTANCE = new RecursionType();

	/**
	 * The constructor is private so that we can rely on comparing with
	 * <code>INSTANCE</code>.
	 */
	private RecursionType() {
	}

	public String toString() {
		return "recursion type call"; //$NON-NLS-1$
	}
}
