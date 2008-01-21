package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;

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
}
