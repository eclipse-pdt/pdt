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
package org.eclipse.php.debug.core.debugger;

import java.io.File;

import org.eclipse.php.debug.core.communication.DebuggerCommunicationKit;
import org.eclipse.php.debug.core.communication.ResponseHandler;
import org.eclipse.php.debug.core.debugger.messages.AddBreakpointRequest;
import org.eclipse.php.debug.core.debugger.messages.AddBreakpointResponse;
import org.eclipse.php.debug.core.debugger.messages.AssignValueRequest;
import org.eclipse.php.debug.core.debugger.messages.CancelAllBreakpointsRequest;
import org.eclipse.php.debug.core.debugger.messages.CancelAllBreakpointsResponse;
import org.eclipse.php.debug.core.debugger.messages.CancelBreakpointRequest;
import org.eclipse.php.debug.core.debugger.messages.CancelBreakpointResponse;
import org.eclipse.php.debug.core.debugger.messages.DebugScriptEndedNotification;
import org.eclipse.php.debug.core.debugger.messages.DebugSessionClosedNotification;
import org.eclipse.php.debug.core.debugger.messages.DebugSessionStartedNotification;
import org.eclipse.php.debug.core.debugger.messages.DebuggerErrorNotification;
import org.eclipse.php.debug.core.debugger.messages.EvalRequest;
import org.eclipse.php.debug.core.debugger.messages.EvalResponse;
import org.eclipse.php.debug.core.debugger.messages.GetCallStackRequest;
import org.eclipse.php.debug.core.debugger.messages.GetCallStackResponse;
import org.eclipse.php.debug.core.debugger.messages.GetStackVariableValueRequest;
import org.eclipse.php.debug.core.debugger.messages.GetStackVariableValueResponse;
import org.eclipse.php.debug.core.debugger.messages.GetVariableValueRequest;
import org.eclipse.php.debug.core.debugger.messages.GetVariableValueResponse;
import org.eclipse.php.debug.core.debugger.messages.GoRequest;
import org.eclipse.php.debug.core.debugger.messages.GoResponse;
import org.eclipse.php.debug.core.debugger.messages.HeaderOutputNotification;
import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.debug.core.debugger.messages.OutputNotification;
import org.eclipse.php.debug.core.debugger.messages.ParsingErrorNotification;
import org.eclipse.php.debug.core.debugger.messages.PauseDebuggerRequest;
import org.eclipse.php.debug.core.debugger.messages.PauseDebuggerResponse;
import org.eclipse.php.debug.core.debugger.messages.ReadyNotification;
import org.eclipse.php.debug.core.debugger.messages.StartRequest;
import org.eclipse.php.debug.core.debugger.messages.StartResponse;
import org.eclipse.php.debug.core.debugger.messages.StepIntoRequest;
import org.eclipse.php.debug.core.debugger.messages.StepIntoResponse;
import org.eclipse.php.debug.core.debugger.messages.StepOutRequest;
import org.eclipse.php.debug.core.debugger.messages.StepOutResponse;
import org.eclipse.php.debug.core.debugger.messages.StepOverRequest;
import org.eclipse.php.debug.core.debugger.messages.StepOverResponse;

/**
 * @author eran
 */
public class RemoteDebugger implements IRemoteDebugger {
    
    public static final int PROTOCOL_ID = 2006040701;
	
	private DebuggerCommunicationKit kit;
	private IDebugHandler debugHandler;

	/**
	 * Creates new RemoteDebugSession
	 */
	public RemoteDebugger(IDebugHandler debugHandler) {
		this.kit = createCommunicationKit();
		this.debugHandler = debugHandler;
	}
	
	/**
	 * Creates a DebuggerCommunicationKit.
	 * Subclasses may override this method to create a different communication kit.
	 * 
	 * @return A newly created DebuggerCommunicationKit.
	 */
	protected DebuggerCommunicationKit createCommunicationKit() {
		DebuggerCommunicationKit kit = new DebuggerCommunicationKit();
		kit.setCommunicationAdministrator(this);
		kit.setCommunicationClient(this);
		return kit;
	}
	
	public IDebugHandler getDebugHandler() {
		return debugHandler;
	}
	
	public DebuggerCommunicationKit getCommunicationKit() {
		return kit;
	}

	public void openConnection(int debugPort) {
		kit.openConnection(debugPort);
	}

	public void openConnection(String host, int remotePort) {
		kit.connectToPeer(host, remotePort);
	}

	public void closeConnection() {
		kit.closeConnection();
	}

	public void setDebugPort(int debugPort) {
		kit.closeConnection();
		kit.openConnection(debugPort);
	}

	public int getPort() {
		return kit.getPort();
	}

	public void setPeerResponseTimeout(int timeout) {
		kit.setPeerResponseTimeout(timeout);
	}

	public void connectionEstablished() {
		debugHandler.connectionEstablished();
	}

