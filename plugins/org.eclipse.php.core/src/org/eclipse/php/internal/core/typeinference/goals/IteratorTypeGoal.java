package org.eclipse.php.internal.core.typeinference.goals;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;

public class IteratorTypeGoal extends AbstractGoal {

	private ASTNode expression;

	public IteratorTypeGoal(IContext context, ASTNode expression) {
		super(context);
		this.expression = expression;
	}

	public ASTNode getExpression() {
		return expression;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IteratorTypeGoal other = (IteratorTypeGoal) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}

}