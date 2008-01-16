package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Holds a class name.
 * note that the class name can be expression,
 * <pre>e.g.<pre> MyClass,
 * getClassName() - the function getClassName return a class name
 * $className - the variable $a holds the class name
 */
public class ClassName extends ASTNode {

	private final Expression className;

	public ClassName(int start, int end, Expression className) {
		super(start, end);
		assert className != null;

		this.className = className;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			className.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_NAME;
	}

	public Expression getClassName() {
		return className;
	}
}
