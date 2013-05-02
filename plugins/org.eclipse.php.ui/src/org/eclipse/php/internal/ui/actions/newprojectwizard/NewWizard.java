/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions.newprojectwizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

/**
 * The new wizard is responsible for allowing the user to choose which new
 * (nested) wizard to run. The set of available new wizards comes from the new
 * extension point.
 */
public class NewWizard extends Wizard {
	private static final String EXAMPLE = "example"; //$NON-NLS-1$

	private static final String ZEND = ".zend."; //$NON-NLS-1$

	private static final String CATEGORY_SEPARATOR = "/"; //$NON-NLS-1$

	private String categoryId = null;

	private NewWizardSelectionPage mainPage;

	private boolean projectsOnly = false;

	private IStructuredSelection selection;

	private IWorkbench workbench;

	/**
	 * Create the wizard pages
	 */
	public void addPages() {
		IWizardCategory root = WorkbenchPlugin.getDefault()
				.getNewWizardRegistry().getRootCategory();
		IWizardDescriptor[] primary = new IWizardDescriptor[0];
		if (projectsOnly) {
			List<IWizardDescriptor> wizards = new ArrayList<IWizardDescriptor>();
			fillWizards(root, wizards);
			primary = wizards.toArray(new IWizardDescriptor[wizards.size()]);
		}

		mainPage = new NewWizardSelectionPage(workbench, selection, root,
				primary, projectsOnly);
		addPage(mainPage);
	}

	private void fillWizards(IWizardCategory root,
			List<IWizardDescriptor> wizards) {
		for (IWizardDescriptor wizardDescriptor : root.getWizards()) {
			if (wizardDescriptor.getId().toLowerCase().indexOf(ZEND) >= 0
					&& (wizardDescriptor.getCategory() == null || wizardDescriptor
							.getCategory().getId().toLowerCase()
							.indexOf(EXAMPLE) < 0)) {
				wizards.add(wizardDescriptor);
			}
		}
		for (IWizardCategory wizardCategory : root.getCategories()) {
			fillWizards(wizardCategory, wizards);
		}
	}

	/**
	 * Returns the id of the category of wizards to show or <code>null</code> to
	 * show all categories. If no entries can be found with this id then all
	 * categories are shown.
	 * 
	 * @return String or <code>null</code>.
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * Returns the child collection element for the given id
	 */
	private IWizardCategory getChildWithID(IWizardCategory parent, String id) {
		IWizardCategory[] children = parent.getCategories();
		for (int i = 0; i < children.length; ++i) {
			IWizardCategory currentChild = children[i];
			if (currentChild.getId().equals(id)) {
				return currentChild;
			}
		}
		return null;
	}

	/**
	 * Lazily create the wizards pages
	 * 
	 * @param aWorkbench
	 *            the workbench
	 * @param currentSelection
	 *            the current selection
	 */
	public void init(IWorkbench aWorkbench,
			IStructuredSelection currentSelection) {
		this.workbench = aWorkbench;
		this.selection = currentSelection;

		if (getWindowTitle() == null) {
			// No title supplied. Set the default title
			if (projectsOnly) {
				setWindowTitle(WorkbenchMessages.NewProject_title);
			} else {
				setWindowTitle(WorkbenchMessages.NewWizard_title);
			}
		}
		setDefaultPageImageDescriptor(WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_NEW_WIZ));
		setNeedsProgressMonitor(true);
	}

	/**
	 * The user has pressed Finish. Instruct self's pages to finish, and answer
	 * a boolean indicating success.
	 * 
	 * @return boolean
	 */
	public boolean performFinish() {
		// save our selection state
		mainPage.saveWidgetValues();
		// if we're finishing from the main page then perform finish on the
		// selected wizard.
		if (getContainer().getCurrentPage() == mainPage) {
			if (mainPage.canFinishEarly()) {
				IWizard wizard = mainPage.getSelectedNode().getWizard();
				wizard.setContainer(getContainer());
				return wizard.performFinish();
			}
		}
		return true;
	}

	/**
	 * Sets the id of the category of wizards to show or <code>null</code> to
	 * show all categories. If no entries can be found with this id then all
	 * categories are shown.
	 * 
	 * @param id
	 *            may be <code>null</code>.
	 */
	public void setCategoryId(String id) {
		categoryId = id;
	}

	/**
	 * Sets the projects only flag. If <code>true</code> only projects will be
	 * shown in this wizard.
	 * 
	 * @param b
	 *            if only projects should be shown
	 */
	public void setProjectsOnly(boolean b) {
		projectsOnly = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#canFinish()
	 */
	public boolean canFinish() {
		// we can finish if the first page is current and the the page can
		// finish early.
		if (getContainer().getCurrentPage() == mainPage) {
			if (mainPage.canFinishEarly()) {
				return true;
			}
		}
		return super.canFinish();
	}

}
