/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
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
package org.eclipse.php.core.tests.dom_ast.parser;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class DomParserTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/dom_parser/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php53/php5only" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/dom_parser/php53",
				"/workspace/dom_parser/php53/php5only", "/workspace/dom_parser/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/dom_parser/php53",
				"/workspace/dom_parser/php53/php5only", "/workspace/dom_parser/php54", "/workspace/dom_parser/php55" });
		TESTS.put(PHPVersion.PHP5_6,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php53/php5only",
						"/workspace/dom_parser/php54", "/workspace/dom_parser/php55", "/workspace/dom_parser/php56" });
		TESTS.put(PHPVersion.PHP7_0, new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
				"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71" });
	};

	private ASTParser parser;

	public DomParserTests(PHPVersion phpVersion, String fileNames[]) {
		parser = ASTParser.newParser(phpVersion, ProjectOptions.useShortTags((IProject) null));
	}

	@Test
	public void parserTest(String fileName) throws Exception {
		PdttFile file = new PdttFile(PHPCoreTests.getDefault().getBundle(), fileName, "UTF-8");

		parser.setSource(file.getFile().trim().toCharArray());
		Program program = parser.createAST(new NullProgressMonitor());

		PDTTUtils.assertContents(file.getExpected(), program.toString());
	}
}
