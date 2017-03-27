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

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet;
import org.eclipse.php.internal.debug.core.model.VariablesUtil;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

/**
 * DBGp object value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpObjectValue extends AbstractDBGpContainerValue {

	/**
	 * Creates new DBGp object value.
	 * 
	 * @param owner
	 */
	public DBGpObjectValue(DBGpVariable owner) {
		super(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.
	 * AbstractDBGpContainerValue#fetchVariables()
	 */
	@Override
	protected void fetchVariables() {
		super.fetchVariables();
		// Sort members by name
		VariablesUtil.sortObjectMembers(fCurrentVariables);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.xdebug.dbgp.model.
	 * AbstractDBGpContainerValue#createVariable(org.w3c.dom.Node)
	 */
	@Override
	protected IVariable createVariable(Node descriptor) {
		switch (getOwner().getKind()) {
		case EVAL: {
			return createEvalVariable(descriptor);
		}
		default:
			return createStackVariable(descriptor);
		}
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
		return DBGpResponse.getAttribute(fDescriptor, "classname"); //$NON-NLS-1$
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
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.xdebug.dbgp.model.AbstractDBGpValue#
	 * verifyValue(java.lang.String)
	 */
	@Override
	protected boolean verifyValue(String expression) {
		return false;
	}

	protected IVariable createStackVariable(Node descriptor) {
		return new DBGpStackVariable((DBGpTarget) getDebugTarget(), descriptor, getOwner().getStackLevel(),
				Facet.KIND_OBJECT_MEMBER);
	}

	protected IVariable createEvalVariable(Node descriptor) {
		DBGpVariable variable = new DBGpEvalVariable((DBGpTarget) getDebugTarget(), descriptor,
				Facet.KIND_OBJECT_MEMBER);
		variable.fFullName = getOwner().getFullName() + "->" + variable.fName; //$NON-NLS-1$
		return variable;
	}

	public String getValueDetail() throws DebugException {
		String toString = this.fOwner.getFullName() + "->__toString()"; //$NON-NLS-1$
		Node resp = ((DBGpTarget) fOwner.getDebugTarget()).eval(toString);
		if (resp == null) {
			return super.getValueDetail();
		}
		DBGpEvalVariable variable = new DBGpEvalVariable(fOwner.getDebugTarget(), toString, resp);
		return variable.getValue().getValueString();
	}

}
