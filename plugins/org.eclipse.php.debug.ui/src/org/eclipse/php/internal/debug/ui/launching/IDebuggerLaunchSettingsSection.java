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
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.StatusMessage;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.WidgetListener;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * A debugger launch settings section is used to edit/view attributes of a
 * specific debugger type in 'Debugger' tab. Debugger launch settings section
 * are contributed with the use of
 * <code>org.eclipse.php.debug.ui.phpDebuggerLaunchSettingsSections</code>
 * extension point.
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IDebuggerLaunchSettingsSection {

	/**
	 * Creates the settings section for this launch configuration tab under the
	 * given parent composite. This method is called once on tab creation, after
	 * <code>setLaunchConfigurationDialog</code> is called.
	 * 
	 * @param parent
	 * @param widgetListener
	 */
	void createSection(Composite parent, WidgetListener widgetListener);

	/**
	 * Initializes this sections's controls with values from the given launch
	 * configuration. This method is called when a configuration is selected to view
	 * or edit, after 'Debugger' tab's control has been created.
	 * 
	 * @param configuration
	 */
	void initialize(ILaunchConfiguration configuration);

	/**
	 * Applies values from this section into the given launch configuration.
	 * 
	 * @param configuration
	 */
	void performApply(ILaunchConfigurationWorkingCopy configuration);

	/**
	 * Initializes the given launch configuration with default values for this
	 * section. This method is called when a new launch configuration is created
	 * such that the configuration can be initialized with meaningful values. This
	 * method may be called before this sections's control is created.
	 * 
	 * @param configuration
	 */
	void setDefaults(ILaunchConfigurationWorkingCopy configuration);

	/**
	 * Returns whether this tab is in a valid state in the context of the specified
	 * launch configuration. This information is typically used by the launch
	 * configuration dialog to decide when it is okay to launch.
	 * 
	 * @param configuration
	 * @return status message with validation state information
	 */
	StatusMessage isValid(ILaunchConfiguration configuration);

}
