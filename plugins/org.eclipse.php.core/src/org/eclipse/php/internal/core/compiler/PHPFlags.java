/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler;

import org.eclipse.dltk.core.Flags;

public class PHPFlags extends Flags implements IPHPModifiers {

	/**
	 * Returns whether the given integer includes the <code>internal</code> modifier.
	 *
	 * @param flags the flags
	 * @return <code>true</code> if the <code>internal</code> modifier is included
	 */
	public static boolean isInternal(int flags) {
		return (flags & Internal) != 0;
	}

	/**
	 * Returns whether the given integer includes the <code>namespace</code> modifier.
	 *
	 * @param flags the flags
	 * @return <code>true</code> if the <code>namespace</code> modifier is included
	 */
	public static boolean isNamespace(int flags) {
		return (flags & AccNameSpace) != 0;
	}

	/**
	 * Returns whether the given integer includes the <code>constant</code> modifier.
	 *
	 * @param flags the flags
	 * @return <code>true</code> if the <code>constant</code> modifier is included
	 */
	public static boolean isConstant(int flags) {
		return (flags & AccConstant) != 0;
	}
}
