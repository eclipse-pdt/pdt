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

import java.util.*;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;

/**
 * Server wizard fragments registry for all the wizardAndCompositeFragments
 * extentions.
 */
public class WizardFragmentsFactoryRegistry {

	private static final String EXTENSION_POINT_NAME = "wizardAndCompositeFragments"; //$NON-NLS-1$
	private static final String FRAGMENT_TAG = "wizardAndCompositeFragment"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String FRAGMENTS_GROUP_ID = "fragmentsGroupID"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PLACE_AFTER_ATTRIBUTE = "placeAfter"; //$NON-NLS-1$

	// Hold a Dictionary of Lists that contains the factories used for the
	// creation of the fragments.
	// This structure will be deleted from the memory once all the factories
	// were created.
	private Map<String, List<FragmentsFactory>> fragments;

	private static WizardFragmentsFactoryRegistry instance;

	private Map<String, Map<String, ICompositeFragmentFactory>> factories;

	public static Map<String, ICompositeFragmentFactory> getFragmentsFactories(String fragmentsGroupID) {
		WizardFragmentsFactoryRegistry registry = getInstance();
		Map<String, ICompositeFragmentFactory> factories = registry.factories.get(fragmentsGroupID);
		if (factories == null) {
			factories = new LinkedHashMap<>();
			List<FragmentsFactory> fragments = registry.fragments.get(fragmentsGroupID);
			for (int i = 0; i < fragments.size(); i++) {
				FragmentsFactory factory = fragments.get(i);
				factories.put(factory.getID(), factory.createFragmentFactory());
			}
			registry.factories.put(fragmentsGroupID, factories);
			// Clear the fragments mapping, since it is no longer needed.
			registry.fragments.remove(fragmentsGroupID);
		}
		return factories;
	}

	private WizardFragmentsFactoryRegistry() {
		factories = new HashMap<>();
		fragments = new HashMap<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPUiPlugin.ID, EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (FRAGMENT_TAG.equals(element.getName())) {
				String id = element.getAttribute(ID_ATTRIBUTE);
				String groupID = element.getAttribute(FRAGMENTS_GROUP_ID);
				String placeAfter = element.getAttribute(PLACE_AFTER_ATTRIBUTE);
				List<FragmentsFactory> list = fragments.get(groupID);
				if (list == null) {
					list = new ArrayList<>();
					fragments.put(groupID, list);
				}
				if (element.getNamespaceIdentifier().equals(PHPUiPlugin.ID)) {
					// Make sure that extentions that exists in this plugin will
					// appear ahead of all others
					// when the user-class calls for getFragmentsFactories().
					list.add(0, new FragmentsFactory(element, id, placeAfter));
				} else {
					list.add(new FragmentsFactory(element, id, placeAfter));
				}
			}
		}
		// Sort all the fragment groups
		Iterator<String> keys = fragments.keySet().iterator();
		while (keys.hasNext()) {
			sortFragmentsByPlace(fragments.get(keys.next()));
		}
	}

	// Sort the fragments according to the 'place-after' attribute
	private void sortFragmentsByPlace(List<FragmentsFactory> fragments) {
		// Scan the fragments and separate the fragments that lacks the
		// place-after property from
		// those that have it.
		List<List<FragmentsFactory>> rootsFragments = new ArrayList<>();
		List<List<FragmentsFactory>> nonRootFragments = new ArrayList<>();
		for (int i = 0; i < fragments.size(); i++) {
			FragmentsFactory factory = fragments.get(i);
			if (factory.getPlaceAfter() == null || factory.getPlaceAfter().equals("")) { //$NON-NLS-1$
				addAsList(rootsFragments, factory);
			} else {
				addAsList(nonRootFragments, factory);
			}
		}

		// Traverse over the non-root fragments and position them.
		for (int i = 0; i < nonRootFragments.size(); i++) {
			List<FragmentsFactory> fragmentsGroup = nonRootFragments.get(i);
			// try to move it to the roots fragments first (order is important).
			boolean moved = placeFragment(rootsFragments, fragmentsGroup);
			if (!moved) {
				// in case we can't find it there, try to move it inside the
				// non-roots fragments.
				moved = placeFragment(nonRootFragments, fragmentsGroup);
			}
			if (!moved) {
				// move it to the roots anyway, since there is an error in the
				// extention definitions.
				FragmentsFactory invalidFactory = getFactory(nonRootFragments, i);
				addAsList(rootsFragments, invalidFactory);
				PHPUiPlugin.log(new Status(IStatus.WARNING, PHPUiPlugin.ID, 0,
						"Invalid 'placeAfter' id (" + invalidFactory.getPlaceAfter() + ')', null)); //$NON-NLS-1$
			}
		}

		// At this stage, the root fragments should hold all the fragments
		// sorted.
		fragments.clear();
		for (int i = 0; i < rootsFragments.size(); i++) {
			List<FragmentsFactory> list = rootsFragments.get(i);
			for (int j = 0; j < list.size(); j++) {
				fragments.add(list.get(j));
			}
		}
	}

	private boolean placeFragment(List<List<FragmentsFactory>> targetFactories, List<FragmentsFactory> factoriesGroup) {
		if (factoriesGroup == null || factoriesGroup.size() == 0) {
			return true;
		}
		FragmentsFactory factory = factoriesGroup.get(0);
		String placeAfter = factory.getPlaceAfter();
		for (int i = 0; i < targetFactories.size(); i++) {
			List<FragmentsFactory> list = targetFactories.get(i);
			for (int j = 0; j < list.size(); j++) {
				FragmentsFactory nextFactory = list.get(j);
				if (nextFactory.getID().equals(placeAfter)) {
					// This list is the list we should add to
					if (list.size() > j + 1) {
						list.addAll(j + 1, factoriesGroup);
					} else {
						// add it to the end
						list.addAll(factoriesGroup);
					}
					return true;
				}
			}
		}
		return false;
	}

	private FragmentsFactory getFactory(List<List<FragmentsFactory>> nonRootFragments, int i) {
		List<FragmentsFactory> list = nonRootFragments.get(i);
		return list.get(0);
	}

	// add an element to a List by wrapping it in another List.
	private void addAsList(List<List<FragmentsFactory>> target, FragmentsFactory element) {
		List<FragmentsFactory> list = new ArrayList<>();
		list.add(element);
		target.add(list);
	}

	private static WizardFragmentsFactoryRegistry getInstance() {
		if (instance == null) {
			instance = new WizardFragmentsFactoryRegistry();
		}
		return instance;
	}

	private class FragmentsFactory {

		private IConfigurationElement element;
		private ICompositeFragmentFactory factory;
		private String id;
		private String placeAfter;

		public FragmentsFactory(IConfigurationElement element, String id, String placeAfter) {
			this.element = element;
			this.id = id;
			this.placeAfter = placeAfter;
		}

		public ICompositeFragmentFactory createFragmentFactory() {
			SafeRunner.run(new SafeRunnable(
					"Error creation extension for extension-point org.eclipse.php.server.ui.wizardAndCompositeFragments") { //$NON-NLS-1$
				@Override
				public void run() throws Exception {
					factory = (ICompositeFragmentFactory) element.createExecutableExtension(CLASS_ATTRIBUTE);
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
	}

}