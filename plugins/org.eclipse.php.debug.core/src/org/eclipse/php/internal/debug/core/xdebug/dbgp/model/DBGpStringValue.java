/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.w3c.dom.Node;

public class DBGpStringValue extends DBGpValue {

	private boolean complete = false;
	private int requiredBytes; 
	private IVariable[] stringInfo = null;
	
	/**
	 * 
	 * @param owningVariable
	 * @param property
	 * @param strByteLen if set to -1, always states we don't have the complete string
	 */
	public DBGpStringValue(DBGpVariable owningVariable, Node property, int strByteLen) {
		super(owningVariable);
		setModifiable(true);
		simpleParseNode(property);
		int actualLength = getValueBytes().length;
		complete = actualLength >= strByteLen;
		requiredBytes = strByteLen;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return DBGpVariable.PHP_STRING;
	}

	void genValueString(String data) {
		stringInfo = null;
		if (data != null && data.trim().length() > 0) {
			setValueString(data);
		} else {
			// if we have no data, then we have an empty string. Could confirm this
			// with the length.
			setValueString("");
			setValueBytes(new byte[0]);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
		stringInfo = null;
		if (expression != null) {
			setValueString(expression.trim());
		} else {
			setValueString(IDBGpModelConstants.INVALID_VAR_CONTENT);
		}
		byte[] newBytes = expression.getBytes(((DBGpTarget)getDebugTarget()).getBinaryCharset());
		setValueBytes(newBytes);
		requiredBytes = newBytes.length;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#verifyValue(java.lang.String)
	 */
	boolean verifyValue(String expression) throws DebugException {
		// any string is ok
		return true;
	}

	public boolean isComplete() {
		return complete;
	}

	public int getRequiredBytes() {
		return requiredBytes;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		final int split = 100;
		if (stringInfo == null) {
			int arrayLength = getValueBytes().length;
			int subs = arrayLength/split;
			if (arrayLength % split !=0) {
				subs++;
			}

			if (arrayLength > split) {
				stringInfo = new IVariable[subs + 1];
			}
			else {
				stringInfo = new IVariable[arrayLength + 1];				
			}
			IValue iv = new SimpleIntValue(arrayLength, getRequiredBytes(), getDebugTarget());
			stringInfo[0] = new SimpleVariable("length", iv, getDebugTarget());
			
			if (arrayLength > split) {
				int rangeStart = 0;
				int rangeEnd = 0;
				for (int j=0; j < subs; j++) {
					if (j == subs - 1) {
						rangeEnd = arrayLength - 1; 
					}
					else {
						rangeEnd = rangeStart + split - 1;
					}
					if (rangeStart <= rangeEnd) {
						iv = new SimpleByteArrayValue(getValueBytes(), rangeStart, rangeEnd, getDebugTarget());
						stringInfo[j + 1] = new SimpleVariable("[" + rangeStart + ".." + rangeEnd + "]", iv, getDebugTarget());				
						rangeStart += split;
						
					}
				}
			}
			else {
				// don't split out the data.
				for (int i = 0; i < arrayLength; i++) {
					IValue iv2 = new SimpleByteValue(getValueBytes()[i], getDebugTarget());
					stringInfo[i + 1] = new SimpleVariable(Integer.toString(i), iv2, getDebugTarget());
				}
				
			}
		}
		return stringInfo;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return true;
	}
}
