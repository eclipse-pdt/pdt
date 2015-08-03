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
	public PHPDebugTarget getDebugTarget() {
		return null;
	}

	/**
	 * Returns an {@link AdvancedRemoteDebugger}.
	 */
	public IRemoteDebugger getRemoteDebugger() {
		if (fRemoteDebugger == null) {
			fRemoteDebugger = new RemoteDebugger(this, fDebugConnection);
		}
		return fRemoteDebugger;
	}

	/**
	 * Does nothing.
	 */
	public void setDebugTarget(PHPDebugTarget debugTarget) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.model.SimpleDebugHandler#
	 * connectionClosed ()
	 */
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
	public void wrongDebugServer() {
		super.wrongDebugServer();
		fRemoteDebugger.finish();
	}

}
