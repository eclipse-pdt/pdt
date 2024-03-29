/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.locator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.junit.BeforeClass;
import org.junit.Test;

public class PHPElementConciliatorV8_0Test extends PHPElementConciliatorV7Test {

	static {
		phpVersion = PHPVersion.PHP8_0;
	}

	@BeforeClass
	public static void setUpSuite() throws Exception {
		AbstractConciliatorTest.setUpSuite();
	}

	@Test
	public void concileAttributeName() {
		setFileContent("<?php #[DateTime(), PDO()] function foo(DateTime $bar): DateTime {}?>");

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 9;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PHPElementConciliator.CONCILIATOR_CLASSNAME, PHPElementConciliator.concile(selectedNode));

		start = 22;
		selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PHPElementConciliator.CONCILIATOR_CLASSNAME, PHPElementConciliator.concile(selectedNode));
	}

}
