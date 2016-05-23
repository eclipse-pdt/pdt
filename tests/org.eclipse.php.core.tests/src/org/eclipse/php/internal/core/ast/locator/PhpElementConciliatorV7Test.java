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
package org.eclipse.php.internal.core.ast.locator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.junit.Test;

public class PhpElementConciliatorV7Test extends PhpElementConciliatorV5_6Test {

	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP7_0;
	}

	@Test
	public void concileFunctionReturnType() {
		IFile file = null;
		try {
			file = setFileContent("<?php function foo(DateTime $bar): DateTime {}?>");
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotNull(file);

		Program program = createProgram(file);

		assertNotNull(program);

		// select the class name.
		int start = 39;
		ASTNode selectedNode = locateNode(program, start, 0);
		assertNotNull(selectedNode);

		assertEquals(PhpElementConciliator.CONCILIATOR_CLASSNAME, PhpElementConciliator.concile(selectedNode));
	}

}
