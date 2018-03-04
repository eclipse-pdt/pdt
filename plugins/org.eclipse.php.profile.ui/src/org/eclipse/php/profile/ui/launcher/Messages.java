/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import org.eclipse.osgi.util.NLS;

/**
 * Messages class.
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.profile.ui.launcher.messages"; //$NON-NLS-1$

	public static String AbstractProfileExeLaunchSettingsSection_Break_at_first_line;
	public static String AbstractProfileExeLaunchSettingsSection_Breakpoint;
	public static String AbstractProfileWebLaunchSettingsSection_Break_at_first_line;
	public static String AbstractProfileWebLaunchSettingsSection_Breakpoint;
	public static String AbstractProfileWebLaunchSettingsSection_Canceled;
	public static String AbstractProfileWebLaunchSettingsSection_Connected_with_warnings;
	public static String AbstractProfileWebLaunchSettingsSection_Could_not_determine_port;
	public static String AbstractProfileWebLaunchSettingsSection_Debug_through_SSH;
	public static String AbstractProfileWebLaunchSettingsSection_Error;
	public static String AbstractProfileWebLaunchSettingsSection_Failed_to_connect;
	public static String AbstractProfileWebLaunchSettingsSection_Missing_host;
	public static String AbstractProfileWebLaunchSettingsSection_Missing_host_address;
	public static String AbstractProfileWebLaunchSettingsSection_Missing_SSH_user_name;
	public static String AbstractProfileWebLaunchSettingsSection_Password;
	public static String AbstractProfileWebLaunchSettingsSection_Profile_through_SSH_tunnel;
	public static String AbstractProfileWebLaunchSettingsSection_SSH_tunnel;
	public static String AbstractProfileWebLaunchSettingsSection_SSH_tunnel_test;
	public static String AbstractProfileWebLaunchSettingsSection_Successfully_connected;
	public static String AbstractProfileWebLaunchSettingsSection_Test_connection;
	public static String AbstractProfileWebLaunchSettingsSection_Testing_connection;
	public static String AbstractProfileWebLaunchSettingsSection_Undetermined;
	public static String AbstractProfileWebLaunchSettingsSection_User_name;
	public static String AbstractPHPLaunchConfigurationProfilerTab_Configure;
	public static String AbstractPHPLaunchConfigurationProfilerTab_Profiler_group_name;
	public static String AbstractPHPLaunchConfigurationProfilerTab_Profiler_label;
	public static String AbstractPHPLaunchConfigurationProfilerTab_Test;
	public static String AbstractPHPLaunchConfigurationProfilerTab_Unsupported_profiler_type;
	public static String PHPExeLaunchConfigurationProfilerTab_No_profiler_is_attached;
	public static String PHPExeLaunchConfigurationProfilerTab_None;
	public static String PHPWebPageLaunchConfigurationProfilerTab_No_profiler_is_attached;
	public static String XDebugProfileLaunchSettingsSection_Profiling_is_not_supported;
	public static String ZendDebuggerProfileExeLaunchSettingsSection_General_group_name;
	public static String ZendDebuggerProfileExeLaunchSettingsSection_Show_code_coverage;
	public static String ZendDebuggerProfileWebLaunchSettingsSection_Browser;
	public static String ZendDebuggerProfileWebLaunchSettingsSection_Open_in_browser;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
