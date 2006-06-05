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
package org.eclipse.php.debug.ui.preferences;

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;

/**
 * Registry class for all IPHPDebugPreferencesPageAddons extentions.
 * @author shalom
 *
 */
public class PHPDebugPreferencesAddonRegistry {

	private static final String EXTENSION_POINT_NAME = "phpDebugPreferencesAddon"; //$NON-NLS-1$
	private static final String DEBUG_PREFERENCES_ADDON_TAG = "debugPreferencesAddon"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PREFERENCES_PAGE_ID_ATTRIBUTE = "preferencesPageId"; //$NON-NLS-1$
	private static final String TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

	private static final String WORKSPACE_TYPE = "Workspace";
	private static final String PROJECT_SPECIFIC_TYPE = "Project Specific";

	/*
	 * Debug preferences addons stored by the ID of the debug preferences page.
	 * This group of addons are for the worspace scope.
	 * Since multiple addons can be added to the same preferences page, the values are
	 * stored as a List.
	 */
	private Dictionary workspaceTypeAddons = new Hashtable();

	/*
	 * Debug preferences addons stored by the ID of the debug preferences page.
	 * This group of addons are for the project-specific scope.
	 * Since multiple addons can be added to the same preferences page, the values are
	 * stored as a List.
	 */
	private Dictionary projectTypeAddons = new Hashtable();

	/** Instance of this registry */
	private static PHPDebugPreferencesAddonRegistry instance = null;
	private static Comparator pageAddonComparator = new PageAddonComparator();

	private PHPDebugPreferencesAddonRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugUIPlugin.getID(), EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (DEBUG_PREFERENCES_ADDON_TAG.equals(element.getName())) {
				String preferencesPageID = element.getAttribute(PREFERENCES_PAGE_ID_ATTRIBUTE);
				if (PROJECT_SPECIFIC_TYPE.equalsIgnoreCase(element.getAttribute(TYPE_ATTRIBUTE))) {
					addAddon(projectTypeAddons, preferencesPageID, element);
				} else {
					addAddon(workspaceTypeAddons, preferencesPageID, element);
				}
			}
		}
	}

	private void addAddon(Dictionary dictionary, String preferencesPageID, IConfigurationElement element) {
		List list = (List) dictionary.get(preferencesPageID);
		if (list == null) {
			list = new ArrayList(5);
		}
		list.add(new PHPDebugPreferencesAddonFactory(element));
		dictionary.put(preferencesPageID, list);
	}

	private Dictionary getWorkspaceAddons() {
		return workspaceTypeAddons;
	}

	private Dictionary getProjectSpecificAddons() {
		return projectTypeAddons;
	}

	private static PHPDebugPreferencesAddonRegistry getInstance() {
		if (instance == null) {
			instance = new PHPDebugPreferencesAddonRegistry();
		}
		return instance;
	}

	/**
	 * Return debug preferences page workspace addons according to its (the preferences page) ID.
	 * The returned IPHPDebugPreferencesPageAddon is always a new instance.
	 *
	 * @param preferencesPageID The debug preferences page ID
	 * @return An array of newly instanciated IPHPDebugPreferencesPageAddons (an empty array, if non exists).
	 * 	Note: The returned order of the addons is by their ID.
	 */
	public static IPHPDebugPreferencesPageAddon[] getDebugPreferencesWorkspaceAddon(String preferencesPageID) throws Exception {
		List addonFactories = (List) getInstance().getWorkspaceAddons().get(preferencesPageID);
		IPHPDebugPreferencesPageAddon[] addons = getAddons(addonFactories);
		Arrays.sort(addons, pageAddonComparator);
		return addons;
	}

	/**
	 * Return debug preferences page project-specific addons according to its (the preferences page) ID.
	 * The returned IPHPDebugPreferencesPageAddon is always a new instance.
	 *
	 * @param preferencesPageID The debug preferences page ID
	 * @return An array of newly instanciated IPHPDebugPreferencesPageAddons (an empty array, if non exists)
	 * 	Note: The returned order of the addons is by their ID.
	 */
	public static IPHPDebugPreferencesPageAddon[] getDebugPreferencesProjectAddon(String preferencesPageID) throws Exception {
		List addonFactories = (List) getInstance().getProjectSpecificAddons().get(preferencesPageID);
		IPHPDebugPreferencesPageAddon[] addons = getAddons(addonFactories);
		Arrays.sort(addons, pageAddonComparator);
		return addons;
	}

	// Collect, initialize and return all the IPHPDebugPreferencesPageAddon from the given factories List.
	private static IPHPDebugPreferencesPageAddon[] getAddons(List addonFactories) {
		if (addonFactories == null) {
			return new IPHPDebugPreferencesPageAddon[0];
		}
		IPHPDebugPreferencesPageAddon[] addons = new IPHPDebugPreferencesPageAddon[addonFactories.size()];
		for (int i = 0; i < addons.length; i++) {
			addons[i] = ((PHPDebugPreferencesAddonFactory) addonFactories.get(i)).createDebugPreferencesAddon();
		}
		return addons;
	}

	/**
	 * Instantiation proxy of the PHP debug preferences page addon object
	 */
	class PHPDebugPreferencesAddonFactory {

		private IConfigurationElement element;
		private IPHPDebugPreferencesPageAddon preferencesPageAddon;

		public PHPDebugPreferencesAddonFactory(IConfigurationElement element) {
			this.element = element;
		}

		public IPHPDebugPreferencesPageAddon createDebugPreferencesAddon() {
			Platform.run(new SafeRunnable("Error creation extension for extension-point org.eclipse.php.debug.ui.phpDebugPreferencesAddon") {
				public void run() throws Exception {
					preferencesPageAddon = (IPHPDebugPreferencesPageAddon) element.createExecutableExtension(CLASS_ATTRIBUTE);
					preferencesPageAddon.setComparableName(element.getAttribute(NAME_ATTRIBUTE));
				}
			});
			return preferencesPageAddon;
		}
	}

	static class PageAddonComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			IPHPDebugPreferencesPageAddon firstAddon = (IPHPDebugPreferencesPageAddon) o1;
			IPHPDebugPreferencesPageAddon secondAddon = (IPHPDebugPreferencesPageAddon) o2;
			if (firstAddon != null && secondAddon != null) {
				return firstAddon.getComparableName().compareTo(secondAddon.getComparableName());
			}
			if (firstAddon == null) {
				return secondAddon == null ? 0 : -1;
			}
			return 1;
		}
	}
}