package org.eclipse.php.internal.core.typeinference.goals.phpdoc;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.VarComment;

public class VarCommentVariableGoal extends AbstractGoal {

	private VarComment varComment;
	private Expression varNode;

	public VarCommentVariableGoal(IContext context, VarComment varComment, Expression varNode) {
		super(context);
		this.varComment = varComment;
		this.varNode = varNode;
	}

	public VarComment getVarComment() {
		return varComment;
	}

	public Expression getVarNode() {
		return varNode;
	}
}
