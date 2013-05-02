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
package org.eclipse.php.internal.debug.ui.launching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;

public class LaunchConfigurationsTabsRegistry {

	private static final String EXTENSION_POINT_NAME = "launchConfigurationTabs"; //$NON-NLS-1$
	private static final String TAB_TAG = "launchConfigurationTab"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String GROUP_ID_ATTRIBUTE = "launchConfigurationTabGroupId"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PLACE_AFTER_ATTRIBUTE = "placeAfter"; //$NON-NLS-1$
	private static final String MODES_ATTRIBUTE = "modes"; //$NON-NLS-1$

	// Hold a Dictionary of Lists that contains the factories used for the
	// creation of the tabs.
	private List factories = new ArrayList(5);

	private static LaunchConfigurationsTabsRegistry instance;

	/**
	 * Returns an array on newly initialized AbstractLaunchConfigurationTab that
	 * are registered for the requested configuration tab group.
	 * 
	 * @param launchConfigurationTabGroupId
	 *            The group id for the requested launch configuration tabs.
	 * @param mode
	 *            The launch mode requested.
	 * @return An array of AbstractLaunchConfigurationTab.
	 */
	public static AbstractLaunchConfigurationTab[] getLaunchTabs(
			String launchConfigurationTabGroupId, String mode) {
		LaunchConfigurationsTabsRegistry registry = getInstance();
		List fragments = registry.factories;
		List factoriesList = new ArrayList();
		for (int i = 0; i < fragments.size(); i++) {
			TabFactory factory = (TabFactory) fragments.get(i);
			boolean modeOK = factory.getModes().length() == 0
					|| factory.getModes().indexOf(mode) > -1;
			// Sort out only the tabs that are related to the requested
			// configuration tab group and launch mode.
			if (factory.getGroupID().equals(launchConfigurationTabGroupId)
					&& modeOK) {
				factoriesList.add(factory.createFragmentFactory());
			}
		}
		AbstractLaunchConfigurationTab[] tabs = new AbstractLaunchConfigurationTab[factoriesList
				.size()];
		factoriesList.toArray(tabs);
		return tabs;
	}

	private LaunchConfigurationsTabsRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPDebugUIPlugin.ID,
						EXTENSION_POINT_NAME);
		ArrayList mightOverride = new ArrayList(5);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (TAB_TAG.equals(element.getName())) {
				String id = element.getAttribute(ID_ATTRIBUTE);
				String groupId = element.getAttribute(GROUP_ID_ATTRIBUTE);
				String placeAfter = element.getAttribute(PLACE_AFTER_ATTRIBUTE);
				String modes = element.getAttribute(MODES_ATTRIBUTE);
				if (element.getNamespaceIdentifier()
						.equals(PHPDebugUIPlugin.ID)
						|| element.getNamespaceIdentifier().startsWith(
								"org.eclipse.php.server.")) { //$NON-NLS-1$
					// Make sure that extentions that exists in this plugin will
					// appear ahead of all others
					// when the user-class calls for getLaunchTabs().
					factories.add(0, new TabFactory(element, groupId, id,
							placeAfter, modes));
				} else {
					boolean override = false;
					for (int j = 0; !override && j < factories.size(); j++) {
						// Check for overriding id's.
						// Override as needed when the element namespace
						// identifier is from
						// an external plugin. The procedure will take the first
						// overidden id.
						TabFactory factory = (TabFactory) factories.get(j);
						if (id.equals(factory.id)) {
							override = true;
							if (!element.getNamespaceIdentifier().startsWith(
									"org.eclipse.php.")) { //$NON-NLS-1$
								factories.remove(j);
								factories.add(new TabFactory(element, groupId,
										id, placeAfter, modes));
								break;
							}
						}
					}
					if (!override) {
						mightOverride.add(new TabFactory(element, groupId, id,
								placeAfter, modes));
					}
				}
			}
		}
		// Check again for overriding extensions.
		// This check is needed since we cannot trust the extension loading
		// order.
		for (int i = 0; i < mightOverride.size(); i++) {
			TabFactory tag = (TabFactory) mightOverride.get(i);
			int index = factories.indexOf(tag);
			if (index > -1) {
				// Found one that needs to be overidden.
				factories.set(index, tag);
			} else {
				// Did not found any to override, so simply add it.
				factories.add(tag);
			}
		}

		sortFragmentsByPlace();
	}

	// Sort the factories according to the 'placeAfter' attribute
	private void sortFragmentsByPlace() {
		// Scan the factories and separate the factories that lacks the
		// place-after property from
		// those that have it.
		ArrayList rootsFragments = new ArrayList(factories.size());
		ArrayList nonRootFragments = new ArrayList(factories.size());
		for (int i = 0; i < factories.size(); i++) {
			TabFactory factory = (TabFactory) factories.get(i);
			if (factory.getPlaceAfter() == null
					|| factory.getPlaceAfter().equals("")) { //$NON-NLS-1$
				addAsList(rootsFragments, factory);
			} else {
				addAsList(nonRootFragments, factory);
			}
		}

		// Traverse over the non-root factories and position them.
		for (int i = 0; i < nonRootFragments.size(); i++) {
			TabFactory factory = getFactory(nonRootFragments, i);
			// try to move it to the roots factories first (order is important).
			boolean moved = placeFragment(rootsFragments, factory);
			if (!moved) {
				// in case we can't find it there, try to move it inside the
				// non-roots factories.
				moved = placeFragment(nonRootFragments, factory);
			}
			if (!moved) {
				// move it to the roots anyway, since there is an error in the
				// extention definitions.
				addAsList(rootsFragments, factory);
				Logger
						.log(
								Logger.WARNING,
								"Invalid 'placeAfter' id (" + factory.getPlaceAfter() + ')'); //$NON-NLS-1$
			}
		}

		// At this stage, the root fragments should hold all the fragments
		// sorted.
		factories.clear();
		for (int i = 0; i < rootsFragments.size(); i++) {
			List list = (List) rootsFragments.get(i);
			for (int j = 0; j < list.size(); j++) {
				factories.add(list.get(j));
			}
		}
	}

	private boolean placeFragment(List factories, TabFactory factory) {
		String placeAfter = factory.getPlaceAfter();
		for (int i = 0; i < factories.size(); i++) {
			List list = (List) factories.get(i);
			for (int j = 0; j < list.size(); j++) {
				TabFactory nextFactory = (TabFactory) list.get(j);
				if (nextFactory.getID().equals(placeAfter)) {
					// This list is the list we should add to
					list.add(factory);
					return true;
				}
			}
		}
		return false;
	}

	private TabFactory getFactory(List nonRootFragments, int i) {
		List list = (List) nonRootFragments.get(i);
		return (TabFactory) list.get(0);
	}

	// add an element to a List by wrapping it in another List.
	private void addAsList(List target, Object element) {
		List list = new ArrayList(3);
		list.add(element);
		target.add(list);
	}

	private static LaunchConfigurationsTabsRegistry getInstance() {
		if (instance == null) {
			instance = new LaunchConfigurationsTabsRegistry();
		}
		return instance;
	}

	private class TabFactory {

		private IConfigurationElement element;
		private AbstractLaunchConfigurationTab factory;
		private String id;
		private String groupId;
		private String placeAfter;
		private String modes;

		public TabFactory(IConfigurationElement element, String groupId,
				String id, String placeAfter, String modes) {
			this.element = element;
			this.groupId = groupId;
			this.id = id;
			this.placeAfter = placeAfter;
			this.modes = modes;
		}

		public AbstractLaunchConfigurationTab createFragmentFactory() {
			SafeRunner
					.run(new SafeRunnable(
							"Error creation extension for extension-point org.eclipse.php.server.ui.serverTabs") { //$NON-NLS-1$
						public void run() throws Exception {
							factory = (AbstractLaunchConfigurationTab) element
									.createExecutableExtension(CLASS_ATTRIBUTE);
						}
					});
			return factory;
		}

		public String getID() {
			return id;
		}

		public String getPlaceAfter() {
			return placeAfter;
		}

		public String getGroupID() {
			return groupId;
		}

		public String getModes() {
			if (modes == null) {
				modes = ""; //$NON-NLS-1$
			}
			return modes;
		}

		public boolean equals(Object other) {
			if (other == this) {
				return true;
			}
			if (other instanceof TabFactory) {
				return getID().equals(((TabFactory) other).getID());
			}
			return false;
		}
	}
}