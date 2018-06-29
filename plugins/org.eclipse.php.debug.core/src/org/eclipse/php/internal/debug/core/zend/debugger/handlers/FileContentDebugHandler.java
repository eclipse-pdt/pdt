/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import org.eclipse.php.internal.debug.core.model.SimpleDebugHandler;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnection;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

/**
 * A debug handler that handles the Zend Platform request to display the source
 * code of the script in the Studio. This handler differ from other (normal)
 * handlers by that it does not hold any debug target.
 * 
 * @author Shalom Gibly
 */
public class FileContentDebugHandler extends SimpleDebugHandler {

	private IRemoteDebugger fRemoteDebugger;
	private DebugConnection fDebugConnection;

	/**
	 * Constructs a new {@link FileContentDebugHandler} with a given connection
	 * thread.
	 * 
	 * @param debugConnection
	 */
	public FileContentDebugHandler(DebugConnection debugConnection) {
		fDebugConnection = debugConnection;
	}

	/**
	 * Returns null.
	 */
	@Override
	public PHPDebugTarget getDebugTarget() {
		return null;
	}

	/**
	 * Returns an {@link AdvancedRemoteDebugger}.
	 */
	@Override
	public IRemoteDebugger getRemoteDebugger() {
		if (fRemoteDebugger == null) {
			fRemoteDebugger = new RemoteDebugger(this, fDebugConnection);
		}
		return fRemoteDebugger;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void setDebugTarget(PHPDebugTarget debugTarget) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.model.SimpleDebugHandler#
	 * connectionClosed ()
	 */
	@Override
	public void connectionClosed() {
		super.connectionClosed();
		fRemoteDebugger.finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.model.SimpleDebugHandler#
	 * wrongDebugServer ()
	 */
	@Override
	public void wrongDebugServer() {
		super.wrongDebugServer();
		fRemoteDebugger.finish();
	}

}
