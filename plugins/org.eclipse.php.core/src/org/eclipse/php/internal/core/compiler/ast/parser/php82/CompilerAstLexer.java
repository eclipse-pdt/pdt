/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser.php82;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.php.core.ast.nodes.IDocumentorLexer;
import org.eclipse.php.core.compiler.ast.nodes.Comment;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.VarComment;
import org.eclipse.php.internal.core.ast.scanner.php82.ParserConstants;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.parser.DocumentorLexer;

import java_cup.runtime.Symbol;

public class CompilerAstLexer extends org.eclipse.php.internal.core.ast.scanner.php82.PHPAstLexer {

	private PHPDocBlock latestDocBlock;

	public CompilerAstLexer(java.io.Reader in) {
		super(in);
	}

	@Override
	protected void handleVarComment() {
		String content = yytext();
		int start = getTokenStartPosition();
		int end = start + getTokenLength();
		VarComment varComment = ASTUtils.parseVarComment(content, start, end);
		if (varComment != null) {
			getCommentList().add(varComment);
		}
	}

	@Override
	protected void addComment(int type) {
		int leftPosition = getTokenStartPosition();
		Comment comment = new Comment(commentStartPosition, leftPosition + getTokenLength(), type);
		getCommentList().add(comment);
	}

	protected void addVarComment() {
	}

	@Override
	protected IDocumentorLexer getDocumentorLexer(java.io.Reader reader) {
		IDocumentorLexer lexer = new DocumentorLexer(reader);
		return lexer;
	}

	@Override
	protected boolean parsePHPDoc() {
		boolean result = super.parsePHPDoc();
		if (result) {
			latestDocBlock = (PHPDocBlock) getCommentList().getLast();
		}
		return result;
	}

	protected static class PHPDocBlockSymbolPair {
		public String value;
		public PHPDocBlock doc;

		public PHPDocBlockSymbolPair(String value, PHPDocBlock block) {
			this.value = value;
			this.doc = block;
		}
	}

	protected static class PHPHeredocSymbolPair {
		@NonNull
		public String value;
		@NonNull
		public String innerIndentation;

		public PHPHeredocSymbolPair(@NonNull String value, @NonNull String innerIndentation) {
			this.value = value;
			this.innerIndentation = innerIndentation;
		}
	}

	@SuppressWarnings("null")
	@Override
	protected Symbol createFullSymbol(int symbolNumber) {
		Symbol symbol = super.createFullSymbol(symbolNumber);

		switch (symbolNumber) {
		case ParserConstants.T_FUNCTION:
		case ParserConstants.T_CONST:
		case ParserConstants.T_VAR:
		case ParserConstants.T_CLASS:
		case ParserConstants.T_INTERFACE:
		case ParserConstants.T_STATIC:
		case ParserConstants.T_ABSTRACT:
		case ParserConstants.T_FINAL:
		case ParserConstants.T_PRIVATE:
		case ParserConstants.T_PROTECTED:
		case ParserConstants.T_PUBLIC:
		case ParserConstants.T_TRAIT:
		case ParserConstants.T_FN:
			symbol.value = new PHPDocBlockSymbolPair((String) symbol.value, latestDocBlock);
			break;
		case ParserConstants.T_END_HEREDOC:
			symbol.value = new PHPHeredocSymbolPair((String) symbol.value, lastHeredocIndentation);
			break;
		}

		latestDocBlock = null;
		return symbol;
	}
}
