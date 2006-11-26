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
package org.eclipse.php.debug.core.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.debug.core.communication.DebugConnectionThread;
import org.eclipse.php.debug.core.debugger.DebugError;
import org.eclipse.php.debug.core.debugger.DefaultExpressionsManager;
import org.eclipse.php.debug.core.debugger.IRemoteDebugger;
import org.eclipse.php.debug.core.debugger.RemoteDebugger;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;

public class ServerDebugHandler extends SimpleDebugHandler {

	private IRemoteDebugger fRemoteDebugger;
	private PHPDebugTarget fDebugTarget;
	private boolean fStatus;
	protected DebugConnectionThread fConnectionThread;

	public ServerDebugHandler() {
	}

	protected IRemoteDebugger createRemoteDebugger() {
		return new RemoteDebugger(this, fConnectionThread);
	}

	public IRemoteDebugger getRemoteDebugger() {
		return fRemoteDebugger;
	}

	public void sessionStarted(String fileName, String uri, String query, String options) {
		super.sessionStarted(fileName, uri, query, options);
		String sFileName = RemoteDebugger.convertToSystemIndependentFileName(fileName);

		fDebugTarget.setLastFileName(sFileName);
		if (!fDebugTarget.isPHPCGI()) {
			fDebugTarget.setServerWindows(false);
			int index;
			// check for Windows, since case isn't always returned correctly
			if (fileName.startsWith(":\\", 1)) {
				index = sFileName.toLowerCase().lastIndexOf(uri.toLowerCase());
				fDebugTarget.setServerWindows(true);
			} else {
				if (uri.startsWith("/~")) {
					int iUDir = uri.indexOf("/", 1);
					uri = uri.substring(iUDir);
				}
				index = sFileName.lastIndexOf(uri);
			}
			fDebugTarget.setHTDocs(sFileName.substring(0, index));
		}

		StartLock startLock = fDebugTarget.getStartLock();
		synchronized (startLock) {
			if (startLock.isRunStart()) {
				startLock.setStarted(true);
				fDebugTarget.started();
				fStatus = getRemoteDebugger().start(fDebugTarget.getStartResponseHandler());
				fDebugTarget.installDeferredBreakpoints();
				if (!fStatus) {
					Logger.log(Logger.ERROR, "PHPDebugTarget: debugger.start return false");
				}
				fDebugTarget.setLastCommand("start");
			} else {
				startLock.setRunStart(true);
			}
		}
	}

