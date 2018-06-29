/*******************************************************************************
 * Copyright (c) 2011, 2013 IBM Corporation and others.
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
package org.eclipse.php.internal.ui.documentation;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
final class PHPDocumentationMessages extends NLS {

	private static final String BUNDLE_NAME = PHPDocumentationMessages.class.getName();

	private PHPDocumentationMessages() {
		// Do not instantiate
	}

	public static String CompletionEvaluator_default_package;
	public static String JavaDoc2HTMLTextReader_parameters_section;
	public static String JavaDoc2HTMLTextReader_returns_section;
	public static String JavaDoc2HTMLTextReader_namespace_section;
	public static String JavaDoc2HTMLTextReader_throws_section;
	public static String JavaDoc2HTMLTextReader_author_section;
	public static String JavaDoc2HTMLTextReader_var_section;
	public static String JavaDoc2HTMLTextReader_deprecated_section;
	public static String JavaDoc2HTMLTextReader_method_in_type;
	public static String JavaDoc2HTMLTextReader_overrides_section;
	public static String JavaDoc2HTMLTextReader_see_section;
	public static String JavaDoc2HTMLTextReader_since_section;
	public static String JavaDoc2HTMLTextReader_specified_by_section;
	public static String JavaDoc2HTMLTextReader_version_section;

	static {
		NLS.initializeMessages(BUNDLE_NAME, PHPDocumentationMessages.class);
	}
}