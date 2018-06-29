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
package org.eclipse.php.internal.server.ui;

import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.php.internal.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPServersPreferencePage extends AbstractConfigurationBlockPreferencePage {

	public PHPServersPreferencePage() {
		noDefaultAndApplyButton();
	}

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		return new PHPServersConfigurationBlock(this, overlayPreferenceStore);
	}

	@Override
	protected String getHelpId() {
		// HELP
		return IPHPHelpContextIds.PHP_SERVERS_PREFERENCES;
	}

	@Override
	protected void setDescription() {
		setDescription(PHPServerUIMessages.getString("PHPServersPreferencePage.removeLaunchNote")); //$NON-NLS-1$
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
	}
}
