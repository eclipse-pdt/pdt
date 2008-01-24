package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a declare statement
 * <pre>e.g.<pre> declare(ticks=1) { }
 * declare(ticks=2) { for ($x = 1; $x < 50; ++$x) {  }  }
 */
public class DeclareStatement extends Statement {

	private final String[] directiveNames;
	private final Expression[] directiveValues;
	private final Statement action;

	private DeclareStatement(int start, int end, String[] directiveNames, Expression[] directiveValues, Statement action) {
		super(start, end);

		assert directiveNames != null && directiveValues != null && directiveNames.length == directiveValues.length;
		this.directiveNames = directiveNames;
		this.directiveValues = directiveValues;
		this.action = action;
	}

	public DeclareStatement(int start, int end, List<String> directiveNames, List<? extends Expression> directiveValues, Statement action) {
		this(start, end,
			directiveNames == null ? null : (String[]) directiveNames.toArray(new String[directiveNames.size()]),
			directiveValues == null ? null : (Expression[]) directiveValues.toArray(new Expression[directiveValues.size()]),
		action);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (int i = 0; i < directiveNames.length; i++) {
				directiveValues[i].traverse(visitor);
			}
			action.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.DECLARE_STATEMENT;
	}

	public Statement getAction() {
		return action;
	}

	public String[] getDirectiveNames() {
		return directiveNames;
	}

	public Expression[] getDirectiveValues() {
		return directiveValues;
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
