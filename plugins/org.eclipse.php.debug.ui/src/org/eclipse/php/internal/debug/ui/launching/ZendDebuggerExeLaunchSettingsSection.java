/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;

/**
 * Zend Debugger dedicated settings section for PHP executable launch
 * configuration.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerExeLaunchSettingsSection extends AbstractDebugExeLaunchSettingsSection {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugExeLaunchSettingsSection#performApply(org.eclipse.debug.core
	 * .ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugExeLaunchSettingsSection#setDefaults(org.eclipse.debug.core.
	 * ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, true);
	}

}
