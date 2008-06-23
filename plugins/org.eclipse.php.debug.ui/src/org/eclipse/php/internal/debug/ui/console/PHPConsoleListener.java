package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.ui.console.IHyperlink;

public class PHPConsoleListener implements IPHPConsoleEventListener {

	protected ILaunch fLaunch;
	protected DebugConsoleMonitor fConsoleMonitor;
	protected PHPHyperLink fPHPHyperLink;

	public void init(ILaunch launch, DebugConsoleMonitor consoleMonitor, PHPHyperLink link) {
		fLaunch = launch;
		fConsoleMonitor = consoleMonitor;
		fPHPHyperLink = link;
	}

	public void handleEvent(DebugError debugError) {
		IHyperlink link = createLink(debugError);
		String message = debugError.toString().trim();
		fPHPHyperLink.addLink(link, message, message.length() - debugError.getErrorTextLength());
		fConsoleMonitor.append(debugError.toString() + '\n');
	}

	protected IHyperlink createLink(DebugError debugError) {
		String fileName = debugError.getFullPathName();
		int lineNumber = debugError.getLineNumber();
		return new PHPFileLink(fileName, lineNumber);
	}
}