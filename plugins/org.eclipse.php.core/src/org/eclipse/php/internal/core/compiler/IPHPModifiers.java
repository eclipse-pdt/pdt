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
package org.eclipse.php.internal.core.compiler;

import org.eclipse.dltk.ast.Modifiers;

public interface IPHPModifiers extends Modifiers {

	public static final int NonPhp = 2 << Modifiers.USER_MODIFIER;
	public static final int Internal = 2 << (Modifiers.USER_MODIFIER + 1);
	public static final int UseStatement = 2 << (Modifiers.USER_MODIFIER + 2);
	public static final int USER_MODIFIER = Modifiers.USER_MODIFIER + 3;
}
