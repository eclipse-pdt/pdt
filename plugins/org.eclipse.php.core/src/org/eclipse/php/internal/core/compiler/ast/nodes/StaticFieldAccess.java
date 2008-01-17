package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;

/**
 * Represents a static field access.
 * <pre>e.g.<pre> MyClass::$a
 * MyClass::$$a[3]
 */
public class StaticFieldAccess extends StaticDispatch {

	private final VariableReference field;

	public StaticFieldAccess(int start, int end, Expression dispatcher, VariableReference field) {
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
		return ASTNodeKinds.STATIC_FIELD_ACCESS;
	}

	public VariableReference getField() {
		return field;
	}
}
