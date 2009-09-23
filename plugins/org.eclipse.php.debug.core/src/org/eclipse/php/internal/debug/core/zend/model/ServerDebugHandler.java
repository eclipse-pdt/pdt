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
package org.eclipse.php.internal.debug.core.zend.model;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.model.SimpleDebugHandler;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnectionThread;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpressionsManager;
import org.eclipse.php.internal.debug.core.zend.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;

/**
 * A PHP debug server handler.
 * 
 * @author Shalom Gibly
 */
public class ServerDebugHandler extends SimpleDebugHandler {

	private IRemoteDebugger fRemoteDebugger;
	private boolean fStatus;
	protected PHPDebugTarget fDebugTarget;
	protected DebugConnectionThread fConnectionThread;

	public ServerDebugHandler() {
	}

	protected IRemoteDebugger createRemoteDebugger() {
		return new RemoteDebugger(this, fConnectionThread);
	}

	public IRemoteDebugger getRemoteDebugger() {
		return fRemoteDebugger;
	}

	public void sessionStarted(String remoteFile, String uri, String query,
			String options) {
		super.sessionStarted(remoteFile, uri, query, options);

		if (isUsingPathMapper()) {
			// Hack for the case when htdocs is symlinked to the workspace.
			// Zend Debugger resolves symbolic links later, and it fucks up the
			// path mapper:
			try {
				File file = new File(remoteFile);
				if (file.exists()) {
					remoteFile = file.getCanonicalPath();
				}
			} catch (Exception e) {
			}

			fDebugTarget.mapFirstDebugFile(remoteFile);
		}

		fDebugTarget.setLastFileName(remoteFile);
		if (!fDebugTarget.isPHPCGI()) {
			fDebugTarget.setServerWindows(false);
		}

		StartLock startLock = fDebugTarget.getStartLock();
		synchronized (startLock) {
			if (startLock.isRunStart()) {
				startLock.setStarted(true);
				fDebugTarget.started();
				fStatus = getRemoteDebugger().start(
						fDebugTarget.getStartResponseHandler());
				if (!fStatus) {
					Logger.log(Logger.ERROR,
							"ServerDebugHandler: debugger.start return false");
					try {
						fDebugTarget.disconnect();
					} catch (DebugException e) {
						Logger.logException(e);
					}
				}
				fDebugTarget.setLastCommand("start");
			} else {
				startLock.setRunStart(true);
			}
		}
	}

	protected boolean isUsingPathMapper() {
		return true;
	}

	public void connectionEstablished() {
		super.connectionEstablished();
		StartLock startLock = fDebugTarget.getStartLock();
		synchronized (startLock) {
			if (startLock.isRunStart()) {
				startLock.setStarted(true);
				fDebugTarget.started();

				fStatus = getRemoteDebugger().start(
						fDebugTarget.getStartResponseHandler());
				if (!fStatus) {
					Logger.log(Logger.ERROR,
							"ServerDebugHandler: debugger.start return false");
				}
				fDebugTarget.setLastCommand("start");
			} else {
				startLock.setRunStart(true);
			}
		}
	}

