/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.types;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This class represents the Wizard's page for creating a new PHP Interface
 * 
 */
public class NewPHPInterfacePage extends NewPHPTypePage {

	private static final String[] INTERFACE__CHECKBOXES = new String[] { REQUIRE_ONCE, PHP_DOC_BLOCKS };

	public NewPHPInterfacePage() {
		super(Messages.NewPHPInterfacePage_0);
		fTypeKind = INTERFACE_TYPE;
		setMessage(Messages.NewPHPInterfacePage_1);
		setDescription(Messages.NewPHPInterfacePage_1);
		setTitle(Messages.NewPHPInterfacePage_2);
		interfacesStatus = new StatusInfo();
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		super.createControl(parent);
		final Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);

		// the location section is generic for both class and interface
		// that's why it is in PHPType
		createLocationSection(composite);

		// this element's section
		createElementSection(composite);

		setControl(composite);
		initValues();
	}

	// create this element's section
	private void createElementSection(Composite container) {
		GridLayout layout = new GridLayout(3, false);
		// the element section is specific to an interface OR a class
		final Composite elementSection = new Composite(container, SWT.NULL);
		elementSection.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		elementSection.setLayoutData(gd);

		addElementNameText(elementSection, Messages.NewPHPInterfacePage_3);
		addNamespaceText(elementSection);

		createSuperInterfacesControls(elementSection);
		addCheckboxesCreation(elementSection, INTERFACE__CHECKBOXES);
	}

	@Override
	protected void sourceFolderChanged() {
		super.sourceFolderChanged();
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		String sourcePath = getSourceText();
		if (sourcePath == null || sourcePath.length() == 0) {
			return;
		}

		IProject currentProject = workspaceRoot.getProject(getProjectName(sourcePath));
		IScriptProject model = DLTKCore.create(currentProject);

		// check that interfaces exist in model
		if (getInterfaces().size() > 0) {
			validateInterfaces(model);
		}
	}

	/**
	 * Finds the most severe error (if there is one)
	 */
	@Override
	protected IStatus findMostSevereStatus() {
		return StatusUtil.getMostSevere(new IStatus[] { elementNameStatus, sourceFolderStatus, newFileStatus,
				existingFileStatus, interfacesStatus, namespaceStatus });
	}

	/**
	 * This method was overriden to handle cases in which project's PHP version
	 * is less than 5
	 */
	@Override
	public void setVisible(boolean visible) {
		if (!visible) {
			super.setVisible(visible);
			return;
		}
		super.setVisible(visible);
	}
}
