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
package org.eclipse.php.internal.debug.ui.wizards;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.debug.internal.ui.PixelConverter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class PHPExeCompositeFragment extends CompositeFragment implements IPHPExeCompositeFragment {

	private PHPexeItem[] existingItems;
	private StringDialogField fPHPexeName;
	private StringButtonDialogField fPHPRoot;
	private StringButtonDialogField fPHPIni;
	private List<String> debuggersIds;
	private Label fDebuggersLabel;
	private Combo fDebuggers;
	private String initialName;

	public PHPExeCompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		setDescription("Specify the PHP Executable Information");
		setDisplayName("PHP Executable");
		controlHandler.setDescription(getDescription());
		controlHandler.setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_PHPEXE));

		debuggersIds = new LinkedList<String>(PHPDebuggersRegistry.getDebuggersIds());
		createControl();
	}

	public void setExistingItems(PHPexeItem[] existingItems) {
		this.existingItems = existingItems;
	}

	public void setData(Object data) {
		if (data != null && !(data instanceof PHPexeItem)) {
			throw new IllegalArgumentException("Data must be instance of PHPExeItem");
		}
		super.setData(data);
		init();
	}

	public PHPexeItem getPHPExeItem() {
		return (PHPexeItem) super.getData();
	}

	protected String getPHPexeName() {
		return fPHPexeName.getText();
	}

	protected File getInstallLocation() {
		return new File(fPHPRoot.getText());
	}

	protected File getIniLocation() {
		return new File(fPHPIni.getText());
	}

	protected void createControl() {
		PixelConverter pixelConverter = new PixelConverter(this);

		GridLayout layout = new GridLayout(1, true);
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite parent = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		fPHPexeName = new StringDialogField();
		fPHPexeName.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpName);

		fPHPRoot = new StringButtonDialogField(new IStringButtonAdapter() {
			public void changeControlPressed(DialogField field) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setFilterPath(fPHPRoot.getText());
				dialog.setMessage(PHPDebugUIMessages.addPHPexeDialog_pickPHPRootDialog_message);
				String newPath = dialog.open();
				if (newPath != null) {
					fPHPRoot.setText(newPath);
				}
			}
		});
		fPHPRoot.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpHome);
		fPHPRoot.setButtonLabel(PHPDebugUIMessages.addPHPexeDialog_browse1);

		fPHPIni = new StringButtonDialogField(new IStringButtonAdapter() {
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				dialog.setFilterPath(fPHPIni.getText());
				dialog.setFilterExtensions(new String[] { "*.ini", "*.*" });
				dialog.setText(PHPDebugUIMessages.addPHPexeDialog_pickPHPIniDialog_message);
				String newPath = dialog.open();
				if (newPath != null) {
					fPHPIni.setText(newPath);
				}
			}
		});
		fPHPIni.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpIni);
		fPHPIni.setButtonLabel(PHPDebugUIMessages.addPHPexeDialog_browse1);

		fPHPexeName.doFillIntoGrid(parent, 3);
		fPHPRoot.doFillIntoGrid(parent, 3);
		((GridData) fPHPRoot.getTextControl(parent).getLayoutData()).widthHint = pixelConverter.convertWidthInCharsToPixels(50);

		fPHPIni.doFillIntoGrid(parent, 3);
		((GridData) fPHPIni.getTextControl(parent).getLayoutData()).widthHint = pixelConverter.convertWidthInCharsToPixels(50);

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

		for (int i = 0; i < debuggersIds.size(); ++i) {
			String id = debuggersIds.get(i);
			String debuggerName = PHPDebuggersRegistry.getDebuggerName(id);
			fDebuggers.add(debuggerName, i);
		}

		init();
		createFieldListeners();
		validate();

		Dialog.applyDialogFont(this);
	}

	protected void createFieldListeners() {
		fPHPexeName.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				validate();
			}
		});

		fPHPRoot.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				validate();
			}
		});

		fPHPIni.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				validate();
			}
		});
	}

	protected void init() {
		PHPexeItem phpExeItem = getPHPExeItem();
		if (phpExeItem == null || phpExeItem.getName() == null) {
			fPHPexeName.setText(""); //$NON-NLS-1$
			fPHPRoot.setText(""); //$NON-NLS-1$
			fPHPIni.setText(""); //$NON-NLS-1$
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
			setTitle("Add PHP Executable");
		} else {
			initialName = phpExeItem.getName();
			fPHPexeName.setTextWithoutUpdate(phpExeItem.getName());
			fPHPexeName.setEnabled(phpExeItem.isEditable());
			fPHPRoot.setTextWithoutUpdate(phpExeItem.getExecutableDirectory().getAbsolutePath());
			fPHPRoot.setEnabled(phpExeItem.isEditable());
			if (phpExeItem.getINILocation() != null) {
				fPHPIni.setTextWithoutUpdate(phpExeItem.getINILocation().toString());
			}
			fPHPIni.setEnabled(phpExeItem.isEditable());
			String debuggerID = phpExeItem.getDebuggerID();
			fDebuggers.setEnabled(phpExeItem.isEditable());
			fDebuggersLabel.setEnabled(phpExeItem.isEditable());
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
			setTitle("Edit PHP Executable");
		}

		controlHandler.setTitle(getTitle());
		validate();
	}

	private void hideDebuggersCombo() {
		fDebuggers.setVisible(false);
		fDebuggersLabel.setVisible(false);
	}

	protected void validate() {
		PHPexeItem phpExeItem = getPHPExeItem();

		// Check whether we can edit this item
		if (phpExeItem != null && !phpExeItem.isEditable()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_readOnlyPHPExe, IMessageProvider.INFORMATION);
			return;
		}

		String name = fPHPexeName.getText();
		if (name == null || name.trim().length() == 0) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterName, IMessageProvider.INFORMATION);
			return;
		}

		// Check whether the name already exists:
		if (existingItems != null) {
			for (PHPexeItem item : existingItems) {
				if (!item.getName().equals(initialName) && item.getName().equals(name)) {
					setMessage(PHPDebugUIMessages.addPHPexeDialog_duplicateName, IMessageProvider.ERROR);
					return;
				}
			}
		}

		String locationName = fPHPRoot.getText();
		if (locationName.length() == 0) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterLocation, IMessageProvider.INFORMATION);
			return;
		}

		final File executableLocation = new File(locationName);
		if (!executableLocation.exists()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_locationNotExists, IMessageProvider.ERROR);
			return;
		}

		final boolean[] error = new boolean[1];
		Runnable r = new Runnable() {
			public void run() {
				File phpExecutable = PHPexeItem.findPHPExecutable(executableLocation);
				if (phpExecutable == null) {
					setMessage(PHPDebugUIMessages.PHPexe_executable_was_not_found_1, IMessageProvider.ERROR);
					error[0] = true;
				}
			}
		};
		BusyIndicator.showWhile(getShell().getDisplay(), r);
		if (error[0]) {
			return;
		}

		String iniLocationName = fPHPIni.getText();
		File iniFile = null;
		if (iniLocationName.trim().length() > 0) {
			iniFile = new File(iniLocationName);
			if (!iniFile.exists()) {
				setMessage(PHPDebugUIMessages.addPHPexeDialog_iniLocationNotExists, IMessageProvider.ERROR);
				return;
			}
		}

		phpExeItem.setName(name);
		phpExeItem.setExecutableDirectory(executableLocation);
		phpExeItem.setDebuggerID(debuggersIds.get(fDebuggers.getSelectionIndex()));
		phpExeItem.setINILocation(iniFile);
		setMessage(getDescription(), IMessageProvider.NONE);
		controlHandler.update();
	}

	protected void setMessage(String message, int type) {
		controlHandler.setMessage(message, type);
		setComplete(type == IMessageProvider.NONE);
		controlHandler.update();
	}

	public boolean performOk() {
		return true;
	}
}