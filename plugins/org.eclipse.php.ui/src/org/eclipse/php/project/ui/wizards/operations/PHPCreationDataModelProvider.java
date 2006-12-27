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
package org.eclipse.php.project.ui.wizards.operations;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.phpModel.parser.PHPVersion;
import org.eclipse.php.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.frameworks.internal.operations.ProjectCreationDataModelProviderNew;

public class PHPCreationDataModelProvider extends ProjectCreationDataModelProviderNew {

	public static final String[] PHP_VERSION_VALUES = { PHPVersion.PHP4, PHPVersion.PHP5 };

	public static final String[] PHP_VERSION_DESCRIPTIONS = { "PHP 4", "PHP 5 or greater" };
	
	public final List wizardPages;

	public PHPCreationDataModelProvider(List wizardPages) {
		super();
		this.wizardPages = wizardPages;
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
		return propertyNames;

	}

	public IDataModelOperation getDefaultOperation() {
		return new PHPModuleCreationOperation(getDataModel(), wizardPages);
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
			return "";
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
			project=ResourcesPlugin.getWorkspace().getRoot().getProject("DUMMY");
		return project;
	}

}
