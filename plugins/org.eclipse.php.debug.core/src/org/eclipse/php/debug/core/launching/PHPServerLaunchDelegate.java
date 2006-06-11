package org.eclipse.php.debug.core.launching;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.PHPSessionLaunchMapper;
import org.eclipse.php.debug.core.debugger.PHPWebServerDebuggerInitializer;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.core.model.DebugSessionIdGenerator;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.server.apache.core.ApacheLaunchConfigurationDelegate;
import org.eclipse.php.server.apache.core.ApachePlugin;
import org.eclipse.php.server.apache.core.ApacheServerBehaviour;
import org.eclipse.php.server.apache.core.IHTTPServerLaunch;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.ServerUtil;

public class PHPServerLaunchDelegate implements IHTTPServerLaunch {

	private ApacheLaunchConfigurationDelegate httpServerDelegate = null;
	private ILaunch launch;
	private Job runDispatch;

	public PHPServerLaunchDelegate() {
	}

	public void setHTTPServerDelegate(ApacheLaunchConfigurationDelegate delegate) {
		this.httpServerDelegate = delegate;
	}

	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		boolean runWithDebug = configuration.getAttribute("run_with_debug", true);
		this.launch = launch;
		if (mode.equals(ILaunchManager.RUN_MODE) && !runWithDebug) {
			httpServerDelegate.doLaunch(configuration, mode, launch, monitor);
			return;
		}

		IServer server = ServerUtil.getServer(configuration);
		if (server == null) {
			Logger.log(Logger.ERROR, "Luanch configuration could not find server");
			// throw CoreException();
			return;
		}

		ApacheServerBehaviour apacheServerBehaviour = (ApacheServerBehaviour) server.loadAdapter(ApacheServerBehaviour.class, null);
		apacheServerBehaviour.setupLaunch(launch, mode, monitor);

