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

import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public interface IDebugMessageHandler {
	/**
	 * Process the relevant message
	 *
	 * @param message debug message
	 * @param debugTarget The {@link PHPDebugTarget}
	 */
	public void handle(IDebugMessage message, PHPDebugTarget debugTarget);
}
