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
package org.eclipse.php.internal.ui.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.action.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.NewExampleAction;
import org.eclipse.ui.actions.NewProjectAction;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class PHPNewWizardMenu extends ContributionItem {
	private IAction showDlgAction;
	private IAction newProjectAction;
	private IAction newExampleAction;
	private Map actions = new HashMap(21);
	private boolean enabled = true;
	private IWorkbenchWindow window;

	private boolean dirty = true;
	private IMenuListener menuListener = new IMenuListener() {
		public void menuAboutToShow(IMenuManager manager) {
			manager.markDirty();
			dirty = true;
		}
	};

	/**
	 * Create a new wizard shortcut menu.
	 * <p>
	 * If the menu will appear on a semi-permanent basis, for instance within a
	 * toolbar or menubar, the value passed for <code>register</code> should be
	 * true. If set, the menu will listen to perspective activation and update
	 * itself to suit. In this case clients are expected to call
	 * <code>deregister</code> when the menu is no longer needed. This will
	 * unhook any perspective listeners.
	 * </p>
	 * 
	 * @param innerMgr
	 *            the location for the shortcut menu contents
	 * @param window
	 *            the window containing the menu
	 * @param register
	 *            if <code>true</code> the menu listens to perspective changes
	 *            in the window
	 */
	public PHPNewWizardMenu(IMenuManager innerMgr, IWorkbenchWindow window,
			boolean register) {
		this(window);
		fillMenu(innerMgr);
		// Must be done after constructor to ensure field initialization.
	}

	public PHPNewWizardMenu(IWorkbenchWindow window) {
		super();
		this.window = window;
		showDlgAction = ActionFactory.NEW.create(window);
		newProjectAction = new NewProjectAction(window);
		newExampleAction = new NewExampleAction(window);
	}

	/*
	 * (non-Javadoc) Fills the menu with New Wizards.
	 */
	private void fillMenu(IContributionManager innerMgr) {
		// Remove all.
		innerMgr.removeAll();

		if (this.enabled) {
			// Add new project ..
			innerMgr.add(newProjectAction);

			// Get visible actions.
			String[] actions = null;
			IWorkbenchPage page = window.getActivePage();
			if (page != null)
				actions = ((WorkbenchPage) page).getNewWizardShortcuts();

			if (actions != null) {
				if (actions.length > 0)
					innerMgr.add(new Separator());
				for (int i = 0; i < actions.length; i++) {
					String id = actions[i];
					IAction action = getAction(id);
					if (action != null) {
						if (WorkbenchActivityHelper.filterItem(action))
							continue;
						innerMgr.add(action);
					}
				}
			}

			if (hasExamples()) {
				// Add examples ..
				innerMgr.add(new Separator());
				innerMgr.add(newExampleAction);
			}

			// Add other ..
			innerMgr.add(new Separator());
			innerMgr.add(showDlgAction);
		}
	}

	/*
	 * (non-Javadoc) Returns the action for the given wizard id, or null if not
	 * found.
	 */
	private IAction getAction(String id) {
		// Keep a cache, rather than creating a new action each time,
		// so that image caching in ActionContributionItem works.
		IAction action = (IAction) actions.get(id);
		if (action == null) {
			IWizardDescriptor wizardDesc = WorkbenchPlugin.getDefault()
					.getNewWizardRegistry().findWizard(id);
			if (wizardDesc != null) {
				action = new NewWizardShortcutAction(window, wizardDesc);
				actions.put(id, action);
				IConfigurationElement element = (IConfigurationElement) wizardDesc
						.getAdapter(IConfigurationElement.class);
				if (element != null)
					window.getExtensionTracker().registerObject(
							element.getDeclaringExtension(), action,
							IExtensionTracker.REF_WEAK);
			}
		}
		return action;
	}

	/*
	 * (non-Javadoc) Method declared on IContributionItem.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/*
	 * (non-Javadoc) Method declared on IContributionItem.
	 */
	public boolean isDynamic() {
		return true;
	}

	/*
	 * (non-Javadoc) Method declared on IContributionItem.
	 */
	public boolean isDirty() {
		return dirty;
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

	/**
	 * Removes all listeners from the containing workbench window.
	 * <p>
	 * This method should only be called if the shortcut menu is created with
	 * <code>register = true</code>.
	 * </p>
	 * 
	 * @deprecated
	 */
	public void deregisterListeners() {
	}

	/*
	 * (non-Javadoc) Method declared on IContributionItem.
	 */
	public void fill(Menu menu, int index) {
		if (getParent() instanceof MenuManager)
			((MenuManager) getParent()).addMenuListener(menuListener);

		if (!dirty)
			return;

		MenuManager manager = new MenuManager();
		fillMenu(manager);
		IContributionItem items[] = manager.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].fill(menu, index++);
		}
		dirty = false;
	}

	protected boolean registryHasCategory(String categoryId) {
		return WorkbenchPlugin.getDefault().getNewWizardRegistry()
				.findCategory(categoryId) != null;
	}

	private boolean hasExamples() {
		return registryHasCategory(WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY);
	}

}
