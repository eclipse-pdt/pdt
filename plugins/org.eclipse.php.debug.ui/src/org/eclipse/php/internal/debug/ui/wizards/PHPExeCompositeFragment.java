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
package org.eclipse.php.internal.debug.ui.wizards;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;

public class PHPExeCompositeFragment extends CompositeFragment implements
		IPHPExeCompositeFragment {

	private static final String PHP_INI = "php.ini"; //$NON-NLS-1$
	private PHPexeItem[] existingItems;
	private StringDialogField fPHPexeName;
	private StringButtonDialogField fPHPExePath;
	private StringButtonDialogField fPHPIni;
	private List<String> debuggersIds;
	private Label fDebuggersLabel;
	private Combo fDebuggers;
	private Label fSapiTypesLabel;
	private Combo fSapiTypes;
	private String initialName;
	private boolean isIniFileSet = false;

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
				}
				validate();
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
		validate();

		Dialog.applyDialogFont(this);
	}

	protected void createFieldListeners() {
		fPHPexeName.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				validate();
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
				validate();
			}
		});

		fPHPIni.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				validate();
			}
		});

		fSapiTypes.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				validate();
			}

			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		});

		fDebuggers.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				validate();
			}

			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		});
	}

	protected void init() {
		PHPexeItem phpExeItem = getPHPExeItem();
		if (phpExeItem == null || phpExeItem.getName() == null) {
			fPHPexeName.setText(""); //$NON-NLS-1$
			fPHPExePath.setText(""); //$NON-NLS-1$
			fPHPIni.setText(""); //$NON-NLS-1$
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
			setMessage(PHPDebugUIMessages.addPHPexeDialog_readOnlyPHPExe,
					IMessageProvider.INFORMATION);
			return;
		}

		String locationName = fPHPExePath.getText();
		if (locationName.length() == 0) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterLocation,
					IMessageProvider.INFORMATION);
			return;
		}
		final File executableLocation = new File(locationName);
		if (!executableLocation.exists()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_locationNotExists,
					IMessageProvider.ERROR);
			return;
		}
		if (!executableLocation.getName().toLowerCase().contains("php")) { //$NON-NLS-1$
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_13,
					IMessageProvider.ERROR);
			return;
		}

		phpExeItem.setExecutable(executableLocation);

		if (phpExeItem.getSapiType() != null
				&& (fSapiTypes.getText() == null || fSapiTypes.getText().trim()
						.length() == 0)) {
			fSapiTypes.setText(phpExeItem.getSapiType());
		}

		if (phpExeItem.getExecutable() == null) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_13,
					IMessageProvider.ERROR);
			return;
		}

		String name = fPHPexeName.getText();
		if (name == null || name.trim().length() == 0) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterName,
					IMessageProvider.INFORMATION);
			return;
		}

		// Check whether the name already exists:
		if (existingItems != null) {
			for (PHPexeItem item : existingItems) {
				if (!item.getName().equals(initialName)
						&& item.getName().equals(name)) {
					setMessage(
							PHPDebugUIMessages.addPHPexeDialog_duplicateName,
							IMessageProvider.ERROR);
					return;
				}
			}
		}

		String sapiType = fSapiTypes.getText().trim();
		if (sapiType.length() == 0) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_15,
					IMessageProvider.ERROR);
			return;
		}

		String iniLocationName = fPHPIni.getText();
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

		phpExeItem.setName(name);
		phpExeItem.setDebuggerID(debuggersIds.get(fDebuggers
				.getSelectionIndex()));
		phpExeItem.setINILocation(iniFile);
		phpExeItem.setSapiType(sapiType);

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