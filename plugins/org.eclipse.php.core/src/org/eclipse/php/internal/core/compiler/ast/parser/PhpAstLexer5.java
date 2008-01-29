package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.InputStream;

import org.eclipse.php.internal.core.ast.parser.IDocumentorLexer;
import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;

public class PhpAstLexer5 extends org.eclipse.php.internal.core.ast.parser.PhpAstLexer5 {

	public PhpAstLexer5(InputStream in) {
		super(in);
	}

	public PhpAstLexer5(java.io.Reader in) {
		super(in);
	}

	protected void addComment(int type) {
		int leftPosition = getTokenStartPosition();
		Comment comment = new Comment(commentStartPosition, leftPosition + getTokenLength(), type);
		getCommentList().add(comment);
	}

	protected IDocumentorLexer getDocumentorLexer(java.io.Reader reader) {
		IDocumentorLexer lexer = new DocumentorLexer(reader);
		return lexer;
	}

}
