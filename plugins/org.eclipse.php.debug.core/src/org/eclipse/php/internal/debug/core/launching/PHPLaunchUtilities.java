/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.debug.core.*;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * Utilities that are shared to all the PHP launches.
 */
public class PHPLaunchUtilities {

	public static final String ID_PHPDebugOutput = "org.eclipse.debug.ui.PHPDebugOutput"; //$NON-NLS-1$
	public static final String ID_PHPBrowserOutput = "org.eclipse.debug.ui.PHPBrowserOutput"; //$NON-NLS-1$

	/**
	 * Display the Debug Output view in case it's hidden or not initialized. 
	 * In case where the Browser Output view is visible, nothing will happen and the Browser Output 
	 * will remain as the visible view during the debug session.
	 * 
	 * Note that the behaviour given by this function is mainly needed when we are in a PHP Perspective (not debug)
	 * and a session without a breakpoint was launched. So in this case a 'force' output display is triggered.
	 * 
	 * This function also take into account the PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS flag and does not
	 * show the debug views in case it was not chosen from the preferences.
	 */
	public static void showDebugView() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		if (!prefs.getBoolean(PHPDebugCorePreferenceNames.OPEN_DEBUG_VIEWS)) {
			return;
		}
		// Get the page through a UI thread! Otherwise, it wont work...
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page != null) {
					try {
						IViewPart debugOutputPart = page.findView("org.eclipse.debug.ui.PHPDebugOutput"); //$NON-NLS-1$
						IViewPart browserOutputPart = page.findView("org.eclipse.debug.ui.PHPBrowserOutput"); //$NON-NLS-1$

						// Test if the Debug Output view is alive and visible.
						boolean shouldShowDebug = false;
						if (debugOutputPart == null || !page.isPartVisible(debugOutputPart)) {
							shouldShowDebug = true;
						}

						// If the Browser Output is visible, do not switch to the Debug Output.
						if (browserOutputPart != null && page.isPartVisible(browserOutputPart)) {
							shouldShowDebug = false;
						}

						if (shouldShowDebug) {
							page.showView("org.eclipse.debug.ui.PHPDebugOutput"); //$NON-NLS-1$
						}
					} catch (Exception e) {
						Logger.logException("Error switching to the Debug Output view", e); //$NON-NLS-1$
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
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		for (int i = 0; i < launches.length; i++) {
			if (!launches[i].isTerminated() && ILaunchManager.DEBUG_MODE.equals(launches[i].getLaunchMode()) && launches[i].getDebugTarget() instanceof PHPDebugTarget) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Notify the existance of a previous PHP debug session in case the user launched a new session.
	 *
	 * @param newLaunchConfiguration
	 * @param newLaunch
	 * @return True, if the launch can be continued; False, otherwise. 
	 * @throws CoreException
	 */
	public static boolean notifyPreviousLaunches(ILaunch newLaunch) throws CoreException {
		// In case the new launch is not a debug launch, we have no problem.
		if (!ILaunchManager.DEBUG_MODE.equals(newLaunch.getLaunchMode())) {
			return true;
		}
		// If there are no active debug launches, return true and continue with the new launch.
		if (!hasPHPDebugLaunch()) {
			return true;
		}

		// Check whether we should ask the user.
		final IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		String option = store.getString(PreferenceConstants.ALLOW_MULTIPLE_LAUNCHES);
		if (MessageDialogWithToggle.ALWAYS.equals(option)) {
			// If always, then we should always allow the launch
			return true;
		}
		if (MessageDialogWithToggle.NEVER.equals(option)) {
			// We should never allow the launch, so display a message describing the situation.
			final Display disp = Display.getDefault();
			disp.syncExec(new Runnable() {
				public void run() {
					MessageDialog.openInformation(disp.getActiveShell(), PHPDebugCoreMessages.PHPLaunchUtilities_phpLaunchTitle, PHPDebugCoreMessages.PHPLaunchUtilities_activeLaunchDetected);
				}
			});
			return false;
		}

		final DialogResultHolder resultHolder = new DialogResultHolder();
		final Display disp = Display.getDefault();
		disp.syncExec(new Runnable() {
			public void run() {
				// Display a dialog to notify the existance of a previous active launch.
				MessageDialogWithToggle m = MessageDialogWithToggle.openYesNoQuestion(
					disp.getActiveShell(), 
					PHPDebugCoreMessages.PHPLaunchUtilities_confirmation, 
					PHPDebugCoreMessages.PHPLaunchUtilities_multipleLaunchesPrompt, PHPDebugCoreMessages.PHPLaunchUtilities_rememberDecision, 
					false, 
					store,
					PreferenceConstants.ALLOW_MULTIPLE_LAUNCHES);
				resultHolder.setReturnCode(m.getReturnCode());
			}
		});
		switch (resultHolder.getReturnCode()) {
			case IDialogConstants.YES_ID:
			case IDialogConstants.OK_ID :
				return true;
			case IDialogConstants.NO_ID :
				return false;
		}
		return true;
	}

	/**
	 * Make all the necessary checks to see if the current launch can be launched with regards to the previous
	 * launches that has 'debug all pages' attribute. 
	 * @throws CoreException 
	 */
	public static boolean checkDebugAllPages(final ILaunchConfiguration newLaunchConfiguration, final ILaunch newLaunch) throws CoreException {
		// If the remote debugger already supports multiple debugging with the 'debug all pages', 
		// we do not have to do a thing and we can return.
		if (PHPDebugPlugin.supportsMultipleDebugAllPages()) {
			return true;
		}
		// Make sure we set the attributes on the ILaunch since the ILaunchConfiguration reference never changes, while the
		// ILaunch is created for each launch.
		newLaunch.setAttribute(IPHPConstants.DEBUGGING_PAGES, newLaunchConfiguration.getAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_ALL_PAGES));
		checkAutoRemoveLaunches();
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		boolean hasContiniousLaunch = false;
		// check for a launch that has a 'debug all pages' or 'start debug from' attribute
		for (int i = 0; !hasContiniousLaunch && i < launches.length; i++) {
			ILaunch launch = launches[i];
			if (launch != newLaunch && ILaunchManager.DEBUG_MODE.equals(launch.getLaunchMode())) {
				if (isDebugAllPages(launch) || isStartDebugFrom(launch)) {
					hasContiniousLaunch = true;
				}
			}
		}
		// Check if the new launch is 'debug all pages'

		boolean newLaunchIsDebug = ILaunchManager.DEBUG_MODE.equals(newLaunch.getLaunchMode());
		final boolean newIsDebugAllPages = newLaunchIsDebug && isDebugAllPages(newLaunch);
		final boolean newIsStartDebugFrom = newLaunchIsDebug && isStartDebugFrom(newLaunch);
		final boolean fHasContiniousLaunch = hasContiniousLaunch;

		if ((fHasContiniousLaunch || newIsDebugAllPages || newIsStartDebugFrom) && launches.length > 1) {
			final DialogResultHolder resultHolder = new DialogResultHolder();
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					// TODO - Advanced message dialog with 'don't show this again' check.
					if (fHasContiniousLaunch) {
						resultHolder
							.setResult(MessageDialog
								.openConfirm(Display.getDefault().getActiveShell(), PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
									"A previous launch with 'Debug All Pages' or 'Start Debug From' attribute was identifed.\nLaunching a new session will terminate and remove the old launch, directing all future debug requests associated with it to the new launch.\nDo you wish to continue and launch a new session?")); //$NON-NLS-1$
					} else {
						if (newIsDebugAllPages) {
							resultHolder.setResult(MessageDialog.openConfirm(Display.getDefault().getActiveShell(), PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
								"The requested launch has a 'Debug All Pages' attribute.\nLaunching this type of session will terminate and remove any other previous launches.\nDo you wish to continue and launch the new session?")); //$NON-NLS-1$
						} else {
							// newIsStartDebugFrom == true
							resultHolder.setResult(MessageDialog.openConfirm(Display.getDefault().getActiveShell(), PHPDebugCoreMessages.PHPLaunchUtilities_confirmation,
								"The requested launch has a 'Start Debug From' attribute.\nLaunching this type of session will terminate and remove any other previous launches.\nDo you wish to continue and launch the new session?")); //$NON-NLS-1$
						}
					}
					if (resultHolder.getResult()) {
						// disable the auto remove launches for the next launch
						PHPDebugPlugin.setDisableAutoRemoveLaunches(true);
						// manually remove the old launches and continue this launch
						removeAndTerminateOldLaunches(newLaunch);
					} else {
						// Remove the latest launch
						DebugPlugin.getDefault().getLaunchManager().removeLaunch(newLaunch);
					}
				}
			});
			return resultHolder.getResult();
		} else {
			if (newIsDebugAllPages || newIsStartDebugFrom) {
				PHPDebugPlugin.setDisableAutoRemoveLaunches(true);
			} else {
				// There are no other launches AND the new launch doesn't have a debug-all-pages.
				PHPDebugPlugin.setDisableAutoRemoveLaunches(!PHPDebugPlugin.getDefault().getInitialAutoRemoveLaunches());
				// This will manually remove the old launches if needed
				DebugUIPlugin.getDefault().getLaunchConfigurationManager().launchAdded(newLaunch);
			}
			return true;
		}
	}

	// In case that there are no launches, make sure to enable the auto-remove old launches in case it's needed
	private static void checkAutoRemoveLaunches() {
		if (DebugPlugin.getDefault().getLaunchManager().getLaunches().length == 1) {
			PHPDebugPlugin.setDisableAutoRemoveLaunches(false);
		}
	}

	/**
	 * Returns if the given launch configuration holds an attribute for 'debug all pages'.
	 * 
	 * @param launchConfiguration An {@link ILaunchConfiguration}
	 * @return True, if the configuration holds an attribute for 'debug all pages'.
	 * @throws CoreException 
	 */
	public static boolean isDebugAllPages(ILaunch launch) throws CoreException {
		String attribute = launch.getAttribute(IPHPConstants.DEBUGGING_PAGES);
		return attribute != null && attribute.equals(IPHPConstants.DEBUGGING_ALL_PAGES);
	}

	/**
	 * Returns if the given launch configuration holds an attribute for 'start debug from'.
	 * 
	 * @param launchConfiguration An {@link ILaunchConfiguration}
	 * @return True, if the configuration holds an attribute for 'start debug from'.
	 * @throws CoreException 
	 */
	public static boolean isStartDebugFrom(ILaunch launch) throws CoreException {
		String attribute = launch.getAttribute(IPHPConstants.DEBUGGING_PAGES);
		return attribute != null && attribute.equals(IPHPConstants.DEBUGGING_START_FROM);
	}

	// terminate and remove all the existing launches accept for the given new launch.
	private static void removeAndTerminateOldLaunches(ILaunch newLaunch) {
		ILaunchManager lManager = DebugPlugin.getDefault().getLaunchManager();
		Object[] launches = lManager.getLaunches();
		for (int i = 0; i < launches.length; i++) {
			ILaunch launch = (ILaunch) launches[i];
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
	 * Display a wait window, indicating the user that the debug session is in progress and the
	 * PDT is waiting for the debugger's response.
	 * Once a responce arrives, the {@link #hideWaitForDebuggerMessage()} should be called to remove 
	 * the window.
	 * In case a response does not arrive, there is a good chance that the {@link #showDebuggerErrorMessage()} should
	 * be called.
	 * @see #hideWaitForDebuggerMessage()
	 * @see #showDebuggerErrorMessage()
	 */
	public static void showWaitForDebuggerMessage() {
		// TODO
	}

	public static void hideWaitForDebuggerMessage() {
		// TODO
	}

	/**
	 * Display an error message to indicating an fatal error detected while staring a debug session.
	 * A fatal error occures when the remote debugger does not exist or has a different version.
	 */
	public static void showDebuggerErrorMessage() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), PHPDebugCoreMessages.Debugger_Error, PHPDebugCoreMessages.Debugger_Error_Message);
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
}
