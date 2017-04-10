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
package org.eclipse.php.ui.tests;

import org.eclipse.php.core.tests.TestAllSuiteWatcher;
import org.eclipse.php.ui.tests.commands.CommandsTests;
import org.eclipse.php.ui.tests.contentassist.ContentAssistTests;
import org.eclipse.php.ui.tests.formatter.autoedit.FormatterAutoEditTests;
import org.eclipse.php.ui.tests.generation.AddGetterSetterTests;
import org.eclipse.php.ui.tests.generation.UnimplementMethodsTests;
import org.eclipse.php.ui.tests.semantic_highlighter.SemanticHighlightingTests;
import org.junit.ClassRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContentAssistTests.class, FormatterAutoEditTests.class, SemanticHighlightingTests.class,
		CommandsTests.class, AddGetterSetterTests.class, UnimplementMethodsTests.class })
public class AllTests {

	@ClassRule
	public static TestWatcher watcher = new TestAllSuiteWatcher();

}
