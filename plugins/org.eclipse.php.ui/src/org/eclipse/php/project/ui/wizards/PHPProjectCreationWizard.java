/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.project.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.project.operation.PHPCreationDataModelProvider;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.util.PHPPluginImages;

public class PHPProjectCreationWizard extends DataModelWizard implements IExecutableExtension, INewWizard {

	IConfigurationElement configElement;
	protected PHPIncludePathPage includePathPage;
    protected PHPProjectWizardBasePage basePage;
	
	public PHPProjectCreationWizard(IDataModel model) {
		super(model);
	}

	public PHPProjectCreationWizard() {
		super();
	}

	protected IDataModelProvider getDefaultProvider() {
		return new PHPCreationDataModelProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void doAddPages() {
		addPage(basePage = new PHPProjectWizardBasePage(getDataModel(), "page1")); //$NON-NLS-1$
		addPage(includePathPage = new PHPIncludePathPage(getDataModel(), "page2")); //$NON-NLS-1$
	}

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		configElement = config;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPUIMessages.PHPProjectCreationWizard_PageTile); 
		setDefaultPageImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_PROJECT);
	}

	protected boolean prePerformFinish() {
		getDataModel().setProperty(PHPCoreConstants.PHPOPTION_INCLUDE_PATH,includePathPage.getIncludePathsBlock().getIncludepathEntries());
 		basePage.setProjectOptionInModel(getDataModel());
        return super.prePerformFinish();
	}

	protected void postPerformFinish() throws InvocationTargetException {
		BasicNewProjectResourceWizard.updatePerspective(configElement);
	}

}