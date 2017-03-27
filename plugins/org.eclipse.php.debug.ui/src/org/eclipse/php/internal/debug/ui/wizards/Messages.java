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
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.ui.wizards.messages"; //$NON-NLS-1$
	public static String DebuggerCommonSettingsSection_Port_is_already_in_use;
	public static String DebuggerCompositeFragment_Configure_exe_debugger_settings;
	public static String DebuggerCompositeFragment_Configure_server_debugger_settings;
	public static String DebuggerCompositeFragment_Debugger;
	public static String DebuggerCompositeFragment_Debugger_settings;
	public static String DebuggerCompositeFragment_Global_settings_link;
	public static String DebuggerCompositeFragment_Test_button;
	public static String DebuggerUnsupportedSettingsSection_Check_global_settings_in_preferences;
	public static String DebuggerUnsupportedSettingsSection_Settings_unsupported_for_debugger_type;
	public static String PHPExeEditDialog_1;
	public static String PHPExeWizard_0;
	public static String XDebugDebuggerExeSettingsSection_Client_port;
	public static String XDebugDebuggerSettingsSection_Client_port_is_missing;
	public static String XDebugDebuggerExeSettingsSection_Connection_settings;
	public static String XDebugDebuggerSettingsSection_Advanced_group;
	public static String XDebugDebuggerSettingsSection_Client_port;
	public static String XDebugDebuggerSettingsSection_Connection_settings;
	public static String XDebugDebuggerSettingsSection_Enable_DBGp_proxy;
	public static String XDebugDebuggerServerSettingsSection_IDE_key_is_missing;
	public static String XDebugDebuggerSettingsSection_Proxy_address;
	public static String XDebugDebuggerServerSettingsSection_Proxy_address_is_missing;
	public static String XDebugDebuggerSettingsSection_Proxy_ide_key;
	public static String ZendDebuggerExeSettingsSection_Client_port;
	public static String ZendDebuggerExeSettingsSection_Client_port_is_missing;
	public static String ZendDebuggerExeSettingsSection_Connection_settings;
	public static String ZendDebuggerServerSettingsSection_Client_IP_is_missing;
	public static String ZendDebuggerServerSettingsSection_Client_IPs;
	public static String ZendDebuggerServerSettingsSection_Client_port;
	public static String ZendDebuggerServerSettingsSection_Client_port_is_missing;
	public static String ZendDebuggerServerSettingsSection_Connection_settings;
	public static String ZendDebuggerServerSettingsSection_Response_timeout;
	public static String ZendDebuggerServerSettingsSection_Response_timeout_is_missing;
	public static String ZendDebuggerServerSettingsSection_Client_host_IP_might_be_invalid;
	public static String ZendDebuggerServerSettingsSection_Client_hosts_IPs_might_be_invalid;
	public static String ZendDebuggerServerSettingsSection_Client_host_IPS_might_be_redundant;
	public static String ZendDebuggerServerSettingsSection_Configure_button;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
