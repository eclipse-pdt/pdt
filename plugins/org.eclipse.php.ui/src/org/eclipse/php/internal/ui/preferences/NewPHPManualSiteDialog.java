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
package org.eclipse.php.internal.ui.preferences;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.PHPManualConfigurationBlock.PHPManualConfig;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewPHPManualSiteDialog extends StatusDialog {
	
	protected Text name;
	protected Text url;
	protected Text extension;
	private Button okButton;
	private Button browseButton; 
	private boolean enableOK = false;
	private List configs;
	private PHPManualConfig edited;
	private PHPManualConfig result;
	
	public NewPHPManualSiteDialog(Shell parentShell, PHPManualConfig edited, List configs) {
		super(parentShell);
		
		this.configs = new ArrayList(configs.size());
		for (int i = 0; i < configs.size(); i++) {
			PHPManualConfig config = (PHPManualConfig)configs.get(i);
			if (!config.equals(edited)) {
				this.configs.add (config);
			}
		}
		this.edited = edited;
		
		if (edited != null) {
			setTitle (PHPUIMessages.NewPHPManualSiteDialog_updateTitle);
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
		
		//super.createButtonBar(parent);
		okButton = createButton(
				parent,
				IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL,
				true);
		createButton(
			parent,
			IDialogConstants.CANCEL_ID,
			IDialogConstants.CANCEL_LABEL,
			false);
		
		okButton.setEnabled(enableOK);
		
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.widthHint = 400;
		composite.setLayoutData(data);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText(PHPUIMessages.NewPHPManualSiteDialog_name);
		
		name = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyComplete();
			}
		});
		
		label = new Label(composite, SWT.NONE);
		label.setText(PHPUIMessages.NewPHPManualSiteDialog_url);
		
		url = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		url.setLayoutData(data);
		url.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyComplete();
			}
		});
		
		browseButton = new Button(composite, SWT.BORDER);
        data = new GridData();
        data.horizontalAlignment = SWT.END;
        data.widthHint = 50;
        browseButton.setLayoutData(data);
        browseButton.setText("..."); //$NON-NLS-1$
        browseButton.setAlignment(SWT.CENTER);
        browseButton.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event event) {
                        choosePHPManualFolder();
                }
        });
		
		label = new Label(composite, SWT.NONE);
		label.setText(PHPUIMessages.NewPHPManualSiteDialog_fileExtension);
		
		extension = new Text(composite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		extension.setLayoutData(data);
		extension.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyComplete();
			}
		});
		
		initializeFields();
		Dialog.applyDialogFont(composite);
		return composite;
	}
	
	protected void initializeFields() {
		if (edited != null) {
			name.setText(edited.getLabel());
			url.setText(edited.getUrl());
			extension.setText(edited.getExtension());
		} else {
			url.setText("http://"); //$NON-NLS-1$
			extension.setText("php"); //$NON-NLS-1$
		}
	}
	
	private void verifyComplete() {
		if (okButton == null) {
			return;		
		}
		
		if (name.getText().trim().length() == 0 || url.getText().trim().length() == 0) {
			okButton.setEnabled(false);
			this.updateStatus(new Status(IStatus.ERROR, PHPUiPlugin.getPluginId(), IStatus.OK, PHPUIMessages.NewPHPManualSiteDialog_siteOrUrlNotSpecified, null)); 
			return;
		}
	
		try {
			new URL(URLDecoder.decode(url.getText().trim(), "UTF-8")); //$NON-NLS-1$
		} catch (Exception e) {
			okButton.setEnabled(false);
			this.updateStatus(new Status(IStatus.ERROR, PHPUiPlugin.getPluginId(), IStatus.OK, PHPUIMessages.NewPHPManualSiteDialog_incorrectUrl, null)); 
			return;
		}
		
		if (isDuplicate()) {
			return;
		} else {
			okButton.setEnabled(true);
			this.updateStatus( new Status(IStatus.OK, PHPUiPlugin.getPluginId(), IStatus.OK, "", null));  //$NON-NLS-1$
		}
		result = new PHPManualConfig (name.getText(), url.getText(), extension.getText(), false);
	}
	
	private boolean isDuplicate() {
		if (configs == null) {
			return false;
		}
		for( int i = 0; i < configs.size(); i++) {
			if (!isCurrentlyEditedSiteBookmark(i)) {
				PHPManualConfig config = (PHPManualConfig)configs.get(i);
				if (config.getLabel().equals(name.getText().trim())) {
					okButton.setEnabled(false);
					this.updateStatus( new Status(IStatus.ERROR, PHPUiPlugin.getPluginId(), IStatus.OK, PHPUIMessages.NewPHPManualSiteDialog_nameAlreadyInUse, null)); 
					return true;
				} else if (config.getUrl().toString().trim().equals(url.getText().trim())) {
					okButton.setEnabled(false);
					this.updateStatus( new Status(IStatus.ERROR, PHPUiPlugin.getPluginId(), IStatus.OK, NLS.bind(PHPUIMessages.NewPHPManualSiteDialog_urlAlreadyInUse, config.getLabel()), null)); 
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean isCurrentlyEditedSiteBookmark (int index) {
		return false;
	}
	
	protected void updateButtonsEnableState(IStatus status) {
		if (okButton != null && !okButton.isDisposed() && name.getText().trim().length() != 0)
			okButton.setEnabled(!status.matches(IStatus.ERROR));
	}
	
	private void choosePHPManualFolder() {
        DirectoryDialog dialog = new DirectoryDialog(PHPUiPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
        dialog.setText(PHPUIMessages.NewPHPManualSiteDialog_chooseDir);
        String directoryAsString = dialog.open();
        if (directoryAsString == null) {
                return;
        }
        url.setText("file://" + directoryAsString); //$NON-NLS-1$
	}
}
