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
 * This interface is not meant to be implamented.
 */
public interface IDebugParametersKeys {

	public static final String PORT = "port";
	public static final String PASSIVE_DEBUG = "passiveDebug";
	public static final String WEB_SERVER_DEBUGGER = "webServerDebugger";
	public static final String FIRST_LINE_BREAKPOINT = "firstLineBreakpoint";
	public static final String OVERRIDE_FIRST_LINE_BREAKPOINT = "overrideFirstLineBreakpoint";
	public static final String PROTOCOL_ID = "protocolID";
	public static final String ORIGINAL_URL = "uriginalURL";
	public static final String SESSION_ID = "debugSessionID";
	public static final String TRANSFER_ENCODING = "debugTransferEncoding";
	public static final String PHP_INI_LOCATION = "PHPIniLocation";
}
