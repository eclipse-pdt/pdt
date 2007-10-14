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
package org.eclipse.php.internal.ui.wizards.operations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.wizards.PHPWizardPagesRegistry;
import org.eclipse.php.internal.ui.wizards.WizardPageFactory;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.frameworks.internal.operations.ProjectCreationDataModelProviderNew;

public class PHPCreationDataModelProvider extends ProjectCreationDataModelProviderNew {

	public static final String[] PHP_VERSION_VALUES = { PHPVersion.PHP4, PHPVersion.PHP5 };

	public static final String[] PHP_VERSION_DESCRIPTIONS = { PHPUIMessages.getString("PHPCreationDataModelProvider.0"), PHPUIMessages.getString("PHPCreationDataModelProvider.1") }; //$NON-NLS-1$ //$NON-NLS-2$
	
	private static final String ID = "org.eclipse.php.ui.wizards.PHPProjectCreationWizard"; //$NON-NLS-1$
	
//	 List of WizardPageFactory(s) added trough the phpWizardPages extention point
	private List /* WizardPageFactory */ wizardPageFactories = new ArrayList();

	
	public PHPCreationDataModelProvider(List wizardPageFactories) {
		super();
		this.wizardPageFactories = wizardPageFactories;
	}
	
	public PHPCreationDataModelProvider() {
		super();		
	}

	public void init() {
		super.init();
		addPHPNature();
	}

	public Set getPropertyNames() {
		Set propertyNames = super.getPropertyNames();
		propertyNames.add(Keys.PHP_VERSION);
		propertyNames.add(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
		propertyNames.add(PHPCoreConstants.PHPOPTION_CONTEXT_ROOT);
		propertyNames.add(PHPCoreConstants.PHPOPTION_INCLUDE_PATH);
        propertyNames.add(Keys.EDITOR_USE_ASP_TAGS);
        
		//		propertyNames.add(IProjectCreationProperties.PROJECT_DESCRIPTION);
        
        // get the specific properties from each page registered for phpProjectCreation 
        // in the extention point ID (above)
        // and add them to the model properties
        
        IWizardPage[] pageGenerators = PHPWizardPagesRegistry.getPageFactories(ID);
		if (pageGenerators != null) {
			for (int i = 0; i < pageGenerators.length; i++) {		
				WizardPageFactory page = (WizardPageFactory) pageGenerators[i];
				Set pagePropertieNames = page.getPropertyNames();
				
				for (Iterator iter = pagePropertieNames.iterator(); iter.hasNext();) {
					propertyNames.add((String) iter.next());					
				}
			}
		}       
		return propertyNames;

	}

	public IDataModelOperation getDefaultOperation() {
		return new PHPModuleCreationOperation(getDataModel(), wizardPageFactories);
	}

	protected final void addPHPNature() {
		String[] natures = (String[]) getProperty(PROJECT_NATURES);
		String[] newNatures;

		if (natures == null) {
			newNatures = new String[1];
			newNatures[0] = PHPNature.ID;
		} else {
			newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = PHPNature.ID;
		}

		model.setProperty(PROJECT_NATURES, newNatures);
	}

	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING))
			return ""; //$NON-NLS-1$
		return super.getDefaultProperty(propertyName);
	}

	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		return super.getPropertyDescriptor(propertyName);
	}

	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(Keys.PHP_VERSION)) {
			return DataModelPropertyDescriptor.createDescriptors(PHP_VERSION_VALUES, PHP_VERSION_DESCRIPTIONS);

		}
		return super.getValidPropertyDescriptors(propertyName);
	}

		// override so the null is never returned, because it causes future execptions
	protected IProject getProject() {		
		IProject project= super.getProject();
		if (project==null)
			project=ResourcesPlugin.getWorkspace().getRoot().getProject("DUMMY"); //$NON-NLS-1$
		return project;
	}

}
