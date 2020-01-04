/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.uri;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.urischeme.IUriSchemeHandler;

/**
 * Support for xdebug.file_link_format, possible usage:
 * xdebug://%f@%l
 * xdebug://%f:%l
 */
public class XDebugURISchemeHandler implements IUriSchemeHandler {

	final static private String SCHEME = "://"; //$NON-NLS-1$

	@Override
	public void handle(String uri) {
		String[] split = uri.split(SCHEME, 2);
		if (split.length != 2) {
			return;
		}
		String fileName = split[1];
		int pos = fileName.lastIndexOf('@');
		if (pos == -1) {
			pos = fileName.lastIndexOf(':');
		}
		int lineNumber = 0;
		if (pos > 0) {
			fileName = fileName.substring(0, pos);
			lineNumber = Integer.valueOf(fileName.substring(pos + 1));
		}
		IFileStore store = EFS.getLocalFileSystem().getStore(new Path(fileName));
		if (store.fetchInfo().exists() && !store.fetchInfo().isDirectory()) {
			Runnable run = new OpenFile(store, lineNumber);
			if (Display.getCurrent() != null) {
				run.run();
			} else {
				Display.getDefault().asyncExec(run);
			}

		}
	}

	private static class OpenFile implements Runnable {

		private IFileStore store;

		private int lineNumber;

		public OpenFile(IFileStore store, int lineNumber) {
			this.store = store;
			this.lineNumber = lineNumber;
		}

		@Override
		public void run() {
			IWorkbenchWindow window = Workbench.getInstance().getActiveWorkbenchWindow();
			try {
				IEditorPart editor = IDE.openEditorOnFileStore(window.getActivePage(), store);
				if (editor instanceof ITextEditor) {
					ITextEditor textEditor = (ITextEditor) editor;
					IDocument document = textEditor.getDocumentProvider().getDocument(editor.getEditorInput());
					int lineStart = document.getLineOffset(lineNumber);
					textEditor.selectAndReveal(lineStart, 0);
				}
			} catch (PartInitException e) {
				Logger.logException(e);
			} catch (BadLocationException e) {
				Logger.logException(e);
			}
		}

	}

}
