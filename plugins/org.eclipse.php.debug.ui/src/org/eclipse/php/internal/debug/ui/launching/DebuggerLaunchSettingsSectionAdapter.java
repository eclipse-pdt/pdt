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

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.StatusMessage;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.WidgetListener;
import org.eclipse.swt.widgets.Composite;

/**
 * This adapter class provides default implementations for the methods described
 * by the <code>IDebuggerLaunchSettingsSection</code> interface.
 * <p>
 * Classes that wish to deal with launch settings sections can extend this class
 * and override only the methods which they are interested in.
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebuggerLaunchSettingsSectionAdapter implements IDebuggerLaunchSettingsSection {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * IDebuggerLaunchSettingsSection#createSection(org.eclipse.swt.widgets.
	 * Composite, org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab.WidgetListener)
	 */
	@Override
	public void createSection(Composite parent, WidgetListener widgetListener) {
		// Empty
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * IDebuggerLaunchSettingsSection#initialize(org.eclipse.debug.core.
	 * ILaunchConfiguration)
	 */
	@Override
	public void initialize(ILaunchConfiguration configuration) {
		// Empty
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * IDebuggerLaunchSettingsSection#performApply(org.eclipse.debug.core.
	 * ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// Empty
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * IDebuggerLaunchSettingsSection#setDefaults(org.eclipse.debug.core.
	 * ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// Empty
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * IDebuggerLaunchSettingsSection#isValid(org.eclipse.debug.core.
	 * ILaunchConfiguration)
	 */
	@Override
	public StatusMessage isValid(ILaunchConfiguration configuration) {
		return new StatusMessage(IMessageProvider.NONE, ""); //$NON-NLS-1$
	}

}
