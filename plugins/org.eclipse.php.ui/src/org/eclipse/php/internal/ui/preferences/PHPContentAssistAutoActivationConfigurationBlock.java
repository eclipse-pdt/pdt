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

import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PositiveIntegerStringValidator;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author guy.g
 * 
 */
public class PHPContentAssistAutoActivationConfigurationBlock extends
		AbstractPHPContentAssistPreferencePageBlock {

	protected Button autoActivationCheckBox;
	protected Text autoActivationDelay;

	public void setCompositeAddon(Composite parent) {
		Composite composite = createSubsection(
				parent,
				PHPUIMessages.CodeAssistPreferencePage_autoActivationSectionLabel);
		autoActivationCheckBox = addCheckBox(composite,
				PHPUIMessages.CodeAssistPreferencePage_enableAutoActivation,
				PHPCoreConstants.CODEASSIST_AUTOACTIVATION, 0);
		autoActivationCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				boolean autoActivateSectionEnabled = ((Button) e.widget)
						.getSelection();
				setControlsEnabled(
						PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY,
						autoActivateSectionEnabled);
			}
		});

		autoActivationDelay = addLabelledTextField(
				composite,
				PHPUIMessages.CodeAssistPreferencePage_autoActivationDelay,
				PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY,
				4,
				20,
				new PositiveIntegerStringValidator(
						PHPUIMessages.CodeAssistPreferencePage_autoActivationDelayIntValue,
						PHPUIMessages.CodeAssistPreferencePage_autoActivationDelayIntValue,
						PHPUIMessages.CodeAssistPreferencePage_autoActivationDelayPositive));

		setControlsEnablement();
	}

	protected void setControlsEnablement() {
		boolean autoActivateSectionEnabled = getPreferenceStore().getBoolean(
				PHPCoreConstants.CODEASSIST_AUTOACTIVATION);
		setControlsEnabled(PHPCoreConstants.CODEASSIST_AUTOACTIVATION_DELAY,
				autoActivateSectionEnabled);
	}

	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(PHPCorePlugin.getDefault()
				.getPluginPreferences());
	}

	protected void storeValues() {
		super.storeValues();
		PHPCorePlugin.getDefault().savePluginPreferences();
	}

	// restore text boxes enablement according to the checkbox
	protected void restoreDefaultTextValues() {
		super.restoreDefaultTextValues();
		setControlsEnablement();
	}
}
