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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.PixelConverter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class AddPHPexeDialog extends StatusDialog {

	private IAddPHPexeDialogRequestor fRequestor;
	private PHPexeItem fEditedPHPexe;
	private PHPexes fPHPexes;

	private StringButtonDialogField fPHPRoot;
	private StringDialogField fPHPexeName;
	private Combo fDebuggers;
	private Collection<String> debuggersIds;

	private IStatus[] fStati;
	private Label fDebuggersLabel;

	public AddPHPexeDialog(IAddPHPexeDialogRequestor requestor, Shell shell, PHPexes phpexes, PHPexeItem editedPHPexe) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		fRequestor = requestor;
		fStati = new IStatus[5];
		for (int i = 0; i < fStati.length; i++) {
			fStati[i] = new StatusInfo();
		}

		fPHPexes = phpexes;
		fEditedPHPexe = editedPHPexe;
		debuggersIds = PHPDebuggersRegistry.getDebuggersIds();
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		//		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, IPHPDebugHelpContextIds.EDIT_PHP_DIALOG);
	}

	protected void createDialogFields() {

		fPHPexeName = new StringDialogField();
		fPHPexeName.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpName); //$NON-NLS-1$

		fPHPRoot = new StringButtonDialogField(new IStringButtonAdapter() {
			public void changeControlPressed(DialogField field) {
				browseForInstallDir();
			}
		});
		fPHPRoot.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpHome); //$NON-NLS-1$
		fPHPRoot.setButtonLabel(PHPDebugUIMessages.addPHPexeDialog_browse1); //$NON-NLS-1$

	}

	protected void createFieldListeners() {
		fPHPexeName.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				setPHPexeNameStatus(validatePHPexeName());
				updateStatusLine();
			}
		});

		fPHPRoot.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				setJRELocationStatus(validateJRELocation());
				updateStatusLine();
			}
		});
	}

	protected String getPHPexeName() {
		return fPHPexeName.getText();
	}

	protected File getInstallLocation() {
		return new File(fPHPRoot.getText());
	}

	protected Control createDialogArea(Composite ancestor) {

		PixelConverter pixelConverter = new PixelConverter(ancestor);

		createDialogFields();
		Composite parent = (Composite) super.createDialogArea(ancestor);
		((GridLayout) parent.getLayout()).numColumns = 3;

		fPHPexeName.doFillIntoGrid(parent, 3);
		fPHPRoot.doFillIntoGrid(parent, 3);
		((GridData) fPHPRoot.getTextControl(parent).getLayoutData()).widthHint = pixelConverter.convertWidthInCharsToPixels(50);

		fDebuggersLabel = new Label(parent, SWT.LEFT | SWT.WRAP);
		fDebuggersLabel.setFont(parent.getFont());
		fDebuggersLabel.setText(PHPDebugUIMessages.addPHPexeDialog_phpDebugger);
		GridData data = new GridData();
		data.horizontalSpan = 1;
		fDebuggersLabel.setLayoutData(data);

		fDebuggers = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData();
		data.horizontalSpan = 1;
		data.grabExcessHorizontalSpace = true;
		fDebuggers.setLayoutData(data);

		initializeFields();
		createFieldListeners();
		setJRELocationStatus(validateJRELocation());
		applyDialogFont(parent);
		return parent;
	}

	public void create() {
		super.create();
		fPHPexeName.setFocus();
	}

	private void initializeFields() {
		Iterator<String> debuggers = debuggersIds.iterator();
		while (debuggers.hasNext()) {
			String id = debuggers.next();
			String debuggerName = PHPDebuggersRegistry.getDebuggerName(id);
			fDebuggers.add(debuggerName);
		}
		if (fEditedPHPexe == null) {
			fPHPexeName.setText(""); //$NON-NLS-1$
			fPHPRoot.setText(""); //$NON-NLS-1$
			String defaultDebuggerId = PHPDebuggersRegistry.getDefaultDebuggerId();
			if (defaultDebuggerId != null) {
				int index = fDebuggers.indexOf(PHPDebuggersRegistry.getDebuggerName(defaultDebuggerId));
				fDebuggers.select(index);
			} else {
				if (fDebuggers.getItemCount() > 0) {
					fDebuggers.select(0);
				} else {
					hideDebuggersCombo();
				}
			}
		} else {
			fPHPexeName.setText(fEditedPHPexe.getName());
			fPHPexeName.setEnabled(fEditedPHPexe.isEditable());
			fPHPRoot.setText(fEditedPHPexe.getLocation().getAbsolutePath());
			fPHPRoot.setEnabled(fEditedPHPexe.isEditable());
			String debuggerID = fEditedPHPexe.getDebuggerID();
			fDebuggers.setEnabled(fEditedPHPexe.isEditable());
			fDebuggersLabel.setEnabled(fEditedPHPexe.isEditable());
			int index = fDebuggers.indexOf(PHPDebuggersRegistry.getDebuggerName(debuggerID));
			if (index > -1) {
				fDebuggers.select(index);
			} else {
				if (fDebuggers.getItemCount() > 0) {
					fDebuggers.select(0);
				} else {
					hideDebuggersCombo();
				}
			}
		}
		setPHPexeNameStatus(validatePHPexeName());
		setEditingEnabledStatus(validateEditingEnabled());
		updateStatusLine();
	}

	private void hideDebuggersCombo() {
		fDebuggers.setVisible(false);
		fDebuggersLabel.setVisible(false);
	}

	private IStatus validateJRELocation() {
		String locationName = fPHPRoot.getText();
		IStatus s = null;
		File file = null;
		if (locationName.length() == 0) {//$NON-NLS-1$
			s = new StatusInfo(IStatus.INFO, PHPDebugUIMessages.addPHPexeDialog_enterLocation); //$NON-NLS-1$
		} else {
			file = new File(locationName);
			if (!file.exists()) {
				s = new StatusInfo(IStatus.ERROR, PHPDebugUIMessages.addPHPexeDialog_locationNotExists); //$NON-NLS-1$
			} else {
				final IStatus[] temp = new IStatus[1];
				final File tempFile = file;
				Runnable r = new Runnable() {
					/**
					 * @see java.lang.Runnable#run()
					 */
					public void run() {
						temp[0] = validateLocation(tempFile);
					}
				};
				BusyIndicator.showWhile(getShell().getDisplay(), r);
				s = temp[0];
			}
		}
		return s;
	}

	private IStatus validatePHPexeName() {
		StatusInfo status = new StatusInfo();
		String name = fPHPexeName.getText();
		if (name == null || name.trim().length() == 0) {
			status.setInfo(PHPDebugUIMessages.addPHPexeDialog_enterName); //$NON-NLS-1$
		} else {
			if (fRequestor.isDuplicateName(name) && (fEditedPHPexe == null || !name.equals(fEditedPHPexe.getName()))) {
				status.setError(PHPDebugUIMessages.addPHPexeDialog_duplicateName); //$NON-NLS-1$
			}
		}
		return status;
	}
	
	private IStatus validateEditingEnabled() {
		StatusInfo status = new StatusInfo();
		if (fEditedPHPexe == null || fEditedPHPexe.isEditable()) {
			status.setOK();
		} else {
			status.setInfo(PHPDebugUIMessages.addPHPexeDialog_readOnlyPHPExe);
		}
		return status;
	}

	protected void updateStatusLine() {
		IStatus max = null;
		for (int i = 0; i < fStati.length; i++) {
			IStatus curr = fStati[i];
			if (curr.matches(IStatus.ERROR)) {
				updateStatus(curr);
				return;
			}
			if (max == null || curr.getSeverity() > max.getSeverity()) {
				max = curr;
			}
		}
		updateStatus(max);
	}

	private void browseForInstallDir() {
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setFilterPath(fPHPRoot.getText());
		dialog.setMessage(PHPDebugUIMessages.addPHPexeDialog_pickPHPRootDialog_message); //$NON-NLS-1$
		String newPath = dialog.open();
		if (newPath != null) {
			fPHPRoot.setText(newPath);
		}
	}

	protected void okPressed() {
		doOkPressed();
		super.okPressed();
	}

	private void doOkPressed() {
		if (fEditedPHPexe == null) {
			PHPexeItem vm = new PHPexeItem();
			setFieldValuesToPHPexe(vm);
			fPHPexes.addItem(vm);
			fRequestor.phpExeAdded(vm);
		} else {
			setFieldValuesToPHPexe(fEditedPHPexe);
		}
	}

	protected void setFieldValuesToPHPexe(PHPexeItem vm) {
		vm.setLocation(new File(fPHPRoot.getText()).getAbsoluteFile());
		vm.setName(fPHPexeName.getText());
		if (fDebuggers.isVisible()) {
			int selectedIndex = fDebuggers.getSelectionIndex();
			String debuggerId = debuggersIds.toArray()[selectedIndex].toString();
			vm.setDebuggerID(debuggerId);
		}

	}

	protected File getAbsoluteFileOrEmpty(String path) {
		if (path == null || path.length() == 0) {
			return new File(""); //$NON-NLS-1$
		}
		return new File(path).getAbsoluteFile();
	}

	private void setPHPexeNameStatus(IStatus status) {
		fStati[0] = status;
	}

	private void setJRELocationStatus(IStatus status) {
		fStati[1] = status;
	}
	
	private void setEditingEnabledStatus(IStatus status) {
		fStati[2] = status;
	}

	/**
	 * Updates the status of the ok button to reflect the given status.
	 * Subclasses may override this method to update additional buttons.
	 * @param status the status.
	 */
	protected void updateButtonsEnableState(IStatus status) {
		Button ok = getButton(IDialogConstants.OK_ID);
		if (ok != null && !ok.isDisposed())
			ok.setEnabled(status.getSeverity() == IStatus.OK);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#setButtonLayoutData(org.eclipse.swt.widgets.Button)
	 */
	protected void setButtonLayoutData(Button button) {
		super.setButtonLayoutData(button);
	}

	/**
	 * Returns the name of the section that this dialog stores its settings in
	 * 
	 * @return String
	 */
	protected String getDialogSettingsSectionName() {
		return "ADD_PHPexe_DIALOG_SECTION"; //$NON-NLS-1$
	}

	public static IStatus validateLocation(File phpHome) {
		IStatus status = null;
		File phpExecutable = PHPexeItem.findPHPExecutable(phpHome);
		if (phpExecutable == null) {
			status = new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, 0, PHPDebugUIMessages.PHPexe_executable_was_not_found_1, null); //$NON-NLS-1$			
		} else {
			status = new Status(IStatus.OK, PHPDebugUIPlugin.ID, 0, PHPDebugUIMessages.PHPexe_ok_2, null); //$NON-NLS-1$
		}
		return status;
	}

}
