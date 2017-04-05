/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.*;
import org.eclipse.php.internal.debug.ui.launching.PHPExeLaunchConfigurationDebuggerTab;
import org.eclipse.php.internal.debug.ui.launching.PHPExecutableLaunchTab;

public class PHPUnitLaunchConfigurationGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(final ILaunchConfigurationDialog dialog, final String mode) {
		final List<AbstractLaunchConfigurationTab> tabs = new ArrayList<AbstractLaunchConfigurationTab>();
		tabs.add(new PHPUnitLaunchConfigurationTab());
		final PHPExecutableLaunchTab phpTab = new PHPExecutableLaunchTab();
		phpTab.setEnableFileSelection(false);
		tabs.add(phpTab);
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			tabs.add(new PHPExeLaunchConfigurationDebuggerTab());
		}
		// Add environment and common tabs to the tabs group.
		tabs.add(new EnvironmentTab());
		tabs.add(new CommonTab());
		for (AbstractLaunchConfigurationTab tab : tabs) {
			tab.setLaunchConfigurationDialog(dialog);
		}
		final AbstractLaunchConfigurationTab[] array = new AbstractLaunchConfigurationTab[tabs.size()];
		tabs.toArray(array);
		setTabs(array);
	}

}
