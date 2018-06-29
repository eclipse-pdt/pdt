/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.preferences;

import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

/**
 * "PHP Profile" preference page.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ProfilePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private RadioGroupFieldEditor fSwitchPerspectiveRadioGroup = null;

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(ProfilerUiPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public boolean performOk() {
		fSwitchPerspectiveRadioGroup.store();
		return super.performOk();
	}

	@Override
	public String getTitle() {
		return PHPProfileUIMessages.getString("ProfilePreferencePage_0"); //$NON-NLS-1$
	}

	@Override
	protected void performDefaults() {
		fSwitchPerspectiveRadioGroup.loadDefault();
		super.performDefaults();
	}

	@Override
	protected Control createContents(Composite parent) {
		SWTFactory.createWrapLabel(parent, "General PHP Profiling Settings.", 2, 300); //$NON-NLS-1$
		SWTFactory.createVerticalSpacer(parent, 1);
		fSwitchPerspectiveRadioGroup = new RadioGroupFieldEditor(
				PreferenceKeys.OPEN_PROFILE_PERSPECTIVE_ON_SESSION_DATA,
				"Open associated perspective on receiving session data", 3, //$NON-NLS-1$
				new String[][] { { "Al&ways", MessageDialogWithToggle.ALWAYS }, //$NON-NLS-1$
						{ "Ne&ver", MessageDialogWithToggle.NEVER }, { "Pr&ompt", MessageDialogWithToggle.PROMPT } }, //$NON-NLS-1$ //$NON-NLS-2$
				SWTFactory.createComposite(parent, 1, 2, GridData.FILL_BOTH), true);
		fSwitchPerspectiveRadioGroup.setPreferenceName(PreferenceKeys.OPEN_PROFILE_PERSPECTIVE_ON_SESSION_DATA);
		fSwitchPerspectiveRadioGroup.setPreferenceStore(getPreferenceStore());
		initializeControls();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.PROFILE_PREFERENCES);
		return parent;
	}

	protected void initializeControls() {
		fSwitchPerspectiveRadioGroup.load();
	}

}
