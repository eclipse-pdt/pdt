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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathBlock;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class PHPIncludePathPage extends DataModelWizardPage implements /*ISimpleWebModuleCreationDataModelProperties,*/IStatusChangeListener {

	protected IncludePathBlock fIncludePathsBlock;

	public PHPIncludePathPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setDescription("PHP Include Path"); //$NON-NLS-1$
		setTitle("PHP Include Path"); //$NON-NLS-1$
		setPageComplete(true);

		createIncludePathsBlock();
	}

	protected void createIncludePathsBlock() {
		fIncludePathsBlock = new IncludePathBlock(new BusyIndicatorRunnableContext(), this, 1, false, null);
		fIncludePathsBlock.init(getDummyProject(), null);
	}

	public IProject getDummyProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject("DUMMY______________Project"); //$NON-NLS-1$
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
		return new String[] {};
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
		Composite composite = new Composite(top, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		composite.setLayout(layout);

		fIncludePathsBlock.createControl(top);

		return top;
	}

	public IncludePathBlock getIncludePathsBlock() {
		return fIncludePathsBlock;
	}

	public void statusChanged(IStatus status) {
		if (status.matches(IStatus.ERROR)) {
			setErrorMessage(status.getMessage());
		} else
			setErrorMessage(null);
	}

}