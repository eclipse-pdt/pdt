package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Statement;

/**
 * Represents a case statement.
 * A case statement is part of switch statement
 * <pre>e.g.<pre>
 * case expr:
 *   statement1;
 *   break;,
 *
 * default:
 *   statement2;
 */
public class SwitchCase extends Statement {

	private final Expression value;
	private final Statement[] actions;
	private final boolean isDefault;

	public SwitchCase(int start, int end, Expression value, Statement[] actions, boolean isDefault) {
		super(start, end);

		assert actions != null;
		this.value = value;
		this.actions = actions;
		this.isDefault = isDefault;
	}

	public SwitchCase(int start, int end, Expression value, List<? extends Expression> actions, boolean isDefault) {
		this(start, end, value, actions == null ? null : (Statement[]) actions.toArray(new Statement[actions.size()]), isDefault);
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			if (value != null) {
				value.traverse(visitor);
			}
			for (Statement action : actions) {
				action.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.SWITCH_CASE;
	}

	public Statement[] getActions() {
		return actions;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public Expression getValue() {
		return value;
	}
}
