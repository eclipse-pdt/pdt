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

import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
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
	protected Button cutCommonPrefixCheckBox;

	public void setCompositeAddon(Composite parent) {
		Composite composite = createSubsection(parent, PHPUIMessages.getString("CodeAssistPreferencePage_filtersSectionLabel"));
		showConstantsAssistCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showConstantsAssist"), PHPCoreConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST, 0);
		caseSensitiveForConstantsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_caseSensitiveForConstants"), PHPCoreConstants.CODEASSIST_CONSTANTS_CASE_SENSITIVE, 0);
		showClassNamesInGlobalListCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showClassNamesInGlobal"), PHPCoreConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION, 0);
		showNonStrictOptionsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showNonStrictOptions"), PHPCoreConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS, 0); //$NON-NLS-1$
		groupCompletionsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_groupCompletionOptions"), PHPCoreConstants.CODEASSIST_GROUP_OPTIONS, 0); //$NON-NLS-1$
		// XXX removed temporarily (we're not sure we want it (damn)):
		// cutCommonPrefixCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_cutCommonPrefix"), PHPCoreConstants.CODEASSIST_CUT_COMMON_PREFIX, 0); //$NON-NLS-1$
	}

	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(PHPCorePlugin.getDefault().getPluginPreferences());
	}
}
