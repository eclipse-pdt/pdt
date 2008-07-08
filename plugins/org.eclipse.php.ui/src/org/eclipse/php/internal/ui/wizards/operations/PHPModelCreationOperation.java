/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.operations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.php.internal.ui.wizards.WizardPageFactory;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;

public class PHPModelCreationOperation extends AbstractDataModelOperation implements IProjectCreationPropertiesNew {

	// List of WizardPageFactory(s) added trough the phpWizardPages extention point
	private List /* WizardPageFactory */wizardPageFactories = new ArrayList();

	public PHPModelCreationOperation(IDataModel dataModel, List wizardPageFactories) {
		super(dataModel);
		this.wizardPageFactories = wizardPageFactories;
	}

	public PHPModelCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}

	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			IProgressMonitor subMonitor = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
			final IProjectDescription desc = (IProjectDescription) model.getProperty(PROJECT_DESCRIPTION);
			final IProject project = (IProject) model.getProperty(PROJECT);
			if (!project.exists()) {
				project.create(desc, subMonitor);
			}

			if (monitor.isCanceled())
				throw new OperationCanceledException();
			subMonitor = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);

			project.open(subMonitor);
			
			// set project properties prior to any job execution
			if (model.isPropertySet(Keys.PHP_VERSION)) {
				String version = model.getStringProperty(Keys.PHP_VERSION);
				PhpVersionProjectPropertyHandler.setVersion(version, project);
				boolean useASPTags = model.getBooleanProperty(Keys.EDITOR_USE_ASP_TAGS);
				UseAspTagsHandler.setUseAspTagsAsPhp(useASPTags, project);
			}

			// For every page added to the projectCreationWizard, call its execute method
			// Here the project settings should be stored into the preferences
			// This action needs to happen here, after the project has been created and opened
			// and before setNatureIds is called

			// NOTE: project opening can take time if it's built from existing source (bug #205444)
			// Thus the next should run as the same type of workspace job.
			WorkspaceJob job = new WorkspaceJob("Saving project options") {

				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					for (Iterator iter = wizardPageFactories.iterator(); iter.hasNext();) {
						WizardPageFactory pageFactory = (WizardPageFactory) iter.next();
						pageFactory.execute();
					}

					String[] natureIds = (String[]) model.getProperty(PROJECT_NATURES);
					if (null != natureIds) {
						desc.setNatureIds(natureIds);
						project.setDescription(desc, monitor);
					}

					return Status.OK_STATUS;
				}

			};
			job.setRule(project.getWorkspace().getRoot());
			job.schedule();
		} catch (CoreException e) {
			Logger.logException(e);
		} finally {
			monitor.done();
		}
		if (monitor.isCanceled())
			throw new OperationCanceledException();
		return OK_STATUS;
	}

	public boolean canUndo() {
		return false;
	}

	public boolean canRedo() {
		return false;
	}

}
