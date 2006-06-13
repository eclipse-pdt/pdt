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
import java.util.Iterator;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class ServerEditDialog extends TitleAreaDialog implements IControlHandler {

	private Server server;
	private ArrayList runtimeComposites;

	/**
	 * Instantiate a new server edit dialog.
	 * 
	 * @param parentShell the parent SWT shell
	 * @param server An assigned IServer
	 */
	public ServerEditDialog(Shell parentShell, Server server) {
		super(parentShell);
		this.server = server;
		runtimeComposites = new ArrayList(3);
	}

	protected Control createDialogArea(Composite parent) {
		// Create a tabbed container that will hold all the fragments
		TabFolder tabFolder = new TabFolder(parent, SWT.TOP | SWT.FLAT);
		ICompositeFragmentFactory[] factories = ServerFragmentsFactoryRegistry.getFragmentsFactories("");
		for (int i = 0; i < factories.length; i++) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			CompositeFragment fragment = factories[i].createComposite(tabFolder, this);
			fragment.setServer(server);
			tabItem.setText(fragment.getDisplayName());
			tabItem.setControl(fragment);
			runtimeComposites.add(fragment);
		}
		getShell().setText("Edit Server");
		return tabFolder;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
	 */
	protected void cancelPressed() {
		Iterator composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			((CompositeFragment) composites.next()).performCancel();
		}
		super.cancelPressed();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		Iterator composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			((CompositeFragment) composites.next()).performOk();
		}
		super.okPressed();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#setDescription(java.lang.String)
	 */
	public void setDescription(String desc) {
		super.setMessage(desc);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#setImageDescriptor(org.eclipse.jface.resource.ImageDescriptor)
	 */
	public void setImageDescriptor(ImageDescriptor image) {
		super.setTitleImage(image.createImage());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#update()
	 */
	public void update() {
		Button button = getButton(IDialogConstants.OK_ID);
		if (button != null) {
			Iterator composites = runtimeComposites.iterator();
			while (composites.hasNext()) {
				if (!((CompositeFragment) composites.next()).isComplete()) {
					button.setEnabled(false);
					return;
				}
			}
			button.setEnabled(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#getServer()
	 */
	public Server getServer() {
		return server;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#setServer(org.eclipse.wst.server.core.IServer)
	 */
	public void setServer(Server server) {
		this.server = server;
	}
}
