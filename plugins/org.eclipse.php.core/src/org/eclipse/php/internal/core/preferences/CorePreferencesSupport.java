/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.preferences;

import org.eclipse.php.internal.core.PHPCorePlugin;

public class CorePreferencesSupport extends PreferencesSupport {

	private static CorePreferencesSupport corePreferencesSupport;

	private CorePreferencesSupport() {
		super(PHPCorePlugin.ID);
	}

	public static CorePreferencesSupport getInstance() {
		if (corePreferencesSupport == null) {
			corePreferencesSupport = new CorePreferencesSupport();
		}

		return corePreferencesSupport;
	}
}
