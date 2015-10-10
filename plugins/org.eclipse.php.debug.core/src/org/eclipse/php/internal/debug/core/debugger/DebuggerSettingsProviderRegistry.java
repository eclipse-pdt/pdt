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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	private class Entry {

		IConfigurationElement element;
		String id;
		String debuggerId;
		String overridenProviderId;

		public Entry(IConfigurationElement element, String id, String debuggerId, String overridenProviderId) {
			this.element = element;
			this.id = id;
			this.debuggerId = debuggerId;
			this.overridenProviderId = overridenProviderId;
		}

	}

	protected static final String PROP_ID = "id"; //$NON-NLS-1$
	protected static final String PROP_DEBUGGER_ID = "debuggerId"; //$NON-NLS-1$
	protected static final String PROP_OVERRIDES = "overrides"; //$NON-NLS-1$
	protected static final String PROP_PROVIDER = "provider"; //$NON-NLS-1$

	/**
	 * The name of extension point to read providers from
	 */
	public static final String EXTENSION_POINT_ID = PHPDebugPlugin.getDefault().getBundle().getSymbolicName()
			+ ".phpDebuggerSettingsProviders"; //$NON-NLS-1$

	/**
	 * The default instance for reading registry.
	 */
	private static DebuggerSettingsProviderRegistry instance;

	/**
	 * Returns registered providers for given debugger type id.
	 * 
	 * @param debuggerId
	 *            the related debugger id
	 * @return a debugger settings provider or <code>null</code>
	 */
	public static synchronized final IDebuggerSettingsProvider getProvider(String debuggerId) {
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
		final List<Entry> entries = new ArrayList<Entry>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			String id = element.getAttribute(PROP_ID);
			String overridenProviderId = element.getAttribute(PROP_OVERRIDES);
			String debuggerId = element.getAttribute(PROP_DEBUGGER_ID);
			entries.add(new Entry(element, id, debuggerId, overridenProviderId));
		}
		return fetchProviders(entries);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object createInstance(IConfigurationElement element, String propertyName, Class instanceClass)
			throws CoreException {
		final Object object = element.createExecutableExtension(propertyName);
		if (!instanceClass.isAssignableFrom(object.getClass())) {
			String message = String.format("Invalid typecast for %s", element.getAttribute(propertyName)); //$NON-NLS-1$
			IStatus status = new Status(IStatus.ERROR, PHPDebugPlugin.getDefault().getBundle().getSymbolicName(),
					message);
			throw new CoreException(status);
		}
		return object;
	}

	private Map<String, IDebuggerSettingsProvider> fetchProviders(List<Entry> entries) {
		Map<String, IDebuggerSettingsProvider> providers = new HashMap<String, IDebuggerSettingsProvider>();
		List<Entry> highestLevelEntries = new ArrayList<Entry>();
		for (Entry entry : entries) {
			boolean isHighestLevel = true;
			for (Entry e : entries) {
				if (entry.id.equals(e.overridenProviderId)) {
					isHighestLevel = false;
					break;
				}
			}
			if (isHighestLevel)
				highestLevelEntries.add(entry);
		}
		for (Entry entry : highestLevelEntries) {
			IDebuggerSettingsProvider provider;
			try {
				provider = (IDebuggerSettingsProvider) createInstance(entry.element, PROP_PROVIDER,
						IDebuggerSettingsProvider.class);
			} catch (CoreException e) {
				Logger.logException("Could not instantiate debugger settings provider from extension point data.", e); //$NON-NLS-1$
				continue;
			}
			if (provider instanceof AbstractDebuggerSettingsProvider) {
				((AbstractDebuggerSettingsProvider) provider).setId(entry.id);
				((AbstractDebuggerSettingsProvider) provider).load();
			}
			providers.put(entry.debuggerId, provider);
		}
		return providers;
	}

}
