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
package org.eclipse.php.server.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;

/**
 * Server wizard fragments registry for all the org.eclipse.php.server.apache.ui.serverWizardFragment extentions. 
 */
public class ServerFragmentsFactoryRegistry {

	private static final String EXTENSION_POINT_NAME = "serverWizardFragment"; //$NON-NLS-1$
	private static final String FRAGMENT_TAG = "wizardFragment"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PLACE_AFTER_ATTRIBUTE = "placeAfter"; //$NON-NLS-1$

	// Hold a Dictionary of Lists that contains the factories used for the creation of the fragments.
	private List fragments = new ArrayList(5);

	private static ServerFragmentsFactoryRegistry instance;
	private ICompositeFragmentFactory[] factories;

	/**
	 * Returns an array on newly initialized WizardFragments that complies to the given server type 
	 * id.
	 * The returned fragments array contains a union of the server specific fragments and the global
	 * fragments that can be defined by adding a fragments extention with a visibility of 'Always'.
	 * 
	 * @param serverType	The id of the server.
	 * @return	An array of ICompositeFragmentFactory.
	 */
	public static ICompositeFragmentFactory[] getFragmentsFactories() {
		ServerFragmentsFactoryRegistry registry = getInstance();
		if (registry.factories == null) {
			List fragments = registry.fragments;
			List factoriesList = new ArrayList();
			for (int i = 0; i < fragments.size(); i++) {
				FragmentsFactory factory = (FragmentsFactory) fragments.get(i);
				factoriesList.add(factory.createFragmentFactory());
			}
			registry.factories = new ICompositeFragmentFactory[factoriesList.size()];
			factoriesList.toArray(registry.factories);
		}
		return registry.factories;
	}

	private ServerFragmentsFactoryRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (FRAGMENT_TAG.equals(element.getName())) {
				String id = element.getAttribute(ID_ATTRIBUTE);
				String placeAfter = element.getAttribute(PLACE_AFTER_ATTRIBUTE);
				if (element.getNamespaceIdentifier().equals(Activator.PLUGIN_ID)) {
					// Make sure that extentions that exists in this plugin will appear ahead of all others
					// when the user-class calls for getFragmentsFactories().
					fragments.add(0, new FragmentsFactory(element, id, placeAfter));
				} else {
					fragments.add(new FragmentsFactory(element, id, placeAfter));
				}
			}
		}
		sortFragmentsByPlace();
	}

	// Sort the fragments according to the 'place-after' attribute
	private void sortFragmentsByPlace() {
		// Scan the fragments and separate the fragments that lacks the place-after property from
		// those that have it.
		ArrayList rootsFragments = new ArrayList(fragments.size());
		ArrayList nonRootFragments = new ArrayList(fragments.size());
		for (int i = 0; i < fragments.size(); i++) {
			FragmentsFactory factory = (FragmentsFactory) fragments.get(i);
			if (factory.getPlaceAfter() == null || factory.getPlaceAfter().equals("")) {
				addAsList(rootsFragments, factory);
			} else {
				addAsList(nonRootFragments, factory);
			}
		}

		// Traverse over the non-root fragments and position them.
		for (int i = 0; i < nonRootFragments.size(); i++) {
			FragmentsFactory factory = getFactory(nonRootFragments, i);
			// try to move it to the roots fragments first (order is important).
			boolean moved = placeFragment(rootsFragments, factory);
			if (!moved) {
				// in case we can't find it there, try to move it inside the non-roots fragments.
				moved = placeFragment(nonRootFragments, factory);
			}
			if (!moved) {
				// move it to the roots anyway, since there is an error in the extention definitions.
				addAsList(rootsFragments, factory);
				Logger.log(Logger.WARNING, "Invalid 'placeAfter' id (" + factory.getPlaceAfter() + ')');
			}
		}

		// At this stage, the root fragments should hold all the fragments sorted.
		fragments.clear();
		for (int i = 0; i < rootsFragments.size(); i++) {
			List list = (List) rootsFragments.get(i);
			for (int j = 0; j < list.size(); j++) {
				fragments.add(list.get(j));
			}
		}
	}

	private boolean placeFragment(List factories, FragmentsFactory factory) {
		String placeAfter = factory.getPlaceAfter();
		for (int i = 0; i < factories.size(); i++) {
			List list = (List) factories.get(i);
			for (int j = 0; j < list.size(); j++) {
				FragmentsFactory nextFactory = (FragmentsFactory) list.get(j);
				if (nextFactory.getID().equals(placeAfter)) {
					// This list is the list we should add to
					list.add(factory);
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

	private static ServerFragmentsFactoryRegistry getInstance() {
		if (instance == null) {
			instance = new ServerFragmentsFactoryRegistry();
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
			SafeRunner.run(new SafeRunnable("Error creation extension for extension-point org.eclipse.php.server.apache.ui.serverWizardFragment") {
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