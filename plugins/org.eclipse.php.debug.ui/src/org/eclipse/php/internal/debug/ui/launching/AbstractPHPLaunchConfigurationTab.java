/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;

/**
 * Abstract launch configuration tab implementation which stores launch
 * configuration that the tab data was initialized from.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractPHPLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private ILaunchConfiguration configuration;

	/**
	 * Initializes this tab's controls with values from the given launch
	 * configuration. This method is called when a configuration is selected to view
	 * or edit, after this tab's control has been created.
	 * 
	 * @param configuration
	 *            launch configuration
	 */
	protected abstract void initialize(ILaunchConfiguration configuration);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.
	 * debug.core.ILaunchConfiguration)
	 */
	@Override
	public final void initializeFrom(ILaunchConfiguration configuration) {
		this.configuration = configuration;
		initialize(configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		this.configuration = configuration;
	}

	/**
	 * Returns launch configuration for this tab
	 * 
	 * @return launch configuration
	 */
	protected ILaunchConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Returns original launch configuration for this tab
	 * 
	 * @return original launch configuration
	 */
	protected ILaunchConfiguration getOriginalConfiguration() {
		if (configuration instanceof ILaunchConfigurationWorkingCopy) {
			return ((ILaunchConfigurationWorkingCopy) configuration).getOriginal();
		}
		return configuration;
	}

}
