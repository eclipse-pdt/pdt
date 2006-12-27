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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.project.ui.wizards.operations.PHPCreationDataModelProvider;
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
	
	private static final String ID = "org.eclipse.php.project.ui.wizards.PHPProjectCreationWizard"; //$NON-NLS-1$

	protected PHPIncludePathPage includePathPage;
    protected PHPProjectWizardBasePage basePage;
    
    protected final ArrayList wizardPagesList = new ArrayList();
    private IProject createdProject = null;
    private IConfigurationElement configElement;
    
	
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
		
		BasicPHPWizardPageExtended[] pages = PHPWizardPagesRegistry.getPages(ID); 
		if (pages != null) {
			for (int i = 0; i < pages.length; ++i) {
				addPage(pages[i]);
				wizardPagesList.add(pages[i]);
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
				
		createdProject = (IProject)getDataModel().getProperty(IProjectCreationPropertiesNew.PROJECT);
		if (createdProject != null) { 			
//			 Save any project-specific data (Fix Bug# 143406)
 			try {
				new ProjectScope(createdProject).getNode(PHPCorePlugin.ID).flush();				
			} catch (BackingStoreException e) {
				Logger.logException(e);
			} 			
 			// call the postPerformFinish for any page added trough the extention point
// 			if (createdProject != null) {
// 				for(Iterator it = wizardPagesList.iterator(); it.hasNext();){
// 					BasicPHPWizardPageExtended page = (BasicPHPWizardPageExtended) it.next();
// 					page.postPerformFinish(createdProject);
// 					page.flushPreferences();		
// 				}
// 			}
 		}
	}
}