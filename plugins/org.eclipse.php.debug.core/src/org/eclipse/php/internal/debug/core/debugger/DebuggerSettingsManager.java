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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
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

	private class EventNotifier extends Job {

		private static final int ADDED = 0x01;
		private static final int REMOVED = 0x02;
		private static final int CHANGED = 0x04;

		private PropertyChangeEvent[] events;
		private int kind;
		private IDebuggerSettings settings;

		/**
		 * @param name
		 */
		public EventNotifier() {
			super(""); //$NON-NLS-1$
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			switch (kind) {
			case ADDED: {
				for (Object listener : listeners.getListeners())
					((IDebuggerSettingsListener) listener)
							.settingsAdded(settings);
				break;
			}
			case REMOVED: {
				for (Object listener : listeners.getListeners())
					((IDebuggerSettingsListener) listener)
							.settingsRemoved(settings);
				break;
			}
			case CHANGED: {
				for (Object listener : listeners.getListeners())
					((IDebuggerSettingsListener) listener)
							.settingsChanged(events);
				break;
			}
			default:
				break;
			}
			return Status.OK_STATUS;
		}

		void fireChanged(List<PropertyChangeEvent> events) {
			kind = CHANGED;
			this.events = events
					.toArray(new PropertyChangeEvent[events.size()]);
			schedule();
		}

		void fireAdded(IDebuggerSettings settings) {
			kind = ADDED;
			this.settings = settings;
			schedule();
		}

		void fireRemoved(IDebuggerSettings settings) {
			kind = REMOVED;
			this.settings = settings;
			schedule();
		}

	}

	private class OwnersListener implements IServersManagerListener,
			IPHPExesListener {

		@Override
		public void phpExeAdded(PHPExesEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(
						event.getPHPExeItem(), debuggerId);
				save(settings);
			}
		}

		@Override
		public void phpExeRemoved(PHPExesEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(
						event.getPHPExeItem(), debuggerId);
				delete(settings);
			}
		}

		@Override
		public void serverAdded(ServerManagerEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(event.getServer(),
						debuggerId);
				save(settings);
			}
		}

		@Override
		public void serverRemoved(ServerManagerEvent event) {
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				IDebuggerSettings settings = findSettings(event.getServer(),
						debuggerId);
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
	private boolean active = false;

	/**
	 * Creates default manager instance.
	 */
	private DebuggerSettingsManager() {
		startup();
	}

	/**
	 * Starts up debugger settings manager.
	 */
	public synchronized void startup() {
		if (!active) {
			ServersManager.addManagerListener(ownersListener);
			PHPexes.getInstance().addPHPExesListener(ownersListener);
			for (String debuggerId : PHPDebuggersRegistry.getDebuggersIds()) {
				settingsCache.put(debuggerId, DebuggerSettingsProviderRegistry
						.getProvider(debuggerId));
			}
			active = true;
		}
	}

	/**
	 * Shuts down debugger settings manager.
	 */
	public synchronized void shutdown() {
		if (active) {
			ServersManager.removeManagerListener(ownersListener);
			PHPexes.getInstance().removePHPExesListener(ownersListener);
			settingsCache.clear();
			active = false;
		}
	}

	/**
	 * Finds and returns debugger settings for given debugger type and owner.
	 * 
	 * @param owner
	 * @param debuggerId
	 * @return debugger settings
	 */
	public IDebuggerSettings findSettings(IUniqueIdentityElement owner,
			String debuggerId) {
		IDebuggerSettingsProvider provider = settingsCache.get(debuggerId);
		return provider.get(owner);
	}

	/**
	 * Finds and returns all debugger settings for given debugger type.
	 * 
	 * @param debuggerId
	 * @return list of debugger settings
	 */
	public List<IDebuggerSettings> findSettings(String debuggerId) {
		return settingsCache.get(debuggerId).getAll();
	}

	/**
	 * Creates and returns editable working copy for provided settings.
	 * 
	 * @param settings
	 * @return settings working copy
	 */
	public IDebuggerSettingsWorkingCopy createWorkingCopy(
			IDebuggerSettings settings) {
		return new DebuggerSettingsWorkingCopy(settings);
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
		Map<String, String> attributesCopy = settingsWorkingCopy
				.getAttributes();
		List<PropertyChangeEvent> events = new ArrayList<PropertyChangeEvent>();
		// Check if there are some new ones that were not in original
		for (String key : attributesCopy.keySet()) {
			if (!attributes.keySet().contains(key)) {
				PropertyChangeEvent event = new PropertyChangeEvent(settings,
						key, null, attributesCopy.get(key));
				events.add(event);
			}
		}
		// Check & compare original attributes with the ones from WC
		for (String key : attributes.keySet()) {
			if (!attributes.get(key).equals(attributesCopy.get(key))) {
				PropertyChangeEvent event = new PropertyChangeEvent(settings,
						key, attributes.get(key), attributesCopy.get(key));
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
		DebuggerSettingsProviderRegistry.getProvider(debuggerId).delete(
				settings);
		(new EventNotifier()).fireRemoved(settings);
	}

}
