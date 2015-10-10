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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentRequest;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.FileContentResponse;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.ResolveBlackList;

/**
 * Handle a server request for the delivery of the file content. This class is
 * used if debugger protocol is &gt;= 2006040902 New protocol provides new
 * message type (StartProcessFileNotification) which allows on-demand path
 * mapping and lazy breakpoint sending.
 * 
 * @author michael
 */
public class FileContentRequestCurrentHandler extends AbstractFileContentRequestHandler {

	private static final String EXCLUDED_EXTENSION = "phar"; //$NON-NLS-1$

	private int reqID;
	private String lastFileName;
	private String encoding;
	private PHPDebugTarget debugTarget;
	private boolean isFirstFileToDebug;
	private FileContentRequest contentRequest;

	public FileContentRequestCurrentHandler() {
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
		contentRequest = (FileContentRequest) request;
		reqID = contentRequest.getID();
		lastFileName = contentRequest.getFileName();
		encoding = contentRequest.getTransferEncoding();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.debug.core.debugger.handlers.IDebugRequestHandler#
	 * getResponseMessage()
	 */
	public IDebugResponseMessage getResponseMessage() {
		FileContentResponse response = new FileContentResponse();
		response.setID(reqID);
		try {
			byte[] content = null;
			if (!lastFileName.endsWith(EXCLUDED_EXTENSION)) {
				if (isFirstFileToDebug && PHPDebugPlugin.isDummyFile(lastFileName)) {
					content = getDummyContent();
					// Blacklist dummy.php file, so it won't be requested for
					// mapping:
					debugTarget.getContextManager().addToResolveBlacklist(new VirtualPath(lastFileName),
							ResolveBlackList.Type.FILE);
				} else {
					RemoteDebugger remoteDebugger = (RemoteDebugger) debugTarget.getRemoteDebugger();
					String localPath = remoteDebugger.convertToLocalFilename(lastFileName);
					if (localPath != null) {
						IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(localPath);
						if (resource != null) {
							IPath location = resource.getLocation();
							if (location != null) {
								content = getBytesFromURI(location.toFile().toURI());
							} else if (resource.exists()) {
								// probably RSE
								content = getBytesFromURI(resource.getLocationURI());
							}
						} else {
							File file = new File(localPath);
							if (file.exists()) {
								content = getBytesFromURI(file.toURI());
							}
						}
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
		} catch (NullPointerException e) {
			// No need to log it. The server will throw an error notification to
			// the console indicating that the file was not found.
		} catch (Throwable t) {
			Logger.logException("Fail to send the file content to the server", //$NON-NLS-1$
					t);
		}
		isFirstFileToDebug = false;
		return response;
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

	/**
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
			if (parentDirectory.endsWith(":\\")) { //$NON-NLS-1$
				parentDirectory += "\\"; //$NON-NLS-1$
			}
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

	/**
	 * Read and returns all the bytes from the given file URI
	 */
	private byte[] getBytesFromURI(URI uri) throws Exception {
		IFileStore fileStore = EFS.getStore(uri);
		IFileInfo fileInfo = fileStore.fetchInfo();
		long length = fileInfo.getLength();
		if (length > Integer.MAX_VALUE) {
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