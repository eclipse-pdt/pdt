/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [469503]
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ui.PreferenceConstants;
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

public class PHPAppearancePreferencePage extends AbstractPreferencePage {

	private Button showReturnTypeCB;
	private Button showFieldTypeCB;

	public PHPAppearancePreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		setDescription(PHPUIMessages.PHPAppearancePreferencePage_appearanceHeader);
	}

	private void createMainBlock(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gd);
		GridLayout gridLayout = new GridLayout(1, false);
		comp.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		showReturnTypeCB = new Button(comp, SWT.CHECK);
		showReturnTypeCB.setText(PHPUIMessages.PHPAppearancePreferencePage_showMehodsReturnType);
		showReturnTypeCB.setData(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE);
		showReturnTypeCB.setLayoutData(gd);
		add(showReturnTypeCB);

		showFieldTypeCB = new Button(comp, SWT.CHECK);
		showFieldTypeCB.setText(PHPUIMessages.PHPAppearancePreferencePage_showMethodParameterType);
		showFieldTypeCB.setData(PreferenceConstants.APPEARANCE_METHOD_PARAMETER_TYPES);
		showFieldTypeCB.setLayoutData(gd);
		add(showFieldTypeCB);
	}

	@Override
	protected Control createContents(Composite parent) {
		createMainBlock(parent);
		initializeValues();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.APPEARANCE_PREFERENCES);
		return null;
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	protected Control createPreferenceContent(Composite composite) {
		return null;
	}

	protected String getPreferencePageID() {
		return null;
	}

	protected String getPropertyPageID() {
		return null;
	}

	protected boolean hasProjectSpecificOptions(IProject project) {
		return false;
	}

}
