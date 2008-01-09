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
package org.eclipse.php.internal.core.preferences;

import org.eclipse.php.internal.core.PHPCorePlugin;

public class CorePreferencesSupport extends PreferencesSupport {

	private static CorePreferencesSupport corePreferencesSupport;

	private CorePreferencesSupport() {
		super(PHPCorePlugin.ID, PHPCorePlugin.getDefault().getPluginPreferences());
	}


	public static CorePreferencesSupport getInstance() {
		if (corePreferencesSupport == null) {
			corePreferencesSupport = new CorePreferencesSupport();
		}

		return corePreferencesSupport;
	}
}
