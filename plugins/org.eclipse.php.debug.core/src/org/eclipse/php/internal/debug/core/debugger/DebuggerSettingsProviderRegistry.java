/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.debugger;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

/**
 * Debugger settings providers registry.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebuggerSettingsProviderRegistry {

	private static Map<String, IDebuggerSettingsProvider> providersMap = null;

	protected static final String PROP_ID = "id"; //$NON-NLS-1$
	protected static final String PROP_DEBUGGER_ID = "debuggerId"; //$NON-NLS-1$
	protected static final String PROP_PROVIDER = "provider"; //$NON-NLS-1$

	/**
	 * The name of extension point to read providers from
	 */
	public static final String EXTENSION_POINT_ID = PHPDebugPlugin.getDefault()
			.getBundle().getSymbolicName()
			+ ".phpDebuggerSettingsProviders"; //$NON-NLS-1$

	/**
	 * The default instance for reading preferences
	 */
	private static DebuggerSettingsProviderRegistry instance;

	/**
	 * Returns registered providers for given debugger type id.
	 * 
	 * @param debuggerId
	 *            the related debugger id
	 * @return a debugger settings provider or <code>null</code>
	 */
	public static synchronized final IDebuggerSettingsProvider getProvider(
			String debuggerId) {
		Map<String, IDebuggerSettingsProvider> providers = getProviders();
		return providers.get(debuggerId);
	}

	/**
	 * Returns all registered providers.
	 */
	protected static final Map<String, IDebuggerSettingsProvider> getProviders() {
		if (providersMap == null) {
			providersMap = getDefault().readFromExtensionPoint();
		}
		return providersMap;
	}

	protected static DebuggerSettingsProviderRegistry getDefault() {
		if (instance == null) {
			instance = new DebuggerSettingsProviderRegistry();
		}
		return instance;
	}

	/**
	 * Reads the extension point
	 */
	protected Map<String, IDebuggerSettingsProvider> readFromExtensionPoint() {
		final Map<String, IDebuggerSettingsProvider> factories = new HashMap<String, IDebuggerSettingsProvider>();
		IConfigurationElement[] configurationElements = Platform
				.getExtensionRegistry().getConfigurationElementsFor(
						EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			IDebuggerSettingsProvider provider;
			try {
				provider = (IDebuggerSettingsProvider) createInstance(element,
						PROP_PROVIDER, IDebuggerSettingsProvider.class);
			} catch (CoreException e) {
				Logger.logException(
						"Could not instantiate debugger settings provider from extension point data.", e); //$NON-NLS-1$
				continue;
			}
			if (provider instanceof AbstractDebuggerSettingsProvider) {
				((AbstractDebuggerSettingsProvider) provider).setId(element
						.getAttribute(PROP_ID));
				((AbstractDebuggerSettingsProvider) provider).load();
			}
			factories.put(element.getAttribute(PROP_DEBUGGER_ID), provider);
		}
		return factories;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object createInstance(IConfigurationElement element,
			String propertyName, Class instanceClass) throws CoreException {
		final Object object = element.createExecutableExtension(propertyName);
		if (!instanceClass.isAssignableFrom(object.getClass())) {
			String message = String
					.format("Invalid typecast for %s", element.getAttribute(propertyName)); //$NON-NLS-1$
			IStatus status = new Status(IStatus.ERROR, PHPDebugPlugin
					.getDefault().getBundle().getSymbolicName(), message);
			throw new CoreException(status);
		}
		return object;
	}

}
