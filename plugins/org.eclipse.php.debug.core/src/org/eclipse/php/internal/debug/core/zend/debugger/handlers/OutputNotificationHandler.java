/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.OutputNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class OutputNotificationHandler implements IDebugMessageHandler {

	@Override
	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		String output = ((OutputNotification) message).getOutput();

		IDebugHandler debugHandler = debugTarget.getRemoteDebugger().getDebugHandler();
		debugHandler.newOutput(output);
	}
}
