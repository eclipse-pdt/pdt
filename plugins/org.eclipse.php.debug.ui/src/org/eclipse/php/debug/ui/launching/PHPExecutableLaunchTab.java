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

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.debug.core.preferences.PHPexeItem;
import org.eclipse.php.debug.core.preferences.PHPexes;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.debug.ui.preferences.phps.PHPexeDescriptor;
import org.eclipse.php.debug.ui.preferences.phps.PHPsComboBlock;
import org.eclipse.php.server.ui.ServerUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class PHPExecutableLaunchTab extends AbstractLaunchConfigurationTab {
	private class ControlAccessibleListener extends AccessibleAdapter {
		private String controlName;

		ControlAccessibleListener(final String name) {
			controlName = name;
		}

		public void getName(final AccessibleEvent e) {
			e.result = controlName;
		}

	}

	protected class WidgetListener extends SelectionAdapter implements ModifyListener {
		public void modifyText(final ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(final SelectionEvent e) {
			setDirty(true);
			final Object source = e.getSource();

			if (source == fileLocationButton) {
				handleFileLocationButtonSelected();
			} else if (source == argumentVariablesButton) {
				handleVariablesButtonSelected(argumentField);
			} else if (source == overrideBreakpiontSettings) {
				handleBreakpointOverride();
			} else if (source == breakOnFirstLine) {
				handleBreakButtonSelected();
			}
		}
	}

	public final static String FIRST_EDIT = "editedByPHPExecutableLaunchTab"; //$NON-NLS-1$
	private Text argumentField;

	private Button argumentVariablesButton;
	private boolean enableDebugInfoOption;

	private boolean enableFileSelection;

	// Selection changed listener (checked PHP exe)
	private final ISelectionChangedListener fCheckListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			handleSelectedPHPexeChanged();
		}
	};

	private Button fileLocationButton;
	protected WidgetListener fListener = new WidgetListener();

	private Text locationField;
	protected PHPsComboBlock phpsComboBlock;
	protected Button overrideBreakpiontSettings;
	protected Button breakOnFirstLine;
	private Button runWithDebugInfo;

	protected SelectionAdapter selectionAdapter;

	public PHPExecutableLaunchTab() {
		enableFileSelection = true;
		phpsComboBlock = new PHPsComboBlock();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#activated(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void activated(final ILaunchConfigurationWorkingCopy workingCopy) {
	}

	/*
	 * Fix for Bug 60163 Accessibility: New Builder Dialog missing object info for textInput controls
	 */
	public void addControlAccessibleListener(final Control control, final String controlName) {
		//strip mnemonic (&)
		final String[] strs = controlName.split("&"); //$NON-NLS-1$
		final StringBuffer stripped = new StringBuffer();
		for (int i = 0; i < strs.length; i++)
			stripped.append(strs[i]);
		control.getAccessible().addAccessibleListener(new ControlAccessibleListener(stripped.toString()));
	}

	/**
	 * Creates the controls needed to edit the argument and
	 * prompt for argument attributes of an external tool
	 *
	 * @param parent the composite to create the controls in
	 */
	protected void createArgumentComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		final String groupName = PHPDebugUIMessages.Arguments;
		group.setText(groupName);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gridData);
		group.setFont(parent.getFont());

		//argumentField = new Text(group, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		argumentField = new Text(group, SWT.BORDER);
		//gridData = new GridData(GridData.FILL_BOTH);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		//gridData.heightHint = 30;
		argumentField.setLayoutData(gridData);
		argumentField.addModifyListener(fListener);
		addControlAccessibleListener(argumentField, group.getText());

		final Composite composite = new Composite(group, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		composite.setLayout(layout);
		composite.setLayoutData(gridData);
		composite.setFont(parent.getFont());

		argumentVariablesButton = createPushButton(composite, PHPDebugUIMessages.Variables, null);
		argumentVariablesButton.addSelectionListener(fListener);
		addControlAccessibleListener(argumentVariablesButton, argumentVariablesButton.getText()); // need to strip the mnemonic from buttons
	}

	// In case this is a debug mode, display checkboxes to override the 'Break on first line' attribute.
	private void createBreakControl(Composite parent) {
		String mode = getLaunchConfigurationDialog().getMode();
		if (ILaunchManager.DEBUG_MODE.equals(mode)) {
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

			overrideBreakpiontSettings.addSelectionListener(fListener);
			breakOnFirstLine.addSelectionListener(fListener);
		}
	}

	public void createControl(final Composite parent) {
		if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.RUN_MODE))
			setEnableDebugInfoOption(true);

		final Composite mainComposite = new Composite(parent, SWT.NONE);
		setControl(mainComposite);
		mainComposite.setFont(parent.getFont());
		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		mainComposite.setLayout(layout);
		mainComposite.setLayoutData(gridData);

		createLocationComponent(mainComposite);
		if (enableDebugInfoOption)
			createDebugInfoComponent(mainComposite);
		//createWorkDirectoryComponent(mainComposite);
		if (enableFileSelection)
			createArgumentComponent(mainComposite);

		createBreakControl(mainComposite);
		createVerticalSpacer(mainComposite, 1);

		Dialog.applyDialogFont(parent);
	}

	/**
	 * Creates the controls needed to edit the working directory
	 * attribute of an external tool
	 * 
	 * @param parent the composite to create the controls in
	 */

	protected void createDebugInfoComponent(final Composite parent) {
		runWithDebugInfo = new Button(parent, SWT.CHECK);
		runWithDebugInfo.setText(PHPDebugUIMessages.PHPexe_Run_With_Debug_Info);
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		runWithDebugInfo.setLayoutData(gd);

		runWithDebugInfo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent se) {
				updateLaunchConfigurationDialog();
			}
		});

	}

	/**
	 * Creates the controls needed to edit the location
	 * attribute of an external tool
	 * 
	 * @param group the composite to create the controls in
	 */
	protected void createLocationComponent(final Composite parent) {

		//		phpsComboBlock.setDefaultPHPexeDescriptor(getDefaultPHPexeDescriptor());
		phpsComboBlock.setSpecificPHPexeDescriptor(getSpecificPHPexeDescriptor());
		phpsComboBlock.createControl(parent);
		final Control control = phpsComboBlock.getControl();
		phpsComboBlock.addSelectionChangedListener(fCheckListener);
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		control.setLayoutData(gd);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#deactivated(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void deactivated(final ILaunchConfigurationWorkingCopy workingCopy) {
	}

	private boolean fileExists(final String projectPath) {
		if (projectPath == null || "".equals(projectPath))
			return false;

		final IPath p3 = new Path(projectPath);
		final boolean file = ResourcesPlugin.getWorkspace().getRoot().exists(p3);
		if (file)
			return true;
		return false;
	}

	protected PHPexeDescriptor getDefaultPHPexeDescriptor() {
		return null;
	}

	public String getName() {
		return "PHP Executable";
	}

	protected PHPexeDescriptor getSpecificPHPexeDescriptor() {
		return null;
	}

	/**
	 * Return the String to use as the label for the working directory field.
	 * Subclasses may wish to override.
	 */
	protected String getWorkingDirectoryLabel() {
		return PHPDebugUIMessages.WorkingDirectory;
	}

	/**
	 * Prompts the user to choose a location from the filesystem and
	 * sets the location as the full path of the selected file.
	 */
	protected void handleFileLocationButtonSelected() {

		final FileDialog fileDialog = new FileDialog(getShell(), SWT.NONE);
		fileDialog.setFileName(locationField.getText());

		final String text = fileDialog.open();
		if (text != null)
			locationField.setText(text);

	}

	protected void handleSelectedPHPexeChanged() {
		updateLaunchConfigurationDialog();
	}

	/**
	 * A variable entry button has been pressed for the given text
	 * field. Prompt the user for a variable and enter the result
	 * in the given field.
	 */
	private void handleVariablesButtonSelected(final Text textField) {
		IFile file = null;
		final IResource resource = ServerUtilities.getFileFromDialog(null, getShell(), LaunchUtil.getFileExtensions(), LaunchUtil.getRequiredNatures());
		if (resource instanceof IFile)
			file = (IFile) resource;
		if (file != null)
			textField.setText(file.getFullPath().toString());
	}

	protected void handleBreakpointOverride() {
		breakOnFirstLine.setEnabled(overrideBreakpiontSettings.getSelection());
		updateLaunchConfigurationDialog();
	}

	protected void handleBreakButtonSelected() {
		updateLaunchConfigurationDialog();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(final ILaunchConfiguration configuration) {
		updateLocation(configuration);
		//updateWorkingDirectory(configuration);
		if (enableDebugInfoOption)
			updateDebugInfoOption(configuration);
		if (enableFileSelection)
			updateArgument(configuration);
		// init the breakpoint settings
		try {
			boolean isOverrideBreakpointSetting = configuration.getAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, false);
			if(overrideBreakpiontSettings != null) {
				overrideBreakpiontSettings.setSelection(isOverrideBreakpointSetting);
			}
			if(breakOnFirstLine != null) {
				breakOnFirstLine.setEnabled(isOverrideBreakpointSetting);
				breakOnFirstLine.setSelection(configuration.getAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false));
			}
		} catch (CoreException e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
		isValid(configuration);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		try {
			final String phpExe = launchConfig.getAttribute(PHPCoreConstants.ATTR_LOCATION, "");
			boolean phpExeExists = true;
			try {
				final File file = new File(phpExe);
				if (!file.exists())
					phpExeExists = false;
			} catch (final NullPointerException e) {
				phpExeExists = false;
			}
			if (!phpExeExists) {
				setErrorMessage(PHPDebugUIMessages.PHP_Location_Message);
				return false;
			}

			if (enableFileSelection) {
				final String phpFile = launchConfig.getAttribute(PHPCoreConstants.ATTR_FILE, "");
				if (!fileExists(phpFile)) {
					setErrorMessage(PHPDebugUIMessages.PHP_File_Not_Exist);
					return false;
				}
			}
		} catch (final CoreException e) {
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		//		String location= locationField.getText().trim();
		final String location = phpsComboBlock.getSelectedLocation();
		if (location.length() == 0)
			configuration.setAttribute(PHPCoreConstants.ATTR_LOCATION, (String) null);
		else
			configuration.setAttribute(PHPCoreConstants.ATTR_LOCATION, location);

		String arguments = null;
		if (!enableFileSelection || (arguments = argumentField.getText().trim()).length() == 0)
			configuration.setAttribute(PHPCoreConstants.ATTR_FILE, (String) null);
		else
			configuration.setAttribute(PHPCoreConstants.ATTR_FILE, arguments);
		final boolean debugInfo = enableDebugInfoOption ? runWithDebugInfo.getSelection() : true;
		configuration.setAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, debugInfo);

		if(overrideBreakpiontSettings != null)
			configuration.setAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, overrideBreakpiontSettings.getSelection());
		if(breakOnFirstLine != null)
			configuration.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, breakOnFirstLine.getSelection());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		try {
			String location = configuration.getAttribute(PHPCoreConstants.ATTR_LOCATION, ""); //$NON-NLS-1$
			if (location.equals("")) {
				final PHPexes phpExes = new PHPexes();
				phpExes.load(PHPDebugUIPlugin.getDefault().getPluginPreferences());
				PHPexeItem phpExeItem = phpExes.getDefaultItem();
				if(phpExeItem == null) return;
				location = phpExeItem.getPhpEXE().toString();
				configuration.setAttribute(PHPCoreConstants.ATTR_LOCATION, location);
				configuration.setAttribute(IDebugParametersKeys.OVERRIDE_FIRST_LINE_BREAKPOINT, false);
			}
		} catch (final CoreException e) {
			Logger.log(Logger.ERROR, "Error setting default configuration", e); //$NON-NLS-1$
		}
		return;
	}

	public void setEnableDebugInfoOption(final boolean enabled) {
		if (enabled == enableDebugInfoOption)
			return;
		enableDebugInfoOption = true;
		if (runWithDebugInfo != null)
			runWithDebugInfo.setVisible(enabled);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	/*
	 public Image getImage() {
	 return ExternalToolsImages.getImage(IExternalToolConstants.IMG_TAB_MAIN);
	 }
	 */

	public void setEnableFileSelection(final boolean enabled) {
		if (enabled == enableFileSelection)
			return;
		enableFileSelection = enabled;
		if (argumentVariablesButton != null)
			argumentVariablesButton.setVisible(enabled);
		if (argumentField != null)
			argumentField.setVisible(enabled);

	}

	/**
	 * Updates the argument widgets to match the state of the given launch
	 * configuration.
	 */
	protected void updateArgument(final ILaunchConfiguration configuration) {
		String arguments = ""; //$NON-NLS-1$
		try {
			arguments = configuration.getAttribute(PHPCoreConstants.ATTR_FILE, ""); //$NON-NLS-1$
		} catch (final CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		if (argumentField != null)
			argumentField.setText(arguments);
	}

	/**
	 * Updates the "Run With Debug Option" to match the state of the given launch
	 * configuration.
	 */
	protected void updateDebugInfoOption(final ILaunchConfiguration configuration) {

		boolean runOption = PHPDebugPlugin.getDebugInfoOption();
		try {
			runOption = configuration.getAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, runOption);
		} catch (final CoreException e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
		if (runWithDebugInfo != null)
			runWithDebugInfo.setSelection(runOption);
	}

	/**
	 * Updates the location widgets to match the state of the given launch
	 * configuration.
	 */
	protected void updateLocation(final ILaunchConfiguration configuration) {
		String location = ""; //$NON-NLS-1$
		try {
			location = configuration.getAttribute(PHPCoreConstants.ATTR_LOCATION, ""); //$NON-NLS-1$
		} catch (final CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		//		locationField.setText(location);
		final PHPexes exes = phpsComboBlock.getPHPs(true);
		PHPexeItem phpexe;
		if (location != null && location.length() > 0)
			phpexe = exes.getItemForFile(location);
		else
			phpexe = exes.getDefaultItem();
		phpsComboBlock.setPHPexe(phpexe);
	}
}
