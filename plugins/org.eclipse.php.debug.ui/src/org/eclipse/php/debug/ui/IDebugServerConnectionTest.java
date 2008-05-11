/*******************************************************************************
 * Copyright (c) 2008 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.debug.ui;

import org.eclipse.swt.widgets.Shell;

/**
 * Represents the object which performs the connection test with the Debug Servers
 * Implementors of this class should handle all cases of tests completions: Success, Failure AND Timeout
 * @see  org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestEvent
 * @author yaronm
 */
public interface IDebugServerConnectionTest {
	/**
	 * Performs a connection test on a Debug Server.
	 * Implementors should handle and display messages using the received shell object.
	 * @param url - The url of the debug server
	 * @param shell - The given shell in order to display result messages to the user
	 */
	public void testConnection(String url, Shell shell);
}
