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
package org.eclipse.php.internal.debug.ui.editor;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.debug.core.zend.communication.IRemoteFileContentRequestor;
import org.eclipse.php.internal.debug.core.zend.communication.RemoteFileStorage;
import org.eclipse.ui.*;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * Default requestor for opening remote files.
 */
public class OpenRemoteFileContentRequestor implements IRemoteFileContentRequestor {

	public void fileContentReceived(final byte[] content, final String serverAddress, final String originalURL,
			final String fileName, final int lineNumber) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				openInEditor(content, originalURL, fileName, lineNumber);
			}
		});
	}

	private void openInEditor(final byte[] content, final String originalURL, final String fileName,
			final int lineNumber) {
		try {
			IEditorInput editorInput = new RemoteFileStorageEditorInput(
					new RemoteFileStorage(content, fileName, originalURL));
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

	public void requestCompleted(Exception e) {
		// Nothing to do here...
	}
}
