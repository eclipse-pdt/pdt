package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;

public class TraitPrecedence extends Expression {
	private FullyQualifiedTraitMethodReference methodReference;
	private List<TypeReference> trList;

	public TraitPrecedence(int start, int end,
			FullyQualifiedTraitMethodReference methodReference,
			List<TypeReference> trList) {
		super(start, end);
		this.methodReference = methodReference;
		this.trList = trList;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (methodReference != null) {
				methodReference.traverse(visitor);
			}
			if (trList != null) {
				for (TypeReference tr : trList) {
					tr.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public FullyQualifiedTraitMethodReference getMethodReference() {
		return methodReference;
	}

	public List<TypeReference> getTrList() {
		return trList;
	}

	@Override
	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

}
