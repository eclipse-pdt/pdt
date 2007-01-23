package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.util.preferences.Key;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public abstract class PHPCoreOptionsConfigurationBlock extends OptionsConfigurationBlock {

	public PHPCoreOptionsConfigurationBlock(IStatusChangeListener context, IProject project, Key[] allKeys, IWorkbenchPreferenceContainer container) {
		super(context, project, allKeys, container);
	}

	protected abstract Control createContents(Composite parent);

	protected abstract void validateSettings(Key changedKey, String oldValue, String newValue);

	protected abstract String[] getFullBuildDialogStrings(boolean workspaceSettings);
		
	
	protected final static Key getPHPCoreKey(String key) {
		return getKey(PHPCorePlugin.ID, key);
	}

	

}
