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
package org.eclipse.php.internal.ui.dialogs.saveFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;

/**
 * @author seva
 * 
 */
public class SaveFilesHandler {
	public static SaveFilesResult handle(IProject project, boolean autoSave,
			boolean promptAutoSave, IProgressMonitor monitor) {
		SaveFilesResult result = new SaveFilesResult();
		List dirtyEditors = getDirtyEditors(project);
		if (dirtyEditors == null || dirtyEditors.size() == 0) {
			result.setAccepted(true);
			return result;
		}
		if (!autoSave) {
			Display.getDefault().syncExec(
					new SaveFilesDialogRunnable(dirtyEditors, result,
							promptAutoSave));
		} else {
			result.setAccepted(true);
			result.setSaved(dirtyEditors);
		}
		List editorsToSave = result.getSaved();
		if (editorsToSave.size() > 0) {
			Display.getDefault().syncExec(
					new SaveFilesRunnable(editorsToSave, monitor));
		}
		if (monitor.isCanceled()) {
			result.setAccepted(false);
		}
		return result;
	}

	/**
	 * Retreive an array of IEditorParts representing all the dirty editors open
	 * for the files provided in the list.
	 * 
	 * @param files
	 *            A list of IFiles.
	 * @return An array of IEditorParts containing all the dirty editors for the
	 *         files in the list.
	 */
	public static List getDirtyEditors(IProject project) {
		List result = new ArrayList(0);
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int x = 0; x < pages.length; x++) {
				IEditorPart[] editors = pages[x].getDirtyEditors();
				for (int z = 0; z < editors.length; z++) {
					IEditorPart ep = editors[z];
					IEditorInput input = ep.getEditorInput();
					if (input instanceof IFileEditorInput) {
						IFileEditorInput fileInput = (IFileEditorInput) input;
						IFile file = fileInput.getFile();
						if (project != null && !(file.getProject() == project)) {
							continue;
						}
						result.add(ep);
					}
				}
			}
		}
		return result;
	}

	protected static class SaveFilesRunnable implements Runnable {
		List dirtyEditors;
		IProgressMonitor monitor;

		public SaveFilesRunnable(List dirtyEditors, IProgressMonitor monitor) {
			this.dirtyEditors = dirtyEditors;
			this.monitor = monitor;
		}

		public void run() {
			monitor.beginTask(PHPUIMessages.SaveFilesHandler_0, dirtyEditors
					.size()); 
			for (Iterator i = dirtyEditors.iterator(); i.hasNext();) {
				if (monitor.isCanceled()) {
					return;
				}
				((IEditorPart) i.next()).doSave(monitor);
				monitor.worked(1);
			}
			monitor.done();
		}
	}

	protected static class SaveFilesDialogRunnable implements Runnable {
		List dirtyEditors;
		SaveFilesResult result;
		boolean promptAutoSave;

		public SaveFilesDialogRunnable(List dirtyEditors,
				SaveFilesResult result, boolean promptAutoSave) {
			this.dirtyEditors = dirtyEditors;
			this.result = result;
			this.promptAutoSave = promptAutoSave;
		}

		public void run() {
			SaveFilesDialog sfDialog = new SaveFilesDialog(Display.getCurrent()
					.getActiveShell(), dirtyEditors, result, promptAutoSave);
			if (sfDialog.open() == Window.OK) {
				result.setAccepted(true);
				result.setSaved(Arrays.asList(sfDialog.getResult()));
			}
		}

	}

	public static class SaveFilesResult {
		boolean autoSave;
		boolean accepted;
		List saved = new ArrayList();

		public boolean isAutoSave() {
			return autoSave;
		}

		public void setAutoSave(boolean autoSave) {
			this.autoSave = autoSave;
		}

		public void setSaved(List saved) {
			this.saved = saved;
		}

		public void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}

		public boolean isAccepted() {
			return accepted;
		}

		public List getSaved() {
			return saved;
		}

		public SaveFilesResult() {
		}

		public SaveFilesResult(List saved, boolean accepted) {
			this();
			setSaved(saved);
			setAccepted(accepted);
		}

		public SaveFilesResult(List saved, boolean accepted, boolean autoSave) {
			this(saved, accepted);
			setAutoSave(autoSave);
			setAccepted(accepted);
		}
	}
}