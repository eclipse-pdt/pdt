package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.*;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * The AST root node for PHP program (meaning a PHP file).
 * The program holds array of statements such as Class, Function and evaluation statement.
 * The program also holds the PHP file comments.
 *
 * @author Moshe S. & Roy G. 2007
 */
public class Program extends ModuleDeclaration {

	private CommentsStatement commentsStatement;

	public Program(int start, int end, List<Statement> statements, final List<Comment> comments) {
		super(end - start);

		setEnd(end);
		setStart(start);

		assert statements != null && comments != null;

		commentsStatement = new CommentsStatement(comments);

		statements.add(commentsStatement);
		setStatements(statements);
	}

	/**
	 * @return the program comments
	 */
	public Collection<Comment> getComments() {
		return commentsStatement.getComments();
	}

	public int getKind() {
		return ASTNodeKinds.PROGRAM;
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
