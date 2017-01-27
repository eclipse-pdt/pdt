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
package org.eclipse.php.composer.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.job.InstallDevJob;
import org.eclipse.ui.IWorkbenchPartSite;

public class InstallDevAction extends ComposerAction {

	public InstallDevAction(IProject project, IWorkbenchPartSite site) {
		super(project, site, "org.eclipse.php.composer.ui.command.installDev"); //$NON-NLS-1$
	}

	@Override
	public void run() {
		ensureSaved();

		InstallDevJob job = new InstallDevJob(project);
		job.setUser(true);
		job.schedule();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ComposerUIPluginImages.INSTALL_DEV;
	}

}
