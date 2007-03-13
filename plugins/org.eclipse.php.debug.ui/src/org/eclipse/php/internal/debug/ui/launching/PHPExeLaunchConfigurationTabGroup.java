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
package org.eclipse.php.internal.debug.ui.launching;

import java.util.ArrayList;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

/**
 * A PHP executable launch configuration tab group that loads all of its tabs
 * from the launch configuration tabs which extends
 * org.eclipse.php.debug.ui.launchConfigurationTabs.
 * 
 * @author shalom
 * 
 */
public class PHPExeLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	protected final String CONFIGURATION_TAB_GROUP_ID = "org.eclipse.php.deubg.ui.launching.launchConfigurationTabGroup.phpexe";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog,
	 *      java.lang.String)
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		AbstractLaunchConfigurationTab[] tabs = LaunchConfigurationsTabsRegistry.getLaunchTabs(CONFIGURATION_TAB_GROUP_ID, mode);
		ArrayList list = new ArrayList();
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
		CommonTab newTab = new CommonTab();
		newTab.setLaunchConfigurationDialog(dialog);
		list.add(newTab);
		ILaunchConfigurationTab[] allTabs = (ILaunchConfigurationTab[]) list.toArray(new ILaunchConfigurationTab[list.size()]);
		setTabs(allTabs);
	}

}
