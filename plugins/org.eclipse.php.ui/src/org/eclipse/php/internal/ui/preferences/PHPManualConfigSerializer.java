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
package org.eclipse.php.internal.ui.preferences;

import java.io.File;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.ui.util.PHPManualSiteDescriptor;

/**
 * @author seva Serializes and unserializes PHPManualConfig using
 *         {@link PHPManualConfigurationBlock#PREFERENCES_DELIMITER}
 */
public class PHPManualConfigSerializer {

	private static final String INSTALL_AREA_PROP = "@osgi.install.area"; //$NON-NLS-1$

	public static String toString(PHPManualConfig config) {
		return config.getLabel()
				+ PHPManualConfigurationBlock.PREFERENCES_DELIMITER
				+ config.getUrl()
				+ PHPManualConfigurationBlock.PREFERENCES_DELIMITER
				+ config.getExtension();
	}

	public static PHPManualConfig fromStringTokenizer(StringTokenizer tokenizer) {
		String url = ""; //$NON-NLS-1$
		String name = PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_LABEL;
		String extension = PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_EXTENSION;

		// No tokens - abort
		if (!tokenizer.hasMoreTokens()) {
			return null;
		}
		name = tokenizer.nextToken();
		if (tokenizer.hasMoreTokens()) {
			url = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				extension = tokenizer.nextToken();
			}
		} else {
			// Just 1 token - it's an URL
			url = name;
			name = ""; //$NON-NLS-1$
		}

		int idx = url.indexOf(INSTALL_AREA_PROP);
		if (idx != -1) {
			String platformLocation = new File(Platform.getInstallLocation()
					.getURL().getPath()).getPath();
			if (platformLocation.endsWith("/")) { //$NON-NLS-1$
				platformLocation = platformLocation.substring(0,
						platformLocation.length() - 1);
			}
			url = "file://" + new File(url.substring(0, idx) + platformLocation + url.substring(idx + INSTALL_AREA_PROP.length())).getAbsolutePath(); //$NON-NLS-1$
		}

		return new PHPManualConfig(name, url, extension, false);
	}

	public static PHPManualConfig fromString(String string) {
		if (string != null && !"".equals(string)) { //$NON-NLS-1$
			return fromStringTokenizer(new StringTokenizer(string,
					PHPManualConfigurationBlock.PREFERENCES_DELIMITER));
		}
		return null;
	}
}