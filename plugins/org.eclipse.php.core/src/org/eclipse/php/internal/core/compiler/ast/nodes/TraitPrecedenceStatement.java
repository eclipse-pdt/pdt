package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;

public class TraitPrecedenceStatement extends TraitStatement {
	private TraitPrecedence precedence;

	public TraitPrecedenceStatement(int start, int end,
			TraitPrecedence precedence) {
		super(start, end);
		this.precedence = precedence;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (precedence != null) {
				precedence.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	public TraitPrecedence getPrecedence() {
		return precedence;
	}

	@Override
	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

}
