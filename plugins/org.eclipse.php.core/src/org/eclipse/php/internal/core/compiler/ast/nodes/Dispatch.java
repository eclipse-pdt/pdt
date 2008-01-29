package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a base class for method invocation and field access
 * <pre>e.g.<pre> $a->$b,
 * foo()->bar(),
 * $myClass->foo()->bar(),
 * A::$a->foo()
 */
public abstract class Dispatch extends Expression {

	private final Expression dispatcher;

	public Dispatch(int start, int end, Expression dispatcher) {
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
