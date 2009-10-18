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
package org.eclipse.php.internal.debug.ui.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.IDBGpModelConstants;

public class DebugViewHelper {

	public IPHPDebugTarget getSelectionElement(ISelection selection) {
		IDebugElement element = getAdaptableElement();
		if (element == null) {
			if (selection != null) {
				if (selection instanceof StructuredSelection) {
					StructuredSelection sSelection = (StructuredSelection) selection;
					if (!sSelection.isEmpty()) {
						Object first = sSelection.getFirstElement();
						if (first instanceof IDebugElement)
							element = (IDebugElement) first;
					}
				}
			}
		}
		IPHPDebugTarget target = getDebugTarget(element);
		// If target is null try to get target from the last debug process to
		// run
		if (target == null) {
			IProcess process = DebugUITools.getCurrentProcess();
			if (process != null) {
				if (process instanceof PHPProcess) {
					target = (IPHPDebugTarget) ((PHPProcess) process)
							.getDebugTarget();
				}
			}
		}

		return target;
	}

	private IDebugElement getAdaptableElement() {
		IDebugElement element = null;
		IAdaptable adaptable = DebugUITools.getDebugContext();
		if (adaptable != null) {
			element = (IDebugElement) adaptable.getAdapter(IDebugElement.class);
		}
		if (element == null) {
			if (adaptable instanceof PHPProcess) {
				element = (IDebugElement) ((PHPProcess) adaptable)
						.getDebugTarget();
			} else if (adaptable instanceof ILaunch) {
				IDebugTarget[] targets = ((ILaunch) (adaptable))
						.getDebugTargets();
				for (int i = 0; i < targets.length; i++) {
					if (targets[i] instanceof IPHPDebugTarget) {
						element = (IDebugElement) targets[i];
					}
				}
			}
		}
		return element;
	}

	private IPHPDebugTarget getDebugTarget(IDebugElement element) {
		IPHPDebugTarget target = null;
		if (element != null) {
			if (element.getModelIdentifier().equals(
					IPHPDebugConstants.ID_PHP_DEBUG_CORE)
					|| element.getModelIdentifier().equals(
							IDBGpModelConstants.DBGP_MODEL_ID)) {
				target = (IPHPDebugTarget) element.getDebugTarget();
			}
		}
		return target;
	}

}
