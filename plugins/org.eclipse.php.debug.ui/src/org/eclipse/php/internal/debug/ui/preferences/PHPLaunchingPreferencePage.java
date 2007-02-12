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
package org.eclipse.php.internal.debug.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * A preference page for configuring PHP launching preferences.
 */
public class PHPLaunchingPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * a list of the field editors
	 * @since 3.2
	 */
	private List fFieldEditors;

	/**
	 * The default contsructor
	 */
	public PHPLaunchingPreferencePage() {
		super();
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		// TODO - Attach the Help
		//		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IDebugHelpContextIds.LAUNCHING_PREFERENCE_PAGE);
	}

	/**
	 * creates a composite to place tab controls on
	 * @param parent the parent to create to composite for
	 * @return a composite for settgin as a tabitem control
	 * @since 3.2
	 */
	private Composite createComposite(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		comp.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		comp.setLayoutData(gd);
		return comp;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		fFieldEditors = new ArrayList();
		Composite comp = createComposite(parent);
		//save dirty editors
		FieldEditor edit = new RadioGroupFieldEditor(PreferenceConstants.ALLOW_MULTIPLE_LAUNCHES, PHPDebugUIMessages.PHPLaunchingPreferencePage_message, 3, new String[][] { { PHPDebugUIMessages.PHPLaunchingPreferencePage_Always, MessageDialogWithToggle.ALWAYS }, { PHPDebugUIMessages.PHPLaunchingPreferencePage_Never, MessageDialogWithToggle.NEVER }, { PHPDebugUIMessages.PHPLaunchingPreferencePage_Prompt, MessageDialogWithToggle.PROMPT } }, comp, true);
		fFieldEditors.add(edit);

		//init the field editors
		initFieldEditors();
		return comp;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * Initializes the field editors to their values
	 * @since 3.2
	 */
	private void initFieldEditors() {
		FieldEditor editor;
		for (int i = 0; i < fFieldEditors.size(); i++) {
			editor = (FieldEditor) fFieldEditors.get(i);
			editor.setPreferenceStore(getPreferenceStore());
			editor.load();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		for (int i = 0; i < fFieldEditors.size(); i++) {
			((FieldEditor) fFieldEditors.get(i)).loadDefault();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		for (int i = 0; i < fFieldEditors.size(); i++) {
			((FieldEditor) fFieldEditors.get(i)).store();
		}
		return super.performOk();
	}
}
