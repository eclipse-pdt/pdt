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
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;

public class SimpleVariable extends DBGpElement implements IVariable,
		IVariableFacet {

	private String name;
	private IValue value;
	private Set<Facet> facets = new HashSet<Facet>();

	public SimpleVariable(String name, IValue value, IDebugTarget debugTarget,
			Facet... facets) {
		super(debugTarget);
		this.name = name;
		this.value = value;
		addFacets(facets);
	}

	public SimpleVariable(String name, IValue value, IDebugTarget debugTarget) {
		super(debugTarget);
		this.name = name;
		this.value = value;
	}

	public String getName() throws DebugException {
		return name;
	}

	public String getReferenceTypeName() throws DebugException {
		return value.getReferenceTypeName();
	}

	public IValue getValue() throws DebugException {
		return value;
	}

	public boolean hasValueChanged() throws DebugException {
		return false;
	}

	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
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
		return facets.contains(facet);
	}

	@Override
	public void addFacets(Facet... facets) {
		for (Facet facet : facets)
			this.facets.add(facet);
	}

}
