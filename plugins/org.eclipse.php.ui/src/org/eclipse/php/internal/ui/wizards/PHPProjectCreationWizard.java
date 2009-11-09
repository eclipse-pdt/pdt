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
package org.eclipse.php.internal.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.wizards.NewElementWizard;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class PHPProjectCreationWizard extends NewElementWizard implements
		INewWizard, IExecutableExtension {

	public static final String SELECTED_PROJECT = "SelectedProject";

	public static final String WIZARD_ID = "org.eclipse.php.wizards.newproject"; //$NON-NLS-1$

	protected PHPProjectWizardFirstPage fFirstPage;
	protected PHPProjectWizardSecondPage fSecondPage;
	protected PHPProjectWizardThirdPage fThirdPage;
	protected PHPProjectWizardSecondPage fLastPage = fThirdPage;
	protected IConfigurationElement fConfigElement;

	protected int fLastPageIndex = -1;

	public PHPProjectCreationWizard() {
		setDefaultPageImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_PROJECT);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(PHPUIMessages
				.getString("PHPProjectCreationWizard_WizardTitle"));
	}

	public void addPages() {
		super.addPages();
		fFirstPage = new PHPProjectWizardFirstPage();

		// First page
		fFirstPage.setTitle(PHPUIMessages
				.getString("PHPProjectCreationWizard_Page1Title"));
		fFirstPage.setDescription(PHPUIMessages
				.getString("PHPProjectCreationWizard_Page1Description"));
		addPage(fFirstPage);

		// Second page (Include Path)
		fSecondPage = new PHPProjectWizardSecondPage(fFirstPage);
		fSecondPage.setTitle(PHPUIMessages
				.getString("PHPProjectCreationWizard_Page2Title"));
		fSecondPage.setDescription(PHPUIMessages
				.getString("PHPProjectCreationWizard_Page2Description"));
		addPage(fSecondPage);

		// Third page (Include Path)
		fThirdPage = new PHPProjectWizardThirdPage(fFirstPage);
		fThirdPage.setTitle(PHPUIMessages
				.getString("PHPProjectCreationWizard_Page3Title"));
		fThirdPage.setDescription(PHPUIMessages
				.getString("PHPProjectCreationWizard_Page3Description"));
		addPage(fThirdPage);

		fLastPage = fSecondPage;
	}

	protected void finishPage(IProgressMonitor monitor)
			throws InterruptedException, CoreException {
		if (fSecondPage != null)
			fSecondPage.performFinish(monitor); // use the full progress monitor
		if (fThirdPage != null)
			fThirdPage.performFinish(monitor); // use the full progress monitor
	}

	public boolean performFinish() {
		boolean res = super.performFinish();
		if (res) {
			BasicNewProjectResourceWizard.updatePerspective(fConfigElement);
			selectAndReveal(fLastPage.getScriptProject().getProject());

			WizardModel model = fFirstPage.getWizardData();

			Object eanblement = null;
			if (model != null) {
				eanblement = model
						.getObject("REMOTE_GROUP_REMOTE_PROJECT_ENABLED");
			}

			if (model != null && eanblement != null && (Boolean) eanblement) {

				model.putObject(SELECTED_PROJECT, fLastPage.getScriptProject()
						.getProject());

				IRunnableWithProgress run = (IRunnableWithProgress) Platform
						.getAdapterManager().getAdapter(model,
								IRunnableWithProgress.class);

				if (run != null) {
					try {
						getContainer().run(true, false, run);
					} catch (InvocationTargetException e) {
						handleFinishException(getShell(), e);
						return false;
					} catch (InterruptedException e) {
						return false;
					}
				}
			}
		}
		return res;
	}

	/*
	 * Stores the configuration element for the wizard. The config element will
	 * be used in <code>performFinish</code> to set the result perspective.
	 */
	public void setInitializationData(IConfigurationElement cfig,
			String propertyName, Object data) {
		fConfigElement = cfig;
	}

	public boolean performCancel() {
		fLastPage.performCancel();
		return super.performCancel();
	}

	public IModelElement getCreatedElement() {
		return DLTKCore.create(fFirstPage.getProjectHandle());
	}

	public int getLastPageIndex() {
		return fLastPageIndex;
	}

	public void setLastPageIndex(int current) {
		fLastPageIndex = current;
	}

}