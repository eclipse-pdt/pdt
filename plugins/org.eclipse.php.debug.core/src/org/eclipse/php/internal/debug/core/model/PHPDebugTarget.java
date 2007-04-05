/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.communication.DebugConnectionThread;
import org.eclipse.php.internal.debug.core.debugger.*;
import org.eclipse.php.internal.debug.core.debugger.Breakpoint;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchProxy;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * PHP Debug Target
 */
public class PHPDebugTarget extends PHPDebugElement implements IDebugTarget, IBreakpointManagerListener {

	// containing launch object
	private ILaunch fLaunch;
	private IProcess fProcess;

	// program name
	private String fName;
	private String fHTDocs;
	private String fURL;
	private int fRequestPort;
	private DebugOutput fOutput = new DebugOutput();

	// suspend state
	private boolean fSuspended = false;

	// terminated state
	private boolean fTerminated = false;
	private boolean fTermainateCalled = false;

	// threads
	private PHPThread fThread;
	private IThread[] fThreads;
	private IRemoteDebugger debugger;
	private String fLastcmd;
	private boolean fStatus;
	private int fLastStop;
	private String fLastFileName;
	private boolean fIsPHPCGI;
	private boolean fIsRunAsDebug;

	private PHPResponseHandler fPHPResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.StartResponseHandler fStartResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.BreakpointAddedResponseHandler fBreakpointAddedResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.BreakpointRemovedResponseHandler fBreakpointRemovedResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.StepIntoResponseHandler fStepIntoResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.StepOverResponseHandler fStepOverResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.StepOutResponseHandler fStepOutResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.GoResponseHandler fGoResponseHandler;
	private org.eclipse.php.internal.debug.core.debugger.Debugger.PauseResponseHandler fPauseResponseHandler;
	private DefaultExpressionsManager expressionsManager;

	//	private IVariable[] fVariables;
	private String fContextRoot = "";
	private String fWorkspacePath = "";
	private String fProjectName = "";
	private IProject fProject;
	private int fSuspendCount;
	private ContextManager fContextManager;
	private Vector fConsoleEventListeners = new Vector();
	private Vector fDebugError = new Vector();
	private StartLock fStartLock = new StartLock();
	private BreakpointSet fBreakpointSet;
	private IBreakpointManager fBreakpointManager;
	private boolean fIsServerWindows = false;
	private DebugConnectionThread fConnectionThread;

	/**
	 * Constructs a new debug target in the given launch for the associated PHP
	 * Debugger on a apache Server.
	 * 
	 * @param connectionThread 	The debug connection thread for the communication read and write processes.
	 * @param launch 			containing launch
	 * @param URL   			URL of the debugger
	 * @param requestPort		port to send requests to the bebugger *
	 * @exception CoreException if unable to connect to host
	 */
	public PHPDebugTarget(DebugConnectionThread connectionThread, ILaunch launch, String URL, int requestPort, IProcess process, String contextRoot, boolean runAsDebug, boolean stopAtFirstLine, IProject project) throws CoreException {
		super(null);
		fConnectionThread = connectionThread;
		fURL = URL;

		if (!(contextRoot.startsWith("/")))
			fContextRoot = "/";
		fContextRoot = fContextRoot + contextRoot;
		//       fContextRoot = contextRoot;
		if (!(contextRoot.equals("")))
			fContextRoot = fContextRoot + "/";

		fIsPHPCGI = false;

		initDebugTarget(launch, requestPort, process, runAsDebug, stopAtFirstLine, project);
	}

	/**
	 * Constructs a new debug target in the given launch for the associated PHP
	 * Debugger using PHP exe.
	 * 
	 * @param connectionThread 	The debug connection thread for the communication read and write processes.
	 * @param launch 			containing launch
	 * @param String 			full path to the PHP executable
	 * @param requestPort 		port to send requests to the bebugger *
	 * @exception CoreException	if unable to connect to host
	 */
	public PHPDebugTarget(DebugConnectionThread connectionThread, ILaunch launch, String phpExe, IFile fileToDebug, int requestPort, IProcess process, boolean runAsDebug, boolean stopAtFirstLine, IProject project) throws CoreException {
		this(connectionThread, launch, phpExe, fileToDebug.getName(), PHPDebugTarget.getWorkspaceRootPath(fileToDebug.getWorkspace()), requestPort, process, runAsDebug, stopAtFirstLine, project);
	}

