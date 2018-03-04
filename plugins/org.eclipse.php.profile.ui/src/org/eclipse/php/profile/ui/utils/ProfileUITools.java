/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.utils;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.profile.core.engine.ZProfiler;
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
	public static ZProfiler getProfileContext() {
		IRemoteDebugger debugger = PHPDebugPlugin.getActiveRemoteDebugger();
		IDebugHandler debugHandler = debugger.getDebugHandler();
		if (debugHandler instanceof ZProfiler) {
			return (ZProfiler) debugHandler;
		}
		return null;
	}

	/**
	 * Finds existing or opens new view using its ID
	 * 
	 * @param String
	 *            id of the view
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
