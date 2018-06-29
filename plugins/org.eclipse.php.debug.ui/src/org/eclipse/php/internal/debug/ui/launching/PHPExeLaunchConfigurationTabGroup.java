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
package org.eclipse.php.internal.debug.ui.launching;

import java.util.ArrayList;

import org.eclipse.debug.ui.*;

/**
 * A PHP executable launch configuration tab group that loads all of its tabs
 * from the launch configuration tabs which extends
 * org.eclipse.php.debug.ui.launchConfigurationTabs.
 * 
 * @author shalom
 * 
 */
public class PHPExeLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	protected final String CONFIGURATION_TAB_GROUP_ID = "org.eclipse.php.deubg.ui.launching.launchConfigurationTabGroup.phpexe"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse
	 * .debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		AbstractLaunchConfigurationTab[] tabs = LaunchConfigurationsTabsRegistry
				.getLaunchTabs(CONFIGURATION_TAB_GROUP_ID, mode);
		ArrayList<ILaunchConfigurationTab> list = new ArrayList<>();
		if (tabs != null) {
			for (int i = 0; i < tabs.length; i++) {
				list.add(tabs[i]);
				tabs[i].setLaunchConfigurationDialog(dialog);
			}
		}
		if (list.isEmpty()) {
			PHPExecutableLaunchTab aTab = new PHPExecutableLaunchTab();
			aTab.setLaunchConfigurationDialog(dialog);
			list.add(aTab);
		}

		// Add environment and common tabs to the tabs group.
		EnvironmentTab environmentTab = new EnvironmentTab();
		environmentTab.setLaunchConfigurationDialog(dialog);
		list.add(environmentTab);

		CommonTab newTab = new CommonTab();
		newTab.setLaunchConfigurationDialog(dialog);
		list.add(newTab);

		ILaunchConfigurationTab[] allTabs = list.toArray(new ILaunchConfigurationTab[list.size()]);
		setTabs(allTabs);
	}

}
