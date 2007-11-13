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
package org.eclipse.php.internal.debug.core.zend.communication;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.core.util.BlockingQueue;
import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugMessagesRegistry;
import org.eclipse.php.internal.debug.core.zend.debugger.PHPSessionLaunchMapper;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugSessionStartedNotification;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.GetVariableValueRequest;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.OutputNotification;
import org.eclipse.php.internal.debug.core.zend.debugger.parameters.AbstractDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.swt.widgets.Display;

/**
 * The debug connection thread is responsible of initializing and handle a single debug session that was
 * triggered by a remote or local debugger.
 *
 * @author shalom
 */
public class DebugConnectionThread implements Runnable {

	protected static int startMessageId = (new DebugSessionStartedNotification()).getType();
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	protected boolean validProtocol;
	private boolean isInitialized;
	private InputMessageHandler inputMessageHandler;
	private CommunicationClient communicationClient;
	private CommunicationAdministrator administrator;
	private Object CONNECTION_CLOSED_MSG = new Object();
	protected boolean isDebugMode = System.getProperty("loggingDebug") != null;
	private IntHashtable requestsTable;
	private IntHashtable responseTable;
	private Hashtable responseHandlers;
	private InputManager inputManager;
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	private DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
	private int lastRequestID = 1000;
	protected int peerResponseTimeout = 500; // 0.5 seconds.
	protected PHPDebugTarget debugTarget;
	private Thread theThread;

	/**
	 * Constructs a new DebugConnectionThread with a given Socket.
	 *
	 * @param socket
	 */
	public DebugConnectionThread(Socket socket) {
		this.socket = socket;
		requestsTable = new IntHashtable();
		responseTable = new IntHashtable();
		responseHandlers = new Hashtable();
		theThread = new Thread(this);
		theThread.start();
	}

