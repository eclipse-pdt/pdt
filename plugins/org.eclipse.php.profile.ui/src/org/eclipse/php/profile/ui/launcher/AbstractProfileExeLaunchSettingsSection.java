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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * createSection(org.eclipse.swt.widgets.Composite,
	 * org.eclipse.php.profile.ui.launcher.
	 * AbstractPHPLaunchConfigurationProfilerTab.WidgetListener)
	 */
	@Override
	public final void createSection(Composite parent, WidgetListener widgetListener) {
		this.widgetListener = widgetListener;
		buildSection(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * initialize(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initialize(ILaunchConfiguration configuration) {
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#
	 * setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.profile.ui.launcher.IProfilerLaunchSettingsSection#isValid(
	 * org.eclipse.debug.core.ILaunchConfiguration)
	 */
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
