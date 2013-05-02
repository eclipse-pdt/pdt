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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

/**
 * @author michael
 * 
 */
public class DebugParametersInitializersRegistry {

	private static final String EXTENSION_POINT_NAME = "phpDebugParametersInitializers"; //$NON-NLS-1$
	private static final String INITIALIZER_TAG = "initializer"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String LAUNCH_CONFIGURATION_TYPE_ATTRIBUTE = "launchConfigurationType"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String HANDLER_ATTRIBUTE = "handler"; //$NON-NLS-1$
	private static final String MODE_ATTRIBUTE = "mode"; //$NON-NLS-1$
	private static final String RUN = "run"; //$NON-NLS-1$
	private static final String DEBUG = "debug"; //$NON-NLS-1$
	private static final String PROFILE = "profile"; //$NON-NLS-1$

	/** Debug parameters initializers stored by ID */
	private Dictionary runInitializers = new Hashtable();
	private Dictionary debugInitializers = new Hashtable();
	private Dictionary profileInitializers = new Hashtable();

	/** Instance of this registry */
	private static DebugParametersInitializersRegistry instance = null;

	private DebugParametersInitializersRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPDebugPlugin.getID(),
						EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (INITIALIZER_TAG.equals(element.getName())) {
				if (RUN.equals(element.getAttribute(MODE_ATTRIBUTE))) {
					runInitializers.put(element.getAttribute(ID_ATTRIBUTE),
							new DebugParametersInitializerFactory(element));
				} else if (PROFILE.equals(element.getAttribute(MODE_ATTRIBUTE))) {
					profileInitializers.put(element.getAttribute(ID_ATTRIBUTE),
							new DebugParametersInitializerFactory(element));
				} else {
					debugInitializers.put(element.getAttribute(ID_ATTRIBUTE),
							new DebugParametersInitializerFactory(element));
				}
			}
		}
	}

	private static DebugParametersInitializersRegistry getInstance() {
		if (instance == null) {
			instance = new DebugParametersInitializersRegistry();
		}
		return instance;
	}

	private Dictionary getInitializersFactories(String mode) {
		if (RUN.equals(mode)) {
			return runInitializers;
		}
		if (PROFILE.equals(mode)) {
			return profileInitializers;
		}
		return debugInitializers;
	}

	/**
	 * Return debug parameters initializer according to its ID. The returned
	 * IDebugParametersInitializer is always a new instance.
	 * 
	 * @param id
	 *            The debug parameters initializer ID
	 * @return A new instance of an IDebugParametersInitializer
	 */
	public static IDebugParametersInitializer getParametersInitializer(String id)
			throws Exception {
		DebugParametersInitializerFactory initializerFactory = (DebugParametersInitializerFactory) getInstance()
				.getInitializersFactories(RUN).get(id);
		if (initializerFactory == null) {
			initializerFactory = (DebugParametersInitializerFactory) getInstance()
					.getInitializersFactories(PROFILE).get(id);
		}
		if (initializerFactory == null) {
			initializerFactory = (DebugParametersInitializerFactory) getInstance()
					.getInitializersFactories(DEBUG).get(id);
		}
		if (initializerFactory != null) {
			return initializerFactory.createParametersInitializer();
		}
		return null;
	}

	/**
	 * Returns the currently configured IDebugParametersInitializer. The
	 * returned IDebugParametersInitializer is always a new instance.
	 * 
	 * @return A new instance of the current IDebugParametersInitializer
	 *         (according to the preferences).
	 */
	public static IDebugParametersInitializer getCurrentDebugParametersInitializer() {
		try {
			String id = PHPDebugPlugin
					.getDefault()
					.getPluginPreferences()
					.getString(
							IPHPDebugConstants.PHP_DEBUG_PARAMETERS_INITIALIZER);
			return getParametersInitializer(id);
		} catch (Exception e) {
			PHPDebugPlugin.log(e);
		}
		return null;
	}

	/**
	 * Return best matching debug parameters initializer according the the
	 * launch The returned IDebugParametersInitializer is always a new instance.
	 * 
	 * @param launch
	 * @return A new instance of a best match IDebugParametersInitializer
	 */
	public static IDebugParametersInitializer getBestMatchDebugParametersInitializer(
			ILaunch launch) {
		try {
			Dictionary factories = getInstance().getInitializersFactories(
					launch.getLaunchMode());

			// 1st try to get the one with matching configuration type
			for (Enumeration e = factories.elements(); e.hasMoreElements();) {
				DebugParametersInitializerFactory initializerFactory = (DebugParametersInitializerFactory) e
						.nextElement();
				String configurationTypeId = initializerFactory.element
						.getAttribute(LAUNCH_CONFIGURATION_TYPE_ATTRIBUTE);
				if (configurationTypeId != null
						&& !"".equals(configurationTypeId) //$NON-NLS-1$
						&& configurationTypeId.equals(launch
								.getLaunchConfiguration().getType()
								.getIdentifier())) {
					return initializerFactory.createParametersInitializer();
				}
			}
			// Then if not found try to get with empty configuration type and
			// not a default one
			for (Enumeration e = factories.elements(); e.hasMoreElements();) {
				DebugParametersInitializerFactory initializerFactory = (DebugParametersInitializerFactory) e
						.nextElement();
				String configurationTypeId = initializerFactory.element
						.getAttribute(LAUNCH_CONFIGURATION_TYPE_ATTRIBUTE);
				if ((configurationTypeId == null || "" //$NON-NLS-1$
						.equals(configurationTypeId))
						&& !PHPDebugPlugin.getID().equals(
								initializerFactory.element
										.getNamespaceIdentifier())) {
					return initializerFactory.createParametersInitializer();
				}
			}
			// Last, if nothing found get the default
			for (Enumeration e = factories.elements(); e.hasMoreElements();) {
				DebugParametersInitializerFactory initializerFactory = (DebugParametersInitializerFactory) e
						.nextElement();
				if (PHPDebugPlugin.getID().equals(
						initializerFactory.element.getNamespaceIdentifier())) {
					return initializerFactory.createParametersInitializer();
				}
			}
		} catch (Exception e) {
			PHPDebugPlugin.log(e);
		}
		return getCurrentDebugParametersInitializer();
	}

	/**
	 * Instantiation proxy of the initializer object
	 */
	class DebugParametersInitializerFactory {

		private IConfigurationElement element;
		private IDebugParametersInitializer parametersInitializer;

		public DebugParametersInitializerFactory(IConfigurationElement element) {
			this.element = element;
		}

		public IDebugParametersInitializer createParametersInitializer() {
			SafeRunner
					.run(new SafeRunnable(
							"Error creation extension for extension-point org.eclipse.php.internal.debug.core.phpDebugParametersInitializers") { //$NON-NLS-1$
						public void run() throws Exception {
							parametersInitializer = (IDebugParametersInitializer) element
									.createExecutableExtension(CLASS_ATTRIBUTE);
						}
					});
			parametersInitializer.setDebugHandler(element
					.getAttribute(HANDLER_ATTRIBUTE));
			return parametersInitializer;
		}
	}
}