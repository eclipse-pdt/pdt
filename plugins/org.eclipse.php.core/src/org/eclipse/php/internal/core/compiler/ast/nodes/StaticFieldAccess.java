package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Represents a static field access.
 * <pre>e.g.<pre> MyClass::$a
 * MyClass::$$a[3]
 */
public class StaticFieldAccess extends StaticDispatch {

	private final Expression field;

	public StaticFieldAccess(int start, int end, Expression dispatcher, Expression field) {
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

	public Expression getField() {
		return field;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("StaticFieldAccess" + getSourceRange() + ":");
		output.indent();

		getDispatcher().printNode(output);
		output.formatPrint("");
		output.formatPrintLn("::");

		field.printNode(output);
		output.formatPrint("");

		output.dedent();
	}
}
