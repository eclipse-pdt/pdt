package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class ChainingInstanceCall extends Expression {

	private PHPArrayDereferenceList arrayDereferenceList;
	private ChainingMethodPropertyList chaining_method_or_property;

	public ChainingInstanceCall(int start, int end,
			PHPArrayDereferenceList arrayDereferenceList,
			ChainingMethodPropertyList chaining_method_or_property) {
		super(start, end);

		this.arrayDereferenceList = arrayDereferenceList;
		this.chaining_method_or_property = chaining_method_or_property;
	}

	public ChainingInstanceCall(PHPArrayDereferenceList arrayDereferenceList,
			ChainingMethodPropertyList chaining_method_or_property) {
		this.arrayDereferenceList = arrayDereferenceList;
		this.chaining_method_or_property = chaining_method_or_property;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (arrayDereferenceList != null) {
				for (Object arrayDereference : arrayDereferenceList.getChilds()) {
					((DereferenceNode) arrayDereference).traverse(visitor);
				}
			}
			if (chaining_method_or_property != null) {
				for (Object method_or_property : chaining_method_or_property
						.getChilds()) {
					((Expression) method_or_property).traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
	}

	public PHPArrayDereferenceList getArrayDereferenceList() {
		return arrayDereferenceList;
	}

	public ChainingMethodPropertyList getChainingMethodPropertyList() {
		return chaining_method_or_property;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
