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

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.StatusMessage;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationDebuggerTab.WidgetListener;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Zend Debugger dedicated settings section for PHP web launch configuration.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerWebLaunchSettingsSection extends AbstractDebugWebLaunchSettingsSection {

	private Group browserGroup;
	private Button openBrowser;
	private Composite sessionGroup;
	private Button debugAllPagesBt;
	private Button debugFirstPageBt;
	private Button debugStartFromBt;
	private Text debugFromTxt;
	private Button resetBt;
	private Button debugContinueBt;
	private Group sourceLocationGroup;
	private Button sourcesLocal;
	private Button sourcesServer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugWebLaunchSettingsSection#createSection(org.eclipse.swt.
	 * widgets.Composite, org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationDebuggerTab.WidgetListener)
	 */
	@Override
	public void createSection(Composite parent, WidgetListener widgetListener) {
		super.createSection(parent, widgetListener);
		createBrowserGroup(parent);
		createSourceLocationGroup(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugWebLaunchSettingsSection#initialize(org.eclipse.debug.core.
	 * ILaunchConfiguration)
	 */
	@Override
	public void initialize(ILaunchConfiguration configuration) {
		super.initialize(configuration);
		try {
			boolean isOpenInBrowser = configuration.getAttribute(IPHPDebugConstants.OPEN_IN_BROWSER,
					PHPDebugPlugin.getOpenInBrowserOption());
			openBrowser.setSelection(isOpenInBrowser);
			String debugSetting = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_PAGES,
					IPHPDebugConstants.DEBUGGING_ALL_PAGES);
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
				boolean shouldContinue = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE,
						false);
				debugContinueBt.setSelection(shouldContinue);
			}
			String startFromURL = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_START_FROM_URL, ""); //$NON-NLS-1$
			debugFromTxt.setText(startFromURL);
			updateDebugFrom();
			enableSessionSettingButtons(isOpenInBrowser);
			// Initialize the source location
			boolean localCopy = configuration.getAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, false);
			sourcesLocal.setSelection(!localCopy);
			sourcesServer.setSelection(localCopy);
		} catch (CoreException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugWebLaunchSettingsSection#performApply(org.eclipse.debug.core
	 * .ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		boolean isOpenInBrowser = openBrowser.getSelection();
		configuration.setAttribute(IPHPDebugConstants.OPEN_IN_BROWSER, isOpenInBrowser);
		if (isOpenInBrowser) {
			if (debugAllPagesBt.getSelection()) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_ALL_PAGES);
			} else if (debugFirstPageBt.getSelection()) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_FIRST_PAGE);
			} else {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_START_FROM);
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_START_FROM_URL, debugFromTxt.getText());
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_SHOULD_CONTINUE,
						debugContinueBt.getSelection());
			}
		} else {
			// Allow only debug-first-page
			configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_FIRST_PAGE);
		}
		// Apply the source location
		boolean value = sourcesServer.getSelection();
		try {
			if (configuration.hasAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES)
					&& value != (configuration.getAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, false))) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, value);
			} else if (!configuration.hasAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES) && value) {
				configuration.setAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, value);
			}
		} catch (CoreException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugWebLaunchSettingsSection#setDefaults(org.eclipse.debug.core.
	 * ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(IPHPDebugConstants.DEBUGGING_PAGES, IPHPDebugConstants.DEBUGGING_ALL_PAGES);
		configuration.setAttribute(IPHPDebugConstants.DEBUGGING_USE_SERVER_FILES, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractDebugWebLaunchSettingsSection#isValid(org.eclipse.debug.core.
	 * ILaunchConfiguration)
	 */
	@Override
	public StatusMessage isValid(ILaunchConfiguration configuration) {
		StatusMessage status = super.isValid(configuration);
		if (status.getMessageType() == IMessageProvider.ERROR) {
			return status;
		}
		if (debugStartFromBt.getSelection()) {
			if (debugFromTxt.getText().trim().equals("")) { //$NON-NLS-1$
				return new StatusMessage(IMessageProvider.ERROR,
						Messages.ZendDebuggerWebLaunchSettingsSection_Invalid_debug_start_page);
			}
			try {
				new URL(debugFromTxt.getText());
			} catch (MalformedURLException mue) {
				return new StatusMessage(IMessageProvider.ERROR,
						Messages.ZendDebuggerWebLaunchSettingsSection_Invalid_URL);
			}
		}
		return status;
	}

	protected void createBrowserGroup(Composite parent) {
		browserGroup = new Group(parent, SWT.NONE);
		browserGroup.setLayout(new GridLayout(1, false));
		browserGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browserGroup.setText(Messages.ZendDebuggerWebLaunchSettingsSection_Browser);
		// Add the Browser group controls
		openBrowser = new Button(browserGroup, SWT.CHECK);
		openBrowser.setText(Messages.ZendDebuggerWebLaunchSettingsSection_Open_in_browser);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		openBrowser.setLayoutData(data);
		sessionGroup = new Composite(browserGroup, SWT.NONE);
		sessionGroup.setLayout(new GridLayout(3, false));
		sessionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		openBrowser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				boolean isOpenInBrowser = b.getSelection();
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
			}
		});
		// Add the Session group controls
		debugAllPagesBt = SWTFactory.createRadioButton(sessionGroup,
				Messages.ZendDebuggerWebLaunchSettingsSection_Debug_all_pages);
		data = (GridData) debugAllPagesBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;
		debugFirstPageBt = SWTFactory.createRadioButton(sessionGroup,
				Messages.ZendDebuggerWebLaunchSettingsSection_Debug_first_page);
		data = (GridData) debugFirstPageBt.getLayoutData();
		data.horizontalSpan = 3;
		data.horizontalIndent = 20;
		debugStartFromBt = SWTFactory.createRadioButton(sessionGroup,
				Messages.ZendDebuggerWebLaunchSettingsSection_Start_debug_from);
		data = (GridData) debugStartFromBt.getLayoutData();
		data.horizontalIndent = 20;
		debugFromTxt = new Text(sessionGroup, SWT.SINGLE | SWT.BORDER);
		debugFromTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		resetBt = SWTFactory.createPushButton(sessionGroup, Messages.ZendDebuggerWebLaunchSettingsSection_Default,
				null);
		resetBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (getConfiguration() != null) {
					try {
						debugFromTxt.setText(getConfiguration().getAttribute(Server.BASE_URL, "")); //$NON-NLS-1$
					} catch (CoreException e1) {
					}
				}
			}
		});
		debugContinueBt = SWTFactory.createCheckButton(sessionGroup,
				Messages.ZendDebuggerWebLaunchSettingsSection_Continue_debug_from, null, false, 1);
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
		openBrowser.addSelectionListener(widgetListener);
		debugFirstPageBt.addSelectionListener(widgetListener);
		debugAllPagesBt.addSelectionListener(widgetListener);
		debugContinueBt.addSelectionListener(widgetListener);
		debugStartFromBt.addSelectionListener(widgetListener);
		debugFromTxt.addModifyListener(widgetListener);
	}

	private void enableSessionSettingButtons(boolean isOpenInBrowser) {
		debugFirstPageBt.setEnabled(isOpenInBrowser);
		debugAllPagesBt.setEnabled(isOpenInBrowser);
		debugStartFromBt.setEnabled(isOpenInBrowser);
		debugContinueBt.setEnabled(false);
		resetBt.setEnabled(false);
		debugFromTxt.setEnabled(false);
	}

	private void updateDebugFrom() {
		if (getConfiguration() != null && debugFromTxt.getText().trim().equals("")) { //$NON-NLS-1$
			try {
				debugFromTxt.setText(getConfiguration().getAttribute(Server.BASE_URL, "")); //$NON-NLS-1$
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
					// Just in case the widget was disposed (cases such as the
					// configuration deletion).
				}
			}
		});
	}

	private void createSourceLocationGroup(Composite parent) {
		// Add the source origin groups
		sourceLocationGroup = new Group(parent, SWT.NONE);
		sourceLocationGroup.setLayout(new GridLayout(1, false));
		sourceLocationGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sourceLocationGroup.setText(PHPDebugUIMessages.ZendDebuggerWebLaunchSettingsSection_Source_location);
		Label label = new Label(sourceLocationGroup, SWT.NONE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(data);
		label.setText(PHPDebugUIMessages.ZendDebuggerWebLaunchSettingsSection_Source_will_be_taken_from);

		sourcesServer = SWTFactory.createRadioButton(sourceLocationGroup,
				PHPDebugUIMessages.ZendDebuggerWebLaunchSettingsSection_Source_the_server);
		sourcesLocal = SWTFactory.createRadioButton(sourceLocationGroup,
				PHPDebugUIMessages.ZendDebuggerWebLaunchSettingsSection_Source_local_otherwise_server);
		// Add widget listeners
		sourcesLocal.addSelectionListener(widgetListener);
		sourcesServer.addSelectionListener(widgetListener);
	}

}
