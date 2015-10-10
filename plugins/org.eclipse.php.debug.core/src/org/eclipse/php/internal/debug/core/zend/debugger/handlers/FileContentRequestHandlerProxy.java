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
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.IDebugFeatures;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

/**
 * This is a proxy to the relevant file content request handler depending on the
 * protocol ID.
 * 
 * @author michael
 */
public class FileContentRequestHandlerProxy implements IDebugRequestHandler {

	private IDebugRequestHandler handler;
	private PHPDebugTarget debugTarget;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler#handle(
	 * org.eclipse.php.debug.core.debugger.messages.IDebugMessage,
	 * org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget)
	 */
	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
		getHandler().handle(message, debugTarget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler#
	 * getResponseMessage()
	 */
	public IDebugResponseMessage getResponseMessage() {
		return getHandler().getResponseMessage();
	}

	private IDebugRequestHandler getHandler() {
		if (handler == null) {
			if (debugTarget.getRemoteDebugger().canDo(IDebugFeatures.START_PROCESS_FILE_NOTIFICATION)) {
				handler = new FileContentRequestCurrentHandler();
			} else {
				handler = new FileContentRequestStaleHandler();
			}
		}
		return handler;
	}

}
