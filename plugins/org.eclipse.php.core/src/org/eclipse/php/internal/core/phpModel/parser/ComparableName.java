/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser;

/**
 * This interface contains a method for returning a String which is to be used for comparing and
 * Object to another one.
 * it is useful when the Object already has a name but you want to perform the comparison by another name.
 */
public interface ComparableName {

	public String getComparableName();
}
