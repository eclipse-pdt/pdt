/*******************************************************************************
 * Copyright (c) 2008 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.testConnection;

/**
 * A Listener to DebugServerTestEvent events
 * @author yaronm
 */
public interface IDebugServerTestListener {
	public void testEventReceived(DebugServerTestEvent e);
}
