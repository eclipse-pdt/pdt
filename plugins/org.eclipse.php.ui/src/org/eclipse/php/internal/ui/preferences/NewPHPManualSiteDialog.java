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
package org.eclipse.php.internal.ui.preferences;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class NewPHPManualSiteDialog extends StatusDialog {

	private static final String[] FILE_EXT = { "php", "htm", "html" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final boolean isWindows = System
			.getProperty("os.name").startsWith("Windows"); //$NON-NLS-1$ //$NON-NLS-2$
	private static final String FILE_PROTO = "file://"; //$NON-NLS-1$
	private static final String CHM_PROTO = "mk:@MSITStore:"; //$NON-NLS-1$
	private static final Pattern LANG_DETECT_PATTERN = Pattern
			.compile("/([a-z_]*)/function\\."); //$NON-NLS-1$

	protected Text name;
	protected Text url;
	private Button okButton;
	private boolean enableOK = false;
	private List configs;
	private PHPManualConfig edited;
	private PHPManualConfig result;
	private Button remoteSiteBtn;
	private Button localDirectoryBtn;
	private Button chmFileBtn;
	private Text localDir;
	private Text chmFile;
	private Button dirBrowseButton;
	private Button chmBrowseButton;
	private Label fileExtLabel;
	private Combo fileExtCombo;

	public NewPHPManualSiteDialog(Shell parentShell, PHPManualConfig edited,
			List configs) {
		super(parentShell);

		this.configs = new ArrayList(configs.size());
		for (int i = 0; i < configs.size(); i++) {
			PHPManualConfig config = (PHPManualConfig) configs.get(i);
			if (!config.equals(edited)) {
				this.configs.add(config);
			}
		}
		this.edited = edited;

		if (edited != null) {
			setTitle(PHPUIMessages.NewPHPManualSiteDialog_updateTitle);
		} else {
			setTitle(PHPUIMessages.NewPHPManualSiteDialog_newTitle);
		}
	}

	public PHPManualConfig getResult() {
		return result;
	}

	protected void setResult(PHPManualConfig result) {
		this.result = result;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		// super.createButtonBar(parent);
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		okButton.setEnabled(enableOK);
	}

	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 10;
		layout.marginWidth = 15;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.widthHint = 400;
		composite.setLayoutData(data);

		// --------- Site Name: ---------------------
		Composite siteNameGroup = new Composite(composite, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		siteNameGroup.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		siteNameGroup.setLayoutData(data);
		Label label = new Label(siteNameGroup, SWT.NONE);
		label.setText(PHPUIMessages.NewPHPManualSiteDialog_name);

		name = new Text(siteNameGroup, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyComplete();
			}
		});

		// --------- Remote Site: ---------------------
		remoteSiteBtn = new Button(composite, SWT.RADIO);
		remoteSiteBtn
				.setText(PHPUIMessages.NewPHPManualSiteDialog_remoteSiteURL); 
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		remoteSiteBtn.setLayoutData(data);
		remoteSiteBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = remoteSiteBtn.getSelection();
				url.setEnabled(enabled);
				if (enabled) {
					fileExtCombo.select(0);
				}
			}
		});

		url = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = convertWidthInCharsToPixels(3);
		data.horizontalSpan = 2;
		url.setLayoutData(data);
		url.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyComplete();
			}
		});

		// --------- Local Directory: ---------------------
		localDirectoryBtn = new Button(composite, SWT.RADIO);
		localDirectoryBtn
				.setText(PHPUIMessages.NewPHPManualSiteDialog_localDirectory); 
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		localDirectoryBtn.setLayoutData(data);
		localDirectoryBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean enabled = localDirectoryBtn.getSelection();
				localDir.setEnabled(enabled);
				dirBrowseButton.setEnabled(enabled);
				if (enabled) {
					fileExtCombo.select(2);
				}
			}
		});

		localDir = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = convertWidthInCharsToPixels(3);
		localDir.setLayoutData(data);
		localDir.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyComplete();
			}
		});

		dirBrowseButton = new Button(composite, SWT.NULL);
		data = new GridData();
		data.widthHint = 50;
		data.horizontalAlignment = SWT.END;
		dirBrowseButton.setLayoutData(data);
		dirBrowseButton.setText("..."); //$NON-NLS-1$
		dirBrowseButton.setAlignment(SWT.CENTER);
		dirBrowseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				DirectoryDialog dialog = new DirectoryDialog(PHPUiPlugin
						.getDefault().getWorkbench().getActiveWorkbenchWindow()
						.getShell(), SWT.OPEN);
				dialog.setText(PHPUIMessages.NewPHPManualSiteDialog_chooseDir);
				String directoryAsString = dialog.open();
				if (directoryAsString == null) {
					return;
				}
				localDir.setText(directoryAsString); 
			}
		});

		// --------- Site Name: ---------------------
		if (isWindows) {
			chmFileBtn = new Button(composite, SWT.RADIO);
			chmFileBtn
					.setText(PHPUIMessages.NewPHPManualSiteDialog_windowsCHMFile); 
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			chmFileBtn.setLayoutData(data);
			chmFileBtn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					boolean enabled = chmFileBtn.getSelection();
					chmFile.setEnabled(enabled);
					chmBrowseButton.setEnabled(enabled);
					if (enabled) {
						fileExtCombo.select(2);
					}
				}
			});

			chmFile = new Text(composite, SWT.BORDER);
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalIndent = convertWidthInCharsToPixels(3);
			chmFile.setLayoutData(data);
			chmFile.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					verifyComplete();
				}
			});

			chmBrowseButton = new Button(composite, SWT.NULL);
			data = new GridData();
			data.widthHint = 50;
			data.horizontalAlignment = SWT.END;
			chmBrowseButton.setLayoutData(data);
			chmBrowseButton.setText("..."); //$NON-NLS-1$
			chmBrowseButton.setAlignment(SWT.CENTER);
			chmBrowseButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					FileDialog dialog = new FileDialog(PHPUiPlugin.getDefault()
							.getWorkbench().getActiveWorkbenchWindow()
							.getShell(), SWT.OPEN);
					dialog.setText(PHPUIMessages.NewPHPManualSiteDialog_chooseCHMFile); 
					dialog.setFilterExtensions(new String[] { "*.chm" }); //$NON-NLS-1$
					String fileAsString = dialog.open();
					if (fileAsString == null) {
						return;
					}
					chmFile.setText(fileAsString); 
				}
			});
		}

		Composite fileExtGroup = new Composite(composite, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		fileExtGroup.setLayout(layout);
		data = new GridData();
		data.horizontalSpan = 2;
		fileExtGroup.setLayoutData(data);

		fileExtLabel = new Label(fileExtGroup, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		fileExtLabel.setLayoutData(data);
		fileExtLabel
				.setText(PHPUIMessages.NewPHPManualSiteDialog_fileExtension); 
		fileExtCombo = new Combo(fileExtGroup, SWT.READ_ONLY);
		fileExtCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				verifyComplete();
			}
		});
		fileExtCombo.setItems(FILE_EXT);
		fileExtCombo.select(0);

		initializeFields();
		Dialog.applyDialogFont(composite);
		return composite;
	}

	protected void initializeFields() {
		if (edited != null) {
			name.setText(edited.getLabel());
			fileExtCombo.setText(edited.getExtension());

			String urlStr = edited.getUrl();
			if (isWindows && urlStr.startsWith(CHM_PROTO)) {
				urlStr = urlStr.substring(CHM_PROTO.length());
				int sepIdx = urlStr.indexOf("::"); //$NON-NLS-1$
				if (sepIdx != -1) {
					urlStr = urlStr.substring(0, sepIdx);
				}
				chmFile.setText(urlStr);
				chmFileBtn.setSelection(true);
			} else if (urlStr.startsWith(FILE_PROTO)) {
				urlStr = urlStr.substring(FILE_PROTO.length());
				localDir.setText(urlStr);
				localDirectoryBtn.setSelection(true);
			} else {
				url.setText(urlStr);
			}
		} else {
			url.setText("http://www.php.net/manual/en"); //$NON-NLS-1$
			remoteSiteBtn.setSelection(true);
		}

		remoteSiteBtn.notifyListeners(SWT.Selection, new Event());
		localDirectoryBtn.notifyListeners(SWT.Selection, new Event());
		if (isWindows) {
			chmFileBtn.notifyListeners(SWT.Selection, new Event());
		}
	}

	private void verifyComplete() {
		if (okButton == null) {
			return;
		}

		if (name.getText().trim().length() == 0) {
			okButton.setEnabled(false);
			this.updateStatus(new Status(IStatus.ERROR, PHPUiPlugin
					.getPluginId(), IStatus.OK,
					PHPUIMessages.NewPHPManualSiteDialog_siteNotSpecified, null));
			return;
		}

		if (isDuplicate()) {
			return;
		} else {
			okButton.setEnabled(true);
			this.updateStatus(new Status(IStatus.OK, PHPUiPlugin.getPluginId(),
					IStatus.OK, "", null)); //$NON-NLS-1$
		}

		if (remoteSiteBtn.getSelection()) {
			try {
				String urlStr = url.getText().trim();
				new URL(URLDecoder.decode(urlStr, "UTF-8")); //$NON-NLS-1$
				result = new PHPManualConfig(name.getText(), urlStr,
						fileExtCombo.getText(), false);
			} catch (Exception e) {
				okButton.setEnabled(false);
				this.updateStatus(new Status(IStatus.ERROR, PHPUiPlugin
						.getPluginId(), IStatus.OK,
						PHPUIMessages.NewPHPManualSiteDialog_incorrectUrl, null));
				return;
			}
		} else if (localDirectoryBtn.getSelection()) {
			String localDirStr = localDir.getText().trim();
			if (!new File(localDirStr).isDirectory()) {
				okButton.setEnabled(false);
				this.updateStatus(new Status(IStatus.ERROR, PHPUiPlugin
						.getPluginId(), IStatus.OK,
						PHPUIMessages.NewPHPManualSiteDialog_dirDoesntExist,
						null)); 
				return;
			}
			result = new PHPManualConfig(name.getText(), FILE_PROTO
					+ localDirStr, fileExtCombo.getText(), false);
		} else if (isWindows) {
			String chmFileStr = chmFile.getText().trim();
			if (!new File(chmFileStr).isFile()) {
				okButton.setEnabled(false);
				this.updateStatus(new Status(IStatus.ERROR, PHPUiPlugin
						.getPluginId(), IStatus.OK,
						PHPUIMessages.NewPHPManualSiteDialog_fileDoesntExist,
						null)); 
				return;
			}
			result = new PHPManualConfig(name.getText(), CHM_PROTO + chmFileStr
					+ detectCHMLanguageSuffix(chmFileStr),
					fileExtCombo.getText(), false); 
		}
	}

	private boolean isDuplicate() {
		if (configs == null) {
			return false;
		}
		for (int i = 0; i < configs.size(); i++) {
			if (!isCurrentlyEditedSiteBookmark(i)) {
				PHPManualConfig config = (PHPManualConfig) configs.get(i);
				if (config.getLabel().equals(name.getText().trim())) {
					okButton.setEnabled(false);
					this.updateStatus(new Status(
							IStatus.ERROR,
							PHPUiPlugin.getPluginId(),
							IStatus.OK,
							PHPUIMessages.NewPHPManualSiteDialog_nameAlreadyInUse,
							null));
					return true;
				}
			}
		}
		return false;
	}

	protected boolean isCurrentlyEditedSiteBookmark(int index) {
		return false;
	}

	protected void updateButtonsEnableState(IStatus status) {
		if (okButton != null && !okButton.isDisposed()
				&& name.getText().trim().length() != 0)
			okButton.setEnabled(!status.matches(IStatus.ERROR));
	}

	private String detectCHMLanguageSuffix(String chmFile) {
		StringBuffer suffix = new StringBuffer("::/"); //$NON-NLS-1$
		char[] buf = new char[8192];
		try {
			FileReader r = new FileReader(chmFile);
			try {
				while (r.ready()) {
					r.read(buf);

					Matcher m = LANG_DETECT_PATTERN.matcher(new String(buf));
					if (m.find()) {
						suffix.append(m.group(1));
						break;
					}
				}
			} finally {
				r.close();
			}
		} catch (Exception e) {
		}
		return suffix.toString();
	}
}
