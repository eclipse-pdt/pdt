package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents back tick expression
 * <pre>e.g.<pre>
 * `.\exec.sh`
 */
public class BackTickExpression extends Expression {

	private final Expression[] expressions;

	public BackTickExpression(int start, int end, Expression[] expressions) {
		super(start, end);

		assert expressions != null;
		this.expressions = expressions;
	}

	public BackTickExpression(int start, int end, List<Expression> expressions) {
		this(start, end, expressions == null ? null : expressions.toArray(new Expression[expressions.size()]));
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (Expression expression : expressions) {
				expression.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.BACK_TICK_EXPRESSION;
	}

	public Expression[] getExpressions() {
		return expressions;
	}
}
