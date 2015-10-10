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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * Abstract implementation for debugger settings provider. It contains default
 * support for loading, creating and saving debugger settings.
 * <p>
 * Client may extend this class (highly recommended)
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public abstract class AbstractDebuggerSettingsProvider implements IDebuggerSettingsProvider {

	/**
	 * Helper class for saving settings to persistent store.
	 */
	private final class PersistentSettings implements IXMLPreferencesStorable {

		private final static String TAG_OWNER = "ownerId"; //$NON-NLS-1$
		private final static String TAG_SETTINGS = "settings"; //$NON-NLS-1$
		private IDebuggerSettings settings;

		private PersistentSettings(IDebuggerSettings settings) {
			this.settings = settings;
		}

		@Override
		public Map<String, Object> storeToMap() {
			Map<String, Object> settingsMap = new HashMap<String, Object>();
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put(TAG_OWNER, settings.getOwnerId());
			for (String key : settings.getAttributes().keySet()) {
				attributes.put(key, settings.getAttribute(key));
			}
			settingsMap.put(TAG_SETTINGS, attributes);
			return (Map<String, Object>) settingsMap;
		}

		@Override
		public void restoreFromMap(Map<String, Object> map) {
			// Never used
		}

	}

	private final Map<String, IDebuggerSettings> settingsCache = new HashMap<String, IDebuggerSettings>();
	private final Map<String, IDebuggerSettings> defaultsCache = new HashMap<String, IDebuggerSettings>();
	private boolean cleanup = false;
	private String id;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsProvider
	 * #getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsProvider
	 * #get(org.eclipse.php.internal.core.IUniqueIdentityElement)
	 */
	@Override
	public IDebuggerSettings get(String ownerId) {
		IDebuggerSettings settings = settingsCache.get(ownerId);
		if (settings == null) {
			settings = defaultsCache.get(ownerId);
			if (settings == null) {
				settings = createSettings(getKind(ownerId), ownerId);
				defaultsCache.put(ownerId, settings);
			}
		}
		return settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsProvider
	 * #getAll()
	 */
	@Override
	public List<IDebuggerSettings> getAll() {
		return new ArrayList<IDebuggerSettings>(settingsCache.values());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsProvider
	 * #save(org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings)
	 */
	@Override
	public void save(IDebuggerSettings settings) {
		settingsCache.put(settings.getOwnerId(), settings);
		save();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsProvider
	 * #delete(org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings)
	 */
	@Override
	public void delete(IDebuggerSettings settings) {
		settingsCache.remove(settings.getOwnerId());
		save();
	}

	/**
	 * Implementors should create and return the appropriate settings taking
	 * into account the provided settings kind and debugger owner unique ID.
	 * 
	 * @param kind
	 * @param ownerId
	 * @return new settings
	 */
	protected abstract IDebuggerSettings createSettings(DebuggerSettingsKind kind, String ownerId);

	/**
	 * Implementors should create and return the appropriate settings taking
	 * into account the provided setting kind, debugger settings owner and
	 * attributes map loaded from persistent storage.
	 * 
	 * @param kind
	 * @param ownerId
	 * @param attributes
	 * @return recreated settings
	 */
	protected abstract IDebuggerSettings createSettings(DebuggerSettingsKind kind, String ownerId,
			Map<String, String> attributes);

	/**
	 * Restore settings with the use of data from persistent storage.
	 */
	private IDebuggerSettings restoreSettings(String ownerId, Map<String, String> attributes) {
		return createSettings(getKind(ownerId), ownerId, attributes);
	}

	/**
	 * Obtains and returns kind of debugger settings owner.
	 */
	private DebuggerSettingsKind getKind(String ownerId) {
		IUniqueIdentityElement owner = ServersManager.findServer(ownerId);
		if (owner != null)
			return DebuggerSettingsKind.PHP_SERVER;
		owner = PHPexes.getInstance().findItem(ownerId);
		if (owner != null)
			return DebuggerSettingsKind.PHP_EXE;
		// Owner is being created
		if (owner == null) {
			if (ownerId.startsWith(Server.ID_PREFIX))
				return DebuggerSettingsKind.PHP_SERVER;
			if (ownerId.startsWith(PHPexeItem.ID_PREFIX))
				return DebuggerSettingsKind.PHP_EXE;
		}
		// Should not happen
		return DebuggerSettingsKind.UNKNOWN;
	}

	/**
	 * Creates and hooks settings during provider startup if those don't exist
	 * yet for persistent PHP servers and executable configurations.
	 */
	private void hookSettings() {
		// Hook existing PHP executables
		for (PHPexeItem owner : PHPexes.getInstance().getAllItems()) {
			IDebuggerSettings settings = createSettings(DebuggerSettingsKind.PHP_EXE, owner.getUniqueId());
			save(settings);
		}
		// Hook existing PHP servers
		for (Server owner : ServersManager.getServers()) {
			IDebuggerSettings settings = createSettings(DebuggerSettingsKind.PHP_SERVER, owner.getUniqueId());
			save(settings);
		}
	}

	/**
	 * Checks if given owner exists.
	 * 
	 * @param ownerId
	 * @return <code>true</code> if given owner exists, <code>false</code>
	 *         otherwise
	 */
	private boolean exists(String ownerId) {
		// Check if owner with given ID exists
		if (PHPexes.getInstance().findItem(ownerId) == null && ServersManager.findServer(ownerId) == null)
			return false;
		return true;
	}

	/**
	 * Sets this provider ID. Should only be used by
	 * {@link DebuggerSettingsProviderRegistry} class.
	 * 
	 * @param id
	 */
	void setId(String id) {
		this.id = id;
	}

	/**
	 * Rewrite settings if there is a need.
	 */
	void cleanup() {
		if (cleanup)
			save();
	}

	/**
	 * Saves settings to XML preferences.
	 */
	void save() {
		List<IXMLPreferencesStorable> persistentSettings = new ArrayList<IXMLPreferencesStorable>();
		for (IDebuggerSettings settings : settingsCache.values()) {
			persistentSettings.add(new PersistentSettings(settings));
		}
		XMLPreferencesWriter.write(InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID), getId(), persistentSettings);
	}

	/**
	 * Loads settings from XML preferences.
	 */
	@SuppressWarnings("unchecked")
	void load() {
		List<Map<String, Object>> settingsList = XMLPreferencesReader
				.read(InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID), getId(), false);
		// Hook default settings to owners if preference does not exist
		if (settingsList.isEmpty())
			hookSettings();
		for (Map<String, Object> settings : settingsList) {
			settings = (Map<String, Object>) settings.get(PersistentSettings.TAG_SETTINGS);
			Map<String, String> attributes = new HashMap<String, String>();
			String ownerId = null;
			for (String key : settings.keySet()) {
				if (key.equals(PersistentSettings.TAG_OWNER)) {
					ownerId = (String) settings.get(key);
				} else {
					String value = (String) settings.get(key);
					attributes.put(key, value);
				}
			}
			/*
			 * Do not restore settings if somehow the related owner doesn't
			 * exist anymore. Set cleanup flag to remove trashes on shutdown.
			 */
			if (!exists(ownerId)) {
				cleanup = true;
				continue;
			}
			IDebuggerSettings restoredSettings = restoreSettings(ownerId, attributes);
			if (restoredSettings != null)
				settingsCache.put(ownerId, restoredSettings);
		}
	}

}
