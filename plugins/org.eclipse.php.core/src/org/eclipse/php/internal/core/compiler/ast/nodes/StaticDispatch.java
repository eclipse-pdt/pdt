package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * base class for all the static access
 */
public abstract class StaticDispatch extends Expression {

	private final Expression dispatcher;

	public StaticDispatch(int start, int end, Expression dispatcher) {
		super(start, end);

		assert dispatcher != null;

		this.dispatcher = dispatcher;
	}

	public Expression getDispatcher() {
		return dispatcher;
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
