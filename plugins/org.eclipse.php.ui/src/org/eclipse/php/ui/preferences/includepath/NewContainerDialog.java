/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.preferences.includepath;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.internal.ui.actions.StatusInfo;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.wizards.fields.DialogField;
import org.eclipse.php.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.ui.wizards.fields.LayoutUtil;
import org.eclipse.php.ui.wizards.fields.StringDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class NewContainerDialog extends StatusDialog {

	private StringDialogField fContainerDialogField;
	private StatusInfo fContainerFieldStatus;

	private IFolder fFolder;
	private IPath[] fExistingFolders;
	private IProject fCurrProject;

	public NewContainerDialog(Shell parent, String title, IProject project, IPath[] existingFolders, IPListElement entryToEdit) {
		super(parent);
		setTitle(title);

		fContainerFieldStatus = new StatusInfo();

		SourceContainerAdapter adapter = new SourceContainerAdapter();
		fContainerDialogField = new StringDialogField();
		fContainerDialogField.setDialogFieldListener(adapter);

		fFolder = null;
		fExistingFolders = existingFolders;
		fCurrProject = project;

		if (entryToEdit == null) {
			fContainerDialogField.setText(""); //$NON-NLS-1$
		} else {
			fContainerDialogField.setText(entryToEdit.getPath().removeFirstSegments(1).toString()); //$NON-NLS-1$
		}
	}

	public void setMessage(String message) {
		fContainerDialogField.setLabelText(message);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		int widthHint = convertWidthInCharsToPixels(80);

		Composite inner = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;
		inner.setLayout(layout);

		fContainerDialogField.doFillIntoGrid(inner, 2);

		LayoutUtil.setWidthHint(fContainerDialogField.getLabelControl(null), widthHint);
		LayoutUtil.setWidthHint(fContainerDialogField.getTextControl(null), widthHint);
		LayoutUtil.setHorizontalGrabbing(fContainerDialogField.getTextControl(null));

		fContainerDialogField.postSetFocusOnDialogField(parent.getDisplay());
		applyDialogFont(composite);
		return composite;
	}

	// -------- SourceContainerAdapter --------

	private class SourceContainerAdapter implements IDialogFieldListener {

		// -------- IDialogFieldListener

		public void dialogFieldChanged(DialogField field) {
			doStatusLineUpdate();
		}
	}

	protected void doStatusLineUpdate() {
		checkIfPathValid();
		updateStatus(fContainerFieldStatus);
	}

	protected void checkIfPathValid() {
		fFolder = null;

		String pathStr = fContainerDialogField.getText();
		if (pathStr.length() == 0) {
			fContainerFieldStatus.setError(PHPUIMessages.NewContainerDialog_error_enterpath);
			return;
		}
		IPath path = fCurrProject.getFullPath().append(pathStr);
		IWorkspace workspace = fCurrProject.getWorkspace();

		IStatus pathValidation = workspace.validatePath(path.toString(), IResource.FOLDER);
		if (!pathValidation.isOK()) {
			fContainerFieldStatus.setError(MessageFormat.format(PHPUIMessages.NewContainerDialog_error_invalidpath, new String[] { pathValidation.getMessage() }));
			return;
		}
		IFolder folder = fCurrProject.getFolder(pathStr);
		if (isFolderExisting(folder)) {
			fContainerFieldStatus.setError(PHPUIMessages.NewContainerDialog_error_pathexists);
			return;
		}
		fContainerFieldStatus.setOK();
		fFolder = folder;
	}

	private boolean isFolderExisting(IFolder folder) {
		for (int i = 0; i < fExistingFolders.length; i++) {
			if (folder.getFullPath().equals(fExistingFolders[i])) {
				return true;
			}
		}
		return false;
	}

	public IFolder getFolder() {
		return fFolder;
	}

	/*
	 * @see org.eclipse.jface.window.Window#configureShell(Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, IPHPHelpContextIds.NEW_CONTAINER_DIALOG);
	}

}
