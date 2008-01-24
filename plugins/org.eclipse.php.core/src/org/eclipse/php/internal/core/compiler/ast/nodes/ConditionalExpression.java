package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents conditional expression
 * Holds the condition, if true expression and if false expression
 * each on e can be any expression
 * <pre>e.g.<pre> (bool) $a ? 3 : 4
 * $a > 0 ? $a : -$a
 */
public class ConditionalExpression extends Expression {

	private final Expression condition;
	private final Expression ifTrue;
	private final Expression ifFalse;

	public ConditionalExpression(int start, int end, Expression condition, Expression ifTrue, Expression ifFalse) {
		super(start, end);

		assert condition != null && ifTrue != null && ifFalse != null;
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			condition.traverse(visitor);
			ifTrue.traverse(visitor);
			ifFalse.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CONDITIONAL_EXPRESSION;
	}

	public Expression getCondition() {
		return condition;
	}

	public Expression getIfFalse() {
		return ifFalse;
	}

	public Expression getIfTrue() {
		return ifTrue;
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
