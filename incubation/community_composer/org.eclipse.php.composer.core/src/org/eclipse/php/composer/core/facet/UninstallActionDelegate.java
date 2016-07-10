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
package org.eclipse.php.composer.core.facet;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.ComposerNature;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Facet installation action delegate to add the composer nature to a PHP
 * project.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
@SuppressWarnings("restriction")
public class UninstallActionDelegate implements IDelegate {
	@Override
	public void execute(IProject project, IProjectFacetVersion version,
			Object object, IProgressMonitor progress) throws CoreException {
		if (!project.hasNature(PHPNature.ID)) {
			return;
		}

		progress.subTask("Uninstalling composer nature");
		// remove the composer nature
		IProjectDescription desc = project.getDescription();
		ArrayList<String> natures =  new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
		int index = natures.indexOf(ComposerNature.NATURE_ID);
		if (index != -1) {
			natures.remove(index);
		}
		desc.setNatureIds(natures.toArray(new String[]{}));
		project.setDescription(desc, progress);

		
		//progress.subTask("Uninstalling composer buildpath");

		// maybe comment out this one:
		// remove composer buildpath entry

		/*
		if (ComposerPlugin.getDefault().isBuildpathContainerEnabled()) {
			IBuildpathContainer composerContainer = new ComposerBuildpathContainer(
					new Path(ComposerBuildpathContainerInitializer.CONTAINER),
					scriptProject);
			List<IBuildpathEntry> entries = new ArrayList<IBuildpathEntry>();
			entries.remove(DLTKCore.newContainerEntry(composerContainer.getPath()));

			// remove the composer buildpathentry to the project
			BuildPathUtils.addEntriesToBuildPath(scriptProject, entries);

			BuildpathUtil.setupVendorBuildpath(scriptProject, progress);
		}
		*/
	}
}
