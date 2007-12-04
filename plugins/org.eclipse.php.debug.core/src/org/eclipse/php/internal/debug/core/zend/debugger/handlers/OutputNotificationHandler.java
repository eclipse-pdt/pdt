package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.OutputNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class OutputNotificationHandler implements IDebugMessageHandler {

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		String output = ((OutputNotification) message).getOutput();

		IDebugHandler debugHandler = debugTarget.getRemoteDebugger().getDebugHandler();
		debugHandler.newOutput(output);
	}
}
