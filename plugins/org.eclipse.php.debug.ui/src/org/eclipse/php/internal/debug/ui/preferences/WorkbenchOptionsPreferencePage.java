package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class WorkbenchOptionsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

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
