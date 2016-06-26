/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.debugger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.launching.ILaunchDelegateListener;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.*;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.debugger.ProcessCrashDetector;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Mock debugger configuration that gives a possibility to create and use PHP
 * launch configurations without any debugger attached to corresponding debugger
 * owner (PHP server or executable).
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class NoneDebuggerConfiguration extends AbstractDebuggerConfiguration {

	public static final String ID = "org.eclipse.php.debug.core.noneDebugger"; //$NON-NLS-1$
	private static final String NAME = "<none>"; //$NON-NLS-1$

	public static final class ScriptLaunchDelegate extends LaunchConfigurationDelegate {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.debug.core.model.LaunchConfigurationDelegate#
		 * preLaunchCheck (org.eclipse.debug.core.ILaunchConfiguration,
		 * java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
				throws CoreException {
			PHPexeItem phpExeItem = PHPLaunchUtilities.getPHPExe(configuration);
			if (phpExeItem == null) {
				displayError(MessageFormat.format(
						PHPDebugCoreMessages.NoneDebuggerConfiguration_There_is_no_PHP_runtime_environment,
						configuration.getName()));
				return false;
			}
			String phpExeString = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION,
					(String) null);
			if (phpExeString == null || !(new File(phpExeString)).exists()) {
				displayError(MessageFormat.format(
						PHPDebugCoreMessages.NoneDebuggerConfiguration_PHP_executable_file_is_invalid,
						configuration.getName()));
				return false;
			}
			String fileName = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, (String) null);
			if (fileName == null || !(new File(fileName)).exists()) {
				displayError(
						MessageFormat.format(PHPDebugCoreMessages.NoneDebuggerConfiguration_PHP_script_file_is_invalid,
								configuration.getName()));
				return false;
			}
			if ((mode.equals(ILaunchManager.DEBUG_MODE) || mode.equals(ILaunchManager.PROFILE_MODE))) {
				displayError(MessageFormat.format(
						PHPDebugCoreMessages.NoneDebuggerConfiguration_There_is_no_debugger_attached_for_PHP_executable,
						configuration.getName(), phpExeItem.getName()));
				ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
				wc.setAttribute(IDebugUIConstants.ATTR_PRIVATE, false);
				wc.doSave();
				return false;
			}
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(
		 * org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
		 * org.eclipse.debug.core.ILaunch,
		 * org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
				throws CoreException {
			// Check for previous launches.
			if (!PHPLaunchUtilities.notifyPreviousLaunches(launch)) {
				monitor.setCanceled(true);
				monitor.done();
				return;
			}
			String phpExeString = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION,
					(String) null);
			String phpIniPath = configuration.getAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
			String fileName = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, (String) null);
			IProject project = null;
			String file = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE, (String) null);
			if (file != null) {
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(file);
				if (resource != null) {
					project = resource.getProject();
				} else {
					String projectName = configuration.getAttribute(IPHPDebugConstants.PHP_Project, (String) null);
					if (projectName != null) {
						IProject resolved = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
						if (resolved != null && resolved.isAccessible()) {
							project = resolved;
						}
					}
				}
			}
			if (monitor.isCanceled()) {
				return;
			}
			IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 10);
			/*
			 * Locate the php.ini by using the attribute. If the attribute was
			 * null, try to locate an php.ini that exists next to the
			 * executable.
			 */
			File phpIni = (phpIniPath != null && new File(phpIniPath).exists()) ? new File(phpIniPath)
					: PHPINIUtil.findPHPIni(phpExeString);
			File tempIni = PHPINIUtil.prepareBeforeLaunch(phpIni, phpExeString, project);
			launch.setAttribute(IDebugParametersKeys.PHP_INI_LOCATION, tempIni.getAbsolutePath());
			// Resolve location
			IPath phpExe = new Path(phpExeString);
			String[] envp = DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);
			File phpExeFile = new File(phpExeString);
			String phpIniLocation = launch.getAttribute(IDebugParametersKeys.PHP_INI_LOCATION);
			// Determine PHP configuration file location:
			String phpConfigDir = phpExeFile.getParent();
			if (phpIniLocation != null && !phpIniLocation.equals("")) { //$NON-NLS-1$
				phpConfigDir = new File(phpIniLocation).getParent();
			}
			// Detect PHP SAPI type:
			String sapiType = null;
			String phpVersion = null;
			PHPexeItem[] items = PHPexes.getInstance().getAllItems();
			for (PHPexeItem item : items) {
				if (item.getExecutable().equals(phpExeFile)) {
					sapiType = item.getSapiType();
					phpVersion = item.getVersion();
					break;
				}
			}
			String[] args = PHPLaunchUtilities.getProgramArguments(launch.getLaunchConfiguration());
			String[] cmdLine = PHPLaunchUtilities.getCommandLine(launch.getLaunchConfiguration(), phpExeString,
					phpConfigDir, fileName, PHPexeItem.SAPI_CLI.equals(sapiType) ? args : null, phpVersion);
			// Set library search path:
			String libPath = PHPLaunchUtilities.getLibrarySearchPathEnv(phpExeFile.getParentFile());
			if (libPath != null) {
				String[] envpNew = new String[envp == null ? 1 : envp.length + 1];
				if (envp != null) {
					System.arraycopy(envp, 0, envpNew, 0, envp.length);
				}
				envpNew[envpNew.length - 1] = libPath;
				envp = envpNew;
			}
			if (monitor.isCanceled()) {
				return;
			}
			File workingDir = new File(fileName).getParentFile();
			Process process = workingDir.exists() ? DebugPlugin.exec(cmdLine, workingDir, envp)
					: DebugPlugin.exec(cmdLine, null, envp);
			// Attach a crash detector
			new Thread(new ProcessCrashDetector(launch, process)).start();
			IProcess runtimeProcess = null;
			// Add process type to process attributes
			Map<String, String> processAttributes = new HashMap<String, String>();
			String programName = phpExe.lastSegment();
			String extension = phpExe.getFileExtension();
			if (extension != null) {
				programName = programName.substring(0, programName.length() - (extension.length() + 1));
			}
			programName = programName.toLowerCase();
			processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);
			if (process != null) {
				subMonitor = new SubProgressMonitor(monitor, 90);
				subMonitor.beginTask(MessageFormat.format(PHPDebugCoreMessages.NoneDebuggerConfiguration_Launching,
						new Object[] { configuration.getName() }), IProgressMonitor.UNKNOWN);
				runtimeProcess = DebugPlugin.newProcess(launch, process, phpExe.toOSString(), processAttributes);
				if (runtimeProcess == null) {
					process.destroy();
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), 0, null, null));
				}
				subMonitor.done();
			}
			if (runtimeProcess != null) {
				runtimeProcess.setAttribute(IProcess.ATTR_CMDLINE, fileName);
			}
		}

	}

	public static final class WebLaunchDelegate extends LaunchConfigurationDelegate {

		// Opens launch URL an gracefully dies.
		private final class MockProcess extends Process {

			private String launchURL;

			private MockProcess(String launchURL) {
				this.launchURL = launchURL;
			}

			private OutputStream outputStream = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					// ignore
				}
			};
			private InputStream inputStream = new InputStream() {
				@Override
				public int read() throws IOException {
					return -1;
				}
			};
			private InputStream errorStream = new InputStream() {
				@Override
				public int read() throws IOException {
					return -1;
				}
			};

			@Override
			public int waitFor() throws InterruptedException {
				try {
					PHPDebugUtil.openLaunchURL(launchURL);
				} catch (DebugException e) {
					Logger.logException("Error while opening launch URL.", e); //$NON-NLS-1$
				}
				return 0;
			}

			@Override
			public OutputStream getOutputStream() {
				return outputStream;
			}

			@Override
			public InputStream getInputStream() {
				return inputStream;
			}

			@Override
			public InputStream getErrorStream() {
				return errorStream;
			}

			@Override
			public int exitValue() {
				throw new IllegalThreadStateException();
			}

			@Override
			public void destroy() {
				// ignore
			}

		}

		private static final String LAUNCH_LISTENERS_EXTENSION_ID = "org.eclipse.php.debug.core.phpLaunchDelegateListener"; //$NON-NLS-1$

		private List<ILaunchDelegateListener> preLaunchListeners = new ArrayList<ILaunchDelegateListener>();

		public WebLaunchDelegate() {
			registerLaunchListeners();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.debug.core.model.LaunchConfigurationDelegate#
		 * preLaunchCheck (org.eclipse.debug.core.ILaunchConfiguration,
		 * java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
				throws CoreException {
			Server server = PHPLaunchUtilities.getPHPServer(configuration);
			if (server == null) {
				displayError(MessageFormat.format(
						PHPDebugCoreMessages.NoneDebuggerConfiguration_There_is_no_PHP_server_specified,
						configuration.getName()));
				return false;
			}
			String fileName = configuration.getAttribute(Server.FILE_NAME, (String) null);
			if (fileName == null) {
				displayError(
						MessageFormat.format(PHPDebugCoreMessages.NoneDebuggerConfiguration_PHP_source_file_is_invalid,
								configuration.getName()));
				return false;
			}
			if ((mode.equals(ILaunchManager.DEBUG_MODE) || mode.equals(ILaunchManager.PROFILE_MODE))) {
				Server phpServer = PHPLaunchUtilities.getPHPServer(configuration);
				displayError(MessageFormat.format(
						PHPDebugCoreMessages.NoneDebuggerConfiguration_There_is_no_debugger_attached_for_PHP_server,
						configuration.getName(), phpServer.getName()));
				ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
				wc.setAttribute(IDebugUIConstants.ATTR_PRIVATE, false);
				wc.doSave();
				return false;
			}
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(
		 * org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
		 * org.eclipse.debug.core.ILaunch,
		 * org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
				throws CoreException {
			// Notify all listeners of a pre-launch event.
			int resultCode = notifyPreLaunch(configuration, mode, launch, monitor);
			if (resultCode != 0) { // cancel launch
				monitor.setCanceled(true);
				monitor.done();
				return; // canceled
			}
			// Check for previous launches
			if (!PHPLaunchUtilities.notifyPreviousLaunches(launch)) {
				monitor.setCanceled(true);
				monitor.done();
				return;
			}
			String fileName = configuration.getAttribute(Server.FILE_NAME, (String) null);
			// Get the project from the file name
			IPath filePath = new Path(fileName);
			IProject project = null;
			try {
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(filePath.segment(0));
			} catch (Throwable t) {
				// ignore
			}
			if (project == null) {
				return;
			}
			ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
			String projectLocation = project.getFullPath().toString();
			wc.setAttribute(IPHPDebugConstants.PHP_Project, projectLocation);
			// Set transfer encoding:
			wc.setAttribute(IDebugParametersKeys.TRANSFER_ENCODING, PHPProjectPreferences.getTransferEncoding(project));
			wc.setAttribute(IDebugParametersKeys.OUTPUT_ENCODING, PHPProjectPreferences.getOutputEncoding(project));
			wc.setAttribute(IDebugParametersKeys.PHP_DEBUG_TYPE, IDebugParametersKeys.PHP_WEB_PAGE_DEBUG);
			wc.doSave();
			final String launchURL = new String(configuration.getAttribute(Server.BASE_URL, "") //$NON-NLS-1$
					.getBytes());
			launch.setAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER, Boolean.toString(true));
			launch.setAttribute(IDebugParametersKeys.ORIGINAL_URL, launchURL);
			DebugPlugin.newProcess(launch, new MockProcess(launchURL), launchURL, new HashMap<String, String>());
		}

		protected int notifyPreLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch,
				IProgressMonitor monitor) {
			for (ILaunchDelegateListener listener : preLaunchListeners) {
				int returnCode = listener.preLaunch(configuration, mode, launch, monitor);
				if (returnCode != 0) {
					return returnCode;
				}
			}
			return 0;
		}

		/**
		 * Registers all pre-launch listeners.
		 */
		private void registerLaunchListeners() {
			IConfigurationElement[] config = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(LAUNCH_LISTENERS_EXTENSION_ID);
			try {
				for (IConfigurationElement e : config) {
					final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
					if (o instanceof ILaunchDelegateListener) {
						ISafeRunnable runnable = new ISafeRunnable() {
							public void run() throws Exception {
								ILaunchDelegateListener listener = (ILaunchDelegateListener) o;
								Assert.isNotNull(listener);
								preLaunchListeners.add(listener);
							}

							public void handleException(Throwable exception) {
								Logger.logException(exception);
							}
						};
						SafeRunner.run(runnable);
					}
				}
			} catch (CoreException ex) {
				Logger.logException(ex);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * openConfigurationDialog(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public void openConfigurationDialog(Shell parentShell) {
		// Not supported
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String id) {
		// Not supported
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getPort ()
	 */
	@Override
	public int getPort() {
		// Not supported
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getName ()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getScriptLaunchDelegateClass()
	 */
	@Override
	public String getScriptLaunchDelegateClass() {
		return ScriptLaunchDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getWebLaunchDelegateClass()
	 */
	@Override
	public String getWebLaunchDelegateClass() {
		return WebLaunchDelegate.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#
	 * getDebuggerId()
	 */
	@Override
	public String getDebuggerId() {
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration#save
	 * ()
	 */
	@Override
	public void save() {
		// Not supported
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 * AbstractDebuggerConfiguration#setPort(int)
	 */
	@Override
	public void setPort(int port) {
		// Not supported
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 * AbstractDebuggerConfiguration#getModuleId()
	 */
	@Override
	public String getModuleId() {
		// Not supported
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 * AbstractDebuggerConfiguration#applyDefaults()
	 */
	@Override
	public void applyDefaults() {
		// Not supported
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.debugger.
	 * AbstractDebuggerConfiguration#validate(PHPexeItem)
	 */
	@Override
	public IStatus validate(PHPexeItem item) {
		// Not supported
		return Status.OK_STATUS;
	}

	private static void displayError(final String message) {
		final Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(display.getActiveShell(), PHPDebugCoreMessages.Debugger_LaunchError_title,
						message);
			}
		});
	}

}
