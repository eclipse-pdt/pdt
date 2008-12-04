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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.wizards.NewElementWizard;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class PHPProjectCreationWizard extends NewElementWizard implements INewWizard, IExecutableExtension {

	public static final String WIZARD_ID = "org.eclipse.php.wizards.newproject"; //$NON-NLS-1$

	protected PHPProjectWizardFirstPage fFirstPage;
	protected PHPProjectWizardSecondPage fSecondPage;
	protected PHPProjectWizardThirdPage fThirdPage;
	protected PHPProjectWizardThirdPage fLastPage = fThirdPage;
	protected IConfigurationElement fConfigElement;

	protected int fLastPageIndex = -1;

	public PHPProjectCreationWizard() {
		setDefaultPageImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_PROJECT);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(PHPUIMessages.getString("PHPProjectCreationWizard_WizardTitle"));
	}

	public void addPages() {
		super.addPages();
		fFirstPage = new PHPProjectWizardFirstPage();

		// First page
		fFirstPage.setTitle(PHPUIMessages.getString("PHPProjectCreationWizard_Page1Title"));
		fFirstPage.setDescription(PHPUIMessages.getString("PHPProjectCreationWizard_Page1Description"));
		addPage(fFirstPage);

		// Second page (Include Path)
		fSecondPage = new PHPProjectWizardSecondPage(fFirstPage);
		fSecondPage.setTitle(PHPUIMessages.getString("PHPProjectCreationWizard_Page2Title"));
		fSecondPage.setDescription(PHPUIMessages.getString("PHPProjectCreationWizard_Page2Description"));
		addPage(fSecondPage);

		// Third page (Include Path)
		fThirdPage = new PHPProjectWizardThirdPage(fFirstPage);
		fThirdPage.setTitle(PHPUIMessages.getString("PHPProjectCreationWizard_Page3Title"));
		fThirdPage.setDescription(PHPUIMessages.getString("PHPProjectCreationWizard_Page3Description"));
		addPage(fThirdPage);
		
		fLastPage = fThirdPage ;
	}

	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
		fLastPage.performFinish(monitor); // use the full progress monitor
	}

	public boolean performFinish() {
		boolean res = super.performFinish();
		if (res) {
			BasicNewProjectResourceWizard.updatePerspective(fConfigElement);
			selectAndReveal(fLastPage.getScriptProject().getProject());
		}
		return res;
	}

	/*
	 * Stores the configuration element for the wizard. The config element will
	 * be used in <code>performFinish</code> to set the result perspective.
	 */
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
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