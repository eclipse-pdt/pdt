/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.sourcelookup;

import org.eclipse.debug.core.model.IStackFrame;

public class PHPSourceNotFoundInput {

	private IStackFrame stackFrame;

	public PHPSourceNotFoundInput(IStackFrame stackFrame) {
		this.stackFrame = stackFrame;

	}

	public IStackFrame getStackFrame() {
		return this.stackFrame;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PHPSourceNotFoundInput) {
			if (stackFrame != null) {
				return stackFrame.equals(((PHPSourceNotFoundInput) obj).stackFrame);
			}
		}
		return super.equals(obj);
	}
}
