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
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.zend.model.PHPValue;

/**
 * A variable value editor that prompts the user to set a primitive's value.
 */
@SuppressWarnings("restriction")
public class PHPVariableValueEditor extends AbstractVariableValueEditor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.actions.AbstractVariableValueEditor#
	 * getValueString(org.eclipse.debug.core.model.IVariable)
	 */
	@Override
	protected String getValueString(IVariable variable) throws DebugException {
		return ((PHPValue) variable.getValue()).getValue();
	}

}
