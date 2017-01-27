/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.preferences;

import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;

/**
 * Ensures that the Launcher knows that the user has set a PHP executable and
 * avoid additional error dialogs.
 *
 */
@SuppressWarnings({ "deprecation" })
public class PHPExecutableChangeListener implements IPropertyChangeListener {

	private final String bundleId;
	private final String executableKey;

	public PHPExecutableChangeListener(String bundleID, String executableKey) {
		this.bundleId = bundleID;
		this.executableKey = executableKey;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {

		if (!"org.eclipse.php.debug.coreinstalledPHPDefaults".equals(event.getProperty())) { //$NON-NLS-1$
			return;
		}

		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(bundleId);
		String executable = preferences.get(executableKey, null);

		if (executable != null && executable.length() > 0) {
			return;
		}

		try {
			PHPexeItem[] exes = PHPexes.getInstance().getAllItems();
			if (exes.length == 1) {
				Logger.debug("PHP executable changed, setting store value for " + bundleId + " to " + executableKey //$NON-NLS-1$ //$NON-NLS-2$
						+ " (" + exes[0].getExecutable().toString() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				preferences.put(executableKey, exes[0].getExecutable().toString());
				preferences.flush();

				// we don't need it anymore now
				PHPDebugPlugin.getDefault().getPluginPreferences().removePropertyChangeListener(this);
			}

		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
