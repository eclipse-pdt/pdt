/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.phps;

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.AbstractPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class PHPInterpreterExecutionPreferencePage extends AbstractPreferencePage implements IWorkbenchPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.debug.ui.preferencesphps.PHPExecutionPreferencePreferencePage"; //$NON-NLS-1$

	private PHPInterpreterExecutionConfigurationBlock fConfigurationBlock;

	public PHPInterpreterExecutionPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(Messages.PHPInterpreterExecutionPreferencePage_0);
		setDescription(Messages.PHPInterpreterExecutionPreferencePage_1);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 *      widgets .Composite)
	 */
	@Override
	protected Control createContents(Composite ancestor) {
		initializeDialogUnits(ancestor);
		noDefaultAndApplyButton();
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite container = new Composite(ancestor, SWT.NONE);
		container.setLayout(layout);

		fConfigurationBlock = new PHPInterpreterExecutionConfigurationBlock();
		fConfigurationBlock.createControl(container);
		Control control = fConfigurationBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		applyDialogFont(ancestor);
		return ancestor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.PHPEXE_ENV_PREFERENCES);
	}

	/**
	 * @see org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 *      getPreferencePageID()
	 */
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

}
