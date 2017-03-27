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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.model.SimpleDebugHandler;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping.MappingSource;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.zend.communication.DebugConnection;
import org.eclipse.php.internal.debug.core.zend.debugger.*;
import org.eclipse.php.internal.debug.core.zend.debugger.parameters.DefaultDebugParametersInitializer;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * A PHP debug server handler.
 * 
 * @author Shalom Gibly
 */
public class ServerDebugHandler extends SimpleDebugHandler {

	protected IRemoteDebugger fRemoteDebugger;
	protected boolean fStatus;
	protected PHPDebugTarget fDebugTarget;
	protected DebugConnection fDebugConnection;
	protected boolean fCodeCoverage;
	protected boolean fUseLocalCopy;
	protected CodeCoverageData[] fCodeCoverageData;

	public ServerDebugHandler() {
	}

	public IRemoteDebugger getRemoteDebugger() {
		return fRemoteDebugger;
	}

	public void sessionStarted(String remoteFile, String uri, String query, String options) {
		super.sessionStarted(remoteFile, uri, query, options);

		fUseLocalCopy = true;
		ILaunchConfiguration launchConfiguration = fDebugTarget.getLaunch().getLaunchConfiguration();
		try {
			fUseLocalCopy = !launchConfiguration.getAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, false);
		} catch (CoreException e) {
			DebugPlugin.log(e);
		}

		if (fUseLocalCopy) {
			// Bind server with this launch configuration if we can find any:
			final String serverURL = fDebugTarget.getURL();
			if (serverURL != null) {
				try {
					String serverName = null;
					Server serverLookup = ServersManager.findByURL(serverURL);
					if (serverLookup != null)
						serverName = serverLookup.getName();
					if (serverName != null) {
						ILaunchConfigurationWorkingCopy wc = launchConfiguration.getWorkingCopy();
						wc.setAttribute(Server.NAME, serverName);
						synchronized (launchConfiguration) {
							wc.doSave();
						}
					}
				} catch (CoreException e) {
					DebugPlugin.log(e);
				}
			}
			// XXX: add initial mapping for debugging via PHP Web Script (needed
			// for the first breakpoint)
			try {
				String debugType = launchConfiguration.getAttribute(IDebugParametersKeys.PHP_DEBUG_TYPE, ""); //$NON-NLS-1$
				if (debugType.equals(IDebugParametersKeys.PHP_WEB_SCRIPT_DEBUG)) {
					PathMapper pathMapper = PathMapperRegistry.getByLaunchConfiguration(launchConfiguration);
					String debugFilePath = launchConfiguration.getAttribute(IPHPDebugConstants.ATTR_FILE,
							(String) null);
					String debugFileFullPath = launchConfiguration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH,
							(String) null);
					if (pathMapper != null && debugFilePath != null && debugFileFullPath != null
							&& pathMapper.getLocalFile(debugFileFullPath) == null) {
						IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(debugFilePath);
						if (resource instanceof IFile) {
							pathMapper.addEntry(debugFileFullPath,
									new PathEntry(debugFilePath, Type.WORKSPACE, resource.getParent()),
									MappingSource.ENVIRONMENT);
						} else if (new File(debugFilePath).exists()) {
							pathMapper.addEntry(debugFilePath, new PathEntry(debugFilePath, Type.EXTERNAL,
									new File(debugFilePath).getParentFile()), MappingSource.ENVIRONMENT);
						}
					}
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}

		if (isUsingPathMapper()) {
			/*
			 * Hack for the case when htdocs is symlinked to the workspace. Zend
			 * Debugger resolves symbolic links later and it breaks path mapper.
			 */
			try {
				String lcServerName = launchConfiguration.getAttribute(Server.NAME, (String) null);
				if ((lcServerName == null || lcServerName.isEmpty()) && fDebugTarget.getURL() != null) {
					// Bind server with this configuration, if we can find any.
					String serverName = null;
					Server serverLookup = ServersManager.findByURL(fDebugTarget.getURL());
					if (serverLookup != null)
						serverName = serverLookup.getName();
					if (serverName != null) {
						ILaunchConfigurationWorkingCopy wc = launchConfiguration.getWorkingCopy();
						wc.setAttribute(Server.NAME, serverName);
						synchronized (launchConfiguration) {
							wc.doSave();
						}
					}
				}
			} catch (CoreException e) {
				DebugPlugin.log(e);
			}
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
				fStatus = getRemoteDebugger().start(fDebugTarget.getStartResponseHandler());
				if (!fStatus) {
					Logger.log(Logger.ERROR, "ServerDebugHandler: debugger.start return false"); //$NON-NLS-1$
					try {
						fDebugTarget.disconnect();
					} catch (DebugException e) {
						Logger.logException(e);
					}
				}
				fDebugTarget.setLastCommand("start"); //$NON-NLS-1$
			} else {
				startLock.setRunStart(true);
			}
		}

