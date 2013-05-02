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

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.phar.PharPath;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.DebugOutput;
import org.eclipse.php.internal.debug.core.model.IPHPDebugTarget;
import org.eclipse.php.internal.debug.core.pathmapper.DebugSearchEngine;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.sourcelookup.PHPSourceLookupDirector;
import org.eclipse.php.internal.debug.core.sourcelookup.containers.PHPCompositeSourceContainer;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpoint;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpPreferences;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.*;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSession;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.DBGpSessionHandler;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.session.IDBGpSessionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBGpTarget extends DBGpElement implements IPHPDebugTarget,
		IDBGpDebugTarget, IStep, IBreakpointManagerListener,
		IDBGpSessionListener {

	// used to identify this debug target with the associated
	// script being debugged.
	private String sessionID;

	private String ideKey;

	private boolean webLaunch = false;

	private boolean multiSessionManaged = false;

	// required for EXE target support
	private IProcess process;

	// required for Web target support
	private IWebBrowser browser;

	private String stopDebugURL;

	// debug target state
	private volatile int targetState;

	private static final int STATE_CREATE = 0; // target creation

	private static final int STATE_INIT_SESSION_WAIT = 1; // waiting for 1st
	// session

	private static final int STATE_STARTED_SUSPENDED = 2; // suspended

	private static final int STATE_STARTED_RUNNING = 3; // running

	private static final int STATE_STARTED_SESSION_WAIT = 4; // web launch
	// waiting for
	// next session

	private static final int STATE_TERMINATING = 5; // ASync stop request made

	private static final int STATE_TERMINATED = 6; // terminated

	private static final int STATE_DISCONNECTED = 7; // disconnected

	// the script being run, or initial web script
	private String projectScript;

	// The name to return for this debug target.
	private String name;

	// launch object
	private ILaunch launch;

	// threads
	private DBGpThread langThread;

	private IThread[] allThreads;

	private int currentStackLevel;

	private IStackFrame[] stackFrames;

	private IVariable[] currentVariables;

	private DBGpBreakpointFacade bpFacade;

	// superglobal variable support, these are immutable, they cannot be changed
	private IVariable[] superGlobalVars;

	// used to cache dbgp commands to program while it is running
	private Vector<DBGpBreakpointCmd> DBGpCmdQueue = new Vector<DBGpBreakpointCmd>();

	// dbgp session support
	private volatile DBGpSession session;

	private DBGpPreferences sessionPreferences;

	private Object sessionMutex = new Object();

	private TimedEvent te = new TimedEvent();

	// debug config settings
	private boolean stopAtStart;

	private boolean asyncSupported;

	private boolean stepping;

	// private int maxChildren = 0;

	private PathMapper pathMapper = null;

	// need to have something in case a target is terminated before
	// a session is initiated to stop a NPE in the debug view
	private DebugOutput debugOutput = new DebugOutput();

	/**
	 * Base constructor
	 * 
	 */
	private DBGpTarget() {
		super(null);
		setState(STATE_CREATE);
		ideKey = DBGpSessionHandler.getInstance().getIDEKey();
		allThreads = new IThread[0]; // needs to be defined when target is
		// added to launch
		fireCreationEvent();
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
	public DBGpTarget(ILaunch launch, String projectRelativeScript,
			String ideKey, String sessionID, boolean stopAtStart) {
		this();
		this.stopAtStart = stopAtStart;
		this.launch = launch;
		this.projectScript = projectRelativeScript;
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
	public DBGpTarget(ILaunch launch, String workspaceRelativeScript,
			String stopDebugURL, String ideKey, boolean stopAtStart,
			IWebBrowser browser) {
		this();
		this.stopAtStart = stopAtStart;
		this.launch = launch;
		this.projectScript = workspaceRelativeScript;
		this.ideKey = ideKey;
		this.webLaunch = true;
		this.sessionID = null; // in the web launch we have no need for the
		// session ID.
		this.stopDebugURL = stopDebugURL;
		this.browser = browser;
		this.process = null;
	}

	/**
	 * wait for the initial dbgp session to be established
	 * 
	 * @param launchMonitor
	 */
	public void waitForInitialSession(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs, IProgressMonitor launchMonitor) {
		configureInitialState(facade, sessionPrefs);

		try {
			while (session == null && !launch.isTerminated()
					&& !isTerminating()
					&& (launchMonitor != null && !launchMonitor.isCanceled())) {

				// if we got here then session has not been updated
				// by the other thread yet, so wait. We wait for
				// an event or a timeout. Even if we timeout we could
				// still get the session before we re-enter the loop.
				te.waitForEvent(DBGpPreferences.DBGP_TIMEOUT_DEFAULT);
			}

			sessionReceived(launchMonitor);
		} catch (Exception e) {
			// cannot proceed any further as we will never be able to get a
			// session. The exception doesn't need logging.
			terminateDebugTarget(true);
		}
	}

	public void sessionReceived(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs) {
		configureInitialState(facade, sessionPrefs);
		sessionReceived(null);
	}

	public void configureInitialState(DBGpBreakpointFacade facade,
			DBGpPreferences sessionPrefs) {
		bpFacade = facade;
		sessionPreferences = sessionPrefs;
		setState(STATE_INIT_SESSION_WAIT);
	}

	private void sessionReceived(IProgressMonitor launchMonitor) {
		boolean launchIsCanceled = false;
		if (session != null && session.isActive()) {
			if (launchMonitor != null) {
				launchIsCanceled = launchMonitor.isCanceled();
			}

			if (!isTerminating() && !launch.isTerminated() && !launchIsCanceled) {
				langThread = new DBGpThread(this);
				allThreads = new IThread[] { langThread };
				langThread.fireCreationEvent();
				IBreakpointManager bpmgr = DebugPlugin.getDefault()
						.getBreakpointManager();
				bpmgr.addBreakpointListener(this);
				bpmgr.addBreakpointManagerListener(this);

				// Determine something about the initial script and path mapping
				testInitialScriptLocating();

				// the pathmapper dialog allows a user to terminate the debug
				// session.
				// so check to see if the session has gone away. Could also
				// check the
				// state of the target as well
				if (session != null) {
					initiateSession();
				}
			} else {
				session.endSession();
				terminateDebugTarget(true);
			}
		} else {
			terminateDebugTarget(true);
		}
	}

	/**
	 * test the initial script to see if we can locate it. If the script is
	 * within the workspace, then we don't need to do anything. If it isn't
	 * check to see if there is a path mapper for it. If not, see if we can
	 * create a path map entry based on the launch information. If we still
	 * cannot do this, prompt the user as we may need info in order to set
	 * breakpoints correctly. TODO: XDebug seemed to accept relative paths as
	 * well as absolute paths, need to investigate further.
	 */
	private void testInitialScriptLocating() {
		String initScript = session.getInitialScript();
		if (initScript != null) {
			// see if the file is in the workspace.
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(new Path(initScript));
			if (file == null) {
				// ok initial script is not in the workspace
				// we could do a search or do an automatic path mapping
				if (pathMapper != null) {
					if (pathMapper.getLocalFile(initScript) == null) {
						if (projectScript != null) {
							// we have a project script so it must be a PDT
							// launch
							handlePDTSessionInitiation(initScript);
						} else {
							// this was a remotely initiated launch as we don't
							// have a project script
							handleRemoteSessionInitiation(initScript);
						}
					}
				}
			}
		}
	}

	/**
	 * handle a PDT launch debug initiation session
	 * 
	 * @param initScript
	 *            the initial script being executed
	 */
	private void handlePDTSessionInitiation(String initScript) {
		VirtualPath vpScr = new VirtualPath(projectScript);
		VirtualPath vpInit = new VirtualPath(initScript);
		// TODO: What happens if there is a difference in case ?
		if (vpScr.getLastSegment().equals(vpInit.getLastSegment())) {
			PathEntry pe = new PathEntry(projectScript,
					PathEntry.Type.WORKSPACE, ResourcesPlugin.getWorkspace()
							.getRoot());
			pathMapper.addEntry(initScript, pe);
		} else {
			// ok, the initial script doesn't match what was passed into
			// the launch, need to locate the required script.
			// it may be possible to determine it from the project name
			// so long as the project name is part of the web server file
			// structure, so we could try this.
			// TODO see if the scriptName is part of the init structure, if
			// so we could workout the local file.
			try {
				DebugSearchEngine.find(initScript, this);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * handle a Remotely Initiated launch debug session
	 * 
	 * @param initScript
	 *            the initial script being executed
	 */

	private void handleRemoteSessionInitiation(String initScript) {
		try {
			PathEntry pe = DebugSearchEngine.find(pathMapper, initScript, null,
					this);
			if (pe != null) {
				Object container = pe.getContainer();
				if (container != null && container instanceof IResource) {
					IResource res = (IResource) container;
					IProject prj = res.getProject();
					PHPSourceLookupDirector dir = (PHPSourceLookupDirector) getLaunch()
							.getSourceLocator();
					// ISourceContainer[] containers = new ISourceContainer[]
					// {new ProjectSourceContainer(prj, false)};
					ISourceContainer[] containers = new ISourceContainer[] { new PHPCompositeSourceContainer(
							prj, null) };
					dir.setSourceContainers(containers);
				}
			} else {
				// either no file was found, or the user pressed the stop
				// debugger
				if (isTerminated() == false) {
					// stop wasn't pressed
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							// No appropriate file located or no file selected.
							// Debug Terminated
							MessageDialog
									.openError(
											Display.getDefault()
													.getActiveShell(),
											PHPDebugCoreMessages.XDebugMessage_debugError,
											PHPDebugCoreMessages.XDebug_DBGpTarget_0);
						}
					});
					session.endSession();
					terminateDebugTarget(true);
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * initiate the session, this cannot be called from the DBGpSession response
	 * handler thread as we install breakpoints synchronously and block waiting
	 * for the response thread to pick them up, so we will deadlock
	 * 
	 */
	private void initiateSession() {
		if (targetState != STATE_INIT_SESSION_WAIT
				&& targetState != STATE_STARTED_SESSION_WAIT) {
			DBGpLogger
					.logWarning(
							"initiateSession in Wrong State: " + targetState, this, null); //$NON-NLS-1$
		}
		stackFrames = null;
		currentVariables = null;
		superGlobalVars = null;
		// clear any previous debug output object and create a new one.
		debugOutput = new DebugOutput();

		session.startSession();

		// we are effectively suspended once the session has handshaked until we
		// run
		setState(STATE_STARTED_SUSPENDED);
		negotiateDBGpFeatures();
		loadPredefinedBreakpoints();
		if (!stopAtStart) {
			// set state before issuing a run otherwise a timing window occurs
			// where
			// a run could suspend, the thread sets state to suspend but then
			// this
			// thread sets it to running.
			setState(STATE_STARTED_RUNNING);
			session.sendAsyncCmd(DBGpCommand.run);
		} else {
			// first say we are suspended on a breakpoint to trigger a
			// perspective switch
			// then do an initial step into to step onto the 1st line
			suspended(DebugEvent.BREAKPOINT);
			try {
				stepInto();
			} catch (DebugException e) {
			}
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
	 */
	public IThread[] getThreads() throws DebugException {
		return allThreads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	public boolean hasThreads() throws DebugException {
		boolean hasThreads = allThreads.length > 0;
		return hasThreads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getName()
	 */
	public String getName() throws DebugException {
		if (name == null) {
			if (isWebLaunch() || multiSessionManaged) {
				// remote launch
				name = PHPDebugCoreMessages.XDebug_DBGpTarget_1;
			} else {
				if (projectScript == null) {
					if (session != null) {
						name = session.getInitialScript();
					} else {
						// Unknown PHP Program
						name = PHPDebugCoreMessages.XDebug_DBGpTarget_2;
					}
				} else {
					name = this.projectScript;
				}
			}
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getDebugTarget()
	 */
	public IDebugTarget getDebugTarget() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getLaunch()
	 */
	public ILaunch getLaunch() {
		return launch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {

		// can only terminate if we have not terminated (allow for terminating
		// state as well to be safe).
		boolean canTerminate = (STATE_TERMINATED != targetState
				&& STATE_CREATE != targetState && STATE_DISCONNECTED != targetState);
		if (process != null) {
			canTerminate = canTerminate && process.canTerminate();
		}
		return canTerminate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */

	public boolean isTerminated() {
		boolean terminated = (STATE_TERMINATED == targetState);
		if (process != null) {
			return process.isTerminated();
		} else {
			return terminated;
		}
	}

	/**
	 * returns if we have terminated or in the process of terminating
	 * 
	 * @return
	 */
	public boolean isTerminating() {
		boolean terminating = (targetState == STATE_TERMINATED)
				|| (targetState == STATE_TERMINATING);
		return terminating;
	}

	/**
	 * returns is the debug target has started and is not terminating
	 * 
	 * @return
	 */
	public boolean hasStarted() {
		boolean started = (STATE_STARTED_RUNNING == targetState)
				|| (STATE_STARTED_SESSION_WAIT == targetState)
				|| (STATE_STARTED_SUSPENDED == targetState);
		return started;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		if (isTerminating()) {
			// just in case we had some problem sending the stop command, allow
			// terminate to still work.
			if (session == null && STATE_TERMINATING == targetState) {
				terminateDebugTarget(true);
			}
			return;
		}

		// we won't accept any more sessions, so stop listening
		DBGpSessionHandler.getInstance().removeSessionListener(this);

		if (STATE_STARTED_SUSPENDED == targetState) {
			// we are suspended, so we can send the stop request to do a clean
			// termination
			synchronized (sessionMutex) {
				if (session != null && session.isActive()) {
					setState(STATE_TERMINATING);
					session.sendAsyncCmd(DBGpCommand.stop);
					// we don't terminateDebugTarget here, we wait for the
					// response from the program under debug
				} else {
					terminateDebugTarget(true);
				}
			}
		} else {
			// we cannot terminate cleanly so we terminate as best we can
			terminateDebugTarget(true);
			if (isWebLaunch()) {
				// We were a web launch so we must now send the stop url
				sendStopDebugURL();
			}
		}
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

	/**
	 * Called by DBGpSession when the session terminates. The session terminates
	 * if we explicitly stop the session, or the script completes process then
	 * it will be a remote server version, so we don't want to terminate the
	 * debug target, but the session will have ended. This target needs to
	 * either be terminated manually or wait for another debug session to be
	 * attached.
	 */
	public void sessionEnded() {
		boolean unexpectedTermination = false;

		synchronized (sessionMutex) {
			session = null;
			if (STATE_TERMINATING == targetState) {
				// we are terminating, if we are a web launch, we need to issue
				// the
				// stop URL, then terminate the debug target.
				if (isWebLaunch()) {
					sendStopDebugURL();
				}
				terminateDebugTarget(true);
			} else {

				// if we were suspended and we are now terminating then
				// something
				// has caused debug to end, most likely a bad eval.
				unexpectedTermination = isSuspended();

				// we were not terminating and the session ended. If we are a
				// web
				// launch, then we need to wait for the next session. Otherwise
				// we
				// terminate the debug target.
				if (isWebLaunch()) {
					if (isSuspended()) {
						// if we are suspended, then inform eclipse we have
						// resumed
						// so all the user can do is terminate or disconnect
						// while
						// waiting for the next session.
						fireResumeEvent(DebugEvent.RESUME);
						langThread.fireResumeEvent(DebugEvent.RESUME);
					}
					stepping = false;
					setState(STATE_STARTED_SESSION_WAIT);
					langThread.setBreakpoints(null);
				} else {
					terminateDebugTarget(true);
				}
			}
		}

		if (unexpectedTermination) {
			// an unexpected termination occurred, so put out a message.
			final String errorMessage = PHPDebugCoreMessages.XDebugMessage_unexpectedTermination;
			Status status = new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, null);
			DebugPlugin.log(status);
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(Display.getDefault()
							.getActiveShell(),
							PHPDebugCoreMessages.XDebugMessage_debugError,
							errorMessage);
				}
			});

		}
	}

	/**
	 * Terminate this debug target, either because we are terminating the thing
	 * being debugged or we are disconnecting
	 * 
	 * @param isTerminate
	 *            if we are terminating rather than disconnecting
	 */
	public void terminateDebugTarget(boolean isTerminate) {
		// check we haven't already terminated
		if (STATE_TERMINATED != targetState) {
			DBGpSessionHandler.getInstance().removeSessionListener(this);
			IBreakpointManager bpmgr = DebugPlugin.getDefault()
					.getBreakpointManager();
			bpmgr.removeBreakpointListener(this);
			bpmgr.removeBreakpointManagerListener(this);

			if (isTerminate && STATE_STARTED_RUNNING == targetState) {
				setState(STATE_TERMINATING);
				if (process != null) {
					try {

						// terminate the process even if Eclipse may also
						// attempt this depending on what the user selected
						// to terminate.
						process.terminate();
					} catch (DebugException e) {
						// ignore any exceptions here
					}
				} else {
					// this is still required as we could enter
					// terminateDebugTarget without
					// session.endSession being called eg when stop debugger is
					// pressed on the DebugSearchEngine Dialog.
					if (session != null) {
						session.endSession();
					}
				}
			}

			setState(STATE_TERMINATED);
			if (session != null) {
				session.endSession();
			}
			if (langThread != null) {
				langThread.fireTerminateEvent();
			}
			stepping = false;
			fireTerminateEvent();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval
	 * ()
	 */
	public boolean supportsStorageRetrieval() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long,
	 * long)
	 */
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return !isTerminated() && isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return asyncSupported;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		boolean isSuspended = (STATE_STARTED_SUSPENDED == targetState);
		return isSuspended;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	public boolean canStepInto() {
		return isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	public boolean canStepOver() {
		return isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	public boolean canStepReturn() {
		// can only step return if there is a method above it, ie there is
		// at least one stack frame above the current stack frame.
		try {
			if (isSuspended() && getCurrentStackFrames().length > 1) {
				return true;
			}
		} catch (DebugException e) {
			// ignore the exception if it fails then we cannot stepReturn
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	public boolean isStepping() {
		return stepping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	public void stepInto() throws DebugException {
		stepping = true;
		resumed(DebugEvent.STEP_INTO);
		session.sendAsyncCmd(DBGpCommand.stepInto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	public void stepOver() throws DebugException {
		stepping = true;
		resumed(DebugEvent.STEP_OVER);
		session.sendAsyncCmd(DBGpCommand.stepOver);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	public void stepReturn() throws DebugException {
		stepping = true;
		resumed(DebugEvent.STEP_RETURN);
		session.sendAsyncCmd(DBGpCommand.StepOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		stepping = false;
		resumed(DebugEvent.RESUME);
		// bug in eclipse 3.2. When I issue a resume when a disconnect
		// is done, the resume button can still be pressed which
		// wouldn't work as the session has gone.
		synchronized (sessionMutex) {
			if (session != null && session.isActive()) {
				session.sendAsyncCmd(DBGpCommand.run);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		synchronized (sessionMutex) {
			if (session != null && session.isActive()) {
				session.sendAsyncCmd(DBGpCommand.suspend);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	public boolean canDisconnect() {
		boolean canDisconnect = STATE_STARTED_RUNNING == targetState
				|| STATE_STARTED_SUSPENDED == targetState;
		return canDisconnect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	public void disconnect() throws DebugException {
		if (isTerminating()) {
			return;
		}
		if (STATE_STARTED_RUNNING == targetState
				|| STATE_STARTED_SUSPENDED == targetState) {
			// we are in the middle of a debug session, single or multi
			// makes no difference, we should stop it
			setState(STATE_DISCONNECTED);
			// TODO: May need to synchronize
			if (session != null) {
				if (!isWebLaunch()) {
					// not a web launch, but could be multi session so we
					// can't just detach
					if (multiSessionManaged
							&& session.getEngineType() == EngineTypes.Xdebug
							&& versionCheckLT(session.getEngineVersion(),
									"2.0.2")) { //$NON-NLS-1$
						// we have to do a stop if xdebug and < 2.0.2
						session.sendSyncCmd(DBGpCommand.stop);
					} else {
						session.sendAsyncCmd(DBGpCommand.detach);
					}
					terminateDebugTarget(false);
				} else {
					// detaching xdebug on apache prior to version 2.0.2
					// causes debug to stop working on the server.
					if (session.getEngineType() == EngineTypes.Xdebug
							&& versionCheckLT(session.getEngineVersion(),
									"2.0.2")) { //$NON-NLS-1$

						// we have to do a stop if xdebug and < 2.0.2
						session.sendSyncCmd(DBGpCommand.stop);
					} else {
						session.sendAsyncCmd(DBGpCommand.detach);
					}
					stepping = false;
					langThread.setBreakpoints(null);
					setState(STATE_STARTED_SESSION_WAIT);
					resumed(DebugEvent.RESUME);

				}
			}
		}
	}

	private boolean versionCheckLT(String engineVersion, String requiredVersion) {
		boolean isLessThan = true;
		boolean isEqual = true;
		StringTokenizer stEngine = new StringTokenizer(engineVersion, "."); //$NON-NLS-1$
		StringTokenizer stCheck = new StringTokenizer(requiredVersion, "."); //$NON-NLS-1$
		while (stEngine.hasMoreTokens()) {
			String engineValStr = stEngine.nextToken();
			if (stCheck.hasMoreTokens()) {
				String checkValStr = stCheck.nextToken();
				try {
					int engineVal = Integer.parseInt(engineValStr);
					try {
						int checkVal = Integer.parseInt(checkValStr);
						if (engineVal > checkVal) {
							isLessThan = false;
							isEqual = false;
						}
						if (engineVal != checkVal) {
							isEqual = false;
						}
					} catch (NumberFormatException nfe) {
						// we are comparing a number to a number followed by
						// characters
						// NOT REQUIRED TO BE SUPPORTED
					}

				} catch (NumberFormatException nfe) {
					// we are comparing a number followed by characters with a
					// number
					int engineVal = getNumber(engineValStr);
					isEqual = false;
					try {
						int checkVal = Integer.parseInt(checkValStr);
						if (engineVal > checkVal) {
							isLessThan = false;
							isEqual = false;
						}
					} catch (NumberFormatException nfe2) {
						// we are comparing a number to a number followed by
						// characters
						// NOT REQUIRED TO BE SUPPORTED
					}
				}
			}
		}
		if (stCheck.hasMoreTokens()) {
			// check has more tokens so if equal so far then 2.0 2.0.(anything)
			// means
			// we must be less than
			isEqual = false;
		}

		return isLessThan && !isEqual;
	}

	/**
	 * this will only work if there are non digits in there.
	 * 
	 * @param engineValStr
	 * @return
	 */
	private int getNumber(String engineValStr) {
		int x = -1;
		for (int i = 0; i < engineValStr.length(); i++) {
			char ch = engineValStr.charAt(i);
			if (!Character.isDigit(ch) && i > 0) {
				x = Integer.parseInt(engineValStr.substring(0, i));
				break;
			}
		}
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
	 */
	public boolean isDisconnected() {
		return STATE_DISCONNECTED == targetState
				|| STATE_TERMINATED == targetState;
	}

	/**
	 * fire a resume event
	 * 
	 * @param detail
	 */
	private void resumed(int detail) {
		setState(STATE_STARTED_RUNNING);
		fireResumeEvent(detail);
		langThread.fireResumeEvent(detail);
	}

	/**
	 * fire a suspend event
	 * 
	 * @param detail
	 */
	public void suspended(int detail) {
		setState(STATE_STARTED_SUSPENDED);
		processQueuedBpCmds();
		stackFrames = null;
		currentVariables = null;
		superGlobalVars = null;
		fireSuspendEvent(detail);
		langThread.fireSuspendEvent(detail);
	}

	/**
	 * setup DBGp specific features, or get information about environment
	 */
	private void negotiateDBGpFeatures() {
		DBGpResponse resp;
		resp = session.sendSyncCmd(DBGpCommand.featureSet,
				"-n show_hidden -v 1"); //$NON-NLS-1$
		// check the responses, but keep going.
		DBGpUtils.isGoodDBGpResponse(this, resp);
		resp = session.sendSyncCmd(DBGpCommand.featureSet,
				"-n max_depth -v " + getMaxDepth()); //$NON-NLS-1$
		DBGpUtils.isGoodDBGpResponse(this, resp);
		resp = session.sendSyncCmd(DBGpCommand.featureSet,
				"-n max_children -v " + getMaxChildren()); //$NON-NLS-1$
		DBGpUtils.isGoodDBGpResponse(this, resp);
		resp = session.sendSyncCmd(DBGpCommand.featureGet, "-n encoding"); //$NON-NLS-1$
		if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
			Node child = resp.getParentNode().getFirstChild();
			if (child != null) {
				String data = child.getNodeValue();
				try {
					"abcdefg".getBytes(data); //$NON-NLS-1$
					session.setSessionEncoding(data);
				} catch (UnsupportedEncodingException uee) {
					DBGpLogger.logWarning(
							"encoding from debug engine invalid", this, uee); //$NON-NLS-1$
				}
			}
		}

		asyncSupported = false;
		resp = session.sendSyncCmd(DBGpCommand.featureGet, "-n supports_async"); //$NON-NLS-1$
		if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
			// TODO: could check the supported atttribute ?
			//String supportedAttr = DBGpResponse.getAttribute(resp, "supported"); 
			Node child = resp.getParentNode().getFirstChild();
			if (child != null) {
				String supported = child.getNodeValue();
				if (supported != null && supported.equals("1")) { //$NON-NLS-1$
					asyncSupported = true;
				}
			}
		}

		resp = session.sendSyncCmd(DBGpCommand.stdout,
				"-c " + getCaptureStdout()); //$NON-NLS-1$
		DBGpUtils.isGoodDBGpResponse(this, resp);
		resp = session.sendSyncCmd(DBGpCommand.stderr,
				"-c " + getCaptureStderr()); //$NON-NLS-1$
		DBGpUtils.isGoodDBGpResponse(this, resp);
	}

	/**
	 * Returns the current stack frames in the target.
	 * 
	 * @return the current stack frames in the target
	 * @throws DebugException
	 *             if unable to perform the request
	 */
	protected synchronized IStackFrame[] getCurrentStackFrames()
			throws DebugException {
		/*
		 * <response command="stack_get" transaction_id="transaction_id"> <stack
		 * level="{NUM}" type="file|eval|?" filename="..." lineno="{NUM}"
		 * where="" cmdbegin="line_number:offset" cmdend="line_number:offset"/>
		 * <stack level="{NUM}" type="file|eval|?" filename="..."
		 * lineno="{NUM}"> <input level="{NUM}" type="file|eval|?"
		 * filename="..." lineno="{NUM}"/> </stack> </response>
		 */

		// this can be called from multiple threads, as the data it manages
		// is global across the debug target, you could end up with 2 threads
		// doing this at the same time on will be getting the data and the other
		// will not and returning null as the data is not yet ready.
		if (stackFrames == null) {
			currentStackLevel = 0;
			stackFrames = new IStackFrame[0];
			synchronized (sessionMutex) {
				if (session != null && session.isActive()) {
					DBGpResponse resp = session
							.sendSyncCmd(DBGpCommand.stackGet);
					if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
						Node parent = resp.getParentNode();
						NodeList stackNodes = parent.getChildNodes(); // <stack>
						// entries
						stackFrames = new IStackFrame[stackNodes.getLength()];
						for (int i = 0; i < stackNodes.getLength(); i++) {
							Node stackNode = stackNodes.item(i);
							stackFrames[i] = new DBGpStackFrame(langThread,
									stackNode);
						}
						currentStackLevel = stackNodes.getLength() - 1;
					}
				}
			}
		}
		return stackFrames;
	}

	/**
	 * get the local variables at a particular stack level. Never returns null
	 * (IVariable[0]).
	 * 
	 * @param level
	 * @return
	 */
	private IVariable[] getContextLocalVars(String level) {
		DBGpResponse resp = session.sendSyncCmd(DBGpCommand.contextGet,
				"-d " + level); //$NON-NLS-1$
		return parseVarResp(resp, level);
	}

	/**
	 * get the super globals. never returns null (IVariable[0]). Cache the info
	 * so that it is never got again when going to other stack levels to view
	 * variables.
	 * 
	 * @return
	 */
	private IVariable[] getSuperGlobalVars() {
		if (superGlobalVars == null) {
			DBGpResponse resp = session.sendSyncCmd(DBGpCommand.contextGet,
					"-c 1"); //$NON-NLS-1$
			// Parse this into a variables block, switch on preload just for
			// this
			superGlobalVars = parseVarResp(resp, "-1"); //$NON-NLS-1$
		}
		return superGlobalVars;
	}

	/**
	 * get all variables to be displayed. Never returns null (IVariable[0])
	 * cache the top level stack frame as this is the one most likely always
	 * requested multiple times.
	 * 
	 * @param level
	 * @return
	 */
	public IVariable[] getVariables(String level) {
		synchronized (sessionMutex) {
			if (session != null && session.isActive()) {
				if (level.equals("0")) { //$NON-NLS-1$
					// level "0" is the current stack frame
					// TODO: we could cache previous level stack frames as well
					// for
					// performance in stackframe switching in the future.
					// TODO: see if preferences have changed about superglobals
					if (currentVariables == null) {
						currentVariables = getContextAtLevel(level);
						return currentVariables;
					}
					DBGpLogger
							.debug("getVariables: returning cached variables"); //$NON-NLS-1$
					return currentVariables;
				} else {
					return getContextAtLevel(level);
				}
			}
			return new IVariable[0];
		}
	}

	private IVariable[] getContextAtLevel(String level) {
		boolean getSuperGlobals = showGLobals();
		IVariable[] globals = null;
		if (getSuperGlobals) {
			globals = getSuperGlobalVars();
		} else {
			globals = new IVariable[0];
		}
		IVariable[] locals = getContextLocalVars(level);
		int totalLength = globals.length + locals.length;

		IVariable[] merged = new IVariable[totalLength];

		if (globals.length > 0) {
			System.arraycopy(globals, 0, merged, 0, globals.length);
		}
		if (locals.length > 0) {
			System.arraycopy(locals, 0, merged, globals.length, locals.length);
		}
		return merged;
	}

	/**
	 * parse each variable request response, never returns null (IVariable[0])
	 * 
	 * @param resp
	 * @param reportedLevel
	 * @return
	 */
	private IVariable[] parseVarResp(DBGpResponse resp, String reportedLevel) {
		IVariable[] variables = new IVariable[0];

		// If you cannot get a property, then a single variable is created with
		// no information as their is a child node, if there are no variables
		// this method creates a 0 size array which is good.
		if (DBGpUtils.isGoodDBGpResponse(this, resp)
				&& resp.getErrorCode() == DBGpResponse.ERROR_OK) {
			Node parent = resp.getParentNode();
			NodeList properties = parent.getChildNodes();
			variables = new DBGpVariable[properties.getLength()];
			for (int i = 0; i < properties.getLength(); i++) {
				Node property = properties.item(i);
				variables[i] = new DBGpVariable(this, property, reportedLevel);
			}
		}
		return variables;
	}

	/**
	 * set a variable to a particular value
	 * 
	 * @param fullName
	 * @param stackLevel
	 * @param data
	 * @return
	 */
	public boolean setProperty(DBGpVariable var, String data) {

		// XDebug expects all data to be base64 encoded.
		// In this case we don't use session encoding, we use transfer
		// encoding as we want control over the bytes being placed into the
		// variable at the other end.
		String encoded;
		try {
			encoded = Base64.encode(data.getBytes(getBinaryEncoding()));
		} catch (UnsupportedEncodingException e1) {
			// should never happen
			DBGpLogger.logException("unexpected encoding problem", this, e1); //$NON-NLS-1$
			encoded = Base64.encode(data.getBytes());
		}
		String fullName = var.getFullName();
		String stackLevel = var.getStackLevel();
		String args = "-n " + fullName + " -d " + stackLevel + " -l " + encoded.length() + " -- " + encoded; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		try {
			if (var.getReferenceTypeName().equals(DBGpVariable.PHP_STRING)) {
				// this ensures XDebug doesn't use eval
				args = "-t string " + args; //$NON-NLS-1$
			}
		} catch (DebugException e) {
		}

		DBGpResponse resp = session.sendSyncCmd(DBGpCommand.propSet, args);
		boolean success = false;
		if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
			if (resp.getTopAttribute("success").equals("1")) { //$NON-NLS-1$ //$NON-NLS-2$
				if (!stackLevel.equals("0")) { //$NON-NLS-1$
					// a variable has been changed on a previous stack
					// the gui won't have updated the current stack
					// level view, so we invalidate the cache to reload
					// the data. The variable also could have been a super
					// global, so invalid the superglobal cache as well.
					currentVariables = null;
					superGlobalVars = null;
				}
				success = true;
			}
		}
		return success;
	}

	/**
	 * get a variable at a particular stack level and page number
	 * 
	 * @param fullName
	 * @param stackLevel
	 * @param page
	 * @return
	 */
	public Node getProperty(String fullName, String stackLevel, int page) {
		if (fullName != null && fullName.trim().length() != 0) {
			String args = "-n " + fullName + " -d " + stackLevel + " -p " + page; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			if (stackLevel.equals("-1")) { //$NON-NLS-1$
				// the following line should work but doesn't in 2.0.0rc1 of
				// XDebug
				// args = "-n " + fullName + " -c 1 -p " + page;
				// but the following works for both rc1 and beyond so will keep
				// it
				// like this for now.
				args = "-n " + fullName + " -d " + getCurrentStackLevel() + " -p " + page; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			DBGpResponse resp = session.sendSyncCmd(DBGpCommand.propGet, args);
			if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
				return resp.getParentNode().getFirstChild();
			}
		}

		// either a bad response or we have a temporary variable from the watch
		// expression
		// which we cannot get the results from.
		return null;
	}

	/**
	 * get a variable at a particular stack level and page number
	 * 
	 * @param fullName
	 * @param stackLevel
	 * @param page
	 * @return
	 */
	public Node getCompleteString(String fullName, String stackLevel, int length) {
		if (fullName != null && fullName.trim().length() != 0) {
			String args = "-n " + fullName + " -d " + stackLevel; //$NON-NLS-1$ //$NON-NLS-2$
			if (stackLevel.equals("-1")) { //$NON-NLS-1$
				// the following line should work but doesn't in 2.0.0rc1 of
				// XDebug
				// args = "-n " + fullName + " -c 1 -p " + page;
				// but the following works for both rc1 and beyond so will keep
				// it
				// like this for now.
				args = "-n " + fullName + " -d " + getCurrentStackLevel(); //$NON-NLS-1$ //$NON-NLS-2$
			}
			// I don't believe the -m option is required for getValue as the
			// spec says you should use getValue to retrieve the entire data
			// but xdebug won't work without it.
			args += " -m " + length; //$NON-NLS-1$
			DBGpResponse resp = session
					.sendSyncCmd(DBGpCommand.propValue, args);
			if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
				return resp.getParentNode();
			}
		}

		// either a bad response or we have a temporary variable from the watch
		// expression
		// which we cannot get the results from.
		return null;
	}

	/**
	 * perform an eval request
	 * 
	 * @param toEval
	 * @return
	 */
	public Node eval(String toEval) {
		// XDebug expects all data to be base64 encoded.
		// Convert to session encoding bytes 1st before converting to Base64
		String encoded = Base64.encode(getSessionEncodingBytes(toEval));
		String args = "-- " + encoded; //$NON-NLS-1$

		Node response = null;
		synchronized (sessionMutex) {
			if (session != null && session.isActive()) {
				DBGpResponse resp = session.sendSyncCmd(DBGpCommand.eval, args);
				if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
					response = resp.getParentNode().getFirstChild();
				}
			}
		}
		return response;
	}

	/**
	 * set the state of the debug target
	 * 
	 * @param newState
	 */
	private synchronized void setState(int newState) {
		// TODO: Improvement: build a proper finite state machine with tests
		if (DBGpLogger.debugState()) {
			String newStateStr = ""; //$NON-NLS-1$
			switch (newState) {
			case STATE_CREATE:
				newStateStr = "STATE_CREATE"; //$NON-NLS-1$
				break;
			case STATE_DISCONNECTED:
				newStateStr = "STATE_DISCONNECTED"; //$NON-NLS-1$
				break;
			case STATE_INIT_SESSION_WAIT:
				newStateStr = "INIT_SESSION_WAIT"; //$NON-NLS-1$
				break;
			case STATE_STARTED_RUNNING:
				newStateStr = "STATE_STARTED_RUNNING"; //$NON-NLS-1$
				break;
			case STATE_STARTED_SESSION_WAIT:
				newStateStr = "STATE_STARTED_SESSION_WAIT"; //$NON-NLS-1$
				break;
			case STATE_STARTED_SUSPENDED:
				newStateStr = "STATE_STARTED_SUSPENDED"; //$NON-NLS-1$
				break;
			case STATE_TERMINATED:
				newStateStr = "STATE_TERMINATED"; //$NON-NLS-1$
				break;
			case STATE_TERMINATING:
				newStateStr = "STATE_TERMINATING"; //$NON-NLS-1$
				break;

			}
			DBGpLogger.debug("State Change: " + newStateStr); //$NON-NLS-1$
		}
		targetState = newState;
	}

	/**
	 * get current stack depth
	 * 
	 * @return
	 */
	public int getCurrentStackLevel() {
		return currentStackLevel;
	}

	/*
	 * get the max number of children
	 */
	// public int getMaxChildren() {
	// return maxChildren;
	// }

	/**
	 * map the file on this file system to the external one expected by xdebug
	 * 1. file is in the workspace a) use PDT Path mapper workspace definition
	 * b) if no mapping found use external file name and PDT path mapper file
	 * system definition c) if no mapping found then send external file name 2.
	 * file is outside of the workspace a) use PDT Path mapper and PDT path
	 * mapper file system definition b) if no mapping found then send as is
	 * (cannot use Internal Path mapper here)
	 * 
	 * @param bp
	 *            the breakpoint which references the file to be mapped to an
	 *            external file
	 * @return a string representing the external file in absolute format.
	 */
	private String mapToExternalFileIfRequired(DBGpBreakpoint bp) {
		String internalFile = ""; //$NON-NLS-1$
		String mappedFileName = null;

		if (pathMapper != null) {
			if (bp.getIFile() != null) {
				// file is defined in the workspace so attempt to map it using
				// the workspace definition
				internalFile = bp.getIFile().getFullPath().toString();
				mappedFileName = pathMapper.getRemoteFile(internalFile);
			}

			if (mappedFileName == null) {
				// file is not defined in the workspace or no mapping for
				// workspace file exists
				// so try to map the fully qualified file.
				internalFile = bp.getFileName();
				mappedFileName = pathMapper.getRemoteFile(internalFile);
			}
		}

		if (mappedFileName == null) {
			DBGpLogger
					.debug("outbound File '" + internalFile + "' Not remapped"); //$NON-NLS-1$ //$NON-NLS-2$
			mappedFileName = bp.getFileName(); // use the fully qualified
												// location of the file
		} else {
			if (DBGpLogger.debugBP()) {
				String mapMsg = "remapped eclipse file: '" + internalFile + "' to '" + mappedFileName + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				DBGpLogger.debug(mapMsg);
			}
		}

		return mappedFileName;
	}

	/**
	 * map a decoded external absolute file to an absolute one the workspace
	 * will hopefully recognise. rules to decide if mapping is required:
	 * 
	 * 1. if the file does exist a) if it mapping found -> remap b) otherwise
	 * don't remap 2. if the file does not exit -> remap
	 * 
	 * @param decodedFile
	 * @return absolute path to a workspace registered file.
	 */
	public String mapToWorkspaceFileIfRequired(String decodedFile) {
		String mappedFile = null;
		PathEntry mappedPathEntry = null;
		// check to see if the file exists on the file system
		java.io.File fileSystemFile = new java.io.File(decodedFile);
		if (fileSystemFile.exists() && pathMapper != null) {
			mappedPathEntry = pathMapper.getLocalFile(decodedFile);
		} else {

			// file doesn't exist so we must remap it, using the PDT path mapper
			// which could end up prompting the user to create a mapping
			try {
				if (projectScript != null) {
					mappedPathEntry = DebugSearchEngine.find(decodedFile, this);
				} else {
					mappedPathEntry = DebugSearchEngine.find(pathMapper,
							decodedFile, null, this);
				}
			} catch (Exception e1) {
			}
		}

		// do we now have a remapped file ?
		if (mappedPathEntry == null) {
			final PharPath pharPath = PharPath.getPharPath(new Path(
					"phar:" + decodedFile)); //$NON-NLS-1$
			if (pharPath != null) {
				DBGpLogger
						.debug("inbound File '" + decodedFile + "' remapped to phar file"); //$NON-NLS-1$ //$NON-NLS-2$
				mappedFile = "phar:" + decodedFile; //$NON-NLS-1$
			} else {
				DBGpLogger
						.debug("inbound File '" + decodedFile + "' Not remapped"); //$NON-NLS-1$ //$NON-NLS-2$
				mappedFile = decodedFile;
			}
		} else {
			mappedFile = mappedPathEntry.getResolvedPath();
			IResource file = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(new Path(mappedFile));
			if (file != null) {
				// changed as RSE resources return null for RawLocation.
				IPath t = file.getRawLocation();
				if (t != null) {
					mappedFile = t.toString();
				} else {
					mappedFile = file.getFullPath().toOSString();
				}
			}
			if (DBGpLogger.debugResp()) {
				String mapMsg = "mapped inbound file '" + decodedFile + "' to '" + mappedFile + "'"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				DBGpLogger.debug(mapMsg);
			}
		}
		return mappedFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse
	 * .debug.core.model.IBreakpoint)
	 */
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		// cannot use this method to reject a breakpoint appearing
		// in the editor.
		return bpFacade.supportsBreakpoint(breakpoint);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse
	 * .debug.core.model.IBreakpoint)
	 */
	public void breakpointAdded(IBreakpoint breakpoint) {
		// attempt to add a breakpoint under the following conditions
		// 1. breakpoint manager enabled
		// 2. the breakpoint is valid for the environment
		// 3. the breakpoint is enabled
		// 4. the debugee is suspended or running and async is supported (send
		// immediately)
		// 5. the debugee is running and async not supported (defer and send
		// later)
		// otherwise do not send or defer the breakpoint

		if (!DebugPlugin.getDefault().getBreakpointManager().isEnabled()) {
			return;
		}

		if (supportsBreakpoint(breakpoint)) {
			try {
				if (breakpoint.isEnabled()) {
					DBGpBreakpoint bp = bpFacade
							.createDBGpBreakpoint(breakpoint);
					if (isSuspended() || (asyncSupported && isRunning())) {
						// we are suspended or async mode is supported and we
						// are running, so send the breakpoint.
						if (DBGpLogger.debugBP()) {
							DBGpLogger
									.debug("Breakpoint Add requested immediately"); //$NON-NLS-1$
						}
						sendBreakpointAddCmd(bp, false);
					} else if (isRunning()) {

						// we are running and async mode is not supported
						// If we send a breakpoint command we may not get a
						// response at all or may get a response when the script
						// suspends on another breakpoint which will hang the
						// gui until then if we send it synchronously.
						// Async would require the read thread to handle
						// the response, locate the breakpoint from the txn_id
						// and add the id to the runtimeBreakpoint.
						// We cannot guarantee that the debug server will hold
						// the request until the script suspends and even queue
						// multiple requests, so the best bet is to
						// queue the requests until we suspend.
						if (DBGpLogger.debugBP()) {
							DBGpLogger
									.debug("Breakpoint Add deferred until suspended"); //$NON-NLS-1$
						}
						DBGpBreakpointCmd bpSet = new DBGpBreakpointCmd(
								DBGpCommand.breakPointSet, bp);
						queueBpCmd(bpSet);
					}
				}
			} catch (CoreException e) {
				DBGpLogger.logException("Exception adding breakpoint", this, e); //$NON-NLS-1$
			}
		}
	}

	/**
	 * create and send the breakpoint add command
	 * 
	 * @param bp
	 * @param onResponseThread
	 */
	private void sendBreakpointAddCmd(DBGpBreakpoint bp,
			boolean onResponseThread) {
		bp.resetConditionChanged();
		String fileName = bp.getFileName();
		int lineNumber = bp.getLineNumber();

		// create the add breakpoint command
		String debugMsg = null;
		if (DBGpLogger.debugBP()) {
			debugMsg = "adding breakpoint to file:" + fileName + ", at Line Number: " + lineNumber; //$NON-NLS-1$ //$NON-NLS-2$
		}
		fileName = mapToExternalFileIfRequired(bp);

		String args = "-t line -f " + DBGpUtils.getFileURIString(fileName) + " -n " + lineNumber; //$NON-NLS-1$ //$NON-NLS-2$

		DBGpBreakpointCondition condition = new DBGpBreakpointCondition(bp);
		if (condition.getType() == DBGpBreakpointCondition.EXPR) {
			if (debugMsg != null) {
				debugMsg += " with expression:" + condition.getExpression(); //$NON-NLS-1$
			}

			// we use session encoding before converting to Base64.
			args += " -- " + Base64.encode(getSessionEncodingBytes(condition.getExpression())); //$NON-NLS-1$
		} else if (condition.getType() == DBGpBreakpointCondition.HIT) {
			if (debugMsg != null) {
				debugMsg += " with hit :" + condition.getHitCondition() + condition.getHitValue(); //$NON-NLS-1$
			}
			args += " -h " + condition.getHitValue() + " -o " + condition.hitCondition; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (debugMsg != null) {
			DBGpLogger.debug(debugMsg);
		}

		DBGpResponse resp;
		if (onResponseThread) {
			resp = session.sendSyncCmdOnResponseThread(
					DBGpCommand.breakPointSet, args);
		} else {
			resp = session.sendSyncCmd(DBGpCommand.breakPointSet, args);
		}
		if (DBGpUtils.isGoodDBGpResponse(this, resp)) {
			/*
			 * <response command="breakpoint_set"
			 * transaction_id="TRANSACTION_ID" state="STATE"
			 * id="BREAKPOINT_ID"/>
			 */
			// TODO: note that you don't get state from XDebug even though the
			// document says so, assume optional and if not provided, assume bp
			// is
			// enabled.
			String bpId = resp.getTopAttribute("id"); //$NON-NLS-1$
			// luckily even though it is a string, the XDebug implementation
			// defines the id as being a c int.
			bp.setID(Integer.parseInt(bpId));
			if (DBGpLogger.debugBP()) {
				DBGpLogger.debug("Breakpoint installed with id: " + bpId); //$NON-NLS-1$
			}
		} else {
			// we have already logged the issue as an error
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse
	 * .debug.core.model.IBreakpoint, org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {
			DBGpBreakpoint bp = bpFacade.createDBGpBreakpoint(breakpoint);
			if (isSuspended() || (asyncSupported && isRunning())) {

				// aysnc mode and running or we are suspended so send the remove
				// request
				if (DBGpLogger.debugBP()) {
					DBGpLogger
							.debug("Immediately removing of breakpoint with ID: " + bp.getID()); //$NON-NLS-1$
				}
				sendBreakpointRemoveCmd(bp, false);
			} else if (isRunning()) {

				// running and not suspended and no async support, so we must
				// defer the removal.
				if (DBGpLogger.debugBP()) {
					DBGpLogger
							.debug("Deferring Removing of breakpoint with ID: " + bp.getID()); //$NON-NLS-1$
				}
				DBGpBreakpointCmd bpRemove = new DBGpBreakpointCmd(
						DBGpCommand.breakPointRemove, bp);
				queueBpCmd(bpRemove);
			}

		}
	}

	/**
	 * create and send the breakpoint remove command
	 * 
	 * @param bp
	 * @param onResponseThread
	 */
	private void sendBreakpointRemoveCmd(DBGpBreakpoint bp,
			boolean onResponseThread) {
		// we are suspended
		String args = "-d " + bp.getID(); //$NON-NLS-1$
		if (DBGpLogger.debugBP()) {
			DBGpLogger.debug("Removing breakpoint with ID: " + bp.getID()); //$NON-NLS-1$
		}
		DBGpResponse resp;
		if (onResponseThread) {
			resp = session.sendSyncCmdOnResponseThread(
					DBGpCommand.breakPointRemove, args);
		} else {
			resp = session.sendSyncCmd(DBGpCommand.breakPointRemove, args);
		}
		DBGpUtils.isGoodDBGpResponse(this, resp); // used to log the
		// result
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse
	 * .debug.core.model.IBreakpoint, org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		IBreakpointManager bmgr = DebugPlugin.getDefault()
				.getBreakpointManager();
		if (!bmgr.isEnabled()) {
			return;
		}
		int deltaLNumber = delta.getAttribute(IMarker.LINE_NUMBER, 0);
		IMarker marker = breakpoint.getMarker();
		int lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, 0);
		if (supportsBreakpoint(breakpoint)) {
			try {

				// did the condition change ?
				DBGpBreakpoint bp = bpFacade.createDBGpBreakpoint(breakpoint);
				if (bp.hasConditionChanged()) {
					if (DBGpLogger.debugBP()) {
						DBGpLogger
								.debug("condition changed for breakpoint with ID: " + bp.getID()); //$NON-NLS-1$
					}
					bp.resetConditionChanged();
					if (breakpoint.isEnabled()) {
						breakpointRemoved(breakpoint, null);
					} else {
						return;
					}
				}

				// did the line number change ?
				if (lineNumber != deltaLNumber) {
					if (DBGpLogger.debugBP()) {
						DBGpLogger
								.debug("line number changed for breakpoint with ID: " + bp.getID()); //$NON-NLS-1$
					}

					if (breakpoint.isEnabled()) {
						breakpointRemoved(breakpoint, null);
					} else {
						return;
					}
				}

				// add or remove the break point depending on whether it was
				// enabled or not
				if (breakpoint.isEnabled()) {
					breakpointAdded(breakpoint);
				} else {
					breakpointRemoved(breakpoint, null);
				}
			} catch (CoreException e) {
				DBGpLogger.logException(
						"Exception Changing Breakpoint", this, e); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Notification a breakpoint was encountered. Determine which breakpoint was
	 * hit and fire a suspend event.
	 * 
	 * @param event
	 *            debug event
	 */
	public void breakpointHit(String filename, int lineno) {
		// useful method to be called by the response listener when a
		// break point has occurred
		IBreakpoint breakpoint = findBreakpointHit(filename, lineno);
		if (breakpoint != null) {
			langThread.setBreakpoints(new IBreakpoint[] { breakpoint });
		} else {
			// set it to an empty set as specified by the API.
			langThread.setBreakpoints(new IBreakpoint[0]);
		}

		// fire event once everything has been established
		suspended(DebugEvent.BREAKPOINT);
	}

	/**
	 * find which breakpoint we have suspended at
	 * 
	 * @param filename
	 * @param lineno
	 * @return
	 */
	private IBreakpoint findBreakpointHit(String filename, int lineno) {
		return bpFacade.findBreakpointHit(filename, lineno);
	}

	/**
	 * setup the currently defined breakpoints before the execution of the
	 * script.
	 */
	private void loadPredefinedBreakpoints() {
		IBreakpointManager bmgr = DebugPlugin.getDefault()
				.getBreakpointManager();
		if (!bmgr.isEnabled()) {
			return;
		}
		IBreakpoint[] breakpoints = bmgr.getBreakpoints(bpFacade
				.getBreakpointModelID());
		for (int i = 0; i < breakpoints.length; i++) {
			breakpointAdded(breakpoints[i]);
		}
	}

	/**
	 * request a run to line
	 * 
	 * @param fileName
	 * @param lineNumber
	 */
	public void runToLine(IFile fileName, int lineNumber) {
		if (DBGpLogger.debugBP()) {
			DBGpLogger.debug("runtoline: " + fileName + " " + lineNumber); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (isSuspended()) {
			try {
				IBreakpoint breakpoint = bpFacade.createRunToLineBreakpoint(
						fileName, lineNumber);
				IBreakpointManager bmgr = DebugPlugin.getDefault()
						.getBreakpointManager();
				bmgr.addBreakpoint(breakpoint);
				resume();
			} catch (DebugException e) {
				DBGpLogger.logException("Unexpected DebugException", this, e); //$NON-NLS-1$
			} catch (CoreException e) {
				DBGpLogger.logException("Unexpected CoreException", this, e); //$NON-NLS-1$
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.debug.core.IBreakpointManagerListener#
	 * breakpointManagerEnablementChanged(boolean)
	 */
	public void breakpointManagerEnablementChanged(boolean enabled) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault()
				.getBreakpointManager()
				.getBreakpoints(bpFacade.getBreakpointModelID());
		for (int i = 0; i < breakpoints.length; i++) {
			if (supportsBreakpoint(breakpoints[i])) {
				if (enabled) {
					// ((PHPLineBreakpoint)breakpoints[i]).setConditionChanged(false);
					breakpointAdded(breakpoints[i]);
				} else {
					breakpointRemoved(breakpoints[i], null);
				}
			}
		}
	}

	/**
	 * queue a breakpoint command to be actioned once the script suspends
	 * 
	 * @param bpCmd
	 */
	private void queueBpCmd(DBGpBreakpointCmd bpCmd) {
		// Rules are
		// 1. a remove can delete a previous add and not be queued
		// 2. all other removes must be honoured
		// 3. all adds must be honoured (they cannot delete a remove)
		// 4. problem with the sequence of add/remove is that the remove
		// cannot work because the add has not been done yet so a
		// remove must always rebuild the the argument set.
		// I hope that if we have removed a breakpoint so long as we
		// hold the reference we can still use the information such as
		// marker information ?
		// the queue must only ever have a single remove, add or remove/add (not
		// add/remove)
		// for a specific file and line
		if (bpCmd.getCmd().equals(DBGpCommand.breakPointRemove)) {
			// search vector in reverse to see if there is an add that matches
			// and remove it
			boolean foundAdd = false;
			if (DBGpCmdQueue.size() > 0) {

				for (int i = DBGpCmdQueue.size() - 1; i >= 0 && !foundAdd; i--) {
					DBGpBreakpointCmd entry = (DBGpBreakpointCmd) DBGpCmdQueue
							.get(i);
					if (entry.getCmd().equals(DBGpCommand.breakPointSet)) {
						if (bpCmd.getBp().getFileName()
								.equals(entry.getBp().getFileName())
								&& bpCmd.getBp().getLineNumber() == entry
										.getBp().getLineNumber()) {

							// ok we have an entry that is an Add, the filename
							// and lineNumber are
							// the same so we found the entry so let's remove
							// it.
							foundAdd = true;
							DBGpCmdQueue.remove(i);
							if (DBGpLogger.debugBP()) {
								DBGpLogger
										.debug("removed a breakpoint command: " + entry); //$NON-NLS-1$
							}
						}
					}
				}
			}
			if (!foundAdd) {
				// add the remove as no add found
				DBGpCmdQueue.add(bpCmd);
			}
		} else {
			// always add an add
			DBGpCmdQueue.add(bpCmd);
		}
	}

	/**
	 * process any queued breakpoint commands
	 * 
	 */
	private void processQueuedBpCmds() {
		// we must assume we are running on the Session listener thread so
		// cannot
		// use sync commands.....
		if (DBGpLogger.debugBP()) {
			DBGpLogger.debug("processing deferred BP cmds"); //$NON-NLS-1$
		}

		for (int i = 0; i < DBGpCmdQueue.size(); i++) {
			DBGpBreakpointCmd bpCmd = (DBGpBreakpointCmd) DBGpCmdQueue.get(i);
			if (bpCmd.getCmd().equals(DBGpCommand.breakPointSet)) {
				sendBreakpointAddCmd(bpCmd.getBp(), true);
			} else if (bpCmd.getCmd().equals(DBGpCommand.breakPointRemove)) {
				sendBreakpointRemoveCmd(bpCmd.getBp(), true);
			}
		}
		DBGpCmdQueue.clear();
	}

	private static class DBGpBreakpointCmd {
		private String cmd;
		private DBGpBreakpoint bp;

		public DBGpBreakpointCmd(String cmd, DBGpBreakpoint bp) {
			this.cmd = cmd;
			this.bp = bp;
		}

		public String getCmd() {
			return cmd;
		}

		public DBGpBreakpoint getBp() {
			return bp;
		}

	}

	/**
	 * class to manage breakpoint conditions
	 * 
	 */
	private static class DBGpBreakpointCondition {

		private String hitCondition;
		private String hitValue;
		private String expression;

		private int type;
		public static final int NONE = 0;
		public static final int HIT = 1;
		public static final int EXPR = 2;
		public static final int INVALID = 3;

		public DBGpBreakpointCondition(DBGpBreakpoint bp) {
			type = NONE;
			if (bp.isConditional() && bp.isConditionEnabled()) {

				// supported
				// - expression
				// - hit(condition value)
				String bpExpression = bp.getExpression().trim();
				if (bpExpression.endsWith(")") && (bpExpression.startsWith("hit(") || bpExpression.startsWith("HIT("))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					if (bpExpression.length() > 5) {
						// support the following formats
						// - >= x, >=x
						// - ==x == x
						// - %x % x
						type = HIT;
						String internal = bpExpression.substring(4,
								bpExpression.length() - 1).trim();
						if (internal.startsWith("%")) { //$NON-NLS-1$
							hitCondition = "%"; //$NON-NLS-1$
							hitValue = internal.substring(1).trim();
						} else {
							hitCondition = internal.substring(0, 2);
							if (hitCondition.equals("==") || hitCondition.equals(">=")) { //$NON-NLS-1$ //$NON-NLS-2$
								hitValue = internal.substring(2).trim();
							} else {
								type = INVALID;
							}
						}

						if (type != INVALID) {
							try {
								Integer.parseInt(hitValue);
							} catch (NumberFormatException nfe) {
								type = INVALID;
							}
						}
					} else {
						type = INVALID;
					}
				} else if (bpExpression.length() == 0) {
					type = NONE;
					expression = bpExpression;
				} else {
					type = EXPR;
					expression = bpExpression;
				}
			}
		}

		public String getExpression() {
			return expression;
		}

		public String getHitCondition() {
			return hitCondition;
		}

		public String getHitValue() {
			return hitValue;
		}

		public int getType() {
			return type;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.xdebug.core.dbgp.session.DBGpSessionListener#SessionCreated
	 * (org.eclipse.php.xdebug.core.session.DBGpSession)
	 */
	public boolean SessionCreated(DBGpSession session) {
		// need to determine if the session is one we want, but only if we
		// are looking for a session, this session may be for us but if
		// we already have a session, the debugtarget is would have to be
		// reset to handle this new session, so safer to ignore it.
		boolean isMine = false;
		isMine = DBGpSessionHandler.getInstance().isCorrectSession(session,
				this);
		if (isMine) {
			if (this.session == null && !isTerminating()) {
				session.setDebugTarget(this);
				this.session = session;

				if (STATE_INIT_SESSION_WAIT == targetState
						|| STATE_CREATE == targetState) {

					// if we are in initial session wait, fire the event to
					// unblock if we haven't even got that far, fire the event
					// so that when we do enter initial session wait, we
					// just go straight through.
					te.signalEvent();
				} else {
					initiateSession();
				}
			} else {
				// well it is mine, but I am already handling a session so so it
				// isn't mine and it will be terminated.
				isMine = false;
			}
		}
		return isMine;
	}

	/**
	 * return the IDEKey
	 * 
	 * @return
	 */
	public String getIdeKey() {
		return ideKey;
	}

	/**
	 * return the session Id
	 * 
	 * @return
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * return if this is a web launch
	 * 
	 * @return
	 */
	public boolean isWebLaunch() {
		return webLaunch;
	}

	public String getSessionEncoding() {
		if (session != null) {
			return session.getSessionEncoding();
		} else {
			return DBGpSession.DEFAULT_SESSION_ENCODING;
		}
	}

	public String getBinaryEncoding() {
		if (session != null) {
			return session.getBinaryEncoding();
		} else {
			return DBGpSession.DEFAULT_BINARY_ENCODING;
		}

	}

	private int getMaxDepth() {
		if (sessionPreferences != null) {
			return sessionPreferences.getInt(
					DBGpPreferences.DBGP_MAX_DEPTH_PROPERTY,
					DBGpPreferences.DBGP_MAX_DEPTH_DEFAULT);
		}
		return DBGpPreferences.DBGP_MAX_DEPTH_DEFAULT;
	}

	public int getMaxChildren() {
		if (sessionPreferences != null) {
			return sessionPreferences.getInt(
					DBGpPreferences.DBGP_MAX_CHILDREN_PROPERTY,
					DBGpPreferences.DBGP_MAX_CHILDREN_DEFAULT);
		}
		return DBGpPreferences.DBGP_MAX_CHILDREN_DEFAULT;
	}

	private int getCaptureStdout() {
		if (sessionPreferences != null) {
			return sessionPreferences.getInt(
					DBGpPreferences.DBGP_CAPTURE_STDOUT_PROPERTY,
					DBGpPreferences.DBGP_CAPTURE_DEFAULT);
		}
		return DBGpPreferences.DBGP_CAPTURE_DEFAULT;
	}

	private int getCaptureStderr() {
		if (sessionPreferences != null) {
			return sessionPreferences.getInt(
					DBGpPreferences.DBGP_CAPTURE_STDERR_PROPERTY,
					DBGpPreferences.DBGP_CAPTURE_DEFAULT);
		}
		return DBGpPreferences.DBGP_CAPTURE_DEFAULT;
	}

	private boolean showGLobals() {
		if (sessionPreferences != null) {
			return sessionPreferences.getBoolean(
					DBGpPreferences.DBGP_SHOW_GLOBALS_PROPERTY,
					DBGpPreferences.DBGP_SHOW_GLOBALS_DEFAULT);
		}
		return DBGpPreferences.DBGP_SHOW_GLOBALS_DEFAULT;
	}

	public void setPathMapper(PathMapper pathMapper) {
		this.pathMapper = pathMapper;
	}

	/**
	 * return true if a script is executed, ie in running state.
	 * 
	 * @return true if running.
	 */
	public boolean isRunning() {
		boolean isRunning = (STATE_STARTED_RUNNING == targetState);
		return isRunning;
	}

	public boolean isMultiSessionManaged() {
		return multiSessionManaged;
	}

	public void setMultiSessionManaged(boolean multiSessionManaged) {
		this.multiSessionManaged = multiSessionManaged;
	}

	public DBGpSession getSession() {
		return session;
	}

	public void setSession(DBGpSession session) {
		this.session = session;
	}

	public DebugOutput getOutputBuffer() {
		return this.debugOutput;
	}

	private byte[] getSessionEncodingBytes(String toConvert) {
		byte[] result = null;
		try {
			result = toConvert.getBytes(getSessionEncoding());
		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("unexpected encoding problem", this, e); //$NON-NLS-1$
		}
		return result;
	}

	public boolean isWaiting() {
		// cannot say isWaiting for init_session_wait because that means the
		// DebugOutput is null and that causes a null pointer exception.
		boolean isWaiting = (STATE_STARTED_SESSION_WAIT == targetState);
		return isWaiting;
	}
}
