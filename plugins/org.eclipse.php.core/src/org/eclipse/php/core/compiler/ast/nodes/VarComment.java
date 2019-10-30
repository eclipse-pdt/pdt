/*******************************************************************************
 * Copyright (c) 2009-2019 IBM Corporation and others.
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

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class VarComment extends Comment {

	private VariableReference variableReference;
	private TypeReference[] typeReference;
	private int varTagStart;

	public VarComment(int start, int end, int varTagStart, VariableReference variableReference,
			TypeReference[] typeReference) {
		super(start, end, Comment.TYPE_MULTILINE);
		this.varTagStart = varTagStart;
		this.variableReference = variableReference;
		this.typeReference = typeReference;
	}

	/**
	 * @return start offset of the "@var" tag
	 */
	public int getVarTagStart() {
		return varTagStart;
	}

	public VariableReference getVariableReference() {
		return variableReference;
	}

	public TypeReference[] getTypeReferences() {
		return typeReference;
	}
}
