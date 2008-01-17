package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents an unary operation expression
 * <pre>e.g.<pre> +$a,
 * -3,
 * -foo(),
 * +-+-$a
 */
public class UnaryOperation extends Expression {

	// '+'
	public static final int OP_PLUS = 0;
	// '-'
	public static final int OP_MINUS = 1;
	// '!'
	public static final int OP_NOT = 2;
	// '~'
	public static final int OP_TILDA = 3;

	private final Expression expr;
	private final int operator;

	public UnaryOperation(int start, int end, Expression expr, int operator) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
		this.operator = operator;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public String getOperator() {
		switch (getOperatorType()) {
			case OP_PLUS:
				return "+"; //$NON-NLS-1$
			case OP_MINUS:
				return "-"; //$NON-NLS-1$
			case OP_NOT:
				return "!"; //$NON-NLS-1$
			case OP_TILDA:
				return "~"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getKind() {
		return ASTNodeKinds.UNARY_OPERATION;
	}

	public Expression getExpr() {
		return expr;
	}

	public int getOperatorType() {
		return operator;
	}
}
