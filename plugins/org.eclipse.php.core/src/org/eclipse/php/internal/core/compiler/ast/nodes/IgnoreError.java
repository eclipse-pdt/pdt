package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents ignore error expression
 * <pre>e.g.<pre> '@$a->foo()'
 */
public class IgnoreError extends Expression {

	private final Expression expr;

	public IgnoreError(int start, int end) {
		this(start, end, null);
	}

	public IgnoreError(int start, int end, Expression expr) {
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
		return ASTNodeKinds.IGNORE_ERROR;
	}

	public Expression getExpr() {
		return expr;
	}
}
