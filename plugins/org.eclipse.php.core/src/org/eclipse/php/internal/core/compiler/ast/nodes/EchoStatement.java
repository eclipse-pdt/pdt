package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;

/**
 * Represent a echo statement.
 * <pre>e.g.<pre> echo "hello",
 * echo "hello", "world"
 */
public class EchoStatement extends Statement {

	private final Expression[] expressions;

	private EchoStatement(int start, int end, Expression[] expressions) {
		super(start, end);

		assert expressions != null;
		this.expressions = expressions;
	}

	public EchoStatement(int start, int end, List<? extends Expression> expressions) {
		this(start, end, expressions.toArray(new Expression[expressions.size()]));
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (Expression expression : expressions) {
				expression.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.ECHO_STATEMENT;
	}

	public Expression[] getExpressions() {
		return expressions;
	}
}
