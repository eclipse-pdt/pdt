/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

/**
 * Zend debugger owner settings constants.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerSettingsConstants {

	private ZendDebuggerSettingsConstants() {
		// Private constructor - constants only container
	}

	public static final String PROP_CLIENT_PORT = "clientPort"; //$NON-NLS-1$
	public static final String PROP_CLIENT_IP = "clientIp"; //$NON-NLS-1$
	public static final String PROP_RESPONSE_TIMEOUT = "responseTimeout"; //$NON-NLS-1$

	public static final String DEFAULT_CLIENT_PORT = "10037"; //$NON-NLS-1$
	public static final String DEFAULT_CLIENT_IP = "127.0.0.1"; //$NON-NLS-1$
	public static final String DEFAULT_RESPONSE_TIMEOUT = "60000"; //$NON-NLS-1$

}
