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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;

/**
 * Hover preference page.
 * <p>
 * Note: Must be public since it is referenced from plugin.xml
 * </p>
 * 
 * @since 3.0
 */
public class PHPEditorHoverPreferencePage extends
		AbstractConfigurationBlockPreferencePage {
	/*
	 * @seeorg.eclipse.ui.internal.editors.text.
	 * AbstractConfigureationBlockPreferencePage#getHelpId()
	 */
	protected String getHelpId() {
		return IPHPHelpContextIds.HOVERS_PREFERENCES;
	}

	/*
	 * @see
	 * org.eclipse.ui.internal.editors.text.AbstractConfigurationBlockPreferencePage
	 * #setDescription()
	 */
	protected void setDescription() {
		String description = PHPUIMessages.PHPEditorPreferencePage_hoverTab_title;
		setDescription(description);
	}

	/*
	 * @seeorg.org.eclipse.ui.internal.editors.text.
	 * AbstractConfigurationBlockPreferencePage#setPreferenceStore()
	 */
	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}

	protected Label createDescriptionLabel(Composite parent) {
		return null; // no description for new look.
	}

	/*
	 * @seeorg.eclipse.ui.internal.editors.text.
	 * AbstractConfigureationBlockPreferencePage
	 * #createConfigurationBlock(org.eclipse
	 * .ui.internal.editors.text.OverlayPreferenceStore)
	 */
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new PHPEditorHoverConfigurationBlock(this,
				overlayPreferenceStore);
	}
}
