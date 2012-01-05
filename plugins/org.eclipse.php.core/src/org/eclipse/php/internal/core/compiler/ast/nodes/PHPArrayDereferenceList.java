/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;

public class PHPArrayDereferenceList extends ASTListNode {
	private ASTNode parent;

	public PHPArrayDereferenceList() {
		super();
	}

	public PHPArrayDereferenceList(ASTNode parent) {
		super();
		this.parent = parent;
	}

	public PHPArrayDereferenceList(int start, int end) {
		super(start, end);
	}

	public PHPArrayDereferenceList(ASTNode parent, int start, int end) {
		super(start, end);
		this.parent = parent;
	}

	public void setParent(ASTNode parent) {
		this.parent = parent;
	}

	public ASTNode getParent() {
		return parent;
	}
}
