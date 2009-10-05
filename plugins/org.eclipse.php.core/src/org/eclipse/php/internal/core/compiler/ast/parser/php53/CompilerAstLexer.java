/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser.php53;

import java.io.InputStream;

import java_cup.runtime.Symbol;

import org.eclipse.php.internal.core.ast.nodes.IDocumentorLexer;
import org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants;
import org.eclipse.php.internal.core.compiler.ast.nodes.Comment;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.VarComment;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.parser.DocumentorLexer;

public class CompilerAstLexer extends
		org.eclipse.php.internal.core.ast.scanner.php53.PhpAstLexer {

	private PHPDocBlock latestDocBlock;

	public CompilerAstLexer(InputStream in) {
		super(in);
	}

	public CompilerAstLexer(java.io.Reader in) {
		super(in);
	}

	protected void handleVarComment() {
		String content = yytext();
		int start = getTokenStartPosition();
		int end = start + getTokenLength();
		VarComment varComment = ASTUtils.parseVarComment(content, start, end);
		if (varComment != null) {
			getCommentList().add(varComment);
		}
	}

	protected void addComment(int type) {
		int leftPosition = getTokenStartPosition();
		Comment comment = new Comment(commentStartPosition, leftPosition
				+ getTokenLength(), type);
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
			symbol.value = latestDocBlock;
			break;
		}

		latestDocBlock = null;
		return symbol;
	}
}
