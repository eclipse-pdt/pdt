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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.viewers.provisional.IColumnEditorFactoryAdapter;
import org.eclipse.debug.internal.ui.viewers.provisional.IColumnPresentationFactoryAdapter;
import org.eclipse.debug.internal.ui.views.launch.DebugElementAdapterFactory;
import org.eclipse.php.internal.debug.core.model.PHPVariable;

/**
 * The PHPDebugElementAdapterFactory is designed to replace the default DebugElementAdapterFactory that is initialized
 * by the DebugUIPlugin
 * @author shalom
 */
public class PHPDebugElementAdapterFactory extends DebugElementAdapterFactory {

	private static IColumnPresentationFactoryAdapter fgVariableColumnFactory = new PHPVariableColumnFactoryAdapter();

	/**
	 * Override the default getAdapter to provide PHPVariable special variable column factory adapter.
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType.equals(IColumnEditorFactoryAdapter.class)) {
			if (adaptableObject instanceof PHPVariable) {
				return fgVariableColumnFactory;
			}
		}
		return super.getAdapter(adaptableObject, adapterType);
	}
}
