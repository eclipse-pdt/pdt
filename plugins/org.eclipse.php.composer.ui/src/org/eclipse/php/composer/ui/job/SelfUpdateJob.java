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
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.core.launch.ScriptLauncher;

public class SelfUpdateJob extends ComposerJob {

	public SelfUpdateJob(IProject project) {
		super(project, Messages.SelfUpdateJob_Name);
	}

	@Override
	protected void launch(ScriptLauncher launcher) throws IOException, InterruptedException, CoreException {
		launcher.launch("selfupdate"); //$NON-NLS-1$
	}
}
