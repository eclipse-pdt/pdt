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

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.internal.debug.core.zend.debugger.parameters.DefaultDebugParametersInitializer;

/**
 * Zend profiler parameters initializer.
 */
public class ProfileParametersInitializer extends DefaultDebugParametersInitializer {

	@Override
	public Hashtable<String, String> getDebugParameters(ILaunch launch) {
		// Fill standard parameters
		Hashtable<String, String> parameters = super.getDebugParameters(launch);
		parameters.put(START_PROFILE, "1"); //$NON-NLS-1$
		return parameters;
	}
}
