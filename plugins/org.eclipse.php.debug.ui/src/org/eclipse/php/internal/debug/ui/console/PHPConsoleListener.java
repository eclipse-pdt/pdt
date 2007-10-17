package org.eclipse.php.internal.debug.ui.console;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.resources.ExternalFileWrapper;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.ui.console.IHyperlink;

public class PHPConsoleListener implements IPHPConsoleEventListener {

	private DebugConsoleMonitor fConsoleMonitor;
	private PHPHyperLink fPHPHyperLink;

	public PHPConsoleListener(DebugConsoleMonitor consoleMonitor, PHPHyperLink link) {
		super();
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
			if (ExternalFilesRegistry.getInstance().isEntryExist(fileName)) {
				fileObject = ExternalFilesRegistry.getInstance().getFileEntry(fileName);
			} else {
				// Search for a file in a Workspace
				file = (IFile)ResourcesPlugin.getWorkspace().getRoot().findMember(fileName);
				if (file.exists()) {
					fileObject = file;
				} else {
					PHPFileData fileData = null;
					try {
						fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(fileName);
					} catch (Exception e) {
					}
					if (fileData != null) {
						fileObject = fileData;
					} else {
						File externalFile = new File(fileName);
						if (externalFile.exists()) {
							fileObject = externalFile;
						} else {
							fileObject = ExternalFileWrapper.createFile(fileName);
						}
					}
				}
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