/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.launcher;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.debug.ui.IDebugServerConnectionTest;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.ui.launching.AbstractPHPLaunchConfigurationTab;
import org.eclipse.php.profile.ui.ProfilerUIImages;
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
 * Abstract implementation of "Profiler" launch configuration tab. It handles
 * switching between dedicated settings sections and updates the corresponding
 * profiler type related data if it has been changed.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractPHPLaunchConfigurationProfilerTab extends AbstractPHPLaunchConfigurationTab {

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

	private final class NoneProfilerSection extends ProfilerLaunchSettingsSectionAdapter {

		@Override
		public StatusMessage isValid(ILaunchConfiguration configuration) {
			return new StatusMessage(IMessageProvider.ERROR, getNoProfilerMessage());
		}

	}

	private final class UnsupportedProfilerSection extends ProfilerLaunchSettingsSectionAdapter {

		@Override
		public void createSection(Composite parent, WidgetListener widgetListener) {
			Label info = new Label(parent, SWT.NONE);
			info.setText(Messages.AbstractPHPLaunchConfigurationProfilerTab_Unsupported_profiler_type);
		}

		@Override
		public StatusMessage isValid(ILaunchConfiguration configuration) {
			return new StatusMessage(IMessageProvider.ERROR,
					Messages.AbstractPHPLaunchConfigurationProfilerTab_Unsupported_profiler_type);
		}

	}

	protected Button validateProfilerBtn;
	protected IDebugServerConnectionTest[] profileTesters;
	private Label profilerName;
	private Button configureProfiler;
	private Composite mainComposite;
	private Composite settingsComposite;
	private Map<String, IProfilerLaunchSettingsSection> currentSection = new HashMap<>();

	@Override
	public String getName() {
		return Messages.AbstractPHPLaunchConfigurationProfilerTab_Profiler_group_name;
	}

	@Override
	public Image getImage() {
		return ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_PROFILE_CONF);
	}

	@Override
	public void createControl(Composite parent) {
		// Create main composite
		mainComposite = new Composite(parent, SWT.NONE);
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainComposite.setLayout(mainLayout);
		createProfilerSelectionControl(mainComposite);
		// Create composite for debugger dedicated settings
		settingsComposite = new Composite(mainComposite, SWT.NONE);
		Dialog.applyDialogFont(mainComposite);
		setControl(mainComposite);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		getSection().setDefaults(configuration);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, getCurrentProfilerId());
		getSection().performApply(configuration);
	}

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

	@Override
	protected void initialize(ILaunchConfiguration configuration) {
		getSection().initialize(configuration);
	}

	/**
	 * Implementors should provide an action that handles triggering of
	 * "Configure..." profiler settings button.
	 */
	protected abstract void handleConfigureProfiler();

	/**
	 * Implementors should provide the current profiler (debugger) ID that
	 * corresponds to debugger settings owner in related launch configuration.
	 * 
	 * @return current profiler (debugger) ID
	 */
	protected abstract String getCurrentProfilerId();

	/**
	 * Implementors should provide the appropriate message in case when there is no
	 * profiler attached to potential profiler/debugger settings owner.
	 * 
	 * @return "No" profiler attached message.
	 */
	protected abstract String getNoProfilerMessage();

	/**
	 * Implementors should update state of a "Test" button.
	 */
	protected abstract void updateProfileTest();

	/**
	 * Implementors should provide an action that handles triggering of "Test"
	 * debugger connection button.
	 */
	protected abstract void performProfileTest();

	private void createProfilerSelectionControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		GridLayout mcLayout = new GridLayout(1, false);
		mcLayout.marginHeight = 0;
		mcLayout.marginWidth = 0;
		mainComposite.setLayout(mcLayout);
		mainComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Composite profilerChoiceComposite = new Composite(mainComposite, SWT.NONE);
		GridLayout layout = new GridLayout(5, false);
		profilerChoiceComposite.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		profilerChoiceComposite.setLayoutData(data);
		Font font = parent.getFont();
		profilerChoiceComposite.setFont(font);
		Label label = new Label(profilerChoiceComposite, SWT.WRAP);
		data = new GridData(SWT.BEGINNING);
		label.setLayoutData(data);
		label.setFont(font);
		label.setText(Messages.AbstractPHPLaunchConfigurationProfilerTab_Profiler_label);
		profilerName = new Label(profilerChoiceComposite, SWT.NONE);
		Label separator = new Label(profilerChoiceComposite, SWT.NONE);
		data = new GridData(SWT.BEGINNING);
		data.widthHint = 20;
		separator.setLayoutData(data);
		configureProfiler = createPushButton(profilerChoiceComposite,
				Messages.AbstractPHPLaunchConfigurationProfilerTab_Configure, null);
		configureProfiler.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleConfigureProfiler();
			}
		});
		configureProfiler.addSelectionListener(new WidgetListener());
		validateProfilerBtn = createPushButton(profilerChoiceComposite,
				Messages.AbstractPHPLaunchConfigurationProfilerTab_Test, null);
		validateProfilerBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				performProfileTest();
			}
		});
		Label lineSeparator = new Label(mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	private IProfilerLaunchSettingsSection getSection() {
		try {
			String launchTypeId = getConfiguration().getType().getIdentifier();
			String profilerId = getCurrentProfilerId();
			IProfilerLaunchSettingsSection section = currentSection.get(profilerId);
			if (section == null) {
				// Debugger type has been changed, rebuild section
				if (PHPDebuggersRegistry.NONE_DEBUGGER_ID.equals(profilerId)) {
					section = new NoneProfilerSection();
				} else {
					section = ProfilerLaunchSettingsSectionRegistry.getSection(profilerId, launchTypeId);
					if (section == null) {
						section = new UnsupportedProfilerSection();
					}
				}
				if (settingsComposite != null) {
					buildSection(profilerId, section);
				}
			}
			return section;
		} catch (CoreException e) {
			Logger.logException(e);
		}
		// Should never return null
		return null;
	}

	private void buildSection(String debuggerId, IProfilerLaunchSettingsSection section) {
		// Update debugger type data first
		updateProfiler(debuggerId);
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

	private void updateProfiler(String profilerId) {
		profilerName.setText(PHPDebuggersRegistry.getDebuggerName(profilerId));
		try {
			// Update debugger type in original configuration
			if (getOriginalConfiguration().contentsEqual(getConfiguration())) {
				// Only debugger might have been changed, update original
				// configuration
				ILaunchConfigurationWorkingCopy wc = getOriginalConfiguration().getWorkingCopy();
				wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, profilerId);
				wc.doSave();
			}
			// Update in working copy
			if (getConfiguration() instanceof ILaunchConfigurationWorkingCopy) {
				((ILaunchConfigurationWorkingCopy) getConfiguration())
						.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, profilerId);
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		updateProfileTest();
	}

}
