/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.php.composer.ui.wizard.ValidationException.Severity;

abstract public class AbstractValidator implements Observer {

	protected AbstractWizardFirstPage firstPage;
	protected IWorkspace workspace;
	protected String name;
	protected IProject handle;
	protected String location;
	protected IEnvironment environment;

	/**
	 * @param composerProjectWizardFirstPage
	 */
	public AbstractValidator(AbstractWizardFirstPage composerProjectWizardFirstPage) {
		firstPage = composerProjectWizardFirstPage;
	}

	@Override
	public void update(Observable observable, Object object) {

		workspace = DLTKUIPlugin.getWorkspace();
		name = firstPage.nameGroup.getName();

		if (name != null && name.length() > 0) {
			handle = firstPage.getProjectHandle();
		}

		if (firstPage.PHPLocationGroup != null) {
			location = firstPage.PHPLocationGroup.getLocation().toOSString();
		}

		environment = firstPage.getEnvironment();

		try {
			validateName();
			beginValidation();
			validateProjectNotExists();
			validateLocation();
			finishValidation();
		} catch (ValidationException e) {

			switch (e.getSeverity()) {
			case WARNING:
				firstPage.setErrorMessage(null);
				firstPage.setMessage(e.getMessage());
				break;
			case ERROR:
				firstPage.setErrorMessage(e.getMessage());
				firstPage.setMessage(null);
				break;
			default:
				return;
			}

			firstPage.setPageComplete(false);
			return;
		}

		firstPage.setPageComplete(true);
		firstPage.setErrorMessage(null);
		firstPage.setMessage(null);
	}

	/**
	 * Check whether the project name field is not empty and valid
	 * 
	 * @param name
	 * @throws ValidationException
	 */
	protected void validateName() throws ValidationException {
		if (name == null || name.length() == 0) {
			throw new ValidationException(NewWizardMessages.ScriptProjectWizardFirstPage_Message_enterProjectName,
					Severity.WARNING);
		}

		final IStatus nameStatus = workspace.validateName(name, IResource.PROJECT);
		if (!nameStatus.isOK()) {
			throw new ValidationException(nameStatus.getMessage(), Severity.ERROR);
		}
	}

	protected void validateProjectNotExists() throws ValidationException {
		// check whether project already exists
		if (!firstPage.isInLocalServer()) {
			if (handle.exists()) {
				throw new ValidationException(
						NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists, Severity.ERROR);
			}
		}

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject currentProject : projects) {
			String existingProjectName = currentProject.getName();
			if (existingProjectName.equalsIgnoreCase(name)) {
				throw new ValidationException(
						NewWizardMessages.ScriptProjectWizardFirstPage_Message_projectAlreadyExists, Severity.ERROR);
			}
		}
	}

	protected void validateLocation() throws ValidationException {
		// check whether location is empty
		if (location.length() == 0) {
			throw new ValidationException(NewWizardMessages.ScriptProjectWizardFirstPage_Message_enterLocation,
					Severity.WARNING);
		}
		// check whether the location is a syntactically correct path
		if (!Path.EMPTY.isValidPath(location)) {
			throw new ValidationException(NewWizardMessages.ScriptProjectWizardFirstPage_Message_invalidDirectory,
					Severity.ERROR);
		}

		// check whether the location has the workspace as prefix
		IPath projectPath = Path.fromOSString(location);
		if (!firstPage.PHPLocationGroup.isInWorkspace() && Platform.getLocation().isPrefixOf(projectPath)) {
			throw new ValidationException(
					NewWizardMessages.ScriptProjectWizardFirstPage_Message_cannotCreateInWorkspace, Severity.ERROR);
		}

		// If we do not place the contents in the workspace validate the
		// location.
		if (!firstPage.PHPLocationGroup.isInWorkspace()) {
			if (EnvironmentManager.isLocal(environment)) {
				final IStatus locationStatus = workspace.validateProjectLocation(handle, projectPath);
				if (!locationStatus.isOK()) {
					throw new ValidationException(locationStatus.getMessage(), Severity.ERROR);
				}

				if (!firstPage.canCreate(projectPath.toFile())) {
					throw new ValidationException(
							NewWizardMessages.ScriptProjectWizardFirstPage_Message_invalidDirectory, Severity.ERROR);
				}
			}
		}
	}

	/**
	 * Begin the validation process of the project wizards first page
	 * 
	 * Place your custom validation logic here and throw a
	 * {@link ValidationException} if validation fails.
	 * 
	 * @throws ValidationException
	 */
	abstract protected void beginValidation() throws ValidationException;

	/**
	 * Finish the validation process of the project wizards first page
	 * 
	 * Place your custom validation logic here and throw a
	 * {@link ValidationException} if validation fails.
	 * 
	 * @throws ValidationException
	 */
	abstract protected void finishValidation() throws ValidationException;
}
