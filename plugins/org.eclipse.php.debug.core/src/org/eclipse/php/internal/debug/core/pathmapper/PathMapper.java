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
package org.eclipse.php.internal.debug.core.pathmapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;

public class PathMapper implements IXMLPreferencesStorable {

	private Map<AbstractPath, AbstractPath> remoteToLocalMap;
	private Map<AbstractPath, AbstractPath> localToRemoteMap;
	private Map<AbstractPath, PathEntry.Type> localToPathEntryType;

	public PathMapper() {
		remoteToLocalMap = new HashMap<AbstractPath, AbstractPath>();
		localToRemoteMap = new HashMap<AbstractPath, AbstractPath>();
		localToPathEntryType = new HashMap<AbstractPath, PathEntry.Type>();
	}

	public synchronized void addEntry(String remoteFile, PathEntry entry) {
		AbstractPath remotePath = new AbstractPath(remoteFile);
		AbstractPath localPath = entry.getAbstractPath();

		while (remotePath.getSegmentsCount() > 1 && localPath.getSegmentsCount() > 1) {
			if (!remotePath.getLastSegment().equals(localPath.getLastSegment())) {
				break;
			}
			remotePath.removeLastSegment();
			localPath.removeLastSegment();
		}
		remoteToLocalMap.put(remotePath, localPath);
		localToRemoteMap.put(localPath, remotePath);
		localToPathEntryType.put(localPath, entry.getType());
	}

	public String getRemoteFile(String localFile) {
		AbstractPath path = getPath(localToRemoteMap, new AbstractPath(localFile));
		if (path != null) {
			return path.toString();
		}
		return null;
	}

	public PathEntry getLocalFile(String remoteFile) {
		AbstractPath path = getPath(remoteToLocalMap, new AbstractPath(remoteFile));
		if (path != null) {
			String localFile = path.toString();
			PathEntry.Type type = getPathType(path);
			if (type == PathEntry.Type.WORKSPACE) {
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(localFile);
				if (resource != null) {
					return new PathEntry(path, type, resource.getParent());
				}
			} else {
				File file = new File(localFile);
				if (file.exists()) {
					if (type == PathEntry.Type.INCLUDE_FOLDER || type == PathEntry.Type.INCLUDE_VAR) {
						return new PathEntry(path, type, null);
					}
					return new PathEntry(path, type, file.getParentFile());
				}
			}
		}
		return null;
	}

	protected AbstractPath getPath(Map<AbstractPath, AbstractPath> map, AbstractPath path) {
		path = path.clone();
		List<String> strippedSegments = new LinkedList<String>();
		while (path.getSegmentsCount() > 0) {
			AbstractPath mapPath = map.get(path);
			if (mapPath != null) {
				mapPath = mapPath.clone();
				ListIterator<String> i = strippedSegments.listIterator(strippedSegments.size());
				while (i.hasPrevious()) {
					mapPath.addLastSegment(i.previous());
				}
				return mapPath;
			}
			strippedSegments.add(path.removeLastSegment());
		}
		return null;
	}

	protected PathEntry.Type getPathType(AbstractPath path) {
		path = path.clone();
		while (path.getSegmentsCount() > 0) {
			PathEntry.Type type = localToPathEntryType.get(path);
			if (type != null) {
				return type;
			}
			path.removeLastSegment();
		}
		return null;
	}

	/**
	 * Returns contents of this path mapper
	 */
	public synchronized Mapping[] getMapping() {
		List<Mapping> l = new ArrayList<Mapping>(localToRemoteMap.size());
		Iterator<AbstractPath> i = localToRemoteMap.keySet().iterator();
		while (i.hasNext()) {
			AbstractPath localPath = i.next();
			AbstractPath remotePath = localToRemoteMap.get(localPath);
			PathEntry.Type type = localToPathEntryType.get(localPath);
			l.add(new Mapping(localPath, remotePath, type));
		}
		return l.toArray(new Mapping[l.size()]);
	}

	/**
	 * Sets this path mapper contents removing any previous mappings
	 */
	public synchronized void setMapping(Mapping[] mappings) {
		remoteToLocalMap.clear();
		localToRemoteMap.clear();
		localToPathEntryType.clear();

		for (Mapping mapping: mappings) {
			localToRemoteMap.put(mapping.localPath, mapping.remotePath);
			remoteToLocalMap.put(mapping.remotePath, mapping.localPath);
			localToPathEntryType.put(mapping.localPath, mapping.type);
		}
	}

	/**
	 * Adds new mapping to this mapper
	 */
	public synchronized void addMapping(Mapping mapping) {
		localToRemoteMap.put(mapping.localPath, mapping.remotePath);
		remoteToLocalMap.put(mapping.remotePath, mapping.localPath);
		localToPathEntryType.put(mapping.localPath, mapping.type);
	}

	/**
	 * Removes mapping
	 */
	public synchronized void removeMapping(Mapping mapping) {
		localToRemoteMap.remove(mapping.localPath);
		remoteToLocalMap.remove(mapping.remotePath);
		localToPathEntryType.remove(mapping.localPath);
	}

	public static class Mapping {
		public AbstractPath localPath;
		public AbstractPath remotePath;
		public PathEntry.Type type;

		public Mapping(AbstractPath localPath, AbstractPath remotePath, PathEntry.Type type) {
			this.localPath = localPath;
			this.remotePath = remotePath;
			this.type = type;
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void restoreFromMap(HashMap map) {
		remoteToLocalMap.clear();
		localToRemoteMap.clear();
		localToPathEntryType.clear();

		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			map = (HashMap) i.next();
			String localStr = (String) map.get("local"); //$NON-NLS-1$
			String remoteStr = (String) map.get("remote"); //$NON-NLS-1$
			String typeStr = (String) map.get("type"); //$NON-NLS-1$
			if (localStr != null && remoteStr != null && typeStr != null) {
				PathEntry.Type type = PathEntry.Type.valueOf(typeStr);
				AbstractPath local = new AbstractPath(localStr);
				AbstractPath remote = new AbstractPath(remoteStr);
				remoteToLocalMap.put(remote, local);
				localToRemoteMap.put(local, remote);
				localToPathEntryType.put(local, type);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized HashMap storeToMap() {
		HashMap entries = new HashMap();
		Iterator<AbstractPath> i = localToRemoteMap.keySet().iterator();
		int c= 1;
		while (i.hasNext()) {
			HashMap entry = new HashMap();
			AbstractPath local = i.next();
			AbstractPath remote = localToRemoteMap.get(local);
			PathEntry.Type type = localToPathEntryType.get(local);
			entry.put("local", local); //$NON-NLS-1$
			entry.put("remote", remote); //$NON-NLS-1$
			entry.put("type", type); //$NON-NLS-1$
			entries.put("entry" + (c++), entry); //$NON-NLS-1$
		}
		return entries;
	}
}
