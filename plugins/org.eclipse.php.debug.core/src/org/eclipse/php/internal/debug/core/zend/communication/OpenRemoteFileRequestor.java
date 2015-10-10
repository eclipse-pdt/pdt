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
package org.eclipse.php.internal.debug.core.zend.communication;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.debug.core.pathmapper.*;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.DebugSessionStartedNotification;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This file content requester handles received file open request with the use
 * of path mapping facilities.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class OpenRemoteFileRequestor implements IFileContentRequestor {

	private static final String OPTION_SERVER_ADDRESS = "server_address"; //$NON-NLS-1$

	private DebugSessionStartedNotification notification;

	/**
	 * Creates new requester.
	 * 
	 * @param notification
	 */
	public OpenRemoteFileRequestor(DebugSessionStartedNotification notification) {
		this.notification = notification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.debug.core.communication.IFileContentRequester#
	 * fileContentReceived(byte[], java.lang.String, int)
	 */
	public void fileContentReceived(final byte[] content, final String fileName, final int lineNumber) {
		final String localFile = getLocalFile(content, fileName);
		if (localFile == null)
			return;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				openEditor(localFile, lineNumber);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.debug.core.communication.IFileContentRequester#
	 * requestCompleted(java.lang.Exception)
	 */
	public void requestCompleted(Exception e) {
		// ignore
	}

	/**
	 * Finds and returns the appropriate editor input.
	 * 
	 * @param content
	 * @param remoteFile
	 * @return editor input
	 */
	private String getLocalFile(byte[] content, String remoteFile) {
		PathMapper mapper = null;
		PathEntry entry = null;
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// Check if we have local file that is a direct match of the remote one
		final IFile file = root.getFileForLocation(new Path(remoteFile));
		if (file != null) {
			return file.getLocation().toOSString();
		}
		// Check for a server address to find a perfect match.
		String serverAddress = extractParameterFromQuery(notification.getOptions(), OPTION_SERVER_ADDRESS);
		try {
			String serverURL = "http://" + serverAddress; //$NON-NLS-1$
			// Try perfect match first
			Server serverMatch = ServersManager.findByURL(serverURL);
			if (serverMatch != null) {
				mapper = PathMapperRegistry.getByServer(serverMatch);
				entry = mapper.getLocalFile(remoteFile);
				// If no mapping, try to find one
				if (entry == null) {
					LocalFileSearchEngine searchEngine = new LocalFileSearchEngine();
					LocalFileSearchResult searchResult = searchEngine.find(root, remoteFile, serverMatch.getUniqueId());
					if (searchResult == null) {
						// Check if file is available locally (outside the
						// workspace)
						File localFile = new File(remoteFile);
						if (localFile.exists())
							return remoteFile;
						// No match - open info dialog
						openNoMatchDialog(remoteFile);
						return null;
					}
					entry = searchResult.getPathEntry();
				}
			} else {
				// Check all servers
				for (Server server : ServersManager.getServers()) {
					mapper = PathMapperRegistry.getByServer(server);
					entry = mapper.getLocalFile(remoteFile);
					if (entry != null)
						break;
				}
			}
		} catch (InterruptedException e) {
			DebugPlugin.log(e);
		}
		if (entry != null) {
			return entry.getResolvedPath();
		}
		return null;

	}

	/**
	 * Opens editor with the use of provided editor input and line number.
	 * 
	 * @param editorInput
	 * @param lineNumber
	 */
	private void openEditor(final String localFile, final int lineNumber) {
		try {
			EditorUtility.openLocalFile(localFile, lineNumber);
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			if (shell != null) {
				shell.forceActive();
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/**
	 * Extracts parameter value from the query string
	 * 
	 * @param query
	 *            The original query string
	 * @param parameter
	 *            Parameter name
	 * @return parameter value
	 */
	private String extractParameterFromQuery(String query, String parameter) {
		int queryStartIndex = query.indexOf(parameter + "="); //$NON-NLS-1$
		if (queryStartIndex > -1) {
			String value = query.substring(queryStartIndex + parameter.length() + 1);
			int paramSeparatorIndex = value.indexOf('&');
			if (paramSeparatorIndex > -1) {
				value = value.substring(0, paramSeparatorIndex);
			}
			return value;
		}
		return null;
	}

	private void openNoMatchDialog(final String remoteFile) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				if (shell != null) {
					shell.forceActive();
				}
				MessageDialog.openInformation(shell, Messages.OpenRemoteFileRequestor_Open_remote_file_request,
						MessageFormat.format(Messages.OpenRemoteFileRequestor_No_match_could_be_found, remoteFile));
			}
		});
	}

}
