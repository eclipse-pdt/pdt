package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class VarComment extends Comment {

	private VariableReference variableReference;
	private TypeReference typeReference;

	public VarComment(int start, int end, VariableReference variableReference, TypeReference typeReference) {
		super(start, end, Comment.TYPE_MULTILINE);
		this.variableReference = variableReference;
		this.typeReference = typeReference;
	}

	public VariableReference getVariableReference() {
		return variableReference;
	}

	public TypeReference getTypeReference() {
		return typeReference;
	}
}
