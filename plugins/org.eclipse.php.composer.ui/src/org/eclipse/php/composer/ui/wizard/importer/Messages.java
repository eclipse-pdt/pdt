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
package org.eclipse.php.composer.ui.wizard.importer;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.wizard.importer.messages"; //$NON-NLS-1$
	public static String ComposerImportWizard_TaskName;
	public static String WizardResourceImportPage_18;
	public static String WizardResourceImportPage_19;
	public static String WizardResourceImportPage_20;
	public static String WizardResourceImportPage_21;
	public static String WizardResourceImportPage_BrowseButton;
	public static String WizardResourceImportPage_BrowseDialogMessage;
	public static String WizardResourceImportPage_CannotReadProjectError;
	public static String WizardResourceImportPage_Description;
	public static String WizardResourceImportPage_EclipseProjectAvailableMessage;
	public static String WizardResourceImportPage_ErrorReadingProject;
	public static String WizardResourceImportPage_Name;
	public static String WizardResourceImportPage_NoComposerJsonError;
	public static String WizardResourceImportPage_ProjectAlreadyExistsError;
	public static String WizardResourceImportPage_ProjectNameLabel;
	public static String WizardResourceImportPage_SourcePathLabel;
	public static String WizardResourceImportPage_Title;
	public static String WizardResourceImportPage_WorkspaceLocationCheckbox;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
