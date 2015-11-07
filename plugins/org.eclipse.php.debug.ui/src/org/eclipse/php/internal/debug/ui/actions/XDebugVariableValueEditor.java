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
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStringValue;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpVariable;
import org.w3c.dom.Node;

/**
 * A variable value editor that prompts the user to set a primitive's value.
 */
@SuppressWarnings("restriction")
public class XDebugVariableValueEditor extends AbstractVariableValueEditor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.actions.AbstractVariableValueEditor#
	 * getValueString(org.eclipse.debug.core.model.IVariable)
	 */
	protected String getValueString(IVariable variable) throws DebugException {
		IValue value = variable.getValue();
		String initialValue = value.getValueString();

		if (value instanceof DBGpStringValue) {
			DBGpStringValue strValue = (DBGpStringValue) value;
			if (strValue.isComplete() == false) {
				DBGpTarget target = (DBGpTarget) value.getDebugTarget();
				DBGpVariable dbgpVar = (DBGpVariable) variable;
				String stackLevel = dbgpVar.getStackLevel();
				Node result = target.getCompleteString(dbgpVar.getFullName(), stackLevel, strValue.getRequiredBytes());
				if (result != null) {
					IVariable tempVar = new DBGpVariable(target, result, stackLevel);
					IValue valRes = null;
					try {
						valRes = tempVar.getValue();
						if (valRes != null) {
							// update the variable with the latest value.
							((DBGpVariable) variable).replaceValue(valRes);
							initialValue = valRes.getValueString();
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return initialValue;
	}

}
