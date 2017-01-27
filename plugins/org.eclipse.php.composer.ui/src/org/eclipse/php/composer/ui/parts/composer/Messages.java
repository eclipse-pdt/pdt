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
package org.eclipse.php.composer.ui.parts.composer;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.composer.ui.parts.composer.messages"; //$NON-NLS-1$
	public static String VersionSuggestion_ConstraintsLabel;
	public static String VersionSuggestion_CustomLabel;
	public static String VersionSuggestion_MajorLabel;
	public static String VersionSuggestion_MinorLabel;
	public static String VersionSuggestion_NAValue;
	public static String VersionSuggestion_NoneLabel;
	public static String VersionSuggestion_StabilityLabel;
	public static String VersionSuggestion_SuggestionsLabel;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
