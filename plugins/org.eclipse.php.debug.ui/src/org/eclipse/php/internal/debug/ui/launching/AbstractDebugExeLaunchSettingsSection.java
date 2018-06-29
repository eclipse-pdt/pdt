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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.StatusMessage;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.WidgetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Abstract implementation of debugger launch settings section that corresponds
 * to PHP executable launch configuration type. Clients should feel free to
 * override this class.
 * 
 * @author Bartlomiej Laczkowski
 */
public class AbstractDebugExeLaunchSettingsSection implements IDebuggerLaunchSettingsSection {

	protected WidgetListener widgetListener;
	protected Group breakpointGroup;
	protected Button breakOnFirstLine;
	private ILaunchConfiguration configuration;

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
		this.widgetListener = widgetListener;
		createBreakpointGroup(parent);
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
		this.configuration = configuration;
		try {
			if (breakpointGroup != null) {
				breakOnFirstLine.setSelection(configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
						PHPDebugPlugin.getStopAtFirstLine()));
			}
		} catch (final CoreException e) {
			Logger.logException(e);
		}
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
		if (breakpointGroup != null) {
			configuration.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, breakOnFirstLine.getSelection());
		}
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
		this.configuration = configuration;
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
		// Nothing to validate here
		return new StatusMessage(IMessageProvider.NONE, ""); //$NON-NLS-1$
	}

	protected void createBreakpointGroup(Composite parent) {
		breakpointGroup = new Group(parent, SWT.NONE);
		breakpointGroup.setText(Messages.AbstractDebugExeLaunchSettingsSection_Breakpoint);
		breakpointGroup.setLayout(new GridLayout(1, false));
		breakpointGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		breakOnFirstLine = SWTFactory.createCheckButton(breakpointGroup,
				Messages.AbstractDebugExeLaunchSettingsSection_Break_at_first_line, null, false, 1);
		breakOnFirstLine.addSelectionListener(widgetListener);
	}

	protected ILaunchConfiguration getConfiguration() {
		return configuration;
	}

}
