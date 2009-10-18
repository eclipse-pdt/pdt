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

public class WorkbenchOptionsPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private WorkbenchOptionsBlock workbenchOptionsBlock;

	public WorkbenchOptionsPreferencePage() {
	}

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
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.WORKBENCH_OPTIONS);
		return comp;
	}

	public void init(IWorkbench workbench) {
	}

	protected void performApply() {
		workbenchOptionsBlock.performApply(false);
	}

	public boolean performOk() {
		return workbenchOptionsBlock.performOK(false);
	}

	public boolean performCancel() {
		return workbenchOptionsBlock.performCancel();
	}

	public void performDefaults() {
		workbenchOptionsBlock.performDefaults();
	}
}
