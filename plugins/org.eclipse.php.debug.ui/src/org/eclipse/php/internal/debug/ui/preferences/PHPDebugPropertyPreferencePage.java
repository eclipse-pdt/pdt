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

import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.AbstractPHPPropertyPreferencePage;
import org.eclipse.php.internal.ui.preferences.PHPPreferencePageBlocksRegistry;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.swt.SWT;
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

	protected Label fDefaultURLLabel;
	protected Text fDefaultURLTextBox;

	private PHPDebuggersTable table;

	public PHPDebugPropertyPreferencePage() {
		super();

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
	
	/**
	 * Override the default creation on the workspace content to add a fixed debuggers table that will display all the
	 * supported debuggers and will allow their preferences modification.
	 */
	protected Control createWorkspaceContents(Composite composite) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(composite, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite group = new Composite(scrolledCompositeImpl, SWT.NONE);
		group.setLayout(new GridLayout());
		// Add the debuggers table
		createDebuggersTable(group);
		try {
			workspaceAddons = PHPPreferencePageBlocksRegistry.getPHPPreferencePageBlock(getPreferencePageID());
			for (int i = 0; i < workspaceAddons.length; i++) {
				workspaceAddons[i].setCompositeAddon(group);
				workspaceAddons[i].initializeValues(this);
			}
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		scrolledCompositeImpl.setContent(group);
		return scrolledCompositeImpl;
	}

	/**
	 * Overrides the super preformDefaults to make sure that the debuggers table also gets updated
	 * to its default values.
	 */
	public void performDefaults() {
		table.performDefaults();
		super.performDefaults();
	}
	
	/**
	 * Creates the debuggers table.
	 * The created table allows only viewing and modifying any existing debugger that is 
	 * registered thought the phpDebuggers extension point.
	 * 
	 * @param composite
	 */
	protected void createDebuggersTable(Composite composite) {
		table = new PHPDebuggersTable();
		table.createControl(composite);
	}
}
