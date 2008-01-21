package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * This class holds the expression that should be evaluated.
 * <pre>e.g.<pre> $a = 5;
 * $a;
 * 3+2;
 */
public class ExpressionStatement extends Statement {

	private final Expression expr;

	public ExpressionStatement(int start, int end, Expression expr) {
		super(start, end);

		assert expr != null;
		this.expr = expr;
	}

	public ExpressionStatement(int start, int end) {
		this(start, end, null);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			expr.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.EXPRESSION_STATEMENT;
	}

	public Expression getExpr() {
		return expr;
	}

	public void printNode(CorePrinter output){
		output.formatPrintLn("ExpressionStatement" + this.getSourceRange().toString() + ":");
		output.indent();
		expr.printNode(output);
		output.formatPrint("");
		output.dedent();
	}
}
