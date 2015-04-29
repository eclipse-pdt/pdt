/*******************************************************************************
 * Copyright (c) 2009, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.communication;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPDebugUtil;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsManager;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsListener;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.sourcelookup.PHPSourceLookupDirector;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr.AcceptRemoteSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.XDebugDebuggerSettingsConstants;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpMultiSessionTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.IDBGpSessionListener;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.util.PerspectiveManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.ibm.icu.text.MessageFormat;

/**
 * XDebug communication daemon.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
@SuppressWarnings("restriction")
public class XDebugCommunicationDaemon implements ICommunicationDaemon {

	public static final String XDEBUG_DEBUGGER_ID = "org.eclipse.php.debug.core.xdebugDebugger"; //$NON-NLS-1$

	private class CommunicationDaemon extends
			AbstractDebuggerCommunicationDaemon {

		private class PromptUser implements Runnable {
			private DBGpSession session;
			private boolean result;

			public boolean isResult() {
				return result;
			}

			public PromptUser(DBGpSession session) {
				this.session = session;
			}

			public void run() {
				String insert = session.getRemoteAddress()
						.getCanonicalHostName()
						+ "/" + session.getRemoteAddress().getHostAddress(); //$NON-NLS-1$
				String message = MessageFormat.format(
						PHPDebugCoreMessages.XDebugMessage_remoteSessionPrompt,
						new Object[] { insert });
				result = MessageDialog.openQuestion(Display.getDefault()
						.getActiveShell(),
						PHPDebugCoreMessages.XDebugMessage_remoteSessionTitle,
						message);
			}
		}

		private int port;

		public CommunicationDaemon(int port) {
			this.port = port;
			init();
		}

		public int getReceiverPort() {
			return port;
		}

		public boolean isDebuggerDaemon() {
			return true;
		}

		public String getDebuggerID() {
			return XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID;
		}

		/**
		 * Starts a connection handling thread on the given Socket.
		 * 
		 * @param socket
		 */
		protected void startConnection(Socket socket) {
			/*
			 * A socket has been accepted by the listener. This runs on the
			 * listener thread so we should make damn sure we don't throw an
			 * exception here otherwise it will abort that thread.
			 */
			if (DBGpLogger.debugSession()) {
				DBGpLogger
						.debug("Connection established: " + socket.toString()); //$NON-NLS-1$
			}
			try {
				DBGpSession session = new DBGpSession(socket);
				if (session.isActive()) {
					if (!DBGpSessionHandler.getInstance().fireSessionAdded(
							session)) {
						// Session not taken, we want to create a launch
						AcceptRemoteSession aSess = XDebugPreferenceMgr
								.getAcceptRemoteSession();
						if (aSess != AcceptRemoteSession.off) {
							if (aSess == AcceptRemoteSession.localhost
									&& session.getRemoteAddress()
											.isLoopbackAddress() == false) {
								session.endSession();
							} else if (aSess == AcceptRemoteSession.prompt) {
								PromptUser prompt = new PromptUser(session);
								Display.getDefault().syncExec(prompt);
								if (prompt.isResult()) {
									createLaunch(session);
								} else {
									session.endSession();
								}

							} else {
								/*
								 * session was either local host or from any
								 * outside one and preferences allow it.
								 */
								createLaunch(session);
							}
						} else {
							// reject the session
							session.endSession();
						}
					}
				}
			} catch (Exception e) {
				DBGpLogger
						.logException(
								"Unexpected Exception: Listener thread still listening", //$NON-NLS-1$
								this, e);
			}
		}

		/**
		 * create a launch and appropriate debug targets to automate launch
		 * initiation. If any problems occurred, we can throw the session away
		 * using session.endSession();
		 * 
		 * @param session
		 *            the DBGpSession.
		 * @throws CoreException
		 */
		private void createLaunch(DBGpSession session) throws CoreException {
			boolean stopAtFirstLine = PHPProjectPreferences
					.getStopAtFirstLine(null);
			DBGpTarget target = null;
			PathMapper mapper = null;
			PHPSourceLookupDirector srcLocator = new PHPSourceLookupDirector();
			srcLocator
					.setSourcePathComputer(DebugPlugin
							.getDefault()
							.getLaunchManager()
							.getSourcePathComputer(
									"org.eclipse.php.debug.core.sourcePathComputer.php")); //$NON-NLS-1$
			ILaunchConfigurationType type = null;
			ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
			if (session.getSessionId() == null) {
				// WEB launch
				type = lm
						.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);
			} else {
				// CLI launch
				type = lm
						.getLaunchConfigurationType(IPHPDebugConstants.PHPEXELaunchType);
			}
			ILaunchConfiguration launchConfig = type.newInstance(null,
					PHPDebugCoreMessages.XDebugMessage_remoteSessionTitle);
			srcLocator.initializeDefaults(launchConfig);
			srcLocator.initializeParticipants();
			ILaunch remoteLaunch = new Launch(launchConfig,
					ILaunchManager.DEBUG_MODE, srcLocator);
			boolean multiSession = XDebugPreferenceMgr.useMultiSession();
			if (session.getSessionId() == null && !multiSession) {
				// Non multi-session web launch
				target = new DBGpTarget(remoteLaunch, null, null,
						session.getIdeKey(), null, stopAtFirstLine);
				/*
				 * try to locate a relevant server definition so we can get its
				 * path mapper
				 */
				Server server = null;
				Server[] servers = ServersManager.getServers();
				for (int i = 0; i < servers.length; i++) {
					if (servers[i].getPort() == session.getRemotePort()
							&& servers[i].getHost().equalsIgnoreCase(
									session.getRemoteHostname())) {
						server = servers[i];
						break;
					}
				}
				if (server != null) {
					mapper = PathMapperRegistry.getByServer(server);
				}
				if (mapper == null) {
					/*
					 * Create a temporary path mapper, we may look to holding
					 * these via the pathmapper registry in the future but they
					 * would be persisted.
					 */
					mapper = new PathMapper();
				}
				/*
				 * Need to add ourselves as a session listener for future
				 * sessions.
				 */
				DBGpSessionHandler.getInstance().addSessionListener(
						(IDBGpSessionListener) target);
			} else {
				// CLI launch or multisession web launch: create a single target
				target = new DBGpTarget(remoteLaunch,
						null /* no script name */, session.getIdeKey(),
						session.getSessionId(), stopAtFirstLine);
				// Create a temporary path mapper
				mapper = new PathMapper();
			}

			// Set up the target with the relevant connections
			target.setPathMapper(mapper);
			target.setSession(session);
			session.setDebugTarget(target);

			if (multiSession && session.getSessionId() == null) {
				// We are a multisession web launch
				DBGpMultiSessionTarget multiSessionTarget = new DBGpMultiSessionTarget(
						remoteLaunch, null, null, session.getIdeKey(),
						stopAtFirstLine);
				DBGpSessionHandler.getInstance().addSessionListener(
						(IDBGpSessionListener) multiSessionTarget);
				remoteLaunch.addDebugTarget(multiSessionTarget);
				multiSessionTarget.sessionReceived(
						(DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
						XDebugPreferenceMgr.createSessionPreferences(), target,
						mapper);
			} else {
				// Not a mult-isession web launch, so just add to the launch
				remoteLaunch.addDebugTarget(target);
				// Tell the target it now has a session.
				target.sessionReceived(
						(DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
						XDebugPreferenceMgr.createSessionPreferences());
				/*
				 * Probably could do waitForInitialSession as session has
				 * already been set.
				 */
			}

			// add the remote launch to the launch manager
			DebugPlugin.getDefault().getLaunchManager().addLaunch(remoteLaunch);

			/*
			 * Check to see owning session target is still active, if so do a
			 * perspective switch
			 */
			if (target.isTerminated() == false
					&& target.isTerminating() == false) {
				Display.getDefault().asyncExec(new Runnable() {

					public void run() {
						IWorkbenchWindow window = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow();
						// Code the Debug perspective.
						// org.eclipse.debug.ui.DebugPerspective
						// also look at the PHPLaunchUtilities
						if (!PerspectiveManager.isCurrentPerspective(window,
								IDebugUIConstants.ID_DEBUG_PERSPECTIVE)) {
							if (PerspectiveManager.shouldSwitchPerspective(
									window,
									IDebugUIConstants.ID_DEBUG_PERSPECTIVE)) {
								PerspectiveManager.switchToPerspective(window,
										IDebugUIConstants.ID_DEBUG_PERSPECTIVE);
							}
						}
					}

				});
			}
		}

	}

	private List<AbstractDebuggerCommunicationDaemon> daemons = new ArrayList<AbstractDebuggerCommunicationDaemon>();
	private IPreferenceChangeListener defaultPortListener = null;
	private IDebuggerSettingsListener debuggerSettingsListener = null;

	// A port change listener
	private class DefaultPortListener implements IPreferenceChangeListener {
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey().equals(XDebugPreferenceMgr.XDEBUG_PREF_PORT)) {
				reset();
			}
		}
	}

	private class DebuggerSettingsListener implements IDebuggerSettingsListener {

		@Override
		public void settingsAdded(IDebuggerSettings settings) {
			if (getDebuggerID().equals(settings.getDebuggerId()))
				reset();
		}

		@Override
		public void settingsRemoved(IDebuggerSettings settings) {
			if (getDebuggerID().equals(settings.getDebuggerId()))
				reset();
		}

		@Override
		public void settingsChanged(PropertyChangeEvent[] events) {
			for (PropertyChangeEvent event : events) {
				IDebuggerSettings settings = (IDebuggerSettings) event
						.getSource();
				if (getDebuggerID().equals(settings.getDebuggerId())
						&& event.getProperty()
								.equals(XDebugDebuggerSettingsConstants.PROP_CLIENT_PORT)) {
					reset();
				}
			}
		}

	}

	@Override
	public void init() {
		registerListeners();
		reset();
	}

	@Override
	public boolean isListening(int port) {
		for (ICommunicationDaemon daemon : daemons)
			if (daemon.isListening(port))
				return true;
		return false;
	}

	@Override
	public void startListen() {
		for (ICommunicationDaemon daemon : daemons)
			daemon.startListen();
	}

	@Override
	public void stopListen() {
		unregisterListeners();
		for (ICommunicationDaemon daemon : daemons)
			daemon.stopListen();
	}

	@Override
	public boolean resetSocket() {
		boolean allReset = true;
		for (ICommunicationDaemon daemon : daemons)
			if (!daemon.resetSocket())
				allReset = false;
		return allReset;
	}

	@Override
	public void handleMultipleBindingError() {
		// Won't be called
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getDebuggerID() {
		return XDEBUG_DEBUGGER_ID;
	}

	@Override
	public boolean isDebuggerDaemon() {
		return true;
	}

	@Override
	public boolean isInitialized() {
		for (ICommunicationDaemon daemon : daemons)
			if (!daemon.isInitialized())
				return false;
		return true;
	}

	private void registerListeners() {
		if (defaultPortListener == null) {
			defaultPortListener = new DefaultPortListener();
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
					.addPreferenceChangeListener(defaultPortListener);
		}
		if (debuggerSettingsListener == null) {
			debuggerSettingsListener = new DebuggerSettingsListener();
			DebuggerSettingsManager.INSTANCE
					.addSettingsListener(debuggerSettingsListener);
		}
	}

	private void unregisterListeners() {
		if (defaultPortListener != null) {
			InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
					.addPreferenceChangeListener(defaultPortListener);
		}
		if (debuggerSettingsListener != null) {
			DebuggerSettingsManager.INSTANCE
					.addSettingsListener(debuggerSettingsListener);
		}
	}

	private synchronized void reset() {
		Set<Integer> ports = PHPDebugUtil.getDebugPorts(getDebuggerID());
		List<AbstractDebuggerCommunicationDaemon> daemonsToSet = new ArrayList<AbstractDebuggerCommunicationDaemon>();
		// Shutdown daemons that should not listen anymore
		for (AbstractDebuggerCommunicationDaemon daemon : daemons) {
			boolean isRedundant = true;
			for (int port : ports) {
				if (daemon.getReceiverPort() == port) {
					daemonsToSet.add(daemon);
					isRedundant = false;
					break;
				}
			}
			if (isRedundant) {
				daemon.stopListen();
			}
		}
		// Start new daemons if there should be any
		for (int port : ports) {
			boolean isRunning = false;
			for (AbstractDebuggerCommunicationDaemon daemon : daemons) {
				if (daemon.getReceiverPort() == port) {
					isRunning = true;
					break;
				}
			}
			if (!isRunning) {
				AbstractDebuggerCommunicationDaemon newDaemon = new CommunicationDaemon(
						port);
				daemonsToSet.add(newDaemon);
			}
		}
		daemons = daemonsToSet;
	}

}
