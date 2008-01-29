/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.io.IOException;
import java.io.Reader;

import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.ast.parser.*;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.lr_parser;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;

public class AST {
	
	private final AstLexer lexer;
	private final lr_parser parser;
	public final String apiLevel;
	
	public AST(Reader reader, String apiLevel, boolean aspTagsAsPhp) throws IOException {
		this.apiLevel = apiLevel;
		this.lexer = getLexerInstance(reader, apiLevel, aspTagsAsPhp);
		this.parser = getParserInstance(apiLevel);
	}

	/**
	 * Constructs a scanner from a given reader
	 * @param reader
	 * @param phpVersion
	 * @param aspTagsAsPhp
	 * @return
	 * @throws IOException
	 */
	private AstLexer getLexerInstance(Reader reader, String phpVersion, boolean aspTagsAsPhp) throws IOException {
		if (PHPVersion.PHP4.equals(phpVersion)) {
			final PhpAstLexer4 lexer4 = getLexer4(reader);
			lexer4.setUseAspTagsAsPhp(aspTagsAsPhp);
			return lexer4;
		} else if (PHPVersion.PHP5.equals(phpVersion)) {
			final PhpAstLexer5 lexer5 = getLexer5(reader);
			lexer5.setUseAspTagsAsPhp(aspTagsAsPhp);
			return lexer5;
		} else {
			throw new IllegalArgumentException(CoreMessages.getString("ASTParser_1") + phpVersion);
		}
	}
	
	private PhpAstLexer5 getLexer5(Reader reader) throws IOException {
		final PhpAstLexer5 phpAstLexer5 = new PhpAstLexer5(reader);
		phpAstLexer5.setAST(this);
		return phpAstLexer5;
	}

	private PhpAstLexer4 getLexer4(Reader reader) throws IOException {
		final PhpAstLexer4 phpAstLexer4 = new PhpAstLexer4(reader);
		phpAstLexer4.setAST(this);
		return phpAstLexer4;
	}

	private lr_parser getParserInstance(String phpVersion) {
		if (PHPVersion.PHP4.equals(phpVersion)) {
			final PhpAstParser4 parser = new PhpAstParser4();
			parser.setAST(this);
			return parser;
		} else if (PHPVersion.PHP5.equals(phpVersion)) {
			final PhpAstParser5 parser = new PhpAstParser5();
			parser.setAST(this);
			return parser;
		} else {
			throw new IllegalArgumentException(CoreMessages.getString("ASTParser_1") + phpVersion);
		}
	}
	
	public void preReplaceChildEvent(ASTNode node, ASTNode oldChild, ASTNode newChild, StructuralPropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void preRemoveChildEvent(ASTNode node, ASTNode oldChild, ChildPropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void postRemoveChildEvent(ASTNode node, ASTNode oldChild, StructuralPropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void postAddChildEvent(ASTNode node, ASTNode newChild, StructuralPropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void postReplaceChildEvent(ASTNode node, ASTNode oldChild, ASTNode newChild, StructuralPropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void preAddChildEvent(ASTNode node, ASTNode newChild, StructuralPropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void preValueChangeEvent(ASTNode node, SimplePropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void modifying() {
		// TODO Auto-generated method stub
		
	}

	public void postValueChangeEvent(ASTNode node, SimplePropertyDescriptor property) {
		// TODO Auto-generated method stub
		
	}

	public void disableEvents() {
		// TODO Auto-generated method stub
		
	}

	public void reenableEvents() {
		// TODO Auto-generated method stub
		
	}

	public Object apiLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	public void preCloneNodeEvent(ASTNode node) {
		// TODO Auto-generated method stub
		
	}

	public void postCloneNodeEvent(ASTNode node, ASTNode c) {
		// TODO Auto-generated method stub
		
	}

	public void preRemoveChildEvent(ASTNode node, ASTNode oldChild, ChildListPropertyDescriptor propertyDescriptor) {
		// TODO Auto-generated method stub
		
	}

}
