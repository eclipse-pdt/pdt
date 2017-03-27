/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import java.io.UnsupportedEncodingException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IPHPDataType;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.Base64;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

/**
 * Abstract base class for DBGp values.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractDBGpValue extends DBGpElement implements IValue, IPHPDataType {

	/**
	 * DBGp value data decoder.
	 */
	protected class DBGpValueData {

		private static final String ENCODING_BASE64 = "base64"; //$NON-NLS-1$

		private byte[] fValueBytes = null;
		private String fValueString;

		protected DBGpValueData(Node property) {
			decode(property);
		}

		private void decode(Node property) {
			String encoding = DBGpResponse.getAttribute(property, "encoding"); //$NON-NLS-1$
			Node Child = property.getFirstChild();
			if (Child != null) {
				String valueData = Child.getNodeValue();
				fValueString = valueData;
				if (encoding != null && encoding.equalsIgnoreCase(ENCODING_BASE64)) {
					if (valueData != null && valueData.trim().length() != 0) {
						DBGpTarget target = (DBGpTarget) getDebugTarget();
						fValueBytes = Base64.decode(valueData.trim());
						try {
							fValueString = new String(fValueBytes, target.getBinaryEncoding());
						} catch (UnsupportedEncodingException e) {
							DBGpLogger.logException("Unexpected encoding problem", //$NON-NLS-1$
									this, e);
							fValueString = new String(fValueBytes);
						}
					}
				}
			}
		}

		public byte[] getValueBytes() {
			return fValueBytes;
		}

		public String getValueString() {
			return fValueString;
		}

	}

	protected DataType fDataType;
	protected boolean fHasVariables = false;
	protected DBGpVariable fOwner;
	protected String fValueString = ""; //$NON-NLS-1$
	protected Node fDescriptor;

	/**
	 * Creates new DBGp value.
	 * 
	 * @param owner
	 */
	public AbstractDBGpValue(DBGpVariable owner) {
		super(owner.getDebugTarget());
		this.fOwner = owner;
		update(fOwner.getDescriptor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.model.IPHPDataType#getDataType()
	 */
	public DataType getDataType() {
		return fDataType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	@Override
	public String getReferenceTypeName() throws DebugException {
		return fDataType.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	@Override
	public String getValueString() throws DebugException {
		return fValueString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	@Override
	public IVariable[] getVariables() throws DebugException {
		return new IVariable[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	@Override
	public boolean hasVariables() throws DebugException {
		return fHasVariables;
	}

	public String getValueDetail() throws DebugException {
		return getValueString();
	}

	/**
	 * Generates and returns related value string.
	 * 
	 * @param valueData
	 * @return value string
	 */
	protected abstract String createValueString(DBGpValueData valueData);

	/**
	 * Checks if value modification is supported.
	 * 
	 * @return <code>true</code> if modification is supported,
	 *         <code>false</code> otherwise
	 */
	protected abstract boolean supportsValueModification();

	/**
	 * Checks if provided value expression is valid.
	 * 
	 * @param expression
	 * @return <code>true</code> if provided value expression is valid,
	 *         <code>false</code> otherwise
	 */
	protected abstract boolean verifyValue(String expression);

	/**
	 * Sets new value string.
	 * 
	 * @param value
	 */
	protected void setValue(String value) {
		fValueString = value;
	}

	/**
	 * Returns variable that is owner of this value.
	 * 
	 * @return variable that is owner of this value
	 */
	protected DBGpVariable getOwner() {
		return fOwner;
	}

	/**
	 * Returns related descriptor.
	 * 
	 * @return related descriptor
	 */
	protected Node getDescriptor() {
		return fDescriptor;
	}

	/**
	 * Updates value state with the provided descriptor.
	 * 
	 * @param descriptor
	 */
	protected void update(Node descriptor) {
		// Reset state
		fDescriptor = descriptor;
		// Set up type
		String t = DBGpResponse.getAttribute(fDescriptor, "type"); //$NON-NLS-1$
		fDataType = DataType.find(t);
		fValueString = createValueString(new DBGpValueData(fDescriptor));
	}

}
