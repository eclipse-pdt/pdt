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

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.ui.util.PersistingSizeAndLocationWizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Helper class which opens <b>Edit Server</b> dialog. Main purpose of the class
 * is to provide settings object the dialog can use to store its settings
 * between every run.
 */
public class ServerEditWizardRunner {

	public static int runWizard(Server server) {
		return runWizard(getDefaultShell(), server);
	}

	public static int runWizard(Server server, String tabId) {
		return runWizard(getDefaultShell(), server, tabId);
	}

	public static int runWizard(Shell parentShell, Server server) {
		ServerEditWizard wizard = new ServerEditWizard(server);
		return runWizard(parentShell, wizard);
	}

	public static int runWizard(Shell parentShell, Server server, String tabId) {
		ServerEditWizard wizard = new ServerEditWizard(server, tabId);
		return runWizard(parentShell, wizard);
	}

	public static int runWizard(ServerEditWizard wizard) {
		return runWizard(getDefaultShell(), wizard);
	}

	public static int runWizard(Shell parentShell, ServerEditWizard wizard) {
		IDialogSettings dialogSettings = DialogSettings.getOrCreateSection(Activator.getDefault().getDialogSettings(),
				ServerEditWizard.class.getSimpleName());
		PersistingSizeAndLocationWizardDialog dialog = new PersistingSizeAndLocationWizardDialog(parentShell, wizard,
				dialogSettings);
		return dialog.open();
	}

	private static Shell getDefaultShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}
