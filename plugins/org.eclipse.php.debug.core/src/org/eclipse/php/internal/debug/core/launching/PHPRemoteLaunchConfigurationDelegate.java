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
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.server.core.Server;

/**
 * A launch configuration delegate that is responsible for handling debug
 * session that were remotely initialized.
 * 
 * @author Shalom Gibly
 */
@SuppressWarnings("restriction")
public class PHPRemoteLaunchConfigurationDelegate implements ILaunchConfigurationDelegate2 {

	private ILaunch launch;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		boolean runWithDebug = configuration.getAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
		this.launch = launch;
		if (mode.equals(ILaunchManager.RUN_MODE) && !runWithDebug) {
			return;
		}
		IProject proj = null;
		String fileName = configuration.getAttribute(Server.FILE_NAME, (String) null);
		if (fileName != null) {
			// Get the project from the file name
			IPath filePath = new Path(fileName);
			try {
				proj = ResourcesPlugin.getWorkspace().getRoot().getProject(filePath.segment(0));
			} catch (Throwable t) {
			}
		}
		ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		if (proj != null) {
			String project = proj.getFullPath().toString();
			wc.setAttribute(IPHPDebugConstants.PHP_Project, project);
		}
		wc.doSave();
		String URL = configuration.getAttribute(Server.BASE_URL, ""); //$NON-NLS-1$
		if (mode.equals(ILaunchManager.DEBUG_MODE) || runWithDebug == true) {
			boolean isStopAtFirstLine = wc.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
					PHPProjectPreferences.getStopAtFirstLine(proj));
			int requestPort = PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
			// Fill all debug attributes:
			launch.setAttribute(IDebugParametersKeys.PORT, Integer.toString(requestPort));
			launch.setAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER, Boolean.toString(true));
			launch.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, Boolean.toString(isStopAtFirstLine));
			launch.setAttribute(IDebugParametersKeys.ORIGINAL_URL, URL);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#buildForLaunch
	 * (org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean buildForLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#
	 * finalLaunchCheck (org.eclipse.debug.core.ILaunchConfiguration,
	 * java.lang.String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean finalLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#getLaunch(
	 * org.eclipse.debug.core.ILaunchConfiguration, java.lang.String)
	 */
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
		return new PHPLaunch(configuration, mode, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate2#preLaunchCheck
	 * (org.eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return true;
	}

	/**
	 * Called when the debug session was terminated.
	 */
	public void terminated() {
		DebugEvent event = null;
		if (launch.getDebugTarget() == null) {
			/*
			 * We have to force the termination of the ILaunch because at this
			 * stage there is no PHPDebugTarget, thus we create a dummy debug
			 * target to overcome this issue and terminate the launch.
			 */
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
	 * @param event
	 *            The event to be fired
	 */
	public void fireEvent(DebugEvent event) {
		DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { event });
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
			return "Session Terminated"; //$NON-NLS-1$
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
			return ""; //$NON-NLS-1$
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
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
