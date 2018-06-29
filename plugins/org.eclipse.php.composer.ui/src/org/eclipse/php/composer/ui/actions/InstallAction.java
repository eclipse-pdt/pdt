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
package org.eclipse.php.composer.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.job.InstallJob;
import org.eclipse.ui.IWorkbenchPartSite;

public class InstallAction extends ComposerAction {

	public InstallAction(IProject project, IWorkbenchPartSite site) {
		super(project, site, "org.eclipse.php.composer.ui.command.install"); //$NON-NLS-1$
	}

	@Override
	public void run() {
		ensureSaved();

		InstallJob job = new InstallJob(project);
		job.setUser(true);
		job.schedule();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ComposerUIPluginImages.INSTALL;
	}

}
