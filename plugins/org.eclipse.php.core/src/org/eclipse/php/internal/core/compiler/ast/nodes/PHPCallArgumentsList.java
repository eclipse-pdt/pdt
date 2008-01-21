package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.Iterator;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.utils.CorePrinter;

public class PHPCallArgumentsList extends CallArgumentsList {

	public PHPCallArgumentsList() {
	}

	public PHPCallArgumentsList(int start, int end) {
		super(start, end);
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("PHPCallArgumentsList:");
		output.indent();
		Iterator i = getChilds().iterator();
		while (i.hasNext()) {
			((ASTNode)i.next()).printNode(output);
			output.formatPrint("");
		}
		output.formatPrint("");
		output.dedent();
	}
}
