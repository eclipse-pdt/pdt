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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.ParsingErrorNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class ParsingErrorNotificationHandler implements IDebugMessageHandler {

	private static final Pattern EVALD_CODE_PATTERN = Pattern
			.compile("(.*)\\((\\d+)\\) : eval\\(\\)'d code"); //$NON-NLS-1$

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		ParsingErrorNotification parseError = (ParsingErrorNotification) message;

		IDebugHandler debugHandler = debugTarget.getRemoteDebugger()
				.getDebugHandler();
		String errorText = parseError.getErrorText();

		int lineNumber = parseError.getLineNumber();
		int errorLevel = parseError.getErrorLevel();
		String fileName = parseError.getFileName();

		// Check whether the problematic file is actually eval() code:
		Matcher m = EVALD_CODE_PATTERN.matcher(fileName);
		if (m.matches()) {
			fileName = m.group(1);
			lineNumber = Integer.parseInt(m.group(2));
		}

		DebugError debugError = new DebugError(errorLevel, fileName,
				lineNumber, errorText);
		debugHandler.parsingErrorOccured(debugError);
	}
}
