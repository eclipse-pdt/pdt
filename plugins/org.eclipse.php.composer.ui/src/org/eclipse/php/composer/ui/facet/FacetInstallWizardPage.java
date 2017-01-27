/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.facet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.common.project.facet.ui.AbstractFacetWizardPage;

public class FacetInstallWizardPage extends AbstractFacetWizardPage {
	public FacetInstallWizardPage() {
		super(Messages.FacetInstallWizardPage_Name);

		setTitle(Messages.FacetInstallWizardPage_Title);
		setDescription(Messages.FacetInstallWizardPage_Description);
	}

	@Override
	public void setConfig(Object config) {

	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		Label label = new Label(container, SWT.NONE);
		label.setText(Messages.FacetInstallWizardPage_Label);

		setControl(container);
	}
}
