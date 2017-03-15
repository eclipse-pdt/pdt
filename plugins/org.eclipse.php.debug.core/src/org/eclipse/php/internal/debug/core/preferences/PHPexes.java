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
package org.eclipse.php.internal.debug.core.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.osgi.service.prefs.BackingStoreException;

/**
 * A managing class for all the registered PHP executables. As of PDT 1.0 this
 * class can handle multiple debuggers.
 * 
 * @author Shalom Gibly
 */
public class PHPexes {

	private class PHPexesUpgrade {

		private void upgrade() {
			String uniqueIds = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
					PHPDebugCorePreferenceNames.INSTALLED_PHP_UNIQUE_IDS, null, null);
			PHPexeItem[] userItems = getEditableItems();
			// There are no user items, no need to upgrade
			if (userItems.length == 0)
				return;
			// Upgrade unique IDs if those don't exist in preferences
			if (uniqueIds == null || uniqueIds.isEmpty()) {
				save();
			}
		}

	}

	private static final String NULL_PLACE_HOLDER = "null"; //$NON-NLS-1$
	private static final String SEPARATOR_FOR_PHPVERSION = "/"; //$NON-NLS-1$
	private static final String DEFAULT_ATTRIBUTE = "default"; //$NON-NLS-1$
	private static final String EXTENSION_POINT_NAME = "phpExe"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String LOCATION_ATTRIBUTE = "location"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String DEBUGGER_ID_ATTRIBUTE = "debuggerID"; //$NON-NLS-1$
	private static final String PHPEXE_TAG = "phpExe"; //$NON-NLS-1$
	private static final String TRUE = "true"; //$NON-NLS-1$
	private static final String FALSE = "false"; //$NON-NLS-1$
	public static final String SEPARATOR = ";"; //$NON-NLS-1$
	private static final String VERSION_ATTRIBUTE = "version"; //$NON-NLS-1$
	public static final String ZEND_DEBUGGER_ID = DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID;

	private static Object lock = new Object();
	// A singleton instance
	private static PHPexes instance;

	// Hold a mapping from the debugger ID to a map of installed
	private HashMap<String, HashMap<String, PHPexeItem>> items = new HashMap<String, HashMap<String, PHPexeItem>>();
	// Hold a mapping to each php version default PHPExeItem.
	private HashMap<PHPVersion, PHPexeItem> defaultItemsForPHPVersion = new HashMap<PHPVersion, PHPexeItem>();
	private final LinkedList<IPHPExesListener> listeners = new LinkedList<IPHPExesListener>();

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
	 * Adds a {@link PHPexeItem} to the list of installed items that are
	 * assigned to its debugger id. Note that the first inserted item will set
	 * to be the default one until a call to {@link #setDefaultItem(PHPexeItem)}
	 * is made.
	 * 
	 * @param item
	 * @see #setDefaultItem(PHPexeItem)
	 * @see #getItem(String, String)
	 */
	public void addItem(PHPexeItem item) {
		if (!hasItems()) {
			setDefaultItem(item);
		}
		storeItem(item);
	}

	private void storeItem(PHPexeItem item) {
		String debuggerId = item.getDebuggerID();
		HashMap<String, PHPexeItem> map = items.get(debuggerId);
		if (map == null) {
			map = new HashMap<String, PHPexeItem>();
			items.put(debuggerId, map);
		}
		map.put(item.getName(), item);
		Iterator<IPHPExesListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			PHPExesEvent phpExesEvent = new PHPExesEvent(item);
			iter.next().phpExeAdded(phpExesEvent);
		}
	}

	/**
	 * Updates original item with the data from working copy.
	 * 
	 * @param original
	 * @param copy
	 */
	public void updateItem(PHPexeItem original, PHPexeItem copy) {
		// Remove binding from debuggers ID map
		String debuggerID = original.getDebuggerID();
		HashMap<String, PHPexeItem> exes = items.get(debuggerID);
		PHPexeItem removedItem = null;
		if (exes != null) {
			exes.remove(original.getName());
		}
		// Check if was default and name has changed
		boolean updateDefault = false;
		if (original.isDefault() && !original.getName().equals(copy.getName())) {
			updateDefault = true;
		}
		// Update original item
		original.setName(copy.getName());
		original.setExecutable(copy.getExecutable());
		original.setINILocation(copy.getINILocation());
		original.setDebuggerID(copy.getDebuggerID());
		original.setSapiType(copy.getSapiType());
		original.setLoadDefaultINI(copy.isLoadDefaultINI());
		original.setVersion(copy.getVersion());
		// Add new binding after update
		debuggerID = original.getDebuggerID();
		exes = items.get(debuggerID);
		if (exes == null) {
			exes = new HashMap<String, PHPexeItem>();
			items.put(debuggerID, exes);
		}
		exes.put(original.getName(), original);
		if (updateDefault) {
			setDefaultItem(original);
		}
	}

	/**
	 * Returns the default workspace PHPexeItem.
	 * 
	 * @return The default workspace PHPexeItem.
	 */
	public PHPexeItem getDefaultItem() {
		return PHPDebugPlugin.getWorkspaceDefaultExe();
	}

	/**
	 * Returns true if there are PHP executables registered to the given
	 * debugger.
	 * 
	 * @param debuggerId
	 *            The debugger id.
	 * @return True, if there are executables for this debugger; False,
	 *         otherwise.
	 * @see #hasItems()
	 */
	public boolean hasItems(String debuggerId) {
		HashMap<String, PHPexeItem> map = items.get(debuggerId);
		return map != null && map.size() > 0;
	}

	/**
	 * Returns true if there are any registered PHP executables.
	 * 
	 * @return True, if there is at least one registered PHP executable; False,
	 *         otherwise.
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
				for (Entry<String, PHPexeItem> entry : installedExes.entrySet()) {
					PHPexeItem exeItem = entry.getValue();
					if (exeItem.isEditable()) {
						list.add(exeItem);
					}
				}
			}
		}
		return list.toArray(new PHPexeItem[list.size()]);
	}

	/**
	 * Returns the {@link PHPexeItem} for the given debuggerId that has the
	 * given name.
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

	public PHPexeItem getItem(String name) {
		for (Iterator<String> iterator = items.keySet().iterator(); iterator.hasNext();) {
			String debuggerId = iterator.next();
			HashMap<String, PHPexeItem> map = items.get(debuggerId);
			if (map != null) {
				PHPexeItem item = map.get(name);
				if (item != null) {
					return item;
				}
			}
		}
		return null;
	}

	public PHPexeItem findItem(String id) {
		for (Iterator<String> iterator = items.keySet().iterator(); iterator.hasNext();) {
			String debuggerId = iterator.next();
			HashMap<String, PHPexeItem> map = items.get(debuggerId);
			if (map != null) {
				for (PHPexeItem value : map.values())
					if (value.getUniqueId().equals(id))
						return value;
			}
		}
		return null;
	}

	public PHPexeItem getPHP54Item() {
		for (Iterator<String> iterator = items.keySet().iterator(); iterator.hasNext();) {
			String debuggerId = iterator.next();
			HashMap<String, PHPexeItem> map = items.get(debuggerId);
			if (map != null) {
				for (Iterator<PHPexeItem> iterator2 = map.values().iterator(); iterator2.hasNext();) {
					PHPexeItem item = iterator2.next();
					if (item != null && item.getVersion().compareTo("5.4.0") >= 0) { //$NON-NLS-1$
						return item;
					}
				}
			}
		}
		return null;
	}

	public List<PHPexeItem> getPHP54Items() {
		List<PHPexeItem> result = new ArrayList<PHPexeItem>();
		for (Iterator<String> iterator = items.keySet().iterator(); iterator.hasNext();) {
			String debuggerId = iterator.next();
			HashMap<String, PHPexeItem> map = items.get(debuggerId);
			if (map != null) {
				for (Iterator<PHPexeItem> iterator2 = map.values().iterator(); iterator2.hasNext();) {
					PHPexeItem item = iterator2.next();
					if (item != null && item.getVersion().compareTo("5.4.0") >= 0) { //$NON-NLS-1$
						result.add(item);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Search for the executable file name in all of the registered
	 * {@link PHPexeItem}s and return a reference to the one that refer to the
	 * same file.
	 * 
	 * @param exeFilePath
	 *            The executable file name.
	 * @param iniFilePath
	 *            The php ini file path (can be null).
	 * @return The corresponding {@link PHPexeItem}, or null if none was found.
	 */
	public PHPexeItem getItemForFile(String exeFilePath, String iniFilePath) {
		Set<String> installedDebuggers = PHPDebuggersRegistry.getDebuggersIds();
		for (String debuggerId : installedDebuggers) {
			HashMap<String, PHPexeItem> installedExes = items.get(debuggerId);
			if (installedExes != null) {
				for (Entry<String, PHPexeItem> entry : installedExes.entrySet()) {
					PHPexeItem exeItem = entry.getValue();
					// Check for ini equality
					boolean iniEquals = true;
					if (iniFilePath == null) {
						iniEquals = exeItem.getINILocation() == null;
					} else {
						iniEquals = exeItem.getINILocation() == null ? iniFilePath.equals("") //$NON-NLS-1$
								: iniFilePath.equals(exeItem.getINILocation().toString());
					}
					if (iniEquals && exeFilePath.equals(exeItem.getExecutable().toString())) {
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
	 * @return An array of installed exe items for the given debugger; null if
	 *         no such debugger is registered, or the debugger does not have any
	 *         executables.
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
	 * Returns an array of all the installed {@link PHPexeItem}s for all the
	 * installed debuggers.
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

	/**
	 * Returns an array of all the installed CLI {@link PHPexeItem}s for all the
	 * installed debuggers.
	 * 
	 * @return An array of all the installed CLI debuggers.
	 */
	public PHPexeItem[] getCLIItems() {
		ArrayList<PHPexeItem> cliItems = new ArrayList<PHPexeItem>();
		PHPexeItem[] allItems = getAllItems();
		for (PHPexeItem item : allItems) {
			if (item.getSapiType() != null && PHPexeItem.SAPI_CLI.equals(item.getSapiType())) {
				cliItems.add(item);
			}
		}
		return cliItems.toArray(new PHPexeItem[cliItems.size()]);
	}

	// Load executables from the preferences.
	private void load() {
		items = new HashMap<String, HashMap<String, PHPexeItem>>();

		// Load the user-defined executable items

		// Load the item names array
		String namesString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES, null, null);
		if (namesString == null) {
			namesString = ""; //$NON-NLS-1$
		}
		final String[] names = namesString.length() > 0 ? namesString.split(SEPARATOR) : new String[0];

		// Load the item executable locations array
		String locationsString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS, null, null);
		if (locationsString == null) {
			locationsString = ""; //$NON-NLS-1$
		}
		final String[] phpExecutablesLocations = locationsString.length() > 0 ? locationsString.split(SEPARATOR)
				: new String[0];

		// Load the item executable ini's array
		String inisString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_INIS, null, null);
		if (inisString == null) {
			inisString = ""; //$NON-NLS-1$
		}

		// In case there is no preference value for the
		// PHPDebugCorePreferenceNames.INSTALLED_PHP_INIS,
		// the size of the array is set to be the same as the executables array.
		final String[] phpIniLocations = inisString.length() > 0 ? inisString.split(SEPARATOR)
				: new String[phpExecutablesLocations.length];

		// Load default inis settings
		String loadDefaultInisString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_LOAD_DEFAULT_INIS, null, null);
		if (loadDefaultInisString == null) {
			loadDefaultInisString = ""; //$NON-NLS-1$
		}
		final String[] loadDefaultInis = loadDefaultInisString.length() > 0 ? loadDefaultInisString.split(SEPARATOR)
				: new String[phpExecutablesLocations.length];

		// Load the PHP items unique IDs
		String uniqueIdsString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_UNIQUE_IDS, null, null);
		if (uniqueIdsString == null) {
			uniqueIdsString = "";
		}
		final String[] phpExecutablesUniqueIds = uniqueIdsString.length() > 0 ? uniqueIdsString.split(SEPARATOR)
				: new String[0];

		// Load the debuggers array
		String debuggersString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_DEBUGGERS, null, null);
		if (debuggersString == null) {
			debuggersString = ""; //$NON-NLS-1$
		}
		final String[] debuggers = debuggersString.length() > 0 ? debuggersString.split(SEPARATOR) : new String[0];

		// Load the PHP Versions array
		String defaultItemForPHPVersionString = Platform.getPreferencesService().getString(PHPDebugPlugin.ID,
				PHPDebugCorePreferenceNames.INSTALLED_PHP_DEFAULT_FOR_VERSIONS, null, null);
		if (defaultItemForPHPVersionString == null) {
			defaultItemForPHPVersionString = ""; //$NON-NLS-1$
		}
		final String[] defaultItemForPHPVersions = defaultItemForPHPVersionString.length() > 0
				? defaultItemForPHPVersionString.split(SEPARATOR) : new String[0];

		// Add the executable items
		assert names.length == phpExecutablesLocations.length;
		for (int i = 0; i < phpExecutablesLocations.length; i++) {
			String iniLocation = NULL_PLACE_HOLDER.equals(phpIniLocations[i]) ? null : phpIniLocations[i];
			// 361399: PDT Project Properties Debug page cause hang
			if ((names.length <= i) || (debuggers.length <= i)) {
				break;
			}
			final PHPexeItem item = new PHPexeItem(names[i], phpExecutablesLocations[i], iniLocation, debuggers[i],
					loadDefaultInis[i] != null && loadDefaultInis[i].equals(TRUE));
			// Overwrite the one generated in new executable constructor with
			// one that is stored in preferences
			if (phpExecutablesUniqueIds.length != 0)
				item.setUniqueId(phpExecutablesUniqueIds[i]);
			// the size of defaultItemForPHPVersions may be 0 when you use this
			// first time
			if (defaultItemForPHPVersions.length == phpExecutablesLocations.length) {
				if (!NULL_PLACE_HOLDER.equals(defaultItemForPHPVersions[i])) {
					final String[] phpVersions = defaultItemForPHPVersions[i].length() > 0
							? defaultItemForPHPVersions[i].split(SEPARATOR_FOR_PHPVERSION) : new String[0];
					for (int j = 0; j < phpVersions.length; j++) {
						PHPVersion phpVersion = PHPVersion.byAlias(phpVersions[j]);
						if (phpVersion != null) {
							item.addPHPVersionToDefaultList(phpVersion);
							defaultItemsForPHPVersion.put(phpVersion, item);
						}
					}
				}
			}
			if (item.getExecutable() != null) {
				boolean filterItem = WorkbenchActivityHelper.filterItem(new IPluginContribution() {
					public String getLocalId() {
						return item.getDebuggerID();
					}

					public String getPluginId() {
						return PHPDebugPlugin.ID;
					}
				});
				if (!filterItem) {
					storeItem(item);
				}
			}
		}

		// Load the executable items that were defined in the registered
		// extensions
		loadExtensions();

		// Check if PHP exe items preferences need upgrade
		(new PHPexesUpgrade()).upgrade();
	}

	/**
	 * Load the PHP executables that were defined in the extensions.
	 */
	private void loadExtensions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(),
				EXTENSION_POINT_NAME);

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows"); //$NON-NLS-1$ //$NON-NLS-2$

		for (final IConfigurationElement element : elements) {
			if (PHPEXE_TAG.equals(element.getName())) {
				try {
					String id = element.getAttribute(ID_ATTRIBUTE);
					final String name = element.getAttribute(NAME_ATTRIBUTE);
					String location = substitudeVariables(element.getAttribute(LOCATION_ATTRIBUTE));
					final String version = element.getAttribute(VERSION_ATTRIBUTE);
					String debuggerID = element.getAttribute(DEBUGGER_ID_ATTRIBUTE);
					if (debuggerID == null || debuggerID.equals("")) { //$NON-NLS-1$
						debuggerID = PHPDebuggersRegistry.NONE_DEBUGGER_ID;
					}
					final boolean isDefault = "true".equalsIgnoreCase(element //$NON-NLS-1$
							.getAttribute(DEFAULT_ATTRIBUTE));

					if (isWindows)
						location = location + ".exe"; //$NON-NLS-1$

					final String pluginId = element.getDeclaringExtension().getNamespaceIdentifier();
					final String finalDebuggerID = debuggerID;
					// Filter the executable if needed.
					boolean filterItem = WorkbenchActivityHelper.filterItem(new IPluginContribution() {
						public String getLocalId() {
							return finalDebuggerID;
						}

						public String getPluginId() {
							return PHPDebugPlugin.ID;
						}
					});
					if (filterItem) {
						continue;
					}

					boolean itemFound = false;

					File file = getFileFromLocation(location, pluginId);
					if (file != null && file.exists()) {
						final PHPexeItem newItem = new PHPexeItem(name, file, null, debuggerID, false);
						if (null == newItem || null == newItem.getExecutable() || newItem.getVersion() == null)
							continue; // not adding "problematic"
						// executables
						if (version != null) {
							newItem.setVersion(version);
						}
						/*
						 * Override unique ID to be always the same when loading
						 * item from extension once again (restart)
						 */
						String uniqueID = (id != null) ? id : "php-extension-exe-" + file.getPath().toString(); //$NON-NLS-1$
						newItem.setUniqueId(uniqueID);
						storeItem(newItem);
						if (isDefault) {
							String defaultExeName = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID)
									.get(PHPDebugCorePreferenceNames.DEFAULT_PHP, null);
							// Make it a default item if there is no any
							if (defaultExeName == null || getItem(defaultExeName) == null) {
								setDefaultItem(newItem);
							}
						}
						itemFound = true;
					}

					if (!itemFound) {
						PHPDebugPlugin.log(new Status(1, PHPDebugPlugin.getID(), 1001,
								"PHP executable " //$NON-NLS-1$
										+ location + " not found neither in plugin " //$NON-NLS-1$
										+ pluginId + " nor in fragments attached to it", //$NON-NLS-1$
								null));
					}
				} catch (CoreException | IOException e) {
					PHPDebugPlugin.log(e);
				}
			}
		}
	}

	/**
	 * Removes an item. In case the removed item was the default one, a
	 * different random item will be picked to be the new default one for the
	 * specific debugger.
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
			detectDefaultItem();
		}
		if (getAllItems().length == 0) {
			setDefaultItem(null);
		}
		Iterator<IPHPExesListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			PHPExesEvent phpExesEvent = new PHPExesEvent(item);
			iter.next().phpExeRemoved(phpExesEvent);
		}
	}

	/**
	 * Sets a default exe item for its debugger id.
	 * 
	 * @param defaultItem
	 */
	public void setDefaultItem(PHPexeItem defaultItem) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID);
		if (defaultItem != null) {
			preferences.put(PHPDebugCorePreferenceNames.DEFAULT_PHP, defaultItem.getName());
		} else {
			preferences.remove(PHPDebugCorePreferenceNames.DEFAULT_PHP);
		}
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	private void detectDefaultItem() {
		PHPexeItem[] allItems = getAllItems();
		Comparator<PHPexeItem> sorter = new Comparator<PHPexeItem>() {
			@Override
			public int compare(PHPexeItem a, PHPexeItem b) {
				return b.getVersion().compareTo(a.getVersion());
			}
		};
		Arrays.sort(allItems, sorter);
		if (allItems.length > 0)
			setDefaultItem(allItems[0]);
	}

	/**
	 * Save the edited PHP executable items to the plug-in preferences.
	 */
	public void save() {
		IEclipsePreferences prefs = PHPDebugPlugin.getInstancePreferences();
		final PHPexeItem[] phpItems = getEditableItems();
		final StringBuilder locationsString = new StringBuilder();
		final StringBuilder inisString = new StringBuilder();
		final StringBuilder namesString = new StringBuilder();
		final StringBuilder uniqueIdsString = new StringBuilder();
		final StringBuilder debuggersString = new StringBuilder();
		final StringBuilder defaultItemForPHPVersionString = new StringBuilder();
		final StringBuilder loadIniDefaultString = new StringBuilder();
		for (int i = 0; i < phpItems.length; i++) {
			final PHPexeItem item = phpItems[i];
			if (i > 0) {
				locationsString.append(SEPARATOR);
				inisString.append(SEPARATOR);
				namesString.append(SEPARATOR);
				uniqueIdsString.append(SEPARATOR);
				debuggersString.append(SEPARATOR);
				defaultItemForPHPVersionString.append(SEPARATOR);
				loadIniDefaultString.append(SEPARATOR);
			}
			locationsString.append(item.getExecutable().toString());
			inisString.append(item.getINILocation() != null ? item.getINILocation().toString() : NULL_PLACE_HOLDER);
			namesString.append(item.getName());
			uniqueIdsString.append(item.getUniqueId());
			debuggersString.append(item.getDebuggerID());
			loadIniDefaultString.append(item.isLoadDefaultINI() ? TRUE : FALSE);

			if (item.geDefaultForPHPVersionSize() > 0) {
				for (int j = 0; j < item.geDefaultForPHPVersionSize(); j++) {
					if (j > 0) {
						defaultItemForPHPVersionString.append(SEPARATOR_FOR_PHPVERSION);
					}
					defaultItemForPHPVersionString.append(item.getPHPVersionAtDefaultList(j).getAlias());
				}
			} else {
				defaultItemForPHPVersionString.append(NULL_PLACE_HOLDER);
			}

		}
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_NAMES, namesString.toString());
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_UNIQUE_IDS, uniqueIdsString.toString());
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOCATIONS, locationsString.toString());
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_INIS, inisString.toString());
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_DEBUGGERS, debuggersString.toString());
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_DEFAULT_FOR_VERSIONS,
				defaultItemForPHPVersionString.toString());
		prefs.put(PHPDebugCorePreferenceNames.INSTALLED_PHP_LOAD_DEFAULT_INIS, loadIniDefaultString.toString());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
	}

	public void addPHPExesListener(IPHPExesListener listener) {
		listeners.add(listener);
	}

	public void removePHPExesListener(IPHPExesListener listener) {
		listeners.remove(listener);
	}

	public void setItemDefaultForPHPVersion(PHPexeItem phPexeItem, PHPVersion phpVersion) {
		PHPexeItem oldItem = defaultItemsForPHPVersion.get(phpVersion);
		if (oldItem != null) {
			oldItem.removePHPVersionToDefaultList(phpVersion);
		}
		phPexeItem.addPHPVersionToDefaultList(phpVersion);
		defaultItemsForPHPVersion.put(phpVersion, phPexeItem);
	}

	public PHPexeItem getDefaultItemForPHPVersion(PHPVersion phpVersion) {
		return defaultItemsForPHPVersion.get(phpVersion);
	}

	public HashMap<PHPVersion, PHPexeItem> getDefaultItemsForPHPVersion() {
		return defaultItemsForPHPVersion;
	}

	public PHPexeItem[] getCompatibleItems(PHPexeItem[] allItems, PHPVersion version) {
		String versionNumber = version.getAlias().substring(3);
		PHPexeItem[] result;
		List<PHPexeItem> list = new ArrayList<PHPexeItem>();
		for (int i = 0; i < allItems.length; i++) {
			// TODO check the condition right or not
			if (allItems[i].getVersion() != null && allItems[i].getVersion().compareTo(versionNumber) >= 0) {
				list.add(allItems[i]);
			}
		}
		result = list.toArray(new PHPexeItem[list.size()]);
		return result;
	}

	private String substitudeVariables(String expression) throws CoreException {
		return VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(expression, true);
	}

	private File getFileFromLocation(String location, String pluginId) throws IOException {
		if (Paths.get(location).isAbsolute()) {
			return new File(location);
		} else {
			URL url = FileLocator.find(Platform.getBundle(pluginId), new Path(location), null);
			if (url != null) {
				url = FileLocator.resolve(url);
				String filename = url.getFile();
				return new File(filename);
			}
		}
		return null;
	}
}
