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

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.internal.ui.actions.StatusInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.includepath.IncludePathEntry;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.php.ui.wizards.fields.DialogField;
import org.eclipse.php.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.ui.wizards.fields.LayoutUtil;
import org.eclipse.php.ui.wizards.fields.StringDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

/**
 */
public class IncludePathContainerDefaultPage extends WizardPage implements IIncludePathContainerPage, IIncludePathContainerPageExtension {

	private StringDialogField fEntryField;
	private ArrayList fUsedPaths;

	/**
	 * Constructor for IncludePathContainerDefaultPage.
	 */
	public IncludePathContainerDefaultPage() {
		super("IncludePathContainerDefaultPage"); //$NON-NLS-1$
		setTitle(PHPUIMessages.IncludePathContainerDefaultPage_title);
		setDescription(PHPUIMessages.IncludePathContainerDefaultPage_description);
		setImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_LIBRARY);

		fUsedPaths = new ArrayList();

		fEntryField = new StringDialogField();
		fEntryField.setLabelText(PHPUIMessages.IncludePathContainerDefaultPage_path_label);
		fEntryField.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				validatePath();
			}
		});
		validatePath();
	}

	private void validatePath() {
		StatusInfo status = new StatusInfo();
		String str = fEntryField.getText();
		if (str.length() == 0) {
			status.setError(PHPUIMessages.IncludePathContainerDefaultPage_path_error_enterpath);
		} else if (!Path.ROOT.isValidPath(str)) {
			status.setError(PHPUIMessages.IncludePathContainerDefaultPage_path_error_invalidpath);
		} else {
			IPath path = new Path(str);
			if (path.segmentCount() == 0) {
				status.setError(PHPUIMessages.IncludePathContainerDefaultPage_path_error_needssegment);
			} else if (fUsedPaths.contains(path)) {
				status.setError(PHPUIMessages.IncludePathContainerDefaultPage_path_error_alreadyexists);
			}
		}
		updateStatus(status);
	}

	protected void updateStatus(IStatus status) {
		IncludePathPropertyPage.applyToStatusLine(this, status);
	}

	/* (non-Javadoc)
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		fEntryField.doFillIntoGrid(composite, 2);
		LayoutUtil.setHorizontalGrabbing(fEntryField.getTextControl(null));

		fEntryField.setFocus();

		setControl(composite);
		Dialog.applyDialogFont(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IPHPHelpContextIds.INCLUDEPATH_CONTAINER_DEFAULT_PAGE);
	}

	/* (non-Javadoc)
	 * @see IIncludePathContainerPage#finish()
	 */
	public boolean finish() {
		return true;
	}

	/* (non-Javadoc)
	 * @see IIncludePathContainerPage#getSelection()
	 */
	public IIncludePathEntry getSelection() {
		return IncludePathEntry.newContainerEntry(new Path(fEntryField.getText()), null, true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.wizards.IIncludePathContainerPageExtension#initialize(org.eclipse.jdt.core.IJavaProject, org.eclipse.jdt.core.IIncludePathEntry)
	 */
	public void initialize(IProject project, IIncludePathEntry[] currentEntries) {
		for (int i = 0; i < currentEntries.length; i++) {
			IIncludePathEntry curr = currentEntries[i];
			if (curr.getEntryKind() == IIncludePathEntry.IPE_CONTAINER) {
				fUsedPaths.add(curr.getPath());
			}
		}
	}

	/* (non-Javadoc)
	 * @see IIncludePathContainerPage#setSelection(IIncludePathEntry)
	 */
	public void setSelection(IIncludePathEntry containerEntry) {
		if (containerEntry != null) {
			fUsedPaths.remove(containerEntry.getPath());
			fEntryField.setText(containerEntry.getPath().toString());
		} else {
			fEntryField.setText(""); //$NON-NLS-1$
		}
	}

}
