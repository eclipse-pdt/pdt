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
package org.eclipse.php.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Assert;
import org.eclipse.php.internal.ui.preferences.PHPManualConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.PHPManualConfigurationBlock.PHPManualConfig;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.PreferenceConstants;

public class PHPManualFactory {
	
	private static PHPManual manual = null;
	private static String fStoredSite;
	
	private static PHPManual createManual(PHPManualSiteDescriptor desc) {
		PHPManualSite site = desc.createSite();
		site.setDirector(getDirector(desc.getDirectorID()));
		return new PHPManual (site);
	}
	
	private static PHPManual createManual(PHPManualConfig config) {
		PHPManualSite site = new PHPManualSite(config.getUrl(), config.getExtension());
		site.setDirector(getDirector(PHPManualSiteDescriptor.DEFAULT_PHP_MANUAL_DIRECTOR));
		return new PHPManual (site);
	}
	
	public static PHPManual getManual() {
		String storedSite = PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.PHP_MANUAL_SITE);
		if (manual == null || !fStoredSite.equals(storedSite)) {
			manual = null;
			fStoredSite = storedSite;
			
			PHPManualSiteDescriptor[] descs = PHPUiPlugin.getDefault().getPHPManualSiteDescriptors();
			for (int i=0; i < descs.length; ++i) {
				if (storedSite != null && storedSite.equals(descs[i].getURL())) {
					manual = createManual(descs[i]);
					break;
				}
			}
			
			if (manual == null) {
				List configs = new ArrayList();
				PHPManualConfigurationBlock.initFromPreferences(PreferenceConstants.getPreferenceStore(), configs);
				if (configs.size() > 0) {
					PHPManualConfig defaultConfig = PHPManualConfigurationBlock.getActiveManualSite(PreferenceConstants.getPreferenceStore(), configs);
					if (defaultConfig != null) {
						manual = createManual(defaultConfig);
					} else {
						manual = createManual((PHPManualConfig)configs.get(0));
					}
				}
				else if (descs.length > 0) {
					manual = createManual(descs[0]);
				}
			}
		}
		Assert.isNotNull(manual);
		return manual;
	}
	
	private static PHPManualDirector getDirector (String id) {
		PHPManualDirector director = null;
		PHPManualDirectorDescriptor[] descs = PHPUiPlugin.getDefault().getPHPManualDirectorDescriptors();
		for (int i=0; i < descs.length; ++i) {
			if (descs[i].getID().equals(id)) {
				director = descs[i].createDirector();
			}
		}
		Assert.isNotNull (director);
		return director;
	}
}
