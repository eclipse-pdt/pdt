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

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPRuntime;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.preferences.phps.NewPHPsComboBlock;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * PHP executable launch tab is a launch configuration tab for the PHP Script
 * launching.
 */
public class PHPExecutableLaunchTab extends AbstractLaunchConfigurationTab {
	static private class ControlAccessibleListener extends AccessibleAdapter {
		private String controlName;

		ControlAccessibleListener(final String name) {
			controlName = name;
		}

		@Override
		public void getName(final AccessibleEvent e) {
			e.result = controlName;
		}

	}

	protected class WidgetListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(final ModifyEvent e) {
			updateLaunchConfigurationDialog();
			phpsComboBlock.setProject(getFileProject(fileTextField.getText()));
		}

		@Override
		public void widgetSelected(final SelectionEvent e) {
			setDirty(true);
			final Object source = e.getSource();
			if (source == fileLocationButton) {
				handleFileLocationButtonSelected();
			} else if (source == argumentVariablesButton) {
				handleChangeFileToDebug(fileTextField);
			}
		}
	}

	// Selection changed listener (checked PHP exe)
	protected final IPropertyChangeListener fPropertyChangeListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			handleSelectedPHPexeChanged();
		}
	};
	protected Text fileTextField;
	protected Button argumentVariablesButton;
	protected Text prgmArgumentsText;
	protected boolean enableFileSelection;

	protected Button fileLocationButton = null; // XXX: never set
	protected WidgetListener fListener = new WidgetListener();
	protected Text locationField;
	protected NewPHPsComboBlock phpsComboBlock;
	protected SelectionAdapter selectionAdapter;

	public PHPExecutableLaunchTab() {
		enableFileSelection = true;
		phpsComboBlock = new NewPHPsComboBlock();
	}

	@Override
	public void createControl(final Composite parent) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		final Composite mainComposite = new Composite(scrolledCompositeImpl, SWT.NONE);
		setControl(scrolledCompositeImpl);
		mainComposite.setFont(parent.getFont());
		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		mainComposite.setLayout(layout);
		mainComposite.setLayoutData(gridData);
		scrolledCompositeImpl.setContent(mainComposite);
		scrolledCompositeImpl.setLayout(layout);
		scrolledCompositeImpl.setFont(parent.getFont());
		createLocationComponent(mainComposite);
		if (enableFileSelection) {
			createFileSelectionComponent(mainComposite);
		}
		createArgumentsComponent(mainComposite);
		createVerticalSpacer(mainComposite, 1);
		Dialog.applyDialogFont(parent);
		// HELP
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.LOCALLY_DEBUGGING_A_PHP_SCRIPT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
	 */
	@Override
	public Image getImage() {
		return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_PHP_EXE_LAUNCH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return PHPDebugUIMessages.PHPExecutableLaunchTab_2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse
	 * .debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		if (enableFileSelection) {
			updateArgument(configuration);
		}
		try {
			prgmArgumentsText
					.setText(configuration.getAttribute(IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS, "")); //$NON-NLS-1$
		} catch (final CoreException e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
		isValid(configuration);
		updatePHPFromConfig(configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug
	 * .core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		setWarningMessage(null);
		try {
			final String phpExe = launchConfig.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, ""); //$NON-NLS-1$
			boolean phpExeExists = true;
			try {
				final File file = new File(phpExe);
				if (!file.exists()) {
					phpExeExists = false;
				}
			} catch (final NullPointerException e) {
				phpExeExists = false;
			}
			if (!phpExeExists) {
				setErrorMessage(PHPDebugUIMessages.PHP_Location_Message);
				return false;
			}

			if (enableFileSelection) {
				final String phpFile = launchConfig.getAttribute(IPHPDebugConstants.ATTR_FILE, ""); //$NON-NLS-1$

				if (FileUtils.resourceExists(phpFile)) {
					IResource fileToData = ResourcesPlugin.getWorkspace().getRoot().findMember(phpFile);
					// check if not a file (project, folder etc.)
					if ((fileToData.getType() != IResource.FILE) || !PHPToolkitUtil.isPHPFile((IFile) fileToData)) {
						setErrorMessage(phpFile + PHPDebugUIMessages.PHPExecutableLaunchTab_isNotPHPFile);
						return false;
					}
				} else if (new File(phpFile).exists()) {
					if (!PHPToolkitUtil.hasPHPExtention(phpFile)) {
						setErrorMessage(phpFile + PHPDebugUIMessages.PHPExecutableLaunchTab_isNotPHPFile);
						return false;
					}
				} else { // resource DOES NOT exist
					setErrorMessage(PHPDebugUIMessages.PHP_File_Not_Exist);
					return false;
				}
			}
			PHPexeItem phpExeItem = phpsComboBlock != null ? phpsComboBlock.getPHPexe() : null;
			if (phpExeItem == null) {
				String storedPHPexePath = launchConfig.getAttribute(PHPRuntime.PHP_CONTAINER, (String) null);
				phpExeItem = storedPHPexePath != null
						? PHPRuntime.getPHPexeItem(Path.fromPortableString(storedPHPexePath)) : null;
			}
			// Check if script arguments can be passed (CLI SAPI required)
			String exeProgramArgs = prgmArgumentsText != null ? prgmArgumentsText.getText() : null;
			if (exeProgramArgs == null)
			 {
				exeProgramArgs = launchConfig.getAttribute(IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS, ""); //$NON-NLS-1$
			}
			if (!exeProgramArgs.isEmpty()) {
				if (phpExeItem != null && !(PHPexeItem.SAPI_CLI.equals(phpExeItem.getSapiType()))) {
					setWarningMessage(PHPDebugUIMessages.PHPExecutableLaunchTab_argumentsWillNotBePassed);
				}
			}
		} catch (final CoreException e) {
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse
	 * .debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		if (phpsComboBlock.isDefaultPHP()) {
			configuration.setAttribute(PHPRuntime.PHP_CONTAINER, (String) null);
		} else {
			IPath containerPath = phpsComboBlock.getPath();
			String portablePath = null;
			if (containerPath != null) {
				portablePath = containerPath.toPortableString();
			}
			configuration.setAttribute(PHPRuntime.PHP_CONTAINER, portablePath);
		}
		// Set the executable path
		final String selectedExecutable = phpsComboBlock.getSelectedExecutablePath();
		if (selectedExecutable.length() == 0) {
			configuration.setAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		} else {
			configuration.setAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, selectedExecutable);
		}
		// Set the PHP ini path
		final String iniPath = phpsComboBlock.getSelectedIniPath();
		if (iniPath.length() == 0) {
			configuration.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
		} else {
			configuration.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, iniPath);
		}
		if (enableFileSelection) {
			configuration.setAttribute(IPHPDebugConstants.ATTR_FILE, fileTextField.getText().trim());
			configuration.setAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH,
					fileTextField.getData().toString().trim());
		}
		String scriptArguments = prgmArgumentsText.getText().trim();
		configuration.setAttribute(IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS,
				scriptArguments.length() > 0 ? scriptArguments : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		try {
			String executableLocation = configuration.getAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, ""); //$NON-NLS-1$
			if (executableLocation.equals("")) { //$NON-NLS-1$
				PHPexes phpExes = PHPexes.getInstance();
				final PHPexeItem phpExeItem = phpExes.getDefaultItem();
				if (phpExeItem == null) {
					return;
				}
				executableLocation = phpExeItem.getExecutable().toString();
				configuration.setAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, executableLocation);
				String iniPath = phpExeItem.getINILocation() != null ? phpExeItem.getINILocation().toString() : null;
				configuration.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, iniPath);
			}
			configuration.setAttribute(IDebugParametersKeys.EXE_CONFIG_PROGRAM_ARGUMENTS, (String) null);
		} catch (final CoreException e) {
			Logger.log(Logger.ERROR, "Error setting default configuration", e); //$NON-NLS-1$
		}
		return;
	}

	/*
	 * Fix for Bug 60163 Accessibility: New Builder Dialog missing object info
	 * for textInput controls
	 */
	public void addControlAccessibleListener(final Control control, final String controlName) {
		// strip mnemonic (&)
		final String[] strs = controlName.split("&"); //$NON-NLS-1$
		final StringBuffer stripped = new StringBuffer();
		for (String element : strs) {
			stripped.append(element);
		}
		control.getAccessible().addAccessibleListener(new ControlAccessibleListener(stripped.toString()));
	}

	public void setEnableFileSelection(final boolean enabled) {
		if (enabled == enableFileSelection) {
			return;
		}
		enableFileSelection = enabled;
		if (argumentVariablesButton != null) {
			argumentVariablesButton.setVisible(enabled);
		}
		if (fileTextField != null) {
			fileTextField.setVisible(enabled);
		}
	}

	/**
	 * Creates the controls needed to edit the argument and prompt for argument
	 * attributes of an external tool
	 * 
	 * @param parent
	 *            the composite to create the controls in
	 */
	protected void createFileSelectionComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		final String groupName = PHPDebugUIMessages.PHP_File;
		group.setText(groupName);

		GridLayout layout = new GridLayout(3, false);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gd);
		group.setFont(parent.getFont());

		fileTextField = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		fileTextField.setLayoutData(gd);
		fileTextField.addModifyListener(fListener);
		addControlAccessibleListener(fileTextField, group.getText());
		fileTextField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(fileTextField.getText());
				String location = ""; //$NON-NLS-1$
				if (resource == null) {
					fileTextField.setData(location);
					return;
				}
				if (resource.getLocation() == null) {
					location = resource.getFullPath().toString();
				} else {
					location = resource.getLocation().toOSString();
				}
				fileTextField.setData(location);
			}
		});

		argumentVariablesButton = createPushButton(group, PHPDebugUIMessages.Browse, null);
		gd = (GridData) argumentVariablesButton.getLayoutData();
		gd.horizontalSpan = 1;
		argumentVariablesButton.addSelectionListener(fListener);
		// need to strip the mnemonic from buttons
		addControlAccessibleListener(argumentVariablesButton, argumentVariablesButton.getText());
	}

	/**
	 * Creates the controls needed to edit the location attribute of an external
	 * tool
	 * 
	 * @param group
	 *            the composite to create the controls in
	 */
	protected void createLocationComponent(final Composite parent) {
		phpsComboBlock.createControl(parent);
		final Control control = phpsComboBlock.getControl();
		if (control instanceof Composite) {
			Layout layout = ((Composite) control).getLayout();
			if (layout instanceof GridLayout) {
				GridLayout gridLayout = (GridLayout) layout;
				gridLayout.marginWidth = 0;
				gridLayout.marginHeight = 0;
			}
		}
		phpsComboBlock.addPropertyChangeListener(fPropertyChangeListener);
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		control.setLayoutData(gd);
	}

	/**
	 * Creates script arguments group.
	 * 
	 * @param parent
	 *            parent component
	 */
	protected void createArgumentsComponent(final Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		group.setText(PHPDebugUIMessages.PHPExecutableLaunchTab_scriptArguments);

		prgmArgumentsText = new Text(group, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		prgmArgumentsText.setLayoutData(new GridData(GridData.FILL_BOTH));
		prgmArgumentsText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});
		addControlAccessibleListener(prgmArgumentsText, group.getText());

		Button pgrmArgVariableButton = createPushButton(group, PHPDebugUIMessages.PHPExecutableLaunchTab_variables,
				null);
		pgrmArgVariableButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		pgrmArgVariableButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(getShell());
				dialog.open();
				String variable = dialog.getVariableExpression();
				if (variable != null) {
					prgmArgumentsText.insert(variable);
					prgmArgumentsText.setFont(parent.getFont());
				}
			}
		});
	}

	/**
	 * Return the String to use as the label for the working directory field.
	 * Subclasses may wish to override.
	 */
	protected String getWorkingDirectoryLabel() {
		return PHPDebugUIMessages.WorkingDirectory;
	}

	protected void handleBreakButtonSelected() {
		updateLaunchConfigurationDialog();
	}

	/**
	 * Prompts the user to choose a location from the filesystem and sets the
	 * location as the full path of the selected file.
	 */
	protected void handleFileLocationButtonSelected() {

		final FileDialog fileDialog = new FileDialog(getShell(), SWT.NONE);
		fileDialog.setFileName(locationField.getText());

		final String text = fileDialog.open();
		if (text != null) {
			locationField.setText(text);
		}

	}

	protected void handleSelectedPHPexeChanged() {
		updateLaunchConfigurationDialog();
	}

	/**
	 * A callback method when changing the file to debug via 'Browse'
	 */
	protected void handleChangeFileToDebug(final Text textField) {
		final IResource resource = LaunchUtilities.getFileFromDialog(null, getShell(), LaunchUtil.getFileExtensions(),
				LaunchUtil.getRequiredNatures(), true);
		if (resource instanceof IFile) {
			textField.setText(resource.getFullPath().toString());

			String fileLocation = ""; //$NON-NLS-1$
			IPath location = resource.getLocation();
			if (location != null) {
				fileLocation = location.toOSString();
			} else {
				fileLocation = resource.getFullPath().toString();
			}
			textField.setData(fileLocation);
		}
	}

	/**
	 * This method updates the jre selection from the
	 * <code>ILaunchConfiguration</code>
	 * 
	 * @param config
	 *            the config to update from
	 */
	protected void updatePHPFromConfig(ILaunchConfiguration config) {
		try {
			if (enableFileSelection) {
				phpsComboBlock.setProject(getFileProject(fileTextField.getText()));
			} else {
				phpsComboBlock
						.setProject(getFileProject(config.getAttribute(IPHPDebugConstants.PHP_Project, (String) null)));
			}
			String path = config.getAttribute(PHPRuntime.PHP_CONTAINER, (String) null);
			phpsComboBlock.setPath(path != null ? Path.fromPortableString(path) : null);
		} catch (CoreException e) {
		}
	}

	protected IProject getFileProject(String phpFile) {
		if (FileUtils.resourceExists(phpFile)) {
			IResource fileToData = ResourcesPlugin.getWorkspace().getRoot().findMember(phpFile);
			return fileToData.getProject();
		}
		return null;
	}

	/**
	 * Updates the argument widgets to match the state of the given launch
	 * configuration.
	 */
	protected void updateArgument(final ILaunchConfiguration configuration) {
		String arguments = ""; //$NON-NLS-1$
		String fullPath = ""; //$NON-NLS-1$
		try {
			arguments = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE, ""); //$NON-NLS-1$
			fullPath = configuration.getAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, ""); //$NON-NLS-1$
		} catch (final CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		if (fileTextField != null) {
			fileTextField.setText(arguments);
			fileTextField.setData(fullPath);
		}
	}

	protected boolean isLaunchMode(String mode) {
		return mode.equals(getLaunchConfigurationDialog().getMode());
	}

}
