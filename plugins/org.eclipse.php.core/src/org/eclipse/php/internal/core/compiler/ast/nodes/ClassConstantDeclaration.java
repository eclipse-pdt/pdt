package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class constant declaration
 * <pre>e.g.<pre> const MY_CONST = 5;
 * const MY_CONST = 5, YOUR_CONSTANT = 8;
 */
public class ClassConstantDeclaration extends Declaration implements IPHPDocAwareDeclaration {

	private final ConstantReference constant;
	private final Expression initializer;
	private PHPDocBlock phpDoc;

	public ClassConstantDeclaration(ConstantReference constant, Expression initializer, int start, int end, PHPDocBlock phpDoc) {
		super(start, end);

		assert constant != null;
		assert initializer != null;

		this.constant = constant;
		this.initializer = initializer;
		this.phpDoc = phpDoc;
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			constant.traverse(visitor);
			initializer.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_CONSTANT_DECLARATION;
	}

	public Expression getConstantValue() {
		return initializer;
	}

	public ConstantReference getConstantName() {
		return constant;
	}
	
	public String getName() {
		return constant.getName();
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
