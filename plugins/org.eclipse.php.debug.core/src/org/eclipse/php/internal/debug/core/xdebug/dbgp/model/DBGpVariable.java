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

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.KIND_ARRAY_MEMBER;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.IPHPDataType;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

/**
 * Abstract DBGp variable implementation.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class DBGpVariable extends DBGpElement implements IVariable, IVariableFacet, IPHPDataType {

	public enum Kind {

		STACK, EVAL;
	}

	protected final int fStackLevel;
	protected final Set<Facet> fFacets = new HashSet<Facet>();
	protected DataType fDataType;
	protected Node fDescriptor;
	protected String fName;
	protected String fFullName;
	protected String fAddress;
	protected AbstractDBGpValue fValue;
	protected boolean fHasValueChanged = false;
	protected boolean fIsDirty = true;

	/**
	 * @param target
	 */
	public DBGpVariable(IDebugTarget target, Node descriptor, int stackLevel, Facet... facets) {
		super(target);
		this.fStackLevel = stackLevel;
		addFacets(facets);
		update(descriptor);
	}

	public DataType getDataType() {
		return fDataType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.
	 * String)
	 */
	@Override
	public void setValue(String value) throws DebugException {
		/*
		 * assume never called unless supportsValueModification is true also
		 * assume that will only be called if been verified BUG in eclipse 3.2:
		 * Cell modification doesn't call verify Value and it should. It does if
		 * you use the editor pane.
		 */
		if (!verifyValue(value)) {
			// setValue called, but verifyValue failed
			Status stat = new Status(Status.WARNING, PHPDebugPlugin.ID, PHPDebugCoreMessages.XDebug_DBGpVariable_0);
			throw new DebugException(stat);
		} else {
			// attempt to set the property
			if (((DBGpTarget) getDebugTarget()).setProperty(this, value)) {
				fValue.setValue(value);
				fireChangeEvent(DebugEvent.CONTENT);
			} else {
				// program under debug rejected value change
				throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.ID,
						DebugException.TARGET_REQUEST_FAILED, PHPDebugCoreMessages.XDebug_DBGpVariable_1, null));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.
	 * debug.core.model.IValue)
	 */
	@Override
	public void setValue(IValue value) throws DebugException {
		setValue(value.getValueString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#supportsValueModification
	 * ()
	 */
	@Override
	public boolean supportsValueModification() {
		return fValue.supportsValueModification();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.
	 * String)
	 */
	@Override
	public boolean verifyValue(String value) throws DebugException {
		return fValue.verifyValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse.
	 * debug.core.model.IValue)
	 */
	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		return verifyValue(value.getValueString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IVariableFacet#hasFacet(org.
	 * eclipse.php.internal.debug.core.model.IVariableFacet.Facet)
	 */
	@Override
	public boolean hasFacet(Facet facet) {
		return fFacets.contains(facet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IVariableFacet#addFacets(org.
	 * eclipse.php.internal.debug.core.model.IVariableFacet.Facet[])
	 */
	@Override
	public void addFacets(Facet... facets) {
		for (Facet facet : facets)
			this.fFacets.add(facet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	@Override
	public IValue getValue() throws DebugException {
		return fValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	@Override
	public String getName() throws DebugException {
		return fName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	@Override
	public String getReferenceTypeName() throws DebugException {
		if (getName().equals(VariablesUtil.CLASS_INDICATOR))
			return "class"; //$NON-NLS-1$
		return fValue.getDataType().getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	@Override
	public boolean hasValueChanged() throws DebugException {
		return fHasValueChanged;
	}

	protected abstract Kind getKind();

	protected abstract Node getNode(int page);

	protected int getStackLevel() {
		return fStackLevel;
	}

	protected String getFullName() {
		return fFullName;
	}

	protected Node getDescriptor() {
		return fDescriptor;
	}

	protected void update(Node descriptor) {
		// Set up descriptor
		fDescriptor = descriptor;
		// Set name
		fName = DBGpResponse.getAttribute(fDescriptor, "name"); //$NON-NLS-1$
		// Set full name (elements chain)
		fFullName = DBGpResponse.getAttribute(fDescriptor, "fullname"); //$NON-NLS-1$
		// Set address
		fAddress = DBGpResponse.getAttribute(fDescriptor, "address"); //$NON-NLS-1$
		// Set facets
		String facets = DBGpResponse.getAttribute(fDescriptor, "facet"); //$NON-NLS-1$
		if (facets.contains("static")) //$NON-NLS-1$
			addFacets(Facet.MOD_STATIC);
		if (facets.contains("public")) //$NON-NLS-1$
			addFacets(Facet.MOD_PUBLIC);
		else if (facets.contains("protected")) //$NON-NLS-1$
			addFacets(Facet.MOD_PROTECTED);
		else if (facets.contains("private")) //$NON-NLS-1$
			addFacets(Facet.MOD_PRIVATE);
		// Adjust name
		if (hasFacet(KIND_ARRAY_MEMBER))
			fName = '[' + fName + ']';
		else if (hasFacet(Facet.MOD_STATIC) && fName.startsWith(":")) //$NON-NLS-1$
			fName = fName.substring(fName.lastIndexOf(':') + 1);
		else if (fName.equals("::")) //$NON-NLS-1$
			fName = VariablesUtil.CLASS_INDICATOR;
		// Hopefully this will put the $ at appropriate point in the name
		if (fFullName.length() > 1 && fName.equals(fFullName.substring(1)))
			fName = fFullName;
		// Build value
		String type = DBGpResponse.getAttribute(fDescriptor, "type"); //$NON-NLS-1$
		// Catch previous value string if there is any
		String previousValueString = null;
		if (fValue != null) {
			previousValueString = fValue.fValueString;
		}
		fDataType = DataType.find(type);
		if (fValue != null && fValue.getDataType() == fDataType) {
			fValue.update(fDescriptor);
		} else {
			fValue = (new DBGpValueBuilder()).build(this);
		}
		// Check if value has changed
		if (previousValueString != null) {
			fHasValueChanged = !previousValueString.equals(fValue.fValueString);
		}
	}

}
