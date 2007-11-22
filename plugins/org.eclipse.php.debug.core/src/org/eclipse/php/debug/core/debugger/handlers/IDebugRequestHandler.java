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
package org.eclipse.php.debug.core.debugger.handlers;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public interface IDebugRequestHandler extends IDebugMessageHandler {

	/**
	 * Return relevant response message that we need to send to the debugger
	 *
	 * @return message message of type IDebugResponseMessage
	 */
	IDebugResponseMessage getResponseMessage();
}
