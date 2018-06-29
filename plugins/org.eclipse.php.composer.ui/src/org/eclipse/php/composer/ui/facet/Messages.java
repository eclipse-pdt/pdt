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
package org.eclipse.php.composer.ui.facet;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.facet.messages"; //$NON-NLS-1$
	public static String FacetInstallWizardPage_Description;
	public static String FacetInstallWizardPage_Label;
	public static String FacetInstallWizardPage_Name;
	public static String FacetInstallWizardPage_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
