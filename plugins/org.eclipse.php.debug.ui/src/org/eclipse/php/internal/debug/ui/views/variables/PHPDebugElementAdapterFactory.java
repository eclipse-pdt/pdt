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

import org.eclipse.debug.core.model.IWatchExpression;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementEditor;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxyFactory;
import org.eclipse.debug.internal.ui.views.launch.DebugElementAdapterFactory;
import org.eclipse.php.internal.debug.core.launching.PHPLaunch;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpMultiSessionTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpVariable;
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPVariable;
import org.eclipse.php.internal.debug.ui.model.PHPModelProxyFactory;

/**
 * The PHPDebugElementAdapterFactory is designed to replace the default
 * DebugElementAdapterFactory that is initialized by the DebugUIPlugin
 * 
 * @author shalom
 */
public class PHPDebugElementAdapterFactory extends DebugElementAdapterFactory {

	private static IElementEditor fElementEditor = new PHPVariableColumnEditor();
	private static IModelProxyFactory fgModelFactory = new PHPModelProxyFactory();
	private static IElementLabelProvider fgLPVariable = new PHPVariableLabelProvider();
	private static IElementLabelProvider fgLPExpression = new PHPExpressionLabelProvider();

	/**
	 * Override the default one to provide the PHP specific adapters.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType.equals(IElementEditor.class)) {
			if (adaptableObject instanceof PHPVariable) {
				return (T) fElementEditor;
			}
		}
		if (adapterType.equals(IModelProxyFactory.class)) {
			if (adaptableObject instanceof PHPLaunch || adaptableObject instanceof PHPMultiDebugTarget
					|| adaptableObject instanceof DBGpMultiSessionTarget) {
				return (T) fgModelFactory;
			}
		}
		if (adapterType.equals(IElementLabelProvider.class)) {
			if (adaptableObject instanceof DBGpVariable) {
				return (T) fgLPVariable;
			}
			if (adaptableObject instanceof IWatchExpression) {
				return (T) fgLPExpression;
			}
		}
		return super.getAdapter(adaptableObject, adapterType);
	}

}