	public void ready(String fileName, int lineNumber) {
		super.ready(fileName, lineNumber);

		fDebugTarget.setLastStop(lineNumber);
		fDebugTarget.setLastFileName(fileName);
		String fLastcmd = fDebugTarget.getLastCommand();
		Logger.debugMSG("ServerDebugHandler: lastCMD " + fLastcmd);

		fDebugTarget.setBreakpoints(new IBreakpoint[] {});

		ILaunchConfiguration launchConfiguration = fDebugTarget.getLaunch()
				.getLaunchConfiguration();
		try {
			fDebugTarget.setExpressionManager(new DefaultExpressionsManager(
					fRemoteDebugger, launchConfiguration.getAttribute(
							IDebugParametersKeys.TRANSFER_ENCODING,
							PHPProjectPreferences.getTransferEncoding(null))));
		} catch (CoreException e) {
		}

		if (fLastcmd.equals("start")) {
			fDebugTarget.breakpointHit(fDebugTarget.getLastFileName(),
					lineNumber);
		} else if (fLastcmd.equals("resume")) {
			fDebugTarget.breakpointHit(fDebugTarget.getLastFileName(),
					lineNumber);
		} else if (fLastcmd.equals("suspend")) {
			fDebugTarget.suspended(DebugEvent.CLIENT_REQUEST);
		} else if (fLastcmd.equals("stepReturn")) {
			fDebugTarget.suspended(DebugEvent.STEP_RETURN);
		} else if (fLastcmd.equals("stepOver")) {
			fDebugTarget.suspended(DebugEvent.STEP_OVER);
		} else if (fLastcmd.equals("stepInto")) {
			fDebugTarget.suspended(DebugEvent.STEP_INTO);
		} else if (fLastcmd.equals("terminate")) {
			// Shouldn't happen, try to shut down cleanly
			fRemoteDebugger.finish();
			fDebugTarget.terminated();
		} else if (fLastcmd.equals("breakpointAdded")) {

		} else if (fLastcmd.equals("breakpointRemoved")) {

		}

	}

	public void sessionEnded() {
		Logger.debugMSG("ServerDebugHandler: Starting sessionEnded()");
		super.sessionEnded();

	}

	public void connectionClosed() {
		Logger.debugMSG("ServerDebugHandler: Starting connectionClosed()");
		super.connectionClosed();
		fRemoteDebugger.finish();
		// if (fDebugTarget.isPHPCGI()) {
		// Logger.debugMSG("ServerDebugHandler: Calling Terminated() for PHP CGI");
		Logger.debugMSG("ServerDebugHandler: Calling Terminated()");
		fDebugTarget.terminated();
		// }
	}

	public void handleScriptEnded() {
		Logger.debugMSG("ServerDebugHandler: handleScriptEnded");
		try {
			Logger.debugMSG("ServerDebugHandler: Calling Terminate()");
			fDebugTarget.terminate();

		} catch (DebugException e1) {
			Logger.logException("ServerDebugHandler: terminate failed", e1);
		}
	}

	public void multipleBindOccured() {
		super.multipleBindOccured();
		Logger.log(Logger.WARNING, "ServerDebugHandler: Multiple Bind Occured");

		String errorMessage = PHPDebugCoreMessages.DebuggerDebugPortInUse_1;
		fRemoteDebugger.closeConnection();
		fDebugTarget.fireError(errorMessage, null);
		fDebugTarget.terminated();
	}

	public void parsingErrorOccured(DebugError debugError) {
		super.parsingErrorOccured(debugError);

		// resolve path
		String localFileName = ((RemoteDebugger) fRemoteDebugger)
				.convertToLocalFilename(debugError.getFullPathName(), null,
						null);
		if (localFileName == null) {
			localFileName = debugError.getFullPathName();
		}
		debugError.setFileName(localFileName);

		if (fDebugTarget.getDebugErrors().add(debugError)) {
			Object[] listeners = fDebugTarget.getConsoleEventListeners()
					.toArray();
			for (Object element : listeners) {
				((IPHPConsoleEventListener) element).handleEvent(debugError);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.SimpleDebugHandler#wrongDebugServer
	 * ()
	 */
	public void wrongDebugServer() {
		super.wrongDebugServer();
		fDebugTarget.fireError("Incompatible Debug Server version.", null);
		fRemoteDebugger.finish();
	}

	public void newOutput(String output) {
		super.newOutput(output);
		fDebugTarget.getOutputBuffer().append(output);
	}

	public void newHeaderOutput(String output) {
		super.newHeaderOutput(output);
		fDebugTarget.getOutputBuffer().appendHeader(output);
	}

	public void setDebugTarget(PHPDebugTarget debugTarget) {
		this.fDebugTarget = debugTarget;
		fConnectionThread = fDebugTarget.getConnectionThread();
		fRemoteDebugger = createRemoteDebugger();
		fConnectionThread.getCommunicationAdministrator()
				.connectionEstablished();
	}

	public PHPDebugTarget getDebugTarget() {
		return fDebugTarget;
	}
}
