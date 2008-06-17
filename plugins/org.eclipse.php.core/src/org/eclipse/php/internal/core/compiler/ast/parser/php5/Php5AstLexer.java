package org.eclipse.php.internal.core.compiler.ast.parser.php5;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java_cup.runtime.Symbol;

import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.php.internal.core.ast.nodes.IDocumentorLexer;
import org.eclipse.php.internal.core.ast.scanner.ParserConstants5;
import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.VarComment;
import org.eclipse.php.internal.core.compiler.ast.parser.DocumentorLexer;

public class Php5AstLexer extends org.eclipse.php.internal.core.ast.scanner.PhpAstLexer5 {

	private static final Pattern VAR_COMMENT_PATTERN = Pattern.compile("(.*)(\\$[^\\s]+)(\\s+)([^\\s]+).*");
	private PHPDocBlock latestDocBlock;
	private VarComment latestVarComment;

	public Php5AstLexer(InputStream in) {
		super(in);
	}

	public Php5AstLexer(java.io.Reader in) {
		super(in);
	}

	protected void handleVarComment() {
		String content = yytext();

		Matcher m = VAR_COMMENT_PATTERN.matcher(content);
		if (m.matches()) {
			int start = getTokenStartPosition();
			int end = start + getTokenLength();

			int varStart = start + m.group(1).length();
			String varName = m.group(2);
			int varEnd = varStart + varName.length();
			int typeStart = varEnd + m.group(3).length();
			String typeName = m.group(4);
			int typeEnd = typeStart + typeName.length();

			VariableReference varReference = new VariableReference(varStart, varEnd, varName);
			TypeReference typeReference = new TypeReference(typeStart, typeEnd, typeName);
			VarComment varComment = new VarComment(start, end, varReference, typeReference);
			getCommentList().add(varComment);

			latestVarComment = varComment;
		}
	}

	protected void addComment(int type) {
		int leftPosition = getTokenStartPosition();
		Comment comment = new Comment(commentStartPosition, leftPosition + getTokenLength(), type);
		getCommentList().add(comment);
	}

	protected void addVarComment() {
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

		switch (symbolNumber) {
			case ParserConstants5.T_FUNCTION:
			case ParserConstants5.T_CONST:
			case ParserConstants5.T_VAR:
			case ParserConstants5.T_CLASS:
			case ParserConstants5.T_INTERFACE:
			case ParserConstants5.T_STATIC:
			case ParserConstants5.T_ABSTRACT:
			case ParserConstants5.T_FINAL:
			case ParserConstants5.T_PRIVATE:
			case ParserConstants5.T_PROTECTED:
			case ParserConstants5.T_PUBLIC:
				symbol.value = latestDocBlock;
				break;
			case ParserConstants5.T_EQUAL:
				symbol.value = latestVarComment;
				latestVarComment = null;
				break;
		}

		latestDocBlock = null;
		return symbol;
	}
}
