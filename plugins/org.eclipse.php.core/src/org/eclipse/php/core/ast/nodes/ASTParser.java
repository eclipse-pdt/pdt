/*******************************************************************************
 * Copyright (c) 2009, 2018 IBM Corporation and others.
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
package org.eclipse.php.core.ast.nodes;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.Document;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.ast.util.Util;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;

/**
 * A PHP language parser for creating abstract syntax trees (ASTs).
 * <p>
 * Example: Create basic AST from source string
 * 
 * <pre>
 * String source = ...;
 * Program program = ASTParser.parse(source);
 * </pre>
 */
public class ASTParser {

	/**
	 * THREAD SAFE AST PARSER STARTS HERE
	 */
	private final AST ast;
	private final ISourceModule sourceModule;

	private ASTParser(Reader reader, PHPVersion phpVersion, boolean useASPTags, boolean useShortTags)
			throws IOException {
		this(reader, phpVersion, useASPTags, useShortTags, null);
	}

	private ASTParser(Reader reader, PHPVersion phpVersion, boolean useASPTags, boolean useShortTags,
			ISourceModule sourceModule) throws IOException {

		this.sourceModule = sourceModule;
		this.ast = new AST(reader, phpVersion, useASPTags, useShortTags);
		this.ast.setDefaultNodeFlag(ASTNode.ORIGINAL);

		// set resolve binding property and the binding resolver
		if (sourceModule != null) {
			this.ast.setFlag(AST.RESOLVED_BINDINGS);
			// try {
			this.ast.setBindingResolver(new DefaultBindingResolver(sourceModule, sourceModule.getOwner()));
			// } catch (ModelException e) {
			// throw new IOException("ModelException " + e.getMessage());
			// }
		}
	}

	/**
	 * Factory methods for ASTParser
	 */
	public static ASTParser newParser(PHPVersion version, boolean useASPTags, boolean useShortTags) {
		try {
			return new ASTParser(new StringReader(""), version, useASPTags, //$NON-NLS-1$
					useShortTags);
		} catch (IOException e) {
			assert false;
			// Since we use empty reader we cannot have an IOException here
			return null;
		}
	}

	/**
	 * Factory methods for ASTParser
	 */
	public static ASTParser newParser(ISourceModule sourceModule) {
		if (sourceModule == null) {
			throw new IllegalStateException("ASTParser - Can't parse with null ISourceModule"); //$NON-NLS-1$
		}
		return newParser(sourceModule.getScriptProject().getProject(), sourceModule);
	}

	/**
	 * Factory methods for ASTParser
	 */
	public static ASTParser newParser(PHPVersion version, boolean useASPTags, boolean useShortTags,
			ISourceModule sourceModule) {
		if (sourceModule == null) {
			throw new IllegalStateException("ASTParser - Can't parse with null ISourceModule"); //$NON-NLS-1$
		}
		try {
			final ASTParser parser = new ASTParser(new StringReader(""), version, useASPTags, //$NON-NLS-1$
					useShortTags, sourceModule);
			parser.setSource(sourceModule.getSourceAsCharArray());
			return parser;
		} catch (IOException e) {
			return null;
		} catch (ModelException e) {
			return null;
		}
	}

	public static ASTParser newParser(IProject project, ISourceModule sourceModule) {
		if (sourceModule == null) {
			throw new IllegalStateException("ASTParser - Can't parse with null ISourceModule"); //$NON-NLS-1$
		}
		try {
			final ASTParser parser = new ASTParser(new StringReader(""), //$NON-NLS-1$
					ProjectOptions.getPHPVersion(project), ProjectOptions.isSupportingASPTags(project),
					ProjectOptions.useShortTags(project), sourceModule);
			parser.setSource(sourceModule.getSourceAsCharArray());
			return parser;
		} catch (IOException e) {
			return null;
		} catch (ModelException e) {
			return null;
		}
	}

	public static ASTParser newParser(Reader reader, PHPVersion version, boolean useASPTags, boolean useShortTags)
			throws IOException {
		return new ASTParser(reader, version, useASPTags, useShortTags);
	}

	public static ASTParser newParser(Reader reader, PHPVersion version, boolean useASPTags, boolean useShortTags,
			@Nullable ISourceModule sourceModule) throws IOException {
		return new ASTParser(reader, version, useASPTags, useShortTags, sourceModule);
	}

	/**
	 * Set the raw source that will be used on parsing
	 * 
	 * @throws IOException
	 */
	public void setSource(char[] source) throws IOException {
		final CharArrayReader charArrayReader = new CharArrayReader(source);
		setSource(charArrayReader);
	}

	/**
	 * Set source of the parser
	 * 
	 * @throws IOException
	 */
	public void setSource(Reader source) throws IOException {
		this.ast.setSource(source);
	}

	/**
	 * Set the source from source module
	 * 
	 * @throws IOException
	 * @throws ModelException
	 */
	public void setSource(ISourceModule sourceModule) throws IOException, ModelException {
		this.ast.setSource(new CharArrayReader(sourceModule.getSourceAsCharArray()));
	}

	/**
	 * This operation creates an abstract syntax tree for the given AST Factory
	 * 
	 * @param progressMonitor
	 * @return Program that represents the equivalent AST, null if AST couldn't be
	 *         built
	 * @throws Exception
	 *             - for exception occurs on the parsing step
	 */
	public Program createAST(IProgressMonitor progressMonitor) throws Exception {
		if (progressMonitor == null) {
			progressMonitor = new NullProgressMonitor();
		}

		progressMonitor.beginTask("Creating Abstract Syntax Tree for source...", //$NON-NLS-1$
				3);
		final Scanner lexer = this.ast.lexer();
		final lr_parser phpParser = this.ast.parser();
		progressMonitor.worked(1);
		phpParser.setScanner(lexer);
		progressMonitor.worked(2);
		final Symbol symbol = phpParser.parse();
		progressMonitor.done();
		if (symbol == null || !(symbol.value instanceof Program)) {
			return null;
		}
		Program p = (Program) symbol.value;
		AST ast = p.getAST();

		p.setSourceModule(sourceModule);
		if (sourceModule != null) {
			p.setLineEndTable(Util.lineEndTable(new Document(sourceModule.getSource())));
		}

		// now reset the ast default node flag back to differntate between
		// original nodes
		ast.setDefaultNodeFlag(0);
		// Set the original modification count to the count after the creation
		// of the Program.
		// This is important to allow the AST rewriting.
		ast.setOriginalModificationCount(ast.modificationCount());
		return p;
	}

}
