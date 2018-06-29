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
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.ui.launching.messages"; //$NON-NLS-1$
	public static String AbstractDebugExeLaunchSettingsSection_Break_at_first_line;
	public static String AbstractDebugExeLaunchSettingsSection_Breakpoint;
	public static String AbstractDebugWebLaunchSettingsSection_Break_at_first_line;
	public static String AbstractDebugWebLaunchSettingsSection_Breakpoint;
	public static String AbstractDebugWebLaunchSettingsSection_Canceled;
	public static String AbstractDebugWebLaunchSettingsSection_Connected_with_warnings;
	public static String AbstractDebugWebLaunchSettingsSection_Could_not_determine_port;
	public static String AbstractDebugWebLaunchSettingsSection_Debug_through_SSH;
	public static String AbstractDebugWebLaunchSettingsSection_Error;
	public static String AbstractDebugWebLaunchSettingsSection_Failed_to_connect;
	public static String AbstractDebugWebLaunchSettingsSection_Missing_host;
	public static String AbstractDebugWebLaunchSettingsSection_Missing_host_address;
	public static String AbstractDebugWebLaunchSettingsSection_Missing_SSH_user_name;
	public static String AbstractDebugWebLaunchSettingsSection_Password;
	public static String AbstractDebugWebLaunchSettingsSection_SSH_tunnel;
	public static String AbstractDebugWebLaunchSettingsSection_SSH_tunnel_test;
	public static String AbstractDebugWebLaunchSettingsSection_Successfully_connected;
	public static String AbstractDebugWebLaunchSettingsSection_Test_connection;
	public static String AbstractDebugWebLaunchSettingsSection_Testing_connection;
	public static String AbstractDebugWebLaunchSettingsSection_Undetermined;
	public static String AbstractDebugWebLaunchSettingsSection_User_name;
	public static String AbstractPHPLaunchConfigurationDebuggerTab_Configure;
	public static String AbstractPHPLaunchConfigurationDebuggerTab_Debugger_tab_name;
	public static String AbstractPHPLaunchConfigurationDebuggerTab_Debugger_type;
	public static String AbstractPHPLaunchConfigurationDebuggerTab_Test;
	public static String AbstractPHPLaunchConfigurationDebuggerTab_Unsupported_debugger_type;
	public static String ApplicationFileSelectionDialog_0;
	public static String ApplicationFileSelectionDialog_1;
	public static String ApplicationFileSelectionDialog_2;
	public static String FileSelectionDialog_0;
	public static String FileSelectionDialog_1;
	public static String FileSelectionDialog_2;
	public static String PHPExeLaunchConfigurationDebuggerTab_No_debugger_is_attached_to_configuration;
	public static String PHPExeLaunchConfigurationDebuggerTab_none;
	public static String ZendDebuggerWebLaunchSettingsSection_Browser;
	public static String ZendDebuggerWebLaunchSettingsSection_Continue_debug_from;
	public static String ZendDebuggerWebLaunchSettingsSection_Debug_all_pages;
	public static String ZendDebuggerWebLaunchSettingsSection_Debug_first_page;
	public static String ZendDebuggerWebLaunchSettingsSection_Default;
	public static String ZendDebuggerWebLaunchSettingsSection_Invalid_debug_start_page;
	public static String ZendDebuggerWebLaunchSettingsSection_Invalid_URL;
	public static String ZendDebuggerWebLaunchSettingsSection_Open_in_browser;
	public static String ZendDebuggerWebLaunchSettingsSection_Start_debug_from;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
