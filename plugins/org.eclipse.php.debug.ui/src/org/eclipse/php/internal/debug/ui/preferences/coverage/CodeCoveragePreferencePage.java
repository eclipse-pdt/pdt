/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.coverage;

import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.php.internal.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

/**
 * Code coverage preference page.
 */
public class CodeCoveragePreferencePage extends AbstractConfigurationBlockPreferencePage {

	public CodeCoveragePreferencePage() {
		noDefaultAndApplyButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.ui.
	 * AbstractConfigurationBlockPreferencePage
	 * #createConfigurationBlock(org.eclipse
	 * .wst.sse.ui.internal.preferences.OverlayPreferenceStore)
	 */
	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		return new CodeCoverageConfigurationBlock(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.ui.
	 * AbstractConfigurationBlockPreferencePage#getHelpId()
	 */
	@Override
	protected String getHelpId() {
		return IPHPHelpContextIds.CODE_COVERAGE_PREFERENCES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.ui.
	 * AbstractConfigurationBlockPreferencePage#setDescription()
	 */
	@Override
	protected void setDescription() {
		setDescription(PHPDebugUIMessages.CodeCoveragePreferencePage_0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.ui.
	 * AbstractConfigurationBlockPreferencePage#setPreferenceStore()
	 */
	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(PHPDebugUIPlugin.getDefault().getPreferenceStore());
	}
}
