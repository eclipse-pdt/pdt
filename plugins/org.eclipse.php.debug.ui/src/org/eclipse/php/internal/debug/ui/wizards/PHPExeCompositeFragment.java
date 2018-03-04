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
 *     Dawid Paku�a [339547]
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.PHPExeException;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPExeInfo;
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

public class PHPExeCompositeFragment extends CompositeFragment implements IPHPExeCompositeFragment {

	private static final String PHP_INI = "php.ini"; //$NON-NLS-1$
	private PHPexeItem[] existingItems;
	private StringDialogField fPHPexeName;
	private StringButtonDialogField fPHPExePath;
	private StringButtonDialogField fPHPIni;
	private Button fLoadDefaultPHPIni;
	private Label fSapiTypesLabel;
	private Combo fSapiTypes;
	private Label fVersionLabel;
	private String initialName;
	private boolean isIniFileSet = false;

	public PHPExeCompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		createDescription();
	}

	protected void createDescription() {
		setDisplayName(PHPDebugUIMessages.PHPExeCompositeFragment_2);
		setDescription(PHPDebugUIMessages.PHPExeCompositeFragment_0);
		controlHandler.setDescription(getDescription());
		setImageDescriptor(PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_WIZBAN_PHPEXE));
		controlHandler.setImageDescriptor(getImageDescriptor());
		switch (controlHandler.getKind()) {
		case WIZARD:
			setTitle(PHPDebugUIMessages.PHPExeCompositeFragment_10);
			break;
		case EDITOR:
			setTitle(PHPDebugUIMessages.PHPExeCompositeFragment_11);
			break;
		default:
			break;
		}
		controlHandler.setTitle(getTitle());
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

	@Override
	protected void createContents(Composite parent) {
		PixelConverter pixelConverter = new PixelConverter(parent);

		Composite settingsComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		settingsComposite.setLayout(layout);
		settingsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		fPHPexeName = new StringDialogField();
		fPHPexeName.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpName);

		fPHPExePath = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				dialog.setFilterPath(fPHPExePath.getText());
				dialog.setText(PHPDebugUIMessages.addPHPexeDialog_pickPHPRootDialog_message);
				String newPath = dialog.open();
				if (newPath != null) {
					fPHPExePath.setText(newPath);
				}
			}
		});
		fPHPExePath.setLabelText(PHPDebugUIMessages.addPHPexeDialog_phpHome);
		fPHPExePath.setButtonLabel(PHPDebugUIMessages.addPHPexeDialog_browse1);

		fPHPIni = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
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

		fPHPexeName.doFillIntoGrid(settingsComposite, 3);
		fPHPExePath.doFillIntoGrid(settingsComposite, 3);
		((GridData) fPHPExePath.getTextControl(settingsComposite).getLayoutData()).widthHint = pixelConverter
				.convertWidthInCharsToPixels(50);

		fPHPIni.doFillIntoGrid(settingsComposite, 3);
		((GridData) fPHPIni.getTextControl(settingsComposite).getLayoutData()).widthHint = pixelConverter
				.convertWidthInCharsToPixels(50);

		fLoadDefaultPHPIni = new Button(settingsComposite, SWT.CHECK);
		fLoadDefaultPHPIni.setText(PHPDebugUIMessages.addPHPexeDialog_loadDefaultPHPIni);
		GridData loadDefaultPHPIniData = new GridData(GridData.FILL);
		loadDefaultPHPIniData.horizontalSpan = 3;
		fLoadDefaultPHPIni.setLayoutData(loadDefaultPHPIniData);

		fSapiTypesLabel = new Label(settingsComposite, SWT.LEFT | SWT.WRAP);
		fSapiTypesLabel.setFont(settingsComposite.getFont());
		fSapiTypesLabel.setText(PHPDebugUIMessages.PHPExeCompositeFragment_1);
		GridData data = new GridData();
		data.horizontalSpan = 1;
		fSapiTypesLabel.setLayoutData(data);

		fSapiTypes = new Combo(settingsComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		fSapiTypes.setLayoutData(data);
		fSapiTypes.add(PHPexeItem.SAPI_CLI);
		fSapiTypes.add(PHPexeItem.SAPI_CGI);

		Label versionLabel = new Label(settingsComposite, SWT.LEFT | SWT.WRAP);
		versionLabel.setFont(settingsComposite.getFont());
		versionLabel.setText("Version: "); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 1;
		versionLabel.setLayoutData(data);

		fVersionLabel = new Label(settingsComposite, SWT.LEFT | SWT.WRAP);
		data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = 100;
		data.grabExcessHorizontalSpace = true;
		fVersionLabel.setLayoutData(data);
		fVersionLabel.setText(""); //$NON-NLS-1$

		init();
		createFieldListeners();
		Dialog.applyDialogFont(this);
	}

	protected void createFieldListeners() {
		fPHPexeName.setDialogFieldListener(new IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(DialogField field) {
				updateItem();
			}
		});

		fPHPExePath.setDialogFieldListener(new IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(DialogField field) {
				String newPath = fPHPExePath.getText();
				if (newPath != null && newPath.trim().length() > 0) {
					if (!isIniFileSet || (fPHPIni.getText() == null || fPHPIni.getText().trim().length() == 0)) {
						IPath path = new Path(newPath);
						path = path.removeLastSegments(1);
						path = path.append(PHP_INI);
						if (path.toFile().exists()) {
							fPHPIni.setTextWithoutUpdate(path.toOSString());
						}
					}
					File executable = new File(newPath);
					PHPExeInfo phpExecInfo = getPHPInfo(executable);
					if (phpExecInfo != null) {
						if (fPHPexeName.getText().isEmpty() && phpExecInfo.getName() != null) {
							fPHPexeName.setTextWithoutUpdate(phpExecInfo.getName());
						}
						if (phpExecInfo.getSapiType() != null) {
							fSapiTypes.setText(phpExecInfo.getSapiType());
						}
						if (phpExecInfo.getVersion() != null) {
							fVersionLabel.setText(phpExecInfo.getVersion());
						}
					} else {
						fSapiTypes.deselectAll();
						fVersionLabel.setText(""); //$NON-NLS-1$
					}
				}
				updateItem();
			}
		});

		fPHPIni.setDialogFieldListener(new IDialogFieldListener() {
			@Override
			public void dialogFieldChanged(DialogField field) {
				updateItem();
			}
		});

		fLoadDefaultPHPIni.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateItem();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateItem();
			}
		});

		fSapiTypes.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateItem();
			}

			@Override
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
		} else {
			initialName = phpExeItem.getName();
			fPHPexeName.setTextWithoutUpdate(phpExeItem.getName());
			fPHPexeName.setEnabled(phpExeItem.isEditable());
			fPHPExePath.setTextWithoutUpdate(phpExeItem.getExecutable().getAbsolutePath());
			fPHPExePath.setEnabled(phpExeItem.isEditable());
			if (phpExeItem.getINILocation() != null) {
				fPHPIni.setTextWithoutUpdate(phpExeItem.getINILocation().toString());
			}
			fPHPIni.setEnabled(phpExeItem.isEditable());
			fLoadDefaultPHPIni.setEnabled(phpExeItem.isEditable());
			fLoadDefaultPHPIni.setSelection(phpExeItem.isLoadDefaultINI());
			String sapiType = phpExeItem.getSapiType();
			if (sapiType != null) {
				fSapiTypes.setText(sapiType);
			}
			fSapiTypes.setEnabled(phpExeItem.isEditable());
			fSapiTypesLabel.setEnabled(phpExeItem.isEditable());
			fVersionLabel.setText(phpExeItem.getVersion());
		}
		updateItem();
	}

	@Override
	public void setExistingItems(PHPexeItem[] existingItems) {
		this.existingItems = existingItems;
	}

	@Override
	public void setData(Object data) {
		if (data != null && !(data instanceof PHPexeItem)) {
			throw new IllegalArgumentException(PHPDebugUIMessages.PHPExeCompositeFragment_3);
		}
		super.setData(data);
		init();
		fPHPExePath.getChangeControl(null).setFocus();
	}

	@Override
	public void validate() {
		PHPexeItem phpExeItem = getPHPExeItem();
		// Let's reset previous state
		setComplete(true);
		setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_0, IMessageProvider.INFORMATION);
		/* MESSAGES */
		// Check if PHP executable location is empty
		if (phpExeItem.getExecutable() == null || phpExeItem.getExecutable().getPath().length() == 0) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterLocation, IMessageProvider.INFORMATION);
			setComplete(false);
			return;
		}
		// Check if name is empty
		if (phpExeItem.getName().isEmpty()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_enterName, IMessageProvider.INFORMATION);
			setComplete(false);
			return;
		}
		/* ERRORS */
		// Check PHP executable
		if (phpExeItem.getExecutable() == null) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_13, IMessageProvider.ERROR);
			return;
		}
		if (!phpExeItem.getExecutable().exists()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_locationNotExists, IMessageProvider.ERROR);
			return;
		}
		PHPExeInfo phpExecInfo = getPHPInfo(phpExeItem.getExecutable());
		if (phpExecInfo == null) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_13, IMessageProvider.ERROR);
			return;
		}
		// Check whether the name already exists:
		if (existingItems != null) {
			for (PHPexeItem item : existingItems) {
				if (!item.getName().equals(initialName) && item.getName().equals(phpExeItem.getName())) {
					setMessage(PHPDebugUIMessages.addPHPexeDialog_duplicateName, IMessageProvider.ERROR);
					return;
				}
			}
		}
		// Check if SAPI type is provided
		if (phpExeItem.getSapiType().isEmpty()) {
			setMessage(PHPDebugUIMessages.PHPExeCompositeFragment_15, IMessageProvider.ERROR);
			return;
		}
		if (phpExecInfo.getSapiType() != null && !phpExeItem.getSapiType().equals(phpExecInfo.getSapiType())) {
			setMessage(
					MessageFormat.format(PHPDebugUIMessages.addPHPexeDialog_wrongSAPItype, phpExecInfo.getSapiType()),
					IMessageProvider.ERROR);
			return;
		}
		// Check INI file location
		if (phpExeItem.getINILocation() != null) {
			String iniLocationName = phpExeItem.getINILocation().getPath();
			File iniFile = null;
			if (iniLocationName.trim().length() > 0) {
				iniFile = new File(iniLocationName);
				if (!iniFile.exists()) {
					setMessage(PHPDebugUIMessages.addPHPexeDialog_iniLocationNotExists, IMessageProvider.ERROR);
					return;
				}
			}
		}
		// Update control handler
		controlHandler.update();
	}

	@Override
	public boolean performOk() {
		return true;
	}

	public PHPexeItem getPHPExeItem() {
		return (PHPexeItem) super.getData();
	}

	/**
	 * @param executable
	 * @return PHP executable info or <code>null</code> if provided executable is
	 *         invalid
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
		if (phpExeItem == null) {
			return;
		}
		// Check whether we can edit this item
		if (phpExeItem != null && !phpExeItem.isEditable()) {
			setMessage(PHPDebugUIMessages.addPHPexeDialog_readOnlyPHPExe, IMessageProvider.INFORMATION);
			setComplete(false);
			// If it is not editable it doesn't mean that it is correct
			validate();
			return;
		}
		// Set up PHP exe item.
		phpExeItem.setLoadDefaultINI(fLoadDefaultPHPIni.getSelection());
		phpExeItem.setExecutable(fPHPExePath.getText().isEmpty() ? null : new File(fPHPExePath.getText()));
		phpExeItem.setName(fPHPexeName.getText());
		phpExeItem.setINILocation(fPHPIni.getText().isEmpty() ? null : new File(fPHPIni.getText()));
		phpExeItem.setSapiType(fSapiTypes.getText());
		PHPExeInfo phpExeInfo = getPHPInfo(phpExeItem.getExecutable());
		if (phpExeInfo != null) {
			// Set up PHP exe item version
			phpExeItem.setVersion(phpExeInfo.getVersion());
		}
		// Validate all
		validate();
	}
}