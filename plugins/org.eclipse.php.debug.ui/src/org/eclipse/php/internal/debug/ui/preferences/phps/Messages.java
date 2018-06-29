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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.ui.preferences.phps.messages"; //$NON-NLS-1$
	public static String PHPExeVerifier_PHP_executable_verification;
	public static String PHPExeVerifier_Unable_to_verify_PHP_exe_error_message;
	public static String PHPExeVerifier_Unable_to_verify_PHP_exe_reason_message;
	public static String PHPExeVerifier_Verifying_PHP_exes;
	public static String PHPInterpreterExecutionConfigurationBlock_0;
	public static String PHPInterpreterExecutionConfigurationBlock_1;
	public static String PHPInterpreterExecutionPreferencePage_0;
	public static String PHPInterpreterExecutionPreferencePage_1;
	public static String PHPsSearchResultDialog_Add;
	public static String PHPsSearchResultDialog_Cancel;
	public static String PHPsSearchResultDialog_Debugger_column;
	public static String PHPsSearchResultDialog_Version_column;
	public static String PHPsSearchResultDialog_Deselect_all;
	public static String PHPsSearchResultDialog_Location;
	public static String PHPsSearchResultDialog_Name;
	public static String PHPsSearchResultDialog_PHP_executables_search;
	public static String PHPsSearchResultDialog_Select_all;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
