/**
 * 
 */
package org.eclipse.php.internal.debug.ui.console;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.resources.ExternalFileDecorator;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.sourcelookup.PHPSourceSearchEngine;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.ui.console.IHyperlink;

public class PHPConsoleListener implements IPHPConsoleEventListener {

	private DebugConsoleMonitor fConsoleMonitor;
	private IConsole fConsole;
	private ILaunch fLaunch;
	private PHPHyperLink fPHPHyperLink;
	// TODO fix constant in multiple files
	private String EditorID = "org.eclipse.php.editor";

	public PHPConsoleListener(DebugConsoleMonitor consoleMonitor, IConsole console, ILaunch launch, PHPHyperLink link) {
		super();
		fConsoleMonitor = consoleMonitor;
		fConsole = console;
		fLaunch = launch;
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
		try {
			String fileName = debugError.getFileName();
			int lineNumber = debugError.getLineNumber();
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileName));
			if (file == null) {
				Object fileObject = null;
				if (ExternalFilesRegistry.getInstance().isEntryExist(fileName)) {
					fileObject = ExternalFilesRegistry.getInstance().getFileEntry(fileName);
				} else {
					ILaunchConfiguration configuration = fLaunch.getLaunchConfiguration();
					String projectName = configuration.getAttribute(IPHPConstants.PHP_Project, (String) null);
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
					// Search for a file match.
					file = PHPSourceSearchEngine.getResource(fileName, project);
					// Modify the DebugError file name - For now it's disabled.
					// debugError.setFileName(file.getFullPath().toString());
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
								fileObject = ExternalFileDecorator.createFile(fileName);
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
		} catch (CoreException e) {
			Logger.logException("PHPConsoleListener unexpected error", e);
		}
		return fileLink;
	}

}