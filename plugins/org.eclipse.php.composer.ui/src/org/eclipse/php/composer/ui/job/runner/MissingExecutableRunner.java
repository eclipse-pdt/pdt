/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.job.runner;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.dialogs.MissingExecutableDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class MissingExecutableRunner implements Runnable {
	@Override
	public void run() {
		try {
			Status status = new Status(IStatus.WARNING, ComposerUIPlugin.PLUGIN_ID,
					Messages.MissingExecutableRunner_ErrorMessage);
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MissingExecutableDialog dialog = new MissingExecutableDialog(shell, status);
			dialog.open();
		} catch (Exception e2) {
			Logger.logException(e2);
		}
	}
}