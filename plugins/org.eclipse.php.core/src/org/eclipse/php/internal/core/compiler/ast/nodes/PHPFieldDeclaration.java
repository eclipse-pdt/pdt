package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPFieldDeclaration extends FieldDeclaration implements IPHPDocAwareDeclaration {

	private Expression initializer;
	private PHPDocBlock phpDoc;

	public PHPFieldDeclaration(VariableReference variable, Expression initializer, int start, int end, int modifiers, PHPDocBlock phpDoc) {
		super(variable.getName(), variable.sourceStart(), variable.sourceEnd(), start, end);

		setModifiers(modifiers);

		this.initializer = initializer;
		this.phpDoc = phpDoc;
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			getRef().traverse(visitor);
			if (initializer != null) {
				initializer.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.FIELD_DECLARATION;
	}

	public Expression getVariableValue() {
		return initializer;
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
