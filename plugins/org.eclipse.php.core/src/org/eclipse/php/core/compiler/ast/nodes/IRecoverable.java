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
package org.eclipse.php.core.compiler.ast.nodes;

/**
 * Represents recoverable AST node
 * 
 * @author michael
 * 
 */
public interface IRecoverable {

	/**
	 * Whether this declaration node was error-recovered by the AST parser
	 * 
	 * @return
	 */
	public abstract boolean isRecovered();

	/**
	 * Sets whether this declaration node was error-recovered by the AST parser
	 * 
	 * @param isRecovered
	 */
	public abstract void setRecovered(boolean isRecovered);

}