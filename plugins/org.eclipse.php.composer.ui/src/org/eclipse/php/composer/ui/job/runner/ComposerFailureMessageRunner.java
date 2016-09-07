/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.job.runner;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.dialogs.ComposerJobFailureDialog;

public class ComposerFailureMessageRunner implements Runnable {

	private final String response;
	private final IProgressMonitor monitor;

	public ComposerFailureMessageRunner(String response, IProgressMonitor monitor) {
		this.response = response;
		this.monitor = monitor;
	}

	public void run() {

		if (monitor != null) {
			monitor.done();
		}

		// ScriptLauncher.resetEnvironment();
		String message = "Launching composer failed. See the .metadata/.log file in your workspace for details.";
		if (response != null && response.length() > 0) {
			message = response.trim();
		}

		try {
			new ComposerJobFailureDialog("", new Status(Status.ERROR, ComposerUIPlugin.PLUGIN_ID, message)).open();
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}