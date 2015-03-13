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

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Wizard page to set the server install directory.
 */
@SuppressWarnings("restriction")
public class ServerCompositeFragment extends CompositeFragment {

	protected Text name;
	protected Text url;
	protected Combo combo;
	private Text webroot;

	/**
	 * ServerCompositeFragment
	 * 
	 * @param parent
	 *            the parent composite
	 * @param wizard
	 *            the wizard handle
	 */
	public ServerCompositeFragment(Composite parent, IControlHandler handler,
			boolean isForEditing) {
		super(parent, handler, isForEditing);
		setDescription(PHPServerUIMessages
				.getString("ServerCompositeFragment.specifyInformation")); //$NON-NLS-1$
		controlHandler.setDescription(getDescription());
		setImageDescriptor(ServersPluginImages.DESC_WIZ_SERVER);
		controlHandler.setImageDescriptor(getImageDescriptor());
		setDisplayName(PHPServerUIMessages
				.getString("ServerCompositeFragment.server")); //$NON-NLS-1$
		createControl();
	}

	/**
	 * Override the super setData to handle only Server types.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given object is not a {@link Server}
	 */
	public void setData(Object server) throws IllegalArgumentException {
		if (server != null && !(server instanceof Server)) {
			throw new IllegalArgumentException(
					"The given object is not a Server"); //$NON-NLS-1$
		}
		super.setData(server);
		init();
		validate();
	}

	public Server getServer() {
		return (Server) getData();
	}

