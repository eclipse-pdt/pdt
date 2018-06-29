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
package org.eclipse.php.internal.debug.core;

/**
 * Constants for the PHP debugger.
 */
public interface IPHPDebugConstants {

	String ATTR_WORKING_DIRECTORY = "ATTR_WORKING_DIRECTORY"; //$NON-NLS-1$
	String ATTR_EXECUTABLE_LOCATION = "ATTR_LOCATION"; //$NON-NLS-1$
	String ATTR_INI_LOCATION = "ATTR_INI_LOCATION"; //$NON-NLS-1$
	String ATTR_FILE = "ATTR_FILE"; //$NON-NLS-1$
	String ATTR_FILE_FULL_PATH = "ATTR_FILE_FULL_PATH"; //$NON-NLS-1$
	String ATTR_ENABLE_CODE_COVERAGE = "ATTR_ENABLE_CODE_COVERAGE"; //$NON-NLS-1$

	/**
	 * Unique identifier for the PHP debug model (value
	 */
	String ID_PHP_DEBUG_CORE = "org.eclipse.php.debug.core"; //$NON-NLS-1$

	String PHP_Port = ID_PHP_DEBUG_CORE + ".PHP_Port"; //$NON-NLS-1$
	String PHP_URL = ID_PHP_DEBUG_CORE + ".PHP_URL"; //$NON-NLS-1$
	String PHP_Project = ID_PHP_DEBUG_CORE + ".PHP_Project"; //$NON-NLS-1$
	String PHP_File = ID_PHP_DEBUG_CORE + ".PHP_File"; //$NON-NLS-1$
	String PHP_Exe = ID_PHP_DEBUG_CORE + ".PHP_EXE"; //$NON-NLS-1$
	String ConditionEnabled = ID_PHP_DEBUG_CORE + ".ConditionEnabled"; //$NON-NLS-1$
	String Condition = ID_PHP_DEBUG_CORE + ".Condition"; //$NON-NLS-1$
	String PHPProcessType = ID_PHP_DEBUG_CORE + ".launching.PHPProcess"; //$NON-NLS-1$
	String RUN_WITH_DEBUG_INFO = ID_PHP_DEBUG_CORE + ".RunWithDebugInfo"; //$NON-NLS-1$
	String OPEN_IN_BROWSER = ID_PHP_DEBUG_CORE + ".OpenInBrowser"; //$NON-NLS-1$

	String CacheGrind_File = ID_PHP_DEBUG_CORE + ".CacheGrind_File"; //$NON-NLS-1$

	String PHPEXELaunchType = "org.eclipse.php.debug.core.launching.PHPExeLaunchConfigurationType"; //$NON-NLS-1$
	String PHPServerLaunchType = "org.eclipse.php.debug.core.launching.webPageLaunch"; //$NON-NLS-1$
	String PHPRemoteLaunchType = "org.eclipse.php.debug.core.remotePHPLaunchConfigurationType"; //$NON-NLS-1$
	// String USE_INTERNAL_BROWSER = ID_PHP_DEBUG_CORE
	// + ".UseExternalBrowser";
	String USE_SSH_TUNNEL = ID_PHP_DEBUG_CORE + ".UseSSHTunnel"; //$NON-NLS-1$
	String SSH_TUNNEL_USER_NAME = ID_PHP_DEBUG_CORE + ".SSHTunnelUserName"; //$NON-NLS-1$
	String SSH_TUNNEL_PASSWORD = ID_PHP_DEBUG_CORE + ".SSHTunnelPassword"; //$NON-NLS-1$
	String SSH_TUNNEL_SECURE_PREF_NODE = "/org.eclipse.php/DEBUG"; //$NON-NLS-1$

	/**
	 * Status code indicating an unexpected internal error (value <code>150</code>).
	 */
	int INTERNAL_ERROR = 150;

	/**
	 * Status code indicating an error while connecting to the debug server (valuse
	 * <code>200</code>), usually, as a result of a debug session that is
	 * initialized on a file that does not exist on the server side.
	 */
	int DEBUG_CONNECTION_ERROR = 200;

	/**
	 * Debug parameters initializer preferences key
	 */
	String PHP_DEBUG_PARAMETERS_INITIALIZER = ID_PHP_DEBUG_CORE + ".debugParametersInitializer"; //$NON-NLS-1$

	String DEBUG_PER_PROJECT = ID_PHP_DEBUG_CORE + ".use-project-settings"; //$NON-NLS-1$
	String DEBUG_QUALIFIER = ID_PHP_DEBUG_CORE + ".Debug_Process_Preferences"; //$NON-NLS-1$
	String PREFERENCE_PAGE_ID = "org.eclipse.php.debug.ui.preferences.PHPDebugPreferencePage"; //$NON-NLS-1$
	String PROJECT_PAGE_ID = "org.eclipse.php.debug.ui.property.PHPDebugPreferencePage"; //$NON-NLS-1$

	String DEBUGGING_PAGES = "debugPages"; //$NON-NLS-1$
	String DEBUGGING_ALL_PAGES = "debugAllPages"; //$NON-NLS-1$
	String DEBUGGING_FIRST_PAGE = "debugFirstPage"; //$NON-NLS-1$
	String DEBUGGING_START_FROM = "debugFrom"; //$NON-NLS-1$
	String DEBUGGING_SHOULD_CONTINUE = "debugFromURL"; //$NON-NLS-1$
	String DEBUGGING_START_FROM_URL = "debugContinue"; //$NON-NLS-1$
	String DEBUGGING_NO_REMOTE = "no_remote"; //$NON-NLS-1$
	String DEBUGGING_DEBUG_NO_REMOTE = "debugNoRemote"; //$NON-NLS-1$
	String DEBUGGING_GET_FILE_CONTENT = "get_file_content"; //$NON-NLS-1$
	String DEBUGGING_LINE_NUMBER = "line_number"; //$NON-NLS-1$
	String DEBUGGING_USE_SERVER_FILES = "debugNoRemote"; //$NON-NLS-1$
	String DEBUGGING_COLLECT_CODE_COVERAGE = "collectCodeCoverage"; //$NON-NLS-1$

	String PREF_STEP_FILTERS_LIST = ID_PHP_DEBUG_CORE + ".pref_step_filters_list"; //$NON-NLS-1$

	// TODO: Keep this in profile plugin
	String XDEBUG_PROFILE_TRIGGER = ID_PHP_DEBUG_CORE + ".XDebugProfileTrigger"; //$NON-NLS-1$
	String XDEBUG_PROFILE_TRIGGER_VALUE = ID_PHP_DEBUG_CORE + ".XDebugProfileTriggerValue"; //$NON-NLS-1$
}
