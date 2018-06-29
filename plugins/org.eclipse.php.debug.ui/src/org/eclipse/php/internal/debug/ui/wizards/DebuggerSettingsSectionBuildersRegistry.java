/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;

/**
 * Debugger's settings section builders registry.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebuggerSettingsSectionBuildersRegistry {

	private static Map<String, IDebuggerSettingsSectionBuilder> buildersMap = null;

	protected static final String PROP_ID = "id"; //$NON-NLS-1$
	protected static final String PROP_DEBUGGER_SETTINGS_PROVIDER_ID = "phpDebuggerSettingsProviderId"; //$NON-NLS-1$
	protected static final String PROP_BUILDER = "builder"; //$NON-NLS-1$

	/**
	 * The name of extension point to read builders from
	 */
	public static final String EXTENSION_POINT_ID = PHPDebugUIPlugin.getDefault().getBundle().getSymbolicName()
			+ ".phpDebuggerSettingsSectionBuilders"; //$NON-NLS-1$

	/**
	 * The default instance for reading extensions
	 */
	private static DebuggerSettingsSectionBuildersRegistry instance;

	/**
	 * Returns registered settings section builder for given settings provider.
	 * 
	 * @param providerId
	 *            the settings provider id
	 * @return a section builder or <code>null</code>
	 */
	public static synchronized final IDebuggerSettingsSectionBuilder getBuilder(String providerId) {
		Map<String, IDebuggerSettingsSectionBuilder> builders = getBuilders();
		return builders.get(providerId);
	}

	/**
	 * Returns all registered section builders.
	 */
	protected static final Map<String, IDebuggerSettingsSectionBuilder> getBuilders() {
		if (buildersMap == null) {
			buildersMap = getDefault().readFromExtensionPoint();
		}
		return buildersMap;
	}

	protected static DebuggerSettingsSectionBuildersRegistry getDefault() {
		if (instance == null) {
			instance = new DebuggerSettingsSectionBuildersRegistry();
		}
		return instance;
	}

	/**
	 * Reads the extension point
	 */
	protected Map<String, IDebuggerSettingsSectionBuilder> readFromExtensionPoint() {
		final Map<String, IDebuggerSettingsSectionBuilder> factories = new HashMap<>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			IDebuggerSettingsSectionBuilder builder;
			try {
				builder = (IDebuggerSettingsSectionBuilder) createInstance(element, PROP_BUILDER,
						IDebuggerSettingsSectionBuilder.class);
			} catch (CoreException e) {
				Logger.logException(
						"Could not instantiate debugger settings section builder from extension point data.", e); //$NON-NLS-1$
				continue;
			}
			factories.put(element.getAttribute(PROP_DEBUGGER_SETTINGS_PROVIDER_ID), builder);
		}
		return factories;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object createInstance(IConfigurationElement element, String propertyName, Class instanceClass)
			throws CoreException {
		final Object object = element.createExecutableExtension(propertyName);
		if (!instanceClass.isAssignableFrom(object.getClass())) {
			String message = String.format("Invalid typecast for %s", element.getAttribute(propertyName)); //$NON-NLS-1$
			IStatus status = new Status(IStatus.ERROR, PHPDebugUIPlugin.getDefault().getBundle().getSymbolicName(),
					message);
			throw new CoreException(status);
		}
		return object;
	}

}
