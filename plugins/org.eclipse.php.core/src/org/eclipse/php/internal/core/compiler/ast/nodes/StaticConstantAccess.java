package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;

/**
 * Represnts a constant class access
 * <pre>e.g.<pre> MyClass::CONST
 */
public class StaticConstantAccess extends StaticDispatch {

	private final ConstantReference constant;

	public StaticConstantAccess(int start, int end, Expression dispatcher, ConstantReference constant) {
		super(start, end, dispatcher);

		assert constant != null;
		this.constant = constant;
	}

	public int getKind() {
		return ASTNodeKinds.STATIC_CONSTANT_ACCESS;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			getDispatcher().traverse(visitor);
			constant.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public ConstantReference getConstant() {
		return constant;
	}
}
