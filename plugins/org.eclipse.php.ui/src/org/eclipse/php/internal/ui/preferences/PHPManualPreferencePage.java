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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPManualPreferencePage extends AbstractConfigurationBlockPreferencePage {

	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		return new PHPManualConfigurationBlock(this, overlayPreferenceStore);
	}

	protected String getHelpId() {
		return IPHPHelpContextIds.PHP_MANUAL_PREFERENCE_PAGE;
	}

	protected void setDescription() {
		setDescription("PHP Manual Sites");
	}

	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}
}
