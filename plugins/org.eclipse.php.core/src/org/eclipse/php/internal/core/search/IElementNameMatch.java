/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.core.IProjectFragment;

/**
 * PHP Search interface for a element name match.
 */
public interface IElementNameMatch {

	public static final int T_TYPE = 1;
	public static final int T_METHOD = 2;
	public static final int T_FIELD = 3;

	/**
	 * Returns the name of the element.
	 *
	 * @return the element name
	 */
	public String getSimpleName();

	/**
	 * Returns the matched element fully qualified name using '\' character as
	 * separator (e.g. namespace name + '\' enclosing type names + '\' simple
	 * name).
	 *
	 * @return Fully qualified name of the element
	 */
	public String getFullyQualifiedName();

	/**
	 * Name of the element container using '\' character as separator (e.g.
	 * namespace name + '\' + enclosing type names).
	 *
	 * @return Name of the element container
	 */
	public String getContainerName();

	/**
	 * Returns the modifiers of the matched element.
	 *
	 * @return the element modifiers
	 */
	public int getModifiers();

	/**
	 * Returns the type of the element
	 *
	 * @return the element type
	 */
	public int getElementType();

	/**
	 * Returns the matched field qualified name using '\' character as separator
	 * (e.g. enclosing type names + '\' + simple name).
	 *
	 * @return Fully qualified field name of the field
	 */
	public String getTypeQualifiedName();

	/**
	 * Returns the package name of the stored type.
	 *
	 * @throws NullPointerException
	 *             if matched type is <code> null</code>
	 * @return the package name
	 */
	public String getPackageName();

	/**
	 * Returns the project fragment of the stored type. Project fragment cannot
	 * be null and <strong>does</strong> exist.
	 *
	 * @throws NullPointerException
	 *             if matched type is <code> null</code>
	 * @return the existing Script model package fragment root (ie. cannot be
	 *         <code>null</code> and will return <code>true</code> to
	 *         <code>exists()</code> message).
	 */
	public IProjectFragment getProjectFragment();

}
