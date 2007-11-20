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
package org.eclipse.php.debug.core.debugger.parameters;

/**
 * An interface that holds the debug parameters keys.
 * This interface is not meant to be implemented.
 */
public interface IDebugParametersKeys {

	public static final String PORT = "port";
	public static final String PASSIVE_DEBUG = "passiveDebug";
	
	/**
	 * Type of debugger (Web server)
	 */
	public static final String WEB_SERVER_DEBUGGER = "webServerDebugger";
	
	/**
	 * Type of debugger (PHP executable)
	 */
	public static final String EXECUTABLE_LAUNCH = "executable_launch";
	
	public static final String FIRST_LINE_BREAKPOINT = "firstLineBreakpoint";
	public static final String EXE_CONFIG_PROGRAM_ARGUMENTS = "exeDebugArguments";
	public static final String PROTOCOL_ID = "protocolID";
	public static final String ORIGINAL_URL = "uriginalURL";
	public static final String SESSION_ID = "debugSessionID";
	public static final String TRANSFER_ENCODING = "debugTransferEncoding";
	public static final String OUTPUT_ENCODING = "debugOutputEncoding";
	public static final String PHP_INI_LOCATION = "PHPIniLocation";
	
	/**
	 * Type of the launch
	 */
	public static final String PHP_DEBUG_TYPE = "php_debug_type";
	public static final String PHP_EXE_SCRIPT_DEBUG = "php_exe_script_debug";
	public static final String PHP_WEB_SCRIPT_DEBUG = "php_web_script_debug";
	public static final String PHP_WEB_PAGE_DEBUG = "php_web_page_debug";
	
	/**
	 * This parameter key is depreciated.
	 * We now use only the {@value #FIRST_LINE_BREAKPOINT} in the 
	 * launch configuration. 
	 * @deprecated
	 */
	public static final String OVERRIDE_FIRST_LINE_BREAKPOINT = "overrideFirstLineBreakpoint";
}
