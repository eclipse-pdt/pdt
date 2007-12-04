package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnectionThread;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.EndProcessFileNotification;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.StartProcessFileNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class StartProcessFileNotificationHandler implements IDebugMessageHandler {

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {

		// do everything we need in order to prepare for processing current file
		StartProcessFileNotification notification = (StartProcessFileNotification) message;
		String fileName = notification.getFileName();

		// send notification to tell debugger to continue processing file:
		DebugConnectionThread connectionThread = debugTarget.getRemoteDebugger().getConnectionThread();
		connectionThread.sendNotification(new EndProcessFileNotification());
	}
}
