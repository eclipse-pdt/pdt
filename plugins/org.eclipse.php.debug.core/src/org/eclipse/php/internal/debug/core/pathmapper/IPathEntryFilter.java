package org.eclipse.php.internal.debug.core.pathmapper;

public interface IPathEntryFilter {

	public PathEntry[] filter(PathEntry[] entries, AbstractPath remotePath);
}
