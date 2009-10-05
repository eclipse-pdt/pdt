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

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class VarComment extends Comment {

	private VariableReference variableReference;
	private TypeReference[] typeReference;

	public VarComment(int start, int end, VariableReference variableReference,
			TypeReference[] typeReference) {
		super(start, end, Comment.TYPE_MULTILINE);
		this.variableReference = variableReference;
		this.typeReference = typeReference;
	}

	public VariableReference getVariableReference() {
		return variableReference;
	}

	public TypeReference[] getTypeReferences() {
		return typeReference;
	}
}
