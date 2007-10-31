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
package org.eclipse.php.internal.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.internal.ui.wizards.operations.PHPCreationDataModelProvider;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;
import org.osgi.service.prefs.BackingStoreException;

public class PHPProjectCreationWizard extends DataModelWizard implements IExecutableExtension, INewWizard {

	private static final String ID = "org.eclipse.php.ui.wizards.PHPProjectCreationWizard"; //$NON-NLS-1$

	protected PHPIncludePathPage includePathPage;
	protected PHPProjectWizardBasePage basePage;

	protected final ArrayList wizardPagesList = new ArrayList();
	private IProject createdProject = null;
	protected IConfigurationElement configElement;
	private List /** WizardPageFactory */
	wizardPageFactories = new ArrayList();

	public PHPProjectCreationWizard(IDataModel model) {
		super(model);
		populateWizardFactoryList();
	}

	public PHPProjectCreationWizard() {
		super();
		populateWizardFactoryList();
	}

	/**
	 * This operation is called after the Wizard  is created (and before the doAddPages) 
	 * and it is used to define all the properties that the wizard pages will set
	 * (not necessarly store as properties).
	 * All the wizard pages may access these properties.  
	 */
	protected IDataModelProvider getDefaultProvider() {
		return new PHPCreationDataModelProvider(wizardPageFactories);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void doAddPages() {
		// if we succeeded adding the default pages, add the contributed pages
		if(addDeafaultPages()){
			addContributedPages();
		}

	}

	protected void addContributedPages() {
		// generates the pages added trough the phpWizardPages extention point
		// and add them to the wizard
		for (Iterator iter = wizardPageFactories.iterator(); iter.hasNext();) {
			WizardPageFactory pageFactory = (WizardPageFactory) iter.next();
			IWizardPage currentPage = pageFactory.createPage(getDataModel());
			addPage(currentPage);
		}
	}

	protected boolean addDeafaultPages() {
		addPage(basePage = new PHPProjectWizardBasePage(getDataModel(), "page1")); //$NON-NLS-1$
		addPage(includePathPage = new PHPIncludePathPage(getDataModel(), "page2")); //$NON-NLS-1$
		return true;
	}

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		configElement = config;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(PHPUIMessages.getString("PHPProjectCreationWizard_PageTile"));
		setDefaultPageImageDescriptor(PHPPluginImages.DESC_WIZBAN_ADD_PHP_PROJECT);
	}

	protected boolean prePerformFinish() {
		createdProject = (IProject) getDataModel().getProperty(IProjectCreationPropertiesNew.PROJECT);

		getDataModel().setProperty(PHPCoreConstants.PHPOPTION_INCLUDE_PATH, includePathPage.getIncludePathsBlock().getIncludepathEntries());
		basePage.setProjectOptionInModel(getDataModel());

		return super.prePerformFinish();
	}

	protected void postPerformFinish() throws InvocationTargetException {
		BasicNewProjectResourceWizard.updatePerspective(configElement);

		if (createdProject != null) {
			//			 Save any project-specific data (Fix Bug# 143406)
			try {
				new ProjectScope(createdProject).getNode(PHPCorePlugin.ID).flush();
			} catch (BackingStoreException e) {
				Logger.logException(e);
			}
		}
	}

	private void populateWizardFactoryList() {
		IWizardPage[] pageGenerators = PHPWizardPagesRegistry.getPageFactories(ID);
		if (pageGenerators != null) {
			for (int i = 0; i < pageGenerators.length; i++) {
				WizardPageFactory pageFactory = (WizardPageFactory) pageGenerators[i];
				wizardPageFactories.add(pageFactory);
			}
		}
	}
}