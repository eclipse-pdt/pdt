package org.eclipse.php.internal.debug.core.zend.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;

public class ResolveBlackList {

	public enum Type {
		FILE,
		FOLDER,
		RECURSIVE,
	}

	private Map<VirtualPath, Type> list;

	public ResolveBlackList() {
		list = new HashMap<VirtualPath, Type>();
	}

	public void add(VirtualPath path, Type type) {
		list.put(path, type);
	}

	public boolean containsEntry(String file) {
		if (!VirtualPath.isAbsolute(file)) {
			return false;
		}
		for (VirtualPath path: list.keySet()) {
			VirtualPath tmp = new VirtualPath(file);
			Type type = list.get(path);
			if (type == Type.FILE) {
				if (path.equals(tmp)) {
					return true;
				}
			}
			else if (type == Type.FOLDER) {
				tmp.removeLastSegment();
				if (path.equals(tmp)) {
					return true;
				}
			}
			else if (type == Type.RECURSIVE) {
				if (path.isPrefixOf(tmp)) {
					return true;
				}
			}
		}
		return false;
	}
}
