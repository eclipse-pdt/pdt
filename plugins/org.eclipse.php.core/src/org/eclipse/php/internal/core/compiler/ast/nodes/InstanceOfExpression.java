package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represent instanceof expression
 * <pre>e.g.<pre> $a instanceof MyClass,
 * foo() instanceof $myClass,
 * $a instanceof $b->$myClass
 */
public class InstanceOfExpression extends Expression {

	private final Expression expr;
	private final ClassName className;

	public InstanceOfExpression(int start, int end, Expression expr, ClassName type) {
		super(start, end);

		assert expr != null && type != null;
		this.expr = expr;
		this.className = type;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
			className.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.INSTANCE_OF_EXPRESSION;
	}

	public ClassName getClassName() {
		return className;
	}

	public Expression getExpr() {
		return expr;
	}
}
