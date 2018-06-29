/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Profile session manager.
 */
public class ProfileSessionsManager {

	private static List<ProfilerDB> fProfileDBs = new ArrayList<>();
	private static List<IProfileSessionListener> fProfileSessionListeners = new ArrayList<>();
	private static ProfilerDB fCurrent;

	/**
	 * Adds new profile session to the manager
	 * 
	 * @param ProfileDB
	 *            profiler database
	 */
	public static void addSession(ProfilerDB db) {
		for (int i = 0; i < fProfileDBs.size(); ++i) {
			if (fProfileDBs.get(i).getProfileDate().equals(db.getProfileDate())) {
				return;
			}
		}
		fProfileDBs.add(db);
		for (int i = 0; i < fProfileSessionListeners.size(); ++i) {
			fProfileSessionListeners.get(i).profileSessionAdded(db);
		}
		setCurrent(db);
	}

	/**
	 * Removes profile session
	 * 
	 * @param ProfilerDB
	 *            profiler database
	 */
	public static void removeSession(ProfilerDB db) {
		if (fProfileDBs.contains(db)) {
			fProfileDBs.remove(db);
			for (int i = 0; i < fProfileSessionListeners.size(); ++i) {
				fProfileSessionListeners.get(i).profileSessionRemoved(db);
			}
			if (db == fCurrent) {
				if (fProfileDBs.size() > 0) {
					setCurrent(fProfileDBs.get(fProfileDBs.size() - 1));
				} else {
					setCurrent(null);
				}
			}
		}
	}

	/**
	 * Returns currently active profile sessions
	 * 
	 * @return ProfilerDB[] profile sessions
	 */
	public static ProfilerDB[] getSessions() {
		return fProfileDBs.toArray(new ProfilerDB[fProfileDBs.size()]);
	}

	/**
	 * Add new profile session listener
	 * 
	 * @param IProfileSessionListener
	 *            listener
	 */
	public static void addProfileSessionListener(IProfileSessionListener listener) {
		if (!fProfileSessionListeners.contains(listener)) {
			fProfileSessionListeners.add(listener);
		}
	}

	/**
	 * Removes profile session listener
	 * 
	 * @param IProfileSessionListener
	 *            listener
	 */
	public static void removeProfileSessionListener(IProfileSessionListener listener) {
		if (fProfileSessionListeners.contains(listener)) {
			fProfileSessionListeners.remove(listener);
		}
	}

	/**
	 * Returns current profile session
	 * 
	 * @return ProfilerDB current profile session
	 */
	public static ProfilerDB getCurrent() {
		return fCurrent;
	}

	/**
	 * Sets current profile session
	 * 
	 * @param ProfilerDB
	 *            current profile session
	 */
	public static void setCurrent(ProfilerDB profileDB) {
		if (fCurrent != profileDB) {
			fCurrent = profileDB;
			for (int i = 0; i < fProfileSessionListeners.size(); ++i) {
				fProfileSessionListeners.get(i).currentSessionChanged(fCurrent);
			}
		}
	}
}
