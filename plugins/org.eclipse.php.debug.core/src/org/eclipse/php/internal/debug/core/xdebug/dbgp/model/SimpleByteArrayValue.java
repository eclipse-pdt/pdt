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

public class SimpleByteArrayValue extends DBGpElement implements IValue {
	private byte[] value;
	private int start;
	private int end;
	private IVariable[] elements;

	public SimpleByteArrayValue(byte[] value, int start, int end,
			IDebugTarget debugTarget) {
		super(debugTarget);
		this.value = value;
		this.start = start;
		this.end = end;
	}

	public String getReferenceTypeName() throws DebugException {
		return "byte[]"; //$NON-NLS-1$
	}

	public String getValueString() throws DebugException {
		return ""; //$NON-NLS-1$
	}

	public IVariable[] getVariables() throws DebugException {
		if (elements == null) {
			elements = createVariables(value, start, end - start + 1, 0,
					getDebugTarget());
		}
		return elements;
	}

	public boolean hasVariables() throws DebugException {
		return true;
	}

	public boolean isAllocated() throws DebugException {
		return false;
	}

	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}

	/**
	 * create variable entries for a byte array
	 * 
	 * @param bytes
	 *            the byte array
	 * @param bytePos
	 *            offset within byte array to start
	 * @param byteCount
	 *            number of bytes to process
	 * @param startOffset
	 *            offset within the ivariable array to place the information.
	 *            This allows the creation of ivariables but to be able to place
	 *            variable information before the result of this output
	 * @param debugTarget
	 *            the bebug target.
	 * @return the variables to return.
	 */
	public static IVariable[] createVariables(byte[] bytes, int bytePos,
			int byteCount, int startOffset, IDebugTarget debugTarget) {
		final int childLimit = 100;

		IVariable[] childVariables = null;

		// determine the number of variables to return
		if (byteCount > childLimit) {

			// more than one variable to be returned
			int split = childLimit;
			int children = byteCount / split;
			if (byteCount % split != 0) {
				children++;
			}

			while (children > childLimit) {
				split *= 10;
				children = byteCount / split;
				if (byteCount % split != 0) {
					children++;
				}
			}

			childVariables = new IVariable[children + startOffset];

			// now populate the variables
			int rangeStart = bytePos;
			int rangeEnd = 0;
			for (int j = 0; j < children; j++) {
				if (j == children - 1) {
					rangeEnd = bytePos + byteCount - 1;
				} else {
					rangeEnd = rangeStart + split - 1;
				}
				if (rangeStart <= rangeEnd) {
					IValue iv = new SimpleByteArrayValue(bytes, rangeStart,
							rangeEnd, debugTarget);
					childVariables[j + startOffset] = new SimpleVariable("[" //$NON-NLS-1$
							+ rangeStart + ".." + rangeEnd + "]", iv, //$NON-NLS-1$ //$NON-NLS-2$
							debugTarget);
					rangeStart += split;

				}
			}
		} else {
			childVariables = new IVariable[byteCount + startOffset];
			// don't split out the data.
			for (int i = 0; i < byteCount; i++) {
				IValue iv2 = new SimpleByteValue(bytes[bytePos + i],
						debugTarget);
				childVariables[i + startOffset] = new SimpleVariable(Integer
						.toString(bytePos + i), iv2, debugTarget);
			}
		}
		return childVariables;
	}
}
