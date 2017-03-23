/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.dom_ast;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.core.ast.nodes.DefaultCommentMapper;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.Statement;
import org.eclipse.php.internal.core.ast.scanner.php5.PhpAstLexer;
import org.eclipse.php.core.project.ProjectOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

/**
 * Tests for {@link DefaultCommentMapper}
 * 
 * @author Roy, 2007
 */
public class CommentMapperTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Test
	public void variable() throws Exception {
		String str = "<?php \r\n// comment of $a\r\n$a; ?>";
		parseAndCompare(str);
	}

	@Test
	public void variableTwoComments() throws Exception {
		String str = "<?php \r\n// comment of $a\r\n// comment of $a\r\n$a; ?>";
		parseAndCompare(str);
	}

	@Test
	public void variableMultiple() throws Exception {
		String str = "<?php \r\n/** \r\ncomment of $a */\r\n$a; ?>";
		parseAndCompare(str);
	}

	@Test
	public void function() throws Exception {
		String str = "<?php \r\n// comment of foo()\r\nfunction foo() {\r\n   }; ?>";
		parseAndCompare(str);
	}

	@Test
	public void testClass() throws Exception {
		String str = "<?php \r\n// comment of A\r\nclass A {\r\n}; ?>";
		parseAndCompare(str);
	}

	@Test
	public void method() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of method foo()\r\n public function foo() {  }  \r\n} ?>";
		parseAndCompareInner(str, 0);
	}

	@Test
	public void field() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of field $a \r\n public $a = 5;\r\n } ?>";
		parseAndCompareInner(str, 0);
	}

	@Test
	public void methodSecond() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of method foo()\r\n public function foo() {  }  \r\n \r\n // comment of method bar()\r\n public function bar() {  } \r\n } ?>";
		parseAndCompareInner(str, 1);
	}

	@Test
	public void fieldSecond() throws Exception {
		String str = "<?php \r\n class A {\r\n // comment of field $a \r\n public $a = 5;\r\n \r\n/** \r\n * comment of field $a */ \r\n public $b = 5;\r\n } ?>";
		parseAndCompareInner(str, 1);
	}

	/**
	 * 
	 * @param reader
	 *            stringReader of inputstream
	 * @param str
	 * @throws Exception
	 */
	public void parseAndCompare(String programStr) throws Exception {
		final IDocument document = new Document(programStr);
		final Reader reader = new StringReader(programStr);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5, ProjectOptions.useShortTags((IProject) null))
				.createAST(new NullProgressMonitor());

		program.initCommentMapper(document, new PhpAstLexer(reader));

		final Statement node = program.statements().get(0);
		final int extendedLength = program.getExtendedLength(node);

		assert extendedLength > node.getLength();
	}

	/**
	 * @param reader
	 *            stringReader of inputstream
	 * @param str
	 * @throws Exception
	 */
	public void parseAndCompareInner(String programStr, int index) throws Exception {
		final IDocument document = new Document(programStr);
		final Reader reader = new StringReader(programStr);
		Program program = ASTParser.newParser(reader, PHPVersion.PHP5, ProjectOptions.useShortTags((IProject) null))
				.createAST(new NullProgressMonitor());

		program.initCommentMapper(document, new PhpAstLexer(reader));

		final ClassDeclaration node = (ClassDeclaration) program.statements().get(0);
		final Statement statement = node.getBody().statements().get(index);

		final int extendedLength = program.getExtendedLength(statement);

		assert extendedLength > statement.getLength();
	}

}
