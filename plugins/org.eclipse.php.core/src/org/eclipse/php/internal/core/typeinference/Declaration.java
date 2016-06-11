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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ast.ASTNode;

/**
 * This is a container for variable declaration
 * 
 * @author michael
 */
public class Declaration {

	private boolean global;
	private ASTNode declNode;

	public Declaration(boolean global, ASTNode declNode) {
		this.global = global;
		this.declNode = declNode;
	}

	/**
	 * Whether this declaration actually belongs to global scope - global $var
	 * was specified earlier.
	 */
	public boolean isGlobal() {
		return global;
	}

	/**
	 * Sets whether this declaration actually belongs to global scope - global
	 * $var was specified earlier.
	 */
	public void setGlobal(boolean global) {
		this.global = global;
	}

	/**
	 * Returns the declaration node itself.
	 */
	public ASTNode getNode() {
		return declNode;
	}

	/**
	 * Sets the declaration node itself.
	 */
	public void setNode(ASTNode declNode) {
		this.declNode = declNode;
	}
}