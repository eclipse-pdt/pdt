package org.eclipse.php.ui.dialogs.saveFiles;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.ui.dialogs.saveFiles.SaveFilesHandler.SaveFilesResult;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * A generic save files dialog. The bulk of the code
 * for this dialog was taken from the JDT refactoring
 * support in org.eclipse.jdt.internal.ui.refactoring.RefactoringSaveHelper.
 * This class is a good candidate for reuse amoung components.
 */
public class SaveFilesDialog extends ListSelectionDialog {

	boolean promptAutoSave;
	SaveFilesResult result;

	public SaveFilesDialog(Shell parent, IEditorPart[] dirtyEditors, SaveFilesResult result, boolean promptAutoSave) {		
		super(parent, dirtyEditors, new ArrayContentProvider(), new LabelProvider() {
			public Image getImage(Object element) {
				return ((IEditorPart) element).getTitleImage();
			}

			public String getText(Object element) {
				return ((IEditorPart) element).getTitle();
			}
		}, "Save Modified Resources");
		this.promptAutoSave = promptAutoSave;
		this.result = result;
		setTitle("Save Modified Resources");
		setMessage("Do you want to save modified resources?");
	}

	protected Control createDialogArea(Composite container) {
		Composite area = (Composite) super.createDialogArea(container);
		if (promptAutoSave) {
			final Button check = new Button(area, SWT.CHECK);
			check.setText("&Save all modified resources automatically");
			check.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					result.setAutoSave(check.getSelection());
				}
			});
			applyDialogFont(area);
		}
		return area;
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
}
