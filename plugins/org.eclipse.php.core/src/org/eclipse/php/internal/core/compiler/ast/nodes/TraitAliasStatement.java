package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;

public class TraitAliasStatement extends TraitStatement {
	private TraitAlias alias;

	public TraitAliasStatement(int start, int end, TraitAlias alias) {
		super(start, end);
		this.alias = alias;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			if (alias != null) {
				alias.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public TraitAlias getAlias() {
		return alias;
	}

	@Override
	public int getKind() {
		return 0;
	}

}
