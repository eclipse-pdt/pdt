/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.profile.ui.launcher.AbstractPHPLaunchConfigurationProfilerTab.StatusMessage;
import org.eclipse.php.profile.ui.launcher.AbstractPHPLaunchConfigurationProfilerTab.WidgetListener;
import org.eclipse.swt.widgets.Composite;

/**
 * This adapter class provides default implementations for the methods described
 * by the <code>IProfilerLaunchSettingsSection</code> interface.
 * <p>
 * Classes that wish to deal with launch settings sections can extend this class
 * and override only the methods which they are interested in.
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public class ProfilerLaunchSettingsSectionAdapter implements IProfilerLaunchSettingsSection {

	@Override
	public void createSection(Composite parent, WidgetListener widgetListener) {
		// Empty
	}

	@Override
	public void initialize(ILaunchConfiguration configuration) {
		// Empty
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// Empty
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// Empty
	}

	@Override
	public StatusMessage isValid(ILaunchConfiguration configuration) {
		return new StatusMessage(IMessageProvider.NONE, ""); //$NON-NLS-1$
	}

}
