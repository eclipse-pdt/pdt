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

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBGpContainerValue extends DBGpValue {

	private IVariable[] childVariables;
	private int numChild;
	private String className;
	private int page = -1;
	private int pageSize;

	private static final boolean IS_ARRAY = false;
	private static final boolean IS_OBJECT = true;
	private boolean containerType = IS_ARRAY;

	public DBGpContainerValue(DBGpBaseVariable owningVariable, Node property) {
		super(owningVariable);
		setModifiable(false);
		if (property != null) {
			parseData(property);
			genValueString(""); //$NON-NLS-1$
		}
	}

	/**
	 * parse the xml information for an object or array
	 * 
	 * @param property
	 */
	private void parseData(Node property) {
		// children, numchildren, page, pagesize, recursive attribute page and
		// pagesize
		// only appear if you exceed the max_children option limit. Pagesize is
		// the max_children limit.
		// children can be zero, but we may as well get all the information
		// we can, so no point checking the flag. We also need to make sure
		// that DBGpVariable is not set to null here otherwise it will attempt
		// to defer the info. This is ok for no children as page will be set to
		// -1
		String type = DBGpResponse.getAttribute(property, "type"); //$NON-NLS-1$
		if (type.equals(DBGpVariable.PHP_OBJECT)) {
			containerType = IS_OBJECT;
			className = DBGpResponse.getAttribute(property, "classname"); //$NON-NLS-1$
		}

		String numChildStr = DBGpResponse.getAttribute(property, "numchildren"); //$NON-NLS-1$
		numChild = 0;
		if (numChildStr != null && numChildStr.trim().length() != 0) {
			try {
				numChild = Integer.parseInt(numChildStr);
			} catch (NumberFormatException nfe) {
				// do nothing
			}
		}
		String pageStr = null;
		String pageSizeStr = null;
		pageStr = DBGpResponse.getAttribute(property, "page"); //$NON-NLS-1$
		pageSizeStr = DBGpResponse.getAttribute(property, "pagesize"); //$NON-NLS-1$
		// TODO: currently getMaxChildren returns 0, may need to look into this.
		pageSize = ((DBGpTarget) getDebugTarget()).getMaxChildren();
		if (pageSizeStr != null && pageSizeStr.trim().length() != 0) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
			} catch (NumberFormatException nfe) {
				// do nothing
			}
		}

		page = -1;
		if (pageStr != null && pageStr.trim().length() != 0) {
			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException nfe) {
				// do nothing
			}
		}
		if (page == -1 && numChild > pageSize) {
			// Assume the xdebug defect and set page = 0 to force container
			// control
			page = 0;
		}

		// TODO: Improvement: nApplyCount > 1 in the zend hash table for an
		// array or object
		// String recursiveStr = DBGpResponse.getAttribute(property,
		// "recursive");

		// xdebug in 2.1 now outputs the page and pagesize attribute even if
		// there is only a single page, ie page will never be -1 now. in
		// xdebug 2.0.5, page will only be -1 if numChild <= pagesize, so we
		// check for that (instead of page == -1) now which provides the
		// required behaviour of no [x..y] type format.
		if (numChild <= pageSize || getOwner() instanceof DBGpContainerVariable) {
			// we have a full set of entries or we have a complete subset within
			// a ContainerVariable
			// create a standard child entries that show the variables and their
			// values
			NodeList childProperties = property.getChildNodes();
			int childrenReceived = childProperties.getLength();
			if (childrenReceived > 0) {
				childVariables = new DBGpVariable[childrenReceived];
				for (int i = 0; i < childrenReceived; i++) {
					Node childProperty = childProperties.item(i);
					childVariables[i] = new DBGpVariable(
							(DBGpTarget) getDebugTarget(), childProperty,
							getOwner().getStackLevel());
				}
			}
		} else {

			// create container variables to handle a complete subset
			// and that show their name as a container [x...y] in the debug view
			int subCount = roundUp((double) numChild / (double) pageSize);
			childVariables = new DBGpContainerVariable[subCount];

			// // we can populate the 1st one, we assume page=0 here.
			// // TODO: xdebug appears to do strange things on the watch
			// expression. For example
			// // if I have expanded $a (array of 201 elements) then a watch
			// doesn't get page 0, it could get page 4 for example.
			// childVariables[0] = new DBGpContainerVariable(getDebugTarget(),
			// page, pageSize, numChild, property, getOwner().getStackLevel(),
			// getOwner().getFullName());
			// for (int i = 1; i < subCount; i++) {
			// childVariables[i] = new DBGpContainerVariable(getDebugTarget(),
			// page + i, pageSize, numChild, null, getOwner().getStackLevel(),
			// getOwner().getFullName());
			// }

			for (int i = 0; i < subCount; i++) {
				if (i == page) {
					// we have data for this page so pass the property info to
					// the container
					childVariables[i] = new DBGpContainerVariable(
							getDebugTarget(), i, pageSize, numChild, property,
							getOwner().getStackLevel(), getOwner()
									.getFullName());
				} else {
					// we don't have data for this page so create a container
					// with no info, could be fetched later.
					childVariables[i] = new DBGpContainerVariable(
							getDebugTarget(), i, pageSize, numChild, null,
							getOwner().getStackLevel(), getOwner()
									.getFullName());
					// we copy the address from the original data as we could
					// use it if we don't
					// have a fullname from the variable, eg if an eval was
					// done.
					((DBGpContainerVariable) childVariables[i])
							.setAddress(getOwner().getAddress());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		if (containerType == IS_OBJECT) {
			return DBGpVariable.PHP_OBJECT;
		}
		return DBGpVariable.PHP_ARRAY;
	}

	void genValueString(String notUsed) {
		// only show an entry for a container value if we are not
		// in a container variable as this is a subset which already
		// has the relevant information being displayed from the container
		// variable
		if (!(getOwner() instanceof DBGpContainerVariable)) {
			if (containerType == IS_OBJECT) {
				if (className != null && className.trim().length() != 0) {
					setValueString(className);
				} else {
					// Unknown object type
					setValueString(PHPDebugCoreMessages.XDebug_DBGpContainerValue_0);
				}
			} else {
				setValueString("Array [" + numChild + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		if (childVariables == null) {
			requestValue();
			if (childVariables == null) {
				childVariables = new IVariable[0];
			}
		}
		return childVariables;
	}

	/**
	 * if we don't have any data for this container we need to request it.
	 */
	private void requestValue() {
		DBGpTarget target = (DBGpTarget) getDebugTarget();
		DBGpBaseVariable var = getOwner();
		int page = 0;
		if (var instanceof DBGpContainerVariable) {
			page = ((DBGpContainerVariable) var).getPage();
		}
		Node property = null;
		property = target.getProperty(var.getFullName(), var.getStackLevel(),
				page);

		if (property != null) {
			parseData(property);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.xdebug.core.dbgp.model.DBGpValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		// if childVariables is null, we assume we do have
		// some variables, it's just they need to be got.
		boolean hasVars = (childVariables == null || childVariables.length > 0);
		return hasVars;
	}

	/**
	 * simple roundup method
	 * 
	 * @param inVal
	 * @return
	 */
	private int roundUp(double inVal) {
		double fraction;
		double retVal;

		fraction = inVal % 1;
		retVal = inVal - fraction;

		if (fraction > 0) {
			retVal++;
		}
		return (int) retVal;
	}
}
