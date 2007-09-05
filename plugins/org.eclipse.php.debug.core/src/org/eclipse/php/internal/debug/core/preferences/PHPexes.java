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
package org.eclipse.php.internal.debug.core.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;

/**
 * A managing class for all the registered PHP executables.
 * As of PDT 1.0 this class can handle multiple debuggers. 
 * 
 * @author Shalom Gibly
 */
public class PHPexes {

	private static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_NAME = "phpExe"; //$NON-NLS-1$
	private static final String LOCATION_ATTRIBUTE = "location"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String DEBUGGER_ID_ATTRIBUTE = "debuggerID"; //$NON-NLS-1$
	private static final String PHPEXE_TAG = "phpExe"; //$NON-NLS-1$
	public static final String SEPARATOR = ";";
	private static final String VERSION_ATTRIBUTE = "version"; //$NON-NLS-1$

	private static Object lock = new Object();
	// A singleton instance
	private static PHPexes instance;

	// Hold a mapping from the debugger ID to a map of installed 
	private HashMap<String, HashMap<String, PHPexeItem>> items = new HashMap<String, HashMap<String, PHPexeItem>>();
	// Hold a mapping to each debugger default PHPExeItem.
	private HashMap<String, PHPexeItem> defaultItems = new HashMap<String, PHPexeItem>();

