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

import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.PHPVersionConfigurationBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;
import org.eclipse.wst.common.frameworks.internal.operations.IProjectCreationPropertiesNew;
import org.eclipse.wst.common.frameworks.internal.ui.NewProjectGroup;

public class PHPProjectWizardBasePage extends DataModelWizardPage implements
		IProjectCreationPropertiesNew {

	/**
	 * GUI for controlling whether a new PHP project should include JavaScript
	 * support or not
	 * 
	 * @author alon
	 * 
	 */
	public class JavaScriptSupportGroup {

		private final Group fGroup;
		protected Button fEnableJavaScriptSupport;

		public JavaScriptSupportGroup(Composite composite,
				PHPProjectWizardBasePage page) {

			fGroup = new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());
			GridLayout layout = new GridLayout();

			fGroup.setLayout(layout);
			GridData data = new GridData(GridData.FILL_BOTH);
			fGroup.setLayoutData(data);
			fGroup.setText(PHPUIMessages.JavaScriptSupportGroup_OptionBlockTitle);

			Composite checkLinkComposite = new Composite(fGroup, SWT.NONE);
			checkLinkComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
					true, false));
			checkLinkComposite.setLayout(new GridLayout(2, false));

			fEnableJavaScriptSupport = new Button(checkLinkComposite, SWT.CHECK
					| SWT.RIGHT);
			fEnableJavaScriptSupport
					.setText(PHPUIMessages.JavaScriptSupportGroup_EnableSupport);
			fEnableJavaScriptSupport.setLayoutData(new GridData(SWT.BEGINNING,
					SWT.CENTER, false, false));
			fEnableJavaScriptSupport.setSelection(model
					.getBooleanProperty(PHPCoreConstants.ADD_JS_NATURE));
		}

		// Stores in the model a property based on the selection of the
		// check-box
		public void setPropertiesInDataModel(IDataModel model) {
			model.setBooleanProperty(PHPCoreConstants.ADD_JS_NATURE,
					fEnableJavaScriptSupport.getSelection());
		}

	}

	protected NewProjectGroup projectNameGroup;
	protected PHPVersionGroup fVersionGroup;
	protected JavaScriptSupportGroup fJavaScriptSupportGroup;

	public PHPProjectWizardBasePage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setTitle(PHPUIMessages.PHPProjectWizardBasePage_0); 
		setDescription(PHPUIMessages.PHPProjectWizardBasePage_1); 
		setPageComplete(false);
	}

	protected void setSize(Composite composite) {
		if (composite != null) {
			Point minSize = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			composite.setSize(minSize);
			// set scrollbar composite's min size so page is expandable but has
			// scrollbars when needed
			if (composite.getParent() instanceof ScrolledComposite) {
				ScrolledComposite sc1 = (ScrolledComposite) composite
						.getParent();
				sc1.setMinSize(minSize);
				sc1.setExpandHorizontal(true);
				sc1.setExpandVertical(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jem.util.ui.wizard.WTPWizardPage#getValidationPropertyNames()
	 */
	protected String[] getValidationPropertyNames() {
		return new String[] { IProjectCreationPropertiesNew.PROJECT_NAME,
				PROJECT_LOCATION };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jem.util.ui.wizard.WTPWizardPage#createTopLevelComposite(
	 * org.eclipse.swt.widgets.Composite)
	 */
	protected Composite createTopLevelComposite(Composite parent) {
		Composite top = new Composite(parent, SWT.NONE);
		top.setLayout(new GridLayout(1, true));
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		createProjectNameGroup(top);

		createProjectOptionsGroup(top);
		createJavaScriptSupportGroup(top);

		setHelpContext(top);
		return top;
	}

	protected void setHelpContext(Composite top) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(top,
				IPHPHelpContextIds.CREATING_PROJECTS_AND_FILES); 
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

	protected void createJavaScriptSupportGroup(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);
		fJavaScriptSupportGroup = new JavaScriptSupportGroup(parent, this);
	}

	public void setProjectOptionInModel(IDataModel model) {
		fVersionGroup.setPropertiesInDataModel(model);
		fJavaScriptSupportGroup.setPropertiesInDataModel(model);
	}

	public PHPVersionConfigurationBlock getPHPVersionBlock() {
		return fVersionGroup.getVersionBlock();
	}

	public void setInitialProjectName(String name) {
		projectNameGroup.projectNameField.setText(name);
	}

}