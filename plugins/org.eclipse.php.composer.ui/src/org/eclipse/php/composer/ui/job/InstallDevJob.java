/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *     Kaloyan Raev - [503025] Composer is using deprecated --dev option
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.composer.core.launch.ScriptLauncher;

public class InstallDevJob extends ComposerJob {

	public InstallDevJob(IProject project) {
		super(project, Messages.InstallDevJob_Name);
	}

	@Override
	protected void launch(ScriptLauncher launcher) throws IOException, InterruptedException, CoreException {
		List<String> params = new ArrayList<>();
		// workaround for incorrect progress displaying on Windows
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			params.add("--no-progress"); //$NON-NLS-1$
		}

		launcher.launch("install", params.toArray(new String[0])); //$NON-NLS-1$
	}
}
