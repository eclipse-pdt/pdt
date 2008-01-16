package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;

/**
 * Represents a catch clause (as part of a try statement)
 * <pre>e.g.<pre> catch (ClassName $e) { },
 *
 */
public class CatchClause extends Statement {

	private final TypeReference className;
	private final VariableReference variable;
	private final Block statement;

	public CatchClause(int start, int end, TypeReference className, VariableReference variable, Block statement) {
		super(start, end);

		assert className != null && variable != null && statement != null;
		this.className = className;
		this.variable = variable;
		this.statement = statement;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			className.traverse(visitor);
			variable.traverse(visitor);
			statement.traverse(visitor);
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.CATCH_CLAUSE;
	}

	public TypeReference getClassName() {
		return className;
	}

	public Block getStatement() {
		return statement;
	}

	public VariableReference getVariable() {
		return variable;
	}
}
