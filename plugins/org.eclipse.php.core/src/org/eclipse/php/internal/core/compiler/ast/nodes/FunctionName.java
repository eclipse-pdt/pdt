package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Holds a function name.
 * note that the function name can be expression,
 * <pre>e.g.<pre> foo() - the name is foo
 * $a() - the variable $a holds the function name
 */
public class FunctionName extends ASTNode {

	private final Expression functionName;

	public FunctionName(int start, int end, Expression functionName) {
		super(start, end);

		assert functionName != null;
		this.functionName = functionName;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			functionName.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public Expression getFunctionName() {
		return functionName;
	}
}
