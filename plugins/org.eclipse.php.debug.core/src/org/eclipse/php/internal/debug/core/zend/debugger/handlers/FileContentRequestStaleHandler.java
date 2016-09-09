/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import java.io.*;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.resources.WorkspaceRoot;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping.MappingSource;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapperRegistry;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentRequest;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentResponse;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * Handle a server request for the delivery of the file content. This class is
 * used if debugger protocol is &lt; 2006040902
 */
@SuppressWarnings("restriction")
public class FileContentRequestStaleHandler extends AbstractFileContentRequestHandler {

	private int reqID;
	private String lastFileName;
	private String encoding;
	private PHPDebugTarget debugTarget;
	private boolean isFirstFileToDebug;
	private FileContentRequest contentRequest;

	public FileContentRequestStaleHandler() {
		isFirstFileToDebug = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler#handle(
	 * org.eclipse.php.debug.core.debugger.messages.IDebugMessage,
	 * org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget)
	 */
	public void handle(IDebugMessage request, PHPDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
		RemoteDebugger remoteDebugger = (RemoteDebugger) debugTarget.getRemoteDebugger();
		boolean isWebServerDebugger = Boolean.toString(true)
				.equals(debugTarget.getLaunch().getAttribute(IDebugParametersKeys.WEB_SERVER_DEBUGGER));
		contentRequest = (FileContentRequest) request;
		reqID = contentRequest.getID();
		String currentFileName = contentRequest.getFileName();
		debugTarget.setLastFileName(currentFileName);
		String debugType = getDebugType();
		lastFileName = currentFileName;
		encoding = contentRequest.getTransferEncoding();
		// If is full Path
		if (VirtualPath.isAbsolute(lastFileName)) {
			Path testWSPath = new Path(lastFileName);
			IFile testWSFile = null;
			if (testWSPath.segmentCount() > 1) {
				testWSFile = ResourcesPlugin.getWorkspace().getRoot().getFile(testWSPath);
			}
			if (isFirstFileToDebug && testWSFile != null && testWSFile.exists()) {
				// this is an RSE file, do nothing
			}
			// not a Dummy file request
			else if (isDummyFile()) {
				return;
			}
			// Exe script
			else if (debugType.equals(IDebugParametersKeys.PHP_EXE_SCRIPT_DEBUG)) {
				lastFileName = null;// this will inform the debugger to use its
									// own copy when getResponseMessage() is
									// called
				isFirstFileToDebug = false;
				return;
			}
			// web script OR web page OR toolbar debug
			else if (isWebServerDebugger) {
				if (isFirstFileToDebug) {// we already checked this it not the
											// Dummy file
					mapFirstFile(currentFileName);
					if (debugType.equals(IDebugParametersKeys.PHP_WEB_SCRIPT_DEBUG)) {
						VirtualPath remotePath = new VirtualPath(currentFileName);
						remotePath.removeLastSegment();
						remoteDebugger.setCurrentWorkingDirectory(remotePath.toString());
					}
				}
				lastFileName = remoteDebugger.convertToLocalFilename(currentFileName);
			}
		}
		// Other - Relative,RSE
		else {
			lastFileName = remoteDebugger.convertToLocalFilename(currentFileName);
		}
		// For each file request in Debug mode, send breakpoint request to the
		// debugger
		if (lastFileName != null && debugTarget.getLaunch().getLaunchMode().equals(ILaunchManager.DEBUG_MODE)) {
			addBreakPoints(debugTarget, currentFileName);
		}
		isFirstFileToDebug = false;
	}

