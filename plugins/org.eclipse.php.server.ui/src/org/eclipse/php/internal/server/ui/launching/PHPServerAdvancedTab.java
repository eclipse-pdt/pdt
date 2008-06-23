/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.launching;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
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
	protected Button openBrowser;
	protected boolean isOpenInBrowser;
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

		// Add the 'Open in Browser' checkbox.
		openBrowser = new Button(group, SWT.CHECK);
		openBrowser.setText("Open in Browser");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		openBrowser.setLayoutData(data);
		openBrowser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				isOpenInBrowser = b.getSelection();
				if (!isOpenInBrowser) {
					debugFirstPageBt.setSelection(true);
					debugAllPagesBt.setSelection(false);
				} else {
					debugFirstPageBt.setSelection(false);
					debugAllPagesBt.setSelection(true);
				}
				debugStartFromBt.setSelection(false);
				debugContinueBt.setSelection(false);
				enableSessionSettingButtons(isOpenInBrowser);
				updateLaunchConfigurationDialog();
			}
		});

		debugAllPagesBt = createRadioButton(group, "Debug &All Pages");
		data = (GridData) debugAllPagesBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;

		debugFirstPageBt = createRadioButton(group, "Debug &First Page Only");
		data = (GridData) debugFirstPageBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;

		debugStartFromBt = createRadioButton(group, "&Start Debug from:");
		data = (GridData) debugStartFromBt.getLayoutData();
		data.horizontalIndent = 20;

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
		data.horizontalIndent = 40;

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

	private void enableSessionSettingButtons(boolean isOpenInBrowser) {
		// also check for debug mode.
		String mode = getLaunchConfigurationDialog().getMode();
		isOpenInBrowser = isOpenInBrowser && ILaunchManager.DEBUG_MODE.equals(mode);
		debugFirstPageBt.setEnabled(isOpenInBrowser);
		debugAllPagesBt.setEnabled(isOpenInBrowser);
		debugStartFromBt.setEnabled(isOpenInBrowser);
		debugContinueBt.setEnabled(false);
		resetBt.setEnabled(false);
		debugFromTxt.setEnabled(false);
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
			isOpenInBrowser = configuration.getAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, PHPDebugPlugin.getOpenInBrowserOption());
			openBrowser.setSelection(isOpenInBrowser);
			String debugSetting = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_ALL_PAGES);
			if (IPHPDebugConstants.DEBUGGING_ALL_PAGES.equals(debugSetting)) {
				debugFirstPageBt.setSelection(false);
				debugAllPagesBt.setSelection(true);
				debugStartFromBt.setSelection(false);
			} else if (IPHPDebugConstants.DEBUGGING_FIRST_PAGE.equals(debugSetting)) {
				debugFirstPageBt.setSelection(true);
				debugAllPagesBt.setSelection(false);
				debugStartFromBt.setSelection(false);
			} else if (IPHPDebugConstants.DEBUGGING_START_FROM.equals(debugSetting)) {
				debugFirstPageBt.setSelection(false);
				debugAllPagesBt.setSelection(false);
				debugStartFromBt.setSelection(true);
				boolean shouldContinue = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE, false);
				debugContinueBt.setSelection(shouldContinue);
			}
			String startFromURL = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_START_FROM_URL, "");
			debugFromTxt.setText(startFromURL);
			updateDebugFrom();

			enableSessionSettingButtons(isOpenInBrowser);
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
		configuration.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, isOpenInBrowser);
		if (isOpenInBrowser) {
			if (debugAllPagesBt.getSelection()) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_ALL_PAGES);
			} else if (debugFirstPageBt.getSelection()) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_FIRST_PAGE);
			} else {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_START_FROM);
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_START_FROM_URL, debugFromTxt.getText());
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE, debugContinueBt.getSelection());
			}
		} else {
			// Allow only debug-first-page
			configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_FIRST_PAGE);
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
		configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_ALL_PAGES);
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
				try {
				boolean debugFromSelected = debugStartFromBt.getSelection();
				debugFromTxt.setEnabled(debugFromSelected);
				debugContinueBt.setEnabled(debugFromSelected);
				resetBt.setEnabled(debugFromSelected);
				} catch (SWTException se) {
					// Just in case the widget was disposed (cases such as the configuration deletion).
				}
			}
		});
	}

	protected class WidgetListener extends SelectionAdapter implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(SelectionEvent e) {
			setDirty(true);
			updateLaunchConfigurationDialog();
		}
	}
}
