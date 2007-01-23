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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class PHPCodeAssistPreferencePage extends AbstractPreferencePage {

	protected Button completionInsertRadioButton;
	protected Button completionOverrideRadioButton;
	protected Button insertSingleproposalsCheckBox;
	protected Button showVariableFromOtherFilesCheckBox;
	protected Button determineObjTypeFromOtherFilesCheckBox;
	protected Button showConstantsAssistCheckBox;
	protected Button caseSensitiveForConstantsCheckBox;
	protected Button showClassNamesInGlobalListCheckBox;
	protected Button showNonStrictOptionsCheckBox;
	protected Button autoActivationCheckBox;
	//	protected Button autoActivationForClassNamesCheckBox;
	//	protected Button autoActivationForVariablesCheckBox;
	//	protected Button autoActivationForFunctionsKeywordsConstantsCheckBox;
	protected Text autoActivationDelay;
	//protected Text autoActivationSizeLimit;
	protected Text autoActivationTriggersPHP;
	protected Text autoActivationTriggersPHPDoc;

	protected void initializeValues() {
		super.initializeValues();
		completionOverrideRadioButton.setSelection(!completionInsertRadioButton.getSelection());
		autoActivationCheckBox.notifyListeners(SWT.Selection, null);
	}

	protected void restoreDefaultValues() {
		super.restoreDefaultValues();
		completionOverrideRadioButton.setSelection(!completionInsertRadioButton.getSelection());
	}

	protected void addOptionsSection(Composite composite) {
		Composite radioButtonsComposite = new Composite(composite, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.numColumns = 2;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		radioButtonsComposite.setLayout(gridLayout);
		radioButtonsComposite.setLayoutData(gridData);

		completionInsertRadioButton = new Button(radioButtonsComposite, SWT.RADIO | SWT.LEFT);
		completionInsertRadioButton.setText(PHPUIMessages.CodeAssistPreferencePage_completionInserts);
		completionInsertRadioButton.setLayoutData(new GridData());
		completionInsertRadioButton.setData(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
		add(completionInsertRadioButton);
		completionOverrideRadioButton = new Button(radioButtonsComposite, SWT.RADIO | SWT.LEFT);
		completionOverrideRadioButton.setText(PHPUIMessages.CodeAssistPreferencePage_completionOverwrites);
		completionOverrideRadioButton.setLayoutData(new GridData());

		insertSingleproposalsCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_insertSignleProposals, PreferenceConstants.CODEASSIST_AUTOINSERT, 0);
		showVariableFromOtherFilesCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_showVariablesFromOtherFiles, PreferenceConstants.CODEASSIST_SHOW_VARIABLES_FROM_OTHER_FILES, 0);
		determineObjTypeFromOtherFilesCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_determineObjTypeFromOtherFiles, PreferenceConstants.CODEASSIST_DETERMINE_OBJ_TYPE_FROM_OTHER_FILES, 0);
	}

	protected void addFiltersSection(Composite composite) {
		showConstantsAssistCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_showConstantsAssist, PreferenceConstants.CODEASSIST_SHOW_CONSTANTS_ASSIST, 0);
		caseSensitiveForConstantsCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_caseSensitiveForConstants, PreferenceConstants.CODEASSIST_CONSTANTS_CASE_SENSITIVE, 0);
		showClassNamesInGlobalListCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_showClassNamesInGlobal, PreferenceConstants.CODEASSIST_SHOW_CLASS_NAMES_IN_GLOBAL_COMPLETION, 0);
		showNonStrictOptionsCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_showNonStrictOptions, PreferenceConstants.CODEASSIST_SHOW_NON_STRICT_OPTIONS, 0); //$NON-NLS-1$
	}

	protected void addAutoActivationSection(Composite composite) {
		autoActivationCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_enableAutoActivation, PreferenceConstants.CODEASSIST_AUTOACTIVATION, 0);
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
				//setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_SIZE_LIMIT, autoActivateSectionEnabled);
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP, autoActivateSectionEnabled);
				setControlsEnabled(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC, autoActivateSectionEnabled);
			}
		});
		//		autoActivationForClassNamesCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationForClassNames, PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_CLASS_NAMES, 20);
		//		autoActivationForVariablesCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationForVariables, PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_VARIABLES, 20);
		//		autoActivationForFunctionsKeywordsConstantsCheckBox = addCheckBox(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationForFunctionsKeywordsConstants, PreferenceConstants.CODEASSIST_AUTOACTIVATION_FOR_FUNCTIONS_KEYWORDS_CONSTANTS, 20);

		autoActivationDelay = addLabelledTextField(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationDelay, PreferenceConstants.CODEASSIST_AUTOACTIVATION_DELAY, 4, 20, new PositiveIntegerStringValidator(PHPUIMessages.CodeAssistPreferencePage_autoActivationDelayIntValue,
			PHPUIMessages.CodeAssistPreferencePage_autoActivationDelayIntValue, PHPUIMessages.CodeAssistPreferencePage_autoActivationDelayPositive));

		// temporary disable this feature (since we don't know yet how to implements this in Eclipse)
		//		autoActivationSizeLimit = addLabelledTextField(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationDisableSizeLimitLabel, PreferenceConstants.CODEASSIST_AUTOACTIVATION_SIZE_LIMIT, 4, 0, new PositiveIntegerStringValidator(
		//			PHPUIMessages.CodeAssistPreferencePage_autoActivationLimitNumberIntValue, PHPUIMessages.CodeAssistPreferencePage_autoActivationLimitNumberIntValue, PHPUIMessages.CodeAssistPreferencePage_autoActivationLimitNumberPositive));

		autoActivationTriggersPHP = addLabelledTextField(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationTriggersPHP, PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHP, 4, 20);

		autoActivationTriggersPHPDoc = addLabelledTextField(composite, PHPUIMessages.CodeAssistPreferencePage_autoActivationTriggersPHPDoc, PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_PHPDOC, 4, 20);
	}

	protected Control createContents(Composite parent) {
		// Create scrolled pane
		ScrolledCompositeImpl scrolled = new ScrolledCompositeImpl(parent, SWT.V_SCROLL | SWT.H_SCROLL);

		Composite control = new Composite(scrolled, SWT.NONE);
		scrolled.setContent(control);
		GridLayout layout = new GridLayout();
		control.setLayout(layout);

		Composite composite;
		composite = createSubsection(control, PHPUIMessages.CodeAssistPreferencePage_optionsSectionLabel);
		addOptionsSection(composite);

		composite = createSubsection(control, PHPUIMessages.CodeAssistPreferencePage_filtersSectionLabel);
		addFiltersSection(composite);

		composite = createSubsection(control, PHPUIMessages.CodeAssistPreferencePage_autoActivationSectionLabel);
		addAutoActivationSection(composite);

		// Resize scrolled pane to the maximum size
		final Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolled.setMinSize(size.x, size.y);

		initializeValues();

		return scrolled;
	}
}
