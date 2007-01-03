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
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.server.PHPServerUIMessages;
import org.eclipse.php.server.core.Server;
import org.eclipse.php.ui.util.SWTUtil;
import org.eclipse.php.ui.wizards.CompositeFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.php.ui.wizards.IControlHandler;
import org.eclipse.php.ui.wizards.WizardFragmentsFactoryRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ServerEditDialog extends TitleAreaDialog implements IControlHandler {

	private Server server;
	private ArrayList runtimeComposites;
	private SelectionListener tabsListener;

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
		CTabFolder tabs = SWTUtil.createTabFolder(parent);
		ICompositeFragmentFactory[] factories = WizardFragmentsFactoryRegistry.getFragmentsFactories();
		for (int i = 0; i < factories.length; i++) {
			CTabItem tabItem = new CTabItem(tabs, SWT.BORDER);
			CompositeFragment fragment = factories[i].createComposite(tabs, this);
			fragment.setData(server);
			tabItem.setText(fragment.getDisplayName());
			tabItem.setControl(fragment);
			runtimeComposites.add(fragment);
		}
		getShell().setText(PHPServerUIMessages.getString("ServerEditDialog.editServer")); //$NON-NLS-1$
		tabsListener = new TabsSelectionListener();
		tabs.addSelectionListener(tabsListener);
		return tabs;
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
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#setMessage(java.lang.String, int)
	 */
	public void setMessage(String newMessage, int newType) {
		// Override the WARNING with an INFORMATION. 
		// We have a bug that cause the warning to be displayed in all the tabs and not
		// only in the selected one. (TODO - Fix this)
		if (newType == IMessageProvider.WARNING) {
			newType = IMessageProvider.INFORMATION;
		}
		super.setMessage(newMessage, newType);
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
	
	private class TabsSelectionListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			// Do nothing
		}

		public void widgetSelected(SelectionEvent e) {
			CTabItem item = (CTabItem)e.item;
			CompositeFragment fragment = (CompositeFragment)item.getControl();
			setTitle(fragment.getTitle());
			setDescription(fragment.getDescription());
		}
		
	}
}
