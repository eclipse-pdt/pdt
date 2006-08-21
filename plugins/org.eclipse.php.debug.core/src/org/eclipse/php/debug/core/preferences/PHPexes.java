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
package org.eclipse.php.debug.core.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.*;
import org.eclipse.php.core.util.UnixChmodUtil;
import org.eclipse.php.debug.core.PHPDebugPlugin;

public class PHPexes {

	private static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_NAME = "phpExe"; //$NON-NLS-1$
	private static final String LOCATION_ATTRIBUTE = "location"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String PHPEXE_TAG = "phpExe"; //$NON-NLS-1$
	public static final String SEPARATOR = ";";
	private static final String VERSION_ATTRIBUTE = "version"; //$NON-NLS-1$
	PHPexeItem defaultItem;
	HashMap items = new HashMap();

	public void addItem(final PHPexeItem item) {
		items.put(item.getName(), item);
	}

	public PHPexeItem getDefaultItem() {
		return defaultItem;
	}

	public PHPexeItem[] getEditableItems() {
		final ArrayList list = new ArrayList();
		for (final Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			final PHPexeItem item = getItem(key);
			if (item.isEditable())
				list.add(item);
		}
		return (PHPexeItem[]) list.toArray(new PHPexeItem[list.size()]);
	}

	public PHPexeItem getItem(final String name) {
		return (PHPexeItem) items.get(name);
	}

	public PHPexeItem getItemForFile(final String fileName) {
		for (final Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			final PHPexeItem item = getItem(key);
			if (fileName.equals(item.getPhpEXE().toString()))
				return item;
		}
		return null;

	}

	public PHPexeItem getItemForLocation(final String location) {
		for (final Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			final PHPexeItem item = getItem(key);
			if (location.equals(item.location))
				return item;
		}
		return null;

	}

	public PHPexeItem[] getItems() {
		int i = 0;
		final PHPexeItem[] retItems = new PHPexeItem[items.size()];
		for (final Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			final PHPexeItem item = getItem(key);
			retItems[i++] = item;
		}
		return retItems;
	}

	public void load(final Preferences prefs) {
		items = new HashMap();
		loadExtensions();
		String namesString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES);
		if (namesString == null)
			namesString = "";
		final String[] names = namesString.length() > 0 ? namesString.split(SEPARATOR) : new String[0];
		String locationsString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS);
		if (locationsString == null)
			locationsString = "";
		final String[] locations = locationsString.length() > 0 ? locationsString.split(SEPARATOR) : new String[0];
		assert names.length == locations.length;
		for (int i = 0; i < locations.length; i++) {
			final PHPexeItem item = new PHPexeItem(names[i], locations[i]);
			if (item.getPhpEXE() != null)
				items.put(names[i], item);
		}
		final String defaultLocationString = prefs.getString(PHPDebugCorePreferenceNames.DEFAULT_PHP);
		if (defaultLocationString != null && defaultLocationString.length() > 0)
			defaultItem = (PHPexeItem) items.get(defaultLocationString);
		if (defaultItem == null && items.size() > 0)
			defaultItem = getItems()[0];
	}

	private void loadExtensions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(), EXTENSION_POINT_NAME);

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows"); //$NON-NLS-1$ //$NON-NLS-2$

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (PHPEXE_TAG.equals(element.getName())) {
				final String name = element.getAttribute(NAME_ATTRIBUTE);
				String location = element.getAttribute(LOCATION_ATTRIBUTE);
				final String version = element.getAttribute(VERSION_ATTRIBUTE);
				final boolean isDefault = "true".equalsIgnoreCase(element.getAttribute(DEFAULT_ATTRIBUTE));

				if (isWindows)
					location = location + ".exe"; //$NON-NLS-1$

				final String pluginId = element.getDeclaringExtension().getNamespaceIdentifier();
				URL url = FileLocator.find(Platform.getBundle(pluginId), new Path(location), new HashMap());
				boolean itemFound = false;
				if (url != null)
					try {
						url = FileLocator.resolve(url);
						final String filename = url.getFile();
						final File file = new File(filename);
						if (file.exists()) {
							final PHPexeItem newItem = new PHPexeItem(name, file, false);
							newItem.setVersion(version);
							items.put(name, newItem);
							if (isDefault)
								defaultItem = newItem;
							itemFound = true;
							if (!isWindows)
								// Try to setup permissions of this file:
								UnixChmodUtil.chmod(filename, UnixChmodUtil.S_IRWXU | UnixChmodUtil.S_IRGRP | UnixChmodUtil.S_IXGRP | UnixChmodUtil.S_IROTH | UnixChmodUtil.S_IXOTH);
						}
					} catch (final IOException e) {
					}
				if (!itemFound)
					PHPDebugPlugin.getDefault().getLog().log(new Status(1, PHPDebugPlugin.getID(), 1001, "PHP executable " + location + " not found neither in plugin " + pluginId + " nor in fragments attached to it", null));
			}
		}
	}

	public void removeItem(final PHPexeItem item) {
		items.remove(item.getName());
	}

	public void setDefaultItem(final PHPexeItem defaultItem) {
		this.defaultItem = defaultItem;
	}

	public void store(final Preferences prefs) {
		final PHPexeItem[] phpItems = getEditableItems();
		final StringBuffer locationsString = new StringBuffer();
		final StringBuffer namesString = new StringBuffer();
		for (int i = 0; i < phpItems.length; i++) {
			final PHPexeItem item = phpItems[i];
			if (i > 0) {
				locationsString.append(SEPARATOR);
				namesString.append(SEPARATOR);
			}
			locationsString.append(item.getLocation().toString());
			namesString.append(item.getName());
		}
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES, namesString.toString());
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS, locationsString.toString());
		final String defaultString = defaultItem != null ? defaultItem.name : "";
		prefs.setValue(PHPDebugCorePreferenceNames.DEFAULT_PHP, defaultString);

	}
}
