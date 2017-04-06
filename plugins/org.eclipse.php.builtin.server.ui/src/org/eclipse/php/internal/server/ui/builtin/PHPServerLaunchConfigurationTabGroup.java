/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.builtin;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.wst.server.ui.ServerLaunchConfigurationTab;

/**
 * A debug tab group for launching PHP Server.
 */
public class PHPServerLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {
	/*
	 * @see ILaunchConfigurationTabGroup#createTabs(ILaunchConfigurationDialog,
	 * String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[4];
		tabs[0] = new ServerLaunchConfigurationTab(new String[] { "org.eclipse.php.server.builtin" }); //$NON-NLS-1$
		tabs[0].setLaunchConfigurationDialog(dialog);
		tabs[1] = new SourceLookupTab();
		tabs[1].setLaunchConfigurationDialog(dialog);
		tabs[2] = new EnvironmentTab();
		tabs[2].setLaunchConfigurationDialog(dialog);
		tabs[3] = new CommonTab();
		tabs[3].setLaunchConfigurationDialog(dialog);
		setTabs(tabs);
	}
}