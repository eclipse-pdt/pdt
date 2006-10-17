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
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

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
	protected WidgetListener listener;
	
	private ILaunchConfiguration launchConfiguration;

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
			updateDebugFrom();
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
		boolean debugFromSelected = debugStartFromBt.getSelection();
		debugFromTxt.setEnabled(debugFromSelected);
		debugContinueBt.setEnabled(debugFromSelected);
		resetBt.setEnabled(debugFromSelected);
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