	public void connectionClosed() {
		debugHandler.connectionClosed();
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

			int serverProtocol = debugSessionStartedNotification.getServerProtocolID();
			if (serverProtocol != PROTOCOL_ID) {
				debugHandler.wrongDebugServer();
				this.finish();
				return;
			}

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
			debugHandler.handleScriptEnded(); // 2 options: close message or
			// start profile
		}
	}

	public void closeDebugSession() {
		if (kit.isConnected()) {
			kit.sendNotification(new DebugSessionClosedNotification());
		}
	}

	public void handleMultipleBindings() {
		debugHandler.multipleBindOccured();
	}

	public void handlePeerResponseTimeout() {
		debugHandler.connectionTimedout();
	}

	public static String convertToSystemIndependentFileName(String fileName) {
		if (fileName == null)
			return null;
		fileName = fileName.replace('\\', '/');
		return fileName;
	}

	private static final String convertToSystemDependentFileName(String fileName) {
		if (fileName == null)
			return null;
		fileName = fileName.replace('/', File.separatorChar);
		fileName = fileName.replace('\\', File.separatorChar);
		return fileName;
	}

	// ---------------------------------------------------------------------------
	
	/**
	 * Sends the request through the communication kit and returns response 
	 * 
	 * @param message request that will be sent to the debugger
	 * @return message response recieved from the debugger
	 */
	public IDebugResponseMessage sendCustomRequest (IDebugRequestMessage request) {
		IDebugResponseMessage response = null;
		if (this.isActive()) {
			try {
				Object obj = kit.sendRequest(request);
				if (obj instanceof IDebugResponseMessage) {
					response = (IDebugResponseMessage)obj;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}
	
	/**
	 * Sends custom notification through the communication kit
	 * 
	 * @param message notification that will be delivered to the debugger
	 * @return <code>true</code> if succeeded sending the message, <code>false</code> - otherwise
	 */
	public boolean sendCustomNotification (IDebugNotificationMessage notification) {
		if (this.isActive()) {
			try {
				kit.sendNotification(notification);
				return true;
			}
			catch (Exception e) {
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
			String fileName = convertToSystemIndependentFileName(tmpBreakpoint.getFileName());
			tmpBreakpoint.setFileName(fileName);
			request.setBreakpoint(tmpBreakpoint);
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Ssynchronic addBreakpoint Returns true if successed adding the
	 * Breakpoint.
	 */
	public void addBreakpoint(Breakpoint breakpoint) {
		if (!this.isActive()) {
			return;
		}
		try {
			AddBreakpointRequest request = new AddBreakpointRequest();
			Breakpoint tmpBreakpoint = (Breakpoint) breakpoint.clone();
			String fileName = convertToSystemIndependentFileName(tmpBreakpoint.getFileName());
			tmpBreakpoint.setFileName(fileName);
			request.setBreakpoint(tmpBreakpoint);
			AddBreakpointResponse response = (AddBreakpointResponse) kit.sendRequest(request);
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
		kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			CancelBreakpointResponse response = (CancelBreakpointResponse) kit.sendRequest(request);
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
		kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			CancelAllBreakpointsResponse response = (CancelAllBreakpointsResponse) kit.sendRequest(request);
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
		kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			StepIntoResponse response = (StepIntoResponse) kit.sendRequest(request);
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
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			StepOverResponse response = (StepOverResponse) kit.sendRequest(request);
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
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			StepOutResponse response = (StepOutResponse) kit.sendRequest(request);
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
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			GoResponse response = (GoResponse) kit.sendRequest(request);
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
		StartRequest request = new StartRequest();
		try {
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
		StartRequest request = new StartRequest();
		try {
			StartResponse response = (StartResponse) kit.sendRequest(request);
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
	public boolean pause(PauseResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		PauseDebuggerRequest request = new PauseDebuggerRequest();
		try {
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			PauseDebuggerResponse response = (PauseDebuggerResponse) kit.sendRequest(request);
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
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
		try {
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
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
		try {
			kit.sendRequest(request);
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
			EvalResponse response = (EvalResponse) kit.sendRequest(request);
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
		kit.closeConnection();
	}

	/**
	 * Checks if there is a connection.
	 */
	public boolean isActive() {
		return kit != null && kit.isConnected();
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
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getVariableValue Returns the variable var.
	 */
	public String getVariableValue(String var, int depth, String[] path) throws IllegalArgumentException {
		if (!this.isActive()) {
			return null;
		}
		GetVariableValueRequest request = new GetVariableValueRequest();
		request.setVar(var);
		request.setDepth(depth);
		request.setPath(path);
		GetVariableValueResponse response = null;
		try {
			response = (GetVariableValueResponse) kit.sendRequest(request);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		if (response == null || response.getStatus() != 0) {
			return null;
		}
		String output = response.getVarResult();
		return output;
	}

	public boolean getCallStack(GetCallStackResponseHandler responseHandler) {
		if (!this.isActive()) {
			return false;
		}
		GetCallStackRequest request = new GetCallStackRequest();
		try {
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
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
			GetCallStackResponse response = (GetCallStackResponse) kit.sendRequest(request);
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
			kit.sendRequest(request, new ThisHandleResponse(responseHandler));
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Synchronic getStackVariableValue Returns the variable value.
	 */
	public String getStackVariableValue(int stackDepth, String value, int depth, String[] path) {
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
			response = (GetStackVariableValueResponse) kit.sendRequest(request);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		if (response == null || response.getStatus() != 0) {
			return null;
		}
		String output = response.getVarResult();
		return output;
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
					result = ((GetVariableValueResponse) response).getVarResult();
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
					result = ((GetStackVariableValueResponse) response).getVarResult();
				}
				((GetStackVariableValueResponseHandler) responseHandler).stackVariableValue(stackDepth, value, depth, path, result, success);
			}

		}
	}
}