package org.eclipse.php.ui.dialogs.saveFiles;

import org.eclipse.core.resources.IProject;

public class SaveFilesRunnable implements Runnable {
	IProject project;
	boolean saved = false;
	boolean showAutoSave;
	AutoSaveHolder autoSave = new AutoSaveHolder();

	public boolean isSaved() {
		return saved;
	}

	public boolean isAutoSaved() {
		return autoSave.isAutoSave();
	}

	public SaveFilesRunnable(IProject project, boolean showAutoSave, boolean autoSave) {
		this.project = project;
		this.showAutoSave = showAutoSave;
		this.autoSave.setAutoSave(autoSave);
	}

	public SaveFilesRunnable(IProject project) {
		this(project, false, false);
	}

	public void run() {
		saved = SaveFilesDialog.handleFilesToSave(project, showAutoSave, autoSave);
	}

	public class AutoSaveHolder {
		boolean autoSave;

		public boolean isAutoSave() {
			return autoSave;
		}

		public void setAutoSave(boolean autoSave) {
			this.autoSave = autoSave;
		}
	}
}
