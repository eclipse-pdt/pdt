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
package org.eclipse.php.internal.debug.ui.preferences.stepFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.DebugStepFilter;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.IStepFilterTypes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * This dialog represents a Debug Filter Pattern UI. The user can enter a text
 * pattern that will be added into the Debug Step filters list
 * 
 * @author yaronm
 */
public class CreateStepFilterDialog extends StatusDialog {

	private Text text;
	private DebugStepFilter filter;
	private Button okButton;

	private boolean filterValid;
	private boolean okClicked;
	private DebugStepFilter[] existingFilters;

	private CreateStepFilterDialog(Shell parent, DebugStepFilter filter,
			DebugStepFilter[] existingFilters) {
		super(parent);
		setShellStyle(getShellStyle());
		this.filter = filter;
		this.existingFilters = existingFilters;
		setTitle(PHPDebugUIMessages.CreateStepFilterDialog_addStepFilter);
		setStatusLineAboveButtons(false);

	}

	static DebugStepFilter showCreateStepFilterDialog(Shell parent,
			DebugStepFilter[] existingFilters) {
		CreateStepFilterDialog createStepFilterDialog = new CreateStepFilterDialog(
				parent, new DebugStepFilter(IStepFilterTypes.PATH_PATTERN,
						true, false, ""), existingFilters); //$NON-NLS-1$
		createStepFilterDialog.create();
		createStepFilterDialog.open();

		return createStepFilterDialog.filter;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 15;
		gridLayout.marginWidth = 15;
		container.setLayout(gridLayout);

		int textStyles = SWT.SINGLE | SWT.LEFT;
		Label label = new Label(container, textStyles);
		label
				.setText(PHPDebugUIMessages.CreateStepFilterDialog_patternToFilter);
		label.setFont(container.getFont());

		// create & configure Text widget for editor
		// Fix for bug 1766. Border behavior on for text fields varies per
		// platform.
		// On Motif, you always get a border, on other platforms,
		// you don't. Specifying a border on Motif results in the characters
		// getting pushed down so that only there very tops are visible. Thus,
		// we have to specify different style constants for the different
		// platforms.
		if (!SWT.getPlatform().equals("motif")) { //$NON-NLS-1$
			textStyles |= SWT.BORDER;
		}

		text = new Text(container, textStyles);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 1;
		gridData.widthHint = 300;
		text.setLayoutData(gridData);
		text.setFont(container.getFont());

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validateChange();
				if (!filterValid) {
					updateStatus(new StatusInfo(
							IStatus.ERROR,
							PHPDebugUIMessages.CreateStepFilterDialog_invalidPathPattern));
					return;
				} else if (isDuplicateFilter(text.getText().trim())) {
					updateStatus(new StatusInfo(
							IStatus.ERROR,
							PHPDebugUIMessages.CreateStepFilterDialog_stepFilterAlreadyExists));
					return;
				} else {
					filterValid = true;
					updateStatus(new StatusInfo());
				}
			}
		});

		return container;
	}

	private void validateChange() {
		String trimmedValue = text.getText().trim();
		if (trimmedValue.length() > 0 && validateInput(trimmedValue)) {
			okButton.setEnabled(true);
			filter.setPath(text.getText());
			filterValid = true;
		} else {
			okButton.setEnabled(false);
			filter.setPath(""); //$NON-NLS-1$
			filterValid = false;
		}
	}

	private boolean isDuplicateFilter(String trimmedValue) {
		for (int i = 0; i < existingFilters.length; i++) {
			if (existingFilters[i].getPath().equalsIgnoreCase(trimmedValue)) {
				okButton.setEnabled(false);
				filterValid = false;
				return true;
			}
		}
		okButton.setEnabled(true);
		filterValid = true;
		return false;
	}

	private boolean validateInput(String trimmedValue) {
		// validate '*' locations, we allow only at start and at the end
		for (int i = 0; i < trimmedValue.length(); i++) {
			if (trimmedValue.charAt(i) == '*' && i != 0
					&& i != trimmedValue.length() - 1) {
				return false;
			} else if (trimmedValue.charAt(i) == ':' && i != 1) {
				return false;
			}
		}

		// check invalid characters
		Pattern p = Pattern.compile("[[{}]|\"<>\\?]"); //$NON-NLS-1$
		Matcher m = p.matcher(trimmedValue);
		if (m.find()) {
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {
		if (!okClicked) {
			filterValid = false;
			filter = null;
		}
		return super.close();
	}

	protected void okPressed() {
		okClicked = true;
		super.okPressed();
	}
}
