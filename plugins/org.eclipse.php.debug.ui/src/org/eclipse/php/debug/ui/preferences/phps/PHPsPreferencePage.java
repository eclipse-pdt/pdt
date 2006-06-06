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
package org.eclipse.php.debug.ui.preferences.phps;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.php.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.debug.core.preferences.PHPexeItem;
import org.eclipse.php.debug.core.preferences.PHPexes;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.ui.preferences.ui.AbstractPreferencePage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The Installed PHPs preference page.
 * 
 * @since 3.0
 */
public class PHPsPreferencePage extends AbstractPreferencePage implements IWorkbenchPreferencePage {

	public static String ID="org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage";
	// PHP Block
	private InstalledPHPsBlock fPHPBlock;

	PHPexes phpExes;

	public PHPsPreferencePage() {
		super();

		// only used when page is shown programatically
		setTitle(PHPDebugUIMessages.PHPsPreferencePage_1); //$NON-NLS-1$

		setDescription(PHPDebugUIMessages.PHPsPreferencePage_2); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	protected Preferences getModelPreferences() {
		return PHPDebugUIPlugin.getDefault().getPluginPreferences();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite ancestor) {
		initializeDialogUnits(ancestor);

		noDefaultAndApplyButton();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		ancestor.setLayout(layout);

		phpExes = new PHPexes();
		phpExes.load(getModelPreferences());
		fPHPBlock = new InstalledPHPsBlock(phpExes);
		fPHPBlock.createControl(ancestor);
		Control control = fPHPBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		fPHPBlock.restoreColumnSettings(PHPDebugUIPlugin.getDefault().getDialogSettings(), PHPDebugCorePreferenceNames.DIALOG_COLUMN_WIDTH);

		initDefaultPHP();
		//		fPHPBlock.addSelectionChangedListener(new ISelectionChangedListener() {
		//			public void selectionChanged(SelectionChangedEvent event) {
		//				PHPexeItem phpexe = getCurrentDefaultPHP();
		//				if (phpexe == null) {
		//					setValid(false);
		//					setErrorMessage(PHPDebugUIMessages.PHPsPreferencePage_13); //$NON-NLS-1$
		//				} else {
		//					setValid(true);
		//					setErrorMessage(null);
		//				}
		//			}
		//		});
		applyDialogFont(ancestor);
		return ancestor;
	}

	private PHPexeItem getCurrentDefaultPHP() {
		return fPHPBlock.getCheckedPHP();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {

		// save column widths
		IDialogSettings settings = PHPDebugUIPlugin.getDefault().getDialogSettings();
		fPHPBlock.saveColumnSettings(settings, PHPDebugCorePreferenceNames.DIALOG_COLUMN_WIDTH);

		return super.performOk();
	}

	protected void storeValues() {

		phpExes.setDefaultItem(fPHPBlock.getCheckedPHP());
		Preferences prefs = getModelPreferences();
		phpExes.store(prefs);
	}

	private void verifyDefaultPHP(PHPexeItem php) {
		if (php != null) {

			boolean exist = php.getLocation().exists();

			// If all library locations exist, check the corresponding entry in the list,
			// otherwise remove the VM
			if (exist) {
				fPHPBlock.setCheckedPHP(php);
			} else {
				fPHPBlock.removePHPs(new PHPexeItem[] { php });
				PHPexeItem def = phpExes.getDefaultItem();
				if (def == null) {
					fPHPBlock.setCheckedPHP(null);
				} else {
					fPHPBlock.setCheckedPHP(def);
				}
				ErrorDialog.openError(getControl().getShell(), PHPDebugUIMessages.PHPsPreferencePage_1, PHPDebugUIMessages.PHPsPreferencePage_10, new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, PHPDebugUIPlugin.INTERNAL_ERROR, PHPDebugUIMessages.PHPsPreferencePage_11, null)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				return;
			}
		} else {
			fPHPBlock.setCheckedPHP(null);
		}
	}

	private void initDefaultPHP() {
		PHPexeItem realDefault = phpExes.getDefaultItem();
		if (realDefault != null) {
			PHPexeItem[] phps = fPHPBlock.getPHPs();
			for (int i = 0; i < phps.length; i++) {
				PHPexeItem fakePHP = phps[i];
				if (fakePHP.equals(realDefault)) {
					verifyDefaultPHP(fakePHP);
					break;
				}
			}
		}
	}
}
