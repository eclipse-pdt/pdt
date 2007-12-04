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
		IDebugHandler debugHandler = debugTarget.getRemoteDebugger().getDebugHandler();
		int errorLevel = parseError.getErrorLevel();
		DebugError debugError = new DebugError();
		String errorText = parseError.getErrorText();
		if (errorText != null && !errorText.equals("")) {
			debugError.setErrorText(errorText);
		}

		debugError.setCode(errorLevel);
		debugHandler.debuggerErrorOccured(debugError);
	}
}
