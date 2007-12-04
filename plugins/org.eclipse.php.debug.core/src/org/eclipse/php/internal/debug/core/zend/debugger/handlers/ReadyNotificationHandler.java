package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.ReadyNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class ReadyNotificationHandler implements IDebugMessageHandler {

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		ReadyNotification readyNotification = (ReadyNotification) message;

		String currentFile = readyNotification.getFileName();
		int currentLine = readyNotification.getLineNumber();
		IDebugHandler debugHandler = debugTarget.getRemoteDebugger().getDebugHandler();

		debugHandler.ready(currentFile, currentLine);
	}
}
