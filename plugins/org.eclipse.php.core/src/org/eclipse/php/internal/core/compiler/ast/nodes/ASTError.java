package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a parsing error
 * <pre>e.g.<pre> echo ;
 * for () {}
 */
public class ASTError extends Statement {

	public ASTError(int start, int end) {
		super(start, end);
	}

	public void childrenAccept(Visitor visitor) {
	}

	public void traverseBottomUp(Visitor visitor) {
	}

	public void traverseTopDown(Visitor visitor) {
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.AST_ERROR;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("Error" + this.getSourceRange().toString());
	}
}
