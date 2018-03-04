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
package org.eclipse.php.profile.ui.launcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.php.internal.debug.ui.launching.PHPExecutableLaunchTab;

public class ProfilePHPExecutableTabGroup extends AbstractLaunchConfigurationTabGroup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.
	 * debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		List<AbstractLaunchConfigurationTab> tabs = new ArrayList<>();
		tabs.add(new PHPExecutableLaunchTab());
		tabs.add(new PHPExeLaunchConfigurationProfilerTab());
		tabs.add(new CommonTab());
		for (AbstractLaunchConfigurationTab tab : tabs) {
			tab.setLaunchConfigurationDialog(dialog);
		}
		AbstractLaunchConfigurationTab[] array = new AbstractLaunchConfigurationTab[tabs.size()];
		tabs.toArray(array);
		setTabs(array);
	}

}
