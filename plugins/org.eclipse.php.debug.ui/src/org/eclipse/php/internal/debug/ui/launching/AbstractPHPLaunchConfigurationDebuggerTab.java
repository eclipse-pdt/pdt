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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Abstract implementation of "Debugger" launch configuration tab. It handles
 * switching between dedicated settings sections and updates the corresponding
 * debugger type related data if it has been changed.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractPHPLaunchConfigurationDebuggerTab extends AbstractPHPLaunchConfigurationTab {

	/**
	 * Widget listener that should be added to all controls that affects the state
	 * of corresponding launch configuration.
	 */
	public final class WidgetListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			setDirty(true);
			updateLaunchConfigurationDialog();
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			setDirty(true);
			updateLaunchConfigurationDialog();
		}
	}

	/**
	 * Status message that provides information about validation result in given
	 * settings section.
	 */
	public final static class StatusMessage implements IMessageProvider {

		private int type;
		private String message;

		public StatusMessage(int type, String message) {
			this.type = type;
			this.message = message;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public int getMessageType() {
			return type;
		}

	}

	private final class NoneDebuggerSection extends DebuggerLaunchSettingsSectionAdapter {

		@Override
		public StatusMessage isValid(ILaunchConfiguration configuration) {
			return new StatusMessage(IMessageProvider.ERROR, getNoDebuggerMessage());
		}

	}

	private final class UnsupportedDebuggerSection extends DebuggerLaunchSettingsSectionAdapter {

		@Override
		public void createSection(Composite parent, WidgetListener widgetListener) {
			Label info = new Label(parent, SWT.NONE);
			info.setText(Messages.AbstractPHPLaunchConfigurationDebuggerTab_Unsupported_debugger_type);
		}

		@Override
		public StatusMessage isValid(ILaunchConfiguration configuration) {
			return new StatusMessage(IMessageProvider.ERROR,
					Messages.AbstractPHPLaunchConfigurationDebuggerTab_Unsupported_debugger_type);
		}

	}

	protected Button validateDebuggerBtn;
	protected IDebugServerConnectionTest[] debugTesters;
	private Label debuggerName;
	private Button configureDebugger;
	private Composite mainComposite;
	private Composite settingsComposite;
	private Map<String, IDebuggerLaunchSettingsSection> currentSection = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return Messages.AbstractPHPLaunchConfigurationDebuggerTab_Debugger_tab_name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
	 */
	@Override
	public Image getImage() {
		return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_DEBUG_CONF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		// Create main composite
		mainComposite = new Composite(parent, SWT.NONE);
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainComposite.setLayout(mainLayout);
		createDebuggerSelectionControl(mainComposite);
		// Create composite for debugger dedicated settings
		settingsComposite = new Composite(mainComposite, SWT.NONE);
		Dialog.applyDialogFont(mainComposite);
		setControl(mainComposite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationTab#setDefaults(org.eclipse.debug.core.
	 * ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		try {
			if (!configuration.hasAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT)) {
				configuration.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
						PHPDebugPlugin.getStopAtFirstLine());
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		getSection().setDefaults(configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, getCurrentDebuggerId());
		getSection().performApply(configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse.
	 * debug.core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setMessage(null);
		setWarningMessage(null);
		setErrorMessage(null);
		StatusMessage status = getSection().isValid(launchConfig);
		switch (status.getMessageType()) {
		case IMessageProvider.ERROR: {
			setErrorMessage(status.getMessage());
			return false;
		}
		case IMessageProvider.WARNING: {
			setWarningMessage(status.getMessage());
			return true;
		}
		default:
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.launching.
	 * AbstractPHPLaunchConfigurationTab#initialize(org.eclipse.debug.core.
	 * ILaunchConfiguration)
	 */
	@Override
	protected void initialize(ILaunchConfiguration configuration) {
		getSection().initialize(configuration);
	}

	/**
	 * Implementors should provide an action that handles triggering of
	 * "Configure..." debugger settings button.
	 */
	protected abstract void handleConfigureDebugger();

	/**
	 * Implementors should provide the current debugger ID that corresponds to
	 * debugger settings owner in related launch configuration.
	 * 
	 * @return current debugger ID
	 */
	protected abstract String getCurrentDebuggerId();

	/**
	 * Implementors should provide the appropriate message in case when there is no
	 * debugger attached to potential debugger settings owner.
	 * 
	 * @return "No" debugger attached message.
	 */
	protected abstract String getNoDebuggerMessage();

	/**
	 * Implementors should update state of a "Test" button.
	 */
	protected abstract void updateDebugTest();

	/**
	 * Implementors should provide an action that handles triggering of "Test"
	 * debugger connection button.
	 */
	protected abstract void performDebugTest();

	private void createDebuggerSelectionControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		GridLayout mcLayout = new GridLayout(1, false);
		mcLayout.marginHeight = 0;
		mcLayout.marginWidth = 0;
		mainComposite.setLayout(mcLayout);
		mainComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Composite debuggerChoiceComposite = new Composite(mainComposite, SWT.NONE);
		GridLayout layout = new GridLayout(5, false);
		debuggerChoiceComposite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		debuggerChoiceComposite.setLayoutData(data);
		Font font = parent.getFont();
		debuggerChoiceComposite.setFont(font);
		Label label = new Label(debuggerChoiceComposite, SWT.WRAP);
		data = new GridData(SWT.BEGINNING);
		label.setLayoutData(data);
		label.setFont(font);
		label.setText(Messages.AbstractPHPLaunchConfigurationDebuggerTab_Debugger_type);
		debuggerName = new Label(debuggerChoiceComposite, SWT.NONE);
		Label separator = new Label(debuggerChoiceComposite, SWT.NONE);
		data = new GridData(SWT.BEGINNING);
		data.widthHint = 20;
		separator.setLayoutData(data);
		configureDebugger = createPushButton(debuggerChoiceComposite,
				Messages.AbstractPHPLaunchConfigurationDebuggerTab_Configure, null);
		configureDebugger.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleConfigureDebugger();
			}
		});
		configureDebugger.addSelectionListener(new WidgetListener());
		validateDebuggerBtn = createPushButton(debuggerChoiceComposite,
				Messages.AbstractPHPLaunchConfigurationDebuggerTab_Test, null);
		validateDebuggerBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				performDebugTest();
			}
		});
		Label lineSeparator = new Label(mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	private IDebuggerLaunchSettingsSection getSection() {
		try {
			String launchTypeId = getConfiguration().getType().getIdentifier();
			String debuggerId = getCurrentDebuggerId();
			IDebuggerLaunchSettingsSection section = currentSection.get(debuggerId);
			if (section == null) {
				// Debugger type has been changed, rebuild section
				if (PHPDebuggersRegistry.NONE_DEBUGGER_ID.equals(debuggerId)) {
					section = new NoneDebuggerSection();
				} else {
					section = DebuggerLaunchSettingsSectionRegistry.getSection(debuggerId, launchTypeId);
					if (section == null) {
						section = new UnsupportedDebuggerSection();
					}
				}
				if (settingsComposite != null) {
					buildSection(debuggerId, section);
				}
			}
			return section;
		} catch (CoreException e) {
			Logger.logException(e);
		}
		// Should never return null
		return null;
	}

	private void buildSection(String debuggerId, IDebuggerLaunchSettingsSection section) {
		// Update debugger type data first
		updateDebugger(debuggerId);
		// Store as singular key map (most recent section by debugger ID)
		currentSection.clear();
		currentSection.put(debuggerId, section);
		settingsComposite.dispose();
		settingsComposite = new Composite(mainComposite, SWT.NONE);
		settingsComposite.setLayout(new GridLayout());
		GridData dscData = new GridData(SWT.FILL, SWT.FILL, true, true);
		settingsComposite.setLayoutData(dscData);
		section.createSection(settingsComposite, new WidgetListener());
		// Rebuild main composite
		mainComposite.layout();
		section.initialize(getConfiguration());
	}

	private void updateDebugger(String debuggerId) {
		debuggerName.setText(PHPDebuggersRegistry.getDebuggerName(debuggerId));
		try {
			// Update debugger type in original configuration
			if (getOriginalConfiguration().contentsEqual(getConfiguration())) {
				// Only debugger might have been changed, update original
				// configuration
				ILaunchConfigurationWorkingCopy wc = getOriginalConfiguration().getWorkingCopy();
				wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerId);
				wc.doSave();
			}
			// Update in working copy
			if (getConfiguration() instanceof ILaunchConfigurationWorkingCopy) {
				((ILaunchConfigurationWorkingCopy) getConfiguration())
						.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, debuggerId);
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		updateDebugTest();
	}

}
