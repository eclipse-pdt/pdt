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
package org.eclipse.php.composer.core.facet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.builder.ComposerBuildPathManagementBuilder;
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
public class InstallActionDelegate implements IDelegate {
	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object object, IProgressMonitor progress)
			throws CoreException {
		if (!project.hasNature(PHPNature.ID)) {
			return;
		}

		progress.subTask(Messages.InstallActionDelegate_TaskName);

		IProjectDescription description = project.getDescription();
		final ICommand buildCommand = description.newCommand();
		buildCommand.setBuilderName(ComposerBuildPathManagementBuilder.ID);

		final List<ICommand> commands = new ArrayList<ICommand>();
		commands.add(buildCommand);
		commands.addAll(Arrays.asList(description.getBuildSpec()));

		description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));
		project.setDescription(description, null);
	}
}
