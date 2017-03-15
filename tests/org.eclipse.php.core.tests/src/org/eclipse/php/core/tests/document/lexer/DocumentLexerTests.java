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
package org.eclipse.php.core.tests.document.lexer;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexerFactory;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class DocumentLexerTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/document_lexer/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/document_lexer/php53" });
		TESTS.put(PHPVersion.PHP5_4,
				new String[] { "/workspace/document_lexer/php53", "/workspace/document_lexer/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/document_lexer/php53",
				"/workspace/document_lexer/php54", "/workspace/document_lexer/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/document_lexer/php53",
				"/workspace/document_lexer/php54", "/workspace/document_lexer/php56" });
		TESTS.put(PHPVersion.PHP7_0, new String[] { "/workspace/document_lexer/php7" });
	};

	private final PHPVersion version;

	public DocumentLexerTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@Test
	public void test(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(PHPCoreTests.getDefault().getBundle(), fileName, "UTF-8");
		AbstractPhpLexer lexer = PhpLexerFactory.createLexer(
				new InputStreamReader(new ByteArrayInputStream(pdttFile.getFile().trim().getBytes("UTF-8")), "UTF-8"),
				version);
		int inScriptingState = lexer.getScriptingState(); // different lexers
															// have different
															// state codes
		lexer.initialize(inScriptingState);

		StringBuilder actualBuf = new StringBuilder();
		String tokenType = lexer.yylex();
		while (tokenType != null) {
			actualBuf.append(tokenType).append('|').append(lexer.yytext()).append('|').append(lexer.yystate())
					.append('\n');
			tokenType = lexer.yylex();
		}

		PDTTUtils.assertContents(pdttFile.getExpected(), actualBuf.toString());
	}

}
