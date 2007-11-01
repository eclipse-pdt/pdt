package org.eclipse.php.internal.debug.core.zend.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.debug.core.pathmapper.AbstractPath;

public class ResolveBlackList {

	public enum Type {
		RECURSIVE,
		FOLDER,
		FILE,
	}

	private Map<AbstractPath, Type> list;

	public ResolveBlackList() {
		list = new HashMap<AbstractPath, Type>();
	}

	public void add(AbstractPath path, Type type) {
		list.put(path, type);
	}

	public boolean containsEntry(String file) {
		if (!AbstractPath.isAbsolute(file)) {
			return false;
		}
		for (AbstractPath path: list.keySet()) {
			AbstractPath tmp = new AbstractPath(file);
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
