/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.actions.ControlAccessibleListener;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * A launch configuration tab that displays and edits program arguments.
 * <p>
 * This class may be instantiated. This class is not intended to be subclassed.
 * </p>
 * 
 * @since PDT 1.0
 */
public class PHPDebugArgumentsTab extends AbstractLaunchConfigurationTab {

	// Program arguments widgets
	protected Label fPrgmArgumentsLabel;
	protected Text fPrgmArgumentsText;

	// Working directory
	// protected WorkingDirectoryBlock fWorkingDirectoryBlock;

	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Font font = parent.getFont();
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		comp.setLayout(layout);
		comp.setFont(font);

		GridData gd = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(gd);
		setControl(comp);
		// setHelpContextId();

		Group group = new Group(comp, SWT.NONE);
		group.setFont(font);
		layout = new GridLayout();
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		String controlName = PHPDebugUIMessages.DebugPHPArgumentsTab_scriptArguments;
		group.setText(controlName);

		fPrgmArgumentsText = new Text(group, SWT.MULTI | SWT.WRAP | SWT.BORDER
				| SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 40;
		gd.widthHint = 100;
		fPrgmArgumentsText.setLayoutData(gd);
		fPrgmArgumentsText.setFont(font);
		fPrgmArgumentsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
		ControlAccessibleListener.addListener(fPrgmArgumentsText, group
				.getText());

		String buttonLabel = PHPDebugUIMessages.DebugPHPArgumentsTab_variables;
		Button pgrmArgVariableButton = createPushButton(group, buttonLabel,
				null);
		pgrmArgVariableButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_END));
		pgrmArgVariableButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(
						getShell());
				dialog.open();
				String variable = dialog.getVariableExpression();
				if (variable != null) {
					fPrgmArgumentsText.insert(variable);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});
		// HELP
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.LOCALLY_DEBUGGING_A_PHP_SCRIPT);

	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#dispose()
	 */
	public void dispose() {
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(ILaunchConfiguration)
	 */

	public boolean isValid(ILaunchConfiguration config) {
		return true;
	}

	/**
	 * Defaults are empty.
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(ILaunchConfigurationWorkingCopy)
	 */

	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS,
				(String) null);
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			fPrgmArgumentsText.setText(configuration.getAttribute(
					IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS, "")); //$NON-NLS-1$
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(
				IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS,
				getAttributeValueFrom(fPrgmArgumentsText));
	}

	/**
	 * Returns the string in the text widget, or <code>null</code> if empty.
	 * 
	 * @return text or <code>null</code>
	 */
	protected String getAttributeValueFrom(Text text) {
		String content = text.getText().trim();
		if (content.length() > 0) {
			return content;
		}
		return null;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return PHPDebugUIMessages.DebugPHPArgumentsTab_phpScriptArguments;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setLaunchConfigurationDialog(ILaunchConfigurationDialog)
	 */
	public void setLaunchConfigurationDialog(ILaunchConfigurationDialog dialog) {
		super.setLaunchConfigurationDialog(dialog);
		// fWorkingDirectoryBlock.setLaunchConfigurationDialog(dialog);
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getErrorMessage()
	 */
	public String getErrorMessage() {
		return super.getErrorMessage();
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getMessage()
	 */
	public String getMessage() {
		return super.getMessage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#activated(org.eclipse.debug
	 * .core.ILaunchConfigurationWorkingCopy)
	 */
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		// fWorkingDirectoryBlock.initializeFrom(workingCopy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#deactivated(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {
		// do nothing when deactivated
	}

}
