/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
