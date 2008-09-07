/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.dltk.ui.PreferenceConstants;

public class PHPAppearancePreferencePage extends AbstractPreferencePage {

	private Button showReturnTypeCB;

	public PHPAppearancePreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		setDescription(PHPUIMessages.getString("PHPAppearancePreferencePage_appearanceHeader")); //$NON-NLS-1$
	}

	protected void initializeValues() {
		super.initializeValues();
		IPreferenceStore store = DLTKUIPlugin.getDefault().getPreferenceStore();
		showReturnTypeCB.setSelection(store.getBoolean(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE));
	}

	private void createMainBlock(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gd);
		GridLayout gridLayout = new GridLayout(1, false);
		comp.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		showReturnTypeCB = new Button(comp, SWT.CHECK);
		showReturnTypeCB.setText(PHPUIMessages.getString("PHPAppearancePreferencePage_showMehodsReturnType")); //$NON-NLS-1$
		showReturnTypeCB.setLayoutData(gd);
	}

	protected Control createContents(Composite parent) {
		createMainBlock(parent);
		initializeValues();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.APPEARANCE_PREFERENCES);
		return null;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	protected Control createPreferenceContent(Composite composite) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getPreferencePageID() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getPropertyPageID() {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean hasProjectSpecificOptions(IProject project) {
		// TODO Auto-generated method stub
		return false;
	}

	protected void performDefaults() {
		IPreferenceStore store = DLTKUIPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE, false);
		super.performDefaults();
	}

	public boolean performOk() {
		IPreferenceStore store = DLTKUIPlugin.getDefault().getPreferenceStore();
		store.setValue(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE, showReturnTypeCB.getSelection());
		return super.performOk();
	}

}
