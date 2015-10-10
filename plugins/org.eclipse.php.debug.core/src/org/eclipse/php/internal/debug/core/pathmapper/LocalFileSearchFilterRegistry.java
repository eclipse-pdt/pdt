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
package org.eclipse.php.internal.debug.core.pathmapper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

/**
 * Local file search filters registry.
 * 
 * @author Bartlomiej Laczkowski
 */
public class LocalFileSearchFilterRegistry {

	private static Map<String, ILocalFileSearchFilter> filtersMap = null;

	private static final String PROP_ID = "id"; //$NON-NLS-1$
	private static final String PROP_CLASS = "class"; //$NON-NLS-1$

	/**
	 * The name of extension point to read filters from
	 */
	public static final String EXTENSION_POINT_ID = PHPDebugPlugin.getDefault().getBundle().getSymbolicName()
			+ ".phpLocalFileSearchFilters"; //$NON-NLS-1$

	/**
	 * The default instance for reading registry.
	 */
	private static LocalFileSearchFilterRegistry instance;

	/**
	 * Returns registered filter for given type id.
	 * 
	 * @param id
	 *            the related id
	 * @return a filter or <code>null</code>
	 */
	public static synchronized final ILocalFileSearchFilter getFilter(String id) {
		Map<String, ILocalFileSearchFilter> filters = getFilters();
		return filters.get(id);
	}

	/**
	 * Returns all registered filters.
	 */
	private static final Map<String, ILocalFileSearchFilter> getFilters() {
		if (filtersMap == null) {
			filtersMap = getDefault().readFromExtensionPoint();
		}
		return filtersMap;
	}

	private static LocalFileSearchFilterRegistry getDefault() {
		if (instance == null) {
			instance = new LocalFileSearchFilterRegistry();
		}
		return instance;
	}

	/**
	 * Reads the extension point
	 */
	private Map<String, ILocalFileSearchFilter> readFromExtensionPoint() {
		final Map<String, ILocalFileSearchFilter> entries = new HashMap<String, ILocalFileSearchFilter>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			String id = element.getAttribute(PROP_ID);
			try {
				ILocalFileSearchFilter filter = (ILocalFileSearchFilter) createInstance(element, PROP_CLASS,
						ILocalFileSearchFilter.class);
				entries.put(id, filter);
			} catch (CoreException e) {
				Logger.logException("Could not instantiate local file search filter from extension point data.", //$NON-NLS-1$
						e);
				continue;
			}
		}
		return entries;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object createInstance(IConfigurationElement element, String propertyName, Class instanceClass)
			throws CoreException {
		final Object object = element.createExecutableExtension(propertyName);
		if (!instanceClass.isAssignableFrom(object.getClass())) {
			String message = String.format("Invalid typecast for %s", //$NON-NLS-1$
					element.getAttribute(propertyName));
			IStatus status = new Status(IStatus.ERROR, PHPDebugPlugin.getDefault().getBundle().getSymbolicName(),
					message);
			throw new CoreException(status);
		}
		return object;
	}

}
