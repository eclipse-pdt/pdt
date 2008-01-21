package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * Represents array creation
 * <pre>e.g.<pre> array(1,2,3,),
 * array('Dodo'=>'Golo','Dafna'=>'Dodidu')
 * array($a, $b=>foo(), 1=>$myClass->getFirst())
 */
public class ArrayCreation extends Expression {

	private final ArrayElement[] elements;

	private ArrayCreation(int start, int end, ArrayElement[] elements) {
		super(start, end);

		assert elements != null;
		this.elements = elements;
	}

	public ArrayCreation(int start, int end, List<ArrayElement> elements) {
		this(start, end, elements == null ? null : elements.toArray(new ArrayElement[elements.size()]));
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (ArrayElement element : elements) {
				element.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.ARRAY_CREATION;
	}

	public ArrayElement[] getElements() {
		return elements;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("ArrayCreation" + this.getSourceRange().toString() + ":");
		output.indent();
		for (ArrayElement a : elements) {
			a.printNode(output);
			output.formatPrint("");
		}
		output.dedent();
	}
}
