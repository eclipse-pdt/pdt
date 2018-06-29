/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.server.core.Server;

/**
 * Wizard for editing PHP server settings.
 */
public class ServerEditWizard extends Wizard {

	private Server server;
	private ServerEditPage serverPage;
	private String tabID;

	public ServerEditWizard(Server server) {
		this.server = server.makeCopy();
		setWindowTitle(PHPServerUIMessages.getString("ServerEditWizard.Title")); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	public ServerEditWizard(Server server, String tabID) {
		this(server);
		this.tabID = tabID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		if (tabID != null) {
			serverPage = new ServerEditPage(server, tabID);
		} else {
			serverPage = new ServerEditPage(server);
		}
		addPage(serverPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return serverPage.performFinish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		serverPage.performCancel();
		return super.performCancel();
	}

}