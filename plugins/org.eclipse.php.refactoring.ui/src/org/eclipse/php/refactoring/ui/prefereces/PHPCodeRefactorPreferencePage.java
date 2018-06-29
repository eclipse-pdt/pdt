/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.prefereces;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class PHPCodeRefactorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public static String ID = "org.eclipse.php.refactoring.ui.preferencePage"; //$NON-NLS-1$
	private Button renameBtn;

	public PHPCodeRefactorPreferencePage() {
		super();
		setPreferenceStore(RefactoringUIPlugin.getDefault().getPreferenceStore());
		setDescription(Messages.PHPCodeRefactorPreferencePage_1);
	}

	@Override
	protected Control createContents(Composite parent) {

		initializeDialogUnits(parent);

		Composite result = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = 0;
		layout.verticalSpacing = convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		result.setLayout(layout);

		Group refactoringGroup = new Group(result, SWT.NONE);
		refactoringGroup.setLayout(new GridLayout());
		refactoringGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		refactoringGroup.setText(Messages.PHPCodeRefactorPreferencePage_2);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		renameBtn = new Button(refactoringGroup, SWT.CHECK);
		renameBtn.setText(Messages.PHPCodeRefactorPreferencePage_3);
		renameBtn.setLayoutData(gd);

		renameBtn.setSelection(getPreferenceStore().getBoolean(PreferenceConstants.REFACTOR_LIGHTWEIGHT));

		Dialog.applyDialogFont(result);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.CODE_REFACTOR_PREFERENCES);
		return result;
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		IPreferenceStore store = getPreferenceStore();
		renameBtn.setSelection(store.getDefaultBoolean(PreferenceConstants.REFACTOR_LIGHTWEIGHT));
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(PreferenceConstants.REFACTOR_LIGHTWEIGHT, renameBtn.getSelection());
		return super.performOk();
	}

}
