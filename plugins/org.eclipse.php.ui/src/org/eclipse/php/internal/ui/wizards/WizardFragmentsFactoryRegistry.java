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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	private HashMap fragments;

	private static WizardFragmentsFactoryRegistry instance;

	private HashMap factories;

	// private ICompositeFragmentFactory[] factories;

	/**
	 * Returns an array on newly initialized WizardFragments that complies to
	 * the given server type id. The returned fragments array contains a union
	 * of the server specific fragments and the global fragments that can be
	 * defined by adding a fragments extention with a visibility of 'Always'.
	 * 
	 * @param serverType
	 *            The id of the server.
	 * @return An array of ICompositeFragmentFactory.
	 */
	public static ICompositeFragmentFactory[] getFragmentsFactories(
			String fragmentsGroupID) {
		WizardFragmentsFactoryRegistry registry = getInstance();
		ICompositeFragmentFactory[] factories = (ICompositeFragmentFactory[]) registry.factories
				.get(fragmentsGroupID);
		if (factories == null) {
			List fragments = (List) registry.fragments.get(fragmentsGroupID);
			List factoriesList = new ArrayList();
			for (int i = 0; i < fragments.size(); i++) {
				FragmentsFactory factory = (FragmentsFactory) fragments.get(i);
				factoriesList.add(factory.createFragmentFactory());
			}
			factories = new ICompositeFragmentFactory[factoriesList.size()];
			factoriesList.toArray(factories);
			registry.factories.put(fragmentsGroupID, factories);
			// Clear the fragments mapping, since it is no longer needed.
			registry.fragments.remove(fragmentsGroupID);
		}
		return factories;
	}

	private WizardFragmentsFactoryRegistry() {
		factories = new HashMap(4);
		fragments = new HashMap(5);
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPUiPlugin.ID,
						EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (FRAGMENT_TAG.equals(element.getName())) {
				String id = element.getAttribute(ID_ATTRIBUTE);
				String groupID = element.getAttribute(FRAGMENTS_GROUP_ID);
				String placeAfter = element.getAttribute(PLACE_AFTER_ATTRIBUTE);
				ArrayList list = (ArrayList) fragments.get(groupID);
				if (list == null) {
					list = new ArrayList(5);
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
		Iterator keys = fragments.keySet().iterator();
		while (keys.hasNext()) {
			sortFragmentsByPlace((ArrayList) fragments.get(keys.next()));
		}
	}

	// Sort the fragments according to the 'place-after' attribute
	private void sortFragmentsByPlace(ArrayList fragments) {
		// Scan the fragments and separate the fragments that lacks the
		// place-after property from
		// those that have it.
		ArrayList rootsFragments = new ArrayList(fragments.size());
		ArrayList nonRootFragments = new ArrayList(fragments.size());
		for (int i = 0; i < fragments.size(); i++) {
			FragmentsFactory factory = (FragmentsFactory) fragments.get(i);
			if (factory.getPlaceAfter() == null
					|| factory.getPlaceAfter().equals("")) { //$NON-NLS-1$
				addAsList(rootsFragments, factory);
			} else {
				addAsList(nonRootFragments, factory);
			}
		}

		// Traverse over the non-root fragments and position them.
		for (int i = 0; i < nonRootFragments.size(); i++) {
			ArrayList fragmentsGroup = (ArrayList) nonRootFragments.get(i);
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
				FragmentsFactory invalidFactory = getFactory(nonRootFragments,
						i);
				addAsList(rootsFragments, invalidFactory);
				PHPUiPlugin
						.log(new Status(
								IStatus.WARNING,
								PHPUiPlugin.ID,
								0,
								"Invalid 'placeAfter' id (" + invalidFactory.getPlaceAfter() + ')', null)); //$NON-NLS-1$
			}
		}

		// At this stage, the root fragments should hold all the fragments
		// sorted.
		fragments.clear();
		for (int i = 0; i < rootsFragments.size(); i++) {
			List list = (List) rootsFragments.get(i);
			for (int j = 0; j < list.size(); j++) {
				fragments.add(list.get(j));
			}
		}
	}

	private boolean placeFragment(List targetFactories, ArrayList factoriesGroup) {
		if (factoriesGroup == null || factoriesGroup.size() == 0) {
			return true;
		}
		FragmentsFactory factory = (FragmentsFactory) factoriesGroup.get(0);
		String placeAfter = factory.getPlaceAfter();
		for (int i = 0; i < targetFactories.size(); i++) {
			List list = (List) targetFactories.get(i);
			for (int j = 0; j < list.size(); j++) {
				FragmentsFactory nextFactory = (FragmentsFactory) list.get(j);
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

	private FragmentsFactory getFactory(List nonRootFragments, int i) {
		List list = (List) nonRootFragments.get(i);
		return (FragmentsFactory) list.get(0);
	}

	// add an element to a List by wrapping it in another List.
	private void addAsList(List target, Object element) {
		List list = new ArrayList(3);
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

		public FragmentsFactory(IConfigurationElement element, String id,
				String placeAfter) {
			this.element = element;
			this.id = id;
			this.placeAfter = placeAfter;
		}

		public ICompositeFragmentFactory createFragmentFactory() {
			SafeRunner
					.run(new SafeRunnable(
							"Error creation extension for extension-point org.eclipse.php.server.ui.wizardAndCompositeFragments") { //$NON-NLS-1$
						public void run() throws Exception {
							factory = (ICompositeFragmentFactory) element
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
	}
}