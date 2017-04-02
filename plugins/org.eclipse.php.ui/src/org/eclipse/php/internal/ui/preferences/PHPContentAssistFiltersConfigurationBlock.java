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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.prefs.BackingStoreException;

/**
 * 
 * @author guy.g
 * 
 */
public class PHPContentAssistFiltersConfigurationBlock extends AbstractPHPContentAssistPreferencePageBlock {

	protected Button caseSensitivityCheckBox;
	protected Button showStrictOptionsCheckBox;

	@Override
	public void setCompositeAddon(Composite parent) {
		Composite composite = createSubsection(parent, PHPUIMessages.CodeAssistPreferencePage_filtersSectionLabel);
		caseSensitivityCheckBox = addCheckBox(composite,
				PHPUIMessages.CodeAssistPreferencePage_caseSensitiveForConstants,
				PHPCoreConstants.CODEASSIST_CASE_SENSITIVITY, 0);
		showStrictOptionsCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_showStrictOptions,
				PHPCoreConstants.CODEASSIST_SHOW_STRICT_OPTIONS, 0);
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getCorePreferenceStore();
	}

	@Override
	protected void storeValues() {
		super.storeValues();
		try {
			InstanceScope.INSTANCE.getNode(PHPCorePlugin.ID).flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}
}
