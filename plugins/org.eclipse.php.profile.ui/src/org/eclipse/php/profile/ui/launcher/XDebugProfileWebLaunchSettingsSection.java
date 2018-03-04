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

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.profile.ui.launcher.AbstractPHPLaunchConfigurationProfilerTab.StatusMessage;
import org.eclipse.swt.widgets.Composite;

/**
 * XDebug profiler settings section for web profiling (not supported yet).
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugProfileWebLaunchSettingsSection extends AbstractProfileWebLaunchSettingsSection {

	@Override
	protected void createTunnelGroup(Composite composite) {
		// Do not create SSH tunnel group
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.profile.ui.launcher.AbstractProfileWebLaunchSettingsSection#
	 * isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public StatusMessage isValid(ILaunchConfiguration configuration) {
		return new StatusMessage(IMessageProvider.ERROR,
				Messages.XDebugProfileLaunchSettingsSection_Profiling_is_not_supported);
	}

}
