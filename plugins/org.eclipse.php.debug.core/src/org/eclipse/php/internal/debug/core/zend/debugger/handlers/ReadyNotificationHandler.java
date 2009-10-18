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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.DebugStepFilterController;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.ReadyNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class ReadyNotificationHandler implements IDebugMessageHandler {

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		ReadyNotification readyNotification = (ReadyNotification) message;

		String currentFile = readyNotification.getFileName();
		try {
			if (debugTarget.isStepFiltersEnabled()) {
				String localPath = ((RemoteDebugger) debugTarget
						.getRemoteDebugger())
						.convertToLocalFilename(currentFile);
				if (DebugStepFilterController.getInstance().isFiltered(
						localPath)) {// file is filtered
					try {
						if (!isBreakPointExistInFile(debugTarget, currentFile)) {// file
																					// has
																					// a
																					// B.P
							// skip this step and continue the next 'Step Into'
							debugTarget.getRemoteDebugger().stepInto();
							return;
						}
					} catch (CoreException ce) {
						PHPDebugPlugin.log(ce);
					}
				}
			}
		} finally {
			int currentLine = readyNotification.getLineNumber();
			IDebugHandler debugHandler = debugTarget.getRemoteDebugger()
					.getDebugHandler();
			debugHandler.ready(currentFile, currentLine);
		}
	}

	// check if the currentFile has a breakpoint
	private boolean isBreakPointExistInFile(PHPDebugTarget debugTarget,
			String currentFile) throws CoreException {
		IBreakpoint[] bPoints = debugTarget.getBreakpointManager()
				.getBreakpoints(IPHPDebugConstants.ID_PHP_DEBUG_CORE);
		for (IBreakpoint bp : bPoints) {
			if (bp.isEnabled()
					&& FileUtils.checkIfEqualFilePaths(
							((PHPConditionalBreakpoint) bp)
									.getRuntimeBreakpoint().getFileName(),
							currentFile)) {
				return true;
			}
		}
		return false;
	}
}
