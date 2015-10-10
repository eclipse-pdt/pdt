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
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping.MappingSource;

public class PathMapper implements IXMLPreferencesStorable {

	private Map<VirtualPath, VirtualPath> remoteToLocalMap;
	private Map<VirtualPath, VirtualPath> localToRemoteMap;
	private Map<VirtualPath, Type> localToPathEntryType;
	private Map<VirtualPath, MappingSource> localToMappingSource;

	public PathMapper() {
		remoteToLocalMap = new HashMap<VirtualPath, VirtualPath>();
		localToRemoteMap = new HashMap<VirtualPath, VirtualPath>();
		localToPathEntryType = new HashMap<VirtualPath, Type>();
		localToMappingSource = new HashMap<VirtualPath, MappingSource>();
	}

	public synchronized void addEntry(String remoteFile, PathEntry entry, MappingSource source) {
		VirtualPath remotePath = new VirtualPath(remoteFile);
		VirtualPath localPath = entry.getAbstractPath().clone(); // don't break
																	// original
																	// entry
																	// path

		// last segments must match!
		if (!remotePath.getLastSegment().equalsIgnoreCase(localPath.getLastSegment())) {
			return;
		}

		while (remotePath.getSegmentsCount() > 0 && localPath.getSegmentsCount() > 1) { // local
																						// path
																						// is
																						// limited
																						// to
																						// have
																						// at
																						// least
																						// one
																						// segment
			if (!remotePath.getLastSegment().equalsIgnoreCase(localPath.getLastSegment())) {
				break;
			}
			remotePath.removeLastSegment();
			localPath.removeLastSegment();
		}
		if (!remotePath.equals(localPath)) {
			remoteToLocalMap.put(remotePath, localPath);
			localToRemoteMap.put(localPath, remotePath);
			localToPathEntryType.put(localPath, entry.getType());
			localToMappingSource.put(localPath, source);
		}
	}

