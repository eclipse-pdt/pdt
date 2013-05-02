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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.preferences.AbstractPreferencePage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

/**
 * The Installed PHPs preference page.
 * 
 * @since 3.0
 */
public class PHPsPreferencePage extends AbstractPreferencePage implements
		IWorkbenchPreferencePage {

	public static String ID = "org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage"; //$NON-NLS-1$
	// PHP Block
	private InstalledPHPsBlock fPHPBlock;

	public PHPsPreferencePage() {
		super();

		// only used when page is shown programatically
		setTitle(PHPDebugUIMessages.PHPsPreferencePage_1);

		setDescription(PHPDebugUIMessages.PHPsPreferencePage_2);
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
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createContents(Composite ancestor) {
		initializeDialogUnits(ancestor);

		noDefaultAndApplyButton();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		ancestor.setLayout(layout);

		fPHPBlock = new InstalledPHPsBlock();
		fPHPBlock.createControl(ancestor);
		Control control = fPHPBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		fPHPBlock.restoreColumnSettings(PHPDebugUIPlugin.getDefault()
				.getDialogSettings(),
				PHPDebugCorePreferenceNames.DIALOG_COLUMN_WIDTH);

		initDefaultPHP();
		// fPHPBlock.addSelectionChangedListener(new ISelectionChangedListener()
		// {
		// public void selectionChanged(SelectionChangedEvent event) {
		// PHPexeItem phpexe = getCurrentDefaultPHP();
		// if (phpexe == null) {
		// setValid(false);
		//					setErrorMessage(PHPDebugUIMessages.PHPsPreferencePage_13); 
		// } else {
		// setValid(true);
		// setErrorMessage(null);
		// }
		// }
		// });
		applyDialogFont(ancestor);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(ancestor,
				IPHPHelpContextIds.PHP_EXECUTABLES_PREFERENCES);
		return ancestor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {

		// save column widths
		IDialogSettings settings = PHPDebugUIPlugin.getDefault()
				.getDialogSettings();
		fPHPBlock.saveColumnSettings(settings,
				PHPDebugCorePreferenceNames.DIALOG_COLUMN_WIDTH);

		fPHPBlock.commitChanges();
		return super.performOk();
	}

	private void verifyDefaultPHP(PHPexeItem php) {
		if (php != null) {
			boolean exist = php.getExecutable().exists();
			// If all library locations exist, check the corresponding entry in
			// the list,
			// otherwise remove the PHP setting
			if (!exist) {
				fPHPBlock.removePHPs(new PHPexeItem[] { php });
				ErrorDialog
						.openError(
								getControl().getShell(),
								PHPDebugUIMessages.PHPsPreferencePage_1,
								PHPDebugUIMessages.PHPsPreferencePage_10,
								new Status(
										IStatus.ERROR,
										PHPDebugUIPlugin.ID,
										PHPDebugUIPlugin.INTERNAL_ERROR,
										PHPDebugUIMessages.PHPsPreferencePage_11,
										null));
				return;
			}
		}
	}

	private void initDefaultPHP() {
		PHPexeItem realDefault = PHPexes.getInstance().getDefaultItem(
				PHPDebugPlugin.getCurrentDebuggerId());
		if (realDefault != null) {
			PHPexeItem[] phps = fPHPBlock.getPHPs();
			for (PHPexeItem fakePHP : phps) {
				if (fakePHP.equals(realDefault)) {
					verifyDefaultPHP(fakePHP);
					break;
				}
			}
		}
	}
}
