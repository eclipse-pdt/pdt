package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
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
		IHyperlink fileLink = null;
		String fileName = debugError.getFullPathName();
		int lineNumber = debugError.getLineNumber();
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileName));
		if (file == null) {
			Object fileObject = null;
			if (ExternalFilesRegistry.getInstance().isEntryExist(new Path(fileName).toOSString())) {
				fileObject = ExternalFilesRegistry.getInstance().getFileEntry(new Path(fileName).toOSString());
			} else {
				// Search for a file in a Workspace
				fileObject  = (IFile)ResourcesPlugin.getWorkspace().getRoot().findMember(fileName);
			}
			if (fileObject != null) {
				fileLink = new PHPFileLink(fileObject, -1, -1, lineNumber);
			}
		} else {
			fileLink = new PHPFileLink(file, -1, -1, lineNumber);
		}
		return fileLink;
	}
}