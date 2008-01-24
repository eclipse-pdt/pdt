package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents a field access
 * <pre>e.g.<pre> $a->$b
 */
public class FieldAccess extends Dispatch {

	private final Expression field;

	public FieldAccess(int start, int end, Expression dispatcher, Expression field) {
		super(start, end, dispatcher);

		assert field != null;
		this.field = field;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			getDispatcher().traverse(visitor);
			field.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.FIELD_ACCESS;
	}

	public Expression getField() {
		return field;
	}
}
