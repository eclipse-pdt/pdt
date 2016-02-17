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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;

/**
 * DBGp uninitialized variable.
 * 
 * @author Bartlomiej Laczkowski
 */
class DBGpUninitVariable extends DBGpElement implements IVariable, IVariableFacet {

	protected class DBGpUninitValue extends DBGpElement implements IValue {

		public DBGpUninitValue(IDebugTarget target) {
			super(target);
		}

		@Override
		public String getReferenceTypeName() throws DebugException {
			return null;
		}

		@Override
		public String getValueString() throws DebugException {
			return null;
		}

		@Override
		public boolean isAllocated() throws DebugException {
			return false;
		}

		@Override
		public IVariable[] getVariables() throws DebugException {
			return new IVariable[] {};
		}

		@Override
		public boolean hasVariables() throws DebugException {
			return false;
		}

	}

	private DBGpUninitValue fValue;
	protected final Set<Facet> fFacets = new HashSet<Facet>();

	/**
	 * Creates new DBGp uninitialized variable.
	 * 
	 * @param target
	 */
	public DBGpUninitVariable(IDebugTarget target) {
		super(target);
		fValue = new DBGpUninitValue(target);
		addFacets(Facet.VIRTUAL_UNINIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.
	 * String)
	 */
	@Override
	public void setValue(String expression) throws DebugException {
		// ignore
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
		// ignore
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
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.
	 * String)
	 */
	@Override
	public boolean verifyValue(String expression) throws DebugException {
		return false;
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
		return false;
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
		return DataType.PHP_UNINITIALIZED.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	@Override
	public String getReferenceTypeName() throws DebugException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	@Override
	public boolean hasValueChanged() throws DebugException {
		return false;
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

}