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
/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

/**
 * An interface that should be implement by any node that defines an unary or binary operation.
 * 
 * @author shalom
 */
public interface IOperationNode {

	/**
	 * Returns the string representation of the operation (e.g. +, -, ~, ++, etc.).
	 * 
	 * @return The string representation of the operation
	 */
	public String getOperationString();

	/**
	 * Translate the given operation id to the string operation that it describes.
	 * 
	 * @param op The operation code.
	 * @return A string of the operation.
	 */
	public String getOperationString(int op);
}
