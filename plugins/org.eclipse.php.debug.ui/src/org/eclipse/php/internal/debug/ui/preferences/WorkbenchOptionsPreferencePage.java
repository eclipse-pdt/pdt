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
package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class WorkbenchOptionsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private WorkbenchOptionsBlock workbenchOptionsBlock;

	public WorkbenchOptionsPreferencePage() {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		comp.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		comp.setLayoutData(gd);

		workbenchOptionsBlock = new WorkbenchOptionsBlock();
		workbenchOptionsBlock.setCompositeAddon(comp);
		workbenchOptionsBlock.initializeValues(this);

		// HELP
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.WORKBENCH_OPTIONS);
		return comp;
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void performApply() {
		workbenchOptionsBlock.performApply(false);
	}

	@Override
	public boolean performOk() {
		return workbenchOptionsBlock.performOK(false);
	}

	@Override
	public boolean performCancel() {
		return workbenchOptionsBlock.performCancel();
	}

	@Override
	public void performDefaults() {
		workbenchOptionsBlock.performDefaults();
	}
}
