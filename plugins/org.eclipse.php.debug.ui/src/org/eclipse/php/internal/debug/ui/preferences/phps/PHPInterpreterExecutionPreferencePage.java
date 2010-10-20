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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
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

public class PHPInterpreterExecutionPreferencePage extends
		AbstractPreferencePage implements IWorkbenchPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.debug.ui.preferencesphps.PHPExecutionPreferencePreferencePage"; //$NON-NLS-1$

	private PHPInterpreterExecutionConfigurationBlock fConfigurationBlock;

	public PHPInterpreterExecutionPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(Messages.PHPInterpreterExecutionPreferencePage_0);
		setDescription(Messages.PHPInterpreterExecutionPreferencePage_1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	protected Preferences getModelPreferences() {
		return PHPProjectPreferences.getModelPreferences();
	}

	/*
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
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
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.PHPEXE_ENV_PREFERENCES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPreferencePageID()
	 */
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

}
