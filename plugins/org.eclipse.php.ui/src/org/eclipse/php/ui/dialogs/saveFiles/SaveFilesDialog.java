package org.eclipse.php.ui.dialogs.saveFiles;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.util.ListContentProvider;
import org.eclipse.php.ui.dialogs.saveFiles.SaveFilesRunnable.AutoSaveHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * A generic save files dialog. The bulk of the code
 * for this dialog was taken from the JDT refactoring
 * support in org.eclipse.jdt.internal.ui.refactoring.RefactoringSaveHelper.
 * This class is a good candidate for reuse amoung components.
 */
public class SaveFilesDialog extends ListDialog {

	boolean showAutoSave;
	AutoSaveHolder autoSave;

	public SaveFilesDialog(Shell parent, boolean showAutoSave, AutoSaveHolder autoSave) {
		super(parent);
		this.showAutoSave = showAutoSave;
		this.autoSave = autoSave;
		setTitle("Save All Modified Resources");
		setAddCancelButton(true);
		setLabelProvider(createDialogLabelProvider());
		setMessage("All modified resources must be saved before this operation.");
		setContentProvider(new ListContentProvider());
	}

	protected Control createDialogArea(Composite container) {
		Composite result = (Composite) super.createDialogArea(container);
		if (showAutoSave) {
			final Button check = new Button(result, SWT.CHECK);
			check.setText("&Save all modified resources automatically");
			check.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					autoSave.setAutoSave(check.getSelection());
				}
			});
			applyDialogFont(result);
		}
		return result;
	}

	private ILabelProvider createDialogLabelProvider() {
		return new LabelProvider() {
			public Image getImage(Object element) {
				return ((IEditorPart) element).getTitleImage();
			}

			public String getText(Object element) {
				return ((IEditorPart) element).getTitle();
			}
		};
	}

	/**
	 * Handle any files that must be saved prior to running
	 * validation.
	 * 
	 * @param projects
	 * 			The list of projects that will be validated.
	 * @return
	 * 			True if all files have been saved, false otherwise.
	 */
	public static boolean handleFilesToSave(IProject project, boolean showAutoSave, AutoSaveHolder autoSave) {

		IEditorPart[] dirtyEditors = SaveFilesHelper.getDirtyEditors(project);
		if (dirtyEditors == null || dirtyEditors.length == 0)
			return true;
		SaveFilesDialog sfDialog = null;
		if (!autoSave.isAutoSave()) {
			sfDialog = new SaveFilesDialog(Display.getCurrent().getActiveShell(), showAutoSave, autoSave);
			sfDialog.setInput(Arrays.asList(dirtyEditors));
		}
		// Save all open editors.
		if (autoSave.isAutoSave() || sfDialog.open() == Window.OK) {
			int numDirtyEditors = dirtyEditors.length;
			for (int i = 0; i < numDirtyEditors; i++) {
				dirtyEditors[i].doSave(null);
			}
			return true;
		}
		return false;
	}
}
