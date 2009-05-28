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
package org.eclipse.php.internal.core.ast.nodes;

/**
 * This is the base class for all statements in the PHP AST tree 
 */
public abstract class Statement extends ASTNode {

	public Statement(int start, int end, AST ast) {
		super(start, end, ast);
	}

	public Statement(AST ast) {
		super(ast);
	}

}