		IModuleArtifact moduleArtifact = httpServerDelegate.getModuleArtifact(configuration);
		if (moduleArtifact == null)
			throw new CoreException(new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, PHPDebugCoreMessages.configurationError, null));

		IModule module = moduleArtifact.getModule();

		boolean publish = configuration.getAttribute(ApachePlugin.DEPLOYABLE, false);
		if (publish) {
			if (!apacheServerBehaviour.publish(module, monitor)) {
				// Return if the publish failed.
				terminated();
				return;
			}
		}
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		IProject proj = module.getProject();
		String project = proj.getFullPath().toString();

		wc.setAttribute(IPHPConstants.PHP_Project, project);
		wc.doSave();

		String URL = configuration.getAttribute(ApachePlugin.URL, "");
		if (mode.equals(ILaunchManager.DEBUG_MODE) || runWithDebug == true) {
			boolean isStopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(proj);
			int requestPort = PHPProjectPreferences.getDebugPort(proj);

			// Generate a session id for this launch and put it in the map
			int sessionID = DebugSessionIdGenerator.generateSessionID();
			PHPSessionLaunchMapper.put(sessionID, new PHPServerLaunchDecorator(launch, apacheServerBehaviour, proj));

			// Fill all debug attributes:
			launch.setAttribute(IDebugParametersKeys.PORT, Integer.toString(requestPort));
			launch.setAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER, Boolean.toString(true));
			launch.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, Boolean.toString(isStopAtFirstLine));
			launch.setAttribute(IDebugParametersKeys.ORIGINAL_URL, URL);
			launch.setAttribute(IDebugParametersKeys.SESSION_ID, Integer.toString(sessionID));

			// Trigger the debug session by initiating a debug requset to the debug server
			runDispatch = new RunDispatchJobWebServer(launch);
			runDispatch.schedule();
		}
	}

	public void runPHPWebServer(ILaunch launch) {
		PHPWebServerDebuggerInitializer debuggerInitializer = new PHPWebServerDebuggerInitializer(launch);
		try {
			debuggerInitializer.debug();
		} catch (DebugException e) {
			IStatus status = e.getStatus();
			String errorMessage = null;
			if (status == null) {
				Logger.traceException("Unexpected Error return from debuggerInitializer ", e);
				fireError(PHPDebugCoreMessages.Debugger_Unexpected_Error_1, e);
				errorMessage = PHPDebugCoreMessages.Debugger_Unexpected_Error_1;
			} else {
				fireError(status);
				errorMessage = status.getMessage();
			}
			displayErrorMessage(errorMessage);
		}
	}

	private void displayErrorMessage(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Debug Error", message);
			}
		});
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

	/**
	 * Throws a IStatus in a Debug Event
	 * 
	 */
	public void fireError(String errorMessage, Exception e) {
		Status status = new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, e);
		DebugEvent event = new DebugEvent(this, DebugEvent.MODEL_SPECIFIC);
		event.setData(status);
		fireEvent(event);
	}

	/**
	 * Called when the debug session was terminated. 
	 */
	public void terminated() {
		DebugEvent event = null;
		if (launch.getDebugTarget() == null) {
			// We have to force the termination of the ILaunch because at this stage there is no
			// PHPDebugTarget, thus we create a dummy debug target to overcome this issue and terminate the launch.
			IDebugTarget dummyDebugTarget = new DummyDebugTarget(launch);
			event = new DebugEvent(dummyDebugTarget, DebugEvent.TERMINATE);
			if (launch != null) {
				launch.addDebugTarget(dummyDebugTarget);
				IDebugEventSetListener launchListener = (IDebugEventSetListener) launch;
				launchListener.handleDebugEvents(new DebugEvent[] { event });
			}
		}
		event = new DebugEvent(this, DebugEvent.TERMINATE);
		fireEvent(event);
	}

	/**
	 * Fires a debug event
	 * 
	 * @param event 	The event to be fired
	 */
	public void fireEvent(DebugEvent event) {
		DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { event });
	}

	/*
	 * Run is seperate thread so launch doesn't hang.
	 */
	class RunDispatchJobWebServer extends Job {
		private ILaunch launch;

		public RunDispatchJobWebServer(ILaunch launch) {
			super("runPHPWebServer");
			this.launch = launch;
			setSystem(true);
		}

		protected IStatus run(IProgressMonitor monitor) {
			runPHPWebServer(launch);
			Logger.debugMSG("[" + this + "] PHPDebugTarget: Calling Terminated()");
			terminated();
			return Status.OK_STATUS;
		}

		public String toString() {
			String className = getClass().getName();
			className = className.substring(className.lastIndexOf('.') + 1);
			return className + "@" + Integer.toHexString(hashCode());
		}
	}

	/*
	 * A dummy debug target for the termination of the ILaunch.
	 */
	private class DummyDebugTarget implements IDebugTarget {

		private ILaunch launch;

		public DummyDebugTarget(ILaunch launch) {
			this.launch = launch;
		}

		public String getName() throws DebugException {
			return "Session Terminated";
		}

		public IProcess getProcess() {
			return null;
		}

		public IThread[] getThreads() throws DebugException {
			return null;
		}

		public boolean hasThreads() throws DebugException {
			return false;
		}

		public boolean supportsBreakpoint(IBreakpoint breakpoint) {
			return false;
		}

		public IDebugTarget getDebugTarget() {
			return this;
		}

		public ILaunch getLaunch() {
			return launch;
		}

		public String getModelIdentifier() {
			return "";
		}

		public Object getAdapter(Class adapter) {
			return null;
		}

		public boolean canTerminate() {
			return true;
		}

		public boolean isTerminated() {
			return true;
		}

		public void terminate() throws DebugException {
		}

		public boolean canResume() {
			return false;
		}

		public boolean canSuspend() {
			return false;
		}

		public boolean isSuspended() {
			return false;
		}

		public void resume() throws DebugException {
		}

		public void suspend() throws DebugException {
		}

		public void breakpointAdded(IBreakpoint breakpoint) {
		}

		public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		}

		public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		}

		public boolean canDisconnect() {
			return false;
		}

		public void disconnect() throws DebugException {
		}

		public boolean isDisconnected() {
			return false;
		}

		public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
			return null;
		}

		public boolean supportsStorageRetrieval() {
			return false;
		}
	}
}
