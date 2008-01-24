package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * This AST node holds all comments of PHP program - we need a separate node for this
 * since we can't override {@link MethodDeclaration}{@link #traverse(ASTVisitor)} in {@link Program}
 *
 * @author michael, guy
 */
public class CommentsStatement extends Statement {

	private final List<Comment> comments;

	public CommentsStatement(List<Comment> comments) {
		assert comments != null;
		this.comments = comments;
	}

	public int getKind() {
		return 0;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			for (Comment comment : comments) {
				comment.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public Collection<Comment> getComments() {
		return comments;
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
