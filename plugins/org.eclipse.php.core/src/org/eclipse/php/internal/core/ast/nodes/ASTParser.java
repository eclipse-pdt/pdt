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

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.ast.scanner.PhpAstLexer4;
import org.eclipse.php.internal.core.ast.scanner.PhpAstLexer5;
import org.eclipse.php.internal.core.ast.scanner.PhpAstParser4;
import org.eclipse.php.internal.core.ast.scanner.PhpAstParser5;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;


/**
 * A PHP language parser for creating abstract syntax trees (ASTs).<p>
 * Example: Create basic AST from source string
 * <pre>
 * String source = ...;
 * Program program = ASTParser.parse(source);  
 * </pre>
 */
public class ASTParser {

	// version tags
	public static final String VERSION_PHP4 = PHPVersion.PHP4;
	public static final String VERSION_PHP5 = PHPVersion.PHP5;

	// empty buffer
	private static final StringReader EMPTY_STRING_READER = new StringReader(""); //$NON-NLS-1$

	/**
	 * THREAD SAFE AST PARSER STARTS HERE
	 */
	private final AST ast;
	
	private ASTParser(Reader reader, String version, boolean useASPTags) throws IOException {
		this(reader, version, useASPTags, null);
	}
	
	private ASTParser(Reader reader, String version, boolean useASPTags, ISourceModule sourceModule) throws IOException {
		
		if (version != VERSION_PHP4 && version != VERSION_PHP5) {
			throw new IllegalArgumentException("Invalid version in ASTParser");
		}
		this.ast = new AST(reader, version, useASPTags);
		this.ast.setDefaultNodeFlag(ASTNode.ORIGINAL);

		// set resolve binding property and the binding resolver 
		if (sourceModule != null) {
			this.ast.setFlag(AST.RESOLVED_BINDINGS);
			try {
				this.ast.setBindingResolver(new DefaultBindingResolver(sourceModule, sourceModule.getWorkingCopy(null).getOwner()));
			} catch (ModelException e) {
				throw new IOException(e);
			}
		}
	}
	
	/**
	 * Factory methods for ASTParser
	 */
	public static ASTParser newParser(String version) {
		try {
			return new ASTParser(EMPTY_STRING_READER, version, false);
		} catch (IOException e) {
			assert false;
			// Since we use empty reader we cannot have an IOException here
			return null;
		}
	}

	public static ASTParser newParser(Reader reader, String version) throws IOException {
		return new ASTParser(reader, version, false);
	}
	
	public static ASTParser newParser(Reader reader, String version, boolean useASPTags) throws IOException {
		return new ASTParser(reader, version, useASPTags);
	}

	public static ASTParser newParser(Reader reader, String version, boolean useASPTags, ISourceModule sourceModule) throws IOException {
		return new ASTParser(reader, version, useASPTags, sourceModule);
	}

	/**
	 * Set the raw source that will be used on parsing
	 * @throws IOException 
	 */
	public void setSource(char[] source) throws IOException {
		final CharArrayReader charArrayReader = new CharArrayReader(source);
		setSource(charArrayReader);
	}
	
	/**
	 * Set source of the parser
	 * @throws IOException 
	 */
	public void setSource(Reader source) throws IOException {
		this.ast.setSource(source);
	}

	/**
	 * This operation creates an abstract syntax tree for the given AST Factory
	 * 
	 * @param progressMonitor
	 * @return Program that represents the equivalent AST 
	 * @throws Exception - for exception occurs on the parsing step
	 */
	public Program createAST(IProgressMonitor progressMonitor) throws Exception {
		if (progressMonitor == null) {
			progressMonitor = new NullProgressMonitor();
		}
		
		progressMonitor.beginTask("Creating Abstract Syntax Tree for source...", 3);
		final Scanner lexer = this.ast.lexer();
		final lr_parser phpParser = this.ast.parser();
		progressMonitor.worked(1);
		phpParser.setScanner(lexer);
		progressMonitor.worked(2);
		final Symbol symbol = phpParser.parse();
		progressMonitor.done();
		if (symbol == null) {
			return null;
		}
		Program p = (Program) symbol.value;
		AST ast = p.getAST();

		// now reset the ast default node flag back to differntate between original nodes 
		ast.setDefaultNodeFlag(0);
		// Set the original modification count to the count after the creation of the Program.
		// This is important to allow the AST rewriting. 
		ast.setOriginalModificationCount(ast.modificationCount());
		return p;
	}
	
	/********************************************************************************
	 * NOT THREAD SAFE IMPLEMENTATION STARTS HERE 
	 *********************************************************************************/
	// php 5 analysis
	private static final PhpAstLexer5 PHP_AST_LEXER5 = new PhpAstLexer5(ASTParser.EMPTY_STRING_READER);
	private static final PhpAstParser5 PHP_AST_PARSER5 = new PhpAstParser5(PHP_AST_LEXER5);

