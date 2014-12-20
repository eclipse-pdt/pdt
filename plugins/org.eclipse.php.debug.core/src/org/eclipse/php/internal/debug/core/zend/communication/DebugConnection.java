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
package org.eclipse.php.internal.debug.core.zend.communication;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugNotificationMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.util.BlockingQueue;
import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugMessagesRegistry;
import org.eclipse.php.internal.debug.core.zend.debugger.PHPSessionLaunchMapper;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.*;
import org.eclipse.php.internal.debug.core.zend.debugger.parameters.AbstractDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestController;
import org.eclipse.php.internal.debug.core.zend.testConnection.DebugServerTestEvent;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.swt.widgets.Display;

import com.ibm.icu.text.MessageFormat;

/**
 * The debug connection is responsible of initializing and handle a single debug
 * session that was triggered by a remote or local debugger.
 * 
 * @author shalom
 */
@SuppressWarnings("restriction")
public class DebugConnection {

	/**
	 * This job handles the requests and notification that are inserted into the
	 * queues by the MessageReceiver job.
	 */
	private class MessageHandler extends Job {

		private BlockingQueue inputMessageQueue = new BlockingQueue(100);
		private ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		private DataOutputStream outArray = new DataOutputStream(byteArray);

		public MessageHandler() {
			super("Debug Message Handler"); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
			setPriority(LONG);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
		 * IProgressMonitor)
		 */
		public IStatus run(IProgressMonitor monitor) {
			// 'while(true)' is OK here since we have blocking queue
			while (true) {
				if (monitor.isCanceled())
					return Status.OK_STATUS;
				try {
					IDebugMessage incomingMessage = (IDebugMessage) inputMessageQueue
							.queueOut();
					Logger.debugMSG("NEW MESSAGE RECEIVED: " + incomingMessage); //$NON-NLS-1$
					try {
						boolean isDebugConnectionTest = false;
						// First debug message has arrived (SESSION STARTED)
						if (incomingMessage instanceof DebugSessionStartedNotification) {
							DebugSessionStartedNotification sessionStartedMessage = (DebugSessionStartedNotification) incomingMessage;
							isDebugConnectionTest = sessionStartedMessage
									.getQuery().indexOf("testConnection=true") != -1; //$NON-NLS-1$
							// This is a connection test...
							if (isDebugConnectionTest) {
								String sourceHost = DebugConnection.this.socket
										.getInetAddress().getHostAddress();
								// Notify success
								if (verifyProtocolID(sessionStartedMessage
										.getServerProtocolID())) {
									sendRequest(new StartRequest());
									DebugServerTestController
											.getInstance()
											.notifyTestListener(
													new DebugServerTestEvent(
															sourceHost,
															DebugServerTestEvent.TEST_SUCCEEDED));
								} else {
									DebugServerTestController
											.getInstance()
											.notifyTestListener(
													new DebugServerTestEvent(
															sourceHost,
															DebugServerTestEvent.TEST_FAILED_DEBUGER_VERSION));
								}
							}
							// START DEBUG (create debug target)
							else {
								hookDebugSession((DebugSessionStartedNotification) incomingMessage);
							}
						}
						// Creation of debug session has succeeded
						if (debugTarget != null) {
							// Try to find relevant handler for the message:
							IDebugMessageHandler messageHandler = createMessageHandler(incomingMessage);
							if (messageHandler != null) {
								Logger.debugMSG("CREATING MESSAGE HANDLER: " //$NON-NLS-1$
										+ messageHandler.getClass().getName()
												.replaceFirst(".*\\.", "")); //$NON-NLS-1$ //$NON-NLS-2$
								// Handle the request
								messageHandler.handle(incomingMessage,
										debugTarget);
								if (messageHandler instanceof IDebugRequestHandler) {
									// Create response
									IDebugResponseMessage response = ((IDebugRequestHandler) messageHandler)
											.getResponseMessage();
									// Send response
									synchronized (connectionOut) {
										byteArray.reset();
										response.serialize(outArray);
										connectionOut
												.writeInt(byteArray.size());
										byteArray.writeTo(connectionOut);
										connectionOut.flush();
									}
								}
							}
							// Handle the response
							else if (incomingMessage instanceof IDebugResponseMessage) {
								IDebugResponseMessage r = (IDebugResponseMessage) incomingMessage;
								// Take the request ID from the response.
								int requestId = r.getID();
								// Find the request.
								IDebugRequestMessage req = (IDebugRequestMessage) requestsTable
										.remove(requestId);
								// Find the handler.
								ResponseHandler handler = responseHandlers
										.remove(Integer.valueOf(requestId));
								handler.handleResponse(req, r);
							}
							// Handle dummy connection close
							else if (incomingMessage == CONNECTION_CLOSED) {
								handleClosed();
							}
						}
						// No debug target?
						else {
							handleClosed();
						}
					}
					// Error processing the current message.
					catch (Exception e) {
						PHPDebugPlugin.log(e);
					}
				} catch (Exception e) {
					PHPDebugPlugin.log(e);
				}
			}
		}

