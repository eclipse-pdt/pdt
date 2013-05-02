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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.internal.ui.actions.StatusInfo;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class VariableCreationDialog extends StatusDialog {

	private IDialogSettings fDialogSettings;

	private StringDialogField fNameField;
	private StatusInfo fNameStatus;

	private StringButtonDialogField fPathField;
	private StatusInfo fPathStatus;
	// private SelectionButtonDialogField fDirButton;

	private IPVariableElement fElement;

	private List fExistingNames;

	public VariableCreationDialog(Shell parent, IPVariableElement element,
			List existingNames) {
		super(parent);
		if (element == null) {
			setTitle(PHPUIMessages.VariableCreationDialog_titlenew);
		} else {
			setTitle(PHPUIMessages.VariableCreationDialog_titleedit);
		}

		fDialogSettings = PHPUiPlugin.getDefault().getDialogSettings();

		fElement = element;

		fNameStatus = new StatusInfo();
		fPathStatus = new StatusInfo();

		NewVariableAdapter adapter = new NewVariableAdapter();
		fNameField = new StringDialogField();
		fNameField.setDialogFieldListener(adapter);
		fNameField
				.setLabelText(PHPUIMessages.VariableCreationDialog_name_label);

		fPathField = new StringButtonDialogField(adapter);
		fPathField.setDialogFieldListener(adapter);
		fPathField
				.setLabelText(PHPUIMessages.VariableCreationDialog_path_label);
		fPathField
				.setButtonLabel(PHPUIMessages.VariableCreationDialog_path_dir_button);

		// fDirButton = new SelectionButtonDialogField(SWT.PUSH);
		// fDirButton.setDialogFieldListener(adapter);
		// fDirButton.setLabelText(PHPUIMessages.getString("VariableCreationDialog_path_dir_button"));

		fExistingNames = existingNames;

		if (element != null) {
			fNameField.setText(element.getName());
			fPathField.setText(element.getPath().toString());
			fExistingNames.remove(element.getName());
		} else {
			fNameField.setText(""); //$NON-NLS-1$
			fPathField.setText(""); //$NON-NLS-1$
		}
	}

	/*
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell,
				IPHPHelpContextIds.PATH_VARIABLES_PREFERENCES);
	}

	public IPVariableElement getIncludePathElement() {
		return null;
		// return new IPVariableElement(fNameField.getText(), new
		// Path(fPathField.getText()),
		// IncludePathVariableManager.instance().isReserved(fNameField.getText()));
	}

	/*
	 * @see Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Composite inner = new Composite(composite, SWT.NONE);
		inner.setFont(composite.getFont());

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 3;
		inner.setLayout(layout);

		int fieldWidthHint = convertWidthInCharsToPixels(50);

		fNameField.doFillIntoGrid(inner, 2);
		LayoutUtil
				.setWidthHint(fNameField.getTextControl(null), fieldWidthHint);
		LayoutUtil.setHorizontalGrabbing(fNameField.getTextControl(null));

		DialogField.createEmptySpace(inner, 1);

		fPathField.doFillIntoGrid(inner, 3);
		LayoutUtil
				.setWidthHint(fPathField.getTextControl(null), fieldWidthHint);

		// DialogField.createEmptySpace(inner, 2);
		// fDirButton.doFillIntoGrid(inner, 1);

		DialogField focusField = (fElement == null) ? fNameField : fPathField;
		focusField.postSetFocusOnDialogField(parent.getDisplay());
		applyDialogFont(composite);
		return composite;
	}

	// -------- NewVariableAdapter --------

	private class NewVariableAdapter implements IDialogFieldListener,
			IStringButtonAdapter {

		// -------- IDialogFieldListener
		public void dialogFieldChanged(DialogField field) {
			doFieldUpdated(field);
		}

		// -------- IStringButtonAdapter
		public void changeControlPressed(DialogField field) {
			doChangeControlPressed(field);
		}
	}

	private void doChangeControlPressed(DialogField field) {
		if (field == fPathField) {
			IPath path = chooseExtDirectory();
			if (path != null) {
				fPathField.setText(path.toString());
			}
		}
	}

	private void doFieldUpdated(DialogField field) {
		if (field == fNameField) {
			fNameStatus = nameUpdated();
		} else if (field == fPathField) {
			fPathStatus = pathUpdated();
		}
		updateStatus(getMoreSevere(fPathStatus, fNameStatus));
	}

	private static IStatus getMoreSevere(IStatus s1, IStatus s2) {
		if (s1.getSeverity() > s2.getSeverity()) {
			return s1;
		} else {
			return s2;
		}
	}

	private StatusInfo nameUpdated() {
		StatusInfo status = new StatusInfo();
		String name = fNameField.getText();
		if (name.length() == 0) {
			status.setError(PHPUIMessages.VariableCreationDialog_error_entername);
			return status;
		}
		if (name.trim().length() != name.length()) {
			status.setError(PHPUIMessages.VariableCreationDialog_error_whitespace);
		} else if (!Path.ROOT.isValidSegment(name)) {
			status.setError(PHPUIMessages.VariableCreationDialog_error_invalidname);
		} else if (nameConflict(name)) {
			status.setError(PHPUIMessages.VariableCreationDialog_error_nameexists);
		}
		return status;
	}

	private boolean nameConflict(String name) {
		if (fElement != null && fElement.getName().equals(name)) {
			return false;
		}
		for (int i = 0; i < fExistingNames.size(); i++) {
			IPVariableElement elem = (IPVariableElement) fExistingNames.get(i);
			if (name.equals(elem.getName())) {
				return true;
			}
		}
		return false;
	}

	private StatusInfo pathUpdated() {
		StatusInfo status = new StatusInfo();

		String path = fPathField.getText();
		if (path.length() > 0) { // empty path is ok
			if (!Path.ROOT.isValidPath(path)) {
				status.setError(PHPUIMessages.VariableCreationDialog_error_invalidpath);
			} else if (!new File(path).exists()) {
				status.setWarning(PHPUIMessages.VariableCreationDialog_warning_pathnotexists);
			}
		}
		return status;
	}

	private String getInitPath() {
		String initPath = fPathField.getText();
		if (initPath.length() == 0) {
			initPath = ""; //$NON-NLS-1$
		} else {
			IPath entryPath = new Path(initPath);
			if (ArchieveFileFilter.isZipPath(entryPath)) {
				entryPath.removeLastSegments(1);
			}
			initPath = entryPath.toOSString();
		}
		return initPath;
	}

	// /*
	// * Open a dialog to choose a jar from the file system
	// */
	// private IPath chooseExtZipFile() {
	// String initPath = getInitPath();
	//
	// FileDialog dialog = new FileDialog(getShell());
	// dialog.setText(PHPUIMessages.getString("VariableCreationDialog_extjardialog_text"));
	//		dialog.setFilterExtensions(new String[] { "*.zip", "*.jar" }); 
	// dialog.setFilterPath(initPath);
	// String res = dialog.open();
	// if (res != null) {
	// // fDialogSettings.put(IncludePathDialogAccess.DIALOGSTORE_LASTEXTZIP,
	// dialog.getFilterPath());
	// return Path.fromOSString(res).makeAbsolute();
	// }
	// return null;
	// }

	private IPath chooseExtDirectory() {
		String initPath = getInitPath();

		DirectoryDialog dialog = new DirectoryDialog(getParentShell(),
				SWT.APPLICATION_MODAL);
		dialog.setText(PHPUIMessages.VariableCreationDialog_extdirdialog_text);
		dialog.setMessage(PHPUIMessages.VariableCreationDialog_extdirdialog_message);
		dialog.setFilterPath(initPath);
		String res = dialog.open();
		if (res != null) {
			// fDialogSettings.put(IncludePathDialogAccess.DIALOGSTORE_LASTEXTZIP,
			// dialog.getFilterPath());
			return Path.fromOSString(res);
		}
		return null;
	}

}
