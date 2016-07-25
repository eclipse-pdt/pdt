/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.VIRTUAL_PARTITION;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * This class is an implementation of a variable that might act as a "container"
 * for grouping variables (i.e. large sets of array members).
 * 
 * @author Bartlomiej Laczkowski
 */
public class VirtualPartition implements IVirtualPartition {

	private final class Value implements IValue {

		private final IDebugElement debugElement;

		private Value(IDebugElement debugElement) {
			this.debugElement = debugElement;
		}

		@Override
		public String getModelIdentifier() {
			return debugElement.getModelIdentifier();
		}

		@Override
		public IDebugTarget getDebugTarget() {
			return debugElement.getDebugTarget();
		}

		@Override
		public ILaunch getLaunch() {
			return debugElement.getLaunch();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			return debugElement.getAdapter(adapter);
		}

		@Override
		public String getReferenceTypeName() throws DebugException {
			return ""; //$NON-NLS-1$
		}

		@Override
		public String getValueString() throws DebugException {
			return ""; //$NON-NLS-1$
		}

		@Override
		public boolean isAllocated() throws DebugException {
			return false;
		}

		@Override
		public IVariable[] getVariables() throws DebugException {
			return variableProvider.getVariables();
		}

		@Override
		public boolean hasVariables() throws DebugException {
			return true;
		}

	}

	private IVariableProvider variableProvider;
	private IValue value;
	private int startIndex;
	private int endIndex;

	/**
	 * Creates new virtual partition
	 * 
	 * @param element
	 * @param variableProvider
	 * @param start
	 * @param end
	 */
	public VirtualPartition(IDebugElement element, IVariableProvider variableProvider, int start, int end) {
		this.value = new Value(element);
		this.variableProvider = variableProvider;
		this.startIndex = start;
		this.endIndex = end;
	}

	@Override
	public String getModelIdentifier() {
		return value.getModelIdentifier();
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return value.getDebugTarget();
	}

	@Override
	public ILaunch getLaunch() {
		return value.getLaunch();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		// forbidden
	}

	@Override
	public void setValue(IValue value) throws DebugException {
		// forbidden
	}

	@Override
	public boolean supportsValueModification() {
		return false;
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		return false;
	}

	@Override
	public IValue getValue() throws DebugException {
		return value;
	}

	@Override
	public String getName() throws DebugException {
		return "[" + startIndex + "..." + endIndex + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
															// ;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return ""; //$NON-NLS-1$
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		return false;
	}

	@Override
	public boolean hasFacet(Facet facet) {
		if (facet == VIRTUAL_PARTITION)
			return true;
		return false;
	}

	@Override
	public void addFacets(Facet... facet) {
		// forbidden
	}

	@Override
	public void setProvider(IVariableProvider variableProvider) {
		this.variableProvider = variableProvider;
	}

}