	// php 4 analysis	
	private static final PhpAstLexer4 PHP_AST_LEXER4 = new PhpAstLexer4(ASTParser.EMPTY_STRING_READER);
	private static final PhpAstParser4 PHP_AST_PARSER4 = new PhpAstParser4(PHP_AST_LEXER4);

	
	/**
	 * @param phpCode String - represents the source code of the PHP program
	 * @param aspTagsAsPhp boolean - true if % is used as PHP process intructor   
	 * @return the {@link Program} node generated from the given source
	 * @throws Exception
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(String phpCode, boolean aspTagsAsPhp) throws Exception {
		StringReader reader = new StringReader(phpCode);
		return parse(reader, aspTagsAsPhp, VERSION_PHP5);
	}

	/**
	 * @param phpFile File - represents the source file of the PHP program
	 * @param aspTagsAsPhp boolean - true if % is used as PHP process intructor   
	 * @return the {@link Program} node generated from the given source PHP file
	 * @throws Exception
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(File phpFile, boolean aspTagsAsPhp) throws Exception {
		final Reader reader = new FileReader(phpFile);
		return parse(reader, aspTagsAsPhp, VERSION_PHP5);
	}

	/**
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(final IDocument phpDocument, boolean aspTagsAsPhp, String phpVersion) throws Exception {
		return parse(phpDocument, aspTagsAsPhp, phpVersion, 0, phpDocument.getLength());
	}

	/**
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(final IDocument phpDocument, boolean aspTagsAsPhp, String phpVersion, final int offset, final int length) throws Exception {
		final Reader reader = new InputStreamReader(new InputStream() {
			private int index = offset;
			private final int size = offset + length;

			public int read() throws IOException {
				try {
					if (index < size) {
						return phpDocument.getChar(index++);
					}
					return -1;
				} catch (BadLocationException e) {
					throw new IOException(e.getMessage());
				}
			}
		});
		return parse(reader, aspTagsAsPhp, phpVersion);
	}

	/**
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(IDocument phpDocument, boolean aspTagsAsPhp) throws Exception {
		return parse(phpDocument, aspTagsAsPhp, VERSION_PHP5);
	}

	/**
	 * @see #parse(String, boolean)
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(String phpCode) throws Exception {
		return parse(phpCode, true);
	}

	/**
	 * @see #parse(File, boolean)
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(File phpFile) throws Exception {
		return parse(phpFile, true);
	}

	/**
	 * @see #parse(Reader, boolean)
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public static final Program parse(Reader reader) throws Exception {
		return parse(reader, true, VERSION_PHP5);
	}

	/**
	 * @param reader
	 * @return the {@link Program} node generated from the given {@link Reader}
	 * @throws Exception
	 * @deprecated use Thread-Safe ASTParser methods
	 */
	public synchronized static Program parse(Reader reader, boolean aspTagsAsPhp, String phpVersion) throws Exception {
		AST ast = new AST(EMPTY_STRING_READER, VERSION_PHP5, false);		
		final Scanner lexer = getLexer(ast, reader, phpVersion, aspTagsAsPhp);
		final lr_parser phpParser = getParser(phpVersion, ast);
		phpParser.setScanner(lexer);

		final Symbol symbol = phpParser.parse();
		return symbol == null ? null : (Program) symbol.value;
	}

	/**
	 * Constructs a scanner from a given reader
	 * @param ast2 
	 * @param reader
	 * @param phpVersion
	 * @param aspTagsAsPhp
	 * @return
	 * @throws IOException
	 */
	private static Scanner getLexer(AST ast, Reader reader, String phpVersion, boolean aspTagsAsPhp) throws IOException {
		if (VERSION_PHP4.equals(phpVersion)) {
			final PhpAstLexer4 lexer4 = getLexer4(reader);
			lexer4.setUseAspTagsAsPhp(aspTagsAsPhp);
			lexer4.setAST(ast);
			return lexer4;
		} else if (VERSION_PHP5.equals(phpVersion)) {
			final PhpAstLexer5 lexer5 = getLexer5(reader);
			lexer5.setUseAspTagsAsPhp(aspTagsAsPhp);
			lexer5.setAST(ast);
			return lexer5;
		} else {
			throw new IllegalArgumentException(CoreMessages.getString("ASTParser_1") + phpVersion);
		}
	}

	private static lr_parser getParser(String phpVersion, AST ast) throws IOException {
		if (VERSION_PHP4.equals(phpVersion)) {
			PHP_AST_PARSER4.setAST(ast);
			return PHP_AST_PARSER4;
		} else if (VERSION_PHP5.equals(phpVersion)) {
			PHP_AST_PARSER5.setAST(ast);
			return PHP_AST_PARSER5;
		} else {
			throw new IllegalArgumentException(CoreMessages.getString("ASTParser_1") + phpVersion);
		}

	}

	/**
	 * @param reader
	 * @return the singleton {@link PhpAstLexer5}
	 */
	private static PhpAstLexer5 getLexer5(Reader reader) throws IOException {
		final PhpAstLexer5 phpAstLexer5 = ASTParser.PHP_AST_LEXER5;
		phpAstLexer5.yyreset(reader);
		phpAstLexer5.resetCommentList();
		return phpAstLexer5;
	}

	/**
	 * @param reader
	 * @return the singleton {@link PhpAstLexer5}
	 */
	private static PhpAstLexer4 getLexer4(Reader reader) throws IOException {
		final PhpAstLexer4 phpAstLexer4 = ASTParser.PHP_AST_LEXER4;
		phpAstLexer4.yyreset(reader);
		phpAstLexer4.resetCommentList();
		return phpAstLexer4;
	}
}