		public void queueIn(IDebugMessage m) {
			inputMessageQueue.queueIn(m);
		}

		void shutdown() {
			cancel();
			inputMessageQueue.releaseReaders();
			inputMessageQueue.clear();
		}

		/**
		 * This method is called by the message receiver so that the message
		 * handler will queueIn an internal protocol message for the closure of
		 * connection.
		 */
		void connectionClosed() {
			queueIn(CONNECTION_CLOSED);
		}

		private void handleClosed() {
			if (getCommunicationAdministrator() != null) {
				getCommunicationAdministrator().connectionClosed();
			}
			shutdown();
			// Have to be here as well as in message receiver
			DebugConnection.this.terminate();
		}

	}

	/**
	 * This job manages the communication initiated by the peer. All the
	 * messages that arrive from the peer are read by the MessageReceiver that
	 * will then handle the message by the message type.
	 */
	private class MessageReceiver extends Job {

		private String transferEncoding;
		private String outputEncoding;

		/**
		 * Create an InputManager in a separate thread.
		 */
		public MessageReceiver() {
			super("Debug Message Receiver"); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
			setPriority(LONG);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
		 * IProgressMonitor)
		 */
		public IStatus run(IProgressMonitor monitor) {
			// 'while(true)' is OK here since we use blocking read
			while (true) {
				try {
					if (monitor.isCanceled())
						return Status.OK_STATUS;
					// Reads the length
					int length = connectionIn.readInt();
					if (length < 0) {
						String message = "Socket error (length is negative): possibly Server is SSL, Client is not."; //$NON-NLS-1$
						Logger.debugMSG(message);
						Logger.log(Logger.ERROR, message);
						shutdown();
						continue;
					}
					// We have a new message. process it !!.
					int messageType = connectionIn.readShort();
					/*
					 * If this is the first message, the protocol is still held
					 * as invalid. Check that the first message has the
					 * DebugSessionStartedNotification type. If not, then we can
					 * assume that the remote debugger protocol has a different
					 * version then expected.
					 */
					if (!isValidProtocol && messageType != START_MESSAGE_ID) {
						showProtocolError();
						shutdown();
						continue;
					}
					isValidProtocol = true;
					// Create message with the use of registry
					IDebugMessage message = DebugMessagesRegistry
							.getMessage(messageType);
					if (message != null) {
						if (message instanceof OutputNotification) {
							message.setTransferEncoding(outputEncoding);
						} else {
							message.setTransferEncoding(transferEncoding);
						}
					}
					// Handle the incoming message
					if (message instanceof IDebugNotificationMessage) {
						message.deserialize(connectionIn);
						// PUT NOTIFICATION TO NOTIFICATION QUEUE
						messageHandler.queueIn(message);
					} else if (message instanceof IDebugResponseMessage) {
						message.deserialize(connectionIn);
						int messageId = ((IDebugResponseMessage) message)
								.getID();
						/*
						 * INSERT RESPONSE TO TABLE AND RELEASE THE THREAD
						 * WAITING FOR THE REQUEST
						 */
						// Find the handler.
						ResponseHandler handler = responseHandlers.get(Integer
								.valueOf(messageId));
						if (handler == null) {
							responseTable.put(/* requestId */messageId, message);
							// Find the request.
							IDebugRequestMessage request = (IDebugRequestMessage) requestsTable
									.remove(messageId);
							if (request != null) {
								// Notify the RESPONSE is here.
								synchronized (request) {
									request.notifyAll();
								}
							} else {
								// Remove this message.
								responseTable.remove(messageId);
							}
						} else {
							messageHandler.queueIn(message);
						}
					}
					// This is a request.
					else if (message instanceof IDebugRequestMessage) {
						message.deserialize(connectionIn);
						messageHandler.queueIn(message);
					}
				} catch (IOException e) {
					// Probably, the connection was dumped
					shutdown();
					continue;
				} catch (Exception e) {
					PHPDebugPlugin.log(e);
				}
			}
		}

