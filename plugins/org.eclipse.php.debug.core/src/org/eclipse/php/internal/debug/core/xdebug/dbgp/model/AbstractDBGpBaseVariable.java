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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;

/**
 * Abstract base for DBGp's variables.
 */
public abstract class AbstractDBGpBaseVariable extends DBGpElement implements
		IVariable, IVariableFacet {

	private String stackLevel;
	private String fullName;
	private String address;
	private final Set<Facet> facets = new HashSet<Facet>();

	/**
	 * Creates new DBGp variable.
	 * 
	 * @param target
	 * @param stackLevel
	 * @param fullName
	 * @param facets
	 */
	public AbstractDBGpBaseVariable(IDebugTarget target, String stackLevel,
			String fullName, Facet... facets) {
		super(target);
		this.stackLevel = stackLevel;
		this.fullName = fullName;
		addFacets(facets);
	}

	/**
	 * Creates new DBGp variable.
	 * 
	 * @param target
	 * @param stackLevel
	 * @param facets
	 */
	public AbstractDBGpBaseVariable(IDebugTarget target, String stackLevel,
			Facet... facets) {
		super(target);
		this.stackLevel = stackLevel;
		addFacets(facets);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#supportsValueModification
	 * ()
	 */
	public boolean supportsValueModification() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang
	 * .String)
	 */
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse
	 * .debug.core.model.IValue)
	 */
	public boolean verifyValue(IValue value) throws DebugException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String
	 * )
	 */
	public void setValue(String expression) throws DebugException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.
	 * debug.core.model.IValue)
	 */
	public void setValue(IValue value) throws DebugException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IVariableFacet#addFacets(org
	 * .eclipse.php.internal.debug.core.model.IVariableFacet.Facet[])
	 */
	@Override
	public void addFacets(Facet... facets) {
		for (Facet facet : facets)
			this.facets.add(facet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IVariableFacet#hasFacet(org
	 * .eclipse.php.internal.debug.core.model.IVariableFacet.Facet)
	 */
	@Override
	public boolean hasFacet(Facet facet) {
		return facets.contains(facet);
	}

	/**
	 * return the full name of the variable
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * return the stack level of this variable
	 * 
	 * @return
	 */
	public String getStackLevel() {
		return stackLevel;
	}

	/**
	 * set the full name of this variable
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	// TODO - find usage for this one
	public String getAddress() {
		return address;
	}

	// TODO - find usage for this one
	public void setAddress(String address) {
		this.address = address;
	}

}
