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
package org.eclipse.php.core.tests.document.lexer;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexerFactory;

public class DocumentLexerTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP4,
				new String[] { "/workspace/document_lexer/php4" });
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/document_lexer/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/document_lexer/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] {
				"/workspace/document_lexer/php53",
				"/workspace/document_lexer/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] {
				"/workspace/document_lexer/php53",
				"/workspace/document_lexer/php54",
				"/workspace/document_lexer/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] {
				"/workspace/document_lexer/php53",
				"/workspace/document_lexer/php54",
				"/workspace/document_lexer/php56" });
	};

	public static void setUpSuite() throws Exception {
	}

	public static void tearDownSuite() throws Exception {
	}

	public DocumentLexerTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Document Lexer Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);
						phpVerSuite.addTest(new DocumentLexerTests(phpVersion
								.getAlias() + " - /" + fileName) {

							protected void runTest() throws Throwable {

								AbstractPhpLexer lexer = PhpLexerFactory
										.createLexer(new ByteArrayInputStream(
												pdttFile.getFile().trim()
														.getBytes()),
												phpVersion);
								int inScriptingState = lexer.getClass()
										.getField("ST_PHP_IN_SCRIPTING")
										.getInt(lexer); // different lexers have
														// different state codes
								lexer.initialize(inScriptingState);

								StringBuilder actualBuf = new StringBuilder();
								String tokenType = lexer.yylex();
								while (tokenType != null) {
									actualBuf.append(tokenType).append('|')
											.append(lexer.yytext()).append('|')
											.append(lexer.yystate())
											.append('\n');
									tokenType = lexer.yylex();
								}

								assertContents(pdttFile.getExpected(),
										actualBuf.toString());
							}
						});
					} catch (final Exception e) {
						// dummy test indicating PDTT file parsing failure
						phpVerSuite.addTest(new TestCase(fileName) {
							protected void runTest() throws Throwable {
								throw e;
							}
						});
					}
				}
			}
			suite.addTest(phpVerSuite);
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
