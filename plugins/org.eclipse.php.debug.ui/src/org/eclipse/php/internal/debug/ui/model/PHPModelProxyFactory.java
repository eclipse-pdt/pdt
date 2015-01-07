/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;

/**
 * PHP model proxy factory.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class PHPModelProxyFactory implements IModelProxyFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxyFactory
	 * #createModelProxy(java.lang.Object,
	 * org.eclipse.debug.internal.ui.viewers.
	 * model.provisional.IPresentationContext)
	 */
	@Override
	public IModelProxy createModelProxy(Object element,
			IPresentationContext context) {
		// Hook the Debug view
		if (IDebugUIConstants.ID_DEBUG_VIEW.equals(context.getId())) {
			if (element instanceof PHPMultiDebugTarget) {
				return new PHPMultiDebugTargetProxy(
						((PHPMultiDebugTarget) element));
			}
			if (element instanceof PHPLaunch) {
				return new PHPLaunchProxy((PHPLaunch) element);
			}
		}
		return null;
	}

}
