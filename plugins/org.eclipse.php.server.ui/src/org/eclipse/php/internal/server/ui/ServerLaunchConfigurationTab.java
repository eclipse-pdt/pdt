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
package org.eclipse.php.internal.server.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.launching.LaunchUtilities;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

public class ServerLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	protected IFile selectedFile = null;
	protected Text fFile;
	protected Label fURLLabel;
	protected Text fURLHost;
	protected Text fURLPath;
	protected Button projectButton;
	protected Button fileButton;
	protected Button publish;
	protected Button createNewServer;
	protected Button configureServers;
	protected Button autoGeneratedURL;
	protected String[] serverTypeIds;
	protected Combo serverCombo;
	protected Server server;
	protected boolean serverCanPublish = false;
	// list of servers that are in combo
	protected List<Server> servers;
	protected boolean init = true;
	// flag to be used to decide whether to enable combo in launch config dialog
	// after the user requests a launch, they cannot change it
	private static final String READ_ONLY = "read-only"; //$NON-NLS-1$

	// flag to be used to decide whether to enable all components related
	// directly to the server attributes (file, URL, server combo, new server
	// button)
	private static final String SERVER_ENABLED = "serverEnabled"; //$NON-NLS-1$
	/**
	 * Indicates that the URL field is auto-generated according to the user
	 * Server and resource selections.
	 */
	public static final String AUTO_GENERATED_URL = "auto_generated_url"; //$NON-NLS-1$
	protected WidgetListener fListener = new WidgetListener();
	private boolean saveWorkingCopy;
	private String basePath;

	/**
	 * Create a new server launch configuration tab.
	 */
	public ServerLaunchConfigurationTab() {
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		ScrolledCompositeImpl scrolledComposite = new ScrolledCompositeImpl(parent, SWT.V_SCROLL);
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createServerControl(composite);
		createFileComponent(composite);
		createURLControl(composite);
		createExtensionControls(composite);

		Dialog.applyDialogFont(composite);
		scrolledComposite.setContent(composite);
		Point size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(size.x, size.y);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.DEBUGGING_A_PHP_WEB_PAGE);
		setControl(scrolledComposite);
	}

	public void createExtensionControls(Composite composite) {
		return;
	}

	public void createURLControl(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		String projectLabel = PHPServerUIMessages.getString("ServerTab.url"); //$NON-NLS-1$
		group.setText(projectLabel);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		autoGeneratedURL = new Button(group, SWT.CHECK);
		autoGeneratedURL.setText(PHPServerUIMessages.getString("ServerTab.autoGenerate")); //$NON-NLS-1$
		autoGeneratedURL.setSelection(true);
		autoGeneratedURL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		autoGeneratedURL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				initializeURLControl();
				updateLaunchConfigurationDialog();
			}
		});

		fURLLabel = new Label(group, SWT.NONE);
		fURLLabel.setText(PHPServerUIMessages.getString("ServerTab.urlLabel")); //$NON-NLS-1$
		GridData gridData = new GridData();
		gridData.horizontalIndent = 20;
		gridData.horizontalSpan = 1;
		fURLLabel.setLayoutData(gridData);

		Composite urlComposite = new Composite(group, SWT.NONE);
		urlComposite.setLayout(new GridLayout(2, false));
		urlComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fURLHost = new Text(urlComposite, SWT.SINGLE | SWT.BORDER);
		fURLHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fURLHost.setEnabled(false);

		fURLPath = new Text(urlComposite, SWT.SINGLE | SWT.BORDER);
		fURLPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fURLPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	protected void createServerSelectionControl(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPServerUIMessages.getString("ServerTab.server")); //$NON-NLS-1$
		GridLayout ly = new GridLayout(1, false);
		ly.marginHeight = 0;
		ly.marginWidth = 0;
		group.setLayout(ly);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite phpServerComp = new Composite(group, SWT.NONE);
		phpServerComp.setLayout(new GridLayout(4, false));
		phpServerComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		phpServerComp.setFont(parent.getFont());

		Label label = new Label(phpServerComp, SWT.WRAP);
		GridData data = new GridData(GridData.BEGINNING);
		data.widthHint = 100;
		label.setLayoutData(data);
		label.setFont(parent.getFont());
		label.setText(PHPServerUIMessages.getString("ServerLaunchConfigurationTab.0")); //$NON-NLS-1$

		serverCombo = new Combo(phpServerComp, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		serverCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleServerSelection();
			}
		});

		createNewServer = createPushButton(phpServerComp, PHPServerUIMessages.getString("ServerTab.new"), null); //$NON-NLS-1$
		createNewServer.addSelectionListener(fListener);

		configureServers = createPushButton(phpServerComp, PHPServerUIMessages.getString("ServerTab.configure"), null); //$NON-NLS-1$
		configureServers.addSelectionListener(fListener);

		servers = new ArrayList<Server>();
		populateServerList(servers);

		// initialize the servers list
		if (!servers.isEmpty()) {
			for (int i = 0; i < servers.size(); i++) {
				Server svr = servers.get(i);
				serverCombo.add(svr.getName());
			}
		}

		// select first item in list
		if (serverCombo.getItemCount() > 0) {
			serverCombo.select(0);
		}

		serverCombo.forceFocus();
	}

	protected void populateServerList(List<Server> serverList) {
		Server[] servers = ServersManager.getServers();

		if (serverList == null)
			serverList = new ArrayList<Server>();

		if (servers != null) {
			int size = servers.length;
			for (int i = 0; i < size; i++) {
				serverList.add(servers[i]);
			}
		}
	}

	public void createServerControl(Composite parent) {
		createServerSelectionControl(parent);
		handleServerSelection();
	}

	protected void createFileComponent(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		String projectLabel = PHPServerUIMessages.getString("ServerTab.file_project"); //$NON-NLS-1$
		group.setText(projectLabel);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fFile = new Text(group, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		fFile.setLayoutData(gd);
		fFile.addModifyListener(fListener);

		fileButton = createPushButton(group, PHPServerUIMessages.getString("ServerTab.browse"), null); //$NON-NLS-1$
		gd = (GridData) fileButton.getLayoutData();
		gd.horizontalSpan = 1;
		fileButton.addSelectionListener(fListener);

		handleServerSelection();
	}

	public String[] getRequiredNatures() {
		return null;
	}

	protected void handleServerButtonSelected() {
		final Server newServer = getServerFromWizard();
		if (newServer != null) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					servers.add(newServer);
					serverCombo.add(newServer.getName());
					serverCombo.select(serverCombo.indexOf(newServer.getName()));
					handleServerSelection();
				}
			});
		}
	}

	protected void handleConfigureButtonSelected() {
		int selectionIndex = serverCombo.getSelectionIndex();
		Server server = servers.get(selectionIndex);
		String serverName = server.getName();
		NullProgressMonitor monitor = new NullProgressMonitor();
		if (ServerEditWizardRunner.runWizard(server) == Window.CANCEL) {
			monitor.setCanceled(true);
			return;
		}
		ServersManager.save();
		String newName = server.getName();
		if (!newName.equals(serverName)) {
			serverCombo.remove(selectionIndex);
			serverCombo.add(newName, selectionIndex);
			serverCombo.select(selectionIndex);
		}
		saveWorkingCopy = true;
		handleServerSelection();
	}

	public String[] getFileExtensions() {
		return null;
	}

	private IResource getFileFromDialog(IProject project) {
		return LaunchUtilities.getFileFromDialog(project, getShell(), getFileExtensions(), getRequiredNatures(), false);
	}

	protected void handleFileButtonSelected() {

		IResource file = null;
		file = getFileFromDialog(null);

		if (file == null)
			return;

		String fName = file.getFullPath().toString();
		fFile.setText(fName);

		if (autoGeneratedURL.getSelection()) {
			updateURLComponents(computeURL(formatFileName(fName)));
		}
	}

	private String getBasePath(IProject project) {
		return initializeBasePath(project);
	}

	protected void updateURLComponents(String urlStr) {
		try {
			URL url = new URL(urlStr);
			String port = url.getPort() == -1 ? "" : ":" + url.getPort(); //$NON-NLS-1$ //$NON-NLS-2$
			fURLHost.setText(url.getProtocol() + "://" + url.getHost() + port + "/"); //$NON-NLS-1$ //$NON-NLS-2$
			if (url.getQuery() != null) {
				fURLPath.setText(url.getPath() + "?" + url.getQuery()); //$NON-NLS-1$
			} else {
				fURLPath.setText(url.getPath());
			}
		} catch (MalformedURLException e) {
			Logger.logException(e);
		}
	}

	protected IProject getProject(String name) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = workspaceRoot.getProjects();

		if (projects == null || projects.length == 0)
			return null;

		for (IProject project : projects) {
			if (project.getName().equals(name))
				return project;
		}

		return null;
	}

	/**
	 * Called when a server is selected. This method should not be called
	 * directly.
	 */
	protected void handleServerSelection() {
		int numEntries = serverCombo.getItemCount();
		int index = serverCombo.getSelectionIndex();

		if (!servers.isEmpty()) {
			Object obj = servers.get(serverCombo.getSelectionIndex());
			if (obj != null && obj instanceof Server) {
				server = servers.get(serverCombo.getSelectionIndex());
				initializeURLControl();
			}
		}

		if (server == null && (index + 1) != numEntries)
			setErrorMessage(PHPServerUIMessages.getString("ServerTab.noSelectedServerError")); //$NON-NLS-1$
		else {
			setErrorMessage(null);
		}
		updateLaunchConfigurationDialog();
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		setErrorMessage(null);
		try {
			selectDefaultServer(configuration);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		serverCombo.setEnabled(true);
		// remove error message that other instances may have set
		setErrorMessage(null);

		try {
			String fileName = configuration.getAttribute(Server.FILE_NAME, ""); //$NON-NLS-1$
			String url = configuration.getAttribute(Server.BASE_URL, ""); //$NON-NLS-1$
			boolean isAutoGeneratedURL = configuration.getAttribute(AUTO_GENERATED_URL, true);

			initializeServerControl(configuration);

			if (isAutoGeneratedURL) {
				autoGeneratedURL.setSelection(true);
				String computedURL = computeURL(formatFileName(fileName));
				fURLLabel.setEnabled(false);
				updateURLComponents(computedURL);
				fURLPath.setEnabled(false);
			} else {
				autoGeneratedURL.setSelection(false);
				fURLLabel.setEnabled(true);
				updateURLComponents(url);
				fURLPath.setEnabled(true);
			}

			fFile.setText(fileName);

			updateFileEnabled(configuration);
			updateURLEnabled(configuration);
		} catch (CoreException e) {
			// ignore
		}

		initializeExtensionControls(configuration);

		isValid(configuration);
	}

	private void updateURLEnabled(ILaunchConfiguration configuration) throws CoreException {
		boolean enabled = configuration.getAttribute(SERVER_ENABLED, true);
		fURLLabel.getParent().setVisible(enabled);
		if (enabled) {
			fURLPath.setEnabled(!autoGeneratedURL.getSelection());
		}
	}

	private void updateFileEnabled(ILaunchConfiguration configuration) throws CoreException {
		boolean enabled = configuration.getAttribute(SERVER_ENABLED, true);
		fFile.getParent().setVisible(enabled);
	}

	private String initializeBasePath(IProject project) {
		if (project == null)
			return null;
		return PHPProjectPreferences.getDefaultBasePath(project);

	}

	protected void initializeExtensionControls(ILaunchConfiguration configuration) {
		return;
	}

	/**
	 * Constructs the URL string according to the given context root and the
	 * file name.
	 * 
	 * @param fileName
	 * @return
	 */
	protected String computeURL(String fileName) {
		if (server == null) {
			return ""; //$NON-NLS-1$
		}
		String urlString = server.getBaseURL();

		if (urlString.equals("")) { //$NON-NLS-1$
			urlString = "http://localhost"; //$NON-NLS-1$
		}
		StringBuffer url = new StringBuffer(urlString);
		if (!fileName.equals("")) { //$NON-NLS-1$
			url.append(fileName);
		}
		return url.toString();
	}

	private String formatFileName(String fileName) {
		String formatFile = null;

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(fileName);

		if (resource == null) {
			return fileName;
		}

		basePath = getBasePath(resource.getProject());
		if (basePath == null && resource.getProject() != null)
			basePath = resource.getProject().getName();
		else if (basePath == null && resource.getProject() == null) {
			basePath = ""; //$NON-NLS-1$
		}

		int type = resource.getType();
		if (type == IResource.FILE || type == IResource.FOLDER) {

			formatFile = basePath + "/" //$NON-NLS-1$
					+ resource.getFullPath().removeFirstSegments(1).toString();
		} else {
			formatFile = basePath + "/"; //$NON-NLS-1$
		}
		if (!formatFile.startsWith("/")) { //$NON-NLS-1$
			formatFile = basePath + "/" + formatFile; //$NON-NLS-1$
		}
		return formatFile;
	}

	protected void initializeURLControl() {
		if (fFile == null || fURLPath == null || fURLHost == null) {
			return;
		}
		String file;
		if (autoGeneratedURL.getSelection()) {
			file = formatFileName(fFile.getText());
		} else {
			file = fURLPath.getText();
		}
		updateURLComponents(computeURL(file));
	}

	protected void initializeServerControl(ILaunchConfiguration configuration) {
		try {
			String serverName = configuration.getAttribute(Server.NAME, ""); //$NON-NLS-1$
			if (serverName != null && !serverName.equals("")) { //$NON-NLS-1$
				server = ServersManager.getServer(serverName);
				if (server == null) { // server no longer exists
					setErrorMessage(PHPServerUIMessages.getString("ServerTab.invalidServerError")); //$NON-NLS-1$
					selectDefaultServer(configuration);
				} else {
					serverCombo.setText(server.getName());
				}
			} else {
				selectDefaultServer(configuration);
			}
			// flag should only be set if launch has been attempted on the
			// config
			if (configuration.getAttribute(READ_ONLY, false)) {
				serverCombo.setEnabled(false);
			}
			boolean enabled = configuration.getAttribute(SERVER_ENABLED, true);
			serverCombo.setEnabled(enabled);
			createNewServer.setEnabled(enabled);
		} catch (Exception e) {
		}
	}

	/*
	 * Select the default server.
	 */
	private void selectDefaultServer(ILaunchConfiguration configuration) throws CoreException {
		if (serverCombo != null && serverCombo.getItemCount() > 0) {
			// Select the default server
			String projectName = configuration.getAttribute(IPHPDebugConstants.PHP_Project, (String) null);
			IProject project = null;
			if (projectName != null) {
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			}
			Server defaultServer = ServersManager.getDefaultServer(project);
			int nameIndex = serverCombo.indexOf(defaultServer.getName());
			if (nameIndex > -1) {
				serverCombo.select(nameIndex);
			} else {
				serverCombo.select(0);
			}
			server = ServersManager.getServer(serverCombo.getText());
		}
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (server != null) {
			configuration.setAttribute(Server.NAME, server.getName());
		} else {
			configuration.setAttribute(Server.NAME, (String) null);
		}
		String fileName = fFile.getText();

		String urlPath = fURLPath.getText().replace('\\', '/');
		if (urlPath.startsWith("/")) { //$NON-NLS-1$
			urlPath = urlPath.substring(1);
		}
		String url = fURLHost.getText() + urlPath;
		configuration.setAttribute(Server.FILE_NAME, fileName);
		configuration.setAttribute(Server.BASE_URL, url);
		configuration.setAttribute(AUTO_GENERATED_URL, autoGeneratedURL.getSelection());

		try {
			updateURLEnabled(configuration);
		} catch (CoreException e) {
			PHPDebugPlugin.log(e);
		}

		applyExtension(configuration);

		if (saveWorkingCopy) {
			try {
				configuration.doSave();
			} catch (CoreException e) {
			}
			saveWorkingCopy = false;
		}
	}

	protected void applyExtension(ILaunchConfigurationWorkingCopy configuration) {
		return;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#isValid(ILaunchConfiguration)
	 */
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setMessage(null);
		setErrorMessage(null);
		// TODO - Note that this method of removing invalid launches is still
		// buggy
		// In order to fix it completely, we might have to perform similar
		// checks when removing or renaming a server from
		// the PHP Servers preferences page.
		try {
			String serverName = launchConfig.getAttribute(Server.NAME, ""); //$NON-NLS-1$
			if (!serverName.equals("")) { //$NON-NLS-1$
				Server server = ServersManager.getServer(serverName);
				if (server == null) {
					deleteLaunchConfiguration(launchConfig);
					ILaunchConfiguration[] allConfigurations = DebugPlugin.getDefault().getLaunchManager()
							.getLaunchConfigurations();
					for (ILaunchConfiguration element : allConfigurations) {
						launchConfig = element;
						serverName = launchConfig.getAttribute(Server.NAME, ""); //$NON-NLS-1$
						if (!serverName.equals("") && ServersManager.getServer(serverName) == null) { //$NON-NLS-1$
							// The server was removed, so remove this launch
							// configuration!
							deleteLaunchConfiguration(launchConfig);
						}
					}
					return false;
				} else {
					if (ServersManager.isNoneServer(server)) {
						setErrorMessage(PHPServerUIMessages.getString("ServerTab.noSelectedServerError")); //$NON-NLS-1$
						return false;
					}
				}
			}

			String fileName = launchConfig.getAttribute(Server.FILE_NAME, ""); //$NON-NLS-1$
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(fileName);
			if (!(resource instanceof IFile)) {
				setErrorMessage(PHPServerUIMessages.getString("ServerTab.file_project_doesNotExist")); //$NON-NLS-1$
				return false;
			}

		} catch (CoreException e) {
			// ignore
		}

		return isValidExtension(launchConfig);
	}

	private void deleteLaunchConfiguration(final ILaunchConfiguration launchConfig) throws CoreException {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				ILaunchConfiguration config = launchConfig;
				try {
					if (config instanceof ILaunchConfigurationWorkingCopy) {
						config = ((ILaunchConfigurationWorkingCopy) config).getOriginal();
					}
					if (config != null) {
						config.delete();
					}
				} catch (CoreException ce) {
					// Ignore
				}
			}
		});

	}

	protected boolean isValidExtension(ILaunchConfiguration launchConfig) {
		return true;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return ServersPluginImages.get(ServersPluginImages.IMG_SERVER);
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return PHPServerUIMessages.getString("ServerTab.server"); //$NON-NLS-1$
	}

	/*
	 * Fix for Bug 60163 Accessibility: New Builder Dialog missing object info
	 * for textInput controls
	 */
	public void addControlAccessibleListener(Control control, String controlName) {
		// strip mnemonic (&)
		String[] strs = controlName.split("&"); //$NON-NLS-1$
		StringBuffer stripped = new StringBuffer();
		for (String element : strs) {
			stripped.append(element);
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

	protected Server getServerFromWizard() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		NullProgressMonitor monitor = new NullProgressMonitor();
		Server theServer = null;

		ServerWizard wizard = new ServerWizard();
		ClosableWizardDialog dialog = new ClosableWizardDialog(shell, wizard);
		if (dialog.open() == Window.CANCEL) {
			monitor.setCanceled(true);
			return null;
		}
		theServer = (Server) wizard.getRootFragment().getWizardModel().getObject(WizardModel.SERVER);
		if (theServer != null) {
			ServersManager.addServer(theServer);
			ServersManager.save();
		}
		return theServer;
	}

	protected class WidgetListener extends SelectionAdapter implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
			initializeURLControl();
		}

		public void widgetSelected(SelectionEvent e) {
			setDirty(true);
			Object source = e.getSource();

			if (source == fileButton) {
				handleFileButtonSelected();
			} else if (source == createNewServer) {
				handleServerButtonSelected();
			} else if (source == configureServers) {
				handleConfigureButtonSelected();
			}
		}
	}
}