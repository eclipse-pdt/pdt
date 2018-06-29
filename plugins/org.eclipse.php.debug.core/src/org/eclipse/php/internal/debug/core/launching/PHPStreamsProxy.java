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
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;

/**
 * 
 */
public class PHPStreamsProxy implements IStreamsProxy {

	private DebugConsoleMonitor fConsoleMonitor = new DebugConsoleMonitor();

	/**
	 * @see org.eclipse.debug.core.model.IStreamsProxy#getErrorStreamMonitor()
	 */
	@Override
	public IStreamMonitor getErrorStreamMonitor() {
		// TODO: re-implement ProcessCrashDetector using this method
		return null;
	}

	/**
	 * @see org.eclipse.debug.core.model.IStreamsProxy#getErrorStreamMonitor()
	 */
	@Override
	public IStreamMonitor getOutputStreamMonitor() {
		// TODO: re-implement ProcessCrashDetector using this method
		return null;
	}

	public IStreamMonitor getConsoleStreamMonitor() {
		return fConsoleMonitor;
	}

	/**
	 * @see org.eclipse.debug.core.model.IStreamsProxy#write(java.lang.String)
	 */
	@Override
	public void write(String input) {
	}

}
