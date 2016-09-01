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
package org.eclipse.php.composer.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.core.builder.ComposerBuildPathManagementBuilder;

public class ComposerNature implements IProjectNature {
	public static final String NATURE_ID = "org.eclipse.php.composer.core.composerNature";

	private IProject project;

	@Override
	public void configure() throws CoreException {

		if (hasBuilder()) {
			return;
		}

		// install builder
		IProjectDescription description = project.getDescription();
		final ICommand buildCommand = description.newCommand();
		buildCommand.setBuilderName(ComposerBuildPathManagementBuilder.ID);

		final List<ICommand> commands = new ArrayList<ICommand>();
		commands.add(buildCommand);
		commands.addAll(Arrays.asList(description.getBuildSpec()));

		description
				.setBuildSpec(commands.toArray(new ICommand[commands.size()]));
		project.setDescription(description, null);
	}

	@Override
	public void deconfigure() throws CoreException {

		// uninstall builder
		int index = getBuilderIndex();
		if (index != -1) {
			final IProjectDescription description = project.getDescription();
			final List<ICommand> commands = new ArrayList<ICommand>();
			commands.addAll(Arrays.asList(description.getBuildSpec()));
			commands.remove(index);

			description.setBuildSpec(commands.toArray(new ICommand[commands
					.size()]));
			project.setDescription(description, null);
		}
	}

	private boolean hasBuilder() {
		return getBuilderIndex() != -1;
	}

	private int getBuilderIndex() {
		try {
			int i = 0;
			for (ICommand cmd : project.getDescription().getBuildSpec()) {
				// activated builder
				if (ComposerBuildPathManagementBuilder.ID.equals(cmd
						.getBuilderName())) {
					return i;
				}

				// deactivated builder
				if ("org.eclipse.ui.externaltools.ExternalToolBuilder"
						.equals(cmd.getBuilderName())) {
					Map<String, String> args = cmd.getArguments();
					if (args.containsKey("LaunchConfigHandle")) {
						String launch = args.get("LaunchConfigHandle");
						if (launch
								.contains(ComposerBuildPathManagementBuilder.ID)) {
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

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
