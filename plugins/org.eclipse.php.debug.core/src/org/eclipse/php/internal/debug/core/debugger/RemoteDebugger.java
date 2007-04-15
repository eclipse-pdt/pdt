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
package org.eclipse.php.internal.debug.core.debugger;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.internal.debug.core.communication.DebugConnectionThread;
import org.eclipse.php.internal.debug.core.communication.ResponseHandler;
import org.eclipse.php.internal.debug.core.debugger.messages.*;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;

/**
 * An IRemoteDebugger implementation. 
 */
public class RemoteDebugger implements IRemoteDebugger {

	public static final int PROTOCOL_ID = 2006040701;

	protected boolean isDebugMode = System.getProperty("loggingDebug") != null;
	protected boolean protocolSet;
	private DebugConnectionThread connection;
	private IDebugHandler debugHandler;

	/**
	 * Creates new RemoteDebugSession
	 */
	public RemoteDebugger(IDebugHandler debugHandler, DebugConnectionThread connectionThread) {
		//		this.kit = createCommunicationKit();
		connection = connectionThread;
		this.debugHandler = debugHandler;
		connection.setCommunicationAdministrator(this);
		connection.setCommunicationClient(this);
	}

	public IDebugHandler getDebugHandler() {
		return debugHandler;
	}

	public DebugConnectionThread getConnectionThread() {
		return connection;
	}

	public void closeConnection() {
		connection.closeConnection();
		protocolSet = false;
	}

	public void setPeerResponseTimeout(int timeout) {
		connection.setPeerResponseTimeout(timeout);
	}

	public void connectionEstablished() {
		debugHandler.connectionEstablished();
	}

	public void connectionClosed() {
		debugHandler.connectionClosed();
		protocolSet = false;
	}

	public void handleNotification(Object msg) {
		if (msg instanceof OutputNotification) {
			String output = ((OutputNotification) msg).getOutput();
			debugHandler.newOutput(output);
		} else if (msg instanceof ReadyNotification) {
			ReadyNotification readyNotification = (ReadyNotification) msg;
			String currentFile = readyNotification.getFileName();
			int currentLine = readyNotification.getLineNumber();
			debugHandler.ready(convertToSystemDependentFileName(currentFile), currentLine);
		} else if (msg instanceof DebugSessionStartedNotification) {
			DebugSessionStartedNotification debugSessionStartedNotification = (DebugSessionStartedNotification) msg;
			String fileName = debugSessionStartedNotification.getFileName();
			String uri = debugSessionStartedNotification.getUri();
			String query = debugSessionStartedNotification.getQuery();
			String options = debugSessionStartedNotification.getOptions();
			debugHandler.sessionStarted(fileName, uri, query, options);
		} else if (msg instanceof HeaderOutputNotification) {
			debugHandler.newHeaderOutput(((HeaderOutputNotification) msg).getOutput());
		} else if (msg instanceof ParsingErrorNotification) {
			ParsingErrorNotification parseError = (ParsingErrorNotification) msg;
			String errorText = parseError.getErrorText();
			String fileName = convertToSystemDependentFileName(parseError.getFileName());
			int lineNumber = parseError.getLineNumber();
			int errorLevel = parseError.getErrorLevel();

			DebugError debugError = new DebugError(errorLevel, fileName, lineNumber, errorText);
			debugHandler.parsingErrorOccured(debugError);
		} else if (msg instanceof DebuggerErrorNotification) {
			DebuggerErrorNotification parseError = (DebuggerErrorNotification) msg;
			int errorLevel = parseError.getErrorLevel();
			DebugError debugError = new DebugError();
			String errorText = parseError.getErrorText();
			if (errorText != null && !errorText.equals("")) {
				debugError.setErrorText(errorText);
			}

			debugError.setCode(errorLevel);
			debugHandler.debuggerErrorOccured(debugError);
		} else if (msg instanceof DebugScriptEndedNotification) {
			debugHandler.handleScriptEnded(); // 2 options: close message or // XXX - uncomment
			// start profile
		}
	}

