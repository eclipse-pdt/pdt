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
package org.eclipse.php.ui.preferences.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.PreferencesUtil;


public class PHPEditorPreferencePage extends AbstractPreferencePage {

	private Button useSmartHomeEndCB;
	
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		createHeader(parent);
		createMainComposite(parent);
		initValues();
		return super.createContents(parent);
	}

	private void createMainComposite(Composite parent) {
		Composite firstComposite = new Composite(parent,SWT.FILL);
		firstComposite.setLayout(new GridLayout(1,false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		firstComposite.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		useSmartHomeEndCB = new Button(firstComposite,SWT.CHECK|SWT.LEFT);
		useSmartHomeEndCB.setLayoutData(gd);
		useSmartHomeEndCB.setText(PHPUIMessages.PHPEditorPreferencePage_smartCaretPositioning);
	}

	protected void performDefaults() {
		useSmartHomeEndCB.setSelection(true);
		super.performDefaults();
	}

	public boolean performOk() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.USE_SMART_HOME_END, useSmartHomeEndCB.getSelection());
		return super.performOk();
	}
	
	private void createHeader(Composite contents) {
		final Shell shell= contents.getShell();
		String text= PHPUIMessages.PHPEditorPreferencePage_prefEditorMessage;
		Link link= new Link(contents, SWT.NONE);
		link.setText(text);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(shell, "org.eclipse.ui.preferencePages.GeneralTextEditor", null, null); //$NON-NLS-1$
			}
		});
		// TODO replace by link-specific tooltips when
		// bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=88866 gets fixed
		link.setToolTipText(PHPUIMessages.PHPEditorPreferencePage_prefEditorTooltip);
		
		GridData gridData= new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.widthHint= 150; // only expand further if anyone else requires it
		link.setLayoutData(gridData);
	}

	protected void initValues() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		useSmartHomeEndCB.setSelection(store.getBoolean(PreferenceConstants.USE_SMART_HOME_END));
		super.initializeValues();
	}
}
