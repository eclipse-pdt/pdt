/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.dltk.core.IModelElement;

/**
 * A Source binding represents fully-resolved resource. 
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 *
 * @see ISourceBinding#getDeclaredTypes()
 * @since 2.0
 */
public interface ISourceBinding extends IBinding {


	/**
	 * Returns the binary name of this type binding.
	 * The binary name of a resource is the string given by the user
	 * in the code, before resolving the Binding
	 *
	 * @return the binary name of this resource, or <code>null</code>
	 * if the binary name is unknown
	 * @since 3.0
	 */
	public String getBinaryName();


	/**
	 * Returns the resource binding of the file where this iclude statement appears
	 *
	 * @return the binding of the type that declares this type, or
	 * <code>null</code> if none
	 */
	public ISourceBinding getDeclaringSource();



	/**
	 * Returns the full path of the resource

	 *
	 * @return the unqualified name of the type represented by this binding,
	 * or the empty string if it has none
	 */
	public String getName();



	/**
	 * Returns the element in the model representing the specified source

	 *
	 * @return Returns the element in the model representing the specified source
	 */
	public IModelElement getSource();

	
	
}
