/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.editors;

import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.composer.internal.ui.editors.messages"; //$NON-NLS-1$

	public static String ComposerJsonEditor_SourceEditorError;

	public static String GeneralPage_CannotModifyWarning;

	public static String GeneralPage_ComposerJsonParseError;

	public static String GeneralPage_DependenciesAdd;

	public static String GeneralPage_DependenciesDesc;

	public static String GeneralPage_DependenciesModify;

	public static String GeneralPage_DependenciesNameColumn;

	public static String GeneralPage_DependenciesRemove;

	public static String GeneralPage_DependenciesInstall;

	public static String GeneralPage_DependenciesSection;

	public static String GeneralPage_DependenciesSectionDesc;

	public static String GeneralPage_DependenciesTypeColumn;

	public static String GeneralPage_DependenciesUpdate;

	public static String GeneralPage_DependenciesVersionColumn;

	public static String GeneralPage_DumpAutoloadLabel;

	public static String GeneralPage_General;

	public static String GeneralPage_GeneralDesc;

	public static String GeneralPage_GeneralName;

	public static String GeneralPage_GeneralSection;

	public static String GeneralPage_GeneralVersion;

	public static String GeneralPage_PackageDetailsJobName;

	public static String GeneralPage_PackageDetailsJobTitle;

	public static String GeneralPage_PackageModifyErrorMessage;

	public static String GeneralPage_PackageModifyErrorTitle;

	public static String GeneralPage_ParseFailedMessage;

	public static String GeneralPage_RepositoriesAdd;

	public static String GeneralPage_RepositoriesDesc;

	public static String GeneralPage_RepositoriesRemove;

	public static String GeneralPage_RepositoriesSection;

	public static String FormatJsonAction_label;

	public static String FormatJsonAction_tooltip;

	public static String FormatJsonAction_description;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	private static ResourceBundle constructedMessagesBundle = ResourceBundle.getBundle(BUNDLE_NAME);

	public static ResourceBundle getResourceBundle() {
		return constructedMessagesBundle;
	}

}