	/*
	 *  (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			socket.setTcpNoDelay(true);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			synchronized (in) {
				//These 2 methods are blocked until the threads
				//will stop working and will be free to handle
				//another connection.
				restartInputMessageHandler(out);
				restartInputManager(in);
				this.in = in;
				this.out = out;
				isInitialized = true;
			}
			//			getCommunicationAdministrator().connectionEstablished();
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	public void setCommunicationClient(CommunicationClient client) {
		this.communicationClient = client;
	}

	public CommunicationClient getCommunicationClient() {
		return communicationClient;
	}

	public void setCommunicationAdministrator(CommunicationAdministrator admin) {
		administrator = admin;
	}

	public CommunicationAdministrator getCommunicationAdministrator() {
		return administrator;
	}

	public int getPeerResponseTimeout() {
		return peerResponseTimeout;
	}

	public void setPeerResponseTimeout(int peerResponseTimeout) {
		this.peerResponseTimeout = peerResponseTimeout;
	}

	/**
	 * Deliver a Notification.
	 *
	 * @param msg The delivered notification message.
	 */
	public void sendNotification(Object msg) {
		try {
			synchronized (byteArrayOutputStream) {
				byteArrayOutputStream.reset();
				((IDebugMessage) msg).serialize(dataOutputStream);
				synchronized (out) {
					out.writeInt(byteArrayOutputStream.size());
					if (isDebugMode) {
						System.out.println("sending notification request size=" + byteArrayOutputStream.size());
					}
					byteArrayOutputStream.writeTo(out);
					out.flush();
				}
			}
		} catch (SocketException se) {
			// probably because the remote host disconnected
			// Just log a warning (might be removed in the near future)
			if (isDebugMode) {
				Logger.log(Logger.WARNING, se.getMessage(), se);
			}
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	/**
	 * Deliver a request & wait for a response
	 *
	 * @param request The delivered Request message.
	 * @return A response for the delivered request.
	 */
	public Object sendRequest(Object request) throws Exception {
		if (isDebugMode) {
			if (request instanceof GetVariableValueRequest) {
				GetVariableValueRequest gvr = (GetVariableValueRequest) request;
				System.out.println("sendRequest()->GetVariableValueRequest:getVar() = " + gvr.getVar());
			}
		}
		try {
			IDebugRequestMessage theMsg = (IDebugRequestMessage) request;
			synchronized (byteArrayOutputStream) {
				byteArrayOutputStream.reset();
				theMsg.setID(lastRequestID++);
				theMsg.serialize(dataOutputStream);

				int messageSize = byteArrayOutputStream.size();

				if (isDebugMode) {
					System.out.println("sending message request size=" + messageSize + " type=" + theMsg.getType());
				}
				synchronized (out) {
					requestsTable.put(theMsg.getID(), theMsg);
					//System.out.println("Request table has " +requestsTable.size() +" items");
					out.writeInt(messageSize);
					byteArrayOutputStream.writeTo(out);
					out.flush();
				}
			}

			IDebugResponseMessage response = null;
			int timeoutCount = 20;
			while (response == null && isConnected()) {
				synchronized (request) {
					response = (IDebugResponseMessage) responseTable.remove(theMsg.getID());
					if (response == null) {
						if (isDebugMode) {
							System.out.println("Response is null. Waiting " + ((21 - timeoutCount) * peerResponseTimeout) + " milliseconds");
						}
						if (timeoutCount == 15) { // Display a progress dialog after a quarter of the assigned time have passed.
							// Display a message that we are waiting for the server response.
							// In case that the response finally arrives, remove the message.
							// In case we have a timeout, close the connection and display a different message.
							PHPLaunchUtilities.showWaitForDebuggerMessage(this);
						}
						request.wait(peerResponseTimeout);
					}
				}
				if (response == null) {
					response = (IDebugResponseMessage) responseTable.remove(theMsg.getID());
				}

				// if the response is null. it means that there is no answer from the server.
				// This can be because on the peerResponseTimeout.
				if (response == null && isConnected()) {
					if (isDebugMode) {
						System.out.println("Communication problems");
					}
					// Handle time out will stop the communication if need to stop.
					//System.out.println("handleto");

					if (timeoutCount > 0) {
						timeoutCount--;
						handlePeerResponseTimeout();
					} else {
						closeConnection();
						PHPLaunchUtilities.hideWaitForDebuggerMessage();
						PHPLaunchUtilities.showLaunchErrorMessage();
					}
					if (!isConnected())
						break;
					//System.out.println("rewaiting");
				}
			}
			PHPLaunchUtilities.hideWaitForDebuggerMessage();
			if (isDebugMode) {
				System.out.println("response received by client: " + response);
			}
			return response;

		} catch (IOException exc) { // Return null for any exception
			//Log.writeLog("No Connection");
			//Log.writeLog(exc);

		} catch (InterruptedException exc) {// Return null for any exception
			//Log.writeLog(exc);
		}
		return null;
	}

	/**
	 *
	 * @param request
	 * @param responseHandler
	 */
	public void sendRequest(Object request, ResponseHandler responseHandler) {
		//Integer msgId = new Integer(lastRequestID++);
		int msgId = lastRequestID++;
		IDebugRequestMessage theMsg = (IDebugRequestMessage) request;
		try {
			//System.out.println("sending:"+request);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			//msg.setID(msgId.intValue());
			theMsg.setID(msgId);
			theMsg.serialize(dataOutputStream);

			int messageSize = byteArrayOutputStream.size();
			if (isDebugMode) {
				System.out.println("sending message request size=" + messageSize);
			}

			synchronized (out) {
				requestsTable.put(msgId, request);
				responseHandlers.put(new Integer(msgId), responseHandler);
				out.writeInt(messageSize);
				byteArrayOutputStream.writeTo(out);
				out.flush();
			}
		} catch (Exception exc) { // Return null for any exception
			System.out.println("Exception for request no." + theMsg.getType() + exc.toString());
			responseHandler.handleResponse(request, null);
			responseHandlers.remove(new Integer(msgId));
		}
	}

	/**
	 * Returns true if the connection is alive.
	 */
	public boolean isConnected() {
		if (in != null) {
			try {
				in.available();
				return true;
			} catch (IOException ioe) {
			}
		}
		return false;
	}

	/**
	 * Closes the connection.
	 * Destroys the server socket without cleaning the the request/response tables.
	 * The cleaning will be done by InputMessageHandler on termination.
	 */
	public synchronized void closeConnection() {
		Logger.debugMSG("[" + this + "] DebugConnectionThread: Starting closeConnection");
		cleanSocket();

		Logger.debugMSG("[" + this + "] DebugConnectionThread: Thread interrupt");
		if (theThread.isAlive()) {
			theThread.interrupt();
		}

		Logger.debugMSG("[" + this + "] DebugConnectionThread: closing the socket");
		if (socket != null) {
			try {
				if (!socket.isClosed())
					socket.close();
			} catch (Exception exc) {
				PHPDebugPlugin.log(exc);
			}
			socket = null;
		}

		// TODO - Check: Commented out - Causing for the second launch of debug to hang

		/*        Logger.debugMSG("DebuggerCommunicationKit: Terminating inputManager");
		 if (inputManager != null && inputManager.isAlive()) {
		 inputManager.terminate();
		 }
		 Logger.debugMSG("DebuggerCommunicationKit: Terminating inputMessageHandler");
		 if (inputMessageHandler != null && inputMessageHandler.isAlive()) {
		 inputMessageHandler.terminate();
		 } */
	}

	/**
	 * This method initializes the input message handler thread.
	 * If it exists it reinitializes the thread.
	 */
	protected void restartInputMessageHandler(DataOutputStream out) {
		if (inputMessageHandler == null) {
			inputMessageHandler = new InputMessageHandler(out);
		} else {
			//inputMessageHandler.start(out,true);
			inputMessageHandler.waitForStart(out, true);
		}
	}

	/**
	 * This method initializes the input manager thread.
	 * If it exists it reinitializes the thread.
	 */
	protected void restartInputManager(DataInputStream in) {
		try {
			if (inputManager == null) {
				inputManager = new InputManager(in);
			} else {
				//inputManager.start(in,out);
				inputManager.waitForStart(in);
			}
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	/**
	 * Reads and returns the message type from the data input stream.
	 *
	 * @return The message type id.
	 * @throws IOException
	 */
	protected short getMessageType(DataInputStream in) throws IOException {
		return in.readShort();
	}

	/**
	 * Destroys the socket and initialize it to null.
	 */
	protected void cleanSocket() {
		if (!isInitialized) {
			return;
		}
		//        System.out.println("close connection");
		if (socket != null) {
			try {
				socket.shutdownInput();
			} catch (Exception exc) {
			}

			try {
				socket.shutdownOutput();
			} catch (Exception exc) {
			}
		}
		if (out != null) {
			try {
				synchronized (out) {
					out.close();
				}
			} catch (Exception exc) {
			}
		}

		if (socket != null) {
			try {
				socket.close();
			} catch (Exception exc) {
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (Exception exc) {
			}
		}

		socket = null;
		in = null;
		if (out != null) {
			synchronized (out) {
				out = null;
			}
		}
	}

	/*
	 * In case of a peerResponseTimeout exception we let the communication client handle the
	 * logic of the peerResponseTimeout.
	 */
	private void handlePeerResponseTimeout() {
		getCommunicationClient().handlePeerResponseTimeout();
	}

	/**
	 * Extract the session id from the query.
	 * Return -1 if no session id was located.
	 *
	 * @param query
	 * @return The session id, or -1 if non was located in the query.
	 */
	protected int getSessionID(String query) {
		int indx = query.lastIndexOf(AbstractDebugParametersInitializer.DEBUG_SESSION_ID);
		if (indx < 0) {
			return -1;
		}
		indx += AbstractDebugParametersInitializer.DEBUG_SESSION_ID.length();
		query = query.substring(indx);
		indx = query.indexOf('&');
		if (indx > -1) {
			return Integer.parseInt(query.substring(0, indx));
		}
		return Integer.parseInt(query.trim());
	}

	/**
	 * Hook the debug session to the correct ILaunch that started it.
	 * In case there is no such launch, the user will have to fill in some of the details needed to start a debug
	 * session correctly.
	 *
	 * @param debugSessionStartedNotification
	 * @return True, if the debug session hook was successful; False, otherwise.
	 */
	protected boolean hookDebugSession(DebugSessionStartedNotification debugSessionStartedNotification) throws CoreException {
		String query = debugSessionStartedNotification.getQuery();
		int sessionID = getSessionID(query);
		if (sessionID == 0) {
			sessionID = getSessionID(debugSessionStartedNotification.getOptions());
		}
		// Get the launch, but keep it in the mapper for any other debug requests that are
		// related to the debug session id.
		// The launch is mapped until the launches are cleared.
		ILaunch launch = PHPSessionLaunchMapper.get(sessionID);
		if (launch == null) {
			// We cannot find a launch the we can associate to the given session id (if any)
			// Try to take the first launch that is terminated and has a 'Debug all Pages' attribute.
			ILaunch[] launchs = DebugPlugin.getDefault().getLaunchManager().getLaunches();
			for (ILaunch aLaunch : launchs) {
				String debugType = aLaunch.getAttribute(IPHPConstants.DEBUGGING_PAGES);
				if (aLaunch.isTerminated() && (IPHPConstants.DEBUGGING_ALL_PAGES.equals(debugType) || IPHPConstants.DEBUGGING_START_FROM.equals(debugType))) {
					launch = aLaunch;
					break;
				}
			}
		}
		if (launch != null) {
			// Remove any debug targets and processes that were terminated.
			IDebugTarget[] debugTargets = launch.getDebugTargets();
			IProcess[] processes = launch.getProcesses();
			for (IDebugTarget element : debugTargets) {
				if (element.isTerminated()) {
					launch.removeDebugTarget(element);
				} else {
					// Do not allow any other targets or processes when an active debug target exists
					return true;
				}
			}
			for (IProcess element : processes) {
				if (element.isTerminated()) {
					launch.removeProcess(element);
				}
			}

			if (Boolean.toString(true).equals(launch.getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER))) {
				hookServerDebug(launch, debugSessionStartedNotification);
			} else {
				hookPHPExeDebug(launch, debugSessionStartedNotification);
			}
			return true;
		}
		return handleHookError("No session id");
	}

	/**
	 * Handle a debug session hook error.
	 * This method can be subclassed for handling more complex causes.
	 * The default implementation is to display the toString() value of the cause and return false.
	 *
	 * @param cause An object that represents the cause for the error. Can be a String description or a different
	 * 				complex object that can supply more information.
	 * @return True, if the error was fixed in this method; False, otherwise.
	 */
	protected boolean handleHookError(Object cause) {
		if (cause != null) {
			Logger.log(Logger.ERROR, cause.toString());
		} else {
			Logger.log(Logger.ERROR, "Debug hook error");
		}
		return false;
	}

	/**
	 * Hook a server debug session
	 *
	 * @param launch An {@link ILaunch}
	 * @param startedNotification	A DebugSessionStartedNotification
	 */
	protected void hookServerDebug(final ILaunch launch, DebugSessionStartedNotification startedNotification) throws CoreException {
		ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
		String projectName = launchConfiguration.getAttribute(IPHPConstants.PHP_Project, (String) null);

		IProject project = null;
		if (projectName != null) {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		}

		inputManager.setTransferEncoding(launchConfiguration.getAttribute(IDebugParametersKeys.TRANSFER_ENCODING, ""));
		inputManager.setOutputEncoding(launchConfiguration.getAttribute(IDebugParametersKeys.OUTPUT_ENCODING, ""));
		String URL = launchConfiguration.getAttribute(Server.BASE_URL, "");

		boolean stopAtFirstLine = project == null ? true : PHPProjectPreferences.getStopAtFirstLine(project);
		int requestPort = PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
		boolean runWithDebug = launchConfiguration.getAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, true);
		if (launch.getLaunchMode().equals(ILaunchManager.DEBUG_MODE)) {
			runWithDebug = false;
		}
		PHPProcess process = new PHPProcess(launch, URL);
		debugTarget = (PHPDebugTarget) createDebugTraget(this, launch, URL, requestPort, process, runWithDebug, stopAtFirstLine, project);
		launch.addDebugTarget(debugTarget);
		// A fix for Linux display problem.
		// This code will auto-expand the debugger view tree.
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				LaunchManager manager = (LaunchManager) DebugPlugin.getDefault().getLaunchManager();
				manager.fireUpdate(new ILaunch[] { launch }, LaunchManager.ADDED);
				// Added to fix a problem while migrating to 3.3.
				// The stack tree was not updated.
				manager.fireUpdate(new ILaunch[] { launch }, LaunchManager.CHANGED);
			}
		});
	}

