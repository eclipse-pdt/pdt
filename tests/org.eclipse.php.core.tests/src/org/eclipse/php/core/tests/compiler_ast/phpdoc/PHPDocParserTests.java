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
package org.eclipse.php.core.tests.compiler_ast.phpdoc;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.parser.DocumentorLexer;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPDocParserTests extends AbstractPDTTTest {

	protected static final String[] TEST_DIRS = { "/workspace/phpdoc_parser" };

	public static void setUpSuite() throws Exception {
	}

	public static void tearDownSuite() throws Exception {
	}

	public PHPDocParserTests(String description) {
		super(description);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("PHPDoc Parser Tests");
		
		for (String testsDirectory : TEST_DIRS) {
			for (final String fileName : getPDTTFiles(testsDirectory)) {
				try {
					final PdttFile pdttFile = new PdttFile(fileName);
					suite.addTest(new PHPDocParserTests("/" + fileName) {

						protected void runTest() throws Throwable {

							byte[] code = pdttFile.getFile().trim().getBytes();
							InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(code));
							DocumentorLexer lexer = new DocumentorLexer(reader);
							PHPDocBlock phpDocBlock = lexer.parse();
							assertContents(pdttFile.getExpected(), ASTPrintVisitor.toXMLString(phpDocBlock));
						}
					});
				} catch (final Exception e) {
					// dummy test indicating PDTT file parsing failure
					suite.addTest(new TestCase(fileName) {
						protected void runTest() throws Throwable {
							throw e;
						}
					});
				}
			}
		}

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				setUpSuite();
			}

			protected void tearDown() throws Exception {
				tearDownSuite();
			}
		};
		return setup;
	}
}
