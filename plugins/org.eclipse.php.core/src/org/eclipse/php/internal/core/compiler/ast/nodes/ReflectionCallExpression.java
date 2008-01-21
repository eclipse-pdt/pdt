package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;

public class ReflectionCallExpression extends Expression {

	private Expression receiver;
	private Expression name;
	private CallArgumentsList args;

	public ReflectionCallExpression(int start, int end, Expression receiver, Expression name, CallArgumentsList args) {
		super(start, end);

		assert name != null;

		if (args == null) {
			args = new CallArgumentsList();
		}

		this.receiver = receiver;
		this.name = name;
		this.args = args;
	}

	public int getKind() {
		return ASTNodeKinds.REFLECTION_CALL_EXPRESSION;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			if (receiver != null) {
				receiver.traverse(visitor);
			}
			name.traverse(visitor);
			args.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public Expression getName() {
		return name;
	}

	public CallArgumentsList getArguments() {
		return args;
	}

	public Expression getReceiver() {
		return receiver;
	}

	public void setReceiver(Expression receiver) {
		assert receiver != null;
		this.receiver = receiver;
		setStart(receiver.sourceStart());
	}
}
