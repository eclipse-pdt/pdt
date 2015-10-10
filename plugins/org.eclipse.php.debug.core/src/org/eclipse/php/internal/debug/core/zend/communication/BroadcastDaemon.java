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
package org.eclipse.php.internal.debug.core.zend.communication;

import java.net.Socket;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;

/**
 * A broadcast daemon class that is responsible for any information request that
 * arrives from Zend Toolbar, Zend Platform, or any other future product that
 * needs to be informed about the IDE's debug port and location.
 * 
 * @author Shalom Gibly
 */
public class BroadcastDaemon extends AbstractDebuggerCommunicationDaemon {

	public static final String ZEND_DEBUGGER_ID = "org.eclipse.php.debug.core.zendDebugger"; //$NON-NLS-1$

	private class BroadcastChangeListener implements IPreferenceChangeListener {
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey().equals(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT)) {
				resetSocket();
			}
		}
	}

	private IPreferenceChangeListener broadcastChangeListener;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.daemon.
	 * AbstractDebuggerCommunicationDaemon#init()
	 */
	@Override
	public void init() {
		super.init();
		registerListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.communication.
	 * DebuggerCommunicationDaemon #handleMultipleBindingError()
	 */
	public void handleMultipleBindingError() {
		final int port = getReceiverPort();
		Logger.log(Logger.ERROR, "Could not open a broadcast port on: " + port //$NON-NLS-1$
				+ " (port might be in use). Please select a different port number"); //$NON-NLS-1$
	}

	/**
	 * Returns the broadcast port, as defined in the preferences.
	 */
	public int getReceiverPort() {
		return InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
				.getInt(PHPDebugCorePreferenceNames.ZEND_DEBUG_BROADCAST_PORT, 20080);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.communication.
	 * DebuggerCommunicationDaemon #isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/*
	 * Override the super implementation to avoid including this daemon as a
	 * debugger daemon. Return false.
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.communication.
	 * DebuggerCommunicationDaemon#isDebuggerDaemon()
	 */
	public boolean isDebuggerDaemon() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.daemon.
	 * AbstractDebuggerCommunicationDaemon#getDebuggerID()
	 */
	@Override
	public String getDebuggerID() {
		return ZEND_DEBUGGER_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.communication.
	 * DebuggerCommunicationDaemon#startConnection(java.net.Socket)
	 */
	protected void startConnection(Socket socket) {
		new BroadcastConnection(socket);
	}

	private void registerListeners() {
		if (broadcastChangeListener == null) {
			broadcastChangeListener = new BroadcastChangeListener();
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID).addPreferenceChangeListener(broadcastChangeListener);
		}
	}

}
