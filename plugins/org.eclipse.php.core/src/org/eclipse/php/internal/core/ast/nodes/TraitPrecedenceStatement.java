package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.php.internal.core.ast.match.ASTMatcher;

public class TraitPrecedenceStatement extends TraitStatement {
	TraitPrecedence precedence;

	public TraitPrecedenceStatement(int start, int end, AST ast,
			TraitPrecedence precedence) {
		super(start, end, ast, precedence);
		this.precedence = precedence;
	}

	public TraitPrecedenceStatement(AST ast) {
		super(ast);
	}

	public TraitPrecedence getPrecedence() {
		return precedence;
	}

	public void setPrecedence(TraitPrecedence precedence) {
		setExp(precedence);
		this.precedence = precedence;
	}

	@Override
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		if (!(other instanceof TraitPrecedenceStatement)) {
			return false;
		}
		return super.subtreeMatch(matcher, other);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitPrecedenceStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		precedence.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</TraitPrecedenceStatement>"); //$NON-NLS-1$
	}

	@Override
	ASTNode clone0(AST target) {
		TraitPrecedence precedence = ASTNode.copySubtree(target,
				getPrecedence());
		final TraitPrecedenceStatement result = new TraitPrecedenceStatement(
				this.getStart(), this.getEnd(), target, precedence);
		return result;
	}

}
