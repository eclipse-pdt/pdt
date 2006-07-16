/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.core;

/**
 * Constants for the PHP debugger.
 */
public interface IPHPConstants {

	/**
	 * Unique identifier for the PHP debug model (value 
	 */
	public static final String ID_PHP_DEBUG_CORE = "org.eclipse.php.debug.core";

	public static final String PHP_Port = ID_PHP_DEBUG_CORE + ".PHP_Port";
	public static final String PHP_URL = ID_PHP_DEBUG_CORE + ".PHP_URL";
	public static final String PHP_Project = ID_PHP_DEBUG_CORE + ".PHP_Project";
	public static final String PHP_File = ID_PHP_DEBUG_CORE + ".PHP_File";
	public static final String PHP_Exe = ID_PHP_DEBUG_CORE + ".PHP_EXE";
	public static final String ConditionEnabled = ID_PHP_DEBUG_CORE + ".ConditionEnabled";
	public static final String Condition = ID_PHP_DEBUG_CORE + ".Condition";
	public static final String PHPProcessType = ID_PHP_DEBUG_CORE + ".launching.PHPProcess";
    public static final String RUN_WITH_DEBUG_INFO = ID_PHP_DEBUG_CORE + ".RunWithDebugInfo";
    public static final String OPEN_IN_BROWSER = ID_PHP_DEBUG_CORE + ".OpenInBrowser";
    public static final String Include_Storage = ID_PHP_DEBUG_CORE + ".Include_Storage";
    public static final String Include_Storage_type = ID_PHP_DEBUG_CORE + ".Include_Storage_Type";
    public static final String Include_Storage_zip = ID_PHP_DEBUG_CORE + ".zip";
    public static final String Include_Storage_LFile = ID_PHP_DEBUG_CORE + ".lfile";
    public static final String Include_Storage_Project = ID_PHP_DEBUG_CORE + ".project";  
    public static final String Default_Server_Name = "Default PHP Web Server"; 
	public static final String PHPEXELaunchType = "org.eclipse.php.debug.core.launching.PHPExeLaunchConfigurationType";
	public static final String PHPServerLaunchType = "org.eclipse.php.server.core.launchConfigurationType";

	/**
	 * Status code indicating an unexpected internal error (value <code>150</code>).
	 */
	public static final int INTERNAL_ERROR = 150;
	
	/**
	 * Debug parameters initializer preferences key
	 */
	public static final String PHP_DEBUG_PARAMETERS_INITIALIZER = ID_PHP_DEBUG_CORE + ".debugParametersInitializer";

    public static final String DEBUG_PER_PROJECT = ID_PHP_DEBUG_CORE + ".use-project-settings"; //$NON-NLS-1$
    public static final String DEBUG_QUALIFIER = ID_PHP_DEBUG_CORE + ".Debug_Process_Preferences"; //$NON-NLS-1$
    public static final String PREFERENCE_PAGE_ID = "org.eclipse.php.debug.ui.preferences.PhpDebugPreferencePage";
    public static final String PROJECT_PAGE_ID = "org.eclipse.php.debug.ui.property.PhpDebugPreferencePage"; //$NON-NLS-1$
    
}
