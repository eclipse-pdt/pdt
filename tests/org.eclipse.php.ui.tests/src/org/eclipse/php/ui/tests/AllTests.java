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
package org.eclipse.php.ui.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.php.ui.tests.contentassist.ContentAssistTests;
import org.eclipse.php.ui.tests.formatter.autoedit.FormatterAutoEditTests;
import org.eclipse.php.ui.tests.semantic_highlighter.SemanticHighlightingTests;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.php.ui");

		// $JUnit-BEGIN$

		// Semantic highlighter tests
		suite.addTest(SemanticHighlightingTests.suite());
		// content assist
		suite.addTest(ContentAssistTests.suite());
		// auto edit, indentation
		suite.addTest(FormatterAutoEditTests.suite());
		return suite;
	}
}
