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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author guy.g
 *
 */
public class PHPContentAssistFiltersConfigurationBlock extends AbstractPHPContentAssistPreferencePageBlock {

	protected Button showConstantsAssistCheckBox;
	protected Button caseSensitiveForConstantsCheckBox;
	protected Button showClassNamesInGlobalListCheckBox;
	protected Button showNonStrictOptionsCheckBox;
	protected Button groupCompletionsCheckBox;

	public void setCompositeAddon(Composite parent) {
		Composite composite = createSubsection(parent, PHPUIMessages.getString("CodeAssistPreferencePage_filtersSectionLabel"));
		showConstantsAssistCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showConstantsAssist"), PreferenceConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST, 0);
		caseSensitiveForConstantsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_caseSensitiveForConstants"), PreferenceConstants.CODEASSIST_CONSTANTS_CASE_SENSITIVE, 0);
		showClassNamesInGlobalListCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showClassNamesInGlobal"), PreferenceConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION, 0);
		showNonStrictOptionsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showNonStrictOptions"), PreferenceConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS, 0); //$NON-NLS-1$
		groupCompletionsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_groupCompletionOptions"), PreferenceConstants.CODEASSIST_GROUP_OPTIONS, 0); //$NON-NLS-1$
	}

}
