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
package org.eclipse.php.composer.ui.preferences.launcher;

import java.io.File;
import java.net.URL;

import org.apache.commons.exec.CommandLine;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseListener;
import org.eclipse.php.composer.core.launch.execution.ScriptExecutor;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.osgi.framework.Bundle;

public class ExecutableTester implements Runnable {

	private PHPexeItem phPexeItem;
	private ExecutionResponseListener listener;

	public ExecutableTester(PHPexeItem phPexeItem, ExecutionResponseListener listener) {
		this.phPexeItem = phPexeItem;
		this.listener = listener;
	}

	@Override
	public void run() {

		try {
			ScriptExecutor executor = new ScriptExecutor();
			CommandLine cmd = new CommandLine(phPexeItem.getExecutable());
			cmd.addArgument("testexecutable"); //$NON-NLS-1$

			Bundle bundle = Platform.getBundle(ComposerUIPlugin.PLUGIN_ID);
			URL entry = bundle.getEntry("Resources/launcher"); //$NON-NLS-1$

			File file = new File(FileLocator.resolve(entry).toURI());

			if (file != null) {
				executor.setWorkingDirectory(file);
			}

			executor.addResponseListener(listener);
			executor.execute(cmd);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}
