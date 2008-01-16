package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;

/**
 * Represents a for each statement
 * <pre>e.g.<pre>
 * foreach (array_expression as $value)
 *   statement;
 *
 * foreach (array_expression as $key => $value)
 *   statement;
 *
 * foreach (array_expression as $key => $value):
 *   statement;
 *   ...
 * endforeach;
 */
public class ForEachStatement extends Statement {

	private final Expression expression;
	private final Expression key;
	private final Expression value;
	private final Statement statement;

	public ForEachStatement(int start, int end, Expression expression, Expression key, Expression value, Statement statement) {
		super(start, end);

		assert expression != null && value != null && statement != null;
		this.expression = expression;
		this.key = key;
		this.value = value;
		this.statement = statement;
	}

	public ForEachStatement(int start, int end, Expression expression, Expression value, Statement statement) {
		this(start, end, expression, null, value, statement);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expression.traverse(visitor);
			if (key != null) {
				key.traverse(visitor);
			}
			value.traverse(visitor);
			statement.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.FOR_EACH_STATEMENT;
	}

	public Expression getExpression() {
		return expression;
	}

	public Expression getKey() {
		return key;
	}

	public Statement getStatement() {
		return statement;
	}

	public Expression getValue() {
		return value;
	}
}
