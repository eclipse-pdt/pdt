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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.TodoTaskConfigurationBlock.TodoTask;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog to enter a na new task tag
 */
public class TodoTaskInputDialog extends StatusDialog {

	private class CompilerTodoTaskInputAdapter implements IDialogFieldListener {
		public void dialogFieldChanged(DialogField field) {
			doValidation();
		}
	}

	private StringDialogField fNameDialogField;
	private ComboDialogField fPriorityDialogField;

	private List fExistingNames;

	public TodoTaskInputDialog(Shell parent, TodoTask task, List existingEntries) {
		super(parent);

		fExistingNames = new ArrayList(existingEntries.size());
		for (int i = 0; i < existingEntries.size(); i++) {
			TodoTask curr = (TodoTask) existingEntries.get(i);
			if (!curr.equals(task)) {
				fExistingNames.add(curr.name);
			}
		}

		if (task == null) {
			setTitle(PHPUIMessages.TodoTaskInputDialog_new_title);
		} else {
			setTitle(PHPUIMessages.TodoTaskInputDialog_edit_title);
		}

		CompilerTodoTaskInputAdapter adapter = new CompilerTodoTaskInputAdapter();

		fNameDialogField = new StringDialogField();
		fNameDialogField
				.setLabelText(PHPUIMessages.TodoTaskInputDialog_name_label);
		fNameDialogField.setDialogFieldListener(adapter);

		fNameDialogField.setText((task != null) ? task.name : ""); //$NON-NLS-1$

		String[] items = new String[] {
				PHPUIMessages.TodoTaskInputDialog_priority_high,
				PHPUIMessages.TodoTaskInputDialog_priority_normal,
				PHPUIMessages.TodoTaskInputDialog_priority_low };

		fPriorityDialogField = new ComboDialogField(SWT.READ_ONLY);
		fPriorityDialogField
				.setLabelText(PHPUIMessages.TodoTaskInputDialog_priority_label);
		fPriorityDialogField.setItems(items);
		if (task != null) {
			if (PHPCoreConstants.TASK_PRIORITY_HIGH.equals(task.priority)) {
				fPriorityDialogField.selectItem(0);
			} else if (PHPCoreConstants.TASK_PRIORITY_NORMAL
					.equals(task.priority)) {
				fPriorityDialogField.selectItem(1);
			} else {
				fPriorityDialogField.selectItem(2);
			}
		} else {
			fPriorityDialogField.selectItem(1);
		}
	}

	public TodoTask getResult() {
		TodoTask task = new TodoTask();
		task.name = fNameDialogField.getText().trim();
		switch (fPriorityDialogField.getSelectionIndex()) {
		case 0:
			task.priority = PHPCoreConstants.TASK_PRIORITY_HIGH;
			break;
		case 1:
			task.priority = PHPCoreConstants.TASK_PRIORITY_NORMAL;
			break;
		default:
			task.priority = PHPCoreConstants.TASK_PRIORITY_LOW;
			break;
		}
		return task;
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Composite inner = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		inner.setLayout(layout);

		fNameDialogField.doFillIntoGrid(inner, 2);
		fPriorityDialogField.doFillIntoGrid(inner, 2);

		LayoutUtil.setHorizontalGrabbing(fNameDialogField.getTextControl(null));
		LayoutUtil.setWidthHint(fNameDialogField.getTextControl(null),
				convertWidthInCharsToPixels(45));

		fNameDialogField.postSetFocusOnDialogField(parent.getDisplay());

		applyDialogFont(composite);
		return composite;
	}

	private void doValidation() {
		StatusInfo status = new StatusInfo();
		String newText = fNameDialogField.getText();
		if (newText.length() == 0) {
			status.setError(PHPUIMessages.TodoTaskInputDialog_error_enterName);
		} else {
			if (newText.indexOf(',') != -1) {
				status.setError(PHPUIMessages.TodoTaskInputDialog_error_comma);
			} else if (fExistingNames.contains(newText)) {
				status.setError(PHPUIMessages.TodoTaskInputDialog_error_entryExists);
			} else if (Character.isWhitespace(newText.charAt(0))
					|| Character.isWhitespace(newText
							.charAt(newText.length() - 1))) {
				status.setError(PHPUIMessages.TodoTaskInputDialog_error_noSpace);
			}
		}
		updateStatus(status);
	}

	/*
	 * @see org.eclipse.jface.window.Window#configureShell(Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		// TODO - Add the Help contex id
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell,
		// IPHPHelpContextIds.TODO_TASK_INPUT_DIALOG);
	}
}
