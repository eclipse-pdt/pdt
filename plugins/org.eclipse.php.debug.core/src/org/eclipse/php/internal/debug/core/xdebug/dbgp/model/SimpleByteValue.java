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
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class SimpleByteValue extends DBGpElement implements IValue {
	private byte value;

	public SimpleByteValue(byte value, IDebugTarget debugTarget) {
		super(debugTarget);
		this.value = value;
	}

	public String getReferenceTypeName() throws DebugException {
		return "byte"; //$NON-NLS-1$
	}

	public String getValueString() throws DebugException {
		// TODO: Cache ?
		String valStr = Integer.toHexString(value & 0xFF);
		if (valStr.length() == 1) {
			valStr = "0" + valStr; //$NON-NLS-1$
		}
		return valStr;
	}

	public IVariable[] getVariables() throws DebugException {
		// TODO: cache
		return new IVariable[0];
	}

	public boolean hasVariables() throws DebugException {
		return false;
	}

	public boolean isAllocated() throws DebugException {
		return false;
	}

	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}
}
