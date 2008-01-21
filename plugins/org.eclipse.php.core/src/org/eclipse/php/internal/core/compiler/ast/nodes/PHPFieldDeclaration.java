package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;

public class PHPFieldDeclaration extends FieldDeclaration {

	private Expression initializer;

	public PHPFieldDeclaration(VariableReference variable, Expression initializer, int start, int end, int modifiers) {
		super(variable.getName(), variable.sourceStart(), variable.sourceEnd(), start, end);

		setModifiers(modifiers);

		this.initializer = initializer;
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
}
