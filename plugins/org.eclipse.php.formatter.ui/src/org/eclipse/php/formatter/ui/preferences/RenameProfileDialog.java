/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * The dialog to rename a new profile.
 */
public class RenameProfileDialog extends StatusDialog {

	private Label fNameLabel;
	private Text fNameText;

	private final StatusInfo fOk;
	private final StatusInfo fEmpty;
	private final StatusInfo fDuplicate;
	private final StatusInfo fNoMessage;

	private final Profile fProfile;
	private final ProfileManager fManager;
	private Profile fRenamedProfile;

	public RenameProfileDialog(Shell parentShell, Profile profile, ProfileManager manager) {
		super(parentShell);
		fManager = manager;
		setTitle(FormatterMessages.RenameProfileDialog_dialog_title);
		fProfile = profile;
		fOk = new StatusInfo();
		fDuplicate = new StatusInfo(IStatus.ERROR,
				FormatterMessages.RenameProfileDialog_status_message_profile_with_this_name_already_exists);
		fEmpty = new StatusInfo(IStatus.ERROR, FormatterMessages.RenameProfileDialog_status_message_profile_name_empty);
		fNoMessage = new StatusInfo(IStatus.ERROR, ""); //$NON-NLS-1$
	}

	@Override
	public Control createDialogArea(Composite parent) {

		final int numColumns = 2;

		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.numColumns = numColumns;

		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(layout);

		// Create "Please enter a new name:" label
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		gd.widthHint = convertWidthInCharsToPixels(60);
		fNameLabel = new Label(composite, SWT.NONE);
		fNameLabel.setText(FormatterMessages.RenameProfileDialog_dialog_label_enter_a_new_name);
		fNameLabel.setLayoutData(gd);

		// Create text field to enter name
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = numColumns;
		fNameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		fNameText.setSelection(0, fProfile.getName().length());
		fNameText.setLayoutData(gd);
		fNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doValidation();
			}
		});
		fNameText.setText(fProfile.getName());
		fNameText.selectAll();

		applyDialogFont(composite);

		return composite;
	}

	/**
	 * Validate the current settings.
	 */
	protected void doValidation() {
		final String name = fNameText.getText().trim();

		if (name.length() == 0) {
			updateStatus(fEmpty);
			return;
		}

		if (name.equals(fProfile.getName())) {
			updateStatus(fNoMessage);
			return;
		}

		if (fManager.containsName(name)) {
			updateStatus(fDuplicate);
			return;
		}

		updateStatus(fOk);
	}

	public Profile getRenamedProfile() {
		return fRenamedProfile;
	}

	@Override
	protected void okPressed() {
		if (!getStatus().isOK())
			return;
		fRenamedProfile = fProfile.rename(fNameText.getText(), fManager);
		super.okPressed();
	}
}
