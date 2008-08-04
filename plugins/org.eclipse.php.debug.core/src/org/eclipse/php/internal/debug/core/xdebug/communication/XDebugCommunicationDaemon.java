/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.debug.core.xdebug.communication;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.debug.core.*;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.sourcelookup.PHPSourceLookupDirector;
import org.eclipse.php.internal.debug.core.xdebug.IDELayer;
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

/**
 * XDebug communication daemon.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public class XDebugCommunicationDaemon extends AbstractDebuggerCommunicationDaemon {

	public static final String XDEBUG_DEBUGGER_ID = "org.eclipse.php.debug.core.xdebugDebugger";
	private PortChangeListener portChangeListener;

	/**
	 * An XDebug communication daemon.
	 */
	public XDebugCommunicationDaemon() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.daemon.AbstractDebuggerCommunicationDaemon#init()
	 */
	public void init() {
		initDeamonChangeListener();
		super.init();
	}

	/**
	 * Initialize a daemon change listener 
	 */
	protected void initDeamonChangeListener() {
		if (portChangeListener == null) {
			Preferences preferences = PHPDebugPlugin.getDefault().getPluginPreferences();
			portChangeListener = new PortChangeListener();
			preferences.addPropertyChangeListener(portChangeListener);
		}
	}

	/**
	 * Returns the server socket port used for the debug requests listening thread. 
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
	 * Returns if this daemon is a debugger daemon. 
	 * In this case, always return true.
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
			DBGpLogger.debug("Connection established: " + socket.toString());
		}
		
		try {
			DBGpSession session = new DBGpSession(socket);
			if (session.isActive()) {
				if (!DBGpSessionHandler.getInstance().fireSessionAdded(session)) {
					//Session not taken, we want to create a launch					
					AcceptRemoteSession aSess = XDebugPreferenceMgr.getAcceptRemoteSession();
					if (aSess != AcceptRemoteSession.off) {
						if (aSess == AcceptRemoteSession.localhost && session.getRemoteAddress().isLoopbackAddress() == false) {
							session.endSession();
						}
						else {
							// session was either localhost or from any outside one and
							// preferences allow it.
							createLaunch(session);
						}
					}
					else {
						//reject the session
						session.endSession();
					}
				}
			}
		}
		catch(Exception e) {
			DBGpLogger.logException("Unexpected Exception: Listener thread still listening", this, e);
		}
	}

	/**
	 * create a launch and appropriate debug targets to automate launch
	 * initiation. If any problems occurred, we can throw the session away using session.endSession();
	 * @param session the DBGpSession.
	 */
	private void createLaunch(DBGpSession session) {
		boolean stopAtFirstLine = true;
		DBGpTarget target = null;
		PathMapper p = null;
		PHPSourceLookupDirector srcLocator = new PHPSourceLookupDirector();
		srcLocator.initializeParticipants();
//		ILaunchConfiguration conf = new ILaunchConfiguration() {
//
//			public boolean contentsEqual(ILaunchConfiguration configuration) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public ILaunchConfigurationWorkingCopy copy(String name) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public void delete() throws CoreException {
//				// TODO Auto-generated method stub
//				
//			}
//
//			public boolean exists() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public boolean getAttribute(String attributeName, boolean defaultValue) throws CoreException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public int getAttribute(String attributeName, int defaultValue) throws CoreException {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//			public List getAttribute(String attributeName, List defaultValue) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public Set getAttribute(String attributeName, Set defaultValue) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public Map getAttribute(String attributeName, Map defaultValue) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public String getAttribute(String attributeName, String defaultValue) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public Map getAttributes() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public String getCategory() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public IFile getFile() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public IPath getLocation() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public IResource[] getMappedResources() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public String getMemento() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public Set getModes() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public String getName() {
//				// TODO Auto-generated method stub
//				return "JIT Launch";
//			}
//
//			public ILaunchDelegate getPreferredDelegate(Set modes) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public ILaunchConfigurationType getType() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public ILaunchConfigurationWorkingCopy getWorkingCopy() throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public boolean hasAttribute(String attributeName) throws CoreException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public boolean isLocal() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public boolean isMigrationCandidate() throws CoreException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public boolean isReadOnly() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public boolean isWorkingCopy() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public ILaunch launch(String mode, IProgressMonitor monitor) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public ILaunch launch(String mode, IProgressMonitor monitor, boolean build) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public ILaunch launch(String mode, IProgressMonitor monitor, boolean build, boolean register) throws CoreException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			public void migrate() throws CoreException {
//				// TODO Auto-generated method stub
//				
//			}
//
//			public boolean supportsMode(String mode) throws CoreException {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			public Object getAdapter(Class adapter) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		};
		
		ILaunch la = new Launch(null, ILaunchManager.DEBUG_MODE, srcLocator);
		boolean multiSession = XDebugPreferenceMgr.useMultiSession();

		if (session.getSessionId() == null && !multiSession) {
			// web launch
			target = new DBGpTarget(la, null, null, session.getIdeKey(), stopAtFirstLine, null);						
			Server server = null;
			Server[] servers = ServersManager.getServers();
			for (int i = 0; i < servers.length; i++) {
				if (servers[i].getPort() == session.getRemotePort() && 
					servers[i].getHost().equalsIgnoreCase(session.getRemoteHostname())) {
					server = servers[i];
					break;
				}
			}
			if (server != null) {
				p = PathMapperRegistry.getByServer(server);						
			}
			else {
				p = new PathMapper(); // create a temporary path mapper, we may look to holding these via the pathmapper registry in the future
				// but they would be persisted. We may try and find one for the particular server or create a temporary one.						
			}
			// need to add ourselves as a session listener for future sessions
			DBGpSessionHandler.getInstance().addSessionListener((IDBGpSessionListener)target);
		}
		else {
			// cli launch or multisession launch create a single shot target
			// The Launch Configuration, Source Locator.
			target = new DBGpTarget(la, null /*no script name*/, session.getIdeKey(), session.getSessionId(), stopAtFirstLine);
			//PathMapper p = PathMapperRegistry.getByPHPExe(null);
			p = new PathMapper(); // create a temporary path mapper, we may look to holding these via the pathmapper registry in the future
			// but they currently would be persisted.
		}

		// if we are multisession and the session was not picked up then we need a 
		// multisession target started and added to the launch and listening for more sessions. 
		if (multiSession) {
			DBGpMultiSessionTarget multiSessionTarget = new DBGpMultiSessionTarget(la, null, null, session.getIdeKey(), stopAtFirstLine, null);
			DBGpSessionHandler.getInstance().addSessionListener((IDBGpSessionListener)multiSessionTarget);			
			multiSessionTarget.addDebugTarget(target);
			la.addDebugTarget(multiSessionTarget);
		}

		target.setPathMapper(p);
		target.setSession(session);
		session.setDebugTarget(target);
		la.addDebugTarget(target);
		
		DebugPlugin.getDefault().getLaunchManager().addLaunch(la);
		target.sessionReceived((DBGpBreakpointFacade) IDELayerFactory.getIDELayer(), XDebugPreferenceMgr.createSessionPreferences());
		//probably could do waitForInitialSession as session has already been set.
		
		//org.eclipse.php.debug.ui.PHPDebugPerspective
		//org.eclipse.debug.ui.DebugPerspective
		//also look at the PHPLaunchUtilities
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				//code the debug perspectives.
				if (!PerspectiveManager.isCurrentPerspective(window, "org.eclipse.php.debug.ui.PHPDebugPerspective")) {
					if(PerspectiveManager.shouldSwitchPerspective(window, "org.eclipse.php.debug.ui.PHPDebugPerspective")) {
						PerspectiveManager.switchToPerspective(window, "org.eclipse.php.debug.ui.PHPDebugPerspective");
					}
				}
			}
			
		});
	}

	/*
	 * A property change listener which resets the server socket listener on every XDebug port change.
	 */
	private class PortChangeListener implements IPropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(XDebugPreferenceMgr.XDEBUG_PREF_PORT)) {
				resetSocket();
			}
		}
	}
}
