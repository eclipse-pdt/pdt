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
package org.eclipse.php.internal.server.ui;

import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.php.internal.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPServersPreferencePage extends
		AbstractConfigurationBlockPreferencePage {

	public PHPServersPreferencePage() {
		noDefaultAndApplyButton();
	}

	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new PHPServersConfigurationBlock(this, overlayPreferenceStore);
	}

	protected String getHelpId() {
		// HELP
		return IPHPHelpContextIds.PHP_SERVERS_PREFERENCES;
	}

	protected void setDescription() {
		setDescription(PHPServerUIMessages
				.getString("PHPServersPreferencePage.removeLaunchNote")); //$NON-NLS-1$
	}

	protected void setPreferenceStore() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
	}
}
