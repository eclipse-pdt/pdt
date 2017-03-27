/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core;

import org.eclipse.osgi.util.NLS;

/**
 * Strings used by PHP Debugger Core
 * 
 */
public class PHPDebugCoreMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.core.PHPDebugCoreMessages"; //$NON-NLS-1$

	public static String LineBreakPointMessage_1;
	public static String ConditionalBreakPointMessage_1;
	public static String ConditionalBreakPointMessage_2;
	public static String DBGpMultiSessionTarget_Multisession_PHP_process;
	public static String DBGpProxyConnection_Registering_DBGp_proxy;
	public static String DebuggerFileNotFound_1;
	public static String DebuggerDebugPortInUse_1;
	public static String DebuggerConnection_Problem_1;
	public static String DebuggerConnection_Problem_2;
	public static String DebuggerConnection_Problem_3;
	public static String DebuggerConnection_Problem_4;
	public static String DebuggerConnection_Problem_5;
	public static String DebuggerConnection_Failed_1;
	public static String Debugger_Unexpected_Error_1;
	public static String Debugger_ResourceNotFound;
	public static String Debugger_LaunchError_title;
	public static String Debugger_InvalidDebugResource;
	public static String Debugger_General_Error;
	public static String Debugger_Launch_Error;
	public static String Debugger_Error_Message;
	public static String Debugger_Error_Message_2;
	public static String Debugger_Error_Message_3;
	public static String Debugger_Error_Crash_Message;
	public static String Debugger_Incompatible_Protocol;
	public static String PHPDebugPlugin_PerformingPostStartupOperations;
	public static String PHPDebugPlugin_PostStartup;
	public static String PHPExecutableDebuggerInitializer_6;
	public static String PHPExecutableDebuggerInitializer_7;
	public static String PHPExecutableLaunchDelegate_0;
	public static String PHPExecutableLaunchDelegate_4;
	public static String PHPLaunchUtilities_0;
	public static String PHPLaunchUtilities_7;
	public static String PHPLaunchUtilities_8;
	public static String PHPLaunchUtilities_activeLaunchDetected;
	public static String PHPLaunchUtilities_confirmation;
	public static String PHPLaunchUtilities_multipleLaunchesPrompt;
	public static String PHPLaunchUtilities_phpLaunchTitle;
	public static String PHPLaunchUtilities_rememberDecision;
	public static String PHPLaunchUtilities_PHPPerspectiveSwitchTitle;
	public static String PHPLaunchUtilities_PHPPerspectiveSwitchMessage;
	public static String PHPLaunchUtilities_terminate;
	public static String PHPLaunchUtilities_waitingForDebugger;
	public static String PHPWebPageLaunchDelegate_DialogError;
	public static String PHPWebPageLaunchDelegate_DialogErrorDebug;
	public static String PHPWebPageLaunchDelegate_DialogErrorProfile;
	public static String PHPWebPageLaunchDelegate_DialogErrorRun;
	public static String PHPWebPageLaunchDelegate_serverNotFound;
	public static String DebugConfigurationDialog_invalidPortRange;
	public static String DebugConfigurationDialog_invalidPort;
	public static String DebugConfigurationDialog_PortInUse;
	public static String DebuggerConfigurationDialog_debugPort;
	public static String DebuggerConfigurationDialog_invalidPortRange;
	public static String ServerDebugHandler_0;
	public static String ZendDebuggerConfiguration_ZendDebuggerNotInstalledError;
	public static String ZendDebuggerConfigurationDialog_AutoMode;
	public static String ZendDebuggerConfigurationDialog_Broadcast_port;
	public static String ZendDebuggerConfigurationDialog_client_host_ip;
	public static String ZendDebuggerConfigurationDialog_Connection_settings_group;
	public static String ZendDebuggerConfigurationDialog_debug_response_timeout;
	public static String ZendDebuggerConfigurationDialog_Dialog_description;
	public static String ZendDebuggerConfigurationDialog_Dialog_title;
	public static String ZendDebuggerConfigurationDialog_Dummy_file_name;
	public static String ZendDebuggerConfigurationDialog_General_settings_group;
	public static String ZendDebuggerConfigurationDialog_invalid_response_time;
	public static String ZendDebuggerConfigurationDialog_invalid_response_time_exc;
	public static String ZendDebuggerConfigurationDialog_ManualMode;
	public static String ZendDebuggerConfigurationDialog_Note_label;
	public static String ZendDebuggerConfigurationDialog_Note_text;
	public static String ZendDebuggerConfigurationDialog_runWithDebugInfo;
	public static String ZendDebuggerConfigurationDialog_useNewProtocol;
	public static String ZendDebuggerConfigurationDialog_zendDebuggerSettings;
	public static String ZendDebuggerConfigurationDialog_UseSSLEncryption;
	public static String ZendDebuggerConfigurationDialog_Reload;
	public static String ZendDebuggerConfigurationDialog_Configure;
	public static String ZendDebuggerConfigurationDialog_ClientIPWarning;
	public static String ZendDebuggerConfigurationDialog_ClientIPsWarning;
	public static String XDebugCommunicationDaemon_Dont_show_this_message_again;
	public static String XDebugCommunicationDaemon_Remote_debug_session_does_not_refer_to_localhost;
	public static String XDebugCommunicationDaemon_Remote_debug_session_for_localhost;
	public static String XDebugCommunicationDaemon_XDebug_remote_session_title;
	public static String XDebugConfigurationDialog_mainTitle;
	public static String XDebugConfigurationDialog_generalGroup;
	public static String XDebugConfigurationDialog_captureGroup;
	public static String XDebugConfigurationDialog_proxyGroup;
	public static String XDebugConfigurationDialog_invalidTimeout;
	public static String XDebugConfigurationDialog_invalidTimeoutValue;
	public static String XDebugConfigurationDialog_maxArrayDepth;
	public static String XDebugConfigurationDialog_maxChildren;
	public static String XDebugConfigurationDialog_showSuperGlobals;
	public static String XDebugConfigurationDialog_useMultisession;
	public static String XDebugConfigurationDialog_remoteSession;
	public static String XDebugConfigurationDialog_remoteSessionOption_off;
	public static String XDebugConfigurationDialog_remoteSessionOption_localhost;
	public static String XDebugConfigurationDialog_remoteSessionOption_any;
	public static String XDebugConfigurationDialog_remoteSessionOption_prompt;
	public static String XDebugConfigurationDialog_captureStdout;
	public static String XDebugConfigurationDialog_captureStderr;
	public static String XDebugConfigurationDialog_capture_off;
	public static String XDebugConfigurationDialog_capture_copy;
	public static String XDebugConfigurationDialog_capture_redirect;
	public static String XDebugConfigurationDialog_Connection_settings_group;
	public static String XDebugConfigurationDialog_Dialog_description;
	public static String XDebugConfigurationDialog_Dialog_title;
	public static String XDebugConfigurationDialog_General_settings_group;
	public static String XDebugConfigurationDialog_useProxy;
	public static String XDebugConfigurationDialog_idekey;
	public static String XDebugConfigurationDialog_MaxData;
	public static String XDebugConfigurationDialog_Note_label;
	public static String XDebugConfigurationDialog_Note_text;
	public static String XDebugConfigurationDialog_proxy;
	public static String XDebugMessage_debugError;
	public static String XDebugMessage_unexpectedTermination;
	public static String XDebugMessage_remoteSessionTitle;
	public static String XDebugMessage_remoteSessionPrompt;
	public static String XDebug_DBGpProxyHandler_0;
	public static String XDebug_DBGpProxyHandler_1;
	public static String XDebug_DBGpProxyHandler_2;
	public static String XDebug_DBGpProxyHandler_3;
	public static String XDebug_ExeLaunchConfigurationDelegate_0;
	public static String XDebug_ExeLaunchConfigurationDelegate_1;
	public static String XDebug_ExeLaunchConfigurationDelegate_2;
	public static String XDebug_ExeLaunchConfigurationDelegate_3;
	public static String XDebug_ExeLaunchConfigurationDelegate_4;
	public static String XDebug_WebLaunchConfigurationDelegate_0;
	public static String XDebug_WebLaunchConfigurationDelegate_1;
	public static String XDebug_WebLaunchConfigurationDelegate_2;
	public static String XDebug_WebLaunchConfigurationDelegate_3;
	public static String XDebug_WebLaunchConfigurationDelegate_4;
	public static String XDebug_DBGpTarget_0;
	public static String XDebug_DBGpTarget_1;
	public static String XDebug_DBGpTarget_2;
	public static String XDebug_DBGpContainerValue_0;
	public static String XDebug_DBGpMultiSessionTarget_0;
	public static String XDebug_DBGpStackFrame_0;
	public static String XDebug_DBGpStringValue_0;
	public static String XDebug_DBGpThread_0;
	public static String XDebug_DBGpVariable_0;
	public static String XDebug_DBGpVariable_1;
	public static String XDebug_IDBGpModelConstants_0;
	public static String XDebug_IDBGpModelConstants_1;
	public static String NoneDebuggerConfiguration_Launching;
	public static String NoneDebuggerConfiguration_PHP_executable_file_is_invalid;
	public static String NoneDebuggerConfiguration_PHP_script_file_is_invalid;
	public static String NoneDebuggerConfiguration_PHP_source_file_is_invalid;
	public static String NoneDebuggerConfiguration_There_is_no_debugger_attached_for_PHP_executable;
	public static String NoneDebuggerConfiguration_There_is_no_debugger_attached_for_PHP_server;
	public static String NoneDebuggerConfiguration_There_is_no_PHP_runtime_environment;
	public static String NoneDebuggerConfiguration_There_is_no_PHP_server_specified;
	public static String ExeLaunchConfigurationDelegate_PortInUse;
	public static String WebLaunchConfigurationDelegate_PortInUse;
	public static String XDebugDebuggerConfiguration_XDebugNotEnabledError;
	public static String XDebugDebuggerConfiguration_XDebugNotInstalledError;
	public static String XDebugWebLaunchConfigurationDelegate_PHP_process;
	public static String PHPProcess_Zend_Debugger_suffix;
	public static String PHPProcess_XDebug_suffix;
	public static String RemoteDebugger_LicenseError;
	public static String RemoteDebugger_WarnNoneI5;
	public static String RemoteDebugger_RequestFileFromServer;
	public static String ConfigureHostsDialog_Address_could_not_be_detected;
	public static String ConfigureHostsDialog_Down_button;
	public static String ConfigureHostsDialog_Up_button;
	public static String ConfigureHostsDialog_Address_column;
	public static String ConfigureHostsDialog_Cancel_button;
	public static String ConfigureHostsDialog_Configure_client_IPs;
	public static String ConfigureHostsDialog_Deselect_all_button;
	public static String ConfigureHostsDialog_OK_button;
	public static String ConfigureHostsDialog_Reset_button;
	public static String ConfigureHostsDialog_Select_all_button;
	public static String ConfigureHostsDialog_Select_addresses;
	public static String ConfigureHostsDialog_Type_column;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, PHPDebugCoreMessages.class);
	}

	private PHPDebugCoreMessages() {
		// cannot create new instance
	}
}
