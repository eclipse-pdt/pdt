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
package org.eclipse.php.core.tests.compiler_ast.phpdoc;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.compiler.ast.parser.DocumentorLexer;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class PHPDocParserTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final String[] TEST_DIRS = { "/workspace/phpdoc_parser" };

	public PHPDocParserTests(String[] fileNames) {
	}

	@Test
	public void parser(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);
		byte[] code = pdttFile.getFile().trim().getBytes();
		InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(code));
		DocumentorLexer lexer = new DocumentorLexer(reader);
		PHPDocBlock phpDocBlock = lexer.parse();
		PDTTUtils.assertContents(pdttFile.getExpected(), ASTPrintVisitor.toXMLString(phpDocBlock));
	}

}
