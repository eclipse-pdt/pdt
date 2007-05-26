/**
 * Copyright (c) 2007 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.preferences;

import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.ui.util.PHPManualSiteDescriptor;

/**
 * @author seva
 * Serializes and unserializes PHPManualConfig using {@link PHPManualConfigurationBlock#PREFERENCES_DELIMITER}  
 */
public class PHPManualConfigSerializer {
	
	private static final String INSTALL_AREA_PROP = "@osgi.install.area";
	
	public static String toString(PHPManualConfig config) {
		return config.getLabel() + PHPManualConfigurationBlock.PREFERENCES_DELIMITER + config.getUrl() + PHPManualConfigurationBlock.PREFERENCES_DELIMITER + config.getExtension();
	}

	public static PHPManualConfig fromStringTokenizer(StringTokenizer tokenizer) {
		String url = "";
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
			name = "";
		}
		
		int idx = url.indexOf(INSTALL_AREA_PROP);
		if (idx != -1) {
			String platformLocation = Platform.getInstallLocation().getURL().toExternalForm();
			if (platformLocation.endsWith("/")) {
				platformLocation = platformLocation.substring(0, platformLocation.length() - 1);
			}
			url = url.substring(0, idx) + platformLocation + url.substring(idx + INSTALL_AREA_PROP.length());
		}
		
		return new PHPManualConfig(name, url, extension, false);
	}

	public static PHPManualConfig fromString(String string) {
		if (string != null && !"".equals(string)) {
			return fromStringTokenizer(new StringTokenizer(string, PHPManualConfigurationBlock.PREFERENCES_DELIMITER));
		}
		return null;
	}
}