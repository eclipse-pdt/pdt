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
package org.eclipse.php.ui.preferences.ui;

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

/**
 * 
 * @author guy.g
 *
 */
public class PhpTypingPreferencePage extends AbstractConfigurationBlockPreferencePage {

	private TypingConfigurationBlock typingConfigurationBlock;
	
	protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
		typingConfigurationBlock = new TypingConfigurationBlock(this, overlayPreferenceStore);
		return typingConfigurationBlock;
	}

	protected String getHelpId() {
		return IPHPHelpContextIds.PHP_TYPING_PREFERENCE_PAGE;
	}

	protected void setDescription() {
		setDescription("");

	}

	protected Label createDescriptionLabel(Composite parent) {
		return null;
	}

	/**
	 * This method overrides the default behavior in order to refresh 
	 * values that are updated due to changes in other Preference pages
	 */
	public void setVisible(boolean visible) {
		if(visible){
			typingConfigurationBlock.refreshValues();
		}
		super.setVisible(visible);
	}

	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}
}