	/**
	 * Creates a new IDebugTarget.
	 * This create method is usually used when hooking a PHP web page launch.
	 *
	 * @throws CoreException
	 */
	protected IDebugTarget createDebugTraget(DebugConnectionThread thread, ILaunch launch, String url, int requestPort, PHPProcess process, boolean runWithDebug, boolean stopAtFirstLine, IProject project) throws CoreException {
		return new PHPDebugTarget(thread, launch, url, requestPort, process, runWithDebug, stopAtFirstLine, project);
	}

	/**
	 * Creates a new IDebugTarget.
	 * This create method is usually used when hooking a PHP executable launch.
	 *
	 * @throws CoreException
	 */
	protected IDebugTarget createDebugTarget(DebugConnectionThread thread, ILaunch launch, String phpExeString, String debugFileName, String workspaceRootPath, int requestPort, PHPProcess process, boolean runWithDebugInfo, boolean stopAtFirstLine, IProject project) throws CoreException {
		return new PHPDebugTarget(thread, launch, phpExeString, debugFileName, workspaceRootPath, requestPort, process, runWithDebugInfo, stopAtFirstLine, project);
	}

	/**
	 * Hook a PHP executable debug session
	 *
	 * @param launch An {@link ILaunch}
	 * @param startedNotification	A DebugSessionStartedNotification
	 */
	protected void hookPHPExeDebug(final ILaunch launch, DebugSessionStartedNotification startedNotification) throws CoreException {
		ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
		inputManager.setTransferEncoding(launchConfiguration.getAttribute(IDebugParametersKeys.TRANSFER_ENCODING, ""));
		inputManager.setOutputEncoding(launchConfiguration.getAttribute(IDebugParametersKeys.OUTPUT_ENCODING, ""));
		String phpExeString = launchConfiguration.getAttribute(PHPCoreConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		String fileNameString = launchConfiguration.getAttribute(PHPCoreConstants.ATTR_FILE, (String) null);
		boolean runWithDebugInfo = launchConfiguration.getAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, true);
		String projectString = launchConfiguration.getAttribute(PHPCoreConstants.ATTR_WORKING_DIRECTORY, (String) null);

		if (launch.getLaunchMode().equals(ILaunchManager.DEBUG_MODE)) {
			runWithDebugInfo = false;
		}

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project;
		String debugFileName;

		IPath filePath = new Path(fileNameString);
		IResource res = workspaceRoot.findMember(filePath);
		if (res != null) {
			IFile fileToDebug = (IFile) res;
			debugFileName = fileToDebug.getName();
			project = fileToDebug.getProject();
		} else if (projectString != null) {
			project = workspaceRoot.getProject(projectString);
			debugFileName = fileNameString;
		} else {
			project = ExternalFilesRegistry.getInstance().getExternalFilesProject();
			debugFileName = fileNameString;
		}
		String workspaceRootPath = PHPDebugTarget.getWorkspaceRootPath(project.getWorkspace());
		boolean stopAtFirstLine = PHPProjectPreferences.getStopAtFirstLine(project);
		int requestPort = PHPDebugPlugin.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);

