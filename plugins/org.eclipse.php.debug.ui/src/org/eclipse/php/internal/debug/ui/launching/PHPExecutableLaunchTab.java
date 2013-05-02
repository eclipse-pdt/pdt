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
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPRuntime;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchDelegateProxy;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.preferences.phps.NewPHPsComboBlock;
import org.eclipse.php.internal.debug.ui.preferences.phps.PHPexeDescriptor;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
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

		public void getName(final AccessibleEvent e) {
			e.result = controlName;
		}

	}

	protected class WidgetListener extends SelectionAdapter implements
			ModifyListener {
		public void modifyText(final ModifyEvent e) {
			updateLaunchConfigurationDialog();
			phpsComboBlock.setProject(getFileProject(debugFileTextField
					.getText()));
		}

		public void widgetSelected(final SelectionEvent e) {
			setDirty(true);
			final Object source = e.getSource();

			if (source == fileLocationButton)
				handleFileLocationButtonSelected();
			else if (source == argumentVariablesButton)
				handleChangeFileToDebug(debugFileTextField);
			else if (source == breakOnFirstLine)
				handleBreakButtonSelected();
		}
	}

	public final static String FIRST_EDIT = "editedByPHPExecutableLaunchTab"; //$NON-NLS-1$

	private Text debugFileTextField;
	private Button argumentVariablesButton;

	protected Button breakOnFirstLine;

	private boolean enableDebugInfoOption;
	protected boolean enableFileSelection;
	protected boolean enableBreakpointSelection;

	// Selection changed listener (checked PHP exe)
	protected final IPropertyChangeListener fPropertyChangeListener = new IPropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent event) {
			if (!DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID
					.equals(phpsComboBlock.getSelectedDebuggerId())) {
				setEnableDebugInfoOption(false);
			} else {
				setEnableDebugInfoOption(true);
			}
			handleSelectedPHPexeChanged();
		}
		// public void selectionChanged(SelectionChangedEvent event) {
		//

		// }
	};

	private Button fileLocationButton;

	protected WidgetListener fListener = new WidgetListener();
	private Text locationField;
	protected NewPHPsComboBlock phpsComboBlock;
	private Button runWithDebugInfo;

	protected SelectionAdapter selectionAdapter;

	public PHPExecutableLaunchTab() {
		enableFileSelection = true;
		enableBreakpointSelection = true;
		phpsComboBlock = new NewPHPsComboBlock();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#activated(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void activated(final ILaunchConfigurationWorkingCopy workingCopy) {
	}

	/*
	 * Fix for Bug 60163 Accessibility: New Builder Dialog missing object info
	 * for textInput controls
	 */
	public void addControlAccessibleListener(final Control control,
			final String controlName) {
		// strip mnemonic (&)
		final String[] strs = controlName.split("&"); //$NON-NLS-1$
		final StringBuffer stripped = new StringBuffer();
		for (String element : strs)
			stripped.append(element);
		control.getAccessible().addAccessibleListener(
				new ControlAccessibleListener(stripped.toString()));
	}

	/**
	 * Creates the controls needed to edit the argument and prompt for argument
	 * attributes of an external tool
	 * 
	 * @param parent
	 *            the composite to create the controls in
	 */
	protected void createArgumentComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		final String groupName = PHPDebugUIMessages.PHP_File;
		group.setText(groupName);

		GridLayout layout = new GridLayout(3, false);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gd);
		group.setFont(parent.getFont());

		debugFileTextField = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		debugFileTextField.setLayoutData(gd);
		debugFileTextField.addModifyListener(fListener);
		addControlAccessibleListener(debugFileTextField, group.getText());

		argumentVariablesButton = createPushButton(group,
				PHPDebugUIMessages.Browse, null);
		gd = (GridData) argumentVariablesButton.getLayoutData();
		gd.horizontalSpan = 1;
		argumentVariablesButton.addSelectionListener(fListener);
		addControlAccessibleListener(argumentVariablesButton,
				argumentVariablesButton.getText()); // need to strip the
													// mnemonic from buttons
	}

	// In case this is a debug mode, display checkboxes to override the 'Break
	// on first line' attribute.
	private void createBreakControl(final Composite parent) {
		final String mode = getLaunchConfigurationDialog().getMode();
		if (ILaunchManager.DEBUG_MODE.equals(mode) && enableBreakpointSelection) {
			final Group group = new Group(parent, SWT.NONE);
			group.setText(PHPDebugUIMessages.Breakpoint_Group_Label);
			final GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			group.setLayout(layout);
			group.setLayoutData(gridData);
			breakOnFirstLine = createCheckButton(group,
					PHPDebugUIMessages.Breakpoint_Group_BreakAtFirstLine);
			breakOnFirstLine.addSelectionListener(fListener);

			if (!enableBreakpointSelection)
				setEnableBreakpointSelection(enableBreakpointSelection);
		}
	}

	public void createControl(final Composite parent) {
		if (getLaunchConfigurationDialog().getMode().equals(
				ILaunchManager.RUN_MODE))
			setEnableDebugInfoOption(true);

		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(
				parent, SWT.V_SCROLL | SWT.H_SCROLL);
		final Composite mainComposite = new Composite(scrolledCompositeImpl,
				SWT.NONE);
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

		if (enableFileSelection)
			createArgumentComponent(mainComposite);

		// Create the debug info component anyway to avoid problems when
		// applying the configuration.
		createDebugInfoComponent(mainComposite);
		runWithDebugInfo.setVisible(enableDebugInfoOption);

		createBreakControl(mainComposite);
		createVerticalSpacer(mainComposite, 1);

		Dialog.applyDialogFont(parent);

		// HELP
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(parent,
						IPHPHelpContextIds.LOCALLY_DEBUGGING_A_PHP_SCRIPT);
	}

	/**
	 * Creates the controls needed to edit the working directory attribute of an
	 * external tool
	 * 
	 * @param parent
	 *            the composite to create the controls in
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
	 * Creates the controls needed to edit the location attribute of an external
	 * tool
	 * 
	 * @param group
	 *            the composite to create the controls in
	 */
	protected void createLocationComponent(final Composite parent) {
		phpsComboBlock.createControl(parent);
		final Control control = phpsComboBlock.getControl();
		phpsComboBlock.addPropertyChangeListener(fPropertyChangeListener);
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		control.setLayoutData(gd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#deactivated(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void deactivated(final ILaunchConfigurationWorkingCopy workingCopy) {
	}

	protected PHPexeDescriptor getDefaultPHPexeDescriptor() {
		return null;
	}

	public String getName() {
		return PHPDebugUIMessages.PHPExecutableLaunchTab_2; 
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
		if (text != null)
			locationField.setText(text);

	}

	protected void handleSelectedPHPexeChanged() {
		updateLaunchConfigurationDialog();
	}

	/**
	 * A callback method when changing the file to debug via 'Browse'
	 */
	private void handleChangeFileToDebug(final Text textField) {
		final IResource resource = LaunchUtilities.getFileFromDialog(null,
				getShell(), LaunchUtil.getFileExtensions(),
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse
	 * .debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(final ILaunchConfiguration configuration) {
		updateLocation(configuration);
		// updateWorkingDirectory(configuration);
		if (enableDebugInfoOption)
			updateDebugInfoOption(configuration);
		if (enableFileSelection)
			updateArgument(configuration);
		// init the breakpoint settings
		try {
			if (breakOnFirstLine != null) {
				breakOnFirstLine.setSelection(configuration.getAttribute(
						IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
						PHPDebugPlugin.getStopAtFirstLine()));
			}
		} catch (final CoreException e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
		isValid(configuration);
		updatePHPFromConfig(configuration);
	}

	/**
	 * This method updates the jre selection from the
	 * <code>ILaunchConfiguration</code>
	 * 
	 * @param config
	 *            the config to update from
	 */
	protected void updatePHPFromConfig(ILaunchConfiguration config) {
		if (enableFileSelection) {
			phpsComboBlock.setProject(getFileProject(debugFileTextField
					.getText()));
		}
		try {
			String path = config.getAttribute(PHPRuntime.PHP_CONTAINER,
					(String) null);
			if (path != null) {
				phpsComboBlock.setPath(Path.fromPortableString(path));
			}
		} catch (CoreException e) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug
	 * .core.ILaunchConfiguration)
	 */
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		try {
			final String phpExe = launchConfig.getAttribute(
					IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, ""); //$NON-NLS-1$
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
				final String phpFile = launchConfig.getAttribute(
						IPHPDebugConstants.ATTR_FILE, ""); //$NON-NLS-1$

				if (FileUtils.resourceExists(phpFile)) {
					IResource fileToData = ResourcesPlugin.getWorkspace()
							.getRoot().findMember(phpFile);
					// check if not a file (project, folder etc.)
					if ((fileToData.getType() != IResource.FILE)
							|| !PHPToolkitUtil.isPhpFile((IFile) fileToData)) {
						setErrorMessage(phpFile
								+ PHPDebugUIMessages.PHPExecutableLaunchTab_isNotPHPFile);
						return false;
					}
					// if valid PHP file, update text field data
					else {
						String dataLocation = ""; //$NON-NLS-1$
						if (fileToData.getLocation() == null) {
							dataLocation = fileToData.getFullPath().toString();
						} else {
							dataLocation = fileToData.getLocation()
									.toOSString();
						}
						debugFileTextField.setData(dataLocation);
					}
				} else if (new File(phpFile).exists()) {
					if (!PHPToolkitUtil.hasPhpExtention(phpFile)) {
						setErrorMessage(phpFile
								+ PHPDebugUIMessages.PHPExecutableLaunchTab_isNotPHPFile);
						return false;
					}
				} else { // resource DOES NOT exist
					setErrorMessage(PHPDebugUIMessages.PHP_File_Not_Exist);
					return false;
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
		final String debuggerID = phpsComboBlock.getSelectedDebuggerId();
		// Set the executable path
		final String selectedExecutable = phpsComboBlock
				.getSelectedExecutablePath();
		if (selectedExecutable.length() == 0) {
			configuration.setAttribute(
					IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, (String) null);
		} else {
			configuration.setAttribute(
					IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION,
					selectedExecutable);
		}
		// Set the PHP ini path
		final String iniPath = phpsComboBlock.getSelectedIniPath();
		if (iniPath.length() == 0) {
			configuration.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION,
					(String) null);
		} else {
			configuration.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION,
					iniPath);
		}

		configuration.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
				debuggerID);

		String arguments = null;
		if (!enableFileSelection
				|| (arguments = debugFileTextField.getText().trim()).length() == 0) {
			configuration.setAttribute(IPHPDebugConstants.ATTR_FILE,
					(String) null);
			configuration.setAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH,
					(String) null);
		} else {
			configuration.setAttribute(IPHPDebugConstants.ATTR_FILE, arguments);
			configuration.setAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH,
					debugFileTextField.getData().toString());
		}
		final boolean debugInfo = enableDebugInfoOption ? runWithDebugInfo != null
				&& runWithDebugInfo.getSelection()
				: true;
		configuration.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO,
				debugInfo);
		if (breakOnFirstLine != null)
			configuration.setAttribute(
					IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
					breakOnFirstLine.getSelection());
		applyLaunchDelegateConfiguration(configuration);
	}

	private IProject getFileProject(String phpFile) {
		if (FileUtils.resourceExists(phpFile)) {
			IResource fileToData = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(phpFile);
			return fileToData.getProject();
		}
		return null;
	}

	/**
	 * Apply the launch configuration delegate class that will be used when
	 * using this launch with the {@link PHPLaunchDelegateProxy}. This method
	 * sets the class name of the launch delegate that is associated with the
	 * debugger that was defined to this launch configuration. The class name is
	 * retrieved from the debugger's {@link IDebuggerConfiguration}.
	 * 
	 * @param configuration
	 *            A ILaunchConfigurationWorkingCopy
	 */
	protected void applyLaunchDelegateConfiguration(
			final ILaunchConfigurationWorkingCopy configuration) {
		String debuggerID = null;
		try {
			debuggerID = configuration.getAttribute(
					PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
					PHPDebugPlugin.getCurrentDebuggerId());
			AbstractDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry
					.getDebuggerConfiguration(debuggerID);
			configuration.setAttribute(
					PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
					debuggerConfiguration.getScriptLaunchDelegateClass());
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		try {
			String executableLocation = configuration.getAttribute(
					IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, ""); //$NON-NLS-1$
			if (executableLocation.equals("")) { //$NON-NLS-1$
				PHPexes phpExes = PHPexes.getInstance();
				final PHPexeItem phpExeItem = phpExes
						.getDefaultItem(PHPDebugPlugin.getCurrentDebuggerId());
				if (phpExeItem == null)
					return;
				executableLocation = phpExeItem.getExecutable().toString();
				configuration.setAttribute(
						IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION,
						executableLocation);

				String iniPath = phpExeItem.getINILocation() != null ? phpExeItem
						.getINILocation().toString() : null;
				configuration.setAttribute(
						IPHPDebugConstants.ATTR_INI_LOCATION, iniPath);

				configuration.setAttribute(
						IDebugParametersKeys.FIRST_LINE_BREAKPOINT,
						PHPDebugPlugin.getStopAtFirstLine());
				applyLaunchDelegateConfiguration(configuration);
			}
		} catch (final CoreException e) {
			Logger.log(Logger.ERROR, "Error setting default configuration", e); //$NON-NLS-1$
		}
		return;
	}

	public void setEnableDebugInfoOption(final boolean enabled) {
		if (enabled == enableDebugInfoOption)
			return;
		// Make sure that the debug-info-option can be true only when we are in
		// a RUN_MODE.
		if (!getLaunchConfigurationDialog().getMode().equals(
				ILaunchManager.RUN_MODE)) {
			enableDebugInfoOption = false;
			return;
		}
		enableDebugInfoOption = enabled;
		if (runWithDebugInfo != null)
			runWithDebugInfo.setVisible(enabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	/*
	 * public Image getImage() { return
	 * ExternalToolsImages.getImage(IExternalToolConstants.IMG_TAB_MAIN); }
	 */

	public void setEnableFileSelection(final boolean enabled) {
		if (enabled == enableFileSelection)
			return;
		enableFileSelection = enabled;
		if (argumentVariablesButton != null)
			argumentVariablesButton.setVisible(enabled);
		if (debugFileTextField != null)
			debugFileTextField.setVisible(enabled);

	}

	public void setEnableBreakpointSelection(final boolean enabled) {
		if (enabled == enableBreakpointSelection)
			return;
		enableBreakpointSelection = enabled;
		if (breakOnFirstLine != null) {
			breakOnFirstLine.setSelection(enabled);
			breakOnFirstLine.setEnabled(enabled);
		}
	}

	/**
	 * Updates the argument widgets to match the state of the given launch
	 * configuration.
	 */
	protected void updateArgument(final ILaunchConfiguration configuration) {
		String arguments = ""; //$NON-NLS-1$
		String fullPath = ""; //$NON-NLS-1$
		try {
			arguments = configuration.getAttribute(
					IPHPDebugConstants.ATTR_FILE, ""); //$NON-NLS-1$
			fullPath = configuration.getAttribute(
					IPHPDebugConstants.ATTR_FILE_FULL_PATH, ""); //$NON-NLS-1$
		} catch (final CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		if (debugFileTextField != null) {
			debugFileTextField.setText(arguments);
			debugFileTextField.setData(fullPath);
		}
	}

	/**
	 * Updates the "Run With Debug Option" to match the state of the given
	 * launch configuration.
	 */
	protected void updateDebugInfoOption(
			final ILaunchConfiguration configuration) {

		boolean runOption = PHPDebugPlugin.getDebugInfoOption();
		try {
			runOption = configuration.getAttribute(
					IPHPDebugConstants.RUN_WITH_DEBUG_INFO, runOption);
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
		String iniPath = ""; //$NON-NLS-1$
		String debuggerID = ""; //$NON-NLS-1$
		try {
			location = configuration.getAttribute(
					IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, ""); //$NON-NLS-1$
			iniPath = configuration.getAttribute(
					IPHPDebugConstants.ATTR_INI_LOCATION, ""); //$NON-NLS-1$
			debuggerID = configuration.getAttribute(
					PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID,
					PHPDebugPlugin.getCurrentDebuggerId());
		} catch (final CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		final PHPexes exes = PHPexes.getInstance();
		PHPexeItem phpexe;
		if (location != null && location.length() > 0) {
			phpexe = exes.getItemForFile(location, iniPath);
		} else {
			phpexe = exes.getDefaultItem(PHPDebugPlugin.getCurrentDebuggerId());
		}
		// Check that this debugger still exists. If not, take the defaults.
		if (!PHPDebuggersRegistry.getDebuggersIds().contains(debuggerID)) {
			debuggerID = PHPDebugPlugin.getCurrentDebuggerId();
			phpexe = exes.getDefaultItem(debuggerID);
		}

		if (!DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID.equals(debuggerID)) {
			setEnableDebugInfoOption(false);
		}
		// phpsComboBlock.setDebugger(debuggerID);
		// phpsComboBlock.setPHPexe(phpexe);
	}
}
