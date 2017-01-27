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
package org.eclipse.php.composer.ui.wizard.project.template;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.wizard.project.template.messages"; //$NON-NLS-1$
	public static String PackageFilterViewer_PackagesFound;
	public static String PackageFilterViewer_ProjectsOnlyLabel;
	public static String PackageProjectCreationWizard_SecondPageTitle;
	public static String PackageProjectCreationWizard_Title;
	public static String PackageProjectWizardFirstPage_Description;
	public static String PackageProjectWizardFirstPage_OverrideComposerJsonLabel;
	public static String PackageProjectWizardFirstPage_Title;
	public static String PackageProjectWizardSecondPage_Description;
	public static String PackageProjectWizardSecondPage_InitializingProjectTaskName;
	public static String PackageProjectWizardSecondPage_Title;
	public static String PackageProjectWizardSecondPage_UpdatingComposerJsonTaskName;
	public static String PackagistItem_DownloadToolTipText;
	public static String PackagistItem_FavoritesToolTipText;
	public static String PackagistItem_LoadingVersionsMessage;
	public static String Validator_DirectoryNotEmptyError;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
