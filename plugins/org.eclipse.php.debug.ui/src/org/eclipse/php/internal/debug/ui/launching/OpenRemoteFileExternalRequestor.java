/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.launching;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.debug.core.pathmapper.*;
import org.eclipse.php.internal.debug.core.zend.communication.IRemoteFileContentRequestor;
import org.eclipse.php.internal.debug.core.zend.communication.RemoteFileStorage;
import org.eclipse.php.internal.debug.ui.editor.RemoteFileStorageEditorInput;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * Open remote file content requestor that handles external "open file"
 * requests.
 */
public class OpenRemoteFileExternalRequestor implements IRemoteFileContentRequestor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.communication.
	 * IRemoteFileContentRequestor#fileContentReceived(byte[], java.lang.String,
	 * java.lang.String, java.lang.String, int)
	 */
	public void fileContentReceived(final byte[] content, final String serverAddress, final String originalURL,
			final String fileName, final int lineNumber) {
		final IEditorInput editorInput = getEditorInput(content, serverAddress, originalURL, fileName);
		if (editorInput == null)
			return;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				openEditor(editorInput, lineNumber);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.communication.
	 * IRemoteFileContentRequestor#requestCompleted(java.lang.Exception)
	 */
	public void requestCompleted(Exception e) {
		// ignore
	}

	private IEditorInput getEditorInput(byte[] content, String serverAddress, String originalURL, String remoteFile) {
		PathMapper mapper = null;
		PathEntry entry = null;
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// Check if we have local file that is a direct match of the remote one
		final IFile file = root.getFileForLocation(new Path(remoteFile));
		if (file != null) {
			return new FileEditorInput(file);
		}
		// Check for a server address to find a perfect match.
		Server serverMatch = null;
		try {
			String serverURL = "http://" + serverAddress;
			// Try perfect match first
			serverMatch = ServersManager.findByURL(serverURL);
			if (serverMatch != null) {
				mapper = PathMapperRegistry.getByServer(serverMatch);
				entry = mapper.getLocalFile(remoteFile);
				// If no mapping, try to find one
				if (entry == null) {
					LocalFileSearchEngine searchEngine = new LocalFileSearchEngine();
					LocalFileSearchResult searchResult = searchEngine.find(root, remoteFile, serverMatch.getUniqueId());
					if (searchResult == null) {
						File localFile = new File(remoteFile);
						if (localFile.exists()) {
							IPath path = new Path(remoteFile);
							String parentPath = path.removeLastSegments(1).toOSString();
							IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(parentPath));
							fileStore = fileStore.getChild(path.lastSegment());
							return new FileStoreEditorInput(fileStore);
						} else {
							return new RemoteFileStorageEditorInput(
									new RemoteFileStorage(content, remoteFile, originalURL));
						}
					}
					if (searchResult.getStatus().isOK() && searchResult.getPathEntry() == null) {
						return new RemoteFileStorageEditorInput(
								new RemoteFileStorage(content, remoteFile, originalURL));
					} else if (!searchResult.getStatus().isOK()) {
						return null;
					} else {
						entry = searchResult.getPathEntry();
					}
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
			return new FileEditorInput(root.getFile(new Path(entry.getPath())));
		}
		return new RemoteFileStorageEditorInput(new RemoteFileStorage(content, remoteFile, originalURL));

	}

	/**
	 * Opens editor with the use of provided editor input and line number.
	 * 
	 * @param editorInput
	 * @param lineNumber
	 */
	private void openEditor(final IEditorInput editorInput, final int lineNumber) {
		try {
			IWorkbenchPage p = DLTKUIPlugin.getActivePage();
			if (p != null) {
				IEditorPart editorPart = p.openEditor(editorInput, EditorUtility.getEditorID(editorInput, null), true);
				if (editorPart != null) {
					if (lineNumber > 0 && editorPart instanceof StructuredTextEditor) {
						IRegion region = ((StructuredTextEditor) editorPart).getTextViewer().getDocument()
								.getLineInformation(lineNumber - 1);
						if (region != null) {
							EditorUtility.revealInEditor(editorPart, region.getOffset(), region.getLength());
						}
					}
					if (editorPart.getSite() != null) {
						editorPart.getSite().getShell().forceActive();
					}
				}
			}
		} catch (PartInitException e) {
			DebugPlugin.log(e);
		} catch (BadLocationException e) {
			DebugPlugin.log(e);
		}

	}

}
