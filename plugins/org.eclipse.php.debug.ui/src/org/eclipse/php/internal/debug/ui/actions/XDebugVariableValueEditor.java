/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

/**
 * A variable value editor that prompts the user to set a primitive's value.
 */
public class XDebugVariableValueEditor extends AbstractVariableValueEditor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.actions.AbstractVariableValueEditor#
	 * getValueString(org.eclipse.debug.core.model.IVariable)
	 */
	@Override
	protected String getValueString(IVariable variable) throws DebugException {
		return variable.getValue().getValueString();
	}

}
