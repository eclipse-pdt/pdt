package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
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

		RemoteDebugger remoteDebugger = (RemoteDebugger) debugTarget.getRemoteDebugger();
		ILaunchConfiguration launchConfiguration = debugTarget.getLaunch().getLaunchConfiguration();

		debugTarget.setLastFileName(remoteFileName);

		boolean isWebServerDebugger = Boolean.toString(true).equals(debugTarget.getLaunch().getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER));
		String debugType = ""; //$NON-NLS-1$
		try {
			debugType = launchConfiguration.getAttribute(IDebugParametersKeys.PHP_DEBUG_TYPE, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			PHPDebugPlugin.log(e);
		}

		String localPath = null;
		if (isFirstFileToDebug) { // we suppose that we always get full path here
			if (isWebServerDebugger) {
				PathEntry pathEntry = debugTarget.mapFirstDebugFile(remoteFileName);
				if (pathEntry != null) {
					localPath = pathEntry.getResolvedPath();
				}

				// set current working directory to the current script directory on debugger side
				if (debugType.equals(IDebugParametersKeys.PHP_WEB_SCRIPT_DEBUG)) {
					VirtualPath remotePath = new VirtualPath(remoteFileName);
					remotePath.removeLastSegment();
					remoteDebugger.setCurrentWorkingDirectory(remotePath.toString());
				}
			}
		} else {
			localPath = remoteDebugger.convertToLocalFilename(remoteFileName);
		}

		// send found breakpoints with remote file name
		if (localPath != null && ILaunchManager.DEBUG_MODE.equals(debugTarget.getLaunch().getLaunchMode())) {
			IBreakpoint[] breakPoints = findBreakpoints (localPath, debugTarget);

			for (IBreakpoint bp : breakPoints) {
				try {
					if (bp.isEnabled()) {
						PHPConditionalBreakpoint phpBP = (PHPConditionalBreakpoint) bp;
						Breakpoint runtimeBreakpoint = phpBP.getRuntimeBreakpoint();
						int lineNumber = (Integer) bp.getMarker().getAttribute(IMarker.LINE_NUMBER);
						Breakpoint tempBreakpoint = new Breakpoint(remoteFileName, lineNumber);
						debugTarget.getRemoteDebugger().addBreakpoint(tempBreakpoint);
						runtimeBreakpoint.setID(tempBreakpoint.getID());
					}
				} catch (CoreException e) {
					PHPDebugPlugin.log(e);
				}
			}
		}

		isFirstFileToDebug = false;
	}

	protected IBreakpoint[] findBreakpoints (String localPath, PHPDebugTarget debugTarget) {
		IBreakpointManager breakpointManager = debugTarget.getBreakpointManager();
		IBreakpoint[] breakpoints = breakpointManager.getBreakpoints(IPHPDebugConstants.ID_PHP_DEBUG_CORE);
		List<IBreakpoint> l = new LinkedList<IBreakpoint>();
		for (IBreakpoint bp : breakpoints) {
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(localPath);
			if (bp.getMarker().getResource().equals(resource)) {
				l.add(bp);
			}
			try {
				Object secondaryId = bp.getMarker().getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
				if (secondaryId != null) {
					if (new VirtualPath(localPath).equals(new VirtualPath((String)secondaryId))) {
						l.add(bp);
					}
				}
			} catch (CoreException e) {
				PHPDebugPlugin.log(e);
			}
		}
		return l.toArray(new IBreakpoint[l.size()]);
	}
}
