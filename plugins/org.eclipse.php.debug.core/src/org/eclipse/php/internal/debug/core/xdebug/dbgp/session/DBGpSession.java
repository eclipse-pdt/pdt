/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.session;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Hashtable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpCommand;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpUtils;
import org.w3c.dom.Node;

public class DBGpSession {

	public static final String DEFAULT_SESSION_ENCODING = "ISO-8859-1";

	private Socket DBGpSocket;
	private AsyncResponseHandlerJob responseHandler;
	private DBGpCommand DBGpCmd;
	private DataInputStream DBGpReader;
	private boolean socketClosed = false;
	private DBGpTarget debugTarget;
	private Hashtable savedResponses = new Hashtable();
	private String ideKey;
	private String sessionId;
	private String initialScript;
	private long creationTime;

	private String sessionEncoding;

	public long getCreationTime() {
		return creationTime;
	}

	public DBGpSession(Socket connection) {
		creationTime = System.currentTimeMillis();
		DBGpSocket = connection;
		sessionEncoding = DEFAULT_SESSION_ENCODING;

		boolean isGood = false;
		try {
			DBGpCmd = new DBGpCommand(DBGpSocket);
			DBGpReader = new DataInputStream(DBGpSocket.getInputStream());
			socketClosed = false;
			byte[] response = readResponse();
			if (response != null) {
				DBGpResponse parsedResponse = new DBGpResponse();
				parsedResponse.parseResponse(response);
				if (DBGpResponse.INIT == parsedResponse.getType()) {
					ideKey = parsedResponse.getIdekey();
					sessionId = parsedResponse.getSession();
					initialScript = DBGpUtils.getFilenameFromURIString(parsedResponse.getFileUri());
					isGood = true;
				}
			}

			if (!isGood) {
				endSession();
			}

		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("UnsupportedEncodingException - 1", this, e);
			endSession();
		} catch (IOException e) {
			DBGpLogger.logException("IOException - 1", this, e);
			endSession();
		}
	}

	/**
	 * Start the session
	 *
	 */
	public void startSession() {
		responseHandler = new AsyncResponseHandlerJob();
		responseHandler.schedule();
	}

	/**
	 * send an async command
	 * @param cmd
	 */
	public void sendAsyncCmd(String cmd) {
		sendAsyncCmd(cmd, null);
	}

	/**
	 * send a sync command
	 * @param cmd
	 * @return
	 */
	public DBGpResponse sendSyncCmd(String cmd) {
		return sendSyncCmd(cmd, null);
	}

	/**
	 * send an async command with arguments
	 * @param cmd
	 * @param arguments
	 */
	public void sendAsyncCmd(String cmd, String arguments) {
		int id = DBGpCommand.getNextId();
		try {
			DBGpCmd.send(cmd, arguments, id, sessionEncoding);
		} catch (IOException e) {
			endSession();
		}
	}

