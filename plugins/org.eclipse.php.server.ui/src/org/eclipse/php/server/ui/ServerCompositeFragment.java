package org.eclipse.php.server.ui;

import java.net.URL;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.core.ServersManager;
import org.eclipse.php.server.internal.ui.ServersPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Wizard page to set the server install directory.
 */
public class ServerCompositeFragment extends CompositeFragment {

	protected Text name;
	protected Text url;
	protected Combo combo;
	protected Button publish;
	protected Text publishDir;
	protected Button browseButton;
	protected Button publishCheckBox;
	protected Label locationLabel;
	private ValuesCache originalValuesCache = new ValuesCache();
	private ValuesCache modifiedValuesCache;

	/**
	 * ServerCompositeFragment
	 * 
	 * @param parent the parent composite
	 * @param wizard the wizard handle
	 */
	public ServerCompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		super(parent, handler, isForEditing);
		controlHandler.setTitle("Server");
		controlHandler.setDescription("Specify the Server Information");
		controlHandler.setImageDescriptor(ServersPluginImages.DESC_WIZ_SERVER);
		setDisplayName("Server");
		createControl();
	}

	public void setServer(Server server) {
		super.setServer(server);
		init();
		validate();
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
		label.setText("Na&me:");
		GridData data = new GridData();
		label.setLayoutData(data);

		name = new Text(nameGroup, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (server != null)
					//					workingCopy.setName(name.getText());
					modifiedValuesCache.serverName = name.getText();
				validate();
			}
		});
		createProjectLocationGroup(this);
		createURLGroup(this);
		init();
		validate();

		Dialog.applyDialogFont(this);

		name.forceFocus();
	}

	protected void init() {
		if (name == null || server == null)
			return;

		originalValuesCache.canPublish = server.canPublish();
		originalValuesCache.publishDir = server.getDocumentRoot();
		originalValuesCache.url = server.getBaseURL();
		originalValuesCache.serverName = server.getName();
		originalValuesCache.host = server.getHost();
		// Clone the cache
		modifiedValuesCache = new ValuesCache(originalValuesCache);

		if (originalValuesCache.serverName != null) {
			boolean nameSet = false;
			String serverName = originalValuesCache.serverName;
			String orgName = serverName;
			if (!isForEditing()) {
				for (int i = 0; i < 10; i++) {
					boolean ok = checkServerName(serverName);
					if (ok) {
						name.setText(serverName);
						//						workingCopy.setName(serverName);
						modifiedValuesCache.serverName = serverName;
						nameSet = true;
						break;
					}
					serverName = orgName + " (" + Integer.toString(i + 2) + ")";
				}
				if (!nameSet) {
					name.setText("");
					//					workingCopy.setName("");
					modifiedValuesCache.serverName = "";
				}
			} else {
				name.setText(serverName);
			}
		} else {
			name.setText("");
		}
		String documentRoot = originalValuesCache.publishDir;
		publishDir.setText(documentRoot);

		String baseURL = originalValuesCache.url;
		if (!baseURL.equals("")) {
			url.setText(baseURL);
			try {
				URL originalURL = new URL(baseURL);
				int port = originalURL.getPort();
				originalValuesCache.port = port;
				modifiedValuesCache.port = port;
			} catch (Exception e) {
				setMessage("Please enter a valid URL", IMessageProvider.ERROR);
			}
		} else {
			baseURL = "http://" + server.getHost();
			url.setText(baseURL);
			modifiedValuesCache.url = baseURL;
			try {
				URL createdURL = new URL(baseURL);
				int port = createdURL.getPort();
				modifiedValuesCache.port = port;
			} catch (Exception e) {
				setMessage("Please enter a valid URL", IMessageProvider.ERROR);
			}
		}
		boolean selected = originalValuesCache.canPublish;
		publishCheckBox.setSelection(selected);
		publishDir.setEnabled(selected);
		browseButton.setEnabled(selected);
		locationLabel.setEnabled(selected);
		controlHandler.setTitle("Edit Server" + " [" + originalValuesCache.serverName + ']');
	}

	protected void validate() {
		if (server == null) {
			setMessage("", IMessageProvider.ERROR);
			return;
		}

		setMessage("", IMessageProvider.NONE);

		String serverName = modifiedValuesCache.serverName;
		if (serverName == null || serverName.trim().equals("")) {
			setMessage("Missing Server Name", IMessageProvider.ERROR);
		} else {
			boolean ok = checkServerName(serverName);
			if (!ok) {
				setMessage("Duplicate Server Name", IMessageProvider.ERROR);
			}
		}

		String urlStr = url.getText();
		if (urlStr == null || urlStr.equals("")) {
			setMessage("Please enter a valid URL", IMessageProvider.ERROR);
			return;
		}

		try {
			URL baseURL = new URL(urlStr);
			String host = baseURL.getHost();
			int port = baseURL.getPort();

			//			workingCopy.setHost(host);
			//			server.setPort(String.valueOf(port));
			modifiedValuesCache.host = host;
			modifiedValuesCache.port = port;
		} catch (Exception e) {
			setMessage("Please enter a valid URL", IMessageProvider.ERROR);
			return;
		}
		controlHandler.update();
	}

	protected void setMessage(String message, int type) {
		controlHandler.setMessage(message, type);
		setComplete(type != IMessageProvider.ERROR);
		controlHandler.update();
	}

	protected void createURLGroup(Composite parent) {
		Font font = parent.getFont();
		Group urlGroup = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		urlGroup.setLayout(layout);
		urlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		urlGroup.setFont(font);
		urlGroup.setText("Enter the URL that points to the document root of this server");

		url = new Text(urlGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		url.setLayoutData(data);
		url.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (server != null) {
					String urlStr = url.getText();
					//					server.setBaseURL(urlStr);
					modifiedValuesCache.url = urlStr;
				}
				validate();
			}
		});
	}

	private final void createProjectLocationGroup(Composite parent) {

		Font font = parent.getFont();
		// project specification group
		Group projectGroup = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		projectGroup.setFont(font);
		projectGroup.setText("Publish Information");

		publishCheckBox = new Button(projectGroup, SWT.CHECK | SWT.RIGHT);
		publishCheckBox.setText("Publish Projects to this Server");
		publishCheckBox.setFont(font);

		publishCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Button b = (Button) se.getSource();
				boolean selected = b.getSelection();

				publishDir.setEnabled(selected);
				browseButton.setEnabled(selected);
				locationLabel.setEnabled(selected);

				if (server != null)
					//					server.setPublish(selected);
					modifiedValuesCache.canPublish = selected;
			}
		});

		GridData buttonData = new GridData();
		buttonData.horizontalSpan = 3;
		publishCheckBox.setLayoutData(buttonData);

		createUserSpecifiedProjectLocationGroup(projectGroup);
	}

	private void createUserSpecifiedProjectLocationGroup(Composite projectGroup) {
		Font font = projectGroup.getFont();
		// location label
		locationLabel = new Label(projectGroup, SWT.NONE);
		locationLabel.setFont(font);
		locationLabel.setText("Directory:");

		// project location entry field
		publishDir = new Text(projectGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 305;
		publishDir.setLayoutData(data);
		publishDir.setFont(font);
		publishDir.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (server != null)
					//					server.setDocumentRoot(publishDir.getText());
					modifiedValuesCache.publishDir = publishDir.getText();
				validate();
			}
		});

		// browse button
		browseButton = new Button(projectGroup, SWT.PUSH);
		browseButton.setFont(font);
		browseButton.setText("B&rowse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DirectoryDialog dialog = new DirectoryDialog(ServerCompositeFragment.this.getShell());
				dialog.setMessage("Select the server's document root.");
				dialog.setFilterPath(publishDir.getText());
				String selectedDirectory = dialog.open();
				if (selectedDirectory != null)
					publishDir.setText(selectedDirectory);
			}
		});
	}

	private boolean checkServerName(String name) {
		name = name.trim();
		if (name.equals(originalValuesCache.serverName)) {
			return true;
		}
		Server[] allServers = ServersManager.getServers();

		if (allServers != null) {
			int size = allServers.length;
			for (int i = 0; i < size; i++) {
				Server server = allServers[i];
				if (name.equals(server.getName()))
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
			// Save any modification logged into the modified value cache object.
			if (server != null) {
				server.setPort(String.valueOf(modifiedValuesCache.port));
				server.setBaseURL(modifiedValuesCache.url);
				server.setDocumentRoot(modifiedValuesCache.publishDir);
				server.setPublish(modifiedValuesCache.canPublish);
			}
			server.setHost(modifiedValuesCache.host);
			server.setName(modifiedValuesCache.serverName);
			if (!originalValuesCache.serverName.equals(modifiedValuesCache.serverName)) {
				// Update the ServerManager with the new server name
				ServersManager.removeServer(originalValuesCache.serverName);
				ServersManager.addServer(server);
			}
//			getServerWorkingCopy().save(true, null); // TODO - Save the server's properties ??
		} catch (Throwable e) {
			Logger.logException("Error while saving the new server settings", e);
			return false;
		}
		return true;
	}

	// A class used as a local original IServerWorkingCopy values cache.
	private class ValuesCache {
		boolean canPublish;
		String serverName;
		String publishDir;
		String url;
		String host;
		int port;

		public ValuesCache() {
		}

		public ValuesCache(ValuesCache cache) {
			this.canPublish = cache.canPublish;
			this.serverName = cache.serverName;
			this.publishDir = cache.publishDir;
			this.url = cache.url;
			this.port = cache.port;
			this.host = cache.host;
		}
	}
}