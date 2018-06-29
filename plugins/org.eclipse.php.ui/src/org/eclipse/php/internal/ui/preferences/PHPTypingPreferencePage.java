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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

/**
 * 
 * @author guy.g
 * 
 */
public class PHPTypingPreferencePage extends AbstractConfigurationBlockPreferencePage {

	private TypingConfigurationBlock typingConfigurationBlock;

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		typingConfigurationBlock = new TypingConfigurationBlock(this, overlayPreferenceStore);
		return typingConfigurationBlock;
	}

	@Override
	protected String getHelpId() {
		return IPHPHelpContextIds.TYPING_PREFERENCES;
	}

	@Override
	protected void setDescription() {
		setDescription(""); //$NON-NLS-1$

	}

	@Override
	protected Label createDescriptionLabel(Composite parent) {
		return null;
	}

	/**
	 * This method overrides the default behavior in order to refresh values that
	 * are updated due to changes in other Preference pages
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			typingConfigurationBlock.refreshValues();
		}
		super.setVisible(visible);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}
}
