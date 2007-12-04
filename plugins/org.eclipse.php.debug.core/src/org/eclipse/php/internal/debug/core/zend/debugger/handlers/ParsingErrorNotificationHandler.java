package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Path;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.ParsingErrorNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

public class ParsingErrorNotificationHandler implements IDebugMessageHandler {

	private static final Pattern EVALD_CODE_PATTERN = Pattern.compile("(.*)\\((\\d+)\\) : eval\\(\\)'d code"); //$NON-NLS-1$

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		ParsingErrorNotification parseError = (ParsingErrorNotification) message;

		IDebugHandler debugHandler = debugTarget.getRemoteDebugger().getDebugHandler();
		String errorText = parseError.getErrorText();

		String fileName = parseError.getFileName();
		try {
			Path errorFilePath = new Path(parseError.getFileName());
			if (errorFilePath.segmentCount() > 1 && errorFilePath.segment(errorFilePath.segmentCount() - 2).equalsIgnoreCase("Untitled_Documents")) {
				fileName = errorFilePath.lastSegment();
			}
		} catch (RuntimeException e) { // if new Path() fails - do nothing
		}

		int lineNumber = parseError.getLineNumber();
		int errorLevel = parseError.getErrorLevel();

		// Check whether the problematic file is actually eval() code:
		Matcher m = EVALD_CODE_PATTERN.matcher(fileName);
		if (m.matches()) {
			fileName = m.group(1);
			lineNumber = Integer.parseInt(m.group(2));
		}

		DebugError debugError = new DebugError(errorLevel, fileName, lineNumber, errorText);
		debugHandler.parsingErrorOccured(debugError);
	}
}