	private void addBreakPoints(PHPDebugTarget debugTarget, String currentFileName) {
		// send synchronized Breakpoint request
		IBreakpointManager breakpointManager = debugTarget.getBreakpointManager();
		if (!breakpointManager.isEnabled()) {
			return;
		}
		IBreakpoint[] breakpoints = breakpointManager.getBreakpoints(IPHPDebugConstants.ID_PHP_DEBUG_CORE);
		for (IBreakpoint element : breakpoints) {
			if (element instanceof IPHPExceptionBreakpoint) {
				// Not supported
				continue;
			}
			IResource resourceWithBreakPoint = element.getMarker().getResource();
			String resourcePathName = ""; //$NON-NLS-1$
			// handle a breakpoint on external file
			if (resourceWithBreakPoint instanceof WorkspaceRoot) {// external
				try {
					resourcePathName = element.getMarker()
							.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY).toString();
				} catch (CoreException ce) {
					PHPDebugPlugin.log(ce);
					return;
				}
			} else {// workspace
				IPath resourceLocation = resourceWithBreakPoint.getLocation();
				if (resourceLocation == null) {
					resourcePathName = resourceWithBreakPoint.getLocationURI().toString();
				} else {
					resourcePathName = resourceLocation.toOSString();
				}
			}
			String comparablePathName = ""; //$NON-NLS-1$
			if (new File(lastFileName).exists()) {
				comparablePathName = lastFileName;
			} else {
				IFile tmpIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(lastFileName));
				IPath tmpLocation = tmpIFile.getLocation();
				if (tmpIFile.exists()) {
					if (tmpLocation == null) {
						comparablePathName = tmpIFile.getLocationURI().toString();
					} else {
						comparablePathName = tmpLocation.toOSString();
					}
				}
			}
			if (new VirtualPath(resourcePathName).equals(new VirtualPath(comparablePathName))) {
				// send break point
				try {
					PHPConditionalBreakpoint phpBP = (PHPConditionalBreakpoint) element;
					Breakpoint runtimeBreakpoint = phpBP.getRuntimeBreakpoint();

					int lineNumber = (Integer) element.getMarker().getAttribute(IMarker.LINE_NUMBER);
					Breakpoint tmpBreakpoint = new Breakpoint(currentFileName, lineNumber);
					if (tmpBreakpoint.isEnable()) {
						debugTarget.getRemoteDebugger().addBreakpoint(tmpBreakpoint);
					}
					runtimeBreakpoint.setID(tmpBreakpoint.getID());
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}
	}

	private void mapFirstFile(String currentFileName) {
		PathEntry pathEntry = null;
		String debugFileName = null;
		ILaunchConfiguration launchConfiguration = debugTarget.getLaunch().getLaunchConfiguration();
		PathMapper pathMapper = PathMapperRegistry.getByLaunchConfiguration(launchConfiguration);
		if (pathMapper != null) {
			try {
				debugFileName = launchConfiguration.getAttribute(IPHPDebugConstants.ATTR_FILE, (String) null);
			} catch (CoreException e) {
				Logger.logException(e);
				return;
			}
			if (debugFileName != null) {
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(debugFileName);
				if (resource instanceof IFile) {
					pathEntry = new PathEntry(debugFileName, Type.WORKSPACE, resource.getParent());
				} else if (new File(debugFileName).exists()) {
					pathEntry = new PathEntry(debugFileName, Type.EXTERNAL, new File(debugFileName).getParentFile());
				}
			}
			if (pathEntry != null) {
				// Map remote file to the map point:
				pathMapper.addEntry(currentFileName, pathEntry, MappingSource.ENVIRONMENT);
				PathMapperRegistry.storeToPreferences();
			}
		}
	}

