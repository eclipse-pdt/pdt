package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;

/**
 * Represents a global statement
 * <pre>e.g.<pre> global $a
 * global $a, $b
 * global ${foo()->bar()},
 * global $$a
 */
public class GlobalStatement extends Statement {

	private final VariableReference[] variables;

	private GlobalStatement(int start, int end, VariableReference[] variables) {
		super(start, end);

		assert variables != null;
		this.variables = variables;
	}

	public GlobalStatement(int start, int end, List<VariableReference> variables) {
		this(start, end, variables == null ? null : (VariableReference[]) variables.toArray(new VariableReference[variables.size()]));
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			for (VariableReference variable : variables) {
				variable.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.GLOBAL_STATEMENT;
	}

	public VariableReference[] getVariables() {
		return variables;
	}
}
