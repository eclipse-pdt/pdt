package org.eclipse.php.internal.ui.wizards;

import java.util.Observable;

import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Request a project name. Fires an event whenever the text field is changed,
 * regardless of its content.
 */
public final class NameGroup extends Observable implements IDialogFieldListener {
	protected final StringDialogField fNameField;
	private Shell shell;

	public NameGroup(Composite composite, String initialName, Shell shell) {
		this.shell = shell;
		final Composite nameComposite = new Composite(composite, SWT.NONE);
		nameComposite.setFont(composite.getFont());
		nameComposite.setLayout(new GridLayout(2, false));
		nameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// text field for project name
		fNameField = new StringDialogField();
		fNameField
				.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_NameGroup_label_text);
		fNameField.setDialogFieldListener(this);
		setName(initialName);
		fNameField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(fNameField.getTextControl(null));
	}

	protected void fireEvent() {
		setChanged();
		notifyObservers();
	}

	public String getName() {
		return fNameField.getText().trim();
	}

	public void postSetFocus() {
		fNameField.postSetFocusOnDialogField(shell.getDisplay());
	}

	public void setName(String name) {
		fNameField.setText(name);
	}

	public void dialogFieldChanged(DialogField field) {
		fireEvent();
	}
}
