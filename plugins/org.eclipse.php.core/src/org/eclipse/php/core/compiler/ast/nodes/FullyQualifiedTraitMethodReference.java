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
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;

public class FullyQualifiedTraitMethodReference extends Expression {

	private String functionName;
	private TypeReference className;

	public FullyQualifiedTraitMethodReference(int start, int end, TypeReference className, String functionName) {
		super(start, end);
		this.className = className;
		this.functionName = functionName;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (className != null) {
				className.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public String getFunctionName() {
		return functionName;
	}

	public TypeReference getClassName() {
		return className;
	}

	@Override
	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

}
