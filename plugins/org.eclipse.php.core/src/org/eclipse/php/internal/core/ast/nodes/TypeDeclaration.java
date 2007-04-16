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
package org.eclipse.php.internal.core.ast.nodes;

/**
 * Represents base class for class declaration and interface declaration  
 */
public abstract class TypeDeclaration extends Statement {

	private final Identifier name;
	private final Identifier[] interfaces;
	private final Block body;

	public TypeDeclaration(int start, int end, final Identifier name, final Identifier[] interfaces, final Block body) {
		super(start, end);

		assert name != null && body != null;
		this.name = name;
		this.interfaces = interfaces;
		this.body = body;

		name.setParent(this);
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].setParent(this);
		}
		body.setParent(this);
	}

	public Block getBody() {
		return body;
	}

	public Identifier[] getInterfaces() {
		return interfaces;
	}

	public Identifier getName() {
		return name;
	}

}
