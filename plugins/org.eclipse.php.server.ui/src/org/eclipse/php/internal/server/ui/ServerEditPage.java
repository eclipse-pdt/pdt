/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.server.core.types.IServerType;
import org.eclipse.php.server.core.types.ServerTypesManager;
import org.eclipse.php.server.ui.types.IServerTypeDescriptor;
import org.eclipse.php.server.ui.types.IServerTypeDescriptor.ImageType;
import org.eclipse.php.server.ui.types.ServerTypesDescriptorRegistry;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * Wizard page for editing PHP server settings.
 */
public class ServerEditPage extends WizardPage implements IControlHandler {

	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.server.ui.serverWizardAndComposite"; //$NON-NLS-1$

	public interface IPostFinish {

		/**
		 * Performs post finish action.
		 */
		public void perform();

	}

	private Server server;
	private ArrayList<CompositeFragment> runtimeComposites;
	private TabFolder tabs;
	private String tabID;
	private List<IPostFinish> postFinish = new ArrayList<>();

	/**
	 * Instantiate a new server edit wizard page.
	 * 
	 * @param server
	 *            An assigned IServer
	 */
	public ServerEditPage(Server server) {
		super(PHPServerUIMessages.getString("ServerEditPage.Title")); //$NON-NLS-1$
		this.server = server;
		this.runtimeComposites = new ArrayList<>();
	}

	/**
	 * Instantiate a new server edit wizard page.
	 * 
	 * @param server
	 *            An assigned IServer
	 * @param init
	 *            selected tabe ID.
	 */
	public ServerEditPage(Server server, String tabID) {
		this(server);
		this.tabID = tabID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		// Create a tabbed container that will hold all the fragments
		tabs = new TabFolder(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		tabs.setLayoutData(gd);
		IServerType serverType = ServerTypesManager.getInstance().getType(server);
		IServerTypeDescriptor serverTypeDescriptor = ServerTypesDescriptorRegistry.getDescriptor(serverType);
		ICompositeFragmentFactory[] factories = serverTypeDescriptor.getEditorFragmentFactories();
		for (ICompositeFragmentFactory element : factories) {
			TabItem tabItem = new TabItem(tabs, SWT.NONE);
			CompositeFragment fragment = element.createComposite(tabs, this);
			fragment.setData(server);
			tabItem.setText(fragment.getDisplayName());
			tabItem.setControl(fragment);
			tabItem.setData(fragment.getId());
			runtimeComposites.add(fragment);
		}

		getShell().setText(PHPServerUIMessages.getString("ServerEditDialog.editServer")); //$NON-NLS-1$
		getShell().setImage(ServersPluginImages.get(ServersPluginImages.IMG_SERVER));

		tabs.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem item = (TabItem) e.item;
				CompositeFragment fragment = (CompositeFragment) item.getControl();
				if (fragment instanceof ServerCompositeFragment) {
					IServerType type = ServerTypesManager.getInstance().getType(server);
					if (type != null) {
						IServerTypeDescriptor serverTypeDescriptor = ServerTypesDescriptorRegistry.getDescriptor(type);
						((ServerCompositeFragment) fragment)
								.setImageDescriptor(serverTypeDescriptor.getImageDescriptor(ImageType.WIZARD));
					}
				}
				setImageDescriptor(fragment.getImageDescriptor());
				setDescription(fragment.getDescription());
				setTitle(fragment.getTitle());
				fragment.validate();
			}
		});

		// set the init selection of tabitem.
		if (tabID != null) {
			setSelect(tabID);
		}

		int selectionIndex = tabs.getSelectionIndex() != -1 ? tabs.getSelectionIndex() : 0;
		CompositeFragment selectedFragment = runtimeComposites.get(selectionIndex);
		setTitle(selectedFragment.getTitle());
		setDescription(selectedFragment.getDescription());

		setControl(tabs);
		IServerType type = ServerTypesManager.getInstance().getType(server);
		if (type != null) {
			serverTypeDescriptor = ServerTypesDescriptorRegistry.getDescriptor(type);
			setImageDescriptor(serverTypeDescriptor.getImageDescriptor(ImageType.WIZARD));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.IControlHandler#getKind()
	 */
	@Override
	public Kind getKind() {
		return Kind.EDITOR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.apache.ui.IControlHandler#setDescription
	 * (java.lang.String)
	 */
	@Override
	public void setDescription(String desc) {
		super.setMessage(desc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.IControlHandler#update()
	 */
	@Override
	public void update() {
		for (CompositeFragment composite : runtimeComposites) {
			if (!composite.isComplete()) {
				setPageComplete(false);
				return;
			}
		}
		setPageComplete(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.IControlHandler#run(boolean,
	 * boolean, org.eclipse.jface.operation.IRunnableWithProgress)
	 */
	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		getContainer().run(fork, cancelable, runnable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.apache.ui.IControlHandler#getServer()
	 */
	public Server getServer() {
		return server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.apache.ui.IControlHandler#setServer(org
	 * .eclipse.wst.server.core.IServer)
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	public void performCancel() {
		for (CompositeFragment composite : runtimeComposites) {
			composite.performCancel();
		}
	}

	public boolean performFinish() {
		for (CompositeFragment composite : runtimeComposites) {
			if (!composite.performOk()) {
				return false;
			}
		}
		// Save original server
		try {
			Server originalServer = ServersManager.findServer(server.getUniqueId());
			// Server exists, update it
			if (originalServer != null) {
				originalServer.update(server);
			}
		} catch (Throwable e) {
			Logger.logException("Error while saving the server settings", e); //$NON-NLS-1$
			return false;
		}
		// Perform post finish actions
		performPostFinish();
		return true;
	}

	public void addPostFinish(IPostFinish operation) {
		postFinish.add(operation);
	}

	private void performPostFinish() {
		for (IPostFinish operation : postFinish) {
			operation.perform();
		}
	}

	private void setSelect(String id) {
		if (id == null) {
			return;
		}
		for (int i = 0; i < tabs.getItemCount(); i++) {
			if (id.equals(tabs.getItem(i).getData())) {
				tabs.setSelection(i);
				break;
			}
		}
	}

}