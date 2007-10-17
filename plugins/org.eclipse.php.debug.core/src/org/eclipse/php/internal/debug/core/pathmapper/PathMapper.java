package org.eclipse.php.internal.debug.core.pathmapper;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

public class PathMapper {

	private Map<AbstractPath, AbstractPath> remoteToLocalMap;
	private Map<AbstractPath, AbstractPath> localToRemoteMap;
	private Map<AbstractPath, PathEntry.Type> localToPathEntryType;
	private static PathMapper instance;

	private PathMapper() {
		remoteToLocalMap = new HashMap<AbstractPath, AbstractPath>();
		localToRemoteMap = new HashMap<AbstractPath, AbstractPath>();
		localToPathEntryType = new HashMap<AbstractPath, PathEntry.Type>();
	}

	public static synchronized PathMapper getInstance() {
		if (instance == null) {
			instance = new PathMapper();
		}
		return instance;
	}
	
	public void addEntry(String remoteFile, PathEntry entry) {
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
						return new PathEntry(path, type , null);
					}
					return new PathEntry(path, type , file.getParentFile());
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
}
