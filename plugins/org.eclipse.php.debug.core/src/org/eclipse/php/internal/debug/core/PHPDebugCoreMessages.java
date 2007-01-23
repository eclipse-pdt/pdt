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
package org.eclipse.php.internal.debug.core;

import org.eclipse.osgi.util.NLS;

/**
 * Strings used by PHP Debugger Core
 * 
 */
public class PHPDebugCoreMessages extends NLS {
    private static final String BUNDLE_NAME = "org.eclipse.php.internal.debug.core.PHPDebugCoreMessages";//$NON-NLS-1$

    public static String LineBreakPointMessage_1;
    public static String ConditionalBreakPointMessage_1;
    public static String ConditionalBreakPointMessage_2;
    public static String DebuggerFileNotFound_1;
    public static String DebuggerDebugPortInUse_1;
    public static String DebuggerConnection_Problem_1;
    public static String DebuggerConnection_Problem_2;
    public static String DebuggerConnection_Problem_3;
    public static String DebuggerConnection_Failed_1;
    public static String DebuggerConnection_Exception_1;
    public static String Debugger_Unexpected_Error_1;
    public static String Debugger_ResourceNotFound;
    public static String Debugger_LaunchError_title;
    public static String Debugger_InvalidDebugResource;
    
    public static String configurationError;

    static {
        // load message values from bundle file
        NLS.initializeMessages(BUNDLE_NAME, PHPDebugCoreMessages.class);
    }

    private PHPDebugCoreMessages() {
        // cannot create new instance
    }
}
