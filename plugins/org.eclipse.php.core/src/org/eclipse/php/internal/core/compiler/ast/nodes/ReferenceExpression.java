package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 *  Represents an reference to a variable to be used inside function call.
 *  <pre>e.g.<pre> &$a, &$a[0], &$$a, &$a{'key'}
 */
public class ReferenceExpression extends Expression {

	private final Expression variable;

	public ReferenceExpression(int start, int end, Expression variable) {
		super(start, end);

		assert variable != null;
		this.variable = variable;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			variable.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.REFERENCE;
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
