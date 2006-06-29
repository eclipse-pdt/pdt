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
			result.setAccepted(true);
			return result;
		}
		if (!autoSave) {
			Display.getDefault().syncExec(new SaveFilesDialogRunnable(dirtyEditors, result, promptAutoSave));
		} else {
			result.setAccepted(true);
			result.setSaved(dirtyEditors);
		}
		IEditorPart[] editorsToSave = result.getSaved();
		if (editorsToSave.length>0) {
			Display.getDefault().syncExec(new SaveFilesRunnable(editorsToSave, monitor));
		}
		if (monitor.isCanceled()) {
			result.setAccepted(false);
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
				result.setAccepted(true);
				Object[] toSave = sfDialog.getResult();
				if(toSave.length>0) {
					result.setSaved((IEditorPart[])toSave);
				}
			}
		}

	}
	public static class SaveFilesResult {
		boolean autoSave;
		boolean accepted;
		IEditorPart[] saved = new IEditorPart[0];

		public boolean isAutoSave() {
			return autoSave;
		}

		public void setAutoSave(boolean autoSave) {
			this.autoSave = autoSave;
		}

		public void setSaved(IEditorPart[] saved) {
			this.saved = saved;
		}
		
		public void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}
		public boolean isAccepted() {
			return accepted;
		}
		public IEditorPart[] getSaved() {
			return saved;
		}
		public int getSavedCount() {
			return this.saved.length;
		}
		public SaveFilesResult() {
		}

		public SaveFilesResult(IEditorPart[] saved, boolean accepted) {
			this();
			setSaved(saved);
			setAccepted(accepted);
		}

		public SaveFilesResult(IEditorPart[] saved, boolean accepted, boolean autoSave) {
			this(saved, accepted);
			setAutoSave(autoSave);
			setAccepted(accepted);
		}
	}
}
