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
package org.eclipse.php.composer.ui.wizard.importer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.internal.resources.ProjectDescriptionReader;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.utils.ResourceUtil;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

@SuppressWarnings("restriction")
public class ComposerImportWizard extends Wizard implements IImportWizard {

	private IWorkbench workbench;
	private IStructuredSelection selection;
	private WizardResourceImportPage mainPage;

	public ComposerImportWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {

		this.workbench = workbench;
		this.selection = currentSelection;

		List selectedResources = IDE.computeSelectedResources(currentSelection);
		if (!selectedResources.isEmpty()) {
			this.selection = new StructuredSelection(selectedResources);
		}

		setWindowTitle(DataTransferMessages.DataTransfer_importTitle);
		setDefaultPageImageDescriptor(ComposerUIPluginImages.IMPORT_PROJECT);// $NON-NLS-1$
		setNeedsProgressMonitor(true);

	}

	public void addPages() {
		super.addPages();
		mainPage = new WizardResourceImportPage(workbench, selection, getFileImportMask());
		addPage(mainPage);
	}

	@Override
	public boolean performFinish() {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				String projectName = mainPage.getProjectName();
				IProject project = root.getProject(mainPage.getProjectName());
				monitor.beginTask(Messages.ComposerImportWizard_TaskName, 5);

				try {

					IPath locationPath = new Path(mainPage.getSourcePath());
					IProjectDescription description = null;

					if (locationPath.append(".project").toFile().exists()) { //$NON-NLS-1$
						ProjectDescriptionReader reader = new ProjectDescriptionReader(project);
						description = reader.read(locationPath.append(".project")); //$NON-NLS-1$
					} else {
						description = workspace.newProjectDescription(projectName);
					}

					// If it is under the root use the default location
					if (Platform.getLocation().isPrefixOf(locationPath)) {
						description.setLocation(null);
					} else {
						description.setLocation(locationPath);
					}

					monitor.worked(1);
					project.create(description, monitor);
					project.open(monitor);
					monitor.worked(1);

					if (!project.hasNature(PHPNature.ID)) {
						ResourceUtil.addNature(project, monitor, PHPNature.ID);
					}

					ProjectFacetsManager.create(project);

					FacetManager.installFacets(project, PHPVersion.PHP5_4, monitor);

					monitor.worked(1);

					project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					monitor.worked(2);

				} catch (CoreException e) {
					Logger.logException(e);
				} catch (IOException e) {
					Logger.logException(e);
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(false, true, op);
		} catch (Exception e) {
			Logger.logException(e);
			return false;
		}
		return true;
	}

	protected String[] getFileImportMask() {
		return null;
	}
}
