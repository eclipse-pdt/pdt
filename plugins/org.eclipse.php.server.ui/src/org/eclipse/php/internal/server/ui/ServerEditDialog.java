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

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragmentsFactoryRegistry;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ServerEditDialog extends TitleAreaDialog implements
		IControlHandler {

	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.server.ui.serverWizardAndComposite"; //$NON-NLS-1$
	private Server server;
	private ArrayList runtimeComposites;
	private SelectionListener tabsListener;
	private CTabFolder tabs;
	private String tabID;

	/**
	 * Instantiate a new server edit dialog.
	 * 
	 * @param parentShell
	 *            the parent SWT shell
	 * @param server
	 *            An assigned IServer
	 */
	public ServerEditDialog(Shell parentShell, Server server) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);

		this.server = server;
		runtimeComposites = new ArrayList(3);
	}

	/**
	 * Instantiate a new server edit dialog.
	 * 
	 * @param parentShell
	 *            the parent SWT shell
	 * @param server
	 *            An assigned IServer
	 * @param init
	 *            selected tabe ID.
	 */
	public ServerEditDialog(Shell parentShell, Server server, String tabID) {
		this(parentShell, server);
		this.tabID = tabID;
	}

	protected Control createDialogArea(Composite parent) {
		// Create a tabbed container that will hold all the fragments
		tabs = SWTUtil.createTabFolder(parent);
		ICompositeFragmentFactory[] factories = WizardFragmentsFactoryRegistry
				.getFragmentsFactories(FRAGMENT_GROUP_ID);
		for (ICompositeFragmentFactory element : factories) {
			CTabItem tabItem = new CTabItem(tabs, SWT.BORDER);
			CompositeFragment fragment = element.createComposite(tabs, this);
			fragment.setData(server);
			tabItem.setText(fragment.getDisplayName());
			tabItem.setControl(fragment);
			tabItem.setData(fragment.getId());
			runtimeComposites.add(fragment);
		}

		getShell().setText(
				PHPServerUIMessages.getString("ServerEditDialog.editServer")); //$NON-NLS-1$
		getShell().setImage(
				ServersPluginImages.get(ServersPluginImages.IMG_SERVER));

		tabsListener = new TabsSelectionListener();
		tabs.addSelectionListener(tabsListener);

		// set the init selection of tabitem.
		if (tabID != null) {
			setSelect(tabID);
		}
		return tabs;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
	 */
	protected void cancelPressed() {
		Iterator composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			((CompositeFragment) composites.next()).performCancel();
		}
		super.cancelPressed();
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see
	 * org.eclipse.php.internal.server.apache.ui.IControlHandler#setDescription
	 * (java.lang.String)
	 */
	public void setDescription(String desc) {
		super.setMessage(desc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.server.apache.ui.IControlHandler#setImageDescriptor
	 * (org.eclipse.jface.resource.ImageDescriptor)
	 */
	public void setImageDescriptor(ImageDescriptor image) {
		super.setTitleImage(image.createImage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.apache.ui.IControlHandler#update()
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
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.TitleAreaDialog#setMessage(java.lang.String,
	 * int)
	 */
	public void setMessage(String newMessage, int newType) {
		// Override the WARNING with an INFORMATION.
		// We have a bug that cause the warning to be displayed in all the tabs
		// and not
		// only in the selected one. (TODO - Fix this)
		if (newType == IMessageProvider.WARNING) {
			newType = IMessageProvider.INFORMATION;
		}
		super.setMessage(newMessage, newType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.server.apache.ui.IControlHandler#getServer()
	 */
	public Server getServer() {
		return server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.server.apache.ui.IControlHandler#setServer(org
	 * .eclipse.wst.server.core.IServer)
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	private class TabsSelectionListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			// Do nothing
		}

		public void widgetSelected(SelectionEvent e) {
			CTabItem item = (CTabItem) e.item;
			CompositeFragment fragment = (CompositeFragment) item.getControl();
			setTitle(fragment.getTitle());
			setDescription(fragment.getDescription());
		}

	}
}
