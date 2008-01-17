package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;

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
}