	public synchronized void addServerEntry(String remoteFile, PathEntry entry, MappingSource source) {
		VirtualPath localPath = entry.getAbstractPath().clone(); // don't break
																	// original
																	// entry
																	// path
		remoteToLocalMap.put(localPath, localPath);
		localToRemoteMap.put(localPath, localPath);
		localToPathEntryType.put(localPath, entry.getType());
		localToMappingSource.put(localPath, source);
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
		VirtualPath path = getPath(remoteToLocalMap, new VirtualPath(remoteFile));
		if (path != null) {
			String localFile = path.toString();
			Type type = getPathType(path);
			if (type == Type.SERVER) {
				return null;
			} else if (type == Type.WORKSPACE) {
				IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(localFile);
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

	protected VirtualPath getPath(Map<VirtualPath, VirtualPath> map, VirtualPath path) {
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
			ListIterator<String> i = strippedSegments.listIterator(strippedSegments.size());
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
		return localToPathEntryType.get(path);
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
			MappingSource source = localToMappingSource.get(localPath);
			l.add(new Mapping(localPath, remotePath, type, source));
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
		localToMappingSource.clear();

		for (Mapping mapping : mappings) {
			localToRemoteMap.put(mapping.localPath, mapping.remotePath);
			remoteToLocalMap.put(mapping.remotePath, mapping.localPath);
			localToPathEntryType.put(mapping.localPath, mapping.type);
			localToMappingSource.put(mapping.localPath, mapping.source);
		}
	}

	/**
	 * Adds new mapping to this mapper
	 */
	public synchronized void addMapping(Mapping mapping) {
		localToRemoteMap.put(mapping.localPath, mapping.remotePath);
		remoteToLocalMap.put(mapping.remotePath, mapping.localPath);
		localToPathEntryType.put(mapping.localPath, mapping.type);
		localToMappingSource.put(mapping.localPath, mapping.source);
	}

	/**
	 * Removes mapping
	 */
	public synchronized void removeMapping(Mapping mapping) {
		localToRemoteMap.remove(mapping.localPath);
		remoteToLocalMap.remove(mapping.remotePath);
		localToPathEntryType.remove(mapping.localPath);
		localToMappingSource.remove(mapping.localPath);
	}

	public static class Mapping implements Cloneable {

		public enum MappingSource {
			UNKNOWN(Messages.PathMapper_MappingSource_Unknown_Name), ENVIRONMENT(
					Messages.PathMapper_MappingSource_Environment_Name), USER(
							Messages.PathMapper_MappingSource_User_Name);

			private String name;

			private MappingSource(String name) {
				this.name = name;
			}

			public String toString() {
				return name;
			}
		}

		public VirtualPath localPath;
		public VirtualPath remotePath;
		public Type type;
		public MappingSource source;

		public Mapping() {
		}

		public Mapping(VirtualPath localPath, VirtualPath remotePath, Type type, MappingSource source) {
			this.localPath = localPath;
			this.remotePath = remotePath;
			this.type = type;
			this.source = source;
		}

		public Mapping clone() {
			return new Mapping(localPath, remotePath, type, source);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Mapping other = (Mapping) obj;
			if (localPath == null) {
				if (other.localPath != null)
					return false;
			} else if (!localPath.equals(other.localPath))
				return false;
			if (remotePath == null) {
				if (other.remotePath != null)
					return false;
			} else if (!remotePath.equals(other.remotePath))
				return false;
			if (source != other.source)
				return false;
			if (type != other.type)
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((localPath == null) ? 0 : localPath.hashCode());
			result = prime * result + ((remotePath == null) ? 0 : remotePath.hashCode());
			result = prime * result + ((source == null) ? 0 : source.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		public String toString() {
			StringBuilder buf = new StringBuilder("Mapping { "); //$NON-NLS-1$
			buf.append(localPath).append(", ").append(remotePath).append(", ") //$NON-NLS-1$ //$NON-NLS-2$
					.append(type).append(", ").append(source.name()) //$NON-NLS-1$
					.append(" }"); //$NON-NLS-1$
			return buf.toString();
		}
	}

	public synchronized void restoreFromMap(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		remoteToLocalMap.clear();
		localToRemoteMap.clear();
		localToPathEntryType.clear();
		localToMappingSource.clear();

		Iterator<String> i = map.keySet().iterator();
		while (i.hasNext()) {
			@SuppressWarnings("unchecked")
			Map<String, Object> entryMap = (Map<String, Object>) map.get(i.next());
			String localStr = (String) entryMap.get("local"); //$NON-NLS-1$
			String remoteStr = (String) entryMap.get("remote"); //$NON-NLS-1$
			String typeStr = (String) entryMap.get("type"); //$NON-NLS-1$
			String sourceStr = (String) entryMap.get("source"); //$NON-NLS-1$
			if (localStr != null && remoteStr != null && typeStr != null) {
				Type type = Type.valueOf(typeStr);
				MappingSource source = MappingSource.UNKNOWN;
				if (sourceStr != null) {
					source = MappingSource.valueOf(sourceStr);
				}
				VirtualPath local = new VirtualPath(localStr);
				VirtualPath remote = new VirtualPath(remoteStr);
				remoteToLocalMap.put(remote, local);
				localToRemoteMap.put(local, remote);
				localToPathEntryType.put(local, type);
				localToMappingSource.put(local, source);
			}
		}
	}

	public synchronized Map<String, Object> storeToMap() {
		Map<String, Object> entries = new HashMap<String, Object>();
		Iterator<VirtualPath> i = localToRemoteMap.keySet().iterator();
		int c = 1;
		while (i.hasNext()) {
			Map<String, Object> entry = new HashMap<String, Object>();
			VirtualPath local = i.next();
			VirtualPath remote = localToRemoteMap.get(local);
			Type type = localToPathEntryType.get(local);
			MappingSource source = localToMappingSource.get(local);
			entry.put("local", local); //$NON-NLS-1$
			entry.put("remote", remote); //$NON-NLS-1$
			if (type != null) {
				entry.put("type", type.name()); //$NON-NLS-1$
			}
			if (source != null) {
				entry.put("source", source.name()); //$NON-NLS-1$
			}
			entries.put("item" + (c++), entry); //$NON-NLS-1$
		}
		return entries;
	}

}
