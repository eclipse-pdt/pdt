package org.eclipse.php.server.ui;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.ui.*;

/**
 * A debug tab group for launching debug on server. 
 */
public class ServerLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	public ServerLaunchConfigurationTabGroup() {
		super();

	}

	/*
	 * @see ILaunchConfigurationTabGroup#createTabs(ILaunchConfigurationDialog, String)
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ArrayList list = new ArrayList();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, "serverTab"); //$NON-NLS-1$

		boolean found = false;
		if (elements != null && elements.length > 0) {
			int i = 0;
			while (i < elements.length) {
				final IConfigurationElement element = elements[i];
				if ("serverTab".equals(element.getName())) { //$NON-NLS-1$
					try {
						AbstractLaunchConfigurationTab newTab = (AbstractLaunchConfigurationTab) element.createExecutableExtension("class"); //$NON-NLS-1$
						newTab.setLaunchConfigurationDialog(dialog);
						list.add((ILaunchConfigurationTab) newTab);
						found = true;
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				i++;
			}
		}

		if (!found) {
			ServerTab aTab = new ServerTab();
			aTab.setLaunchConfigurationDialog(dialog);
			list.add((ILaunchConfigurationTab) aTab);
		}

		CommonTab newTab = new CommonTab();
		newTab.setLaunchConfigurationDialog(dialog);

		list.add((ILaunchConfigurationTab) newTab);
		ILaunchConfigurationTab[] tabs = (ILaunchConfigurationTab[]) list.toArray(new ILaunchConfigurationTab[list.size()]);

		setTabs(tabs);
	}
}