		IPath phpExe = new Path(phpExeString);
		PHPProcess process = new PHPProcess(launch, phpExe.toOSString());

		debugTarget = (PHPDebugTarget) createDebugTarget(this, launch, phpExeString, debugFileName, workspaceRootPath, requestPort, process, runWithDebugInfo, stopAtFirstLine, project);
		launch.addDebugTarget(debugTarget);
		// A fix for Linux display problem.
		// This code will auto-expand the debugger view tree.
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				LaunchManager manager = (LaunchManager) DebugPlugin.getDefault().getLaunchManager();
				manager.fireUpdate(new ILaunch[] { launch }, LaunchManager.ADDED);
			}
		});
	}

	/**
	 * This thread handles the requests and notification that are inserted into the
	 * Queues by the InputManager thread.
	 */
	private class InputMessageHandler implements Runnable {

		private BlockingQueue inputMessageQueue = new BlockingQueue(100);

		private boolean shouldExit = false;
		private boolean isAlive = true;
		private boolean inWork = true;
		private Thread theThread;
		private DataOutputStream out;
		private Object STOP_MSG = new Object();

		private ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		private DataOutputStream outArray = new DataOutputStream(byteArray);

		private Object WAIT = new Object();
		private Object READY_FOR_RESTART_LOCK = new Object();

		public InputMessageHandler(DataOutputStream out) {
			this.out = out;
			isAlive = true;
			inWork = true;
			shouldExit = false;
			theThread = new Thread(this);
			// This makes the printing much faster.
			theThread.setPriority(1);//theThread.getPriority()+1);
			theThread.start();
		}

		/**
		 * start the thread on a new output.
		 * This will block until end of processing of all the messages.
		 */
		public synchronized void start(DataOutputStream out, boolean clearQueue) {
			if (clearQueue)
				inputMessageQueue.clear();
			this.out = out;
			ensureStarted();
		}

		/**
		 * This function will block the calling thread until the the InputMessageHandler
		 * becomes inactive.
		 */
		public void waitForStart(DataOutputStream out, boolean clearQueue) {
			if (inWork) {
				synchronized (READY_FOR_RESTART_LOCK) {
					try {
						READY_FOR_RESTART_LOCK.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			start(out, clearQueue);
		}

		/* just make sure the thread is working */
		public synchronized void ensureStarted() {
			synchronized (WAIT) {
				inWork = true;
				isAlive = true;
				shouldExit = false;
				WAIT.notifyAll();
			}
		}

		public synchronized void stopImmediately(boolean clearQueue) {
			if (!inWork)
				return;
			inWork = false;
			isAlive = true;
			inputMessageQueue.releaseReaders();
			if (clearQueue)
				inputMessageQueue.clear();
			synchronized (WAIT) {
				WAIT.notifyAll();
			}
		}

		public void terminate() {
			inWork = false;
			isAlive = false;
			validProtocol = false;
			inputMessageQueue.releaseReaders();
			inputMessageQueue.clear();
			theThread.interrupt();
		}

		public boolean isAlive() {
			return isAlive;
		}

		public void queueIn(IDebugMessage m) {
			inputMessageQueue.queueIn(m);
		}

		private void queueIn(Object m) {
			inputMessageQueue.queueIn(m);
		}

		/**
		 * This method is called by the input manager so that the message handler
		 * will queueIn an internal protocol message for the closure of connection.
		 */
		public synchronized void connectionClosed() {
			ensureStarted(); // make sure the thread will process the stop message
			queueIn(CONNECTION_CLOSED_MSG);
		}

		private synchronized void resetCommunication() {
			// Now we can stop the input manager.
			if (inputManager != null)
				inputManager.stop();

			// first stop the input messages handler. So if the inputMessagesHandler
			// is sending a request the inputManager will exists to read the response.
			stopImmediately(true);

			synchronized (requestsTable) {
				Iterator i = requestsTable.values().iterator();
				while (i.hasNext()) {
					IDebugRequestMessage r = (IDebugRequestMessage) i.next();
					synchronized (r) {
						r.notifyAll();
					}
				}
			}

			requestsTable.clear();
			responseTable.clear();
			responseHandlers.clear();
		}

		public void run() {

			while (isAlive) {

				while (!inWork && isAlive) {
					try {
						synchronized (WAIT) {
							synchronized (READY_FOR_RESTART_LOCK) {
								READY_FOR_RESTART_LOCK.notify(); // release one thread.
							}
							WAIT.wait();
						}

					} catch (InterruptedException e) {
					}
				}

				if (!isAlive)
					break;

				try {

					Object newInputMessage = inputMessageQueue.queueOut();

					//System.out.println("InputMessageHandler handle: " + newInputMessage);

					// do not stop until the message is processed.

					synchronized (this) {
						try {
							if (newInputMessage instanceof DebugSessionStartedNotification) {
								hookDebugSession((DebugSessionStartedNotification) newInputMessage);
								if (getCommunicationClient() != null) {
									getCommunicationClient().handleNotification(newInputMessage);
								} else {
									handleConnectionClosed();
								}
							} else if (newInputMessage instanceof IDebugNotificationMessage) {
								//System.out.println("Processing notification:"+ newInputMessage);
								getCommunicationClient().handleNotification(newInputMessage);
							} else if (newInputMessage instanceof IDebugRequestMessage) {

								//								int reqId = ((IDebugRequestMessage) newInputMessage).getID();
								IDebugMessageHandler requestHandler = DebugMessagesRegistry.getHandler((IDebugRequestMessage) newInputMessage);

								if (requestHandler instanceof IDebugRequestHandler) {
									requestHandler.handle((IDebugRequestMessage) newInputMessage, debugTarget);
									IDebugResponseMessage response = ((IDebugRequestHandler) requestHandler).getResponseMessage();

									//Log.writeLog("Client Sending response: " +response);
									//ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
									//DataOutputStream outArray = new DataOutputStream (byteArray );
									byteArray.reset();
									response.serialize(outArray);
									if (isDebugMode) {
										System.out.println("sending message size=" + byteArray.size());
									}
									synchronized (out) {
										out.writeInt(byteArray.size());
										byteArray.writeTo(out);
										out.flush();
									}
								} else {
									// error, we could not find the relevant request message handler for the received message.
								}
							} else if (newInputMessage instanceof IDebugResponseMessage) {
								IDebugResponseMessage r = (IDebugResponseMessage) newInputMessage;
								//                                Object requestId = new Integer(r.getID()); // take the req id from the response.
								int requestId = r.getID(); // take the req id from the response.
								IDebugRequestMessage req = (IDebugRequestMessage) requestsTable.remove(requestId); // find the request.
								ResponseHandler handler = (ResponseHandler) responseHandlers.remove(new Integer(requestId)); // find the handler.
								handler.handleResponse(req, r);
							} else if (newInputMessage == STOP_MSG) {
								synchronized (STOP_MSG) {
									inWork = false;
									STOP_MSG.notifyAll();
									if (shouldExit) {
										isAlive = false;
										inputMessageQueue.releaseReaders(); // why do we need this??
										//notifyAll();
									}
								}
							} else if (newInputMessage == CONNECTION_CLOSED_MSG) {
								handleConnectionClosed();
								//inWork = false;
							}
							//else if (newInputMessage == MULTIPLE_BINDINGS_MSG){
							//    handleMultipleBindingsMessage();
							//}
							//else if (newInputMessage == PEER_RESPONSE_TIMEOUT_MSG){
							//    handlePeerResponseTimeoutMessage();
							//}

						} catch (Exception exc) { // error processing the current message.
							PHPDebugPlugin.log(exc);
						}
					}

				} catch (Exception exc) {
					//inWork = false;
					PHPDebugPlugin.log(exc);
				}
			}
		}

		private void handleConnectionClosed() {
			resetCommunication();
			if (getCommunicationAdministrator() != null) {
				getCommunicationAdministrator().connectionClosed();
			}
			terminate();
			closeConnection();
		}
	}

	public String toString() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className + "@" + Integer.toHexString(hashCode());
	}

	/**
	 * This thread manages the Communication initiated by the peer.
	 * All the messages that arrive form the peer are read by the ImputManager.
	 * The InputManager will then handle the message by the message type.
	 * It the message type is a {@link IDebugRequestMessage}. The input manager will also write out
	 * the {@link IDebugResponseMessage}
	 */
	private class InputManager implements Runnable {

		private DataInputStream in;

		private boolean inWork = false;
		private boolean isAlive = true;
		private Thread theThread;
		private Object READY_FOR_RESTART_LOCK = new Object();
		private String transferEncoding;
		private String outputEncoding;

		/**
		 * Sets the transfer encoding.
		 *
		 * @param transferEncoding
		 */
		public void setTransferEncoding(String transferEncoding) {
			this.transferEncoding = transferEncoding;
		}

		/**
		 * Set the debug output encoding.
		 * The output encoding effects the {@link OutputNotification} strings encoding.
		 *
		 * @param outputEncoding
		 */
		public void setOutputEncoding(String outputEncoding) {
			this.outputEncoding = outputEncoding;
		}

		/**
		 * Create an InputManager in a separate thread.
		 */
		InputManager(DataInputStream in) {
			this.in = in;
			inWork = true;
			isAlive = true;
			theThread = new Thread(this);
			theThread.start();
			//Log.writeLog("Input Manager is started");
		}

		/*
		 * This method will block the reader until the end of processing of the current
		 * message.
		 */
		public synchronized void start(DataInputStream in) {
			stop();
			this.in = in;
			inWork = true;

			// on start we do not need to interrupt since we are sure
			// the thread is in wait on this.
			notifyAll();
		}

		public boolean isAlive() {
			return isAlive;
		}

		public void waitForStart(DataInputStream in) {
			if (inWork) {
				synchronized (READY_FOR_RESTART_LOCK) {
					try {
						READY_FOR_RESTART_LOCK.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			start(in);
		}

		/**
		 * Stop this thread from reading from the socket without killing it.
		 * This method will block the reader until the end of processing of the current
		 * message.
		 */
		public synchronized void stop() {
			if (!inWork) {
				return;
			}
			inWork = false;
			isAlive = true;
			theThread.interrupt();
		}

		/**
		 * Terminate this thread
		 */
		public synchronized void terminate() {
			inWork = false;
			isAlive = false;
			theThread.interrupt();
		}

		/**
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			while (isAlive) {

				while (!inWork && isAlive) {
					try {
						synchronized (this) {
							synchronized (READY_FOR_RESTART_LOCK) {
								READY_FOR_RESTART_LOCK.notify(); // release one thread.

							}
							wait();
							// This way we will start working until the connectionCreator
							// has finished updating the in + out.
							synchronized (in) {
							}
						}
					} catch (InterruptedException e) {
						if (isDebugMode) {
							System.out.println("interrupted: inWork = " + inWork + ", isAlive = " + isAlive);
						}
					}
				}

				try {

					if (!isAlive) {
						break;
					}

					// reads the length
					int num = in.readInt();
					if (isDebugMode) {
						System.out.println("recieved message size = " + num);
					}
					if (num < 0) {
						shutDown();
						if (isDebugMode) {
							System.out.println("Socket error (length is negative): possibly Server is SSL, Client is not.");
						}
						Logger.log(Logger.ERROR, "Socket error (length is negative): possibly Server is SSL, Client is not.");
					}
					// We have a new message. process it !!.
					// This part is synchronized since we do not want the thread to be stopped
					// when in processing of a message.
					synchronized (this) {
						int messageType = getMessageType(in);
						// If this is the first message, the protocol is still held as invalid.
						// Check that the first message has the DebugSessionStartedNotification type. If not, then we
						// can assume that the remote debugger protocol has a different version then expected.
						if (!validProtocol && messageType != startMessageId) {
							// display an error message that the protocol in used is wrong.
							final String errorMessage = MessageFormat.format(PHPDebugCoreMessages.Debugger_Incompatible_Protocol, new String[] { String.valueOf(RemoteDebugger.PROTOCOL_ID) });
							Status status = new Status(IStatus.ERROR, PHPDebugPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null);
							DebugPlugin.log(status);
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									MessageDialog.openError(Display.getDefault().getActiveShell(), "Debugger Error", errorMessage);
								}
							});
							shutDown();
							return;
						}
						validProtocol = true;
						if (isDebugMode) {
							System.out.println("message type=" + messageType);
						}

						IDebugMessage message = DebugMessagesRegistry.getMessage(messageType);
						if (message != null) {
							if (message instanceof OutputNotification) {
								message.setTransferEncoding(outputEncoding);
							} else {
								message.setTransferEncoding(transferEncoding);
							}
						}

						// handle the message
						if (message instanceof IDebugNotificationMessage) {
							if (isDebugMode) {
								System.out.println("Starting to read notification ");
							}
							message.deserialize(in);
							if (isDebugMode) {
								System.out.println("End reading of notification " + message);
							}
							//getCommunicationClient().handleNotification((Notification)message);
							//PUT NOTIFICATION TO NOTIFICATION QUEUE
							inputMessageHandler.queueIn(message);
						} else if (message instanceof IDebugResponseMessage) {
							if (isDebugMode) {
								System.out.println("Starting to read response");
							}
							message.deserialize(in);
							int idd = ((IDebugResponseMessage) message).getID();
							if (isDebugMode) {
								System.out.println("End reading of response " + message);
							}
							//responseQueue.queueIn(message);
							//INSERT RESPONSE TO TABLE AND RELEASE THE THREAD WAITING FOR THE REQUEST
							ResponseHandler handler = (ResponseHandler) responseHandlers.get(new Integer(idd)); // find the handler.
							if (handler == null) {
								responseTable.put(/*requestId*/idd, message);
								IDebugRequestMessage req = (IDebugRequestMessage) requestsTable.remove(idd); // find the request.
								if (req != null) {
									synchronized (req) {
										req.notifyAll(); // Notify the response is here.
									}
								} else {
									// Remove this message.
									responseTable.remove(idd);
								}
							} else {
								inputMessageHandler.queueIn(message);
							}
						} else if (message instanceof IDebugRequestMessage) { // this is a request.
							if (isDebugMode) {
								System.out.println("Starting to read request");
							}
							message.deserialize(in);
							if (isDebugMode) {
								System.out.println("End reading of request " + message);
							}
							//Response response =  getCommunicationClient().handleRequest((Request)message);
							inputMessageHandler.queueIn(message);
						}
					} // end of synchronized part.

				} catch (EOFException exc) {
					shutDown();
				} catch (SocketException exc) {
					PHPLaunchUtilities.showDebuggerErrorMessage(PHPDebugCoreMessages.Debugger_General_Error, "Connection reset");
					shutDown();
				} catch (IOException exc) {
					PHPDebugPlugin.log(exc);
					shutDown();
				} catch (Exception exc) {
					PHPDebugPlugin.log(exc);
				}
			}
		}

		private void shutDown() {
			terminate();
			cleanSocket();
			inputMessageHandler.connectionClosed();
		}
	}
}
