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
package org.eclipse.php.internal.debug.ui.views.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.internal.ui.elements.adapters.DefaultVariableCellModifier;
import org.eclipse.debug.internal.ui.elements.adapters.VariableColumnPresentation;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.php.internal.debug.core.zend.model.PHPValue;
import org.eclipse.php.internal.debug.core.zend.model.PHPVariable;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;

/**
 * PHPVariableCellModifier is the cell modifier for the PHPVariables.
 * 
 * @author shalom
 * 
 */
public class PHPVariableCellModifier extends DefaultVariableCellModifier implements ICellModifier {

	/**
	 * Overrides the default cell modifier to support PHPVariables. (The method
	 * returns the PHPValue.getValue() instead of getValueString()).
	 */
	@Override
	public Object getValue(Object element, String property) {
		if (VariableColumnPresentation.COLUMN_VARIABLE_VALUE.equals(property)) {
			if (element instanceof PHPVariable) {
				PHPVariable variable = (PHPVariable) element;
				try {
					if (variable.getValue() instanceof PHPValue) {
						return ((PHPValue) variable.getValue()).getValue();
					}
					return variable.getValue().getValueString();
				} catch (DebugException e) {
					PHPDebugUIPlugin.log(e);
				}
			}
		}
		return null;
	}

}
