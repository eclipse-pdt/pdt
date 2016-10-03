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

import java.util.*;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;
import org.eclipse.php.internal.debug.core.model.IVirtualPartition;
import org.eclipse.php.internal.debug.core.model.IVirtualPartition.IVariableProvider;
import org.eclipse.php.internal.debug.core.model.VirtualPartition;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Abstract class for DBGp container values that can contain child variables.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractDBGpContainerValue extends AbstractDBGpValue {

	/**
	 * Virtual partition for paging variables set.
	 */
	protected class DBGpPage implements IVariableProvider {

		private final int fPage;
		private IVariable[] fPartitionVariables = null;

		public DBGpPage(int page) {
			this.fPage = page;
		}

		@Override
		public synchronized IVariable[] getVariables() throws DebugException {
			// Should be synchronized and lazy
			if (fPartitionVariables == null) {
				Node node = getOwner().getNode(fPage);
				if (canProcess(node)) {
					return new IVariable[] { new DBGpUnreachableVariable(getDebugTarget()) };
				}
				NodeList childProperties = node.getChildNodes();
				int childrenReceived = childProperties.getLength();
				fPartitionVariables = new IVariable[childrenReceived];
				if (childrenReceived > 0) {
					for (int i = 0; i < childrenReceived; i++) {
						Node childProperty = childProperties.item(i);
						IVariable child = createVariable(childProperty);
						fPartitionVariables[i] = merge(child);
					}
				}
				// Add partition variables to current variables storage
				IVariable[] concat = Arrays.copyOf(fCurrentVariables,
						fCurrentVariables.length + fPartitionVariables.length);
				System.arraycopy(fPartitionVariables, 0, concat, fCurrentVariables.length, fPartitionVariables.length);
				fCurrentVariables = concat;
			}
			return fPartitionVariables;
		}

	}

	/**
	 * DBGp unreachable variable (is shown if i.e. XDebug max array depth
	 * parameter is exceeded while unfolding variables).
	 * 
	 * @author Bartlomiej Laczkowski
	 */
	protected static class DBGpUnreachableVariable extends DBGpElement implements IVariable, IVariableFacet {

		protected class DBGpUnreachableValue extends DBGpElement implements IValue {

			public DBGpUnreachableValue(IDebugTarget target) {
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

		private DBGpUnreachableValue fValue;
		protected final Set<Facet> fFacets = new HashSet<Facet>();

		/**
		 * Creates new DBGp uninitialized variable.
		 * 
		 * @param target
		 */
		public DBGpUnreachableVariable(IDebugTarget target) {
			super(target);
			fValue = new DBGpUnreachableValue(target);
			addFacets(Facet.VIRTUAL_UNINIT);
		}

		@Override
		public void setValue(String expression) throws DebugException {
			// ignore
		}

		@Override
		public void setValue(IValue value) throws DebugException {
			// ignore
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
			return fValue;
		}

		@Override
		public String getName() throws DebugException {
			return DataType.PHP_UNINITIALIZED.getText();
		}

		@Override
		public String getReferenceTypeName() throws DebugException {
			return null;
		}

		@Override
		public boolean hasValueChanged() throws DebugException {
			return false;
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

	protected IVariable[] fCurrentVariables = null;
	protected IVariable[] fPreviousVariables = null;
	protected Map<String, IVirtualPartition> fCurrentPartitions = new LinkedHashMap<>();
	protected Map<String, IVirtualPartition> fPreviousPartitions = new LinkedHashMap<>();

	/**
	 * Creates new DBGp container value.
	 * 
	 * @param owner
	 */
	public AbstractDBGpContainerValue(DBGpVariable owner) {
		super(owner);
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
		// Should be synchronized and lazy
		if (fCurrentVariables == null) {
			fetchVariables();
		}
		if (!hasPages()) {
			return fCurrentVariables;
		}
		return fCurrentPartitions.values().toArray(new IVariable[fCurrentPartitions.size()]);
	}

	protected abstract IVariable createVariable(Node descriptor);

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
		// Reset state
		fPreviousVariables = fCurrentVariables;
		fCurrentVariables = null;
		// Check if has any child elements
		String childCountNumber = DBGpResponse.getAttribute(fDescriptor, "numchildren"); //$NON-NLS-1$
		int childCount = 0;
		fHasVariables = false;
		if (childCountNumber != null && childCountNumber.trim().length() != 0) {
			try {
				childCount = Integer.parseInt(childCountNumber);
				if (childCount > 0)
					fHasVariables = true;
			} catch (NumberFormatException nfe) {
			}
		}
	}

	/**
	 * Checks if there are multiple pages with variables.
	 * 
	 * @return <code>true</code> if there are multiple pages with variables,
	 *         <code>false</code> otherwise
	 */
	protected boolean hasPages() {
		return fCurrentPartitions.size() > 0;
	}

	/**
	 * Checks if given property node can be processed.
	 * 
	 * @param property
	 * @return <code>true</code> if given property node can be processed,
	 *         <code>false</code> otherwise
	 */
	protected boolean canProcess(Node property) {
		return property == null || "error".equalsIgnoreCase(property.getNodeName()); //$NON-NLS-1$
	}

	/**
	 * Uses container value related node to fetch child elements and build child
	 * variables or multiple pages with variables.
	 */
	protected void fetchVariables() {
		fCurrentVariables = new IVariable[] {};
		fPreviousPartitions = fCurrentPartitions;
		fCurrentPartitions = new LinkedHashMap<>();
		String childCountString = DBGpResponse.getAttribute(fDescriptor, "numchildren"); //$NON-NLS-1$
		int childCount = 0;
		if (childCountString != null && childCountString.trim().length() != 0) {
			try {
				childCount = Integer.parseInt(childCountString);
			} catch (NumberFormatException nfe) {
			}
		}
		String pageSizeStr = null;
		pageSizeStr = DBGpResponse.getAttribute(fDescriptor, "pagesize"); //$NON-NLS-1$
		int pageSize = ((DBGpTarget) getDebugTarget()).getMaxChildren();
		if (pageSizeStr != null && pageSizeStr.trim().length() != 0) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
			} catch (NumberFormatException nfe) {
			}
		}
		if (childCount <= pageSize) {
			// Child number < page size, no need for paging.
			NodeList childProperties = fDescriptor.getChildNodes();
			int childrenReceived = childProperties.getLength();
			// Check if descriptor is already filled up
			if (childCount != childrenReceived) {
				DBGpTarget target = (DBGpTarget) getDebugTarget();
				switch (getOwner().getKind()) {
				case EVAL: {
					fDescriptor = target.eval(getOwner().getFullName(), 0);
					break;
				}
				default: {
					fDescriptor = target.getProperty(getOwner().getFullName(),
							String.valueOf(getOwner().getStackLevel()), 0);
					break;
				}
				}
				if (canProcess(fDescriptor)) {
					fCurrentVariables = new IVariable[] { new DBGpUnreachableVariable(getDebugTarget()) };
					return;
				}
				childProperties = fDescriptor.getChildNodes();
				childrenReceived = childProperties.getLength();
				childCount = childrenReceived;
			}
			fCurrentVariables = new IVariable[childCount];
			if (childrenReceived > 0) {
				for (int i = 0; i < childrenReceived; i++) {
					Node childProperty = childProperties.item(i);
					IVariable child = createVariable(childProperty);
					fCurrentVariables[i] = merge(child);
				}
			}
		} else {
			// Create multiple pages
			int subCount = (int) Math.ceil((double) childCount / (double) pageSize);
			for (int i = 0; i < subCount; i++) {
				int startIndex = i * pageSize;
				int endIndex = (i + 1) * pageSize - 1;
				if (endIndex > childCount) {
					endIndex = childCount - 1;
				}
				String partitionId = String.valueOf(startIndex) + '-' + String.valueOf(endIndex);
				IVirtualPartition partition = fPreviousPartitions.get(partitionId);
				if (partition != null) {
					partition.setProvider(new DBGpPage(i));
					fCurrentPartitions.put(partitionId, partition);
				} else {
					fCurrentPartitions.put(partitionId,
							new VirtualPartition(this, new DBGpPage(i), startIndex, endIndex));
				}
			}
		}
	}

	/**
	 * Merges incoming variable. Merge is done by means of checking if related
	 * child variable existed in "one step back" state of a container. If
	 * related variable existed, it is updated with the use of the most recent
	 * descriptor and returned instead of the incoming one.
	 * 
	 * @param variable
	 * @param descriptor
	 * @return merged variable
	 */
	protected IVariable merge(IVariable variable) {
		if (fPreviousVariables == null)
			return variable;
		if (!(variable instanceof DBGpVariable))
			return variable;
		DBGpVariable incoming = (DBGpVariable) variable;
		if (incoming.getFullName().isEmpty())
			return incoming;
		for (IVariable stored : fPreviousVariables) {
			if (stored instanceof DBGpVariable) {
				DBGpVariable previous = (DBGpVariable) stored;
				if (previous.getFullName().equals(incoming.getFullName())) {
					((DBGpVariable) stored).update(incoming.getDescriptor());
					return stored;
				}
			}
		}
		return variable;
	}

}
