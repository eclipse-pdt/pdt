package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.DLTKToken;
import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Represents an indirect reference to a variable.
 * <pre>e.g.<pre> $$a
 * $$foo()
 * ${foo()}
 */
public class ReflectionVariableReference extends Expression {

	private Expression expression;

	public ReflectionVariableReference(int start, int end, Expression name) {
		super(start, end);
		this.expression = name;
	}

	public ReflectionVariableReference(DLTKToken token) {
		super(token);
	}

	public int getKind() {
		return ASTNodeKinds.REFLECTION_VARIABLE;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			expression.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public Expression getExpression(){
		return this.expression;
	}
}
