package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.utils.CorePrinter;

public class PHPCallExpression extends CallExpression {

	public PHPCallExpression(int start, int end, ASTNode receiver, String name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public PHPCallExpression(int start, int end, ASTNode receiver, SimpleReference name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public PHPCallExpression(ASTNode receiver, String name, CallArgumentsList args) {
		super(receiver, name, args);
	}

	public int getKind() {
		return ASTNodeKinds.METHOD_INVOCATION;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("PHPCallExpression" + this.getSourceRange().toString() + ":");
		output.indent();
		if (this.receiver != null) {
			this.receiver.printNode(output);
			output.formatPrint("");
		}
		output.formatPrint("->" + this.getName() + "(");
		if (this.getArgs() != null) {
			this.getArgs().printNode(output);
			output.formatPrint("");
		}
		output.formatPrint(")");
		output.dedent();
	}
}
