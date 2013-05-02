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
 *     Aptana Inc.
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.launching;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.*;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnectionThread;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.tunneling.SSHTunnel;
import org.eclipse.php.internal.server.core.tunneling.SSHTunnelFactory;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;

/**
 * Utilities that are shared to all the PHP launches.
 */
public class PHPLaunchUtilities {

	public static final String ID_PHPDebugOutput = "org.eclipse.debug.ui.PHPDebugOutput"; //$NON-NLS-1$
	public static final String ID_PHPBrowserOutput = "org.eclipse.debug.ui.PHPBrowserOutput"; //$NON-NLS-1$
	private static DebuggerDelayProgressMonitorDialog progressDialog;

	/**
	 * Display the Debug Output view in case it's hidden or not initialized. In
	 * case where the Browser Output view is visible, nothing will happen and
	 * the Browser Output will remain as the visible view during the debug
	 * session.
	 * 
	 * Note that the behavior given by this function is mainly needed when we
	 * are in a PHP Perspective (not debug) and a session without a breakpoint
	 * was launched. So in this case a 'force' output display is triggered.
	 * 
	 * This function also take into account the
	 * PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS flag and does not show the
	 * debug views in case it was not chosen from the preferences.
	 */
	public static void showDebugView() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		if (!prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS)) {
			return;
		}
		// Get the page through a UI thread! Otherwise, it wont work...
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				if (page != null) {
					try {
						IViewPart debugOutputPart = page
								.findView("org.eclipse.debug.ui.PHPDebugOutput"); //$NON-NLS-1$
						IViewPart browserOutputPart = page
								.findView("org.eclipse.debug.ui.PHPBrowserOutput"); //$NON-NLS-1$

						// Test if the Debug Output view is alive and visible.
						boolean shouldShowDebug = false;
						if (debugOutputPart == null
								|| !page.isPartVisible(debugOutputPart)) {
							shouldShowDebug = true;
						}

						// If the Browser Output is visible, do not switch to
						// the Debug Output.
						if (browserOutputPart != null
								&& page.isPartVisible(browserOutputPart)) {
							shouldShowDebug = false;
						}

						if (shouldShowDebug) {
							page.showView("org.eclipse.debug.ui.PHPDebugOutput"); //$NON-NLS-1$
						}
					} catch (Exception e) {
						Logger.logException(
								"Error switching to the Debug Output view", e); //$NON-NLS-1$
					}
				}
			}
		});
	}

	/**
	 * Returns true if the is at least one active PHP debug session.
	 * 
	 * @return True, if there is an active debug session; False, otherwise.
	 */
	public static boolean hasPHPDebugLaunch() {
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager()
				.getLaunches();
		for (int i = 0; i < launches.length; i++) {
			if (!launches[i].isTerminated()
					&& ILaunchManager.DEBUG_MODE.equals(launches[i]
							.getLaunchMode())
					&& launches[i].getDebugTarget() instanceof PHPDebugTarget) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Notify the existence of a previous PHP debug session in case the user
	 * launched a new session.
	 * 
	 * @param newLaunchConfiguration
	 * @param newLaunch
	 * @return True, if the launch can be continued; False, otherwise.
	 * @throws CoreException
	 */
	public static boolean notifyPreviousLaunches(ILaunch newLaunch)
			throws CoreException {
		// In case the new launch is not a debug launch, we have no problem.
		if (!ILaunchManager.DEBUG_MODE.equals(newLaunch.getLaunchMode())) {
			return true;
		}
		// If there are no active debug launches, return true and continue with
		// the new launch.
		if (!hasPHPDebugLaunch()) {
			return true;
		}

		// Check whether we should ask the user.
		final IPreferenceStore store = PHPUiPlugin.getDefault()
				.getPreferenceStore();
		String option = store
				.getString(PreferenceConstants.ALLOW_MULTIPLE_LAUNCHES);
		if (MessageDialogWithToggle.ALWAYS.equals(option)) {
			// If always, then we should always allow the launch
			return true;
		}
		if (MessageDialogWithToggle.NEVER.equals(option)) {
			// We should never allow the launch, so display a message describing
			// the situation.
			final Display disp = Display.getDefault();
			disp.syncExec(new Runnable() {
				public void run() {
					MessageDialog.openInformation(
							disp.getActiveShell(),
							PHPDebugCoreMessages.PHPLaunchUtilities_phpLaunchTitle,
							PHPDebugCoreMessages.PHPLaunchUtilities_activeLaunchDetected);
				}
			});
			return false;
		}

		final DialogResultHolder resultHolder = new DialogResultHolder();
		final Display disp = Display.getDefault();
		disp.syncExec(new Runnable() {
			public void run() {
				// Display a dialog to notify the existence of a previous active
				// launch.
				MessageDialogWithToggle m = MessageDialogWithToggle.openYesNoQuestion(
						disp.getActiveShell(),
						PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
						PHPDebugCoreMessages.PHPLaunchUtilities_multipleLaunchesPrompt,
						PHPDebugCoreMessages.PHPLaunchUtilities_rememberDecision,
						false, store,
						PreferenceConstants.ALLOW_MULTIPLE_LAUNCHES);
				resultHolder.setReturnCode(m.getReturnCode());
			}
		});
		switch (resultHolder.getReturnCode()) {
		case IDialogConstants.YES_ID:
		case IDialogConstants.OK_ID:
			return true;
		case IDialogConstants.NO_ID:
			return false;
		}
		return true;
	}

	/**
	 * Switch from the PHP debug perspective to the PHP perspective (in case we
	 * are not using it already). This method is called when the last active PHP
	 * debug session was terminated.
	 */
	public static void switchToPHPPerspective() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(new Runnable() {
			public void run() {
				String perspectiveID = PHPUiPlugin.PERSPECTIVE_ID;
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

				if (shouldSwitchToPHPPerspective(perspectiveID)) {
					try {
						workbench.showPerspective(perspectiveID, window);
					} catch (WorkbenchException e) {
						PHPUiPlugin.log(e);
					}
				}
			}
		});
	}

	// Returns true iff the PHP perspective should be displayed.
	private static boolean shouldSwitchToPHPPerspective(String perspectiveID) {
		// check whether we should ask the user.
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		String option = store
				.getString(PreferenceConstants.SWITCH_BACK_TO_PHP_PERSPECTIVE);
		if (MessageDialogWithToggle.ALWAYS.equals(option)) {
			return true;
		}
		if (MessageDialogWithToggle.NEVER.equals(option)) {
			return false;
		}

		// Check whether the desired perspective is already active.
		IPerspectiveRegistry registry = PlatformUI.getWorkbench()
				.getPerspectiveRegistry();
		IPerspectiveDescriptor perspective = registry
				.findPerspectiveWithId(perspectiveID);
		if (perspective == null) {
			return false;
		}

		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IPerspectiveDescriptor current = page.getPerspective();
				if (current != null && current.getId().equals(perspectiveID)) {
					return false;
				}
			}

			// Ask the user whether to switch
			MessageDialogWithToggle m = MessageDialogWithToggle
					.openYesNoQuestion(
							window.getShell(),
							PHPDebugCoreMessages.PHPLaunchUtilities_PHPPerspectiveSwitchTitle,
							NLS.bind(
									PHPDebugCoreMessages.PHPLaunchUtilities_PHPPerspectiveSwitchMessage,
									new String[] { perspective.getLabel() }),
							PHPDebugCoreMessages.PHPLaunchUtilities_rememberDecision,
							false, store,
							PreferenceConstants.SWITCH_BACK_TO_PHP_PERSPECTIVE);

			int result = m.getReturnCode();
			switch (result) {
			case IDialogConstants.YES_ID:
			case IDialogConstants.OK_ID:
				return true;
			case IDialogConstants.NO_ID:
				return false;
			}
		}
		return false;
	}

	/**
	 * Make all the necessary checks to see if the current launch can be
	 * launched with regards to the previous launches that has 'debug all pages'
	 * attribute.
	 * 
	 * @throws CoreException
	 */
	public static boolean checkDebugAllPages(
			final ILaunchConfiguration newLaunchConfiguration,
			final ILaunch newLaunch) throws CoreException {
		// If the remote debugger already supports multiple debugging with the
		// 'debug all pages',
		// we do not have to do a thing and we can return.
		if (PHPDebugPlugin.supportsMultipleDebugAllPages()) {
			return true;
		}
		// Make sure we set the attributes on the ILaunch since the
		// ILaunchConfiguration reference never changes, while the
		// ILaunch is created for each launch.
		newLaunch.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
				newLaunchConfiguration.getAttribute(
						IPHPDebugConstants.DEBUGGING_PAGES,
						IPHPDebugConstants.DEBUGGING_ALL_PAGES));
		checkAutoRemoveLaunches();
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager()
				.getLaunches();
		boolean hasContiniousLaunch = false;
		// check for a launch that has a 'debug all pages' or 'start debug from'
		// attribute
		for (int i = 0; !hasContiniousLaunch && i < launches.length; i++) {
			ILaunch launch = launches[i];
			if (launch != newLaunch
					&& ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode())) {
				if (isDebugAllPages(launch) || isStartDebugFrom(launch)) {
					hasContiniousLaunch = true;
				}
			}
		}
		// Check if the new launch is 'debug all pages'

		boolean newLaunchIsDebug = ILaunchManager.DEBUG_MODE.equals(newLaunch
				.getLaunchMode());
		final boolean newIsDebugAllPages = newLaunchIsDebug
				&& isDebugAllPages(newLaunch);
		final boolean newIsStartDebugFrom = newLaunchIsDebug
				&& isStartDebugFrom(newLaunch);
		final boolean fHasContiniousLaunch = hasContiniousLaunch;

		if ((fHasContiniousLaunch || newIsDebugAllPages || newIsStartDebugFrom)
				&& launches.length > 1) {
			final DialogResultHolder resultHolder = new DialogResultHolder();
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					// TODO - Advanced message dialog with 'don't show this
					// again' check.
					if (fHasContiniousLaunch) {
						resultHolder.setResult(MessageDialog
								.openConfirm(
										Display.getDefault().getActiveShell(),
										PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
										PHPDebugCoreMessages.PHPLaunchUtilities_0)); 
					} else {
						if (newIsDebugAllPages) {
							resultHolder.setResult(MessageDialog
									.openConfirm(
											Display.getDefault()
													.getActiveShell(),
											PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
											PHPDebugCoreMessages.PHPLaunchUtilities_7)); 
						} else {
							// newIsStartDebugFrom == true
							resultHolder.setResult(MessageDialog
									.openConfirm(
											Display.getDefault()
													.getActiveShell(),
											PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
											PHPDebugCoreMessages.PHPLaunchUtilities_8)); 
						}
					}
					if (resultHolder.getResult()) {
						// disable the auto remove launches for the next launch
						PHPDebugPlugin.setDisableAutoRemoveLaunches(true);
						// manually remove the old launches and continue this
						// launch
						removeAndTerminateOldLaunches(newLaunch);
					} else {
						// Remove the latest launch
						DebugPlugin.getDefault().getLaunchManager()
								.removeLaunch(newLaunch);
					}
				}
			});
			return resultHolder.getResult();
		} else {
			if (newIsDebugAllPages || newIsStartDebugFrom) {
				PHPDebugPlugin.setDisableAutoRemoveLaunches(true);
			} else {
				// There are no other launches AND the new launch doesn't have a
				// debug-all-pages.
				PHPDebugPlugin.setDisableAutoRemoveLaunches(!PHPDebugPlugin
						.getDefault().getInitialAutoRemoveLaunches());
				// This will manually remove the old launches if needed
				DebugUIPlugin.getDefault().getLaunchConfigurationManager()
						.launchAdded(newLaunch);
			}
			return true;
		}
	}

	// In case that there are no launches, make sure to enable the auto-remove
	// old launches in case it's needed
	private static void checkAutoRemoveLaunches() {
		if (DebugPlugin.getDefault().getLaunchManager().getLaunches().length == 1) {
			PHPDebugPlugin.setDisableAutoRemoveLaunches(false);
		}
	}

	/**
	 * Returns if the given launch configuration holds an attribute for 'debug
	 * all pages'.
	 * 
	 * @param launchConfiguration
	 *            An {@link ILaunchConfiguration}
	 * @return True, if the configuration holds an attribute for 'debug all
	 *         pages'.
	 * @throws CoreException
	 */
	public static boolean isDebugAllPages(ILaunch launch) throws CoreException {
		String attribute = launch
				.getAttribute(IPHPDebugConstants.DEBUGGING_PAGES);
		return attribute != null
				&& attribute.equals(IPHPDebugConstants.DEBUGGING_ALL_PAGES);
	}

	/**
	 * Returns if the given launch configuration holds an attribute for 'start
	 * debug from'.
	 * 
	 * @param launchConfiguration
	 *            An {@link ILaunchConfiguration}
	 * @return True, if the configuration holds an attribute for 'start debug
	 *         from'.
	 * @throws CoreException
	 */
	public static boolean isStartDebugFrom(ILaunch launch) throws CoreException {
		String attribute = launch
				.getAttribute(IPHPDebugConstants.DEBUGGING_PAGES);
		return attribute != null
				&& attribute.equals(IPHPDebugConstants.DEBUGGING_START_FROM);
	}

	// terminate and remove all the existing launches accept for the given new
	// launch.
	private static void removeAndTerminateOldLaunches(ILaunch newLaunch) {
		ILaunchManager lManager = DebugPlugin.getDefault().getLaunchManager();
		Object[] launches = lManager.getLaunches();
		for (Object element : launches) {
			ILaunch launch = (ILaunch) element;
			if (launch != newLaunch) {
				if (!launch.isTerminated()) {
					try {
						launch.terminate();
					} catch (DebugException e) {
						Logger.logException(e);
					}
				}
				lManager.removeLaunch(launch);
			}
		}
	}

	/**
	 * Display a wait window, indicating the user that the debug session is in
	 * progress and the PDT is waiting for the debugger's response. Once a
	 * response arrives, the {@link #hideWaitForDebuggerMessage()} should be
	 * called to remove the window. In case a response does not arrive, there is
	 * a good chance that the {@link #showLaunchErrorMessage()} should be
	 * called.
	 * 
	 * @param debugConnectionThread
	 * @see #hideWaitForDebuggerMessage()
	 * @see #showLaunchErrorMessage()
	 */
	public static void showWaitForDebuggerMessage(
			final DebugConnectionThread debugConnectionThread) {
		if (progressDialog != null) {
			// Allow only one progress indicator
			return;
		}
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				progressDialog = new DebuggerDelayProgressMonitorDialog();
				if (progressDialog.open() == Window.CANCEL) {
					debugConnectionThread.closeConnection();
				}
				progressDialog = null;
			}
		});
	}

	/**
	 * Hides the progress indicator that appears when user is waiting for the
	 * debugger to response.
	 * 
	 * @see #showWaitForDebuggerMessage()
	 */
	public static void hideWaitForDebuggerMessage() {
		if (progressDialog != null) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if (progressDialog != null) {
						progressDialog.close();
					}
				}
			});
			progressDialog = null;
		}
	}

	/**
	 * Display a standard error message to indicating an fatal error detected
	 * while staring a debug session. A fatal error occurs when the remote
	 * debugger does not exist or has a different version.
	 */
	public static void showLaunchErrorMessage() {
		showDebuggerErrorMessage(PHPDebugCoreMessages.Debugger_Launch_Error,
				PHPDebugCoreMessages.Debugger_Error_Message);
	}

	/**
	 * Display an error message to indicating an fatal error detected while
	 * staring a debug session. A fatal error occurs when the remote debugger
	 * does not exist or has a different version.
	 * 
	 * @param errorMessage
	 *            The message to display.
	 */
	public static void showLaunchErrorMessage(final String errorMessage) {
		showDebuggerErrorMessage(PHPDebugCoreMessages.Debugger_Launch_Error,
				errorMessage);
	}

	/**
	 * Display an error message to indicating an fatal error detected while
	 * staring a debug session. A fatal error occurs when the remote debugger
	 * does not exist or has a different version.
	 * 
	 * @param title
	 *            The error message title.
	 * @param errorMessage
	 *            The message to display.
	 */
	public static void showDebuggerErrorMessage(final String title,
			final String errorMessage) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(),
						title, errorMessage);
			}
		});
	}

	/*
	 * A class used to hold the message dialog results
	 */
	private static class DialogResultHolder {
		private int returnCode;
		private boolean result;

		public boolean getResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public int getReturnCode() {
			return returnCode;
		}

		public void setReturnCode(int returnCode) {
			this.returnCode = returnCode;
		}
	}

	private static class DebuggerDelayProgressMonitorDialog extends
			ProgressMonitorDialog {

		public DebuggerDelayProgressMonitorDialog() {
			super(null);
			setBlockOnOpen(true);
			setCancelable(true);
		}

		protected void createCancelButton(Composite parent) {
			cancel = createButton(parent, IDialogConstants.CANCEL_ID,
					PHPDebugCoreMessages.PHPLaunchUtilities_terminate, true);
			if (arrowCursor == null) {
				arrowCursor = new Cursor(cancel.getDisplay(), SWT.CURSOR_ARROW);
			}
			cancel.setCursor(arrowCursor);
			setOperationCancelButtonEnabled(enableCancelButton);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.dialogs.ProgressMonitorDialog#createDialogArea(
		 * org.eclipse.swt.widgets.Composite)
		 */
		protected Control createDialogArea(Composite parent) {
			Control c = super.createDialogArea(parent);
			getProgressMonitor().beginTask(
					PHPDebugCoreMessages.PHPLaunchUtilities_waitingForDebugger,
					IProgressMonitor.UNKNOWN);
			return c;
		}
	}

	/**
	 * Opens the launch configuration dialog on the given launch configuration
	 * in the given mode.
	 * 
	 * @param configuration
	 *            An {@link ILaunchConfiguration}
	 * @param mode
	 *            The launch mode (Run/Debug)
	 */
	public static void openLaunchConfigurationDialog(
			final ILaunchConfiguration configuration, final String mode) {
		// Run it on the UI thread
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				ILaunchConfiguration conf = configuration;
				try {
					// The DebugUIPlugin creates stand-in launches with copied
					// configurations
					// while a launch is waiting for a build. These copied
					// configurations
					// have an attribute that points to the config that the user
					// is really
					// launching.
					String underlyingHandle = configuration.getAttribute(
							DebugUIPlugin.ATTR_LAUNCHING_CONFIG_HANDLE, ""); //$NON-NLS-1$
					if (underlyingHandle.length() > 0) {
						ILaunchConfiguration underlyingConfig = DebugPlugin
								.getDefault().getLaunchManager()
								.getLaunchConfiguration(underlyingHandle);
						if (underlyingConfig != null) {
							conf = underlyingConfig;
						}
					}
				} catch (CoreException e) {
				}
				ILaunchGroup group = DebugUITools.getLaunchGroup(conf, mode);
				if (group != null) {
					DebugUITools.openLaunchConfigurationDialog(Display
							.getDefault().getActiveShell(), conf, group
							.getIdentifier(), null);
				}
			}
		});
	}

	/**
	 * Returns an array of system environment attributes from the given launch
	 * configuration. If empty, then the current native environment attributes
	 * will be returned. From this we append any additional environment
	 * variables we might want to add. Note: Additional environments may
	 * override the native environment attributes, but disregarded when an
	 * equivalent launch configuration attribute is set for the given launch.
	 * 
	 * @param configuration
	 *            the launch configuration
	 * @param additionalEnv
	 *            additional environment strings
	 * @return the complete environment
	 * @throws CoreException
	 *             rethrown exception
	 */
	@SuppressWarnings("unchecked")
	public static String[] getEnvironment(ILaunchConfiguration configuration,
			String[] additionalEnv) throws CoreException {

		if (additionalEnv == null) {
			additionalEnv = new String[0];
		}
		Map<String, String> additionalEnvMap = asAttributesMap(additionalEnv);

		String[] totalEnv = null;
		String[] launchConfigurationEnvironment = DebugPlugin.getDefault()
				.getLaunchManager().getEnvironment(configuration);

		if (launchConfigurationEnvironment != null) {
			// The launch configuration tab has environment settings.
			Map<String, String> envMap = asAttributesMap(launchConfigurationEnvironment);
			// Make sure that these settings override any additional settings,
			// so add them to the
			// additional environments map.
			envMap.putAll(additionalEnvMap);
			totalEnv = asAttributesArray(envMap);
		} else {
			// We have nothing in the environment tab, so we need to set
			// currentEnv ourselves to the current environment
			Map<String, String> nativeEnvironment = DebugPlugin.getDefault()
					.getLaunchManager().getNativeEnvironmentCasePreserved();
			// Make sure we override any native environment with the additional
			// environment values
			nativeEnvironment.putAll(additionalEnvMap);
			totalEnv = asAttributesArray(nativeEnvironment);
		}
		return totalEnv;
	}

	/*
	 * Returns a map of Strings parsed from a given array of attributes in a
	 * form of 'key=value'.
	 */
	public static Map<String, String> asAttributesMap(String[] attributesArray) {
		Map<String, String> map = new HashMap<String, String>();
		if (attributesArray == null) {
			return map;
		}
		for (String attribute : attributesArray) {
			try {
				int index = attribute.indexOf('=');
				map.put(attribute.substring(0, index),
						attribute.substring(index + 1));
			} catch (Exception e) {
				Logger.logException("Error while parsing launch attribute '" //$NON-NLS-1$
						+ attribute + '\'', e);
			}
		}
		return map;
	}

	/*
	 * Returns an array of Strings in the form of 'key=value'
	 */
	public static String[] asAttributesArray(Map<String, String> attributesMap) {
		String[] attributes = new String[attributesMap.size()];
		int index = 0;
		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			attributes[index++] = entry.getKey() + '=' + entry.getValue();
		}
		return attributes;
	}

	/**
	 * Returns PHP CGI related parameters needed for launch
	 * 
	 * @param fileName
	 * @param query
	 * @param phpConfigDir
	 * @param phpExeDir
	 * @return A map of environment settings.
	 */
	public static Map<String, String> getPHPCGILaunchEnvironment(
			String fileName, String query, String phpConfigDir,
			String phpExeDir, String[] scriptArguments) {
		Map<String, String> env = new HashMap<String, String>();
		env.put("REQUEST_METHOD", "GET"); //$NON-NLS-1$ //$NON-NLS-2$
		env.put("SCRIPT_FILENAME", fileName); //$NON-NLS-1$
		env.put("SCRIPT_NAME", fileName); //$NON-NLS-1$
		env.put("PATH_TRANSLATED", fileName); //$NON-NLS-1$
		env.put("PATH_INFO", fileName); //$NON-NLS-1$

		// Build query string
		StringBuilder queryStringBuf = new StringBuilder(query);
		queryStringBuf.append("&debug_host=127.0.0.1"); //$NON-NLS-1$
		if (scriptArguments != null) {
			for (String arg : scriptArguments) {
				queryStringBuf.append('&').append(arg);
			}
		}
		env.put("QUERY_STRING", queryStringBuf.toString()); //$NON-NLS-1$
		env.put("REDIRECT_STATUS", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		env.put("PHPRC", phpConfigDir); //$NON-NLS-1$

		appendLibrarySearchPathEnv(env, new File(phpExeDir));

		return env;
	}

	public static Map<String, String> getPHP54BuildinServerLaunchEnvironment(
			String fileName, String query, String phpConfigDir,
			String phpExeDir, String[] scriptArguments) {
		Map<String, String> env = new HashMap<String, String>();
		env.put("REQUEST_METHOD", "GET"); //$NON-NLS-1$ //$NON-NLS-2$
		//		env.put("SCRIPT_FILENAME", fileName);
		//		env.put("SCRIPT_NAME", fileName);
		//		env.put("PATH_TRANSLATED", fileName);
		//		env.put("PATH_INFO", fileName);

		// Build query string
		StringBuilder queryStringBuf = new StringBuilder(query);
		queryStringBuf.append("&debug_host=127.0.0.1"); //$NON-NLS-1$
		if (scriptArguments != null) {
			for (String arg : scriptArguments) {
				queryStringBuf.append('&').append(arg);
			}
		}
		env.put("QUERY_STRING", queryStringBuf.toString()); //$NON-NLS-1$
		env.put("REDIRECT_STATUS", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		env.put("PHPRC", phpConfigDir); //$NON-NLS-1$

		appendLibrarySearchPathEnv(env, new File(phpExeDir));

		return env;
	}

	/**
	 * Appends needed environment variable that says where to look for 3rd party
	 * libraries depending on the OS.
	 * 
	 * @param env
	 *            Hash map to append environment variable to
	 * @param phpExeDir
	 *            Directory handle where PHP.exe is located
	 */
	public static void appendLibrarySearchPathEnv(Map<String, String> env,
			File phpExeDir) {
		String variable = getLibrarySearchEnvVariable();
		if (variable == null) {
			return;
		}
		String value = getLibrarySearchEnvValue(variable, phpExeDir, false);
		env.put(variable, value);
	}

	/**
	 * Returns needed environment variable that says where to look for 3rd party
	 * libraries depending on the OS.
	 * 
	 * @param phpExeDir
	 *            Directory handle where PHP.exe is located
	 * @return string containing variable=value for appending it to the process
	 *         environment vars array
	 */
	public static String getLibrarySearchPathEnv(File phpExeDir, boolean quoted) {
		String variable = getLibrarySearchEnvVariable();
		if (variable == null) {
			return null;
		}
		String value = getLibrarySearchEnvValue(variable, phpExeDir, quoted);
		return new StringBuilder(variable).append('=').append(value).toString();
	}

	public static String getLibrarySearchPathEnv(File phpExeDir) {
		return getLibrarySearchPathEnv(phpExeDir, false);
	}

	private static String getLibrarySearchEnvValue(String variable,
			File phpExeDir, boolean quoted) {
		StringBuilder buf = new StringBuilder();
		File libDirectory = new File(phpExeDir.getParentFile(), "lib"); //$NON-NLS-1$
		if (libDirectory.exists()) {
			buf.append(createPath(libDirectory, quoted));
		} else {
			buf.append(createPath(phpExeDir, quoted));
		}
		try {
			String env = System.getenv(variable);
			if (env != null) {
				buf.append(File.pathSeparatorChar).append(env);
			}
		} catch (Throwable e) {
		}
		return buf.toString();
	}

	private static String createPath(File path, boolean quoted) {
		return quoted ? '"' + path.getAbsolutePath() + '"' : path
				.getAbsolutePath();
	}

	private static String getLibrarySearchEnvVariable() {
		String os = System.getProperty("os.name"); //$NON-NLS-1$
		if (os.startsWith("Win")) { //$NON-NLS-1$
			return null; // "PATH";
		}
		if (os.startsWith("Mac")) { //$NON-NLS-1$
			return "DYLD_LIBRARY_PATH"; //$NON-NLS-1$
		}
		return "LD_LIBRARY_PATH"; //$NON-NLS-1$
	}

	/**
	 * Creates and returns a command line invocation string for the execution of
	 * the PHP script.
	 * 
	 * @param configuration
	 *            Launch configuration
	 * @param phpExe
	 *            PHP Executable path
	 * @param phpConfigDir
	 *            PHP configuration file location (directory where php.ini
	 *            resides)
	 * @param scriptPath
	 *            Script path
	 * @param phpIniLocation
	 *            PHP configuration file path
	 * @param args
	 *            Command line arguments, if using PHP CLI, otherwise -
	 *            <code>null</code>
	 * @param phpVersion
	 * @return commands array
	 * @throws CoreException
	 */
	public static String[] getCommandLine(ILaunchConfiguration configuration,
			String phpExe, String phpConfigDir, String scriptPath,
			String[] args, String phpVersion) throws CoreException {
		// Check if we should treat ASP tags as PHP tags
		IProject project = getProject(configuration);
		String aspTags = ProjectOptions.isSupportingAspTags(project) ? "on" //$NON-NLS-1$
				: "off"; //$NON-NLS-1$
		String shortOpenTag = ProjectOptions.useShortTags(project) ? "on" //$NON-NLS-1$
				: "off"; //$NON-NLS-1$

		List<String> cmdLineList = new LinkedList<String>();
		cmdLineList.addAll(Arrays.asList(new String[] { phpExe, "-c", //$NON-NLS-1$
				phpConfigDir, "-d", "asp_tags=" + aspTags, "-d", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"short_open_tag=" + shortOpenTag, scriptPath })); //$NON-NLS-1$
		if (args != null) {
			cmdLineList.addAll(Arrays.asList(args));
		}
		return cmdLineList.toArray(new String[cmdLineList.size()]);
	}

	public static String[] getCommandLine(ILaunchConfiguration configuration,
			String phpExe, String phpConfigDir, String scriptPath, String[] args)
			throws CoreException {
		return getCommandLine(configuration, phpExe, phpConfigDir, scriptPath,
				args, null);
	}

	public static String[] getCommandLineForPHP54BuildinServer(
			ILaunchConfiguration configuration, String phpExe,
			String phpConfigDir, String server, String root, String routerFile,
			String[] args) throws CoreException {
		// Check if we should treat ASP tags as PHP tags
		IProject project = getProject(configuration);
		String aspTags = ProjectOptions.isSupportingAspTags(project) ? "on" //$NON-NLS-1$
				: "off"; //$NON-NLS-1$
		String shortOpenTag = ProjectOptions.useShortTags(project) ? "on" //$NON-NLS-1$
				: "off"; //$NON-NLS-1$
		if (server.startsWith("http://")) { //$NON-NLS-1$
			server = server.substring(7);
		} else if (server.startsWith("https://")) { //$NON-NLS-1$
			server = server.substring(8);
		}

		List<String> cmdLineList = new LinkedList<String>();
		if (routerFile == null) {
			cmdLineList.addAll(Arrays.asList(new String[] { phpExe, "-S", //$NON-NLS-1$
					server, "-t", root, "-n", "-c", phpConfigDir, "-d", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					"asp_tags=" + aspTags, "-d", //$NON-NLS-1$ //$NON-NLS-2$
					"short_open_tag=" + shortOpenTag })); //$NON-NLS-1$
		} else {
			cmdLineList.addAll(Arrays.asList(new String[] { phpExe, "-S", //$NON-NLS-1$
					server, "-t", root, routerFile, "-n", "-c", phpConfigDir, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"-d", "asp_tags=" + aspTags, "-d", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"short_open_tag=" + shortOpenTag })); //$NON-NLS-1$
		}
		if (args != null) {
			cmdLineList.addAll(Arrays.asList(args));
		}
		return cmdLineList.toArray(new String[cmdLineList.size()]);
	}

	/**
	 * Returns the project that is related to the launch configuration.
	 * 
	 * @param configuration
	 * @return
	 */
	private static IProject getProject(ILaunchConfiguration configuration) {
		try {
			String fileNameString = configuration.getAttribute(
					IPHPDebugConstants.ATTR_FILE, (String) null);
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
					.getRoot();
			final IPath filePath = new Path(fileNameString);
			IResource res = workspaceRoot.findMember(filePath);
			if (res != null) {
				return res.getProject();
			}
		} catch (CoreException ce) {
			Logger.logException(ce);
		}
		return null;
	}

	/**
	 * Returns the program arguments from the launch configuration. Program
	 * arguments will allow variable substitution as well. The arguments are
	 * extracted from the IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS
	 * configuration attribute.
	 * 
	 * @param configuration
	 *            the launch configuration
	 * @return the program arguments
	 * @throws CoreException
	 *             rethrown exception
	 */
	public static String[] getProgramArguments(
			ILaunchConfiguration configuration) throws CoreException {
		String arguments = configuration.getAttribute(
				IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS,
				(String) null);
		if (arguments == null || arguments.trim().equals("")) { //$NON-NLS-1$
			return new String[0];
		}
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=298606
		return DebugPlugin.parseArguments(VariablesPlugin.getDefault()
				.getStringVariableManager()
				.performStringSubstitution(arguments));
	}

	/**
	 * Returns true if the given project is using ASP tags as PHP tags.
	 * 
	 * @param project
	 *            an {@link IProject}.
	 * @return True, if ASP tags are supported, false otherwise.
	 */
	public static boolean isUsingASPTags(IProject project) {
		PreferencesSupport preferencesSupport = new PreferencesSupport(
				PHPCorePlugin.getPluginId(), PHPCorePlugin.getDefault()
						.getPluginPreferences());
		String value = preferencesSupport
				.getPreferencesValue(
						CorePreferenceConstants.Keys.EDITOR_USE_ASP_TAGS, null,
						project);
		if (value == null) {
			value = preferencesSupport
					.getWorkspacePreferencesValue(CorePreferenceConstants.Keys.EDITOR_USE_ASP_TAGS);
		}
		return Boolean.valueOf(value).booleanValue();
	}

	/**
	 * Generates debug query from parameters for the GET method. This method
	 * encodes debug parameters.
	 * 
	 * @param launch
	 * @return
	 */
	public static String generateQuery(ILaunch launch,
			IDebugParametersInitializer debugParametersInitializer) {
		StringBuffer buf = new StringBuffer();

		Hashtable<String, String> parameters = debugParametersInitializer
				.getDebugParameters(launch);
		Enumeration<String> e = parameters.keys();

		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			buf.append(key).append('=');
			try {
				buf.append(URLEncoder.encode((String) parameters.get(key),
						"UTF-8")); //$NON-NLS-1$
			} catch (UnsupportedEncodingException exc) {
			}
			if (e.hasMoreElements()) {
				buf.append('&'); //$NON-NLS-1$
			}
		}
		return buf.toString();
	}

	/*
	 * Tunneling functionality
	 */

	/**
	 * Returns a SSHTunnel instance in case defined in the given launch
	 * configuration. The returned SSHTunnel may be null in case the given
	 * configuration is not defined to use one. Also, the returned instance
	 * might be shared between other launches as well, and might already be in a
	 * connected state.
	 * 
	 * @param configuration
	 * @return An SSHTunnel instance; Null, in case the configuration does not
	 *         need one.
	 */
	public static SSHTunnel getSSHTunnel(ILaunchConfiguration configuration) {
		try {
			if (configuration.getAttribute(IPHPDebugConstants.USE_SSH_TUNNEL,
					false)) {
				String remoteHost = PHPLaunchUtilities
						.getDebugHost(configuration);
				int port = PHPLaunchUtilities.getDebugPort(configuration);
				if (remoteHost != null && remoteHost.length() > 0 && port > -1) {
					String userName = configuration.getAttribute(
							IPHPDebugConstants.SSH_TUNNEL_USER_NAME, "");//$NON-NLS-1$
					String password = PHPLaunchUtilities.getSecurePreferences(
							remoteHost).get(userName, "");//$NON-NLS-1$
					return SSHTunnelFactory.getSSHTunnel(remoteHost, userName,
							password, port, port);
				}
			}
		} catch (CoreException e) {
			Logger.logException("Error obtaining an SSHTunnel instance", e);//$NON-NLS-1$
		} catch (StorageException e) {
			Logger.logException(
					"Error accessing the secured storage for the debug SSH tunnel",//$NON-NLS-1$
					e);
		}
		return null;
	}

	/**
	 * Returns the port that is associated to the debugger that is involved in
	 * the given launch configuration.
	 * 
	 * @return The port in use. -1, in case of an error.
	 */
	public static int getDebugPort(ILaunchConfiguration launchConfiguration) {
		try {
			String debuggerID = launchConfiguration.getAttribute(
					PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
					PHPDebugPlugin.getCurrentDebuggerId());
			AbstractDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry
					.getDebuggerConfiguration(debuggerID);
			return debuggerConfiguration.getPort();
		} catch (Exception e) {
			Logger.logException(
					"Could not retrieve the debugger's port number", e);//$NON-NLS-1$
		}
		return -1;
	}

	/**
	 * Returns the host that is associated with the given launch configuration.
	 * The returned host can be null in case of an error or a missing host
	 * setting.
	 * 
	 * @return The host address, or null.
	 */
	public static String getDebugHost(ILaunchConfiguration launchConfiguration) {
		try {
			String url = launchConfiguration.getAttribute(Server.BASE_URL, "");//$NON-NLS-1$
			if (url == null || url.length() == 0) {
				return null;
			}
			return new URL(url).getHost();
		} catch (CoreException e) {
			Logger.logException("Could not retrieve the host name", e);//$NON-NLS-1$
		} catch (MalformedURLException e) {
			Logger.logException("Could not retrieve the host name", e);//$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Returns the secure storage node for the tunnel connections that are set
	 * on the given host.
	 * 
	 * @param host
	 * 
	 * @return An ISecurePreferences for the php debug secured node.
	 */
	public static ISecurePreferences getSecurePreferences(String host) {
		String hostPath = "";//$NON-NLS-1$
		if (host != null) {
			hostPath = '/' + host;
		}
		ISecurePreferences root = SecurePreferencesFactory.getDefault();
		ISecurePreferences node = root
				.node(IPHPDebugConstants.SSH_TUNNEL_SECURE_PREF_NODE + hostPath);
		return node;
	}
}
