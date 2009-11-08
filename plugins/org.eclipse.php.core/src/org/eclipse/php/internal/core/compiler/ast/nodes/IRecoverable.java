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
package org.eclipse.php.internal.core.compiler.ast.nodes;

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