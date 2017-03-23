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
 *     Kaloyan Raev - [511744] Wizard freezes if no PHP executable is configured
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.wizards.NewElementWizard;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.editor.composer.ComposerFormEditor;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public abstract class AbstractComposerWizard extends NewElementWizard implements INewWizard, IExecutableExtension {

	protected AbstractWizardFirstPage firstPage;
	protected AbstractWizardSecondPage secondPage;
	protected AbstractWizardSecondPage lastPage;
	protected IConfigurationElement config;

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		this.config = config;
	}

	public void addPages() {
		super.addPages();

		firstPage = getFirstPage();
		addPage(firstPage);

		secondPage = getSecondPage();
		addPage(secondPage);

		lastPage = secondPage;
	}

	@Override
	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {

		if (firstPage != null) {
			firstPage.performFinish(monitor);
		}

		if (secondPage != null) {
			secondPage.performFinish(monitor);
		}
	}

	@Override
	public IModelElement getCreatedElement() {
		return DLTKCore.create(firstPage.getProjectHandle());
	}

	@Override
	public boolean performFinish() {

		boolean res = super.performFinish();
		if (res) {
			BasicNewProjectResourceWizard.updatePerspective(config);
			IScriptProject scriptProject = lastPage.getScriptProject();
			if (scriptProject != null) {
				IProject project = scriptProject.getProject();
				selectAndReveal(project);
				PHPVersion version = firstPage.getPHPVersionValue();
				if (version == null) {
					version = ProjectOptions.getDefaultPHPVersion();
				}

				FacetManager.installFacets(project, version, null);
				IFile json = project.getFile("composer.json"); //$NON-NLS-1$

				if (json != null) {
					try {
						IEditorInput editorInput = new FileEditorInput(json);
						IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
						IWorkbenchPage page = window.getActivePage();
						page.openEditor(editorInput, ComposerFormEditor.ID);
					} catch (Exception e) {
						Logger.logException(e);
					}
				}
			}
		}

		return res;
	}

	@Override
	public boolean performCancel() {
		secondPage.cancel();
		return super.performCancel();
	}

	protected abstract AbstractWizardFirstPage getFirstPage();

	protected abstract AbstractWizardSecondPage getSecondPage();

}