	public IDebugResponseMessage getResponseMessage() {
		FileContentResponse response = new FileContentResponse();
		response.setID(reqID);
		try {
			byte[] content = null;
			if (isDummyFile()) {
				content = getDummyContent();
			} else {
				IResource member = null;
				if (lastFileName != null) {
					member = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(lastFileName));
				}
				if (member != null) {
					IPath location = member.getLocation();
					if (location != null) {
						File file = location.toFile();
						content = getBytesFromFile(file);
					} else if (member.exists()) {
						// probably RSE
						URI uri = member.getLocationURI();
						content = getBytesFromURI(uri);
					}
				} else if (lastFileName != null) {
					// try to get the file content directly from the file system
					File file = new File(lastFileName);
					if (file.exists()) {
						content = getBytesFromFile(file);
					}
				}
			}
			if (content == null) {
				content = new byte[0];
			}
			setResponseContent(response, contentRequest, content);
		} catch (FileNotFoundException e) {
			// No need to log it. The server will throw an error notification to
			// the console indicating that the file was not found.
		} catch (NullPointerException npe) {
			// No need to log it. The server will throw an error notification to
			// the console indicating that the file was not found.
		} catch (Throwable t) {
			Logger.logException("Fail to send the file content to the server", //$NON-NLS-1$
					t);
		}
		return response;
	}

	/*
	 * Returns if the current file name is actually a dummy file.
	 */
	private boolean isDummyFile() {
		return lastFileName != null && lastFileName.endsWith(getDummyFileName());
	}

	/*
	 * Returns the dummy file name that was set in the preferences. Cache the
	 * name through the lifecycle of this instance.
	 */
	private String getDummyFileName() {
		return Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.ZEND_DEBUG_DUMMY_FILE, "", //$NON-NLS-1$
				null);
	}

	private String getDebugType() {
		String debugType = ""; //$NON-NLS-1$
		try {
			debugType = debugTarget.getLaunch().getLaunchConfiguration()
					.getAttribute(IDebugParametersKeys.PHP_DEBUG_TYPE, ""); //$NON-NLS-1$
		} catch (CoreException ce) {
			PHPDebugPlugin.log(ce);
		}
		return debugType;
	}

	/*
	 * Returns the dummy content for the current file name.
	 */
	private byte[] getDummyContent() {
		String originalFileName = ""; //$NON-NLS-1$
		try {
			ILaunchConfiguration launchConfiguration = debugTarget.getLaunch().getLaunchConfiguration();
			// The dummy request should be made on the full path of the debugged
			// file.
			originalFileName = launchConfiguration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, ""); //$NON-NLS-1$
		} catch (CoreException e) {
		}
		StringBuilder contentBuf = new StringBuilder("<?php "); //$NON-NLS-1$
		File originalFile = new File(originalFileName);
		if (!originalFileName.startsWith("\\\\") && originalFile.exists() //$NON-NLS-1$
				&& getDebugType().equals(IDebugParametersKeys.PHP_EXE_SCRIPT_DEBUG)) {
			String parentDirectory = originalFile.getParentFile().getAbsolutePath();
			parentDirectory = parentDirectory.replaceAll("\\\\", "\\\\\\\\"); //$NON-NLS-1$ //$NON-NLS-2$
			contentBuf.append("chdir('").append(parentDirectory).append("'); "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		originalFileName = originalFileName.replaceAll("\\\\", "\\\\\\\\"); //$NON-NLS-1$ //$NON-NLS-2$
		contentBuf.append("include('").append(originalFileName) //$NON-NLS-1$
				.append("'); ?>"); //$NON-NLS-1$
		String content = contentBuf.toString();
		if (encoding != null) {
			try {
				return content.getBytes(encoding);
			} catch (UnsupportedEncodingException e) {
				Logger.logException(
						"Failed to create dummy content in the '" //$NON-NLS-1$
								+ encoding + "' encoding. \nCreating with the default encoding.", //$NON-NLS-1$
						e);
			}
		}
		return content.getBytes();
	}

	// Read and returns all the bytes from the given file.
	private byte[] getBytesFromFile(File file) throws Exception {
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// the file is too big
			throw new Exception("The requested file '" + lastFileName + "' is too big"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// TODO - There is no handle of file encoding!
		byte[] bytes = new byte[(int) length];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		in.readFully(bytes);
		in.close();
		return bytes;
	}

	// Read and returns all the bytes from the given file URI.
	private byte[] getBytesFromURI(URI uri) throws Exception {
		IFileStore fileStore = EFS.getStore(uri);
		IFileInfo fileInfo = fileStore.fetchInfo();
		long length = fileInfo.getLength();
		if (length > Integer.MAX_VALUE) {
			// the file is too big
			throw new Exception("The requested file '" + lastFileName + "' is too big"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// TODO - There is no handle of file encoding!
		byte[] bytes = new byte[(int) length];
		InputStream openInputStream = fileStore.openInputStream(EFS.NONE, null);
		DataInputStream in = new DataInputStream(openInputStream);
		in.readFully(bytes);
		in.close();
		return bytes;
	}

}