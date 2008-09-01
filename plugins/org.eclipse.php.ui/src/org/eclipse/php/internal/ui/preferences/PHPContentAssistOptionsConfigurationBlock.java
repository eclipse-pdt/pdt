/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author guy.g
 *
 */
public class PHPContentAssistOptionsConfigurationBlock extends AbstractPHPContentAssistPreferencePageBlock {

	protected Button completionInsertRadioButton;
	protected Button completionOverrideRadioButton;
	protected Button insertSingleproposalsCheckBox;
	protected Button showVariableFromOtherFilesCheckBox;
	protected Button determineObjTypeFromOtherFilesCheckBox;

	public void setCompositeAddon(Composite parent) {
		Composite composite = createSubsection(parent, PHPUIMessages.getString("CodeAssistPreferencePage_optionsSectionLabel"));
		Composite radioButtonsComposite = new Composite(composite, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.numColumns = 2;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		radioButtonsComposite.setLayout(gridLayout);
		radioButtonsComposite.setLayoutData(gridData);

		completionInsertRadioButton = new Button(radioButtonsComposite, SWT.RADIO | SWT.LEFT);
		completionInsertRadioButton.setText(PHPUIMessages.getString("CodeAssistPreferencePage_completionInserts"));
		completionInsertRadioButton.setLayoutData(new GridData());
		completionInsertRadioButton.setData(PHPCoreConstants.CODEASSIST_INSERT_COMPLETION);
		add(completionInsertRadioButton);

		completionOverrideRadioButton = new Button(radioButtonsComposite, SWT.RADIO | SWT.LEFT);
		completionOverrideRadioButton.setText(PHPUIMessages.getString("CodeAssistPreferencePage_completionOverwrites"));
		completionOverrideRadioButton.setLayoutData(new GridData());

		add(completionOverrideRadioButton);

		insertSingleproposalsCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_insertSignleProposals"), PHPCoreConstants.CODEASSIST_AUTOINSERT, 0);
		showVariableFromOtherFilesCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_showVariablesFromOtherFiles"), PHPCoreConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES, 0);
		determineObjTypeFromOtherFilesCheckBox = addCheckBox(composite, PHPUIMessages.getString("CodeAssistPreferencePage_determineObjTypeFromOtherFiles"), PHPCoreConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES, 0);

	}

	protected void initializeValues() {
		super.initializeValues();
		completionOverrideRadioButton.setSelection(!completionInsertRadioButton.getSelection());
	}

	@Override
	protected void restoreDefaultButtonValues() {
		super.restoreDefaultButtonValues();
		completionOverrideRadioButton.setSelection(!completionInsertRadioButton.getSelection());
	}
	
	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(PHPCorePlugin.getDefault().getPluginPreferences());
	}

	protected void storeValues() {
		super.storeValues();
		PHPCorePlugin.getDefault().savePluginPreferences();
	}
}
