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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.debug.core.PHPDebugPlugin;

public class PHPexes {

	PHPexeItem defaultItem;
	HashMap items = new HashMap();
	public static final String SEPARATOR = ";";
	private static final String EXTENSION_POINT_NAME = "phpExe"; //$NON-NLS-1$
	private static final String PHPEXE_TAG = "phpExe"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String LOCATION_ATTRIBUTE = "location"; //$NON-NLS-1$
	private static final String VERSION_ATTRIBUTE = "version"; //$NON-NLS-1$
	private static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	private static final String OS_ATTRIBUTE = "os"; //$NON-NLS-1$

	public void load(Preferences prefs) {
		items = new HashMap();
		loadExtensions();
		String namesString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES);
		if (namesString == null)
			namesString = "";
		String[] names = (namesString.length() > 0) ? namesString.split(SEPARATOR) : new String[0];
		String locationsString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS);
		if (locationsString == null)
			locationsString = "";
		String[] locations = (locationsString.length() > 0) ? locationsString.split(SEPARATOR) : new String[0];
		assert names.length == locations.length;
		for (int i = 0; i < locations.length; i++) {
			PHPexeItem item = new PHPexeItem(names[i], locations[i]);
			items.put(names[i], item);
		}
		String defaultLocationString = prefs.getString(PHPDebugCorePreferenceNames.DEFAULT_PHP);
		if (defaultLocationString != null && defaultLocationString.length() > 0)
			defaultItem = (PHPexeItem) items.get(defaultLocationString);
		if (defaultItem==null && items.size()>0)
		{
			defaultItem=getItems()[0];
		}
	}

	public void store(Preferences prefs) {
		PHPexeItem[] phpItems = getEditableItems();
		StringBuffer locationsString = new StringBuffer();
		StringBuffer namesString = new StringBuffer();
		for (int i = 0; i < phpItems.length; i++) {
			PHPexeItem item = phpItems[i];
			if (i > 0) {
				locationsString.append(SEPARATOR);
				namesString.append(SEPARATOR);
			}
			locationsString.append(item.getLocation().toString());
			namesString.append(item.getName());
		}
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES, namesString.toString());
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS, locationsString.toString());
		String defaultString = (phpItems.length > 0) ? defaultItem.name : "";
		prefs.setValue(PHPDebugCorePreferenceNames.DEFAULT_PHP, defaultString);

	}

	public void addItem(PHPexeItem item) {
		items.put(item.getName(), item);
	}

	public void removeItem(PHPexeItem item) {
		items.remove(item.getName());
	}

	public PHPexeItem getItem(String name) {
		return (PHPexeItem) items.get(name);
	}

	public PHPexeItem[] getItems() {
		int i = 0;
		PHPexeItem[] retItems = new PHPexeItem[items.size()];
		for (Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			PHPexeItem item = getItem(key);
			retItems[i++] = item;
		}
		return retItems;
	}

	public PHPexeItem[] getEditableItems() {
		ArrayList list = new ArrayList();
		for (Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			PHPexeItem item = getItem(key);
			if (item.isEditable())
				list.add(item);
		}
		return (PHPexeItem[]) list.toArray(new PHPexeItem[list.size()]);
	}

	public PHPexeItem getDefaultItem() {
		return defaultItem;
	}

	public void setDefaultItem(PHPexeItem defaultItem) {
		this.defaultItem = defaultItem;
	}

	public PHPexeItem getItemForLocation(String location) {
		for (Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			PHPexeItem item = getItem(key);
			if (location.equals(item.location))
				return item;
		}
		return null;

	}

	public PHPexeItem getItemForFile(String fileName) {
		for (Iterator iter = items.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			PHPexeItem item = getItem(key);
			if (fileName.equals(item.getPhpEXE().toString()))
				return item;
		}
		return null;

	}
	
	private void loadExtensions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(), EXTENSION_POINT_NAME);
		
		String myOS = null;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.startsWith("windows")) {
			myOS = "Windows";
		} else if (OS.startsWith("linux")) {
			myOS = "Linux";
		} else if (OS.startsWith("mac")) {
			myOS = "Mac";
		}
		
		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (PHPEXE_TAG.equals(element.getName())) {
				String name = element.getAttribute(NAME_ATTRIBUTE);
				String location = element.getAttribute(LOCATION_ATTRIBUTE);
				String version = element.getAttribute(VERSION_ATTRIBUTE);
				boolean isDefault = "true".equalsIgnoreCase(element.getAttribute(DEFAULT_ATTRIBUTE));
				
				if (myOS != null) {
					String os = element.getAttribute(OS_ATTRIBUTE);
					if (os != null && !os.equals(myOS)) {
						continue; // Skip current PHP, since its OS doesn't match ours
					}
				}

				String pluginId = element.getDeclaringExtension().getDeclaringPluginDescriptor().getUniqueIdentifier();
				StringBuffer buff = new StringBuffer("platform:/plugin/");
				buff.append(pluginId);

				if (!location.startsWith("/"))
					buff.append('/');

				buff.append(location);
				URL url;
				try {
					url = new URL(buff.toString());
					url = Platform.resolve(url);
					String filename = url.getFile();
//					if (filename.startsWith("/"))
//						filename = filename.substring(1);
					File file = new File(filename);
					if (file.exists()) {
						PHPexeItem newItem = new PHPexeItem(name, file,false);
						newItem.setVersion(version);
						items.put(name, newItem);
						if (isDefault)
							defaultItem = newItem;
					} else
						PHPDebugPlugin.getDefault().getLog().log(new Status(1, PHPDebugPlugin.getID(), 1001, "php exe " + location + " not found in plugin " + pluginId, null));
				} catch (MalformedURLException e) {
					PHPDebugPlugin.getDefault().getLog().log(new Status(1, PHPDebugPlugin.getID(), 1001, "php exe " + location + " not found in plugin " + pluginId, e));
				} catch (IOException e) {
					PHPDebugPlugin.getDefault().getLog().log(new Status(1, PHPDebugPlugin.getID(), 1001, "php exe " + location + " not found in plugin " + pluginId, e));
				}
			}
		}
	}
}