	public static String getWorkspaceRootPath(IWorkspace ws) {
		return ws.getRoot().getRawLocation().toString() + "/";
	}

	public PHPDebugTarget(DebugConnectionThread connectionThread, ILaunch launch, String phpExe, String fileToDebug, String workspacePath, int requestPort, IProcess process, boolean runAsDebug, boolean stopAtFirstLine, IProject project) throws CoreException {
		super(null);
		fConnectionThread = connectionThread;
		fName = fileToDebug;
		fWorkspacePath = workspacePath;
		fIsPHPCGI = true;
		initDebugTarget(launch, requestPort, process, runAsDebug, stopAtFirstLine, project);
	}

	/**
	 * Returns the DebugConnectionThread for this PHPDebugTarget.
	 * @return The DebugConnectionThread.
	 */
	public DebugConnectionThread getConnectionThread() {
		return fConnectionThread;
	}

	/*
	 * Initialize the debug target.
	 * @param launch
	 * @param requestPort
	 * @param process
	 * @param runAsDebug
	 * @param stopAtFirstLine
	 * @param project
	 * @throws CoreException
	 */
	private void initDebugTarget(ILaunch launch, int requestPort, IProcess process, boolean runAsDebug, boolean stopAtFirstLine, IProject project) throws CoreException {
		fLaunch = launch;
		fProcess = process;
		fIsRunAsDebug = runAsDebug;
		fProject = project;
		if (project != null)
			fProjectName = project.getName();
		fProcess.setAttribute(IProcess.ATTR_PROCESS_TYPE, IPHPConstants.PHPProcessType);
		((PHPProcess) fProcess).setDebugTarget(this);
		fRequestPort = requestPort;

		//		synchronized (fUsedPorts) {
		//			if (fUsedPorts.containsKey(String.valueOf(requestPort))) {
		//				Logger.debugMSG("PHPDebugTarget: Debug Port already in use");
		//				String errorMessage = PHPDebugCoreMessages.DebuggerDebugPortInUse_1;
		//				completeTerminated();
		//				throw new DebugException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null));
		//			} else {
		//				fUsedPorts.put(String.valueOf(requestPort), new Integer(requestPort));
		//				fRequestPort = requestPort;
		//			}
		//		}

		fBreakpointSet = new BreakpointSet(project, fIsPHPCGI);

		IDebugHandler debugHandler = null;
		IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry.getBestMatchDebugParametersInitializer(launch);
		if (parametersInitializer != null) {
			String debugHandlerID = parametersInitializer.getDebugHandler();
			if (debugHandlerID != null) {
				try {
					debugHandler = DebugHandlersRegistry.getHandler(debugHandlerID);
				} catch (Exception e) {
					PHPDebugPlugin.log(e);
				}
			}
		}
		// If couldn't find contributed IDebugHandler - use default
		if (debugHandler == null) {
			debugHandler = new ServerDebugHandler();
		}
		debugHandler.setDebugTarget(this);
		debugger = debugHandler.getRemoteDebugger();

		fThread = new PHPThread(this);
		fThreads = new IThread[] { fThread };
		fTerminated = false;

		// create response handlers
		fPHPResponseHandler = new PHPResponseHandler(this);
		fStartResponseHandler = fPHPResponseHandler.new StartResponseHandler();
		fBreakpointAddedResponseHandler = fPHPResponseHandler.new BreakpointAddedResponseHandler();
		fBreakpointRemovedResponseHandler = fPHPResponseHandler.new BreakpointRemovedResponseHandler();
		fStepIntoResponseHandler = fPHPResponseHandler.new StepIntoResponseHandler();
		fStepOverResponseHandler = fPHPResponseHandler.new StepOverResponseHandler();
		fStepOutResponseHandler = fPHPResponseHandler.new StepOutResponseHandler();
		fGoResponseHandler = fPHPResponseHandler.new GoResponseHandler();
		fPauseResponseHandler = fPHPResponseHandler.new PauseResponseHandler();

		fSuspendCount = 0;
		fContextManager = new ContextManager(this, debugger);

		fBreakpointManager = DebugPlugin.getDefault().getBreakpointManager();
		fBreakpointManager.addBreakpointListener(this);
		fBreakpointManager.addBreakpointManagerListener(this);
	}

