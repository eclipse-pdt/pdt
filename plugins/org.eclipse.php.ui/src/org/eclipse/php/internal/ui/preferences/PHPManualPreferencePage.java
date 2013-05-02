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

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

public class PHPManualPreferencePage extends
		AbstractConfigurationBlockPreferencePage {

	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new PHPManualConfigurationBlock(this, overlayPreferenceStore);
	}

	protected String getHelpId() {
		return IPHPHelpContextIds.PHP_MANUAL_PREFERENCES;
	}

	protected void setDescription() {
		setDescription(PHPUIMessages.PHPManualPreferencePage_0); 
	}

	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}
}
