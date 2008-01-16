package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;

public class DoStatement extends WhileStatement {

	public DoStatement(int start, int end, Expression condition, Statement action) {
		super(start, end, condition, action);
	}

	public int getKind() {
		return ASTNodeKinds.DO_STATEMENT;
	}
}