	/**
	 * Provide a wizard page to change the Server's installation directory.
	 */
	protected void createControl() {
		GridLayout layout = new GridLayout(1, true);
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite nameGroup = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		nameGroup.setLayout(layout);
		nameGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label label = new Label(nameGroup, SWT.NONE);
		label.setText(PHPServerUIMessages
				.getString("ServerCompositeFragment.nameLabel")); //$NON-NLS-1$
		GridData data = new GridData();
		label.setLayoutData(data);
		name = new Text(nameGroup, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				getServer().setName(name.getText());
				validate();
			}
		});
		createURLGroup(this);
		init();
		validate();
		Dialog.applyDialogFont(this);
		name.forceFocus();
	}

	protected void init() {
		Server server = getServer();
		if (name == null || server == null)
			return;
		if (getServer().getName() != null) {
			boolean nameSet = false;
			String serverName = getServer().getName();
			String orgName = serverName;
			if (!isForEditing()) {
				for (int i = 0; i < 10; i++) {
					boolean ok = checkServerName(serverName);
					if (ok) {
						name.setText(serverName);
						getServer().setName(serverName);
						;
						nameSet = true;
						break;
					}
					serverName = orgName + " (" + Integer.toString(i + 2) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				if (!nameSet) {
					name.setText(""); //$NON-NLS-1$
					getServer().setName(""); //$NON-NLS-1$
				}
			} else {
				name.setText(serverName);
			}
		} else {
			name.setText(""); //$NON-NLS-1$
		}
		if (getServer().getDocumentRoot() != null) {
			webroot.setText(getServer().getDocumentRoot());
		}
		String baseURL = getServer().getBaseURL();
		if (!baseURL.equals("")) { //$NON-NLS-1$
			url.setText(baseURL);
			try {
				URL originalURL = new URL(baseURL);
				int port = originalURL.getPort();
				getServer().setPort(String.valueOf(port));
			} catch (Exception e) {
				setMessage(
						PHPServerUIMessages
								.getString("ServerCompositeFragment.enterValidURL"), IMessageProvider.ERROR); //$NON-NLS-1$
			}
		} else {
			baseURL = "http://" + server.getHost(); //$NON-NLS-1$
			url.setText(baseURL);
			try {
				getServer().setBaseURL(baseURL);
				URL createdURL = new URL(baseURL);
				int port = createdURL.getPort();
				getServer().setPort(String.valueOf(port));
			} catch (Exception e) {
				setMessage(
						PHPServerUIMessages
								.getString("ServerCompositeFragment.enterValidURL"), IMessageProvider.ERROR); //$NON-NLS-1$
			}
		}
		if (getServer().getName() != null && getServer().getName().length() > 0) {
			setTitle(PHPServerUIMessages
					.getString("ServerCompositeFragment.editServer") + " [" + getServer().getName() + ']'); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			setTitle(PHPServerUIMessages
					.getString("ServerCompositeFragment.configureServer")); //$NON-NLS-1$
		}
		controlHandler.setTitle(getTitle());
	}

	public void validate() {
		if (getServer() == null) {
			setMessage("", IMessageProvider.ERROR); //$NON-NLS-1$
			return;
		}
		setMessage(getDescription(), IMessageProvider.NONE);
		String urlStr = url.getText();
		if (urlStr != null && !urlStr.trim().equals("")) { //$NON-NLS-1$
			boolean ok = checkServerUrl(urlStr);
			if (!ok) {
				setMessage(
						PHPServerUIMessages
								.getString("ServerCompositeFragment.duplicateServerUrl"), IMessageProvider.ERROR); //$NON-NLS-1$
			}
		}
		try {
			URL url = new URL(urlStr);
			if (url.getPath() != null && url.getPath().length() != 0) {
				urlStr = null;
			}
		} catch (MalformedURLException e1) {
			// in case of Malformed URL - reset
			urlStr = null;
		}
		if (urlStr == null || urlStr.equals("")) { //$NON-NLS-1$
			setMessage(
					PHPServerUIMessages
							.getString("ServerCompositeFragment.enterValidURL"), IMessageProvider.ERROR); //$NON-NLS-1$
			return;
		}
		try {
			URL baseURL = new URL(urlStr);
			String host = baseURL.getHost();
			if (host.trim().length() == 0) {
				setMessage(
						PHPServerUIMessages
								.getString("ServerCompositeFragment.serverURLEmpty"), IMessageProvider.ERROR); //$NON-NLS-1$
			}
			int port = baseURL.getPort();

			getServer().setHost(host);
			getServer().setPort(String.valueOf(port));
		} catch (Exception e) {
			setMessage(
					PHPServerUIMessages
							.getString("ServerCompositeFragment.enterValidURL"), IMessageProvider.ERROR); //$NON-NLS-1$
			return;
		}
		String serverName = getServer().getName();
		if (serverName == null || serverName.trim().equals("")) { //$NON-NLS-1$
			setMessage(
					PHPServerUIMessages
							.getString("ServerCompositeFragment.missingServerName"), IMessageProvider.ERROR); //$NON-NLS-1$
		} else {
			boolean ok = checkServerName(serverName);
			if (!ok) {
				setMessage(
						PHPServerUIMessages
								.getString("ServerCompositeFragment.duplicateServerName"), IMessageProvider.ERROR); //$NON-NLS-1$
			}
		}
		String webrootStr = webroot.getText().trim();
		if (webrootStr.length() != 0 && !new Path(webrootStr).toFile().exists()) {
			setMessage(
					PHPServerUIMessages
							.getString("ServerCompositeFragment.webrootNotExists"), IMessageProvider.ERROR); //$NON-NLS-1$
		}
		controlHandler.update();
	}

	protected void createURLGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setFont(parent.getFont());
		GridLayout layout = new GridLayout(3, false);
		group.setLayout(layout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(data);
		group.setText(PHPServerUIMessages
				.getString("ServerCompositeFragment.serverProperties")); //$NON-NLS-1$
		Label urlLabel = new Label(group, SWT.None);
		urlLabel.setText(PHPServerUIMessages
				.getString("ServerCompositeFragment.baseURL")); //$NON-NLS-1$
		url = new Text(group, SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		url.setLayoutData(layoutData);
		url.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (getServer() != null) {
					String urlStr = url.getText();
					try {
						getServer().setBaseURL(urlStr);
					} catch (MalformedURLException e1) {
						// ignore
					}
				}
				validate();
			}
		});
		Label labelRoot = new Label(group, SWT.None);
		labelRoot.setText(PHPServerUIMessages
				.getString("ServerCompositeFragment.localWebRoot")); //$NON-NLS-1$
		labelRoot.setLayoutData(new GridData());
		webroot = new Text(group, SWT.BORDER);
		webroot.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		webroot.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (getServer() != null) {
					String webrootStr = webroot.getText();
					getServer().setDocumentRoot(webrootStr);
				}
				validate();
			}
		});
		Button browseButton = new Button(group, SWT.PUSH);
		browseButton.setText(PHPServerUIMessages
				.getString("ServerCompositeFragment.browse")); //$NON-NLS-1$
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				if (!"".equals(webroot.getText())) { //$NON-NLS-1$
					String initialDir = webroot.getText();
					dialog.setFilterPath(initialDir);
				}
				String result = dialog.open();
				if (result != null)
					webroot.setText(result.toString());
			}
		});

	}

	private boolean checkServerName(String name) {
		name = name.trim();
		Server[] allServers = ServersManager.getServers();
		if (allServers != null) {
			int size = allServers.length;
			for (int i = 0; i < size; i++) {
				Server server = allServers[i];
				if (name.equals(server.getName())
						&& !getServer().getUniqueId().equals(
								server.getUniqueId()))
					return false;
			}
		}
		return true;
	}

	private boolean checkServerUrl(String url) {
		url = url.trim();
		Server[] allServers = ServersManager.getServers();
		if (allServers != null) {
			int size = allServers.length;
			for (int i = 0; i < size; i++) {
				Server server = allServers[i];
				if (url.equals(server.getBaseURL())
						&& !getServer().getUniqueId().equals(
								server.getUniqueId()))
					return false;
			}
		}
		return true;
	}

	/**
	 * Saves the IServerWorkingCopy.
	 */
	public boolean performOk() {
		try {
			Server server = getServer();
			Server originalServer = ServersManager.findServer(server
					.getUniqueId());
			// Server exists, update it
			if (originalServer != null) {
				originalServer.update(server);
			}
		} catch (Throwable e) {
			Logger.logException("Error while saving the server settings", e); //$NON-NLS-1$
			return false;
		}
		return true;
	}

}