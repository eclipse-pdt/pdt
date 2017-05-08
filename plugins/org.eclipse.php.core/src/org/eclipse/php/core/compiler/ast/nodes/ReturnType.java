/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Statement;

public class ReturnType extends Statement {

	private TypeReference returnType;

	public ReturnType(TypeReference returnType) {
		super(returnType.start(), returnType.end());
		this.returnType = returnType;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (returnType != null) {
				returnType.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public TypeReference getReturnType() {
		return returnType;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.RETURN_TYPE;
	}

}
