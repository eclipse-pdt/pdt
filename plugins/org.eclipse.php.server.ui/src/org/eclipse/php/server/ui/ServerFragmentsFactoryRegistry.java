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
import java.util.Dictionary;
import java.util.Hashtable;
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
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String SERVER_TYPE_ATTRIBUTE = "serverTypeId"; //$NON-NLS-1$
	private static final String VISIBILITY_ATTRIBUTE = "visible"; //$NON-NLS-1$
	private static final String VISIBILITY_ALWAYS = "Always";

	// Hold a Dictionary of Lists that contains the factories used for the creation of the fragments.
	// The dictionary holds all the fragments that where assigned to appear in a specific server wizard / editorsssssssssssssssssssssssssssssssss.
	private Dictionary fragmentsByType = new Hashtable();

	// Hold the wizard fragments that should appear in all the servers wizards and editors.
	private List allWizardsfragments = new ArrayList();

	private static ServerFragmentsFactoryRegistry instance;

	/**
	 * Returns an array on newly initialized WizardFragments that complies to the given server type 
	 * id.
	 * The returned fragments array contains a union of the server specific fragments and the global
	 * fragments that can be defined by adding a fragments extention with a visibility of 'Always'.
	 * 
	 * @param serverType	The id of the server.
	 * @return	An array of ICompositeFragmentFactory.
	 */
	public static ICompositeFragmentFactory[] getFragmentsFactories(String serverType) {
		ServerFragmentsFactoryRegistry registry = getInstance();
		List factories = (List) registry.fragmentsByType.get(serverType);
		List allFragments = new ArrayList();
		if (factories != null) {
			for (int i = 0; i < factories.size(); i++) {
				FragmentsFactory factory = (FragmentsFactory) factories.get(i);
				allFragments.add(factory.createFragmentFactory());
			}
		}
		factories = registry.allWizardsfragments;
		for (int i = 0; i < factories.size(); i++) {
			FragmentsFactory factory = (FragmentsFactory) factories.get(i);
			allFragments.add(factory.createFragmentFactory());
		}
		ICompositeFragmentFactory[] fragmentFactories = new ICompositeFragmentFactory[allFragments.size()];
		allFragments.toArray(fragmentFactories);
		return fragmentFactories;
	}

	private ServerFragmentsFactoryRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (FRAGMENT_TAG.equals(element.getName())) {
				if (element.getAttribute(VISIBILITY_ATTRIBUTE).equals(VISIBILITY_ALWAYS)) {
					allWizardsfragments.add(new FragmentsFactory(element));
				} else {
					String type = element.getAttribute(SERVER_TYPE_ATTRIBUTE);
					if (type != null) {
						List list = (List) fragmentsByType.get(type.toLowerCase());
						if (list == null) {
							list = new ArrayList(5);
							fragmentsByType.put(element.getAttribute(SERVER_TYPE_ATTRIBUTE).toLowerCase(), list);
						}
						list.add(new FragmentsFactory(element));
					} else {
						Logger.log(Logger.WARNING, "A server wizard fragment was defined without a required server ID.");
					}
				}
			}
		}
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

		public FragmentsFactory(IConfigurationElement element) {
			this.element = element;
		}

		public ICompositeFragmentFactory createFragmentFactory() {
			SafeRunner.run(new SafeRunnable("Error creation extension for extension-point org.eclipse.php.server.apache.ui.serverWizardFragment") {
				public void run() throws Exception {
					factory = (ICompositeFragmentFactory) element.createExecutableExtension(CLASS_ATTRIBUTE);
				}
			});
			return factory;
		}
	}
}