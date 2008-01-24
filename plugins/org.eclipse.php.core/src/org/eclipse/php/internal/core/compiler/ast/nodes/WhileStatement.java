package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents while statement.
 * <pre>e.g.<pre>
 * while (expr)
 *   statement;
 *
 * while (expr):
 *   statement
 *   ...
 * endwhile;
 */
public class WhileStatement extends Statement {

	private final Expression condition;
	private final Statement action;

	public WhileStatement(int start, int end, Expression condition, Statement action) {
		super(start, end);

		assert condition != null && action != null;
		this.condition = condition;
		this.action = action;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			condition.traverse(visitor);
			action.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.WHILE_STATEMENT;
	}

	public Statement getAction() {
		return action;
	}

	public Expression getCondition() {
		return condition;
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
