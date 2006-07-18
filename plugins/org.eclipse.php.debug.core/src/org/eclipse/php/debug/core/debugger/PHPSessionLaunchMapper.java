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
package org.eclipse.php.debug.core.debugger;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.php.core.util.collections.IntHashtable;
import org.eclipse.php.core.util.collections.IntMap;
import org.eclipse.php.debug.core.launching.PHPServerLaunchDecorator;

/**
 * This class is responsible for mapping debug session id's to the ILaunch that is responsible for
 * the session.
 * 
 * @author Shalom Gibly
 */
public class PHPSessionLaunchMapper implements ILaunchesListener {

	private static PHPSessionLaunchMapper instance;
	private IntHashtable map;

	private PHPSessionLaunchMapper() {
		map = new IntHashtable(10);
	}

	private static PHPSessionLaunchMapper getInstance() {
		if (instance == null) {
			instance = new PHPSessionLaunchMapper();
			DebugPlugin.getDefault().getLaunchManager().addLaunchListener(instance);
		}
		return instance;
	}

	/**
	 * Put a session Id mapping to an ILaunch.
	 * 
	 * @param sessionID
	 * @param launch
	 */
	public static void put(int sessionID, ILaunch launch) {
		getInstance().map.put(sessionID, launch);
	}

	/**
	 * Returns the ILaunch mapped to the given session ID.
	 * In case there is no such map, null is returned.
	 * 
	 * @param sessionID
	 * @return	The mapped ILaunch, or null if non exists.
	 */
	public static ILaunch get(int sessionID) {
		return (ILaunch) getInstance().map.get(sessionID);
	}

	/**
	 * Removes the ILaunch mapped to the given sessionID and returns it.
	 * Returns null if no such session ID was found in the mapper.
	 * 
	 * @param sessionID
	 * @return	The removed ILaunch, or null if non exists.
	 */
	public static ILaunch remove(int sessionID) {
		return (ILaunch) getInstance().map.remove(sessionID);
	}

	public void launchesAdded(ILaunch[] launches) {
	}

	public void launchesChanged(ILaunch[] launches) {
	}

	public void launchesRemoved(ILaunch[] launches) {
		// Remove any launch mapping if the launch was removed and we are still mapping it.
		IntMap.Entry[] entries = new IntMap.Entry[map.size()];
		map.entrySet().toArray(entries);
		for (int i = 0; i < entries.length; i++) {
			IntMap.Entry entry = entries[i];
			for (int j = 0; j < launches.length; j++) {
				if (entry.getValue() == launches[j]) {
					map.remove(entry.getKey());
				} else if (entry.getValue() instanceof PHPServerLaunchDecorator) {
					if (((PHPServerLaunchDecorator) entry.getValue()).getLaunch() == launches[j]) {
						map.remove(entry.getKey());
					}
				}
			}
		}
	}
}
