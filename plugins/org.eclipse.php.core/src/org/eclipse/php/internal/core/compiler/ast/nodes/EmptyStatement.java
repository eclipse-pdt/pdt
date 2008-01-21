package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * This class represents an empty statement.
 * <pre>e.g.<pre> ;
 * while(true); - the while statement contains empty statement
 */
public class EmptyStatement extends Statement {

	public EmptyStatement(int start, int end) {
		super(start, end);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.EMPTY_STATEMENT;
	}

	public void printNode(CorePrinter output){
		output.formatPrintLn("EmptyStatement" + this.getSourceRange().toString());
	}
}
