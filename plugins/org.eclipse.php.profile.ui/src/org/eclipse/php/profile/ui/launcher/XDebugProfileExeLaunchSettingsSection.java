/*******************************************************************************
 * Copyright (c) 2017, 2018 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *  Dawid Pakuła - xDebug profiling
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

/**
 * XDebug profiler settings section for CLI profiling (not supported yet).
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugProfileExeLaunchSettingsSection extends AbstractProfileExeLaunchSettingsSection {

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
	}

	@Override
	public void initialize(ILaunchConfiguration configuration) {
		super.initialize(configuration);

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
	}

	@Override
	protected void buildSection(Composite parent) {
	}

}
