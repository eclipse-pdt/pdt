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

import java.util.Set;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * Description: This factory is actually an intermediate class used for pages
 * added to a wizard trough the phpWizardPages extention point mechanism, that
 * need to get IDataModel as an argument in the constructor. After this class is
 * instantiated , we call the createPage method passing it the dataModel and
 * this way the actual wizard page is generated. This is actually a workaround
 * to solve the problem of not being able to pass arguments to constructors in
 * extention points
 * 
 * @author Eden K., 2006
 * 
 */
public abstract class WizardPageFactory extends WizardPage {

	protected static String fPageName = "WizardName"; //$NON-NLS-1$

	protected WizardPageFactory(String pageName) {
		super(pageName);
	}

	protected WizardPageFactory() {
		super(fPageName);
	}

	public void createControl(Composite parent) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Generates the actual wizard page
	 * 
	 * @param dataModel
	 * @return instance of the generated wizard page associated with the
	 *         specific factory
	 */
	public abstract IWizardPage createPage(IDataModel dataModel);

	/**
	 * @return a Set of properties to be added to the wizard IDataModel
	 *         properties Set
	 */
	public abstract Set getPropertyNames();

	/**
	 * Called at the final phase, after the project is created Can be used to
	 * store the project settings in the preferences
	 */
	public abstract void execute();

}
