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
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.project.operation.PHPCreationDataModelProvider;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.php.ui.wizards.BasicPHPWizardPageExtended;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizard;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;
import org.osgi.service.prefs.BackingStoreException;

public class PHPProjectCreationWizard extends DataModelWizard implements IExecutableExtension, INewWizard {

	
	private static final String PHP_WIZARD_PAGS_EXTENSION_POINT = "org.eclipse.php.ui.phpWizardPages"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String WIZARDNAME_ATTRIBUTE = "wizardName"; //$NON-NLS-1$
	private static final String WizardName = "PHPProjectCreationWizard"; //$NON-NLS-1$
	
	
	IConfigurationElement configElement;
	protected PHPIncludePathPage includePathPage;
    protected PHPProjectWizardBasePage basePage;
    ArrayList wizardPagesList = new ArrayList();
    
    
	
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

		//  load all additional pages registered trough the extension point and add them to the wizard
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHP_WIZARD_PAGS_EXTENSION_POINT); //$NON-NLS-1$
		for (int i = 0; i < elements.length; i++) {			
			if (elements[i].getAttribute(WIZARDNAME_ATTRIBUTE).equals(WizardName) && elements[i].getAttribute(CLASS_ATTRIBUTE) != null) {
				try {
					BasicPHPWizardPageExtended page = (BasicPHPWizardPageExtended) elements[i].createExecutableExtension(CLASS_ATTRIBUTE);
					addPage(page);
					wizardPagesList.add(page);
				} catch (CoreException e) {
					Logger.logException(PHPUIMessages.PHPProjectCreationWizard_LoadPagesFailure, e);
				}
			}
		}
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
		
		IProject createdProject = (IProject)getDataModel().getProperty(IProjectCreationPropertiesNew.PROJECT);
 		if (createdProject != null) {
 			
			for(Iterator it = wizardPagesList.iterator(); it.hasNext();){
				BasicPHPWizardPageExtended page = (BasicPHPWizardPageExtended) it.next();
				page.postPerformFinish(createdProject);		
			}
//			 Save any project-specific data (Fix Bug# 143406)
 			try {
				new ProjectScope(createdProject).getNode(PHPCorePlugin.ID).flush();
			} catch (BackingStoreException e) {
				Logger.logException(e);
			}
 		}
 		
	}

}