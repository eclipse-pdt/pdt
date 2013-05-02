/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.php.internal.ui.actions.newprojectwizard.NewProjectAction;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.BaseNewWizardMenu;
import org.eclipse.ui.actions.NewExampleAction;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

/**
 * A <code>NewWizardMenu</code> augments <code>BaseNewWizardMenu</code> with
 * IDE-specific actions: New Project... (always shown) and New Example... (shown
 * only if there are example wizards installed).
 */
public class NewWizardMenu extends BaseNewWizardMenu {

	private static final List<String> PROJECT_WIZARD_ID = new ArrayList<String>();
	static {
		PROJECT_WIZARD_ID
				.add("org.eclipse.php.ui.wizards.PHPFileCreationWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("com.zend.php.ui.wizards.phpElementsWizard.NewPHPClassWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("com.zend.php.ui.wizards.phpElementsWizard.NewPHPInterfaceWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("com.zend.php.ui.wizards.phpElementsWizard.NewPHPTraitWizard"); //$NON-NLS-1$

		PROJECT_WIZARD_ID
				.add("org.eclipse.php.ui.wizards.UntitledPHPDocumentWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID.add("org.eclipse.ui.wizards.new.folder"); //$NON-NLS-1$
		PROJECT_WIZARD_ID.add("org.eclipse.ui.wizards.new.file"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("org.eclipse.wst.css.ui.internal.wizard.NewCSSWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("org.eclipse.wst.html.ui.internal.wizard.NewHTMLWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("org.eclipse.wst.xml.ui.internal.wizards.NewXMLWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("org.eclipse.ui.editors.wizards.UntitledTextFileWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("org.zend.php.framework.ui.wizards.NewZendItemWizard"); //$NON-NLS-1$

		PROJECT_WIZARD_ID
				.add("com.zend.php.ui.wizards.wizards.RemoteFolderWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID.add("org.eclipse.php.wst.jsdt.ui.NewJSWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID
				.add("org.eclipse.mylyn.tasks.ui.wizards.new.repository.task"); //$NON-NLS-1$
		PROJECT_WIZARD_ID.add("com.zend.php.phpunit.wizards.TestCaseWizard"); //$NON-NLS-1$
		PROJECT_WIZARD_ID.add("com.zend.php.phpunit.wizards.TestSuiteWizard"); //$NON-NLS-1$
	}
	private final IAction newExampleAction;
	private final IAction newProjectAction;

	private boolean enabled = true;
	private boolean isProject = true;

	/**
	 * Creates a new wizard shortcut menu for the IDE.
	 * 
	 * @param window
	 *            the window containing the menu
	 * @param isProject
	 */
	public NewWizardMenu(IWorkbenchWindow window, boolean isProject) {
		this(window, null, isProject);

	}

	/**
	 * Creates a new wizard shortcut menu for the IDE.
	 * 
	 * @param window
	 *            the window containing the menu
	 * @param id
	 *            the identifier for this contribution item
	 * @param isProject
	 */
	public NewWizardMenu(IWorkbenchWindow window, String id, boolean isProject) {
		super(window, id);
		newExampleAction = new NewExampleAction(window);
		newProjectAction = new NewProjectAction(window);
		this.isProject = isProject;
	}

	/**
	 * Removes all listeners from the containing workbench window.
	 * <p>
	 * This method should only be called if the shortcut menu is created with
	 * <code>register = true</code>.
	 * </p>
	 * 
	 * @deprecated has no effect
	 */
	public void deregisterListeners() {
		// do nothing
	}

	/**
	 * Return whether or not any examples are in the current install.
	 * 
	 * @return boolean
	 */
	private boolean hasExamples() {
		boolean hasCategory = registryHasCategory(WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY);
		if (hasCategory) {
			IWizardCategory exampleCategory = WorkbenchPlugin
					.getDefault()
					.getNewWizardRegistry()
					.findCategory(
							WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY);
			return hasWizards(exampleCategory);
		}
		return false;
	}

	private boolean hasWizards(IWizardCategory category) {
		IWizardDescriptor[] wizards = category.getWizards();
		if (wizards.length > 0) {
			for (int i = 0; i < wizards.length; i++) {
				if (!WorkbenchActivityHelper.filterItem(wizards[i])) {
					return true;
				}
			}
		}
		IWizardCategory[] categories = category.getCategories();
		for (int i = 0; i < categories.length; i++) {
			if (hasWizards(categories[i])) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.BaseNewWizardMenu#addItems(org.eclipse.jface.action
	 * .IContributionManager)
	 */
	protected void addItems(List list) {
		ArrayList shortCuts = new ArrayList();
		addShortcuts(shortCuts);

		for (Iterator iterator = shortCuts.iterator(); iterator.hasNext();) {
			Object curr = iterator.next();
			if (curr instanceof ActionContributionItem
					&& isNewProjectWizardAction(((ActionContributionItem) curr)
							.getAction())) {
				iterator.remove();
				// list.add(curr);
			}
		}
		list.add(new ActionContributionItem(newProjectAction));
		list.add(new Separator());

		shortCuts = sortShortcuts(shortCuts);
		if (!isProject) {
			if (!shortCuts.isEmpty()) {
				list.addAll(shortCuts);
				list.add(new Separator());
			}
		}
		if (hasExamples()) {
			list.add(new ActionContributionItem(newExampleAction));
			list.add(new Separator());
		}
		list.add(new ActionContributionItem(getShowDialogAction()));
	}

	private ArrayList sortShortcuts(ArrayList shortCuts) {
		ArrayList result = new ArrayList();
		for (String id : PROJECT_WIZARD_ID) {
			for (Iterator iterator = shortCuts.iterator(); iterator.hasNext();) {
				Object curr = iterator.next();
				if (curr instanceof ActionContributionItem) {
					ActionContributionItem item = (ActionContributionItem) curr;
					if (item.getAction() instanceof NewWizardShortcutAction) {
						NewWizardShortcutAction action = (NewWizardShortcutAction) item
								.getAction();
						if (id.equals(action.getWizardDescriptor().getId())) {
							result.add(item);
							iterator.remove();
						}
					}
				}
			}
		}
		result.addAll(shortCuts);
		return result;
	}

	private boolean isNewProjectWizardAction(IAction action) {
		if (action instanceof NewWizardShortcutAction) {
			IWizardDescriptor wizardDescriptor = ((NewWizardShortcutAction) action)
					.getWizardDescriptor();
			String[] tags = wizardDescriptor.getTags();
			for (int i = 0; i < tags.length; i++) {
				if (WorkbenchWizardElement.TAG_PROJECT.equals(tags[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc) Method declared on IContributionItem.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled state of the receiver.
	 * 
	 * @param enabledValue
	 *            if <code>true</code> the menu is enabled; else it is disabled
	 */
	public void setEnabled(boolean enabledValue) {
		this.enabled = enabledValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.BaseNewWizardMenu#getContributionItems()
	 */
	protected IContributionItem[] getContributionItems() {
		if (isEnabled()) {
			return super.getContributionItems();
		}
		return new IContributionItem[0];
	}
}