	public IProcess getProcess() {
		return fProcess;
	}

	public IRemoteDebugger getRemoteDebugger() {
		return debugger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
	 */
	public IThread[] getThreads() throws DebugException {
		return fThreads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	public boolean hasThreads() throws DebugException {
		return !fTerminated && fThreads.length > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getName()
	 */
	public String getName() throws DebugException {
		if (fName == null) {
			fName = fURL;
		}
		return fName;
	}

	public String getURL() {
		return fURL;
	}

	int getLastStop() {
		return fLastStop;
	}

	String getLastFileName() {
		return fLastFileName;
	}

	int getSuspendCount() {
		return fSuspendCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse.debug.core.model.IBreakpoint)
	 */
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		if (breakpoint.getModelIdentifier().equals(IPHPConstants.ID_PHP_DEBUG_CORE)) {
			boolean support = fBreakpointSet.supportsBreakpoint(breakpoint);
			return support;
		}
		return false;
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
		if (fLaunch instanceof PHPLaunchProxy) {
			return ((PHPLaunchProxy) fLaunch).getLaunch();
		}
		return fLaunch;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		if (fTerminated)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return fTerminated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		StartLock startLock = getStartLock();
		// Don't synchronize on lock, debugger may be hung. Just terminate. 
		if (!startLock.isStarted()) {
			terminated();
			return;
		}
		fTermainateCalled = true;
		((PHPThread) getThreads()[0]).setStepping(false);
		fTerminated = true;
		fSuspended = false;
		fLastcmd = "terminate";
		Logger.debugMSG("[" + this + "] PHPDebugTarget: Calling closeDebugSession()");
		debugger.closeDebugSession();
		//		terminated(); // TODO - Might be needed...
		//		fTermainateCalled = true;
	}

	/**
	 * Called when this debug target terminates.
	 */
	public void terminated() {
		fTerminated = true;
		fSuspended = false;
		Logger.debugMSG("[" + this + "] PHPDebugTarget: Calling debugger.closeConnection();");
		if (!fTermainateCalled) {
			debugger.closeConnection();
		}
		completeTerminated();
		PHPSessionLaunchMapper.updateSystemProperty(DebugPlugin.getDefault().getLaunchManager().getLaunches());
	}

	private void completeTerminated() {
		fTerminated = true;
		fSuspended = false;
		fThreads = new IThread[0];
		try {
			fProcess.terminate();
		} catch (DebugException e) {
			// PHPprocess doesn't throw this exception          
		}
		Logger.debugMSG("[" + this + "] PHPDebugTarget: Calling removeBreakpointListener(this);");
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointManagerListener(this);
		Logger.debugMSG("[" + this + "] PHPDebugTarget: Firing terminate");
		fireTerminateEvent();
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
		return !isTerminated() && !isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return fSuspended;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		fLastcmd = "resume";
		((PHPThread) getThreads()[0]).setStepping(false);
		fStatus = debugger.go(fGoResponseHandler);
		if (!fStatus) {
			Logger.log(Logger.ERROR, "PHPDebugTarget: debugger.go return false");
		}
		int detail = DebugEvent.CLIENT_REQUEST;
		resumed(detail);

	}

	/**
	 * Notification the target has resumed for the given reason
	 * 
	 * @param detail
	 *            reason for the resume
	 */
	private void resumed(int detail) {
		fSuspended = false;
		fThread.fireResumeEvent(detail);
	}

	/**
	 * Notification the target has suspended for the given reason
	 * 
	 * @param detail
	 *            reason for the suspend
	 */
	public void suspended(int detail) {
		fSuspended = true;
		fSuspendCount++;
		System.setProperty("org.eclipse.debugger.variables", "true");
		try {
			((PHPThread) getThreads()[0]).setStepping(false);
		} catch (DebugException e) {
			// PHPThread doesn't throw exception
		}
		fThread.fireSuspendEvent(detail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		fLastcmd = "suspend";
		((PHPThread) getThreads()[0]).setStepping(false);
		fStatus = debugger.pause(fPauseResponseHandler);
		if (!fStatus) {
			Logger.log(Logger.ERROR, "PHPDebugTarget: debugger.pause return false");
		}
		int detail = DebugEvent.CLIENT_REQUEST;
		suspended(detail);
	}

	/**
	 * Creates a breakpoint
	 * 
	 * @param resource
	 *            File resource to add breakpoint
	 * @param lineNumber
	 *            Line Number to add breakpoint
	 *            
	 */
	public static IBreakpoint createBreakpoint(IResource resource, int lineNumber) throws CoreException {

		return createBreakpoint(resource, lineNumber, new java.util.HashMap(10));
	}

	/**
	 * Creates a breakpoint
	 * 
	 * @param resource
	 *            File resource to add breakpoint
	 * @param lineNumber
	 *            Line Number to add breakpoint
	 * @param attributes
	 *            java.util.Map of attributes to add to breakpoint
	 *            
	 */
	public static IBreakpoint createBreakpoint(IResource resource, int lineNumber, Map attributes) throws CoreException {
		IBreakpoint point = null;
		try {
			point = new PHPConditionalBreakpoint(resource, lineNumber, attributes);
		} catch (CoreException e1) {
			Logger.logException("PHPDebugTarget: error creating breakpoint", e1);
			throw e1;
		}
		return point;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse.debug.core.model.IBreakpoint)
	 */
	public void breakpointAdded(IBreakpoint breakpoint) {
		if (supportsBreakpoint(breakpoint)) {
			try {
				if (breakpoint.isEnabled()) {
					fLastcmd = "breakpointAdded";
					PHPLineBreakpoint bp = (PHPLineBreakpoint) breakpoint;
					IMarker marker = bp.getMarker();
					IResource resource = null;
					Breakpoint runtimeBreakpoint = bp.getRuntimeBreakpoint();
					int lineNumber = runtimeBreakpoint.getLineNumber();
					if (breakpoint instanceof PHPRunToLineBreakpoint) {
						PHPRunToLineBreakpoint rtl = (PHPRunToLineBreakpoint) breakpoint;
						resource = rtl.getSourceFile();
					} else {
						resource = marker.getResource();
						lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, 0);
						runtimeBreakpoint.setLineNumber(lineNumber);
					}
					String fileName;
					if (!fIsPHPCGI) {
						if (resource instanceof IWorkspaceRoot) {
							if (IPHPConstants.Include_Storage_RFile.equals(marker.getAttribute(IPHPConstants.Include_Storage_type))) {
								fileName = (String) marker.getAttribute(IPHPConstants.Include_Storage);
								fileName = marker.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY, fileName);
							} else {
								fileName = fHTDocs + '/' + (String) marker.getAttribute(IPHPConstants.Include_Storage);
							}
						} else {
							if (fHTDocs == null || fHTDocs.length() == 0) {
								if (fContextRoot == null || fContextRoot.length() <= 1) {
									fileName = resource.getProjectRelativePath().toString();
								} else {
									fileName = fContextRoot + resource.getProjectRelativePath();
								}
							} else {
								fileName = fHTDocs + fContextRoot + resource.getProjectRelativePath();
							}
//							fileName = resource.getProjectRelativePath().toString();
						}
					} else {
						fileName = (resource.getRawLocation()).toString();
					}

					runtimeBreakpoint.setFileName(fileName);
					Logger.debugMSG("[" + this + "] PHPDebugTarget: Setting Breakpoint - File " + fileName + " Line Number " + lineNumber);
					debugger.addBreakpoint(bp.getRuntimeBreakpoint(), fBreakpointAddedResponseHandler);
				}
			} catch (CoreException e1) {
				Logger.logException("PHPDebugTarget: Exception Adding Breakpoint", e1);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse.debug.core.model.IBreakpoint,
	 *      org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (supportsBreakpoint(breakpoint)) {
			if (breakpoint instanceof PHPRunToLineBreakpoint)
				return;
			fLastcmd = "breakpointRemoved";
			PHPLineBreakpoint bp = (PHPLineBreakpoint) breakpoint;
			Breakpoint runtimeBreakpoint = bp.getRuntimeBreakpoint();
			Logger.debugMSG("[" + this + "] PHPDebugTarget: Removing Breakpoint - File " + runtimeBreakpoint.getFileName() + " Line Number " + runtimeBreakpoint.getLineNumber());
			fStatus = debugger.removeBreakpoint(runtimeBreakpoint, fBreakpointRemovedResponseHandler);
			if (!fStatus && debugger.isActive()) {
				Logger.log(Logger.ERROR, "PHPDebugTarget: debugger.removeBreakpoint return false");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse.debug.core.model.IBreakpoint,
	 *      org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (!fBreakpointManager.isEnabled())
			return;
		int deltaLNumber = delta.getAttribute(IMarker.LINE_NUMBER, 0);
		IMarker marker = breakpoint.getMarker();
		int lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, 0);
		if (supportsBreakpoint(breakpoint)) {
			try {
				if (((PHPLineBreakpoint) breakpoint).isConditionChanged()) {
					((PHPLineBreakpoint) breakpoint).setConditionChanged(false);
					if (breakpoint.isEnabled()) {
						breakpointRemoved(breakpoint, null);
					} else {
						return;
					}
				}
				if (lineNumber != deltaLNumber) {
					if (breakpoint.isEnabled()) {
						breakpointRemoved(breakpoint, null);
					} else {
						return;
					}
				}
				if (breakpoint.isEnabled()) {
					breakpointAdded(breakpoint);
				} else {
					breakpointRemoved(breakpoint, null);
				}
			} catch (CoreException e) {
				Logger.logException("PHPDebugTarget: Exception Changing Breakpoint", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	public boolean canDisconnect() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	public void disconnect() throws DebugException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
	 */
	public boolean isDisconnected() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval()
	 */
	public boolean supportsStorageRetrieval() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long,
	 *      long)
	 */
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	/**
	 * Notification we have connected to the debugger and it has started. .
	 */
	public void started() {
		fSuspended = false;
		fireCreationEvent();
	}

	/**
	 * Install breakpoints that are already registered with the breakpoint
	 * manager.
	 * In case {@link #isRunWithDebug()} returns true, nothing will happen.
	 */
	public void installDeferredBreakpoints() {
		if (fIsRunAsDebug) {
			return;
		}
		if (!fBreakpointManager.isEnabled())
			return;
		IBreakpoint[] breakpoints = fBreakpointManager.getBreakpoints(IPHPConstants.ID_PHP_DEBUG_CORE);
		for (int i = 0; i < breakpoints.length; i++) {
			((PHPLineBreakpoint) breakpoints[i]).setConditionChanged(false);
			breakpointAdded(breakpoints[i]);
			/*            try {
			 breakpoints[i].delete();
			 } catch (CoreException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 } */
		}
	}

	/**
	 * Returns the current stack frames in the target.
	 * 
	 * @return the current stack frames in the target
	 * @throws DebugException
	 *             if unable to perform the request
	 */
	protected IStackFrame[] getStackFrames() throws DebugException {
		int length = 0;
		String context;
		if (!fIsPHPCGI) {
			length = fHTDocs.length();
			context = fContextRoot;
		} else {
			IPath raw = fProject.getLocation();
			String location = raw.toPortableString();
			length = location.length() + 1;
			context = null;
		}
		return fContextManager.getStackFrames(length, context, fIsServerWindows);
	}

	/**
	 * Returns the Expression Manager for the Debug Target.
	 * 
	 * @return the current Expression Manager target
	 */
	public DefaultExpressionsManager getExpressionManager() {
		return expressionsManager;
	}

	public void setExpressionManager(DefaultExpressionsManager expressionManager) {
		this.expressionsManager = expressionManager;
	}

	/**
	 * Returns the Parameter Stack for the Debug Target.
	 * 
	 * @return the Parameter Stack for the target
	 */
	public Expression[] getStackVariables(PHPStackFrame stack) {
		return fContextManager.getStackVariables(stack);
	}

	/**
	 * Step Return the debugger.
	 * 
	 * @throws DebugException
	 *             if the request fails
	 */
	protected void stepReturn() throws DebugException {
		fLastcmd = "stepReturn";
		Logger.debugMSG("[" + this + "] PHPDebugTarget: stepReturn ");
		// Fix for bug #163780 - Debugger irregular state control
		// Call for the resumed before the debugger.stepOut
		int detail = DebugEvent.STEP_RETURN;
		resumed(detail);
		fStatus = debugger.stepOut(fStepOutResponseHandler);
		if (!fStatus) {
			Logger.log(Logger.ERROR_DEBUG, "PHPDebugTarget: debugger.stepOut return false");
		}
	}

	/**
	 * Step Over the debugger.
	 * 
	 * @throws DebugException
	 *             if the request fails
	 */
	protected void stepOver() throws DebugException {
		fLastcmd = "stepOver";
		Logger.debugMSG("[" + this + "] PHPDebugTarget: stepOver");
		// Fix for bug #163780 - Debugger irregular state control
		// Call for the resumed before the debugger.stepOver
		int detail = DebugEvent.STEP_OVER;
		resumed(detail);
		fStatus = debugger.stepOver(fStepOverResponseHandler);
		if (!fStatus) {
			Logger.log(Logger.ERROR_DEBUG, "PHPDebugTarget: debugger.stepOver return false");
		}
	}

	/**
	 * Step Into the debugger.
	 * 
	 * @throws DebugException
	 *             if the request fails
	 */
	protected void stepInto() throws DebugException {
		Logger.debugMSG("[" + this + "] PHPDebugTarget: stepInto ");
		fLastcmd = "stepInto";
		// Fix for bug #163780 - Debugger irregular state control
		// Call for the resumed before the debugger.stepInto
		int detail = DebugEvent.STEP_INTO;
		resumed(detail);
		fStatus = debugger.stepInto(fStepIntoResponseHandler);
		if (!fStatus) {
			Logger.log(Logger.ERROR_DEBUG, "PHPDebugTarget: debugger.stepInto return false");
		}
	}

	/**
	 * Returns the Local Variabales for the Debug Target.
	 * 
	 * @return the Local Variabales for the target
	 */
	public IVariable[] getVariables() {
		return fContextManager.getVariables();
	}

	/**
	 * Notification a breakpoint was encountered. Determine which breakpoint was
	 * hit and fire a suspend event.
	 * 
	 * @param event
	 *            debug event
	 */
	public void breakpointHit(String fileName, int lineNumber) {
		// determine which breakpoint was hit, and set the thread's breakpoint
		IBreakpoint breakpoint = findBreakpoint(fileName, lineNumber);
		if (breakpoint != null) {
			fThread.setBreakpoints(new IBreakpoint[] { breakpoint });
		} else {
			fThread.setBreakpoints(new IBreakpoint[] {});
		}

		suspended(DebugEvent.BREAKPOINT);
	}

	/**
	 * Finds the breakpoint hit
	 * 
	 * @param fileName
	 *            Filename containing the breakpoint
	 * @param lineNumber
	 *            Linenumber of breakpoint
	 * @return the Local Variabales for the target
	 *                       
	 */
	protected IBreakpoint findBreakpoint(String fileName, int lineNumber) {
		// determine which breakpoint was hit, and set the thread's breakpoint
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(IPHPConstants.ID_PHP_DEBUG_CORE);
		for (int i = 0; i < breakpoints.length; i++) {
			IBreakpoint breakpoint = breakpoints[i];
			if (supportsBreakpoint(breakpoint)) {
				if (breakpoint instanceof PHPLineBreakpoint) {
					PHPLineBreakpoint lineBreakpoint = (PHPLineBreakpoint) breakpoint;
					Breakpoint zBP = lineBreakpoint.getRuntimeBreakpoint();
					String bFileName = zBP.getFileName();
					int bLineNumber = zBP.getLineNumber();
					if (bLineNumber == lineNumber && bFileName.equals(fileName)) {
						return breakpoint;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Finds the breakpoint hit
	 * 
	 * @param enabled
	 *            Enabled or Disable breakpoints. 
	 *            
	 */
	public void breakpointManagerEnablementChanged(boolean enabled) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(getModelIdentifier());
		for (int i = 0; i < breakpoints.length; i++) {
			if (supportsBreakpoint(breakpoints[i])) {
				if (enabled) {
					((PHPLineBreakpoint) breakpoints[i]).setConditionChanged(false);
					breakpointAdded(breakpoints[i]);
				} else {
					breakpointRemoved(breakpoints[i], null);
				}
			}
		}
	}

	/**
	 * Registers the given event listener. The listener will be notified of
	 * events in the program being interpretted. Has no effect if the listener
	 * is already registered.
	 *  
	 * @param listener event listener
	 */
	public void addConsoleEventListener(IPHPConsoleEventListener listener) {
		if (!fConsoleEventListeners.contains(listener)) {
			fConsoleEventListeners.add(listener);
		}
		if (fDebugError != null) {
			Enumeration enumObject = fDebugError.elements();
			boolean empty = fDebugError.isEmpty();
			if (!empty) {
				while (enumObject.hasMoreElements()) {
					DebugError debugError = (DebugError) enumObject.nextElement();
					listener.handleEvent(debugError);
				}
			}
		}
	}

	/**
	 * Deregisters the given event listener. Has no effect if the listener is
	 * not currently registered.
	 *  
	 * @param listener event listener
	 */
	public void removeConsoleEventListener(IPHPConsoleEventListener listener) {
		fConsoleEventListeners.remove(listener);
	}

	public List getConsoleEventListeners() {
		return fConsoleEventListeners;
	}

	/**
	 * Returns the Output buffer for the Debug Target.
	 * 
	 * @return the Output buffer for the target
	 */
	public DebugOutput getOutputBufffer() {
		return fOutput;
	}

	/**
	 * Returns whether running with debug info.
	 * 
	 * @return boolean - whether running with debug info
	 */
	public boolean isRunWithDebug() {
		return fIsRunAsDebug;
	}

	/**
	 * Throws a IStatus in a Debug Event
	 * 
	 */
	public void fireError(String errorMessage, Exception e1) {
		Status status = new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, e1);
		DebugEvent event = new DebugEvent(this, DebugEvent.MODEL_SPECIFIC);
		event.setData(status);
		fireEvent(event);
	}

	/**
	 * Throws a IStatus in a Debug Event
	 * 
	 */
	public void fireError(IStatus status) {
		DebugEvent event = new DebugEvent(this, DebugEvent.MODEL_SPECIFIC);
		event.setData(status);
		fireEvent(event);
	}

	public void setLastStop(int lineNumber) {
		fLastStop = lineNumber;
	}

	public void setLastFileName(String string) {
		fLastFileName = string;
	}

	public void setBreakpoints(IBreakpoint[] breakpoints) {
		fThread.setBreakpoints(new IBreakpoint[] {});
	}

	public boolean isPHPCGI() {
		return fIsPHPCGI;
	}

	public void setHTDocs(String fHTDocs) {
		this.fHTDocs = fHTDocs;
	}

	public StartLock getStartLock() {
		return fStartLock;
	}

	public void setLastCommand(String command) {
		fLastcmd = command;
	}

	public String getLastCommand() {
		return fLastcmd;
	}

	public int getRequestPort() {
		return fRequestPort;
	}

	public String getHTDocs() {
		return fHTDocs;
	}

	public String getContextRoot() {
		return fContextRoot;
	}

	public String getWorkspacePath() {
		return fWorkspacePath;
	}

	public String getProjectName() {
		return fProjectName + "/";
	}

	public List getDebugErrors() {
		return fDebugError;
	}

	public boolean isServerWindows() {
		return fIsServerWindows;
	}

	public void setServerWindows(boolean isServerWindows) {
		fIsServerWindows = isServerWindows;
	}

	public org.eclipse.php.internal.debug.core.debugger.Debugger.StartResponseHandler getStartResponseHandler() {
		return fStartResponseHandler;
	}

	public String toString() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className + "@" + Integer.toHexString(hashCode());
	}
}
