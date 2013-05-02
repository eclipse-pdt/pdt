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
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.Base64;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

public abstract class DBGpValue extends DBGpElement implements IValue {

	private boolean modifiable = false;
	private IVariable[] variables = new IVariable[0];
	private DBGpBaseVariable owner;
	private String valueString = ""; //$NON-NLS-1$
	private byte[] valueBytes = null;

	public byte[] getValueBytes() {
		return valueBytes;
	}

	public void setValueBytes(byte[] valueBytes) {
		this.valueBytes = valueBytes;
	}

	private static final String ENCODING_BASE64 = "base64"; //$NON-NLS-1$

	public DBGpValue(DBGpBaseVariable variable) {
		super(variable.getDebugTarget());
		owner = variable;
	}

	public DBGpValue(DBGpBaseVariable variable, Node property) {
		super(variable.getDebugTarget());
		owner = variable;
		simpleParseNode(property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	public boolean isAllocated() throws DebugException {
		return true;
	}

	boolean isModifiable() {
		return modifiable;
	}

	void setModifiable(boolean canModify) {
		modifiable = canModify;
	}

	// override this definitely
	public abstract String getReferenceTypeName() throws DebugException;

	public String getValueString() throws DebugException {
		return valueString;
	}

	void setValueString(String newValueStr) {
		valueString = newValueStr;
	}

	// override this if necessary: Object, Array only
	public IVariable[] getVariables() throws DebugException {
		return variables;
	}

	// override this if necessary: Object, Array only
	public boolean hasVariables() throws DebugException {
		return false;
	}

	// override this if modifiable
	boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	// override this if modifiable
	public void setValue(String expression) throws DebugException {
	}

	public DBGpBaseVariable getOwner() {
		return owner;
	}

	/*
	 * This method extracts the information out of the CDATA section
	 * 
	 * <property name="1" fullname="$a[1]" address="10208616" type="float">
	 * <![CDATA[15.1]]> </property>
	 */
	void simpleParseNode(Node property) {
		String data = null;
		String encoding = DBGpResponse.getAttribute(property, "encoding"); //$NON-NLS-1$
		Node Child = property.getFirstChild();
		if (Child != null) {
			data = decodeValue(Child.getNodeValue(), encoding);
		}
		genValueString(data);
	}

	private String decodeValue(String valueData, String encoding) {
		String resStr = valueData;
		if (encoding != null && encoding.equalsIgnoreCase(ENCODING_BASE64)) {
			if (valueData != null && valueData.trim().length() != 0) {
				DBGpTarget target = (DBGpTarget) getDebugTarget();
				valueBytes = Base64.decode(valueData.trim());
				try {
					resStr = new String(valueBytes, target.getBinaryEncoding());
				} catch (UnsupportedEncodingException e) {
					DBGpLogger.logException("unexpected encoding problem", //$NON-NLS-1$
							this, e);
					resStr = new String(valueBytes);
				}
			}
		}
		return resStr;
	}

	// override this definitely
	abstract void genValueString(String data);

	/*
	 * // override these to enhance the way variables are displayed // in the
	 * variable view, ie to reduce the refresh of all entities // in there.
	 * 
	 * @Override public boolean equals(Object obj) { // should work for
	 * DBGpNullValue, DBGpNumValue // DBGpResourceValue, DBGpUnInitValue,
	 * DBGpBoolValue // but not container values. if
	 * (!obj.getClass().isInstance(this)) { return false; } DBGpValue value =
	 * (DBGpValue)obj; if (!valueString.equals(value.valueString)) { return
	 * false; } return true; }
	 * 
	 * @Override public int hashCode() { return getClass().hashCode() +
	 * valueString.hashCode(); }
	 */

}
