/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.launching.XDebugLaunch;
import org.eclipse.php.internal.debug.core.zend.debugger.parameters.DefaultDebugParametersInitializer;

/**
 * Zend and xdebug profiler parameters initializer.
 */
public class ProfileParametersInitializer extends DefaultDebugParametersInitializer {

	@Override
	public Hashtable<String, String> getDebugParameters(ILaunch launch) {
		Hashtable<String, String> parameters;
		if (launch instanceof XDebugLaunch) {
			parameters = new Hashtable<>();
			if (launch.getLaunchMode().equals(ILaunchManager.PROFILE_MODE)) {
				try {
					if (launch.getLaunchConfiguration().getAttribute(IPHPDebugConstants.XDEBUG_PROFILE_TRIGGER, true)) {
						parameters.put("XDEBUG_PROFILE", launch.getLaunchConfiguration() //$NON-NLS-1$
								.getAttribute(IPHPDebugConstants.XDEBUG_PROFILE_TRIGGER_VALUE, "")); //$NON-NLS-1$
					}
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
			return parameters;
		} else {
			parameters = super.getDebugParameters(launch);
			parameters.put(START_PROFILE, "1"); //$NON-NLS-1$
		}
		return parameters;
	}
}
