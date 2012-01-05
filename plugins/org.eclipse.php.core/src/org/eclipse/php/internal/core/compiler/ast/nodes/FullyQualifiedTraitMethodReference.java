package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;

public class FullyQualifiedTraitMethodReference extends Expression {

	private String functionName;
	private TypeReference className;

	public FullyQualifiedTraitMethodReference(int start, int end,
			TypeReference className, String functionName) {
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
