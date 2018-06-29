/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;

public interface IPHPConsoleEventListener {

	/**
	 * Initialize this PHP console event listener
	 * 
	 * @param launch
	 *            Current launch
	 * @param consoleMonitor
	 *            Console monitor
	 * @param link
	 *            Hyper link container
	 */
	public void init(ILaunch launch, DebugConsoleMonitor consoleMonitor, PHPHyperLink link);

	/**
	 * Notification the given event occurred in the target program being
	 * interpreted.
	 * 
	 * @param event
	 *            the event
	 */
	public void handleEvent(DebugError debugError);

}
