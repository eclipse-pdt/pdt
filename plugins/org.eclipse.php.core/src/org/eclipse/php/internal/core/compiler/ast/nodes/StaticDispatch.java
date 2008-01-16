package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;

/**
 * base class for all the static access
 */
public abstract class StaticDispatch extends Expression {

	private final SimpleReference dispatcher;

	public StaticDispatch(int start, int end, SimpleReference dispatcher) {
		super(start, end);

		assert dispatcher != null;
		this.dispatcher = dispatcher;
	}

	public SimpleReference getDispatcher() {
		return dispatcher;
	}
}
