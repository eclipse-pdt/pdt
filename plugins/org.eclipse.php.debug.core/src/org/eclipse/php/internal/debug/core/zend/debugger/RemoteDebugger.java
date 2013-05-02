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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathContainer;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.util.*;
import org.eclipse.php.internal.core.util.PHPSearchEngine.ExternalFileResult;
import org.eclipse.php.internal.core.util.PHPSearchEngine.IncludedFileResult;
import org.eclipse.php.internal.core.util.PHPSearchEngine.ResourceResult;
import org.eclipse.php.internal.core.util.PHPSearchEngine.Result;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.pathmapper.*;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnectionThread;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.communication.ResponseHandler;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.*;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.swt.widgets.Display;

/**
 * An IRemoteDebugger implementation.
 */
public class RemoteDebugger implements IRemoteDebugger {

	/**
	 * Original PDT protocol ID from 04/2006
	 */
	public static final int PROTOCOL_ID_2006040701 = 2006040701;

	/**
	 * Improved protocol ID from 06/2007 which provides new message type (
	 * {@link StartProcessFileNotification}) that allows to control debug state
	 * when debugger is preparing processing new file. We use this state for
	 * doing on-demand path mapping, and for sending breakpoints for the next
	 * file.
	 */
	public static final int PROTOCOL_ID_2006040703 = 2006040703;

	/**
	 * New protocol ID from 04/2008 which provides two new message types:
	 * {@link GetCWDRequest} allows to ask Debugger to return current working
	 * directory,
	 */
	public static final int PROTOCOL_ID_2006040705 = 2006040705;

	/**
	 * New protocol ID from 12/2012 which provides new message type:
	 * {@link AddFilesRequest} allows send initial list of files which contain
	 * at least one breakpoint.
	 */
	public static final int PROTOCOL_ID_2012121702 = 2012121702;

	/**
	 * Latest protocol ID
	 */
	public static final int PROTOCOL_ID_LATEST = PROTOCOL_ID_2012121702;

	private static final String EVAL_ERROR = "[Error]"; //$NON-NLS-1$

	protected boolean isDebugMode = System.getProperty("loggingDebug") != null; //$NON-NLS-1$
	private DebugConnectionThread connection;
	private IDebugHandler debugHandler;
	private Map<String, String> resolvedFiles;
	private Map<String, List<IPath>> resolvedIncludePaths;
	private int currentProtocolId = 0;
	private int previousSuspendCount;
	private String cachedCWD;
	private PHPstack cachedStack;

	/**
	 * Creates new RemoteDebugSession
	 */
	public RemoteDebugger(IDebugHandler debugHandler,
			DebugConnectionThread connectionThread) {
		// this.kit = createCommunicationKit();
		connection = connectionThread;
		this.debugHandler = debugHandler;
		connection.setCommunicationAdministrator(this);
		connection.setCommunicationClient(this);
		resolvedFiles = new HashMap<String, String>();
		resolvedIncludePaths = new HashMap<String, List<IPath>>();
	}

	public IDebugHandler getDebugHandler() {
		return debugHandler;
	}

	public DebugConnectionThread getConnectionThread() {
		return connection;
	}

	public void closeConnection() {
		connection.closeConnection();
	}

	public void connectionEstablished() {
		debugHandler.connectionEstablished();
	}

	public void connectionClosed() {
		debugHandler.connectionClosed();
	}

	public void closeDebugSession() {
		if (connection.isConnected()) {
			connection.sendNotification(new DebugSessionClosedNotification());
		}
	}

	public void handleMultipleBindings() {
		debugHandler.multipleBindOccured();
	}

	public void handlePeerResponseTimeout() {
		debugHandler.connectionTimedout();
	}

	public boolean canDo(int feature) {
		switch (feature) {
		case START_PROCESS_FILE_NOTIFICATION:
			return getCurrentProtocolID() >= PROTOCOL_ID_2006040703;
		case GET_CWD:
		case GET_CALL_STACK_LITE:
			return getCurrentProtocolID() >= PROTOCOL_ID_2006040705;
		}
		return false;
	}

