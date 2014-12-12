/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpUtils;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.IDBGpSessionListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;

public class DBGpMultiSessionTarget extends DBGpElement implements
		IPHPDebugTarget, IDBGpDebugTarget, IDBGpSessionListener,
		IDebugEventSetListener {

	// used to identify this debug target with the associated
	// script being debugged.
	private String sessionID;

	private String ideKey;

	private boolean webLaunch = false;

	// required for EXE target support
	private IProcess process;

	// required for Web target support
	private IWebBrowser browser;

	private String stopDebugURL;

	// debug target state
	private volatile int targetState;

	// waiting for 1st session
	private static final int STATE_INIT_SESSION_WAIT = 0;

	// Target fully started
	private static final int STATE_STARTED = 1;

	// ASync stop request made
	private static final int STATE_TERMINATING = 2;

	// terminated
	private static final int STATE_TERMINATED = 3;

	// the script being run, or initial web script
	private String scriptName;

	// launch object
	private ILaunch launch;

	private DBGpBreakpointFacade bpFacade;

	private DBGpPreferences sessionPreferences;

	private TimedEvent te = new TimedEvent();

	// debug config settings
	private boolean stopAtStart;

	private ArrayList<DBGpTarget> debugTargets = new ArrayList<DBGpTarget>();

	private PathMapper pathMapper;

	// need to have something in case a target is terminated before
	// a session is initiated to stop a NPE in the debug view
	private DebugOutput debugOutput = new DebugOutput();

	/**
	 * Base constructor
	 * 
	 */
	private DBGpMultiSessionTarget() {
		super(null);
		ideKey = DBGpSessionHandler.getInstance().getIDEKey();
		// listen for debug events
		DebugPlugin.getDefault().addDebugEventListener(this);
		fireCreationEvent();
		targetState = STATE_INIT_SESSION_WAIT;
	}

	/**
	 * Target that handles PHP Exe launches
	 * 
	 * @param launch
	 * @param process
	 * @param projectRelativeScript
	 * @param stopAtStart
	 * @throws CoreException
	 */
	public DBGpMultiSessionTarget(ILaunch launch, String projectRelativeScript,
			String ideKey, String sessionID, boolean stopAtStart)
			throws CoreException {
		this();
		this.stopAtStart = stopAtStart;
		this.launch = launch;
		this.scriptName = projectRelativeScript;
		this.ideKey = ideKey;
		this.webLaunch = false;
		this.sessionID = sessionID;
		this.process = null; // this will be set later
		this.stopDebugURL = null; // never set
		this.browser = null; // never set
	}

	/**
	 * target that handles invocation via a web browser
	 * 
	 * @param launch
	 * @param workspaceRelativeScript
	 * @param stopDebugURL
	 * @param sessionID
	 * @param stopAtStart
	 */
	public DBGpMultiSessionTarget(ILaunch launch,
			String workspaceRelativeScript, String stopDebugURL, String ideKey,
			boolean stopAtStart, IWebBrowser browser) {
		this();
		this.stopAtStart = stopAtStart;
		this.launch = launch;
		this.scriptName = workspaceRelativeScript;
		this.ideKey = ideKey;
		this.webLaunch = true;
		this.sessionID = null; // in the web launch we have no need for the
		// session ID.
		this.stopDebugURL = stopDebugURL;
		this.browser = browser;
		this.process = null; // no process indicates a web launch
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getDebugTarget()
	 */
	public IDebugTarget getDebugTarget() {
		return this;
	}

	public ILaunch getLaunch() {
		return launch;
	}

	public String getName() throws DebugException {
		// Multisession Manager
		return PHPDebugCoreMessages.XDebug_DBGpMultiSessionTarget_0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getProcess()
	 */
	public IProcess getProcess() {
		return process;
	}

	/**
	 * set the process
	 * 
	 * @param proc
	 */
	public void setProcess(IProcess proc) {
		process = proc;
	}

	public IThread[] getThreads() throws DebugException {
		return new IThread[0];
	}

	public boolean hasThreads() throws DebugException {
		return false;
	}

	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		synchronized (debugTargets) {
			if (debugTargets.size() > 0) {
				IDebugTarget firstTarget = debugTargets.get(0);
				return firstTarget.supportsBreakpoint(breakpoint);
			}
		}
		return false;
	}

	public boolean isSuspended() {
		boolean isSuspended = false;
		synchronized (debugTargets) {
			for (int i = 0; i < debugTargets.size() && !isSuspended; i++) {
				IDebugTarget target = debugTargets.get(i);
				isSuspended = isSuspended | target.isSuspended();
			}
		}
		return isSuspended;
	}

	public boolean isTerminated() {
		return targetState == STATE_TERMINATED;
	}

	public void terminate() throws DebugException {
		if (targetState == STATE_TERMINATING) {
			// we attempt a sledge hammer termination.
			synchronized (debugTargets) {
				if (debugTargets.size() > 0) {
					for (int i = 0; i < debugTargets.size(); i++) {
						IDebugTarget target = debugTargets.get(i);
						try {
							target.terminate();
						} catch (Exception e) {

						}
					}
				}
				// session listening will already have been removed
				terminateMultiSessionDebugTarget();
			}
			return;
		}

		synchronized (debugTargets) {
			// remove myself as a session listener.
			DBGpSessionHandler.getInstance().removeSessionListener(this);
			targetState = STATE_TERMINATING;
			if (debugTargets.size() > 0) {
				for (int i = 0; i < debugTargets.size(); i++) {
					IDebugTarget target = debugTargets.get(i);
					if (target.canTerminate()) {
						target.terminate();
					}
				}
			} else {
				terminateMultiSessionDebugTarget();
			}
		}
	}

	public boolean canDisconnect() {
		boolean canDisconnect = false;
		return canDisconnect;
	}

	public void disconnect() throws DebugException {
	}

	public boolean isDisconnected() {
		return false;
	}

	public boolean canTerminate() {
		boolean canTerminate = (targetState == STATE_STARTED || targetState == STATE_INIT_SESSION_WAIT);
		return canTerminate;
	}

	public boolean canResume() {
		boolean canResume = false;
		synchronized (debugTargets) {
			for (int i = 0; i < debugTargets.size() && !canResume; i++) {
				IDebugTarget target = debugTargets.get(i);
				canResume = canResume | target.canResume();
			}
		}
		return canResume;
	}

	public boolean canSuspend() {
		boolean canSuspend = false;
		synchronized (debugTargets) {
			for (int i = 0; i < debugTargets.size() && !canSuspend; i++) {
				IDebugTarget target = debugTargets.get(i);
				canSuspend = canSuspend | target.canSuspend();
			}
		}
		return canSuspend;
	}

	public void resume() throws DebugException {
		synchronized (debugTargets) {
			for (int i = 0; i < debugTargets.size(); i++) {
				IDebugTarget target = debugTargets.get(i);
				if (target.canResume()) {
					target.resume();
				}
			}
		}
	}

	public void suspend() throws DebugException {
		synchronized (debugTargets) {
			for (int i = 0; i < debugTargets.size(); i++) {
				IDebugTarget target = debugTargets.get(i);
				if (target.canSuspend()) {
					target.suspend();
				}
			}
		}
	}

	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	public boolean supportsStorageRetrieval() {
		return false;
	}

	/**
	 * returns if we have terminated or in the process of terminating
	 * 
	 * @return
	 */
	private boolean isTerminating() {
		boolean terminating = (targetState == STATE_TERMINATED)
				|| (targetState == STATE_TERMINATING);
		return terminating;
	}

	public void waitForInitialSession(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs, IProgressMonitor launchMonitor) {
		configureInitialState(facade, sessionPrefs);
		try {
			while (debugTargets.size() == 0 && !launch.isTerminated()
					&& !isTerminating() && !launchMonitor.isCanceled()) {
				te.waitForEvent(DBGpPreferences.DBGP_TIMEOUT_DEFAULT);
			}
		} catch (InterruptedException e) {
		}
		if (debugTargets.size() == 0) {
			DBGpSessionHandler.getInstance().removeSessionListener(this);
			terminateMultiSessionDebugTarget();
		}
	}

	public void sessionReceived(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs, DBGpTarget owningTarget,
			PathMapper globalMapper) {
		configureInitialState(facade, sessionPrefs);
		owningTarget.setMultiSessionManaged(true);
		addDebugTarget(owningTarget);
		setPathMapper(globalMapper);
		launch.addDebugTarget(owningTarget);
		owningTarget.sessionReceived(facade, sessionPrefs);
	}

	public void configureInitialState(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs) {
		bpFacade = facade;
		sessionPreferences = sessionPrefs;
	}

	public boolean SessionCreated(DBGpSession session) {
		boolean accepted = false;
		synchronized (debugTargets) {

			// we need to use single shot debug targets to ensure that
			// they terminate when complete and don't hang around waiting for
			// another session they won't receive.
			DBGpTarget target = new DBGpTarget(this.launch, this.scriptName,
					this.ideKey, this.sessionID, this.stopAtStart);
			target.setMultiSessionManaged(true);
			target.setPathMapper(pathMapper);
			accepted = target.SessionCreated(session);

			if (accepted) {
				// need to make sure bpFacade is thread safe.
				// cannot provide a launch monitor here, unless this is the
				// first launch, but it doesn't matter.
				target.waitForInitialSession(bpFacade, sessionPreferences, null);
				if (!target.isTerminated()) {
					addDebugTarget(target);
					launch.addDebugTarget(target);
					if (targetState == STATE_INIT_SESSION_WAIT) {
						targetState = STATE_STARTED;
						te.signalEvent();
					}
				}
			}
		}
		return accepted;
	}

	public void addDebugTarget(DBGpTarget target) {
		synchronized (debugTargets) {
			if (debugTargets.size() == 0) {
				// this is the first target, so clear out any old debug output
				// and set up new information.
				debugOutput = new DebugOutput();
			}
			debugTargets.add(target);
		}
	}

	public void breakpointAdded(IBreakpoint breakpoint) {
		// Do nothing

	}

	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// do nothing

	}

	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// do nothing

	}

	public void handleDebugEvents(DebugEvent[] events) {
		synchronized (debugTargets) {
			for (int i = 0; i < events.length; i++) {
				DebugEvent evt = events[i];
				Object src = evt.getSource();
				if (src instanceof DBGpTarget) {
					if (debugTargets.contains(src)) {
						// ok it is one of ours, what is the event
						int kind = evt.getKind();
						if (kind == DebugEvent.TERMINATE) {
							launch.removeDebugTarget((IDebugTarget) src);
							debugTargets.remove(src);
						}
					}
				}
			}
			if (targetState == STATE_TERMINATING && debugTargets.size() == 0) {
				// session listening was removed when we went to
				// STATE_TERMINATING
				terminateMultiSessionDebugTarget();
			}
		}
	}

	private void terminateMultiSessionDebugTarget() {
		if (webLaunch) {
			sendStopDebugURL();
		}
		targetState = STATE_TERMINATED;
		DebugPlugin.getDefault().removeDebugEventListener(this);
		fireTerminateEvent();

	}

	/**
	 * 
	 * 
	 */
	private void sendStopDebugURL() {
		if (stopDebugURL == null) {
			return;
		}

		try {
			if (browser != null) {
				DBGpLogger
						.debug("browser is not null, sending " + stopDebugURL); //$NON-NLS-1$
				browser.openURL(new URL(stopDebugURL));
			} else {
				DBGpUtils.openInternalBrowserView(stopDebugURL);
			}
		} catch (PartInitException e) {
			DBGpLogger.logException(
					"Failed to send stop URL: " + stopDebugURL, this, e); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			// this should never happen, if it does I want it in the log
			// as something will need to be fixed
			DBGpLogger.logException(null, this, e);
		}
	}

	public void setPathMapper(PathMapper mapper) {
		pathMapper = mapper;
	}

	/**
	 * return if this is a web launch
	 * 
	 * @return
	 */
	public boolean isWebLaunch() {
		return webLaunch;
	}

	public DebugOutput getOutputBuffer() {
		return debugOutput;
	}

	public boolean isWaiting() {
		boolean isWaiting = (targetState == STATE_INIT_SESSION_WAIT);
		synchronized (debugTargets) {
			for (int i = 0; i < debugTargets.size() && !isWaiting; i++) {
				IPHPDebugTarget target = debugTargets.get(i);
				isWaiting = isWaiting | target.isWaiting();
			}
		}
		return isWaiting;
	}
}