	/**
	 * send a sync command with arguments
	 * @param cmd
	 * @param arguments
	 * @return
	 */
	public DBGpResponse sendSyncCmd(String cmd, String arguments) {
		// this must be done before the command is sent because
		// the savedResponses must have the id and event in the
		// table so that the response handler can locate it.
		int id = DBGpCommand.getNextId();
		Event idev = new Event();
		Integer idObj = new Integer(id);
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

	/**
	 * only call this if you are on the response thread
	 * @param cmd
	 * @param args
	 * @return
	 */
	public DBGpResponse sendSyncCmdOnResponseThread(String cmd, String args) {
		sendAsyncCmd(cmd, args); // can't send Sync command as we block waiting for the response thread
		byte[] response = readResponse();
		DBGpResponse parsedResponse = null;
		if (response != null) {
			parsedResponse = new DBGpResponse();
			parsedResponse.parseResponse(response);
		}
		return parsedResponse;
	}

	/**
	 * process all responses from DBGp based debugger, this runs on
	 * a background thread
	 */
	private class AsyncResponseHandlerJob extends Job {

		public AsyncResponseHandlerJob() {
			super("DBGp Response Handler");
			setSystem(true);
		}

		protected IStatus run(IProgressMonitor monitor) {
			byte[] response = null;
			while (!socketClosed) {
				// here we need to block waiting for a reponse
				// then process that response
				response = readResponse();
				if (response != null) {
					DBGpResponse parsedResponse = new DBGpResponse();
					parsedResponse.parseResponse(response);
					// allow cannot get property error as this is allowed.
					if (parsedResponse.getErrorCode() == DBGpResponse.ERROR_OK || parsedResponse.getErrorCode() == DBGpResponse.ERROR_CANT_GET_PROPERTY) {

						if (DBGpResponse.RESPONSE == parsedResponse.getType()) {

							// The response handler only processes stop and break
							// status responses, all others are ignored or returned
							// to a sync caller.
							if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_STOPPED)) {
								handleStopStatus();
							} else if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_BREAK)) {
								handleBreakStatus(parsedResponse);
							}
						} else if (DBGpResponse.STREAM == parsedResponse.getType()) {
							handleStreamData(parsedResponse);
						} else {
							DBGpLogger.logWarning("Unknown type of XML: " + response, DBGpSession.this, null);
						}
					}
					unblockSyncCaller(parsedResponse);
				}
			}

			// if the socket is closed or the session terminated then we inform the debug target
			try {
				// wait a very brief period to ensure console
				// displays everything before stating the debug
				// session has ended.
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			debugTarget.sessionEnded();
			return Status.OK_STATUS;
		}

		/**
		 * unblock a sync caller 
		 * @param parsedResponse
		 */
		private void unblockSyncCaller(DBGpResponse parsedResponse) {
			// look to see if another thread is waiting for this response, if not then
			// the response is lost
			// must protect if the response doesn't include a txn id
			Integer idObj = null;
			try {
				idObj = new Integer(parsedResponse.getId());
			} catch (NumberFormatException nfe) {
				idObj = new Integer(DBGpCmd.getLastIdSent());
			}
			if (savedResponses.containsKey(idObj)) {
				Event idev = (Event) savedResponses.get(idObj);
				savedResponses.put(idObj, parsedResponse);
				idev.signalEvent();
			} else {
				// no one waiting for the response, so we need to check the response was
				// ok and generate log info
				DBGpUtils.isGoodDBGpResponse(this, parsedResponse);
			}
		}

		private void handleStreamData(DBGpResponse parsedResponse) {
			// ok, we need to put the stream somewhere
			/*
			IConsole console = DebugUITools.getConsole(debugTarget);
			if (console != null && console instanceof TextConsole) {
			   TextConsole tc = (TextConsole)console;
			   IDocument doc = tc.getDocument();
			   doc.set(doc.get() + parsedResponse.getStreamData());
			}
			*/
		}

		/**
		 * script has stopped, either by request or reached the end
		 *
		 */
		private void handleStopStatus() {
			try {
				DBGpSocket.close();
			} catch (IOException e) {
				// Ignore the exception except for debug purposes
				DBGpLogger.debugException(e);
			}
			socketClosed = true;
		}

		/**
		 * script has suspended
		 * @param parsedResponse
		 */
		private void handleBreakStatus(DBGpResponse parsedResponse) {
			// Handle the break status response information. 
			// this occurs when 
			// 1. a break point is hit
			// 2. a step command ends and we are suspended
			// 3. a command has failed, you get the status = break, reason=ok, then you get the error information
			if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_BREAK)) {
				// we have suspended, so now we can go off and handle outstanding breakpoint requests
				//debugTarget.processDBGpQueuedCmds();
				if (parsedResponse.getReason().equals(DBGpResponse.REASON_OK)) {
					// we have hit a breakpoint, or completed a step
					String cmd = parsedResponse.getCommand();

					if (cmd.equals(DBGpCommand.run)) {
						// breakpoint hit, cannot fire the suspended event until stack is created, thread info
						// setup.
						processBreakpointHit();
					} else if (cmd.equals(DBGpCommand.stepInto) || cmd.equals(DBGpCommand.StepOut) || cmd.equals(DBGpCommand.stepOver)) {
						// step hit
						// no need to setup any information ? 
						debugTarget.suspended(DebugEvent.STEP_END);
					} else {
						// we got another status response, probably due to cannot get property error
					}
				}
			}
		}

		/**
		 * process a suspend from a breakpoint
		 *
		 */
		private void processBreakpointHit() {
			// ok we hit a break point somewhere, we need to get the stack information
			// to find out which breakpoint we hit as no info is provided in the
			// response. We cannot use the DBGpTarget version here as we do
			// an async call. Plus we need to handle the possibility of STATUS_STOPPED
			// being returned.

			//Todo: Improvement: update DBGpTarget with the latest stack information         
			DBGpResponse parsedResponse = sendSyncCmdOnResponseThread(DBGpCommand.stackGet, null);
			if (parsedResponse != null) {

				// we could have received a stop here so we need to check for this
				if (parsedResponse.getStatus().equals(DBGpResponse.STATUS_STOPPED)) {
					handleStopStatus();
				} else {
					Node stackData = parsedResponse.getParentNode().getFirstChild(); // get the first stack entry
					String line = DBGpResponse.getAttribute(stackData, "lineno");
					int lineno = 0;
					try {
						lineno = Integer.parseInt(line);
						String filename = DBGpUtils.getFilenameFromURIString(DBGpResponse.getAttribute(stackData, "filename"));
						filename = debugTarget.mapInboundFileIfRequired(filename);
						debugTarget.breakpointHit(filename, lineno);
					} catch (NumberFormatException nfe) {
						DBGpLogger.logException("Unexpected number format exception", this, nfe);
					}
				}
			}
		}
	}

	/**
	 * DBGp protocol is as follows
	 * "xxx\0" where xxx is the length of the message to follow
	 * "message\0" where message is the data we are interested in.
	 * @return
	 */
	private byte[] readResponse() {
		byte byteArray[];
		byte receivedByte;
		int remainingBytesToRead = 0;

		try {

			// the first part of the DBGp protocol is the length
			// as a string, so we read it and convert it to an int
			while ((receivedByte = DBGpReader.readByte()) != 0) {
				remainingBytesToRead = remainingBytesToRead * 10 + receivedByte - 48;
			}

			byteArray = new byte[remainingBytesToRead];
			int totalBytesSoFar = 0;
			while ((remainingBytesToRead > 0)) {

				// need to handle situation where we could block still waiting for more info ?
				int bytesReceived = DBGpReader.read(byteArray, totalBytesSoFar, remainingBytesToRead);
				remainingBytesToRead -= bytesReceived;
				totalBytesSoFar += bytesReceived;
			}

			// final part of the protocol is a null value
			if ((DBGpReader.readByte()) != 0) {
				//unexpected message so the message is not valid, end the session as things
				//could become very confused.
				endSession();
				return null;
			}
		} catch (IOException e) {
			// the exception could be caused by the user terminating or disconnecting
			// however due to the nature of the debug framework, a termination request
			// may not be sent to the debug target or may be sent after it terminates
			// the process, so we cannot rely on testing the debug target for it's
			// state to determine if there has been any user activity that may
			// have caused this. 
			// we could have tested and even check the type of exception but on windows
			// you get SocketException: Connection Reset and on Linux you get
			// EOFException, so for other platforms you don't know what to expect as an
			// exception. So it is better to ignore the information
			endSession();
			return null;
		}

		try {
			if (DBGpLogger.debugResp()) {
				DBGpLogger.debug("Response: " + new String(byteArray, sessionEncoding));
			}
			return byteArray;
		} catch (UnsupportedEncodingException e) {
			DBGpLogger.logException("UnsupportedEncodingException - 2", this, e);
			endSession();
		}
		return null;
	}

	/**
	 * end this session
	 *
	 */
	public void endSession() {
		try {
			DBGpSocket.close();
			socketClosed = true;
			if (debugTarget != null) {
				debugTarget.sessionEnded();
			}
		} catch (IOException e) {
			// Ignore the exception except for debug purposes
			DBGpLogger.debugException(e);
		}
	}

	/**
	 * get the ide key for this session
	 * @return
	 */
	public String getIdeKey() {
		return ideKey;
	}

	/**
	 * get the session id for this session
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}

	public boolean isActive() {
		return !socketClosed;
	}

	/**
	 * the initial script is not remapped. Callers of this must remap
	 * it itself.
	 * @return
	 */
	public String getInitialScript() {
		return initialScript;
	}

	public DBGpTarget getDebugTarget() {
		return debugTarget;
	}

	public void setDebugTarget(DBGpTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(getIdeKey());
		if (getSessionId() != null) {
			strBuf.append(" - Session:");
			strBuf.append(getSessionId());
		} else {
			strBuf.append(" - Web Server Session");
		}
		return strBuf.toString();
	}

	public String getSessionEncoding() {
		return sessionEncoding;
	}

	public void setSessionEncoding(String sessionEncoding) {
		this.sessionEncoding = sessionEncoding;
	}
}
