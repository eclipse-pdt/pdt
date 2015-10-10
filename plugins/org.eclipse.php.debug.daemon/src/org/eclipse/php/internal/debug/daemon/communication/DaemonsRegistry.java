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
package org.eclipse.php.internal.debug.daemon.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.*;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.php.internal.debug.daemon.Logger;

/**
 * Communication daemons registry.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DaemonsRegistry {

	private static Map<String, ICommunicationDaemon> daemons = null;

	private class Entry {

		IConfigurationElement element;
		String id;
		String overridenDaemonId;

		public Entry(IConfigurationElement element, String id, String overridenDaemonId) {
			this.element = element;
			this.id = id;
			this.overridenDaemonId = overridenDaemonId;
		}

	}

	protected static final String PROP_ID = "id"; //$NON-NLS-1$
	protected static final String PROP_OVERRIDES = "overrides"; //$NON-NLS-1$
	protected static final String PROP_CLASS = "class"; //$NON-NLS-1$

	/**
	 * The name of extension point to read daemons from
	 */
	public static final String EXTENSION_POINT_ID = DaemonPlugin.getDefault().getBundle().getSymbolicName()
			+ ".debugCommunicationDaemon"; //$NON-NLS-1$

	/**
	 * The default instance for reading registry
	 */
	private static DaemonsRegistry instance;

	/**
	 * Returns all registered daemons.
	 */
	protected static final Map<String, ICommunicationDaemon> getDaemonsMap() {
		if (daemons == null) {
			daemons = getDefault().readFromExtensionPoint();
		}
		return daemons;
	}

	/**
	 * Gets daemon by its ID.
	 * 
	 * @param id
	 * @return daemon
	 */
	public static final ICommunicationDaemon getDaemon(String id) {
		return getDaemonsMap().get(id);
	}

	/**
	 * Returns all daemons.
	 * 
	 * @return all daemons
	 */
	public static final List<ICommunicationDaemon> getDaemons() {
		return new ArrayList<ICommunicationDaemon>(getDaemonsMap().values());
	}

	protected static DaemonsRegistry getDefault() {
		if (instance == null) {
			instance = new DaemonsRegistry();
		}
		return instance;
	}

	/**
	 * Reads the extension point
	 */
	protected Map<String, ICommunicationDaemon> readFromExtensionPoint() {
		final List<Entry> entries = new ArrayList<Entry>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			String id = element.getAttribute(PROP_ID);
			String overridenProviderId = element.getAttribute(PROP_OVERRIDES);
			entries.add(new Entry(element, id, overridenProviderId));
		}
		return fetchDaemons(entries);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object createInstance(IConfigurationElement element, String propertyName, Class instanceClass)
			throws CoreException {
		final Object object = element.createExecutableExtension(propertyName);
		if (!instanceClass.isAssignableFrom(object.getClass())) {
			String message = String.format("Invalid typecast for %s", element.getAttribute(propertyName)); //$NON-NLS-1$
			IStatus status = new Status(IStatus.ERROR, DaemonPlugin.getDefault().getBundle().getSymbolicName(),
					message);
			throw new CoreException(status);
		}
		return object;
	}

	private Map<String, ICommunicationDaemon> fetchDaemons(List<Entry> entries) {
		Map<String, ICommunicationDaemon> daemons = new HashMap<String, ICommunicationDaemon>();
		List<Entry> topHierarchyEntries = new ArrayList<Entry>();
		for (Entry entryToCheck : entries) {
			boolean isTopHierarchy = true;
			for (Entry entry : entries) {
				if (entryToCheck.id.equals(entry.overridenDaemonId)) {
					isTopHierarchy = false;
					break;
				}
			}
			if (isTopHierarchy)
				topHierarchyEntries.add(entryToCheck);
		}
		for (Entry entry : topHierarchyEntries) {
			ICommunicationDaemon daemon;
			try {
				daemon = (ICommunicationDaemon) createInstance(entry.element, PROP_CLASS, ICommunicationDaemon.class);
			} catch (CoreException e) {
				Logger.logException("Could not instantiate communication daemon from extension point data.", e); //$NON-NLS-1$
				continue;
			}
			daemons.put(entry.id, daemon);
		}
		return daemons;
	}

}
