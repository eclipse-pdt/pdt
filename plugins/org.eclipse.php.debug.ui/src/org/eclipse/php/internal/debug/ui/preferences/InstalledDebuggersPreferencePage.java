package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

public class InstalledDebuggersPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private PHPDebuggersTable table;

	public InstalledDebuggersPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
	}

	protected Control createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout());
		comp.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		comp.setLayoutData(gd);

		// Add the debuggers table
		createDebuggersTable(comp);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.INSTALLED_DEBUGGERS);
		return comp;
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

	public void init(IWorkbench workbench) {
	}

	/**
	 * Overrides the super preformDefaults to make sure that the debuggers table also gets updated
	 * to its default values.
	 */
	public void performDefaults() {
		table.performDefaults();
		super.performDefaults();
	}
}
