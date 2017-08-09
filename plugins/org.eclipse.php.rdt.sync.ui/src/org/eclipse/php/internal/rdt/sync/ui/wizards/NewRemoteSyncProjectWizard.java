/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Rogue Wave Software - Initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.rdt.sync.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ptp.internal.rdt.sync.ui.wizards.NewSyncProjectWizardProxy;

public class NewRemoteSyncProjectWizard extends NewSyncProjectWizardProxy {

	@Override
	public boolean performFinish() {
		if (!super.performFinish()) {
			return false;
		}

		try {
			IProject project = getNewProject();
			IProjectDescription description = project.getDescription();

			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = PHPNature.ID;

			// validate the natures
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateNatureSet(newNatures);

			// only apply new nature, if the status is ok
			if (status.getCode() == IStatus.OK) {
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			}
		} catch (CoreException e) {
			return false;
		}

		return true;
	}
}
