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

import java.text.MessageFormat;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.w3c.dom.Node;

/**
 * DBGp array value.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpArrayValue extends AbstractDBGpContainerValue {

	private static final String NAME_FORMAT = "Array [{0}]"; //$NON-NLS-1$

	/**
	 * Creates new DBGp array value.
	 * 
	 * @param owner
	 */
	public DBGpArrayValue(DBGpVariable owner) {
		super(owner);
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
		String numChildStr = DBGpResponse.getAttribute(fDescriptor, "numchildren"); //$NON-NLS-1$
		return MessageFormat.format(NAME_FORMAT, numChildStr);
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
				Facet.KIND_ARRAY_MEMBER);
	}

	protected IVariable createEvalVariable(Node descriptor) {
		DBGpVariable variable = new DBGpEvalVariable((DBGpTarget) getDebugTarget(), descriptor,
				Facet.KIND_ARRAY_MEMBER);
		String arrayKey = variable.fName.substring(1, variable.fName.length() - 1);
		if (!arrayKey.matches("\\d+")) { //$NON-NLS-1$
			arrayKey = '\'' + arrayKey + '\'';
		}
		variable.fFullName = getOwner().getFullName() + '[' + arrayKey + ']';
		return variable;
	}

	public String getValueDetail() throws DebugException {
		StringBuffer result = new StringBuffer("["); //$NON-NLS-1$
		IVariable[] variables = getVariables();
		for (int i = 0; i < variables.length; i++) {
			IVariable child = variables[i];
			if (i > 0) {
				result.append(","); //$NON-NLS-1$
				result.append(" "); //$NON-NLS-1$
			}
			result.append(child.getName());
			result.append(" => "); //$NON-NLS-1$
			if (child.getValue() instanceof AbstractDBGpValue) {
				result.append(((AbstractDBGpValue) child.getValue()).getValueDetail());
			} else {
				result.append(child.getValue().getValueString());
			}
		}
		result.append("]"); //$NON-NLS-1$
		return result.toString();
	}

}
