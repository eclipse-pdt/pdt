package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a prefix expression
 * <pre>e.g.<pre> --$a,
 * --foo()
 */
public class PrefixExpression extends Expression {

	// '++'
	public static final int OP_INC = 0;
	// '--'
	public static final int OP_DEC = 1;

	private final Expression variable;
	private final int operator;

	public PrefixExpression(int start, int end, Expression variable, int operator) {
		super(start, end);

		assert variable != null;

		this.variable = variable;
		this.operator = operator;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			variable.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public String getOperator() {
		switch (getOperatorType()) {
			case OP_DEC:
				return "--"; //$NON-NLS-1$
			case OP_INC:
				return "++"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public int getKind() {
		return ASTNodeKinds.PREFIX_EXPRESSION;
	}

	public int getOperatorType() {
		return operator;
	}

	public Expression getVariable() {
		return variable;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
