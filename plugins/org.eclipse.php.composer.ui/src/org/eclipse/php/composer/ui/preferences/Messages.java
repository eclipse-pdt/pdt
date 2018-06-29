/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Rogue Wave Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.preferences.messages"; //$NON-NLS-1$
	public static String ComposerConfigurationBlock_BinaryLabel;
	public static String ComposerConfigurationBlock_BinaryLocationLabel;
	public static String ComposerConfigurationBlock_GlobalChoiceLabel;
	public static String ComposerConfigurationBlock_HeaderLabel;
	public static String ComposerConfigurationBlock_ProjectChoiceLabel;
	public static String ComposerConfigurationBlock_SelectionLabel;
	public static String ComposerPreferencePage_Title;
	public static String SaveActionsConfigurationBlock_RunComposerUpdateLabel;
	public static String SaveActionsConfigurationBlock_UpdateBuildPathLabel;
	public static String SaveActionsPreferencePage_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
