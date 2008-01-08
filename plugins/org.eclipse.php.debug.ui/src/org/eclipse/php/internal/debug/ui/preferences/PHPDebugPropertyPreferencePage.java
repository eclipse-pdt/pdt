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

import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.preferences.AbstractPHPPropertyPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

/**
 * The main PHP | Debug preferences page.
 *
 * @author Shalom Gibly
 */
public class PHPDebugPropertyPreferencePage extends AbstractPHPPropertyPreferencePage {

	private PHPDebugPreferencesBlock debugPreferencesBlock;
	protected Label fDefaultURLLabel;
	protected Text fDefaultURLTextBox;

	public PHPDebugPropertyPreferencePage() {
		super();
	}

	protected String getPreferenceNodeQualifier() {
		return PHPProjectPreferences.getPreferenceNodeQualifier();
	}

	protected String getPreferencePageID() {
		return IPHPDebugConstants.PREFERENCE_PAGE_ID;
	}

	protected String getProjectSettingsKey() {
		return PHPProjectPreferences.getProjectSettingsKey();
	}

	protected String getPropertyPageID() {
		return IPHPDebugConstants.PROJECT_PAGE_ID;
	}

	public void init(IWorkbench workbench) {
	}

	public String getTitle() {
		return PHPDebugUIMessages.PhpDebugPreferencePage_8;
	}

	protected Control createCommonContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		comp.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		comp.setLayoutData(gd);

		debugPreferencesBlock = new PHPDebugPreferencesBlock();
		debugPreferencesBlock.setCompositeAddon(comp);
		debugPreferencesBlock.initializeValues(this);

		return comp;
	}

	protected Control createProjectContents(Composite parent) {
		return createCommonContents(parent);
	}
}
