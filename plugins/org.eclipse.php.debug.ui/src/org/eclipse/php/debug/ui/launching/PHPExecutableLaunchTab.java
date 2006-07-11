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
import org.eclipse.php.debug.core.preferences.PHPexeItem;
import org.eclipse.php.debug.core.preferences.PHPexes;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
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
	public final static String FIRST_EDIT = "editedByPHPExecutableLaunchTab"; //$NON-NLS-1$

	private Text locationField;
	private Button fileLocationButton;
	private Button runWithDebugInfo;

	private Text argumentField;
	private Button argumentVariablesButton;

	protected SelectionAdapter selectionAdapter;

	protected PHPsComboBlock phpsComboBlock;

	private boolean enableFileSelection;
	private boolean enableDebugInfoOption;

	public PHPExecutableLaunchTab() {
		enableFileSelection = true;
	}

	public void setEnableFileSelection(boolean enabled) {
		if (enabled == enableFileSelection) {
			return;
		}
		this.enableFileSelection = enabled;
		if (argumentVariablesButton != null) {
			argumentVariablesButton.setVisible(enabled);
		}
		if (argumentField != null) {
			argumentField.setVisible(enabled);
		}
		
	}

	public void setEnableDebugInfoOption(boolean enabled) {
		if (enabled == enableDebugInfoOption) {
			return;
		}
		this.enableDebugInfoOption = true;
		if(runWithDebugInfo != null) {
			runWithDebugInfo.setVisible(enabled);
		}
	}

	// Selection changed listener (checked PHP exe)
	private ISelectionChangedListener fCheckListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			handleSelectedPHPexeChanged();
		}
	};
	protected WidgetListener fListener = new WidgetListener();

	protected class WidgetListener extends SelectionAdapter implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetSelected(SelectionEvent e) {
			setDirty(true);
			Object source = e.getSource();

			if (source == fileLocationButton) {
				handleFileLocationButtonSelected();
				//} else if (source == workspaceWorkingDirectoryButton) {
				//handleWorkspaceWorkingDirectoryButtonSelected();
			} else if (source == argumentVariablesButton) {
				handleVariablesButtonSelected(argumentField);
			}
		}
	}

	public void createControl(Composite parent) {
		if (getLaunchConfigurationDialog().getMode().equals(ILaunchManager.RUN_MODE)) {
			setEnableDebugInfoOption(true);
		}
		
		Composite mainComposite = new Composite(parent, SWT.NONE);
		setControl(mainComposite);
		mainComposite.setFont(parent.getFont());
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		mainComposite.setLayout(layout);
		mainComposite.setLayoutData(gridData);

		createLocationComponent(mainComposite);
		if (enableDebugInfoOption) {
			createDebugInfoComponent(mainComposite);
		}
		//createWorkDirectoryComponent(mainComposite);
		if (enableFileSelection) {
			createArgumentComponent(mainComposite);
		}
		createVerticalSpacer(mainComposite, 1);

		Dialog.applyDialogFont(parent);
		// TODO Auto-generated method stub

	}

	/**
	 * Creates the controls needed to edit the location
	 * attribute of an external tool
	 * 
	 * @param group the composite to create the controls in
	 */
	protected void createLocationComponent(Composite parent) {

		phpsComboBlock = new PHPsComboBlock();
		//		phpsComboBlock.setDefaultPHPexeDescriptor(getDefaultPHPexeDescriptor());
		phpsComboBlock.setSpecificPHPexeDescriptor(getSpecificPHPexeDescriptor());
		phpsComboBlock.createControl(parent);
		Control control = phpsComboBlock.getControl();
		phpsComboBlock.addSelectionChangedListener(fCheckListener);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		control.setLayoutData(gd);

		//		Group group = new Group(parent, SWT.NONE);
		//		String locationLabel = PHPDebugUIMessages.Location;
		//		group.setText(locationLabel);
		//		GridLayout layout = new GridLayout();
		//		layout.numColumns = 1;	
		//		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		//		group.setLayout(layout);
		//		group.setLayoutData(gridData);
		//		
		//		locationField = new Text(group, SWT.BORDER);
		//		gridData = new GridData(GridData.FILL_HORIZONTAL);
		//		gridData.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		//		locationField.setLayoutData(gridData);
		//		locationField.addModifyListener(fListener);
		//		addControlAccessibleListener(locationField, group.getText());
		//		
		//		Composite buttonComposite = new Composite(group, SWT.NONE);
		//		layout = new GridLayout();
		//		layout.marginHeight = 0;
		//        layout.marginWidth = 0;   
		//		layout.numColumns = 3;
		//		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		//		buttonComposite.setLayout(layout);
		//		buttonComposite.setLayoutData(gridData);
		//		buttonComposite.setFont(parent.getFont());
		//		
		//		fileLocationButton= createPushButton(buttonComposite, PHPDebugUIMessages.BrowseFilesystem, null); //$NON-NLS-1$
		//		fileLocationButton.addSelectionListener(fListener);
		//		addControlAccessibleListener(fileLocationButton, group.getText() + " " + fileLocationButton.getText()); //$NON-NLS-1$

	}

	protected PHPexeDescriptor getDefaultPHPexeDescriptor() {
		return null;
	}

	protected PHPexeDescriptor getSpecificPHPexeDescriptor() {
		return null;
	}

	protected void handleSelectedPHPexeChanged() {
		updateLaunchConfigurationDialog();
	}

	/**
	 * Creates the controls needed to edit the working directory
	 * attribute of an external tool
	 * 
	 * @param parent the composite to create the controls in
	 */

	protected void createDebugInfoComponent(Composite parent) {
		runWithDebugInfo = new Button(parent, SWT.CHECK);
		runWithDebugInfo.setText(PHPDebugUIMessages.PHPexe_Run_With_Debug_Info);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		runWithDebugInfo.setLayoutData(gd);

		runWithDebugInfo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				updateLaunchConfigurationDialog();
			}
		});

	}

	/**
	 * Return the String to use as the label for the working directory field.
	 * Subclasses may wish to override.
	 */
	protected String getWorkingDirectoryLabel() {
		return PHPDebugUIMessages.WorkingDirectory; //$NON-NLS-1$
	}

	/**
	 * Creates the controls needed to edit the argument and
	 * prompt for argument attributes of an external tool
	 *
	 * @param parent the composite to create the controls in
	 */
	protected void createArgumentComponent(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		String groupName = PHPDebugUIMessages.Arguments; //$NON-NLS-1$
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

		Composite composite = new Composite(group, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		composite.setLayout(layout);
		composite.setLayoutData(gridData);
		composite.setFont(parent.getFont());

		argumentVariablesButton = createPushButton(composite, PHPDebugUIMessages.Variables, null); //$NON-NLS-1$
		argumentVariablesButton.addSelectionListener(fListener);
		addControlAccessibleListener(argumentVariablesButton, argumentVariablesButton.getText()); // need to strip the mnemonic from buttons
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		return;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		updateLocation(configuration);
		//updateWorkingDirectory(configuration);
		if (enableDebugInfoOption) {
			updateDebugInfoOption(configuration);
		}
		if (enableFileSelection) {
			updateArgument(configuration);
		}
		isValid(configuration);
	}

	public String getName() {
		return "PHP Executable";
	}

	/**
	 * Updates the location widgets to match the state of the given launch
	 * configuration.
	 */
	protected void updateLocation(ILaunchConfiguration configuration) {
		String location = ""; //$NON-NLS-1$
		try {
			location = configuration.getAttribute(PHPCoreConstants.ATTR_LOCATION, ""); //$NON-NLS-1$
		} catch (CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		//		locationField.setText(location);
		PHPexes exes = phpsComboBlock.getPHPs(true);
		PHPexeItem phpexe;
		if (location != null && location.length() > 0)
			phpexe = exes.getItemForFile(location);
		else
			phpexe = exes.getDefaultItem();
		phpsComboBlock.setPHPexe(phpexe);
	}

	/**
	 * Updates the argument widgets to match the state of the given launch
	 * configuration.
	 */
	protected void updateArgument(ILaunchConfiguration configuration) {
		String arguments = ""; //$NON-NLS-1$
		try {
			arguments = configuration.getAttribute(PHPCoreConstants.ATTR_FILE, ""); //$NON-NLS-1$
		} catch (CoreException ce) {
			Logger.log(Logger.ERROR, "Error reading configuration", ce); //$NON-NLS-1$
		}
		argumentField.setText(arguments);
	}

	/**
	 * Updates the "Run With Debug Option" to match the state of the given launch
	 * configuration.
	 */
	protected void updateDebugInfoOption(ILaunchConfiguration configuration) {

		boolean runOption = PHPDebugPlugin.getDebugInfoOption();
		try {
			runOption = configuration.getAttribute(IPHPConstants.RunWithDebugInfo, runOption);
		} catch (CoreException e) {
			Logger.log(Logger.ERROR, "Error reading configuration", e); //$NON-NLS-1$
		}
		runWithDebugInfo.setSelection(runOption);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		//		String location= locationField.getText().trim();
		String location = phpsComboBlock.getSelectedLocation();
		if (location.length() == 0) {
			configuration.setAttribute(PHPCoreConstants.ATTR_LOCATION, (String) null);
		} else {
			configuration.setAttribute(PHPCoreConstants.ATTR_LOCATION, location);
		}

		String arguments = null;
		if (!enableFileSelection || (arguments = argumentField.getText().trim()).length() == 0) {
			configuration.setAttribute(PHPCoreConstants.ATTR_FILE, (String) null);
		} else {
			configuration.setAttribute(PHPCoreConstants.ATTR_FILE, arguments);
		}
		boolean debugInfo = enableDebugInfoOption ? runWithDebugInfo.getSelection() : true;
		configuration.setAttribute(IPHPConstants.RunWithDebugInfo, debugInfo);

	}

	private boolean fileExists(String projectPath) {
		if (projectPath == null || "".equals(projectPath)) {
			return false;
		}

		IPath p3 = new Path(projectPath);
		boolean file = ResourcesPlugin.getWorkspace().getRoot().exists(p3);
		if (file) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		try {
			String phpExe = launchConfig.getAttribute(PHPCoreConstants.ATTR_LOCATION, "");
			boolean phpExeExists = true;
			try {
				File file = new File(phpExe);
				if (!file.exists()) {
					phpExeExists = false;
				}
			} catch (NullPointerException e) {
				phpExeExists = false;
			}
			if (!phpExeExists) {
				setErrorMessage(PHPDebugUIMessages.PHP_Location_Message);
				return false;
			}

			if (enableFileSelection) {
				String phpFile = launchConfig.getAttribute(PHPCoreConstants.ATTR_FILE, "");
				if (!fileExists(phpFile)) {
					setErrorMessage(PHPDebugUIMessages.PHP_File_Not_Exist);
					return false;
				}
			}
		} catch (CoreException e) {
		}

		return true;
	}

	/**
	 * Prompts the user to choose a location from the filesystem and
	 * sets the location as the full path of the selected file.
	 */
	protected void handleFileLocationButtonSelected() {

		FileDialog fileDialog = new FileDialog(getShell(), SWT.NONE);
		fileDialog.setFileName(locationField.getText());

		String text = fileDialog.open();
		if (text != null) {
			locationField.setText(text);
		}

	}

	/**
	 * A variable entry button has been pressed for the given text
	 * field. Prompt the user for a variable and enter the result
	 * in the given field.
	 */
	private void handleVariablesButtonSelected(Text textField) {
		IFile file = null;
		IResource resource = ServerUtilities.getFileFromDialog(null, getShell(), LaunchUtil.getFileExtensions(), LaunchUtil.getRequiredNatures());
		if (resource instanceof IFile) {
			file = (IFile) resource;
		}
		if (file != null) {
			textField.setText(file.getFullPath().toString());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	/*
	 public Image getImage() {
	 return ExternalToolsImages.getImage(IExternalToolConstants.IMG_TAB_MAIN);
	 }
	 */

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#deactivated(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#activated(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
	}
	
	/*
	 * Fix for Bug 60163 Accessibility: New Builder Dialog missing object info for textInput controls
	 */
	public void addControlAccessibleListener(Control control, String controlName) {
		//strip mnemonic (&)
		String[] strs = controlName.split("&"); //$NON-NLS-1$
		StringBuffer stripped = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			stripped.append(strs[i]);
		}
		control.getAccessible().addAccessibleListener(new ControlAccessibleListener(stripped.toString()));
	}

	private class ControlAccessibleListener extends AccessibleAdapter {
		private String controlName;

		ControlAccessibleListener(String name) {
			controlName = name;
		}

		public void getName(AccessibleEvent e) {
			e.result = controlName;
		}

	}
}
