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
package org.eclipse.php.core.tests.dom_ast.parser;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;

public class DomParserTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP4, new String[] { "/workspace/dom_parser/php4" });
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/dom_parser/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/dom_parser/php53" });
	};

	public static void setUpSuite() throws Exception {
	}

	public static void tearDownSuite() throws Exception {
	}

	public DomParserTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("DOM Parser Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());
			final ASTParser newParser = ASTParser.newParser(phpVersion);

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);
						phpVerSuite.addTest(new DomParserTests(phpVersion.getAlias() + " - /" + fileName) {

							protected void runTest() throws Throwable {
								newParser.setSource(pdttFile.getFile().trim().toCharArray());
								Program program = newParser.createAST(new NullProgressMonitor());
								
								assertContents(pdttFile.getExpected(), program.toString());
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
