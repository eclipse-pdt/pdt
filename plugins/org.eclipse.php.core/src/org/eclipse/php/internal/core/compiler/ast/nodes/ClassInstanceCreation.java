package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents a class instanciation.
 * This class holds the class name as an expression and
 * array of constructor parameters
 * <pre>e.g.<pre> new MyClass(),
 * new $a('start'),
 * new foo()(1, $a)
 */
public class ClassInstanceCreation extends Expression {

	private final ClassName className;
	private final Expression[] ctorParams;

	public ClassInstanceCreation(int start, int end, ClassName className, Expression[] ctorParams) {
		super(start, end);

		assert className != null && ctorParams != null;

		this.className = className;
		this.ctorParams = ctorParams;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			className.traverse(visitor);
			for (Expression ctorParam : ctorParams) {
				ctorParam.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public ClassInstanceCreation(int start, int end, ClassName className, List<Expression> ctorParams) {
		this(start, end, className, ctorParams == null ? null : ctorParams.toArray(new Expression[ctorParams.size()]));
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_INSTANCE_CREATION;
	}

	public ClassName getClassName() {
		return className;
	}

	public Expression[] getCtorParams() {
		return ctorParams;
	}
}
