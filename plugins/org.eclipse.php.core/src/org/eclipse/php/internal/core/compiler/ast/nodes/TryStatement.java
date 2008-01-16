package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;

/**
 * Represents the try statement
 * <pre>e.g.<pre>
 * try {
 *   statements...
 * } catch (Exception $e) {
 *   statements...
 * } catch (AnotherException $ae) {
 *   statements...
 * }
 */
public class TryStatement extends Statement {

	private final Block tryStatement;
	private final CatchClause[] catchClauses;

	private TryStatement(int start, int end, Block tryStatement, CatchClause[] catchClauses) {
		super(start, end);

		assert tryStatement != null && catchClauses != null;
		this.tryStatement = tryStatement;
		this.catchClauses = catchClauses;
	}

	public TryStatement(int start, int end, Block tryStatement, List<CatchClause> catchClauses) {
		this(start, end, tryStatement, catchClauses == null ? null : (CatchClause[]) catchClauses.toArray(new CatchClause[catchClauses.size()]));
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		final boolean visit = visitor.visit(this);
		if (visit) {
			tryStatement.traverse(visitor);
			for (CatchClause catchClause : catchClauses) {
				catchClause.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.TRY_STATEMENT;
	}

	public CatchClause[] getCatchClauses() {
		return catchClauses;
	}

	public Block getTryStatement() {
		return tryStatement;
	}
}
