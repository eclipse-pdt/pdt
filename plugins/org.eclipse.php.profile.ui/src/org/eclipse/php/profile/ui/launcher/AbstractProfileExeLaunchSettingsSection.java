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
 * Abstract implementation of profiler launch settings section that corresponds
 * to PHP executable launch configuration type.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractProfileExeLaunchSettingsSection implements IProfilerLaunchSettingsSection {

	protected WidgetListener widgetListener;
	private ILaunchConfiguration configuration;

	@Override
	public final void createSection(Composite parent, WidgetListener widgetListener) {
		this.widgetListener = widgetListener;
		buildSection(parent);
	}

	@Override
	public void initialize(ILaunchConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		this.configuration = configuration;
	}

	@Override
	public StatusMessage isValid(ILaunchConfiguration configuration) {
		// Nothing to validate here
		return new StatusMessage(IMessageProvider.NONE, ""); //$NON-NLS-1$
	}

	protected void buildSection(Composite parent) {
		// To implement by subclasses
	}

	protected ILaunchConfiguration getConfiguration() {
		return configuration;
	}

}
