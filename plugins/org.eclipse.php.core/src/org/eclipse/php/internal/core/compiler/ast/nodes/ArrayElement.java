package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Represents a single element of array.
 * Holds the key and the value both can be any expression
 * The key can be null
 * <pre>e.g.<pre> 1,
 * 'Dodo'=>'Golo',
 * $a,
 * $b=>foo(),
 * 1=>$myClass->getFirst() *
 */
public class ArrayElement extends ASTNode {

	private final Expression key;
	private final Expression value;

	public ArrayElement(int start, int end, Expression key, Expression value) {
		super(start, end);

		assert value != null;
		this.key = key;
		this.value = value;
	}

	public ArrayElement(int start, int end, Expression value) {
		this(start, end, null, value);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (key != null) {
				key.traverse(visitor);
			}
			value.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.ARRAY_ELEMENT;
	}

	public Expression getKey() {
		return key;
	}

	public Expression getValue() {
		return value;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("ArrayElement" + this.getSourceRange().toString() + ":");
		output.indent();
		if (key != null) {
			key.printNode(output);
			output.formatPrint(" =>");
		}
		value.printNode(output);
		output.formatPrint("");
		output.dedent();
	}
}
