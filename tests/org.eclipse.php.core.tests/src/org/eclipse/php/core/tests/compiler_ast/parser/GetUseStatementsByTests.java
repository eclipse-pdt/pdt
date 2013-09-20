/*******************************************************************************
 * Copyright (c) 2013 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
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
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPSourceParserFactory;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class GetUseStatementsByTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/astutils/get_use_statements/php53" });
	};

	public GetUseStatementsByTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Get Use Statements Tests");

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
								ModuleDeclaration moduleDeclaration = (ModuleDeclaration) parser
										.parse(new InputStreamReader(
												inputStream),
												null,
												ProjectOptions
														.useShortTags((IProject) null));

								int offset = Integer.parseInt(pdttFile
										.getConfig().get("offset"));

								UseStatement[] useStatements = ASTUtils
										.getUseStatements(moduleDeclaration,
												offset);

								StringBuilder builder = new StringBuilder();
								for (UseStatement s : useStatements) {
									builder.append(s);
								}
								String actual = builder.toString();

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
			}

			protected void tearDown() throws Exception {
			}
		};
		return setup;
	}
}
