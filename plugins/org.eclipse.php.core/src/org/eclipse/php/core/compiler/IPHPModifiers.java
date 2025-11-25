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
package org.eclipse.php.core.compiler;

import org.eclipse.dltk.ast.Modifiers;

/**
 * This interface extends DLTK's {@link Modifiers} with PDT-related flags.
 * 
 */
public interface IPHPModifiers extends Modifiers {

	/**
	 * Represents non-PHP language element
	 */
	public static final int NonPHP = (1 << Modifiers.USER_MODIFIER);

	/**
	 * Constructor method
	 */
	public static final int Constructor = 1 << (Modifiers.USER_MODIFIER + 1);

	public static final int AccTrait = (1 << Modifiers.USER_MODIFIER + 2);
	public static final int AccDeprecated = (1 << Modifiers.USER_MODIFIER + 3);
	public static final int AccAnonymous = (1 << Modifiers.USER_MODIFIER + 4);

	/**
	 * Method / Function have return or yield statement
	 * 
	 * @since 4.0
	 */
	public static final int AccReturn = (1 << Modifiers.USER_MODIFIER + 5);

	/**
	 * Variadic method
	 * 
	 * @since 4.0
	 */
	public static final int AccVariadic = (1 << Modifiers.USER_MODIFIER + 6);

	/**
	 * Method phpDoc contains inheritdoc tag
	 * 
	 * @since 4.1
	 */
	public static final int AccInheritdoc = (1 << Modifiers.USER_MODIFIER + 7);

	/**
	 * Indicates nullable parameter/return type
	 * 
	 * @since 4.1
	 */
	public static final int AccNullable = (1 << Modifiers.USER_MODIFIER + 8);

	/**
	 * @since 8.0
	 */
	public static final int AccReadonly = (1 << Modifiers.USER_MODIFIER + 9);

	/**
	 * @since 8.0
	 */
	public static final int AccEnum = (1 << Modifiers.USER_MODIFIER + 10);

	/**
	 * @since 8.0
	 */
	public static final int AccEnumCase = Modifiers.AccConst; // integer END

	/**
	 * @since 8.3
	 */
	public static final int AccPrivateSet = (1 << Modifiers.USER_MODIFIER + 11);
	public static final int AccProtectedSet = (1 << Modifiers.USER_MODIFIER + 12);
	public static final int AccPublicSet = (1 << Modifiers.USER_MODIFIER + 13);

	/**
	 * All access modifiers as one for compiler validation
	 * 
	 * @since 4.0
	 */
	public static final int AccessMask = AccPublic | AccPrivate | AccProtected;

	/**
	 * @since 8.3
	 */
	public static final int AccessSetMask = AccPrivateSet | AccProtectedSet | AccPublicSet;

	public static final int USER_MODIFIER = Modifiers.USER_MODIFIER + 15;
}
