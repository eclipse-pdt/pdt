/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.phar.PharPath;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.ContinueProcessFileNotification;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.StartProcessFileNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

public class StartProcessFileNotificationHandler implements IDebugMessageHandler {

	protected boolean isFirstFileToDebug;

	public StartProcessFileNotificationHandler() {
		isFirstFileToDebug = true;
	}

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {

		// do everything we need in order to prepare for processing current file
		StartProcessFileNotification notification = (StartProcessFileNotification) message;
		String remoteFileName = notification.getFileName();

		prepareForProcessing(remoteFileName, debugTarget);

		// send notification to tell debugger to continue processing file
		RemoteDebugger remoteDebugger = (RemoteDebugger) debugTarget.getRemoteDebugger();
		remoteDebugger.sendCustomNotification(new ContinueProcessFileNotification());
	}

	protected void prepareForProcessing(String remoteFileName, PHPDebugTarget debugTarget) {

		if (isFirstFileToDebug && PHPDebugPlugin.isDummyFile(remoteFileName)) {
			debugTarget.setLastFileName(remoteFileName);
			return;
		}

		RemoteDebugger remoteDebugger = (RemoteDebugger) debugTarget.getRemoteDebugger();
		ILaunchConfiguration launchConfiguration = debugTarget.getLaunch().getLaunchConfiguration();

		debugTarget.setLastFileName(remoteFileName);

		boolean isWebServerDebugger = Boolean.toString(true)
				.equals(debugTarget.getLaunch().getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER));
		boolean isBuiltinServerDebugger = Boolean.toString(true)
				.equals(debugTarget.getLaunch().getAttribute(IDebugParametersKeys.BUILTIN_SERVER_DEBUGGER));

		String debugType = ""; //$NON-NLS-1$
		try {
			debugType = launchConfiguration.getAttribute(IDebugParametersKeys.PHP_DEBUG_TYPE, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			PHPDebugPlugin.log(e);
		}

		if (isFirstFileToDebug) { // we suppose that we always get full path
			// here
			if (isWebServerDebugger || isBuiltinServerDebugger) {
				debugTarget.mapFirstDebugFile(remoteFileName);

				// set current working directory to the current script directory
				// on debugger side
				if (debugType.equals(IDebugParametersKeys.PHP_WEB_SCRIPT_DEBUG)) {
					VirtualPath remotePath = new VirtualPath(remoteFileName);
					remotePath.removeLastSegment();
					remoteDebugger.setCurrentWorkingDirectory(remotePath.toString());
				}
			}
			debugTarget.addBreakpointFiles(debugTarget.getProject());
		}

		String localPath = remoteDebugger.convertToLocalFilename(remoteFileName);

		// send found breakpoints with remote file name
		if (localPath != null && ILaunchManager.DEBUG_MODE.equals(debugTarget.getLaunch().getLaunchMode())) {
			IBreakpoint[] breakPoints = findBreakpoints(localPath, debugTarget);
			for (IBreakpoint bp : breakPoints) {
				try {
					if (bp.isEnabled()) {

						PHPConditionalBreakpoint phpBP = (PHPConditionalBreakpoint) bp;
						Breakpoint runtimeBreakpoint = phpBP.getRuntimeBreakpoint();

						int lineNumber = (Integer) bp.getMarker().getAttribute(IMarker.LINE_NUMBER);
						int bpID = runtimeBreakpoint.getID();
						int bpType = runtimeBreakpoint.getType();
						int bpLifeTime = runtimeBreakpoint.getLifeTime();
						Breakpoint bpToSend = new Breakpoint(remoteFileName, lineNumber);
						bpToSend.setID(bpID);
						bpToSend.setType(bpType);
						bpToSend.setLifeTime(bpLifeTime);
						bpToSend.setConditionalFlag(runtimeBreakpoint.getConditionalFlag());
						bpToSend.setExpression(runtimeBreakpoint.getExpression());

						debugTarget.getRemoteDebugger().addBreakpoint(bpToSend);
						runtimeBreakpoint.setID(bpToSend.getID());
					}
				} catch (CoreException e) {
					PHPDebugPlugin.log(e);
				}
			}
		}

		isFirstFileToDebug = false;
	}

	protected IBreakpoint[] findBreakpoints(String processFileLocation, PHPDebugTarget debugTarget) {
		IBreakpointManager breakpointManager = debugTarget.getBreakpointManager();
		if (!breakpointManager.isEnabled()) {
			return new IBreakpoint[0];
		}
		IBreakpoint[] breakpoints = breakpointManager.getBreakpoints(IPHPDebugConstants.ID_PHP_DEBUG_CORE);
		List<IBreakpoint> matches = new LinkedList<IBreakpoint>();
		for (IBreakpoint bp : breakpoints) {
			if (bp instanceof IPHPExceptionBreakpoint || PharPath.isPharPath(new Path(processFileLocation))) {
				/*
				 * Not supported by Zend Debugger engine - exception breakpoints
				 * and handling breakpoints in PHAR files.
				 */
				continue;
			}
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(processFileLocation);
			/*
			 * Check if breakpoint marker resource is the same as the one found
			 * for file location that should be processed.
			 */
			if (bp.getMarker().getResource().equals(resource)) {
				matches.add(bp);
				continue;
			}
			try {
				/*
				 * If previous check failed, try to establish if PHP breakpoint
				 * file location is pointing to the same file as the one that
				 * should be processed. Symbolic links will also be resolved.
				 */
				String bpMarkerFileLocation = bp.getMarker().getResource().getLocation().toOSString();
				String phpBpFileLocation = ((PHPLineBreakpoint) bp).getRuntimeBreakpoint().getFileName();
				if (FileUtils.isSameFile(bpMarkerFileLocation, processFileLocation)
						|| FileUtils.isSameFile(phpBpFileLocation, processFileLocation)) {
					/*
					 * If there is a debug target related project then we can
					 * filter out possible breakpoint duplicates (in case of
					 * symbolic links)
					 */
					IProject debugProject = PHPLaunchUtilities.getProject(debugTarget);
					IProject bpProject = bp.getMarker().getResource().getProject();
					if (debugProject == null || bpProject == null || debugProject.equals(bpProject)) {
						matches.add(bp);
						continue;
					}
				}
			} catch (Exception e) {
				/*
				 * Ignore as some path descriptors might be illegal for this
				 * check e.g. DLTK external library scripts (see next step).
				 */
			}
			/*
			 * Breakpoint may also correspond to a PHP script from project
			 * external libraries. Try to check it as well.
			 */
			String secondaryId = null;
			try {
				secondaryId = (String) bp.getMarker()
						.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
			} catch (CoreException e) {
				// Should never be thrown, anyway...
				Logger.logException(e);
			}
			if (secondaryId != null) {
				IPath path = Path.fromPortableString(secondaryId);
				if ((path.getDevice() == null) && (path.toString().startsWith("org.eclipse.dltk"))) { //$NON-NLS-1$
					String fullPathString = path.toString();
					String absolutePath = fullPathString.substring(fullPathString.indexOf(':') + 1);
					path = Path.fromPortableString(absolutePath);
				} else {
					path = EnvironmentPathUtils.getLocalPath(path);
				}
				secondaryId = path.toString();
				if ((VirtualPath.isAbsolute(processFileLocation)
						&& new VirtualPath(processFileLocation).equals(new VirtualPath(secondaryId)))
						|| (resource != null && resource.getLocation() != null
								&& secondaryId.equals(resource.getLocation().toString()))) {
					matches.add(bp);
				}
			}
		}
		return matches.toArray(new IBreakpoint[matches.size()]);
	}
}
