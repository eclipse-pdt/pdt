package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugSessionStartedNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class SessionStartedNotificationHandler implements IDebugMessageHandler {

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		DebugSessionStartedNotification notification = (DebugSessionStartedNotification) message;
		IDebugHandler debugHandler = debugTarget.getRemoteDebugger().getDebugHandler();

		String fileName = notification.getFileName();
		String uri = notification.getUri();
		String query = notification.getQuery();
		String options = notification.getOptions();
		debugHandler.sessionStarted(fileName, uri, query, options);
	}
}
