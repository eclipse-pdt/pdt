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

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.VIRTUAL_ARRAY_MEMBER;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;
import org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet;
import org.eclipse.php.internal.debug.core.model.IVirtualPartition.IVariableProvider;
import org.eclipse.php.internal.debug.core.model.VirtualPartition;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

/**
 * DBGp string value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpStringValue extends AbstractDBGpValue {

	private class InfoVariable extends DBGpElement implements IVariable, IVariableFacet {

		private String fName;
		private IValue fValue;
		private Set<Facet> fFacets = new HashSet<Facet>();

		public InfoVariable(String name, IValue value, IDebugTarget debugTarget, Facet... facets) {
			super(debugTarget);
			this.fName = name;
			this.fValue = value;
			addFacets(facets);
		}

		public InfoVariable(String name, IValue value, IDebugTarget debugTarget) {
			super(debugTarget);
			this.fName = name;
			this.fValue = value;
		}

		public String getName() throws DebugException {
			return fName;
		}

		public String getReferenceTypeName() throws DebugException {
			return fValue.getReferenceTypeName();
		}

		public IValue getValue() throws DebugException {
			return fValue;
		}

		public boolean hasValueChanged() throws DebugException {
			return false;
		}

		public void setValue(String expression) throws DebugException {
		}

		public void setValue(IValue value) throws DebugException {
		}

		public boolean supportsValueModification() {
			return false;
		}

		public boolean verifyValue(String expression) throws DebugException {
			return true;
		}

		public boolean verifyValue(IValue value) throws DebugException {
			return true;
		}

		@Override
		public boolean hasFacet(Facet facet) {
			return fFacets.contains(facet);
		}

		@Override
		public void addFacets(Facet... facets) {
			for (Facet facet : facets)
				this.fFacets.add(facet);
		}

	}

	private class InfoByteValue extends DBGpElement implements IValue {
		private byte fValue;

		public InfoByteValue(byte value, IDebugTarget debugTarget) {
			super(debugTarget);
			this.fValue = value;
		}

		public String getReferenceTypeName() throws DebugException {
			return "byte"; //$NON-NLS-1$
		}

		public String getValueString() throws DebugException {
			String valStr = Integer.toHexString(fValue & 0xFF);
			if (valStr.length() == 1) {
				valStr = "0" + valStr; //$NON-NLS-1$
			}
			return valStr;
		}

		public IVariable[] getVariables() throws DebugException {
			return new IVariable[0];
		}

		public boolean hasVariables() throws DebugException {
			return false;
		}

		public boolean isAllocated() throws DebugException {
			return false;
		}

	}

	private class InfoLengthValue extends DBGpElement implements IValue {
		private int fCurrentLength;
		private int fTotalLength;

		public InfoLengthValue(int currentValue, int wantedValue, IDebugTarget debugTarget) {
			super(debugTarget);
			this.fCurrentLength = currentValue;
			this.fTotalLength = wantedValue;
		}

		public String getReferenceTypeName() throws DebugException {
			return null;
		}

		public String getValueString() throws DebugException {
			if (fCurrentLength == fTotalLength) {
				return Integer.toString(fCurrentLength);
			} else {
				return Integer.toString(fCurrentLength) + " (" //$NON-NLS-1$
						+ Integer.toString(fTotalLength) + ")"; //$NON-NLS-1$
			}
		}

		public IVariable[] getVariables() throws DebugException {
			return new IVariable[0];
		}

		public boolean hasVariables() throws DebugException {
			return false;
		}

		public boolean isAllocated() throws DebugException {
			return false;
		}

	}

	private boolean fIsComplete = false;
	private int fRequiredBytes;
	private IVariable[] fStringInfo = null;
	private byte[] fValueBytes;

	/**
	 * Creates new DBGp string value.
	 * 
	 * @param owner
	 */
	public DBGpStringValue(DBGpVariable owner) {
		super(owner);
	}

	// TODO - where to show complete value?
	public boolean isComplete() {
		return fIsComplete;
	}

	public int getRequiredBytes() {
		return fRequiredBytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * getVariables()
	 */
	@Override
	public synchronized IVariable[] getVariables() throws DebugException {
		if (fStringInfo == null) {
			createVariables();
		}
		return fStringInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * hasVariables()
	 */
	@Override
	public boolean hasVariables() throws DebugException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * update(org.w3c.dom.Node)
	 */
	@Override
	protected void update(Node descriptor) {
		super.update(descriptor);
		// Set up additional string data
		String size = DBGpResponse.getAttribute(descriptor, "size"); //$NON-NLS-1$
		int byteLength = -1;
		try {
			byteLength = Integer.parseInt(size);
		} catch (NumberFormatException e) {
		}
		DBGpValueData data = new DBGpValueData(descriptor);
		fValueBytes = data.getValueBytes();
		if (fValueBytes == null) {
			// We didn't get a binary representation, so we must create one
			String XMLEncoding = descriptor.getOwnerDocument().getInputEncoding();
			if (XMLEncoding == null) {
				XMLEncoding = ((DBGpTarget) getDebugTarget()).getBinaryEncoding();
			}
			try {
				fValueBytes = fValueString.getBytes(XMLEncoding);
			} catch (UnsupportedEncodingException uee) {
				DBGpLogger.logException("Unexpected encoding problem", this, //$NON-NLS-1$
						uee);
				// Use the platform encoding
				fValueBytes = fValueString.getBytes();
			}
		}

		int actualLength = fValueBytes.length;
		fIsComplete = actualLength >= byteLength;
		fRequiredBytes = byteLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * createValueString(org.eclipse.php.internal.debug.core.xdebug.dbgp.model.
	 * AbstractDBGpValue.DBGpValueData)
	 */
	@Override
	protected String createValueString(DBGpValueData valueData) {
		fStringInfo = null;
		String valueString = valueData.getValueString();
		if (valueString != null && valueString.trim().length() > 0) {
			return valueString;
		} else {
			fValueBytes = new byte[0];
			return ""; //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * supportsValueModification()
	 */
	@Override
	protected boolean supportsValueModification() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#setValue(java.lang.
	 * String )
	 */
	protected void setValue(String value) {
		fStringInfo = null;
		if (value != null) {
			fValueString = value.trim();
		} else {
			fValueString = IDBGpModelConstants.INVALID_VAR_CONTENT;
		}
		byte[] newBytes;
		try {
			newBytes = fValueString.getBytes(((DBGpTarget) getDebugTarget()).getBinaryEncoding());
		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("unexpected encoding problem", this, e); //$NON-NLS-1$
			newBytes = fValueString.getBytes();
		}
		fValueBytes = newBytes;
		fRequiredBytes = newBytes.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * verifyValue(java.lang.String)
	 */
	protected boolean verifyValue(String expression) {
		// any string is ok
		return true;
	}

	/**
	 * Creates string info variables (length and virtual array of bytes).
	 */
	protected void createVariables() {
		int byteCount = fValueBytes.length;
		fStringInfo = new IVariable[1];
		// Add length element
		fStringInfo[0] = new InfoVariable(PHPDebugCoreMessages.XDebug_DBGpStringValue_0,
				new InfoLengthValue(byteCount, getRequiredBytes(), getDebugTarget()), getDebugTarget(),
				Facet.VIRTUAL_LENGTH);
		// Add pseudo-bytes directly or as a partitions
		final int childLimit = 100;
		IVariable[] byteVariables = null;
		// determine the number of variables to return
		if (byteCount > childLimit) {
			// Split to partitions
			int subCount = (int) Math.ceil((double) byteCount / (double) childLimit);
			byteVariables = new IVariable[subCount];
			for (int i = 0; i < subCount; i++) {
				int startIndex = i * childLimit;
				int endIndex = (i + 1) * childLimit - 1;
				if (endIndex > byteCount) {
					endIndex = byteCount - 1;
				}
				final int partitionSize = (endIndex - startIndex) + 1;
				final IVariable[] partitionVariables = new IVariable[partitionSize];
				for (int j = startIndex; j <= endIndex; j++) {
					IValue byteValue = new InfoByteValue(fValueBytes[j], getDebugTarget());
					partitionVariables[j - startIndex] = new InfoVariable('[' + Integer.toString(j) + ']', byteValue,
							getDebugTarget(), VIRTUAL_ARRAY_MEMBER);
				}
				IVariable partition = new VirtualPartition(this, new IVariableProvider() {
					@Override
					public IVariable[] getVariables() throws DebugException {
						return partitionVariables;
					}
				}, startIndex, endIndex);
				byteVariables[i] = partition;
			}
		} else {
			byteVariables = new InfoVariable[byteCount];
			for (int i = 0; i < byteCount; i++) {
				IValue byteValue = new InfoByteValue(fValueBytes[i], getDebugTarget());
				byteVariables[i] = new InfoVariable('[' + Integer.toString(i) + ']', byteValue, getDebugTarget(),
						VIRTUAL_ARRAY_MEMBER);
			}
		}
		// Add byte variables to info
		IVariable[] concat = Arrays.copyOf(fStringInfo, fStringInfo.length + byteVariables.length);
		System.arraycopy(byteVariables, 0, concat, fStringInfo.length, byteVariables.length);
		fStringInfo = concat;
	}

}