	public void closeDebugSession() {
		if (connection.isConnected()) {
			connection.sendNotification(new DebugSessionClosedNotification());
			protocolSet = false;
		}
	}

	public void handleMultipleBindings() {
		debugHandler.multipleBindOccured();
	}

	public void handlePeerResponseTimeout() {
		debugHandler.connectionTimedout();
	}

	/**
	 * Converts the given file name to a system independent file name. 
	 * The convertion is platform independent and is similar to calling the convertToSystemIndependentFileName(fileName, true)
	 * @param fileName
	 * @return
	 */
	public static String convertToSystemIndependentFileName(String fileName) {
		return convertToSystemIndependentFileName(fileName, true);
	}

	/**
	 * Converts the given file name to a system independent file name. If the ignoreSystemType is false, the
	 * convertion will occure only if the current system is Microsoft Windows.
	 * @param fileName
	 * @return
	 */
	public static String convertToSystemIndependentFileName(String fileName, boolean ignoreSystemType) {
		if (ignoreSystemType || !ignoreSystemType && Platform.WS_WIN32.equals(Platform.getOS())) {
			if (fileName == null)
				return null;
			fileName = fileName.replace('\\', '/');
		}
		return fileName;
	}

	private static final String convertToSystemDependentFileName(String fileName) {
		if (fileName == null)
			return null;
		if (Platform.WS_WIN32.equals(Platform.getOS())) {
			fileName = fileName.replace('/', File.separatorChar);
			fileName = fileName.replace('\\', File.separatorChar);
		}
		return fileName;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Sends the request through the communication connection and returns response 
	 * 
	 * @param message request that will be sent to the debugger
	 * @return message response recieved from the debugger
	 */
	public IDebugResponseMessage sendCustomRequest(IDebugRequestMessage request) {
		if (isDebugMode) {
			System.out.println("Sending custome request: " + request + " (type = " + request.getType() + ')');
		}
		IDebugResponseMessage response = null;
		if (this.isActive()) {
			try {
				Object obj = connection.sendRequest(request);
				if (obj instanceof IDebugResponseMessage) {
					response = (IDebugResponseMessage) obj;
				}
				if (isDebugMode && response != null) {
					System.out.println("Response to custom request: " + response + " (type = " + response.getType() + ')');
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
	 * @param message notification that will be delivered to the debugger
	 * @return <code>true</code> if succeeded sending the message, <code>false</code> - otherwise
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
	 * Asynchronic addBreakpoint Returns true if successed sending the request,
	 * false otherwise.
	 */
	public boolean addBreakpoint(Breakpoint bp, BreakpointAddedResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			AddBreakpointRequest request = new AddBreakpointRequest();
			Breakpoint tmpBreakpoint = (Breakpoint) bp.clone();
			String fileName = convertToSystemIndependentFileName(tmpBreakpoint.getFileName(), false);
			tmpBreakpoint.setFileName(fileName);
			request.setBreakpoint(tmpBreakpoint);
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic addBreakpoint Returns true if successed adding the
	 * Breakpoint.
	 */
	public void addBreakpoint(Breakpoint breakpoint) {
		if (!this.isActive()) {
			return;
		}
		try {
			AddBreakpointRequest request = new AddBreakpointRequest();
			Breakpoint tmpBreakpoint = (Breakpoint) breakpoint.clone();
			String fileName = convertToSystemIndependentFileName(tmpBreakpoint.getFileName(), false);
			tmpBreakpoint.setFileName(fileName);
			request.setBreakpoint(tmpBreakpoint);
			AddBreakpointResponse response = (AddBreakpointResponse) connection.sendRequest(request);
			if (response != null && response.getStatus() == 0) {
				// Log.writeLog("addBreakpoint");
				breakpoint.setID(response.getBreakpointID());
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Asynchronic removeBreakpoint Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean removeBreakpoint(int id, BreakpointRemovedResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		CancelBreakpointRequest request = new CancelBreakpointRequest();
		request.setBreakpointID(id);
		connection.sendRequest(request, new ThisHandleResponse(responseHandler));
		return true;
	}

	/**
	 * Ssynchronic removeBreakpoint Returns true if successed removing the
	 * Breakpoint.
	 */
	public boolean removeBreakpoint(int id) {
		if (!this.isActive()) {
			return false;
		}
		try {
			CancelBreakpointRequest request = new CancelBreakpointRequest();
			request.setBreakpointID(id);
			CancelBreakpointResponse response = (CancelBreakpointResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic removeBreakpoint Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean removeBreakpoint(Breakpoint breakpoint, BreakpointRemovedResponseHandler responseHandler) {
		return removeBreakpoint(breakpoint.getID(), responseHandler);
	}

	/**
	 * Synchronic removeBreakpoint Returns true if successed removing the
	 * Breakpoint.
	 */
	public boolean removeBreakpoint(Breakpoint breakpoint) {
		return removeBreakpoint(breakpoint.getID());
	}

	/**
	 * Asynchronic removeAllBreakpoints Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean removeAllBreakpoints(AllBreakpointRemovedResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		CancelAllBreakpointsRequest request = new CancelAllBreakpointsRequest();
		connection.sendRequest(request, new ThisHandleResponse(responseHandler));
		return true;
	}

	/**
	 * Synchronic removeAllBreakpoints Returns true if successed removing all
	 * the Breakpoint.
	 */
	public boolean removeAllBreakpoints() {
		if (!this.isActive()) {
			return false;
		}
		try {
			CancelAllBreakpointsRequest request = new CancelAllBreakpointsRequest();
			CancelAllBreakpointsResponse response = (CancelAllBreakpointsResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic stepInto Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean stepInto(StepIntoResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		StepIntoRequest request = new StepIntoRequest();
		connection.sendRequest(request, new ThisHandleResponse(responseHandler));
		return true;
	}

	/**
	 * Synchronic stepInto Returns true if successed stepInto.
	 */
	public boolean stepInto() {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepIntoRequest request = new StepIntoRequest();
			StepIntoResponse response = (StepIntoResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic stepOver Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean stepOver(StepOverResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOverRequest request = new StepOverRequest();
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic stepOver Returns true if successed stepOver.
	 */
	public boolean stepOver() {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOverRequest request = new StepOverRequest();
			StepOverResponse response = (StepOverResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic stepOut Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean stepOut(StepOutResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOutRequest request = new StepOutRequest();
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic stepOut Returns true if successed stepOut.
	 */
	public boolean stepOut() {
		if (!this.isActive()) {
			return false;
		}
		try {
			StepOutRequest request = new StepOutRequest();
			StepOutResponse response = (StepOutResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic go Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean go(GoResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		try {
			GoRequest request = new GoRequest();
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic go Returns true if successed go.
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
	 * Asynchronic start Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean start(StartResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		if (!setProtocol(getProtocolID())) {
			return false;
		}
		debugHandler.getDebugTarget().installDeferredBreakpoints();
		StartRequest request = new StartRequest();
		try {
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic start Returns true if successed start.
	 */
	public boolean start() {
		if (!this.isActive()) {
			return false;
		}
		if (!setProtocol(getProtocolID())) {
			return false;
		}
		StartRequest request = new StartRequest();
		try {
			StartResponse response = (StartResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns true if the protocol was successfully set by this debugger.
	 * 
	 * @return True, if the protocol was set; False, otherwise.
	 */
	public boolean isProtocolSet() {
		return protocolSet;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.Debugger#setProtocol(int)
	 */
	public boolean setProtocol(int protocolID) {
		SetProtocolRequest request = new SetProtocolRequest();
		request.setProtocolID(protocolID);
		IDebugResponseMessage response = sendCustomRequest(request);
		if (response != null && response instanceof SetProtocolResponse) {
			int responceProtocolID = ((SetProtocolResponse) response).getProtocolID();
			if (responceProtocolID != protocolID) {
				getDebugHandler().wrongDebugServer();
				protocolSet = false;
			} else {
				protocolSet = true;
			}
		} else {
			protocolSet = false;
		}
		return protocolSet;
	}

	/**
	 * Returns the protocol ID that should be used by this debugger.
	 * @return
	 */
	protected int getProtocolID() {
		return PROTOCOL_ID;
	}

	/**
	 * Asynchronic pause Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean pause(PauseResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		PauseDebuggerRequest request = new PauseDebuggerRequest();
		try {
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic pause Returns true if successed pause.
	 */
	public boolean pause() {
		if (!this.isActive()) {
			return false;
		}
		PauseDebuggerRequest request = new PauseDebuggerRequest();
		try {
			PauseDebuggerResponse response = (PauseDebuggerResponse) connection.sendRequest(request);
			return response != null && response.getStatus() == 0;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Asynchronic pause Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean eval(String commandString, EvalResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		EvalRequest request = new EvalRequest();
		request.setCommand(commandString);
		try {
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	public boolean assignValue(String var, String value, int depth, String[] path, AssignValueResponseHandler responseHandler) {
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
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
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
		String projectName = debugHandler.getDebugTarget().getProjectName();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return PHPProjectPreferences.getTransferEncoding(project);
	}

	/**
	 * aSynchronic assigned value
	 */
	public boolean assignValue(String var, String value, int depth, String[] path) {
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
	 * Synchronic pause Returns true if successed pause.
	 */
	public String eval(String commandString) {
		if (!this.isActive()) {
			return null;
		}
		EvalRequest request = new EvalRequest();
		request.setCommand(commandString);
		try {
			EvalResponse response = (EvalResponse) connection.sendRequest(request);
			String result = null;
			if (response != null) {
				if (response.getStatus() == 0) {
					result = response.getResult();
				} else {
					result = "---ERROR---";
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
		protocolSet = false;
	}

	/**
	 * Checks if there is a connection.
	 */
	public boolean isActive() {
		return connection != null && connection.isConnected();
	}

	public boolean getVariableValue(String var, int depth, String[] path, VariableValueResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		GetVariableValueRequest request = new GetVariableValueRequest();
		request.setVar(var);
		request.setDepth(depth);
		request.setPath(path);
		try {
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getVariableValue Returns the variable var.
	 */
	public byte[] getVariableValue(String var, int depth, String[] path) throws IllegalArgumentException {
		if (!this.isActive()) {
			return null;
		}
		GetVariableValueRequest request = new GetVariableValueRequest();
		request.setVar(var);
		request.setDepth(depth);
		request.setPath(path);
		GetVariableValueResponse response = null;
		try {
			response = (GetVariableValueResponse) connection.sendRequest(request);
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
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getCallStack Returns the Stack layer.
	 */
	public PHPstack getCallStack() {
		if (!this.isActive()) {
			return null;
		}
		GetCallStackRequest request = new GetCallStackRequest();
		PHPstack remoteStack = null;
		try {
			GetCallStackResponse response = (GetCallStackResponse) connection.sendRequest(request);
			if (response != null) {
				remoteStack = response.getPHPstack();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		convertToSystem(remoteStack);
		return remoteStack;
	}

	private static void convertToSystem(PHPstack remoteStack) {
		if (remoteStack != null) {
			for (int i = 0; i < remoteStack.getSize(); i++) {
				StackLayer layer = remoteStack.getLayer(i);
				layer.setCallerLineNumber(layer.getCallerLineNumber() - 1);
				layer.setCalledLineNumber(layer.getCalledLineNumber() - 1);

				layer.setCallerFileName(convertToSystemDependentFileName(layer.getCallerFileName()));
				layer.setCalledFileName(convertToSystemDependentFileName(layer.getCalledFileName()));
			}

		}
	}

	public boolean getStackVariableValue(int stackDepth, String value, int depth, String[] path, GetStackVariableValueResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		GetStackVariableValueRequest request = new GetStackVariableValueRequest();
		request.setVar(value);
		request.setDepth(depth);
		request.setLayerDepth(stackDepth);
		request.setPath(path);
		try {
			connection.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getStackVariableValue Returns the variable value.
	 */
	public byte[] getStackVariableValue(int stackDepth, String value, int depth, String[] path) {
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
			response = (GetStackVariableValueResponse) connection.sendRequest(request);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		if (response == null || response.getStatus() != 0) {
			return null;
		}
		return response.getVarResult();
	}

	// ---------------------------------------------------------------------------
	// ---------------------------------------------------------------------------
	// ---------------------------------------------------------------------------

	private static class ThisHandleResponse implements ResponseHandler {
		Object responseHandler;

		public ThisHandleResponse(Object responseHandler) {
			this.responseHandler = responseHandler;
		}

		public void handleResponse(Object request, Object response) {
			boolean success = response != null && ((IDebugResponseMessage) response).getStatus() == 0;

			if (request instanceof AddBreakpointRequest) {
				AddBreakpointRequest addBreakpointRequest = (AddBreakpointRequest) request;
				Breakpoint bp = addBreakpointRequest.getBreakpoint();
				String fileName = convertToSystemDependentFileName(bp.getFileName());
				int lineNumber = bp.getLineNumber();
				int id = -1;
				if (response != null) {
					id = ((AddBreakpointResponse) response).getBreakpointID();
				}
				((BreakpointAddedResponseHandler) responseHandler).breakpointAdded(fileName, lineNumber, id, success);

			} else if (request instanceof CancelBreakpointRequest) {
				((BreakpointRemovedResponseHandler) responseHandler).breakpointRemoved(((CancelBreakpointRequest) request).getBreakpointID(), success);

			} else if (request instanceof CancelAllBreakpointsRequest) {
				((AllBreakpointRemovedResponseHandler) responseHandler).allBreakpointRemoved(success);

			} else if (request instanceof StartRequest) {
				((StartResponseHandler) responseHandler).started(success);

			} else if (request instanceof EvalRequest) {
				((EvalResponseHandler) responseHandler).evaled(((EvalRequest) request).getCommand(), (success) ? ((EvalResponse) response).getResult() : null, success);

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
				((AssignValueResponseHandler) responseHandler).valueAssigned(var, value, depth, path, success);

			} else if (request instanceof GetVariableValueRequest) {
				GetVariableValueRequest getVariableValueRequest = (GetVariableValueRequest) request;
				String value = getVariableValueRequest.getVar();
				int depth = getVariableValueRequest.getDepth();
				String[] path = getVariableValueRequest.getPath();

				String result = null;
				if (response != null) {
					try {
						result = new String(((GetVariableValueResponse) response).getVarResult(), ((IDebugMessage) response).getTransferEncoding());
					} catch (UnsupportedEncodingException e) {
					}
				}
				((VariableValueResponseHandler) responseHandler).variableValue(value, depth, path, result, success);

			} else if (request instanceof GetCallStackRequest) {
				PHPstack remoteStack = null;
				if (response != null) {
					remoteStack = ((GetCallStackResponse) response).getPHPstack();
				}
				convertToSystem(remoteStack);
				((GetCallStackResponseHandler) responseHandler).callStack(remoteStack, success);

			} else if (request instanceof GetStackVariableValueRequest) {
				GetStackVariableValueRequest getStackVariableValueRequest = (GetStackVariableValueRequest) request;

				int stackDepth = getStackVariableValueRequest.getLayerDepth();
				String value = getStackVariableValueRequest.getVar();
				int depth = getStackVariableValueRequest.getDepth();
				String[] path = getStackVariableValueRequest.getPath();

				String result = null;
				if (response != null) {
					try {
						result = new String(((GetStackVariableValueResponse) response).getVarResult(), ((IDebugMessage) response).getTransferEncoding());
					} catch (UnsupportedEncodingException e) {
					}
				}
				((GetStackVariableValueResponseHandler) responseHandler).stackVariableValue(stackDepth, value, depth, path, result, success);
			}
		}
	}
}