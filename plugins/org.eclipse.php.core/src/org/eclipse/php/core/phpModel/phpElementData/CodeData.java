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
package org.eclipse.php.core.phpModel.phpElementData;


import java.io.Serializable;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.core.util.Visitable;
import org.eclipse.php.core.util.Visitor;

/**
 * The top level interface for all langauges code.
 */
public interface CodeData extends Comparable, Serializable, Visitable, IAdaptable {

	/**
	 * Returns the name of the CodeData.
	 * @return The name of the CodeData.
	 */
	public String getName();

	/**
	 * Returns a description of the CodeData.
	 * @return Description of the CodeData.
	 */
	public String getDescription();

	/**
	 * return true if this CodeData is user code
	 */
	public boolean isUserCode();

	/**
	 * Returns the user data
	 * @return the user data
	 */
	public UserData getUserData();

	/**
	 * Accept the specific visitor
	 * @param v the visitor to accept
	 */
	public void accept(Visitor v);

}