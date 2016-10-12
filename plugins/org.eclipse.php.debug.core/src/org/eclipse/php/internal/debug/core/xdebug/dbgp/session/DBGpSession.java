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
package org.eclipse.php.internal.debug.core.xdebug.dbgp.session;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.*;
import org.w3c.dom.Node;

/**
 * DBGp session.
 */
public class DBGpSession {

	/**
	 * Reads all responses from DBGp based debugger, this runs on a background
	 * job thread.
	 */
	private class ResponseReader extends Job {

		public ResponseReader() {
			super("DBGp Response Reader"); //$NON-NLS-1$
			setSystem(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			byte[] response = null;
			while (sessionActive) {
				/*
				 * Here we need to block waiting for a response then process
				 * that response by related handler.
				 */
				try {
					response = readResponse();
					if (response != null) {
						DBGpResponse parsedResponse = new DBGpResponse();
						parsedResponse.parseResponse(response);
						int respErrorCode = parsedResponse.getErrorCode();
						/*
						 * We have received something back from the debugger so
						 * first we try to process a stop or break async
						 * response, even if the response was invalid.
						 */
						if (respErrorCode == DBGpResponse.ERROR_OK
								|| respErrorCode == DBGpResponse.ERROR_INVALID_RESPONSE) {
							int respType = parsedResponse.getType();
							if (respType == DBGpResponse.RESPONSE) {
								if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_STOPPED)) {
									(new ResponseHandler()).perform(ResponseHandlerAction.HANDLE_STOP, parsedResponse);
								} else if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_BREAK)) {
									(new ResponseHandler()).perform(ResponseHandlerAction.HANDLE_BREAK, parsedResponse);
								} else if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_STOPPING)) {
									(new ResponseHandler()).perform(ResponseHandlerAction.HANDLE_STOPPING,
											parsedResponse);
								}
							} else if (respType == DBGpResponse.STREAM
									&& respErrorCode != DBGpResponse.ERROR_INVALID_RESPONSE) {
								(new ResponseHandler()).perform(ResponseHandlerAction.HANDLE_STREAM, parsedResponse);
							} else {
								DBGpLogger.logWarning("Unknown type of XML: " //$NON-NLS-1$
										+ response, DBGpSession.this, null);
							}
						}
						/*
						 * Unblock any Sync caller who might be waiting
						 * regardless of what we got back.
						 */
						unblockSyncCaller(parsedResponse);
					}
				} catch (Throwable t) {
					DBGpLogger.logException("Unexpected exception. Terminating the debug session", //$NON-NLS-1$
							this, t);
				}
			}
			/*
			 * If the socket is closed or the session terminated then we inform
			 * the debug target.
			 */
			try {
				/*
				 * Wait a very brief period to ensure console displays
				 * everything before stating the debug session has ended.
				 */
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			/*
			 * End the session here as we most likely terminated cleanly. It
			 * doesn't matter if endSession is called multiple times.
			 */
			endSession();
			return Status.OK_STATUS;
		}

		/**
		 * unblock a sync caller
		 * 
		 * @param parsedResponse
		 */
		private void unblockSyncCaller(DBGpResponse parsedResponse) {
			/*
			 * Look to see if another thread is waiting for this response, if
			 * not then the response is lost must protect if the response
			 * doesn't include a txn id.
			 */
			Integer idObj = null;
			try {
				idObj = Integer.valueOf(parsedResponse.getId());
			} catch (NumberFormatException nfe) {
				idObj = Integer.valueOf(DBGpCmd.getLastIdSent());
				if (DBGpLogger.debugResp()) {
					DBGpLogger.debug("no txn id, using last which was" //$NON-NLS-1$
							+ idObj.toString());
				}
			}
			if (savedResponses.containsKey(idObj) && parsedResponse.getType() == DBGpResponse.RESPONSE) {
				postAndSignalCaller(idObj, parsedResponse);
			} else {
				/*
				 * No one waiting for the response, so we need to check the
				 * response was ok and generate log info. This could have been a
				 * response to an async invocation.
				 */
				DBGpUtils.isGoodDBGpResponse(this, parsedResponse);
			}
		}

	}

	/**
	 * Handles action related to given response type.
	 */
	private class ResponseHandler extends Job {

		private ResponseHandlerAction actionType;
		private DBGpResponse response;

		public ResponseHandler() {
			super("DBGp Response Handler"); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
			// Should be performed one after another
			setRule(schedulingRule);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			switch (actionType) {
			case HANDLE_BREAK: {
				handleBreak();
				break;
			}
			case HANDLE_STREAM: {
				handleStream();
				break;
			}
			case HANDLE_STOPPING:
			case HANDLE_STOP: {
				handleStop();
				break;
			}
			default:
				break;
			}
			return Status.OK_STATUS;
		}

		void perform(ResponseHandlerAction actionType, DBGpResponse response) {
			this.actionType = actionType;
			this.response = response;
			schedule();
		}

		private void handleStream() {
			// OK, we need to put the stream somewhere
			String data = response.getStreamData();
			if (data != null) {
				byte[] streamData = Base64.decode(data);
				String streamStr;
				try {
					streamStr = new String(streamData, outputEncoding);
				} catch (UnsupportedEncodingException e) {
					DBGpLogger.logException("invalid encoding: " //$NON-NLS-1$
							+ outputEncoding, this, e);
					streamStr = new String(streamData);
				}
				// Debug target might be already disconnected
				if (debugTarget != null) {
					debugTarget.getOutputBuffer().append(streamStr);
				}
			}
		}

		/**
		 * script has stopped, either by request or reached the end
		 * 
		 */
		private void handleStop() {
			endSession();
		}

		/**
		 * script has suspended
		 * 
		 * @param parsedResponse
		 */
		private void handleBreak() {
			// Handle the break status response information.
			// this occurs when:
			// 1. a break point is hit
			// 2. a step command ends and we are suspended
			// 3. a command has failed, you get the status = break, reason=ok,
			// then you get the error information
			if (response.getStatus().equals(DBGpResponse.STATUS_BREAK)) {
				Node breakData = response.getParentNode().getFirstChild();
				String exception = DBGpResponse.getAttribute(breakData, "exception"); //$NON-NLS-1$
				/*
				 * We have suspended, so now we can go off and handle
				 * outstanding breakpoint requests
				 * debugTarget.processDBGpQueuedCmds();
				 */
				if (response.getReason().equals(DBGpResponse.REASON_OK)) {
					// we have hit a breakpoint, or completed a step
					String cmd = response.getCommand();
					if (cmd.equals(DBGpCommand.run) || !exception.isEmpty()) {
						/*
						 * OK we hit a break point somewhere, we need to get the
						 * stack information to find out which breakpoint we hit
						 * as no info is provided in the response. We cannot use
						 * the DBGpTarget version here as we do an async call.
						 * Plus we need to handle the possibility of
						 * STATUS_STOPPED being returned.
						 */
						response = sendSyncCmd(DBGpCommand.stackGet, null);
						if (response != null) {
							/*
							 * We could have received a stop here so we need to
							 * check for this.
							 */
							if (response.getStatus().equals(DBGpResponse.STATUS_STOPPED)) {
								handleStop();
							} else {
								Node stackData = response.getParentNode().getFirstChild();
								String line = DBGpResponse.getAttribute(stackData, "lineno"); //$NON-NLS-1$
								int lineno = 0;
								try {
									lineno = Integer.parseInt(line);
									String filename = DBGpUtils
											.getFilenameFromURIString(DBGpResponse.getAttribute(stackData, "filename")); //$NON-NLS-1$
									filename = debugTarget.mapToWorkspaceFileIfRequired(filename);
									// Debug target might be already
									// disconnected
									if (debugTarget != null) {
										debugTarget.breakpointHit(filename, lineno, exception);
									}
								} catch (NumberFormatException nfe) {
									DBGpLogger.logException("Unexpected number format exception", //$NON-NLS-1$
											this, nfe);
								}
							}
						}
					} else if (cmd.equals(DBGpCommand.stepInto) || cmd.equals(DBGpCommand.StepOut)
							|| cmd.equals(DBGpCommand.stepOver)) {
						// Debug target might be already disconnected
						if (debugTarget != null) {
							debugTarget.suspended(DebugEvent.STEP_END);
						}
					} else {
						/*
						 * we got another status response, probably due to
						 * cannot get property error.
						 */
					}
				}
			}
		}

	}

	private static enum ResponseHandlerAction {

		HANDLE_BREAK, HANDLE_STOPPING, HANDLE_STOP, HANDLE_STREAM;

	}

	public static final String DEFAULT_SESSION_ENCODING = "ISO-8859-1"; //$NON-NLS-1$
	public static final String DEFAULT_BINARY_ENCODING = Charset.defaultCharset().name();
	public static final String DEFAULT_OUTPUT_ENCODING = Charset.defaultCharset().name();

	private final ISchedulingRule schedulingRule = new ISchedulingRule() {
		@Override
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}
	};

	private Socket DBGpSocket;
	private ResponseReader responseHandler;
	private DBGpCommand DBGpCmd;
	private DataInputStream DBGpReader;
	private boolean sessionActive = false;
	private DBGpTarget debugTarget;
	private Hashtable<Integer, Object> savedResponses = new Hashtable<Integer, Object>();
	private String ideKey;
	private String sessionId;
	private String initialScript;
	private EngineTypes engineType;
	private String engineVersion;
	private String threadId;
	private long creationTime;
	private String sessionEncoding;
	private String outputEncoding;
	private String binaryEncoding;

	public long getCreationTime() {
		return creationTime;
	}

	/**
	 * create a DBGpSession. This waits for the initial INIT response to be sent
	 * 
	 * @param connection
	 *            the socket connection.
	 */
	public DBGpSession(Socket connection) {
		creationTime = System.currentTimeMillis();
		DBGpSocket = connection;
		sessionEncoding = DEFAULT_SESSION_ENCODING;
		boolean isGood = false;
		try {
			DBGpCmd = new DBGpCommand(DBGpSocket);
			DBGpReader = new DataInputStream(DBGpSocket.getInputStream());
			sessionActive = true;

			// TODO: Could look at supporting a timeout here.
			byte[] response = readResponse();
			if (response != null) {
				DBGpResponse parsedResponse = new DBGpResponse();
				parsedResponse.parseResponse(response);
				if (DBGpResponse.INIT == parsedResponse.getType()) {
					ideKey = parsedResponse.getIdekey();
					sessionId = parsedResponse.getSession();
					initialScript = DBGpUtils.getFilenameFromURIString(parsedResponse.getFileUri());
					engineVersion = parsedResponse.getEngineVersion();
					engineType = parsedResponse.getEngineType();
					threadId = parsedResponse.getThreadId();
					isGood = true;
				} else {
					DBGpLogger.logError("Init response not received. XML=" //$NON-NLS-1$
							+ parsedResponse.getRawXML(), this, null);
					// TODO: dialog box up
				}
			} else {
				DBGpLogger.logError("Unexpected null from readResponse waiting for Init", //$NON-NLS-1$
						this, null);
			}
			if (!isGood) {
				endSession();
			}
		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("UnsupportedEncodingException - 1", this, e); //$NON-NLS-1$
			endSession();
		} catch (IOException e) {
			DBGpLogger.logException("IOException - 1", this, e); //$NON-NLS-1$
			endSession();
		}
	}

	/**
	 * Start the session. This schedules the job that listens for incoming
	 * responses from the system being debugged as these can happen
	 * asynchronously.
	 * 
	 */
	public void startSession() {
		responseHandler = new ResponseReader();
		responseHandler.schedule();
	}

	/**
	 * send an async command
	 * 
	 * @param cmd
	 *            the command to send
	 */
	public void sendAsyncCmd(String cmd) {
		sendAsyncCmd(cmd, null);
	}

	/**
	 * send a sync command
	 * 
	 * @param cmd
	 *            the command to send
	 * @return the response.
	 */
	public DBGpResponse sendSyncCmd(String cmd) {
		return sendSyncCmd(cmd, null);
	}

	/**
	 * send an async command with arguments
	 * 
	 * @param cmd
	 *            the command
	 * @param arguments
	 *            its arguments
	 */
	public void sendAsyncCmd(String cmd, String arguments) {
		if (sessionActive) {
			int id = DBGpCommand.getNextId();
			try {
				DBGpCmd.send(cmd, arguments, id, sessionEncoding);
			} catch (IOException e) {
				endSession();
			}
		}
	}

	/**
	 * send a sync command with arguments
	 * 
	 * @param cmd
	 *            the command
	 * @param arguments
	 *            its arguments
	 * @return the response
	 */
	public DBGpResponse sendSyncCmd(String cmd, String arguments) {
		if (sessionActive) {
			/*
			 * this must be done before the command is sent because the
			 * savedResponses must have the id and event in the table so that
			 * the response handler can locate it.
			 */
			int id = DBGpCommand.getNextId();
			Event idev = new Event();
			Integer idObj = Integer.valueOf(id);
			savedResponses.put(idObj, idev);
			try {
				DBGpCmd.send(cmd, arguments, id, sessionEncoding);
				idev.waitForEvent(); // wait forever
				return (DBGpResponse) savedResponses.remove(idObj);
			} catch (InterruptedException e) {
				return null;
			} catch (IOException e) {
				endSession();
				return null;
			}
		}
		return null;
	}

	/**
	 * end this session
	 * 
	 */
	public synchronized void endSession() {
		/*
		 * We are ending the session so ensure anything that is waiting for a
		 * response is unblocked.
		 */
		unblockAllCallers(null);
		if (sessionActive) {
			sessionActive = false;
			try {
				DBGpSocket.shutdownInput();
			} catch (IOException e) {
			}
			try {
				DBGpSocket.shutdownOutput();
			} catch (IOException e) {
			}
			try {
				DBGpSocket.close();
			} catch (IOException e) {
				// Ignore the exception except for debug purposes
				DBGpLogger.debugException(e);
			}
		}
		if (debugTarget != null) {
			debugTarget.sessionEnded();
			debugTarget = null;
		}
	}

	/**
	 * get the ide key for this session
	 * 
	 * @return
	 */
	public String getIdeKey() {
		return ideKey;
	}

	/**
	 * get the session id for this session
	 * 
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * get the Thread id for this session. A blank threadid usually indicates
	 * that no thread id was returned.
	 * 
	 * @return the thread id
	 */
	public String getThreadId() {
		return threadId;
	}

	public boolean isActive() {
		return sessionActive;
	}

	/**
	 * the initial script is not remapped. Callers of this must remap it itself.
	 * 
	 * @return
	 */
	public String getInitialScript() {
		return initialScript;
	}

	public DBGpTarget getDebugTarget() {
		return debugTarget;
	}

	/**
	 * debug target calls this to say it is owner of the session.
	 * 
	 * @param debugTarget
	 */
	public void setDebugTarget(DBGpTarget debugTarget) {
		this.debugTarget = debugTarget;
		// Now we have a target we can determine the user defined encodings
		determineEncodings();
	}

	/**
	 * get the current session encoding
	 * 
	 * @return the session encoding.
	 */
	public String getSessionEncoding() {
		return sessionEncoding;
	}

	public String getOutputEncoding() {
		return outputEncoding;
	}

	public String getBinaryEncoding() {
		return binaryEncoding;
	}

	/**
	 * set the session encoding. DBGpTarget determines this when handshaking.
	 * 
	 * @param sessionEncoding
	 *            session encoding.
	 */
	public void setSessionEncoding(String sessionEncoding) {
		this.sessionEncoding = sessionEncoding;
	}

	public EngineTypes getEngineType() {
		return engineType;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public int getRemotePort() {
		return DBGpSocket.getPort();
	}

	public InetAddress getRemoteAddress() {
		return DBGpSocket.getInetAddress();
	}

	public String getRemoteHostname() {
		return DBGpSocket.getInetAddress().getHostName();
	}

	public String toString() {
		StringBuilder strBuf = new StringBuilder(getIdeKey());
		if (getSessionId() != null) {
			strBuf.append(" - Session:"); //$NON-NLS-1$
			strBuf.append(getSessionId());
		} else {
			strBuf.append(" - Web Server Session"); //$NON-NLS-1$
		}
		return strBuf.toString();
	}

	private void unblockAllCallers(DBGpResponse parsedResponse) {
		if (parsedResponse == null) {
			// if null passed in, create a dummy response.
			parsedResponse = new DBGpResponse();
			parsedResponse.parseResponse(null);
		}
		Set<Integer> keys = savedResponses.keySet();
		for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
			Integer idObj = (Integer) iterator.next();
			postAndSignalCaller(idObj, parsedResponse);
		}
	}

	private void postAndSignalCaller(Integer idObj, DBGpResponse parsedResponse) {
		Object responder = savedResponses.get(idObj);
		if (responder instanceof Event) {
			/*
			 * We have an event for the id so we need to respond and unblock the
			 * caller, otherwise it has already been done (maybe from
			 * unblockAllCallers)
			 */
			Event idev = (Event) responder;
			savedResponses.put(idObj, parsedResponse);
			idev.signalEvent();
		}
	}

	/**
	 * DBGp protocol is as follows "xxx\0" where xxx is the length of the
	 * message to follow "message\0" where message is the data we are interested
	 * in.
	 * 
	 * @return
	 */
	private byte[] readResponse() {
		byte byteArray[];
		byte receivedByte;
		int remainingBytesToRead = 0;
		try {
			/*
			 * The first part of the DBGp protocol is the length as a string, so
			 * we read it and convert it to an int
			 */
			while ((receivedByte = DBGpReader.readByte()) != 0) {
				remainingBytesToRead = remainingBytesToRead * 10 + receivedByte - 48;
			}
			byteArray = new byte[remainingBytesToRead];
			int totalBytesSoFar = 0;
			while ((remainingBytesToRead > 0)) {
				int bytesReceived = DBGpReader.read(byteArray, totalBytesSoFar, remainingBytesToRead);
				remainingBytesToRead -= bytesReceived;
				totalBytesSoFar += bytesReceived;
			}
			// Final part of the protocol is a null value
			if ((DBGpReader.readByte()) != 0) {
				/*
				 * Unexpected message so the message is not valid, end the
				 * session as things could become very confused.
				 */
				endSession();
				return null;
			}
		} catch (IOException e) {
			/*
			 * The exception could be caused by the user terminating or
			 * disconnecting however due to the nature of the debug framework, a
			 * termination request may not be sent to the debug target or may be
			 * sent after it terminates the process, so we cannot rely on
			 * testing the debug target for it's state to determine if there has
			 * been any user activity that may have caused this. we could have
			 * tested and even check the type of exception but on windows you
			 * get SocketException: Connection Reset and on Linux you get
			 * EOFException, so for other platforms you don't know what to
			 * expect as an exception. So it is better to ignore the
			 * information.
			 */
			endSession();
			return null;
		}
		try {
			if (DBGpLogger.debugResp()) {
				DBGpLogger.debug("Response: " //$NON-NLS-1$
						+ new String(byteArray, sessionEncoding));
			}
			return byteArray;
		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("UnsupportedEncodingException - 2", this, e); //$NON-NLS-1$
			endSession();
		}
		return null;
	}

	private void determineEncodings() {
		ILaunch launch = getDebugTarget().getLaunch();
		ILaunchConfiguration launchConfig = launch.getLaunchConfiguration();
		outputEncoding = getCharset(IDebugParametersKeys.OUTPUT_ENCODING, launchConfig);
		binaryEncoding = getCharset(IDebugParametersKeys.TRANSFER_ENCODING, launchConfig);
	}

	private String getCharset(String encodingKey, ILaunchConfiguration launchConfig) {
		String charset = null;
		String outputEncoding = null;
		if (launchConfig != null) {
			try {
				outputEncoding = launchConfig.getAttribute(encodingKey, ""); //$NON-NLS-1$
			} catch (CoreException e) {
			}
		}
		if (outputEncoding == null || outputEncoding.length() == 0) {
			// null will return it from the main preferences
			if (encodingKey == IDebugParametersKeys.OUTPUT_ENCODING) {
				outputEncoding = PHPProjectPreferences.getOutputEncoding(null);
			} else {
				outputEncoding = PHPProjectPreferences.getTransferEncoding(null);
			}
		}
		if (outputEncoding == null || Charset.isSupported(outputEncoding) == false) {
			charset = Charset.defaultCharset().name();
		} else {
			charset = outputEncoding;
		}
		return charset;
	}

}
