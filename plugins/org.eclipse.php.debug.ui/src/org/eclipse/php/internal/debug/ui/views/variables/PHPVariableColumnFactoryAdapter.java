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

import org.eclipse.debug.internal.ui.elements.adapters.VariableColumnFactoryAdapter;
import org.eclipse.debug.internal.ui.viewers.provisional.IColumnEditor;
import org.eclipse.debug.internal.ui.viewers.provisional.IPresentationContext;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.php.internal.debug.core.model.PHPVariable;

/**
 * A PHPVariableColumnFactoryAdapter which overrides the default VariableColumnFactoryAdapter to provide special
 * PHPVariable colum editor.
 * 
 * @author shalom
 */
public class PHPVariableColumnFactoryAdapter extends VariableColumnFactoryAdapter {

	/**
	 * Override the default createColumnEditor to handle the creation of editors for PHPVariables.
	 */
	public IColumnEditor createColumnEditor(IPresentationContext context, Object element) {
		String id = context.getPart().getSite().getId();
		if (IDebugUIConstants.ID_VARIABLE_VIEW.equals(id) || IDebugUIConstants.ID_REGISTER_VIEW.equals(id)) {
			if (element instanceof PHPVariable) {
				return new PHPVariableColumnEditor();
			}
		}
		return super.createColumnEditor(context, element);
	}

}
