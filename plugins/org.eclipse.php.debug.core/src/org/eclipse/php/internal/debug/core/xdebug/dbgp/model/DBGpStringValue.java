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

import java.io.UnsupportedEncodingException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.w3c.dom.Node;

public class DBGpStringValue extends DBGpValue {

	private boolean complete = false;
	private int requiredBytes;
	private IVariable[] stringInfo = null;

	/**
	 * 
	 * @param owningVariable
	 * @param property
	 * @param strByteLen
	 *            if set to -1, always states we don't have the complete string
	 */
	public DBGpStringValue(DBGpVariable owningVariable, Node property,
			int strByteLen) {
		super(owningVariable);
		setModifiable(true);
		simpleParseNode(property);
		if (getValueBytes() == null) {
			// we didn't get a binary representation, so we must create one
			String XMLEncoding = property.getOwnerDocument().getInputEncoding();
			if (XMLEncoding == null) {
				XMLEncoding = ((DBGpTarget) getDebugTarget())
						.getBinaryEncoding();
			}
			try {
				setValueBytes(getValueString().getBytes(XMLEncoding));
			} catch (UnsupportedEncodingException uee) {
				DBGpLogger.logException("unexpected encoding problem", this, //$NON-NLS-1$
						uee);
				// use the platform encoding
				try {
					setValueBytes(getValueString().getBytes());
				} catch (DebugException e) {
					DBGpLogger.logException("unexpected exception", this, e); //$NON-NLS-1$
					setValueBytes(new byte[0]);
				}
			} catch (DebugException e) {
				DBGpLogger.logException("unexpected exception", this, e); //$NON-NLS-1$
				setValueBytes(new byte[0]);
			}
		}

		int actualLength = getValueBytes().length;
		complete = actualLength >= strByteLen;
		requiredBytes = strByteLen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return DBGpVariable.PHP_STRING;
	}

	void genValueString(String data) {
		stringInfo = null;
		if (data != null && data.trim().length() > 0) {
			setValueString(data);
		} else {
			// if we have no data, then we have an empty string. Could confirm
			// this
			// with the length.
			setValueString(""); //$NON-NLS-1$
			setValueBytes(new byte[0]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#setValue(java.lang.String
	 * )
	 */
	public void setValue(String expression) throws DebugException {
		stringInfo = null;
		if (expression != null) {
			setValueString(expression.trim());
		} else {
			setValueString(IDBGpModelConstants.INVALID_VAR_CONTENT);
		}
		byte[] newBytes;
		try {
			newBytes = expression.getBytes(((DBGpTarget) getDebugTarget())
					.getBinaryEncoding());
		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("unexpected encoding problem", this, e); //$NON-NLS-1$
			newBytes = expression.getBytes();
		}
		setValueBytes(newBytes);
		requiredBytes = newBytes.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#verifyValue(java.lang
	 * .String)
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

		if (stringInfo == null) {
			int arrayLength = getValueBytes().length;
			// stringInfo = createVariables(getValueBytes(), 0, arrayLength, 1,
			// getDebugTarget());
			stringInfo = SimpleByteArrayValue.createVariables(getValueBytes(),
					0, arrayLength, 1, getDebugTarget());

			// add in the current length (real length) information
			IValue iv = new SimpleIntValue(arrayLength, getRequiredBytes(),
					getDebugTarget());
			// length
			stringInfo[0] = new SimpleVariable(
					PHPDebugCoreMessages.XDebug_DBGpStringValue_0, iv,
					getDebugTarget()); 
		}
		return stringInfo;
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return true;
	}
}
