/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.template;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
final class TemplatePreferencesMessages extends NLS {

	private static final String BUNDLE_NAME = TemplatePreferencesMessages.class.getName();

	private TemplatePreferencesMessages() {
		// Do not instantiate
	}

	public static String TemplateVariableProposal_error_title;

	static {
		NLS.initializeMessages(BUNDLE_NAME, TemplatePreferencesMessages.class);
	}
}