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
package org.eclipse.php.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.core.LaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationManager;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationPresentationManager;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationView;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.debug.internal.ui.preferences.LaunchConfigurationsPreferencePage;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.core.ServersManager;
import org.eclipse.php.server.internal.ui.ClosableWizardDialog;
import org.eclipse.php.server.internal.ui.ServerWizard;
import org.eclipse.php.server.ui.wizard.WizardModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ServerTab extends AbstractLaunchConfigurationTab {

	private static final String SERVERS_PREFERENCES_PAGE_ID = "org.eclipse.php.server.internal.ui.PHPServersPreferencePage"; //$NON-NLS-1$

	protected IFile selectedFile = null;

	protected Text fFile = null;
	protected Text fContextRoot = null;
	protected Text fURL = null;

	protected Button projectButton;
	protected Button fileButton;
	protected Button publish;
	protected Button createNewServer;
	protected Button configureServers;

	protected String[] serverTypeIds;

	protected Combo serverCombo;
	protected Server server;
	protected boolean serverCanPublish = false;

	// list of servers that are in combo
	protected List servers;

	protected boolean init = true;
	// flag to be used to decide whether to enable combo in launch config dialog
	// after the user requests a launch, they cannot change it
	private static final String READ_ONLY = "read-only";

	protected WidgetListener fListener = new WidgetListener();

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

	/**
	 * Create a new server launch configuration tab.
	 */
	public ServerTab() {
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 1;
		composite.setLayout(layout);

		createServerControl(composite);
		createFileComponent(composite);
		createContextRootControl(composite);
		createURLControl(composite);
		createExtensionControls(composite);

		Dialog.applyDialogFont(composite);
		setControl(composite);
	}

	public void createExtensionControls(Composite composite) {
		return;
	}

	public void createURLControl(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		String projectLabel = "URL";
		group.setText(projectLabel);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gridData);

		fURL = new Text(group, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fURL.setLayoutData(gd);
		fURL.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

	}

	public void createContextRootControl(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		String projectLabel = "Context Root";
		group.setText(projectLabel);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gridData);

		fContextRoot = new Text(group, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fContextRoot.setLayoutData(gd);
		//fProject.setFont(font);
		fContextRoot.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
				initializeURLControl();
			}
		});
	}

	protected void createServerSelectionControl(Composite parent) {
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(data);

		Group group = new Group(composite, SWT.NONE);
		//String projectLabel = "Context Root";
		group.setText("Server");
		GridLayout ly = new GridLayout();
		ly.numColumns = 3;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(ly);
		group.setLayoutData(gridData);

		serverCombo = new Combo(group, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		serverCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		serverCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				handleServerSelection();
			}
		});

		createNewServer = createPushButton(group, "New", null);
		createNewServer.addSelectionListener(fListener);

		configureServers = createPushButton(group, "Configure...", null);
		configureServers.addSelectionListener(fListener);

		servers = new ArrayList();
		populateServerList((ArrayList) servers);
		// initialize

		if (!servers.isEmpty()) {
			for (int i = 0; i < servers.size(); i++) {
				Server svr = (Server) servers.get(i);
				serverCombo.add(svr.getName());
			}
		}

		// select first item in list
		if (serverCombo.getItemCount() > 0)
			serverCombo.select(0);

		serverCombo.forceFocus();
	}

	protected void populateServerList(ArrayList serverList) {
		Server[] servers = ServersManager.getServers();

		if (serverList == null)
			serverList = new ArrayList();

		if (servers != null) {
			int size = servers.length;
			for (int i = 0; i < size; i++) {
				serverList.add(servers[i]);
			}
		}
	}

	public void createServerControl(Composite parent) {
		createServerSelectionControl(parent);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(data);

		publish = new Button(composite, SWT.CHECK);
		publish.setText("Publish to Server");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		publish.setLayoutData(gd);

		publish.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				updateLaunchConfigurationDialog();
			}
		});

		handleServerSelection();
	}

	protected void createFileComponent(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		String projectLabel = "File";
		group.setText(projectLabel);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(gridData);

		fFile = new Text(group, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		//gridData = new GridData();
		//gridData.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		fFile.setLayoutData(gridData);
		fFile.addModifyListener(fListener);
		addControlAccessibleListener(fFile, group.getText());

		fileButton = createPushButton(group, "Files...", null);
		fileButton.addSelectionListener(fListener);
		addControlAccessibleListener(fileButton, group.getText() + " " + fileButton.getText()); //$NON-NLS-1$
	}

	public String[] getRequiredNatures() {
		return null;
	}

	protected void handleServerButtonSelected() {
		Server newServer = getServerFromWizard();
		if (newServer != null) {
			servers.add(newServer);
			serverCombo.add(newServer.getName());
			serverCombo.select(serverCombo.indexOf(newServer.getName()));
			handleServerSelection();
			updateLaunchConfigurationDialog();
		}
	}

	protected void handleConfigureButtonSelected() {
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getShell(), SERVERS_PREFERENCES_PAGE_ID, null, null);
		dialog.open();
		int serverIndex = serverCombo.getSelectionIndex();
		Object obj = servers.get(serverIndex);
		if (obj instanceof Server) {
			Server server = (Server) obj;
			serverCombo.remove(serverIndex);
			serverCombo.add(server.getName(), serverIndex);
			serverCombo.select(serverIndex);
			handleServerSelection();
			updateLaunchConfigurationDialog();
		}
	}

	public String[] getFileExtensions() {
		return null;
	}

	private IResource getFileFromDialog(IProject project) {
		return ServerUtilities.getFileFromDialog(project, getShell(), getFileExtensions(), getRequiredNatures());
	}

	protected void handleFileButtonSelected() {

		IResource file = null;
		file = getFileFromDialog(null);

		if (file == null)
			return;

		String fName = file.getFullPath().toString();
		fFile.setText(fName);

		initializeURLControl(fContextRoot.getText(), fName);
	}

	protected IProject getProject(String name) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = workspaceRoot.getProjects();

		if (projects == null || projects.length == 0)
			return null;

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (project.getName().equals(name))
				return project;
		}

		return null;
	}

	private boolean isSupportedServer(String serverTypeId) {
		if (serverTypeIds == null)
			return true;
		int size = serverTypeIds.length;
		for (int i = 0; i < size; i++) {
			if (matches(serverTypeId, serverTypeIds[i]))
				return true;
		}
		return false;
	}

	private static boolean matches(String a, String b) {
		if (a == null || b == null || "*".equals(a) || "*".equals(b) || a.startsWith(b) || b.startsWith(a))
			return true;
		return false;
	}

	/**
	 * Called when a server is selected.
	 * This method should not be called directly.
	 */
	protected void handleServerSelection() {
		int numEntries = serverCombo.getItemCount();
		int index = serverCombo.getSelectionIndex();

		if (!servers.isEmpty()) {
			Object obj = servers.get(serverCombo.getSelectionIndex());
			if (obj != null && obj instanceof Server) {
				server = (Server) servers.get(serverCombo.getSelectionIndex());

				//				ApacheServer apacheServer = (ApacheServer) server.getAdapter(ApacheServer.class);
				//				if (apacheServer == null)
				//					apacheServer = (ApacheServer) server.loadAdapter(ApacheServer.class, null);

				boolean canPublish = false;
				if (server != null)
					canPublish = server.canPublish();

				serverCanPublish = canPublish;
				publish.setSelection(canPublish);
				publish.setEnabled(canPublish);

				initializeURLControl();
			}
		}

		if (server == null && (index + 1) != numEntries)
			setErrorMessage("No server selected");
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
		if (serverCombo != null) {
			serverCombo.setEnabled(true);
			if (serverCombo.getItemCount() > 0)
				serverCombo.select(0);
		}

		//		if (servers != null) { // TODO - ?
		//			server = (Server) servers.get(serverCombo.getSelectionIndex());
		//			if (server != null)
		//				server.setupLaunchConfiguration(configuration, null);
		//		}
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		serverCombo.setEnabled(true);
		//remove error message that other instances may have set
		setErrorMessage(null);

		try {
			String fileName = configuration.getAttribute(Server.FILE_NAME, "");
			String contextRoot = configuration.getAttribute(Server.CONTEXT_ROOT, "");
			boolean deployable = configuration.getAttribute(Server.PUBLISH, false);
			String url = configuration.getAttribute(Server.BASE_URL, "");

			if (deployable) {
				publish.setSelection(true);
			}
			if (fileName.equals("") && Activator.currentSelection != null && !Activator.currentSelection.isEmpty()) {

				IStructuredSelection sel = Activator.currentSelection;
				// TODO - ?
				//				IModuleArtifact moduleArtifact = ServerPlugin.getModuleArtifact(sel.getFirstElement());
				//
				//				if (moduleArtifact instanceof WebResource) {
				//					WebResource webResource = (WebResource) moduleArtifact;
				//					IModule module = webResource.getModule();
				//
				//					if (module != null) {
				//						IProject proj = module.getProject();
				//
				//						if (proj != null) {
				//							IPath filePath = webResource.getPath();
				//
				//							if (filePath.isEmpty()) {
				//								fileName = proj.getFullPath().toString();
				//							} else {
				//								IFile file = proj.getFile(filePath);
				//								fileName = file.getFullPath().toString();
				//							}
				//
				//							contextRoot = proj.getName();
				//						}
				//					}
				//				}
			}

			initializeServerControl(configuration);

			fFile.setText(fileName);
			fContextRoot.setText(contextRoot);

			if (url.equals("")) {
				initializeURLControl(contextRoot, fileName);
			} else {
				fURL.setText(url);
			}
		} catch (CoreException e) {
			// ignore
		}

		initializeExtensionControls(configuration);
	}

	protected void initializeExtensionControls(ILaunchConfiguration configuration) {
		return;
	}

	//
	//	protected IModuleArtifact getModuleArtifact(String fileName, String projectName) {
	//		IModuleArtifact moduleArtifact = null;
	//
	//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	//		IProject project = root.getProject(projectName);
	//		if (project == null) {
	//			return null;
	//		}
	//
	//		if (fileName != null && !(fileName.equals(""))) {
	//			IFile file = project.getFile(fileName);
	//			moduleArtifact = ServerPlugin.getModuleArtifact(file);
	//		} else {
	//			moduleArtifact = ServerPlugin.getModuleArtifact(project);
	//		}
	//		return moduleArtifact;
	//	}

	protected void initializeURLControl(String contextRoot, String fileName) {
		if (server == null) {
			return;
		}
		//		Server as = (Server) server.getAdapter(ApacheServer.class);
		//		if (as == null) {
		//			as = (ApacheServer) server.loadAdapter(ApacheServer.class, null);
		//		}
		String urlString = server.getBaseURL();

		if (urlString.equals("")) {
			urlString = "http://localhost";
		}
		StringBuffer url = new StringBuffer(urlString);

		if (!contextRoot.equals("")) {
			url.append("/");
			url.append(contextRoot);
		}
		if (!fileName.equals("")) {
			url.append(formatFileName(fileName));
		}
		fURL.setText(url.toString());
	}

	private String formatFileName(String fileName) {
		String formatFile = null;

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(fileName);

		if (resource == null) {
			return "";
		}
		int type = resource.getType();
		if (type == IResource.FILE || type == IResource.FOLDER) {
			formatFile = resource.getProjectRelativePath().toString();
		} else if (resource.getType() == IResource.PROJECT) {
			formatFile = "/";
		}
		if (!formatFile.startsWith("/")) {
			formatFile = "/" + formatFile;
		}
		return formatFile;
	}

	protected void initializeURLControl() {
		if (fContextRoot == null || fFile == null) {
			return;
		}
		initializeURLControl(fContextRoot.getText(), fFile.getText());
	}

	protected void initializeServerControl(ILaunchConfiguration configuration) {
		try {
			String serverName = configuration.getAttribute(Server.NAME, "");
			if (serverName != null && !serverName.equals("")) {
				server = ServersManager.getServer(serverName);

				if (server == null) { //server no longer exists				
					setErrorMessage("Invalid server");
					//serverCombo.clearSelection();  //appears to be broken...doesn't work with read only?												
					serverCombo.setEnabled(false);
					return;
				}

				serverCombo.setText(server.getName());
				//if (server.getServerState() != IServer.STATE_STOPPED)
				//	setErrorMessage(Messages.errorServerAlreadyRunning);
			} else {
				if (serverCombo.getItemCount() > 0) {
					serverCombo.select(0);
				}
			}
			//flag should only be set if launch has been attempted on the config
			if (configuration.getAttribute(READ_ONLY, false)) {
				serverCombo.setEnabled(false);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (server != null)
			configuration.setAttribute(Server.NAME, server.getName());
		else
			configuration.setAttribute(Server.NAME, (String) null);

		String fileName = fFile.getText();
		String contextRoot = fContextRoot.getText();
		String url = fURL.getText();
		boolean deployable = publish.getSelection();
		configuration.setAttribute(Server.FILE_NAME, fileName);
		configuration.setAttribute(Server.CONTEXT_ROOT, contextRoot);
		configuration.setAttribute(Server.PUBLISH, deployable);
		configuration.setAttribute(Server.BASE_URL, url);

		applyExtension(configuration);
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
		// TODO - Note that this method of removing invalid launches is still buggy
		// In order to fix it completely, we might have to perform similar checks when removing or renaming a server from
		// the PHP Servers preferences page.
		try {
			String serverName = launchConfig.getAttribute(Server.NAME, "");
			if (!serverName.equals("")) {
				Server server = ServersManager.getServer(serverName);
				if (server == null) {
					deleteLaunchConfiguration(launchConfig);
					ILaunchConfiguration[] allConfigurations = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations();
					for (int i = 0; i < allConfigurations.length; i++) {
						launchConfig = allConfigurations[i];
						serverName = launchConfig.getAttribute(Server.NAME, "");
						if (!serverName.equals("") && ServersManager.getServer(serverName) == null) {
							// The server was removed, so remove this launch configuration!
							deleteLaunchConfiguration(launchConfig);
						}
					}
					return false;
				}
			}

			String fileName = launchConfig.getAttribute(Server.FILE_NAME, "");
			if (fileName != null && !(fileName.equals(""))) {
				if (!fileExists(fileName)) {
					setErrorMessage("File Does Not Exist");
					return false;
				}
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
					if (config instanceof LaunchConfigurationWorkingCopy) {
						config = ((LaunchConfigurationWorkingCopy) config).getOriginal();
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

	private boolean fileExists(String projectPath) {
		boolean file = false;

		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath p3 = new Path(projectPath);

			file = root.exists(p3);
		} catch (Exception e) {
		}

		return file;
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return null; // TODO - ImageResource.getImage(ImageResource.IMG_SERVER);
	}

	/**
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return "Server";
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

	protected Server getServerFromWizard() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		NullProgressMonitor monitor = new NullProgressMonitor();
		Server theServer = null;

		// try the full wizard
		//RunOnServerWizard wizard = new RunOnServerWizard(module, "run");
		ServerWizard wizard = new ServerWizard();
		ClosableWizardDialog dialog = new ClosableWizardDialog(shell, wizard);
		if (dialog.open() == Window.CANCEL) {
			monitor.setCanceled(true);
			return null;
		}

		try {
			Platform.getJobManager().join("org.eclipse.wst.server.ui.family", null);
		} catch (Exception e) {
			Logger.log(Logger.WARNING, "Error waiting for job", e);
		}
		theServer = (Server) wizard.getRootFragment().getWizardModel().getObject(WizardModel.SERVER);

		try {
			Platform.getJobManager().join("org.eclipse.wst.server.ui.family", new NullProgressMonitor());
		} catch (Exception e) {
			Logger.log(Logger.WARNING, "Error waiting for job", e);
		}

		return theServer;
	}

}
