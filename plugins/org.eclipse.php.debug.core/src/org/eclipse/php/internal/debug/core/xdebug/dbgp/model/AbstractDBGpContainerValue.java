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

import java.util.Arrays;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.VirtualPartition;
import org.eclipse.php.internal.debug.core.model.VirtualPartition.IVariableProvider;
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
				DBGpTarget target = (DBGpTarget) getDebugTarget();
				Node property = target.getProperty(getOwner().getFullName(), String.valueOf(getOwner().getStackLevel()),
						fPage);
				NodeList childProperties = property.getChildNodes();
				int childrenReceived = childProperties.getLength();
				fPartitionVariables = new IVariable[childrenReceived];
				if (childrenReceived > 0) {
					for (int i = 0; i < childrenReceived; i++) {
						Node childProperty = childProperties.item(i);
						IVariable child;
						if (isUninit(childProperty)) {
							child = new DBGpUninitVariable(getDebugTarget());
						} else {
							child = createVariable(childProperty);
						}
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

	protected IVariable[] fCurrentVariables = null;
	protected IVariable[] fPreviousVariables = null;
	protected IVariable[] fPartitions = new IVariable[] {};

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
		return fPartitions;
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
		fPartitions = new IVariable[] {};
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
		return fPartitions.length > 0;
	}

	/**
	 * Checks if given property node is an uninitialized element.
	 * 
	 * @param property
	 * @return <code>true</code> if given property node is an uninitialized
	 *         element, <code>false</code> otherwise
	 */
	protected boolean isUninit(Node property) {
		String type = DBGpResponse.getAttribute(property, "type"); //$NON-NLS-1$
		if (DataType.find(type) == DataType.PHP_UNINITIALIZED) {
			return true;
		}
		return false;
	}

	/**
	 * Uses container value related node to fetch child elements and build child
	 * variables or multiple pages with variables.
	 */
	protected void fetchVariables() {
		fCurrentVariables = new IVariable[] {};
		fPartitions = new IVariable[] {};
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
				fDescriptor = target.getProperty(getOwner().getFullName(), String.valueOf(getOwner().getStackLevel()),
						0);
				// Might be null for watch expression variables
				if (fDescriptor == null) {
					fCurrentVariables = new IVariable[] {};
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
					IVariable child;
					if (isUninit(childProperty)) {
						child = new DBGpUninitVariable(getDebugTarget());
					} else {
						child = createVariable(childProperty);
					}
					fCurrentVariables[i] = merge(child);
				}
			}
		} else {
			// Create multiple pages
			int subCount = (int) Math.ceil((double) childCount / (double) pageSize);
			fPartitions = new IVariable[subCount];
			for (int i = 0; i < subCount; i++) {
				int startIndex = i * pageSize;
				int endIndex = (i + 1) * pageSize - 1;
				if (endIndex > childCount) {
					endIndex = childCount - 1;
				}
				IVariable page = new VirtualPartition(this, new DBGpPage(i), startIndex, endIndex);
				fPartitions[i] = page;
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
