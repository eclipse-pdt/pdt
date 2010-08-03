/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IWorkbenchSite;

public class ConvertToPDTProjectAction extends SelectionDispatchAction {

	private static final String PHPECLIPSE_NATURE = "net.sourceforge.phpeclipse.phpnature"; //$NON-NLS-1$
	private static final String PHPECLIPSE_BUILDER = "net.sourceforge.phpeclipse.parserbuilder"; //$NON-NLS-1$

	private IProject[] selectedProjects;

	public ConvertToPDTProjectAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.ConvertToPDTProjectAction_convert_to_PDT_project_title);
		setToolTipText(PHPUIMessages.ConvertToPDTProjectAction_convert_to_PDT_project_tooltip);
		setDescription(PHPUIMessages.ConvertToPDTProjectAction_convert_to_PDT_project_description);
	}

	public void selectionChanged(IStructuredSelection selection) {
		selectedProjects = getProjectsFromSelection(selection);
		setEnabled(selectedProjects.length > 0);
	}

	private IProject[] getProjectsFromSelection(IStructuredSelection selection) {
		List phpEclipseProjects = new LinkedList();
		Iterator i = selection.iterator();
		while (i.hasNext()) {
			Object element = i.next();
			if (element instanceof IProject) {
				IProject project = (IProject) element;
				try {
					if (project.isOpen()
							&& project.hasNature(PHPECLIPSE_NATURE)) {
						phpEclipseProjects.add(project);
					}
				} catch (CoreException e) {
				}
			}
		}
		return (IProject[]) phpEclipseProjects
				.toArray(new IProject[phpEclipseProjects.size()]);
	}

	public void run(IStructuredSelection selection) {
		final IProject[] projects = getProjectsFromSelection(selection);
		if (projects.length > 0) {
			WorkspaceJob convertJob = new WorkspaceJob(
					PHPUIMessages.ConvertToPDTProjectAction_converting_project_job_title) {
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {

					for (int i = 0; i < projects.length; ++i) {
						IProject project = projects[i];
						IProjectDescription projectDescription = project
								.getDescription();

						// Configure builders:
						List newBuildSpec = new LinkedList();
						ICommand[] buildSpec = projectDescription
								.getBuildSpec();
						for (int c = 0; c < buildSpec.length; ++c) {
							if (!buildSpec[c].getBuilderName().equals(
									PHPECLIPSE_BUILDER)) {
								newBuildSpec.add(buildSpec[c]);
							}
						}
						ICommand command = projectDescription.newCommand();
						command.setBuilderName(PHPNature.VALIDATION_BUILDER_ID);
						newBuildSpec.add(command);

						command = projectDescription.newCommand();
						newBuildSpec.add(command);

						projectDescription
								.setBuildSpec((ICommand[]) newBuildSpec
										.toArray(new ICommand[newBuildSpec
												.size()]));

						// Configure natures:
						List newNatures = new LinkedList();
						String[] natures = projectDescription.getNatureIds();
						for (int c = 0; c < natures.length; ++c) {
							if (!natures[c].equals(PHPECLIPSE_NATURE)) {
								newNatures.add(natures[c]);
							}
						}
						newNatures.add(PHPNature.ID);
						projectDescription.setNatureIds((String[]) newNatures
								.toArray(new String[newNatures.size()]));

						// Save project description:
						project.setDescription(projectDescription, monitor);
					}
					return Status.OK_STATUS;
				}
			};
			convertJob.setUser(true);
			convertJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
			convertJob.schedule();
		}
	}
}