		fCodeCoverage = query.indexOf(DefaultDebugParametersInitializer.CODE_COVERAGE) != -1;
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
					Logger.log(Logger.ERROR, "ServerDebugHandler: debugger.start return false"); //$NON-NLS-1$
				}
				fDebugTarget.setLastCommand("start"); //$NON-NLS-1$
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
		Logger.debugMSG("ServerDebugHandler: lastCMD " + fLastcmd); //$NON-NLS-1$

		fDebugTarget.setBreakpoints(new IBreakpoint[] {});

		ILaunchConfiguration launchConfiguration = fDebugTarget.getLaunch().getLaunchConfiguration();
		try {
			fDebugTarget.setExpressionManager(
					new DefaultExpressionsManager(fRemoteDebugger, launchConfiguration.getAttribute(
							IDebugParametersKeys.TRANSFER_ENCODING, PHPProjectPreferences.getTransferEncoding(null))));
		} catch (CoreException e) {
		}

		if (fLastcmd.equals("start")) { //$NON-NLS-1$
			fDebugTarget.breakpointHit(fDebugTarget.getLastFileName(), lineNumber);
		} else if (fLastcmd.equals("resume")) { //$NON-NLS-1$
			fDebugTarget.breakpointHit(fDebugTarget.getLastFileName(), lineNumber);
		} else if (fLastcmd.equals("suspend")) { //$NON-NLS-1$
			fDebugTarget.suspended(DebugEvent.CLIENT_REQUEST);
		} else if (fLastcmd.equals("stepReturn")) { //$NON-NLS-1$
			fDebugTarget.suspended(DebugEvent.STEP_RETURN);
		} else if (fLastcmd.equals("stepOver")) { //$NON-NLS-1$
			fDebugTarget.suspended(DebugEvent.STEP_OVER);
		} else if (fLastcmd.equals("stepInto")) { //$NON-NLS-1$
			fDebugTarget.suspended(DebugEvent.STEP_INTO);
		} else if (fLastcmd.equals("terminate")) { //$NON-NLS-1$
			// Shouldn't happen, try to shut down cleanly
			fRemoteDebugger.finish();
			fDebugTarget.terminated();
		} else if (fLastcmd.equals("breakpointAdded")) { //$NON-NLS-1$

		} else if (fLastcmd.equals("breakpointRemoved")) { //$NON-NLS-1$

		}

	}

	public void sessionEnded() {
		Logger.debugMSG("ServerDebugHandler: Starting sessionEnded()"); //$NON-NLS-1$
		super.sessionEnded();

	}

	public void connectionClosed() {
		Logger.debugMSG("ServerDebugHandler: Starting connectionClosed()"); //$NON-NLS-1$
		super.connectionClosed();
		fRemoteDebugger.finish();
		// if (fDebugTarget.isPHPCGI()) {
		// Logger.debugMSG("ServerDebugHandler: Calling Terminated() for PHP
		// CGI");
		Logger.debugMSG("ServerDebugHandler: Calling Terminated()"); //$NON-NLS-1$
		fDebugTarget.terminated();
		// }
	}

	public void handleScriptEnded() {
		try {
			if (fCodeCoverage) {
				IRemoteDebugger remoteDebugger = getRemoteDebugger();
				if (remoteDebugger instanceof RemoteDebugger) {
					fCodeCoverageData = ((RemoteDebugger) remoteDebugger).getCodeCoverageData();
				}
			}
		} finally {
			Logger.debugMSG("ServerDebugHandler: handleScriptEnded"); //$NON-NLS-1$
			try {
				Logger.debugMSG("ServerDebugHandler: Calling Terminate()"); //$NON-NLS-1$
				fDebugTarget.terminate();

			} catch (DebugException e1) {
				Logger.logException("ServerDebugHandler: terminate failed", e1); //$NON-NLS-1$
			}
		}
	}

	public void multipleBindOccured() {
		super.multipleBindOccured();
		Logger.log(Logger.WARNING, "ServerDebugHandler: Multiple Bind Occured"); //$NON-NLS-1$

		String errorMessage = PHPDebugCoreMessages.DebuggerDebugPortInUse_1;
		fRemoteDebugger.closeConnection();
		fDebugTarget.fireError(errorMessage, null);
		fDebugTarget.terminated();
	}

	public void parsingErrorOccured(DebugError debugError) {
		super.parsingErrorOccured(debugError);

		// resolve path
		String localFileName = ((RemoteDebugger) fRemoteDebugger).convertToLocalFilename(debugError.getFullPathName(),
				null, null);
		if (localFileName == null) {
			localFileName = debugError.getFullPathName();
		}
		debugError.setFileName(localFileName);

		if (fDebugTarget.getDebugErrors().add(debugError)) {
			Object[] listeners = fDebugTarget.getConsoleEventListeners().toArray();
			for (Object element : listeners) {
				((IPHPConsoleEventListener) element).handleEvent(debugError);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.model.SimpleDebugHandler#
	 * wrongDebugServer ()
	 */
	public void wrongDebugServer() {
		super.wrongDebugServer();
		fDebugTarget.fireError(PHPDebugCoreMessages.ServerDebugHandler_0, null);
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
		fDebugConnection = fDebugTarget.getDebugConnection();
		fRemoteDebugger = createRemoteDebugger();
		fDebugConnection.getCommunicationAdministrator().connectionEstablished();
	}

	public PHPDebugTarget getDebugTarget() {
		return fDebugTarget;
	}

	/**
	 * Returns code coverage data from the last debug session.
	 * 
	 * @return CodeCoverage data or <code>null</code> if there wasn't directive
	 *         to retreive code coverage data.
	 */
	public CodeCoverageData[] getLastCodeCoverageData() {
		return fCodeCoverageData;
	}

	protected IRemoteDebugger createRemoteDebugger() {
		return new RemoteDebugger(this, fDebugConnection);
	}

	protected boolean isUsingPathMapper() {
		return fUseLocalCopy;
	}

}
