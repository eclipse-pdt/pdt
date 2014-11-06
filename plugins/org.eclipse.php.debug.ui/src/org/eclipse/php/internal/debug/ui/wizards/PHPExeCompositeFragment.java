/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [339547]
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import java.io.File;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.PHPExeException;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPExeInfo;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class PHPExeCompositeFragment extends CompositeFragment implements
		IPHPExeCompositeFragment {

	private static final String PHP_INI = "php.ini"; //$NON-NLS-1$
	private PHPexeItem[] existingItems;
	private StringDialogField fPHPexeName;
	private StringButtonDialogField fPHPExePath;
	private StringButtonDialogField fPHPIni;
	private Button fLoadDefaultPHPIni;
	private List<String> debuggersIds;
	private Label fDebuggersLabel;
	private Combo fDebuggers;
	private Label fSapiTypesLabel;
	private Combo fSapiTypes;
	private String initialName;
	private boolean isIniFileSet = false;
	private boolean updateDebuggerStatus = true;

	public PHPExeCompositeFragment(Composite parent, IControlHandler handler,
			boolean isForEditing) {
		super(parent, handler, isForEditing);
		setDescription(PHPDebugUIMessages.PHPExeCompositeFragment_0);
		setDisplayName(PHPDebugUIMessages.PHPExeCompositeFragment_2);
		controlHandler.setDescription(getDescription());
		controlHandler.setImageDescriptor(PHPDebugUIImages
				.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_PHPEXE));

		debuggersIds = new LinkedList<String>(
				PHPDebuggersRegistry.getDebuggersIds());
		createControl();
	}

	public void setExistingItems(PHPexeItem[] existingItems) {
		this.existingItems = existingItems;
	}

	public void setData(Object data) {
		if (data != null && !(data instanceof PHPexeItem)) {
			throw new IllegalArgumentException(
					PHPDebugUIMessages.PHPExeCompositeFragment_3);
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
		return new File(fPHPExePath.getText());
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

		fPHPExePath = new StringButtonDialogField(new IStringButtonAdapter() {
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				dialog.setFilterPath(fPHPExePath.getText());
				dialog.setText(PHPDebugUIMessages.addPHPexeDialog_pickPHPRootDialog_message);
				String newPath = dialog.open();
				if (newPath != null) {
					fPHPExePath.setText(newPath);
					File executable = new File(newPath);
					PHPExeInfo phpExecInfo = getPHPInfo(executable);
					if (phpExecInfo != null) {
						if (fPHPexeName.getText().isEmpty()
								&& phpExecInfo.getName() != null)
							fPHPexeName.setTextWithoutUpdate(phpExecInfo
									.getName());
						if (phpExecInfo.getSapiType() != null)
							fSapiTypes.setText(phpExecInfo.getSapiType());
					}
				}
				updateItem();
			}
		});
		fPHPExePath.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpHome);
		fPHPExePath.setButtonLabel(PHPDebugUIMessages.addPHPexeDialog_browse1);

		fPHPIni = new StringButtonDialogField(new IStringButtonAdapter() {
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				dialog.setFilterPath(fPHPIni.getText());
				dialog.setFilterExtensions(new String[] { "*.ini", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setText(PHPDebugUIMessages.addPHPexeDialog_pickPHPIniDialog_message);
				String newPath = dialog.open();
				if (newPath != null) {
					fPHPIni.setText(newPath);
					isIniFileSet = true;
				}
			}
		});
		fPHPIni.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpIni);
		fPHPIni.setButtonLabel(PHPDebugUIMessages.addPHPexeDialog_browse1);

		fPHPexeName.doFillIntoGrid(parent, 3);
		fPHPExePath.doFillIntoGrid(parent, 3);
		((GridData) fPHPExePath.getTextControl(parent).getLayoutData()).widthHint = pixelConverter
				.convertWidthInCharsToPixels(50);

		fPHPIni.doFillIntoGrid(parent, 3);
		((GridData) fPHPIni.getTextControl(parent).getLayoutData()).widthHint = pixelConverter
				.convertWidthInCharsToPixels(50);

		fLoadDefaultPHPIni = new Button(parent, SWT.CHECK);
		fLoadDefaultPHPIni
				.setText(PHPDebugUIMessages.addPHPexeDialog_loadDefaultPHPIni);
		GridData loadDefaultPHPIniData = new GridData(GridData.FILL);
		loadDefaultPHPIniData.horizontalSpan = 3;
		fLoadDefaultPHPIni.setLayoutData(loadDefaultPHPIniData);

		fSapiTypesLabel = new Label(parent, SWT.LEFT | SWT.WRAP);
		fSapiTypesLabel.setFont(parent.getFont());
		fSapiTypesLabel.setText(PHPDebugUIMessages.PHPExeCompositeFragment_1);
		GridData data = new GridData();
		data.horizontalSpan = 1;
		fSapiTypesLabel.setLayoutData(data);

		fSapiTypes = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		fSapiTypes.setLayoutData(data);
		fSapiTypes.add(PHPexeItem.SAPI_CLI);
		fSapiTypes.add(PHPexeItem.SAPI_CGI);

		fDebuggersLabel = new Label(parent, SWT.LEFT | SWT.WRAP);
		fDebuggersLabel.setFont(parent.getFont());
		fDebuggersLabel.setText(PHPDebugUIMessages.addPHPexeDialog_phpDebugger);
		data = new GridData();
		data.horizontalSpan = 1;
		fDebuggersLabel.setLayoutData(data);

		fDebuggers = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		fDebuggers.setLayoutData(data);

		for (int i = 0; i < debuggersIds.size(); ++i) {
			String id = debuggersIds.get(i);
			String debuggerName = PHPDebuggersRegistry.getDebuggerName(id);
			fDebuggers.add(debuggerName, i);
		}

		init();
		createFieldListeners();
		updateItem();

		Dialog.applyDialogFont(this);
	}

	protected void createFieldListeners() {
		fPHPexeName.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				// Do not validate debugger while changing name
				if (isComplete())
					updateDebuggerStatus = false;
				updateItem();
			}
		});

		fPHPExePath.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				String newPath = fPHPExePath.getText();
				if (newPath != null && newPath.trim().length() > 0) {
					if (!isIniFileSet
							|| (fPHPIni.getText() == null || fPHPIni.getText()
									.trim().length() == 0)) {
						IPath path = new Path(newPath);
						path = path.removeLastSegments(1);
						path = path.append(PHP_INI);
						if (path.toFile().exists()) {
							fPHPIni.setText(path.toOSString());
						}
					}
				}
				updateItem();
			}
		});

		fPHPIni.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				updateItem();
			}
		});

		fLoadDefaultPHPIni.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateItem();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				updateItem();
			}
		});

		fSapiTypes.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				updateItem();
			}

			public void widgetSelected(SelectionEvent e) {
				updateItem();
			}
		});

		fDebuggers.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				updateItem();
			}

			public void widgetSelected(SelectionEvent e) {
				updateItem();
			}
		});
	}

	protected void init() {
		PHPexeItem phpExeItem = getPHPExeItem();
		if (phpExeItem == null || phpExeItem.getName() == null) {
			fPHPexeName.setTextWithoutUpdate(""); //$NON-NLS-1$
			fPHPExePath.setTextWithoutUpdate(""); //$NON-NLS-1$
			fPHPIni.setTextWithoutUpdate(""); //$NON-NLS-1$
			String defaultDebuggerId = PHPDebuggersRegistry
					.getDefaultDebuggerId();
			if (defaultDebuggerId != null) {
				int index = fDebuggers.indexOf(PHPDebuggersRegistry
						.getDebuggerName(defaultDebuggerId));
				fDebuggers.select(index);
			} else {
				if (fDebuggers.getItemCount() > 0) {
					fDebuggers.select(0);
				} else {
					hideDebuggersCombo();
				}
			}
			setTitle(PHPDebugUIMessages.PHPExeCompositeFragment_10);
		} else {
			initialName = phpExeItem.getName();
			fPHPexeName.setTextWithoutUpdate(phpExeItem.getName());
			fPHPexeName.setEnabled(phpExeItem.isEditable());
			fPHPExePath.setTextWithoutUpdate(phpExeItem.getExecutable()
					.getAbsolutePath());
			fPHPExePath.setEnabled(phpExeItem.isEditable());
			if (phpExeItem.getINILocation() != null) {
				fPHPIni.setTextWithoutUpdate(phpExeItem.getINILocation()
						.toString());
			}
			fPHPIni.setEnabled(phpExeItem.isEditable());
			fLoadDefaultPHPIni.setEnabled(phpExeItem.isEditable());
			fLoadDefaultPHPIni.setSelection(phpExeItem.isLoadDefaultINI());
			String debuggerID = phpExeItem.getDebuggerID();
			fDebuggers.setEnabled(phpExeItem.isEditable());
			fDebuggersLabel.setEnabled(phpExeItem.isEditable());
			int index = fDebuggers.indexOf(PHPDebuggersRegistry
					.getDebuggerName(debuggerID));
			if (index > -1) {
				fDebuggers.select(index);
			} else {
				if (fDebuggers.getItemCount() > 0) {
					fDebuggers.select(0);
				} else {
					hideDebuggersCombo();
				}
			}
			String sapiType = phpExeItem.getSapiType();
			if (sapiType != null) {
				fSapiTypes.setText(sapiType);
			}
			fSapiTypes.setEnabled(phpExeItem.isEditable());
			fSapiTypesLabel.setEnabled(phpExeItem.isEditable());

			setTitle(PHPDebugUIMessages.PHPExeCompositeFragment_11);
		}

		controlHandler.setTitle(getTitle());
	}

	private void hideDebuggersCombo() {
		fDebuggers.setVisible(false);
		fDebuggersLabel.setVisible(false);
	}

	public void validate() {
		PHPexeItem phpExeItem = getPHPExeItem();
		// Let's reset previous state
		setComplete(true);
		setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_0,
				IMessageProvider.INFORMATION);
		/* MESSAGES */
		// Check if PHP executable location is empty
		if (phpExeItem.getExecutable() == null
				|| phpExeItem.getExecutable().getPath().length() == 0) {
			setComplete(false);
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterLocation,
					IMessageProvider.INFORMATION);
			return;
		}
		// Check if name is empty
		if (phpExeItem.getName().isEmpty()) {
			setComplete(false);
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterName,
					IMessageProvider.INFORMATION);
			return;
		}
		/* ERRORS */
		// Check PHP executable
		if (phpExeItem.getExecutable() == null) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_13,
					IMessageProvider.ERROR);
			return;
		}
		if (!phpExeItem.getExecutable().exists()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_locationNotExists,
					IMessageProvider.ERROR);
			return;
		}
		PHPExeInfo phpExecInfo = getPHPInfo(phpExeItem.getExecutable());
		if (phpExecInfo == null) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_13,
					IMessageProvider.ERROR);
			return;
		}
		// Check whether the name already exists:
		if (existingItems != null) {
			for (PHPexeItem item : existingItems) {
				if (!item.getName().equals(initialName)
						&& item.getName().equals(phpExeItem.getName())) {
					setMessage(
							PHPDebugUIMessages.addPHPexeDialog_duplicateName,
							IMessageProvider.ERROR);
					return;
				}
			}
		}
		// Check if SAPI type is provided
		if (phpExeItem.getSapiType().isEmpty()) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_15,
					IMessageProvider.ERROR);
			return;
		}
		if (phpExecInfo.getSapiType() != null
				&& !phpExeItem.getSapiType().equals(phpExecInfo.getSapiType())) {
			setMessage(MessageFormat.format(
					PHPDebugUIMessages.addPHPexeDialog_wrongSAPItype,
					phpExecInfo.getSapiType()), IMessageProvider.ERROR);
			return;
		}
		// Check INI file location
		if (phpExeItem.getINILocation() != null) {
			String iniLocationName = phpExeItem.getINILocation().getPath();
			File iniFile = null;
			if (iniLocationName.trim().length() > 0) {
				iniFile = new File(iniLocationName);
				if (!iniFile.exists()) {
					setMessage(
							PHPDebugUIMessages.addPHPexeDialog_iniLocationNotExists,
							IMessageProvider.ERROR);
					return;
				}
			}
		}
		// Check debugger status
		if (updateDebuggerStatus) {
			IStatus debuggerStatus = Status.OK_STATUS;
			AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry
					.getDebuggersConfigurations();
			for (AbstractDebuggerConfiguration debugger : debuggers) {
				if (getPHPExeItem().getDebuggerID().equals(
						debugger.getDebuggerId())) {
					debuggerStatus = debugger.validate(getPHPExeItem());
				}
			}
			if (debuggerStatus.getSeverity() != IStatus.OK) {
				if (debuggerStatus.getSeverity() == IStatus.ERROR) {
					setMessage(debuggerStatus.getMessage(),
							IMessageProvider.ERROR);
					// set complete ?
					return;
				} else {
					setMessage(debuggerStatus.getMessage(),
							IMessageProvider.WARNING);
				}
			} else {
				setMessage(getDescription(), IMessageProvider.NONE);
			}
		}
		updateDebuggerStatus = true;
		// Update control handler
		controlHandler.update();
	}

	protected void setMessage(String message, int type) {
		controlHandler.setMessage(message, type);
		// Might be already incomplete
		if (isComplete())
			setComplete(type != IMessageProvider.ERROR);
		controlHandler.update();
	}

	public boolean performOk() {
		return true;
	}

	/**
	 * @param executable
	 * @return PHP executable info or <code>null</code> if provided executable
	 *         is invalid
	 */
	private PHPExeInfo getPHPInfo(File executable) {
		try {
			return PHPExeUtil.getPHPInfo(executable, false);
		} catch (PHPExeException e) {
			return null;
		}
	}

	private void updateItem() {
		PHPexeItem phpExeItem = getPHPExeItem();
		if (phpExeItem == null)
			return;
		// Check whether we can edit this item
		if (phpExeItem != null && !phpExeItem.isEditable()) {
			setComplete(false);
			setMessage(PHPDebugUIMessages.addPHPexeDialog_readOnlyPHPExe,
					IMessageProvider.INFORMATION);
			// If it is not editable it doesn't mean that it is correct
			validate();
			return;
		}
		// Set up PHP exe item.
		phpExeItem.setLoadDefaultINI(fLoadDefaultPHPIni.getSelection());
		phpExeItem.setExecutable(new File(fPHPExePath.getText()));
		phpExeItem.setName(fPHPexeName.getText());
		phpExeItem.setDebuggerID(debuggersIds.get(fDebuggers
				.getSelectionIndex()));
		phpExeItem.setINILocation(fPHPIni.getText().isEmpty() ? null
				: new File(fPHPIni.getText()));
		phpExeItem.setSapiType(fSapiTypes.getText());
		PHPExeInfo phpExeInfo = getPHPInfo(phpExeItem.getExecutable());
		if (phpExeInfo != null)
			phpExeItem.setVersion(phpExeInfo.getVersion());
		// Validate all
		validate();
	}
}