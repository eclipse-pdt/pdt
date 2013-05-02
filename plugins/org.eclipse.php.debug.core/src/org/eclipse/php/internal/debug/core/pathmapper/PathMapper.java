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
package org.eclipse.php.internal.debug.core.pathmapper;

import java.io.File;
import java.util.*;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;

public class PathMapper implements IXMLPreferencesStorable {

	private Map<VirtualPath, VirtualPath> remoteToLocalMap;
	private Map<VirtualPath, VirtualPath> localToRemoteMap;
	private Map<VirtualPath, Type> localToPathEntryType;

	public PathMapper() {
		remoteToLocalMap = new HashMap<VirtualPath, VirtualPath>();
		localToRemoteMap = new HashMap<VirtualPath, VirtualPath>();
		localToPathEntryType = new HashMap<VirtualPath, Type>();
	}

	public synchronized void addEntry(String remoteFile, PathEntry entry) {
		VirtualPath remotePath = new VirtualPath(remoteFile);
		VirtualPath localPath = entry.getAbstractPath().clone(); // don't break
																	// original
																	// entry
																	// path

		// last segments must match!
		if (!remotePath.getLastSegment().equalsIgnoreCase(
				localPath.getLastSegment())) {
			return;
		}

		while (remotePath.getSegmentsCount() > 0
				&& localPath.getSegmentsCount() > 1) { // local path is limited
														// to have at least one
														// segment
			if (!remotePath.getLastSegment().equalsIgnoreCase(
					localPath.getLastSegment())) {
				break;
			}
			remotePath.removeLastSegment();
			localPath.removeLastSegment();
		}
		if (!remotePath.equals(localPath)) {
			remoteToLocalMap.put(remotePath, localPath);
			localToRemoteMap.put(localPath, remotePath);
			localToPathEntryType.put(localPath, entry.getType());
		}
	}

	public synchronized void addServerEntry(String remoteFile, PathEntry entry) {
		VirtualPath localPath = entry.getAbstractPath().clone(); // don't break
																	// original
																	// entry
																	// path
		remoteToLocalMap.put(localPath, localPath);
		localToRemoteMap.put(localPath, localPath);
		localToPathEntryType.put(localPath, entry.getType());
	}

	public String getRemoteFile(String localFile) {
		VirtualPath path = getPath(localToRemoteMap, new VirtualPath(localFile));
		if (path != null) {
			return path.toString();
		}
		return null;
	}

	/**
	 * Returns exact mapping for the given remote path (if exists)
	 * 
	 * @param remoteFile
	 *            Remote path
	 * @return virtual path
	 */
	public VirtualPath getLocalPathMapping(VirtualPath remotePath) {
		return remoteToLocalMap.get(remotePath);
	}

	public PathEntry getLocalFile(String remoteFile) {
		VirtualPath path = getPath(remoteToLocalMap,
				new VirtualPath(remoteFile));
		if (path != null) {
			String localFile = path.toString();
			Type type = getPathType(path);
			if (type == Type.SERVER) {
				return null;
			} else if (type == Type.WORKSPACE) {
				IResource resource = ResourcesPlugin.getWorkspace().getRoot()
						.findMember(localFile);
				if (resource != null) {
					return new PathEntry(path, type, resource.getParent());
				}
			} else {
				File file = new File(localFile);
				if (file.exists()) {
					if (type == Type.INCLUDE_FOLDER || type == Type.INCLUDE_VAR) {
						return new PathEntry(path, type, null);
					}
					return new PathEntry(path, type, file.getParentFile());
				}
			}
		}
		return null;
	}

	public PathEntry getServerFile(String remoteFile) {
		VirtualPath tmp = new VirtualPath(remoteFile);
		for (VirtualPath path : remoteToLocalMap.keySet()) {
			if (localToPathEntryType.get(path) == Type.SERVER) {
				if (path.isPrefixOf(tmp)) {
					return new PathEntry(path, Type.SERVER, null);
				}
			}
		}
		return null;
	}

