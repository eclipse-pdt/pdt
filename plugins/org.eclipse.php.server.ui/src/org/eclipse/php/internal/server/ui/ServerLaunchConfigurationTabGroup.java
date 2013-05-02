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
package org.eclipse.php.internal.server.ui;

import java.util.ArrayList;

import org.eclipse.debug.ui.*;
import org.eclipse.php.internal.debug.ui.launching.LaunchConfigurationsTabsRegistry;

/**
 * A debug tab group for launching debug on server.
 */
public class ServerLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	protected final String CONFIGURATION_TAB_GROUP_ID = "org.eclipse.php.server.ui.launchConfigurationTabGroup"; //$NON-NLS-1$

	public ServerLaunchConfigurationTabGroup() {
		super();

	}

	/*
	 * @see ILaunchConfigurationTabGroup#createTabs(ILaunchConfigurationDialog,
	 * String)
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		AbstractLaunchConfigurationTab[] tabs = LaunchConfigurationsTabsRegistry
				.getLaunchTabs(CONFIGURATION_TAB_GROUP_ID, mode);
		ArrayList list = new ArrayList();
		if (tabs != null) {
			for (int i = 0; i < tabs.length; i++) {
				list.add(tabs[i]);
				tabs[i].setLaunchConfigurationDialog(dialog);
			}
		}
		if (list.isEmpty()) {
			ServerLaunchConfigurationTab aTab = new ServerLaunchConfigurationTab();
			aTab.setLaunchConfigurationDialog(dialog);
			list.add(aTab);
		}

		CommonTab newTab = new CommonTab();
		newTab.setLaunchConfigurationDialog(dialog);
		list.add(newTab);

		ILaunchConfigurationTab[] allTabs = (ILaunchConfigurationTab[]) list
				.toArray(new ILaunchConfigurationTab[list.size()]);
		setTabs(allTabs);
	}
}
