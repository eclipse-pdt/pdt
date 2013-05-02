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
/**
 * 
 */
package org.eclipse.php.internal.debug.core.xdebug.communication;

import java.net.Socket;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.sourcelookup.PHPSourceLookupDirector;
import org.eclipse.php.internal.debug.core.xdebug.IDELayerFactory;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr;
import org.eclipse.php.internal.debug.core.xdebug.XDebugPreferenceMgr.AcceptRemoteSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
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
public class XDebugCommunicationDaemon extends
		AbstractDebuggerCommunicationDaemon {

	public static final String XDEBUG_DEBUGGER_ID = "org.eclipse.php.debug.core.xdebugDebugger"; //$NON-NLS-1$
	private PortChangeListener portChangeListener;

	// private boolean isInitialized;

	/**
	 * An XDebug communication daemon.
	 */
	public XDebugCommunicationDaemon() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.debug.core.daemon.
	 * AbstractDebuggerCommunicationDaemon#init()
	 */
	public void init() {
		initDeamonChangeListener();
		super.init();
	}

	// @Override
	// public void startListen() {
	// synchronized (lock) {
	// if (!isAlive && serverSocket != null) {
	// startListenThread();
	// } else {
	// isInitialized = true;
	// }
	// }
	// }

	/**
	 * Initialize a daemon change listener
	 */
	protected void initDeamonChangeListener() {
		if (portChangeListener == null) {
			Preferences preferences = PHPDebugPlugin.getDefault()
					.getPluginPreferences();
			portChangeListener = new PortChangeListener();
			preferences.addPropertyChangeListener(portChangeListener);
		}
	}

	/**
	 * Returns the server socket port used for the debug requests listening
	 * thread.
	 * 
	 * @return The port specified in the preferences.
	 */
	public int getReceiverPort() {
		return PHPDebugPlugin.getDebugPort(XDEBUG_DEBUGGER_ID);
	}

	/**
	 * Returns the XDebug debugger ID.
	 * 
	 * @return The debugger ID that is using this daemon (e.g. XDebug).
	 * @since PDT 1.0
	 */
	public String getDebuggerID() {
		return XDEBUG_DEBUGGER_ID;
	}

	/**
	 * Returns if this daemon is a debugger daemon. In this case, always return
	 * true.
	 */
	public boolean isDebuggerDaemon() {
		return true;
	}

	/**
	 * Starts a connection handling thread on the given Socket.
	 * 
	 * @param socket
	 */
	protected void startConnectionThread(Socket socket) {
		// a socket has been accepted by the listener. This runs on the listener
		// thread so we should make damn sure we don't throw an exception here
		// otherwise it will abort that thread.
		if (DBGpLogger.debugSession()) {
			DBGpLogger.debug("Connection established: " + socket.toString()); //$NON-NLS-1$
		}

		try {
			DBGpSession session = new DBGpSession(socket);
			if (session.isActive()) {
				if (!DBGpSessionHandler.getInstance().fireSessionAdded(session)) {
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
							// session was either localhost or from any outside
							// one and
							// preferences allow it.
							createLaunch(session);
						}
					} else {
						// reject the session
						session.endSession();
					}
				}
			}
		} catch (Exception e) {
			DBGpLogger.logException(
					"Unexpected Exception: Listener thread still listening", //$NON-NLS-1$
					this, e);
		}
		// isInitialized = true;
	}

	/**
	 * create a launch and appropriate debug targets to automate launch
	 * initiation. If any problems occurred, we can throw the session away using
	 * session.endSession();
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
		srcLocator.setSourcePathComputer(DebugPlugin
				.getDefault()
				.getLaunchManager()
				.getSourcePathComputer(
						"org.eclipse.php.debug.core.sourcePathComputer.php")); //$NON-NLS-1$
		ILaunchConfigurationType type = null;
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();

		if (session.getSessionId() == null) {
			// web launch
			type = lm
					.getLaunchConfigurationType(IPHPDebugConstants.PHPServerLaunchType);
		} else {
			// cli launch
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
			// non multisession web launch
			target = new DBGpTarget(remoteLaunch, null, null,
					session.getIdeKey(), stopAtFirstLine, null);

			// try to locate a relevant server definition so we can get its path
			// mapper
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
				// create a temporary path mapper, we may look to holding these
				// via the pathmapper registry in the future
				// but they would be persisted.
				mapper = new PathMapper();
			}
			// need to add ourselves as a session listener for future sessions
			DBGpSessionHandler.getInstance().addSessionListener(
					(IDBGpSessionListener) target);
		} else {
			// cli launch or multisession web launch: create a single shot
			// target
			target = new DBGpTarget(remoteLaunch, null /* no script name */,
					session.getIdeKey(), session.getSessionId(),
					stopAtFirstLine);
			// PathMapper p = PathMapperRegistry.getByPHPExe(null);
			// create a temporary path mapper
			mapper = new PathMapper();
		}

		// set up the target with the relevant connections
		target.setPathMapper(mapper);
		target.setSession(session);
		session.setDebugTarget(target);

		if (multiSession && session.getSessionId() == null) {
			// we are a multisession web launch
			DBGpMultiSessionTarget multiSessionTarget = new DBGpMultiSessionTarget(
					remoteLaunch, null, null, session.getIdeKey(),
					stopAtFirstLine, null);
			DBGpSessionHandler.getInstance().addSessionListener(
					(IDBGpSessionListener) multiSessionTarget);
			remoteLaunch.addDebugTarget(multiSessionTarget);
			multiSessionTarget.sessionReceived(
					(DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
					XDebugPreferenceMgr.createSessionPreferences(), target,
					mapper);
		} else {
			// we are not a multisession web launch, so just add to the launch
			remoteLaunch.addDebugTarget(target);
			// tell the target it now has a session.
			target.sessionReceived(
					(DBGpBreakpointFacade) IDELayerFactory.getIDELayer(),
					XDebugPreferenceMgr.createSessionPreferences());
			// probably could do waitForInitialSession as session has already
			// been set.
		}

		// add the remote launch to the launch manager
		DebugPlugin.getDefault().getLaunchManager().addLaunch(remoteLaunch);

		// check to see owning session target is still active, if so do a
		// perspective switch
		if (target.isTerminated() == false && target.isTerminating() == false) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					IWorkbenchWindow window = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();
					// code the debug perspective.
					// org.eclipse.debug.ui.DebugPerspective
					// also look at the PHPLaunchUtilities
					if (!PerspectiveManager.isCurrentPerspective(window,
							IDebugUIConstants.ID_DEBUG_PERSPECTIVE)) {
						if (PerspectiveManager.shouldSwitchPerspective(window,
								IDebugUIConstants.ID_DEBUG_PERSPECTIVE)) {
							PerspectiveManager.switchToPerspective(window,
									IDebugUIConstants.ID_DEBUG_PERSPECTIVE);
						}
					}
				}

			});
		}
	}

	/*
	 * A property change listener which resets the server socket listener on
	 * every XDebug port change.
	 */
	private class PortChangeListener implements IPropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty()
					.equals(XDebugPreferenceMgr.XDEBUG_PREF_PORT)) {
				resetSocket();
			}
		}
	}

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
			String insert = session.getRemoteAddress().getCanonicalHostName()
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
}
