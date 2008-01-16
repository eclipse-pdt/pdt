package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents a clone expression
 * <pre>e.g.<pre> clone $a,
 * $a = clone $b
 */
public class CloneExpression extends Expression {

	private final Expression expr;

	public CloneExpression(int start, int end, Expression expr) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CLONE_EXPRESSION;
	}

	public Expression getExpr() {
		return expr;
	}
}
