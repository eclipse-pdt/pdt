/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.ui.launching;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A PHPServerAdvancedTab for selecting advanced debug options, such as 'Debug all Pages', 'Start Debug from' etc.
 * 
 * @author shalom
 */
public class PHPServerAdvancedTab extends AbstractLaunchConfigurationTab {

	private Button debugFirstPageBt;
	private Button debugAllPagesBt;
	private Button debugStartFromBt;
	private Button debugContinueBt;
	private Button resetBt;
	private Text debugFromTxt;
	protected Button overrideBreakpiontSettings;
	protected Button breakOnFirstLine;
	protected WidgetListener listener;
	protected ILaunchConfiguration launchConfiguration;

	/**
	 * Constructor
	 */
	public PHPServerAdvancedTab() {
		listener = new WidgetListener();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 1;
		composite.setLayout(layout);

		createAdvanceControl(composite);
		createBreakControl(composite);
		createExtensionControls(composite);

		Dialog.applyDialogFont(composite);
		setControl(composite);
	}

	/**
	 * Create the advanced control.
	 * 
	 * @param composite
	 */
	protected void createAdvanceControl(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setText("Session Settings");

		debugAllPagesBt = createRadioButton(group, "Debug &All Pages");
		GridData data = (GridData) debugAllPagesBt.getLayoutData();
		data.horizontalSpan = 3;

		debugFirstPageBt = createRadioButton(group, "Debug &First Page Only");
		data = (GridData) debugFirstPageBt.getLayoutData();
		data.horizontalSpan = 3;

		debugStartFromBt = createRadioButton(group, "&Start Debug from:");

		debugFromTxt = new Text(group, SWT.SINGLE | SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		debugFromTxt.setLayoutData(data);

		resetBt = createPushButton(group, "Default", null);
		resetBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (launchConfiguration != null) {
					try {
						debugFromTxt.setText(launchConfiguration.getAttribute(Server.BASE_URL, ""));
					} catch (CoreException e1) {
					}
				}
			}
		});

		debugContinueBt = createCheckButton(group, "&Continue Debug from This Page");
		data = (GridData) debugContinueBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;

		// Add listeners
		debugStartFromBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateDebugFrom();
			}
		});

		updateDebugFrom();

		// Add widget listeners
		debugFirstPageBt.addSelectionListener(listener);
		debugAllPagesBt.addSelectionListener(listener);
		debugContinueBt.addSelectionListener(listener);
		debugStartFromBt.addSelectionListener(listener);
		debugFromTxt.addModifyListener(listener);
	}

	/**
	 * Override this method to add more widgets to this tab.
	 * 
	 * @param composite
	 */
	protected void createExtensionControls(Composite composite) {
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return "Advanced";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		launchConfiguration = configuration;
		try {
			String debugSetting = configuration.getAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_ALL_PAGES);
			if (IPHPConstants.DEBUGGING_ALL_PAGES.equals(debugSetting)) {
				debugFirstPageBt.setSelection(false);
				debugAllPagesBt.setSelection(true);
				debugStartFromBt.setSelection(false);
			} else if (IPHPConstants.DEBUGGING_FIRST_PAGE.equals(debugSetting)) {
				debugFirstPageBt.setSelection(true);
				debugAllPagesBt.setSelection(false);
				debugStartFromBt.setSelection(false);
			} else if (IPHPConstants.DEBUGGING_START_FROM.equals(debugSetting)) {
				debugFirstPageBt.setSelection(false);
				debugAllPagesBt.setSelection(false);
				debugStartFromBt.setSelection(true);
				boolean shouldContinue = configuration.getAttribute(IPHPConstants.DEBUGGING_SHOULD_CONTINUE, false);
				debugContinueBt.setSelection(shouldContinue);
			}
			String startFromURL = configuration.getAttribute(IPHPConstants.DEBUGGING_START_FROM_URL, "");
			debugFromTxt.setText(startFromURL);
			if (overrideBreakpiontSettings != null) {
				// init the breakpoint settings
				boolean isOverrideBreakpointSetting = configuration.getAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, false);
				overrideBreakpiontSettings.setSelection(isOverrideBreakpointSetting);
				breakOnFirstLine.setEnabled(isOverrideBreakpointSetting);
				breakOnFirstLine.setSelection(configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false));
			}
			updateDebugFrom();
			
			// Disables/Enables all the controls according the the debug mode.
			String mode = getLaunchConfigurationDialog().getMode();
			boolean isDebugMode = ILaunchManager.DEBUG_MODE.equals(mode);
			debugFirstPageBt.setEnabled(isDebugMode);
			debugAllPagesBt.setEnabled(isDebugMode);
			debugStartFromBt.setEnabled(isDebugMode);
			debugContinueBt.setEnabled(isDebugMode);
			resetBt.setEnabled(isDebugMode);
			debugFromTxt.setEnabled(isDebugMode);
		} catch (CoreException e) {
		}
		isValid(configuration);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		launchConfiguration = configuration;

		if (debugAllPagesBt.getSelection()) {
			configuration.setAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_ALL_PAGES);
		} else if (debugFirstPageBt.getSelection()) {
			configuration.setAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_FIRST_PAGE);
		} else {
			configuration.setAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_START_FROM);
			configuration.setAttribute(IPHPConstants.DEBUGGING_START_FROM_URL, debugFromTxt.getText());
			configuration.setAttribute(IPHPConstants.DEBUGGING_SHOULD_CONTINUE, debugContinueBt.getSelection());
		}
		if (overrideBreakpiontSettings != null) {
			configuration.setAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, overrideBreakpiontSettings.getSelection());
			configuration.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, breakOnFirstLine.getSelection());
		}
		applyExtension(configuration);
	}

	/**
	 * Override this method to perform the apply in the extending classes.
	 * 
	 * @param configuration
	 */
	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		launchConfiguration = configuration;
		setErrorMessage(null);
		configuration.setAttribute(IPHPConstants.DEBUGGING_PAGES, IPHPConstants.DEBUGGING_ALL_PAGES);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration launchConfig) {
		launchConfiguration = launchConfig;
		setMessage(null);
		setErrorMessage(null);
		if (debugStartFromBt.getSelection()) {
			if (debugFromTxt.getText().trim().equals("")) {
				setErrorMessage("Invalid debug start page");
				return false;
			}
			try {
				new URL(debugFromTxt.getText());
			} catch (MalformedURLException mue) {
				setErrorMessage("Invalid URL");
				return false;
			}
		}
		return isValidExtension(launchConfig);
	}

	//	 In case this is a debug mode, display checkboxes to override the 'Break on first line' attribute.
	protected void createBreakControl(Composite parent) {

		Group group = new Group(parent, SWT.NONE);
		group.setText("Breakpoint");
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gridData);

		overrideBreakpiontSettings = createCheckButton(group, "Override project/workspace 'Break at First Line' setting");
		breakOnFirstLine = createCheckButton(group, "Break at First Line");
		GridData data = (GridData) breakOnFirstLine.getLayoutData();
		data.horizontalIndent = 20;

		overrideBreakpiontSettings.addSelectionListener(listener);
		breakOnFirstLine.addSelectionListener(listener);
		
		// Disables/Enables all the controls according the the debug mode.
		String mode = getLaunchConfigurationDialog().getMode();
		boolean isDebugMode = ILaunchManager.DEBUG_MODE.equals(mode);
		overrideBreakpiontSettings.setEnabled(isDebugMode);
		breakOnFirstLine.setEnabled(isDebugMode);
	}

	/**
	 * Override this method to perform the isValid in the extending classes.
	 * 
	 * @param launchConfig
	 * @return true, if the extention is in a valid state.
	 */
	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}

	// Update the 'debug from' related widgets 
	private void updateDebugFrom() {
		if (launchConfiguration != null && debugFromTxt.getText().trim().equals("")) {
			try {
				debugFromTxt.setText(launchConfiguration.getAttribute(Server.BASE_URL, ""));
			} catch (CoreException e) {
			}
		}
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				boolean debugFromSelected = debugStartFromBt.getSelection();
				debugFromTxt.setEnabled(debugFromSelected);
				debugContinueBt.setEnabled(debugFromSelected);
				resetBt.setEnabled(debugFromSelected);
			}
		});
	}

	protected class WidgetListener extends SelectionAdapter implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(SelectionEvent e) {
			setDirty(true);
			Object source = e.getSource();
			if (source == overrideBreakpiontSettings) {
				breakOnFirstLine.setEnabled(overrideBreakpiontSettings.getSelection());
			}
			updateLaunchConfigurationDialog();
		}
	}
}
