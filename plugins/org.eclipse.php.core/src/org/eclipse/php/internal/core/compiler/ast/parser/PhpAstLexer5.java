package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.InputStream;

import org.eclipse.php.internal.core.ast.nodes.IDocumentorLexer;
import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;

public class PhpAstLexer5 extends org.eclipse.php.internal.core.ast.scanner.PhpAstLexer5 {

	private PHPDocBlock latestDocBlock;

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

	protected boolean parsePHPDoc() {
		boolean result = super.parsePHPDoc();
		if (result) {
			latestDocBlock = (PHPDocBlock) getCommentList().getLast();
		}
		return result;
	}

	protected Symbol createSymbol(int symbolNumber) {
		Symbol symbol = super.createSymbol(symbolNumber);
		symbol.value = latestDocBlock;
		latestDocBlock = null;
		return symbol;
	}
}
