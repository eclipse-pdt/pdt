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
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebuggerErrorNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class DebugErrorNotificationHandler implements IDebugMessageHandler {

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		DebuggerErrorNotification parseError = (DebuggerErrorNotification) message;
		IDebugHandler debugHandler = debugTarget.getRemoteDebugger()
				.getDebugHandler();
		int errorLevel = parseError.getErrorLevel();
		DebugError debugError = new DebugError();
		String errorText = parseError.getErrorText();
		if (errorText != null && !errorText.equals("")) { //$NON-NLS-1$
			debugError.setErrorText(errorText);
		}

		debugError.setCode(errorLevel);
		debugHandler.debuggerErrorOccured(debugError);
	}
}
