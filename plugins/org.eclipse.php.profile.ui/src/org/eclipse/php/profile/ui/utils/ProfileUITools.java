/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.utils;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.profile.core.engine.IProfiler;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.ui.*;

/**
 * Profiler UI tools.
 */
public class ProfileUITools {

	/**
	 * Returns the currently selected profiler in the profile monitor view, or
	 * <code>null</code> if no profiler selected.
	 * 
	 * @return profiler
	 */
	public static IProfiler getProfileContext() {
		IRemoteDebugger debugger = PHPDebugPlugin.getActiveRemoteDebugger();
		IDebugHandler debugHandler = debugger.getDebugHandler();
		if (debugHandler instanceof IProfiler) {
			return (IProfiler) debugHandler;
		}
		return null;
	}

	/**
	 * Finds existing or opens new view using its ID
	 * 
	 * @param String
	 *                   id of the view
	 * @return IViewPart view, or <code>null</code> if didn's succeed
	 */
	public static IViewPart findExistingView(String id) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IViewPart view = page.findView(id);
				if (view == null) {
					try {
						view = page.showView(id);
					} catch (PartInitException e) {
						ProfilerUiPlugin.log(e);
					}
				}
				return view;
			}
		}
		return null;
	}
}
