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
package org.eclipse.php.internal.debug.ui.views.variables;

import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementEditor;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxyFactory;
import org.eclipse.debug.internal.ui.views.launch.DebugElementAdapterFactory;
import org.eclipse.php.internal.debug.core.launching.PHPLaunch;
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPVariable;
import org.eclipse.php.internal.debug.ui.model.PHPModelProxyFactory;

/**
 * The PHPDebugElementAdapterFactory is designed to replace the default
 * DebugElementAdapterFactory that is initialized by the DebugUIPlugin
 * 
 * @author shalom
 */
@SuppressWarnings("restriction")
public class PHPDebugElementAdapterFactory extends DebugElementAdapterFactory {

	private static IElementEditor fElementEditor = new PHPVariableColumnEditor();
	private static IModelProxyFactory fgFactory = new PHPModelProxyFactory();

	/**
	 * Override the default one to provide the PHP specific adapters.
	 */
	public Object getAdapter(Object adaptableObject,
			@SuppressWarnings("rawtypes") Class adapterType) {
		if (adapterType.equals(IElementEditor.class)) {
			if (adaptableObject instanceof PHPVariable) {
				return fElementEditor;
			}
		}
		if (adapterType.equals(IModelProxyFactory.class)) {
			if (adaptableObject instanceof PHPMultiDebugTarget)
				return fgFactory;
			if (adaptableObject instanceof PHPLaunch)
				return fgFactory;
		}
		return super.getAdapter(adaptableObject, adapterType);
	}

}
