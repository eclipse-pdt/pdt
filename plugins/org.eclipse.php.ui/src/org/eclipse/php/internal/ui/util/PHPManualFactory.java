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
package org.eclipse.php.internal.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PHPManualConfig;
import org.eclipse.php.internal.ui.preferences.PHPManualConfigSerializer;
import org.eclipse.php.internal.ui.preferences.PHPManualConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;

public class PHPManualFactory {

	private static PHPManual manual = null;
	private static PHPManualConfig fStoredConfig;

	private static PHPManual createManual(PHPManualSiteDescriptor desc) {
		PHPManualSite site = desc.createSite();
		return new PHPManual(site);
	}

	private static PHPManual createManual(PHPManualConfig config) {
		PHPManualSite site = new PHPManualSite(config.getUrl(), config
				.getExtension());
		return new PHPManual(site);
	}

	public static PHPManual getManual() {
		String storedConfigString = PreferenceConstants.getPreferenceStore()
				.getString(PreferenceConstants.PHP_MANUAL_SITE);
		PHPManualConfig storedConfig = PHPManualConfigSerializer
				.fromString(storedConfigString);
		if (manual == null || !fStoredConfig.equals(storedConfigString)) {
			manual = null;
			fStoredConfig = storedConfig;

			PHPManualSiteDescriptor[] descs = PHPUiPlugin.getDefault()
					.getPHPManualSiteDescriptors();
			for (int i = 0; i < descs.length; ++i) {
				// here we don't check anything except the URL since we are
				// using a predefined sites with fixed extensions.
				if (storedConfig != null
						&& storedConfig.getUrl().equals(descs[i].getURL())) {
					manual = createManual(descs[i]);
					break;
				}
			}

			if (manual == null) {
				List configs = new ArrayList();
				PHPManualConfigurationBlock.initFromPreferences(
						PreferenceConstants.getPreferenceStore(), configs);
				if (configs.size() > 0) {
					PHPManualConfig defaultConfig = PHPManualConfigurationBlock
							.getActiveManualSite(PreferenceConstants
									.getPreferenceStore(), configs);
					if (defaultConfig != null) {
						manual = createManual(defaultConfig);
					} else {
						manual = createManual((PHPManualConfig) configs.get(0));
					}
				} else if (descs.length > 0) {
					manual = createManual(descs[0]);
				}
			}
		}
		Assert.isNotNull(manual);
		return manual;
	}
}
