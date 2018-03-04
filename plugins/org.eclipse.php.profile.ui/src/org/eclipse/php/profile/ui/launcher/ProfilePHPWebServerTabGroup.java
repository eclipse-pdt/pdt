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

import org.eclipse.debug.ui.*;
import org.eclipse.php.internal.server.ui.launching.PHPWebPageLaunchConfigurationTab;

/**
 * PHP web server launch configuration profiler tab group.
 */
public class ProfilePHPWebServerTabGroup extends AbstractLaunchConfigurationTabGroup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.
	 * debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ArrayList<ILaunchConfigurationTab> tabs = new ArrayList<>(10);
		tabs.add(new PHPWebPageLaunchConfigurationTab());
		tabs.add(new PHPWebPageLaunchConfigurationProfilerTab());
		tabs.add(new CommonTab());
		AbstractLaunchConfigurationTab[] array = new AbstractLaunchConfigurationTab[tabs.size()];
		tabs.toArray(array);
		setTabs(array);
	}

}
