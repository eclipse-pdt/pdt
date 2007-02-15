/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.variables;

import org.eclipse.debug.internal.ui.elements.adapters.VariableColumnEditor;
import org.eclipse.jface.viewers.ICellModifier;

/**
 * PHPVariableColumnEditor that returns PHPVariableCellModifiers.
 * 
 * @author shalom
 */
public class PHPVariableColumnEditor extends VariableColumnEditor {

	private ICellModifier fCellModifier;

	public ICellModifier getCellModifier() {
		if (fCellModifier == null) {
			fCellModifier = new PHPVariableCellModifier(getPresentationContext());
		}
		return fCellModifier;
	}

}
