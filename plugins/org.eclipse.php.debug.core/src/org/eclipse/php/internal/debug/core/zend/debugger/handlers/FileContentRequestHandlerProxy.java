/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
	@Override
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
	@Override
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
