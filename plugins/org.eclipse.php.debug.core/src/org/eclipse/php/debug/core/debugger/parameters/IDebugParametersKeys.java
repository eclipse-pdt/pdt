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
package org.eclipse.php.debug.core.debugger.parameters;

/**
 * An interface that holds the debug parameters keys. This interface is not
 * meant to be implemented.
 */
public interface IDebugParametersKeys {

	public static final String PORT = "port"; //$NON-NLS-1$
	public static final String PASSIVE_DEBUG = "passiveDebug"; //$NON-NLS-1$

	/**
	 * Type of debugger (Web server)
	 */
	public static final String WEB_SERVER_DEBUGGER = "webServerDebugger"; //$NON-NLS-1$

	/**
	 * Type of debugger (PHP executable)
	 */
	public static final String EXECUTABLE_LAUNCH = "executable_launch"; //$NON-NLS-1$

	public static final String FIRST_LINE_BREAKPOINT = "firstLineBreakpoint"; //$NON-NLS-1$
	public static final String EXE_CONFIG_PROGRAM_ARGUMENTS = "exeDebugArguments"; //$NON-NLS-1$
	public static final String PROTOCOL_ID = "protocolID"; //$NON-NLS-1$
	public static final String ORIGINAL_URL = "uriginalURL"; //$NON-NLS-1$
	public static final String SESSION_ID = "debugSessionID"; //$NON-NLS-1$
	public static final String TRANSFER_ENCODING = "debugTransferEncoding"; //$NON-NLS-1$
	public static final String OUTPUT_ENCODING = "debugOutputEncoding"; //$NON-NLS-1$
	public static final String PHP_INI_LOCATION = "PHPIniLocation"; //$NON-NLS-1$

	/**
	 * Type of the launch
	 */
	public static final String PHP_DEBUG_TYPE = "php_debug_type"; //$NON-NLS-1$
	public static final String PHP_EXE_SCRIPT_DEBUG = "php_exe_script_debug"; //$NON-NLS-1$
	public static final String PHP_WEB_SCRIPT_DEBUG = "php_web_script_debug"; //$NON-NLS-1$
	public static final String PHP_WEB_PAGE_DEBUG = "php_web_page_debug"; //$NON-NLS-1$

	/**
	 * This parameter key is depreciated. We now use only the
	 * {@value #FIRST_LINE_BREAKPOINT} in the launch configuration.
	 * 
	 * @deprecated
	 */
	public static final String OVERRIDE_FIRST_LINE_BREAKPOINT = "overrideFirstLineBreakpoint"; //$NON-NLS-1$
}
