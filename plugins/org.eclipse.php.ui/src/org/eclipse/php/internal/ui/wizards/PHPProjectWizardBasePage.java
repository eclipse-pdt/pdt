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

import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.PHPVersionConfigurationBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;
import org.eclipse.wst.common.frameworks.internal.ui.NewProjectGroup;

public class PHPProjectWizardBasePage extends DataModelWizardPage implements IProjectCreationPropertiesNew {
	protected NewProjectGroup projectNameGroup;
	protected PHPVersionGroup fVersionGroup;

	public PHPProjectWizardBasePage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);		
		setTitle(PHPUIMessages.getString("PHPProjectWizardBasePage.0"));  //$NON-NLS-1$
		setDescription(PHPUIMessages.getString("PHPProjectWizardBasePage.1")); //$NON-NLS-1$
		setPageComplete(false);
	}

	protected void setSize(Composite composite) {
		if (composite != null) {
			Point minSize = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			composite.setSize(minSize);
			// set scrollbar composite's min size so page is expandable but has
			// scrollbars when needed
			if (composite.getParent() instanceof ScrolledComposite) {
				ScrolledComposite sc1 = (ScrolledComposite) composite.getParent();
				sc1.setMinSize(minSize);
				sc1.setExpandHorizontal(true);
				sc1.setExpandVertical(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jem.util.ui.wizard.WTPWizardPage#getValidationPropertyNames()
	 */
	protected String[] getValidationPropertyNames() {
		return new String[] { IProjectCreationPropertiesNew.PROJECT_NAME, PROJECT_LOCATION };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jem.util.ui.wizard.WTPWizardPage#createTopLevelComposite(org.eclipse.swt.widgets.Composite)
	 */
	protected Composite createTopLevelComposite(Composite parent) {
		Composite top = new Composite(parent, SWT.NONE);
		top.setLayout(new GridLayout(1, true));
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		createProjectNameGroup(top);

		createProjectOptionsGroup(top);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, "com.ibm.etools.webtools.wizards.basic.webw1450"); //$NON-NLS-1$
		return top;
	}

	protected void createProjectNameGroup(Composite parent) {
		projectNameGroup = new NewProjectGroup(parent, model);
	}

	protected void createProjectOptionsGroup(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		composite.setLayout(layout);
		fVersionGroup = new PHPVersionGroup(parent, this);
	}

	public void setProjectOptionInModel(IDataModel model) {
		fVersionGroup.setPropertiesInDataModel(model);
	}

	public PHPVersionConfigurationBlock getPHPVersionBlock() {
		return fVersionGroup.getVersionBlock();
	}

	public void setInitialProjectName(String name) {
		projectNameGroup.projectNameField.setText(name);
	}

}