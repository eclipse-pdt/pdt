/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.ui.dialogs.saveFiles;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author seva
 *
 */
public class SaveFilesHandler {
	public static SaveFilesResult handle(IProject project, boolean autoSave, boolean promptAutoSave, IProgressMonitor monitor) {
		SaveFilesResult result = new SaveFilesResult();
		IEditorPart[] dirtyEditors = getDirtyEditors(project);
		if (dirtyEditors == null || dirtyEditors.length == 0) {
			result.setSaved(true);
			return result;
		}
		if (!autoSave) {
			Display.getDefault().syncExec(new SaveFilesDialogRunnable(dirtyEditors, result, promptAutoSave));
		} else {
			result.setSaved(true);
		}
		if (result.isSaved()) {
			Display.getDefault().syncExec(new SaveFilesRunnable(dirtyEditors, monitor));
		}
		if (monitor.isCanceled()) {
			result.setSaved(false);
		}
		return result;
	}

	/**
	 * Retreive an array of IEditorParts representing all the dirty
	 * editors open for the files provided in the list.
	 * 
	 * @param files
	 * 			A list of IFiles.
	 * @return
	 * 			An array of IEditorParts containing all the dirty editors for the files in the list.
	 */
	public static IEditorPart[] getDirtyEditors(IProject project) {
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
						if (file.getProject() == project)
							result.add(ep);
					}
				}
			}
		}
		return (IEditorPart[]) result.toArray(new IEditorPart[result.size()]);
	}

	protected static class SaveFilesRunnable implements Runnable {
		IEditorPart[] dirtyEditors;
		IProgressMonitor monitor;

		public SaveFilesRunnable(IEditorPart[] dirtyEditors, IProgressMonitor monitor) {
			this.dirtyEditors = dirtyEditors;
			this.monitor = monitor;
		}

		public void run() {
			int numDirtyEditors = dirtyEditors.length;
			monitor.beginTask("Saving edited files", numDirtyEditors);
			for (int i = 0; i < numDirtyEditors; i++) {
				if (monitor.isCanceled()) {
					return;
				}
				dirtyEditors[i].doSave(monitor);
				monitor.worked(1);
			}
			monitor.done();
		}
	}

	protected static class SaveFilesDialogRunnable implements Runnable {
		IEditorPart[] dirtyEditors;
		SaveFilesResult result;
		boolean promptAutoSave;

		public SaveFilesDialogRunnable(IEditorPart[] dirtyEditors, SaveFilesResult result, boolean promptAutoSave) {
			this.dirtyEditors = dirtyEditors;
			this.result = result;
			this.promptAutoSave = promptAutoSave;
		}

		public void run() {
			SaveFilesDialog sfDialog = new SaveFilesDialog(Display.getCurrent().getActiveShell(), dirtyEditors, result, promptAutoSave);
			if (sfDialog.open() == Window.OK) {
				result.setSaved(true);
			}
		}

	}
	public static class SaveFilesResult {
		boolean autoSave;
		boolean saved;

		public boolean isAutoSave() {
			return autoSave;
		}

		public boolean isSaved() {
			return saved;
		}

		public void setAutoSave(boolean autoSave) {
			this.autoSave = autoSave;
		}

		public void setSaved(boolean saved) {
			this.saved = saved;
		}

		public SaveFilesResult() {
		}

		public SaveFilesResult(boolean saved) {
			this();
			setSaved(saved);
		}

		public SaveFilesResult(boolean saved, boolean autoSave) {
			this(saved);
			setAutoSave(autoSave);
		}
	}
}
