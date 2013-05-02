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
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.w3c.dom.Node;

public class DBGpContainerVariable extends DBGpBaseVariable implements
		IVariable {

	private int page;
	private int pageSize;
	private int numChildren;
	private DBGpContainerValue value;

	public DBGpContainerVariable(IDebugTarget target, int page, int pageSize,
			int numChildren, Node property, String stackLevel, String fullName) {
		super(target, stackLevel, fullName);
		this.page = page;
		this.pageSize = pageSize;
		this.numChildren = numChildren;
		if (property != null) {
			value = new DBGpContainerValue(this, property);
		} else {
			value = new DBGpContainerValue(this, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	public String getName() throws DebugException {
		int startIndex = page * pageSize;
		int endIndex = (page + 1) * pageSize - 1;
		if (endIndex > numChildren) {
			endIndex = numChildren - 1;
		}

		return "[" + startIndex + "..." + endIndex + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return "Container"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	public IValue getValue() throws DebugException {
		return value;
	}

	/**
	 * return the page reference
	 * 
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * return the page size
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
}
