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
import java.util.Map;

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
public class UninstallActionDelegate implements IDelegate {
	@Override
	public void execute(IProject project, IProjectFacetVersion version, Object object, IProgressMonitor progress)
			throws CoreException {
		if (!project.hasNature(PHPNature.ID)) {
			return;
		}

		// remove the composer nature
		int index = getBuilderIndex(project);
		if (index != -1) {
			progress.subTask(Messages.UninstallActionDelegate_TaskName);
			final IProjectDescription description = project.getDescription();
			final List<ICommand> commands = new ArrayList<ICommand>();
			commands.addAll(Arrays.asList(description.getBuildSpec()));
			commands.remove(index);

			description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));
			project.setDescription(description, null);
		}

	}

	private int getBuilderIndex(IProject project) {
		try {
			int i = 0;
			for (ICommand cmd : project.getDescription().getBuildSpec()) {
				// activated builder
				if (ComposerBuildPathManagementBuilder.ID.equals(cmd.getBuilderName())) {
					return i;
				}

				// deactivated builder
				if ("org.eclipse.ui.externaltools.ExternalToolBuilder".equals(cmd.getBuilderName())) { //$NON-NLS-1$
					Map<String, String> args = cmd.getArguments();
					if (args.containsKey("LaunchConfigHandle")) { //$NON-NLS-1$
						String launch = args.get("LaunchConfigHandle"); //$NON-NLS-1$
						if (launch.contains(ComposerBuildPathManagementBuilder.ID)) {
							return i;
						}
					}
				}
				i++;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return -1;
	}
}