	/**
	 * Returns a single instance of this PHPexes class.
	 * 
	 * @return A singleton PHPexes instance.
	 */
	public static PHPexes getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new PHPexes();
			}
			return instance;
		}
	}

	// Private constructor
	private PHPexes() {
		load();
	}

	/**
	 * Take the given PHP CLI executable path and change it to PHP CGI path.
	 * We assume that there is a php-cgi file in the same location. If we cannot find one, 
	 * we return the given executable path.
	 * 
	 * @param phpCLIPath
	 * @return php CGI path (or the given CLI, if no such php executable exists)
	 */
	public static String changeToCGI(String phpCLIPath) {
		File cgi = new File(phpCLIPath);
		File parentFile = cgi.getParentFile();
		File cli = null;
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			cli = new File(parentFile, "php-cgi.exe");
		} else {
			// Check the case of the name
			String name = cgi.getName();
			if (Character.isUpperCase(name.charAt(0))) {
				cli = new File(parentFile, name + "-CGI");
			} else {
				cli = new File(parentFile, name + "-cgi");
			}
		}
		if (cli.exists()) {
			return cli.getAbsolutePath();
		}
		return phpCLIPath;
	}

	/**
	 * Change to executable permissions for non-windows machines.
	 */
	public static void changePermissions(File file) {
		if (!Platform.getOS().equals(Platform.OS_WIN32)) {
			LocalFile localFile = new LocalFile(file);
			IFileInfo info = localFile.fetchInfo();
			if (!info.getAttribute(EFS.ATTRIBUTE_EXECUTABLE)) {
				info.setAttribute(EFS.ATTRIBUTE_EXECUTABLE, true);
				try {
					localFile.putInfo(info, EFS.SET_ATTRIBUTES, null);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
		}
	}

	/**
	 * Adds a {@link PHPexeItem} to the list of installed items that are assigned to its debugger id.
	 * Note that the first inserted item will set to be the default one until a call to {@link #setDefaultItem(PHPexeItem)} is made.
	 * 
	 * @param item
	 * @see #setDefaultItem(PHPexeItem)
	 * @see #getItem(String, String)
	 */
	public void addItem(PHPexeItem item) {
		String debuggerId = item.getDebuggerID();
		HashMap<String, PHPexeItem> map = items.get(debuggerId);
		if (map == null) {
			map = new HashMap<String, PHPexeItem>();
			items.put(debuggerId, map);
		}
		// Set the first item in this map to be the default one.
		if (map.isEmpty()) {
			setDefaultItem(item);
		}
		map.put(item.getName(), item);
	}

	/**
	 * Returns the default item for the specified debugger.
	 * 
	 * @return The default PHPexeItem for the given debugger, or null if no such debugger exists.
	 */
	public PHPexeItem getDefaultItem(String debuggerId) {
		return defaultItems.get(debuggerId);
	}

	/**
	 * Returns true if there are PHP executables registered to the given debugger.
	 * 
	 * @param debuggerId The debugger id.
	 * @return True, if there are executables for this debugger; False, otherwise.
	 * @see #hasItems()
	 */
	public boolean hasItems(String debuggerId) {
		HashMap<String, PHPexeItem> map = items.get(debuggerId);
		return map != null && map.size() > 0;
	}

	/**
	 * Returns true if there are any registered PHP executables.
	 * 
	 * @return True, if there is at least one registered PHP executable; False, otherwise.
	 * @see #hasItems(String)
	 */
	public boolean hasItems() {
		return getAllItems().length > 0;
	}

	/**
	 * Returns all the editable items.
	 * 
	 * @return An array of editable PHPExeItems.
	 */
	public PHPexeItem[] getEditableItems() {
		Set<String> installedDebuggers = PHPDebuggersRegistry.getDebuggersIds();
		ArrayList<PHPexeItem> list = new ArrayList<PHPexeItem>();
		for (String debuggerId : installedDebuggers) {
			HashMap<String, PHPexeItem> installedExes = items.get(debuggerId);
			if (installedExes != null) {
				Set<String> exeNames = installedExes.keySet();
				for (String name : exeNames) {
					PHPexeItem exeItem = installedExes.get(name);
					if (exeItem.isEditable()) {
						list.add(exeItem);
					}
				}
			}
		}
		return (PHPexeItem[]) list.toArray(new PHPexeItem[list.size()]);
	}

	/**
	 * Returns the {@link PHPexeItem} for the given debuggerId that has the given name.
	 * 
	 * @param debuggerId
	 * @param name
	 * @return A {@link PHPexeItem} or null if none is installed.
	 */
	public PHPexeItem getItem(String debuggerId, String name) {
		HashMap<String, PHPexeItem> map = items.get(debuggerId);
		if (map == null) {
			return null;
		}
		return map.get(name);
	}

	/**
	 * Search for the executable file name in all of the registered {@link PHPexeItem}s and return
	 * a reference to the one that refer to the same file.
	 * 
	 * @param exeFileName The executable file name.
	 * @return The corresponding {@link PHPexeItem}, or null if none was found.
	 */
	public PHPexeItem getItemForFile(final String exeFileName) {
		Set<String> installedDebuggers = PHPDebuggersRegistry.getDebuggersIds();
		for (String debuggerId : installedDebuggers) {
			HashMap<String, PHPexeItem> installedExes = items.get(debuggerId);
			if (installedExes != null) {
				Set<String> exeNames = installedExes.keySet();
				for (String name : exeNames) {
					PHPexeItem exeItem = installedExes.get(name);
					if (exeFileName.equals(exeItem.getPhpEXE().toString())) {
						return exeItem;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the PHPExeItems registered for the given debugger id.
	 * 
	 * @param debuggerId
	 * @return An array of installed exe items for the given debugger; null if no such debugger is registered, or the debugger does not have any executables.
	 */
	public PHPexeItem[] getItems(String debuggerId) {
		HashMap<String, PHPexeItem> installedExes = items.get(debuggerId);
		if (installedExes == null) {
			return null;
		}
		PHPexeItem[] retItems = new PHPexeItem[installedExes.size()];
		return installedExes.values().toArray(retItems);
	}

	/**
	 * Returns an array of all the installed {@link PHPexeItem}s for all the installed debuggers.
	 * 
	 * @return An array of all the installed debuggers.
	 */
	public PHPexeItem[] getAllItems() {
		ArrayList<PHPexeItem> allItems = new ArrayList<PHPexeItem>();
		Set<String> debuggers = items.keySet();
		for (String debugger : debuggers) {
			HashMap<String, PHPexeItem> debuggerItems = items.get(debugger);
			if (debuggerItems != null) {
				Collection<PHPexeItem> exeItems = debuggerItems.values();
				for (PHPexeItem item : exeItems) {
					allItems.add(item);
				}
			}
		}
		return allItems.toArray(new PHPexeItem[allItems.size()]);
	}

	// Load executables from the preferences.
	private void load() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		items = new HashMap<String, HashMap<String, PHPexeItem>>();
		loadExtensions();
		String namesString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES);
		if (namesString == null) {
			namesString = "";
		}
		final String[] names = namesString.length() > 0 ? namesString.split(SEPARATOR) : new String[0];
		String locationsString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS);
		if (locationsString == null) {
			locationsString = "";
		}
		final String[] locations = locationsString.length() > 0 ? locationsString.split(SEPARATOR) : new String[0];
		String debuggersString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_DEBUGGERS);
		if (debuggersString == null) {
			debuggersString = "";
		}
		final String[] debuggers = debuggersString.length() > 0 ? debuggersString.split(SEPARATOR) : new String[0];
		String defaultsString = prefs.getString(PHPDebugCorePreferenceNames.INSTALLED_PHP_DEFAULTS);
		if (defaultsString == null) {
			defaultsString = "";
		}
		final String[] defaults = defaultsString.length() > 0 ? debuggersString.split(SEPARATOR) : new String[0];

		assert names.length == locations.length;
		for (int i = 0; i < locations.length; i++) {
			final PHPexeItem item = new PHPexeItem(names[i], locations[i], debuggers[i]);
			if (item.getPhpEXE() != null) {
				addItem(item);
			}
			// Set the default item.
			if (Boolean.parseBoolean(defaults[i])) {
				setDefaultItem(item);
			}
		}
		//		final String defaultLocationString = prefs.getString(PHPDebugCorePreferenceNames.DEFAULT_PHP);
		//		if (defaultLocationString != null && defaultLocationString.length() > 0)
		//			defaultItem = (PHPexeItem) items.get(defaultLocationString);
		//		if (defaultItem == null && items.size() > 0)
		//			defaultItem = getItems()[0];
	}

	/*
	 * Load the PHP executables that were defined in the extensions.
	 */
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
				String debuggerID = element.getAttribute(DEBUGGER_ID_ATTRIBUTE);
				if (debuggerID == null || debuggerID.equals("")) {
					// The debugger id is an optional field, so in case that none was entered assign the debugger to Zend. 
					debuggerID = DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID;
				}
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
							final PHPexeItem newItem = new PHPexeItem(name, file, debuggerID, false);
							newItem.setVersion(version);
							addItem(newItem);
							if (isDefault) {
								setDefaultItem(newItem);
							}
							itemFound = true;
						}
					} catch (final IOException e) {
					}
				if (!itemFound)
					PHPDebugPlugin.getDefault().getLog().log(new Status(1, PHPDebugPlugin.getID(), 1001, "PHP executable " + location + " not found neither in plugin " + pluginId + " nor in fragments attached to it", null));
			}
		}
	}

	/**
	 * Removes an item. 
	 * In case the removed item was the default one, a different random item will be picked to be the 
	 * new default one for the specific debugger.
	 * 
	 * @param debuggerID 
	 * @param item
	 */
	public void removeItem(PHPexeItem item) {
		String debuggerID = item.getDebuggerID();
		HashMap<String, PHPexeItem> exes = items.get(debuggerID);
		PHPexeItem removedItem = null;
		if (exes != null) {
			removedItem = exes.remove(item.getName());
		}
		if (removedItem != null && removedItem.isDefault()) {
			defaultItems.remove(debuggerID);
			// Pick the next item from the exes list for this debugger to be the default one.
			Iterator<PHPexeItem> iterator = exes.values().iterator();
			if (iterator.hasNext()) {
				setDefaultItem(iterator.next());
			}
		}
	}

	/**
	 * Sets a default exe item for its debugger id.
	 * 
	 * @param defaultItem
	 */
	public void setDefaultItem(PHPexeItem defaultItem) {
		String debuggerID = defaultItem.getDebuggerID();
		// Remove any item that was previously set as default.
		PHPexeItem oldDefault = defaultItems.get(debuggerID);
		if (oldDefault == defaultItem) {
			return;
		}
		if (oldDefault != null) {
			oldDefault.setDefault(false);
		}
		defaultItem.setDefault(true);
		defaultItems.put(debuggerID, defaultItem);
	}

	/**
	 * Sets a default exe item for the given debugger.
	 * 
	 * @param debuggerID
	 * @param defaultItem
	 */
	public void setDefaultItem(String debuggerID, String defaultItemName) {
		PHPexeItem item = getItem(debuggerID, defaultItemName);
		if (item != null) {
			setDefaultItem(item);
		}
	}

	/**
	 * Save the edited PHP executable items to the plug-in preferences.
	 */
	public void save() {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		final PHPexeItem[] phpItems = getEditableItems();
		final StringBuffer locationsString = new StringBuffer();
		final StringBuffer namesString = new StringBuffer();
		final StringBuffer debuggersString = new StringBuffer();
		final StringBuffer defaultsString = new StringBuffer();
		for (int i = 0; i < phpItems.length; i++) {
			final PHPexeItem item = phpItems[i];
			if (i > 0) {
				locationsString.append(SEPARATOR);
				namesString.append(SEPARATOR);
				debuggersString.append(SEPARATOR);
				defaultsString.append(SEPARATOR);
			}
			locationsString.append(item.getLocation().toString());
			namesString.append(item.getName());
			debuggersString.append(item.getDebuggerID());
			defaultsString.append(item.isDefault());
		}
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES, namesString.toString());
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS, locationsString.toString());
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_DEBUGGERS, debuggersString.toString());
		prefs.setValue(PHPDebugCorePreferenceNames.INSTALLED_PHP_DEFAULTS, defaultsString.toString());
		PHPDebugPlugin.getDefault().savePluginPreferences();
		//		final String defaultString = defaultItem != null ? defaultItem.name : "";
		//		prefs.setValue(PHPDebugCorePreferenceNames.DEFAULT_PHP, defaultString);
	}
}
