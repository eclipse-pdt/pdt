/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;

public abstract class AbstractRenameRefactoringTest extends AbstractRefactoringTest {
	public AbstractRenameRefactoringTest() {
		super();
	}

	public AbstractRenameRefactoringTest(String name) {
		super(name);
	}

	protected ASTNode locateNode(Program program,int start,int end) {
		ASTNode locateNode = NodeFinder.perform(program, start, end);
		return locateNode;
	}

}