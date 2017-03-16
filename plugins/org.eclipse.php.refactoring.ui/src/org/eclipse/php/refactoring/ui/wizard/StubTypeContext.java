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
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.php.core.ast.nodes.Program;

public class StubTypeContext {
	private String fBeforeString;
	private String fAfterString;
	private final Program fCuHandle;

	public StubTypeContext(Program cuHandle, String beforeString, String afterString) {
		fCuHandle = cuHandle;
		fBeforeString = beforeString;
		fAfterString = afterString;
	}

	public Program getCuHandle() {
		return fCuHandle;
	}

	public String getBeforeString() {
		return fBeforeString;
	}

	public String getAfterString() {
		return fAfterString;
	}
}
