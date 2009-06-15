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
package org.eclipse.php.core.tests.dom_ast;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.ast.nodes.DefaultCommentMapper;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Statement;
import org.eclipse.php.internal.core.ast.scanner.php5.PhpAstLexer;

/**
 * Tests for {@link  DefaultCommentMapper}
 * @author Roy, 2007
 */
public class CommentMapperTests extends TestCase {
	
	public CommentMapperTests(String name) {
		super(name);
	}
	
	public static TestSuite suite() {
		return new TestSuite(CommentMapperTests.class);
	}

	public void testVariable() throws Exception {
		String str = "<?php \r\n// comment of $a\r\n$a; ?>";
		parseAndCompare(str);
	}

	public void testVariableTwoComments() throws Exception {
		String str = "<?php \r\n// comment of $a\r\n// comment of $a\r\n$a; ?>";
		parseAndCompare(str);
	}

	public void testVariableMultiple() throws Exception {
		String str = "<?php \r\n/** \r\ncomment of $a */\r\n$a; ?>";
		parseAndCompare(str);
	}

	public void testFunction() throws Exception {
		String str = "<?php \r\n// comment of foo()\r\nfunction foo() {\r\n   }; ?>";
		parseAndCompare(str);
	}

	public void testClass() throws Exception {
		String str = "<?php \r\n// comment of A\r\nclass A {\r\n}; ?>";
		parseAndCompare(str);
	}

	public void testMethod() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of method foo()\r\n public function foo() {  }  \r\n} ?>";
		parseAndCompareInner(str, 0);
	}

	public void testField() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of field $a \r\n public $a = 5;\r\n } ?>";
		parseAndCompareInner(str, 0);
	}

	public void testMethodSecond() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of method foo()\r\n public function foo() {  }  \r\n \r\n // comment of method bar()\r\n public function bar() {  } \r\n } ?>";
		parseAndCompareInner(str, 1);
	}

	public void testFieldSecond() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of field $a \r\n public $a = 5;\r\n \r\n/** \r\n * comment of field $a */ \r\n public $b = 5;\r\n } ?>";
		parseAndCompareInner(str, 1);
	}

	/**
	 * 
	 * @param reader stringReader of inputstream
	 * @param str 
	 * @throws Exception 
	 */
	public void parseAndCompare(String programStr) throws Exception {
		final IDocument document = new Document(programStr);
		final Reader reader = new StringReader(programStr);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5).createAST(new NullProgressMonitor());

		program.initCommentMapper(document, new PhpAstLexer(reader));

		final Statement node = program.statements().get(0);
		final int extendedLength = program.getExtendedLength(node);

		assert extendedLength > node.getLength();
	}

	/**
	 * @param reader stringReader of inputstream
	 * @param str 
	 * @throws Exception 
	 */
	public void parseAndCompareInner(String programStr, int index) throws Exception {
		final IDocument document = new Document(programStr);
		final Reader reader = new StringReader(programStr);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5).createAST(new NullProgressMonitor());

		program.initCommentMapper(document, new PhpAstLexer(reader));

		final ClassDeclaration node = (ClassDeclaration) program.statements().get(0);
		final Statement statement = node.getBody().statements().get(index);

		final int extendedLength = program.getExtendedLength(statement);

		assert extendedLength > statement.getLength();
	}

}
