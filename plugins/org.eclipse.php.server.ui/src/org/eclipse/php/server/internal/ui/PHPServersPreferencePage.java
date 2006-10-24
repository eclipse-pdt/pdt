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
package org.eclipse.php.server.internal.ui;

import org.eclipse.php.server.PHPServerUIMessages;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.ui.AbstractConfigurationBlockPreferencePage;
import org.eclipse.php.ui.preferences.ui.IPreferenceConfigurationBlock;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPServersPreferencePage extends AbstractConfigurationBlockPreferencePage {

	public PHPServersPreferencePage() {
		noDefaultAndApplyButton();
	}

	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		return new PHPServersConfigurationBlock(this, overlayPreferenceStore);
	}

	protected String getHelpId() {
		return null;
	}

	protected void setDescription() {
		setDescription(PHPServerUIMessages.getString("PHPServersPreferencePage.removeLaunchNote")); //$NON-NLS-1$
	}

	protected void setPreferenceStore() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
	}
}
