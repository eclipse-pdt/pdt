/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.launching;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * A PHP web page launch shortcut.
 * 
 * @author Shalom Gibly
 */
public class PHPWebPageLaunchShortcut implements ILaunchShortcut2 {

	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			searchAndLaunch(((IStructuredSelection) selection).toArray(), mode,
					getPHPServerLaunchConfigType());
		}
	}

	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		IFile file = (IFile) input.getAdapter(IFile.class);
		if (file != null) {
			searchAndLaunch(new Object[] { file }, mode,
					getPHPServerLaunchConfigType());
		}
	}

	private ILaunchConfigurationType getPHPServerLaunchConfigType() {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm
				.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);
	}

	public static void searchAndLaunch(Object[] search, String mode,
			ILaunchConfigurationType configType) {
		int entries = search == null ? 0 : search.length;
		for (int i = 0; i < entries; i++) {
			try {
				String phpPathString = null;
				IProject project = null;
				Object obj = search[i];
				IResource res = null;
				if (obj instanceof IModelElement) {
					IModelElement elem = (IModelElement) obj;

					if (elem instanceof ISourceModule) {
						res = ((ISourceModule) elem).getCorrespondingResource();
					} else if (elem instanceof IType) {
						res = ((IType) elem).getUnderlyingResource();
					} else if (elem instanceof IMethod) {
						res = ((IMethod) elem).getUnderlyingResource();
					}
					if (res instanceof IFile) {
						obj = (IFile) res;
					}
				}

				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					res = file;
					project = file.getProject();
					IContentType contentType = Platform.getContentTypeManager()
							.getContentType(
									ContentTypeIdForPHP.ContentTypeID_PHP);
					if (contentType.isAssociatedWith(file.getName())) {
						phpPathString = file.getFullPath().toString();
					}
				}

				Server defaultServer = ServersManager.getDefaultServer(project);
				if (defaultServer == null) {
					PHPDebugPlugin.createDefaultPHPServer();
					defaultServer = ServersManager.getDefaultServer(project);
					if (defaultServer == null) {
						// Sould not happen
						throw new CoreException(new Status(IStatus.ERROR,
								PHPDebugUIPlugin.ID, IStatus.OK,
								Messages.PHPWebPageLaunchShortcut_0, null));
					}
				}

				String basePath = PHPProjectPreferences
						.getDefaultBasePath(project);

				boolean breakAtFirstLine = PHPProjectPreferences
						.getStopAtFirstLine(project);
				String selectedURL = null;
				boolean showDebugDialog = true;
				if (obj instanceof IScriptProject) {
					final PHPWebPageLaunchDialog dialog = new PHPWebPageLaunchDialog(
							mode, (IScriptProject) obj, basePath);
					final int open = dialog.open();
					if (open == PHPWebPageLaunchDialog.OK) {
						defaultServer = dialog.getServer();
						selectedURL = dialog.getPhpPathString();
						phpPathString = dialog.getFilename();
						breakAtFirstLine = dialog.isBreakAtFirstLine();
						showDebugDialog = false;
					} else {
						continue;
					}
				}

				if (phpPathString == null) {
					// Could not find target to launch
					throw new CoreException(new Status(IStatus.ERROR,
							PHPDebugUIPlugin.ID, IStatus.OK,
							Messages.launch_failure_no_target, null));
				}

				// Launch the app
				ILaunchConfiguration config = findLaunchConfiguration(project,
						phpPathString, selectedURL, defaultServer, mode,
						configType, breakAtFirstLine, showDebugDialog, res);
				if (config != null) {
					DebugUITools.launch(config, mode);
				} else {
					// Could not find launch configuration or the user
					// cancelled.
					// throw new CoreException(new Status(IStatus.ERROR,
					// PHPDebugUIPlugin.ID, IStatus.OK,
					// PHPDebugUIMessages.launch_failure_no_config, null));
				}
			} catch (CoreException ce) {
				final IStatus stat = ce.getStatus();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						ErrorDialog.openError(
								PHPDebugUIPlugin.getActiveWorkbenchShell(),
								Messages.launch_failure_msg_title,
								Messages.launch_failure_server_msg_text, stat);
					}
				});
			}
		}
	}

	/**
	 * Locate a configuration to relaunch for the given type. If one cannot be
	 * found, create one.
	 * 
	 * @param breakAtFirstLine
	 * @param res
	 * 
	 * @return a re-useable config or <code>null</code> if none
	 */
	static ILaunchConfiguration findLaunchConfiguration(IProject project,
			String fileName, String selectedURL, Server server, String mode,
			ILaunchConfigurationType configType, boolean breakAtFirstLine,
			boolean showDebugDialog, IResource res) {
		ILaunchConfiguration config = null;

		try {
			ILaunchConfiguration[] configs = DebugPlugin.getDefault()
					.getLaunchManager().getLaunchConfigurations(configType);

			int numConfigs = configs == null ? 0 : configs.length;
			for (int i = 0; i < numConfigs; i++) {
				String configuredServerName = configs[i].getAttribute(
						Server.NAME, (String) null);
				String configuredFileName = configs[i].getAttribute(
						Server.FILE_NAME, (String) null);

				if (configuredFileName != null)
					if (configuredFileName.equals(fileName)
							&& server.getName().equals(configuredServerName)) {
						config = configs[i].getWorkingCopy();
						break;
					}
			}

			if (config == null) {
				config = createConfiguration(project, fileName, selectedURL,
						server, configType, mode, breakAtFirstLine,
						showDebugDialog, res);
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return config;
	}

	static String computeContextRoot(String url, String fileName, Server server) {
		String serverBaseURL = server.getBaseURL();
		if (url.length() > serverBaseURL.length() + 1) {
			url = url.substring(serverBaseURL.length() + 1);
		} else if (url.length() == serverBaseURL.length()
				|| url.length() == serverBaseURL.length() + 1) {
			return ""; //$NON-NLS-1$
		}
		// Remove the project name from the file name
		if (fileName.length() > 0) {
			fileName = fileName.substring(1);
			int pathIndex = fileName.indexOf('/');
			if (pathIndex < 0) {
				fileName = ""; //$NON-NLS-1$
			} else {
				fileName = fileName.substring(pathIndex);
			}
		}
		if (url.length() > fileName.length()) {
			url = url.substring(0, url.length() - fileName.length());
		} else {
			return ""; //$NON-NLS-1$
		}
		return url;
	}

	static ILaunchConfiguration createConfiguration(IProject project,
			String fileName, String selectedURL, Server server,
			ILaunchConfigurationType configType, String mode,
			boolean breakAtFirstLine, boolean showDebugDialog, IResource res)
			throws CoreException {
		ILaunchConfiguration config = null;
		if (!FileUtils.resourceExists(fileName)) {
			return null;
		}

		String URL = null;
		if (selectedURL != null) {
			URL = selectedURL;
		} else {
			try {
				URL resolvedUrl = constractURL(res.getProject(),
						server.getBaseURL(), new Path(fileName));
				URL = resolvedUrl.toString();
			} catch (MalformedURLException e) {
				// safe as resolved URL is server.getBaseURL()
			}
		}

		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null,
				getNewConfigurationName(fileName));

		// Set the debugger ID and the configuration delegate for this launch
		// configuration
		String debuggerID = PHPProjectPreferences.getDefaultDebuggerID(project);
		wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerID);
		AbstractDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry
				.getDebuggerConfiguration(debuggerID);
		wc.setAttribute(
				PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
				debuggerConfiguration.getWebLaunchDelegateClass());

		wc.setAttribute(Server.NAME, server.getName());
		wc.setAttribute(Server.FILE_NAME, fileName);
		wc.setAttribute(Server.BASE_URL, URL);
		wc.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO,
				PHPDebugPlugin.getDebugInfoOption());
		wc.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER,
				PHPDebugPlugin.getOpenInBrowserOption());
		wc.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
				breakAtFirstLine);
		if (res != null) {
			wc.setMappedResources(new IResource[] { res });
		}
		// Display a dialog for selecting the URL.
		if (showDebugDialog) {
			String title = (ILaunchManager.DEBUG_MODE.equals(mode) ? Messages.PHPWebPageLaunchShortcut_1
					: (ILaunchManager.PROFILE_MODE.equals(mode) ? Messages.PHPWebPageLaunchShortcut_2
							: Messages.PHPWebPageLaunchShortcut_3));
			PHPWebPageURLLaunchDialog launchDialog = new PHPWebPageURLLaunchDialog(
					wc, server, title);
			launchDialog.setBlockOnOpen(true);
			if (launchDialog.open() != PHPWebPageURLLaunchDialog.OK) {
				return null;
			}
		}
		config = wc.doSave();
		return config;
	}

	private static URL constractURL(IProject project, String serverURL,
			Path path) throws MalformedURLException {

		URL server = new URL(serverURL);
		IPath url = new Path("/" + server.getPath()); //$NON-NLS-1$

		String basePath = getProjectsBasePath(project);
		boolean removeFirstSegment = true;
		if (basePath == null) {
			basePath = "/"; //$NON-NLS-1$
			removeFirstSegment = false;
		}
		url = url.append(basePath);
		if (removeFirstSegment)
			url = url.append(path.removeFirstSegments(1));
		else
			url = url.append(path);
		return new URL(server.getProtocol(), server.getHost(),
				server.getPort(), url.toString());

	}

	private static String getProjectsBasePath(IProject project) {
		if (project == null)
			return null;
		return PHPProjectPreferences.getDefaultBasePath(project);

	}

	/**
	 * Returns a name for a newly created launch configuration according to the
	 * given file name. In case the name generation fails, return the
	 * "New_configuration" string.
	 * 
	 * @param fileName
	 *            The original file name that this shortcut shoul execute.
	 * @return The new configuration name, or "New_configuration" in case it
	 *         fails for some reason.
	 */
	protected static String getNewConfigurationName(String fileName) {
		String configurationName = Messages.PHPWebPageLaunchShortcut_4;
		try {
			IPath path = Path.fromOSString(fileName);
			String fileExtention = path.getFileExtension();
			String lastSegment = path.lastSegment();
			if (lastSegment != null) {
				if (fileExtention != null) {
					lastSegment = lastSegment.replaceFirst("." + fileExtention, //$NON-NLS-1$
							""); //$NON-NLS-1$
				}
				configurationName = lastSegment;
			}
		} catch (Exception e) {
			Logger.log(Logger.WARNING_DEBUG,
					Messages.PHPWebPageLaunchShortcut_9 + fileName
							+ Messages.PHPWebPageLaunchShortcut_10, e);
		}
		return DebugPlugin.getDefault().getLaunchManager()
				.generateUniqueLaunchConfigurationNameFrom(configurationName);
	}

	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return null;
	}

	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse
	 * .ui.IEditorPart)
	 */
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return getLaunchableResource(editorpart.getEditorInput());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchShortcut2#getLaunchableResource(org.eclipse
	 * .jface.viewers.ISelection)
	 */
	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			if (ss.size() == 1) {
				Object element = ss.getFirstElement();
				if (element instanceof IAdaptable) {
					return getLaunchableResource((IAdaptable) element);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the resource containing the Java element associated with the
	 * given adaptable, or <code>null</code>.
	 * 
	 * @param adaptable
	 *            adaptable object
	 * @return containing resource or <code>null</code>
	 */
	private IResource getLaunchableResource(IAdaptable adaptable) {
		IModelElement je = (IModelElement) adaptable
				.getAdapter(IModelElement.class);
		if (je != null) {
			return je.getResource();
		}
		return null;
	}
}
