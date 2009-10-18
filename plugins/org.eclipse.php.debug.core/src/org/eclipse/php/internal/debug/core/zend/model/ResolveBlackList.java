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
package org.eclipse.php.internal.debug.core.zend.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;

public class ResolveBlackList implements ILaunchesListener {

	public enum Type {
		FILE, FOLDER, RECURSIVE,
	}

	private static Map<ILaunch, Map<VirtualPath, Type>> blackListMap;
	private static ResolveBlackList instance = new ResolveBlackList();

	private ResolveBlackList() {
		blackListMap = new HashMap<ILaunch, Map<VirtualPath, Type>>();

		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(this);
	}

	public static ResolveBlackList getInstance() {
		return instance;
	}

	private static Map<VirtualPath, Type> getByLaunch(ILaunch launch) {
		if (!blackListMap.containsKey(launch)) {
			blackListMap.put(launch, new HashMap<VirtualPath, Type>());
		}
		return blackListMap.get(launch);
	}

	public void add(ILaunch launch, VirtualPath path, Type type) {
		getByLaunch(launch).put(path, type);
	}

	public boolean containsEntry(ILaunch launch, String file) {
		if (!VirtualPath.isAbsolute(file)) {
			return false;
		}
		Map<VirtualPath, Type> map = getByLaunch(launch);
		for (VirtualPath path : map.keySet()) {
			VirtualPath tmp = new VirtualPath(file);
			Type type = map.get(path);
			if (type == Type.FILE) {
				if (path.equals(tmp)) {
					return true;
				}
			} else if (type == Type.FOLDER) {
				tmp.removeLastSegment();
				if (path.equals(tmp)) {
					return true;
				}
			} else if (type == Type.RECURSIVE) {
				if (path.isPrefixOf(tmp)) {
					return true;
				}
			}
		}
		return false;
	}

	public void launchesAdded(ILaunch[] launches) {
	}

	public void launchesChanged(ILaunch[] launches) {
	}

	public void launchesRemoved(ILaunch[] launches) {
		for (ILaunch l : launches) {
			blackListMap.remove(l);
		}
	}
}
