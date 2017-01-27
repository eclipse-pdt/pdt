/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Rogue Wave Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.project;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.wizard.project.messages"; //$NON-NLS-1$
	public static String AutoloadGroup_NamespaceLabel;
	public static String AutoloadValidator_ErrorMessage;
	public static String AutoloadValidator_Message;
	public static String BasicSettingsGroup_ContentAssistDecorationText;
	public static String BasicSettingsGroup_DescriptionLabel;
	public static String BasicSettingsGroup_KeywordsLabel;
	public static String BasicSettingsGroup_LicenseLabel;
	public static String BasicSettingsGroup_TypeLabel;
	public static String BasicSettingsGroup_VendorNameLabel;
	public static String ComposerProjectCreationWizard_Title;
	public static String ComposerProjectWizardFirstPage_Description;
	public static String ComposerProjectWizardFirstPage_Name;
	public static String ComposerProjectWizardFirstPage_Title;
	public static String ComposerProjectWizardSecondPage_CreatingProjectStructureTaskName;
	public static String ComposerProjectWizardSecondPage_Description;
	public static String ComposerProjectWizardSecondPage_DumpingAutoloaderTaskName;
	public static String ComposerProjectWizardSecondPage_InstallingComposerPharTaskName;
	public static String ComposerProjectWizardSecondPage_Name;
	public static String ComposerProjectWizardSecondPage_PSR4Label;
	public static String ComposerProjectWizardSecondPage_Title;
	public static String Validator_EnterVendorName;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
