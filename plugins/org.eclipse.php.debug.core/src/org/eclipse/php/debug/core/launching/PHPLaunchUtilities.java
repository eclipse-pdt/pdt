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
package org.eclipse.php.debug.core.launching;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
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
						IViewPart debugOutputPart = page.findView("org.eclipse.debug.ui.PHPDebugOutput");
						IViewPart browserOutputPart = page.findView("org.eclipse.debug.ui.PHPBrowserOutput");

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
							page.showView("org.eclipse.debug.ui.PHPDebugOutput");
						}
					} catch (Exception e) {
						Logger.logException("Error switching to the Debug Output view", e);
					}
				}
			}
		});
	}
}
