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

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.debug.core.preferences.IPHPExesListener;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPExesEvent;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.server.core.manager.IServersManagerListener;
import org.eclipse.php.internal.server.core.manager.ServerManagerEvent;
import org.eclipse.php.internal.server.core.manager.ServersManager;

/**
 * General manager for handling settings of different debuggers.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public enum DebuggerSettingsManager {

	/**
	 * Singleton Instance.
	 */
	INSTANCE;

	private class EventNotifier {

		private static final int ADDED = 0x01;
		private static final int REMOVED = 0x02;
		private static final int CHANGED = 0x04;

		private PropertyChangeEvent[] events;
		private int kind;
		private IDebuggerSettings settings;

		private void fireEvent() {
			switch (kind) {
			case ADDED: {
				for (Object listener : listeners.getListeners())
					((IDebuggerSettingsListener) listener).settingsAdded(settings);
				break;
			}
			case REMOVED: {
				for (Object listener : listeners.getListeners())
					((IDebuggerSettingsListener) listener).settingsRemoved(settings);
				break;
			}
			case CHANGED: {
				for (Object listener : listeners.getListeners())
					((IDebuggerSettingsListener) listener).settingsChanged(events);
				break;
			}
			default:
				break;
			}
		}

		void fireChanged(List<PropertyChangeEvent> events) {
			kind = CHANGED;
			this.events = events.toArray(new PropertyChangeEvent[events.size()]);
			fireEvent();
		}

		void fireAdded(IDebuggerSettings settings) {
			kind = ADDED;
			this.settings = settings;
			fireEvent();
		}

		void fireRemoved(IDebuggerSettings settings) {
			kind = REMOVED;
			this.settings = settings;
			fireEvent();
		}

	}

	private class OwnersListener implements IServersManagerListener, IPHPExesListener {

		@Override
		public void phpExeAdded(PHPExesEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(event.getPHPExeItem().getUniqueId(), debuggerId);
				if (settings != null)
					save(settings);
			}
		}

		@Override
		public void phpExeRemoved(PHPExesEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(event.getPHPExeItem().getUniqueId(), debuggerId);
				if (settings != null)
					delete(settings);
			}
		}

		@Override
		public void serverAdded(ServerManagerEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(event.getServer().getUniqueId(), debuggerId);
				if (settings != null)
					save(settings);
			}
		}

		@Override
		public void serverRemoved(ServerManagerEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(event.getServer().getUniqueId(), debuggerId);
				if (settings != null)
					delete(settings);
			}
		}

		@Override
		public void serverModified(ServerManagerEvent event) {
			// ignore
		}

	}

	private final OwnersListener ownersListener = new OwnersListener();
	private final ListenerList listeners = new ListenerList();
	private final Map<String, IDebuggerSettingsProvider> settingsCache = new HashMap<String, IDebuggerSettingsProvider>();
	private final Map<IDebuggerSettings, IDebuggerSettingsWorkingCopy> settingsCopies = new HashMap<IDebuggerSettings, IDebuggerSettingsWorkingCopy>();

	/**
	 * Creates default manager instance.
	 */
	private DebuggerSettingsManager() {
		for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
			settingsCache.put(debuggerId, DebuggerSettingsProviderRegistry.getProvider(debuggerId));
		}
	}

	/**
	 * Starts up debugger settings manager.
	 */
	public synchronized void startup() {
		ServersManager.addManagerListener(ownersListener);
		PHPexes.getInstance().addPHPExesListener(ownersListener);
	}

	/**
	 * Shuts down debugger settings manager.
	 */
	public synchronized void shutdown() {
		ServersManager.removeManagerListener(ownersListener);
		PHPexes.getInstance().removePHPExesListener(ownersListener);
		for (IDebuggerSettingsProvider provider : settingsCache.values()) {
			if (provider instanceof AbstractDebuggerSettingsProvider) {
				((AbstractDebuggerSettingsProvider) provider).cleanup();
			}
		}
	}

	/**
	 * Finds and returns debugger settings for given debugger type and owner.
	 * May return <code>null</code> if there is no settings provider for
	 * particular debugger type.
	 * 
	 * @param ownerId
	 * @param debuggerId
	 * @return debugger settings
	 */
	public IDebuggerSettings findSettings(String ownerId, String debuggerId) {
		IDebuggerSettingsProvider provider = settingsCache.get(debuggerId);
		if (provider == null)
			// There is no provider registered
			return null;
		IDebuggerSettings settings = provider.get(ownerId);
		// Check if there is any pending working copy
		IDebuggerSettingsWorkingCopy pendingCopy = findWorkingCopy(settings);
		if (pendingCopy != null)
			return pendingCopy;
		return settings;
	}

	/**
	 * Finds and returns all debugger settings for given debugger type.
	 * 
	 * @param debuggerId
	 * @return list of debugger settings
	 */
	public List<IDebuggerSettings> findSettings(String debuggerId) {
		IDebuggerSettingsProvider provider = settingsCache.get(debuggerId);
		if (provider == null)
			// There is no provider registered
			return new ArrayList<IDebuggerSettings>();
		return provider.getAll();
	}

	/**
	 * Creates and returns editable working copy for provided settings. Callers
	 * of this method should always use
	 * {@link DebuggerSettingsManager#dropWorkingCopy(IDebuggerSettingsWorkingCopy)}
	 * every time when corresponding working copy is no longer used.
	 * 
	 * @param settings
	 * @return settings working copy
	 */
	public IDebuggerSettingsWorkingCopy fetchWorkingCopy(IDebuggerSettings settings) {
		IDebuggerSettingsWorkingCopy workingCopy = settingsCopies.get(settings);
		if (workingCopy == null) {
			IDebuggerSettingsProvider provider = settingsCache.get(settings.getDebuggerId());
			workingCopy = provider.createWorkingCopy(settings);
			settingsCopies.put(settings, workingCopy);
		}
		return workingCopy;
	}

	/**
	 * Drops working copy by removing it from copies cache.
	 * 
	 * @param settingsWorkingCopy
	 */
	public void dropWorkingCopy(IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		for (IDebuggerSettings key : settingsCopies.keySet()) {
			if (settingsCopies.get(key) == settingsWorkingCopy) {
				settingsCopies.remove(key);
				break;
			}
		}
	}

	/**
	 * Adds debugger settings listener to this manager.
	 * 
	 * @param listener
	 */
	public void addSettingsListener(IDebuggerSettingsListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes debugger settings listener from this manager.
	 * 
	 * @param listener
	 */
	public void removeSettingsListener(IDebuggerSettingsListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Saves the original settings with the use of provided working copy. This
	 * method should always be used by a clients whenever original settings
	 * should be saved to fire up the appropriate events.
	 * 
	 * @param settingsWorkingCopy
	 */
	public void save(IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		// Find the differences to send change notifications
		IDebuggerSettings settings = settingsWorkingCopy.getOriginal();
		Map<String, String> attributes = settings.getAttributes();
		Map<String, String> attributesCopy = settingsWorkingCopy.getAttributes();
		List<PropertyChangeEvent> events = new ArrayList<PropertyChangeEvent>();
		// Check if there are some new ones that were not in original
		for (String key : attributesCopy.keySet()) {
			if (!attributes.keySet().contains(key)) {
				PropertyChangeEvent event = new PropertyChangeEvent(settings, key, null, attributesCopy.get(key));
				events.add(event);
			}
		}
		// Check & compare original attributes with the ones from WC
		for (String key : attributes.keySet()) {
			if (!attributes.get(key).equals(attributesCopy.get(key))) {
				PropertyChangeEvent event = new PropertyChangeEvent(settings, key, attributes.get(key),
						attributesCopy.get(key));
				events.add(event);
			}
		}
		// Go out if there are no changes
		if (events.isEmpty())
			return;
		// Update original settings
		((AbstractDebuggerSettings) settings).update(settingsWorkingCopy);
		// Delegate saving of persistent data to related provider
		save(settings);
		// Send change notifications
		(new EventNotifier()).fireChanged(events);
	}

	/**
	 * Saves settings to cache & persistent storage.
	 * 
	 * @param settings
	 */
	private void save(IDebuggerSettings settings) {
		String debuggerId = settings.getDebuggerId();
		// Delegate saving to appropriate settings provider
		DebuggerSettingsProviderRegistry.getProvider(debuggerId).save(settings);
		(new EventNotifier()).fireAdded(settings);
	}

	/**
	 * Deletes settings from cache & persistent storage.
	 * 
	 * @param settings
	 */
	private void delete(IDebuggerSettings settings) {
		String debuggerId = settings.getDebuggerId();
		// Delegate deleting to appropriate settings provider
		DebuggerSettingsProviderRegistry.getProvider(debuggerId).delete(settings);
		(new EventNotifier()).fireRemoved(settings);
	}

	/**
	 * Finds and returns settings working copy if there is any
	 * 
	 * @param settings
	 * @return settings working copy or <code>null</code>
	 */
	private IDebuggerSettingsWorkingCopy findWorkingCopy(IDebuggerSettings settings) {
		for (IDebuggerSettings key : settingsCopies.keySet()) {
			if (key == settings)
				return settingsCopies.get(key);
		}
		return null;
	}

}
