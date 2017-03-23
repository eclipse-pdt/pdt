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

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPSourceParserFactory;
import org.eclipse.php.internal.core.compiler.ast.visitor.TypeDeclarationVisitor;
import org.eclipse.php.core.project.ProjectOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class TypeDeclarationVisitorTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/astutils/type_declaration_visitor/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/astutils/type_declaration_visitor/php5",
				"/workspace/astutils/type_declaration_visitor/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/astutils/type_declaration_visitor/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/astutils/type_declaration_visitor/php54",
				"/workspace/astutils/type_declaration_visitor/php55" });
	};

	private AbstractPHPSourceParser parser;

	public TypeDeclarationVisitorTests(PHPVersion version, String[] fileNames) {
		parser = PHPSourceParserFactory.createParser(version);
	}

	@Test
	public void visitor(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(pdttFile.getFile().trim().getBytes());
		ModuleDeclaration moduleDeclaration = (ModuleDeclaration) parser.parse(new InputStreamReader(inputStream), null,
				ProjectOptions.useShortTags((IProject) null));

		final StringBuilder builder = new StringBuilder();

		moduleDeclaration.traverse(new TypeDeclarationVisitor() {
			@Override
			public void visitType(TypeDeclaration s) {
				builder.append(s.getName());
				builder.append('\n');
			}
		});

		String actual = builder.toString();
		String expected = pdttFile.getExpected();

		PDTTUtils.assertContents(expected, actual);
	}
}