		/**
		 * Sets the transfer encoding.
		 * 
		 * @param transferEncoding
		 */
		public void setTransferEncoding(String transferEncoding) {
			this.transferEncoding = transferEncoding;
		}

		/**
		 * Set the debug output encoding. The output encoding effects the
		 * {@link OutputNotification} strings encoding.
		 * 
		 * @param outputEncoding
		 */
		public void setOutputEncoding(String outputEncoding) {
			this.outputEncoding = outputEncoding;
		}

		void shutdown() {
			cancel();
			// Shutdown message handler
			messageHandler.connectionClosed();
			// Have to be here as well as in message handler
			DebugConnection.this.terminate();
		}

		private void showProtocolError() {
			final String errorMessage = MessageFormat.format(
					PHPDebugCoreMessages.Debugger_Incompatible_Protocol,
					new Object[] { String
							.valueOf(RemoteDebugger.PROTOCOL_ID_LATEST) });
			Status status = new Status(IStatus.ERROR, PHPDebugPlugin.getID(),
					IPHPDebugConstants.INTERNAL_ERROR, errorMessage, null);
			DebugPlugin.log(status);
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(Display.getDefault()
							.getActiveShell(), "Debugger Error", errorMessage); //$NON-NLS-1$
				}
			});
		}

	}

	protected class SessionDescriptor {

		private int id;
		private int ordinal;
		private DebugSessionStartedNotification startedNotification;

		public SessionDescriptor(
				DebugSessionStartedNotification startedNotification) {
			this.startedNotification = startedNotification;
			this.id = -1;
			this.ordinal = -1;
			build();
		}

		public DebugSessionStartedNotification getStartedNotification() {
			return startedNotification;
		}

		private void build() {
			String params;
			if (startedNotification.getQuery().contains(
					AbstractDebugParametersInitializer.DEBUG_SESSION_ID))
				params = startedNotification.getQuery();
			else
				params = startedNotification.getOptions();
			List<String> parameters = Arrays.asList(params.split("&")); //$NON-NLS-1$
			Iterator<String> i = parameters.iterator();
			while (i.hasNext()) {
				String parameter = i.next();
				if (parameter
						.startsWith(AbstractDebugParametersInitializer.DEBUG_SESSION_ID)) {
					int idx = parameter.indexOf('=');
					Integer parsedId = parseInt(parameter.substring(idx + 1));
					if (parsedId != null)
						id = parsedId;
					if (i.hasNext()) {
						Integer parsedOrdinal = parseInt(i.next());
						if (parsedOrdinal != null)
							ordinal = parsedOrdinal;
					}
				}
			}
		}

		private Integer parseInt(String number) {
			try {
				return Integer.parseInt(number);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		public int getId() {
			return id;
		}

		public int getOrdinal() {
			return ordinal;
		}

		public boolean isPrimary() {
			return getOrdinal() < 0;
		}

		public boolean isUnknown() {
			return getId() < 0 && getOrdinal() < 0;
		}

	}

	private static final Lock HOOK_LOCK = new ReentrantLock(true);
	private static final long HOOK_TIMEOUT = 10000;

	// Phantom message used to notify that connection was closed
	private final IDebugMessage CONNECTION_CLOSED = new DebugMessageImpl() {

		public void deserialize(DataInputStream in) throws IOException {
		}

		public int getType() {
			return 0;
		}

		public void serialize(DataOutputStream out) throws IOException {
		}

	};
	protected static final int START_MESSAGE_ID = (new DebugSessionStartedNotification())
			.getType();
	protected int debugResponseTimeout;
	protected PHPDebugTarget debugTarget;
	protected boolean isValidProtocol;
	private Socket socket;
	private DataInputStream connectionIn;
	private DataOutputStream connectionOut;
	private boolean isInitialized;
	private MessageReceiver messageReceiver;
	private MessageHandler messageHandler;
	private CommunicationClient communicationClient;
	private CommunicationAdministrator communicationAdministrator;
	private IntHashtable requestsTable;
	private IntHashtable responseTable;
	private Hashtable<Integer, ResponseHandler> responseHandlers;
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	private DataOutputStream dataOutputStream = new DataOutputStream(
			byteArrayOutputStream);
	private int lastRequestID = 1000;
	private Map<Integer, IDebugMessageHandler> messageHandlers;
	private boolean isConnected = true;

	/**
	 * Constructs a new DebugConnectionThread with a given Socket.
	 * 
	 * @param socket
	 */
	public DebugConnection(Socket socket) {
		debugResponseTimeout = Platform.getPreferencesService()
				.getInt(PHPDebugPlugin.ID,
						PHPDebugCorePreferenceNames.DEBUG_RESPONSE_TIMEOUT,
						10000, null);
		this.socket = socket;
		connect();
	}

	/**
	 * Closes the connection. Causes message receiver & handler to be shutdown.
	 */
	public synchronized void disconnect() {
		messageReceiver.shutdown();
	}

	/**
	 * Returns true if the connection is alive.
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * Returns communication client
	 * 
	 * @return communication client
	 */
	public CommunicationClient getCommunicationClient() {
		return communicationClient;
	}

	/**
	 * Returns communication administrator
	 * 
	 * @return communication administrator
	 */
	public CommunicationAdministrator getCommunicationAdministrator() {
		return communicationAdministrator;
	}

	/**
	 * Sends a notification.
	 * 
	 * @param msg
	 *            The delivered notification message.
	 */
	public void sendNotification(Object msg) {
		if (!isConnected)
			// Skip if already disconnected
			return;
		try {
			synchronized (connectionOut) {
				byteArrayOutputStream.reset();
				((IDebugMessage) msg).serialize(dataOutputStream);
				connectionOut.writeInt(byteArrayOutputStream.size());
				byteArrayOutputStream.writeTo(connectionOut);
				connectionOut.flush();
			}
		} catch (SocketException se) {
			// Probably because the remote host disconnected.
			// Just log a warning (might be removed in the near future).
			if (PHPDebugPlugin.DEBUG) {
				Logger.log(Logger.WARNING, se.getMessage(), se);
			}
		} catch (Exception exc) {
			PHPDebugPlugin.log(exc);
		}
	}

	/**
	 * Send a synchronous request & wait for a response
	 * 
	 * @param request
	 *            The delivered Request message.
	 * @return A response for the delivered request.
	 */
	public Object sendRequest(Object request) throws Exception {
		if (!isConnected)
			// Skip if already disconnected
			return null;
		Logger.debugMSG("SENDING SYNCHRONOUS REQUEST: " + request); //$NON-NLS-1$
		try {
			IDebugRequestMessage theMsg = (IDebugRequestMessage) request;
			synchronized (connectionOut) {
				byteArrayOutputStream.reset();
				theMsg.setID(lastRequestID++);
				theMsg.serialize(dataOutputStream);
				int messageSize = byteArrayOutputStream.size();
				requestsTable.put(theMsg.getID(), theMsg);
				connectionOut.writeInt(messageSize);
				byteArrayOutputStream.writeTo(connectionOut);
				connectionOut.flush();
			}
			IDebugResponseMessage response = null;
			int timeoutTick = 500; // 0.5 of second
			int waitedTime = 0;
			while (response == null && isConnected()) {
				synchronized (request) {
					response = (IDebugResponseMessage) responseTable
							.remove(theMsg.getID());
					if (response == null) {
						/*
						 * Display a progress dialog after a quarter of the
						 * assigned time have passed.
						 */
						if (waitedTime > debugResponseTimeout / 4) {
							/*
							 * Display a message that we are waiting for the
							 * server response. In case that the response
							 * finally arrives, remove the message. In case we
							 * have a timeout, close the connection and display
							 * a different message.
							 */
							PHPLaunchUtilities.showWaitForDebuggerMessage(this);
						}
						// Wait for notify from MessageReceiver
						request.wait(timeoutTick);
					}
				}
				if (response == null) {
					response = (IDebugResponseMessage) responseTable
							.remove(theMsg.getID());
				}
				/*
				 * if the response is null. it means that there is no answer
				 * from the server. This can be because on the
				 * peerResponseTimeout.
				 */
				if (response == null && isConnected()) {
					Logger.debugMSG("COMMUNICATION PROBLEMS (response is null)"); //$NON-NLS-1$
					// Handle time out will stop the communication if needed.
					if (waitedTime < debugResponseTimeout - timeoutTick) {
						waitedTime += timeoutTick;
						handlePeerResponseTimeout();
					} else {
						disconnect();
						PHPLaunchUtilities.hideWaitForDebuggerMessage();
						PHPLaunchUtilities.showLaunchErrorMessage();
					}
					if (!isConnected())
						break;
				}
			}
			PHPLaunchUtilities.hideWaitForDebuggerMessage();
			Logger.debugMSG("RECEIVED RESPONSE: " + response); //$NON-NLS-1$
			return response;
		} catch (IOException e) { // Return null for any exception
			PHPDebugPlugin.log(e);
		} catch (InterruptedException e) {// Return null for any exception
			PHPDebugPlugin.log(e);
		}
		return null;
	}

	/**
	 * Send an asynchronous request.
	 * 
	 * @param request
	 * @param responseHandler
	 */
	public void sendRequest(Object request, ResponseHandler responseHandler) {
		if (!isConnected)
			// Skip if already disconnected
			return;
		Logger.debugMSG("SENDING ASYNCHRONOUS REQUEST: " + request); //$NON-NLS-1$
		int msgId = lastRequestID++;
		IDebugRequestMessage theMsg = (IDebugRequestMessage) request;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					byteArrayOutputStream);
			theMsg.setID(msgId);
			theMsg.serialize(dataOutputStream);
			int messageSize = byteArrayOutputStream.size();
			synchronized (connectionOut) {
				requestsTable.put(msgId, request);
				responseHandlers.put(Integer.valueOf(msgId), responseHandler);
				connectionOut.writeInt(messageSize);
				byteArrayOutputStream.writeTo(connectionOut);
				connectionOut.flush();
			}
		} catch (Exception e) {
			// Return null for any exception
			String message = "Exception for request NO." + theMsg.getType() + e.toString(); //$NON-NLS-1$
			Logger.debugMSG(message);
			Logger.logException(e);
			responseHandler.handleResponse(request, null);
			responseHandlers.remove(Integer.valueOf(msgId));
		}
	}

	/**
	 * Sets communication administrator.
	 * 
	 * @param admin
	 */
	public void setCommunicationAdministrator(CommunicationAdministrator admin) {
		communicationAdministrator = admin;
	}

	/**
	 * Sets communication client.
	 * 
	 * @param client
	 */
	public void setCommunicationClient(CommunicationClient client) {
		this.communicationClient = client;
	}

	protected boolean hookLaunch(SessionDescriptor sessionDescriptor)
			throws CoreException {
		// Try to hook any of the existing launches
		ILaunch launch = PHPSessionLaunchMapper.get(sessionDescriptor.getId());
		/*
		 * There are no existing launches (created by user nor "mock" ones) for
		 * incoming session ID, try to find/create new "mock" launch
		 */
		if (launch == null)
			launch = fetchLaunch(sessionDescriptor);
		/*
		 * If session is primary (new one has come) and launch exists then it
		 * means that session has been restarted. If so, terminate the previous
		 * launch.
		 */
		else if (sessionDescriptor.isPrimary() && !launch.isTerminated())
			try {
				launch.terminate();
			} catch (DebugException e) {
				// ignore
			}
		// Move on with the launch
		if (launch != null) {
			// Remove terminated elements if any
			cleanup(launch);
			// Hook by launch type
			if (isServerLaunch(launch)) {
				hookServerLaunch(launch, sessionDescriptor);
			} else {
				hookPHPExeLaunch(launch, sessionDescriptor);
			}
			return true;
		}
		return false;
	}

	/**
	 * Hook a server debug session
	 * 
	 * @param launch
	 *            An {@link ILaunch}
	 * @param startedNotification
	 *            A DebugSessionStartedNotification
	 */
	protected void hookServerLaunch(final ILaunch launch,
			SessionDescriptor sessionDescriptor) throws CoreException {
		ILaunchConfiguration launchConfiguration = launch
				.getLaunchConfiguration();
		IProject project = getProject(launchConfiguration);
		messageReceiver.setTransferEncoding(launchConfiguration.getAttribute(
				IDebugParametersKeys.TRANSFER_ENCODING, "")); //$NON-NLS-1$
		messageReceiver.setOutputEncoding(launchConfiguration.getAttribute(
				IDebugParametersKeys.OUTPUT_ENCODING, "")); //$NON-NLS-1$
		String URL = launchConfiguration.getAttribute(Server.BASE_URL, ""); //$NON-NLS-1$
		boolean stopAtFirstLine = project == null ? true
				: PHPProjectPreferences.getStopAtFirstLine(project);
		int requestPort = PHPDebugPlugin
				.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
		boolean runWithDebug = launchConfiguration.getAttribute(
				IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
		if (launch.getLaunchMode().equals(ILaunchManager.DEBUG_MODE)) {
			runWithDebug = false;
		}
		PHPProcess process = new PHPProcess(launch, URL);
		debugTarget = (PHPDebugTarget) createDebugTarget(this, launch, URL,
				requestPort, process, runWithDebug, stopAtFirstLine, project);
		// Bind debug target to the launch
		bindTarget(launch);
	}

	/**
	 * Hook a PHP executable debug session
	 * 
	 * @param launch
	 *            An {@link ILaunch}
	 * @param startedNotification
	 *            A DebugSessionStartedNotification
	 */
	protected void hookPHPExeLaunch(final ILaunch launch,
			SessionDescriptor sessionDescriptor) throws CoreException {
		ILaunchConfiguration launchConfiguration = launch
				.getLaunchConfiguration();
		messageReceiver.setTransferEncoding(launchConfiguration.getAttribute(
				IDebugParametersKeys.TRANSFER_ENCODING, "")); //$NON-NLS-1$
		messageReceiver.setOutputEncoding(launchConfiguration.getAttribute(
				IDebugParametersKeys.OUTPUT_ENCODING, "")); //$NON-NLS-1$
		String phpExeString = launchConfiguration.getAttribute(
				IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		String fileNameString = launchConfiguration.getAttribute(
				IPHPDebugConstants.ATTR_FILE, (String) null);
		boolean runWithDebugInfo = launchConfiguration.getAttribute(
				IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = null;
		String file = launchConfiguration.getAttribute(
				IPHPDebugConstants.ATTR_FILE, (String) null);
		if (file != null) {
			IResource resource = workspaceRoot.findMember(file);
			if (resource != null) {
				project = resource.getProject();
			}
		}
		if (launch.getLaunchMode().equals(ILaunchManager.DEBUG_MODE)) {
			runWithDebugInfo = false;
		}
		String debugFileName = fileNameString;
		IPath filePath = new Path(fileNameString);
		IResource res = workspaceRoot.findMember(filePath);
		if (res != null) {
			IFile fileToDebug = (IFile) res;
			debugFileName = fileToDebug.getName();
		}
		boolean stopAtFirstLine = PHPProjectPreferences
				.getStopAtFirstLine(project);
		int requestPort = PHPDebugPlugin
				.getDebugPort(DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID);
		IPath phpExe = new Path(phpExeString);
		PHPProcess process = new PHPProcess(launch, phpExe.toOSString());
		debugTarget = (PHPDebugTarget) createDebugTarget(this, launch,
				phpExeString, debugFileName, requestPort, process,
				runWithDebugInfo, stopAtFirstLine, project);
		// Bind debug target to the launch
		bindTarget(launch);
	}

	/**
	 * Handle a debug session hook error. This method can be subclassed for
	 * handling more complex causes. The default implementation is to display
	 * the toString() value of the cause and return false.
	 * 
	 * @param cause
	 *            An object that represents the cause for the error. Can be a
	 *            String description or a different complex object that can
	 *            supply more information.
	 * @return True, if the error was fixed in this method; False, otherwise.
	 */
	protected void hookError(Object cause) {
		if (cause != null) {
			Logger.log(Logger.ERROR, cause.toString());
		} else {
			Logger.log(Logger.ERROR, "Debug hook error"); //$NON-NLS-1$
		}
	}

	protected ILaunch fetchLaunch(SessionDescriptor sessionDescriptor)
			throws CoreException {
		// TODO - this one should be done better in the future...
		/*
		 * We cannot find a launch the we can associate to the given session id
		 * (if any) Try to take the first launch that is terminated and has a
		 * 'Debug all Pages' attribute.
		 */
		ILaunch[] launchs = DebugPlugin.getDefault().getLaunchManager()
				.getLaunches();
		for (ILaunch aLaunch : launchs) {
			String debugType = aLaunch
					.getAttribute(IPHPDebugConstants.DEBUGGING_PAGES);
			if (aLaunch.isTerminated()
					&& (IPHPDebugConstants.DEBUGGING_ALL_PAGES
							.equals(debugType) || IPHPDebugConstants.DEBUGGING_START_FROM
							.equals(debugType))) {
				return aLaunch;
			}
		}
		return null;
	}

	/**
	 * Creates a new IDebugTarget. This create method is usually used when
	 * hooking a PHP web page launch.
	 * 
	 * @throws CoreException
	 */
	protected IDebugTarget createDebugTarget(DebugConnection thread,
			ILaunch launch, String url, int requestPort, PHPProcess process,
			boolean runWithDebug, boolean stopAtFirstLine, IProject project)
			throws CoreException {
		return new PHPDebugTarget(thread, launch, url, requestPort, process,
				runWithDebug, stopAtFirstLine, project);
	}

	/**
	 * Creates a new IDebugTarget. This create method is usually used when
	 * hooking a PHP executable launch.
	 * 
	 * @throws CoreException
	 */
	protected IDebugTarget createDebugTarget(DebugConnection thread,
			ILaunch launch, String phpExeString, String debugFileName,
			int requestPort, PHPProcess process, boolean runWithDebugInfo,
			boolean stopAtFirstLine, IProject project) throws CoreException {
		return new PHPDebugTarget(thread, launch, phpExeString, debugFileName,
				requestPort, process, runWithDebugInfo, stopAtFirstLine,
				project);
	}

	/**
	 * Get {@link IProject} instance from provided launch configuration.
	 * 
	 * @param configuration
	 * @return {@link IProject}
	 * @throws CoreException
	 */
	protected IProject getProject(ILaunchConfiguration configuration)
			throws CoreException {
		String projectName = configuration.getAttribute(
				IPHPDebugConstants.PHP_Project, (String) null);
		if (projectName != null) {
			return ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);
		}
		return null;
	}

	protected boolean setProtocol(int protocolID) {
		SetProtocolRequest request = new SetProtocolRequest();
		request.setProtocolID(protocolID);
		try {
			Object response = sendRequest(request);
			if (response != null && response instanceof SetProtocolResponse) {
				int responceProtocolID = ((SetProtocolResponse) response)
						.getProtocolID();
				if (responceProtocolID == protocolID) {
					return true;
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return false;
	}

	/**
	 * This method checks whether the server protocol is older than the latest
	 * Studio protocol.
	 * 
	 * @return <code>true</code> if debugger protocol matches the Studio
	 *         protocol, otherwise <code>false</code>
	 */
	protected boolean verifyProtocolID(int serverProtocolID) {
		if (serverProtocolID < RemoteDebugger.PROTOCOL_ID_LATEST) {
			return setProtocol(RemoteDebugger.PROTOCOL_ID_LATEST);
		}
		return true;
	}

	/**
	 * This method checks whether this launch is a server one.
	 * 
	 * @param launch
	 * @return <code>true</code> if launch is server one, otherwise
	 *         <code>false</code>
	 */
	protected boolean isServerLaunch(ILaunch launch) {
		return Boolean.toString(true).equals(
				launch.getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER));
	}

	/**
	 * Hook the debug session to the correct ILaunch that started it.
	 * 
	 * @param debugSessionStartedNotification
	 * @return True, if the debug session hook was successful; False, otherwise.
	 */
	private void hookDebugSession(
			DebugSessionStartedNotification debugSessionStartedNotification)
			throws CoreException {
		/*
		 * Try to hook (debug session -> launch) only one at a time, just to
		 * avoid an ugly mess with debug events.
		 */
		try {
			// Do not lock forever
			HOOK_LOCK.tryLock(HOOK_TIMEOUT, TimeUnit.MILLISECONDS);
			SessionDescriptor sessionDescriptor = new SessionDescriptor(
					debugSessionStartedNotification);
			if (!hookLaunch(sessionDescriptor))
				// May happen
				hookError("No session id"); //$NON-NLS-1$
		} catch (InterruptedException e) {
			Logger.logException(e);
		} finally {
			HOOK_LOCK.unlock();
		}
	}

	/**
	 * Bind debug target to the launch finally.
	 * 
	 * @param launch
	 * @throws CoreException
	 */
	private void bindTarget(ILaunch launch) throws CoreException {
		IDebugTarget target = launch.getDebugTarget();
		if (target != null) {
			/*
			 * Launch already has one multiple-threaded target, extend it with
			 * incoming sub-target.
			 */
			if (target instanceof PHPMultiDebugTarget) {
				PHPMultiDebugTarget multi = (PHPMultiDebugTarget) target;
				multi.addSubTarget(debugTarget);
			}
			/*
			 * Launch already has one single-threaded target, replace it with
			 * multiple-threaded one.
			 */
			else if (target instanceof PHPDebugTarget) {
				PHPDebugTarget single = (PHPDebugTarget) target;
				// Cleanup 'single' info
				launch.removeDebugTarget(single);
				IProcess[] processes = launch.getProcesses();
				for (IProcess p : processes)
					launch.removeProcess(p);
				// Create 'multi' process & target
				PHPProcess process = new PHPProcess(launch,
						"Parallel Requests' Process"); //$NON-NLS-1$
				PHPMultiDebugTarget multi = new PHPMultiDebugTarget(launch,
						process);
				multi.addSubTarget(single);
				multi.addSubTarget(debugTarget);
				// Connect to launch
				launch.addDebugTarget(multi);
				launch.addProcess(process);
			}
		} else {
			// It is just single-threaded target
			launch.addDebugTarget(debugTarget);
			launch.addProcess(debugTarget.getProcess());
		}
	}

	/**
	 * Clean up launch. Remove terminated launches and processes.
	 * 
	 * @param launch
	 */
	private void cleanup(ILaunch launch) {
		final IDebugTarget[] debugTargets = launch.getDebugTargets();
		final IProcess[] processes = launch.getProcesses();
		final ILaunch currentLaunch = launch;
		for (IDebugTarget element : debugTargets) {
			if (element.isTerminated()) {
				currentLaunch.removeDebugTarget(element);
			}
		}
		for (IProcess element : processes) {
			if (element.isTerminated()) {
				currentLaunch.removeProcess(element);
			}
		}
	}

	private IDebugMessageHandler createMessageHandler(IDebugMessage message) {
		if (!messageHandlers.containsKey(message.getType())) {
			IDebugMessageHandler requestHandler = DebugMessagesRegistry
					.getHandler(message);
			messageHandlers.put(message.getType(), requestHandler);
		}
		return messageHandlers.get(message.getType());
	}

	/**
	 * In case of a peerResponseTimeout exception we let the communication
	 * client handle the logic of the peerResponseTimeout.
	 */
	private void handlePeerResponseTimeout() {
		getCommunicationClient().handlePeerResponseTimeout();
	}

	/**
	 * Start the connection with debugger.
	 */
	private void connect() {
		requestsTable = new IntHashtable();
		responseTable = new IntHashtable();
		responseHandlers = new Hashtable<Integer, ResponseHandler>();
		messageHandlers = new HashMap<Integer, IDebugMessageHandler>();
		try {
			socket.setTcpNoDelay(true);
			this.connectionIn = new DataInputStream(socket.getInputStream());
			this.connectionOut = new DataOutputStream(socket.getOutputStream());
			messageHandler = new MessageHandler();
			messageReceiver = new MessageReceiver();
			// Start message handler
			messageHandler.schedule();
			// Start message receiver
			messageReceiver.schedule();
			isInitialized = true;
		} catch (Exception e) {
			PHPDebugPlugin.log(e);
		}
	}

	/**
	 * Destroys the socket and initialize it to null.
	 */
	private void cleanSocket() {
		if (!isInitialized)
			return;
		if (socket != null) {
			try {
				socket.shutdownInput();
			} catch (Exception exc) {
				// ignore
			}
			try {
				socket.shutdownOutput();
			} catch (Exception exc) {
				// ignore
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (Exception exc) {
				// ignore
			} finally {
				socket = null;
			}
		}
		if (connectionIn != null) {
			try {
				synchronized (connectionIn) {
					connectionIn.close();
				}
			} catch (Exception exc) {
				// ignore
			} finally {
				connectionIn = null;
			}
		}
		if (connectionOut != null) {
			try {
				synchronized (connectionOut) {
					connectionOut.close();
				}
			} catch (Exception exc) {
				// ignore
			} finally {
				connectionOut = null;
			}
		}
	}

	/**
	 * Terminates connection completely.
	 */
	private void terminate() {
		if (!isConnected())
			return;
		// Mark it as closed already
		isConnected = false;
		cleanSocket();
		Logger.debugMSG("DEBUG CONNECTION: Socket Cleaned"); //$NON-NLS-1$
	}

}