	public void connectionEstablished() {
		super.connectionEstablished();
		StartLock startLock = fDebugTarget.getStartLock();
		synchronized (startLock) {
			if (startLock.isRunStart()) {
				startLock.setStarted(true);
				fDebugTarget.started();

				fStatus = getRemoteDebugger().start(fDebugTarget.getStartResponseHandler());
				if (!fStatus) {
					Logger.log(Logger.ERROR, "PHPDebugTarget: debugger.start return false");
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
		fDebugTarget.setLastFileName(RemoteDebugger.convertToSystemIndependentFileName(fileName));
		String fLastcmd = fDebugTarget.getLastCommand();
		Logger.debugMSG("[" + this + "] PHPDebugTarget: lastCMD " + fLastcmd);

		fDebugTarget.setBreakpoints(new IBreakpoint[] {});

		ILaunchConfiguration launchConfiguration = fDebugTarget.getLaunch().getLaunchConfiguration();
		try {
			fDebugTarget.setExpressionManager(new DefaultExpressionsManager(fRemoteDebugger, launchConfiguration.getAttribute(IDebugParametersKeys.TRANSFER_ENCODING, "")));
		} catch (CoreException e) {
		}

		if (fLastcmd.equals("start")) {
			fDebugTarget.breakpointHit(fDebugTarget.getLastFileName(), lineNumber);
		} else if (fLastcmd.equals("resume")) {
			fDebugTarget.breakpointHit(fDebugTarget.getLastFileName(), lineNumber);
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
		Logger.debugMSG("[" + this + "] PHPDebugTarget: Starting sessionEnded()");
		super.sessionEnded();

	}

	public void connectionClosed() {
		Logger.debugMSG("[" + this + "] PHPDebugTarget:Starting connectionClosed()");
		super.connectionClosed();
		fRemoteDebugger.finish();
		//		if (fDebugTarget.isPHPCGI()) {
		//			Logger.debugMSG("PHPDebugTarget: Calling Terminated() for PHP CGI");
		Logger.debugMSG("[" + this + "] PHPDebugTarget: Calling Terminated()");
		fDebugTarget.terminated();
		//		}
	}

	public void handleScriptEnded() {
		Logger.debugMSG("[" + this + "] PHPDebugTarget: handleScriptEnded");
		try {
			Logger.debugMSG("[" + this + "] PHPDebugTarget: Calling Terminate()");
			fDebugTarget.terminate();

		} catch (DebugException e1) {
			Logger.logException("PHPDebugTarget: terminate failed", e1);
		}
	}

	public void multipleBindOccured() {
		super.multipleBindOccured();
		Logger.log(Logger.WARNING, "PHPDebugTarget: Multiple Bind Occured");

		//		Hashtable usedPorts = fDebugTarget.getUsedPorts();
		//		synchronized (usedPorts) {
		//			usedPorts.remove(String.valueOf(fDebugTarget.getRequestPort()));
		//		}
		/*            fRequestPort++;
		 debugger.setDebugPort(fRequestPort);
		 if (fIsPHPCGI){
		 try {
		 debugPHPExecutable(fPHPExe, fFileToDebug.getLocation().toString(), fRequestPort, fIsStopAtFirstLine);
		 } catch (DebugException e) {
		 // Not likely to happened, since this is the second time we found the file.
		 Logger.logException("PHPDebugTarget: Debugger didn't find file to debug.", e);
		 } 
		 }else {
		 runPHPWebServer(fURL, fRequestPort, fIsStopAtFirstLine);
		 terminated();

		 }    */
		String errorMessage = PHPDebugCoreMessages.DebuggerDebugPortInUse_1;
		fRemoteDebugger.closeConnection();
		fDebugTarget.fireError(errorMessage, null);
		fDebugTarget.terminated();
	}

	public void parsingErrorOccured(DebugError debugError) {
		super.parsingErrorOccured(debugError);
		String sName = debugError.getFileName();
		int length;
		String rName;
		if (!fDebugTarget.isPHPCGI()) {
			length = fDebugTarget.getHTDocs().length() + fDebugTarget.getContextRoot().length();
			rName = sName.substring(length);
		} else {
			length = fDebugTarget.getWorkspacePath().length() + fDebugTarget.getProjectName().length();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFileForLocation(new Path(sName));
			if (file != null) {
				if (root.getProject(fDebugTarget.getProjectName()).equals(file.getProject())) {
					rName = file.getProjectRelativePath().toOSString();
				} else {
					rName = ".." + file.getFullPath().toOSString();
				}
			} else {
				rName = sName;
			}
		}
		String dFileName = RemoteDebugger.convertToSystemIndependentFileName(rName);
		debugError.setFileName(dFileName);
		fDebugTarget.getDebugErrors().add(debugError);

		Object[] listeners = fDebugTarget.getConsoleEventListeners().toArray();
		for (int i = 0; i < listeners.length; i++) {
			((IPHPConsoleEventListener) listeners[i]).handleEvent(debugError);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.debug.core.model.SimpleDebugHandler#wrongDebugServer()
	 */
	public void wrongDebugServer() {
		super.wrongDebugServer();
		fDebugTarget.fireError("Incompatible Debug Server version.", null);
		fRemoteDebugger.finish();
	}

	public void newOutput(String output) {
		super.newOutput(output);
		fDebugTarget.getOutputBufffer().append(output);
		fDebugTarget.getOutputBufffer().incrementUpdateCount();
	}

	public void newHeaderOutput(String output) {
		super.newHeaderOutput(output);
		fDebugTarget.getOutputBufffer().append(output);
		fDebugTarget.getOutputBufffer().incrementUpdateCount();
	}

	public void setDebugTarget(PHPDebugTarget debugTarget) {
		this.fDebugTarget = debugTarget;
		fConnectionThread = fDebugTarget.getConnectionThread();
		fRemoteDebugger = createRemoteDebugger();
		fConnectionThread.getCommunicationAdministrator().connectionEstablished();
	}

	public PHPDebugTarget getDebugTarget() {
		return fDebugTarget;
	}
}
