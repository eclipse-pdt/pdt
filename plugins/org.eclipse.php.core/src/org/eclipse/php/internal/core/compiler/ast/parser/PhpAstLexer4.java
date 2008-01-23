package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.InputStream;

import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;

public class PhpAstLexer4 extends org.eclipse.php.internal.core.ast.parser.PhpAstLexer4 {

	public PhpAstLexer4(InputStream in) {
		super(in);
	}

	public PhpAstLexer4(java.io.Reader in) {
		super(in);
	}

	protected void addComment(int type) {
		int leftPosition = getTokenStartPosition();
		Comment comment = new Comment(commentStartPosition, leftPosition + getTokenLength(), type);
		getCommentList().add(comment);
	}
}