	/**
	 * Asks Debug server for a current working directory (old way)
	 * 
	 * @return current working directory, or <code>null</code> in case of error
	 */
	public String getCWDOld() {
		EvalRequest request = new EvalRequest();
		request.setCommand("getcwd()"); //$NON-NLS-1$
		IDebugResponseMessage response = sendCustomRequest(request);
		if (response != null && response instanceof EvalResponse) {
			String result = ((EvalResponse) response).getResult();
			if (!EVAL_ERROR.equals(result)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Asks Debug server for a current working directory (new way)
	 * 
	 * @return current working directory, or <code>null</code> in case of error
	 */
	public String getCWDNew() {
		GetCWDRequest request = new GetCWDRequest();
		IDebugResponseMessage response = sendCustomRequest(request);
		if (response != null && response.getStatus() == 0) {
			return ((GetCWDResponse) response).getCWD();
		}
		return null;
	}

	public String getCWD() {
		if (!this.isActive()) {
			return null;
		}

		PHPDebugTarget debugTarget = getDebugHandler().getDebugTarget();
		int suspendCount = debugTarget.getSuspendCount();
		if (suspendCount == previousSuspendCount && cachedCWD != null) {
			return cachedCWD;
		}

		if (canDo(GET_CWD)) {
			cachedCWD = getCWDNew();
		} else {
			cachedCWD = getCWDOld();
		}
		return cachedCWD;
	}

	/**
	 * Returns local path corresponding to the current working directory of the
	 * PHP script, which is currently running.
	 * 
	 * @return current working directory
	 */
	public String getCurrentWorkingDirectory() {
		PHPDebugTarget debugTarget = debugHandler.getDebugTarget();

		String cwd = getCWD();
		if (cwd != null) {
			PathMapper pathMapper = PathMapperRegistry
					.getByLaunchConfiguration(debugTarget.getLaunch()
							.getLaunchConfiguration());
			if (pathMapper != null) {
				PathEntry cwdEntry = pathMapper.getLocalFile(cwd);
				if (cwdEntry != null) {
					cwd = cwdEntry.getResolvedPath();
				}
			}
		}
		return cwd;
	}

	/**
	 * Sets current working directory on the debugger side
	 * 
	 * @param cwd
	 *            Current working directory to set
	 * @return <code>true</code> if success, <code>false</code> - otherwise
	 */
	public boolean setCurrentWorkingDirectory(String cwd) {
		try {
			EvalRequest request = new EvalRequest();
			request.setCommand(String.format("chdir('%1$s')", cwd)); //$NON-NLS-1$
			IDebugResponseMessage response = sendCustomRequest(request);
			if (response != null && response instanceof EvalResponse) {
				String result = ((EvalResponse) response).getResult();
				if (!EVAL_ERROR.equals(result)) {
					return true;
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return false;
	}

	/**
	 * Returns local file name corresponding to the given remote path. This
	 * method asks debugger for the current working directory before resolving.
	 * 
	 * @param remoteFile
	 *            File to resolve
	 * @return local file, or <code>null</code> in case of resolving failure
	 */
	public String convertToLocalFilename(String remoteFile) {
		String currentScript = null;
		PHPstack callStack = getCallStack();
		if (callStack.getSize() > 0) {
			currentScript = callStack.getLayer(callStack.getSize() - 1)
					.getResolvedCalledFileName();
		}
		return convertToLocalFilename(remoteFile, getCurrentWorkingDirectory(),
				currentScript);
	}

	/**
	 * Returns local file name corresponding to the given remote path
	 * 
	 * @param remoteFile
	 *            File to resolve
	 * @param cwd
	 *            Current working directory received from the debugger
	 * @param currentScript
	 *            Script that is on the top of the debug stack currently
	 * @return local file, or <code>null</code> in case of resolving failure
	 */
	public String convertToLocalFilename(String remoteFile, String cwd,
			String currentScript) {
		PHPDebugTarget debugTarget = debugHandler.getDebugTarget();
		if (debugTarget.getContextManager().isResolveBlacklisted(remoteFile)) {
			return remoteFile;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// check if this file is already local
		if (workspace.getRoot().findMember(remoteFile) != null) {
			return remoteFile;
		}

		// If we are running local debugger, check if "remote" file exists and
		// return it if it does
		if (debugTarget.isPHPCGI() && new File(remoteFile).exists()) {

			IFile wsFile = null;
			IPath location = new Path(remoteFile);
			IProject[] projects = workspace.getRoot().getProjects();
			IProject currentProject = debugTarget.getProject();
			// set current project to higher priority:
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				if (project.equals(currentProject)) {
					IProject tmp = projects[0];
					projects[0] = project;
					projects[i] = tmp;
					break;
				}
			}
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				if (!project.isOpen() || !project.isAccessible()) {
					continue;
				}
				IPath projectLocation = project.getLocation();
				if (projectLocation != null
						&& projectLocation.isPrefixOf(location)) {
					int segmentsToRemove = projectLocation.segmentCount();
					wsFile = workspace
							.getRoot()
							.getFile(
									project.getFullPath()
											.append(location
													.removeFirstSegments(segmentsToRemove)));
					break;
				}
			}

			if (wsFile != null) {
				return wsFile.getFullPath().toString();
			} else {
				return remoteFile;
			}
		}

		String resolvedFileKey = new StringBuilder(remoteFile).append(cwd)
				.append(currentScript).toString();
		if (!resolvedFiles.containsKey(resolvedFileKey)) {
			String currentScriptDir = null;
			if (currentScript != null) {
				currentScriptDir = new Path(currentScript)
						.removeLastSegments(1).toString();
			}

			String resolvedFile = null;
			PathEntry pathEntry = DebugSearchEngine.find(remoteFile,
					debugTarget, cwd, currentScriptDir);
			if (pathEntry != null) {
				resolvedFile = pathEntry.getResolvedPath();
			} else {
				try {
					resolvedFile = tryGuessMapping(remoteFile, debugTarget);
				} catch (ModelException e) {
					resolvedFile = remoteFile;
				}
			}
			resolvedFiles.put(resolvedFileKey, resolvedFile);
		}
		String localFile = resolvedFiles.get(resolvedFileKey);
		if (localFile == null) {
			return remoteFile;
		}
		return localFile;
	}

	/**
	 * Returns remote file name corresponding to the given local path
	 * 
	 * @param localFile
	 * @return remote file path, or localFile in case it couldn't be resolved
	 */
	public static String convertToRemoteFilename(String localFile,
			PHPDebugTarget debugTarget) {
		IPath path = Path.fromPortableString(localFile);

		// check if this is valid workspace path to avoid IAE
		// e.g. when using debug URL, localFile can be "c:\Program Files\..."
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=306834
		if (path.segmentCount() >= 2) {

			IFile wsFile = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(path);
			if (debugTarget.isPHPCGI() && wsFile.exists()
					&& wsFile.getLocation() != null) {
				File fsFile = wsFile.getLocation().toFile();
				if (fsFile.exists()) {
					return fsFile.getAbsolutePath();
				}
			}
		}
		if (VirtualPath.isAbsolute(localFile)) {
			PathMapper pathMapper = PathMapperRegistry
					.getByLaunchConfiguration(debugTarget.getLaunch()
							.getLaunchConfiguration());
			if (pathMapper != null) {
				String remoteFile = pathMapper.getRemoteFile(localFile);
				if (remoteFile != null) {
					return remoteFile;
				}
			}
		}
		return localFile;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Sends the request through the communication connection and returns
	 * response
	 * 
	 * @param message
	 *            request that will be sent to the debugger
	 * @return message response recieved from the debugger
	 */
	public IDebugResponseMessage sendCustomRequest(IDebugRequestMessage request) {
		IDebugResponseMessage response = null;
		if (this.isActive()) {
			try {
				Object obj = connection.sendRequest(request);
				if (obj instanceof IDebugResponseMessage) {
					response = (IDebugResponseMessage) obj;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * Sends custom notification through the communication connection
	 * 
	 * @param message
	 *            notification that will be delivered to the debugger
	 * @return <code>true</code> if succeeded sending the message,
	 *         <code>false</code> - otherwise
	 */
	public boolean sendCustomNotification(IDebugNotificationMessage notification) {
		if (this.isActive()) {
			try {
				connection.sendNotification(notification);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Asynchronic addBreakpoint Returns true if succeeded sending the request,
	 * false otherwise.
	 */
	public boolean addBreakpoint(Breakpoint bp,
			BreakpointAddedResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			AddBreakpointRequest request = new AddBreakpointRequest();
			Breakpoint tmpBreakpoint = (Breakpoint) bp.clone();
			String fileName = tmpBreakpoint.getFileName();
			tmpBreakpoint.setFileName(fileName);
			request.setBreakpoint(tmpBreakpoint);
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic addBreakpoint Returns true if succeeded adding the Breakpoint.
	 */
	public void addBreakpoint(Breakpoint breakpoint) {
		if (!this.isActive()) {
			return;
		}
		try {
			AddBreakpointRequest request = new AddBreakpointRequest();
			Breakpoint tmpBreakpoint = (Breakpoint) breakpoint.clone();
			String fileName = tmpBreakpoint.getFileName();
			tmpBreakpoint.setFileName(fileName);
			request.setBreakpoint(tmpBreakpoint);
			AddBreakpointResponse response = (AddBreakpointResponse) connection
					.sendRequest(request);
			if (response != null && response.getStatus() == 0) {
				// Log.writeLog("addBreakpoint");
				breakpoint.setID(response.getBreakpointID());
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Asynchronic removeBreakpoint Returns true if succeeded sending the
	 * request, false otherwise.
	 */
	public boolean removeBreakpoint(int id,
			BreakpointRemovedResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		CancelBreakpointRequest request = new CancelBreakpointRequest();
		request.setBreakpointID(id);
		connection
				.sendRequest(request, new ThisHandleResponse(responseHandler));
		return true;
	}

	/**
	 * Synchronic removeBreakpoint Returns true if succeeded removing the
	 * Breakpoint.
	 */
	public boolean removeBreakpoint(int id) {
		if (!this.isActive()) {
			return false;
		}
		try {
			CancelBreakpointRequest request = new CancelBreakpointRequest();
			request.setBreakpointID(id);
			CancelBreakpointResponse response = (CancelBreakpointResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic removeBreakpoint Returns true if succeeded sending the
	 * request, false otherwise.
	 */
	public boolean removeBreakpoint(Breakpoint breakpoint,
			BreakpointRemovedResponseHandler responseHandler) {
		return removeBreakpoint(breakpoint.getID(), responseHandler);
	}

	/**
	 * Synchronic removeBreakpoint Returns true if succeeded removing the
	 * Breakpoint.
	 */
	public boolean removeBreakpoint(Breakpoint breakpoint) {
		return removeBreakpoint(breakpoint.getID());
	}

	/**
	 * Asynchronic removeAllBreakpoints Returns true if succeeded sending the
	 * request, false otherwise.
	 */
	public boolean removeAllBreakpoints(
			AllBreakpointRemovedResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		CancelAllBreakpointsRequest request = new CancelAllBreakpointsRequest();
		connection
				.sendRequest(request, new ThisHandleResponse(responseHandler));
		return true;
	}

	/**
	 * Synchronic removeAllBreakpoints Returns true if succeeded removing all
	 * the Breakpoint.
	 */
	public boolean removeAllBreakpoints() {
		if (!this.isActive()) {
			return false;
		}
		try {
			CancelAllBreakpointsRequest request = new CancelAllBreakpointsRequest();
			CancelAllBreakpointsResponse response = (CancelAllBreakpointsResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic stepInto Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean stepInto(StepIntoResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		StepIntoRequest request = new StepIntoRequest();
		connection
				.sendRequest(request, new ThisHandleResponse(responseHandler));
		return true;
	}

	/**
	 * Synchronic stepInto Returns true if succeeded stepInto.
	 */
	public boolean stepInto() {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepIntoRequest request = new StepIntoRequest();
			StepIntoResponse response = (StepIntoResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic stepOver Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean stepOver(StepOverResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOverRequest request = new StepOverRequest();
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic stepOver Returns true if succeeded stepOver.
	 */
	public boolean stepOver() {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOverRequest request = new StepOverRequest();
			StepOverResponse response = (StepOverResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic stepOut Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean stepOut(StepOutResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOutRequest request = new StepOutRequest();
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic stepOut Returns true if succeeded stepOut.
	 */
	public boolean stepOut() {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOutRequest request = new StepOutRequest();
			StepOutResponse response = (StepOutResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic go Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean go(GoResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			GoRequest request = new GoRequest();
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic go Returns true if succeeded go.
	 */
	public boolean go() {
		if (!this.isActive()) {
			return false;
		}
		try {
			GoRequest request = new GoRequest();
			GoResponse response = (GoResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic start Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean start(StartResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}

		if (!detectProtocolID()) {
			return false;
		}

		try {
			debugHandler.getDebugTarget().installDeferredBreakpoints();
		} catch (CoreException ce) {
			return false;
		}

		StartRequest request = new StartRequest();
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic start Returns true if succeeded start.
	 */
	public boolean start() {
		if (!this.isActive()) {
			return false;
		}

		if (!detectProtocolID()) {
			return false;
		}

		StartRequest request = new StartRequest();
		try {
			StartResponse response = (StartResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronous addFiles Returns true if succeeded sending the request,
	 * false otherwise.
	 */
	public boolean addFiles(String[] paths,
			AddFilesResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			AddFilesRequest request = new AddFilesRequest();
			request.setPaths(paths);
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronous addFiles Returns true if succeeded adding the Breakpoint.
	 */
	public boolean addFiles(String[] paths) {
		if (!this.isActive()) {
			return false;
		}
		try {
			AddFilesRequest request = new AddFilesRequest();
			request.setPaths(paths);
			AddFilesResponse response = (AddFilesResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is used for detecting protocol version of Debugger
	 * 
	 * @return <code>true</code> if succeeded to detect, otherwise
	 *         <code>false</code>
	 */
	protected boolean detectProtocolID() {
		boolean isUseNewProtocol = false;
		try {
			ILaunchConfiguration config = getDebugHandler().getDebugTarget()
					.getLaunch().getLaunchConfiguration();
			String debuggerId = config.getAttribute(
					PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
					DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
			if (debuggerId == DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID) {
				ZendDebuggerConfiguration debuggerConfiguration = (ZendDebuggerConfiguration) PHPDebuggersRegistry
						.getDebuggerConfiguration(debuggerId);
				isUseNewProtocol = debuggerConfiguration.isUseNewProtocol();
			}
		} catch (CoreException e) {
			PHPDebugPlugin.log(e);
		}
		// check whether debugger is using the latest protocol ID:
		if (isUseNewProtocol && setProtocol(PROTOCOL_ID_LATEST)) {
			return true;
		}
		// check whether debugger is using one of older protocol ID:
		if (setProtocol(PROTOCOL_ID_2006040705)) {
			return true;
		}
		// check whether debugger is using one of older protocol ID:
		if (setProtocol(PROTOCOL_ID_2006040703)) {
			// warn user that he is using an old debugger
			warnOlderDebugVersion();
			return true;
		}
		// check whether debugger is using one of older protocol ID:
		if (setProtocol(PROTOCOL_ID_2006040701)) {
			// warn user that he is using an old debugger
			warnOlderDebugVersion();
			return true;
		}
		// user is using an incompatible version of debugger:
		getDebugHandler().wrongDebugServer();
		return false;
	}

	public static void warnOlderDebugVersion() {
		boolean dontShowWarning = PHPDebugPlugin.getDefault()
				.getPluginPreferences()
				.getBoolean("DontShowOlderDebuggerWarning"); //$NON-NLS-1$
		if (!dontShowWarning) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					OldDebuggerWarningDialog dialog = new OldDebuggerWarningDialog(
							Display.getDefault().getActiveShell());
					dialog.open();
				}
			});
		}
	}

	public boolean setProtocol(int protocolID) {
		SetProtocolRequest request = new SetProtocolRequest();
		request.setProtocolID(protocolID);
		IDebugResponseMessage response = sendCustomRequest(request);
		if (response != null && response instanceof SetProtocolResponse) {
			int responceProtocolID = ((SetProtocolResponse) response)
					.getProtocolID();
			if (responceProtocolID == protocolID) {
				currentProtocolId = protocolID;
				return true;
			}
		}
		return false;
	}

	public int getCurrentProtocolID() {
		return currentProtocolId;
	}

	/**
	 * Asynchronic pause Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean pause(PauseResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		PauseDebuggerRequest request = new PauseDebuggerRequest();
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic pause Returns true if succeeded pause.
	 */
	public boolean pause() {
		if (!this.isActive()) {
			return false;
		}
		PauseDebuggerRequest request = new PauseDebuggerRequest();
		try {
			PauseDebuggerResponse response = (PauseDebuggerResponse) connection
					.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic pause Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	public boolean eval(String commandString,
			EvalResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		EvalRequest request = new EvalRequest();
		request.setCommand(commandString);
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	public boolean assignValue(String var, String value, int depth,
			String[] path, AssignValueResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		AssignValueRequest request = new AssignValueRequest();
		request.setVar(var);
		request.setValue(value);
		request.setDepth(depth);
		request.setPath(path);
		request.setTransferEncoding(getTransferEncoding());
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/*
	 * Returns the transfer encoding for the current project.
	 */
	private String getTransferEncoding() {
		IProject project = debugHandler.getDebugTarget().getProject();
		return project == null ? null : PHPProjectPreferences
				.getTransferEncoding(project);
	}

	/**
	 * aSynchronic assigned value
	 */
	public boolean assignValue(String var, String value, int depth,
			String[] path) {
		if (!this.isActive()) {
			return false;
		}
		AssignValueRequest request = new AssignValueRequest();
		request.setVar(var);
		request.setValue(value);
		request.setDepth(depth);
		request.setPath(path);
		request.setTransferEncoding(getTransferEncoding());
		try {
			connection.sendRequest(request);
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic pause Returns true if succeeded pause.
	 */
	public String eval(String commandString) {
		if (!this.isActive()) {
			return null;
		}
		EvalRequest request = new EvalRequest();
		request.setCommand(commandString);
		try {
			EvalResponse response = (EvalResponse) connection
					.sendRequest(request);
			String result = null;
			if (response != null) {
				if (response.getStatus() == 0) {
					result = response.getResult();
				} else {
					result = "---ERROR---"; //$NON-NLS-1$
				}
			}
			return result;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}

	/**
	 * Finish the debugger running.
	 */
	public void finish() {
		connection.closeConnection();
	}

	/**
	 * Checks if there is a connection.
	 */
	public boolean isActive() {
		return connection != null && connection.isConnected();
	}

	public boolean getVariableValue(String var, int depth, String[] path,
			VariableValueResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		GetVariableValueRequest request = new GetVariableValueRequest();
		request.setVar(var);
		request.setDepth(depth);
		request.setPath(path);
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getVariableValue Returns the variable var.
	 */
	public byte[] getVariableValue(String var, int depth, String[] path)
			throws IllegalArgumentException {
		if (!this.isActive()) {
			return null;
		}
		GetVariableValueRequest request = new GetVariableValueRequest();
		request.setVar(var);
		request.setDepth(depth);
		request.setPath(path);
		GetVariableValueResponse response = null;
		try {
			response = (GetVariableValueResponse) connection
					.sendRequest(request);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		if (response == null || response.getStatus() != 0) {
			return null;
		}
		return response.getVarResult();
	}

	public boolean getCallStack(GetCallStackResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		GetCallStackRequest request = new GetCallStackRequest();
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getCallStack Returns the Stack layer with no variables
	 * information.
	 */
	public PHPstack getCallStack() {
		if (!this.isActive()) {
			return null;
		}

		PHPDebugTarget debugTarget = getDebugHandler().getDebugTarget();
		int suspendCount = debugTarget.getSuspendCount();
		if (suspendCount == previousSuspendCount && cachedStack != null) {
			return cachedStack;
		}

		GetCallStackRequest request = new GetCallStackRequest();
		PHPstack remoteStack = null;
		try {
			GetCallStackResponse response = (GetCallStackResponse) connection
					.sendRequest(request);
			if (response != null) {
				remoteStack = response.getPHPstack();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		convertToSystem(remoteStack);

		previousSuspendCount = suspendCount;
		cachedStack = remoteStack;

		return remoteStack;
	}

	private void convertToSystem(PHPstack remoteStack) {
		if (remoteStack != null) {
			String currentWorkingDir = getCurrentWorkingDirectory();

			for (int i = 0; i < remoteStack.getSize(); i++) {
				StackLayer layer = remoteStack.getLayer(i);
				layer.setCallerLineNumber(layer.getCallerLineNumber() - 1);
				layer.setCalledLineNumber(layer.getCalledLineNumber() - 1);

				layer.setResolvedCalledFileName(layer.getCalledFileName());
				if (i > 0) {
					String previousScript = remoteStack.getLayer(i - 1)
							.getResolvedCalledFileName();
					String previousScriptDir = "."; //$NON-NLS-1$
					int idx = Math.max(previousScript.lastIndexOf('/'),
							previousScript.lastIndexOf('\\'));
					if (idx != -1) {
						previousScriptDir = previousScript.substring(0, idx);
					}

					IProject project = null;
					IResource resource = ResourcesPlugin.getWorkspace()
							.getRoot().findMember(previousScript);
					if (resource != null) {
						project = resource.getProject();
					} else {
						project = debugHandler.getDebugTarget().getProject();
					}

					if (layer.getCalledFileName() != null
							&& currentWorkingDir != null && project != null) {
						Result<?, ?> result = PHPSearchEngine.find(
								layer.getCalledFileName(), currentWorkingDir,
								previousScriptDir, project);
						if (result instanceof ResourceResult) {
							layer.setResolvedCalledFileName(((ResourceResult) result)
									.getFile().getFullPath().toString());
						} else if (result instanceof IncludedFileResult) {
							layer.setResolvedCalledFileName(((IncludedFileResult) result)
									.getFile().getAbsolutePath());
						} else if (result instanceof ExternalFileResult) {
							layer.setResolvedCalledFileName(((ExternalFileResult) result)
									.getFile().getAbsolutePath());
						}
					}
				}
			}
		}
	}

	public boolean getStackVariableValue(int stackDepth, String value,
			int depth, String[] path,
			GetStackVariableValueResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		GetStackVariableValueRequest request = new GetStackVariableValueRequest();
		request.setVar(value);
		request.setDepth(depth);
		request.setLayerDepth(stackDepth);
		request.setPath(path);
		try {
			connection.sendRequest(request, new ThisHandleResponse(
					responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getStackVariableValue Returns the variable value.
	 */
	public byte[] getStackVariableValue(int stackDepth, String value,
			int depth, String[] path) {
		if (!this.isActive()) {
			return null;
		}
		GetStackVariableValueRequest request = new GetStackVariableValueRequest();
		request.setVar(value);
		request.setDepth(depth);
		request.setLayerDepth(stackDepth);
		request.setPath(path);
		GetStackVariableValueResponse response = null;
		try {
			response = (GetStackVariableValueResponse) connection
					.sendRequest(request);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		if (response == null || response.getStatus() != 0) {
			return null;
		}
		return response.getVarResult();
	}

	private String tryGuessMapping(String remoteFile, PHPDebugTarget debugTarget)
			throws ModelException {
		IProject project = debugTarget.getProject();
		if (project == null) {
			String orginalURL = debugTarget.getLaunch().getAttribute(
					IDebugParametersKeys.ORIGINAL_URL);
			String projectName = new Path(orginalURL).segment(0);
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			project = workspace.getRoot().getProject(projectName);
		}
		if (project != null) {
			String projectName = project.getName();
			IPath remotePath = new Path(remoteFile);
			int size = remotePath.segmentCount();
			for (int j = 0; j < size; j++) {
				String segment = remotePath.segment(j);
				if (segment.equals(projectName)) {
					remotePath = remotePath.removeFirstSegments(j);
					size = remotePath.segmentCount();
					for (int i = 0; i < size; i++) {
						remotePath = remotePath.removeFirstSegments(1);
						if (remotePath.segmentCount() > 0) {
							IResource res = project.getFile(remotePath);
							if (res != null && res.exists()) {
								return project.getFullPath().append(remotePath)
										.toString();
							}
						}
					}
					List<IPath> includePaths = getIncludePaths(project);
					if (includePaths.size() > 0) {
						return checkIncludePaths(remoteFile, includePaths);
					}
					break;
				}
			}
		}
		return remoteFile;
	}

	private String checkIncludePaths(String remoteFile, List<IPath> includePaths) {
		IPath remotePath = new Path(remoteFile);
		int size = remotePath.segmentCount();
		for (int i = 0; i < size; i++) {
			if (remotePath.segmentCount() > 0) {
				for (IPath includePath : includePaths) {
					File file = includePath.append(remotePath).toFile();
					if (file.exists()) {
						return file.toString();
					}
				}
				remotePath = remotePath.removeFirstSegments(1);
			}
		}
		return remoteFile;
	}

	public List<IPath> getIncludePaths(IProject project) throws ModelException {
		List<IPath> includePaths = resolvedIncludePaths.get(project.getName());
		if (includePaths != null) {
			return includePaths;
		} else {
			includePaths = new ArrayList<IPath>();
		}
		IncludePath[] paths = IncludePathManager.getInstance().getIncludePaths(
				project);
		for (IncludePath includePath : paths) {
			if (includePath.getEntry() instanceof IBuildpathEntry) {
				IBuildpathEntry bPath = (IBuildpathEntry) includePath
						.getEntry();
				if (bPath.getEntryKind() == IBuildpathEntry.BPE_CONTAINER
						&& !bPath.getPath().toString()
								.equals("org.eclipse.php.core.LANGUAGE")) { //$NON-NLS-1$
					IBuildpathContainer buildpathContainer = DLTKCore
							.getBuildpathContainer(bPath.getPath(),
									DLTKCore.create(project));
					if (buildpathContainer != null) {
						final IBuildpathEntry[] buildpathEntries = buildpathContainer
								.getBuildpathEntries();
						for (IBuildpathEntry buildpathEntry : buildpathEntries) {
							IPath localPath = EnvironmentPathUtils
									.getLocalPath(buildpathEntry.getPath());
							includePaths.add(localPath);
						}
					}
				}
			}
		}
		resolvedIncludePaths.put(project.getName(), includePaths);
		return includePaths;
	}

	// ---------------------------------------------------------------------------
	// ---------------------------------------------------------------------------
	// ---------------------------------------------------------------------------

	private class ThisHandleResponse implements ResponseHandler {
		Object responseHandler;

		public ThisHandleResponse(Object responseHandler) {
			this.responseHandler = responseHandler;
		}

		public void handleResponse(Object request, Object response) {
			boolean success = response != null
					&& ((IDebugResponseMessage) response).getStatus() == 0;

			if (request instanceof AddBreakpointRequest) {
				AddBreakpointRequest addBreakpointRequest = (AddBreakpointRequest) request;
				Breakpoint bp = addBreakpointRequest.getBreakpoint();
				String fileName = bp.getFileName();
				int lineNumber = bp.getLineNumber();
				int id = -1;
				if (response != null) {
					id = ((AddBreakpointResponse) response).getBreakpointID();
				}
				((BreakpointAddedResponseHandler) responseHandler)
						.breakpointAdded(fileName, lineNumber, id, success);

			} else if (request instanceof CancelBreakpointRequest) {
				((BreakpointRemovedResponseHandler) responseHandler)
						.breakpointRemoved(((CancelBreakpointRequest) request)
								.getBreakpointID(), success);

			} else if (request instanceof CancelAllBreakpointsRequest) {
				((AllBreakpointRemovedResponseHandler) responseHandler)
						.allBreakpointRemoved(success);

			} else if (request instanceof StartRequest) {
				((StartResponseHandler) responseHandler).started(success);

			} else if (request instanceof EvalRequest) {
				((EvalResponseHandler) responseHandler).evaled(
						((EvalRequest) request).getCommand(),
						success ? ((EvalResponse) response).getResult() : null,
						success);

			} else if (request instanceof StepIntoRequest) {
				((StepIntoResponseHandler) responseHandler).stepInto(success);

			} else if (request instanceof StepOverRequest) {
				((StepOverResponseHandler) responseHandler).stepOver(success);

			} else if (request instanceof StepOutRequest) {
				((StepOutResponseHandler) responseHandler).stepOut(success);

			} else if (request instanceof GoRequest) {
				((GoResponseHandler) responseHandler).go(success);

			} else if (request instanceof PauseDebuggerRequest) {
				((PauseResponseHandler) responseHandler).pause(success);

			} else if (request instanceof AssignValueRequest) {
				AssignValueRequest assignValueRequest = (AssignValueRequest) request;
				String var = assignValueRequest.getVar();
				String value = assignValueRequest.getValue();
				int depth = assignValueRequest.getDepth();
				String[] path = assignValueRequest.getPath();
				((AssignValueResponseHandler) responseHandler).valueAssigned(
						var, value, depth, path, success);

			} else if (request instanceof GetVariableValueRequest) {
				GetVariableValueRequest getVariableValueRequest = (GetVariableValueRequest) request;
				String value = getVariableValueRequest.getVar();
				int depth = getVariableValueRequest.getDepth();
				String[] path = getVariableValueRequest.getPath();

				String result = null;
				if (response != null) {
					try {
						result = new String(
								((GetVariableValueResponse) response)
										.getVarResult(),
								((IDebugMessage) response)
										.getTransferEncoding());
					} catch (UnsupportedEncodingException e) {
					}
				}
				((VariableValueResponseHandler) responseHandler).variableValue(
						value, depth, path, result, success);

			} else if (request instanceof GetCallStackRequest) {
				PHPstack remoteStack = null;
				if (response != null) {
					remoteStack = ((GetCallStackResponse) response)
							.getPHPstack();
				}
				convertToSystem(remoteStack);
				((GetCallStackResponseHandler) responseHandler).callStack(
						remoteStack, success);

			} else if (request instanceof GetStackVariableValueRequest) {
				GetStackVariableValueRequest getStackVariableValueRequest = (GetStackVariableValueRequest) request;

				int stackDepth = getStackVariableValueRequest.getLayerDepth();
				String value = getStackVariableValueRequest.getVar();
				int depth = getStackVariableValueRequest.getDepth();
				String[] path = getStackVariableValueRequest.getPath();

				String result = null;
				if (response != null) {
					try {
						result = new String(
								((GetStackVariableValueResponse) response)
										.getVarResult(),
								((IDebugMessage) response)
										.getTransferEncoding());
					} catch (UnsupportedEncodingException e) {
					}
				}
				((GetStackVariableValueResponseHandler) responseHandler)
						.stackVariableValue(stackDepth, value, depth, path,
								result, success);
			}
		}
	}
}