/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.model;

import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxy;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxyFactory;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.php.internal.debug.core.launching.PHPLaunch;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpMultiSessionTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;

/**
 * PHP model proxy factory.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPModelProxyFactory implements IModelProxyFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.model.provisional.
	 * IModelProxyFactory #createModelProxy(java.lang.Object,
	 * org.eclipse.debug.internal.ui.viewers.
	 * model.provisional.IPresentationContext)
	 */
	@Override
	public IModelProxy createModelProxy(Object element, IPresentationContext context) {
		// Hook the Debug view
		if (IDebugUIConstants.ID_DEBUG_VIEW.equals(context.getId())) {
			if (element instanceof PHPLaunch) {
				return new PHPLaunchProxy((PHPLaunch) element);
			}
			if (element instanceof PHPMultiDebugTarget) {
				return new PHPMultiDebugTargetProxy(((PHPMultiDebugTarget) element));
			}
			if (element instanceof DBGpMultiSessionTarget) {
				return new PHPMultiDebugTargetProxy(((DBGpMultiSessionTarget) element));
			}
		}
		return null;
	}

}
