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
public class PHPContentAssistAutoActivationConfigurationBlock extends AbstractPHPContentAssistPreferencePageBlock {

	protected Button autoActivationCheckBox;
	protected Text autoActivationDelay;
	protected Text autoActivationTriggersPHP;
	protected Text autoActivationTriggersPHPDoc;

	public void setCompositeAddon(Composite parent) {
		Composite composite = createSubsection(parent, PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationSectionLabel"));
		autoActivationCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_enableAutoActivation"), PreferenceConstants.CODEASSIST_AUTOACTIVATION, 0);
		autoActivationCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				boolean autoActivateSectionEnabled = ((Button) e.widget).getSelection();
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_DELAY, autoActivateSectionEnabled);
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES, autoActivateSectionEnabled);
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES, autoActivateSectionEnabled);
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS, autoActivateSectionEnabled);
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP, autoActivateSectionEnabled);
//				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC, autoActivateSectionEnabled);
			}
		});

		autoActivationDelay = addLabelledTextField(composite, PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationDelay"), PreferenceConstants.CODEASSIST_AUTOACTIVATION_DELAY, 4, 20, new PositiveIntegerStringValidator(PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationDelayIntValue"),
			PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationDelayIntValue"), PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationDelayPositive")));

		autoActivationTriggersPHP = addLabelledTextField(composite, PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationTriggersPHP"), PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP, 4, 20);

		autoActivationTriggersPHPDoc = addLabelledTextField(composite, PHPUIMessages.getString("CodeAssistPreferencePage_autoActivationTriggersPHPDoc"), PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC, 4, 20);
		autoActivationTriggersPHPDoc.setEnabled(false);

	}
}
