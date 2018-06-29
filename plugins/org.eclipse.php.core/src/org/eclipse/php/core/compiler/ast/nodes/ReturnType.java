/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	@Override
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
