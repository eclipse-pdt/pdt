package org.eclipse.php.server.ui;

import java.util.ArrayList;

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
		AbstractLaunchConfigurationTab[] tabs = ServerLaunchTabsRegistry.getLaunchTabs();
		ArrayList list = new ArrayList();
		if (tabs != null) {
			for (int i = 0; i < tabs.length; i++) {
				list.add(tabs[i]);
				tabs[i].setLaunchConfigurationDialog(dialog);
			}
		}
		if (list.isEmpty()) {
			ServerTab aTab = new ServerTab();
			aTab.setLaunchConfigurationDialog(dialog);
			list.add(aTab);
		}

		CommonTab newTab = new CommonTab();
		newTab.setLaunchConfigurationDialog(dialog);
		list.add(newTab);

		ILaunchConfigurationTab[] allTabs = (ILaunchConfigurationTab[]) list.toArray(new ILaunchConfigurationTab[list.size()]);
		setTabs(allTabs);
	}
}
