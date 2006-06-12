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
package org.eclipse.php.debug.ui.preferences;

import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.ui.preferences.ui.ScrolledCompositeImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

public class PHPDebugPropertyPreferencePage extends PropertyPreferencePage {

	private static final String PAGE_ID = "org.eclipse.php.debug.ui.preferences.PhpDebugPreferencePage";

	protected Label fDefaultURLLabel;
	protected Text fDefaultURLTextBox;
	private IPHPDebugPreferencesPageAddon[] projectScopeAddons;
	private IPHPDebugPreferencesPageAddon[] workspaceAddons;

	public PHPDebugPropertyPreferencePage() {
		super();

	}

	protected Control createWorkspaceContents(Composite composite) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(composite, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite group = new Composite(scrolledCompositeImpl, SWT.NONE);
		group.setLayout(new GridLayout());
		try {
			workspaceAddons = PHPDebugPreferencesAddonRegistry.getDebugPreferencesWorkspaceAddon(PAGE_ID);
			for (int i = 0; i < workspaceAddons.length; i++) {
				workspaceAddons[i].setCompositeAddon(group);
				workspaceAddons[i].initializeValues(this);
			}
			scrolledCompositeImpl.setContent(group);
		} catch (Exception e) {
			Logger.logException(e);
		}
		return scrolledCompositeImpl;
	}

	protected Control createProjectContents(Composite composite) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(composite, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite group = new Composite(scrolledCompositeImpl, SWT.NONE);
		group.setLayout(new GridLayout());
		try {
			projectScopeAddons = PHPDebugPreferencesAddonRegistry.getDebugPreferencesProjectAddon(PAGE_ID);
			for (int i = 0; i < projectScopeAddons.length; i++) {
				projectScopeAddons[i].setCompositeAddon(group);
				projectScopeAddons[i].initializeValues(this);
			}
			scrolledCompositeImpl.setContent(group);
		} catch (Exception e) {
			Logger.logException(e);
		}
		return scrolledCompositeImpl;
	}

	protected String getPreferenceNodeQualifier() {
		return PHPProjectPreferences.getPreferenceNodeQualifier();
	}

	protected String getPreferencePageID() {
		return IPHPConstants.PREFERENCE_PAGE_ID;
	}

	protected String getProjectSettingsKey() {
		return PHPProjectPreferences.getProjectSettingsKey();
	}

	protected String getPropertyPageID() {
		return IPHPConstants.PROJECT_PAGE_ID;
	}

	public void init(IWorkbench workbench) {
	}

	public String getTitle() {
		return PHPDebugUIMessages.PhpDebugPreferencePage_8;
	}

	public boolean performOk() {
		boolean result = super.performOk();
		for (int i = 0; i < projectScopeAddons.length; i++) {
			projectScopeAddons[i].performOK(isElementSettingsEnabled());
		}
		for (int i = 0; i < workspaceAddons.length; i++) {
			workspaceAddons[i].performOK(false);
		}
		return result;
	}

	public void performApply() {
		super.performApply(); // Will execute the preformOK()
	}

	public void performDefaults() {
		for (int i = 0; i < projectScopeAddons.length; i++) {
			projectScopeAddons[i].performDefaults();
		}
		for (int i = 0; i < workspaceAddons.length; i++) {
			workspaceAddons[i].performDefaults();
		}
		super.performDefaults();
	}
}