	protected VirtualPath getPath(Map<VirtualPath, VirtualPath> map,
			VirtualPath path) {
		path = path.clone();
		VirtualPath mapPath = null;
		List<String> strippedSegments = new LinkedList<String>();

		while (path.getSegmentsCount() > 0) {
			mapPath = map.get(path);
			if (mapPath != null) {
				mapPath = mapPath.clone();
				break;
			}
			strippedSegments.add(path.removeLastSegment());
		}
		// Check whether device is mapped (path contains only device):
		if (mapPath == null) {
			mapPath = map.get(path);
			if (mapPath != null) {
				mapPath = mapPath.clone();
			}
		}
		// Append all stripped segments to the result path:
		if (mapPath != null) {
			ListIterator<String> i = strippedSegments
					.listIterator(strippedSegments.size());
			while (i.hasPrevious()) {
				mapPath.addLastSegment(i.previous());
			}
		}
		return mapPath;
	}

	protected Type getPathType(VirtualPath path) {
		path = path.clone();
		while (path.getSegmentsCount() > 0) {
			Type type = localToPathEntryType.get(path);
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
		Iterator<VirtualPath> i = localToRemoteMap.keySet().iterator();
		while (i.hasNext()) {
			VirtualPath localPath = i.next();
			VirtualPath remotePath = localToRemoteMap.get(localPath);
			Type type = localToPathEntryType.get(localPath);
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

		for (Mapping mapping : mappings) {
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

	public static class Mapping implements Cloneable {
		public VirtualPath localPath;
		public VirtualPath remotePath;
		public Type type;

		public Mapping() {
		}

		public Mapping(VirtualPath localPath, VirtualPath remotePath, Type type) {
			this.localPath = localPath;
			this.remotePath = remotePath;
			this.type = type;
		}

		public Mapping clone() {
			return new Mapping(localPath, remotePath, type);
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof Mapping)) {
				return false;
			}
			Mapping other = (Mapping) obj;
			return other.localPath.equals(localPath)
					&& other.remotePath.equals(remotePath)
					&& other.type == type;
		}

		public int hashCode() {
			return localPath.hashCode() + 13 * remotePath.hashCode() + 31
					* type.hashCode();
		}

		public String toString() {
			StringBuilder buf = new StringBuilder("Mapping { "); //$NON-NLS-1$
			buf.append(localPath).append(", ").append(remotePath).append(", ") //$NON-NLS-1$ //$NON-NLS-2$
					.append(type);
			return buf.toString();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void restoreFromMap(HashMap map) {
		if (map == null) {
			return;
		}
		remoteToLocalMap.clear();
		localToRemoteMap.clear();
		localToPathEntryType.clear();

		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			HashMap entryMap = (HashMap) map.get(i.next());
			String localStr = (String) entryMap.get("local"); //$NON-NLS-1$
			String remoteStr = (String) entryMap.get("remote"); //$NON-NLS-1$
			String typeStr = (String) entryMap.get("type"); //$NON-NLS-1$
			if (localStr != null && remoteStr != null && typeStr != null) {
				Type type = Type.valueOf(typeStr);
				VirtualPath local = new VirtualPath(localStr);
				VirtualPath remote = new VirtualPath(remoteStr);
				remoteToLocalMap.put(remote, local);
				localToRemoteMap.put(local, remote);
				localToPathEntryType.put(local, type);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized HashMap storeToMap() {
		HashMap entries = new HashMap();
		Iterator<VirtualPath> i = localToRemoteMap.keySet().iterator();
		int c = 1;
		while (i.hasNext()) {
			HashMap entry = new HashMap();
			VirtualPath local = i.next();
			VirtualPath remote = localToRemoteMap.get(local);
			Type type = localToPathEntryType.get(local);
			entry.put("local", local); //$NON-NLS-1$
			entry.put("remote", remote); //$NON-NLS-1$
			if (type != null) {
				entry.put("type", type.name()); //$NON-NLS-1$
			}
			entries.put("item" + (c++), entry); //$NON-NLS-1$
		}
		return entries;
	}
}
