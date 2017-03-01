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
package org.eclipse.php.composer.ui.dialogs;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.dialogs.messages"; //$NON-NLS-1$
	public static String ComposerJobFailureDialog_Message;
	public static String ComposerJobFailureDialog_Title;
	public static String DependencyDialog_NameLabel;
	public static String DependencyDialog_Title;
	public static String DependencyDialog_VersionLabel;
	public static String MissingExecutableDialog_CancelButtonLabel;
	public static String MissingExecutableDialog_ConfigureButtonLabel;
	public static String MissingExecutableDialog_LinkText;
	public static String MissingExecutableDialog_LinkToolTipText;
	public static String MissingExecutableDialog_Message;
	public static String MissingExecutableDialog_Title;
	public static String PersonDialog_EmailLabel;
	public static String PersonDialog_HomepageLabel;
	public static String PersonDialog_NameLabel;
	public static String PersonDialog_RoleLabel;
	public static String PersonDialog_Title;
	public static String PsrDialog_EditButton;
	public static String PsrDialog_NamespaceLabel;
	public static String PsrDialog_PathsLabel;
	public static String PsrDialog_RemoveButton;
	public static String PsrDialog_SelectionDialogMessage;
	public static String PsrDialog_SelectionDialogTitle;
	public static String PsrDialog_Title;
	public static String RepositoryDialog_ComposerRepoType;
	public static String RepositoryDialog_GitRepoType;
	public static String RepositoryDialog_MercurialRepoType;
	public static String RepositoryDialog_NameLabel;
	public static String RepositoryDialog_PackageRepoType;
	public static String RepositoryDialog_PearRepoType;
	public static String RepositoryDialog_SvnRepoType;
	public static String RepositoryDialog_Title;
	public static String RepositoryDialog_TypeLabel;
	public static String RepositoryDialog_UrlLabel;
	public static String ScriptDialog_EventLabel;
	public static String ScriptDialog_HandlerLabel;
	public static String ScriptDialog_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
