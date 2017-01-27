/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences.launcher;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.SelectionButtonDialogFieldGroup;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.core.launch.ScriptLauncherManager;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseAdapter;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.preferences.phps.PHPsPreferencePage;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.OptionsConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public abstract class LauncherConfigurationBlock extends OptionsConfigurationBlock implements IDialogFieldListener {

	protected final Key phpExecutable;
	protected final Key scriptToExecute;
	protected final Key useScriptInsideProject;
	protected final String pluginID;

	protected Group scriptGroup;
	protected ComboDialogField exes;
	protected Button testButton;
	protected PHPexes phpExes;

	protected SelectionButtonDialogFieldGroup buttonGroup;
	protected StringButtonDialogField scriptField;
	protected IProject project;

	@Inject
	public ScriptLauncherManager manager;

	public LauncherConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container, LauncherKeyBag keyBag) {
		super(context, project, keyBag.getAllKeys(), container);

		phpExecutable = keyBag.getPHPExecutableKey();
		scriptToExecute = keyBag.getScriptKey();
		useScriptInsideProject = keyBag.getUseProjectKey();
		pluginID = getPluginId();
		this.project = project;

		ContextInjectionFactory.inject(this, ComposerUIPlugin.getDefault().getEclipseContext());
	}

	protected abstract String getPluginId();

	protected abstract void afterSave();

	protected abstract void beforeSave();

	protected abstract String getHeaderLabel();

	protected abstract String getProjectChoiceLabel();

	protected abstract String getGlobalChoiceLabel();

	protected abstract String getScriptLabel();

	protected abstract String getButtonGroupLabel();

	protected abstract String getScriptFieldLabel();

	protected abstract boolean validateScript(String text);

	@Override
	public Control createContents(Composite parent) {
		setShell(parent.getShell());

		Label header = new Label(parent, SWT.NONE);
		header.setText(getHeaderLabel());
		Composite markersComposite = createInnerContent(parent);
		validateSettings(null, null, null);

		return markersComposite;
	}

	protected Composite createInnerContent(Composite folder) {

		Composite result = new Composite(folder, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		result.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 440;
		result.setLayoutData(gd);

		// php
		gd = new GridData(GridData.FILL_HORIZONTAL);
		Group sourceFolderGroup = new Group(result, SWT.NONE);
		sourceFolderGroup.setLayout(new GridLayout(3, false));
		sourceFolderGroup.setLayoutData(gd);
		sourceFolderGroup.setText(Messages.LauncherConfigurationBlock_Title);

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		Link prefLink = new Link(sourceFolderGroup, SWT.WRAP);
		prefLink.setText(Messages.LauncherConfigurationBlock_PhpExesLink);
		prefLink.setLayoutData(gridData);

		prefLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getPreferenceContainer();
				container.openPage(PHPsPreferencePage.ID, null);
			};
		});

		Link helpLink = new Link(sourceFolderGroup, SWT.WRAP);
		helpLink.setLayoutData(gridData);
		helpLink.setText(Messages.LauncherConfigurationBlock_HelpLink);
		helpLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser()
							.openURL(new URL("http://www.phptherightway.com/#getting_started")); //$NON-NLS-1$
				} catch (Exception e1) {
					Logger.logException(e1);
				}
			};
		});

		exes = new ComboDialogField(SWT.READ_ONLY);
		exes.setLabelText(Messages.LauncherConfigurationBlock_PhpExeLabel);
		exes.doFillIntoGrid(sourceFolderGroup, 2);
		exes.setDialogFieldListener(this);

		createTestButton(sourceFolderGroup);

		loadExecutables();

		createScriptGroup(result);
		loadPhar();

		if (phpExes.getAllItems().length == 0) {
			testButton.setEnabled(false);
		}

		createExtraContent(result);

		return result;
	}

	protected void createScriptGroup(Composite result) {

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		scriptGroup = new Group(result, SWT.NONE);
		scriptGroup.setLayout(new GridLayout(3, false));
		scriptGroup.setLayoutData(gd);
		scriptGroup.setText(getScriptLabel());

		buttonGroup = new SelectionButtonDialogFieldGroup(SWT.RADIO,
				new String[] { getProjectChoiceLabel(), getGlobalChoiceLabel() }, 2);
		buttonGroup.setLabelText(getButtonGroupLabel());
		buttonGroup.doFillIntoGrid(scriptGroup, 3);
		buttonGroup
				.setDialogFieldListener(new org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener() {
					@Override
					public void dialogFieldChanged(
							org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField field) {
						scriptField.setEnabled(buttonGroup.isSelected(1));
					}
				});

		scriptField = new StringButtonDialogField(new IStringButtonAdapter() {
			@Override
			public void changeControlPressed(DialogField field) {
				FileDialog dialog = new FileDialog(getShell());
				String path = dialog.open();
				if (path != null) {
					scriptField.setText(path);
				}
			}
		});

		scriptField.setButtonLabel(Messages.LauncherConfigurationBlock_BrowseButton);

		boolean useProjectPhar = getBooleanValue(useScriptInsideProject);

		if (useProjectPhar) {
			scriptField.setEnabled(false);
			buttonGroup.setSelection(0, true);
			buttonGroup.setSelection(1, false);
		} else {
			buttonGroup.setSelection(0, false);
			buttonGroup.setSelection(1, true);
		}

		scriptField.setDialogFieldListener(this);
		scriptField.setLabelText(getScriptFieldLabel());
		scriptField.doFillIntoGrid(scriptGroup, 3);
	}

	protected void createExtraContent(Composite result) {
		// override to add additional stuff to the page
	}

	private void loadPhar() {

		String phar = getValue(scriptToExecute);
		if (phar == null) {
			return;
		}

		scriptField.setText(phar);
	}

	protected void loadExecutables() {

		phpExes = PHPexes.getInstance();
		String current = getValue(phpExecutable);
		List<String> items = new ArrayList<String>();

		int i = 0;
		int select = -1;
		for (PHPexeItem item : phpExes.getAllItems()) {
			if (item.isDefault() && (current == null || current.length() == 0)
					|| item.getExecutable().toString().equals(current)) {
				select = i;
			}
			i++;
			items.add(item.getName());
		}

		exes.setItems(items.toArray(new String[items.size()]));

		if (select > -1) {
			exes.selectItem(select);
		}
	}

	protected void createTestButton(Composite parent) {

		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		testButton = new Button(parent, SWT.PUSH);
		testButton.setLayoutData(gd);

		testButton.setText(Messages.LauncherConfigurationBlock_TestButton);
		testButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String current = exes.getText();
					PHPexeItem phPexeItem = null;

					for (PHPexeItem i : phpExes.getAllItems()) {
						if (current.equals(i.getName())) {
							phPexeItem = i;
							break;
						}
					}

					if (phPexeItem == null) {
						Logger.log(Logger.WARNING, "No executable selected"); //$NON-NLS-1$
						return;
					}

					ExecutionResponseAdapter adapter = new ExecutionResponseAdapter() {
						public void executionFailed(final String response, Exception exception) {
							getShell().getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									String message = Messages.LauncherConfigurationBlock_ExecutionFailedMessage;
									if (response != null && response.length() > 0) {
										message += NLS.bind(Messages.LauncherConfigurationBlock_ReasonMessage,
												response);
									}
									MessageDialog.openInformation(getShell(),
											Messages.LauncherConfigurationBlock_TestDialogTitle, message);
								}
							});
						};

						public void executionFinished(final String response, int exitValue) {
							getShell().getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									String message = Messages.LauncherConfigurationBlock_TestSuccessMessage;
									if (response != null && response.length() > 0) {
										message += NLS.bind(Messages.LauncherConfigurationBlock_DetectedVersionMessage,
												response);
									} else {
										message += Messages.LauncherConfigurationBlock_CannotDetermineVersionMessage;
									}
									MessageDialog.openInformation(getShell(),
											Messages.LauncherConfigurationBlock_TestDialogTitle, message);
								}
							});
						};
					};

					// TODO: refactor
					new Thread(new ExecutableTester(phPexeItem, adapter)).run();

				} catch (Exception ex) {
					Logger.logException(ex);
				}
			}
		});
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue, String newValue) {

		StatusInfo status = new StatusInfo();

		if (phpExes.getAllItems().length == 0) {
			status = new StatusInfo(StatusInfo.WARNING, Messages.LauncherConfigurationBlock_NoPHPConfiguredError);
		}

		if (buttonGroup != null && buttonGroup.isSelected(1)) {
			if (!validateScript(scriptField.getText())) {
				status = new StatusInfo(StatusInfo.WARNING, Messages.LauncherConfigurationBlock_InvalidPHPScriptError);
			}
		}

		fContext.statusChanged(status);
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

	protected final Key getCoreKey(String key) {
		return getKey(pluginID, key);
	}

	@Override
	public void performDefaults() {

		scriptField.setText(""); //$NON-NLS-1$
		scriptField.setEnabled(false);

		if (buttonGroup != null) {
			buttonGroup.setSelection(0, true);
			buttonGroup.setSelection(1, false);
		}
		setValue(useScriptInsideProject, true);
		setValue(scriptToExecute, ""); //$NON-NLS-1$
		setValue(phpExecutable, ""); //$NON-NLS-1$
		validateSettings(null, null, null);
		super.performDefaults();
	}

	@Override
	public boolean performApply() {
		saveExecutable();
		return super.performApply();
	}

	@Override
	public boolean performOk() {
		saveExecutable();
		return super.performOk();
	}

	protected void saveExecutable() {

		beforeSave();
		String selected = exes.getText();
		String executable = null;

		for (PHPexeItem exe : phpExes.getAllItems()) {
			if (exe.getName().equals(selected)) {
				executable = exe.getExecutable().getAbsolutePath();
			}
		}

		setValue(phpExecutable, executable);
		setValue(scriptToExecute, scriptField.getText());
		setValue(useScriptInsideProject, doUseScriptInsideProject());

		afterSave();
	}

	protected boolean doUseScriptInsideProject() {
		return buttonGroup.isSelected(0);
	}

	@Override
	public void dialogFieldChanged(DialogField field) {
		validateSettings(null, null, null);
	}
}
