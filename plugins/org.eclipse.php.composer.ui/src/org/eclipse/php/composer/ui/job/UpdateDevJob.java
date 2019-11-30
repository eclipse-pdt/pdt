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
import java.util.Arrays;
import java.util.List;

import org.apache.commons.exec.ExecuteException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.composer.core.launch.ScriptLauncher;

public class UpdateDevJob extends AbstractInstallJob {

	private String[] packages = null;

	public UpdateDevJob(IProject project) {
		super(project, Messages.UpdateDevJob_Name);
	}

	public void setPackages(String[] packages) {
		this.packages = packages;
	}

	public String[] getPackages() {
		return packages;
	}

	@Override
	protected void launch(ScriptLauncher launcher) throws ExecuteException, IOException, InterruptedException {
		super.launch(launcher);
		List<String> params = new ArrayList<>();
		if (packages != null) {
			params.addAll(Arrays.asList(packages));
		}
		// workaround for incorrect progress displaying on Windows
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			params.add("--no-progress"); //$NON-NLS-1$
		}
		launcher.launch("update", params.toArray(new String[0])); //$NON-NLS-1$
	}
}
