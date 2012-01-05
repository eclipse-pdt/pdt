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
package org.eclipse.php.core.tests.compiler_ast.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPSourceParserFactory;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class CompilerParserTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP4,
				new String[] { "/workspace/compiler_parser/php4" });
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/compiler_parser/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/compiler_parser/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] {
				"/workspace/compiler_parser/php53",
				"/workspace/compiler_parser/php54" });
	};

	public static void setUpSuite() throws Exception {
	}

	public static void tearDownSuite() throws Exception {
	}

	public CompilerParserTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Compiler Parser Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());
			final AbstractPHPSourceParser parser = PHPSourceParserFactory
					.createParser(phpVersion);

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);
						phpVerSuite.addTest(new CompilerParserTests(phpVersion
								.getAlias() + " - /" + fileName) {

							protected void runTest() throws Throwable {

								ByteArrayInputStream inputStream = new ByteArrayInputStream(
										pdttFile.getFile().trim().getBytes());
								IModuleDeclaration moduleDeclaration = parser
										.parse(new InputStreamReader(
												inputStream),
												null,
												ProjectOptions
														.useShortTags((IProject) null));

								String actual = ASTPrintVisitor
										.toXMLString((ASTNode) moduleDeclaration);
								assertContents(pdttFile.getExpected(), actual);
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
