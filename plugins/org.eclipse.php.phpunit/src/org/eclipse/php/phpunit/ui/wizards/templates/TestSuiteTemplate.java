/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.wizards.templates;

import java.util.ArrayList;
import java.util.List;

public class TestSuiteTemplate extends TestTemplate {

	private static final String TEMPLATE_PATH = "resources/templates/ZendPHPUnitSuite.tpl.php"; //$NON-NLS-1$
	private static final String TEST_VAR = "TestName"; //$NON-NLS-1$
	private static final String TESTS_COMPILED = "TestsCompiled"; //$NON-NLS-1$

	static final String TESTS_STRUCT = "Tests"; //$NON-NLS-1$

	private List<String> tests;

	public void addTest(final String name) {
		if (tests == null) {
			tests = new ArrayList<>();
		}
		tests.add(name);
	}

	public void compileTests() {
		extract(TestTemplate.INPUT, TESTS_STRUCT, TESTS_COMPILED);
		if (tests == null || tests.isEmpty()) {
			set(TESTS_STRUCT, ""); //$NON-NLS-1$
			return;
		}
		for (String test : tests) {
			set(TEST_VAR, test);
			compile(TESTS_COMPILED, TESTS_STRUCT, true);
		}
	}

	@Override
	protected String getTemplatePath() {
		return TEMPLATE_PATH;
	}

}