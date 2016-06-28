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
package org.eclipse.php.composer.internal.ui.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.internal.ui.preferences.messages"; //$NON-NLS-1$
	public static String ComposerPreferencePage_BuiltIn;
	public static String ComposerPreferencePage_External;
	public static String ComposerPreferencePage_PathEmptyError;
	public static String ComposerPreferencePage_PharBrowse;
	public static String ComposerPreferencePage_PharDesc;
	public static String ComposerPreferencePage_PharDialogTitle;
	public static String ComposerPreferencePage_PharError;
	public static String ComposerPreferencePage_PharLabel;
	public static String ComposerPreferencePage_PharSection;
	public static String ComposerPreferencePage_PhpDesc;
	public static String ComposerPreferencePage_PhpWarning;
	public static String ComposerPreferencePage_PhpLabel;
	public static String ComposerPreferencePage_PhpManage;
	public static String ComposerPreferencePage_PhpSection;
	public static String ComposerPreferencePage_RepoAdd;
	public static String ComposerPreferencePage_RepoDesc;
	public static String ComposerPreferencePage_RepoModify;
	public static String ComposerPreferencePage_RepoRemove;
	public static String ComposerPreferencePage_RepoSection;
	public static String ComposerPreferencePage_Update;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
